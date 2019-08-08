package com.daikit.graphql.test.introspection;

/**
 * Introspection result
 *
 * @author tcaselli
 */
public class IntrospectionResult {

	private IntrospectionData data;

	/**
	 * @return the data
	 */
	public IntrospectionData getData() {
		return data;
	}

	/**
	 * @param data
	 *            the data to set
	 */
	public void setData(IntrospectionData data) {
		this.data = data;
	}
}
