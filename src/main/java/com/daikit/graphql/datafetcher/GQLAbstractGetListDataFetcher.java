package com.daikit.graphql.datafetcher;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;

import com.daikit.graphql.builder.GQLSchemaBuilder;
import com.daikit.graphql.data.input.GQLListLoadConfig;
import com.daikit.graphql.data.output.GQLListLoadResult;
import com.daikit.graphql.dynamicattribute.IGQLDynamicAttributeGetter;
import com.daikit.graphql.enums.GQLFilterOperatorEnum;
import com.daikit.graphql.enums.GQLOrderByDirectionEnum;

import graphql.language.Argument;
import graphql.language.ArrayValue;
import graphql.language.Field;
import graphql.language.Node;
import graphql.language.ObjectField;
import graphql.language.ObjectValue;
import graphql.schema.DataFetchingEnvironment;

/**
 * Abstract super class that may be overridden to provide "get list" fetcher to
 * the schema building. This class is typically to be extended and used in
 * {@link GQLSchemaBuilder} for buildSchema "get list" method data fetcher
 * argument
 *
 * @author Thibaut Caselli
 */
public abstract class GQLAbstractGetListDataFetcher extends GQLAbstractDataFetcher<GQLListLoadResult> {

	private GQLDynamicAttributeRegistry dynamicAttributeRegistry;

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// ABSTRACT METHODS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	protected abstract GQLListLoadResult getAll(Class<?> entityClass, GQLListLoadConfig listLoadConfig);

	protected abstract Object getById(Class<?> entityClass, String id);

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// OVERRIDABLE METHODS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	/**
	 * Override to provide custom {@link GQLListLoadConfig} extension
	 *
	 * @return a {@link GQLListLoadConfig}
	 */
	protected GQLListLoadConfig createGQLListLoadConfig() {
		return new GQLListLoadConfig();
	}

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// METHODS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	@Override
	public GQLListLoadResult get(final DataFetchingEnvironment environment) {
		// The load configuration that will be used to run the query against
		// database using the service layer
		final GQLListLoadConfig listLoadConfig = createGQLListLoadConfig();
		final Map<String, Object> arguments = environment.getArguments();

		// Parse input query
		final Field queryField = environment.getField();
		final String entityName = getEntityName(getConfig().getQueryGetListPrefix(), queryField.getName());
		final Class<?> entityClass = getEntityClassByEntityName(entityName);

		// Handle paging if needed
		final Optional<Argument> pagingConfig = queryField.getArguments().stream()
				.filter(argument -> getConfig().getQueryGetListPagingAttributeName().equals(argument.getName()))
				.findFirst();
		if (pagingConfig.isPresent()) {
			final Map<String, Object> contextArguments = getArgumentsForContext(arguments,
					pagingConfig.get().getName());

			final Optional<ObjectField> limitField = ((ObjectValue) pagingConfig.get().getValue()).getObjectFields()
					.stream()
					.filter(field -> getConfig().getQueryGetListPagingAttributeLimitName().equals(field.getName()))
					.findFirst();
			final Optional<ObjectField> offsetField = ((ObjectValue) pagingConfig.get().getValue()).getObjectFields()
					.stream()
					.filter(field -> getConfig().getQueryGetListPagingAttributeOffsetName().equals(field.getName()))
					.findFirst();

			final int limit = limitField.isPresent()
					? mapValue(limitField.get(), contextArguments)
					: getConfig().getQueryGetListPagingAttributeLimitDefaultValue();
			final int offset = offsetField.isPresent() ? mapValue(offsetField.get(), contextArguments) : 0;

			listLoadConfig.setPaging(limit, offset);
		}

		// Handle sorting if needed
		final Optional<Argument> orderByConfig = queryField.getArguments().stream()
				.filter(argument -> getConfig().getQueryGetListFilterAttributeOrderByName().equals(argument.getName()))
				.findFirst();
		if (orderByConfig.isPresent()) {
			for (final Node<?> sortInfoNode : ((ArrayValue) orderByConfig.get().getValue()).getChildren()) {
				final List<Map<String, Object>> subArgumentsList = getArgumentsForContextAsList(arguments,
						orderByConfig.get().getName());

				for (final Map<String, Object> subArguments : subArgumentsList) {
					final Optional<ObjectField> fieldField = ((ObjectValue) sortInfoNode)
							.getObjectFields().stream().filter(field -> getConfig()
									.getQueryGetListFilterAttributeOrderByFieldName().equals(field.getName()))
							.findFirst();
					final Optional<ObjectField> directionField = ((ObjectValue) sortInfoNode)
							.getObjectFields().stream().filter(field -> getConfig()
									.getQueryGetListFilterAttributeOrderByDirectionName().equals(field.getName()))
							.findFirst();

					// Field always non null
					final String field = mapValue(fieldField.get(), subArguments);
					final Object directionObj = mapValue(directionField.get(), subArguments);
					final GQLOrderByDirectionEnum direction = directionField.isPresent()
							? directionObj instanceof String
									? GQLOrderByDirectionEnum.valueOf((String) directionObj)
									: (GQLOrderByDirectionEnum) directionObj
							: getConfig().getQueryGetListFilterAttributeOrderByDirectionDefaultValue();

					listLoadConfig.addOrderBy(field, direction);
				}
			}
		}

		// Handle filtering if needed
		final Optional<Argument> filterConfig = queryField.getArguments().stream()
				.filter(argument -> getConfig().getQueryGetListFilterAttributeName().equals(argument.getName()))
				.findFirst();
		if (filterConfig.isPresent()) {
			final Map<String, Object> contextArguments = getArgumentsForContext(arguments,
					filterConfig.get().getName());

			for (final ObjectField filterField : ((ObjectValue) filterConfig.get().getValue()).getObjectFields()) {
				final String fieldName = getConfig().removePropertyIdSuffix(filterField.getName());

				GQLFilterOperatorEnum operator;
				Object value;

				if (filterField.getValue() instanceof ObjectValue) {
					final Optional<ObjectField> operatorField = ((ObjectValue) filterField.getValue()).getObjectFields()
							.stream().filter(field -> getConfig().getQueryGetListFilterAttributeOperatorName()
									.equals(field.getName()))
							.findFirst();
					final Optional<ObjectField> optionalValueField = ((ObjectValue) filterField.getValue())
							.getObjectFields().stream().filter(field -> getConfig()
									.getQueryGetListFilterAttributeValueName().equals(field.getName()))
							.findFirst();

					final Map<String, Object> filterArguments = getArgumentsForContext(contextArguments,
							filterField.getName());

					// Operator always non null
					final Object operatorObject = mapValue(operatorField.get(), filterArguments);
					operator = operatorObject instanceof GQLFilterOperatorEnum ? (GQLFilterOperatorEnum) operatorObject
							: GQLFilterOperatorEnum.forCode(mapValue(operatorField.get(), filterArguments));
					value = optionalValueField.isPresent() ? mapValue(optionalValueField.get(), filterArguments) : null;
				} else {
					operator = GQLFilterOperatorEnum.EQUAL;
					final String id = mapValue(filterField, contextArguments);
					value = id == null ? null : getById(entityClass, id);
				}

				final Optional<IGQLDynamicAttributeGetter<Object, Object>> dynAttr = dynamicAttributeRegistry
						.getGetter(entityClass, fieldName);
				listLoadConfig
						.addFilter(dynAttr.isPresent() && StringUtils.isNoneEmpty(dynAttr.get().getFilterQueryPath())
								? dynAttr.get().getFilterQueryPath()
								: fieldName, operator, value, dynAttr.isPresent() ? dynAttr.get() : null);
			}
		}

		return getAll(entityClass, listLoadConfig);
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
	 * @param dynamicAttributeRegistry
	 *            the dynamicAttributeRegistry to set
	 */
	public void setDynamicAttributeRegistry(final GQLDynamicAttributeRegistry dynamicAttributeRegistry) {
		this.dynamicAttributeRegistry = dynamicAttributeRegistry;
	}
}
