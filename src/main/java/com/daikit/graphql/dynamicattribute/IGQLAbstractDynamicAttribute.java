package com.daikit.graphql.dynamicattribute;

import com.daikit.generics.utils.GenericsUtils;

/**
 * Abstract super interface for dynamic attributes
 *
 * @author Thibaut Caselli
 *
 * @param <ENTITY_TYPE>
 *            the type of the entity this dynamic attribute is registered on
 * @param <ATTRIBUTE_TYPE>
 *            the type of this dynamic attribute
 */
public interface IGQLAbstractDynamicAttribute<ENTITY_TYPE, ATTRIBUTE_TYPE> {

	/**
	 * @return the name for the attribute. This name will be used for building
	 *         GraphQL schema : queries, mutations, descriptions etc.
	 */
	String getName();

	/**
	 * @return the type of the entity this dynamic attribute is registered on.
	 *         By default it is taken from class generics.
	 */
	default Class<?> getEntityType() {
		return GenericsUtils.getTypeClassArguments(getClass(), IGQLAbstractDynamicAttribute.class).get(0);
	}

	/**
	 * @return the type of this dynamic attribute. By default it is taken from
	 *         class generics.
	 */
	default Class<?> getAttributeType() {
		return GenericsUtils.getTypeClassArguments(getClass(), IGQLAbstractDynamicAttribute.class).get(1);
	}

}
