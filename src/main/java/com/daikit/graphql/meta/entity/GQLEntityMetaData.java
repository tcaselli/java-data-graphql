package com.daikit.graphql.meta.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Stream;

import com.daikit.graphql.builder.GQLExecutionContext;
import com.daikit.graphql.builder.GQLExecutionContext.GQLRolesJunctionEnum;
import com.daikit.graphql.exception.GQLException;
import com.daikit.graphql.meta.GQLAbstractMetaData;
import com.daikit.graphql.meta.attribute.GQLAbstractAttributeMetaData;

/**
 * Abstract super class for all entity meta datas (concrete, abstract, embedded
 * and abstract embedded)
 *
 * @author Thibaut Caselli
 */
public class GQLEntityMetaData extends GQLAbstractMetaData {

	private boolean embedded = false;
	private boolean concrete = true;

	private List<GQLEntityRightsMetaData> rights = new ArrayList<>();

	private String name;
	private String description;
	private Class<?> superEntityClass;
	private Class<?> entityClass;
	private List<GQLAbstractAttributeMetaData> attributes = new ArrayList<>();

	private final GQLEntityRightsMetaData DEFAULT_RIGHTS = new GQLEntityRightsMetaData();

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// CONSTRUCTORS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	/**
	 * Default constructor
	 */
	public GQLEntityMetaData() {
		// Nothing done
	}

	/**
	 * Constructor passing name and entity class. If the entity is abstract then
	 * don't forget to set <code>concrete</code> to <code>false</code>.
	 *
	 * @param name
	 *            the name for the attribute. This name will be used for
	 *            building GraphQL schema : queries, mutations, descriptions
	 *            etc.
	 * @param entityClass
	 *            the entity class
	 */
	public GQLEntityMetaData(final String name, final Class<?> entityClass) {
		this.name = name;
		this.entityClass = entityClass;
	}

	/**
	 * Constructor passing name and entity class.
	 *
	 * @param name
	 *            the name for the attribute. This name will be used for
	 *            building GraphQL schema : queries, mutations, descriptions
	 *            etc.
	 * @param entityClass
	 *            the entity class
	 * @param concrete
	 *            whether the entity is a concrete class (<code>true</code>) or
	 *            abstract class (<code>false</code>). Default is
	 *            <code>true</code>.
	 */
	public GQLEntityMetaData(final String name, final Class<?> entityClass, final boolean concrete) {
		this.name = name;
		this.entityClass = entityClass;
		this.concrete = concrete;
	}

	/**
	 * Constructor passing name, entity class and super entity class and keeping
	 * default values for other attributes. If the entity is abstract then don't
	 * forget to set <code>concrete</code> to <code>false</code>.
	 *
	 * @param name
	 *            the name for the attribute. This name will be used for
	 *            building GraphQL schema : queries, mutations, descriptions
	 *            etc.
	 * @param entityClass
	 *            the entity class
	 * @param superEntityClass
	 *            the super entity class
	 */
	public GQLEntityMetaData(final String name, final Class<?> entityClass, final Class<?> superEntityClass) {
		this.name = name;
		this.entityClass = entityClass;
		this.superEntityClass = superEntityClass;
	}

	/**
	 * Constructor passing name, entity class and super entity class and keeping
	 * default values for other attributes.
	 *
	 * @param name
	 *            the name for the attribute. This name will be used for
	 *            building GraphQL schema : queries, mutations, descriptions
	 *            etc.
	 * @param entityClass
	 *            the entity class
	 * @param superEntityClass
	 *            the super entity class
	 * @param concrete
	 *            whether the entity is a concrete class (<code>true</code>) or
	 *            abstract class (<code>false</code>). Default is
	 *            <code>true</code>.
	 */
	public GQLEntityMetaData(final String name, final Class<?> entityClass, final Class<?> superEntityClass,
			final boolean concrete) {
		this.name = name;
		this.entityClass = entityClass;
		this.superEntityClass = superEntityClass;
		this.concrete = concrete;
	}

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// PUBLIC METHODS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	@Override
	protected void appendToString(final StringBuilder stringBuilder) {
		stringBuilder.append(concrete ? "" : "[ABS]");
		stringBuilder.append("[").append(entityClass == null ? "" : entityClass.getSimpleName());
		stringBuilder.append(superEntityClass == null ? "" : " EXTENDS " + superEntityClass.getSimpleName())
				.append("]");
		stringBuilder.append("[ATTR=").append(attributes.size()).append("])");
	}

	/**
	 * Add given attribute if non null
	 *
	 * @param attribute
	 *            the {@link GQLAbstractAttributeMetaData} to be added
	 */
	public void addAttribute(final GQLAbstractAttributeMetaData attribute) {
		if (attribute != null) {
			attributes.add(attribute);
		}
	}

	/**
	 * Get whether this entity is saveable in given context
	 *
	 * @param executionContext
	 *            the {@link GQLExecutionContext}
	 * @return a boolean
	 */
	public boolean isSaveable(GQLExecutionContext executionContext) {
		return checkRights(executionContext, rights -> rights.isSaveable());
	}

	/**
	 * Get whether this entity is readable in given context
	 *
	 * @param executionContext
	 *            the {@link GQLExecutionContext}
	 * @return a boolean
	 */
	public boolean isReadable(GQLExecutionContext executionContext) {
		return checkRights(executionContext, rights -> rights.isReadable());
	}

	/**
	 * Get whether this entity is deleteable in given context
	 *
	 * @param executionContext
	 *            the {@link GQLExecutionContext}
	 * @return a boolean
	 */
	public boolean isDeletable(GQLExecutionContext executionContext) {
		return checkRights(executionContext, rights -> rights.isDeletable());
	}

	/**
	 * Add given rights
	 *
	 * @param rightsArray
	 *            the array of GQLEntityRightsMetaData to be added
	 * @return this
	 */
	public GQLEntityMetaData addRights(GQLEntityRightsMetaData... rightsArray) {
		Stream.of(rightsArray).forEach(rights -> {
			getRights().stream().filter(r -> Objects.equals(r.getRole(), rights.getRole())).findFirst().ifPresent(r -> {
				throw new GQLException(
						"Several rights configured for same role [" + r.getRole() + "] within entity [" + this + "]");
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

	private Stream<GQLEntityRightsMetaData> getRights(List<?> roles) {
		return getRights().stream().filter(rights -> rights.getRole() == null || roles.contains(rights.getRole()));
	}

	private boolean checkRights(GQLExecutionContext executionContext, Predicate<GQLEntityRightsMetaData> predicate) {
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
	public List<GQLEntityRightsMetaData> getRights() {
		return rights;
	}

	/**
	 * @param rights
	 *            the rights to set
	 */
	public void setRights(List<GQLEntityRightsMetaData> rights) {
		this.rights = rights;
	}

	/**
	 * Get the description for the entity. This description will be used for
	 * building descriptions in GraphQL schema every time this entity is used in
	 * queries, mutations etc.
	 *
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Set the description for the entity. This description will be used for
	 * building descriptions in GraphQL schema every time this entity is used in
	 * queries, mutations etc.
	 *
	 * @param description
	 *            the description to set
	 * @return this instance
	 */
	public GQLEntityMetaData setDescription(String description) {
		this.description = description;
		return this;
	}

	/**
	 * Get whether the entity is a concrete class (<code>true</code>) or
	 * abstract class (<code>false</code>). Default is <code>true</code>.
	 *
	 * @return the concrete
	 */
	public boolean isConcrete() {
		return concrete;
	}

	/**
	 * Set whether the entity is a concrete class (<code>true</code>) or
	 * abstract class (<code>false</code>). Default is <code>true</code>.
	 *
	 * @param concrete
	 *            the concrete to set
	 * @return this instance
	 */
	public GQLEntityMetaData setConcrete(final boolean concrete) {
		this.concrete = concrete;
		return this;
	}

	/**
	 * Set the name for the entity. This name will be used for building GraphQL
	 * schema : queries, mutations, descriptions etc.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Get the name for the entity. This name will be used for building GraphQL
	 * schema : queries, mutations, descriptions etc.
	 *
	 * @param name
	 *            the name to set
	 * @return this instance
	 */
	public GQLEntityMetaData setName(final String name) {
		this.name = name;
		return this;
	}

	/**
	 * Get the super class for the entity.
	 *
	 * @return the superEntityClass
	 */
	public Class<?> getSuperEntityClass() {
		return superEntityClass;
	}

	/**
	 * Set the super class for the entity. You shouldn't set this property if
	 * super class is {@link Object}.
	 *
	 * @param superEntityClass
	 *            the superEntityClass to set
	 * @return this instance
	 */
	public GQLEntityMetaData setSuperEntityClass(final Class<?> superEntityClass) {
		this.superEntityClass = superEntityClass;
		return this;
	}

	/**
	 * Get the entity class.
	 *
	 * @return the entityClass
	 */
	public Class<?> getEntityClass() {
		return entityClass;
	}

	/**
	 * Set the entity class
	 *
	 * @param entityClass
	 *            the entityClass to set
	 * @return this instance
	 */
	public GQLEntityMetaData setEntityClass(final Class<?> entityClass) {
		this.entityClass = entityClass;
		return this;
	}

	/**
	 * Get attributes of the entity
	 *
	 * @return the attributes
	 */
	public List<GQLAbstractAttributeMetaData> getAttributes() {
		return attributes;
	}

	/**
	 * Set the entity attributes
	 *
	 * @param attributes
	 *            the attributes to set
	 * @return this instance
	 */
	public GQLEntityMetaData setAttributes(final List<GQLAbstractAttributeMetaData> attributes) {
		this.attributes = attributes;
		return this;
	}

	/**
	 * @return the embedded
	 */
	public boolean isEmbedded() {
		return embedded;
	}

	/**
	 * @param embedded
	 *            the embedded to set
	 * @return this instance
	 */
	public GQLEntityMetaData setEmbedded(final boolean embedded) {
		this.embedded = embedded;
		return this;
	}

}
