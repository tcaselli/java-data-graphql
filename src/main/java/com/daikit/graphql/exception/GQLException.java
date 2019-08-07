package com.daikit.graphql.exception;

/**
 * Exception that are thrown from GraphQL module.
 *
 * @author tcaselli
 */
public class GQLException extends RuntimeException {

	private static final long serialVersionUID = -8369861339925202855L;

	/**
	 * Default Constructor. This one should not be used but is here for
	 * serialization purpose.
	 */
	@Deprecated
	public GQLException() {
		super();
	}

	/**
	 * Constructs a new {@link GQLException} with given message.
	 *
	 * @param message
	 *            the message for the exception
	 */
	public GQLException(final String message) {
		super(message);
	}

	/**
	 * Constructs a new {@link GQLException} with given message.
	 *
	 * @param message
	 *            the exception message
	 * @param cause
	 *            the cause (which is saved for later retrieval by the
	 *            {@link #getCause()} method). (A <tt>null</tt> value is
	 *            permitted, and indicates that the cause is nonexistent or
	 *            unknown.)
	 */
	public GQLException(final String message, final Throwable cause) {
		super(message, cause);
	}
}
