package com.daikit.graphql.enums;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Filter operator
 *
 * @author Thibaut Caselli
 */
@SuppressWarnings("javadoc")
public enum GQLFilterOperatorEnum {

	EMPTY("isEmpty", "Field is either null or an empty text. No [value] needed for the related filter."),

	NOT_EMPTY("isNotEmpty", "Field is not null and not an empty text. No [value] needed for the related filter."),

	NULL("isNull", "Field is null. No [value] needed for the related filter."),

	NOT_NULL("isNotNull", "Field is not null. No [value] needed for the related filter."),

	ENDS_WITH("endsWith", "Field ends with text given in [value] property."),

	STARTS_WITH("startsWith", "Field starts with text given in [value] property."),

	CONTAINS("contains", "Field contains text given in [value] property."),

	LIKE("like", "Field is like text given in [value] property. Wildcard character is %."),

	EQUAL("eq", "Field equals text given in [value] property."),

	NOT_EQUAL("neq", "Field is not equal to text given in [value] property."),

	GREATER_THAN("gt", "Field is greater than number given in [value] property."),

	GREATER_EQUAL("ge", "Field is greater or equal to the number given in [value] property."),

	LOWER_THAN("lt", "Field is lower than number given in [value] property."),

	LOWER_EQUAL("le", "Field is lower or equal to the number given in [value] property."),

	IN("in", "Field is contained in list of possible values given in [value] property."),

	NOT_IN("notIn", "Field is not contained in list of possible values given in [value] property.");

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// CONSTRUCTOR / METHODS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	private final String code;
	private final String description;

	private static Map<String, GQLFilterOperatorEnum> MAP = new HashMap<>();

	static {
		for (final GQLFilterOperatorEnum type : GQLFilterOperatorEnum.values()) {
			MAP.put(type.getCode(), type);
		}
	}

	private GQLFilterOperatorEnum(final String code, final String description) {
		this.code = code;
		this.description = description;
	}

	/**
	 * JSON deserializer
	 *
	 * @param code
	 *            the code to get enum from
	 * @return the {@link GQLFilterOperatorEnum} for given code
	 */
	public static GQLFilterOperatorEnum forCode(final String code) {
		return MAP.get(code);
	}

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// PUBLIC METHODS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	public static final List<GQLFilterOperatorEnum> __NUMBER_OPERATORS = Arrays.asList(GQLFilterOperatorEnum.EQUAL,
			GQLFilterOperatorEnum.NOT_EQUAL, GQLFilterOperatorEnum.GREATER_EQUAL, GQLFilterOperatorEnum.GREATER_THAN,
			GQLFilterOperatorEnum.LOWER_EQUAL, GQLFilterOperatorEnum.LOWER_THAN, GQLFilterOperatorEnum.IN,
			GQLFilterOperatorEnum.NOT_IN, GQLFilterOperatorEnum.NULL, GQLFilterOperatorEnum.NOT_NULL);

	public static final List<GQLFilterOperatorEnum> __BOOLEAN_OPERATORS = Arrays.asList(GQLFilterOperatorEnum.EQUAL,
			GQLFilterOperatorEnum.NULL, GQLFilterOperatorEnum.NOT_NULL);

	public static final List<GQLFilterOperatorEnum> __DATE_OPERATORS = Arrays.asList(GQLFilterOperatorEnum.EQUAL,
			GQLFilterOperatorEnum.NOT_EQUAL, GQLFilterOperatorEnum.GREATER_EQUAL, GQLFilterOperatorEnum.GREATER_THAN,
			GQLFilterOperatorEnum.LOWER_EQUAL, GQLFilterOperatorEnum.LOWER_THAN, GQLFilterOperatorEnum.IN,
			GQLFilterOperatorEnum.NOT_IN, GQLFilterOperatorEnum.NULL, GQLFilterOperatorEnum.NOT_NULL);

	public static final List<GQLFilterOperatorEnum> __STRING_OPERATORS = Arrays.asList(GQLFilterOperatorEnum.EQUAL,
			GQLFilterOperatorEnum.NOT_EQUAL, GQLFilterOperatorEnum.IN, GQLFilterOperatorEnum.NOT_IN,
			GQLFilterOperatorEnum.NULL, GQLFilterOperatorEnum.NOT_NULL, GQLFilterOperatorEnum.EMPTY,
			GQLFilterOperatorEnum.NOT_EMPTY, GQLFilterOperatorEnum.STARTS_WITH, GQLFilterOperatorEnum.ENDS_WITH,
			GQLFilterOperatorEnum.CONTAINS, GQLFilterOperatorEnum.LIKE);

	public static final List<GQLFilterOperatorEnum> __ID_OPERATORS = Arrays.asList(GQLFilterOperatorEnum.EQUAL,
			GQLFilterOperatorEnum.NOT_EQUAL, GQLFilterOperatorEnum.IN, GQLFilterOperatorEnum.NOT_IN);

	public static final List<GQLFilterOperatorEnum> __ENUM_OPERATORS = Arrays.asList(GQLFilterOperatorEnum.EQUAL,
			GQLFilterOperatorEnum.NOT_EQUAL, GQLFilterOperatorEnum.IN, GQLFilterOperatorEnum.NOT_IN,
			GQLFilterOperatorEnum.NULL, GQLFilterOperatorEnum.NOT_NULL);

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// GETTERS / SETTERS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

}
