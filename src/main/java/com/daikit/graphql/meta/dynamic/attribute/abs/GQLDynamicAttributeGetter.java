package com.daikit.graphql.meta.dynamic.attribute.abs;

import com.daikit.graphql.meta.dynamic.attribute.IGQLDynamicAttributeGetter;

/**
 * Dynamic attribute getter
 *
 * @author tcaselli
 * @param <ENTITY_TYPE>
 *            the input object value holding type
 * @param <ATTRIBUTE_TYPE>
 *            the returned value attribute type
 */
public abstract class GQLDynamicAttributeGetter<ENTITY_TYPE, ATTRIBUTE_TYPE>
		extends
			GQLAbstractDynamicAttribute<ENTITY_TYPE, ATTRIBUTE_TYPE>
		implements
			IGQLDynamicAttributeGetter<ENTITY_TYPE, ATTRIBUTE_TYPE> {

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// CONSTRUCTORS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	/**
	 * Constructor
	 */
	public GQLDynamicAttributeGetter() {
		// Nothing done
	}

	/**
	 * Constructor
	 *
	 * @param name
	 *            the property name that will be available in GraphQL schema
	 */
	public GQLDynamicAttributeGetter(String name) {
		super(name);
	}

}
