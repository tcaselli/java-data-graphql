package com.daikit.graphql.dynamicattribute;

import com.daikit.generics.utils.GenericsUtils;

/**
 * Interface for dynamic attribute setters
 *
 * @author Thibaut Caselli
 *
 * @param <ENTITY_TYPE>
 *            the type of the entity this dynamic attribute is registered on
 * @param <SETTER_ATTRIBUTE_TYPE>
 *            the setter attribute argument type
 */
public interface IGQLDynamicAttributeSetter<ENTITY_TYPE, SETTER_ATTRIBUTE_TYPE>
		extends
			IGQLAbstractDynamicAttribute<ENTITY_TYPE> {

	/**
	 * Set <code>valueToSet</code> within <code>inputObject</code>
	 *
	 * @param source
	 *            the source object
	 * @param valueToSet
	 *            the value to set
	 */
	void setValue(ENTITY_TYPE source, SETTER_ATTRIBUTE_TYPE valueToSet);

	/**
	 * Get the name of the entity holding this attribute in the GQL schema. By
	 * default this is the simple name of the entity class taken from class
	 * generics.
	 *
	 * @return the entity name
	 */
	default String getEntityName() {
		return GenericsUtils.getTypeClassArguments(getClass(), IGQLDynamicAttributeSetter.class).get(0).getSimpleName();
	}

	/**
	 * @return the setter type of this dynamic attribute. By default it is taken
	 *         from class generics.
	 */
	default Class<?> getSetterAttributeType() {
		return GenericsUtils.getTypeClassArguments(getClass(), IGQLDynamicAttributeSetter.class).get(1);
	}

}
