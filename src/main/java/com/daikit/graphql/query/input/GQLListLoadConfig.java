package com.daikit.graphql.query.input;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;

import com.daikit.graphql.enums.GQLFilterOperatorEnum;
import com.daikit.graphql.enums.GQLOrderByDirectionEnum;
import com.daikit.graphql.meta.dynamic.attribute.GQLDynamicAttributeFilter;
import com.daikit.graphql.query.output.GQLOrderByEntry;

/**
 * List load config for "get list" method
 *
 * @author tcaselli
 */
public class GQLListLoadConfig {

	private final List<GQLOrderByEntry> orderBy = new ArrayList<>();
	private final List<GQLFilterEntry> filters = new ArrayList<>();
	private int limit = 0;
	private int offset = 0;

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// PUBLIC METHODS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	/**
	 * Set paging limit & offset
	 *
	 * @param limit
	 *            the paging limit
	 * @param offset
	 *            the paging offset
	 */
	public void setPaging(int limit, int offset) {
		setLimit(limit);
		setOffset(offset);
	}

	/**
	 * Add order by from given map
	 *
	 * @param orderBys
	 *            a map of sort informations
	 */
	public void addOrderBys(final Map<String, GQLOrderByDirectionEnum> orderBys) {
		for (final Entry<String, GQLOrderByDirectionEnum> entry : orderBys.entrySet()) {
			addOrderBy(entry.getKey(), entry.getValue());
		}
	}

	/**
	 * Add order by
	 *
	 * @param field
	 *            the field to sort on
	 * @param direction
	 *            the direction
	 */
	public void addOrderBy(final String field, final GQLOrderByDirectionEnum direction) {
		addOrReplace(new GQLOrderByEntry(field, direction));
	}

	/**
	 * Add a filter
	 *
	 * @param field
	 *            the field name
	 * @param operator
	 *            the {@link GQLFilterOperatorEnum}
	 * @param value
	 *            the value
	 */
	public void addFilter(final String field, final GQLFilterOperatorEnum operator, final Object value) {
		filters.add(new GQLFilterEntry(field, operator, value));
	}

	/**
	 * Add a filter
	 *
	 * @param field
	 *            the field name
	 * @param operator
	 *            the {@link GQLFilterOperatorEnum}
	 * @param value
	 *            the value
	 * @param fieldFilter
	 *            the {@link GQLDynamicAttributeFilter}
	 */
	public void addFilter(final String field, final GQLFilterOperatorEnum operator, final Object value,
			GQLDynamicAttributeFilter<?, ?, ?> fieldFilter) {
		filters.add(new GQLFilterEntry(field, operator, value, fieldFilter));
	}

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// PROTECTED METHODS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	protected void addOrReplace(final GQLOrderByEntry order) {
		boolean removed = false;
		final Iterator<GQLOrderByEntry> ordersIterator = orderBy.iterator();
		while (!removed && ordersIterator.hasNext()) {
			final GQLOrderByEntry next = ordersIterator.next();
			if (Objects.equals(order.getField(), next.getField())) {
				ordersIterator.remove();
				removed = true;
			}
		}
		orderBy.add(order);
	}

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// GETTERS / SETTERS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	/**
	 * @return the orderBy
	 */
	public List<GQLOrderByEntry> getOrderBy() {
		return orderBy;
	}

	/**
	 * @param limit
	 *            the limit to set
	 */
	public void setLimit(final int limit) {
		this.limit = limit;
	}

	/**
	 * @return the limit
	 */
	public int getLimit() {
		return limit;
	}

	/**
	 * @return the offset
	 */
	public int getOffset() {
		return offset;
	}

	/**
	 * @param offset
	 *            the offset to set
	 */
	public void setOffset(final int offset) {
		this.offset = offset;
	}
}
