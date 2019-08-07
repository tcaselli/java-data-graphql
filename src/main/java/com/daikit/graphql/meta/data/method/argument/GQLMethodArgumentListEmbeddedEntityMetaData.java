package com.daikit.graphql.meta.data.method.argument;

import java.util.List;

/**
 * GraphQL dynamic method argument {@link List} of embedded entities meta data
 *
 * @author tcaselli
 */
public class GQLMethodArgumentListEmbeddedEntityMetaData extends GQLAbstractMethodArgumentMetaData {

	private Class<?> foreignClass;

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// CONSTRUCTORS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	/**
	 * Default constructor
	 */
	public GQLMethodArgumentListEmbeddedEntityMetaData() {
		// Nothing done
	}

	/**
	 * Constructor passing name and foreign embedded entity class and keeping
	 * default values for other attributes.
	 *
	 * @param name
	 *            the name for the method argument. This name will be used for
	 *            building GraphQL schema query or mutation for this method
	 * @param foreignClass
	 *            the foreign embedded entity class
	 */
	public GQLMethodArgumentListEmbeddedEntityMetaData(String name, Class<?> foreignClass) {
		super(name);
		this.foreignClass = foreignClass;
	}

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// METHODS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	@Override
	protected void appendToString(final StringBuilder stringBuilder) {
		stringBuilder.append("{METHOD-ARGUMENT-LIST-ENTITY-EMBEDDED(")
				.append(foreignClass == null ? "" : foreignClass.getSimpleName()).append(")}");
		super.appendToString(stringBuilder);
	}

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// GETTERS / SETTERS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	/**
	 * Get the foreign class
	 *
	 * @return the foreignClass
	 */
	public Class<?> getForeignClass() {
		return foreignClass;
	}

	/**
	 * Set the foreign class
	 *
	 * @param foreignClass
	 *            the foreignClass to set
	 */
	public void setForeignClass(final Class<?> foreignClass) {
		this.foreignClass = foreignClass;
	}

}
