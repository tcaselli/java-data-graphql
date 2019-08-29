package com.daikit.graphql.builder.types;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.daikit.graphql.builder.GQLSchemaBuilderCache;
import com.daikit.graphql.builder.custommethod.GQLCustomMethodBuilder;
import com.daikit.graphql.constants.GQLSchemaConstants;
import com.daikit.graphql.data.output.GQLDeleteResult;
import com.daikit.graphql.meta.GQLMetaModel;
import com.daikit.graphql.meta.custommethod.GQLAbstractMethodMetaData;
import com.daikit.graphql.meta.internal.GQLAbstractEntityMetaDataInfos;
import com.daikit.graphql.utils.Message;

import graphql.schema.DataFetcher;
import graphql.schema.GraphQLArgument;
import graphql.schema.GraphQLFieldDefinition;
import graphql.schema.GraphQLNonNull;
import graphql.schema.GraphQLObjectType;

/**
 * Type builder for mutations
 *
 * @author Thibaut Caselli
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
	 * @param metaModel
	 *            the {@link GQLMetaModel}
	 * @param saveDataFetcher
	 *            the {@link DataFetcher} for create/update
	 * @param deleteDataFetcher
	 *            the {@link DataFetcher} for delete
	 * @param customMethodsDataFetcher
	 *            the {@link DataFetcher} for custom methods
	 * @return the created {@link GraphQLObjectType}
	 */
	public GraphQLObjectType buildMutationType(final GQLMetaModel metaModel,
			final DataFetcher<?> saveDataFetcher, final DataFetcher<GQLDeleteResult> deleteDataFetcher,
			final DataFetcher<?> customMethodsDataFetcher) {
		logger.debug("START building mutation types...");

		final GraphQLObjectType.Builder builder = GraphQLObjectType.newObject();
		builder.name(GQLSchemaConstants.MUTATION_TYPE);
		builder.description("Mutation type from meta model");

		logger.debug("Build mutation types for entities...");
		final List<GraphQLFieldDefinition> saveFieldDefinitions = metaModel.getNonEmbeddedConcretes().stream()
				.filter(infos -> infos.getEntity().isSaveable()).map(infos -> buildSaveMutationFieldDefinition(infos))
				.collect(Collectors.toList());
		builder.fields(saveFieldDefinitions);
		final List<GraphQLFieldDefinition> deleteFieldDefinitions = metaModel.getNonEmbeddedConcretes().stream()
				.filter(infos -> infos.getEntity().isDeletable())
				.map(infos -> buildDeleteMutationFieldDefinition(infos)).collect(Collectors.toList());
		builder.fields(deleteFieldDefinitions);

		logger.debug("Build mutation types for custom methods...");
		final Map<GQLAbstractMethodMetaData, GraphQLFieldDefinition> customMethodFieldDefinitions = new GQLCustomMethodBuilder(
				getCache())
						.buildMethods(metaModel.getCustomMethods().stream()
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
				.dataFetcher(mutationType, entry.getValue(), customMethodsDataFetcher));

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
