package com.daikit.graphql.meta.data.method;

import java.util.List;

import com.daikit.graphql.meta.dynamic.method.GQLAbstractCustomMethod;

/**
 * GraphQL dynamic method returning a {@link List} of embedded entities meta
 * data
 *
 * @author tcaselli
 */
public class GQLMethodListEmbeddedEntityMetaData extends GQLAbstractMethodMetaData {

	private Class<?> foreignClass;

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// CONSTRUCTORS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	/**
	 * Default constructor
	 */
	public GQLMethodListEmbeddedEntityMetaData() {
		// Nothing done
	}

	/**
	 * Constructor passing name, whether this is a mutation or a query, method
	 * and return foreign embedded entity type
	 *
	 * @param name
	 *            the name for the method. This name will be used for building
	 *            GraphQL schema query or mutation for this method
	 * @param mutation
	 *            whether this is a mutation (<code>true</code>) or a query
	 *            (<code>false</code>)
	 * @param method
	 *            the {@link GQLAbstractCustomMethod}
	 * @param foreignClass
	 *            the foreign embedded entity class for method return type
	 */
	public GQLMethodListEmbeddedEntityMetaData(String name, boolean mutation, GQLAbstractCustomMethod<?> method,
			Class<?> foreignClass) {
		super(name, mutation, method);
		this.foreignClass = foreignClass;
	}

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// METHODS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	@Override
	protected void appendToString(final StringBuilder stringBuilder) {
		stringBuilder.append("{METHOD-LIST-ENTITY-EMBEDDED(")
				.append(foreignClass == null ? "" : foreignClass.getSimpleName()).append(")}");
		super.appendToString(stringBuilder);
	}

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// GETTERS / SETTERS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	/**
	 * Get the foreign embedded entity method return type
	 *
	 * @return the foreignClass
	 */
	public Class<?> getForeignClass() {
		return foreignClass;
	}

	/**
	 * Set the foreign embedded entity method return type
	 *
	 * @param foreignClass
	 *            the foreignClass to set
	 */
	public void setForeignClass(final Class<?> foreignClass) {
		this.foreignClass = foreignClass;
	}

}
