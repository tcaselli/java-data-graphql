package com.daikit.graphql.meta.custommethod;

import com.daikit.graphql.enums.GQLScalarTypeEnum;

/**
 * GraphQL dynamic method argument scalar meta data
 *
 * @author Thibaut Caselli
 */
public class GQLMethodArgumentScalarMetaData extends GQLAbstractMethodArgumentMetaData {

	private GQLScalarTypeEnum scalarType;

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// CONSTRUCTORS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	/**
	 * Default constructor
	 */
	public GQLMethodArgumentScalarMetaData() {
		// Nothing done
	}

	/**
	 * Constructor passing name and scalar type and keeping default values for
	 * other attributes.
	 *
	 * @param name
	 *            the name for the method argument. This name will be used for
	 *            building GraphQL schema query or mutation for this method
	 * @param scalarType
	 *            the scalar type
	 */
	public GQLMethodArgumentScalarMetaData(String name, GQLScalarTypeEnum scalarType) {
		super(name);
		this.scalarType = scalarType;
	}

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// METHODS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	@Override
	protected void appendToString(final StringBuilder stringBuilder) {
		stringBuilder.append("{METHOD-ARGUMENT-SCALAR(").append(scalarType == null ? "" : scalarType.name())
				.append(")}");
		super.appendToString(stringBuilder);
	}

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// GETTERS / SETTERS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	/**
	 * Get the scalar type
	 *
	 * @return the scalarType
	 */
	public GQLScalarTypeEnum getScalarType() {
		return scalarType;
	}

	/**
	 * Set the scalar type
	 *
	 * @param scalarType
	 *            the scalarType to set
	 * @return this instance
	 */
	public GQLMethodArgumentScalarMetaData setScalarType(final GQLScalarTypeEnum scalarType) {
		this.scalarType = scalarType;
		return this;
	}

}
