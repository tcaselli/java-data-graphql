package com.daikit.graphql.constants;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.daikit.graphql.enums.GQLOrderByDirectionEnum;
import com.daikit.graphql.enums.GQLScalarTypeEnum;

import graphql.Scalars;
import graphql.schema.GraphQLScalarType;

/**
 * Schema constants
 *
 * @author tcaselli
 */
@SuppressWarnings("javadoc")
public class GQLSchemaConstants {

	public static final String QUERY_TYPE = "QueryType";
	public static final String MUTATION_TYPE = "MutationType";

	public static final String INPUT_OBJECT_SUFFIX = "InputType";
	public static final String OUTPUT_OBJECT_SUFFIX = "OutputType";

	public static final String LOAD_RESULT_SUFFIX = "LoadResult";
	public static final String DELETE_RESULT_PREFIX = "DeleteResult";

	public static final String QUERY_GET_LIST_PREFIX = "getAll";
	public static final String QUERY_GET_SINGLE_PREFIX = "get";
	public static final String MUTATION_SAVE_PREFIX = "save";
	public static final String MUTATION_DELETE_PREFIX = "delete";

	public static final String ID_SUFFIX = "Id";
	public static final String PLURAL_SUFFIX = "s";
	public static final String IDS_SUFFIX = ID_SUFFIX + PLURAL_SUFFIX;

	public static final String INPUT_DATA = "data";
	public static final String RESULT_DATA = "data";

	public static final String ORDER_BY = "orderBy";
	public static final String ORDER_BY_FIELD = "field";
	public static final String ORDER_BY_DIRECTION = "direction";
	public static final GQLOrderByDirectionEnum ORDER_BY_DIRECTION_DEFAULT_VALUE = GQLOrderByDirectionEnum.ASC;

	public static final String DELETE_RESULT_ID = "id";
	public static final String DELETE_RESULT_TYPENAME = "typename";

	public static final String PAGING = "paging";
	public static final String PAGING_TOTAL_LENGTH = "totalLength";
	public static final String PAGING_OFFSET = "offset";
	public static final String PAGING_LIMIT = "limit";
	public static final int PAGING_LIMIT_DEFAULT_VALUE = 25;

	public static final String FILTER = "filter";
	public static final String FILTER_OPERATOR = "operator";
	public static final String FILTER_VALUE = "value";

	public static final String FILTER_FIELDS_PREFIX = "FilterFields";

	public static final String EMBEDDED_TYPE_PREFIX = "type";

	public static final String FIELD_ID = "id";

	public static Map<GQLScalarTypeEnum, GraphQLScalarType> SCALARS = new HashMap<>();

	static {
		SCALARS.put(GQLScalarTypeEnum.INT, Scalars.GraphQLInt);
		SCALARS.put(GQLScalarTypeEnum.LONG, Scalars.GraphQLLong);
		SCALARS.put(GQLScalarTypeEnum.FLOAT, Scalars.GraphQLFloat);
		SCALARS.put(GQLScalarTypeEnum.STRING, Scalars.GraphQLString);
		SCALARS.put(GQLScalarTypeEnum.BOOLEAN, Scalars.GraphQLBoolean);
		SCALARS.put(GQLScalarTypeEnum.ID, Scalars.GraphQLID);
		SCALARS.put(GQLScalarTypeEnum.BIG_INTEGER, Scalars.GraphQLBigInteger);
		SCALARS.put(GQLScalarTypeEnum.BIG_DECIMAL, Scalars.GraphQLBigDecimal);
		SCALARS.put(GQLScalarTypeEnum.BYTE, Scalars.GraphQLByte);
		SCALARS.put(GQLScalarTypeEnum.SHORT, Scalars.GraphQLShort);
		SCALARS.put(GQLScalarTypeEnum.CHAR, Scalars.GraphQLChar);
		SCALARS.put(GQLScalarTypeEnum.DATE, GQLJavaScalars.GraphQLDate);
		SCALARS.put(GQLScalarTypeEnum.FILE, GQLJavaScalars.GraphQLFile);
		SCALARS.put(GQLScalarTypeEnum.LOCAL_DATE, GQLJavaScalars.GraphQLLocalDate);
		SCALARS.put(GQLScalarTypeEnum.LOCAL_DATE_TIME, GQLJavaScalars.GraphQLLocalDateTime);
	}

	/**
	 * Remove {@link GQLSchemaConstants#ID_SUFFIX} or
	 * {@link GQLSchemaConstants#IDS_SUFFIX} suffixes from given property name
	 *
	 * @param property
	 *            the property name
	 * @return the property without suffix
	 */
	public static final String removePropertyIdSuffix(final String property) {
		String ret;
		if (property.endsWith(IDS_SUFFIX)) {
			ret = StringUtils.removeEnd(property, IDS_SUFFIX) + PLURAL_SUFFIX;
		} else {
			ret = StringUtils.removeEnd(property, ID_SUFFIX);
		}
		return ret;
	}

}
