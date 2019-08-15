package com.daikit.graphql.custommethod.abs;

import java.lang.reflect.Type;
import java.util.List;
import java.util.stream.Collectors;

import com.daikit.generics.utils.GenericsUtils;
import com.daikit.graphql.custommethod.IGQLCustomMethod1Arg;

/**
 * Custom method that will be added to GQL Schema. Method has one argument.
 *
 * @author tcaselli
 * @param <OUTPUT_TYPE>
 *            the output type
 * @param <ARGUMENT_1_TYPE>
 *            first argument type
 */
public abstract class GQLCustomMethod1Arg<OUTPUT_TYPE, ARGUMENT_1_TYPE> extends GQLAbstractCustomMethod<OUTPUT_TYPE>
		implements
			IGQLCustomMethod1Arg<OUTPUT_TYPE, ARGUMENT_1_TYPE> {

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// CONSTRUCTORS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	/**
	 * Default constructor
	 */
	public GQLCustomMethod1Arg() {
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
	 * @param argName
	 *            argument name
	 */
	public GQLCustomMethod1Arg(String methodName, boolean mutation, String argName) {
		super(methodName, mutation, argName);
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
		return GenericsUtils.getTypeArguments(getClass(), GQLCustomMethod1Arg.class).stream().skip(1)
				.collect(Collectors.toList());
	}

}
