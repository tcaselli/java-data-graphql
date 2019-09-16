package com.daikit.graphql.builder.types;

import org.apache.commons.lang3.StringUtils;

import com.daikit.graphql.builder.GQLSchemaBuilderCache;

import graphql.Scalars;
import graphql.schema.GraphQLInputObjectField;
import graphql.schema.GraphQLInputObjectType;
import graphql.schema.GraphQLNonNull;

/**
 * GQL query order by input type builder
 *
 * @author Thibaut Caselli
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
		builder.name(StringUtils.capitalize(getConfig().getQueryGetListFilterAttributeOrderByName())
				+ getConfig().getInputTypeNameSuffix());
		builder.description("Sort configuration for returned list of objects.");

		final GraphQLInputObjectField.Builder attributeFieldBuilder = GraphQLInputObjectField.newInputObjectField();
		attributeFieldBuilder.name(getConfig().getQueryGetListFilterAttributeOrderByFieldName());
		attributeFieldBuilder.description("The field in which to order objects by.");
		attributeFieldBuilder.type(new GraphQLNonNull(Scalars.GraphQLString));
		builder.field(attributeFieldBuilder.build());

		final GraphQLInputObjectField.Builder directionFieldBuilder = GraphQLInputObjectField.newInputObjectField();
		directionFieldBuilder.name(getConfig().getQueryGetListFilterAttributeOrderByDirectionName());
		directionFieldBuilder.description("The direction in which to order objects by the specified field.");
		directionFieldBuilder.type(getCache().getOrderByDirectionEnumType());
		directionFieldBuilder.defaultValue(getConfig().getQueryGetListFilterAttributeOrderByDirectionDefaultValue());
		builder.field(directionFieldBuilder.build());

		return builder.build();
	}
}
