package com.daikit.graphql.meta.builder;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;

import com.daikit.generics.utils.GenericsUtils;
import com.daikit.graphql.config.GQLSchemaConfig;
import com.daikit.graphql.enums.GQLScalarTypeEnum;
import com.daikit.graphql.meta.GQLAttribute;
import com.daikit.graphql.meta.GQLEntity;
import com.daikit.graphql.meta.attribute.GQLAbstractAttributeMetaData;
import com.daikit.graphql.meta.attribute.GQLAttributeEntityMetaData;
import com.daikit.graphql.meta.attribute.GQLAttributeEnumMetaData;
import com.daikit.graphql.meta.attribute.GQLAttributeListEntityMetaData;
import com.daikit.graphql.meta.attribute.GQLAttributeListEnumMetaData;
import com.daikit.graphql.meta.attribute.GQLAttributeListScalarMetaData;
import com.daikit.graphql.meta.attribute.GQLAttributeScalarMetaData;
import com.daikit.graphql.meta.entity.GQLEntityMetaData;

/**
 * Build for entity meta data from its class
 *
 * @author Thibaut Caselli
 */
public class GQLEntityMetaDataBuilder extends GQLAbstractMetaDataBuilder {

	private final Collection<Class<?>> allEntityClasses;
	private final Collection<Class<?>> allDataClasses;
	private final Collection<Class<? extends Enum<?>>> allEnumClasses;

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// CONSTRUCTORS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	/**
	 * Constructor
	 *
	 * @param schemaConfig
	 *            the {@link GQLSchemaConfig}
	 * @param allEntityClasses
	 *            a collection of all entity classes
	 * @param allDataClasses
	 *            a collection of all available data classes
	 * @param allEnumClasses
	 *            a collection of all available enum classes
	 */
	public GQLEntityMetaDataBuilder(final GQLSchemaConfig schemaConfig, final Collection<Class<?>> allEntityClasses,
			final Collection<Class<?>> allDataClasses, final Collection<Class<? extends Enum<?>>> allEnumClasses) {
		super(schemaConfig);
		this.allEntityClasses = allEntityClasses;
		this.allDataClasses = allDataClasses;
		this.allEnumClasses = allEnumClasses;
	}

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// PUBLIC METHODS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	/**
	 * Build GraphQL entity from given entity class
	 *
	 *
	 * @param entityClass
	 *            the entity class to build meta data from
	 * @param embedded
	 *            whether this entity is embedded
	 * @return a {@link GQLEntityMetaData}
	 */
	public GQLEntityMetaData build(final Class<?> entityClass, final boolean embedded) {
		final GQLEntity annotation = entityClass.getAnnotation(GQLEntity.class);
		GQLEntityMetaData gqlEntity = null;
		if (annotation == null || !annotation.exclude()) {
			gqlEntity = new GQLEntityMetaData();
			gqlEntity.setConcrete(!Modifier.isAbstract(entityClass.getModifiers()));
			gqlEntity.setEntityClass(entityClass);
			gqlEntity.setEmbedded(embedded);

			final boolean readOnly = annotation != null && annotation.readOnly();

			gqlEntity.setName(annotation == null || StringUtils.isEmpty(annotation.name())
					? entityClass.getSimpleName()
					: annotation.name());

			gqlEntity.setDescription(annotation == null ? null : annotation.description());

			gqlEntity.setReadable(readOnly || annotation == null || annotation.read());
			gqlEntity.setSaveable(!readOnly && (annotation == null || annotation.save()));
			gqlEntity.setDeletable(!readOnly && (annotation == null || annotation.delete()));

			if (allEntityClasses.contains(entityClass.getSuperclass())) {
				gqlEntity.setSuperEntityClass(entityClass.getSuperclass());
			}

			gqlEntity.getAttributes()
					.addAll(FieldUtils.getAllFieldsList(entityClass).stream()
							.map(field -> buildAttribute(entityClass, field)).filter(Objects::nonNull)
							.collect(Collectors.toList()));
		}
		return gqlEntity;
	}

	private GQLAbstractAttributeMetaData buildAttribute(Class<?> entityClass, final Field field) {
		GQLAbstractAttributeMetaData attribute = null;
		if (!Modifier.isTransient(field.getModifiers()) && !Modifier.isStatic(field.getModifiers())) {
			final GQLAttribute annotation = field.getAnnotation(GQLAttribute.class);
			if (annotation == null || !annotation.exclude()) {
				if (getConfig().isScalarType(field.getType())) {
					attribute = buildGQLAttributeScalar(field);
				} else if (isByteArray(field.getType())) {
					attribute = buildGQLAttributeScalarArray(field, field.getType().getComponentType());
				} else if (Collection.class.isAssignableFrom(field.getType())) {
					final Class<?> genericType = GenericsUtils.getFieldTypeArgumentAsClass(entityClass, field);
					if (getConfig().isScalarType(genericType)) {
						attribute = buildGQLAttributeListScalar(field, genericType);
					} else if (allEntityClasses.contains(genericType)) {
						attribute = buildGQLAttributeListEntity(field, genericType, false);
					} else if (allDataClasses.contains(genericType)) {
						attribute = buildGQLAttributeListEntity(field, genericType, true);
					} else if (allEnumClasses.contains(genericType)) {
						attribute = buildGQLAttributeListEnum(field, genericType);
					}
				} else if (allEntityClasses.contains(field.getType())) {
					attribute = buildGQLAttributeEntity(field, false);
				} else if (allDataClasses.contains(field.getType())) {
					attribute = buildGQLAttributeEntity(field, true);
				} else if (allEnumClasses.contains(field.getType())) {
					attribute = buildGQLAttributeEnum(field);
				}
			}
		}
		return attribute;
	}
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// PRIVATE UTILS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	private GQLAttributeScalarMetaData buildGQLAttributeScalar(final Field field) {
		final GQLAttributeScalarMetaData attribute = new GQLAttributeScalarMetaData();
		populateAttributeMetaData(field, attribute);
		if (getConfig().getAttributeIdName().equals(field.getName())) {
			attribute.setScalarType(GQLScalarTypeEnum.ID.toString());
			attribute.setNullableForUpdate(false);
			attribute.setMandatoryForUpdate(true);
		} else {
			attribute.setScalarType(getConfig().getScalarTypeCodeFromClass(field.getType()).get());
		}
		return attribute;
	}

	private GQLAttributeScalarMetaData buildGQLAttributeScalarArray(final Field field, Class<?> componentType) {
		final GQLAttributeScalarMetaData attribute = new GQLAttributeScalarMetaData();
		populateAttributeMetaData(field, attribute);
		attribute.setScalarType(getConfig().getScalarTypeCodeFromClass(componentType).get());
		return attribute;
	}

	private GQLAttributeListScalarMetaData buildGQLAttributeListScalar(Field field, Class<?> genericType) {
		final GQLAttributeListScalarMetaData attribute = new GQLAttributeListScalarMetaData();
		populateAttributeMetaData(field, attribute);
		attribute.setScalarType(getConfig().getScalarTypeCodeFromClass(genericType).get());
		return attribute;
	}

	@SuppressWarnings("unchecked")
	private GQLAttributeEnumMetaData buildGQLAttributeEnum(final Field field) {
		final GQLAttributeEnumMetaData attribute = new GQLAttributeEnumMetaData();
		populateAttributeMetaData(field, attribute);
		attribute.setEnumClass((Class<? extends Enum<?>>) field.getType());
		return attribute;
	}

	@SuppressWarnings("unchecked")
	private GQLAttributeListEnumMetaData buildGQLAttributeListEnum(final Field field, Class<?> foreignClass) {
		final GQLAttributeListEnumMetaData attribute = new GQLAttributeListEnumMetaData();
		populateAttributeMetaData(field, attribute);
		attribute.setEnumClass((Class<? extends Enum<?>>) foreignClass);
		return attribute;
	}

	private GQLAttributeEntityMetaData buildGQLAttributeEntity(final Field field, boolean embedded) {
		final GQLAttributeEntityMetaData attribute = new GQLAttributeEntityMetaData();
		populateAttributeMetaData(field, attribute);
		attribute.setEntityClass(field.getType());
		attribute.setEmbedded(embedded);
		return attribute;
	}

	private GQLAttributeListEntityMetaData buildGQLAttributeListEntity(final Field field, Class<?> foreignClass,
			boolean embedded) {
		final GQLAttributeListEntityMetaData attribute = new GQLAttributeListEntityMetaData();
		populateAttributeMetaData(field, attribute);
		attribute.setForeignClass(foreignClass);
		attribute.setEmbedded(embedded);
		return attribute;
	}

	private void populateAttributeMetaData(final Field field, final GQLAbstractAttributeMetaData attribute) {
		final GQLAttribute annotation = field.getAnnotation(GQLAttribute.class);
		attribute.setName(
				annotation == null || StringUtils.isEmpty(annotation.name()) ? field.getName() : annotation.name());
		attribute.setDescription(annotation == null ? null : annotation.description());
		if (annotation != null) {
			attribute.setNullableForCreate(annotation.nullableForCreate() && annotation.nullable());
			attribute.setNullableForUpdate(annotation.nullableForUpdate() && annotation.nullable());
		}
		attribute.setReadable(annotation == null || annotation.readOnly() || annotation.read());
		attribute.setSaveable(annotation == null || !annotation.readOnly() && annotation.save());
		attribute.setFilterable(annotation == null || annotation.filter());
	}

}
