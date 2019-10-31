package com.daikit.graphql.meta;

import java.util.ArrayList;
import java.util.Collection;

import com.daikit.graphql.dynamicattribute.IGQLAbstractDynamicAttribute;
import com.daikit.graphql.meta.custommethod.GQLAbstractMethodMetaData;
import com.daikit.graphql.meta.entity.GQLEntityMetaData;
import com.daikit.graphql.meta.entity.GQLEnumMetaData;

/**
 * Class holding meta data model.
 *
 * @author Thibaut Caselli
 */
public class GQLMetaModel {

	private Collection<Class<?>> entityClasses = new ArrayList<>();
	private Collection<Class<?>> availableEmbeddedEntityClasses = new ArrayList<>();
	private Collection<IGQLAbstractDynamicAttribute<?>> dynamicAttributes = new ArrayList<>();
	// private Collection<IGQLAbstractCustomMethod<?>> methods = new
	// ArrayList<>();
	private Collection<Object> controllers = new ArrayList<>();
	private Collection<GQLEnumMetaData> enumMetaDatas = new ArrayList<>();
	private Collection<GQLEntityMetaData> entityMetaDatas = new ArrayList<>();
	private Collection<GQLAbstractMethodMetaData> methodMetaDatas = new ArrayList<>();

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// CONSTRUCTORS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	/**
	 * Hide constructor
	 */
	private GQLMetaModel() {
		// Prevent instantiation from other class
	}

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// PUBLIC METHODS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	/**
	 * Create {@link GQLMetaModel}. With this method, all meta datas will be
	 * automatically generated and registered from given entity classes,
	 * attributes and methods. Entities, enums, dynamic attributes and custom
	 * method meta data will be automatically generated and registered.<br>
	 * You can also provide entities and enums as meta datas with dynamic
	 * attributes already registered and custom methods meta data already
	 * created. For this use the other constructor.
	 *
	 * @param entityClasses
	 *            the collection of entity classes
	 * @param availableEmbeddedEntityClasses
	 *            the collection of all available embedded entity classes. It is
	 *            the only way to provide allowed extending classes for embedded
	 *            entities. You can leave this null or empty if you don't need
	 *            this advanced behavior.
	 * @param dynamicAttributes
	 *            the collection of {@link IGQLAbstractDynamicAttribute} to be
	 *            automatically registered
	 * @param controllers
	 *            the collection of controllers holding custom methods to be
	 *            added to schema
	 * @return the created instance
	 */
	public static GQLMetaModel createFromEntityClasses(final Collection<Class<?>> entityClasses,
			final Collection<Class<?>> availableEmbeddedEntityClasses,
			final Collection<IGQLAbstractDynamicAttribute<?>> dynamicAttributes, final Collection<Object> controllers) {
		final GQLMetaModel metaModel = new GQLMetaModel();
		metaModel.entityClasses = entityClasses;
		metaModel.availableEmbeddedEntityClasses = availableEmbeddedEntityClasses;
		metaModel.dynamicAttributes = dynamicAttributes;
		metaModel.controllers = controllers;
		return metaModel;
	}

	/**
	 * Create {@link GQLMetaModel}. With this method, entities and enums
	 * MetaData have been created manually but dynamic attributes and custom
	 * method meta data will be automatically generated and registered.<br>
	 * You can also provide entities with dynamic attributes already registered
	 * and custom methods meta data already created. For this use the other
	 * constructor.
	 *
	 * @param enumMetaDatas
	 *            the collection of all registered {@link GQLEnumMetaData}
	 * @param entityMetaDatas
	 *            the collection of all registered {@link GQLEntityMetaData}
	 * @param dynamicAttributes
	 *            the collection of {@link IGQLAbstractDynamicAttribute} to be
	 *            automatically registered (meta data will be created
	 *            automatically)
	 * @param controllers
	 *            the collection of controllers holding custom methods to be
	 *            added to schema
	 * @return the created instance
	 */
	public static GQLMetaModel createFromMetaDatas(final Collection<GQLEnumMetaData> enumMetaDatas,
			final Collection<GQLEntityMetaData> entityMetaDatas,
			final Collection<IGQLAbstractDynamicAttribute<?>> dynamicAttributes, final Collection<Object> controllers) {
		final GQLMetaModel metaModel = new GQLMetaModel();
		metaModel.enumMetaDatas = enumMetaDatas;
		metaModel.entityMetaDatas = entityMetaDatas;
		metaModel.dynamicAttributes = dynamicAttributes;
		metaModel.controllers = controllers;
		return metaModel;
	}

	/**
	 * Create {@link GQLMetaModel}. With this method, dynamic attributes should
	 * already be registered in entities and custom methods should already have
	 * their MetaData automatically generated and registered. For an automatic
	 * registration of dynamic attributes and custom method use the other
	 * constructor.
	 *
	 * @param enumMetaDatas
	 *            the collection of all registered {@link GQLEnumMetaData}
	 * @param entityMetaDatas
	 *            the collection of all registered {@link GQLEntityMetaData}
	 * @param methodMetaDatas
	 *            the collection of all registered
	 *            {@link GQLAbstractMethodMetaData}
	 * @return the created instance
	 */
	public static GQLMetaModel createFromMetaDatas(final Collection<GQLEnumMetaData> enumMetaDatas,
			final Collection<GQLEntityMetaData> entityMetaDatas,
			final Collection<GQLAbstractMethodMetaData> methodMetaDatas) {
		final GQLMetaModel metaModel = new GQLMetaModel();
		metaModel.enumMetaDatas = enumMetaDatas;
		metaModel.entityMetaDatas = entityMetaDatas;
		metaModel.methodMetaDatas = methodMetaDatas;
		return metaModel;
	}

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// GETTERS / SETTERS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	/**
	 * @return the entityClasses
	 */
	public Collection<Class<?>> getEntityClasses() {
		return entityClasses;
	}

	/**
	 * @return the availableEmbeddedEntityClasses
	 */
	public Collection<Class<?>> getAvailableEmbeddedEntityClasses() {
		return availableEmbeddedEntityClasses;
	}

	/**
	 * @return the dynamicAttributes
	 */
	public Collection<IGQLAbstractDynamicAttribute<?>> getDynamicAttributes() {
		return dynamicAttributes;
	}

	/**
	 * @return the controllers
	 */
	public Collection<Object> getControllers() {
		return controllers;
	}

	/**
	 * @return the enumMetaDatas
	 */
	public Collection<GQLEnumMetaData> getEnumMetaDatas() {
		return enumMetaDatas;
	}

	/**
	 * @return the entityMetaDatas
	 */
	public Collection<GQLEntityMetaData> getEntityMetaDatas() {
		return entityMetaDatas;
	}

	/**
	 * @return the methodMetaDatas
	 */
	public Collection<GQLAbstractMethodMetaData> getMethodMetaDatas() {
		return methodMetaDatas;
	}

}
