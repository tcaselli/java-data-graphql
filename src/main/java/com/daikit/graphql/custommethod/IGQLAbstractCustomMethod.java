package com.daikit.graphql.custommethod;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Abstract super class for custom method that will be added to GQL Schema.
 *
 * @author Thibaut Caselli
 * @param <OUTPUT_TYPE>
 *            the output type
 */
public interface IGQLAbstractCustomMethod<OUTPUT_TYPE> {

	/**
	 * Get output type from generic configuration of this class
	 *
	 * @return the output type
	 */
	Type getOutputType();

	/**
	 * Get the method name that will be available in GraphQL schema
	 *
	 * @return the method name
	 */
	String getMethodName();

	/**
	 * Get whether this method is a mutation (return true) or a query (return
	 * false). Default value is <code>true</code> (mutation).
	 *
	 * @return a boolean
	 */
	boolean isMutation();

	/**
	 * Get method argument names
	 *
	 * @return the argNames
	 */
	List<String> getArgumentNames();

	/**
	 * Get argument name at given position
	 *
	 * @param argumentPosition
	 *            the argument position (0 if first argument of the method, 1
	 * @return the argument name
	 */
	String getArgumentName(int argumentPosition);

	/**
	 * Get argument types from generic configuration of this class
	 *
	 * @return the output type
	 */
	List<Type> getArgumentTypes();

}
