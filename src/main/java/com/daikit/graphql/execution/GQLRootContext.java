package com.daikit.graphql.execution;

import com.daikit.graphql.builder.GQLExecutionContext;

/**
 * Root context that will wrap custom context given at execution time and execution context used for access rights
 */
public class GQLRootContext {

	private GQLExecutionContext executionContext;
	private Object customContext;

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// CONSTRUCTORS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	/**
	 * Constructor
	 *
	 * @param executionContext the {@link GQLExecutionContext}
	 * @param customContext    the custom context given at execution time for storing execution related properties
	 */
	public GQLRootContext(final GQLExecutionContext executionContext, final Object customContext) {
		this.executionContext = executionContext;
		this.customContext = customContext;
	}

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// GETTERS & SETTERS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	/**
	 * @return the executionContext
	 */
	public GQLExecutionContext getExecutionContext() {
		return executionContext;
	}

	/**
	 * @param executionContext the executionContext to set
	 */
	public void setExecutionContext(final GQLExecutionContext executionContext) {
		this.executionContext = executionContext;
	}

	/**
	 * @return the customContext
	 */
	public Object getCustomContext() {
		return customContext;
	}

	/**
	 * @param customContext the customContext to set
	 */
	public void setCustomContext(final Object customContext) {
		this.customContext = customContext;
	}
}
