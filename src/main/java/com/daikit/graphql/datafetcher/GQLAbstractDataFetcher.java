package com.daikit.graphql.datafetcher;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import graphql.language.Argument;
import graphql.language.Field;
import graphql.language.ObjectField;
import graphql.language.ObjectValue;
import graphql.schema.DataFetcher;

/**
 * Abstract super class for all data fetchers
 *
 * @author tcaselli
 * @param <FETCHED_DATA_TYPE>
 *            the fetched data type
 */
public abstract class GQLAbstractDataFetcher<FETCHED_DATA_TYPE> implements DataFetcher<FETCHED_DATA_TYPE> {

	/**
	 * Get provided variable names for the request
	 *
	 * @return a {@link Collection} of variable names
	 */
	protected Collection<String> getRequestProvidedVariableNames() {
		return Collections.emptyList();
	}

	/**
	 * Get entity by name
	 *
	 * @param prefix
	 *            the entity prefix
	 * @param queryName
	 *            the query name
	 * @return the entity name
	 */
	protected String getEntityName(final String prefix, final String queryName) {
		return GQLDataFetcherUtils.getEntityName(prefix, queryName);
	}

	/**
	 * Map GQL object field value to an object
	 *
	 * @param field
	 *            the GQL {@link ObjectField}
	 * @param arguments
	 *            the map of query arguments for potential replacements
	 * @return a mapped object value
	 */
	protected <X> X mapValue(final ObjectField field, final Map<String, Object> arguments) {
		return GQLDataFetcherUtils.mapValue(field, arguments, getRequestProvidedVariableNames());
	}

	/**
	 * Map GQL argument value to an object
	 *
	 * @param argument
	 *            the GQL {@link Argument}
	 * @param arguments
	 *            the map of query arguments for potential replacements
	 * @return a mapped object value
	 */
	protected <X> X mapValue(final Argument argument, final Map<String, Object> arguments) {
		return GQLDataFetcherUtils.mapValue(argument, arguments, getRequestProvidedVariableNames());
	}

	/**
	 * Get arguments with given property name within parent's arguments map
	 *
	 * @param arguments
	 *            the parent's arguments map
	 * @param argumentContext
	 *            the argument context name
	 * @return a {@link Map} (may be empty)
	 */
	protected Map<String, Object> getArgumentsForContext(final Map<String, Object> arguments,
			final String argumentContext) {
		return GQLDataFetcherUtils.getArgumentsForContext(arguments, argumentContext);
	}

	/**
	 * Get arguments with given property name within parent's arguments map as a
	 * {@link List}
	 *
	 * @param arguments
	 *            the parent's arguments map
	 * @param argumentContext
	 *            the argument context name
	 * @return a {@link List} of {@link Map} (may be empty)
	 */
	protected List<Map<String, Object>> getArgumentsForContextAsList(final Map<String, Object> arguments,
			final String argumentContext) {
		return GQLDataFetcherUtils.getArgumentsForContextAsList(arguments, argumentContext);
	}

	/**
	 * Convert GQL object value to a {@link Map}
	 *
	 * @param objectValue
	 *            the {@link ObjectValue}
	 * @param arguments
	 *            the {@link Map} of arguments
	 * @return the converted map
	 */
	protected Map<String, Object> convertObjectValue(final ObjectValue objectValue,
			final Map<String, Object> arguments) {
		return GQLDataFetcherUtils.convertObjectValue(objectValue, arguments, getRequestProvidedVariableNames());
	}

	/**
	 * Get argument by name within given query field
	 *
	 * @param queryField
	 *            the query {@link Field}
	 * @param name
	 *            then argument name
	 * @param arguments
	 *            the {@link Map} of arguments
	 * @return the argument value
	 */
	protected Object getArgumentValue(final Field queryField, final String name, final Map<String, Object> arguments) {
		return GQLDataFetcherUtils.getArgumentValue(queryField, name, arguments, getRequestProvidedVariableNames());
	}

}
