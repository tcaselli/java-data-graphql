package com.daikit.graphql.datafetcher;

import java.lang.reflect.Type;
import java.util.Map;

import com.daikit.graphql.constants.GQLUtils;
import com.daikit.graphql.meta.dynamic.method.GQLAbstractCustomMethod;
import com.daikit.graphql.meta.dynamic.method.GQLCustomMethod0Arg;
import com.daikit.graphql.meta.dynamic.method.GQLCustomMethod1Arg;
import com.daikit.graphql.meta.dynamic.method.GQLCustomMethod2Arg;
import com.daikit.graphql.meta.dynamic.method.GQLCustomMethod3Arg;
import com.daikit.graphql.meta.dynamic.method.GQLCustomMethod4Arg;
import com.daikit.graphql.meta.dynamic.method.GQLCustomMethod5Arg;
import com.daikit.graphql.utils.Message;

import graphql.language.Field;
import graphql.schema.DataFetchingEnvironment;

/**
 * Abstract super class for dynamic method data fetchers.
 *
 * @author tcaselli
 */
public abstract class GQLMethodDataFetcher extends GQLAbstractDataFetcher<Object> {

	/**
	 * Convert argument from query conversion to the actual type of the
	 * argument.
	 * <ul>
	 * <li>For simple argument types (with no nested fields) , this method
	 * should simply return the input</li>
	 * <li>For complex object types, a mapping should be provided. In this case
	 * the argument object will be a {@link Map} with key = property name, value
	 * = Object (simple object type or Map)</li>
	 * </ul>
	 *
	 * @param argument
	 *            the input argument
	 * @param argumentExpectedType
	 *            the argument expected type class
	 * @return the mapped argument
	 */
	protected abstract Object mapArgument(Object argument, Type argumentExpectedType);

	private final GQLAbstractCustomMethod<?> method;

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// CONSTRUCTORS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	/**
	 * Constructor
	 *
	 * @param method
	 *            the method {@link GQLAbstractCustomMethod}
	 */
	public GQLMethodDataFetcher(final GQLAbstractCustomMethod<?> method) {
		this.method = method;
	}

	/**
	 * @return the method
	 */
	public GQLAbstractCustomMethod<?> getMethod() {
		return method;
	}

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// METHODS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	@SuppressWarnings({"unchecked", "rawtypes"})
	@Override
	public Object get(final DataFetchingEnvironment environment) {
		final Field queryField = environment.getField();
		final Map<String, Object> arguments = environment.getArguments();
		Object ret;

		if (method instanceof GQLCustomMethod0Arg) {
			ret = ((GQLCustomMethod0Arg) method).apply();
		} else if (method instanceof GQLCustomMethod1Arg) {
			final Object arg1 = mapArgument(
					getArgumentValue(queryField,
							GQLUtils.mapDynamicMethodName(((GQLCustomMethod1Arg) method).getArg1Name()), arguments),
					getArgumentType(1));
			ret = ((GQLCustomMethod1Arg) method).apply(arg1);
		} else if (method instanceof GQLCustomMethod2Arg) {
			final Object arg1 = mapArgument(
					getArgumentValue(queryField,
							GQLUtils.mapDynamicMethodName(((GQLCustomMethod2Arg) method).getArg1Name()), arguments),
					getArgumentType(1));
			final Object arg2 = mapArgument(
					getArgumentValue(queryField,
							GQLUtils.mapDynamicMethodName(((GQLCustomMethod2Arg) method).getArg2Name()), arguments),
					getArgumentType(2));
			ret = ((GQLCustomMethod2Arg) method).apply(arg1, arg2);
		} else if (method instanceof GQLCustomMethod3Arg) {
			final Object arg1 = mapArgument(
					getArgumentValue(queryField,
							GQLUtils.mapDynamicMethodName(((GQLCustomMethod3Arg) method).getArg1Name()), arguments),
					getArgumentType(1));
			final Object arg2 = mapArgument(
					getArgumentValue(queryField,
							GQLUtils.mapDynamicMethodName(((GQLCustomMethod3Arg) method).getArg2Name()), arguments),
					getArgumentType(2));
			final Object arg3 = mapArgument(
					getArgumentValue(queryField,
							GQLUtils.mapDynamicMethodName(((GQLCustomMethod3Arg) method).getArg3Name()), arguments),
					getArgumentType(3));
			ret = ((GQLCustomMethod3Arg) method).apply(arg1, arg2, arg3);
		} else if (method instanceof GQLCustomMethod4Arg) {
			final Object arg1 = mapArgument(
					getArgumentValue(queryField,
							GQLUtils.mapDynamicMethodName(((GQLCustomMethod4Arg) method).getArg1Name()), arguments),
					getArgumentType(1));
			final Object arg2 = mapArgument(
					getArgumentValue(queryField,
							GQLUtils.mapDynamicMethodName(((GQLCustomMethod4Arg) method).getArg2Name()), arguments),
					getArgumentType(2));
			final Object arg3 = mapArgument(
					getArgumentValue(queryField,
							GQLUtils.mapDynamicMethodName(((GQLCustomMethod4Arg) method).getArg3Name()), arguments),
					getArgumentType(3));
			final Object arg4 = mapArgument(
					getArgumentValue(queryField,
							GQLUtils.mapDynamicMethodName(((GQLCustomMethod4Arg) method).getArg4Name()), arguments),
					getArgumentType(4));
			ret = ((GQLCustomMethod4Arg) method).apply(arg1, arg2, arg3, arg4);
		} else if (method instanceof GQLCustomMethod5Arg) {
			final Object arg1 = mapArgument(
					getArgumentValue(queryField,
							GQLUtils.mapDynamicMethodName(((GQLCustomMethod5Arg) method).getArg1Name()), arguments),
					getArgumentType(1));
			final Object arg2 = mapArgument(
					getArgumentValue(queryField,
							GQLUtils.mapDynamicMethodName(((GQLCustomMethod5Arg) method).getArg2Name()), arguments),
					getArgumentType(2));
			final Object arg3 = mapArgument(
					getArgumentValue(queryField,
							GQLUtils.mapDynamicMethodName(((GQLCustomMethod5Arg) method).getArg3Name()), arguments),
					getArgumentType(3));
			final Object arg4 = mapArgument(
					getArgumentValue(queryField,
							GQLUtils.mapDynamicMethodName(((GQLCustomMethod5Arg) method).getArg4Name()), arguments),
					getArgumentType(4));
			final Object arg5 = mapArgument(
					getArgumentValue(queryField,
							GQLUtils.mapDynamicMethodName(((GQLCustomMethod5Arg) method).getArg5Name()), arguments),
					getArgumentType(5));
			ret = ((GQLCustomMethod5Arg) method).apply(arg1, arg2, arg3, arg4, arg5);
		} else {
			throw new IllegalArgumentException(
					Message.format("Unsupported custom method type [{}]", method.getClass()));
		}
		return ret;
	}

	private Type getArgumentType(final int argumentNumber) {
		Type argumentType;
		if (method instanceof GQLCustomMethod1Arg) {
			argumentType = ((GQLCustomMethod1Arg<?, ?>) method).getArgumentTypes().get(argumentNumber - 1);
		} else if (method instanceof GQLCustomMethod2Arg) {
			argumentType = ((GQLCustomMethod2Arg<?, ?, ?>) method).getArgumentTypes().get(argumentNumber - 1);
		} else if (method instanceof GQLCustomMethod3Arg) {
			argumentType = ((GQLCustomMethod3Arg<?, ?, ?, ?>) method).getArgumentTypes().get(argumentNumber - 1);
		} else if (method instanceof GQLCustomMethod4Arg) {
			argumentType = ((GQLCustomMethod4Arg<?, ?, ?, ?, ?>) method).getArgumentTypes().get(argumentNumber - 1);
		} else if (method instanceof GQLCustomMethod5Arg) {
			argumentType = ((GQLCustomMethod5Arg<?, ?, ?, ?, ?, ?>) method).getArgumentTypes().get(argumentNumber - 1);
		} else {
			throw new IllegalArgumentException(
					Message.format("Unsupported custom method type [{}]", method.getClass()));
		}
		return argumentType;
	}
}
