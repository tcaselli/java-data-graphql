package com.daikit.graphql.data.output;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import graphql.ExecutionResult;
import graphql.GraphQLError;

/**
 * GRaphQL execution result data
 *
 * @author tcaselli
 * @version $Revision$ Last modifier: $Author$ Last commit: $Date$
 */
public class GQLExecutionResult implements ExecutionResult {

	private final GQLExecutionErrorDetails errorDetails;
	private final ExecutionResult wrappedExecutionResult;

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// CONSTRUCTORS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	/**
	 * Constructor for execution error
	 *
	 * @param errorDetails
	 *            the {@link GQLExecutionErrorDetails}
	 */
	public GQLExecutionResult(GQLExecutionErrorDetails errorDetails) {
		this(null, errorDetails);
	}

	/**
	 * Constructor
	 *
	 * @param wrappedExecutionResult
	 *            the wrappedExecutionResult {@link ExecutionResult}
	 * @param errorDetails
	 *            the {@link GQLExecutionErrorDetails}
	 */
	public GQLExecutionResult(ExecutionResult wrappedExecutionResult, GQLExecutionErrorDetails errorDetails) {
		this.wrappedExecutionResult = wrappedExecutionResult;
		this.errorDetails = errorDetails;
	}

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// PUBLIC METHODS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	/**
	 * @return the errorDetails
	 */
	public GQLExecutionErrorDetails getErrorDetails() {
		return errorDetails;
	}

	@Override
	public List<GraphQLError> getErrors() {
		return wrappedExecutionResult == null ? null : wrappedExecutionResult.getErrors();
	}

	@Override
	public <T> T getData() {
		return wrappedExecutionResult == null ? null : wrappedExecutionResult.getData();
	}

	@Override
	public boolean isDataPresent() {
		return wrappedExecutionResult == null ? false : wrappedExecutionResult.isDataPresent();
	}

	@Override
	public Map<Object, Object> getExtensions() {
		return wrappedExecutionResult == null ? Collections.emptyMap() : wrappedExecutionResult.getExtensions();
	}

	@Override
	public Map<String, Object> toSpecification() {
		return wrappedExecutionResult == null ? Collections.emptyMap() : wrappedExecutionResult.toSpecification();
	}

}
