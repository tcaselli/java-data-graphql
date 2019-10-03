package com.daikit.graphql.meta.attribute;

import com.daikit.graphql.dynamicattribute.IGQLDynamicAttributeGetter;
import com.daikit.graphql.dynamicattribute.IGQLDynamicAttributeSetter;
import com.daikit.graphql.meta.GQLAbstractMetaData;

/**
 * Abstract super class for GraphQL entity/data attribute meta data
 *
 * @author Thibaut Caselli
 */
public abstract class GQLAbstractAttributeMetaData extends GQLAbstractMetaData {

	private String name;
	private String description;
	private boolean readable = true;
	private boolean saveable = true;
	private boolean nullableForCreate = true;
	private boolean nullableForUpdate = true;
	private boolean filterable = true;

	private IGQLDynamicAttributeGetter<?, ?> dynamicAttributeGetter;
	private IGQLDynamicAttributeSetter<?, ?> dynamicAttributeSetter;

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// CONSTRUCTORS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	/**
	 * Default constructor
	 */
	public GQLAbstractAttributeMetaData() {
		// Nothing done
	}

	/**
	 * Constructor passing name and keeping default values for other attributes.
	 *
	 * @param name
	 *            the name for the attribute. This name will be used for
	 *            building GraphQL schema : queries, mutations, descriptions
	 *            etc.
	 */
	public GQLAbstractAttributeMetaData(final String name) {
		this.name = name;
	}

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// METHODS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	@Override
	protected void appendToString(final StringBuilder stringBuilder) {
		stringBuilder.append(name).append(!isNullableForCreate() && !isNullableForUpdate() ? "[!NULL]" : "")
				.append(!isNullableForCreate() ? "[!NULL-C]" : "").append(!isNullableForUpdate() ? "[!NULL-U]" : "")
				.append(readable ? "[R]" : "").append(saveable ? "[S]" : "").append(filterable ? "[F]" : "")
				.append(isDynamic() ? "[DYN]" : "");
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
	 * {@link #nullableForCreate} AND {@link #nullableForUpdate} to TRUE
	 *
	 * @param nullable
	 *            the nullable to set
	 * @return this instance
	 */
	public GQLAbstractAttributeMetaData setNullable(final boolean nullable) {
		this.setNullableForCreate(nullable);
		this.setNullableForUpdate(nullable);
		return this;
	}

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// GETTERS / SETTERS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	/**
	 * Get whether this attribute is dynamic, which means that either a
	 * dynamicAttributeGetter or a dynamicAttributeSetter (or both) is/are set.
	 *
	 * @return a boolean
	 */
	public boolean isDynamic() {
		return dynamicAttributeGetter != null || dynamicAttributeSetter != null;
	}

	/**
	 * Get whether this attribute is a dynamic attribute setter, which means
	 * that a dynamicAttributeSetter is set
	 *
	 * @return a boolean
	 */
	public boolean isDynamicAttributeSetter() {
		return dynamicAttributeSetter != null;
	}

	/**
	 * Get whether this attribute is a dynamic attribute getter, which means
	 * that a dynamicAttributeGetter is set
	 *
	 * @return a boolean
	 */
	public boolean isDynamicAttributeGetter() {
		return dynamicAttributeGetter != null;
	}

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// GETTERS / SETTERS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	/**
	 * Get the description for the attribute. This description will be used for
	 * building descriptions in GraphQL schema every time this attribute is used
	 * in queries, mutations etc.
	 *
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Set the description for the attribute. This description will be used for
	 * building descriptions in GraphQL schema every time this attribute is used
	 * in queries, mutations etc.
	 *
	 * @param description
	 *            the description to set
	 * @return this instance
	 */
	public GQLAbstractAttributeMetaData setDescription(String description) {
		this.description = description;
		return this;
	}

	/**
	 * Get the name for the attribute. This name will be used for building
	 * GraphQL schema : queries, mutations, descriptions etc.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Set the name for the attribute. This name will be used for building
	 * GraphQL schema : queries, mutations, descriptions etc.
	 *
	 * @param name
	 *            the name to set
	 * @return this instance
	 */
	public GQLAbstractAttributeMetaData setName(final String name) {
		this.name = name;
		return this;
	}

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
	public GQLAbstractAttributeMetaData setReadable(final boolean readable) {
		this.readable = readable;
		return this;
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
	public GQLAbstractAttributeMetaData setSaveable(final boolean saveable) {
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
	public GQLAbstractAttributeMetaData setNullableForCreate(boolean nullableForCreate) {
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
	public GQLAbstractAttributeMetaData setNullableForUpdate(boolean nullableForUpdate) {
		this.nullableForUpdate = nullableForUpdate;
		return this;
	}

	/**
	 * Get whether this attribute will be available in schema queries filters
	 * for retrieving this attribute parent entity. Default is
	 * <code>true</code>.
	 *
	 * @return the filterable
	 */
	public boolean isFilterable() {
		return filterable;
	}

	/**
	 * Set whether this attribute will be available in schema queries filters
	 * for retrieving this attribute parent entity. Default is
	 *
	 * @param filterable
	 *            the filterable to set
	 * @return this instance
	 */
	public GQLAbstractAttributeMetaData setFilterable(final boolean filterable) {
		this.filterable = filterable;
		return this;
	}

	/**
	 * If this attribute is dynamic then this method returns the getter for
	 * computing the attribute value. (An attribute is dynamic if there is no
	 * related property in holding entity, if you provide a
	 * {@link IGQLDynamicAttributeGetter} then if this attribute is set in
	 * schema queries for retrieving this attribute parent entity then this
	 * getter will be called with this attribute value as parameter.)
	 *
	 * @return the dynamicAttributeGetter
	 */
	public IGQLDynamicAttributeGetter<?, ?> getDynamicAttributeGetter() {
		return dynamicAttributeGetter;
	}

	/**
	 * If this attribute is dynamic then this method sets the getter for
	 * computing the attribute value. (An attribute is dynamic if there is no
	 * related property in holding entity, if you provide a
	 * {@link IGQLDynamicAttributeGetter} then if this attribute is set in
	 * schema queries for retrieving this attribute parent entity then this
	 * getter will be called with this attribute value as parameter.)
	 *
	 * @param dynamicAttributeGetter
	 *            the dynamicAttributeGetter to set
	 * @return this instance
	 */
	public GQLAbstractAttributeMetaData setDynamicAttributeGetter(
			final IGQLDynamicAttributeGetter<?, ?> dynamicAttributeGetter) {
		this.dynamicAttributeGetter = dynamicAttributeGetter;
		return this;
	}

	/**
	 * If this attribute is dynamic then this method returns the setter for
	 * storing the attribute value. (An attribute is dynamic if there is no
	 * related property in holding entity, if you provide a
	 * {@link IGQLDynamicAttributeSetter} then if this attribute is set in
	 * schema mutations for saving this attribute parent entity then this setter
	 * will be called with this attribute value as parameter.)
	 *
	 * @return the dynamicAttributeSetter
	 */
	public IGQLDynamicAttributeSetter<?, ?> getDynamicAttributeSetter() {
		return dynamicAttributeSetter;
	}

	/**
	 * If this attribute is dynamic then this method sets the setter for storing
	 * the attribute value. (An attribute is dynamic if there is no related
	 * property in holding entity, if you provide a
	 * {@link IGQLDynamicAttributeSetter} then if this attribute is set in
	 * schema mutations for saving this attribute parent entity then this setter
	 * will be called with this attribute value as parameter.)
	 *
	 * @param dynamicAttributeSetter
	 *            the dynamicAttributeSetter to set
	 * @return this instance
	 */
	public GQLAbstractAttributeMetaData setDynamicAttributeSetter(
			final IGQLDynamicAttributeSetter<?, ?> dynamicAttributeSetter) {
		this.dynamicAttributeSetter = dynamicAttributeSetter;
		return this;
	}

}
