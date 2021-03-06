package com.daikit.graphql.utils;
import static java.lang.String.format;

import graphql.AssertException;

/**
 * Assert utility methods
 *
 * @author Thibaut Caselli
 */
public class Assert {

	/**
	 * Assert not null
	 *
	 * @param object
	 *            the object to be tested
	 * @param format
	 *            the message in case of error
	 * @param args
	 *            arguments for the message
	 * @param <T>
	 *            the object type
	 * @return the input object
	 */
	public static <T> T assertNotNull(final T object, final String format, final Object... args) {
		if (object != null) {
			return object;
		}
		throw new AssertException(format(format, args));
	}

	/**
	 * Assert not null, sending a {@link NullPointerException} if object is null
	 *
	 * @param object
	 *            the object to be tested
	 * @param format
	 *            the message in case of error
	 * @param args
	 *            arguments for the message
	 * @param <T>
	 *            the object type
	 * @return the input object
	 */
	public static <T> T assertNotNullWithNPE(final T object, final String format, final Object... args) {
		if (object != null) {
			return object;
		}
		throw new NullPointerException(format(format, args));
	}

	/**
	 * Assert not null
	 *
	 * @param object
	 *            the object to be tested
	 * @param <T>
	 *            the object type
	 * @return the input object
	 */
	public static <T> T assertNotNull(final T object) {
		if (object != null) {
			return object;
		}
		throw new AssertException("Object required to be not null");
	}

	/**
	 * Assert null
	 *
	 * @param object
	 *            the object to be tested
	 * @param format
	 *            the message in case of error
	 * @param args
	 *            arguments for the message
	 * @param <T>
	 *            the object type
	 */
	public static <T> void assertNull(final T object, final String format, final Object... args) {
		if (object == null) {
			return;
		}
		throw new AssertException(format(format, args));
	}

	/**
	 * Assert given condition is true
	 *
	 * @param condition
	 *            the condition to be tested
	 * @param format
	 *            the message in case of error
	 * @param args
	 *            arguments for the message
	 */
	public static void assertTrue(final boolean condition, final String format, final Object... args) {
		if (condition) {
			return;
		}
		throw new AssertException(format(format, args));
	}

}
