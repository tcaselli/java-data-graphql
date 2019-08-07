package com.daikit.graphql.constants;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.daikit.graphql.meta.dynamic.method.GQLAbstractCustomMethod;

/**
 * GQL Utils
 *
 * @author tcaselli
 */
public class GQLUtils {

	/**
	 * Map dynamic method types
	 *
	 * @param types
	 *            the {@link List} of types
	 * @param argNames
	 *            the arguments names in same order than types
	 * @return a list of mapped types
	 */
	public static List<Type> mapDynamicMethodTypes(final List<Type> types, final String... argNames) {
		final List<Type> returnedTypes = new ArrayList<>();
		for (int i = 0; i < types.size(); i++) {
			returnedTypes.add(isDynamicMethodIdArgument(argNames[i]) ? GQLUtilsConstants.IdType.class : types.get(i));
		}
		return returnedTypes;
	}

	/**
	 * Map dynamic method argument name
	 * 
	 * @param name
	 *            the method argument name
	 * @return the mapped argument name
	 */
	public static String mapDynamicMethodName(final String name) {
		return isDynamicMethodIdArgument(name) ? name.substring(GQLAbstractCustomMethod.ID_PREFIX.length()) : name;
	}

	/**
	 * Get whether this method argument name is an ID argument
	 * 
	 * @param name
	 *            the argument name
	 * @return a boolean
	 */
	public static boolean isDynamicMethodIdArgument(final String name) {
		return name.startsWith(GQLAbstractCustomMethod.ID_PREFIX);
	}

}
