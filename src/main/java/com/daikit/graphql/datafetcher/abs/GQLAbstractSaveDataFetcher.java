package com.daikit.graphql.datafetcher.abs;

import java.util.Map;

import com.daikit.graphql.builder.GQLSchemaBuilder;
import com.daikit.graphql.constants.GQLSchemaConstants;
import com.daikit.graphql.datafetcher.GQLAbstractDataFetcher;

import graphql.language.Field;
import graphql.language.ObjectValue;
import graphql.schema.DataFetchingEnvironment;

/**
 * Abstract super class that may be overridden to provide "save entity" data
 * fetcher to the schema building. This class is typically to be extended and
 * used in {@link GQLSchemaBuilder} for buildSchema method "save entity" data
 * fetcher argument
 *
 * @author tcaselli
 */
public abstract class GQLAbstractSaveDataFetcher extends GQLAbstractDataFetcher<Object> {

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// ABSTRACT METHODS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	/**
	 * Save the entity in persistence layer.
	 *
	 * @param entity
	 *            the entity to be saved
	 */
	protected abstract void runSave(Object entity);

	/**
	 * Find or create entity and set its field values from given field map
	 *
	 * @param entityName
	 *            the entity class name
	 * @param fieldMap
	 *            the {@link Map} of fields to set in entity
	 * @return the found/created entity
	 */
	protected abstract Object findOrCreateEntity(final String entityName, final Map<String, Object> fieldMap);

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// METHODS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	protected Object runSave(final String entityName, final Map<String, Object> fieldMap) {
		final Object model = findOrCreateEntity(entityName, fieldMap);
		// Run save
		runSave(model);
		return model;
	}

	@Override
	public Object get(final DataFetchingEnvironment environment) {
		final Field mutationField = environment.getField();
		final String entityName = getEntityName(GQLSchemaConstants.MUTATION_SAVE_PREFIX, mutationField.getName());
		final ObjectValue objectValue = (ObjectValue) mutationField.getArguments().stream()
				.filter(argument -> GQLSchemaConstants.INPUT_DATA.equals(argument.getName())).findFirst().get()
				.getValue();
		final Map<String, Object> arguments = getArgumentsForContext(environment.getArguments(),
				GQLSchemaConstants.INPUT_DATA);
		final Map<String, Object> fieldMap = convertObjectValue(objectValue, arguments);
		return runSave(entityName, fieldMap);
	}

}
