package com.daikit.graphql.builder.types;

import java.util.stream.Stream;

import com.daikit.graphql.builder.GQLAbstractSchemaSubBuilder;
import com.daikit.graphql.builder.GQLSchemaBuilderCache;
import com.daikit.graphql.builder.GQLSchemaBuilderUtils;
import com.daikit.graphql.meta.GQLInternalMetaModel;
import com.daikit.graphql.utils.Message;

import graphql.Scalars;
import graphql.schema.GraphQLEnumType;
import graphql.schema.GraphQLFieldDefinition;
import graphql.schema.GraphQLList;
import graphql.schema.GraphQLObjectType;
import graphql.schema.GraphQLOutputType;

/**
 * Data model query method builder
 *
 * @author Thibaut Caselli
 */
public class GQLQueryDataModelTypeBuilder extends GQLAbstractSchemaSubBuilder {

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// CONSTRUCTORS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	/**
	 * Constructor
	 *
	 * @param cache
	 *            the {@link GQLSchemaBuilderCache}
	 */
	public GQLQueryDataModelTypeBuilder(final GQLSchemaBuilderCache cache) {
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
	public GraphQLFieldDefinition buildDataMetaModel() {
		logger.debug("Build query data model method");
		final GraphQLFieldDefinition.Builder builder = GraphQLFieldDefinition.newFieldDefinition();
		builder.name(getCache().getConfig().getDataModelQueryName());
		builder.description("Query for retrieving the data model the graphQL schema relates to.");
		builder.type(buildOutputType());
		return builder.build();
	}

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// PRIVATE UTILS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	private GraphQLOutputType buildOutputType() {
		final GraphQLObjectType.Builder builder = GraphQLObjectType.newObject();
		builder.name("DataModel");
		builder.description("Data model query output object type.");

		// Enums
		builder.field(buildEnumsFieldDefinition());
		// Enum - model
		// Enum - values

		// Entities
		builder.field(buildEntitiesFieldDefinition());
		// Entity - model

		// Entity - actions
		// Entity - actions - read
		// Entity - actions - create
		// Entity - actions - update
		// Entity - actions - delete

		// Entity - attributes
		// Entity - attribute - actions
		// Entity - attribute - actions - read
		// Entity - attribute - actions - create
		// Entity - attribute - actions - update
		// Entity - attribute - actions - delete
	}

	private GraphQLFieldDefinition buildEnumsFieldDefinition() {
		return buildFieldDefinition("enums", "Data model enumerations", new GraphQLList(buildEnumObjectType()));
	}

	private GraphQLFieldDefinition buildEntitiesFieldDefinition() {
		return buildFieldDefinition("entities", "Data model entities", new GraphQLList(buildEntityObjectType()));
	}

	private GraphQLObjectType buildEnumObjectType() {
		final GraphQLObjectType.Builder builder = GraphQLObjectType.newObject();
		builder.name("Enum");
		builder.description("Data model enumeration");
		builder.field(buildFieldDefinition("name", "Enumeration name", Scalars.GraphQLString));
		builder.field(buildFieldDefinition("values", "Enumeration values", new GraphQLList(Scalars.GraphQLString)));
		return builder.build();
	}

	private GraphQLObjectType buildEntityObjectType() {
		final GraphQLObjectType.Builder builder = GraphQLObjectType.newObject();
		builder.name("Entity");
		builder.description("Data model entity");
		builder.field(buildFieldDefinition("name", "Entity name", Scalars.GraphQLString));
		builder.field(buildFieldDefinition("description", "Entity description", Scalars.GraphQLString));
		builder.field(
				buildFieldDefinition("embedded", "Whether this is an embedded data entity", Scalars.GraphQLBoolean));
		builder.field(buildFieldDefinition("attributes", "Entity attributes",
				new GraphQLList(buildEntityAttributeObjectType())));
		return builder.build();
	}

	private GraphQLObjectType buildEntityAttributeObjectType() {
		final GraphQLObjectType.Builder builder = GraphQLObjectType.newObject();
		builder.name("EntityAttribute");
		builder.description("Data model entity attribute");
		builder.field(buildFieldDefinition("name", "Entity attribute name", Scalars.GraphQLString));
		builder.field(buildFieldDefinition("description", "Entity attribute description", Scalars.GraphQLString));

		final GraphQLEnumType.Builder attributeTypeBuilder = GraphQLEnumType.newEnum();
		attributeTypeBuilder.name("Entity attribute type");
		attributeTypeBuilder.description("Enum for entity attribute type");
		Stream.of(enumMetaData.getEnumClass().getEnumConstants())
				.forEach(enumValue -> builder.value(enumValue.name(), enumValue));

		return attributeTypeBuilder.build();
	}

	private GraphQLObjectType buildEntityAttributeActionObjectType() {
		final GraphQLObjectType.Builder builder = GraphQLObjectType.newObject();

		return builder.build();
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
