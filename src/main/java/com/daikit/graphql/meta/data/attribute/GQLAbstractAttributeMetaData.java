package com.daikit.graphql.meta.data.attribute;

import com.daikit.graphql.meta.data.GQLAbstractMetaData;
import com.daikit.graphql.meta.dynamic.attribute.IGQLDynamicAttributeGetter;
import com.daikit.graphql.meta.dynamic.attribute.IGQLDynamicAttributeSetter;

/**
 * Abstract super class for GraphQL entity/data attribute meta data
 *
 * @author tcaselli
 */
public abstract class GQLAbstractAttributeMetaData extends GQLAbstractMetaData {

	private String name;
	private boolean readable = true;
	private boolean saveable = true;
	private boolean nullable = true;
	private boolean filterable = false;

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
	public GQLAbstractAttributeMetaData(String name) {
		this.name = name;
	}

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// METHODS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	@Override
	protected void appendToString(final StringBuilder stringBuilder) {
		stringBuilder.append(name).append(nullable ? "[N]" : "").append(readable ? "[R]" : "")
				.append(saveable ? "[S]" : "");
	}

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
	 */
	public void setName(final String name) {
		this.name = name;
	}

	/**
	 * Get whether this attribute can be null. Default is <code>true</code>.
	 *
	 * @return the nullable
	 */
	public boolean isNullable() {
		return nullable;
	}

	/**
	 * Set whether this attribute can be null. Default is <code>true</code>.
	 *
	 * @param nullable
	 *            the nullable to set
	 */
	public void setNullable(final boolean nullable) {
		this.nullable = nullable;
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
	 */
	public void setReadable(final boolean readable) {
		this.readable = readable;
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
	 */
	public void setSaveable(final boolean saveable) {
		this.saveable = saveable;
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
	 */
	public void setFilterable(final boolean filterable) {
		this.filterable = filterable;
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
	 */
	public void setDynamicAttributeGetter(final IGQLDynamicAttributeGetter<?, ?> dynamicAttributeGetter) {
		this.dynamicAttributeGetter = dynamicAttributeGetter;
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
	 */
	public void setDynamicAttributeSetter(final IGQLDynamicAttributeSetter<?, ?> dynamicAttributeSetter) {
		this.dynamicAttributeSetter = dynamicAttributeSetter;
	}

}
