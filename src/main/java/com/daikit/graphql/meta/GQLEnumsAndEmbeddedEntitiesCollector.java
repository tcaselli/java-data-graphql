package com.daikit.graphql.meta;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.reflect.FieldUtils;

import com.daikit.generics.utils.GenericsUtils;
import com.daikit.graphql.config.GQLSchemaConfig;
import com.daikit.graphql.custommethod.GQLCustomMethod;
import com.daikit.graphql.dynamicattribute.IGQLAbstractDynamicAttribute;
import com.daikit.graphql.dynamicattribute.IGQLDynamicAttributeGetter;
import com.daikit.graphql.dynamicattribute.IGQLDynamicAttributeSetter;
import com.daikit.graphql.enums.GQLScalarTypeEnum;

/**
 * Collectors for all entities and enumerations that will need to be put as meta
 * data in meta model
 *
 * @author Thibaut Caselli
 */
public class GQLEnumsAndEmbeddedEntitiesCollector {

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
	public GQLEnumsAndEmbeddedEntitiesCollector(final GQLSchemaConfig schemaConfig) {
		this.schemaConfig = schemaConfig;
	}

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// PUBLIC METHODS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	/**
	 * Collect all entities and enumerations from given entities, dynamic
	 * attributes and custom methods
	 *
	 * @param entityClasses
	 *            the collection of entity classes
	 * @param availableEmbeddedEntityClasses
	 *            the collection of embedded entity classes
	 * @param dynamicAttributes
	 *            the collection of {@link IGQLAbstractDynamicAttribute}
	 * @param methods
	 *            the collection of {@link GQLCustomMethod} (meta data will be
	 *            created automatically)
	 * @return a {@link GQLEnumsAndEmbeddedEntities}
	 */
	public GQLEnumsAndEmbeddedEntities collect(final Collection<Class<?>> entityClasses,
			final Collection<Class<?>> availableEmbeddedEntityClasses,
			final Collection<IGQLAbstractDynamicAttribute<?>> dynamicAttributes,
			final Collection<GQLCustomMethod> methods) {
		final GQLEnumsAndEmbeddedEntities collected = new GQLEnumsAndEmbeddedEntities();

		dynamicAttributes.stream().filter(dynamicAttribute -> dynamicAttribute instanceof IGQLDynamicAttributeGetter)
				.forEach(dynamicAttribute -> collect(entityClasses, availableEmbeddedEntityClasses,
						((IGQLDynamicAttributeGetter<?, ?>) dynamicAttribute).getGetterAttributeType(), collected));

		dynamicAttributes.stream().filter(dynamicAttribute -> dynamicAttribute instanceof IGQLDynamicAttributeSetter)
				.forEach(dynamicAttribute -> collect(entityClasses, availableEmbeddedEntityClasses,
						((IGQLDynamicAttributeSetter<?, ?>) dynamicAttribute).getSetterAttributeType(), collected));

		methods.forEach(method -> {
			collect(entityClasses, availableEmbeddedEntityClasses, method.getOutputType(), collected);
			method.getArgs()
					.forEach(arg -> collect(entityClasses, availableEmbeddedEntityClasses, arg.getType(), collected));
		});

		entityClasses.forEach(entityClass -> {
			FieldUtils.getAllFieldsList(entityClass).forEach(
					field -> collect(entityClasses, availableEmbeddedEntityClasses, field.getGenericType(), collected));
		});

		return collected;
	}

	@SuppressWarnings("unchecked")
	private void collect(final Collection<Class<?>> entityClasses,
			final Collection<Class<?>> availableEmbeddedEntityClasses, final Type attributeType,
			final GQLEnumsAndEmbeddedEntities collected) {
		final Class<?> rawClass = GenericsUtils.getTypeClass(attributeType);
		if (Enum.class.isAssignableFrom(rawClass)) {
			collected.getEnums().add((Class<? extends Enum<?>>) rawClass);
		} else if (Collection.class.isAssignableFrom(rawClass)) {
			collect(entityClasses, availableEmbeddedEntityClasses, GenericsUtils.getTypeArguments(attributeType).get(0),
					collected);
		} else if (isByteArray(rawClass)) {
			collect(entityClasses, availableEmbeddedEntityClasses, rawClass.getComponentType(), collected);
		} else if (!schemaConfig.isScalarType(rawClass) && !entityClasses.contains(rawClass)
				&& !entityClasses.contains(attributeType)) {
			if (!collected.getEntities().contains(rawClass)) {
				getWithExtendingClasses(availableEmbeddedEntityClasses, rawClass).forEach(embeddedEntityClass -> {
					collected.getEntities().add(embeddedEntityClass);
					FieldUtils.getAllFieldsList(embeddedEntityClass).forEach(field -> collect(entityClasses,
							availableEmbeddedEntityClasses, field.getGenericType(), collected));
				});
			}
		}
	}

	private boolean isByteArray(final Class<?> type) {
		return type.isArray() && schemaConfig.isScalarType(type.getComponentType()) && GQLScalarTypeEnum.BYTE.toString()
				.equals(schemaConfig.getScalarTypeCodeFromClass(type.getComponentType()).get());
	}

	private Set<Class<?>> getWithExtendingClasses(final Collection<Class<?>> availableEmbeddedEntityClasses,
			final Class<?> entityClass) {
		final Set<Class<?>> assignableClasses = availableEmbeddedEntityClasses == null
				? new HashSet<>()
				: availableEmbeddedEntityClasses.stream().filter(potential -> entityClass.isAssignableFrom(potential))
						.collect(Collectors.toSet());
		if (!assignableClasses.contains(entityClass)) {
			assignableClasses.add(entityClass);
		}
		return assignableClasses.size() == 1
				? assignableClasses
				: assignableClasses.stream()
						.flatMap(subClass -> getWithExtendingClasses(availableEmbeddedEntityClasses, subClass).stream())
						.collect(Collectors.toSet());
	}

}
