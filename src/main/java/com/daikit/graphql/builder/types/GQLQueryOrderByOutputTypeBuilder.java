package com.daikit.graphql.builder.types;

import org.apache.commons.lang3.StringUtils;

import com.daikit.graphql.builder.GQLSchemaBuilderCache;

import graphql.Scalars;
import graphql.schema.GraphQLFieldDefinition;
import graphql.schema.GraphQLObjectType;

/**
 * GQL query order by output type builder
 *
 * @author Thibaut Caselli
 */
public class GQLQueryOrderByOutputTypeBuilder extends GQLAbstractTypesBuilder {

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// CONSTRUCTORS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	/**
	 * Constructor
	 *
	 * @param cache
	 *            the {@link GQLSchemaBuilderCache}
	 */
	public GQLQueryOrderByOutputTypeBuilder(final GQLSchemaBuilderCache cache) {
		super(cache);
	}

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// METHODS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	/**
	 * Build orderBy output type and cache it
	 */
	public void buildOrderByOutputType() {
		logger.debug("Build orderBy output type");
		getCache().setOrderByOutputObjectType(buildOrderByOutputObjectType());
	}

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// PRIVATE UTILS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	private GraphQLObjectType buildOrderByOutputObjectType() {
		final GraphQLObjectType.Builder builder = GraphQLObjectType.newObject();
		builder.name(StringUtils.capitalize(getConfig().getQueryGetListFilterAttributeOrderByName())
				+ getConfig().getOutputTypeNameSuffix());
		builder.description("Sort configuration for returned list of objects.");

		final GraphQLFieldDefinition.Builder attributeFieldBuilder = GraphQLFieldDefinition.newFieldDefinition();
		attributeFieldBuilder.name(getConfig().getQueryGetListFilterAttributeOrderByFieldName());
		attributeFieldBuilder.description("The field in which objects have been ordered.");
		attributeFieldBuilder.type(Scalars.GraphQLString);
		builder.field(attributeFieldBuilder.build());

		final GraphQLFieldDefinition.Builder directionFieldBuilder = GraphQLFieldDefinition.newFieldDefinition();
		directionFieldBuilder.name(getConfig().getQueryGetListFilterAttributeOrderByDirectionName());
		directionFieldBuilder.description("The direction in which objects have been ordered.");
		directionFieldBuilder.type(getCache().getOrderByDirectionEnumType());
		builder.field(directionFieldBuilder.build());

		return builder.build();
	}

}
