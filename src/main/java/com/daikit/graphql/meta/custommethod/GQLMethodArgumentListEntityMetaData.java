package com.daikit.graphql.meta.custommethod;

import java.util.List;

/**
 * GraphQL dynamic method argument {@link List} of entities meta data
 *
 * @author tcaselli
 */
public class GQLMethodArgumentListEntityMetaData extends GQLAbstractMethodArgumentMetaData {

	private Class<?> foreignClass;

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// CONSTRUCTORS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	/**
	 * Default constructor
	 */
	public GQLMethodArgumentListEntityMetaData() {
		// Nothing done
	}

	/**
	 * Constructor passing name and foreign entity class and keeping default
	 * values for other attributes.
	 *
	 * @param name
	 *            the name for the method argument. This name will be used for
	 *            building GraphQL schema query or mutation for this method
	 * @param foreignClass
	 *            the foreign entity class
	 */
	public GQLMethodArgumentListEntityMetaData(String name, Class<?> foreignClass) {
		super(name);
		this.foreignClass = foreignClass;
	}

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// METHODS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	@Override
	protected void appendToString(final StringBuilder stringBuilder) {
		stringBuilder.append("{METHOD-ARGUMENT-LIST-ENTITY(")
				.append(foreignClass == null ? "" : foreignClass.getSimpleName()).append(")}");
		super.appendToString(stringBuilder);
	}

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// GETTERS / SETTERS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	/**
	 * Get the foreign entity class
	 *
	 * @return the foreignClass
	 */
	public Class<?> getForeignClass() {
		return foreignClass;
	}

	/**
	 * Set the foreign entity class
	 *
	 * @param foreignClass
	 *            the foreignClass to set
	 */
	public void setForeignClass(final Class<?> foreignClass) {
		this.foreignClass = foreignClass;
	}

}
