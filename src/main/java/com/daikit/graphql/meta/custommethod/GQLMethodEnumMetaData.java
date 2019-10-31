package com.daikit.graphql.meta.custommethod;

import com.daikit.graphql.custommethod.GQLCustomMethod;

/**
 * GraphQL dynamic method returning an entity meta data
 *
 * @author Thibaut Caselli
 */
public class GQLMethodEnumMetaData extends GQLAbstractMethodMetaData {

	private Class<? extends Enum<?>> enumClass;

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// CONSTRUCTORS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	/**
	 * Default constructor
	 */
	public GQLMethodEnumMetaData() {
		// Nothing done
	}

	/**
	 * Constructor passing name, whether this is a mutation or a query, method
	 * and return type
	 *
	 * @param method
	 *            the {@link GQLCustomMethod}
	 * @param enumClass
	 *            the enumeration class for method return type
	 */
	public GQLMethodEnumMetaData(final GQLCustomMethod method, final Class<? extends Enum<?>> enumClass) {
		super(method);
		this.enumClass = enumClass;
	}

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// METHODS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	@Override
	protected void appendToString(final StringBuilder stringBuilder) {
		stringBuilder.append("{METHOD-ENUM(").append(enumClass == null ? "" : enumClass.getSimpleName()).append(")}");
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
	public Class<? extends Enum<?>> getEnumClass() {
		return enumClass;
	}

	/**
	 * Set the method enumeration return type
	 *
	 * @param enumClass
	 *            the enumClass to set
	 * @return this instance
	 */
	public GQLMethodEnumMetaData setEnumClass(final Class<? extends Enum<?>> enumClass) {
		this.enumClass = enumClass;
		return this;
	}

}
