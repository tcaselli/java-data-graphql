package com.daikit.graphql.data.output;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * GraphQL execution result error details
 *
 * @author tcaselli
 * @version $Revision$ Last modifier: $Author$ Last commit: $Date$
 */
public class GQLExecutionErrorDetails {

	private String message;
	private String type;

	@JsonIgnore
	private transient Throwable wrappedException;

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// GETTERS / SETTERS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message
	 *            the message to set
	 */
	public void setMessage(final String message) {
		this.message = message;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(final String type) {
		this.type = type;
	}

	/**
	 * @return the wrappedException
	 */
	public Throwable getWrappedException() {
		return wrappedException;
	}

	/**
	 * @param wrappedException
	 *            the wrappedException to set
	 */
	public void setWrappedException(final Throwable wrappedException) {
		this.wrappedException = wrappedException;
	}

}
