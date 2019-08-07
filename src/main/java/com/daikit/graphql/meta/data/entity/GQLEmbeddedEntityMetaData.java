package com.daikit.graphql.meta.data.entity;

/**
 * Embedded entity meta data
 *
 * @author tcaselli
 */
public class GQLEmbeddedEntityMetaData extends GQLAbstractEntityMetaData {

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// CONSTRUCTORS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	/**
	 * Default constructor
	 */
	public GQLEmbeddedEntityMetaData() {
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
	public GQLEmbeddedEntityMetaData(String name, Class<?> entityClass) {
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
	public GQLEmbeddedEntityMetaData(String name, Class<?> entityClass, boolean concrete) {
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
	public GQLEmbeddedEntityMetaData(String name, Class<?> entityClass, Class<?> superEntityClass) {
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
	public GQLEmbeddedEntityMetaData(String name, Class<?> entityClass, Class<?> superEntityClass, boolean concrete) {
		super(name, entityClass, superEntityClass, concrete);
	}

}
