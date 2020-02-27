package com.daikit.graphql.builder.types;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import com.daikit.graphql.builder.GQLExecutionContext;
import com.daikit.graphql.builder.GQLSchemaBuilderCache;
import com.daikit.graphql.builder.GQLSchemaBuilderUtils;
import com.daikit.graphql.builder.custommethod.GQLCustomMethodBuilder;
import com.daikit.graphql.data.output.GQLListLoadResult;
import com.daikit.graphql.meta.GQLInternalMetaModel;
import com.daikit.graphql.meta.attribute.GQLAbstractAttributeMetaData;
import com.daikit.graphql.meta.attribute.GQLAttributeEntityMetaData;
import com.daikit.graphql.meta.attribute.GQLAttributeEnumMetaData;
import com.daikit.graphql.meta.attribute.GQLAttributeListEntityMetaData;
import com.daikit.graphql.meta.attribute.GQLAttributeListEnumMetaData;
import com.daikit.graphql.meta.attribute.GQLAttributeListScalarMetaData;
import com.daikit.graphql.meta.attribute.GQLAttributeScalarMetaData;
import com.daikit.graphql.meta.custommethod.GQLAbstractMethodMetaData;
import com.daikit.graphql.meta.entity.GQLEntityMetaData;
import com.daikit.graphql.meta.internal.GQLAbstractEntityMetaDataInfos;
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
 * @author Thibaut Caselli
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
	 * @param executionContext
	 *            the {@link GQLExecutionContext}
	 * @param metaModel
	 *            the {@link GQLInternalMetaModel}
	 * @param getByIdDataFetcher
	 *            the {@link DataFetcher} for getById methods
	 * @param listDataFetcher
	 *            the {@link DataFetcher} for getAll methods
	 * @param customMethodsDataFetcher
	 *            the {@link DataFetcher} for custom methods
	 * @return the created {@link GraphQLObjectType}
	 */
	public GraphQLObjectType buildQueryType(GQLExecutionContext executionContext, final GQLInternalMetaModel metaModel,
			final DataFetcher<?> getByIdDataFetcher, final DataFetcher<GQLListLoadResult> listDataFetcher,
			final DataFetcher<?> customMethodsDataFetcher) {
		logger.debug("START building query types...");

		final GraphQLObjectType.Builder builder = GraphQLObjectType.newObject();
		builder.name(getConfig().getQueryTypeName());
		builder.description("Query type from meta model");

		final List<GraphQLFieldDefinition> getByIdFieldDefinitions = new ArrayList<>();
		final List<GraphQLFieldDefinition> getAllFieldDefinitions = new ArrayList<>();

		logger.debug("Build query types for interfaces...");
		metaModel.getNonEmbeddedInterfaces().stream().filter(infos -> infos.getEntity().isReadable(executionContext))
				.forEach(infos -> {
					getByIdFieldDefinitions.add(buildGetSingleQueryFieldDefinitions(infos, true));
					getAllFieldDefinitions.add(buildGetAllQueryFieldDefinitions(infos, true));
				});

		logger.debug("Build query types for entities...");
		metaModel.getNonEmbeddedConcretes().stream().filter(infos -> infos.getEntity().isReadable(executionContext))
				.forEach(infos -> {
					getByIdFieldDefinitions.add(buildGetSingleQueryFieldDefinitions(infos, false));
					getAllFieldDefinitions.add(buildGetAllQueryFieldDefinitions(infos, false));
				});

		builder.fields(getByIdFieldDefinitions);
		builder.fields(getAllFieldDefinitions);

		logger.debug("Build query types for custom methods...");
		final Map<GQLAbstractMethodMetaData, GraphQLFieldDefinition> customMethodFieldDefinitions = new GQLCustomMethodBuilder(
				getCache())
						.buildMethods(metaModel.getCustomMethods().stream()
								.filter(customMethod -> !customMethod.isMutation()).collect(Collectors.toList()));
		builder.fields(new ArrayList<>(customMethodFieldDefinitions.values()));

		final GraphQLObjectType queryType = builder.build();

		if (!getByIdFieldDefinitions.isEmpty()) {
			Assert.assertNotNull(getByIdDataFetcher,
					"If there is at least one entity with an ID property defined then 'getByIdDataFetcher' must be non null");
			getByIdFieldDefinitions.forEach(fieldDefinition -> getCache().getCodeRegistryBuilder()
					.dataFetcher(queryType, fieldDefinition, getByIdDataFetcher));
		}

		if (!getAllFieldDefinitions.isEmpty()) {
			Assert.assertNotNull(listDataFetcher,
					"If there is at least one entity defined then 'listDataFetcher' must be non null");
			getAllFieldDefinitions.forEach(fieldDefinition -> getCache().getCodeRegistryBuilder().dataFetcher(queryType,
					fieldDefinition, listDataFetcher));
		}

		customMethodFieldDefinitions.entrySet().forEach(entry -> getCache().getCodeRegistryBuilder()
				.dataFetcher(queryType, entry.getValue(), customMethodsDataFetcher));

		logger.debug("END building query types");
		return queryType;
	}

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// PRIVATE UTILS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	private GraphQLFieldDefinition buildGetSingleQueryFieldDefinitions(final GQLAbstractEntityMetaDataInfos infos,
			final boolean isInterface) {
		logger.debug(
				Message.format("Build 'getById' query type for " + (isInterface ? "interface" : "entity") + " [{}]",
						infos.getEntity().getName()));
		// Query for single entity
		final GraphQLFieldDefinition.Builder builder = GraphQLFieldDefinition.newFieldDefinition();
		builder.name(getConfig().getQueryGetByIdPrefix() + infos.getEntity().getName());
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
		builder.name(getConfig().getQueryGetListPrefix() + infos.getEntity().getName());
		builder.description(
				(isInterface ? "Interface" : "Entity") + " listing query for [" + infos.getEntity().getName()
						+ "]. This method accepts several filtering arguments and returns a list load result. Give a ["
						+ getConfig().getQueryGetListPagingAttributeName() + "] argument if you need paging.");
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
		builder.name(getConfig().getQueryGetListPagingAttributeName());
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
		builder.name(getConfig().getQueryGetListFilterAttributeOrderByName());
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
		builder.name(infos.getEntity().getName() + getConfig().getQueryGetListFilterEntityTypeNameSuffix());
		builder.description("Query filter fields object type for entity [" + infos.getEntity().getName() + "]");
		final List<GraphQLInputObjectField> objectFields = new ArrayList<>();
		// Add fields
		GQLSchemaBuilderUtils.addOrReplaceInputObjectFields(objectFields,
				buildQueryFilterInputObjectFields(infos.getEntity()));
		builder.fields(objectFields);
		return builder.build();
	}

	private List<GraphQLInputObjectField> buildQueryFilterInputObjectFields(final GQLEntityMetaData entity) {
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
			final GraphQLInputObjectType scalarType = getCache().getInputScalarFilterOperators()
					.get(((GQLAttributeScalarMetaData) attribute).getScalarType());
			if (scalarType == null) {
				// Not handled
			} else {
				field = buildInputField(name, description, scalarType);
			}
		} else if (attribute instanceof GQLAttributeEnumMetaData) {
			field = buildInputField(name, description, getCache().getInputEnumFilterOperators()
					.get(((GQLAttributeEnumMetaData) attribute).getEnumClass()));
		} else if (attribute instanceof GQLAttributeEntityMetaData) {
			if (((GQLAttributeEntityMetaData) attribute).isEmbedded()) {
				// Not handled
			} else {
				field = buildInputField(name + getConfig().getAttributeIdSuffix(),
						"Filter [id] of [" + attribute.getName() + "]", Scalars.GraphQLID);
				// TODO add operator
			}
		} else if (attribute instanceof GQLAttributeListEnumMetaData) {
			field = buildInputField(name, description,
					new GraphQLList(getCache().getEnumType(((GQLAttributeListEnumMetaData) attribute).getEnumClass())));
			// TODO add operator
		} else if (attribute instanceof GQLAttributeListEntityMetaData) {
			// Not handled
		} else if (attribute instanceof GQLAttributeListScalarMetaData) {
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
		builder.name(infos.getEntity().getName() + getConfig().getQueryGetListOutputTypeNameSuffix());
		builder.description("Result list wrapper for [" + infos.getEntity().getName()
				+ "]. This object will contain list load result in ["
				+ getConfig().getQueryGetListAttributeOutputDataName()
				+ "] property and also metadata about the query. (paging, sorting...)");

		// Result content field
		final GraphQLFieldDefinition.Builder contentBuilder = GraphQLFieldDefinition.newFieldDefinition();
		contentBuilder.name(getConfig().getQueryGetListAttributeOutputDataName());
		contentBuilder.description("The actual results list.");
		contentBuilder.type(new GraphQLList(isInterface
				? getCache().getInterfaceType(infos.getEntity().getEntityClass())
				: getCache().getEntityType(infos.getEntity().getEntityClass())));
		builder.field(contentBuilder.build());
		// OrderBy field
		final GraphQLFieldDefinition.Builder orderByBuilder = GraphQLFieldDefinition.newFieldDefinition();
		orderByBuilder.name(getConfig().getQueryGetListFilterAttributeOrderByName());
		orderByBuilder.description("Sort informations used to sort this query.");
		orderByBuilder.type(new GraphQLList(getCache().getOrderByOutputObjectType()));
		builder.field(orderByBuilder.build());
		// Paging field
		final GraphQLFieldDefinition.Builder pagingBuilder = GraphQLFieldDefinition.newFieldDefinition();
		pagingBuilder.name(getConfig().getQueryGetListPagingAttributeName());
		pagingBuilder.description("Paging informations used for this query.");
		pagingBuilder.type(getCache().getPagingOutputObjectType());
		builder.field(pagingBuilder.build());

		return builder.build();
	}
}
