package com.daikit.graphql.builder.custommethod;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import com.daikit.graphql.builder.GQLAbstractSchemaSubBuilder;
import com.daikit.graphql.builder.GQLSchemaBuilderCache;
import com.daikit.graphql.meta.custommethod.GQLAbstractMethodArgumentMetaData;
import com.daikit.graphql.meta.custommethod.GQLAbstractMethodMetaData;
import com.daikit.graphql.meta.custommethod.GQLMethodArgumentEntityMetaData;
import com.daikit.graphql.meta.custommethod.GQLMethodArgumentEnumMetaData;
import com.daikit.graphql.meta.custommethod.GQLMethodArgumentListEntityMetaData;
import com.daikit.graphql.meta.custommethod.GQLMethodArgumentListEnumMetaData;
import com.daikit.graphql.meta.custommethod.GQLMethodArgumentListScalarMetaData;
import com.daikit.graphql.meta.custommethod.GQLMethodArgumentScalarMetaData;
import com.daikit.graphql.meta.custommethod.GQLMethodEntityMetaData;
import com.daikit.graphql.meta.custommethod.GQLMethodEnumMetaData;
import com.daikit.graphql.meta.custommethod.GQLMethodListEntityMetaData;
import com.daikit.graphql.meta.custommethod.GQLMethodListEnumMetaData;
import com.daikit.graphql.meta.custommethod.GQLMethodListScalarMetaData;
import com.daikit.graphql.meta.custommethod.GQLMethodScalarMetaData;
import com.daikit.graphql.utils.Message;

import graphql.schema.GraphQLArgument;
import graphql.schema.GraphQLFieldDefinition;
import graphql.schema.GraphQLInputType;
import graphql.schema.GraphQLList;
import graphql.schema.GraphQLOutputType;

/**
 * Query methods builder
 *
 * @author Thibaut Caselli
 */
public class GQLCustomMethodBuilder extends GQLAbstractSchemaSubBuilder {

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// CONSTRUCTORS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	/**
	 * Constructor
	 *
	 * @param cache
	 *            the {@link GQLSchemaBuilderCache}
	 */
	public GQLCustomMethodBuilder(final GQLSchemaBuilderCache cache) {
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
	 * @return a {@link Map} of (key={@link GQLAbstractMethodMetaData}, value=
	 *         {@link GraphQLFieldDefinition}]
	 */
	public Map<GQLAbstractMethodMetaData, GraphQLFieldDefinition> buildMethods(
			final List<GQLAbstractMethodMetaData> methods) {
		checkDuplicates(methods);
		return methods.stream().collect(LinkedHashMap::new, (map, method) -> map.put(method, buildMethod(method)),
				Map::putAll);
	}

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// PRIVATE UTILS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	private void checkDuplicates(final List<GQLAbstractMethodMetaData> methods) {
		final Set<String> names = new HashSet<>();
		methods.stream().forEach(method -> {
			if (!names.add(method.getName())) {
				throw new IllegalArgumentException(
						Message.format("Multiple methods set with name [{}]", method.getName()));
			}
		});
	}

	private GraphQLFieldDefinition buildMethod(final GQLAbstractMethodMetaData method) {
		logger.debug(Message.format("Build query custom method [{}]", method.getName()));
		final GraphQLFieldDefinition.Builder builder = GraphQLFieldDefinition.newFieldDefinition();
		builder.name(method.getName());
		builder.description(StringUtils.isEmpty(method.getDescription())
				? "Custom " + (method.getMethod().isMutation() ? "mutation" : "query") + " method [" + method.getName()
						+ "]"
				: method.getDescription());
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
					.getInputEntityType(((GQLMethodArgumentListEntityMetaData) argumentMetaData).getForeignClass()));
		} else if (argumentMetaData instanceof GQLMethodArgumentListScalarMetaData) {
			argumentType = new GraphQLList(
					getCache().getScalarType(((GQLMethodArgumentListScalarMetaData) argumentMetaData).getScalarType()));
		} else {
			throw new IllegalArgumentException(
					Message.format("Dynamic method argument type not handled for [{}]", argumentMetaData));
		}
		return argumentType;
	}

}
