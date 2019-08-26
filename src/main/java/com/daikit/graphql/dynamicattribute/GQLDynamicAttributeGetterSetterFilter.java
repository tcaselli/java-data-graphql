package com.daikit.graphql.dynamicattribute;

import org.apache.commons.lang3.StringUtils;

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
 * @param <QUERY_TYPE>
 *            the type of the generic query this filter will be applied on
 */
public abstract class GQLDynamicAttributeGetterSetterFilter<ENTITY_TYPE, GETTER_ATTRIBUTE_TYPE, SETTER_ATTRIBUTE_TYPE, QUERY_TYPE>
		extends
			GQLDynamicAttributeFilter<ENTITY_TYPE, GETTER_ATTRIBUTE_TYPE, QUERY_TYPE>
		implements
			IGQLDynamicAttributeGetter<ENTITY_TYPE, GETTER_ATTRIBUTE_TYPE>,
			IGQLDynamicAttributeSetter<ENTITY_TYPE, SETTER_ATTRIBUTE_TYPE> {

	private String entityName;

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// CONSTRUCTORS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	/**
	 * Constructor
	 */
	public GQLDynamicAttributeGetterSetterFilter() {
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
	public GQLDynamicAttributeGetterSetterFilter(String name, String filteredPropertyQueryPath) {
		super(name, filteredPropertyQueryPath);
	}

	/**
	 * Constructor
	 *
	 * @param entityName
	 *            the name of the entity holding this attribute in the GQL
	 *            schema. By default this is the simple name of the entity class
	 *            taken from class generics.
	 * @param name
	 *            the property name that will be available in GraphQL schema
	 * @param filteredPropertyQueryPath
	 *            the query path for the dynamic filter
	 */
	public GQLDynamicAttributeGetterSetterFilter(String entityName, String name, String filteredPropertyQueryPath) {
		super(name, filteredPropertyQueryPath);
		this.entityName = entityName;
	}

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// GETTERS / SETTERS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	/**
	 * Get the name of the entity holding this attribute in the GQL schema. By
	 * default this is the simple name of the entity class taken from class
	 * generics.
	 *
	 * @return the entity name
	 */
	@Override
	public String getEntityName() {
		return StringUtils.isEmpty(entityName) ? IGQLDynamicAttributeSetter.super.getEntityName() : entityName;
	}

	/**
	 * @param entityName
	 *            the entityName to set
	 */
	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}

}
