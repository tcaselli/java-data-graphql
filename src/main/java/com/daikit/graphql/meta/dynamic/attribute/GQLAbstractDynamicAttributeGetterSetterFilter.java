package com.daikit.graphql.meta.dynamic.attribute;

import java.lang.reflect.Type;

import com.daikit.generics.utils.GenericsUtils;

/**
 * Abstract super class for {@link GQLDynamicAttributeGetter} ,
 * {@link GQLDynamicAttributeSetter} GQLDyna
 *
 * @author tcaselli
 */
public abstract interface GQLAbstractDynamicAttributeGetterSetterFilter {

	/**
	 * @return the property name that will be available in GraphQL schema
	 */
	String getPropertyName();

	/**
	 * Get input type from generic configuration of this class
	 *
	 * @return the input type
	 */
	default Class<?> getInputType() {
		return GenericsUtils.getRawClass(GenericsUtils.getTypeArguments(getClass(),
				this instanceof GQLDynamicAttributeGetter
						? GQLDynamicAttributeGetter.class
						: GQLDynamicAttributeSetter.class)
				.get(0));
	}

	/**
	 * Get output type from generic configuration of this class
	 *
	 * @return the output type
	 */
	default Type getOutputType() {
		return GenericsUtils.getTypeArguments(getClass(),
				this instanceof GQLDynamicAttributeGetter
						? GQLDynamicAttributeGetter.class
						: GQLDynamicAttributeSetter.class)
				.get(1);
	}
}
