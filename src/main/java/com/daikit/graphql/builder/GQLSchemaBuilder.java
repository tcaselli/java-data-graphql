package com.daikit.graphql.builder;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.daikit.graphql.builder.operation.mutation.GQLInputEntityTypesBuilder;
import com.daikit.graphql.builder.operation.mutation.GQLMutationDeleteResultOutputTypeBuilder;
import com.daikit.graphql.builder.operation.mutation.GQLMutationTypeBuilder;
import com.daikit.graphql.builder.operation.query.GQLQueryFilterOperatorsInputTypeBuilder;
import com.daikit.graphql.builder.operation.query.GQLQueryOrderByDirectionTypeBuilder;
import com.daikit.graphql.builder.operation.query.GQLQueryOrderByInputTypeBuilder;
import com.daikit.graphql.builder.operation.query.GQLQueryOrderByOutputTypeBuilder;
import com.daikit.graphql.builder.operation.query.GQLQueryPagingInputTypeBuilder;
import com.daikit.graphql.builder.operation.query.GQLQueryPagingOutputTypeBuilder;
import com.daikit.graphql.builder.operation.query.GQLQueryTypeBuilder;
import com.daikit.graphql.builder.types.GQLEntityTypesBuilder;
import com.daikit.graphql.builder.types.GQLEnumTypesBuilder;
import com.daikit.graphql.builder.types.GQLInterfaceTypesBuilder;
import com.daikit.graphql.builder.types.GQLReferencesBuilder;
import com.daikit.graphql.datafetcher.GQLMethodDataFetcher;
import com.daikit.graphql.datafetcher.GQLPropertyDataFetcher;
import com.daikit.graphql.meta.GQLMetaDataModel;
import com.daikit.graphql.meta.dynamic.method.GQLAbstractCustomMethod;
import com.daikit.graphql.query.output.GQLDeleteResult;
import com.daikit.graphql.query.output.GQLListLoadResult;

import graphql.schema.DataFetcher;
import graphql.schema.GraphQLCodeRegistry;
import graphql.schema.GraphQLSchema;
import graphql.schema.GraphQLType;

/**
 * Graph QL schema initializer from data meta model
 *
 * @author tcaselli
 */
public class GQLSchemaBuilder {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// BUILDER METHOD
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	/**
	 * Initialize GraphQL schema from given {@link GQLMetaDataModel}
	 *
	 * @param metaDataModel
	 *            the meta data model
	 * @param getSingleDataFetcher
	 *            the {@link DataFetcher} for 'getSingle' methods
	 * @param listDataFetcher
	 *            the {@link DataFetcher} for 'getAll' methods
	 * @param saveDataFetcher
	 *            the {@link DataFetcher} for 'save' methods
	 * @param deleteDataFetcher
	 *            the {@link DataFetcher} for 'delete' methods
	 * @param propertyDataFetchers
	 *            custom {@link GQLPropertyDataFetcher} list
	 * @param customMethodDataFetchers
	 *            the {@link GQLMethodDataFetcher} list (related to each
	 *            {@link GQLAbstractCustomMethod})
	 *
	 * @return the generated {@link GraphQLSchema}
	 */
	public GraphQLSchema buildSchema(final GQLMetaDataModel metaDataModel, final DataFetcher<?> getSingleDataFetcher,
			final DataFetcher<GQLListLoadResult> listDataFetcher, final DataFetcher<?> saveDataFetcher,
			final DataFetcher<GQLDeleteResult> deleteDataFetcher,
			final List<GQLPropertyDataFetcher<?>> propertyDataFetchers,
			final List<GQLMethodDataFetcher> customMethodDataFetchers) {

		logger.debug("START building schema...");
		final GraphQLSchema.Builder builder = GraphQLSchema.newSchema();
		logger.debug("END building schema");

		final GQLSchemaBuilderCache cache = new GQLSchemaBuilderCache();
		cache.setCodeRegistryBuilder(GraphQLCodeRegistry.newCodeRegistry());

		final List<GQLPropertyDataFetcher<?>> nullSafePropertyDataFetchers = propertyDataFetchers == null
				? Collections.emptyList()
				: propertyDataFetchers;
		final List<GQLMethodDataFetcher> nullSafeCustomMethodDataFetchers = customMethodDataFetchers == null
				? Collections.emptyList()
				: customMethodDataFetchers;

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
		builder.query(new GQLQueryTypeBuilder(cache).buildQueryType(metaDataModel, getSingleDataFetcher,
				listDataFetcher, nullSafeCustomMethodDataFetchers));
		logger.debug("END building queries");

		logger.debug("START building mutations...");
		builder.mutation(new GQLMutationTypeBuilder(cache).buildMutationType(metaDataModel, saveDataFetcher,
				deleteDataFetcher, nullSafeCustomMethodDataFetchers));
		logger.debug("END building mutations");

		// Dictionary needed for "only-referred" types.
		return builder.additionalTypes(getDictionnaryTypes(cache)).build();
	}

	private Set<GraphQLType> getDictionnaryTypes(final GQLSchemaBuilderCache cache) {
		final Set<GraphQLType> dictionnaryTypes = new HashSet<>();
		dictionnaryTypes.addAll(cache.getObjectTypes().values());
		dictionnaryTypes.addAll(cache.getInterfaceTypes().values());
		return dictionnaryTypes;
	}

}
