package com.daikit.graphql.test.introspection;

/**
 * Introspection enumeration
 *
 * @author Thibaut Caselli
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
	public void setName(final String name) {
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
	public void setDescription(final String description) {
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
	public void setDeprecated(final boolean isDeprecated) {
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
	public void setDeprecationReason(final String deprecationReason) {
		this.deprecationReason = deprecationReason;
	}
}
