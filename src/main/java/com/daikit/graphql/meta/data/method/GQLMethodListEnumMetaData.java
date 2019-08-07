package com.daikit.graphql.meta.data.method;

import java.util.List;

import com.daikit.graphql.meta.dynamic.method.GQLAbstractCustomMethod;

/**
 * GraphQL dynamic method returning a {@link List} of entities meta data
 *
 * @author tcaselli
 */
public class GQLMethodListEnumMetaData extends GQLAbstractMethodMetaData {

	private Class<?> enumClass;

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// CONSTRUCTORS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	/**
	 * Default constructor
	 */
	public GQLMethodListEnumMetaData() {
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
	 * @param enumClass
	 *            the enumeration class for method return type
	 */
	public GQLMethodListEnumMetaData(String name, boolean mutation, GQLAbstractCustomMethod<?> method,
			Class<? extends Enum<?>> enumClass) {
		super(name, mutation, method);
		this.enumClass = enumClass;
	}

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// METHODS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	@Override
	protected void appendToString(final StringBuilder stringBuilder) {
		stringBuilder.append("{METHOD-LIST-ENUM(").append(enumClass == null ? "" : enumClass.getSimpleName())
				.append(")}");
		super.appendToString(stringBuilder);
	}

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// GETTERS / SETTERS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	/**
	 * Get the method enumeration return type
	 * 
	 * @return the enumClass
	 */
	public Class<?> getEnumClass() {
		return enumClass;
	}

	/**
	 * Set the method enumeration return type
	 * 
	 * @param enumClass
	 *            the enumClass to set
	 */
	public void setEnumClass(final Class<?> enumClass) {
		this.enumClass = enumClass;
	}

}
