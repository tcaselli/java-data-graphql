package com.daikit.graphql.dynamicattribute;

/**
 * Dynamic attribute getter and setter
 *
 * @author Thibaut Caselli
 * @param <ENTITY_TYPE>
 *            the input object value holding type
 * @param <GETTER_ATTRIBUTE_TYPE>
 *            the getter value attribute type
 * @param <SETTER_ATTRIBUTE_TYPE>
 *            the setter attribute argument type
 */
public interface IGQLDynamicAttributeGetterSetter<ENTITY_TYPE, GETTER_ATTRIBUTE_TYPE, SETTER_ATTRIBUTE_TYPE>
		extends
			IGQLDynamicAttributeGetter<ENTITY_TYPE, GETTER_ATTRIBUTE_TYPE>,
			IGQLDynamicAttributeSetter<ENTITY_TYPE, SETTER_ATTRIBUTE_TYPE> {

}
