package com.daikit.graphql.meta.data.entity;

/**
 * GraphQL Enumeration meta data
 *
 * @author tcaselli
 */
public class GQLEnumMetaData {

	private String name;
	private Class<? extends Enum<?>> enumClass;

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// CONSTRUCTORS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	/**
	 * Default constructor
	 */
	public GQLEnumMetaData() {
		// Nothing done
	}

	/**
	 * Constructor passing name and enumeration class.
	 *
	 * @param name
	 *            the name for this enumeration. This name will be used for
	 *            building GraphQL schema : queries, mutations, descriptions
	 *            etc.
	 * @param enumClass
	 *            the enumeration class
	 */
	public GQLEnumMetaData(String name, Class<? extends Enum<?>> enumClass) {
		this.name = name;
		this.enumClass = enumClass;
	}

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// METHODS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("(").append(getName()).append("[ENUM(").append(enumClass.getSimpleName()).append(")]").append(")");
		return sb.toString();
	}

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// GETTERS / SETTERS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	/**
	 * Get the enum class.
	 *
	 * @return the enumClass
	 */
	public Class<? extends Enum<?>> getEnumClass() {
		return enumClass;
	}

	/**
	 * Set the enum class
	 *
	 * @param enumClass
	 *            the enumClass to set
	 */
	public void setEnumClass(final Class<? extends Enum<?>> enumClass) {
		this.enumClass = enumClass;
	}

	/**
	 * Get the name for the enum. This name will be used for building GraphQL
	 * schema : queries, mutations, descriptions etc
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Set the name for the enum. This name will be used for building GraphQL
	 * schema : queries, mutations, descriptions etc
	 *
	 * @param name
	 *            the name to set
	 */
	public void setName(final String name) {
		this.name = name;
	}

}
