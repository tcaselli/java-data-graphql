package com.daikit.graphql.test.introspection;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Introspection data
 *
 * @author tcaselli
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
	public void setSchema(IntrospectionSchema schema) {
		this.schema = schema;
	}

}
