package com.daikit.graphql.meta.data.method;

import java.util.List;

import com.daikit.graphql.meta.dynamic.method.abs.GQLAbstractCustomMethod;

/**
 * GraphQL dynamic method returning a {@link List} of entities meta data
 *
 * @author tcaselli
 */
public class GQLMethodListEntityMetaData extends GQLAbstractMethodMetaData {

	private Class<?> foreignClass;

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// CONSTRUCTORS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	/**
	 * Default constructor
	 */
	public GQLMethodListEntityMetaData() {
		// Nothing done
	}

	/**
	 * Constructor passing name, whether this is a mutation or a query, method
	 * and return foreign entity type
	 *
	 * @param method
	 *            the {@link GQLAbstractCustomMethod}
	 * @param foreignClass
	 *            the foreign entity class for method return type
	 */
	public GQLMethodListEntityMetaData(GQLAbstractCustomMethod<?> method, Class<?> foreignClass) {
		super(method);
		this.foreignClass = foreignClass;
	}

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// METHODS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	@Override
	protected void appendToString(final StringBuilder stringBuilder) {
		stringBuilder.append("{METHOD-LIST-ENTITY(").append(foreignClass == null ? "" : foreignClass.getSimpleName())
				.append(")}");
		super.appendToString(stringBuilder);
	}

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// GETTERS / SETTERS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	/**
	 * Get the foreign entity method return type
	 *
	 * @return the foreignClass
	 */
	public Class<?> getForeignClass() {
		return foreignClass;
	}

	/**
	 * Set the foreign entity method return type
	 *
	 * @param foreignClass
	 *            the foreignClass to set
	 */
	public void setForeignClass(final Class<?> foreignClass) {
		this.foreignClass = foreignClass;
	}

}
