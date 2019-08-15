package com.daikit.graphql.dynamicattribute;

import com.daikit.generics.utils.GenericsUtils;
import com.daikit.graphql.dynamicattribute.abs.GQLAbstractDynamicAttribute;

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
	 * Get the name of the entity holding this attribute in the GQL schema. By
	 * default this is the simple name of the entity class taken from class
	 * generics.
	 *
	 * @return the entity name
	 */
	default String getEntityName() {
		return GenericsUtils
				.getRawClass(GenericsUtils.getTypeArguments(getClass(), GQLAbstractDynamicAttribute.class).get(0))
				.getSimpleName();
	}

	/**
	 * @return the type of the entity this dynamic attribute is registered on.
	 *         By default it is taken from class generics.
	 */
	default Class<?> getEntityType() {
		return GenericsUtils
				.getRawClass(GenericsUtils.getTypeArguments(getClass(), IGQLAbstractDynamicAttribute.class).get(0));
	}

	/**
	 * @return the type of this dynamic attribute. By default it is taken from
	 *         class generics.
	 */
	default Class<?> getAttributeType() {
		return GenericsUtils
				.getRawClass(GenericsUtils.getTypeArguments(getClass(), IGQLAbstractDynamicAttribute.class).get(1));
	}

}
