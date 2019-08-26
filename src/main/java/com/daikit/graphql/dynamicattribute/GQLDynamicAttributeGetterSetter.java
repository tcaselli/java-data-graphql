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
 */
public abstract class GQLDynamicAttributeGetterSetter<ENTITY_TYPE, GETTER_ATTRIBUTE_TYPE, SETTER_ATTRIBUTE_TYPE>
		extends
			GQLAbstractDynamicAttribute<ENTITY_TYPE>
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
	 * @param entityName
	 *            the name of the entity holding this attribute in the GQL
	 *            schema. By default this is the simple name of the entity class
	 *            taken from class generics.
	 * @param name
	 *            the property name that will be available in GraphQL schema
	 */
	public GQLDynamicAttributeGetterSetter(String entityName, String name) {
		super(name);
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
