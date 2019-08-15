package com.daikit.graphql.custommethod;

/**
 * Custom method that will be added to GQL Schema. Method has one argument.
 *
 * @author Thibaut Caselli
 * @param <OUTPUT_TYPE>
 *            the output type
 * @param <ARGUMENT_1_TYPE>
 *            first argument type
 * @param <ARGUMENT_2_TYPE>
 *            second argument type
 */
public interface IGQLCustomMethod2Arg<OUTPUT_TYPE, ARGUMENT_1_TYPE, ARGUMENT_2_TYPE>
		extends
			IGQLAbstractCustomMethod<OUTPUT_TYPE> {

	/**
	 * Apply method
	 *
	 * @param arg1
	 *            the first argument
	 * @param arg2
	 *            the second argument
	 * @return the output object
	 */
	OUTPUT_TYPE apply(ARGUMENT_1_TYPE arg1, ARGUMENT_2_TYPE arg2);

}
