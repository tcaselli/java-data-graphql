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
public abstract class GQLDynamicAttributeGetterSetter<ENTITY_TYPE, GETTER_ATTRIBUTE_TYPE, SETTER_ATTRIBUTE_TYPE>
		extends
			GQLAbstractDynamicAttribute<ENTITY_TYPE>
		implements
			IGQLDynamicAttributeGetter<ENTITY_TYPE, GETTER_ATTRIBUTE_TYPE>,
			IGQLDynamicAttributeSetter<ENTITY_TYPE, SETTER_ATTRIBUTE_TYPE> {

	private String filterQueryPath;

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// CONSTRUCTORS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	/**
	 * Constructor
	 */
	public GQLDynamicAttributeGetterSetter() {
		// Nothing done
	}

	/**
	 * Constructor
	 *
	 * @param name
	 *            the property name that will be available in GraphQL schema
	 */
	public GQLDynamicAttributeGetterSetter(String name) {
		super(name);
	}

	/**
	 * Constructor
	 *
	 * @param name
	 *            the property name that will be available in GraphQL schema
	 * @param filterQueryPath
	 *            the query path for the dynamic filter
	 */
	public GQLDynamicAttributeGetterSetter(String name, String filterQueryPath) {
		super(name);
		this.filterQueryPath = filterQueryPath;
	}

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// GETTERS / SETTERS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	/**
	 * @return the filterQueryPath
	 */
	@Override
	public String getFilterQueryPath() {
		return filterQueryPath;
	}

	/**
	 * @param filterQueryPath
	 *            the filterQueryPath to set
	 */
	public void setFilterQueryPath(String filterQueryPath) {
		this.filterQueryPath = filterQueryPath;
	}
}
