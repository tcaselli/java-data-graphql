package com.daikit.graphql.test.introspection;

import java.util.ArrayList;
import java.util.List;

/**
 * Introspection schema
 *
 * @author Thibaut Caselli
 */
public class IntrospectionSchema {

	private List<IntrospectionFullType> types = new ArrayList<>();

	/**
	 * @return the types
	 */
	public List<IntrospectionFullType> getTypes() {
		return types;
	}

	/**
	 * @param types
	 *            the types to set
	 */
	public void setTypes(final List<IntrospectionFullType> types) {
		this.types = types;
	}
}
