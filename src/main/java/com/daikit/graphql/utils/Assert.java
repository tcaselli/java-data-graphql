package com.daikit.graphql.utils;
import static java.lang.String.format;

import graphql.AssertException;

/**
 * Assert utility methods
 *
 * @author tcaselli
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
	 * @return the input object
	 */
	public static <T> T assertNotNull(T object, String format, Object... args) {
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
	 * @return the input object
	 */
	public static <T> T assertNotNullWithNPE(T object, String format, Object... args) {
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
	 * @return the input object
	 */
	public static <T> T assertNotNull(T object) {
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
	 */
	public static <T> void assertNull(T object, String format, Object... args) {
		if (object == null) {
			return;
		}
		throw new AssertException(format(format, args));
	}

	/**
	 * Assert null
	 *
	 * @param object
	 *            the object to be tested
	 */
	public static <T> void assertNull(T object) {
		if (object == null) {
			return;
		}
		throw new AssertException("Object required to be null");
	}

}
