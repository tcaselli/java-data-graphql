package com.daikit.graphql.test.introspection;

/**
 * Introspection result
 *
 * @author Thibaut Caselli
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
	public void setData(final IntrospectionData data) {
		this.data = data;
	}
}
