package com.daikit.graphql.execution;

import java.util.List;
import java.util.Map;

import com.daikit.graphql.builder.GQLSchemaBuilder;
import com.daikit.graphql.config.GQLSchemaConfig;
import com.daikit.graphql.data.output.GQLDeleteResult;
import com.daikit.graphql.data.output.GQLExecutionResult;
import com.daikit.graphql.data.output.GQLListLoadResult;
import com.daikit.graphql.datafetcher.GQLPropertyDataFetcher;
import com.daikit.graphql.meta.GQLMetaModel;

import graphql.ExecutionInput;
import graphql.ExecutionResult;
import graphql.GraphQL;
import graphql.schema.DataFetcher;
import graphql.schema.GraphQLSchema;

/**
 * Executor for GraphQL requests. This class is thread safe.
 *
 * @author Thibaut Caselli
 */
public class GQLExecutor {

	private final GQLMetaModel metaModel;
	private final GraphQLSchema schema;
	private final GraphQL graphql;
	private final IGQLErrorProcessor errorProcessor;
	private final IGQLExecutorCallback callback;

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// CONSTRUCTORS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	/**
	 * Initialize GraphQL executor from given {@link GQLMetaModel} with no
	 * callback
	 * 
	 * @param schemaConfig
	 *            the schema configuration {@link GQLSchemaConfig}
	 * @param metaModel
	 *            the meta model
	 * @param errorProcessor
	 *            the {@link IGQLErrorProcessor}
	 * @param getByIdDataFetcher
	 *            the {@link DataFetcher} for 'getById' methods
	 * @param listDataFetcher
	 *            the {@link DataFetcher} for 'getAll' methods
	 * @param saveDataFetcher
	 *            the {@link DataFetcher} for 'save' methods
	 * @param deleteDataFetcher
	 *            the {@link DataFetcher} for 'delete' methods
	 * @param customMethodDataFetcher
	 *            the {@link DataFetcher} for custom methods
	 * @param propertyDataFetchers
	 *            custom {@link GQLPropertyDataFetcher} list
	 */
	public GQLExecutor(final GQLSchemaConfig schemaConfig, final GQLMetaModel metaModel,
			final IGQLErrorProcessor errorProcessor, final DataFetcher<?> getByIdDataFetcher,
			final DataFetcher<GQLListLoadResult> listDataFetcher, final DataFetcher<?> saveDataFetcher,
			final DataFetcher<GQLDeleteResult> deleteDataFetcher, final DataFetcher<?> customMethodDataFetcher,
			final List<GQLPropertyDataFetcher<?>> propertyDataFetchers) {
		this(schemaConfig, metaModel, errorProcessor, null, getByIdDataFetcher, listDataFetcher, saveDataFetcher,
				deleteDataFetcher, customMethodDataFetcher, propertyDataFetchers);
	}

	/**
	 * Initialize GraphQL executor from given {@link GQLMetaModel} with a
	 * callback {@link IGQLExecutorCallback}
	 *
	 * @param schemaConfig
	 *            the schema configuration {@link GQLSchemaConfig}
	 * @param metaModel
	 *            the meta model
	 * @param errorProcessor
	 *            the {@link IGQLErrorProcessor}
	 * @param callback
	 *            the {@link IGQLExecutorCallback}
	 * @param getByIdDataFetcher
	 *            the {@link DataFetcher} for 'getById' methods
	 * @param listDataFetcher
	 *            the {@link DataFetcher} for 'getAll' methods
	 * @param saveDataFetcher
	 *            the {@link DataFetcher} for 'save' methods
	 * @param deleteDataFetcher
	 *            the {@link DataFetcher} for 'delete' methods
	 * @param customMethodDataFetcher
	 *            the {@link DataFetcher} for custom methods
	 * @param propertyDataFetchers
	 *            custom {@link GQLPropertyDataFetcher} list
	 */
	public GQLExecutor(final GQLSchemaConfig schemaConfig, final GQLMetaModel metaModel,
			final IGQLErrorProcessor errorProcessor, final IGQLExecutorCallback callback, final DataFetcher<?> getByIdDataFetcher,
			final DataFetcher<GQLListLoadResult> listDataFetcher, final DataFetcher<?> saveDataFetcher,
			final DataFetcher<GQLDeleteResult> deleteDataFetcher, final DataFetcher<?> customMethodDataFetcher,
			final List<GQLPropertyDataFetcher<?>> propertyDataFetchers) {
		this.metaModel = metaModel;
		this.errorProcessor = errorProcessor;
		this.callback = callback;
		this.schema = new GQLSchemaBuilder().build(schemaConfig, metaModel, getByIdDataFetcher, listDataFetcher,
				saveDataFetcher, deleteDataFetcher, customMethodDataFetcher, propertyDataFetchers);
		this.graphql = GraphQL.newGraphQL(schema).build();
	}

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// PUBLIC METHODS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	/**
	 * Wrapper for execution
	 *
	 * @param requestString
	 *            the request content as string
	 * @return the {@link GQLExecutionResult}
	 */
	public GQLExecutionResult execute(final String requestString) {
		return execute(ExecutionInput.newExecutionInput().query(requestString).build());
	}

	/**
	 * Wrapper for execution
	 *
	 * @param requestString
	 *            the request content as string
	 * @param operationName
	 *            the operation name
	 * @param context
	 *            the context object
	 * @param arguments
	 *            the arguments {@link Map}
	 * @return the {@link GQLExecutionResult}
	 */
	public GQLExecutionResult execute(final String requestString, final String operationName, final Object context,
			final Map<String, Object> arguments) {
		return execute(ExecutionInput.newExecutionInput().query(requestString).operationName(operationName)
				.context(context).root(context).variables(arguments).build());
	}

	/**
	 * Execute given {@link ExecutionInput}
	 *
	 * @param executionInput
	 *            the {@link ExecutionInput}
	 * @return the {@link GQLExecutionResult}
	 */
	public GQLExecutionResult execute(final ExecutionInput executionInput) {
		if (callback != null) {
			callback.onBeforeExecute(executionInput);
		}
		final GQLExecutionResult executionResult = wrapResult(graphql.execute(executionInput));
		if (callback != null) {
			callback.onAfterExecute(executionInput, executionResult);
		}
		return executionResult;
	}

	/**
	 * Wrap result
	 *
	 * @param executionResult
	 *            the {@link ExecutionResult}
	 * @return the {@link GQLExecutionResult}
	 */
	protected GQLExecutionResult wrapResult(final ExecutionResult executionResult) {
		return new GQLExecutionResult(executionResult, errorProcessor.handleError(executionResult.getErrors()));
	}

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// GETTERS / SETTERS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	/**
	 * @return the metaModel
	 */
	public GQLMetaModel getMetaModel() {
		return metaModel;
	}

	/**
	 * @return the schema
	 */
	public GraphQLSchema getSchema() {
		return schema;
	}

	/**
	 * @return the graphql
	 */
	public GraphQL getGraphql() {
		return graphql;
	}

	/**
	 * @return the errorProcessor
	 */
	public IGQLErrorProcessor getErrorProcessor() {
		return errorProcessor;
	}

}
