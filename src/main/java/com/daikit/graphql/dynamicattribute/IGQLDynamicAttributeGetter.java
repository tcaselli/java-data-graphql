package com.daikit.graphql.dynamicattribute;

/**
 * Interface for dynamic attribute getters
 *
 * @author Thibaut Caselli
 *
 * @param <ENTITY_TYPE>
 *            the type of the entity this dynamic attribute is registered on
 * @param <ATTRIBUTE_TYPE>
 *            the type of this dynamic attribute
 */
public interface IGQLDynamicAttributeGetter<ENTITY_TYPE, ATTRIBUTE_TYPE>
		extends
			IGQLAbstractDynamicAttribute<ENTITY_TYPE, ATTRIBUTE_TYPE> {

	/**
	 * Get dynamic value computed from given input object
	 *
	 * @param source
	 *            the source object
	 * @return the computed dynamic attribute value
	 */
	ATTRIBUTE_TYPE getValue(ENTITY_TYPE source);

}
