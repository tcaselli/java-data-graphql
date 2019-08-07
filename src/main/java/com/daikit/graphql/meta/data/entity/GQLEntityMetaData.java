package com.daikit.graphql.meta.data.entity;

/**
 * GraphQL entity meta data. This entity corresponds to a persistence layer
 * model object.
 *
 * @author tcaselli
 */
public class GQLEntityMetaData extends GQLAbstractEntityMetaData {

	private boolean readable = true;
	private boolean saveable = true;

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
	public GQLEntityMetaData(String name, Class<?> entityClass) {
		super(name, entityClass);
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
	public GQLEntityMetaData(String name, Class<?> entityClass, boolean concrete) {
		super(name, entityClass, concrete);
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
	public GQLEntityMetaData(String name, Class<?> entityClass, Class<?> superEntityClass) {
		super(name, entityClass, superEntityClass);
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
	public GQLEntityMetaData(String name, Class<?> entityClass, Class<?> superEntityClass, boolean concrete) {
		super(name, entityClass, superEntityClass, concrete);
	}

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// METHODS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	@Override
	protected void appendToString(final StringBuilder stringBuilder) {
		stringBuilder.append(readable ? "[R]" : "").append(saveable ? "[S]" : "");
		super.appendToString(stringBuilder);
	}

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// GETTERS / SETTERS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	/**
	 * Get whether this entity can be retrieved in queries. Default is
	 * <code>true</code>.
	 *
	 * @return the readable
	 */
	public boolean isReadable() {
		return readable;
	}

	/**
	 * Set whether queries will be available in schema for retrieving this
	 * entity. Default is <code>true</code>.
	 *
	 * @param readable
	 *            the readable to set
	 */
	public void setReadable(final boolean readable) {
		this.readable = readable;
	}

	/**
	 * Get whether a save mutation will be available in schema for this entity.
	 * Default is <code>true</code>.
	 *
	 * @return the saveable
	 */
	public boolean isSaveable() {
		return saveable;
	}

	/**
	 * Set whether a save mutation will be available in schema for this entity.
	 * Default is <code>true</code>.
	 *
	 * @param saveable
	 *            the saveable to set
	 */
	public void setSaveable(final boolean saveable) {
		this.saveable = saveable;
	}

}
