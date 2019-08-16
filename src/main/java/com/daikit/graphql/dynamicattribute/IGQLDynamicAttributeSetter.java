package com.daikit.graphql.dynamicattribute;

/**
 * Interface for dynamic attribute setters
 *
 * @author Thibaut Caselli
 *
 * @param <ENTITY_TYPE>
 *            the type of the entity this dynamic attribute is registered on
 * @param <ATTRIBUTE_TYPE>
 *            the type of this dynamic attribute
 */
public interface IGQLDynamicAttributeSetter<ENTITY_TYPE, ATTRIBUTE_TYPE>
		extends
			IGQLAbstractDynamicAttribute<ENTITY_TYPE, ATTRIBUTE_TYPE> {

	/**
	 * Set <code>valueToSet</code> within <code>inputObject</code>
	 *
	 * @param source
	 *            the source object
	 * @param valueToSet
	 *            the value to set
	 */
	void setValue(ENTITY_TYPE source, ATTRIBUTE_TYPE valueToSet);

	/**
	 * Get the name of the entity holding this attribute in the GQL schema. By
	 * default this is the simple name of the entity class taken from class
	 * generics.
	 *
	 * @return the entity name
	 */
	String getEntityName();

}
