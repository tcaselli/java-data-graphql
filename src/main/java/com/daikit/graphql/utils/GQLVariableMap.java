package com.daikit.graphql.utils;

import java.util.HashMap;

import com.daikit.graphql.custommethod.IGQLAbstractCustomMethod;

/**
 * Utility class for for queries and mutations variables map creation
 *
 * @author Thibaut Caselli
 */
public class GQLVariableMap extends HashMap<String, Object> {

	private static final long serialVersionUID = -8783742614168429688L;

	/**
	 * Constructor
	 *
	 * @param method
	 *            the {@link IGQLAbstractCustomMethod}
	 * @param args
	 *            the method arguments
	 */
	public GQLVariableMap(final IGQLAbstractCustomMethod<?> method, final Object... args) {
		for (int i = 0; i < method.getArgumentNames().size(); i++) {
			final Object value = args[i];

			put(method.getArgumentNames().get(i), value);
		}
	}
}
