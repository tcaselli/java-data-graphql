package com.daikit.graphql.meta.custommethod;

import java.util.List;

import com.daikit.graphql.custommethod.GQLCustomMethod;

/**
 * GraphQL dynamic method returning a {@link List} of entities meta data
 *
 * @author Thibaut Caselli
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
	 *            the {@link GQLCustomMethod}
	 * @param foreignClass
	 *            the foreign entity class for method return type
	 */
	public GQLMethodListEntityMetaData(final GQLCustomMethod method, final Class<?> foreignClass) {
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
	 * @return this instance
	 */
	public GQLMethodListEntityMetaData setForeignClass(final Class<?> foreignClass) {
		this.foreignClass = foreignClass;
		return this;
	}

}
