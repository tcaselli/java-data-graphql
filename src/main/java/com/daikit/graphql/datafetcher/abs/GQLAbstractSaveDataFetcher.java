package com.daikit.graphql.datafetcher.abs;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.daikit.graphql.builder.GQLSchemaBuilder;
import com.daikit.graphql.constants.GQLSchemaConstants;
import com.daikit.graphql.datafetcher.GQLAbstractDataFetcher;
import com.daikit.graphql.dynamicattribute.IGQLDynamicAttributeSetter;
import com.daikit.graphql.utils.Message;

import graphql.GraphQLException;
import graphql.language.Field;
import graphql.language.ObjectValue;
import graphql.schema.DataFetchingEnvironment;

/**
 * Abstract super class that may be overridden to provide "save entity" data
 * fetcher to the schema building. This class is typically to be extended and
 * used in {@link GQLSchemaBuilder} for buildSchema method "save entity" data
 * fetcher argument
 *
 * @author Thibaut Caselli
 */
public abstract class GQLAbstractSaveDataFetcher extends GQLAbstractDataFetcher<Object> {

	private final Map<String, Map<String, IGQLDynamicAttributeSetter<Object, Object>>> dynamicAttributeSetters = new HashMap<>();

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
	 * @param fieldValueMap
	 *            the {@link Map} of fields values to set in entity
	 * @return the found/created entity
	 */
	protected abstract Object findOrCreateEntity(final String entityName,
			final Map<String, IGQLDynamicAttributeSetter<Object, Object>> dynamicAttributeSetters,
			final Map<String, Object> fieldValueMap);

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// METHODS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	protected Object runSave(final String entityName,
			final Map<String, IGQLDynamicAttributeSetter<Object, Object>> dynamicAttributeSetters,
			final Map<String, Object> fieldValueMap) {
		final Object model = findOrCreateEntity(entityName, dynamicAttributeSetters, fieldValueMap);
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
		final Map<String, Object> fieldValueMap = convertObjectValue(objectValue, arguments);
		final Map<String, IGQLDynamicAttributeSetter<Object, Object>> dynamicAttributeSetters = this.dynamicAttributeSetters
				.get(entityName);
		return runSave(entityName, dynamicAttributeSetters == null ? Collections.emptyMap() : dynamicAttributeSetters,
				fieldValueMap);
	}

	/**
	 * Register dynamic attribute setters in this data fetcher. This will be
	 * done automatically during schema building process.
	 *
	 * @param dynamicAttributeSetters
	 *            a {@link List} of {@link IGQLDynamicAttributeSetter}
	 */
	@SuppressWarnings("unchecked")
	public void registerDynamicAttributeSetters(List<IGQLDynamicAttributeSetter<?, ?>> dynamicAttributeSetters) {
		dynamicAttributeSetters.stream().forEach(dynamicAttributeSetter -> {
			final Map<String, IGQLDynamicAttributeSetter<Object, Object>> map = this.dynamicAttributeSetters
					.computeIfAbsent(dynamicAttributeSetter.getEntityName(), x -> new HashMap<>());
			final IGQLDynamicAttributeSetter<Object, Object> existing = map.get(dynamicAttributeSetter.getName());
			if (existing != null && !existing.equals(dynamicAttributeSetter)) {
				throw new GraphQLException(Message.format(
						"Duplicate dynamic attribute setters registered for entity {} and property name {}.",
						dynamicAttributeSetter.getEntityName(), dynamicAttributeSetter.getName()));
			}
			map.put(dynamicAttributeSetter.getName(),
					(IGQLDynamicAttributeSetter<Object, Object>) dynamicAttributeSetter);
		});
	}

}
