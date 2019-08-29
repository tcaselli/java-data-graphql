package com.daikit.graphql.datafetcher;

import com.daikit.graphql.builder.GQLSchemaBuilder;
import com.daikit.graphql.constants.GQLSchemaConstants;

import graphql.language.Field;
import graphql.schema.DataFetchingEnvironment;

/**
 * Abstract super class that may be overridden to provide "getById" data fetcher
 * by ID to the schema building. This class is typically to be extended and used
 * in {@link GQLSchemaBuilder} for buildSchema "getById" method data fetcher
 * argument
 *
 * @author Thibaut Caselli
 *
 */
public abstract class GQLAbstractGetByIdDataFetcher extends GQLAbstractDataFetcher<Object> {

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// ABSTRACT METHODS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	protected abstract Object getById(Class<?> entityClass, String id);

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// METHODS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	@Override
	public Object get(final DataFetchingEnvironment environment) {
		final Field queryField = environment.getField();
		final String entityName = getEntityName(GQLSchemaConstants.QUERY_GET_SINGLE_PREFIX, queryField.getName());
		final String id = mapValue(
				queryField.getArguments().stream()
						.filter(argument -> GQLSchemaConstants.FIELD_ID.equals(argument.getName())).findFirst().get(),
				environment.getArguments());
		return getById(getEntityClassByEntityName(entityName), id);
	}

}
