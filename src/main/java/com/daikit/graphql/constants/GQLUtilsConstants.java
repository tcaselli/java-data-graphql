package com.daikit.graphql.constants;

import java.io.File;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.daikit.graphql.meta.data.GQLScalarTypeEnum;

/**
 * GQL utils constants
 *
 * @author tcaselli
 */
public class GQLUtilsConstants {

	/**
	 * Type ID
	 */
	public static final class IdType {
		// Nothing done
	}

	private static final Map<Class<?>, GQLScalarTypeEnum> SCALAR_TYPE_MAPPINGS = new HashMap<>();

	static {
		SCALAR_TYPE_MAPPINGS.put(IdType.class, GQLScalarTypeEnum.ID);
		SCALAR_TYPE_MAPPINGS.put(String.class, GQLScalarTypeEnum.STRING);
		SCALAR_TYPE_MAPPINGS.put(Integer.class, GQLScalarTypeEnum.INT);
		SCALAR_TYPE_MAPPINGS.put(Boolean.class, GQLScalarTypeEnum.BOOLEAN);
		SCALAR_TYPE_MAPPINGS.put(Date.class, GQLScalarTypeEnum.DATE);
		SCALAR_TYPE_MAPPINGS.put(File.class, GQLScalarTypeEnum.FILE);
		SCALAR_TYPE_MAPPINGS.put(Double.class, GQLScalarTypeEnum.FLOAT);
		SCALAR_TYPE_MAPPINGS.put(BigDecimal.class, GQLScalarTypeEnum.BIG_DECIMAL);
	}

	/**
	 * Scalar type classes
	 */
	public static final Set<Class<?>> SCALAR_TYPE_CLASSES = SCALAR_TYPE_MAPPINGS.keySet().stream()
			.collect(Collectors.toSet());

	/**
	 * Get scalar type enum from class
	 *
	 * @param scalarClass
	 *            the scalar class
	 * @return an optional {@link GQLScalarTypeEnum}
	 */
	public static final Optional<GQLScalarTypeEnum> getScalarTypeFromClass(final Class<?> scalarClass) {
		return Optional.ofNullable(SCALAR_TYPE_MAPPINGS.get(scalarClass));
	}

}
