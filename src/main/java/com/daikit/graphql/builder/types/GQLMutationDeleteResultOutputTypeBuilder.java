package com.daikit.graphql.builder.types;

import com.daikit.graphql.builder.GQLSchemaBuilderCache;

import graphql.Scalars;
import graphql.schema.GraphQLFieldDefinition;
import graphql.schema.GraphQLObjectType;

/**
 * GQL delete entity mutation output type
 *
 * @author Thibaut Caselli
 */
public class GQLMutationDeleteResultOutputTypeBuilder extends GQLAbstractTypesBuilder {

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// CONSTRUCTORS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	/**
	 * Constructor
	 *
	 * @param cache
	 *            the {@link GQLSchemaBuilderCache}
	 */
	public GQLMutationDeleteResultOutputTypeBuilder(final GQLSchemaBuilderCache cache) {
		super(cache);
	}

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// METHODS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	/**
	 * Build delete result output type and cache it
	 */
	public void buildDeleteResultOutputType() {
		logger.debug("Build deleteResult output type");
		getCache().setDeleteResultOutputObjectType(buildDeleteResultOutputObjectType());
	}

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// PRIVATE UTILS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	private GraphQLObjectType buildDeleteResultOutputObjectType() {
		final GraphQLObjectType.Builder builder = GraphQLObjectType.newObject();
		builder.name(getConfig().getOutputDeleteResultTypeNamePrefix() + getConfig().getOutputTypeNameSuffix());
		builder.description("Output type for deletion mutation for all entities.");

		final GraphQLFieldDefinition.Builder idFieldBuilder = GraphQLFieldDefinition.newFieldDefinition();
		idFieldBuilder.name(getConfig().getMutationDeleteResultAttributeId());
		idFieldBuilder.description("The id of the deleted entity.");
		idFieldBuilder.type(Scalars.GraphQLString);
		builder.field(idFieldBuilder.build());

		final GraphQLFieldDefinition.Builder typenameFieldBuilder = GraphQLFieldDefinition.newFieldDefinition();
		typenameFieldBuilder.name(getConfig().getMutationDeleteResultAttributeTypename());
		typenameFieldBuilder.description("The type name of the deleted entity.");
		typenameFieldBuilder.type(Scalars.GraphQLString);
		builder.field(typenameFieldBuilder.build());

		return builder.build();
	}

}
