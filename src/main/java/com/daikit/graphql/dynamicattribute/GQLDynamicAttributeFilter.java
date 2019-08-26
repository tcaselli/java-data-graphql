package com.daikit.graphql.dynamicattribute;

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
public abstract class GQLDynamicAttributeFilter<ENTITY_TYPE, FILTER_ATTRIBUTE_TYPE, QUERY_TYPE>
		extends
			GQLAbstractDynamicAttribute<ENTITY_TYPE>
		implements
			IGQLDynamicAttributeFilter<ENTITY_TYPE, FILTER_ATTRIBUTE_TYPE, QUERY_TYPE> {

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
