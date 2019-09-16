package com.daikit.graphql.builder;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.daikit.graphql.config.GQLSchemaConfig;
import com.daikit.graphql.utils.Message;

import graphql.schema.GraphQLCodeRegistry;
import graphql.schema.GraphQLEnumType;
import graphql.schema.GraphQLInputObjectType;
import graphql.schema.GraphQLInterfaceType;
import graphql.schema.GraphQLObjectType;
import graphql.schema.GraphQLOutputType;
import graphql.schema.GraphQLScalarType;

/**
 * Schema builder cache shared by all schema fragment builders
 *
 * @author Thibaut Caselli
 */
public class GQLSchemaBuilderCache {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	private final GQLSchemaConfig schemaConfig;

	// Type references for outputs
	private final Map<Class<?>, String> typeReferences = new HashMap<>();
	private final Map<Class<?>, GraphQLEnumType> enumTypes = new HashMap<>();
	private final Map<Class<?>, GraphQLInterfaceType> interfaceTypes = new HashMap<>();
	private final Map<Class<?>, GraphQLObjectType> objectTypes = new HashMap<>();
	// Type references for inputs
	private final Map<Class<?>, GraphQLInputObjectType> inputEntityTypes = new HashMap<>();

	// Mutations utility types
	private GraphQLOutputType deleteResultOutputObjectType;

	// Queries utility types
	private GraphQLEnumType orderByDirectionEnumType;
	private GraphQLInputObjectType pagingInputObjectType;
	private GraphQLInputObjectType orderByInputObjectType;
	private final Map<String, GraphQLInputObjectType> inputScalarFilterOperators = new HashMap<>();
	private final Map<Class<?>, GraphQLInputObjectType> inputEnumFilterOperators = new HashMap<>();
	private GraphQLOutputType pagingOutputObjectType;
	private GraphQLOutputType orderByOutputObjectType;

	// Data fetcher code registry
	private GraphQLCodeRegistry.Builder codeRegistryBuilder;

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// CONSTRUCTORS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	/**
	 * Constructor
	 *
	 * @param schemaConfig
	 *            the {@link GQLSchemaConfig}
	 */
	public GQLSchemaBuilderCache(GQLSchemaConfig schemaConfig) {
		this.schemaConfig = schemaConfig;
	}

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// METHODS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	/**
	 * Get cached {@link GraphQLEnumType} by its related enum class
	 *
	 * @param enumClass
	 *            the enum class
	 * @return the found {@link GraphQLEnumType}
	 * @throws IllegalArgumentException
	 *             if not found
	 */
	public GraphQLEnumType getEnumType(final Class<?> enumClass) {
		final GraphQLEnumType enumType = enumTypes.get(enumClass);
		if (enumType == null) {
			throw new IllegalArgumentException(Message.format("No enum type defined for enum class [{}]", enumClass));
		}
		return enumType;
	}

	/**
	 * Get cached {@link GraphQLObjectType} by its related entity class
	 *
	 * @param entityClass
	 *            the entity class
	 * @return the found {@link GraphQLObjectType}
	 * @throws IllegalArgumentException
	 *             if not found
	 */
	public GraphQLObjectType getEntityType(final Class<?> entityClass) {
		final GraphQLObjectType objectType = objectTypes.get(entityClass);
		if (objectType == null) {
			throw new IllegalArgumentException(
					Message.format("No entity type defined for entity class [{}]", entityClass));
		}
		return objectType;
	}

	/**
	 * Get cached {@link GraphQLInterfaceType} by its related entity class
	 *
	 * @param entityClass
	 *            the entity class
	 * @return the found {@link GraphQLInterfaceType}
	 * @throws IllegalArgumentException
	 *             if not found
	 */
	public GraphQLInterfaceType getInterfaceType(final Class<?> entityClass) {
		final GraphQLInterfaceType interfaceType = interfaceTypes.get(entityClass);
		if (interfaceType == null) {
			throw new IllegalArgumentException(
					Message.format("No interface type defined for entity class [{}]", entityClass));
		}
		return interfaceType;
	}

	/**
	 * Get cached {@link GraphQLScalarType} by its related scalar type code
	 *
	 * @param scalarTypeCode
	 *            the scalar type code
	 * @return the found {@link GraphQLScalarType}
	 * @throws IllegalArgumentException
	 *             if not found
	 */
	public GraphQLScalarType getScalarType(final String scalarTypeCode) {
		final Optional<GraphQLScalarType> scalarType = schemaConfig.getScalarType(scalarTypeCode);
		if (!scalarType.isPresent()) {
			throw new IllegalArgumentException(
					Message.format("No scalar type defined for scalar type code [{}]", scalarTypeCode));
		}
		return scalarType.get();
	}

	/**
	 * Get cached entity type name by its related entity class
	 *
	 * @param entityClass
	 *            the entity class
	 * @return the found type name
	 * @throws IllegalArgumentException
	 *             if not found
	 */
	public String getEntityTypeName(final Class<?> entityClass) {
		final String typeName = typeReferences.get(entityClass);
		if (typeName == null) {
			throw new IllegalArgumentException(
					Message.format("No entity type name defined for entity class [{}]", entityClass));
		}
		return typeName;
	}

	/**
	 * Get cached input save entity type name by its related entity class
	 *
	 * @param entityClass
	 *            the entity class
	 * @return the found {@link GraphQLInputObjectType}
	 * @throws IllegalArgumentException
	 *             if not found
	 */
	public GraphQLInputObjectType getInputEntityType(final Class<?> entityClass) {
		final GraphQLInputObjectType objectType = getInputEntityTypes().get(entityClass);
		if (objectType == null) {
			throw new IllegalArgumentException(
					Message.format("No input entity type defined for entity class [{}]", entityClass));
		}
		return objectType;
	}

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// GETTERS / SETTERS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	/**
	 * @return the logger
	 */
	public Logger getLogger() {
		return logger;
	}

	/**
	 * @return the typeReferences
	 */
	public Map<Class<?>, String> getTypeReferences() {
		return typeReferences;
	}

	/**
	 * @return the enumTypes
	 */
	public Map<Class<?>, GraphQLEnumType> getEnumTypes() {
		return enumTypes;
	}

	/**
	 * @return the interfaceTypes
	 */
	public Map<Class<?>, GraphQLInterfaceType> getInterfaceTypes() {
		return interfaceTypes;
	}

	/**
	 * @return the objectTypes
	 */
	public Map<Class<?>, GraphQLObjectType> getObjectTypes() {
		return objectTypes;
	}

	/**
	 * @return the inputEntityTypes
	 */
	public Map<Class<?>, GraphQLInputObjectType> getInputEntityTypes() {
		return inputEntityTypes;
	}

	/**
	 * @return the orderByDirectionEnumType
	 */
	public GraphQLEnumType getOrderByDirectionEnumType() {
		return orderByDirectionEnumType;
	}

	/**
	 * @param orderByDirectionEnumType
	 *            the orderByDirectionEnumType to set
	 */
	public void setOrderByDirectionEnumType(final GraphQLEnumType orderByDirectionEnumType) {
		this.orderByDirectionEnumType = orderByDirectionEnumType;
	}

	/**
	 * @return the pagingInputObjectType
	 */
	public GraphQLInputObjectType getPagingInputObjectType() {
		return pagingInputObjectType;
	}

	/**
	 * @param pagingInputObjectType
	 *            the pagingInputObjectType to set
	 */
	public void setPagingInputObjectType(final GraphQLInputObjectType pagingInputObjectType) {
		this.pagingInputObjectType = pagingInputObjectType;
	}

	/**
	 * @return the orderByInputObjectType
	 */
	public GraphQLInputObjectType getOrderByInputObjectType() {
		return orderByInputObjectType;
	}

	/**
	 * @param orderByInputObjectType
	 *            the orderByInputObjectType to set
	 */
	public void setOrderByInputObjectType(final GraphQLInputObjectType orderByInputObjectType) {
		this.orderByInputObjectType = orderByInputObjectType;
	}

	/**
	 * @return the pagingOutputObjectType
	 */
	public GraphQLOutputType getPagingOutputObjectType() {
		return pagingOutputObjectType;
	}

	/**
	 * @param pagingOutputObjectType
	 *            the pagingOutputObjectType to set
	 */
	public void setPagingOutputObjectType(final GraphQLOutputType pagingOutputObjectType) {
		this.pagingOutputObjectType = pagingOutputObjectType;
	}

	/**
	 * @return the orderByOutputObjectType
	 */
	public GraphQLOutputType getOrderByOutputObjectType() {
		return orderByOutputObjectType;
	}

	/**
	 * @return the inputScalarFilterOperators
	 */
	public Map<String, GraphQLInputObjectType> getInputScalarFilterOperators() {
		return inputScalarFilterOperators;
	}

	/**
	 * @return the inputEnumFilterOperators
	 */
	public Map<Class<?>, GraphQLInputObjectType> getInputEnumFilterOperators() {
		return inputEnumFilterOperators;
	}

	/**
	 * @return the codeRegistryBuilder
	 */
	public GraphQLCodeRegistry.Builder getCodeRegistryBuilder() {
		return codeRegistryBuilder;
	}

	/**
	 * @param codeRegistryBuilder
	 *            the codeRegistryBuilder to set
	 */
	public void setCodeRegistryBuilder(GraphQLCodeRegistry.Builder codeRegistryBuilder) {
		this.codeRegistryBuilder = codeRegistryBuilder;
	}

	/**
	 * @return the deleteResultOutputObjectType
	 */
	public GraphQLOutputType getDeleteResultOutputObjectType() {
		return deleteResultOutputObjectType;
	}

	/**
	 * @param deleteResultOutputObjectType
	 *            the deleteResultOutputObjectType to set
	 */
	public void setDeleteResultOutputObjectType(GraphQLOutputType deleteResultOutputObjectType) {
		this.deleteResultOutputObjectType = deleteResultOutputObjectType;
	}

	/**
	 * @param orderByOutputObjectType
	 *            the orderByOutputObjectType to set
	 */
	public void setOrderByOutputObjectType(GraphQLOutputType orderByOutputObjectType) {
		this.orderByOutputObjectType = orderByOutputObjectType;
	}

	/**
	 * @return the schemaConfig
	 */
	public GQLSchemaConfig getConfig() {
		return schemaConfig;
	}

}
