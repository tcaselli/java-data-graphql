package com.daikit.graphql.builder.types;

import java.util.stream.Stream;

import com.daikit.graphql.builder.GQLAbstractSchemaSubBuilder;
import com.daikit.graphql.builder.GQLSchemaBuilderCache;
import com.daikit.graphql.meta.GQLMetaDataModel;
import com.daikit.graphql.meta.entity.GQLEnumMetaData;
import com.daikit.graphql.utils.Message;

import graphql.schema.GraphQLEnumType;

/**
 * Type builder for enumerations
 *
 * @author Thibaut Caselli
 */
public class GQLEnumTypesBuilder extends GQLAbstractSchemaSubBuilder {

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// CONSTRUCTORS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	/**
	 * Constructor
	 *
	 * @param cache
	 *            the {@link GQLSchemaBuilderCache}
	 */
	public GQLEnumTypesBuilder(final GQLSchemaBuilderCache cache) {
		super(cache);
	}

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// METHODS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	/**
	 * Builder {@link GraphQLEnumType} types from given {@link GQLMetaDataModel}
	 *
	 * @param metaDataModel
	 *            the {@link GQLMetaDataModel}
	 */
	public void buildEnumTypes(final GQLMetaDataModel metaDataModel) {
		logger.debug("START building enum types...");
		metaDataModel.getEnums()
				.forEach(enumMeta -> getCache().getEnumTypes().put(enumMeta.getEnumClass(), buildEnum(enumMeta)));
		logger.debug("END building enum types");
	}

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// PRIVATE UTILS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	private GraphQLEnumType buildEnum(final GQLEnumMetaData enumMetaData) {
		logger.debug(Message.format("Build enum type [{}]", enumMetaData.getName()));
		final GraphQLEnumType.Builder builder = GraphQLEnumType.newEnum();
		builder.name(enumMetaData.getName());
		builder.description("Enum type for [" + enumMetaData.getName() + "]");
		Stream.of(enumMetaData.getEnumClass().getEnumConstants())
				.forEach(enumValue -> builder.value(enumValue.name(), enumValue));
		return builder.build();
	}

}
