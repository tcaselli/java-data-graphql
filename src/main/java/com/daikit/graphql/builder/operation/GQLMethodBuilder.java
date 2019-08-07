package com.daikit.graphql.builder.operation;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.daikit.graphql.builder.GQLAbstractSchemaSubBuilder;
import com.daikit.graphql.builder.GQLSchemaBuilderCache;
import com.daikit.graphql.datafetcher.GQLMethodDataFetcher;
import com.daikit.graphql.meta.data.method.GQLAbstractMethodMetaData;
import com.daikit.graphql.meta.data.method.GQLMethodEmbeddedEntityMetaData;
import com.daikit.graphql.meta.data.method.GQLMethodEntityMetaData;
import com.daikit.graphql.meta.data.method.GQLMethodEnumMetaData;
import com.daikit.graphql.meta.data.method.GQLMethodListEmbeddedEntityMetaData;
import com.daikit.graphql.meta.data.method.GQLMethodListEntityMetaData;
import com.daikit.graphql.meta.data.method.GQLMethodListEnumMetaData;
import com.daikit.graphql.meta.data.method.GQLMethodListScalarMetaData;
import com.daikit.graphql.meta.data.method.GQLMethodScalarMetaData;
import com.daikit.graphql.meta.data.method.argument.GQLAbstractMethodArgumentMetaData;
import com.daikit.graphql.meta.data.method.argument.GQLMethodArgumentEmbeddedEntityMetaData;
import com.daikit.graphql.meta.data.method.argument.GQLMethodArgumentEntityMetaData;
import com.daikit.graphql.meta.data.method.argument.GQLMethodArgumentEnumMetaData;
import com.daikit.graphql.meta.data.method.argument.GQLMethodArgumentListEmbeddedEntityMetaData;
import com.daikit.graphql.meta.data.method.argument.GQLMethodArgumentListEntityMetaData;
import com.daikit.graphql.meta.data.method.argument.GQLMethodArgumentListEnumMetaData;
import com.daikit.graphql.meta.data.method.argument.GQLMethodArgumentListScalarMetaData;
import com.daikit.graphql.meta.data.method.argument.GQLMethodArgumentScalarMetaData;
import com.daikit.graphql.utils.Message;

import graphql.schema.GraphQLArgument;
import graphql.schema.GraphQLFieldDefinition;
import graphql.schema.GraphQLInputType;
import graphql.schema.GraphQLList;
import graphql.schema.GraphQLOutputType;

/**
 * Query methods builder
 *
 * @author tcaselli
 */
public class GQLMethodBuilder extends GQLAbstractSchemaSubBuilder {

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// CONSTRUCTORS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	/**
	 * Constructor
	 *
	 * @param cache
	 *            the {@link GQLSchemaBuilderCache}
	 */
	public GQLMethodBuilder(final GQLSchemaBuilderCache cache) {
		super(cache);
	}

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// METHODS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	/**
	 * Build method query fields for given {@link GQLAbstractMethodMetaData}
	 * list
	 *
	 * @param methods
	 *            the list of {@link GQLAbstractMethodMetaData} to build query
	 *            fields from
	 * @param dynamicMethodDataFetcherCreator
	 *            the {@link GQLMethodDataFetcher} creator for dynamic methods
	 * @return a {@link Map} of (key={@linkGQLAbstractMethodMetaData }, value=
	 *         {@link GraphQLFieldDefinition}]
	 */
	public Map<GQLAbstractMethodMetaData, GraphQLFieldDefinition> buildMethods(
			final List<GQLAbstractMethodMetaData> methods) {
		return methods.stream().collect(LinkedHashMap::new, (map, method) -> map.put(method, buildMethod(method)),
				Map::putAll);
	}

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// PRIVATE UTILS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	private GraphQLFieldDefinition buildMethod(final GQLAbstractMethodMetaData method) {
		logger.debug(Message.format("Build query custom method [{}]", method.getName()));
		final GraphQLFieldDefinition.Builder builder = GraphQLFieldDefinition.newFieldDefinition();
		builder.name(method.getName());
		builder.description("Query custom method [" + method.getName() + "]");
		method.getArguments().forEach(argument -> builder.argument(buildQueryArgument(argument)));
		builder.type(getOutputType(method));
		return builder.build();
	}

	private GraphQLOutputType getOutputType(final GQLAbstractMethodMetaData dynamicMethod) {
		GraphQLOutputType outputType;
		if (dynamicMethod instanceof GQLMethodScalarMetaData) {
			outputType = getCache().getScalarType(((GQLMethodScalarMetaData) dynamicMethod).getScalarType());
		} else if (dynamicMethod instanceof GQLMethodEnumMetaData) {
			outputType = getCache().getEnumType(((GQLMethodEnumMetaData) dynamicMethod).getEnumClass());
		} else if (dynamicMethod instanceof GQLMethodEntityMetaData) {
			outputType = getCache().getEntityType(((GQLMethodEntityMetaData) dynamicMethod).getEntityClass());
		} else if (dynamicMethod instanceof GQLMethodListEnumMetaData) {
			outputType = new GraphQLList(
					getCache().getEnumType(((GQLMethodListEnumMetaData) dynamicMethod).getEnumClass()));
		} else if (dynamicMethod instanceof GQLMethodListEntityMetaData) {
			outputType = new GraphQLList(
					getCache().getEntityType(((GQLMethodListEntityMetaData) dynamicMethod).getForeignClass()));
		} else if (dynamicMethod instanceof GQLMethodListScalarMetaData) {
			outputType = new GraphQLList(
					getCache().getScalarType(((GQLMethodScalarMetaData) dynamicMethod).getScalarType()));
		} else if (dynamicMethod instanceof GQLMethodEmbeddedEntityMetaData) {
			outputType = getCache().getEntityType(((GQLMethodEmbeddedEntityMetaData) dynamicMethod).getEntityClass());
		} else if (dynamicMethod instanceof GQLMethodListEmbeddedEntityMetaData) {
			outputType = new GraphQLList(
					getCache().getEntityType(((GQLMethodListEmbeddedEntityMetaData) dynamicMethod).getForeignClass()));
		} else {
			throw new IllegalArgumentException(
					Message.format("Dynamic method output type not handled for [{}]", dynamicMethod));
		}
		return outputType;
	}

	private GraphQLArgument buildQueryArgument(final GQLAbstractMethodArgumentMetaData argumentMetaData) {
		logger.debug(Message.format("Build query dynamic method argument [{}]", argumentMetaData.getName()));
		final GraphQLArgument.Builder builder = GraphQLArgument.newArgument();
		builder.name(argumentMetaData.getName());
		builder.description("Query dynamic method argument [" + argumentMetaData.getName() + "]");
		builder.type(getArgumentType(argumentMetaData));
		final GraphQLArgument ret = builder.build();
		return ret;
	}

	private GraphQLInputType getArgumentType(final GQLAbstractMethodArgumentMetaData argumentMetaData) {
		GraphQLInputType argumentType;
		if (argumentMetaData instanceof GQLMethodArgumentScalarMetaData) {
			argumentType = getCache()
					.getScalarType(((GQLMethodArgumentScalarMetaData) argumentMetaData).getScalarType());
		} else if (argumentMetaData instanceof GQLMethodArgumentEnumMetaData) {
			argumentType = getCache().getEnumType(((GQLMethodArgumentEnumMetaData) argumentMetaData).getEnumClass());
		} else if (argumentMetaData instanceof GQLMethodArgumentEntityMetaData) {
			argumentType = getCache()
					.getInputEntityType(((GQLMethodArgumentEntityMetaData) argumentMetaData).getEntityClass());
		} else if (argumentMetaData instanceof GQLMethodArgumentListEnumMetaData) {
			argumentType = new GraphQLList(
					getCache().getEnumType(((GQLMethodArgumentListEnumMetaData) argumentMetaData).getEnumClass()));
		} else if (argumentMetaData instanceof GQLMethodArgumentListEntityMetaData) {
			argumentType = new GraphQLList(getCache()
					.getEntityType(((GQLMethodArgumentListEntityMetaData) argumentMetaData).getForeignClass()));
		} else if (argumentMetaData instanceof GQLMethodArgumentListScalarMetaData) {
			argumentType = new GraphQLList(
					getCache().getScalarType(((GQLMethodArgumentListScalarMetaData) argumentMetaData).getScalarType()));
		} else if (argumentMetaData instanceof GQLMethodArgumentEmbeddedEntityMetaData) {
			argumentType = getCache()
					.getInputEntityType(((GQLMethodArgumentEmbeddedEntityMetaData) argumentMetaData).getEntityClass());
		} else if (argumentMetaData instanceof GQLMethodArgumentListEmbeddedEntityMetaData) {
			argumentType = new GraphQLList(getCache()
					.getEntityType(((GQLMethodArgumentListEmbeddedEntityMetaData) argumentMetaData).getForeignClass()));
		} else {
			throw new IllegalArgumentException(
					Message.format("Dynamic method argument type not handled for [{}]", argumentMetaData));
		}
		return argumentType;
	}

}
