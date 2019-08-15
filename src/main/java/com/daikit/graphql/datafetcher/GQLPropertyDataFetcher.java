package com.daikit.graphql.datafetcher;

import graphql.schema.PropertyDataFetcher;

/**
 * GraphQL property data fetcher
 *
 * @author Thibaut Caselli
 * @param <PROPERTY_TYPE>
 *            the property type
 */
public class GQLPropertyDataFetcher<PROPERTY_TYPE> extends PropertyDataFetcher<PROPERTY_TYPE> {

	private final Class<?> entityClass;
	private final String entityPropertyName;
	private final String graphQLPropertyName;

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// CONSTRUCTORS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	/**
	 * Build a {@link PropertyDataFetcher} for given Entity class and property
	 * with <code>propertyName</code> name within this entity
	 *
	 * @param entityClass
	 *            the entity class
	 * @param entityPropertyName
	 *            the property name within the entity
	 * @param graphQLPropertyName
	 *            the property name for GraphQL
	 */
	public GQLPropertyDataFetcher(final Class<?> entityClass, final String entityPropertyName,
			final String graphQLPropertyName) {
		super(entityPropertyName);
		this.entityClass = entityClass;
		this.entityPropertyName = entityPropertyName;
		this.graphQLPropertyName = graphQLPropertyName;
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
	 * @return the entityPropertyName
	 */
	public String getEntityPropertyName() {
		return entityPropertyName;
	}

	/**
	 * @return the graphQLPropertyName
	 */
	public String getGraphQLPropertyName() {
		return graphQLPropertyName;
	}

}
