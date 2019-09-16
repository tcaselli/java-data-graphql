package com.daikit.graphql.builder.types;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.WordUtils;

import com.daikit.graphql.builder.GQLSchemaBuilderCache;
import com.daikit.graphql.enums.GQLFilterOperatorEnum;
import com.daikit.graphql.enums.GQLScalarTypeEnum;
import com.daikit.graphql.meta.GQLMetaModel;
import com.daikit.graphql.meta.entity.GQLEnumMetaData;

import graphql.schema.GraphQLEnumType;
import graphql.schema.GraphQLInputObjectField;
import graphql.schema.GraphQLInputObjectType;
import graphql.schema.GraphQLNonNull;
import graphql.schema.GraphQLType;

/**
 * Builder for filter operators
 *
 * @author Thibaut Caselli
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
	 * {@link GQLMetaModel}
	 *
	 * @param metaModel
	 *            the {@link GQLMetaModel}
	 */
	public void buildFilterOperatorsInputTypes(final GQLMetaModel metaModel) {
		logger.debug("Build filter operators types");
		getCache().getInputScalarFilterOperators().putAll(buildScalarFilterOperatorsInputObjectTypes());
		metaModel.getEnums().forEach(enumMeta -> getCache().getInputEnumFilterOperators().put(enumMeta.getEnumClass(),
				buildEnumFilterOperatorsInputObjectType(enumMeta)));
	}

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// PRIVATE UTILS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	private Map<String, GraphQLInputObjectType> buildScalarFilterOperatorsInputObjectTypes() {
		final Map<String, GraphQLInputObjectType> filterOperators = new HashMap<>();

		// Enumeration type for numbers
		final GraphQLEnumType numberOperatorEnumType = buildOperatorEnumType("numbers",
				GQLFilterOperatorEnum.__NUMBER_OPERATORS);

		final GraphQLEnumType booleanOperatorEnumType = buildOperatorEnumType("booleans",
				GQLFilterOperatorEnum.__BOOLEAN_OPERATORS);

		final GraphQLEnumType dateOperatorEnumType = buildOperatorEnumType("dates",
				GQLFilterOperatorEnum.__DATE_OPERATORS);

		final GraphQLEnumType stringOperatorEnumType = buildOperatorEnumType("strings",
				GQLFilterOperatorEnum.__STRING_OPERATORS);

		final GraphQLEnumType idOperatorEnumType = buildOperatorEnumType("entities",
				GQLFilterOperatorEnum.__ID_OPERATORS);

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
				.forEach(entry -> filterOperators.put(entry.getKey().toString(), buildFilterOperator(entry.getValue(),
						// Convert UPPER_SNAKE_CASE to CamelCase
						StringUtils.remove(WordUtils.capitalizeFully(entry.getKey().name(), '_'), "_"),
						getConfig().getScalarType(entry.getKey().toString()).get())));

		return filterOperators;
	}

	private GraphQLEnumType buildOperatorEnumType(final String typeName,
			final Collection<GQLFilterOperatorEnum> operators) {
		final GraphQLEnumType.Builder operatorEnumTypeBuilder = GraphQLEnumType.newEnum();
		operatorEnumTypeBuilder.name("FilterOperatorEnum" + removeEnumSuffix(StringUtils.capitalize(typeName)));
		operatorEnumTypeBuilder.description("Filter operators for " + typeName + ".");
		operators.forEach(
				operator -> operatorEnumTypeBuilder.value(operator.getCode(), operator, operator.getDescription()));
		return operatorEnumTypeBuilder.build();
	}

	private GraphQLInputObjectType buildEnumFilterOperatorsInputObjectType(final GQLEnumMetaData enumMetaData) {
		final GraphQLEnumType enumOperatorEnumType = buildOperatorEnumType(enumMetaData.getName(),
				GQLFilterOperatorEnum.__ENUM_OPERATORS);
		return buildFilterOperator(enumOperatorEnumType, enumMetaData.getName(),
				getCache().getEnumType(enumMetaData.getEnumClass()));
	}

	private String removeEnumSuffix(final String name) {
		return name.endsWith("Enum") ? name.substring(0, name.length() - 4) : name;
	}

	private GraphQLInputObjectType buildFilterOperator(final GraphQLEnumType operatorType, final String typeName,
			final GraphQLType valueType) {
		final GraphQLInputObjectType.Builder builder = GraphQLInputObjectType.newInputObject();
		builder.name(getConfig().getQueryGetListFilterAttributeOperatorTypeNamePrefix() + typeName);
		builder.description("Filter field for value type [" + valueType.getName() + "]");

		final GraphQLInputObjectField.Builder operatorFieldBuilder = GraphQLInputObjectField.newInputObjectField();
		operatorFieldBuilder.name(getConfig().getQueryGetListFilterAttributeOperatorName());
		operatorFieldBuilder.description("Filter operator.");
		operatorFieldBuilder.type(new GraphQLNonNull(operatorType));
		builder.field(operatorFieldBuilder.build());

		if (valueType != null) {
			final GraphQLInputObjectField.Builder valueFieldBuilder = GraphQLInputObjectField.newInputObjectField();
			valueFieldBuilder.name(getConfig().getQueryGetListFilterAttributeValueName());
			valueFieldBuilder.description("Filter value.");
			valueFieldBuilder.type(new GraphQLNonNull(valueType));
			builder.field(valueFieldBuilder.build());
		}

		return builder.build();
	}
}
