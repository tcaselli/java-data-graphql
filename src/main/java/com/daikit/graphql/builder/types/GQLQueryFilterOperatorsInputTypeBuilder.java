package com.daikit.graphql.builder.types;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.WordUtils;

import com.daikit.graphql.builder.GQLSchemaBuilderCache;
import com.daikit.graphql.constants.GQLSchemaConstants;
import com.daikit.graphql.enums.GQLFilterOperatorEnum;
import com.daikit.graphql.enums.GQLScalarTypeEnum;
import com.daikit.graphql.meta.GQLMetaDataModel;
import com.daikit.graphql.meta.entity.GQLEnumMetaData;

import graphql.schema.GraphQLEnumType;
import graphql.schema.GraphQLInputObjectField;
import graphql.schema.GraphQLInputObjectType;
import graphql.schema.GraphQLNonNull;
import graphql.schema.GraphQLType;

/**
 * Builder for filter operators
 *
 * @author tcaselli
 */
public class GQLQueryFilterOperatorsInputTypeBuilder extends GQLAbstractTypesBuilder {

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// CONSTRUCTORS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	/**
	 * Constructor
	 *
	 * @param cache
	 *            the {@link GQLSchemaBuilderCache}
	 */
	public GQLQueryFilterOperatorsInputTypeBuilder(final GQLSchemaBuilderCache cache) {
		super(cache);
	}

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// METHODS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	/**
	 * Build query filter input type and cache it from given
	 * {@link GQLMetaDataModel}
	 *
	 * @param metaDataModel
	 *            the {@link GQLMetaDataModel}
	 */
	public void buildFilterOperatorsInputTypes(final GQLMetaDataModel metaDataModel) {
		logger.debug("Build filter operators types");
		getCache().getInputScalarFilterOperators().putAll(buildScalarFilterOperatorsInputObjectTypes());
		metaDataModel.getEnums().forEach(enumMeta -> getCache().getInputEnumFilterOperators()
				.put(enumMeta.getEnumClass(), buildEnumFilterOperatorsInputObjectType(enumMeta)));
	}

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// PRIVATE UTILS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	private Map<GQLScalarTypeEnum, GraphQLInputObjectType> buildScalarFilterOperatorsInputObjectTypes() {
		final Map<GQLScalarTypeEnum, GraphQLInputObjectType> filterOperators = new HashMap<>();

		// Enumeration type for numbers
		final GraphQLEnumType numberOperatorEnumType = buildOperatorEnumType("numbers", GQLFilterOperatorEnum.EQUAL,
				GQLFilterOperatorEnum.NOT_EQUAL, GQLFilterOperatorEnum.GREATER_EQUAL,
				GQLFilterOperatorEnum.GREATER_THAN, GQLFilterOperatorEnum.LOWER_EQUAL, GQLFilterOperatorEnum.LOWER_THAN,
				GQLFilterOperatorEnum.IN, GQLFilterOperatorEnum.NOT_IN, GQLFilterOperatorEnum.NULL,
				GQLFilterOperatorEnum.NOT_NULL);

		final GraphQLEnumType booleanOperatorEnumType = buildOperatorEnumType("booleans", GQLFilterOperatorEnum.EQUAL,
				GQLFilterOperatorEnum.NULL, GQLFilterOperatorEnum.NOT_NULL);

		final GraphQLEnumType dateOperatorEnumType = buildOperatorEnumType("dates", GQLFilterOperatorEnum.EQUAL,
				GQLFilterOperatorEnum.NOT_EQUAL, GQLFilterOperatorEnum.GREATER_EQUAL,
				GQLFilterOperatorEnum.GREATER_THAN, GQLFilterOperatorEnum.LOWER_EQUAL, GQLFilterOperatorEnum.LOWER_THAN,
				GQLFilterOperatorEnum.IN, GQLFilterOperatorEnum.NOT_IN, GQLFilterOperatorEnum.NULL,
				GQLFilterOperatorEnum.NOT_NULL);

		final GraphQLEnumType stringOperatorEnumType = buildOperatorEnumType("strings", GQLFilterOperatorEnum.EQUAL,
				GQLFilterOperatorEnum.NOT_EQUAL, GQLFilterOperatorEnum.IN, GQLFilterOperatorEnum.NOT_IN,
				GQLFilterOperatorEnum.NULL, GQLFilterOperatorEnum.NOT_NULL, GQLFilterOperatorEnum.EMPTY,
				GQLFilterOperatorEnum.NOT_EMPTY, GQLFilterOperatorEnum.STARTS_WITH, GQLFilterOperatorEnum.ENDS_WITH,
				GQLFilterOperatorEnum.CONTAINS, GQLFilterOperatorEnum.LIKE);

		final GraphQLEnumType idOperatorEnumType = buildOperatorEnumType("entities", GQLFilterOperatorEnum.EQUAL,
				GQLFilterOperatorEnum.NOT_EQUAL, GQLFilterOperatorEnum.IN, GQLFilterOperatorEnum.NOT_IN);

		final Map<GQLScalarTypeEnum, GraphQLEnumType> scalarOperators = new HashMap<>();
		scalarOperators.put(GQLScalarTypeEnum.BIG_DECIMAL, numberOperatorEnumType);
		scalarOperators.put(GQLScalarTypeEnum.BIG_INTEGER, numberOperatorEnumType);
		scalarOperators.put(GQLScalarTypeEnum.LONG, numberOperatorEnumType);
		scalarOperators.put(GQLScalarTypeEnum.SHORT, numberOperatorEnumType);
		scalarOperators.put(GQLScalarTypeEnum.FLOAT, numberOperatorEnumType);
		scalarOperators.put(GQLScalarTypeEnum.INT, numberOperatorEnumType);
		scalarOperators.put(GQLScalarTypeEnum.ID, idOperatorEnumType);
		scalarOperators.put(GQLScalarTypeEnum.BOOLEAN, booleanOperatorEnumType);
		scalarOperators.put(GQLScalarTypeEnum.DATE, dateOperatorEnumType);
		scalarOperators.put(GQLScalarTypeEnum.LOCAL_DATE, dateOperatorEnumType);
		scalarOperators.put(GQLScalarTypeEnum.LOCAL_DATE_TIME, dateOperatorEnumType);
		scalarOperators.put(GQLScalarTypeEnum.CHAR, stringOperatorEnumType);
		scalarOperators.put(GQLScalarTypeEnum.STRING, stringOperatorEnumType);
		// scalarOperators.put(GQLScalarTypeEnum.BYTE, numberOperatorEnumType);
		// // TODO

		scalarOperators.entrySet()
				.forEach(entry -> filterOperators.put(entry.getKey(), buildFilterOperator(entry.getValue(),
						// Convert UPPER_SNAKE_CASE to CamelCase
						StringUtils.remove(WordUtils.capitalizeFully(entry.getKey().name(), '_'), "_"),
						GQLSchemaConstants.SCALARS.get(entry.getKey()))));

		return filterOperators;
	}

	private GraphQLEnumType buildOperatorEnumType(final String typeName, final GQLFilterOperatorEnum... operators) {
		final GraphQLEnumType.Builder operatorEnumTypeBuilder = GraphQLEnumType.newEnum();
		operatorEnumTypeBuilder.name("FilterOperatorEnum" + removeEnumSuffix(StringUtils.capitalize(typeName)));
		operatorEnumTypeBuilder.description("Filter operators for " + typeName + ".");
		Arrays.asList(operators).forEach(
				operator -> operatorEnumTypeBuilder.value(operator.getCode(), operator, operator.getDescription()));
		return operatorEnumTypeBuilder.build();
	}

	private GraphQLInputObjectType buildEnumFilterOperatorsInputObjectType(final GQLEnumMetaData enumMetaData) {
		final GraphQLEnumType enumOperatorEnumType = buildOperatorEnumType(enumMetaData.getName(),
				GQLFilterOperatorEnum.EQUAL, GQLFilterOperatorEnum.NOT_EQUAL, GQLFilterOperatorEnum.IN,
				GQLFilterOperatorEnum.NOT_IN, GQLFilterOperatorEnum.NULL, GQLFilterOperatorEnum.NOT_NULL);
		return buildFilterOperator(enumOperatorEnumType, enumMetaData.getName(),
				getCache().getEnumType(enumMetaData.getEnumClass()));
	}

	private String removeEnumSuffix(final String name) {
		return name.endsWith("Enum") ? name.substring(0, name.length() - 4) : name;
	}

	private GraphQLInputObjectType buildFilterOperator(final GraphQLEnumType operatorType, final String typeName,
			final GraphQLType valueType) {
		final GraphQLInputObjectType.Builder builder = GraphQLInputObjectType.newInputObject();
		builder.name("FilterOperator" + typeName);
		builder.description("Filter field for value type [" + valueType.getName() + "]");

		final GraphQLInputObjectField.Builder operatorFieldBuilder = GraphQLInputObjectField.newInputObjectField();
		operatorFieldBuilder.name(GQLSchemaConstants.FILTER_OPERATOR);
		operatorFieldBuilder.description("Filter operator.");
		operatorFieldBuilder.type(new GraphQLNonNull(operatorType));
		builder.field(operatorFieldBuilder.build());

		if (valueType != null) {
			final GraphQLInputObjectField.Builder valueFieldBuilder = GraphQLInputObjectField.newInputObjectField();
			valueFieldBuilder.name(GQLSchemaConstants.FILTER_VALUE);
			valueFieldBuilder.description("Filter value.");
			valueFieldBuilder.type(new GraphQLNonNull(valueType));
			builder.field(valueFieldBuilder.build());
		}

		return builder.build();
	}
}