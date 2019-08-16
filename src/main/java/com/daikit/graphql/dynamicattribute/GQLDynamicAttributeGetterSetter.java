package com.daikit.graphql.dynamicattribute;

/**
 * Dynamic attribute getter & setter
 *
 * @author Thibaut Caselli
 * @param <ENTITY_TYPE>
 *            the input object value holding type
 * @param <ATTRIBUTE_TYPE>
 *            the attribute type
 */
public abstract class GQLDynamicAttributeGetterSetter<ENTITY_TYPE, ATTRIBUTE_TYPE>
		extends
			GQLAbstractDynamicAttribute<ENTITY_TYPE, ATTRIBUTE_TYPE>
		implements
			IGQLDynamicAttributeGetter<ENTITY_TYPE, ATTRIBUTE_TYPE>,
			IGQLDynamicAttributeSetter<ENTITY_TYPE, ATTRIBUTE_TYPE> {

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

}