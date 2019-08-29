package com.daikit.graphql.builder;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
import com.daikit.graphql.datafetcher.GQLAbstractDataFetcher;
import com.daikit.graphql.datafetcher.GQLAbstractGetListDataFetcher;
import com.daikit.graphql.datafetcher.GQLAbstractSaveDataFetcher;
import com.daikit.graphql.datafetcher.GQLCustomMethodDataFetcher;
import com.daikit.graphql.datafetcher.GQLDynamicAttributeRegistry;
import com.daikit.graphql.datafetcher.GQLPropertyDataFetcher;
import com.daikit.graphql.meta.GQLMetaModel;

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

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// BUILDER METHOD
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	/**
	 * Initialize GraphQL schema from given {@link GQLMetaModel}
	 *
	 * @param metaModel
	 *            the meta model
	 * @param getByIdDataFetcher
	 *            the {@link DataFetcher} for 'getById' methods
	 * @param listDataFetcher
	 *            the {@link DataFetcher} for 'getAll' methods
	 * @param saveDataFetcher
	 *            the {@link DataFetcher} for 'save' methods
	 * @param deleteDataFetcher
	 *            the {@link DataFetcher} for 'delete' methods
	 * @param customMethodDataFetcher
	 *            the {@link DataFetcher} for custom methods
	 * @param propertyDataFetchers
	 *            custom {@link GQLPropertyDataFetcher} list
	 *
	 * @return the generated {@link GraphQLSchema}
	 */
	public GraphQLSchema buildSchema(final GQLMetaModel metaModel, final DataFetcher<?> getByIdDataFetcher,
			final DataFetcher<GQLListLoadResult> listDataFetcher, final DataFetcher<?> saveDataFetcher,
			final DataFetcher<GQLDeleteResult> deleteDataFetcher, final DataFetcher<?> customMethodDataFetcher,
			final List<GQLPropertyDataFetcher<?>> propertyDataFetchers) {

		logger.debug("START building schema...");
		final GQLSchemaBuilderCache cache = new GQLSchemaBuilderCache();
		final GraphQLSchema.Builder builder = GraphQLSchema.newSchema();

		cache.setCodeRegistryBuilder(GraphQLCodeRegistry.newCodeRegistry());

		final List<GQLPropertyDataFetcher<?>> nullSafePropertyDataFetchers = propertyDataFetchers == null
				? Collections.emptyList()
				: propertyDataFetchers;

		final List<DataFetcher<?>> allDataFetchers = Stream.concat(Stream.of(getByIdDataFetcher, listDataFetcher,
				saveDataFetcher, deleteDataFetcher, customMethodDataFetcher), propertyDataFetchers.stream())
				.collect(Collectors.toList());
		setMetaModel(metaModel, allDataFetchers);

		final GQLDynamicAttributeRegistry dynAttrRegistry = new GQLDynamicAttributeRegistry(metaModel);

		if (listDataFetcher instanceof GQLAbstractGetListDataFetcher) {
			((GQLAbstractGetListDataFetcher) listDataFetcher).setDynamicAttributeRegistry(dynAttrRegistry);
		}
		if (saveDataFetcher instanceof GQLAbstractSaveDataFetcher) {
			((GQLAbstractSaveDataFetcher<?>) saveDataFetcher).setDynamicAttributeRegistry(dynAttrRegistry);
		}

		if (customMethodDataFetcher instanceof GQLCustomMethodDataFetcher) {
			logger.debug("START registering custom methods...");
			((GQLCustomMethodDataFetcher) customMethodDataFetcher).registerCustomMethods(metaModel.getCustomMethods()
					.stream().map(method -> method.getMethod()).collect(Collectors.toList()));
			logger.debug("END registering custom methods");
		}
		logger.debug("START building output reference types...");
		new GQLReferencesBuilder(cache).buildTypeReferences(metaModel);
		new GQLEnumTypesBuilder(cache).buildEnumTypes(metaModel);
		new GQLInterfaceTypesBuilder(cache).buildInterfaceTypes(metaModel, nullSafePropertyDataFetchers);
		new GQLEntityTypesBuilder(cache).buildEntityTypes(metaModel, nullSafePropertyDataFetchers);
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
		new GQLQueryFilterOperatorsInputTypeBuilder(cache).buildFilterOperatorsInputTypes(metaModel);
		logger.debug("END building queries utility types");

		logger.debug("START building mutation entities input types...");
		new GQLInputEntityTypesBuilder(cache).buildInputEntities(metaModel);
		logger.debug("END building mutation entities input types");

		logger.debug("START building queries...");
		builder.query(new GQLQueryTypeBuilder(cache).buildQueryType(metaModel, getByIdDataFetcher, listDataFetcher,
				customMethodDataFetcher));
		logger.debug("END building queries");

		logger.debug("START building mutations...");
		builder.mutation(new GQLMutationTypeBuilder(cache).buildMutationType(metaModel, saveDataFetcher,
				deleteDataFetcher, customMethodDataFetcher));
		logger.debug("END building mutations");

		// Dictionary needed for "only-referred" types.
		builder.additionalTypes(getDictionnaryTypes(cache));

		logger.debug("Register code registry");
		builder.codeRegistry(cache.getCodeRegistryBuilder().build());
		final GraphQLSchema schema = builder.build();
		logger.debug("END building schema");
		return schema;

	}

	private void setMetaModel(final GQLMetaModel metaModel, List<DataFetcher<?>> allDataFetchers) {
		allDataFetchers.forEach(dataFetcher -> {
			if (dataFetcher instanceof GQLAbstractDataFetcher) {
				((GQLAbstractDataFetcher<?>) dataFetcher).setMetaModel(metaModel);
			}
		});
	}

	private Set<GraphQLType> getDictionnaryTypes(final GQLSchemaBuilderCache cache) {
		final Set<GraphQLType> dictionnaryTypes = new HashSet<>();
		dictionnaryTypes.addAll(cache.getObjectTypes().values());
		dictionnaryTypes.addAll(cache.getInterfaceTypes().values());
		return dictionnaryTypes;
	}

}
