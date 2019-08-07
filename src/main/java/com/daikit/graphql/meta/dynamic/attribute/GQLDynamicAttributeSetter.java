package com.daikit.graphql.meta.dynamic.attribute;

/**
 * Dynamic attribute setter
 *
 * @author tcaselli
 * @param <HOLDING_TYPE>
 *            the input object value holding type
 * @param <ATTRIBUTE_TYPE>
 *            the returned value attribute type
 */
public interface GQLDynamicAttributeSetter<HOLDING_TYPE, ATTRIBUTE_TYPE>
		extends
			GQLAbstractDynamicAttributeGetterSetterFilter {

	/**
	 * Set <code>valueToSet</code> within <code>inputObject</code>
	 *
	 * @param source
	 *            the source object
	 * @param valueToSet
	 *            the value to set
	 */
	void setValue(HOLDING_TYPE source, ATTRIBUTE_TYPE valueToSet);

}
