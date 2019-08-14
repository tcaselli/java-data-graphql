package com.daikit.graphql.meta.dynamic.attribute.abs;

import com.daikit.graphql.meta.dynamic.attribute.IGQLDynamicAttributeSetter;

/**
 * Dynamic attribute setter
 *
 * @author tcaselli
 * @param <ENTITY_TYPE>
 *            the input object value holding type
 * @param <ATTRIBUTE_TYPE>
 *            the returned value attribute type
 */
public abstract class GQLDynamicAttributeSetter<ENTITY_TYPE, ATTRIBUTE_TYPE>
		extends
			GQLAbstractDynamicAttribute<ENTITY_TYPE, ATTRIBUTE_TYPE>
		implements
			IGQLDynamicAttributeSetter<ENTITY_TYPE, ATTRIBUTE_TYPE> {

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// CONSTRUCTORS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	/**
	 * Constructor
	 */
	public GQLDynamicAttributeSetter() {
		// Nothing done
	}

	/**
	 * Constructor
	 *
	 * @param name
	 *            the property name that will be available in GraphQL schema
	 */
	public GQLDynamicAttributeSetter(String name) {
		super(name);
	}

}
