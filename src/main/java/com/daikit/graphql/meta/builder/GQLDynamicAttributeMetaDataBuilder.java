package com.daikit.graphql.meta.builder;

import java.lang.reflect.Type;
import java.util.Collection;

import org.apache.commons.lang3.StringUtils;

import com.daikit.generics.utils.GenericsUtils;
import com.daikit.graphql.config.GQLSchemaConfig;
import com.daikit.graphql.custommethod.GQLAbstractCustomMethod;
import com.daikit.graphql.dynamicattribute.IGQLAbstractDynamicAttribute;
import com.daikit.graphql.dynamicattribute.IGQLDynamicAttributeGetter;
import com.daikit.graphql.dynamicattribute.IGQLDynamicAttributeSetter;
import com.daikit.graphql.meta.attribute.GQLAbstractAttributeMetaData;
import com.daikit.graphql.meta.attribute.GQLAttributeEntityMetaData;
import com.daikit.graphql.meta.attribute.GQLAttributeEnumMetaData;
import com.daikit.graphql.meta.attribute.GQLAttributeListEntityMetaData;
import com.daikit.graphql.meta.attribute.GQLAttributeListEnumMetaData;
import com.daikit.graphql.meta.attribute.GQLAttributeListScalarMetaData;
import com.daikit.graphql.meta.attribute.GQLAttributeScalarMetaData;
import com.daikit.graphql.meta.custommethod.GQLAbstractMethodMetaData;
import com.daikit.graphql.meta.entity.GQLEntityMetaData;
import com.daikit.graphql.meta.entity.GQLEnumMetaData;
import com.daikit.graphql.utils.Message;

/**
 * Builder for dynamic attribute meta data from
 * {@link IGQLAbstractDynamicAttribute}
 *
 * @author Thibaut Caselli
 */
public class GQLDynamicAttributeMetaDataBuilder extends GQLAbstractMetaDataBuilder {

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// CONSTRUCTORS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	/**
	 * Constructor
	 *
	 * @param schemaConfig
	 *            the {@link GQLSchemaConfig}
	 */
	public GQLDynamicAttributeMetaDataBuilder(final GQLSchemaConfig schemaConfig) {
		super(schemaConfig);
	}

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// PUBLIC METHODS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	/**
	 * Build dynamic attribute meta data from its
	 * {@link GQLAbstractCustomMethod} definition
	 *
	 * @param enumMetaDatas
	 *            the collection of all registered {@link GQLEnumMetaData} for
	 *            being able to look for type references (for dynamic attribute
	 *            type)
	 * @param entityMetaDatas
	 *            the collection of all registered {@link GQLEntityMetaData} for
	 *            being able to look for type references (for dynamic attribute
	 *            type)
	 * @param attribute
	 *            the {@link GQLAbstractAttributeMetaData} to create meta data
	 *            from
	 * @return the created {@link GQLAbstractMethodMetaData}
	 */
	@SuppressWarnings("unchecked")
	public GQLAbstractAttributeMetaData build(final Collection<GQLEnumMetaData> enumMetaDatas,
			final Collection<GQLEntityMetaData> entityMetaDatas, final IGQLAbstractDynamicAttribute<?> attribute) {
		Type attributeType;
		boolean readable = false;
		boolean saveable = false;
		boolean filterable = false;
		// Type is given by getter
		if (attribute instanceof IGQLDynamicAttributeGetter) {
			attributeType = GenericsUtils.getTypeArguments(attribute.getClass(), IGQLDynamicAttributeGetter.class)
					.get(1);
			readable = true;
			saveable = attribute instanceof IGQLDynamicAttributeSetter;
			filterable = StringUtils.isNotEmpty(((IGQLDynamicAttributeGetter<?, ?>) attribute).getFilterQueryPath());
		}
		// If attribute is not a getter then type is given by setter
		else if (attribute instanceof IGQLDynamicAttributeSetter) {
			attributeType = GenericsUtils.getTypeArguments(attribute.getClass(), IGQLDynamicAttributeSetter.class)
					.get(1);
			saveable = true;
		} else {
			throw new IllegalArgumentException(
					"Unsupported dynamic attribute type for : " + attribute.getClass().getName());
		}

		final Class<?> attributeRawClass = GenericsUtils.getTypeClass(attributeType);

		final GQLAbstractAttributeMetaData attributeMetaData;
		// Create attribute
		if (getConfig().isScalarType(attributeRawClass)) {
			attributeMetaData = new GQLAttributeScalarMetaData();
			((GQLAttributeScalarMetaData) attributeMetaData)
					.setScalarType(getConfig().getScalarTypeCodeFromClass(attributeRawClass).get());
		} else if (isEnum(enumMetaDatas, attributeRawClass)) {
			attributeMetaData = new GQLAttributeEnumMetaData();
			((GQLAttributeEnumMetaData) attributeMetaData).setEnumClass((Class<? extends Enum<?>>) attributeRawClass);
		} else if (isEntity(entityMetaDatas, attributeRawClass)) {
			attributeMetaData = new GQLAttributeEntityMetaData();
			((GQLAttributeEntityMetaData) attributeMetaData).setEntityClass(attributeRawClass);
		} else if (Collection.class.isAssignableFrom(attributeRawClass)) {
			final Class<?> foreignClass = GenericsUtils.getTypeArgumentsAsClasses(attributeType, Collection.class)
					.get(0);
			if (getConfig().isScalarType(foreignClass)) {
				attributeMetaData = new GQLAttributeListScalarMetaData();
				((GQLAttributeListScalarMetaData) attributeMetaData)
						.setScalarType(getConfig().getScalarTypeCodeFromClass(foreignClass).get());
			} else if (isEnum(enumMetaDatas, foreignClass)) {
				attributeMetaData = new GQLAttributeListEnumMetaData();
				((GQLAttributeListEnumMetaData) attributeMetaData).setEnumClass(foreignClass);
			} else if (isEntity(entityMetaDatas, foreignClass)) {
				attributeMetaData = new GQLAttributeListEntityMetaData();
				((GQLAttributeListEntityMetaData) attributeMetaData).setForeignClass(foreignClass);
			} else {
				throw new IllegalArgumentException(
						Message.format("Not handled dynamic attribute [{}] on [{}] collection type [{}].",
								attribute.getName(), attribute.getEntityType().getName(), foreignClass.getName()));
			}
		} else {
			throw new IllegalArgumentException(Message.format("Not handled  dynamic attribute [{}] on [{}] type [{}].",
					attribute.getName(), attribute.getEntityType().getName(), attributeRawClass.getSimpleName()));
		}

		attributeMetaData.setReadable(readable);
		attributeMetaData.setSaveable(saveable);
		attributeMetaData.setFilterable(filterable);
		attributeMetaData.setName(attribute.getName());
		attributeMetaData.setDynamicAttributeGetter(
				attribute instanceof IGQLDynamicAttributeGetter ? (IGQLDynamicAttributeGetter<?, ?>) attribute : null);
		attributeMetaData.setDynamicAttributeSetter(
				attribute instanceof IGQLDynamicAttributeSetter ? (IGQLDynamicAttributeSetter<?, ?>) attribute : null);

		return attributeMetaData;
	}

}
