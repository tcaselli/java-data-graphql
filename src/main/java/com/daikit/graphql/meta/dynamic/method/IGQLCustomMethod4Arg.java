package com.daikit.graphql.meta.dynamic.method;

/**
 * Custom method that will be added to GQL Schema. Method has one argument.
 *
 * @author tcaselli
 * @param <OUTPUT_TYPE>
 *            the output type
 * @param <ARGUMENT_1_TYPE>
 *            first argument type
 * @param <ARGUMENT_2_TYPE>
 *            second argument type
 * @param <ARGUMENT_3_TYPE>
 *            third argument type
 * @param <ARGUMENT_4_TYPE>
 *            fourth argument type
 */
public interface IGQLCustomMethod4Arg<OUTPUT_TYPE, ARGUMENT_1_TYPE, ARGUMENT_2_TYPE, ARGUMENT_3_TYPE, ARGUMENT_4_TYPE>
		extends
			IGQLAbstractCustomMethod<OUTPUT_TYPE> {

	/**
	 * Apply method
	 *
	 * @param arg1
	 *            the first argument
	 * @param arg2
	 *            the second argument
	 * @param arg3
	 *            the third argument
	 * @param arg4
	 *            the fourth argument
	 * @return the output object
	 */
	OUTPUT_TYPE apply(ARGUMENT_1_TYPE arg1, ARGUMENT_2_TYPE arg2, ARGUMENT_3_TYPE arg3, ARGUMENT_4_TYPE arg4);

}
