package com.daikit.graphql.datafetcher;

import com.daikit.graphql.meta.dynamic.attribute.GQLDynamicAttributeGetter;

import graphql.schema.DataFetchingEnvironment;
import graphql.schema.PropertyDataFetcher;

/**
 * Dynamic property data fetcher. By default {@link PropertyDataFetcher} would
 * be used for any field definition, this one is overriding default behavior for
 * dynamic attributes.
 *
 * @author tcaselli
 */
public class GQLDynamicAttributeDataFetcher extends PropertyDataFetcher<Object> {

	private final GQLDynamicAttributeGetter<?, ?> dynamicAttributeGetter;

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// CONSTRUCTORS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	/**
	 * Constructor
	 *
	 * @param propertyName
	 *            the property name
	 * @param dynamicAttributeGetter
	 *            the optional {@link GQLDynamicAttributeGetter} dynamic
	 *            attribute getter
	 */
	public GQLDynamicAttributeDataFetcher(final String propertyName,
			final GQLDynamicAttributeGetter<?, ?> dynamicAttributeGetter) {
		super(propertyName);
		this.dynamicAttributeGetter = dynamicAttributeGetter;
	}

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// METHODS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	@SuppressWarnings({"unchecked", "rawtypes"})
	@Override
	public Object get(final DataFetchingEnvironment environment) {
		Object ret;
		if (dynamicAttributeGetter == null) {
			ret = super.get(environment);
		} else {
			// The object holding the property
			final Object source = environment.getSource();
			ret = ((GQLDynamicAttributeGetter) dynamicAttributeGetter).getValue(source);
		}
		return ret;
	}

}
