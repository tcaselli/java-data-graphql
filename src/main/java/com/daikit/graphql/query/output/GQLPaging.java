package com.daikit.graphql.query.output;

/**
 * Paging meta informations for output
 *
 * @author tcaselli
 */
public class GQLPaging {

	private int totalLength = -1;
	private int offset = 0;
	private int limit = 0;

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// CONSTRUCTORS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	/**
	 * Default constructor
	 */
	public GQLPaging() {
		// Nothing done
	}

	/**
	 * Constructor
	 * 
	 * @param offset
	 *            the offset
	 * @param limit
	 *            the limit
	 * @param totalLength
	 *            the total length
	 */
	public GQLPaging(int offset, int limit, int totalLength) {
		this.offset = offset;
		this.limit = limit;
		this.totalLength = totalLength;
	}

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// GETTERS / SETTERS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	/**
	 * @return the totalLength
	 */
	public int getTotalLength() {
		return totalLength;
	}

	/**
	 * @param totalLength
	 *            the totalLength to set
	 */
	public void setTotalLength(final int totalLength) {
		this.totalLength = totalLength;
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

	/**
	 * @return the limit
	 */
	public int getLimit() {
		return limit;
	}

	/**
	 * @param limit
	 *            the limit to set
	 */
	public void setLimit(final int limit) {
		this.limit = limit;
	}

}
