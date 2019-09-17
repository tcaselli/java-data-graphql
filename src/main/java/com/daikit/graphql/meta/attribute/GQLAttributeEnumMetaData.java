package com.daikit.graphql.meta.attribute;

/**
 * GraphQL attribute enum meta data
 *
 * @author Thibaut Caselli
 */
public class GQLAttributeEnumMetaData extends GQLAbstractAttributeMetaData {

	private Class<? extends Enum<?>> enumClass;

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// CONSTRUCTORS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	/**
	 * Default constructor
	 */
	public GQLAttributeEnumMetaData() {
		// Nothing done
	}

	/**
	 * Constructor passing name and enum class and keeping default values for
	 * other attributes.
	 *
	 * @param name
	 *            the name for the attribute. This name will be used for
	 *            building GraphQL schema : queries, mutations, descriptions
	 *            etc.
	 * @param enumClass
	 *            the enum class
	 */
	public GQLAttributeEnumMetaData(final String name, final Class<? extends Enum<?>> enumClass) {
		super(name);
		this.enumClass = enumClass;
	}

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// METHODS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	@Override
	protected void appendToString(final StringBuilder stringBuilder) {
		stringBuilder.append("{ENUM(").append(enumClass == null ? "" : enumClass.getSimpleName()).append(")}");
		super.appendToString(stringBuilder);
	}

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// GETTERS / SETTERS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	/**
	 * Get the enum class
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
	 * @return this instance
	 */
	public GQLAttributeEnumMetaData setEnumClass(final Class<? extends Enum<?>> enumClass) {
		this.enumClass = enumClass;
		return this;
	}

}
