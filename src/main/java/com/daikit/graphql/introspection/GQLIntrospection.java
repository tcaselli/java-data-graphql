package com.daikit.graphql.introspection;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.function.Consumer;
import java.util.function.Function;

import org.apache.commons.io.IOUtils;

import com.daikit.graphql.exception.GQLException;

import graphql.ExecutionResult;

/**
 * Introspection methods
 *
 * @author tcaselli
 */
public class GQLIntrospection {

	/**
	 * Run get all types introspection query
	 *
	 * @param executor
	 *            the executor {@link Consumer}
	 * @return an {@link ExecutionResult}
	 */
	public static <X> X getAllTypes(final Function<String, X> executor) {
		try {
			final String query = IOUtils.toString(
					GQLIntrospection.class.getResourceAsStream("queryGetAllTypes.graphql"), Charset.forName("UTF-8"));
			return executor.apply(query);
		} catch (final IOException e) {
			throw new GQLException("Unable to retrieve query to get all types.", e);
		}
	}

	/**
	 * Run get all types introspection query
	 *
	 * @param executor
	 *            the executor {@link Consumer}
	 * @return an {@link ExecutionResult}
	 */
	public static <X> X getFragments(final Function<String, X> executor) {
		try {
			final String query = IOUtils.toString(
					GQLIntrospection.class.getResourceAsStream("queryGetFragments.graphql"), Charset.forName("UTF-8"));
			return executor.apply(query);
		} catch (final IOException e) {
			throw new GQLException("Unable to retrieve query to get fragments.", e);
		}
	}

}
