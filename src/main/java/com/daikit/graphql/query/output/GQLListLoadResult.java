package com.daikit.graphql.query.output;

import java.util.ArrayList;
import java.util.List;

/**
 * GraphQL list load result wrapper
 *
 * @author tcaselli
 */
public class GQLListLoadResult {

	private List<?> data;
	private List<GQLOrderByEntry> orderBy = new ArrayList<>();
	private GQLPaging paging;

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// METHODS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	/**
	 * Add an order by entry
	 *
	 * @param orderBy
	 *            the {@link GQLOrderByEntry}
	 */
	public void addOrderBy(final GQLOrderByEntry orderBy) {
		this.orderBy.add(orderBy);
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
	 * @param orderBy
	 *            the orderBy to set
	 */
	public void setOrderBy(final List<GQLOrderByEntry> orderBy) {
		this.orderBy = orderBy;
	}

	/**
	 * @return the paging
	 */
	public GQLPaging getPaging() {
		return paging;
	}

	/**
	 * @param paging
	 *            the paging to set
	 */
	public void setPaging(final GQLPaging paging) {
		this.paging = paging;
	}

	/**
	 * @return the data
	 */
	public List<?> getData() {
		return data;
	}

	/**
	 * @param data
	 *            the data to set
	 */
	public void setData(final List<?> data) {
		this.data = data;
	}

}
