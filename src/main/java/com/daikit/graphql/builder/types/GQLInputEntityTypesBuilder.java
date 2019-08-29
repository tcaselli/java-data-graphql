package com.daikit.graphql.builder.types;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import com.daikit.graphql.builder.GQLSchemaBuilderUtils;
import com.daikit.graphql.builder.GQLSchemaBuilderCache;
import com.daikit.graphql.constants.GQLSchemaConstants;
import com.daikit.graphql.meta.GQLMetaModel;
import com.daikit.graphql.meta.attribute.GQLAbstractAttributeMetaData;
import com.daikit.graphql.meta.attribute.GQLAttributeEntityMetaData;
import com.daikit.graphql.meta.attribute.GQLAttributeEnumMetaData;
import com.daikit.graphql.meta.attribute.GQLAttributeListEntityMetaData;
import com.daikit.graphql.meta.attribute.GQLAttributeListEnumMetaData;
import com.daikit.graphql.meta.attribute.GQLAttributeListScalarMetaData;
import com.daikit.graphql.meta.attribute.GQLAttributeScalarMetaData;
import com.daikit.graphql.meta.internal.GQLAbstractEntityMetaDataInfos;
import com.daikit.graphql.meta.internal.GQLConcreteEntityMetaDataInfos;
import com.daikit.graphql.meta.internal.GQLInterfaceEntityMetaDataInfos;
import com.daikit.graphql.utils.Message;

import graphql.Scalars;
import graphql.schema.GraphQLInputObjectField;
import graphql.schema.GraphQLInputObjectType;
import graphql.schema.GraphQLInputType;
import graphql.schema.GraphQLList;
import graphql.schema.GraphQLObjectType;

/**
 * Type builder for entities in save inputs
 *
 * @author Thibaut Caselli
 */
public class GQLInputEntityTypesBuilder extends GQLAbstractInputOutputTypesBuilder {

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// CONSTRUCTORS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	/**
	 * Constructor
	 *
	 * @param cache
	 *            the {@link GQLSchemaBuilderCache}
	 */
	public GQLInputEntityTypesBuilder(final GQLSchemaBuilderCache cache) {
		super(cache);
	}

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// METHODS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	/**
	 * Build {@link GraphQLObjectType} from given {@link GQLMetaModel}
	 *
	 * @param metaModel
	 *            the {@link GQLMetaModel}
	 */
	public void buildInputEntities(final GQLMetaModel metaModel) {
		logger.debug("START building input entity types...");
		new BuilderImpl().build(metaModel);
		logger.debug("END building input entity types");
	}

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// PRIVATE UTILS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	private class BuilderImpl {

		private final List<GQLAbstractEntityMetaDataInfos> allInfos = new ArrayList<>();
		// For entities containing attributes referencing input type not already
		// created
		// The corresponding entity input types will be created in a second
		// phase
		private final Map<GQLAbstractEntityMetaDataInfos, Set<Class<?>>> lazyEntities = new HashMap<>();

		public void build(final GQLMetaModel metaModel) {
			allInfos.addAll(metaModel.getEmbeddedConcretes());
			allInfos.addAll(metaModel.getEmbeddedInterfaces());
			allInfos.addAll(metaModel.getAllNonEmbeddedEntities());
			allInfos.forEach(infos -> getOrBuildAndRegisterInputEntity(infos));
			generateLazyEntities();
		}

		private GraphQLInputObjectType getOrBuildAndRegisterInputEntity(final Class<?> entityClass) {
			GraphQLInputObjectType existingInputEntityType = getCache().getInputEntityTypes().get(entityClass);
			if (existingInputEntityType == null) {
				final GQLAbstractEntityMetaDataInfos infos = allInfos.stream()
						.filter(current -> entityClass.equals(current.getEntity().getEntityClass())).findFirst()
						.orElseThrow(() -> new IllegalArgumentException(
								Message.format("No meta data infos defined for entity class [{}]", entityClass)));
				existingInputEntityType = getOrBuildAndRegisterInputEntity(infos);
			}
			return existingInputEntityType;
		}

		private GraphQLInputObjectType getOrBuildAndRegisterInputEntity(final GQLAbstractEntityMetaDataInfos infos) {
			// TODO: circular dependencies between embedded entities will result
			// in infinite loop
			GraphQLInputObjectType inputObjectType = getCache().getInputEntityTypes()
					.get(infos.getEntity().getEntityClass());
			if (inputObjectType == null) {
				if (!infos.getEntity().isEmbedded() || infos.getEntity().isEmbedded() && infos.isConcrete()) {
					// Build for concrete embedded or for non embedded
					inputObjectType = buildInputEntity(infos);
				} else {
					// Build for abstract embedded
					inputObjectType = buildEmbeddedAbstractInputEntity((GQLInterfaceEntityMetaDataInfos) infos);
				}
				// inputObjectType may still be null in case of lazy field
				// loading
				if (inputObjectType != null) {
					getCache().getInputEntityTypes().put(infos.getEntity().getEntityClass(), inputObjectType);
				}
			}
			return inputObjectType;
		}

		private GraphQLInputObjectType buildInputEntity(final GQLAbstractEntityMetaDataInfos infos) {
			logger.debug(Message.format("Build input save entity type [{}]", infos.getEntity().getName()));
			final List<GraphQLInputObjectField> buildFields = buildInputEntityFields(infos);
			GraphQLInputObjectType ret = null;
			if (lazyEntities.containsKey(infos)) {
				logger.debug(Message.format("Build input save entity [{}] deferred because of lazy field loading.",
						infos.getEntity().getName()));
			} else {
				final GraphQLInputObjectType.Builder builder = GraphQLInputObjectType.newInputObject();
				builder.name(infos.getEntity().getName() + GQLSchemaConstants.INPUT_OBJECT_SUFFIX);
				builder.description("Object input type for " + (infos.getEntity().isEmbedded() ? "embedded " : "")
						+ "entity [" + infos.getEntity().getName() + "]");
				// set interface
				final List<GraphQLInputObjectField> fields = new ArrayList<>();
				// Add id input field if existing
				if (infos.getEntity().getAttributes().stream()
						.filter(attribute -> GQLSchemaConstants.FIELD_ID.equals(attribute.getName())).count() > 0) {
					fields.add(buildIdInputField());
				}
				// Add other attributes
				GQLSchemaBuilderUtils.addOrReplaceInputObjectFields(fields, buildFields);
				// Effectively add fields
				builder.fields(fields);
				ret = builder.build();
			}
			return ret;
		}

		private List<GraphQLInputObjectField> buildInputEntityFields(final GQLAbstractEntityMetaDataInfos infos) {
			logger.debug(Message.format("Build input save entity fields for entity [{}]", infos.getEntity().getName()));
			final List<GraphQLInputObjectField> inputFields = infos.getEntity().getAttributes().stream()
					.filter(attribute -> attribute.isSaveable())
					.map(attribute -> buildInputEntityField(infos, attribute)).flatMap(list -> list.stream())
					.filter(Objects::nonNull).collect(Collectors.toList());
			return inputFields;
		}

		private List<GraphQLInputObjectField> buildInputEntityField(final GQLAbstractEntityMetaDataInfos infos,
				final GQLAbstractAttributeMetaData attribute) {
			logger.debug(Message.format("Build input save entity field for attribute [{}]", attribute.getName()));
			final List<GraphQLInputObjectField> inputFields = new ArrayList<>();
			final String name = attribute.getName();
			final String description = "Input field [" + attribute.getName() + "]"
					+ getDescriptionNullableSuffix(attribute);
			// Set attribute type
			if (attribute instanceof GQLAttributeScalarMetaData) {
				inputFields.add(buildInputField(name, description,
						GQLSchemaConstants.SCALARS.get(((GQLAttributeScalarMetaData) attribute).getScalarType())));
			} else if (attribute instanceof GQLAttributeEnumMetaData) {
				inputFields.add(buildInputField(name, description,
						getCache().getEnumType(((GQLAttributeEnumMetaData) attribute).getEnumClass())));
			} else if (attribute instanceof GQLAttributeEntityMetaData) {
				if (((GQLAttributeEntityMetaData) attribute).isEmbedded()) {
					final GraphQLInputObjectType existingInputEntityType = getOrBuildAndRegisterInputEntity(
							((GQLAttributeEntityMetaData) attribute).getEntityClass());
					inputFields.add(buildInputField(name, "Input field [object-embedded] [" + attribute.getName() + "]"
							+ getDescriptionNullableSuffix(attribute), existingInputEntityType));
				} else {
					inputFields.add(buildInputField(name + GQLSchemaConstants.ID_SUFFIX, "Input field [id] of ["
							+ attribute.getName() + "]" + getDescriptionNullableSuffix(attribute), Scalars.GraphQLID));
				}
			} else if (attribute instanceof GQLAttributeListEnumMetaData) {
				inputFields.add(buildInputField(name, description, new GraphQLList(
						getCache().getEnumType(((GQLAttributeListEnumMetaData) attribute).getEnumClass()))));
			} else if (attribute instanceof GQLAttributeListEntityMetaData) {
				if (((GQLAttributeListEntityMetaData) attribute).isEmbedded()) {
					final GraphQLInputObjectType existingInputEntityType = getOrBuildAndRegisterInputEntity(
							((GQLAttributeListEntityMetaData) attribute).getForeignClass());
					inputFields.add(buildInputField(name,
							"Input field [object-embedded] [" + attribute.getName() + "]"
									+ getDescriptionNullableSuffix(attribute),
							new GraphQLList(existingInputEntityType)));
				} else {
					// If the attribute accepts cascade create/update
					if (((GQLAttributeListEntityMetaData) attribute).isCascadeSave()) {
						final GraphQLInputType foreignInputType = getCache().getInputEntityTypes()
								.get(((GQLAttributeListEntityMetaData) attribute).getForeignClass());
						if (foreignInputType == null) {
							logger.debug(Message.format("Input save entity field attribute [{}] build deferred",
									attribute.getName()));
							lazyEntities.computeIfAbsent(infos, k -> new HashSet<>())
									.add(((GQLAttributeListEntityMetaData) attribute).getForeignClass());
						} else {
							inputFields.add(buildInputField(
									name.endsWith(GQLSchemaConstants.PLURAL_SUFFIX)
											? name
											: name + GQLSchemaConstants.PLURAL_SUFFIX,
									"Input field [Array] of [" + attribute.getName() + "]"
											+ getDescriptionNullableSuffix(attribute),
									new GraphQLList(foreignInputType)));
						}
					}
					inputFields.add(buildInputField(
							(name.endsWith(GQLSchemaConstants.PLURAL_SUFFIX)
									? name.substring(0, name.length() - 1)
									: name) + GQLSchemaConstants.IDS_SUFFIX,
							"Input field [Array] of [id] of [" + attribute.getName() + "]"
									+ getDescriptionNullableSuffix(attribute),
							new GraphQLList(Scalars.GraphQLID)));
				}
			} else if (attribute instanceof GQLAttributeListScalarMetaData) {
				inputFields.add(buildInputField(name, description, new GraphQLList(
						getCache().getScalarType(((GQLAttributeListScalarMetaData) attribute).getScalarType()))));
			} else {
				throw new IllegalArgumentException(
						Message.format("Attribute could not be mapped to GraphQL [{}]", attribute));
			}
			return inputFields;
		}

		private GraphQLInputObjectType buildEmbeddedAbstractInputEntity(final GQLInterfaceEntityMetaDataInfos infos) {
			logger.debug(
					Message.format("Build input save embedded abstract entity type [{}]", infos.getEntity().getName()));
			final GraphQLInputObjectType.Builder builder = GraphQLInputObjectType.newInputObject();
			builder.name(infos.getEntity().getName() + GQLSchemaConstants.INPUT_OBJECT_SUFFIX);
			builder.description("Object input type for embedded abstract entity [" + infos.getEntity().getName() + "]");
			// set interface
			final List<GraphQLInputObjectField> fields = infos.getConcreteSubEntities().stream()
					.map(concreteSubEntityType -> buildConcreteExtendingInputEntity(concreteSubEntityType))
					.collect(Collectors.toList());
			// Effectively add fields
			builder.fields(fields);
			final GraphQLInputObjectType ret = builder.build();
			return ret;
		}

		private GraphQLInputObjectField buildConcreteExtendingInputEntity(
				final GQLConcreteEntityMetaDataInfos concreteSubEntityType) {
			logger.debug(Message.format("Build input save entity field for concrete embedded extending type [{}]",
					concreteSubEntityType.getEntity().getName()));
			final String name = GQLSchemaConstants.EMBEDDED_TYPE_PREFIX + concreteSubEntityType.getEntity().getName();
			final GraphQLInputObjectType type = getCache()
					.getInputEntityType(concreteSubEntityType.getEntity().getEntityClass());
			final GraphQLInputObjectField.Builder builder = GraphQLInputObjectField.newInputObjectField();
			builder.name(name);
			builder.description("Input field [object-embedded] [" + name + "] for concrete sub type ["
					+ concreteSubEntityType.getEntity().getName() + "] within ["
					+ concreteSubEntityType.getSuperEntity().getEntity().getName() + "]");
			builder.type(type);
			final GraphQLInputObjectField ret = builder.build();
			return ret;
		}

		private void generateLazyEntities() {
			logger.debug("START building lazy entities...");
			generateLazyEntitiesWrapped();
			logger.debug("END building lazy entities");
		}

		private void generateLazyEntitiesWrapped() {
			if (!lazyEntities.isEmpty()) {
				boolean atLeastOneBuilt = false;
				final Iterator<Entry<GQLAbstractEntityMetaDataInfos, Set<Class<?>>>> iterator = lazyEntities.entrySet()
						.iterator();
				while (iterator.hasNext()) {
					final Entry<GQLAbstractEntityMetaDataInfos, Set<Class<?>>> entry = iterator.next();
					if (entry.getValue().stream()
							.allMatch(foreignClass -> getCache().getInputEntityTypes().get(foreignClass) != null)) {
						iterator.remove();
						atLeastOneBuilt = true;
						getOrBuildAndRegisterInputEntity(entry.getKey());
					}
				}
				if (atLeastOneBuilt) {
					generateLazyEntitiesWrapped();
				} else {
					throw new IllegalArgumentException(
							Message.format("Impossible to generate input types for [{}]. Maybe because of a loop ?",
									lazyEntities.entrySet().stream()
											.map(entry -> entry.getKey().getEntity().getName() + " (fields=["
													+ entry.getValue().stream().map(clazz -> clazz.getSimpleName())
															.collect(Collectors.joining(", "))
													+ "])")
											.collect(Collectors.joining(", "))));
				}
			}
		}

		// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
		// PRIVATE UTILS
		// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

		private String getDescriptionNullableSuffix(final GQLAbstractAttributeMetaData attribute) {
			return attribute.isNullable() ? "" : " [field is mandatory for creation]";
		}

		private GraphQLInputObjectField buildIdInputField() {
			final GraphQLInputObjectField.Builder builder = GraphQLInputObjectField.newInputObjectField();
			builder.name(GQLSchemaConstants.FIELD_ID);
			builder.description("Input field [" + GQLSchemaConstants.FIELD_ID + "]");
			builder.type(Scalars.GraphQLID);
			return builder.build();
		}
	}
}
