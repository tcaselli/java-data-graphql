package com.daikit.graphql.meta.custommethod;

import com.daikit.graphql.custommethod.GQLCustomMethod;

/**
 * GraphQL dynamic method returning a scalar meta data
 *
 * @author Thibaut Caselli
 */
public class GQLMethodScalarMetaData extends GQLAbstractMethodMetaData {

	private String scalarTypeCode;

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// CONSTRUCTORS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	/**
	 * Default constructor
	 */
	public GQLMethodScalarMetaData() {
		// Nothing done
	}

	/**
	 * Constructor passing name, whether this is a mutation or a query, method
	 * and return type
	 *
	 * @param method
	 *            the {@link GQLCustomMethod}
	 * @param scalarTypeCode
	 *            the scalar type code for method return type
	 */
	public GQLMethodScalarMetaData(final GQLCustomMethod method, final String scalarTypeCode) {
		super(method);
		this.scalarTypeCode = scalarTypeCode;
	}

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// METHODS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	@Override
	protected void appendToString(final StringBuilder stringBuilder) {
		stringBuilder.append("{METHOD-SCALAR(").append(scalarTypeCode == null ? "" : scalarTypeCode).append(")}");
		super.appendToString(stringBuilder);
	}

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// GETTERS / SETTERS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	/**
	 * Get the method scalar return type
	 *
	 * @return the scalarTypeCode
	 */
	public String getScalarType() {
		return scalarTypeCode;
	}

	/**
	 * Set the method scalar return type
	 *
	 * @param scalarTypeCode
	 *            the scalarTypeCode to set
	 * @return this instance
	 */
	public GQLMethodScalarMetaData setScalarType(final String scalarTypeCode) {
		this.scalarTypeCode = scalarTypeCode;
		return this;
	}

}
