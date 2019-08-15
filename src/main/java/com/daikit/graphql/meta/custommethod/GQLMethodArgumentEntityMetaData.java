package com.daikit.graphql.meta.custommethod;

/**
 * GraphQL dynamic method argument entity meta data
 *
 * @author tcaselli
 */
public class GQLMethodArgumentEntityMetaData extends GQLAbstractMethodArgumentMetaData {

	private Class<?> entityClass;

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// CONSTRUCTORS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	/**
	 * Default constructor
	 */
	public GQLMethodArgumentEntityMetaData() {
		// Nothing done
	}

	/**
	 * Constructor passing name and entity class and keeping default values for
	 * other attributes.
	 *
	 * @param name
	 *            the name for the method argument. This name will be used for
	 *            building GraphQL schema query or mutation for this method
	 * @param entityClass
	 *            the entity class
	 */
	public GQLMethodArgumentEntityMetaData(String name, Class<?> entityClass) {
		super(name);
		this.entityClass = entityClass;
	}

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// METHODS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	@Override
	protected void appendToString(final StringBuilder stringBuilder) {
		stringBuilder.append("{METHOD-ARGUMENT-ENTITY(").append(entityClass == null ? "" : entityClass.getSimpleName())
				.append(")}");
		super.appendToString(stringBuilder);
	}

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// GETTERS / SETTERS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	/**
	 * Get the entity class
	 *
	 * @return the entityClass
	 */
	public Class<?> getEntityClass() {
		return entityClass;
	}

	/**
	 * Set the entity class
	 *
	 * @param entityClass
	 *            the entityClass to set
	 */
	public void setEntityClass(final Class<?> entityClass) {
		this.entityClass = entityClass;
	}

}
