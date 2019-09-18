package com.daikit.graphql.execution;

import java.util.List;

import com.daikit.graphql.data.output.GQLExecutionErrorDetails;

import graphql.ExceptionWhileDataFetching;
import graphql.GraphQLError;

/**
 * Default implementation of {@link IGQLErrorProcessor}. This class is intended
 * to be overridden to provide custom error handling.
 *
 * @author Thibaut Caselli
 */
public class GQLErrorProcessor implements IGQLErrorProcessor {

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// PUBLIC METHODS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	@Override
	public GQLExecutionErrorDetails handleError(final List<GraphQLError> errors) {
		GQLExecutionErrorDetails error = null;
		if (errors != null) {
			if (errors.size() == 1 && errors.get(0) instanceof ExceptionWhileDataFetching) {
				error = handleError(((ExceptionWhileDataFetching) errors.get(0)).getException());
			} else if (!errors.isEmpty()) {
				error = createError();
				error.setType("GQLClientError");
				error.setMessage((errors.size() <= 1 ? "An error" : "Multiple errors")
						+ " happened while processing client request");
			}
		}
		return error;
	}

	@Override
	public GQLExecutionErrorDetails handleError(final Throwable exception) {
		final GQLExecutionErrorDetails error = createError();
		error.setMessage(exception.getMessage());
		error.setType(exception.getClass().getSimpleName());
		error.setWrappedException(exception);
		return error;
	}

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// PROTECTED METHODS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	/**
	 * Override to provide custom {@link GQLExecutionErrorDetails} extension
	 *
	 * @return a {@link GQLExecutionErrorDetails}
	 */
	protected GQLExecutionErrorDetails createError() {
		return new GQLExecutionErrorDetails();
	}

}
