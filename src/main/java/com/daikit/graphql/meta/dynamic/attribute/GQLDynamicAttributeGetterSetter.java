package com.daikit.graphql.meta.dynamic.attribute;

/**
 * Dynamic attribute getter & setter
 *
 * @author tcaselli
 * @param <HOLDING_TYPE>
 *            the input object value holding type
 * @param <ATTRIBUTE_TYPE>
 *            the returned value attribute type
 */
public interface GQLDynamicAttributeGetterSetter<HOLDING_TYPE, ATTRIBUTE_TYPE>
		extends
			GQLDynamicAttributeGetter<HOLDING_TYPE, ATTRIBUTE_TYPE>,
			GQLDynamicAttributeSetter<HOLDING_TYPE, ATTRIBUTE_TYPE> {

}
