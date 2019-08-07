package com.daikit.graphql.meta.dynamic.attribute;

/**
 * Dynamic attribute getter
 *
 * @author tcaselli
 * @param <HOLDING_TYPE>
 *            the input object value holding type
 * @param <ATTRIBUTE_TYPE>
 *            the returned value attribute type
 */
public interface GQLDynamicAttributeGetter<HOLDING_TYPE, ATTRIBUTE_TYPE>
		extends
			GQLAbstractDynamicAttributeGetterSetterFilter {

	/**
	 * Get dynamic value computed from given input object
	 *
	 * @param source
	 *            the source object
	 * @return the computed dynamic attribute value
	 */
	ATTRIBUTE_TYPE getValue(HOLDING_TYPE source);

}
