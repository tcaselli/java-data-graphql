package com.daikit.graphql.dynamicattribute.abs;

import com.daikit.graphql.dynamicattribute.IGQLDynamicAttributeFilter;

/**
 * Dynamic attribute filter
 *
 * @author tcaselli
 * @param <ENTITY_TYPE>
 *            the input object value holding type
 * @param <ATTRIBUTE_TYPE>
 *            the returned value attribute type
 * @param <QUERY_TYPE>
 *            the type of the generic query this filter will be applied on
 */
public abstract class GQLDynamicAttributeFilter<ENTITY_TYPE, ATTRIBUTE_TYPE, QUERY_TYPE>
		extends
			GQLAbstractDynamicAttribute<ENTITY_TYPE, ATTRIBUTE_TYPE>
		implements
			IGQLDynamicAttributeFilter<ENTITY_TYPE, ATTRIBUTE_TYPE, QUERY_TYPE> {

	private String filteredPropertyQueryPath;

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// CONSTRUCTORS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	/**
	 * Constructor
	 */
	public GQLDynamicAttributeFilter() {
		// Nothing done
	}

	/**
	 * Constructor
	 *
	 * @param name
	 *            the property name that will be available in GraphQL schema
	 * @param filteredPropertyQueryPath
	 *            the query path for the dynamic filter
	 */
	public GQLDynamicAttributeFilter(String name, String filteredPropertyQueryPath) {
		super(name);
		this.filteredPropertyQueryPath = filteredPropertyQueryPath;
	}

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// GETTERS / SETTERS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	/**
	 * @return the filteredPropertyQueryPath
	 */
	@Override
	public String getFilteredPropertyQueryPath() {
		return filteredPropertyQueryPath;
	}

	/**
	 * @param filteredPropertyQueryPath
	 *            the filteredPropertyQueryPath to set
	 */
	public void setFilteredPropertyQueryPath(String filteredPropertyQueryPath) {
		this.filteredPropertyQueryPath = filteredPropertyQueryPath;
	}

}
