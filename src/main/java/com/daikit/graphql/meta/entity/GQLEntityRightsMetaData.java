package com.daikit.graphql.meta.entity;

/**
 * Access rights configuration for entity meta data
 *
 * @author Thibaut Caselli
 */
public class GQLEntityRightsMetaData {

	private Object role;
	private boolean readable = true;
	private boolean saveable = true;
	private boolean deletable = true;

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// GETTERS / SETTERS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	/**
	 * @return the role
	 */
	public Object getRole() {
		return role;
	}

	/**
	 * @param role
	 *            the role to set
	 */
	public void setRole(Object role) {
		this.role = role;
	}

	/**
	 * @return the readable
	 */
	public boolean isReadable() {
		return readable;
	}

	/**
	 * @param readable
	 *            the readable to set
	 * @return this instance
	 */
	public GQLEntityRightsMetaData setReadable(final boolean readable) {
		this.readable = readable;
		return this;
	}

	/**
	 * @return the deletable
	 */
	public boolean isDeletable() {
		return deletable;
	}

	/**
	 * @param deletable
	 *            the deletable to set
	 * @return this instance
	 */
	public GQLEntityRightsMetaData setDeletable(final boolean deletable) {
		this.deletable = deletable;
		return this;
	}

	/**
	 * @return the saveable
	 */
	public boolean isSaveable() {
		return saveable;
	}

	/**
	 * @param saveable
	 *            the saveable to set
	 * @return this instance
	 */
	public GQLEntityRightsMetaData setSaveable(final boolean saveable) {
		this.saveable = saveable;
		return this;
	}

}
