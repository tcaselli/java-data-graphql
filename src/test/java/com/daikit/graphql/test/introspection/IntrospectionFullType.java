package com.daikit.graphql.test.introspection;

import java.util.ArrayList;
import java.util.List;

/**
 * Introspection full type
 *
 * @author Thibaut Caselli
 */
public class IntrospectionFullType {

	private String kind;
	private String name;
	private String description;
	private List<IntrospectionTypeField> fields = new ArrayList<>();
	private List<IntrospectionInputValue> inputFields = new ArrayList<>();
	private List<IntrospectionTypeRef> interfaces = new ArrayList<>();
	private List<IntrospectionEnum> enumValues = new ArrayList<>();
	private List<IntrospectionTypeRef> possibleTypes = new ArrayList<>();
	/**
	 * @return the kind
	 */
	public String getKind() {
		return kind;
	}
	/**
	 * @param kind
	 *            the kind to set
	 */
	public void setKind(String kind) {
		this.kind = kind;
	}
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
	 * @return the fields
	 */
	public List<IntrospectionTypeField> getFields() {
		return fields;
	}
	/**
	 * @param fields
	 *            the fields to set
	 */
	public void setFields(List<IntrospectionTypeField> fields) {
		this.fields = fields;
	}
	/**
	 * @return the inputFields
	 */
	public List<IntrospectionInputValue> getInputFields() {
		return inputFields;
	}
	/**
	 * @param inputFields
	 *            the inputFields to set
	 */
	public void setInputFields(List<IntrospectionInputValue> inputFields) {
		this.inputFields = inputFields;
	}
	/**
	 * @return the interfaces
	 */
	public List<IntrospectionTypeRef> getInterfaces() {
		return interfaces;
	}
	/**
	 * @param interfaces
	 *            the interfaces to set
	 */
	public void setInterfaces(List<IntrospectionTypeRef> interfaces) {
		this.interfaces = interfaces;
	}
	/**
	 * @return the enumValues
	 */
	public List<IntrospectionEnum> getEnumValues() {
		return enumValues;
	}
	/**
	 * @param enumValues
	 *            the enumValues to set
	 */
	public void setEnumValues(List<IntrospectionEnum> enumValues) {
		this.enumValues = enumValues;
	}
	/**
	 * @return the possibleTypes
	 */
	public List<IntrospectionTypeRef> getPossibleTypes() {
		return possibleTypes;
	}
	/**
	 * @param possibleTypes
	 *            the possibleTypes to set
	 */
	public void setPossibleTypes(List<IntrospectionTypeRef> possibleTypes) {
		this.possibleTypes = possibleTypes;
	}

}
