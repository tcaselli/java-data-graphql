package com.daikit.graphql.meta;

import java.util.ArrayList;
import java.util.Collection;

import com.daikit.graphql.custommethod.GQLAbstractCustomMethod;
import com.daikit.graphql.custommethod.IGQLAbstractCustomMethod;
import com.daikit.graphql.dynamicattribute.IGQLAbstractDynamicAttribute;
import com.daikit.graphql.meta.custommethod.GQLAbstractMethodMetaData;
import com.daikit.graphql.meta.entity.GQLEntityMetaData;
import com.daikit.graphql.meta.entity.GQLEnumMetaData;

/**
 * Class holding meta data model
 *
 * @author Thibaut Caselli
 */
public class GQLMetaModel {

	private Collection<IGQLAbstractDynamicAttribute<?>> dynamicAttributes = new ArrayList<>();
	private Collection<IGQLAbstractCustomMethod<?>> methods = new ArrayList<>();
	private Collection<GQLEnumMetaData> enumMetaDatas = new ArrayList<>();
	private Collection<GQLEntityMetaData> entityMetaDatas = new ArrayList<>();
	private Collection<GQLAbstractMethodMetaData> methodMetaDatas = new ArrayList<>();

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// CONSTRUCTORS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	/**
	 * Default constructor.
	 */
	public GQLMetaModel() {
		// Nothing done
	}

	/**
	 * Constructor. Dynamic attributes and custom method meta data will be
	 * automatically generated and registered.<br>
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
	 * @param customMethods
	 *            the collection of {@link GQLAbstractCustomMethod} to be
	 *            automatically registered (meta data will be created
	 *            automatically)
	 */
	public GQLMetaModel(final Collection<GQLEnumMetaData> enumMetaDatas,
			final Collection<GQLEntityMetaData> entityMetaDatas,
			final Collection<IGQLAbstractDynamicAttribute<?>> dynamicAttributes,
			final Collection<IGQLAbstractCustomMethod<?>> customMethods) {
		this.enumMetaDatas = enumMetaDatas;
		this.entityMetaDatas = entityMetaDatas;
		this.dynamicAttributes = dynamicAttributes;
		this.methods = customMethods;
	}

	/**
	 * Constructor. With this constructor dynamic attributes should already be
	 * registered in entities and custom methods should already have their
	 * MetaData created. For an automatic registration of dynamic attributes and
	 * custom method use the other constructor.
	 *
	 * @param enumMetaDatas
	 *            the collection of all registered {@link GQLEnumMetaData}
	 * @param entityMetaDatas
	 *            the collection of all registered {@link GQLEntityMetaData}
	 * @param methodMetaDatas
	 *            the collection of all registered
	 *            {@link GQLAbstractMethodMetaData}
	 */
	public GQLMetaModel(final Collection<GQLEnumMetaData> enumMetaDatas,
			final Collection<GQLEntityMetaData> entityMetaDatas,
			final Collection<GQLAbstractMethodMetaData> methodMetaDatas) {
		this.enumMetaDatas = enumMetaDatas;
		this.entityMetaDatas = entityMetaDatas;
		this.methodMetaDatas = methodMetaDatas;
	}

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// GETTERS / SETTERS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	/**
	 * @return the dynamicAttributes
	 */
	public Collection<IGQLAbstractDynamicAttribute<?>> getDynamicAttributes() {
		return dynamicAttributes;
	}

	/**
	 * @param dynamicAttributes
	 *            the dynamicAttributes to set
	 */
	public void setDynamicAttributes(Collection<IGQLAbstractDynamicAttribute<?>> dynamicAttributes) {
		this.dynamicAttributes = dynamicAttributes;
	}

	/**
	 * @return the methods
	 */
	public Collection<IGQLAbstractCustomMethod<?>> getMethods() {
		return methods;
	}

	/**
	 * @param methods
	 *            the methods to set
	 */
	public void setMethods(Collection<IGQLAbstractCustomMethod<?>> methods) {
		this.methods = methods;
	}

	/**
	 * @return the enumMetaDatas
	 */
	public Collection<GQLEnumMetaData> getEnumMetaDatas() {
		return enumMetaDatas;
	}

	/**
	 * @param enumMetaDatas
	 *            the enumMetaDatas to set
	 */
	public void setEnumMetaDatas(Collection<GQLEnumMetaData> enumMetaDatas) {
		this.enumMetaDatas = enumMetaDatas;
	}

	/**
	 * @return the entityMetaDatas
	 */
	public Collection<GQLEntityMetaData> getEntityMetaDatas() {
		return entityMetaDatas;
	}

	/**
	 * @param entityMetaDatas
	 *            the entityMetaDatas to set
	 */
	public void setEntityMetaDatas(Collection<GQLEntityMetaData> entityMetaDatas) {
		this.entityMetaDatas = entityMetaDatas;
	}

	/**
	 * @return the methodMetaDatas
	 */
	public Collection<GQLAbstractMethodMetaData> getMethodMetaDatas() {
		return methodMetaDatas;
	}

	/**
	 * @param methodMetaDatas
	 *            the methodMetaDatas to set
	 */
	public void setMethodMetaDatas(Collection<GQLAbstractMethodMetaData> methodMetaDatas) {
		this.methodMetaDatas = methodMetaDatas;
	}

}
