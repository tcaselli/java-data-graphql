package com.daikit.graphql.introspection;

import java.util.function.Consumer;
import java.util.function.Function;

import graphql.ExecutionResult;
import graphql.introspection.IntrospectionQuery;

/**
 * Introspection methods
 *
 * @author Thibaut Caselli
 */
public class GQLIntrospection {

	/**
	 * Run get all types introspection query
	 *
	 * @param executor
	 *            the executor {@link Consumer}
	 * @param <T>
	 *            the return type
	 * @return an {@link ExecutionResult}
	 */
	public static <T> T getAllTypes(final Function<String, T> executor) {
		return executor.apply(IntrospectionQuery.INTROSPECTION_QUERY);
	}

	/**
	 * Run get all types introspection query
	 *
	 * @param executor
	 *            the executor {@link Consumer}
	 * @param <T>
	 *            the return type
	 * @return an {@link ExecutionResult}
	 */
	public static <T> T getFragments(final Function<String, T> executor) {
		return executor.apply(GQLIntrospectionFragmentsQuery.INTROSPECTION_FRAGMENTS_QUERY);
	}

}
