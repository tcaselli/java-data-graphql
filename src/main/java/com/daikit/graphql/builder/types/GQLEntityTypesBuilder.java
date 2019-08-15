package com.daikit.graphql.builder.types;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.daikit.graphql.builder.GQLBuilderUtils;
import com.daikit.graphql.builder.GQLSchemaBuilderCache;
import com.daikit.graphql.constants.GQLSchemaConstants;
import com.daikit.graphql.datafetcher.GQLConcreteSubEntityPropertyDataFetcher;
import com.daikit.graphql.datafetcher.GQLPropertyDataFetcher;
import com.daikit.graphql.meta.GQLMetaDataModel;
import com.daikit.graphql.meta.attribute.GQLAbstractAttributeMetaData;
import com.daikit.graphql.meta.internal.GQLAbstractEntityMetaDataInfos;
import com.daikit.graphql.meta.internal.GQLConcreteEntityMetaDataInfos;
import com.daikit.graphql.meta.internal.GQLInterfaceEntityMetaDataInfos;
import com.daikit.graphql.utils.Message;

import graphql.schema.GraphQLFieldDefinition;
import graphql.schema.GraphQLInterfaceType;
import graphql.schema.GraphQLObjectType;
import graphql.schema.GraphQLOutputType;
import graphql.schema.GraphQLTypeReference;

/**
 * Type builder for entities
 *
 * @author tcaselli
 */
public class GQLEntityTypesBuilder extends GQLAbstractTypesBuilder {

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// CONSTRUCTORS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	/**
	 * Constructor
	 *
	 * @param cache
	 *            the {@link GQLSchemaBuilderCache}
	 */
	public GQLEntityTypesBuilder(final GQLSchemaBuilderCache cache) {
		super(cache);
	}

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// METHODS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	/**
	 * Build entities {@link GraphQLObjectType} types from given
	 * {@link GQLMetaDataModel}
	 *
	 * @param metaDataModel
	 *            the {@link GQLMetaDataModel}
	 * @param propertiesDataFetchers
	 *            the list of all {@link GQLPropertyDataFetcher} for all
	 *            entities
	 */
	public void buildEntityTypes(final GQLMetaDataModel metaDataModel,
			final List<GQLPropertyDataFetcher<?>> propertiesDataFetchers) {
		logger.debug("START building entity types...");
		// Build entities for concrete and concrete embedded entities types
		for (final GQLConcreteEntityMetaDataInfos infos : metaDataModel.getAllConcretes()) {
			// Filter propertyDataFetcher by entity class name
			final List<GQLPropertyDataFetcher<?>> dataFetchers = propertiesDataFetchers.stream()
					.filter(propertyDataFetcher -> propertyDataFetcher.getEntityClass()
							.isAssignableFrom(infos.getEntity().getEntityClass()))
					.collect(Collectors.toList());
			getCache().getObjectTypes().put(infos.getEntity().getEntityClass(), buildEntity(infos, dataFetchers));
		}
		// Build entities for abstract embedded entities types
		for (final GQLInterfaceEntityMetaDataInfos infos : metaDataModel.getEmbeddedInterfaces()) {
			getCache().getObjectTypes().put(infos.getEntity().getEntityClass(), buildEmbeddedInterface(infos));
		}
		logger.debug("END building entity types");
	}

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// PRIVATE UTILS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	private GraphQLObjectType buildEntity(final GQLAbstractEntityMetaDataInfos infos,
			final List<GQLPropertyDataFetcher<?>> entityPropertiesDataFetchers) {
		logger.debug(Message.format("Build entity type [{}]", infos.getEntity().getName()));
		final GraphQLObjectType.Builder builder = GraphQLObjectType.newObject();
		builder.name(infos.getEntity().getName());
		builder.description("Object type for entity [" + infos.getEntity().getName() + "]");

		final List<GraphQLFieldDefinition> fieldDefinitions = new ArrayList<>();

		GraphQLFieldDefinition idFieldDefinition = null;
		if (!infos.isEmbedded()) {
			idFieldDefinition = buildIdFieldDefinition();
			fieldDefinitions.add(idFieldDefinition);
		}

		// Add fields from interface
		if (!infos.isEmbedded()) {
			infos.getSuperInterfaces().forEach(superInterface -> {
				final GraphQLInterfaceType interfaceType = getCache().getInterfaceTypes()
						.get(superInterface.getEntity().getEntityClass());
				// set interface
				builder.withInterface(interfaceType);
				// Add fields from interface
				GQLBuilderUtils.addOrReplaceFieldDefinitions(fieldDefinitions, interfaceType.getFieldDefinitions());
			});
		}

		// Add other fields
		final Map<GQLAbstractAttributeMetaData, GraphQLFieldDefinition> entityFieldDefinitions = buildEntityFieldDefinitions(
				infos.getEntity());
		GQLBuilderUtils.addOrReplaceFieldDefinitions(fieldDefinitions, entityFieldDefinitions.values());

		// Effectively add fields
		builder.fields(fieldDefinitions);

		final GraphQLObjectType objectType = builder.build();

		// // Register data fetcher for id
		if (fieldDefinitions.contains(idFieldDefinition)) {
			registerIdDataFetcher(objectType, idFieldDefinition, entityPropertiesDataFetchers);
		}

		// Register data fetcher for each other field definition
		registerOtherDataFetchers(objectType, entityFieldDefinitions, entityPropertiesDataFetchers);

		return objectType;
	}

	private GraphQLObjectType buildEmbeddedInterface(final GQLInterfaceEntityMetaDataInfos infos) {
		logger.debug(Message.format("Build embedded abstract entity type [{}]", infos.getEntity().getName()));
		final GraphQLObjectType.Builder builder = GraphQLObjectType.newObject();
		builder.name(infos.getEntity().getName());
		builder.description("Object type for embedded abstract entity [" + infos.getEntity().getName() + "]");
		// Create one field by concrete extending types
		final Map<GQLConcreteEntityMetaDataInfos, GraphQLFieldDefinition> fieldDefinitions = infos
				.getConcreteSubEntities().stream().collect(LinkedHashMap::new, (map, concreteSubEntityType) -> map
						.put(concreteSubEntityType, buildConcreteEmbeddedExtendingType(concreteSubEntityType)),
						Map::putAll);

		// Effectively add fields
		builder.fields(new ArrayList<>(fieldDefinitions.values()));

		// Build embedded entity type
		final GraphQLObjectType objectType = builder.build();

		// Register data fetcher for each field definition
		fieldDefinitions.entrySet().stream()
				.forEach(entry -> getCache().getCodeRegistryBuilder().dataFetcher(objectType, entry.getValue(),
						new GQLConcreteSubEntityPropertyDataFetcher(entry.getKey().getEntity().getEntityClass())));

		return objectType;
	}

	private GraphQLFieldDefinition buildConcreteEmbeddedExtendingType(
			final GQLConcreteEntityMetaDataInfos concreteSubEntityType) {
		logger.debug("Build field definition for concrete embedded extending type [{}]",
				concreteSubEntityType.getEntity().getName());
		final GraphQLFieldDefinition.Builder builder = GraphQLFieldDefinition.newFieldDefinition();
		final String name = GQLSchemaConstants.EMBEDDED_TYPE_PREFIX + concreteSubEntityType.getEntity().getName();
		final GraphQLOutputType type = new GraphQLTypeReference(
				getCache().getEntityTypeName(concreteSubEntityType.getEntity().getEntityClass()));
		builder.name(name);
		builder.description("Field definition [object-embedded] [" + name + "] for concrete sub type ["
				+ concreteSubEntityType.getEntity().getName() + "] within ["
				+ concreteSubEntityType.getSuperEntity().getEntity().getName() + "]");
		builder.type(type);
		logger.debug(Message.format("Field definition created for [{}] with type [{}]", name,
				GQLBuilderUtils.typeToString(type)));
		return builder.build();
	}

}
