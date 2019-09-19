package com.daikit.graphql.datafetcher;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.daikit.graphql.dynamicattribute.IGQLAbstractDynamicAttribute;
import com.daikit.graphql.dynamicattribute.IGQLDynamicAttributeGetter;
import com.daikit.graphql.dynamicattribute.IGQLDynamicAttributeSetter;
import com.daikit.graphql.meta.GQLInternalMetaModel;
import com.daikit.graphql.utils.Message;

import graphql.GraphQLException;

/**
 * Registry for all {@link IGQLDynamicAttributeSetter} and
 * {@link IGQLDynamicAttributeGetter}
 *
 * @author Thibaut Caselli
 */
public class GQLDynamicAttributeRegistry {

	// Map (key=entityClass,value=Map(key=fieldName,value=dynAttr))
	private final Map<Class<?>, Map<String, IGQLDynamicAttributeSetter<?, ?>>> dynamicAttributeSetters = new HashMap<>();
	private final Map<Class<?>, Map<String, IGQLDynamicAttributeGetter<?, ?>>> dynamicAttributeGetters = new HashMap<>();

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// CONSTRUCTORS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	/**
	 * Constructor
	 *
	 * @param metaModel
	 *            the {@link GQLInternalMetaModel}
	 */
	public GQLDynamicAttributeRegistry(final GQLInternalMetaModel metaModel) {
		this.register(metaModel, metaModel.getDynamicAttributeSetters(), dynamicAttributeSetters);
		this.register(metaModel, metaModel.getDynamicAttributeGetters(), dynamicAttributeGetters);
	}

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// PRIVATE METHODS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	private <T extends IGQLAbstractDynamicAttribute<?>> void register(final GQLInternalMetaModel metaModel,
			final Collection<T> dynamicAttributes, final Map<Class<?>, Map<String, T>> map) {
		dynamicAttributes.stream().forEach(dynAttr -> {
			final Map<String, T> attrMap = map.computeIfAbsent(dynAttr.getEntityType(), x -> new HashMap<>());
			final T existing = attrMap.get(dynAttr.getName());
			if (existing != null && !existing.equals(dynAttr)) {
				throw new GraphQLException(
						Message.format("Duplicate dynamic attributes registered for entity {} and property name {}.",
								dynAttr.getEntityType(), dynAttr.getName()));
			}
			attrMap.put(dynAttr.getName(), dynAttr);
		});
	}

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// PUBLIC METHODS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	/**
	 * Get optional registered {@link IGQLDynamicAttributeSetter} for given
	 * entity class and attribute name
	 *
	 * @param entityClass
	 *            the class of the entity the {@link IGQLDynamicAttributeSetter}
	 *            is registered on
	 * @param attributeName
	 *            the name of the attribute the
	 *            {@link IGQLDynamicAttributeSetter} is registered on within the
	 *            entity with given entityClass
	 * @param <ENTITY_TYPE>
	 *            the entity type
	 * @param <SETTER_ATTRIBUTE_TYPE>
	 *            the attribute type within the
	 *            {@link IGQLDynamicAttributeSetter}
	 * @return the {@link Optional} {@link IGQLDynamicAttributeSetter}
	 */
	@SuppressWarnings("unchecked")
	public <ENTITY_TYPE, SETTER_ATTRIBUTE_TYPE> Optional<IGQLDynamicAttributeSetter<ENTITY_TYPE, SETTER_ATTRIBUTE_TYPE>> getSetter(
			final Class<?> entityClass, final String attributeName) {
		final Map<String, IGQLDynamicAttributeSetter<?, ?>> map = dynamicAttributeSetters.get(entityClass);
		return map == null
				? Optional.empty()
				: Optional.ofNullable(
						(IGQLDynamicAttributeSetter<ENTITY_TYPE, SETTER_ATTRIBUTE_TYPE>) map.get(attributeName));
	}

	/**
	 * Get optional registered {@link IGQLDynamicAttributeGetter} for given
	 * entity class and attribute name
	 *
	 * @param entityClass
	 *            the class of the entity the {@link IGQLDynamicAttributeGetter}
	 *            is registered on
	 * @param attributeName
	 *            the name of the attribute the
	 *            {@link IGQLDynamicAttributeGetter} is registered on within the
	 *            entity with given entityClass
	 * @param <ENTITY_TYPE>
	 *            the entity type
	 * @param <SETTER_ATTRIBUTE_TYPE>
	 *            the attribute type within the
	 *            {@link IGQLDynamicAttributeSetter}
	 * @return the {@link Optional} {@link IGQLDynamicAttributeGetter}
	 */
	@SuppressWarnings("unchecked")
	public <ENTITY_TYPE, SETTER_ATTRIBUTE_TYPE> Optional<IGQLDynamicAttributeGetter<ENTITY_TYPE, SETTER_ATTRIBUTE_TYPE>> getGetter(
			final Class<?> entityClass, final String attributeName) {
		final Map<String, IGQLDynamicAttributeGetter<?, ?>> map = dynamicAttributeGetters.get(entityClass);
		return map == null
				? Optional.empty()
				: Optional.ofNullable(
						(IGQLDynamicAttributeGetter<ENTITY_TYPE, SETTER_ATTRIBUTE_TYPE>) map.get(attributeName));
	}

}
