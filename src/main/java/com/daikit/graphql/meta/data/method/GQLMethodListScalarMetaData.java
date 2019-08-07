package com.daikit.graphql.meta.data.method;

import java.util.List;

import com.daikit.graphql.meta.data.GQLScalarTypeEnum;
import com.daikit.graphql.meta.dynamic.method.GQLAbstractCustomMethod;

/**
 * GraphQL dynamic method returning a {@link List} of entities meta data
 *
 * @author tcaselli
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
	 * @param name
	 *            the name for the method. This name will be used for building
	 *            GraphQL schema query or mutation for this method
	 * @param mutation
	 *            whether this is a mutation (<code>true</code>) or a query
	 *            (<code>false</code>)
	 * @param method
	 *            the {@link GQLAbstractCustomMethod}
	 * @param scalarType
	 *            the scalar type for method return type
	 *            {@link GQLScalarTypeEnum}
	 */
	public GQLMethodListScalarMetaData(String name, boolean mutation, GQLAbstractCustomMethod<?> method,
			GQLScalarTypeEnum scalarType) {
		super(name, mutation, method);
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
	 */
	public void setScalarType(final GQLScalarTypeEnum scalarType) {
		this.scalarType = scalarType;
	}

}
