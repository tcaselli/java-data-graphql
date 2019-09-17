package com.daikit.graphql.meta.custommethod;

import com.daikit.graphql.enums.GQLScalarTypeEnum;

/**
 * GraphQL dynamic method argument scalar meta data
 *
 * @author Thibaut Caselli
 */
public class GQLMethodArgumentScalarMetaData extends GQLAbstractMethodArgumentMetaData {

	private String scalarTypeCode;

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
	 * @param scalarTypeCode
	 *            the scalar type code
	 */
	public GQLMethodArgumentScalarMetaData(final String name, final String scalarTypeCode) {
		super(name);
		this.scalarTypeCode = scalarTypeCode;
	}

	/**
	 * Constructor passing name and scalar type and keeping default values for
	 * other attributes.
	 *
	 * @param name
	 *            the name for the method argument. This name will be used for
	 *            building GraphQL schema query or mutation for this method
	 * @param scalarType
	 *            the scalar type {@link GQLScalarTypeEnum}
	 */
	public GQLMethodArgumentScalarMetaData(final String name, final GQLScalarTypeEnum scalarType) {
		super(name);
		this.scalarTypeCode = scalarType.toString();
	}

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// METHODS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	@Override
	protected void appendToString(final StringBuilder stringBuilder) {
		stringBuilder.append("{METHOD-ARGUMENT-SCALAR(").append(scalarTypeCode == null ? "" : scalarTypeCode)
				.append(")}");
		super.appendToString(stringBuilder);
	}

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// GETTERS / SETTERS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	/**
	 * Get the scalar type
	 *
	 * @return the scalarTypeCode
	 */
	public String getScalarType() {
		return scalarTypeCode;
	}

	/**
	 * Set the scalar type
	 *
	 * @param scalarTypeCode
	 *            the scalarTypeCode to set
	 * @return this instance
	 */
	public GQLMethodArgumentScalarMetaData setScalarType(final String scalarTypeCode) {
		this.scalarTypeCode = scalarTypeCode;
		return this;
	}

}
