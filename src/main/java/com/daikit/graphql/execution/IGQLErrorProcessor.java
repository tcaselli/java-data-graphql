package com.daikit.graphql.execution;

import java.util.List;

import com.daikit.graphql.data.output.GQLExecutionErrorDetails;

import graphql.GraphQLError;

/**
 * Error processor for GraphQL requests
 *
 * @author Thibaut Caselli
 */
public interface IGQLErrorProcessor {

	/**
	 * Create {@link GQLExecutionErrorDetails} from given details list
	 *
	 * @param errors
	 *            the error details to populate error from
	 * @return the created {@link GQLExecutionErrorDetails}
	 */
	GQLExecutionErrorDetails handleError(List<GraphQLError> errors);

	/**
	 * Create {@link GQLExecutionErrorDetails} after catching given exception
	 *
	 * @param exception
	 *            the exception to populate error from
	 * @return the created {@link GQLExecutionErrorDetails}
	 */
	GQLExecutionErrorDetails handleError(Throwable exception);

}
