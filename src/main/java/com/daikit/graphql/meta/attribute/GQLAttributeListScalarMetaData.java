package com.daikit.graphql.meta.attribute;

import com.daikit.graphql.enums.GQLScalarTypeEnum;

/**
 * GraphQL list attribute meta data for scalar type
 *
 * @author Thibaut Caselli
 */
public class GQLAttributeListScalarMetaData extends GQLAbstractAttributeMetaData {

	private String scalarTypeCode;

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// CONSTRUCTORS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	/**
	 * Default constructor
	 */
	public GQLAttributeListScalarMetaData() {
		// Nothing done
	}

	/**
	 * Constructor passing name and scalar type and keeping default values for
	 * other attributes.
	 *
	 * @param name
	 *            the name for the attribute. This name will be used for
	 *            building GraphQL schema : queries, mutations, descriptions
	 *            etc.
	 * @param scalarTypeCode
	 *            the scalar type code
	 */
	public GQLAttributeListScalarMetaData(final String name, final String scalarTypeCode) {
		super(name);
		this.scalarTypeCode = scalarTypeCode;
	}

	/**
	 * Constructor passing name and scalar type and keeping default values for
	 * other attributes.
	 *
	 * @param name
	 *            the name for the attribute. This name will be used for
	 *            building GraphQL schema : queries, mutations, descriptions
	 *            etc.
	 * @param scalarType
	 *            the scalar type {@link GQLScalarTypeEnum}
	 */
	public GQLAttributeListScalarMetaData(final String name, final GQLScalarTypeEnum scalarType) {
		super(name);
		this.scalarTypeCode = scalarType.toString();
	}

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// METHODS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	@Override
	protected void appendToString(final StringBuilder stringBuilder) {
		stringBuilder.append("{LIST-SCALAR(").append(scalarTypeCode == null ? "" : scalarTypeCode).append(")}");
		super.appendToString(stringBuilder);
	}

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// GETTERS / SETTERS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	/**
	 * Get the scalar type code
	 *
	 * @return the scalarTypeCode
	 */
	public String getScalarType() {
		return scalarTypeCode;
	}

	/**
	 * Set the scalar type code
	 *
	 * @param scalarTypeCode
	 *            the scalarTypeCode to set
	 * @return this instance
	 */
	public GQLAttributeListScalarMetaData setScalarType(final String scalarTypeCode) {
		this.scalarTypeCode = scalarTypeCode;
		return this;
	}

}
