package com.daikit.graphql.utils;

import org.slf4j.helpers.MessageFormatter;

/**
 * Message utility class
 *
 * @author tcaselli
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
	public static String format(String message, Object... args) {
		return MessageFormatter.arrayFormat(message, args).getMessage();
	}

}
