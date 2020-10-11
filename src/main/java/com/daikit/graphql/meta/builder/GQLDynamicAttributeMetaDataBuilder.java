package com.daikit.graphql.meta.builder;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;

import com.daikit.generics.utils.GenericsUtils;
import com.daikit.graphql.config.GQLSchemaConfig;
import com.daikit.graphql.custommethod.GQLCustomMethod;
import com.daikit.graphql.dynamicattribute.IGQLAbstractDynamicAttribute;
import com.daikit.graphql.dynamicattribute.IGQLDynamicAttributeGetter;
import com.daikit.graphql.dynamicattribute.IGQLDynamicAttributeSetter;
import com.daikit.graphql.enums.GQLScalarTypeEnum;
import com.daikit.graphql.meta.GQLAttribute;
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
 * Builder for dynamic attribute meta data from {@link IGQLAbstractDynamicAttribute}
 *
 * @author Thibaut Caselli
 */
public class GQLDynamicAttributeMetaDataBuilder extends GQLAbstractAttributeMetaDataBuilder {

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// CONSTRUCTORS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	/**
	 * Constructor
	 *
	 * @param schemaConfig the {@link GQLSchemaConfig}
	 */
	public GQLDynamicAttributeMetaDataBuilder(final GQLSchemaConfig schemaConfig) {
		super(schemaConfig);
	}

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// PUBLIC METHODS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	/**
	 * Build dynamic attribute meta data from its {@link GQLCustomMethod} definition
	 *
	 * @param enumMetaDatas   the collection of all registered {@link GQLEnumMetaData} for being able to look for type references (for dynamic attribute type)
	 * @param entityMetaDatas the collection of all registered {@link GQLEntityMetaData} for being able to look for type references (for dynamic attribute type)
	 * @param attribute       the {@link GQLAbstractAttributeMetaData} to create meta data from
	 * @return the created {@link List} of {@link GQLAbstractMethodMetaData} (one for getter, one for setter)
	 */
	public List<GQLAbstractAttributeMetaData> build(final Collection<GQLEnumMetaData> enumMetaDatas, final Collection<GQLEntityMetaData> entityMetaDatas,
			final IGQLAbstractDynamicAttribute<?> attribute) {
		final GQLAttribute annotation = attribute.getClass().getAnnotation(GQLAttribute.class);
		final List<GQLAbstractAttributeMetaData> attributeMetaDatas = new ArrayList<>();
		if (annotation == null || !annotation.exclude()) {
			if (attribute instanceof IGQLDynamicAttributeGetter) {
				final Type attributeType = GenericsUtils.getTypeArguments(attribute.getClass(), IGQLDynamicAttributeGetter.class).get(1);
				final boolean filterable = StringUtils.isNotEmpty(((IGQLDynamicAttributeGetter<?, ?>) attribute).getFilterQueryPath());
				final GQLAbstractAttributeMetaData attributeMetaData = createAttributeMetaData(enumMetaDatas, entityMetaDatas, attributeType, attribute);
				populate(attribute, annotation, true, false, filterable, attributeMetaData);
				attributeMetaData.setDynamicAttributeGetter((IGQLDynamicAttributeGetter<?, ?>) attribute);
				attributeMetaDatas.add(attributeMetaData);
			}
			if (attribute instanceof IGQLDynamicAttributeSetter) {
				final Type attributeType = GenericsUtils.getTypeArguments(attribute.getClass(), IGQLDynamicAttributeSetter.class).get(1);
				final GQLAbstractAttributeMetaData attributeMetaData = createAttributeMetaData(enumMetaDatas, entityMetaDatas, attributeType, attribute);
				populate(attribute, annotation, false, true, false, attributeMetaData);
				attributeMetaData.setDynamicAttributeSetter((IGQLDynamicAttributeSetter<?, ?>) attribute);
				attributeMetaDatas.add(attributeMetaData);
			}
			if (!(attribute instanceof IGQLDynamicAttributeGetter) && !(attribute instanceof IGQLDynamicAttributeSetter)) {
				throw new IllegalArgumentException("Unsupported dynamic attribute type for : " + attribute.getClass().getName());
			}
		}
		return attributeMetaDatas;
	}

	private void populate(final IGQLAbstractDynamicAttribute<?> attribute, final GQLAttribute annotation, final boolean readable, final boolean saveable,
			final boolean filterable, final GQLAbstractAttributeMetaData attributeMetaData) {
		populateAttributeRights(attributeMetaData, annotation, readable, saveable);
		attributeMetaData.setFilterable(filterable);
		attributeMetaData.setName(attribute.getName());
	}

	@SuppressWarnings("unchecked")
	private GQLAbstractAttributeMetaData createAttributeMetaData(final Collection<GQLEnumMetaData> enumMetaDatas,
			final Collection<GQLEntityMetaData> entityMetaDatas, final Type attributeType, final IGQLAbstractDynamicAttribute<?> attribute) {
		GQLAbstractAttributeMetaData attributeMetaData;
		final Class<?> attributeRawClass = GenericsUtils.getTypeClass(attributeType);
		// Create attribute
		if (getConfig().isScalarType(attributeRawClass) || attributeRawClass.isArray() && getConfig().isScalarType(attributeRawClass.getComponentType())
				&& GQLScalarTypeEnum.BYTE.toString().equals(getConfig().getScalarTypeCodeFromClass(attributeRawClass.getComponentType()).get())) {
			attributeMetaData = new GQLAttributeScalarMetaData();
			((GQLAttributeScalarMetaData) attributeMetaData).setScalarType(getConfig().getScalarTypeCodeFromClass(attributeRawClass).get());
		} else if (isEnum(enumMetaDatas, attributeRawClass)) {
			attributeMetaData = new GQLAttributeEnumMetaData();
			((GQLAttributeEnumMetaData) attributeMetaData).setEnumClass((Class<? extends Enum<?>>) attributeRawClass);
		} else {
			final Optional<GQLEntityMetaData> optionalEntity = getEntity(entityMetaDatas, attributeRawClass);
			if (optionalEntity.isPresent()) {
				attributeMetaData = new GQLAttributeEntityMetaData();
				((GQLAttributeEntityMetaData) attributeMetaData).setEntityClass(attributeRawClass);
				((GQLAttributeEntityMetaData) attributeMetaData).setEmbedded(optionalEntity.get().isEmbedded());
			} else if (Collection.class.isAssignableFrom(attributeRawClass)) {
				final Class<?> foreignClass = GenericsUtils.getTypeArgumentsAsClasses(attributeType, Collection.class).get(0);
				if (getConfig().isScalarType(foreignClass)) {
					attributeMetaData = new GQLAttributeListScalarMetaData();
					((GQLAttributeListScalarMetaData) attributeMetaData).setScalarType(getConfig().getScalarTypeCodeFromClass(foreignClass).get());
				} else if (isEnum(enumMetaDatas, foreignClass)) {
					attributeMetaData = new GQLAttributeListEnumMetaData();
					((GQLAttributeListEnumMetaData) attributeMetaData).setEnumClass((Class<? extends Enum<?>>) foreignClass);
				} else {
					final Optional<GQLEntityMetaData> optionalForeignEntity = getEntity(entityMetaDatas, foreignClass);
					if (optionalForeignEntity.isPresent()) {
						attributeMetaData = new GQLAttributeListEntityMetaData();
						((GQLAttributeListEntityMetaData) attributeMetaData).setForeignClass(foreignClass);
						((GQLAttributeListEntityMetaData) attributeMetaData).setEmbedded(optionalForeignEntity.get().isEmbedded());
					} else {
						throw new IllegalArgumentException(Message.format("Not handled dynamic attribute [{}] on [{}] collection type [{}].",
								attribute.getName(), attribute.getEntityType().getName(), foreignClass.getName()));
					}
				}
			} else {
				throw new IllegalArgumentException(Message.format("Not handled  dynamic attribute [{}] on [{}] type [{}].", attribute.getName(),
						attribute.getEntityType().getName(), attributeRawClass.getSimpleName()));
			}
		}
		return attributeMetaData;
	}

}
