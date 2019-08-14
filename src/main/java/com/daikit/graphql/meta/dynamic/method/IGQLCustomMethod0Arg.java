package com.daikit.graphql.meta.dynamic.method;

/**
 * Custom method that will be added to GQL Schema. Method has one argument.
 *
 * @author tcaselli
 * @param <OUTPUT_TYPE>
 *            the output type
 */
public interface IGQLCustomMethod0Arg<OUTPUT_TYPE> extends IGQLAbstractCustomMethod<OUTPUT_TYPE> {

	/**
	 * Apply method
	 *
	 * @return the output object
	 */
	OUTPUT_TYPE apply();

}
