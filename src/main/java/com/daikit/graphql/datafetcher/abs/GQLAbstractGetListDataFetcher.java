package com.daikit.graphql.datafetcher.abs;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.daikit.graphql.builder.GQLSchemaBuilder;
import com.daikit.graphql.constants.GQLSchemaConstants;
import com.daikit.graphql.data.input.GQLListLoadConfig;
import com.daikit.graphql.data.output.GQLListLoadResult;
import com.daikit.graphql.datafetcher.GQLAbstractDataFetcher;
import com.daikit.graphql.dynamicattribute.IGQLDynamicAttributeFilter;
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

	@SuppressWarnings("rawtypes")
	private final Map<String, Map<String, IGQLDynamicAttributeFilter>> dynamicAttributeFiltersMap = new HashMap<>();

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// ABSTRACT METHODS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	protected abstract GQLListLoadResult runGetAll(String entityName, GQLListLoadConfig listLoadConfig);

	protected abstract Object getById(String entityName, String id);

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// METHODS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	/**
	 * Default constructor. If you have dynamic attributes and you want a
	 * possibility to filter on them, then use
	 * {@link #GQLAbstractGetListDataFetcher(Collection)} instead.
	 */
	public GQLAbstractGetListDataFetcher() {

	}
	/**
	 * Constructor with dynamic attribute filters. Providing dynamic attribute
	 * filters gives you the possibility to filter on entity dynamic attributes.
	 *
	 * @param dynamicAttributeFilters
	 *            the collection of all registered
	 *            {@link IGQLDynamicAttributeFilter}
	 */
	@SuppressWarnings("rawtypes")
	public GQLAbstractGetListDataFetcher(final Collection<IGQLDynamicAttributeFilter> dynamicAttributeFilters) {
		final Map<String, List<IGQLDynamicAttributeFilter>> map = dynamicAttributeFilters.stream()
				.collect(Collectors.groupingBy(filter -> filter.getEntityType().getSimpleName()));
		map.entrySet().forEach(entry -> dynamicAttributeFiltersMap.put(entry.getKey(), entry.getValue().stream()
				.collect(Collectors.toMap(IGQLDynamicAttributeFilter::getName, Function.identity()))));
	}

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// METHODS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	@Override
	public GQLListLoadResult get(final DataFetchingEnvironment environment) {
		// The load configuration that will be used to run the query against
		// database using the service layer
		final GQLListLoadConfig listLoadConfig = createListLoadConfig();
		final Map<String, Object> arguments = environment.getArguments();

		// Parse input query
		final Field queryField = environment.getField();
		final String entityName = getEntityName(GQLSchemaConstants.QUERY_GET_LIST_PREFIX, queryField.getName());

		@SuppressWarnings("rawtypes")
		final Map<String, IGQLDynamicAttributeFilter> filters = dynamicAttributeFiltersMap.get(entityName);

		// Handle paging if needed
		final Optional<Argument> pagingConfig = queryField.getArguments().stream()
				.filter(argument -> GQLSchemaConstants.PAGING.equals(argument.getName())).findFirst();
		if (pagingConfig.isPresent()) {
			final Map<String, Object> contextArguments = getArgumentsForContext(arguments,
					pagingConfig.get().getName());

			final Optional<ObjectField> limitField = ((ObjectValue) pagingConfig.get().getValue()).getObjectFields()
					.stream().filter(field -> GQLSchemaConstants.PAGING_LIMIT.equals(field.getName())).findFirst();
			final Optional<ObjectField> offsetField = ((ObjectValue) pagingConfig.get().getValue()).getObjectFields()
					.stream().filter(field -> GQLSchemaConstants.PAGING_OFFSET.equals(field.getName())).findFirst();

			final int limit = limitField.isPresent()
					? mapValue(limitField.get(), contextArguments)
					: GQLSchemaConstants.PAGING_LIMIT_DEFAULT_VALUE;
			final int offset = offsetField.isPresent() ? mapValue(offsetField.get(), contextArguments) : 0;

			listLoadConfig.setPaging(limit, offset);
		}

		// Handle sorting if needed
		final Optional<Argument> orderByConfig = queryField.getArguments().stream()
				.filter(argument -> GQLSchemaConstants.ORDER_BY.equals(argument.getName())).findFirst();
		if (orderByConfig.isPresent()) {
			for (final Node<?> sortInfoNode : ((ArrayValue) orderByConfig.get().getValue()).getChildren()) {
				final List<Map<String, Object>> subArgumentsList = getArgumentsForContextAsList(arguments,
						orderByConfig.get().getName());

				for (final Map<String, Object> subArguments : subArgumentsList) {
					final Optional<ObjectField> fieldField = ((ObjectValue) sortInfoNode).getObjectFields().stream()
							.filter(field -> GQLSchemaConstants.ORDER_BY_FIELD.equals(field.getName())).findFirst();
					final Optional<ObjectField> directionField = ((ObjectValue) sortInfoNode).getObjectFields().stream()
							.filter(field -> GQLSchemaConstants.ORDER_BY_DIRECTION.equals(field.getName())).findFirst();

					// Field always non null
					final String field = mapValue(fieldField.get(), subArguments);
					final Object directionObj = mapValue(directionField.get(), subArguments);
					final GQLOrderByDirectionEnum direction = directionField.isPresent()
							? directionObj instanceof String
									? GQLOrderByDirectionEnum.valueOf((String) directionObj)
									: (GQLOrderByDirectionEnum) directionObj
							: GQLSchemaConstants.ORDER_BY_DIRECTION_DEFAULT_VALUE;

					listLoadConfig.addOrderBy(field, direction);
				}
			}
		}

		// Handle filtering if needed
		final Optional<Argument> filterConfig = queryField.getArguments().stream()
				.filter(argument -> GQLSchemaConstants.FILTER.equals(argument.getName())).findFirst();
		if (filterConfig.isPresent()) {
			final Map<String, Object> contextArguments = getArgumentsForContext(arguments,
					filterConfig.get().getName());

			for (final ObjectField filterField : ((ObjectValue) filterConfig.get().getValue()).getObjectFields()) {
				final String name = GQLSchemaConstants.removePropertyIdSuffix(filterField.getName());

				GQLFilterOperatorEnum operator;
				Object value;

				if (filterField.getValue() instanceof ObjectValue) {
					final Optional<ObjectField> operatorField = ((ObjectValue) filterField.getValue()).getObjectFields()
							.stream().filter(field -> GQLSchemaConstants.FILTER_OPERATOR.equals(field.getName()))
							.findFirst();
					final Optional<ObjectField> optionalValueField = ((ObjectValue) filterField.getValue())
							.getObjectFields().stream()
							.filter(field -> GQLSchemaConstants.FILTER_VALUE.equals(field.getName())).findFirst();

					final Map<String, Object> filterArguments = getArgumentsForContext(contextArguments,
							filterField.getName());

					// Operator always non null
					operator = GQLFilterOperatorEnum.forCode(mapValue(operatorField.get(), filterArguments));
					value = optionalValueField.isPresent() ? mapValue(optionalValueField.get(), filterArguments) : null;
				} else {
					operator = GQLFilterOperatorEnum.EQUAL;
					value = getById(entityName, mapValue(filterField, contextArguments));
				}

				final IGQLDynamicAttributeFilter<?, ?, ?> filter = filters == null ? null : filters.get(name);

				listLoadConfig.addFilter(filter == null ? name : filter.getFilteredPropertyQueryPath(), operator, value,
						filter);
			}
		}

		return runGetAll(entityName, listLoadConfig);
	}

	/**
	 * Override to provide custom {@link GQLListLoadConfig} extension
	 *
	 * @return a {@link GQLListLoadConfig}
	 */
	protected GQLListLoadConfig createListLoadConfig() {
		return new GQLListLoadConfig();
	}

}
