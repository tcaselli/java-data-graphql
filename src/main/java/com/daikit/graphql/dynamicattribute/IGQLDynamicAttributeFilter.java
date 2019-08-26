package com.daikit.graphql.dynamicattribute;

import com.daikit.generics.utils.GenericsUtils;

/**
 * Dynamic attribute filter
 *
 * @author Thibaut Caselli
 * @param <ENTITY_TYPE>
 *            the input object value holding type
 * @param <FILTER_ATTRIBUTE_TYPE>
 *            the filtered value attribute type
 * @param <QUERY_TYPE>
 *            the type of the generic query this filter will be applied on
 */
public interface IGQLDynamicAttributeFilter<ENTITY_TYPE, FILTER_ATTRIBUTE_TYPE, QUERY_TYPE>
		extends
			IGQLAbstractDynamicAttribute<ENTITY_TYPE> {

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

	/**
	 * @return the type of this dynamic attribute. By default it is taken from
	 *         class generics.
	 */
	default Class<?> getFilterAttributeType() {
		return GenericsUtils.getTypeClassArguments(getClass(), IGQLDynamicAttributeFilter.class).get(1);
	}

	/**
	 * @return the query type of this dynamic attribute. By default it is taken
	 *         from class generics.
	 */
	default Class<?> getFilterQueryType() {
		return GenericsUtils.getTypeClassArguments(getClass(), IGQLDynamicAttributeFilter.class).get(2);
	}
}
