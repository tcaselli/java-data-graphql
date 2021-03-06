package com.daikit.graphql.builder.types;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;

import com.daikit.graphql.builder.GQLAbstractSchemaSubBuilder;
import com.daikit.graphql.builder.GQLExecutionContext;
import com.daikit.graphql.builder.GQLSchemaBuilderCache;
import com.daikit.graphql.builder.GQLSchemaBuilderUtils;
import com.daikit.graphql.datafetcher.GQLDynamicAttributeDataFetcher;
import com.daikit.graphql.datafetcher.GQLPropertyDataFetcher;
import com.daikit.graphql.meta.attribute.GQLAbstractAttributeMetaData;
import com.daikit.graphql.meta.attribute.GQLAttributeEntityMetaData;
import com.daikit.graphql.meta.attribute.GQLAttributeEnumMetaData;
import com.daikit.graphql.meta.attribute.GQLAttributeListEntityMetaData;
import com.daikit.graphql.meta.attribute.GQLAttributeListEnumMetaData;
import com.daikit.graphql.meta.attribute.GQLAttributeListScalarMetaData;
import com.daikit.graphql.meta.attribute.GQLAttributeScalarMetaData;
import com.daikit.graphql.meta.entity.GQLEntityMetaData;
import com.daikit.graphql.utils.Message;

import graphql.Scalars;
import graphql.schema.GraphQLFieldDefinition;
import graphql.schema.GraphQLFieldsContainer;
import graphql.schema.GraphQLList;
import graphql.schema.GraphQLOutputType;
import graphql.schema.GraphQLTypeReference;

/**
 * Abstract super class for schema types builders
 *
 * @author Thibaut Caselli
 */
public class GQLAbstractTypesBuilder extends GQLAbstractSchemaSubBuilder {

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// CONSTRUCTORS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	/**
	 * Constructor
	 *
	 * @param cache
	 *            the {@link GQLSchemaBuilderCache}
	 */
	public GQLAbstractTypesBuilder(final GQLSchemaBuilderCache cache) {
		super(cache);
	}

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// METHODS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	protected Map<GQLAbstractAttributeMetaData, GraphQLFieldDefinition> buildEntityFieldDefinitions(
			GQLExecutionContext executionContext, final GQLEntityMetaData entity) {
		logger.debug(Message.format("Build field definitions for entity/interface [{}]", entity.getName()));
		final Map<GQLAbstractAttributeMetaData, GraphQLFieldDefinition> fieldDefinitions = entity.getAttributes()
				.stream().filter(attribute -> attribute.isReadable(executionContext)).collect(LinkedHashMap::new,
						(map, attribute) -> map.put(attribute, buildEntityFieldDefinition(attribute)), Map::putAll);
		return fieldDefinitions;
	}

	protected GraphQLFieldDefinition buildIdFieldDefinition() {
		final GraphQLFieldDefinition.Builder builder = GraphQLFieldDefinition.newFieldDefinition();
		builder.name(getConfig().getAttributeIdName());
		builder.description("Field [" + getConfig().getAttributeIdName() + "]");
		builder.type(Scalars.GraphQLID);
		return builder.build();
	}

	protected void registerIdDataFetcher(final GraphQLFieldsContainer fieldsContainer,
			final GraphQLFieldDefinition idFieldDefinition,
			final List<GQLPropertyDataFetcher<?>> propertiesDataFetchers) {
		if (idFieldDefinition != null) {
			final Optional<GQLPropertyDataFetcher<?>> dataFetcher = propertiesDataFetchers.stream()
					.filter(df -> getConfig().getAttributeIdName().equals(df.getGraphQLPropertyName())).findFirst();
			if (dataFetcher.isPresent()) {
				getCache().getCodeRegistryBuilder().dataFetcher(fieldsContainer, idFieldDefinition, dataFetcher.get());
			}
		}
	}

	protected void registerOtherDataFetchers(final GraphQLFieldsContainer fieldsContainer,
			final Map<GQLAbstractAttributeMetaData, GraphQLFieldDefinition> fieldDefinitions,
			final List<GQLPropertyDataFetcher<?>> propertiesDataFetchers) {
		fieldDefinitions.entrySet().stream().forEach(entry -> {
			if (entry.getKey().isDynamic()) {
				if (entry.getKey().getDynamicAttributeGetter() != null) {
					getCache().getCodeRegistryBuilder().dataFetcher(fieldsContainer, entry.getValue(),
							new GQLDynamicAttributeDataFetcher(entry.getKey().getName(),
									entry.getKey().getDynamicAttributeGetter()));
				}
			} else {
				final Optional<GQLPropertyDataFetcher<?>> dataFetcher = propertiesDataFetchers.stream()
						.filter(propertyDataFetcher -> Objects.equals(entry.getKey().getName(),
								propertyDataFetcher.getEntityPropertyName()))
						.findFirst();
				if (dataFetcher.isPresent()) {
					getCache().getCodeRegistryBuilder().dataFetcher(fieldsContainer, entry.getValue(),
							dataFetcher.get());
				}
			}
		});
	}

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// PRIVATE UTILS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	private GraphQLFieldDefinition buildEntityFieldDefinition(final GQLAbstractAttributeMetaData attribute) {
		logger.debug(Message.format("Build field definition for attribute [{}]", attribute.getName()));
		GraphQLFieldDefinition fieldDefinition = null;
		// Set attribute type
		if (attribute instanceof GQLAttributeScalarMetaData) {
			fieldDefinition = buildFieldDefinition(attribute,
					getCache().getScalarType(((GQLAttributeScalarMetaData) attribute).getScalarType()));
		} else if (attribute instanceof GQLAttributeEnumMetaData) {
			fieldDefinition = buildFieldDefinition(attribute,
					getCache().getEnumType(((GQLAttributeEnumMetaData) attribute).getEnumClass()));
		} else if (attribute instanceof GQLAttributeEntityMetaData) {
			fieldDefinition = buildFieldDefinition(attribute, new GraphQLTypeReference(
					getCache().getEntityTypeName(((GQLAttributeEntityMetaData) attribute).getEntityClass())));
		} else if (attribute instanceof GQLAttributeListEnumMetaData) {
			fieldDefinition = buildFieldDefinition(attribute,
					new GraphQLList(getCache().getEnumType(((GQLAttributeListEnumMetaData) attribute).getEnumClass())));
		} else if (attribute instanceof GQLAttributeListEntityMetaData) {
			fieldDefinition = buildFieldDefinition(attribute, new GraphQLList(new GraphQLTypeReference(
					getCache().getEntityTypeName(((GQLAttributeListEntityMetaData) attribute).getForeignClass()))));
		} else if (attribute instanceof GQLAttributeListScalarMetaData) {
			fieldDefinition = buildFieldDefinition(attribute, new GraphQLList(
					getCache().getScalarType(((GQLAttributeListScalarMetaData) attribute).getScalarType())));
		} else {
			throw new IllegalArgumentException(
					Message.format("Attribute could not be mapped to GraphQL [{}]", attribute));
		}
		return fieldDefinition;
	}

	private GraphQLFieldDefinition buildFieldDefinition(final GQLAbstractAttributeMetaData attribute,
			final GraphQLOutputType type) {
		final GraphQLFieldDefinition.Builder builder = GraphQLFieldDefinition.newFieldDefinition();
		builder.name(attribute.getName());
		builder.description("Field [" + attribute.getName() + "]"
				+ (StringUtils.isNotEmpty(attribute.getDescription()) ? " : " + attribute.getDescription() : ""));
		builder.type(type);
		logger.debug(Message.format("Field definition created for [{}] with type [{}]", attribute.getName(),
				GQLSchemaBuilderUtils.typeToString(type)));
		return builder.build();
	}
}
