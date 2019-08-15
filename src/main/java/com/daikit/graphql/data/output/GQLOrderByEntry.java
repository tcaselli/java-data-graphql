package com.daikit.graphql.data.output;

import com.daikit.graphql.enums.GQLOrderByDirectionEnum;

/**
 * Order by
 *
 * @author tcaselli
 */
public class GQLOrderByEntry {

	private String field;
	private GQLOrderByDirectionEnum direction;

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// CONSTRUCTORS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	/**
	 * Default constructor
	 */
	public GQLOrderByEntry() {
		// Nothing done by default
	}

	/**
	 * Constructor
	 *
	 * @param field
	 *            the field
	 * @param direction
	 *            the direction
	 */
	public GQLOrderByEntry(final String field, final GQLOrderByDirectionEnum direction) {
		this.field = field;
		this.direction = direction;
	}

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// GETTERS / SETTERS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	/**
	 * @return the field
	 */
	public String getField() {
		return field;
	}

	/**
	 * @param field
	 *            the field to set
	 */
	public void setField(final String field) {
		this.field = field;
	}

	/**
	 * @return the direction
	 */
	public GQLOrderByDirectionEnum getDirection() {
		return direction;
	}

	/**
	 * @param direction
	 *            the direction to set
	 */
	public void setDirection(final GQLOrderByDirectionEnum direction) {
		this.direction = direction;
	}

}
