package com.daikit.graphql.test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
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

import com.daikit.graphql.constants.GQLSchemaConstants;
import com.daikit.graphql.data.input.GQLListLoadConfig;
import com.daikit.graphql.data.output.GQLDeleteResult;
import com.daikit.graphql.data.output.GQLExecutionResult;
import com.daikit.graphql.data.output.GQLListLoadResult;
import com.daikit.graphql.datafetcher.GQLAbstractDeleteDataFetcher;
import com.daikit.graphql.datafetcher.GQLAbstractGetByIdDataFetcher;
import com.daikit.graphql.datafetcher.GQLAbstractGetListDataFetcher;
import com.daikit.graphql.datafetcher.GQLAbstractSaveDataFetcher;
import com.daikit.graphql.datafetcher.GQLCustomMethodDataFetcher;
import com.daikit.graphql.datafetcher.GQLDynamicAttributeRegistry;
import com.daikit.graphql.datafetcher.GQLPropertyDataFetcher;
import com.daikit.graphql.dynamicattribute.IGQLDynamicAttributeFilter;
import com.daikit.graphql.dynamicattribute.IGQLDynamicAttributeSetter;
import com.daikit.graphql.execution.GQLErrorProcessor;
import com.daikit.graphql.execution.GQLExecutor;
import com.daikit.graphql.introspection.GQLIntrospection;
import com.daikit.graphql.test.data.DataModel;
import com.daikit.graphql.test.data.GQLMetaData;
import com.daikit.graphql.utils.GQLPropertyUtils;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import graphql.ExecutionResult;
import graphql.schema.DataFetcher;

/**
 * Super class for all tests. It initialize a data entity , a graphQL meta
 * entity and a graphQL schema
 *
 * @author Thibaut Caselli
 *
 */
public abstract class AbstractTestSuite {

	protected final Logger logger = LoggerFactory.getLogger(getClass());

	private GQLExecutionResult schemaIntrospection;

	protected DataModel dataModel;

	protected static GQLExecutor EXECUTOR;
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
	public void createExecutor() {
		logger.info("Initialize test graphQL schema & data entity");
		EXECUTOR = new GQLExecutor(GQLMetaData.buildMetaModel(), new GQLErrorProcessor(), createGetByIdDataFetcher(),
				createListDataFetcher(Collections.emptyList()), createSaveDataFetchers(), createDeleteDataFetcher(),
				createCustomMethodsDataFetcher(), createPropertyDataFetchers());
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

	protected GQLExecutionResult handleErrors(GQLExecutionResult result) {
		if (result.getErrorDetails() != null) {
			result.getErrors().forEach(error -> System.err.println(error));
			Assert.fail("Error(s) happened during graphql execution. " + result.getErrorDetails().getMessage());
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

	protected GQLExecutionResult getSchemaIntrospection() {
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
	protected <T> T getResultDataProperty(GQLExecutionResult executionResult, String path) {
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

	private DataFetcher<?> createGetByIdDataFetcher() {
		return new GQLAbstractGetByIdDataFetcher() {

			@Override
			protected Object getById(Class<?> entityClass, String id) {
				return dataModel.getById(entityClass, id);
			}

		};
	}

	private DataFetcher<GQLListLoadResult> createListDataFetcher(
			List<IGQLDynamicAttributeFilter<?, ?, ?>> dynamicAttributeFilters) {
		return new GQLAbstractGetListDataFetcher(dynamicAttributeFilters) {

			@Override
			protected GQLListLoadResult getAll(Class<?> entityClass, GQLListLoadConfig listLoadConfig) {
				return dataModel.getAll(entityClass, listLoadConfig);
			}

			@Override
			protected Object getById(Class<?> entityClass, String id) {
				return dataModel.getById(entityClass, id);
			}

		};
	}

	private DataFetcher<?> createSaveDataFetchers() {
		return new GQLAbstractSaveDataFetcher<Object>() {

			@Override
			protected void save(Object entity) {
				dataModel.save(entity);
			}

			@SuppressWarnings("unchecked")
			@Override
			protected Object getOrCreateAndSetProperties(Class<?> entityClass,
					GQLDynamicAttributeRegistry dynamicAttributeRegistry, Map<String, Object> fieldValueMap) {
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
					final Optional<IGQLDynamicAttributeSetter<Object, Object>> dynamicAttributeSetter = dynamicAttributeRegistry
							.getSetter(entityClass, entry.getKey());
					if (!GQLSchemaConstants.FIELD_ID.equals(entry.getKey())) {
						Object value = entry.getValue();
						if (entry.getValue() instanceof Map) {
							final Class<?> propertyType = GQLPropertyUtils.getPropertyType(entity.getClass(),
									entry.getKey());
							value = getOrCreateAndSetProperties(propertyType, dynamicAttributeRegistry,
									(Map<String, Object>) entry.getValue());
						}
						if (dynamicAttributeSetter.isPresent()) {
							dynamicAttributeSetter.get().setValue(entity, value);
						} else {
							GQLPropertyUtils.setPropertyValue(entity, entry.getKey(), value);
						}
					}
				});
				return entity;
			}
		};
	}

	private DataFetcher<GQLDeleteResult> createDeleteDataFetcher() {
		return new GQLAbstractDeleteDataFetcher() {
			@Override
			protected void delete(Class<?> entityClass, String id) {
				dataModel.delete(entityClass, id);
			}
		};
	}

	private DataFetcher<?> createCustomMethodsDataFetcher() {
		return new GQLCustomMethodDataFetcher();
	}

	private List<GQLPropertyDataFetcher<?>> createPropertyDataFetchers() {
		return Collections.emptyList();
	}

}
