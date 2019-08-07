package com.daikit.graphql.meta.data.entity;

import java.util.ArrayList;
import java.util.List;

import com.daikit.graphql.meta.data.GQLAbstractMetaData;
import com.daikit.graphql.meta.data.attribute.GQLAbstractAttributeMetaData;

/**
 * Abstract super class for all entity meta datas (concrete, abstract, embedded
 * and abstract embedded)
 *
 * @author tcaselli
 */
public class GQLAbstractEntityMetaData extends GQLAbstractMetaData {

	private boolean concrete = true;
	private String name;
	private Class<?> superEntityClass;
	private Class<?> entityClass;
	private List<GQLAbstractAttributeMetaData> attributes = new ArrayList<>();

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// CONSTRUCTORS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	/**
	 * Default constructor
	 */
	public GQLAbstractEntityMetaData() {
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
	public GQLAbstractEntityMetaData(String name, Class<?> entityClass) {
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
	public GQLAbstractEntityMetaData(String name, Class<?> entityClass, boolean concrete) {
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
	public GQLAbstractEntityMetaData(String name, Class<?> entityClass, Class<?> superEntityClass) {
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
	public GQLAbstractEntityMetaData(String name, Class<?> entityClass, Class<?> superEntityClass, boolean concrete) {
		this.name = name;
		this.entityClass = entityClass;
		this.superEntityClass = superEntityClass;
		this.concrete = concrete;
	}

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// METHODS
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

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// GETTERS / SETTERS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

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
	 */
	public void setConcrete(final boolean concrete) {
		this.concrete = concrete;
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
	 */
	public void setName(final String name) {
		this.name = name;
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
	 */
	public void setSuperEntityClass(final Class<?> superEntityClass) {
		this.superEntityClass = superEntityClass;
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
	 */
	public void setEntityClass(final Class<?> entityClass) {
		this.entityClass = entityClass;
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
	 */
	public void setAttributes(final List<GQLAbstractAttributeMetaData> attributes) {
		this.attributes = attributes;
	}
}
