package com.daikit.graphql.test.introspection;

/**
 * Introspection enumeration
 *
 * @author tcaselli
 */
public class IntrospectionEnum {

	private String name;
	private String description;
	private boolean isDeprecated;
	private String deprecationReason;
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * @return the isDeprecated
	 */
	public boolean isDeprecated() {
		return isDeprecated;
	}
	/**
	 * @param isDeprecated
	 *            the isDeprecated to set
	 */
	public void setDeprecated(boolean isDeprecated) {
		this.isDeprecated = isDeprecated;
	}
	/**
	 * @return the deprecationReason
	 */
	public String getDeprecationReason() {
		return deprecationReason;
	}
	/**
	 * @param deprecationReason
	 *            the deprecationReason to set
	 */
	public void setDeprecationReason(String deprecationReason) {
		this.deprecationReason = deprecationReason;
	}
}
