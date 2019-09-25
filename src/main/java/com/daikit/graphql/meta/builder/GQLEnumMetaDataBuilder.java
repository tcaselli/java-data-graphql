package com.daikit.graphql.meta.builder;

import com.daikit.graphql.config.GQLSchemaConfig;
import com.daikit.graphql.meta.entity.GQLEnumMetaData;

/**
 * Builder for enum meta data from its class
 *
 * @author Thibaut Caselli
 */
public class GQLEnumMetaDataBuilder extends GQLAbstractMetaDataBuilder {

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// CONSTRUCTORS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	/**
	 * Constructor
	 *
	 * @param schemaConfig
	 *            the {@link GQLSchemaConfig}
	 */
	public GQLEnumMetaDataBuilder(final GQLSchemaConfig schemaConfig) {
		super(schemaConfig);
	}

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// PUBLIC METHODS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	/**
	 * Build enum meta data from given enum class
	 *
	 * @param enumClass
	 *            the enum class
	 * @return the created {@link GQLEnumMetaData}
	 */
	public GQLEnumMetaData build(final Class<? extends Enum<?>> enumClass) {
		final GQLEnumMetaData enumMetaData = new GQLEnumMetaData();
		enumMetaData.setName(enumClass.getSimpleName());
		enumMetaData.setEnumClass(enumClass);
		return enumMetaData;
	}

}
