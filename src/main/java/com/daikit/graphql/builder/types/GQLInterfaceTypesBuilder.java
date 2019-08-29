package com.daikit.graphql.builder.types;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.daikit.graphql.builder.GQLSchemaBuilderUtils;
import com.daikit.graphql.builder.GQLSchemaBuilderCache;
import com.daikit.graphql.datafetcher.GQLPropertyDataFetcher;
import com.daikit.graphql.meta.GQLMetaModel;
import com.daikit.graphql.meta.attribute.GQLAbstractAttributeMetaData;
import com.daikit.graphql.meta.internal.GQLAbstractEntityMetaDataInfos;
import com.daikit.graphql.meta.internal.GQLInterfaceEntityMetaDataInfos;
import com.daikit.graphql.utils.Message;

import graphql.TypeResolutionEnvironment;
import graphql.schema.GraphQLFieldDefinition;
import graphql.schema.GraphQLInterfaceType;
import graphql.schema.GraphQLObjectType;
import graphql.schema.TypeResolver;

/**
 * Type builder for entity abstract super classes
 *
 * @author Thibaut Caselli
 */
public class GQLInterfaceTypesBuilder extends GQLAbstractTypesBuilder {

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// CONSTRUCTORS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	/**
	 * Constructor
	 *
	 * @param cache
	 *            the {@link GQLSchemaBuilderCache}
	 */
	public GQLInterfaceTypesBuilder(final GQLSchemaBuilderCache cache) {
		super(cache);
	}

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// METHODS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	/**
	 * Build abstract entities {@link GraphQLInterfaceType} types from given
	 * {@link GQLMetaModel}
	 *
	 * @param metaModel
	 *            the {@link GQLMetaModel}
	 * @param propertiesDataFetchers
	 *            the list of {@link GQLPropertyDataFetcher}
	 */
	public void buildInterfaceTypes(final GQLMetaModel metaModel,
			final List<GQLPropertyDataFetcher<?>> propertiesDataFetchers) {
		logger.debug("START building interface types...");
		// Create abstract entities types
		for (final GQLInterfaceEntityMetaDataInfos infos : metaModel.getNonEmbeddedInterfaces()) {
			// Filter propertyDataFetcher by entity class name
			final List<GQLPropertyDataFetcher<?>> propertyDataFetchers = propertiesDataFetchers.stream()
					.filter(propertyDataFetcher -> propertyDataFetcher.getEntityClass()
							.isAssignableFrom(infos.getEntity().getEntityClass()))
					.collect(Collectors.toList());
			getCache().getInterfaceTypes().put(infos.getEntity().getEntityClass(),
					buildInterface(infos, propertyDataFetchers));
		}
		logger.debug("END building interface types");
	}

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// PRIVATE UTILS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	private GraphQLInterfaceType buildInterface(final GQLInterfaceEntityMetaDataInfos infos,
			final List<GQLPropertyDataFetcher<?>> interfacePropertiesDataFetchers) {
		logger.debug(Message.format("Build interface type [{}]", infos.getEntity().getName()));
		final GraphQLInterfaceType.Builder builder = GraphQLInterfaceType.newInterface();
		builder.name(infos.getEntity().getName());
		builder.description("Interface type for [" + infos.getEntity().getName() + "]");
		// Build fields
		final List<GraphQLFieldDefinition> fieldDefinitions = new ArrayList<>();

		// Add id Field
		GraphQLFieldDefinition idFieldDefinition = null;
		if (!infos.getEntity().isEmbedded()) {
			idFieldDefinition = buildIdFieldDefinition();
			fieldDefinitions.add(idFieldDefinition);
		}

		// Add other fields
		final Map<GQLAbstractAttributeMetaData, GraphQLFieldDefinition> entityFieldDefinitions = buildEntityFieldDefinitions(
				infos.getEntity());
		GQLSchemaBuilderUtils.addOrReplaceFieldDefinitions(fieldDefinitions, entityFieldDefinitions.values());

		// Effectively add fields
		builder.fields(fieldDefinitions);

		final GraphQLInterfaceType objectType = builder.build();

		// // Register data fetcher for id
		if (fieldDefinitions.contains(idFieldDefinition)) {
			registerIdDataFetcher(objectType, idFieldDefinition, interfacePropertiesDataFetchers);
		}

		// Register data fetcher for each other field definition
		registerOtherDataFetchers(objectType, entityFieldDefinitions, interfacePropertiesDataFetchers);

		getCache().getCodeRegistryBuilder().typeResolver(objectType, buildTypeResolver(infos));

		return builder.build();
	}

	private TypeResolver buildTypeResolver(final GQLInterfaceEntityMetaDataInfos infos) {
		logger.debug(Message.format("Build type resolver for interface type [{}]", infos.getEntity().getName()));
		final TypeResolver typeResolver = new TypeResolver() {
			@Override
			public GraphQLObjectType getType(final TypeResolutionEnvironment env) {
				GraphQLObjectType type = null;
				if (env.getObject() != null) {
					final GQLAbstractEntityMetaDataInfos concreteTypeInfos = infos.getConcreteSubEntities().stream()
							.filter(infos -> infos.getEntity().getEntityClass().equals(env.getObject().getClass()))
							.findFirst().orElse(null);
					if (concreteTypeInfos == null) {
						throw new IllegalArgumentException(
								Message.format("Not able to resolve type for object [{}] in interface [{}]",
										env.getObject().getClass(), infos.getEntity()));
					}
					type = getCache().getEntityType(concreteTypeInfos.getEntity().getEntityClass());
				}
				return type;
			}
		};
		return typeResolver;
	}

}
