package com.daikit.graphql.utils;

import java.io.File;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.daikit.graphql.enums.GQLScalarTypeEnum;

/**
 * GQL Utils
 *
 * @author tcaselli
 */
public class GQLScalarUtils {

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
		return SCALAR_TYPE_MAPPINGS.entrySet().stream().filter(entry -> entry.getKey().isAssignableFrom(scalarClass))
				.map(Entry::getValue).findFirst();
	}

	/**
	 * Get whether the given class corresponds to a scalar type
	 *
	 * @param clazz
	 *            the class to be tested
	 * @return a boolean
	 */
	public static final boolean isScalarType(final Class<?> clazz) {
		return SCALAR_TYPE_MAPPINGS.keySet().stream().filter(key -> key.isAssignableFrom(clazz)).findFirst()
				.isPresent();
	}

}
