package com.daikit.graphql.introspection;

import java.util.function.Consumer;
import java.util.function.Function;

import graphql.ExecutionResult;
import graphql.introspection.IntrospectionQuery;

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
		return executor.apply(IntrospectionQuery.INTROSPECTION_QUERY);
	}

	/**
	 * Run get all types introspection query
	 *
	 * @param executor
	 *            the executor {@link Consumer}
	 * @return an {@link ExecutionResult}
	 */
	public static <X> X getFragments(final Function<String, X> executor) {
		return executor.apply(GQLIntrospectionFragmentsQuery.INTROSPECTION_FRAGMENTS_QUERY);
	}

}
