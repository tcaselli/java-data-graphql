package com.daikit.graphql.meta.dynamic.attribute;

/**
 * Dynamic attribute filter
 *
 * @author tcaselli
 * @param <HOLDING_TYPE>
 *            the input object value holding type
 * @param <ATTRIBUTE_TYPE>
 *            the returned value attribute type
 * @param <QUERY_TYPE>
 *            the type of the generic query this filter will be applied on
 */
public interface GQLDynamicAttributeFilter<HOLDING_TYPE, ATTRIBUTE_TYPE, QUERY_TYPE>
		extends
			GQLAbstractDynamicAttributeGetterSetterFilter {

	/**
	 * Get the exact path of the property on which the filter will be applied
	 *
	 * @return the property path.
	 */
	String getFilteredPropertyQueryPath();

	/**
	 * Apply modifications on the query this filter will be applied on.
	 *
	 * @param query
	 *            the generic query
	 */
	void applyModificationsOnRequest(QUERY_TYPE query);

}
