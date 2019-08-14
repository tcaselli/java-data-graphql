package com.daikit.graphql.constants;

import java.io.File;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.daikit.graphql.meta.data.GQLScalarTypeEnum;
import com.daikit.graphql.meta.dynamic.method.abs.GQLAbstractCustomMethod;

/**
 * GQL Utils
 *
 * @author tcaselli
 */
public class GQLUtils {

	/**
	 * Type ID
	 */
	public static final class IdType {
		// Nothing done
	}

	private static final Map<Class<?>, GQLScalarTypeEnum> SCALAR_TYPE_MAPPINGS = new HashMap<>();

	static {
		SCALAR_TYPE_MAPPINGS.put(IdType.class, GQLScalarTypeEnum.ID);
		SCALAR_TYPE_MAPPINGS.put(Integer.class, GQLScalarTypeEnum.INT);
		SCALAR_TYPE_MAPPINGS.put(Long.class, GQLScalarTypeEnum.LONG);
		SCALAR_TYPE_MAPPINGS.put(Double.class, GQLScalarTypeEnum.FLOAT);
		SCALAR_TYPE_MAPPINGS.put(String.class, GQLScalarTypeEnum.STRING);
		SCALAR_TYPE_MAPPINGS.put(Boolean.class, GQLScalarTypeEnum.BOOLEAN);
		SCALAR_TYPE_MAPPINGS.put(BigInteger.class, GQLScalarTypeEnum.BIG_INTEGER);
		SCALAR_TYPE_MAPPINGS.put(BigDecimal.class, GQLScalarTypeEnum.BIG_DECIMAL);
		SCALAR_TYPE_MAPPINGS.put(Byte.class, GQLScalarTypeEnum.BYTE);
		SCALAR_TYPE_MAPPINGS.put(Short.class, GQLScalarTypeEnum.SHORT);
		SCALAR_TYPE_MAPPINGS.put(Character.class, GQLScalarTypeEnum.CHAR);
		SCALAR_TYPE_MAPPINGS.put(Date.class, GQLScalarTypeEnum.DATE);
		SCALAR_TYPE_MAPPINGS.put(File.class, GQLScalarTypeEnum.FILE);
		SCALAR_TYPE_MAPPINGS.put(LocalDate.class, GQLScalarTypeEnum.LOCAL_DATE);
		SCALAR_TYPE_MAPPINGS.put(LocalDateTime.class, GQLScalarTypeEnum.LOCAL_DATE_TIME);
	}

	/**
	 * Scalar type classes
	 */
	public static final Set<Class<?>> SCALAR_TYPE_CLASSES = SCALAR_TYPE_MAPPINGS.keySet().stream()
			.collect(Collectors.toSet());

	/**
	 * Get scalar type enumeration from class
	 *
	 * @param scalarClass
	 *            the scalar class
	 * @return an optional {@link GQLScalarTypeEnum}
	 */
	public static final Optional<GQLScalarTypeEnum> getScalarTypeFromClass(final Class<?> scalarClass) {
		return Optional.ofNullable(SCALAR_TYPE_MAPPINGS.get(scalarClass));
	}

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
			returnedTypes.add(isDynamicMethodIdArgument(argNames[i]) ? IdType.class : types.get(i));
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
	public static String mapDynamicMethodArgumentName(final String name) {
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
