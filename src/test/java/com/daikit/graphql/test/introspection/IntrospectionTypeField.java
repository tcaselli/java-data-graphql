package com.daikit.graphql.test.introspection;

import java.util.ArrayList;
import java.util.List;

/**
 * Introspection type field
 *
 * @author tcaselli
 */
public class IntrospectionTypeField {

	private String name;
	private String description;
	private List<IntrospectionInputValue> args = new ArrayList<>();
	private boolean isDeprecated;
	private String deprecationReason;
	private IntrospectionTypeRef type;
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
	 * @return the args
	 */
	public List<IntrospectionInputValue> getArgs() {
		return args;
	}
	/**
	 * @param args
	 *            the args to set
	 */
	public void setArgs(List<IntrospectionInputValue> args) {
		this.args = args;
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
}
