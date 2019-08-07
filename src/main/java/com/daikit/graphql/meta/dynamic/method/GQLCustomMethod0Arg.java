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
 */
public interface GQLCustomMethod0Arg<OUTPUT_TYPE> extends GQLAbstractCustomMethod<OUTPUT_TYPE> {

	/**
	 * Apply method
	 *
	 * @return the output object
	 */
	OUTPUT_TYPE apply();

	/**
	 * Get argument types from generic configuration of this class
	 *
	 * @return the output type
	 */
	default List<Type> getArgumentTypes() {
		return GenericsUtils.getTypeArguments(getClass(), GQLCustomMethod0Arg.class).stream().skip(1)
				.collect(Collectors.toList());
	}

}
