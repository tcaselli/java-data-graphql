package com.daikit.graphql.test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.BeforeClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.daikit.graphql.builder.GQLSchemaBuilder;
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
import com.daikit.graphql.test.test.data.DataModel;
import com.daikit.graphql.test.test.data.GQLMetaData;

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

	protected DataModel dataModel;
	protected static GraphQL executor;

	private ExecutionResult schemaIntrospection;

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// INITIALIZATION METHODS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	/**
	 * Initialize schema
	 */
	@BeforeClass
	public void initializeSchema() {
		logger.info("Initialize test graphQL schema");
		final GQLMetaDataModel metaDataModel = GQLMetaData.buildMetaDataModel();
		final GraphQLSchema schema = new GQLSchemaBuilder().buildSchema(metaDataModel, createGetSingleDataFetcher(),
				createListDataFetcher(Collections.emptyList()), createSaveDataFetchers(), createDeleteDataFetcher(),
				createPropertyDataFetchers(), createCustomMethodsDataFetchers());
		executor = GraphQL.newGraphQL(schema).build();
	}

	/**
	 * Data model initialization before each test
	 *
	 * @throws FileNotFoundException
	 *             if file not found
	 * @throws IOException
	 *             if file not readable
	 */
	@Before
	public void resetDataModel() throws FileNotFoundException, IOException {
		logger.info("Initialize test data model");
		dataModel = new DataModel();
	}

	protected ExecutionResult getSchemaIntrospection() {
		if (schemaIntrospection == null) {
			schemaIntrospection = GQLIntrospection.getAllTypes(query -> executor.execute(query));
		}
		return schemaIntrospection;
	}

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// PRIVATE METHODS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	private DataFetcher<?> createGetSingleDataFetcher() {
		return new GQLAbstractGetSingleDataFetcher() {
			@Override
			protected Object runGet(String entityName, String id) {
				return null;
			}
		};
	}

	private DataFetcher<GQLListLoadResult> createListDataFetcher(
			@SuppressWarnings("rawtypes") List<GQLDynamicAttributeFilter> dynamicAttributeFilters) {
		return new GQLAbstractGetListDataFetcher(dynamicAttributeFilters) {

			@Override
			protected GQLListLoadResult runGetAll(String entityName, GQLListLoadConfig listLoadConfig) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			protected Object getById(String publicId) {
				// TODO Auto-generated method stub
				return null;
			}
		};
	}

	private DataFetcher<?> createSaveDataFetchers() {
		return new GQLAbstractSaveDataFetcher() {

			@Override
			protected void runSave(Object model) {
				// TODO Auto-generated method stub

			}

			@Override
			protected Object findOrCreateModel(String entityName, Map<String, Object> fieldMap) {
				// TODO Auto-generated method stub
				return null;
			}

		};
	}

	private DataFetcher<GQLDeleteResult> createDeleteDataFetcher() {
		return new GQLAbstractDeleteDataFetcher() {

			@Override
			protected void runDelete(String entityName, String id) {
				// TODO Auto-generated method stub

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
