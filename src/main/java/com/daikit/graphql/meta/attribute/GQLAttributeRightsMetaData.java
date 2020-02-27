package com.daikit.graphql.meta.attribute;

import com.daikit.graphql.meta.GQLAbstractMetaData;

/**
 * Abstract super class for GraphQL entity/data attribute meta data
 *
 * @author Thibaut Caselli
 */
public class GQLAttributeRightsMetaData extends GQLAbstractMetaData {

	private Object role = null;
	private boolean readable = true;
	private boolean saveable = true;
	private boolean nullableForCreate = true;
	private boolean nullableForUpdate = true;
	private boolean mandatoryForCreate = false;
	private boolean mandatoryForUpdate = false;

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// CONSTRUCTORS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	/**
	 * Default constructor
	 */
	public GQLAttributeRightsMetaData() {
		// Nothing done
	}

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// METHODS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	@Override
	protected void appendToString(final StringBuilder stringBuilder) {
		stringBuilder.append("(RIGHTS: [").append(getRole() == null ? "ALL" : getRole()).append("] ")
				.append(!isNullableForCreate() && !isNullableForUpdate() ? "[!NULL]" : "")
				.append(!isNullableForCreate() ? "[!NULL-C]" : "").append(!isNullableForUpdate() ? "[!NULL-U]" : "")
				.append(isMandatoryForCreate() && isMandatoryForUpdate() ? "[MAND]" : "")
				.append(isMandatoryForCreate() ? "[MAND-C]" : "").append(isMandatoryForUpdate() ? "[MAND-U]" : "")
				.append(readable ? "[R]" : "").append(saveable ? "[S]" : "").append(")");
	}

	/**
	 * Get whether this attribute can be null. This is a shortcut to
	 * ({@link #isNullableForCreate()} AND {@link #isNullableForUpdate()})
	 *
	 * @return the nullable
	 */
	public boolean isNullable() {
		return isNullableForCreate() && isNullableForUpdate();
	}

	/**
	 * Set whether this attribute can be null. This will set
	 * {@link #nullableForCreate} AND {@link #nullableForUpdate} to the same
	 * given value
	 *
	 * @param nullable
	 *            the nullable to set
	 * @return this instance
	 */
	public GQLAttributeRightsMetaData setNullable(final boolean nullable) {
		this.setNullableForCreate(nullable);
		this.setNullableForUpdate(nullable);
		return this;
	}

	/**
	 * Get whether this attribute must be provided during save. This is a
	 * shortcut to ({@link #isMandatoryForCreate()} AND
	 * {@link #isMandatoryForUpdate()})
	 *
	 * @return the mandatory
	 */
	public boolean isMandatory() {
		return isMandatoryForCreate() && isMandatoryForUpdate();
	}

	/**
	 * Set whether this attribute can be null. This will set
	 * {@link #mandatoryForCreate} AND {@link #mandatoryForUpdate} to the same
	 * given value
	 *
	 * @param mandatory
	 *            the mandatory to set
	 * @return this instance
	 */
	public GQLAttributeRightsMetaData setMandatory(final boolean mandatory) {
		this.setMandatoryForCreate(mandatory);
		this.setMandatoryForUpdate(mandatory);
		return this;
	}

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// GETTERS / SETTERS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	/**
	 * Get whether this attribute will be available in schema queries for
	 * retrieving this attribute parent entity. Default is <code>true</code>.
	 *
	 * @return the readable
	 */
	public boolean isReadable() {
		return readable;
	}

	/**
	 * Set whether this attribute will be available in schema queries for
	 * retrieving this attribute parent entity. Default is <code>true</code>.
	 *
	 * @param readable
	 *            the readable to set
	 * @return this instance
	 */
	public GQLAttributeRightsMetaData setReadable(final boolean readable) {
		this.readable = readable;
		return this;
	}

	/**
	 * Get the role this attribute rights is configured for
	 *
	 * @return the role the role
	 */
	public Object getRole() {
		return role;
	}

	/**
	 * Set the role this attribute rights is configured for
	 *
	 * @param role
	 *            the role to set
	 */
	public void setRole(Object role) {
		this.role = role;
	}

	/**
	 * Get whether this attribute will be available in schema mutations for
	 * saving this attribute parent entity. Default is <code>true</code>.
	 *
	 * @return the saveable
	 */
	public boolean isSaveable() {
		return saveable;
	}

	/**
	 * Set whether this attribute will be available in schema mutations for
	 * saving this attribute parent entity. Default is <code>true</code>.
	 *
	 * @param saveable
	 *            the saveable to set
	 * @return this instance
	 */
	public GQLAttributeRightsMetaData setSaveable(final boolean saveable) {
		this.saveable = saveable;
		return this;
	}

	/**
	 * Get whether this attribute can be null at parent entity creation time.
	 * Default is <code>true</code>.
	 *
	 * @return the nullableForCreate
	 */
	public boolean isNullableForCreate() {
		return nullableForCreate;
	}

	/**
	 * Set whether this attribute can be null at parent entity creation time.
	 * Default is <code>true</code>.
	 *
	 * @param nullableForCreate
	 *            the nullableForCreate to set
	 * @return this instance
	 */
	public GQLAttributeRightsMetaData setNullableForCreate(boolean nullableForCreate) {
		this.nullableForCreate = nullableForCreate;
		return this;
	}

	/**
	 * Get whether this attribute can be null at parent entity creation time.
	 * Default is <code>true</code>.
	 *
	 * @return the nullableForUpdate
	 */
	public boolean isNullableForUpdate() {
		return nullableForUpdate;
	}

	/**
	 * Set whether this attribute can be null at parent entity creation time.
	 * Default is <code>true</code>.
	 *
	 * @param nullableForUpdate
	 *            the nullableForUpdate to set
	 * @return this instance
	 */
	public GQLAttributeRightsMetaData setNullableForUpdate(boolean nullableForUpdate) {
		this.nullableForUpdate = nullableForUpdate;
		return this;
	}

	/**
	 * Get whether this attribute must be provided during parent entity
	 * creation. This does not mean it cannot be null, this is given by
	 * {@link #isNullable()} or {@link #isNullableForCreate()} methods. Default
	 * is <code>false</code>.
	 *
	 * @return the mandatoryForCreate
	 */
	public boolean isMandatoryForCreate() {
		return mandatoryForCreate;
	}

	/**
	 * Set whether this attribute must be provided during parent entity
	 * creation. This does not mean it cannot be null, this is configurable with
	 * {@link #setNullable(boolean)} or {@link #setNullableForCreate(boolean)}
	 * methods. Default is <code>false</code>.
	 *
	 * @param mandatoryForCreate
	 *            the mandatoryForCreate to set
	 * @return this instance
	 */
	public GQLAttributeRightsMetaData setMandatoryForCreate(boolean mandatoryForCreate) {
		this.mandatoryForCreate = mandatoryForCreate;
		return this;
	}

	/**
	 * Get whether this attribute must be provided during parent entity update.
	 * This does not mean it cannot be null, this is given by
	 * {@link #isNullable()} or {@link #isNullableForUpdate()} methods. Default
	 * is <code>false</code>.
	 *
	 * @return the mandatoryForUpdate
	 */
	public boolean isMandatoryForUpdate() {
		return mandatoryForUpdate;
	}

	/**
	 * Set whether this attribute must be provided during parent entity update.
	 * This does not mean it cannot be null, this is configurable with
	 * {@link #setNullable(boolean)} or {@link #setNullableForUpdate(boolean)}
	 * methods. Default is <code>false</code>.
	 *
	 * @param mandatoryForUpdate
	 *            the mandatoryForUpdate to set
	 * @return this instance
	 */
	public GQLAttributeRightsMetaData setMandatoryForUpdate(boolean mandatoryForUpdate) {
		this.mandatoryForUpdate = mandatoryForUpdate;
		return this;
	}

}
