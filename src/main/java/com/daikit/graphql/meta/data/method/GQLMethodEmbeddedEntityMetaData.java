package com.daikit.graphql.meta.data.method;

import com.daikit.graphql.meta.dynamic.method.abs.GQLAbstractCustomMethod;

/**
 * GraphQL dynamic method returning an entity meta data
 *
 * @author tcaselli
 */
public class GQLMethodEmbeddedEntityMetaData extends GQLAbstractMethodMetaData {

	private Class<?> entityClass;

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// CONSTRUCTORS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	/**
	 * Default constructor
	 */
	public GQLMethodEmbeddedEntityMetaData() {
		// Nothing done
	}

	/**
	 * Constructor passing name, whether this is a mutation or a query, method
	 * and return type
	 *
	 * @param method
	 *            the {@link GQLAbstractCustomMethod}
	 * @param entityClass
	 *            the embedded entity class for method return type
	 */
	public GQLMethodEmbeddedEntityMetaData(GQLAbstractCustomMethod<?> method, Class<?> entityClass) {
		super(method);
		this.entityClass = entityClass;
	}

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// METHODS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	@Override
	protected void appendToString(final StringBuilder stringBuilder) {
		stringBuilder.append("{METHOD-ENTITY-EMBEDDED(").append(entityClass == null ? "" : entityClass.getSimpleName())
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
