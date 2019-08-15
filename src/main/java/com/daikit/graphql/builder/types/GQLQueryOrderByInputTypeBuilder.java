package com.daikit.graphql.builder.types;

import org.apache.commons.lang3.StringUtils;

import com.daikit.graphql.builder.GQLSchemaBuilderCache;
import com.daikit.graphql.constants.GQLSchemaConstants;

import graphql.Scalars;
import graphql.schema.GraphQLInputObjectField;
import graphql.schema.GraphQLInputObjectType;
import graphql.schema.GraphQLNonNull;

/**
 * GQL query order by input type builder
 *
 * @author tcaselli
 */
public class GQLQueryOrderByInputTypeBuilder extends GQLAbstractTypesBuilder {

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// CONSTRUCTORS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	/**
	 * Constructor
	 *
	 * @param cache
	 *            the {@link GQLSchemaBuilderCache}
	 */
	public GQLQueryOrderByInputTypeBuilder(final GQLSchemaBuilderCache cache) {
		super(cache);
	}

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// METHODS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	/**
	 * Build orderBy input type and cache it
	 */
	public void buildOrderByInputType() {
		logger.debug("Build orderBy input type");
		getCache().setOrderByInputObjectType(buildOrderByInputObjectType());
	}

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// PRIVATE UTILS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	private GraphQLInputObjectType buildOrderByInputObjectType() {
		final GraphQLInputObjectType.Builder builder = GraphQLInputObjectType.newInputObject();
		builder.name(StringUtils.capitalize(GQLSchemaConstants.ORDER_BY) + GQLSchemaConstants.INPUT_OBJECT_SUFFIX);
		builder.description("Sort configuration for returned list of objects.");

		final GraphQLInputObjectField.Builder attributeFieldBuilder = GraphQLInputObjectField.newInputObjectField();
		attributeFieldBuilder.name(GQLSchemaConstants.ORDER_BY_FIELD);
		attributeFieldBuilder.description("The field in which to order objects by.");
		attributeFieldBuilder.type(new GraphQLNonNull(Scalars.GraphQLString));
		builder.field(attributeFieldBuilder.build());

		final GraphQLInputObjectField.Builder directionFieldBuilder = GraphQLInputObjectField.newInputObjectField();
		directionFieldBuilder.name(GQLSchemaConstants.ORDER_BY_DIRECTION);
		directionFieldBuilder.description("The direction in which to order objects by the specified field.");
		directionFieldBuilder.type(getCache().getOrderByDirectionEnumType());
		directionFieldBuilder.defaultValue(GQLSchemaConstants.ORDER_BY_DIRECTION_DEFAULT_VALUE);
		builder.field(directionFieldBuilder.build());

		return builder.build();
	}
}
