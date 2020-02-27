package com.daikit.graphql.execution;

import com.daikit.graphql.builder.GQLExecutionContext;
import com.daikit.graphql.data.output.GQLExecutionResult;

import graphql.ExecutionInput;

/**
 * Callback that may be registered on {@link GQLExecutor} to provide hooks
 * before and after a query/mutation execution
 *
 * @author Thibaut Caselli
 */
public interface IGQLExecutorCallback {

	/**
	 * Callback before execution
	 *
	 * @param executionContext
	 *            the {@link GQLExecutionContext}
	 * @param executionInput
	 *            the {@link ExecutionInput}
	 */
	default void onBeforeExecute(final GQLExecutionContext executionContext, final ExecutionInput executionInput) {
		// Nothing done by default
	}

	/**
	 * Callback after execution
	 *
	 * @param executionContext
	 *            the {@link GQLExecutionContext}
	 * @param executionInput
	 *            the {@link ExecutionInput}
	 * @param executionResult
	 *            the {@link GQLExecutionResult}
	 */
	default void onAfterExecute(final GQLExecutionContext executionContext, final ExecutionInput executionInput,
			final GQLExecutionResult executionResult) {
		// Nothing done by default
	}

}
