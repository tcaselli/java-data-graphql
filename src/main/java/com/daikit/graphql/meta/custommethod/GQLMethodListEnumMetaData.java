package com.daikit.graphql.meta.custommethod;

import java.util.List;

import com.daikit.graphql.custommethod.GQLAbstractCustomMethod;

/**
 * GraphQL dynamic method returning a {@link List} of entities meta data
 *
 * @author Thibaut Caselli
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
	 * @param method
	 *            the {@link GQLAbstractCustomMethod}
	 * @param enumClass
	 *            the enumeration class for method return type
	 */
	public GQLMethodListEnumMetaData(GQLAbstractCustomMethod<?> method, Class<? extends Enum<?>> enumClass) {
		super(method);
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
	 * @return this instance
	 */
	public GQLMethodListEnumMetaData setEnumClass(final Class<?> enumClass) {
		this.enumClass = enumClass;
		return this;
	}

}
