package com.daikit.graphql.builder.types;

import org.apache.commons.lang3.StringUtils;

import com.daikit.graphql.builder.GQLSchemaBuilderCache;

import graphql.Scalars;
import graphql.schema.GraphQLFieldDefinition;
import graphql.schema.GraphQLObjectType;

/**
 * GQL Query paging output type builder
 *
 * @author Thibaut Caselli
 */
public class GQLQueryPagingOutputTypeBuilder extends GQLAbstractTypesBuilder {

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// CONSTRUCTORS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	/**
	 * Constructor
	 *
	 * @param cache
	 *            the {@link GQLSchemaBuilderCache}
	 */
	public GQLQueryPagingOutputTypeBuilder(final GQLSchemaBuilderCache cache) {
		super(cache);
	}

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// METHODS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	/**
	 * Build paging output type and cache it
	 */
	public void buildPagingOutputType() {
		logger.debug("Build paging output type");
		getCache().setPagingOutputObjectType(buildPagingOutputObjectType());
	}

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// PRIVATE UTILS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	// Paging output type
	private GraphQLObjectType buildPagingOutputObjectType() {
		final GraphQLObjectType.Builder builder = GraphQLObjectType.newObject();
		builder.name(StringUtils.capitalize(getConfig().getQueryGetListPagingAttributeName())
				+ getConfig().getOutputTypeNameSuffix());
		builder.description("Paging informations. Only meaningful for paging requests.");

		// Paging limit field
		final GraphQLFieldDefinition.Builder limitBuilder = GraphQLFieldDefinition.newFieldDefinition();
		limitBuilder.name(getConfig().getQueryGetListPagingAttributeLimitName());
		limitBuilder.description(
				"How many results this query load contains at maximum. This is equal to the limit passed as parameter.");
		limitBuilder.type(Scalars.GraphQLInt);
		builder.field(limitBuilder.build());
		// Paging offset field
		final GraphQLFieldDefinition.Builder offsetBuilder = GraphQLFieldDefinition.newFieldDefinition();
		offsetBuilder.name(getConfig().getQueryGetListPagingAttributeOffsetName());
		offsetBuilder.description(
				"Offset of the results for this query. This is equal to the offset passed as parameters. This is equal to the offset passed as parameter.");
		offsetBuilder.type(Scalars.GraphQLInt);
		builder.field(offsetBuilder.build());
		// Paging total length field
		final GraphQLFieldDefinition.Builder totalLengthBuilder = GraphQLFieldDefinition.newFieldDefinition();
		totalLengthBuilder.name(getConfig().getQueryGetListPagingAttributeTotalLengthName());
		totalLengthBuilder.description("Total number of results on the database for this query.");
		totalLengthBuilder.type(Scalars.GraphQLLong);
		builder.field(totalLengthBuilder.build());

		return builder.build();
	}

}
