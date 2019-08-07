package com.daikit.graphql.builder.operation.mutation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.daikit.graphql.builder.GQLSchemaBuilderCache;
import com.daikit.graphql.builder.operation.GQLAbstractInputOutputTypesBuilder;
import com.daikit.graphql.builder.operation.GQLMethodBuilder;
import com.daikit.graphql.constants.GQLSchemaConstants;
import com.daikit.graphql.datafetcher.GQLMethodDataFetcher;
import com.daikit.graphql.meta.GQLMetaDataModel;
import com.daikit.graphql.meta.data.method.GQLAbstractMethodMetaData;
import com.daikit.graphql.meta.dynamic.method.GQLAbstractCustomMethod;
import com.daikit.graphql.meta.internal.GQLAbstractEntityMetaDataInfos;
import com.daikit.graphql.query.output.GQLDeleteResult;
import com.daikit.graphql.utils.Message;

import graphql.schema.DataFetcher;
import graphql.schema.GraphQLArgument;
import graphql.schema.GraphQLFieldDefinition;
import graphql.schema.GraphQLNonNull;
import graphql.schema.GraphQLObjectType;

/**
 * Type builder for mutations
 *
 * @author tcaselli
 */
public class GQLMutationTypeBuilder extends GQLAbstractInputOutputTypesBuilder {

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// CONSTRUCTORS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	/**
	 * Constructor
	 *
	 * @param cache
	 *            the {@link GQLSchemaBuilderCache}
	 */
	public GQLMutationTypeBuilder(final GQLSchemaBuilderCache cache) {
		super(cache);
	}

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// METHODS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	/**
	 * Build mutation type
	 *
	 * @param metaDataModel
	 *            the {@link GQLMetaDataModel}
	 * @param saveDataFetcher
	 *            the {@link DataFetcher} for create/update
	 * @param deleteDataFetcher
	 *            the {@link DataFetcher} for delete
	 * @param customMethodDataFetchers
	 *            the {@link GQLMethodDataFetcher} list (related to each
	 *            {@link GQLAbstractCustomMethod})
	 * @return the created {@link GraphQLObjectType}
	 */
	public GraphQLObjectType buildMutationType(final GQLMetaDataModel metaDataModel,
			final DataFetcher<?> saveDataFetcher, final DataFetcher<GQLDeleteResult> deleteDataFetcher,
			final List<GQLMethodDataFetcher> customMethodDataFetchers) {
		logger.debug("START building mutation types...");

		final GraphQLObjectType.Builder builder = GraphQLObjectType.newObject();
		builder.name("MutationType");
		builder.description("Mutation type from meta model");

		logger.debug("Build mutation types for entities...");
		final List<GraphQLFieldDefinition> saveFieldDefinitions = metaDataModel.getNonEmbeddedConcretes().stream()
				.map(infos -> buildSaveMutationFieldDefinition(infos)).collect(Collectors.toList());
		builder.fields(saveFieldDefinitions);
		final List<GraphQLFieldDefinition> deleteFieldDefinitions = metaDataModel.getNonEmbeddedConcretes().stream()
				.map(infos -> buildDeleteMutationFieldDefinition(infos)).collect(Collectors.toList());
		builder.fields(deleteFieldDefinitions);

		logger.debug("Build mutation types for custom methods...");
		final Map<GQLAbstractMethodMetaData, GraphQLFieldDefinition> customMethodFieldDefinitions = new GQLMethodBuilder(
				getCache())
						.buildMethods(metaDataModel.getCustomMethods().stream()
								.filter(GQLAbstractMethodMetaData::isMutation).collect(Collectors.toList()));
		builder.fields(new ArrayList<>(customMethodFieldDefinitions.values()));

		// Build mutation type
		final GraphQLObjectType mutationType = builder.build();

		// Register data fetcher for each field definition
		saveFieldDefinitions.forEach(fieldDefinition -> getCache().getCodeRegistryBuilder().dataFetcher(mutationType,
				fieldDefinition, saveDataFetcher));
		deleteFieldDefinitions.forEach(fieldDefinition -> getCache().getCodeRegistryBuilder().dataFetcher(mutationType,
				fieldDefinition, deleteDataFetcher));
		customMethodFieldDefinitions.entrySet().forEach(entry -> getCache().getCodeRegistryBuilder()
				.dataFetcher(mutationType, entry.getValue(), customMethodDataFetchers.stream()
						.filter(methodDataFetcher -> methodDataFetcher.getMethod().equals(entry.getKey().getMethod()))
						.findFirst().get()));

		logger.debug("END building mutation types");
		return mutationType;
	}

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// PRIVATE UTILS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	private GraphQLFieldDefinition buildSaveMutationFieldDefinition(final GQLAbstractEntityMetaDataInfos infos) {
		logger.debug(Message.format("Build save mutation for entity [{}]", infos.getEntity().getName()));
		final GraphQLFieldDefinition.Builder builderSave = GraphQLFieldDefinition.newFieldDefinition();
		builderSave.name(GQLSchemaConstants.MUTATION_SAVE_PREFIX + infos.getEntity().getName());
		builderSave.description("Entity save mutation for [" + infos.getEntity().getName() + "].");
		builderSave.type(getCache().getEntityType(infos.getEntity().getEntityClass()));
		builderSave.argument(buildSaveMutationArgument(infos));
		return builderSave.build();
	}

	private GraphQLArgument buildSaveMutationArgument(final GQLAbstractEntityMetaDataInfos infos) {
		logger.debug(Message.format("Build save mutation argument for entity [{}]", infos.getEntity().getName()));
		final GraphQLArgument.Builder builder = GraphQLArgument.newArgument();
		builder.name(GQLSchemaConstants.INPUT_DATA);
		builder.description("Entity save mutation argument for [" + infos.getEntity().getName() + "]");
		builder.type(new GraphQLNonNull(getCache().getInputEntityType(infos.getEntity().getEntityClass())));
		return builder.build();
	}

	private GraphQLFieldDefinition buildDeleteMutationFieldDefinition(final GQLAbstractEntityMetaDataInfos infos) {
		logger.debug(Message.format("Build delete mutation for entity [{}]", infos.getEntity().getName()));
		final GraphQLFieldDefinition.Builder builderDelete = GraphQLFieldDefinition.newFieldDefinition();
		builderDelete.name(GQLSchemaConstants.MUTATION_DELETE_PREFIX + infos.getEntity().getName());
		builderDelete.description("Entity delete mutation for [" + infos.getEntity().getName() + "].");
		builderDelete.type(getCache().getDeleteResultOutputObjectType());
		builderDelete.argument(buildArgumentNonNull(getIdAttribute(infos)));
		return builderDelete.build();
	}

}
