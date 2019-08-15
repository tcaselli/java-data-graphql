package com.daikit.graphql.dynamicattribute;

/**
 * Dynamic attribute getter & setter
 *
 * @author tcaselli
 * @param <ENTITY_TYPE>
 *            the input object value holding type
 * @param <ATTRIBUTE_TYPE>
 *            the returned value attribute type
 */
public interface IGQLDynamicAttributeGetterSetter<ENTITY_TYPE, ATTRIBUTE_TYPE>
		extends
			IGQLDynamicAttributeGetter<ENTITY_TYPE, ATTRIBUTE_TYPE>,
			IGQLDynamicAttributeSetter<ENTITY_TYPE, ATTRIBUTE_TYPE> {

}
