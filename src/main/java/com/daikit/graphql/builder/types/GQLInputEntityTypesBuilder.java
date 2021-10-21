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

import com.daikit.graphql.builder.GQLExecutionContext;
import com.daikit.graphql.builder.GQLSchemaBuilderCache;
import com.daikit.graphql.builder.GQLSchemaBuilderUtils;
import com.daikit.graphql.meta.GQLInternalMetaModel;
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
	 * @param cache the {@link GQLSchemaBuilderCache}
	 */
	public GQLInputEntityTypesBuilder(final GQLSchemaBuilderCache cache) {
		super(cache);
	}

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// METHODS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	/**
	 * Build {@link GraphQLObjectType} from given {@link GQLInternalMetaModel}
	 *
	 * @param executionContext the {@link GQLExecutionContext}
	 * @param metaModel        the {@link GQLInternalMetaModel}
	 */
	public void buildInputEntities(final GQLExecutionContext executionContext, final GQLInternalMetaModel metaModel) {
		logger.debug("START building input entity types...");
		new BuilderImpl().build(executionContext, metaModel);
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

		public void build(final GQLExecutionContext executionContext, final GQLInternalMetaModel metaModel) {
			allInfos.addAll(metaModel.getEmbeddedConcretes());
			allInfos.addAll(metaModel.getEmbeddedInterfaces());
			allInfos.addAll(metaModel.getAllNonEmbeddedEntities());
			allInfos.forEach(infos -> getOrBuildAndRegisterInputEntity(executionContext, infos));
			generateLazyEntities(executionContext);
		}

		private GraphQLInputObjectType getOrBuildAndRegisterInputEntity(final GQLExecutionContext executionContext,
				final Class<?> entityClass) {
			GraphQLInputObjectType existingInputEntityType = getCache().getInputEntityTypes().get(entityClass);
			if (existingInputEntityType == null) {
				final GQLAbstractEntityMetaDataInfos infos = allInfos.stream()
						.filter(current -> entityClass.equals(current.getEntity().getEntityClass())).findFirst()
						.orElseThrow(() -> new IllegalArgumentException(
								Message.format("No meta data infos defined for entity class [{}]", entityClass)));
				existingInputEntityType = getOrBuildAndRegisterInputEntity(executionContext, infos);
			}
			return existingInputEntityType;
		}

		private GraphQLInputObjectType getOrBuildAndRegisterInputEntity(final GQLExecutionContext executionContext,
				final GQLAbstractEntityMetaDataInfos infos) {
			// TODO: circular dependencies between embedded entities will result
			// in infinite loop
			GraphQLInputObjectType inputObjectType = getCache().getInputEntityTypes()
					.get(infos.getEntity().getEntityClass());
			if (inputObjectType == null) {
				if (!infos.getEntity().isEmbedded() || infos.getEntity().isEmbedded() && infos.isConcrete()) {
					// Build for concrete embedded or for non embedded
					inputObjectType = buildInputEntity(executionContext, infos);
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

		private GraphQLInputObjectType buildInputEntity(final GQLExecutionContext executionContext,
				final GQLAbstractEntityMetaDataInfos infos) {
			logger.debug(Message.format("Build input save entity type [{}]", infos.getEntity().getName()));
			final List<GraphQLInputObjectField> buildFields = buildInputEntityFields(executionContext, infos);
			GraphQLInputObjectType ret = null;
			if (lazyEntities.containsKey(infos)) {
				logger.debug(Message.format("Build input save entity [{}] deferred because of lazy field loading.",
						infos.getEntity().getName()));
			} else {
				final GraphQLInputObjectType.Builder builder = GraphQLInputObjectType.newInputObject();
				builder.name(infos.getEntity().getName() + getConfig().getInputTypeNameSuffix());
				builder.description("Object input type for " + (infos.getEntity().isEmbedded() ? "embedded " : "")
						+ "entity [" + infos.getEntity().getName() + "]");
				// set interface
				final List<GraphQLInputObjectField> fields = new ArrayList<>();
				// Add id input field if existing
				if (infos.getEntity().getAttributes().stream()
						.filter(attribute -> getConfig().getAttributeIdName().equals(attribute.getName()))
						.count() > 0) {
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

		private List<GraphQLInputObjectField> buildInputEntityFields(final GQLExecutionContext executionContext,
				final GQLAbstractEntityMetaDataInfos infos) {
			logger.debug(Message.format("Build input save entity fields for entity [{}]", infos.getEntity().getName()));
			final List<GraphQLInputObjectField> inputFields = infos.getEntity().getAttributes().stream()
					.filter(attribute -> attribute.isSaveable(executionContext))
					.map(attribute -> buildInputEntityField(executionContext, infos, attribute))
					.flatMap(list -> list.stream()).filter(Objects::nonNull).collect(Collectors.toList());
			return inputFields;
		}

		private List<GraphQLInputObjectField> buildInputEntityField(final GQLExecutionContext executionContext,
				final GQLAbstractEntityMetaDataInfos infos, final GQLAbstractAttributeMetaData attribute) {
			logger.debug(Message.format("Build input save entity field for attribute [{}]", attribute.getName()));
			final List<GraphQLInputObjectField> inputFields = new ArrayList<>();
			final String name = attribute.getName();
			final String nullableSuffix = getDescriptionNullableSuffix(executionContext, attribute);
			// Set attribute type
			if (attribute instanceof GQLAttributeScalarMetaData) {
				inputFields.add(buildInputField(name,
						"Input field [Scalar] [" + attribute.getName() + "]" + nullableSuffix,
						getConfig().getScalarType(((GQLAttributeScalarMetaData) attribute).getScalarType()).get()));
			} else if (attribute instanceof GQLAttributeEnumMetaData) {
				inputFields
						.add(buildInputField(name, "Input field [Enum] [" + attribute.getName() + "]" + nullableSuffix,
								getCache().getEnumType(((GQLAttributeEnumMetaData) attribute).getEnumClass())));
			} else if (attribute instanceof GQLAttributeEntityMetaData) {
				if (((GQLAttributeEntityMetaData) attribute).isEmbedded()) {
					final GraphQLInputObjectType existingInputEntityType = getOrBuildAndRegisterInputEntity(
							executionContext, ((GQLAttributeEntityMetaData) attribute).getEntityClass());
					if (existingInputEntityType == null) {
						logger.debug(Message.format("Input save embedded entity field attribute [{}] build deferred",
								attribute.getName()));
						lazyEntities.computeIfAbsent(infos, k -> new HashSet<>())
								.add(((GQLAttributeEntityMetaData) attribute).getEntityClass());
					} else {
						inputFields.add(buildInputField(name,
								"Input field [Object embedded] [" + attribute.getName() + "]" + nullableSuffix,
								existingInputEntityType));
					}
				} else {
					inputFields.add(buildInputField(name + getConfig().getAttributeIdSuffix(),
							"Input field [id] of [" + attribute.getName() + "]" + nullableSuffix, Scalars.GraphQLID));
				}
			} else if (attribute instanceof GQLAttributeListEnumMetaData) {
				inputFields.add(buildInputField(name,
						"Input field [List enum] [" + attribute.getName() + "]" + nullableSuffix, new GraphQLList(
								getCache().getEnumType(((GQLAttributeListEnumMetaData) attribute).getEnumClass()))));
			} else if (attribute instanceof GQLAttributeListEntityMetaData) {
				if (((GQLAttributeListEntityMetaData) attribute).isEmbedded()) {
					final GraphQLInputObjectType existingInputEntityType = getOrBuildAndRegisterInputEntity(
							executionContext, ((GQLAttributeListEntityMetaData) attribute).getForeignClass());
					if (existingInputEntityType == null) {
						logger.debug(
								Message.format("Input save list embedded entity field attribute [{}] build deferred",
										attribute.getName()));
						lazyEntities.computeIfAbsent(infos, k -> new HashSet<>())
								.add(((GQLAttributeListEntityMetaData) attribute).getForeignClass());
					} else {
						inputFields.add(buildInputField(name,
								"Input field [List object embedded] [" + attribute.getName() + "]" + nullableSuffix,
								new GraphQLList(existingInputEntityType)));
					}
				} else {
					// If the attribute accepts cascade create/update then add
					// possibility to input children
					if (((GQLAttributeListEntityMetaData) attribute).isCascadeSave()) {
						final GraphQLInputType foreignInputType = getCache().getInputEntityTypes()
								.get(((GQLAttributeListEntityMetaData) attribute).getForeignClass());
						if (foreignInputType == null) {
							logger.debug(Message.format("Input save list entity field attribute [{}] build deferred",
									attribute.getName()));
							lazyEntities.computeIfAbsent(infos, k -> new HashSet<>())
									.add(((GQLAttributeListEntityMetaData) attribute).getForeignClass());
						} else {
							inputFields.add(buildInputField(
									name.endsWith(getConfig().getAttributePluralSuffix()) ? name
											: name + getConfig().getAttributePluralSuffix(),
									"Input field [Array] of [" + attribute.getName() + "]"
											+ getDescriptionNullableSuffix(executionContext, attribute),
									new GraphQLList(foreignInputType)));
						}
					}
					// Anyway add possibility to reference children by IDs
					inputFields.add(buildInputField(
							(name.endsWith(getConfig().getAttributePluralSuffix())
									? name.substring(0, name.length() - 1)
									: name) + getConfig().getAttributeIdPluralSuffix(),
							"Input field [Array] of [id] of [" + attribute.getName() + "]"
									+ getDescriptionNullableSuffix(executionContext, attribute),
							new GraphQLList(Scalars.GraphQLID)));
				}
			} else if (attribute instanceof GQLAttributeListScalarMetaData) {
				inputFields.add(buildInputField(name,
						"Input field [List scalar] [" + attribute.getName() + "]" + nullableSuffix,
						new GraphQLList(getCache()
								.getScalarType(((GQLAttributeListScalarMetaData) attribute).getScalarType()))));
			} else {
				throw new IllegalArgumentException(
						Message.format("Attribute could not be mapped to GraphQL [{}]", attribute));
			}
			return inputFields;
		}

		private GraphQLInputObjectType buildEmbeddedAbstractInputEntity(final GQLInterfaceEntityMetaDataInfos infos) {
			logger.debug(
					Message.format("Build input save embedded abstract entity type [{}]", infos.getEntity().getName()));
			final List<GraphQLInputObjectField> fields = infos.getConcreteSubEntities().stream()
					.map(concreteSubEntityType -> buildConcreteExtendingInputEntity(infos, concreteSubEntityType))
					.collect(Collectors.toList());
			GraphQLInputObjectType ret = null;
			if (lazyEntities.containsKey(infos)) {
				logger.debug(Message.format(
						"Build input save embedded abstract entity [{}] deferred because of lazy field loading.",
						infos.getEntity().getName()));
			} else {
				final GraphQLInputObjectType.Builder builder = GraphQLInputObjectType.newInputObject();
				builder.name(infos.getEntity().getName() + getConfig().getInputTypeNameSuffix());
				builder.description(
						"Object input type for embedded abstract entity [" + infos.getEntity().getName() + "]");
				builder.fields(fields);
				ret = builder.build();
			}
			return ret;
		}

		private GraphQLInputObjectField buildConcreteExtendingInputEntity(final GQLAbstractEntityMetaDataInfos infos,
				final GQLConcreteEntityMetaDataInfos concreteSubEntityType) {
			logger.debug(Message.format("Build input save entity field for concrete embedded extending type [{}]",
					concreteSubEntityType.getEntity().getName()));
			final String name = getConfig().getConcreteEmbeddedExtendingTypeNamePrefix()
					+ concreteSubEntityType.getEntity().getName();
			GraphQLInputObjectField ret = null;
			final GraphQLInputObjectType type = getCache().getInputEntityTypes()
					.get(concreteSubEntityType.getEntity().getEntityClass());
			if (type == null) {
				logger.debug(Message.format(
						"Input save entity field for concrete embedded extending type [{}] build deferred",
						concreteSubEntityType.getEntity().getName()));
				lazyEntities.computeIfAbsent(infos, k -> new HashSet<>())
						.add(concreteSubEntityType.getEntity().getEntityClass());
			} else {
				final GraphQLInputObjectField.Builder builder = GraphQLInputObjectField.newInputObjectField();
				builder.name(name);
				builder.description("Input field [object-embedded] [" + name + "] for concrete sub type ["
						+ concreteSubEntityType.getEntity().getName() + "] within ["
						+ concreteSubEntityType.getSuperEntity().getEntity().getName() + "]");
				builder.type(type);
				ret = builder.build();
			}
			return ret;
		}

		private void generateLazyEntities(final GQLExecutionContext executionContext) {
			logger.debug("START building lazy entities...");
			generateLazyEntitiesWrapped(executionContext);
			logger.debug("END building lazy entities");
		}

		private void generateLazyEntitiesWrapped(final GQLExecutionContext executionContext) {
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
						getOrBuildAndRegisterInputEntity(executionContext, entry.getKey());
					}
				}
				if (atLeastOneBuilt) {
					generateLazyEntitiesWrapped(executionContext);
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

		private String getDescriptionNullableSuffix(final GQLExecutionContext executionContext,
				final GQLAbstractAttributeMetaData attribute) {
			String description = "";
			if (!attribute.isNullableForCreate(executionContext)) {
				if (!attribute.isNullableForUpdate(executionContext)) {
					description += " [field is not nullable]";
				} else {
					description += " [field is not nullable for creation]";
				}
			} else if (!attribute.isNullableForUpdate(executionContext)) {
				description += " [field is not nullable for update]";
			}
			if (attribute.isMandatoryForCreate(executionContext)) {
				if (attribute.isMandatoryForUpdate(executionContext)) {
					description += " [field is mandatory]";
				} else {
					description += " [field is mandatory for creation]";
				}
			} else if (attribute.isMandatoryForUpdate(executionContext)) {
				description += " [field is mandatory for update]";
			}
			return description;
		}

		private GraphQLInputObjectField buildIdInputField() {
			final GraphQLInputObjectField.Builder builder = GraphQLInputObjectField.newInputObjectField();
			builder.name(getConfig().getAttributeIdName());
			builder.description("Input field [" + getConfig().getAttributeIdName() + "]");
			builder.type(Scalars.GraphQLID);
			return builder.build();
		}
	}
}
