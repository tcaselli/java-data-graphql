package com.daikit.graphql.meta.builder;

import java.util.Collection;
import java.util.Optional;

import com.daikit.graphql.config.GQLSchemaConfig;
import com.daikit.graphql.enums.GQLScalarTypeEnum;
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
	 * @param schemaConfig the {@link GQLSchemaConfig}
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
		return getEntity(entityMetaDatas, clazz).isPresent();
	}

	protected Optional<GQLEntityMetaData> getEntity(final Collection<GQLEntityMetaData> entityMetaDatas,
			final Class<?> clazz) {
		return entityMetaDatas.stream().filter(metaData -> metaData.getEntityClass().isAssignableFrom(clazz))
				.findFirst();
	}

	protected boolean isByteArray(final Class<?> type) {
		return type.isArray() && getConfig().isScalarType(type.getComponentType()) && GQLScalarTypeEnum.BYTE.toString()
				.equals(getConfig().getScalarTypeCodeFromClass(type.getComponentType()).get());
	}

	/**
	 * @return the schemaConfig
	 */
	protected GQLSchemaConfig getConfig() {
		return schemaConfig;
	}

}
