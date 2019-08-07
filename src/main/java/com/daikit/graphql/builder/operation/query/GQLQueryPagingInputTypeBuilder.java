package com.daikit.graphql.builder.operation.query;

import org.apache.commons.lang3.StringUtils;

import com.daikit.graphql.builder.GQLSchemaBuilderCache;
import com.daikit.graphql.builder.types.GQLAbstractTypesBuilder;
import com.daikit.graphql.constants.GQLSchemaConstants;

import graphql.Scalars;
import graphql.schema.GraphQLInputObjectField;
import graphql.schema.GraphQLInputObjectType;

/**
 * GQL query paging output type builder
 *
 * @author tcaselli
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
		builder.name(StringUtils.capitalize(GQLSchemaConstants.PAGING) + GQLSchemaConstants.INPUT_OBJECT_SUFFIX);
		builder.description(
				"Query object for Pagination Requests, specifying the requested offset (0-indexed, default=0) and limit (default="
						+ GQLSchemaConstants.PAGING_LIMIT_DEFAULT_VALUE + "). Example: (" + GQLSchemaConstants.PAGING
						+ ": { " + GQLSchemaConstants.PAGING_OFFSET + ": 0, " + GQLSchemaConstants.PAGING_LIMIT + ": "
						+ GQLSchemaConstants.PAGING_LIMIT_DEFAULT_VALUE + " })");

		final GraphQLInputObjectField.Builder offsetFieldBuilder = GraphQLInputObjectField.newInputObjectField();
		offsetFieldBuilder.name(GQLSchemaConstants.PAGING_OFFSET);
		offsetFieldBuilder.description(
				"Offset of the first result to be returned, starting with 0 (0-indexed). This is equivalent to the page size multiplied by the index (0-indexed) of the page to be retrieved.");
		offsetFieldBuilder.type(Scalars.GraphQLInt);
		offsetFieldBuilder.defaultValue(0);
		builder.field(offsetFieldBuilder.build());

		final GraphQLInputObjectField.Builder liitFieldBuilder = GraphQLInputObjectField.newInputObjectField();
		liitFieldBuilder.name(GQLSchemaConstants.PAGING_LIMIT);
		liitFieldBuilder.description(
				"How many results should the resulting load result contain. This is equivalent to the page size. Default value is ["
						+ GQLSchemaConstants.PAGING_LIMIT_DEFAULT_VALUE + "]");
		liitFieldBuilder.type(Scalars.GraphQLInt);
		offsetFieldBuilder.defaultValue(GQLSchemaConstants.PAGING_LIMIT_DEFAULT_VALUE);
		builder.field(liitFieldBuilder.build());

		return builder.build();
	}

}
