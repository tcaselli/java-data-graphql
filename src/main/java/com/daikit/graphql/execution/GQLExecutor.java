package com.daikit.graphql.execution;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.daikit.graphql.builder.GQLExecutionContext;
import com.daikit.graphql.builder.GQLSchemaBuilder;
import com.daikit.graphql.config.GQLSchemaConfig;
import com.daikit.graphql.data.output.GQLDeleteResult;
import com.daikit.graphql.data.output.GQLExecutionResult;
import com.daikit.graphql.data.output.GQLListLoadResult;
import com.daikit.graphql.datafetcher.GQLPropertyDataFetcher;
import com.daikit.graphql.exception.GQLException;
import com.daikit.graphql.meta.GQLInternalMetaModel;
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

	private final GQLInternalMetaModel metaModel;
	private final Map<GQLExecutionContext, GraphQLSchema> schemaMap = new HashMap<>();
	private final Map<GQLExecutionContext, GraphQL> graphqlMap = new HashMap<>();
	private final IGQLErrorProcessor errorProcessor;
	private final IGQLExecutorCallback callback;

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// CONSTRUCTORS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	/**
	 * Initialize GraphQL executorManualMetaModel from given
	 * {@link GQLMetaModel} with no callback
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
	 * @param allPossibleExecutionContexts
	 *            a list of all possible {@link GQLExecutionContext}. Leave it
	 *            empty or null if you
	 */
	public GQLExecutor(final GQLSchemaConfig schemaConfig, final GQLMetaModel metaModel,
			final IGQLErrorProcessor errorProcessor, final DataFetcher<?> getByIdDataFetcher,
			final DataFetcher<GQLListLoadResult> listDataFetcher, final DataFetcher<?> saveDataFetcher,
			final DataFetcher<GQLDeleteResult> deleteDataFetcher, final DataFetcher<?> customMethodDataFetcher,
			final List<GQLPropertyDataFetcher<?>> propertyDataFetchers,
			final List<GQLExecutionContext> allPossibleExecutionContexts) {
		this(schemaConfig, metaModel, errorProcessor, null, getByIdDataFetcher, listDataFetcher, saveDataFetcher,
				deleteDataFetcher, customMethodDataFetcher, propertyDataFetchers, allPossibleExecutionContexts);
	}

	/**
	 * Initialize GraphQL executorManualMetaModel from given
	 * {@link GQLMetaModel} with a callback {@link IGQLExecutorCallback}
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
	 * @param allPossibleExecutionContexts
	 *            a list of all possible {@link GQLExecutionContext}
	 */
	public GQLExecutor(final GQLSchemaConfig schemaConfig, final GQLMetaModel metaModel,
			final IGQLErrorProcessor errorProcessor, final IGQLExecutorCallback callback,
			final DataFetcher<?> getByIdDataFetcher, final DataFetcher<GQLListLoadResult> listDataFetcher,
			final DataFetcher<?> saveDataFetcher, final DataFetcher<GQLDeleteResult> deleteDataFetcher,
			final DataFetcher<?> customMethodDataFetcher, final List<GQLPropertyDataFetcher<?>> propertyDataFetchers,
			final List<GQLExecutionContext> allPossibleExecutionContexts) {
		this.errorProcessor = errorProcessor;
		this.callback = callback;
		this.metaModel = new GQLInternalMetaModel(schemaConfig, metaModel);
		final List<GQLExecutionContext> executionContexts = new ArrayList<>();
		executionContexts.add(GQLExecutionContext.DEFAULT);
		if (allPossibleExecutionContexts != null) {
			executionContexts.addAll(allPossibleExecutionContexts);
		}
		executionContexts.forEach(context -> {
			final GraphQLSchema schema = new GQLSchemaBuilder().build(context, schemaConfig, this.metaModel,
					getByIdDataFetcher, listDataFetcher, saveDataFetcher, deleteDataFetcher, customMethodDataFetcher,
					propertyDataFetchers);
			schemaMap.put(context, schema);
			graphqlMap.put(context, GraphQL.newGraphQL(schema).build());
		});
	}

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// PUBLIC METHODS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	/**
	 * Wrapper for execution
	 *
	 * @param executionContext
	 *            the {@link GQLExecutionContext}
	 * @param requestString
	 *            the request content as string
	 * @return the {@link GQLExecutionResult}
	 */
	public GQLExecutionResult execute(final GQLExecutionContext executionContext, final String requestString) {
		return execute(executionContext, ExecutionInput.newExecutionInput().query(requestString).build());
	}

	/**
	 * Wrapper for execution
	 *
	 * @param executionContext
	 *            the {@link GQLExecutionContext}
	 * @param requestString
	 *            the request content as string
	 * @param operationName
	 *            the operation name
	 * @param rootContext
	 *            the execution root context object
	 * @param arguments
	 *            the arguments {@link Map}
	 * @return the {@link GQLExecutionResult}
	 */
	public GQLExecutionResult execute(final GQLExecutionContext executionContext, final String requestString,
			final String operationName, final Object rootContext, final Map<String, Object> arguments) {
		return execute(executionContext, ExecutionInput.newExecutionInput().query(requestString)
				.operationName(operationName).context(rootContext).root(rootContext).variables(arguments).build());
	}

	/**
	 * Execute given {@link ExecutionInput}
	 *
	 * @param executionContext
	 *            the {@link GQLExecutionContext}
	 * @param executionInput
	 *            the {@link ExecutionInput}
	 * @return the {@link GQLExecutionResult}
	 */
	public GQLExecutionResult execute(final GQLExecutionContext executionContext, final ExecutionInput executionInput) {
		if (callback != null) {
			callback.onBeforeExecute(executionContext, executionInput);
		}
		final GQLExecutionResult executionResult = wrapResult(getGraphql(executionContext)
				.orElseThrow(() -> new GQLException("No schema registered for given context " + executionContext
						+ ". Schemas should be precomputed for each possible context for better performances."))
				.execute(executionInput));
		if (callback != null) {
			callback.onAfterExecute(executionContext, executionInput, executionResult);
		}
		return executionResult;
	}

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// PROTECTED METHODS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	/**
	 * Wrap result. This method may be overridden to provide custom behavior.
	 *
	 * @param executionResult
	 *            the {@link ExecutionResult}
	 * @return the {@link GQLExecutionResult}
	 */
	protected GQLExecutionResult wrapResult(final ExecutionResult executionResult) {
		return new GQLExecutionResult(executionResult, errorProcessor.handleError(executionResult.getErrors()));
	}

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// PUBLIC METHODS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	/**
	 * Get schema for given context
	 *
	 * @param executionContext
	 *            the {@link GQLExecutionContext}
	 * @return the schema {@link GraphQLSchema}
	 */
	public Optional<GraphQLSchema> getSchema(GQLExecutionContext executionContext) {
		return Optional.ofNullable(schemaMap.get(executionContext));
	}

	/**
	 * Get graphQL executor for given context
	 *
	 * @param executionContext
	 *            the {@link GQLExecutionContext}
	 * @return the graphql {@link GraphQL}
	 */
	public Optional<GraphQL> getGraphql(GQLExecutionContext executionContext) {
		return Optional.ofNullable(graphqlMap.get(executionContext));
	}

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// GETTERS / SETTERS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	/**
	 * @return the schema {@link GraphQLSchema} for default
	 *         {@link GQLExecutionContext}
	 */
	public GraphQLSchema getSchema() {
		return getSchema(GQLExecutionContext.DEFAULT).get();
	}

	/**
	 * @return the graphql {@link GraphQL} for default
	 *         {@link GQLExecutionContext}
	 */
	public GraphQL getGraphql() {
		return getGraphql(GQLExecutionContext.DEFAULT).get();
	}

	/**
	 * @return the errorProcessor
	 */
	public IGQLErrorProcessor getErrorProcessor() {
		return errorProcessor;
	}

	/**
	 * @return the metaModel
	 */
	public GQLInternalMetaModel getMetaModel() {
		return metaModel;
	}

}
