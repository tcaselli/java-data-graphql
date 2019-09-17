package com.daikit.graphql.data.output;

import java.util.ArrayList;
import java.util.List;

/**
 * GraphQL list load result wrapper
 *
 * @author Thibaut Caselli
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

	/**
	 * Set the paging
	 *
	 * @param limit
	 *            the limit
	 * @param offset
	 *            the offset
	 * @param totalLength
	 *            the total length
	 * @return this instance for chaining
	 */
	public GQLListLoadResult setPaging(final int limit, final int offset, final int totalLength) {
		paging = new GQLPaging(offset, limit, totalLength);
		return this;
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
