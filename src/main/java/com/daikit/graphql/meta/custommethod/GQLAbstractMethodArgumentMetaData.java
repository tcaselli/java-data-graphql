package com.daikit.graphql.meta.custommethod;

import com.daikit.graphql.meta.GQLAbstractMetaData;

/**
 * Abstract super class for method arguments
 *
 * @author tcaselli
 */
public abstract class GQLAbstractMethodArgumentMetaData extends GQLAbstractMetaData {

	private String name;

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// CONSTRUCTORS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	/**
	 * Default constructor
	 */
	public GQLAbstractMethodArgumentMetaData() {
		// Nothing done
	}

	/**
	 * Constructor passing name and keeping default values for other attributes.
	 *
	 * @param name
	 *            the name for the method. This name will be used for building
	 *            GraphQL schema query or mutation for this method
	 */
	public GQLAbstractMethodArgumentMetaData(String name) {
		this.name = name;
	}

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// METHODS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	@Override
	protected void appendToString(final StringBuilder stringBuilder) {
		stringBuilder.append(name);
	}

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// GETTERS / SETTERS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	/**
	 * Get the name for the method. This name will be used for building GraphQL
	 * schema query or mutation for this method
	 * 
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Set the name for the method. This name will be used for building GraphQL
	 * schema query or mutation for this method
	 * 
	 * @param name
	 *            the name to set
	 */
	public void setName(final String name) {
		this.name = name;
	}

}
