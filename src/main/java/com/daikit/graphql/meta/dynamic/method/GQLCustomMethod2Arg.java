package com.daikit.graphql.meta.dynamic.method;

import java.lang.reflect.Type;
import java.util.List;
import java.util.stream.Collectors;

import com.daikit.generics.utils.GenericsUtils;

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
 */
public interface GQLCustomMethod2Arg<OUTPUT_TYPE, ARGUMENT_1_TYPE, ARGUMENT_2_TYPE>
		extends
			GQLAbstractCustomMethod<OUTPUT_TYPE> {

	/**
	 * @return the first argument name for the method
	 */
	String getArg1Name();

	/**
	 * @return the second argument name for the method
	 */
	String getArg2Name();

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

	/**
	 * Get argument types from generic configuration of this class
	 *
	 * @return the output type
	 */
	default List<Type> getArgumentTypes() {
		return GenericsUtils.getTypeArguments(getClass(), GQLCustomMethod2Arg.class).stream().skip(1)
				.collect(Collectors.toList());
	}

}
