package com.daikit.graphql.utils;

import org.slf4j.helpers.MessageFormatter;

/**
 * Message utility class
 *
 * @author Thibaut Caselli
 */
public class Message {

	/**
	 * Utility shortcut class for formatting a string with arguments
	 *
	 * @param message
	 *            the message
	 * @param args
	 *            the arguments
	 * @return the formatted message with injected arguments
	 */
	public static String format(final String message, final Object... args) {
		return MessageFormatter.arrayFormat(message, args).getMessage();
	}

}
