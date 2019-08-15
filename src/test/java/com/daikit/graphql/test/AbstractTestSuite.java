package com.daikit.graphql.test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.daikit.graphql.builder.GQLSchemaBuilder;
import com.daikit.graphql.constants.GQLSchemaConstants;
import com.daikit.graphql.data.input.GQLListLoadConfig;
import com.daikit.graphql.data.output.GQLDeleteResult;
import com.daikit.graphql.data.output.GQLListLoadResult;
import com.daikit.graphql.datafetcher.GQLPropertyDataFetcher;
import com.daikit.graphql.datafetcher.abs.GQLCustomMethodDataFetcher;
import com.daikit.graphql.dynamicattribute.IGQLDynamicAttributeFilter;
import com.daikit.graphql.dynamicattribute.IGQLDynamicAttributeSetter;
import com.daikit.graphql.datafetcher.abs.GQLAbstractDeleteDataFetcher;
import com.daikit.graphql.datafetcher.abs.GQLAbstractGetByIdDataFetcher;
import com.daikit.graphql.datafetcher.abs.GQLAbstractGetListDataFetcher;
import com.daikit.graphql.datafetcher.abs.GQLAbstractSaveDataFetcher;
import com.daikit.graphql.introspection.GQLIntrospection;
import com.daikit.graphql.meta.GQLMetaDataModel;
import com.daikit.graphql.test.data.DataModel;
import com.daikit.graphql.test.data.Entity1;
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
 * Super class for all tests. It initialize a data entity , a graphQL meta
 * entity and a graphQL schema
 *
 * @author Thibaut Caselli
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
		logger.info("Initialize test graphQL schema & data entity");
		final GQLMetaDataModel metaDataModel = GQLMetaData.buildMetaDataModel();
		final GraphQLSchema schema = new GQLSchemaBuilder().buildSchema(metaDataModel, createGetSingleDataFetcher(),
				createListDataFetcher(Collections.emptyList()), createSaveDataFetchers(), createDeleteDataFetcher(),
				createCustomMethodsDataFetcher(), createPropertyDataFetchers());
		EXECUTOR = GraphQL.newGraphQL(schema).build();
		resetDataModel();
	}

	protected String readGraphql(String fileName) {
		try {
			final InputStream stream = AbstractTestSuite.class.getResourceAsStream(fileName);
			return IOUtils.toString(stream, "UTF-8");
		} catch (final IOException e) {
			throw new RuntimeException(e);
		}
	}

	protected ExecutionResult handleErrors(ExecutionResult result) {
		if (!result.getErrors().isEmpty()) {
			result.getErrors().forEach(error -> System.err.println(error));
			Assert.fail("Error(s) happened during graphql execution, see log for details");
		}
		return result;
	}

	/**
	 * Data entity initialization before each test
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

	@SuppressWarnings("unchecked")
	protected Map<String, Object> toMap(Object object) {
		return MAPPER.convertValue(object, Map.class);
	}

	protected <T> T toObject(ExecutionResult executionResult, Class<T> expectedType, String property) {
		return toObject(executionResult.<Map<String, Object>>getData().get(property), expectedType);
	}

	protected <T> T toObject(ExecutionResult executionResult, Class<T> expectedType) {
		return toObject(executionResult.<Map<String, Object>>getData().values().iterator().next(), expectedType);
	}

	protected <T> T toObject(Object object, Class<T> expectedType) {
		return MAPPER.convertValue(object, expectedType);
	}

	@SuppressWarnings("unchecked")
	protected <T> T getResultDataProperty(ExecutionResult executionResult, String path) {
		final String[] pathFragments = path.split("\\.");
		Object value = executionResult.<Map<String, Object>>getData().values().iterator().next();
		for (final String pathFragment : pathFragments) {
			value = ((Map<String, Object>) value).get(pathFragment);
		}
		return (T) value;
	}

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// PRIVATE METHODS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	private Class<?> getClassByName(String entityName) {
		try {
			return Class.forName(Entity1.class.getPackage().getName() + "." + entityName);
		} catch (final ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	private DataFetcher<?> createGetSingleDataFetcher() {
		return new GQLAbstractGetByIdDataFetcher() {

			@Override
			protected Object runGet(String entityName, String id) {
				return dataModel.getById(getClassByName(entityName), id);
			}

		};
	}

	private DataFetcher<GQLListLoadResult> createListDataFetcher(
			@SuppressWarnings("rawtypes") List<IGQLDynamicAttributeFilter> dynamicAttributeFilters) {
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
			protected Object findOrCreateEntity(String entityName,
					Map<String, IGQLDynamicAttributeSetter<Object, Object>> dynamicAttributeSetters,
					Map<String, Object> fieldValueMap) {
				final Class<?> entityClass = getClassByName(entityName);
				// Find or create entity
				final String id = (String) fieldValueMap.get(GQLSchemaConstants.FIELD_ID);
				final Optional<?> existing = StringUtils.isEmpty(id)
						? Optional.empty()
						: dataModel.getById(entityClass, id);
				Object entity;
				try {
					entity = existing.isPresent() ? existing.get() : entityClass.newInstance();
				} catch (InstantiationException | IllegalAccessException e) {
					throw new RuntimeException(e);
				}
				// Set properties
				fieldValueMap.entrySet().stream().forEach(entry -> {
					final IGQLDynamicAttributeSetter<Object, Object> dynamicAttributeSetter = dynamicAttributeSetters
							.get(entry.getKey());
					if (dynamicAttributeSetter == null) {
						PropertyUtils.setPropertyValue(entity, entry.getKey(), entry.getValue());
					} else {
						dynamicAttributeSetter.setValue(entity, entry.getValue());
					}
				});
				return entity;
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

	private DataFetcher<?> createCustomMethodsDataFetcher() {
		return new GQLCustomMethodDataFetcher();
	}

	private List<GQLPropertyDataFetcher<?>> createPropertyDataFetchers() {
		final List<GQLPropertyDataFetcher<?>> propertyDataFetchers = new ArrayList<>();
		return propertyDataFetchers;
	}

}
