package com.daikit.graphql.dynamicattribute;

import com.daikit.generics.utils.GenericsUtils;

/**
 * Interface for dynamic attribute getters
 *
 * @author Thibaut Caselli
 *
 * @param <ENTITY_TYPE>
 *            the type of the entity this dynamic attribute is registered on
 * @param <GETTER_ATTRIBUTE_TYPE>
 *            the type of this dynamic attribute
 */
public interface IGQLDynamicAttributeGetter<ENTITY_TYPE, GETTER_ATTRIBUTE_TYPE>
		extends
			IGQLAbstractDynamicAttribute<ENTITY_TYPE> {

	/**
	 * Get dynamic value computed from given input object
	 *
	 * @param source
	 *            the source object
	 * @return the computed dynamic attribute value
	 */
	GETTER_ATTRIBUTE_TYPE getValue(ENTITY_TYPE source);

	/**
	 * @return the getter type of this dynamic attribute. By default it is taken
	 *         from class generics.
	 */
	default Class<?> getGetterAttributeType() {
		return GenericsUtils.getTypeClassArguments(getClass(), IGQLDynamicAttributeGetter.class).get(1);
	}

}
