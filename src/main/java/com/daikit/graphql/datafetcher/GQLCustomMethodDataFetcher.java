package com.daikit.graphql.datafetcher;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.daikit.generics.utils.GenericsUtils;
import com.daikit.graphql.builder.GQLExecutionContext;
import com.daikit.graphql.builder.GQLSchemaBuilder;
import com.daikit.graphql.custommethod.GQLCustomMethod;
import com.daikit.graphql.custommethod.GQLCustomMethodArg;
import com.daikit.graphql.execution.GQLRootContext;
import com.daikit.graphql.utils.Message;

import graphql.GraphQLException;
import graphql.language.Field;
import graphql.schema.DataFetchingEnvironment;

/**
 * Class that may be overridden (or used as is) to provide "custom method" data fetcher to the schema building. This
 * class is typically to be extended and used in {@link GQLSchemaBuilder} for buildSchema custom method data fetcher
 * argument
 *
 * @author Thibaut Caselli
 */
public class GQLCustomMethodDataFetcher extends GQLAbstractDataFetcher<Object> {

	// All registered custom methods mapped by name
	private final Map<String, GQLCustomMethod> allMethods = new HashMap<>();
	private GQLDynamicAttributeRegistry dynamicAttributeRegistry;

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// METHODS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	/**
	 * Register custom methods in this data fetcher. This will be done automatically during schema building process.
	 *
	 * @param customMethods a {@link List} of {@link GQLCustomMethod}
	 */
	public void registerCustomMethods(final List<? extends GQLCustomMethod> customMethods) {
		customMethods.stream().forEach(customMethod -> {
			final GQLCustomMethod existing = allMethods.get(customMethod.getName());
			if (existing != null && !existing.equals(customMethod)) {
				throw new GraphQLException(
						Message.format("Duplicate custom methods registered with name {}.", customMethod.getName()));
			}
			allMethods.put(customMethod.getName(), customMethod);
		});
	}

	@Override
	public Object get(final DataFetchingEnvironment environment) throws Exception {
		final GQLCustomMethod method = allMethods.get(environment.getField().getName());
		final List<Object> arguments = method.getArgs().stream()
				.map(arg -> getArgumentValue(environment, arg, dynamicAttributeRegistry)).collect(Collectors.toList());
		try {
			return method.getMethod().invoke(method.getController(), arguments.toArray());
		} catch (final InvocationTargetException e) {
			throw e.getCause() instanceof Exception ? (Exception) e.getCause() : e;
		}
	}

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// METHODS THAT MAY BE OVERRIDDEN TO PROVIDE CUSTOM BEHAVIOR
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	/**
	 * Get argument value from given environment
	 *
	 * @param environment              the {@link DataFetchingEnvironment}
	 * @param argument                 the {@link GQLCustomMethodArg}
	 * @param <T>                      the argument type
	 * @param dynamicAttributeRegistry the {@link GQLDynamicAttributeRegistry}
	 * @return the argument value
	 */
	@SuppressWarnings("unchecked")
	protected <T> T getArgumentValue(final DataFetchingEnvironment environment, final GQLCustomMethodArg argument,
			final GQLDynamicAttributeRegistry dynamicAttributeRegistry) {
		if (GQLExecutionContext.class.isAssignableFrom(GenericsUtils.getTypeClass(argument.getType()))) {
			return (T) ((GQLRootContext) environment.getContext()).getExecutionContext();
		} else if (GQLRootContext.class.isAssignableFrom(GenericsUtils.getTypeClass(argument.getType()))) {
			return (T) ((GQLRootContext) environment.getContext());
		} else {
			final Field queryField = environment.getField();
			final Object argumentGraphQLValue = getArgumentValue(queryField, argument.getName(),
					environment.getArguments());
			Object mappedValue = argumentGraphQLValue;
			if (mappedValue != null && mappedValue instanceof Map) {
				mappedValue = convertValue(dynamicAttributeRegistry, (Map<String, Object>) mappedValue, argument);
			}
			return (T) mappedValue;
		}
	}

	/**
	 * Convert given argument property values map to an actual argument object of the expected argument type
	 *
	 * @param dynamicAttributeRegistry the {@link GQLDynamicAttributeRegistry}
	 * @param argumentPropertyValues   a {@link Map} of property values to set in argument object
	 * @param argument                 the {@link GQLCustomMethodArg}
	 * @return the converted value
	 */
	protected Object convertValue(final GQLDynamicAttributeRegistry dynamicAttributeRegistry,
			final Map<String, Object> argumentPropertyValues, final GQLCustomMethodArg argument) {
		return createAndSetPropertyValues(argumentPropertyValues, GenericsUtils.getTypeClass(argument.getType()));
	}

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// GETTERS / SETTERS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	/**
	 * @return the dynamicAttributeRegistry
	 */
	public GQLDynamicAttributeRegistry getDynamicAttributeRegistry() {
		return dynamicAttributeRegistry;
	}

	/**
	 * @param dynamicAttributeRegistry the dynamicAttributeRegistry to set
	 */
	public void setDynamicAttributeRegistry(final GQLDynamicAttributeRegistry dynamicAttributeRegistry) {
		this.dynamicAttributeRegistry = dynamicAttributeRegistry;
	}
}
