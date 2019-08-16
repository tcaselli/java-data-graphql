package com.daikit.graphql.meta.custommethod;

import java.util.List;

import com.daikit.graphql.custommethod.GQLAbstractCustomMethod;
import com.daikit.graphql.enums.GQLScalarTypeEnum;

/**
 * GraphQL dynamic method returning a {@link List} of entities meta data
 *
 * @author Thibaut Caselli
 */
public class GQLMethodListScalarMetaData extends GQLAbstractMethodMetaData {

	private GQLScalarTypeEnum scalarType;

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
	 * @param scalarType
	 *            the scalar type for method return type
	 *            {@link GQLScalarTypeEnum}
	 */
	public GQLMethodListScalarMetaData(GQLAbstractCustomMethod<?> method, GQLScalarTypeEnum scalarType) {
		super(method);
		this.scalarType = scalarType;
	}

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// METHODS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	@Override
	protected void appendToString(final StringBuilder stringBuilder) {
		stringBuilder.append("{METHOD-LIST-SCALAR(").append(scalarType == null ? "" : scalarType.name()).append(")}");
		super.appendToString(stringBuilder);
	}

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// GETTERS / SETTERS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	/**
	 * Get the method scalar return type {@link GQLScalarTypeEnum}
	 *
	 * @return the scalarType
	 */
	public GQLScalarTypeEnum getScalarType() {
		return scalarType;
	}

	/**
	 * Set the method scalar return type {@link GQLScalarTypeEnum}
	 *
	 * @param scalarType
	 *            the scalarType to set
	 * @return this instance
	 */
	public GQLMethodListScalarMetaData setScalarType(final GQLScalarTypeEnum scalarType) {
		this.scalarType = scalarType;
		return this;
	}

}
