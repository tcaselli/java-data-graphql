package com.daikit.graphql.meta.data.method;

import com.daikit.graphql.meta.dynamic.method.GQLAbstractCustomMethod;

/**
 * GraphQL dynamic method returning an embedded entity meta data
 *
 * @author tcaselli
 */
public class GQLMethodEntityMetaData extends GQLAbstractMethodMetaData {

	private Class<?> entityClass;

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// CONSTRUCTORS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	/**
	 * Default constructor
	 */
	public GQLMethodEntityMetaData() {
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
	 * @param entityClass
	 *            the entity class for method return type
	 */
	public GQLMethodEntityMetaData(String name, boolean mutation, GQLAbstractCustomMethod<?> method,
			Class<?> entityClass) {
		super(name, mutation, method);
		this.entityClass = entityClass;
	}

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// METHODS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	@Override
	protected void appendToString(final StringBuilder stringBuilder) {
		stringBuilder.append("{METHOD-ENTITY(").append(entityClass == null ? "" : entityClass.getSimpleName())
				.append(")}");
		super.appendToString(stringBuilder);
	}

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// GETTERS / SETTERS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	/**
	 * @return the entityClass
	 */
	public Class<?> getEntityClass() {
		return entityClass;
	}

	/**
	 * @param entityClass
	 *            the entityClass to set
	 */
	public void setEntityClass(final Class<?> entityClass) {
		this.entityClass = entityClass;
	}

}
