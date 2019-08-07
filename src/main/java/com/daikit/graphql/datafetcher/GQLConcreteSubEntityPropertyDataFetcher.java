package com.daikit.graphql.datafetcher;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;

/**
 * Concrete sub entity data fetcher for a property referencing its abstract
 * super class
 * 
 * @author tcaselli
 */
public class GQLConcreteSubEntityPropertyDataFetcher implements DataFetcher<Object> {

	private final Class<?> entityType;

	/**
	 * Constructor
	 *
	 * @param entityType
	 *            the entity tyê
	 */
	public GQLConcreteSubEntityPropertyDataFetcher(final Class<?> entityType) {
		this.entityType = entityType;
	}

	@Override
	public Object get(final DataFetchingEnvironment environment) {
		// The concrete entity
		final Object source = environment.getSource();
		// We should return source object only if its type is
		return entityType.equals(source.getClass()) ? source : null;
	}

}
