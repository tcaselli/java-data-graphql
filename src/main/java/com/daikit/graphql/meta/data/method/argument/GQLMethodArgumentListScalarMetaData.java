package com.daikit.graphql.meta.data.method.argument;

import java.util.List;

import com.daikit.graphql.meta.data.GQLScalarTypeEnum;

/**
 * GraphQL dynamic method argument {@link List} of entities meta data
 *
 * @author tcaselli
 */
public class GQLMethodArgumentListScalarMetaData extends GQLAbstractMethodArgumentMetaData {

	private GQLScalarTypeEnum scalarType;

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
	 * @param scalarType
	 *            the scalar type
	 */
	public GQLMethodArgumentListScalarMetaData(String name, GQLScalarTypeEnum scalarType) {
		super(name);
		this.scalarType = scalarType;
	}

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// METHODS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	@Override
	protected void appendToString(final StringBuilder stringBuilder) {
		stringBuilder.append("{METHOD-ARGUMENT-LIST-SCALAR(").append(scalarType == null ? "" : scalarType.name())
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
	 */
	public void setScalarType(final GQLScalarTypeEnum scalarType) {
		this.scalarType = scalarType;
	}

}
