package com.daikit.graphql.meta.custommethod;

import java.util.List;

import com.daikit.graphql.custommethod.GQLAbstractCustomMethod;

/**
 * GraphQL dynamic method returning a {@link List} of entities meta data
 *
 * @author Thibaut Caselli
 */
public class GQLMethodListScalarMetaData extends GQLAbstractMethodMetaData {

	private String scalarTypeCode;

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// CONSTRUCTORS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	/**
	 * Default constructor
	 */
	public GQLMethodListScalarMetaData() {
		// Nothing done
	}

	/**
	 * Constructor passing name, whether this is a mutation or a query, method
	 * and return type
	 *
	 * @param method
	 *            the {@link GQLAbstractCustomMethod}
	 * @param scalarTypeCode
	 *            the scalar type code for method return type
	 */
	public GQLMethodListScalarMetaData(GQLAbstractCustomMethod<?> method, String scalarTypeCode) {
		super(method);
		this.scalarTypeCode = scalarTypeCode;
	}

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// METHODS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	@Override
	protected void appendToString(final StringBuilder stringBuilder) {
		stringBuilder.append("{METHOD-LIST-SCALAR(").append(scalarTypeCode == null ? "" : scalarTypeCode).append(")}");
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
	public GQLMethodListScalarMetaData setScalarType(final String scalarTypeCode) {
		this.scalarTypeCode = scalarTypeCode;
		return this;
	}

}
