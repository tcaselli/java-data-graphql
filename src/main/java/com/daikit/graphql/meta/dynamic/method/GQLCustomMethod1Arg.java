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
 */
public interface GQLCustomMethod1Arg<OUTPUT_TYPE, ARGUMENT_1_TYPE> extends GQLAbstractCustomMethod<OUTPUT_TYPE> {

	/**
	 * @return the first argument name for the method
	 */
	String getArg1Name();

	/**
	 * Apply method
	 *
	 * @param arg1
	 *            the first argument
	 * @return the output object
	 */
	OUTPUT_TYPE apply(ARGUMENT_1_TYPE arg1);

	/**
	 * Get argument types from generic configuration of this class
	 *
	 * @return the output type
	 */
	default List<Type> getArgumentTypes() {
		return GenericsUtils.getTypeArguments(getClass(), GQLCustomMethod1Arg.class).stream().skip(1)
				.collect(Collectors.toList());
	}

}
