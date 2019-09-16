package com.daikit.graphql.meta.custommethod;

import java.util.List;

import com.daikit.graphql.enums.GQLScalarTypeEnum;

/**
 * GraphQL dynamic method argument {@link List} of entities meta data
 *
 * @author Thibaut Caselli
 */
public class GQLMethodArgumentListScalarMetaData extends GQLAbstractMethodArgumentMetaData {

	private String scalarTypeCode;

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// CONSTRUCTORS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	/**
	 * Default constructor
	 */
	public GQLMethodArgumentListScalarMetaData() {
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
	 *            the scalar type
	 */
	public GQLMethodArgumentListScalarMetaData(String name, String scalarTypeCode) {
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
	public GQLMethodArgumentListScalarMetaData(String name, GQLScalarTypeEnum scalarType) {
		super(name);
		this.scalarTypeCode = scalarType.toString();
	}

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// METHODS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	@Override
	protected void appendToString(final StringBuilder stringBuilder) {
		stringBuilder.append("{METHOD-ARGUMENT-LIST-SCALAR(").append(scalarTypeCode == null ? "" : scalarTypeCode)
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
	public GQLMethodArgumentListScalarMetaData setScalarType(final String scalarTypeCode) {
		this.scalarTypeCode = scalarTypeCode;
		return this;
	}

}
