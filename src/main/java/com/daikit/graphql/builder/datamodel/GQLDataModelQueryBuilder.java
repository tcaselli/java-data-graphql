package com.daikit.graphql.builder.datamodel;

import com.daikit.graphql.builder.GQLAbstractSchemaSubBuilder;
import com.daikit.graphql.builder.GQLSchemaBuilderCache;
import com.daikit.graphql.builder.GQLSchemaBuilderUtils;
import com.daikit.graphql.meta.GQLInternalMetaModel;
import com.daikit.graphql.utils.Message;

import graphql.schema.GraphQLFieldDefinition;
import graphql.schema.GraphQLObjectType;
import graphql.schema.GraphQLOutputType;

/**
 * Data model query method builder
 *
 * @author Thibaut Caselli
 */
public class GQLDataModelQueryBuilder extends GQLAbstractSchemaSubBuilder {

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// CONSTRUCTORS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	/**
	 * Constructor
	 *
	 * @param cache
	 *            the {@link GQLSchemaBuilderCache}
	 */
	public GQLDataModelQueryBuilder(final GQLSchemaBuilderCache cache) {
		super(cache);
	}

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// METHODS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	/**
	 * Build method query field for given {@link GQLInternalMetaModel}
	 *
	 * @param internalMetaModel
	 *            the {@link GQLInternalMetaModel}
	 *
	 * @return the created {@link GraphQLFieldDefinition}]
	 */
	public GraphQLFieldDefinition buildDataMetaModel(final GQLInternalMetaModel internalMetaModel) {
		logger.debug("Build query data model method");
		final GraphQLFieldDefinition.Builder builder = GraphQLFieldDefinition.newFieldDefinition();
		builder.name(getCache().getConfig().getDataModelQueryName());
		builder.description("Query for retrieving the data model the graphQL schema relates to.");
		builder.type(buildOutputType(internalMetaModel));
		return builder.build();
	}

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// PRIVATE UTILS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	private GraphQLOutputType buildOutputType(final GQLInternalMetaModel internalMetaModel) {
		final GraphQLOutputType outputType;
		final GraphQLObjectType.Builder builder = GraphQLObjectType.newObject();
		builder.name("DataModel");
		return outputType;
	}

	private GraphQLFieldDefinition buildFieldDefinition(final String name, final String description,
			final GraphQLOutputType type) {
		final GraphQLFieldDefinition.Builder builder = GraphQLFieldDefinition.newFieldDefinition();
		builder.name(name);
		builder.description(description);
		builder.type(type);
		logger.debug(Message.format("Field definition created for [{}] with type [{}]", name,
				GQLSchemaBuilderUtils.typeToString(type)));
		return builder.build();
	}
}
