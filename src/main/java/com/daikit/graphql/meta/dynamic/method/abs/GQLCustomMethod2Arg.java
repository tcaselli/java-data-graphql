package com.daikit.graphql.meta.dynamic.method.abs;

import java.lang.reflect.Type;
import java.util.List;
import java.util.stream.Collectors;

import com.daikit.generics.utils.GenericsUtils;
import com.daikit.graphql.meta.dynamic.method.IGQLCustomMethod2Arg;

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
public abstract class GQLCustomMethod2Arg<OUTPUT_TYPE, ARGUMENT_1_TYPE, ARGUMENT_2_TYPE>
		extends
			GQLAbstractCustomMethod<OUTPUT_TYPE>
		implements
			IGQLCustomMethod2Arg<OUTPUT_TYPE, ARGUMENT_1_TYPE, ARGUMENT_2_TYPE> {

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// CONSTRUCTORS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	/**
	 * Default constructor
	 */
	public GQLCustomMethod2Arg() {
		// Nothing done
	}

	/**
	 * Constructor
	 *
	 * @param methodName
	 *            the custom method name
	 * @param mutation
	 *            whether this method is a mutation (return true) or a query
	 *            (return false)
	 * @param arg1Name
	 *            first argument name
	 * @param arg2Name
	 *            second argument name
	 */
	public GQLCustomMethod2Arg(String methodName, boolean mutation, String arg1Name, String arg2Name) {
		super(methodName, mutation, arg1Name, arg2Name);
	}

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// PUBLIC METHODS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	/**
	 * Get argument types from generic configuration of this class
	 *
	 * @return the output type
	 */
	@Override
	public List<Type> getArgumentTypes() {
		return GenericsUtils.getTypeArguments(getClass(), GQLCustomMethod2Arg.class).stream().skip(1)
				.collect(Collectors.toList());
	}

}
