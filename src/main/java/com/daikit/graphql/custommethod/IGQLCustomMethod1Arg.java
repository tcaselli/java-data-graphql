package com.daikit.graphql.custommethod;

/**
 * Custom method that will be added to GQL Schema. Method has one argument.
 *
 * @author tcaselli
 * @param <OUTPUT_TYPE>
 *            the output type
 * @param <ARGUMENT_1_TYPE>
 *            first argument type
 */
public interface IGQLCustomMethod1Arg<OUTPUT_TYPE, ARGUMENT_1_TYPE> extends IGQLAbstractCustomMethod<OUTPUT_TYPE> {

	/**
	 * Apply method
	 *
	 * @param arg1
	 *            the first argument
	 * @return the output object
	 */
	OUTPUT_TYPE apply(ARGUMENT_1_TYPE arg1);

}
