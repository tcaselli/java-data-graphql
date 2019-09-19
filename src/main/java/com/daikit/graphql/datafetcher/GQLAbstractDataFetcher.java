package com.daikit.graphql.datafetcher;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.daikit.graphql.config.GQLSchemaConfig;
import com.daikit.graphql.meta.GQLInternalMetaModel;
import com.daikit.graphql.utils.Message;

import graphql.language.Argument;
import graphql.language.ArrayValue;
import graphql.language.BooleanValue;
import graphql.language.EnumValue;
import graphql.language.Field;
import graphql.language.FloatValue;
import graphql.language.IntValue;
import graphql.language.ObjectField;
import graphql.language.ObjectValue;
import graphql.language.StringValue;
import graphql.language.Value;
import graphql.language.VariableReference;
import graphql.schema.DataFetcher;

/**
 * Abstract super class for all data fetchers
 *
 * @author Thibaut Caselli
 * @param <FETCHED_DATA_TYPE>
 *            the fetched data type
 */
public abstract class GQLAbstractDataFetcher<FETCHED_DATA_TYPE> implements DataFetcher<FETCHED_DATA_TYPE> {

	private GQLInternalMetaModel metaModel;
	private GQLSchemaConfig schemaConfig;

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// PUBLIC METHODS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	/**
	 * Get provided variable names for the request
	 *
	 * @return a {@link Collection} of variable names
	 */
	protected Collection<String> getRequestProvidedVariableNames() {
		return Collections.emptyList();
	}

	/**
	 * Get entity class by its name
	 *
	 * @param entityName
	 *            the entity class name
	 * @return the entity class
	 */
	protected Class<?> getEntityClassByEntityName(final String entityName) {
		return metaModel.getEntityClassByEntityName(entityName);
	}

	/**
	 * Get entity by name
	 *
	 * @param prefix
	 *            the entity prefix
	 * @param queryName
	 *            the query name
	 * @return the entity name
	 */
	protected String getEntityName(final String prefix, final String queryName) {
		if (!queryName.startsWith(prefix)) {
			throw new IllegalArgumentException(
					Message.format("Query name [{}] should start with [{}] prefix.", queryName, prefix));
		}
		return queryName.substring(prefix.length());
	}

	/**
	 * Get arguments with given property name within parent's arguments map
	 *
	 * @param arguments
	 *            the parent's arguments map
	 * @param argumentContext
	 *            the argument context name
	 * @return a {@link Map} (may be empty)
	 */
	@SuppressWarnings("unchecked")
	protected Map<String, Object> getArgumentsForContext(final Map<String, Object> arguments,
			final String argumentContext) {
		return arguments.containsKey(argumentContext)
				? (Map<String, Object>) arguments.get(argumentContext)
				: Collections.emptyMap();
	}

	/**
	 * Get arguments with given property name within parent's arguments map as a
	 * {@link List}
	 *
	 * @param arguments
	 *            the parent's arguments map
	 * @param argumentContext
	 *            the argument context name
	 * @return a {@link List} of {@link Map} (may be empty)
	 */
	@SuppressWarnings("unchecked")
	protected List<Map<String, Object>> getArgumentsForContextAsList(final Map<String, Object> arguments,
			final String argumentContext) {
		return arguments.containsKey(argumentContext)
				? (List<Map<String, Object>>) arguments.get(argumentContext)
				: Collections.emptyList();
	}

	/**
	 * Map GQL object field value to an object
	 *
	 * @param field
	 *            the GQL {@link ObjectField}
	 * @param arguments
	 *            the map of query arguments for potential replacements
	 * @param <T>
	 *            the mapped object type
	 * @return a mapped object value
	 */
	protected <T> T mapValue(final ObjectField field, final Map<String, Object> arguments) {
		return mapValue(field.getValue(), field.getName(), arguments);
	}

	/**
	 * Map GQL argument value to an object
	 *
	 * @param argument
	 *            the GQL {@link Argument}
	 * @param arguments
	 *            the map of query arguments for potential replacements
	 * @param <T>
	 *            the mapped object type
	 * @return a mapped object value
	 */
	protected <T> T mapValue(final Argument argument, final Map<String, Object> arguments) {
		return mapValue(argument.getValue(), argument.getName(), arguments);
	}

	/**
	 * Convert GQL object value to a {@link Map}
	 *
	 * @param objectValue
	 *            the {@link ObjectValue}
	 * @param arguments
	 *            the {@link Map} of arguments
	 * @return the converted map
	 */
	protected Map<String, Object> convertObjectValue(final ObjectValue objectValue,
			final Map<String, Object> arguments) {
		final Map<String, Object> map = new HashMap<>();
		// Build map of values
		for (final ObjectField objectField : objectValue.getObjectFields()) {
			if (objectField.getValue() instanceof ObjectValue) {
				map.put(objectField.getName(), convertObjectValue((ObjectValue) objectField.getValue(),
						getArgumentsForContext(arguments, objectField.getName())));
			} else {
				final Object value = mapValue(objectField, arguments);
				if (value != null
						// Variable reference is set but with null value
						|| objectField.getValue() instanceof VariableReference
								&& arguments.containsKey(objectField.getName())
						//
						|| !(objectField.getValue() instanceof VariableReference)
								&& !getRequestProvidedVariableNames().contains(objectField.getName())) {
					map.put(objectField.getName(), value);
				}
			}
		}
		// Remove mutually exclusive values
		final Set<String> propNames = map.keySet().stream().collect(Collectors.toSet());
		for (final String propName : propNames) {
			if (propName.endsWith(getConfig().getAttributeIdPluralSuffix())) {
				final String propNamePlural = propName.substring(0,
						propName.length() - getConfig().getAttributeIdPluralSuffix().length())
						+ getConfig().getAttributePluralSuffix();
				if (map.containsKey(propNamePlural)) {
					final Object propValuePlural = map.get(propNamePlural);
					if (propValuePlural instanceof Collection && !((Collection<?>) propValuePlural).isEmpty()) {
						map.remove(propName);
					} else {
						final Object propValueIds = map.get(propName);
						if (propValueIds instanceof Collection && !((Collection<?>) propValueIds).isEmpty()) {
							map.remove(propNamePlural);
						}
					}
				}
			}
		}
		return map;
	}

	/**
	 * Get argument by name within given query field
	 *
	 * @param queryField
	 *            the query {@link Field}
	 * @param name
	 *            then argument name
	 * @param arguments
	 *            the {@link Map} of arguments
	 * @return the argument value
	 */
	protected Object getArgumentValue(final Field queryField, final String name, final Map<String, Object> arguments) {
		final Optional<Argument> argumentOpt = queryField.getArguments().stream()
				.filter(argument -> name.equals(argument.getName())).findFirst();
		Object ret;
		if (argumentOpt.isPresent()) {
			ret = mapValue(argumentOpt.get(), arguments);
		} else {
			throw new IllegalArgumentException(Message.format("Argument not found with name [{}]", name));
		}
		return ret;
	}

	/**
	 * @return the metaModel
	 */
	protected GQLInternalMetaModel getMetaModel() {
		return metaModel;
	}

	/**
	 * @return the schemaConfig
	 */
	protected GQLSchemaConfig getConfig() {
		return schemaConfig;
	}

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// PRIVATE UTILS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	@SuppressWarnings("unchecked")
	private <X> X mapValue(final Value<?> value, final String argumentName, final Map<String, Object> arguments) {
		Object mappedValue = null;
		if (value instanceof ArrayValue) {
			// TODO not handled
			throw new IllegalArgumentException(Message.format("Unsupported value type [{}]", value));
		} else if (value instanceof BooleanValue) {
			mappedValue = Boolean.valueOf(((BooleanValue) value).isValue());
		} else if (value instanceof EnumValue) {
			mappedValue = ((EnumValue) value).getName();
		} else if (value instanceof FloatValue) {
			mappedValue = Double.valueOf(((FloatValue) value).getValue().doubleValue());
		} else if (value instanceof IntValue) {
			mappedValue = Integer.valueOf(((IntValue) value).getValue().intValue());
		} else if (value instanceof StringValue) {
			mappedValue = ((StringValue) value).getValue();
		} else if (value instanceof VariableReference) {
			mappedValue = arguments.get(argumentName);
		} else if (value instanceof ObjectValue) {
			mappedValue = convertObjectValue((ObjectValue) value, getArgumentsForContext(arguments, argumentName));
		}
		return (X) mappedValue;
	}

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// GETTERS / SETTERS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	/**
	 * @param metaModel
	 *            the metaModel to set
	 */
	public void setMetaModel(final GQLInternalMetaModel metaModel) {
		this.metaModel = metaModel;
	}
	/**
	 * @param schemaConfig
	 *            the schemaConfig to set
	 */
	public void setSchemaConfig(final GQLSchemaConfig schemaConfig) {
		this.schemaConfig = schemaConfig;
	}

}
