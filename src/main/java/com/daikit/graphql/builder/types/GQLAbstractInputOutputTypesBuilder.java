package com.daikit.graphql.builder.types;

import com.daikit.graphql.builder.GQLAbstractSchemaSubBuilder;
import com.daikit.graphql.builder.GQLSchemaBuilderCache;
import com.daikit.graphql.constants.GQLSchemaConstants;
import com.daikit.graphql.enums.GQLScalarTypeEnum;
import com.daikit.graphql.meta.attribute.GQLAbstractAttributeMetaData;
import com.daikit.graphql.meta.attribute.GQLAttributeScalarMetaData;
import com.daikit.graphql.meta.internal.GQLAbstractEntityMetaDataInfos;
import com.daikit.graphql.utils.Message;

import graphql.schema.GraphQLArgument;
import graphql.schema.GraphQLInputObjectField;
import graphql.schema.GraphQLInputType;
import graphql.schema.GraphQLNonNull;

/**
 * Abstract input/output types builder , super class for mutation and query
 * types builders
 *
 * @author Thibaut Caselli
 */
public class GQLAbstractInputOutputTypesBuilder extends GQLAbstractSchemaSubBuilder {

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// CONSTRUCTORS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	/**
	 * Constructor
	 *
	 * @param cache
	 *            the {@link GQLSchemaBuilderCache}
	 */
	public GQLAbstractInputOutputTypesBuilder(final GQLSchemaBuilderCache cache) {
		super(cache);
	}

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// PROTECTED UTILS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	protected GQLAttributeScalarMetaData getIdAttribute(final GQLAbstractEntityMetaDataInfos infos) {
		GQLAbstractEntityMetaDataInfos currentInfos = infos;
		GQLAttributeScalarMetaData idAttribute = null;
		while (idAttribute == null && currentInfos != null) {
			idAttribute = (GQLAttributeScalarMetaData) currentInfos.getEntity().getAttributes().stream()
					.filter(attribute -> attribute instanceof GQLAttributeScalarMetaData
							&& GQLScalarTypeEnum.ID.equals(((GQLAttributeScalarMetaData) attribute).getScalarType()))
					.findFirst().orElse(null);
			currentInfos = currentInfos.getSuperEntity();
		}
		if (idAttribute == null) {
			throw new IllegalArgumentException(Message.format("No ID attribute defined for entity class [{}]",
					infos.getEntity().getEntityClass()));
		}
		return idAttribute;
	}

	protected GraphQLArgument buildArgumentNonNull(final GQLAbstractAttributeMetaData attribute) {
		final GraphQLArgument.Builder builder = GraphQLArgument.newArgument();
		builder.name(attribute.getName());
		builder.description("Argument [" + attribute.getName() + "]");
		builder.type(new GraphQLNonNull(
				GQLSchemaConstants.SCALARS.get(((GQLAttributeScalarMetaData) attribute).getScalarType())));
		return builder.build();
	}

	protected GraphQLInputObjectField buildInputField(final String name, final String description,
			final GraphQLInputType type) {
		final GraphQLInputObjectField.Builder builder = GraphQLInputObjectField.newInputObjectField();
		builder.name(name);
		builder.description(description);
		builder.type(type);
		return builder.build();
	}

}
