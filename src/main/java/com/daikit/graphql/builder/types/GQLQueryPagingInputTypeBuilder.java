package com.daikit.graphql.builder.types;

import org.apache.commons.lang3.StringUtils;

import com.daikit.graphql.builder.GQLSchemaBuilderCache;

import graphql.Scalars;
import graphql.schema.GraphQLInputObjectField;
import graphql.schema.GraphQLInputObjectType;

/**
 * GQL query paging output type builder
 *
 * @author Thibaut Caselli
 */
public class GQLQueryPagingInputTypeBuilder extends GQLAbstractTypesBuilder {

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// CONSTRUCTORS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	/**
	 * Constructor
	 *
	 * @param cache
	 *            the {@link GQLSchemaBuilderCache}
	 */
	public GQLQueryPagingInputTypeBuilder(final GQLSchemaBuilderCache cache) {
		super(cache);
	}

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// METHODS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	/**
	 * Build paging input type and cache it
	 */
	public void buildPagingInputType() {
		logger.debug("Build paging input type");
		getCache().setPagingInputObjectType(buildPagingQueryInputObjectType());
	}

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// PRIVATE UTILS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	// Paging input object type
	private GraphQLInputObjectType buildPagingQueryInputObjectType() {
		final GraphQLInputObjectType.Builder builder = GraphQLInputObjectType.newInputObject();
		builder.name(StringUtils.capitalize(getConfig().getQueryGetListPagingAttributeName())
				+ getConfig().getInputTypeNameSuffix());
		builder.description(
				"Query object for Pagination Requests, specifying the requested offset (0-indexed, default=0) and limit (default="
						+ getConfig().getQueryGetListPagingAttributeLimitDefaultValue() + "). Example: ("
						+ getConfig().getQueryGetListPagingAttributeName() + ": { "
						+ getConfig().getQueryGetListPagingAttributeOffsetName() + ": 0, "
						+ getConfig().getQueryGetListPagingAttributeLimitName() + ": "
						+ getConfig().getQueryGetListPagingAttributeLimitDefaultValue() + " })");

		final GraphQLInputObjectField.Builder offsetFieldBuilder = GraphQLInputObjectField.newInputObjectField();
		offsetFieldBuilder.name(getConfig().getQueryGetListPagingAttributeOffsetName());
		offsetFieldBuilder.description(
				"Offset of the first result to be returned, starting with 0 (0-indexed). This is equivalent to the page size multiplied by the index (0-indexed) of the page to be retrieved.");
		offsetFieldBuilder.type(Scalars.GraphQLInt);
		offsetFieldBuilder.defaultValue(0);
		builder.field(offsetFieldBuilder.build());

		final GraphQLInputObjectField.Builder liitFieldBuilder = GraphQLInputObjectField.newInputObjectField();
		liitFieldBuilder.name(getConfig().getQueryGetListPagingAttributeLimitName());
		liitFieldBuilder.description(
				"How many results should the resulting load result contain. This is equivalent to the page size. Default value is ["
						+ getConfig().getQueryGetListPagingAttributeLimitDefaultValue() + "]");
		liitFieldBuilder.type(Scalars.GraphQLInt);
		offsetFieldBuilder.defaultValue(getConfig().getQueryGetListPagingAttributeLimitDefaultValue());
		builder.field(liitFieldBuilder.build());

		return builder.build();
	}

}
