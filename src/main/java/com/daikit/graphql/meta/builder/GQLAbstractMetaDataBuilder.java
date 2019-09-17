package com.daikit.graphql.meta.builder;

import java.util.Collection;

import com.daikit.graphql.config.GQLSchemaConfig;
import com.daikit.graphql.meta.entity.GQLEntityMetaData;
import com.daikit.graphql.meta.entity.GQLEnumMetaData;

/**
 * Abstract super class for all meta data builders
 *
 * @author Thibaut Caselli
 */
public class GQLAbstractMetaDataBuilder {

	private final GQLSchemaConfig schemaConfig;

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// CONSTRUCTORS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	/**
	 * Constructor
	 *
	 * @param schemaConfig
	 *            the {@link GQLSchemaConfig}
	 */
	public GQLAbstractMetaDataBuilder(final GQLSchemaConfig schemaConfig) {
		this.schemaConfig = schemaConfig;
	}

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// PROTECTED METHODS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	protected boolean isEnum(final Collection<GQLEnumMetaData> enumMetaDatas, final Class<?> clazz) {
		return enumMetaDatas.stream().filter(metaData -> metaData.getEnumClass().isAssignableFrom(clazz)).findFirst()
				.isPresent();
	}

	protected boolean isEntity(final Collection<GQLEntityMetaData> entityMetaDatas, final Class<?> clazz) {
		return entityMetaDatas.stream().filter(metaData -> metaData.getEntityClass().isAssignableFrom(clazz))
				.findFirst().isPresent();
	}

	/**
	 * @return the schemaConfig
	 */
	protected GQLSchemaConfig getConfig() {
		return schemaConfig;
	}

}
