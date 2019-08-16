package com.daikit.graphql.datafetcher;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.daikit.generics.utils.GenericsUtils;
import com.daikit.graphql.builder.GQLSchemaBuilder;
import com.daikit.graphql.custommethod.IGQLAbstractCustomMethod;
import com.daikit.graphql.custommethod.IGQLCustomMethod0Arg;
import com.daikit.graphql.custommethod.IGQLCustomMethod1Arg;
import com.daikit.graphql.custommethod.IGQLCustomMethod2Arg;
import com.daikit.graphql.custommethod.IGQLCustomMethod3Arg;
import com.daikit.graphql.custommethod.IGQLCustomMethod4Arg;
import com.daikit.graphql.custommethod.IGQLCustomMethod5Arg;
import com.daikit.graphql.utils.Message;
import com.fasterxml.jackson.databind.ObjectMapper;

import graphql.GraphQLException;
import graphql.language.Field;
import graphql.schema.DataFetchingEnvironment;

/**
 * Class that may be overridden (or used as is) to provide "custom method" data
 * fetcher to the schema building. This class is typically to be extended and
 * used in {@link GQLSchemaBuilder} for buildSchema custom method data fetcher
 * argument
 *
 * @author Thibaut Caselli
 *
 */
public class GQLCustomMethodDataFetcher extends GQLAbstractDataFetcher<Object> {

	// All registered custom methods mapped by name
	private final Map<String, IGQLAbstractCustomMethod<?>> allMethods = new HashMap<>();
	private ObjectMapper objectMapper = new ObjectMapper();

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// METHODS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	@SuppressWarnings({"rawtypes", "unchecked"})
	@Override
	public Object get(DataFetchingEnvironment environment) throws Exception {
		final IGQLAbstractCustomMethod<?> method = allMethods.get(environment.getField().getName());
		Object ret;
		if (method instanceof IGQLCustomMethod0Arg) {
			ret = ((IGQLCustomMethod0Arg) method).apply();
		} else if (method instanceof IGQLCustomMethod1Arg) {
			ret = ((IGQLCustomMethod1Arg) method).apply(getArgumentValue(environment, method, 0));
		} else if (method instanceof IGQLCustomMethod2Arg) {
			ret = ((IGQLCustomMethod2Arg) method).apply(getArgumentValue(environment, method, 0),
					getArgumentValue(environment, method, 1));
		} else if (method instanceof IGQLCustomMethod3Arg) {
			ret = ((IGQLCustomMethod3Arg) method).apply(getArgumentValue(environment, method, 0),
					getArgumentValue(environment, method, 1), getArgumentValue(environment, method, 2));
		} else if (method instanceof IGQLCustomMethod4Arg) {
			ret = ((IGQLCustomMethod4Arg) method).apply(getArgumentValue(environment, method, 0),
					getArgumentValue(environment, method, 1), getArgumentValue(environment, method, 2),
					getArgumentValue(environment, method, 3));
		} else if (method instanceof IGQLCustomMethod5Arg) {
			ret = ((IGQLCustomMethod5Arg) method).apply(getArgumentValue(environment, method, 0),
					getArgumentValue(environment, method, 1), getArgumentValue(environment, method, 2),
					getArgumentValue(environment, method, 3), getArgumentValue(environment, method, 4));
		} else {
			throw new GraphQLException(
					Message.format("Unsupported custom method type [{}]", method.getClass().getName()));
		}
		return ret;
	}

	/**
	 * Get argument value from given environment
	 *
	 * @param environment
	 *            the {@link DataFetchingEnvironment}
	 * @param method
	 *            the {@link IGQLAbstractCustomMethod}
	 * @param argumentPosition
	 *            the argument position (0 if first argument of the method, 1
	 *            the second, etc)
	 * @return the argument value
	 */
	@SuppressWarnings("unchecked")
	protected <T> T getArgumentValue(DataFetchingEnvironment environment, IGQLAbstractCustomMethod<?> method,
			int argumentPosition) {
		final Field queryField = environment.getField();
		final String argumentName = mapDynamicMethodArgumentName(method.getArgNames().get(argumentPosition));
		final Object argumentGraphQLValue = getArgumentValue(queryField, argumentName, environment.getArguments());
		final Type expectedType = method.getArgumentTypes().get(argumentPosition);
		Object mappedValue = argumentGraphQLValue;
		if (mappedValue != null && mappedValue instanceof Map) {
			final Class<?> expectedClass = GenericsUtils.getRawClass(expectedType);
			mappedValue = convertValue((Map<String, Object>) mappedValue, expectedClass, argumentPosition);
		}
		return (T) mappedValue;
	}

	/**
	 * Map dynamic method argument name
	 *
	 * @param name
	 *            the method argument name
	 * @return the mapped argument name
	 */
	protected String mapDynamicMethodArgumentName(final String name) {
		return name.startsWith(IGQLAbstractCustomMethod.ID_PREFIX)
				? name.substring(IGQLAbstractCustomMethod.ID_PREFIX.length())
				: name;
	}

	/**
	 * Convert given argument property values map to an actual argument object
	 * of the expected argument type
	 *
	 * @param argumentPropertyValues
	 *            a {@link Map} of property values to set in argument object
	 * @param argumentType
	 *            the type of the argument to be created
	 * @param argumentPosition
	 *            the argument position among method arguments
	 * @return the converted value
	 */
	protected Object convertValue(Map<String, Object> argumentPropertyValues, Class<?> argumentType,
			int argumentPosition) {
		return getObjectMapper().convertValue(argumentPropertyValues, argumentType);
	}

	/**
	 * Register custom methods in this data fetcher. This will be done
	 * automatically during schema building process.
	 *
	 * @param customMethods
	 *            a {@link List} of {@link IGQLAbstractCustomMethod}
	 */
	public void registerCustomMethods(List<? extends IGQLAbstractCustomMethod<?>> customMethods) {
		customMethods.stream().forEach(customMethod -> {
			final IGQLAbstractCustomMethod<?> existing = allMethods.get(customMethod.getMethodName());
			if (existing != null && !existing.equals(customMethod)) {
				throw new GraphQLException(Message.format("Duplicate custom methods registered with name {}.",
						customMethod.getMethodName()));
			}
			allMethods.put(customMethod.getMethodName(), customMethod);
		});
	}

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// GETTERS / SETTERS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	/**
	 * @return the objectMapper
	 */
	public ObjectMapper getObjectMapper() {
		return objectMapper;
	}

	/**
	 * @param objectMapper
	 *            the objectMapper to set
	 */
	public void setObjectMapper(ObjectMapper objectMapper) {
		this.objectMapper = objectMapper;
	}
}
