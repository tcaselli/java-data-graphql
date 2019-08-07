package com.daikit.graphql.builder.operation.query;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import com.daikit.graphql.builder.GQLSchemaBuilderCache;
import com.daikit.graphql.builder.operation.GQLAbstractInputOutputTypesBuilder;
import com.daikit.graphql.builder.operation.GQLMethodBuilder;
import com.daikit.graphql.builder.utils.GQLBuilderUtils;
import com.daikit.graphql.constants.GQLSchemaConstants;
import com.daikit.graphql.datafetcher.GQLMethodDataFetcher;
import com.daikit.graphql.meta.GQLMetaDataModel;
import com.daikit.graphql.meta.data.attribute.GQLAbstractAttributeMetaData;
import com.daikit.graphql.meta.data.attribute.GQLAttributeEmbeddedEntityMetaData;
import com.daikit.graphql.meta.data.attribute.GQLAttributeEntityMetaData;
import com.daikit.graphql.meta.data.attribute.GQLAttributeEnumMetaData;
import com.daikit.graphql.meta.data.attribute.GQLAttributeListEmbeddedEntityMetaData;
import com.daikit.graphql.meta.data.attribute.GQLAttributeListEntityMetaData;
import com.daikit.graphql.meta.data.attribute.GQLAttributeListEnumMetaData;
import com.daikit.graphql.meta.data.attribute.GQLAttributeListScalarMetaData;
import com.daikit.graphql.meta.data.attribute.GQLAttributeScalarMetaData;
import com.daikit.graphql.meta.data.entity.GQLAbstractEntityMetaData;
import com.daikit.graphql.meta.data.method.GQLAbstractMethodMetaData;
import com.daikit.graphql.meta.dynamic.method.GQLAbstractCustomMethod;
import com.daikit.graphql.meta.internal.GQLAbstractEntityMetaDataInfos;
import com.daikit.graphql.query.output.GQLListLoadResult;
import com.daikit.graphql.utils.Assert;
import com.daikit.graphql.utils.Message;

import graphql.Scalars;
import graphql.schema.DataFetcher;
import graphql.schema.GraphQLArgument;
import graphql.schema.GraphQLFieldDefinition;
import graphql.schema.GraphQLInputObjectField;
import graphql.schema.GraphQLInputObjectType;
import graphql.schema.GraphQLList;
import graphql.schema.GraphQLObjectType;

/**
 * Type builder for queries
 *
 * @author tcaselli
 */
public class GQLQueryTypeBuilder extends GQLAbstractInputOutputTypesBuilder {

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// CONSTRUCTORS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	/**
	 * Constructor
	 *
	 * @param cache
	 *            the {@link GQLSchemaBuilderCache}
	 */
	public GQLQueryTypeBuilder(final GQLSchemaBuilderCache cache) {
		super(cache);
	}

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// METHODS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	/**
	 * Build query type
	 *
	 * @param metaDataModel
	 *            the {@link GQLMetaDataModel}
	 * @param getSingleDataFetcher
	 *            the {@link DataFetcher} for getSingle methods
	 * @param listDataFetcher
	 *            the {@link DataFetcher} for getAll methods
	 * @param customMethodDataFetchers
	 *            the {@link Map} of {@link GQLAbstractCustomMethod} related
	 *            {@link GQLMethodDataFetcher}
	 * @return the created {@link GraphQLObjectType}
	 */
	public GraphQLObjectType buildQueryType(final GQLMetaDataModel metaDataModel,
			final DataFetcher<?> getSingleDataFetcher, final DataFetcher<GQLListLoadResult> listDataFetcher,
			final List<GQLMethodDataFetcher> customMethodDataFetchers) {
		logger.debug("START building query types...");

		final GraphQLObjectType.Builder builder = GraphQLObjectType.newObject();
		builder.name("QueryType");
		builder.description("Query type from meta model");

		final List<GraphQLFieldDefinition> getSingleFieldDefinitions = new ArrayList<>();
		final List<GraphQLFieldDefinition> getAllFieldDefinitions = new ArrayList<>();

		logger.debug("Build query types for interfaces...");

		metaDataModel.getNonEmbeddedInterfaces().forEach(infos -> {
			getSingleFieldDefinitions.add(buildGetSingleQueryFieldDefinitions(infos, true));
			getAllFieldDefinitions.add(buildGetAllQueryFieldDefinitions(infos, true));
		});

		logger.debug("Build query types for entities...");
		metaDataModel.getNonEmbeddedConcretes().forEach(infos -> {
			getSingleFieldDefinitions.add(buildGetSingleQueryFieldDefinitions(infos, false));
			getAllFieldDefinitions.add(buildGetAllQueryFieldDefinitions(infos, false));
		});

		builder.fields(getSingleFieldDefinitions);
		builder.fields(getAllFieldDefinitions);

		logger.debug("Build query types for custom methods...");
		final Map<GQLAbstractMethodMetaData, GraphQLFieldDefinition> customMethodFieldDefinitions = new GQLMethodBuilder(
				getCache())
						.buildMethods(metaDataModel.getCustomMethods().stream()
								.filter(customMethod -> !customMethod.isMutation()).collect(Collectors.toList()));
		builder.fields(new ArrayList<>(customMethodFieldDefinitions.values()));

		final GraphQLObjectType queryType = builder.build();

		if (!getSingleFieldDefinitions.isEmpty()) {
			Assert.assertNotNull(getSingleDataFetcher,
					"If there is at least one entity with an ID property defined then 'getSingleDataFetcher' must be non null");
			getSingleFieldDefinitions.forEach(fieldDefinition -> getCache().getCodeRegistryBuilder()
					.dataFetcher(queryType, fieldDefinition, getSingleDataFetcher));
		}

		if (!getAllFieldDefinitions.isEmpty()) {
			Assert.assertNotNull(listDataFetcher,
					"If there is at least one entity defined then 'listDataFetcher' must be non null");
			getAllFieldDefinitions.forEach(fieldDefinition -> getCache().getCodeRegistryBuilder().dataFetcher(queryType,
					fieldDefinition, listDataFetcher));
		}

		if (!customMethodFieldDefinitions.isEmpty()) {
			Assert.assertNotNull(customMethodDataFetchers,
					"If there is at least one custom method defined then 'customMethodDataFetchers' must be non null");
			customMethodFieldDefinitions.entrySet().forEach(entry -> getCache().getCodeRegistryBuilder()
					.dataFetcher(queryType, entry.getValue(), customMethodDataFetchers.stream().filter(
							methodDataFetcher -> methodDataFetcher.getMethod().equals(entry.getKey().getMethod()))
							.findFirst().get()));
		}

		logger.debug("END building query types");
		return queryType;
	}

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// PRIVATE UTILS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	private GraphQLFieldDefinition buildGetSingleQueryFieldDefinitions(final GQLAbstractEntityMetaDataInfos infos,
			final boolean isInterface) {
		logger.debug(
				Message.format("Build 'getSingle' query type for " + (isInterface ? "interface" : "entity") + " [{}]",
						infos.getEntity().getName()));
		// Query for single entity
		final GraphQLFieldDefinition.Builder builder = GraphQLFieldDefinition.newFieldDefinition();
		builder.name(GQLSchemaConstants.QUERY_GET_SINGLE_PREFIX + infos.getEntity().getName());
		builder.description(
				(isInterface ? "Interface" : "Entity") + " single result query for [" + infos.getEntity().getName()
						+ "]. This method only accepts the mandatory [id] parameter and returns a single result.");
		builder.type(isInterface
				? getCache().getInterfaceType(infos.getEntity().getEntityClass())
				: getCache().getEntityType(infos.getEntity().getEntityClass()));
		builder.argument(buildArgumentNonNull(getIdAttribute(infos)));
		return builder.build();
	}

	private GraphQLFieldDefinition buildGetAllQueryFieldDefinitions(final GQLAbstractEntityMetaDataInfos infos,
			final boolean isInterface) {
		logger.debug("Build 'getAll' query type for " + (isInterface ? "interface" : "entity") + " [{}]",
				infos.getEntity().getName());
		// Query for list of entities
		final GraphQLFieldDefinition.Builder builder = GraphQLFieldDefinition.newFieldDefinition();
		builder.name(GQLSchemaConstants.QUERY_GET_LIST_PREFIX + infos.getEntity().getName());
		builder.description(
				(isInterface ? "Interface" : "Entity") + " listing query for [" + infos.getEntity().getName()
						+ "]. This method accepts several filtering arguments and returns a list load result. Give a ["
						+ GQLSchemaConstants.PAGING + "] argument if you need paging.");
		builder.type(buildListResultWrapperType(infos, isInterface));
		builder.arguments(buildListQueryArguments(infos));
		return builder.build();
	}

	private List<GraphQLArgument> buildListQueryArguments(final GQLAbstractEntityMetaDataInfos infos) {
		logger.debug(Message.format("Build paging query arguments for entity [{}]", infos.getEntity().getName()));
		final List<GraphQLArgument> arguments = new ArrayList<>();
		arguments.add(buildQueryFilterArgument(infos));
		arguments.add(buildPagingQueryArgument());
		arguments.add(buildOrderByQueryArgument());
		return arguments;
	}

	private GraphQLArgument buildPagingQueryArgument() {
		final GraphQLArgument.Builder builder = GraphQLArgument.newArgument();
		builder.name(GQLSchemaConstants.PAGING);
		builder.type(getCache().getPagingInputObjectType());
		return builder.build();
	}

	private GraphQLArgument buildQueryFilterArgument(final GQLAbstractEntityMetaDataInfos infos) {
		logger.debug(Message.format("Build filter query argument for entity [{}]", infos.getEntity().getName()));
		final GraphQLArgument.Builder builder = GraphQLArgument.newArgument();
		builder.name("filter");
		builder.description("Query filter argument for [" + infos.getEntity().getName() + "]");
		builder.type(buildQueryFilterObjectType(infos));
		final GraphQLArgument ret = builder.build();
		return ret;
	}

	private GraphQLArgument buildOrderByQueryArgument() {
		final GraphQLArgument.Builder builder = GraphQLArgument.newArgument();
		builder.name(GQLSchemaConstants.ORDER_BY);
		builder.type(new GraphQLList(getCache().getOrderByInputObjectType()));
		return builder.build();
	}

	private GraphQLInputObjectType buildQueryFilterObjectType(final GQLAbstractEntityMetaDataInfos infos) {
		final GraphQLInputObjectType.Builder builder = GraphQLInputObjectType.newInputObject();
		builder.name("Filter" + infos.getEntity().getName());
		builder.description("Query filter object type for entity [" + infos.getEntity().getName() + "]");

		final GraphQLInputObjectType filterFieldsObjectType = buildQueryFilterFieldsObjectType(infos);

		// TODO add AND and OR filters here

		// final GraphQLInputObjectType.Builder andOrFilterBuilder =
		// GraphQLInputObjectType.newInputObject();
		// final GraphQLInputObjectType andOrFilter =
		// andOrFilterBuilder.build();
		// return builder.build();

		return filterFieldsObjectType;
	}

	private GraphQLInputObjectType buildQueryFilterFieldsObjectType(final GQLAbstractEntityMetaDataInfos infos) {
		final GraphQLInputObjectType.Builder builder = GraphQLInputObjectType.newInputObject();
		builder.name("FilterFields" + infos.getEntity().getName());
		builder.description("Query filter fields object type for entity [" + infos.getEntity().getName() + "]");
		final List<GraphQLInputObjectField> objectFields = new ArrayList<>();
		// Add fields
		GQLBuilderUtils.addOrReplaceInputObjectFields(objectFields,
				buildQueryFilterInputObjectFields(infos.getEntity()));
		builder.fields(objectFields);
		return builder.build();
	}

	private List<GraphQLInputObjectField> buildQueryFilterInputObjectFields(final GQLAbstractEntityMetaData entity) {
		return entity.getAttributes().stream().filter(attribute -> attribute.isFilterable())
				.map(attribute -> buildQueryFilterInputObjectField(attribute)).filter(Objects::nonNull)
				.collect(Collectors.toList());
	}

	private GraphQLInputObjectField buildQueryFilterInputObjectField(final GQLAbstractAttributeMetaData attribute) {
		logger.debug(Message.format("Build query filter input object field for attribute [{}]", attribute.getName()));
		GraphQLInputObjectField field = null;
		final String name = attribute.getName();
		final String description = "Filter [" + attribute.getName() + "]";
		// Set attribute type
		if (attribute instanceof GQLAttributeScalarMetaData) {
			field = buildInputField(name, description, getCache().getInputScalarFilterOperators()
					.get(((GQLAttributeScalarMetaData) attribute).getScalarType()));
		} else if (attribute instanceof GQLAttributeEnumMetaData) {
			field = buildInputField(name, description, getCache().getInputEnumFilterOperators()
					.get(((GQLAttributeEnumMetaData) attribute).getEnumClass()));
		} else if (attribute instanceof GQLAttributeEntityMetaData) {
			field = buildInputField(name + GQLSchemaConstants.ID_SUFFIX, "Filter [id] of [" + attribute.getName() + "]",
					Scalars.GraphQLID); // TODO add operator
		} else if (attribute instanceof GQLAttributeListEnumMetaData) {
			field = buildInputField(name, description,
					new GraphQLList(getCache().getEnumType(((GQLAttributeListEnumMetaData) attribute).getEnumClass()))); // TODO
																															// add
																															// operator
		} else if (attribute instanceof GQLAttributeListEntityMetaData) {
			// Not handled
		} else if (attribute instanceof GQLAttributeListScalarMetaData) {
			// Not handled
		} else if (attribute instanceof GQLAttributeEmbeddedEntityMetaData) {
			// Not handled
		} else if (attribute instanceof GQLAttributeListEmbeddedEntityMetaData) {
			// Not handled
		} else {
			throw new IllegalArgumentException(
					Message.format("Attribute could not be mapped to GraphQL [{}]", attribute));
		}
		return field;
	}

	private GraphQLObjectType buildListResultWrapperType(final GQLAbstractEntityMetaDataInfos infos,
			final boolean isInterface) {
		final GraphQLObjectType.Builder builder = GraphQLObjectType.newObject();
		builder.name(infos.getEntity().getName() + "LoadResult");
		builder.description("Result list wrapper for [" + infos.getEntity().getName()
				+ "]. This object will contain list load result in [" + GQLSchemaConstants.RESULT_DATA
				+ "] property and also metadata about the query. (paging, sorting...)");

		// Result content field
		final GraphQLFieldDefinition.Builder contentBuilder = GraphQLFieldDefinition.newFieldDefinition();
		contentBuilder.name(GQLSchemaConstants.RESULT_DATA);
		contentBuilder.description("The actual results list.");
		contentBuilder.type(new GraphQLList(isInterface
				? getCache().getInterfaceType(infos.getEntity().getEntityClass())
				: getCache().getEntityType(infos.getEntity().getEntityClass())));
		builder.field(contentBuilder.build());
		// OrderBy field
		final GraphQLFieldDefinition.Builder orderByBuilder = GraphQLFieldDefinition.newFieldDefinition();
		orderByBuilder.name(GQLSchemaConstants.ORDER_BY);
		orderByBuilder.description("Sort informations used to sort this query.");
		orderByBuilder.type(new GraphQLList(getCache().getOrderByOutputObjectType()));
		builder.field(orderByBuilder.build());
		// Paging field
		final GraphQLFieldDefinition.Builder pagingBuilder = GraphQLFieldDefinition.newFieldDefinition();
		pagingBuilder.name(GQLSchemaConstants.PAGING);
		pagingBuilder.description("Paging informations used for this query.");
		pagingBuilder.type(getCache().getPagingOutputObjectType());
		builder.field(pagingBuilder.build());

		return builder.build();
	}
}
