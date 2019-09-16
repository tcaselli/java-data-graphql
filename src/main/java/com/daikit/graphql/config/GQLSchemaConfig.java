package com.daikit.graphql.config;

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

import org.apache.commons.lang3.StringUtils;

import com.daikit.graphql.enums.GQLOrderByDirectionEnum;
import com.daikit.graphql.enums.GQLScalarTypeEnum;

import graphql.Scalars;
import graphql.schema.GraphQLScalarType;

/**
 * Schema configuration allowing to choose prefixes, suffixes, attribute and
 * type names for generated schema items.
 *
 * @author Thibaut Caselli
 */
public class GQLSchemaConfig {

	/**
	 * Type ID
	 */
	public static final class IdType {
		// Nothing done
	}

	private String queryTypeName = "QueryType";
	private String mutationTypeName = "MutationType";

	private String inputTypeNameSuffix = "InputType";
	private String outputTypeNameSuffix = "OutputType";

	private String queryGetListOutputTypeNameSuffix = "LoadResult";
	private String outputDeleteResultTypeNamePrefix = "DeleteResult";

	private String queryGetListPrefix = "getAll";
	private String queryGetByIdPrefix = "get";
	private String mutationSavePrefix = "save";
	private String mutationDeletePrefix = "delete";

	private String attributeIdSuffix = "Id";
	private String attributePluralSuffix = "s";
	private String attributeIdPluralSuffix = attributeIdSuffix + attributePluralSuffix;

	private String mutationAttributeInputDataName = "data";
	private String queryGetListAttributeOutputDataName = "data";

	private GQLOrderByDirectionEnum queryGetListFilterAttributeOrderByDirectionDefaultValue = GQLOrderByDirectionEnum.ASC;

	private String mutationDeleteResultAttributeId = "id";
	private String mutationDeleteResultAttributeTypename = "typename";

	private String queryGetListFilterAttributeName = "filter";
	private String queryGetListFilterAttributeOperatorTypeNamePrefix = "FilterOperator";
	private String queryGetListFilterAttributeOperatorName = "operator";
	private String queryGetListFilterAttributeValueName = "value";
	private String queryGetListFilterAttributeOrderByName = "orderBy";
	private String queryGetListFilterAttributeOrderByFieldName = "field";
	private String queryGetListFilterAttributeOrderByDirectionName = "direction";
	private String queryGetListPagingAttributeName = "paging";
	private String queryGetListPagingAttributeTotalLengthName = "totalLength";
	private String queryGetListPagingAttributeOffsetName = "offset";
	private String queryGetListPagingAttributeLimitName = "limit";
	private int queryGetListPagingAttributeLimitDefaultValue = 25;

	private String queryGetListFilterEntityTypeNameSuffix = "Filter";

	private String concreteEmbeddedExtendingTypeNamePrefix = "type";

	private String attributeIdName = "id";

	private final Map<String, GraphQLScalarType> scalars = new HashMap<>();
	private final Map<Class<?>, String> scalarTypeMappings = new HashMap<>();

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// CONSTRUCTORS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	/**
	 * Constructor
	 */
	public GQLSchemaConfig() {
		scalars.put(GQLScalarTypeEnum.INT.toString(), Scalars.GraphQLInt);
		scalars.put(GQLScalarTypeEnum.LONG.toString(), Scalars.GraphQLLong);
		scalars.put(GQLScalarTypeEnum.FLOAT.toString(), Scalars.GraphQLFloat);
		scalars.put(GQLScalarTypeEnum.STRING.toString(), Scalars.GraphQLString);
		scalars.put(GQLScalarTypeEnum.BOOLEAN.toString(), Scalars.GraphQLBoolean);
		scalars.put(GQLScalarTypeEnum.ID.toString(), Scalars.GraphQLID);
		scalars.put(GQLScalarTypeEnum.BIG_INTEGER.toString(), Scalars.GraphQLBigInteger);
		scalars.put(GQLScalarTypeEnum.BIG_DECIMAL.toString(), Scalars.GraphQLBigDecimal);
		scalars.put(GQLScalarTypeEnum.BYTE.toString(), Scalars.GraphQLByte);
		scalars.put(GQLScalarTypeEnum.SHORT.toString(), Scalars.GraphQLShort);
		scalars.put(GQLScalarTypeEnum.CHAR.toString(), Scalars.GraphQLChar);
		scalars.put(GQLScalarTypeEnum.DATE.toString(), GQLJavaScalars.GraphQLDate);
		scalars.put(GQLScalarTypeEnum.FILE.toString(), GQLJavaScalars.GraphQLFile);
		scalars.put(GQLScalarTypeEnum.LOCAL_DATE.toString(), GQLJavaScalars.GraphQLLocalDate);
		scalars.put(GQLScalarTypeEnum.LOCAL_DATE_TIME.toString(), GQLJavaScalars.GraphQLLocalDateTime);

		scalarTypeMappings.put(IdType.class, GQLScalarTypeEnum.ID.toString());
		scalarTypeMappings.put(Integer.class, GQLScalarTypeEnum.INT.toString());
		scalarTypeMappings.put(Long.class, GQLScalarTypeEnum.LONG.toString());
		scalarTypeMappings.put(Double.class, GQLScalarTypeEnum.FLOAT.toString());
		scalarTypeMappings.put(String.class, GQLScalarTypeEnum.STRING.toString());
		scalarTypeMappings.put(Boolean.class, GQLScalarTypeEnum.BOOLEAN.toString());
		scalarTypeMappings.put(BigInteger.class, GQLScalarTypeEnum.BIG_INTEGER.toString());
		scalarTypeMappings.put(BigDecimal.class, GQLScalarTypeEnum.BIG_DECIMAL.toString());
		scalarTypeMappings.put(Byte.class, GQLScalarTypeEnum.BYTE.toString());
		scalarTypeMappings.put(Short.class, GQLScalarTypeEnum.SHORT.toString());
		scalarTypeMappings.put(Character.class, GQLScalarTypeEnum.CHAR.toString());
		scalarTypeMappings.put(Date.class, GQLScalarTypeEnum.DATE.toString());
		scalarTypeMappings.put(File.class, GQLScalarTypeEnum.FILE.toString());
		scalarTypeMappings.put(LocalDate.class, GQLScalarTypeEnum.LOCAL_DATE.toString());
		scalarTypeMappings.put(LocalDateTime.class, GQLScalarTypeEnum.LOCAL_DATE_TIME.toString());

	}

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// PUBLIC METHODS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	/**
	 * Remove {@link #attributeIdSuffix} or {@link #attributeIdPluralSuffix}
	 * suffixes from given property name
	 *
	 * @param property
	 *            the property name
	 * @return the property without suffix
	 */
	public String removePropertyIdSuffix(final String property) {
		String ret;
		if (property.endsWith(getAttributeIdPluralSuffix())) {
			ret = StringUtils.removeEnd(property, getAttributeIdPluralSuffix()) + getAttributePluralSuffix();
		} else {
			ret = StringUtils.removeEnd(property, getAttributeIdSuffix());
		}
		return ret;
	}

	/**
	 * Scalar type classes
	 *
	 * @return a {@link Set} of all scalar type classes
	 */
	public Set<Class<?>> getScalarTypeClasses() {
		return scalarTypeMappings.keySet().stream().collect(Collectors.toSet());
	}

	/**
	 * Get scalar type code from class
	 *
	 * @param scalarClass
	 *            the scalar class
	 * @return an optional scalar type code
	 */
	public Optional<String> getScalarTypeCodeFromClass(final Class<?> scalarClass) {
		return scalarTypeMappings.entrySet().stream().filter(entry -> entry.getKey().isAssignableFrom(scalarClass))
				.map(Entry::getValue).findFirst();
	}

	/**
	 * Get whether the given class corresponds to a scalar type
	 *
	 * @param clazz
	 *            the class to be tested
	 * @return a boolean
	 */
	public boolean isScalarType(final Class<?> clazz) {
		return scalarTypeMappings.keySet().stream().filter(key -> key.isAssignableFrom(clazz)).findFirst().isPresent();
	}

	/**
	 * Get {@link GraphQLScalarType} from its related scalar type code
	 *
	 * @param scalarTypeCode
	 *            the scalar type code
	 * @return the {@link Optional} found {@link GraphQLScalarType}
	 */
	public Optional<GraphQLScalarType> getScalarType(String scalarTypeCode) {
		return Optional.ofNullable(scalars.get(scalarTypeCode));
	}

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// GETTERS / SETTERS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	/**
	 * @return the queryTypeName
	 */
	public String getQueryTypeName() {
		return queryTypeName;
	}

	/**
	 * @param queryTypeName
	 *            the queryTypeName to set
	 */
	public void setQueryTypeName(String queryTypeName) {
		this.queryTypeName = queryTypeName;
	}

	/**
	 * @return the mutationTypeName
	 */
	public String getMutationTypeName() {
		return mutationTypeName;
	}

	/**
	 * @param mutationTypeName
	 *            the mutationTypeName to set
	 */
	public void setMutationTypeName(String mutationTypeName) {
		this.mutationTypeName = mutationTypeName;
	}

	/**
	 * @return the inputTypeNameSuffix
	 */
	public String getInputTypeNameSuffix() {
		return inputTypeNameSuffix;
	}

	/**
	 * @param inputTypeNameSuffix
	 *            the inputTypeNameSuffix to set
	 */
	public void setInputTypeNameSuffix(String inputTypeNameSuffix) {
		this.inputTypeNameSuffix = inputTypeNameSuffix;
	}

	/**
	 * @return the outputTypeNameSuffix
	 */
	public String getOutputTypeNameSuffix() {
		return outputTypeNameSuffix;
	}

	/**
	 * @param outputTypeNameSuffix
	 *            the outputTypeNameSuffix to set
	 */
	public void setOutputTypeNameSuffix(String outputTypeNameSuffix) {
		this.outputTypeNameSuffix = outputTypeNameSuffix;
	}

	/**
	 * @return the queryGetListOutputTypeNameSuffix
	 */
	public String getQueryGetListOutputTypeNameSuffix() {
		return queryGetListOutputTypeNameSuffix;
	}

	/**
	 * @param queryGetListOutputTypeNameSuffix
	 *            the queryGetListOutputTypeNameSuffix to set
	 */
	public void setQueryGetListOutputTypeNameSuffix(String queryGetListOutputTypeNameSuffix) {
		this.queryGetListOutputTypeNameSuffix = queryGetListOutputTypeNameSuffix;
	}

	/**
	 * @return the outputDeleteResultTypeNamePrefix
	 */
	public String getOutputDeleteResultTypeNamePrefix() {
		return outputDeleteResultTypeNamePrefix;
	}

	/**
	 * @param outputDeleteResultTypeNamePrefix
	 *            the outputDeleteResultTypeNamePrefix to set
	 */
	public void setOutputDeleteResultTypeNamePrefix(String outputDeleteResultTypeNamePrefix) {
		this.outputDeleteResultTypeNamePrefix = outputDeleteResultTypeNamePrefix;
	}

	/**
	 * @return the queryGetListPrefix
	 */
	public String getQueryGetListPrefix() {
		return queryGetListPrefix;
	}

	/**
	 * @param queryGetListPrefix
	 *            the queryGetListPrefix to set
	 */
	public void setQueryGetListPrefix(String queryGetListPrefix) {
		this.queryGetListPrefix = queryGetListPrefix;
	}

	/**
	 * @return the queryGetByIdPrefix
	 */
	public String getQueryGetByIdPrefix() {
		return queryGetByIdPrefix;
	}

	/**
	 * @param queryGetByIdPrefix
	 *            the queryGetByIdPrefix to set
	 */
	public void setQueryGetByIdPrefix(String queryGetByIdPrefix) {
		this.queryGetByIdPrefix = queryGetByIdPrefix;
	}

	/**
	 * @return the mutationSavePrefix
	 */
	public String getMutationSavePrefix() {
		return mutationSavePrefix;
	}

	/**
	 * @param mutationSavePrefix
	 *            the mutationSavePrefix to set
	 */
	public void setMutationSavePrefix(String mutationSavePrefix) {
		this.mutationSavePrefix = mutationSavePrefix;
	}

	/**
	 * @return the mutationDeletePrefix
	 */
	public String getMutationDeletePrefix() {
		return mutationDeletePrefix;
	}

	/**
	 * @param mutationDeletePrefix
	 *            the mutationDeletePrefix to set
	 */
	public void setMutationDeletePrefix(String mutationDeletePrefix) {
		this.mutationDeletePrefix = mutationDeletePrefix;
	}

	/**
	 * @return the attributeIdSuffix
	 */
	public String getAttributeIdSuffix() {
		return attributeIdSuffix;
	}

	/**
	 * @param attributeIdSuffix
	 *            the attributeIdSuffix to set
	 */
	public void setAttributeIdSuffix(String attributeIdSuffix) {
		this.attributeIdSuffix = attributeIdSuffix;
	}

	/**
	 * @return the attributePluralSuffix
	 */
	public String getAttributePluralSuffix() {
		return attributePluralSuffix;
	}

	/**
	 * @param attributePluralSuffix
	 *            the attributePluralSuffix to set
	 */
	public void setAttributePluralSuffix(String attributePluralSuffix) {
		this.attributePluralSuffix = attributePluralSuffix;
	}

	/**
	 * @return the attributeIdPluralSuffix
	 */
	public String getAttributeIdPluralSuffix() {
		return attributeIdPluralSuffix;
	}

	/**
	 * @param attributeIdPluralSuffix
	 *            the attributeIdPluralSuffix to set
	 */
	public void setAttributeIdPluralSuffix(String attributeIdPluralSuffix) {
		this.attributeIdPluralSuffix = attributeIdPluralSuffix;
	}

	/**
	 * @return the mutationAttributeInputDataName
	 */
	public String getMutationAttributeInputDataName() {
		return mutationAttributeInputDataName;
	}

	/**
	 * @param mutationAttributeInputDataName
	 *            the mutationAttributeInputDataName to set
	 */
	public void setMutationAttributeInputDataName(String mutationAttributeInputDataName) {
		this.mutationAttributeInputDataName = mutationAttributeInputDataName;
	}

	/**
	 * @return the queryGetListAttributeOutputDataName
	 */
	public String getQueryGetListAttributeOutputDataName() {
		return queryGetListAttributeOutputDataName;
	}

	/**
	 * @param queryGetListAttributeOutputDataName
	 *            the queryGetListAttributeOutputDataName to set
	 */
	public void setQueryGetListAttributeOutputDataName(String queryGetListAttributeOutputDataName) {
		this.queryGetListAttributeOutputDataName = queryGetListAttributeOutputDataName;
	}

	/**
	 * @return the queryGetListFilterAttributeOrderByDirectionDefaultValue
	 */
	public GQLOrderByDirectionEnum getQueryGetListFilterAttributeOrderByDirectionDefaultValue() {
		return queryGetListFilterAttributeOrderByDirectionDefaultValue;
	}

	/**
	 * @param queryGetListFilterAttributeOrderByDirectionDefaultValue
	 *            the queryGetListFilterAttributeOrderByDirectionDefaultValue to
	 *            set
	 */
	public void setQueryGetListFilterAttributeOrderByDirectionDefaultValue(
			GQLOrderByDirectionEnum queryGetListFilterAttributeOrderByDirectionDefaultValue) {
		this.queryGetListFilterAttributeOrderByDirectionDefaultValue = queryGetListFilterAttributeOrderByDirectionDefaultValue;
	}

	/**
	 * @return the mutationDeleteResultAttributeId
	 */
	public String getMutationDeleteResultAttributeId() {
		return mutationDeleteResultAttributeId;
	}

	/**
	 * @param mutationDeleteResultAttributeId
	 *            the mutationDeleteResultAttributeId to set
	 */
	public void setMutationDeleteResultAttributeId(String mutationDeleteResultAttributeId) {
		this.mutationDeleteResultAttributeId = mutationDeleteResultAttributeId;
	}

	/**
	 * @return the mutationDeleteResultAttributeTypename
	 */
	public String getMutationDeleteResultAttributeTypename() {
		return mutationDeleteResultAttributeTypename;
	}

	/**
	 * @param mutationDeleteResultAttributeTypename
	 *            the mutationDeleteResultAttributeTypename to set
	 */
	public void setMutationDeleteResultAttributeTypename(String mutationDeleteResultAttributeTypename) {
		this.mutationDeleteResultAttributeTypename = mutationDeleteResultAttributeTypename;
	}

	/**
	 * @return the queryGetListFilterAttributeName
	 */
	public String getQueryGetListFilterAttributeName() {
		return queryGetListFilterAttributeName;
	}

	/**
	 * @param queryGetListFilterAttributeName
	 *            the queryGetListFilterAttributeName to set
	 */
	public void setQueryGetListFilterAttributeName(String queryGetListFilterAttributeName) {
		this.queryGetListFilterAttributeName = queryGetListFilterAttributeName;
	}

	/**
	 * @return the queryGetListFilterAttributeOperatorTypeNamePrefix
	 */
	public String getQueryGetListFilterAttributeOperatorTypeNamePrefix() {
		return queryGetListFilterAttributeOperatorTypeNamePrefix;
	}

	/**
	 * @param queryGetListFilterAttributeOperatorTypeNamePrefix
	 *            the queryGetListFilterAttributeOperatorTypeNamePrefix to set
	 */
	public void setQueryGetListFilterAttributeOperatorTypeNamePrefix(
			String queryGetListFilterAttributeOperatorTypeNamePrefix) {
		this.queryGetListFilterAttributeOperatorTypeNamePrefix = queryGetListFilterAttributeOperatorTypeNamePrefix;
	}

	/**
	 * @return the queryGetListFilterAttributeOperatorName
	 */
	public String getQueryGetListFilterAttributeOperatorName() {
		return queryGetListFilterAttributeOperatorName;
	}

	/**
	 * @param queryGetListFilterAttributeOperatorName
	 *            the queryGetListFilterAttributeOperatorName to set
	 */
	public void setQueryGetListFilterAttributeOperatorName(String queryGetListFilterAttributeOperatorName) {
		this.queryGetListFilterAttributeOperatorName = queryGetListFilterAttributeOperatorName;
	}

	/**
	 * @return the queryGetListFilterAttributeValueName
	 */
	public String getQueryGetListFilterAttributeValueName() {
		return queryGetListFilterAttributeValueName;
	}

	/**
	 * @param queryGetListFilterAttributeValueName
	 *            the queryGetListFilterAttributeValueName to set
	 */
	public void setQueryGetListFilterAttributeValueName(String queryGetListFilterAttributeValueName) {
		this.queryGetListFilterAttributeValueName = queryGetListFilterAttributeValueName;
	}

	/**
	 * @return the queryGetListFilterAttributeOrderByName
	 */
	public String getQueryGetListFilterAttributeOrderByName() {
		return queryGetListFilterAttributeOrderByName;
	}

	/**
	 * @param queryGetListFilterAttributeOrderByName
	 *            the queryGetListFilterAttributeOrderByName to set
	 */
	public void setQueryGetListFilterAttributeOrderByName(String queryGetListFilterAttributeOrderByName) {
		this.queryGetListFilterAttributeOrderByName = queryGetListFilterAttributeOrderByName;
	}

	/**
	 * @return the queryGetListFilterAttributeOrderByFieldName
	 */
	public String getQueryGetListFilterAttributeOrderByFieldName() {
		return queryGetListFilterAttributeOrderByFieldName;
	}

	/**
	 * @param queryGetListFilterAttributeOrderByFieldName
	 *            the queryGetListFilterAttributeOrderByFieldName to set
	 */
	public void setQueryGetListFilterAttributeOrderByFieldName(String queryGetListFilterAttributeOrderByFieldName) {
		this.queryGetListFilterAttributeOrderByFieldName = queryGetListFilterAttributeOrderByFieldName;
	}

	/**
	 * @return the queryGetListFilterAttributeOrderByDirectionName
	 */
	public String getQueryGetListFilterAttributeOrderByDirectionName() {
		return queryGetListFilterAttributeOrderByDirectionName;
	}

	/**
	 * @param queryGetListFilterAttributeOrderByDirectionName
	 *            the queryGetListFilterAttributeOrderByDirectionName to set
	 */
	public void setQueryGetListFilterAttributeOrderByDirectionName(
			String queryGetListFilterAttributeOrderByDirectionName) {
		this.queryGetListFilterAttributeOrderByDirectionName = queryGetListFilterAttributeOrderByDirectionName;
	}

	/**
	 * @return the queryGetListPagingAttributeName
	 */
	public String getQueryGetListPagingAttributeName() {
		return queryGetListPagingAttributeName;
	}

	/**
	 * @param queryGetListPagingAttributeName
	 *            the queryGetListPagingAttributeName to set
	 */
	public void setQueryGetListPagingAttributeName(String queryGetListPagingAttributeName) {
		this.queryGetListPagingAttributeName = queryGetListPagingAttributeName;
	}

	/**
	 * @return the queryGetListPagingAttributeTotalLengthName
	 */
	public String getQueryGetListPagingAttributeTotalLengthName() {
		return queryGetListPagingAttributeTotalLengthName;
	}

	/**
	 * @param queryGetListPagingAttributeTotalLengthName
	 *            the queryGetListPagingAttributeTotalLengthName to set
	 */
	public void setQueryGetListPagingAttributeTotalLengthName(String queryGetListPagingAttributeTotalLengthName) {
		this.queryGetListPagingAttributeTotalLengthName = queryGetListPagingAttributeTotalLengthName;
	}

	/**
	 * @return the queryGetListPagingAttributeOffsetName
	 */
	public String getQueryGetListPagingAttributeOffsetName() {
		return queryGetListPagingAttributeOffsetName;
	}

	/**
	 * @param queryGetListPagingAttributeOffsetName
	 *            the queryGetListPagingAttributeOffsetName to set
	 */
	public void setQueryGetListPagingAttributeOffsetName(String queryGetListPagingAttributeOffsetName) {
		this.queryGetListPagingAttributeOffsetName = queryGetListPagingAttributeOffsetName;
	}

	/**
	 * @return the queryGetListPagingAttributeLimitName
	 */
	public String getQueryGetListPagingAttributeLimitName() {
		return queryGetListPagingAttributeLimitName;
	}

	/**
	 * @param queryGetListPagingAttributeLimitName
	 *            the queryGetListPagingAttributeLimitName to set
	 */
	public void setQueryGetListPagingAttributeLimitName(String queryGetListPagingAttributeLimitName) {
		this.queryGetListPagingAttributeLimitName = queryGetListPagingAttributeLimitName;
	}

	/**
	 * @return the queryGetListPagingAttributeLimitDefaultValue
	 */
	public int getQueryGetListPagingAttributeLimitDefaultValue() {
		return queryGetListPagingAttributeLimitDefaultValue;
	}

	/**
	 * @param queryGetListPagingAttributeLimitDefaultValue
	 *            the queryGetListPagingAttributeLimitDefaultValue to set
	 */
	public void setQueryGetListPagingAttributeLimitDefaultValue(int queryGetListPagingAttributeLimitDefaultValue) {
		this.queryGetListPagingAttributeLimitDefaultValue = queryGetListPagingAttributeLimitDefaultValue;
	}

	/**
	 * @return the queryGetListFilterEntityTypeNameSuffix
	 */
	public String getQueryGetListFilterEntityTypeNameSuffix() {
		return queryGetListFilterEntityTypeNameSuffix;
	}

	/**
	 * @param queryGetListFilterEntityTypeNameSuffix
	 *            the queryGetListFilterEntityTypeNameSuffix to set
	 */
	public void setQueryGetListFilterEntityTypeNameSuffix(String queryGetListFilterEntityTypeNameSuffix) {
		this.queryGetListFilterEntityTypeNameSuffix = queryGetListFilterEntityTypeNameSuffix;
	}

	/**
	 * @return the concreteEmbeddedExtendingTypeNamePrefix
	 */
	public String getConcreteEmbeddedExtendingTypeNamePrefix() {
		return concreteEmbeddedExtendingTypeNamePrefix;
	}

	/**
	 * @param concreteEmbeddedExtendingTypeNamePrefix
	 *            the concreteEmbeddedExtendingTypeNamePrefix to set
	 */
	public void setConcreteEmbeddedExtendingTypeNamePrefix(String concreteEmbeddedExtendingTypeNamePrefix) {
		this.concreteEmbeddedExtendingTypeNamePrefix = concreteEmbeddedExtendingTypeNamePrefix;
	}

	/**
	 * @return the attributeIdName
	 */
	public String getAttributeIdName() {
		return attributeIdName;
	}

	/**
	 * @param attributeIdName
	 *            the attributeIdName to set
	 */
	public void setAttributeIdName(String attributeIdName) {
		this.attributeIdName = attributeIdName;
	}

}
