package com.daikit.graphql.builder;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.daikit.graphql.builder.types.GQLEntityTypesBuilder;
import com.daikit.graphql.builder.types.GQLEnumTypesBuilder;
import com.daikit.graphql.builder.types.GQLInputEntityTypesBuilder;
import com.daikit.graphql.builder.types.GQLInterfaceTypesBuilder;
import com.daikit.graphql.builder.types.GQLMutationDeleteResultOutputTypeBuilder;
import com.daikit.graphql.builder.types.GQLMutationTypeBuilder;
import com.daikit.graphql.builder.types.GQLQueryFilterOperatorsInputTypeBuilder;
import com.daikit.graphql.builder.types.GQLQueryOrderByDirectionTypeBuilder;
import com.daikit.graphql.builder.types.GQLQueryOrderByInputTypeBuilder;
import com.daikit.graphql.builder.types.GQLQueryOrderByOutputTypeBuilder;
import com.daikit.graphql.builder.types.GQLQueryPagingInputTypeBuilder;
import com.daikit.graphql.builder.types.GQLQueryPagingOutputTypeBuilder;
import com.daikit.graphql.builder.types.GQLQueryTypeBuilder;
import com.daikit.graphql.builder.types.GQLReferencesBuilder;
import com.daikit.graphql.data.output.GQLDeleteResult;
import com.daikit.graphql.data.output.GQLListLoadResult;
import com.daikit.graphql.datafetcher.GQLPropertyDataFetcher;
import com.daikit.graphql.datafetcher.abs.GQLCustomMethodDataFetcher;
import com.daikit.graphql.datafetcher.abs.GQLAbstractSaveDataFetcher;
import com.daikit.graphql.meta.GQLMetaDataModel;

import graphql.schema.DataFetcher;
import graphql.schema.GraphQLCodeRegistry;
import graphql.schema.GraphQLSchema;
import graphql.schema.GraphQLType;

/**
 * Graph QL schema initializer from data meta model
 *
 * @author Thibaut Caselli
 */
public class GQLSchemaBuilder {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	private GQLSchemaBuilderCache cache = new GQLSchemaBuilderCache();

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// GETTERS / SETTERS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	/**
	 * Set the cache
	 *
	 * @param cache
	 *            the {@link GQLSchemaBuilderCache}
	 * @return this {@link GQLSchemaBuilder} instance
	 */
	public GQLSchemaBuilder setCache(GQLSchemaBuilderCache cache) {
		this.cache = cache;
		return this;
	}

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// BUILDER METHOD
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	/**
	 * Initialize GraphQL schema from given {@link GQLMetaDataModel}
	 *
	 * @param metaDataModel
	 *            the meta data model
	 * @param getByIdDataFetcher
	 *            the {@link DataFetcher} for 'getById' methods
	 * @param listDataFetcher
	 *            the {@link DataFetcher} for 'getAll' methods
	 * @param saveDataFetcher
	 *            the {@link DataFetcher} for 'save' methods
	 * @param deleteDataFetcher
	 *            the {@link DataFetcher} for 'delete' methods
	 * @param customMethodsDataFetcher
	 *            the {@link DataFetcher} for custom methods
	 * @param propertyDataFetchers
	 *            custom {@link GQLPropertyDataFetcher} list
	 *
	 * @return the generated {@link GraphQLSchema}
	 */
	public GraphQLSchema buildSchema(final GQLMetaDataModel metaDataModel, final DataFetcher<?> getByIdDataFetcher,
			final DataFetcher<GQLListLoadResult> listDataFetcher, final DataFetcher<?> saveDataFetcher,
			final DataFetcher<GQLDeleteResult> deleteDataFetcher, final DataFetcher<?> customMethodsDataFetcher,
			final List<GQLPropertyDataFetcher<?>> propertyDataFetchers) {

		logger.debug("START building schema...");
		final GraphQLSchema.Builder builder = GraphQLSchema.newSchema();

		cache.setCodeRegistryBuilder(GraphQLCodeRegistry.newCodeRegistry());

		final List<GQLPropertyDataFetcher<?>> nullSafePropertyDataFetchers = propertyDataFetchers == null
				? Collections.emptyList()
				: propertyDataFetchers;

		if (customMethodsDataFetcher instanceof GQLCustomMethodDataFetcher) {
			logger.debug("START registering custom methods...");
			((GQLCustomMethodDataFetcher) customMethodsDataFetcher).registerCustomMethods(metaDataModel
					.getCustomMethods().stream().map(method -> method.getMethod()).collect(Collectors.toList()));
			logger.debug("END registering custom methods");
		}

		if (saveDataFetcher instanceof GQLAbstractSaveDataFetcher) {
			logger.debug("START registering dynamic attribute setters...");
			((GQLAbstractSaveDataFetcher) saveDataFetcher)
					.registerDynamicAttributeSetters(metaDataModel.getDynamicAttributeSetters());
			logger.debug("END registering dynamic attribute setters");
		}

		logger.debug("START building output reference types...");
		new GQLReferencesBuilder(cache).buildTypeReferences(metaDataModel);
		new GQLEnumTypesBuilder(cache).buildEnumTypes(metaDataModel);
		new GQLInterfaceTypesBuilder(cache).buildInterfaceTypes(metaDataModel, nullSafePropertyDataFetchers);
		new GQLEntityTypesBuilder(cache).buildEntityTypes(metaDataModel, nullSafePropertyDataFetchers);
		logger.debug("END building output reference types");

		logger.debug("END building mutations utility types...");
		new GQLMutationDeleteResultOutputTypeBuilder(cache).buildDeleteResultOutputType();
		logger.debug("END building mutations utility types");

		logger.debug("END building queries utility types...");
		new GQLQueryPagingInputTypeBuilder(cache).buildPagingInputType();
		new GQLQueryPagingOutputTypeBuilder(cache).buildPagingOutputType();
		new GQLQueryOrderByDirectionTypeBuilder(cache).buildOrderByDirectionType();
		new GQLQueryOrderByInputTypeBuilder(cache).buildOrderByInputType();
		new GQLQueryOrderByOutputTypeBuilder(cache).buildOrderByOutputType();
		new GQLQueryFilterOperatorsInputTypeBuilder(cache).buildFilterOperatorsInputTypes(metaDataModel);
		logger.debug("END building queries utility types");

		logger.debug("START building mutation entities input types...");
		new GQLInputEntityTypesBuilder(cache).buildInputEntities(metaDataModel);
		logger.debug("END building mutation entities input types");

		logger.debug("START building queries...");
		builder.query(new GQLQueryTypeBuilder(cache).buildQueryType(metaDataModel, getByIdDataFetcher, listDataFetcher,
				customMethodsDataFetcher));
		logger.debug("END building queries");

		logger.debug("START building mutations...");
		builder.mutation(new GQLMutationTypeBuilder(cache).buildMutationType(metaDataModel, saveDataFetcher,
				deleteDataFetcher, customMethodsDataFetcher));
		logger.debug("END building mutations");

		// Dictionary needed for "only-referred" types.
		builder.additionalTypes(getDictionnaryTypes(cache));

		logger.debug("Register code registry");
		builder.codeRegistry(cache.getCodeRegistryBuilder().build());
		final GraphQLSchema schema = builder.build();
		logger.debug("END building schema");
		return schema;

	}

	private Set<GraphQLType> getDictionnaryTypes(final GQLSchemaBuilderCache cache) {
		final Set<GraphQLType> dictionnaryTypes = new HashSet<>();
		dictionnaryTypes.addAll(cache.getObjectTypes().values());
		dictionnaryTypes.addAll(cache.getInterfaceTypes().values());
		return dictionnaryTypes;
	}

}
