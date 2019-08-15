package com.daikit.graphql.test.introspection;
/**
 * Introspection input value
 * 
 * @author Thibaut Caselli
 */
public class IntrospectionInputValue {
	private String name;
	private String description;
	private IntrospectionTypeRef type;
	private Object defaultValue;
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
	 * @return the type
	 */
	public IntrospectionTypeRef getType() {
		return type;
	}
	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(IntrospectionTypeRef type) {
		this.type = type;
	}
	/**
	 * @return the defaultValue
	 */
	public Object getDefaultValue() {
		return defaultValue;
	}
	/**
	 * @param defaultValue
	 *            the defaultValue to set
	 */
	public void setDefaultValue(Object defaultValue) {
		this.defaultValue = defaultValue;
	}
}
