package com.daikit.graphql.meta.attribute;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Stream;

import com.daikit.graphql.builder.GQLExecutionContext;
import com.daikit.graphql.builder.GQLExecutionContext.GQLRolesJunctionEnum;
import com.daikit.graphql.dynamicattribute.IGQLDynamicAttributeGetter;
import com.daikit.graphql.dynamicattribute.IGQLDynamicAttributeSetter;
import com.daikit.graphql.exception.GQLException;
import com.daikit.graphql.meta.GQLAbstractMetaData;

/**
 * Abstract super class for GraphQL entity/data attribute meta data
 *
 * @author Thibaut Caselli
 */
public abstract class GQLAbstractAttributeMetaData extends GQLAbstractMetaData {

	private String name;
	private String description;
	private boolean filterable = true;

	private List<GQLAttributeRightsMetaData> rights = new ArrayList<>();
	private IGQLDynamicAttributeGetter<?, ?> dynamicAttributeGetter;
	private IGQLDynamicAttributeSetter<?, ?> dynamicAttributeSetter;

	private final GQLAttributeRightsMetaData DEFAULT_RIGHTS = new GQLAttributeRightsMetaData();

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
	// PUBLIC METHODS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	@Override
	protected void appendToString(final StringBuilder stringBuilder) {
		stringBuilder.append(name).append(filterable ? "[FILT]" : "").append(isDynamic() ? "[DYN]" : "");
	}

	/**
	 * Get whether this attribute is saveable in given context
	 *
	 * @param executionContext
	 *            the {@link GQLExecutionContext}
	 * @return a boolean
	 */
	public boolean isSaveable(GQLExecutionContext executionContext) {
		return checkRights(executionContext, rights -> rights.isSaveable());
	}

	/**
	 * Get whether this attribute is readable in given context
	 *
	 * @param executionContext
	 *            the {@link GQLExecutionContext}
	 * @return a boolean
	 */
	public boolean isReadable(GQLExecutionContext executionContext) {
		return checkRights(executionContext, rights -> rights.isReadable());
	}

	/**
	 * Get whether this attribute is nullable for creation in given context
	 *
	 * @param executionContext
	 *            the {@link GQLExecutionContext}
	 * @return a boolean
	 */
	public boolean isNullableForCreate(GQLExecutionContext executionContext) {
		return checkRights(executionContext, rights -> rights.isNullableForCreate());
	}

	/**
	 * Get whether this attribute is nullable for update in given context
	 *
	 * @param executionContext
	 *            the {@link GQLExecutionContext}
	 * @return a boolean
	 */
	public boolean isNullableForUpdate(GQLExecutionContext executionContext) {
		return checkRights(executionContext, rights -> rights.isNullableForUpdate());
	}

	/**
	 * Get whether this attribute is mandatory for creation in given context
	 *
	 * @param executionContext
	 *            the {@link GQLExecutionContext}
	 * @return a boolean
	 */
	public boolean isMandatoryForCreate(GQLExecutionContext executionContext) {
		return checkRights(executionContext, rights -> rights.isMandatoryForCreate());
	}

	/**
	 * Get whether this attribute is mandatory for update in given context
	 *
	 * @param executionContext
	 *            the {@link GQLExecutionContext}
	 * @return a boolean
	 */
	public boolean isMandatoryForUpdate(GQLExecutionContext executionContext) {
		return checkRights(executionContext, rights -> rights.isMandatoryForUpdate());
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

	/**
	 * Add given rights
	 *
	 * @param rightsArray
	 *            the array of GQLAttributeRightsMetaData to be added
	 * @return this
	 */
	public GQLAbstractAttributeMetaData addRights(GQLAttributeRightsMetaData... rightsArray) {
		Stream.of(rightsArray).forEach(rights -> {
			getRights().stream().filter(r -> Objects.equals(r.getRole(), rights.getRole())).findFirst().ifPresent(r -> {
				throw new GQLException("Several rights configured for same role [" + r.getRole()
						+ "] within attribute [" + this + "]");
			});
			if (rights.getRole() == null) {
				getRights().add(0, rights);
			} else {
				getRights().add(rights);
			}
		});
		return this;
	}

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// PRIVATE METHODS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	private Stream<GQLAttributeRightsMetaData> getRights(List<?> roles) {
		return getRights().stream().filter(rights -> rights.getRole() == null || roles.contains(rights.getRole()));
	}

	private boolean checkRights(GQLExecutionContext executionContext, Predicate<GQLAttributeRightsMetaData> predicate) {
		return getRights().isEmpty()
				? predicate.test(DEFAULT_RIGHTS)
				: GQLRolesJunctionEnum.AND.equals(executionContext.getRolesJunction())
						? getRights(executionContext.getRoles()).allMatch(predicate)
						: getRights(executionContext.getRoles()).anyMatch(predicate);
	}

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// GETTERS / SETTERS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	/**
	 * @return the rights
	 */
	public List<GQLAttributeRightsMetaData> getRights() {
		return rights;
	}

	/**
	 * @param rights
	 *            the rights to set
	 */
	public void setRights(List<GQLAttributeRightsMetaData> rights) {
		this.rights = rights;
	}

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
