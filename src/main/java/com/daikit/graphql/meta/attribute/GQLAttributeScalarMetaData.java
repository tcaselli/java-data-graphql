package com.daikit.graphql.meta.attribute;

import com.daikit.graphql.enums.GQLScalarTypeEnum;

/**
 * GraphQL Scalar attribute meta data
 *
 * @author tcaselli
 */
public class GQLAttributeScalarMetaData extends GQLAbstractAttributeMetaData {

	private GQLScalarTypeEnum scalarType;

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// CONSTRUCTORS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	/**
	 * Default constructor
	 */
	public GQLAttributeScalarMetaData() {
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
	 * @param scalarType
	 *            the scalar type
	 */
	public GQLAttributeScalarMetaData(String name, GQLScalarTypeEnum scalarType) {
		super(name);
		this.scalarType = scalarType;
	}

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// METHODS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	@Override
	protected void appendToString(final StringBuilder stringBuilder) {
		stringBuilder.append("{SCALAR(").append(scalarType == null ? "" : scalarType.name()).append(")}");
		super.appendToString(stringBuilder);
	}

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// GETTERS / SETTERS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	/**
	 * Get the scalar type {@link GQLScalarTypeEnum}
	 *
	 * @return the scalarType
	 */
	public GQLScalarTypeEnum getScalarType() {
		return scalarType;
	}

	/**
	 * Set the scalar type {@link GQLScalarTypeEnum}
	 *
	 * @param scalarType
	 *            the scalarType to set
	 */
	public void setScalarType(final GQLScalarTypeEnum scalarType) {
		this.scalarType = scalarType;
	}

}
