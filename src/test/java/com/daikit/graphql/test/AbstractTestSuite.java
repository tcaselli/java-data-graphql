package com.daikit.graphql.test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.daikit.graphql.builder.GQLSchemaBuilder;
import com.daikit.graphql.constants.GQLSchemaConstants;
import com.daikit.graphql.datafetcher.GQLMethodDataFetcher;
import com.daikit.graphql.datafetcher.GQLPropertyDataFetcher;
import com.daikit.graphql.datafetcher.abs.GQLAbstractDeleteDataFetcher;
import com.daikit.graphql.datafetcher.abs.GQLAbstractGetListDataFetcher;
import com.daikit.graphql.datafetcher.abs.GQLAbstractGetSingleDataFetcher;
import com.daikit.graphql.datafetcher.abs.GQLAbstractSaveDataFetcher;
import com.daikit.graphql.introspection.GQLIntrospection;
import com.daikit.graphql.meta.GQLMetaDataModel;
import com.daikit.graphql.meta.dynamic.attribute.GQLDynamicAttributeFilter;
import com.daikit.graphql.query.input.GQLListLoadConfig;
import com.daikit.graphql.query.output.GQLDeleteResult;
import com.daikit.graphql.query.output.GQLListLoadResult;
import com.daikit.graphql.test.data.DataModel;
import com.daikit.graphql.test.data.GQLMetaData;
import com.daikit.graphql.test.utils.PropertyUtils;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import graphql.ExecutionResult;
import graphql.GraphQL;
import graphql.schema.DataFetcher;
import graphql.schema.GraphQLSchema;

/**
 * Super class for all tests. It initialize a data model , a graphQL meta model
 * and a graphQL schema
 *
 * @author tcaselli
 *
 */
public abstract class AbstractTestSuite {

	protected final Logger logger = LoggerFactory.getLogger(getClass());

	private ExecutionResult schemaIntrospection;

	protected DataModel dataModel;

	protected static GraphQL EXECUTOR;
	protected static ObjectMapper MAPPER;
	protected static ObjectWriter WRITER_PRETTY;

	static {
		MAPPER = new ObjectMapper();
		MAPPER.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
		MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		MAPPER.configure(MapperFeature.PROPAGATE_TRANSIENT_MARKER, true);
		WRITER_PRETTY = MAPPER.writer(new DefaultPrettyPrinter());
	}

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// INITIALIZATION METHODS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	/**
	 * Initialize schema
	 */
	@Before
	public void initializeSchema() {
		logger.info("Initialize test graphQL schema & data model");
		final GQLMetaDataModel metaDataModel = GQLMetaData.buildMetaDataModel();
		final GraphQLSchema schema = new GQLSchemaBuilder().buildSchema(metaDataModel, createGetSingleDataFetcher(),
				createListDataFetcher(Collections.emptyList()), createSaveDataFetchers(), createDeleteDataFetcher(),
				createPropertyDataFetchers(), createCustomMethodsDataFetchers());
		EXECUTOR = GraphQL.newGraphQL(schema).build();
		resetDataModel();
	}

	/**
	 * Data model initialization before each test
	 *
	 * @throws FileNotFoundException
	 *             if file not found
	 * @throws IOException
	 *             if file not readable
	 */
	public void resetDataModel() {
		try {
			dataModel = new DataModel();
		} catch (final IOException e) {
			throw new RuntimeException(e);
		}
	}

	protected ExecutionResult getSchemaIntrospection() {
		if (schemaIntrospection == null) {
			schemaIntrospection = GQLIntrospection.getAllTypes(query -> EXECUTOR.execute(query));
		}
		return schemaIntrospection;
	}

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// PRIVATE METHODS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	private Class<?> getClassByName(String entityName) {
		try {
			return Class.forName(entityName);
		} catch (final ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	private DataFetcher<?> createGetSingleDataFetcher() {
		return new GQLAbstractGetSingleDataFetcher() {

			@Override
			protected Object runGet(String entityName, String id) {
				return dataModel.getById(getClassByName(entityName), id);
			}

		};
	}

	private DataFetcher<GQLListLoadResult> createListDataFetcher(
			@SuppressWarnings("rawtypes") List<GQLDynamicAttributeFilter> dynamicAttributeFilters) {
		return new GQLAbstractGetListDataFetcher(dynamicAttributeFilters) {

			@Override
			protected GQLListLoadResult runGetAll(String entityName, GQLListLoadConfig listLoadConfig) {
				return dataModel.getAll(getClassByName(entityName), listLoadConfig);
			}

			@Override
			protected Object getById(String entityName, String id) {
				return dataModel.getById(getClassByName(entityName), id);
			}

		};
	}

	private DataFetcher<?> createSaveDataFetchers() {
		return new GQLAbstractSaveDataFetcher() {

			@Override
			protected void runSave(Object entity) {
				dataModel.save(entity);
			}

			@Override
			protected Object findOrCreateEntity(String entityName, Map<String, Object> fieldMap) {
				final Class<?> entityClass = getClassByName(entityName);
				// Find or create model
				final String id = (String) fieldMap.get(GQLSchemaConstants.FIELD_ID);
				final Optional<?> existing = StringUtils.isEmpty(id)
						? Optional.empty()
						: dataModel.getById(entityClass, id);
				Object model;
				try {
					model = existing.isPresent() ? existing.get() : entityClass.newInstance();
				} catch (InstantiationException | IllegalAccessException e) {
					throw new RuntimeException(e);
				}
				// Set properties
				fieldMap.entrySet().stream().forEach(entry -> {
					PropertyUtils.setPropertyValue(model, entry.getKey(), entry.getValue());
				});
				return model;
			}

		};
	}

	private DataFetcher<GQLDeleteResult> createDeleteDataFetcher() {
		return new GQLAbstractDeleteDataFetcher() {

			@Override
			protected void runDelete(String entityName, String id) {
				dataModel.delete(getClassByName(entityName), id);
			}

		};
	}

	private List<GQLPropertyDataFetcher<?>> createPropertyDataFetchers() {
		final List<GQLPropertyDataFetcher<?>> propertyDataFetchers = new ArrayList<>();
		return propertyDataFetchers;
	}

	private List<GQLMethodDataFetcher> createCustomMethodsDataFetchers() {
		final List<GQLMethodDataFetcher> customMethodDataFetchers = new ArrayList<>();
		return customMethodDataFetchers;
	}

}
