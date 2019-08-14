package com.daikit.graphql.meta.dynamic.method.abs;

import java.lang.reflect.Type;
import java.util.List;
import java.util.stream.Collectors;

import com.daikit.generics.utils.GenericsUtils;
import com.daikit.graphql.meta.dynamic.method.IGQLCustomMethod5Arg;

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
 * @param <ARGUMENT_5_TYPE>
 *            fifth argument type
 */
public abstract class GQLCustomMethod5Arg<OUTPUT_TYPE, ARGUMENT_1_TYPE, ARGUMENT_2_TYPE, ARGUMENT_3_TYPE, ARGUMENT_4_TYPE, ARGUMENT_5_TYPE>
		extends
			GQLAbstractCustomMethod<OUTPUT_TYPE>
		implements
			IGQLCustomMethod5Arg<OUTPUT_TYPE, ARGUMENT_1_TYPE, ARGUMENT_2_TYPE, ARGUMENT_3_TYPE, ARGUMENT_4_TYPE, ARGUMENT_5_TYPE> {

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// CONSTRUCTORS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	/**
	 * Default constructor
	 */
	public GQLCustomMethod5Arg() {
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
	 * @param arg3Name
	 *            third argument name
	 * @param arg4Name
	 *            fourth argument name
	 * @param arg5Name
	 *            fifth argument name
	 */
	public GQLCustomMethod5Arg(String methodName, boolean mutation, String arg1Name, String arg2Name, String arg3Name,
			String arg4Name, String arg5Name) {
		super(methodName, mutation, arg1Name, arg2Name, arg3Name, arg4Name, arg5Name);
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
		return GenericsUtils.getTypeArguments(getClass(), GQLCustomMethod5Arg.class).stream().skip(1)
				.collect(Collectors.toList());
	}

}
