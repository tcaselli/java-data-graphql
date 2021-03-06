package com.daikit.graphql.test.introspection;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Introspection data
 *
 * @author Thibaut Caselli
 */
public class IntrospectionData {

	@JsonProperty("__schema")
	private IntrospectionSchema schema;

	/**
	 * @return the schema
	 */
	public IntrospectionSchema getSchema() {
		return schema;
	}

	/**
	 * @param schema
	 *            the schema to set
	 */
	public void setSchema(final IntrospectionSchema schema) {
		this.schema = schema;
	}

}
