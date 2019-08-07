package com.daikit.graphql.meta.dynamic.method;

import java.lang.reflect.Type;

import com.daikit.generics.utils.GenericsUtils;

/**
 * Custom method that will be added to GQL Schema. Method has no argument.
 *
 * @author tcaselli
 * @param <OUTPUT_TYPE>
 *            the output type
 */
public interface GQLAbstractCustomMethod<OUTPUT_TYPE> {

	/**
	 * @return the method name that will be available in GraphQL schema
	 */
	String getMethodName();

	/**
	 * Get whether this method is a mutation (return true) or a query (return
	 * false)
	 *
	 * @return a boolean
	 */
	boolean isMutation();

	/**
	 * Get output type from generic configuration of this class
	 *
	 * @return the output type
	 */
	default Type getOutputType() {
		return GenericsUtils.getTypeArguments(getClass(), GQLAbstractCustomMethod.class).get(0);
	}

	/**
	 * Prefix to be prepended to ID parameter names
	 */
	String ID_PREFIX = "ID#";

}
