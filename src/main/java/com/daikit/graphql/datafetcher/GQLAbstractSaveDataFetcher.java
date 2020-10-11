package com.daikit.graphql.datafetcher;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.daikit.generics.utils.GenericsUtils;
import com.daikit.graphql.builder.GQLSchemaBuilder;
import com.daikit.graphql.dynamicattribute.IGQLDynamicAttributeSetter;
import com.daikit.graphql.exception.GQLException;
import com.daikit.graphql.utils.Message;

import graphql.language.Field;
import graphql.language.ObjectValue;
import graphql.language.VariableReference;
import graphql.schema.DataFetchingEnvironment;

/**
 * Abstract super class that may be overridden to provide "save entity" data fetcher to the schema building. This class is typically to be extended and used in
 * {@link GQLSchemaBuilder} for buildSchema method "save entity" data fetcher argument
 *
 * @author Thibaut Caselli
 * @param <SUPER_ENTITY_TYPE> the super type for all saveable entities
 */
public abstract class GQLAbstractSaveDataFetcher<SUPER_ENTITY_TYPE> extends GQLAbstractDataFetcher<SUPER_ENTITY_TYPE> {

	private GQLDynamicAttributeRegistry dynamicAttributeRegistry;

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// ABSTRACT METHODS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	/**
	 * Save the entity in persistence layer.
	 *
	 * @param entity the entity to be saved
	 */
	protected abstract void save(SUPER_ENTITY_TYPE entity);

	/**
	 * Find or create entity and set its field values from given field map.
	 *
	 * @param entityClass              the entity class
	 * @param dynamicAttributeRegistry the {@link GQLDynamicAttributeRegistry}
	 * @param fieldValueMap            the {@link Map} of fields values to set in entity
	 * @return the found/created entity
	 */
	protected abstract SUPER_ENTITY_TYPE getOrCreateAndSetProperties(final Class<?> entityClass, final GQLDynamicAttributeRegistry dynamicAttributeRegistry,
			final Map<String, Object> fieldValueMap);

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// METHODS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	protected SUPER_ENTITY_TYPE save(final Class<?> entityClass, final GQLDynamicAttributeRegistry dynamicAttributeRegistry,
			final Map<String, Object> fieldValueMap) {
		final SUPER_ENTITY_TYPE model = getOrCreateAndSetProperties(entityClass, dynamicAttributeRegistry, fieldValueMap);
		// Run save
		save(model);
		return model;
	}

	@Override
	public SUPER_ENTITY_TYPE get(final DataFetchingEnvironment environment) {
		final Field mutationField = environment.getField();
		final String entityName = getEntityName(getConfig().getMutationSavePrefix(), mutationField.getName());
		final Object dataValue = mutationField.getArguments().stream()
				.filter(argument -> getConfig().getMutationAttributeInputDataName().equals(argument.getName())).findFirst().get().getValue();
		final Map<String, Object> arguments = getArgumentsForContext(environment.getArguments(), getConfig().getMutationAttributeInputDataName());
		final Class<?> entityClass = getEntityClassByEntityName(entityName);
		Map<String, Object> fieldValueMap;
		if (dataValue instanceof ObjectValue) {
			fieldValueMap = convertObjectValue((ObjectValue) dataValue, arguments);
		} else if (dataValue instanceof VariableReference) {
			fieldValueMap = new HashMap<>(arguments);
			mapDynamicAttributeSettersValues(entityClass, fieldValueMap);
		} else {
			throw new GQLException(Message.format("Unsupported save data type : [{}]", dataValue.getClass().getName()));
		}
		return save(entityClass, dynamicAttributeRegistry, fieldValueMap);
	}

	@SuppressWarnings("unchecked")
	protected void mapDynamicAttributeSettersValues(final Class<?> entityClass, final Map<String, Object> fieldValueMap) {
		fieldValueMap.entrySet().forEach(entry -> {
			if (entry.getValue() instanceof Map) {
				final Optional<IGQLDynamicAttributeSetter<Object, Object>> setter = dynamicAttributeRegistry.getSetter(entityClass, entry.getKey());
				if (setter.isPresent()) {
					entry.setValue(convertValue((Map<String, Object>) entry.getValue(), setter.get()));
				}
			}
		});
	}

	protected Object convertValue(final Map<String, Object> dynamicAttributeValuePropertyValues,
			final IGQLDynamicAttributeSetter<?, ?> dynamicAttributeSetter) {
		final Type type = GenericsUtils.getTypeArguments(dynamicAttributeSetter.getClass(), IGQLDynamicAttributeSetter.class).get(1);
		final Object converted = createAndSetPropertyValues(dynamicAttributeValuePropertyValues, GenericsUtils.getTypeClass(type));
		return converted;
	}

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// GETTERS / SETTERS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	/**
	 * @return the dynamicAttributeRegistry
	 */
	public GQLDynamicAttributeRegistry getDynamicAttributeRegistry() {
		return dynamicAttributeRegistry;
	}

	/**
	 * @param dynamicAttributeRegistry the dynamicAttributeRegistry to set
	 */
	public void setDynamicAttributeRegistry(final GQLDynamicAttributeRegistry dynamicAttributeRegistry) {
		this.dynamicAttributeRegistry = dynamicAttributeRegistry;
	}
}
