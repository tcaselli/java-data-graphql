package com.daikit.graphql.data.input;

import com.daikit.graphql.dynamicattribute.IGQLDynamicAttributeFilter;
import com.daikit.graphql.enums.GQLFilterOperatorEnum;

/**
 * Filter config for {@link GQLListLoadConfig}
 *
 * @author Thibaut Caselli
 */
public class GQLFilterEntry {

	private String fieldName;
	private GQLFilterOperatorEnum operator;
	private Object value;

	private IGQLDynamicAttributeFilter<?, ?, ?> dynamicAttributeFilter;

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// CONSTRUCTORS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	/**
	 * Constructor
	 *
	 * @param fieldName
	 *            the field name
	 * @param operator
	 *            the {@link GQLFilterOperatorEnum}
	 * @param value
	 *            the value
	 */
	public GQLFilterEntry(final String fieldName, final GQLFilterOperatorEnum operator, final Object value) {
		this(fieldName, operator, value, null);
	}

	/**
	 * Constructor
	 *
	 * @param fieldName
	 *            the field name
	 * @param operator
	 *            the {@link GQLFilterOperatorEnum}
	 * @param value
	 *            the value
	 * @param dynamicAttributeFilter
	 *            the {@link IGQLDynamicAttributeFilter} related to the field
	 *            this entry is applied on
	 */
	public GQLFilterEntry(final String fieldName, final GQLFilterOperatorEnum operator, final Object value,
			IGQLDynamicAttributeFilter<?, ?, ?> dynamicAttributeFilter) {
		this.fieldName = fieldName;
		this.operator = operator;
		this.value = value;
		this.dynamicAttributeFilter = dynamicAttributeFilter;
	}

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// GETTERS / SETTERS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	/**
	 * @return the fieldName
	 */
	public String getFieldName() {
		return fieldName;
	}

	/**
	 * @param fieldName
	 *            the fieldName to set
	 */
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	/**
	 * @return the operator
	 */
	public GQLFilterOperatorEnum getOperator() {
		return operator;
	}

	/**
	 * @param operator
	 *            the operator to set
	 */
	public void setOperator(GQLFilterOperatorEnum operator) {
		this.operator = operator;
	}

	/**
	 * @return the value
	 */
	public Object getValue() {
		return value;
	}

	/**
	 * @param value
	 *            the value to set
	 */
	public void setValue(Object value) {
		this.value = value;
	}

	/**
	 * @return the dynamicAttributeFilter
	 */
	public IGQLDynamicAttributeFilter<?, ?, ?> getDynamicAttributeFilter() {
		return dynamicAttributeFilter;
	}

	/**
	 * @param dynamicAttributeFilter
	 *            the dynamicAttributeFilter to set
	 */
	public void setDynamicAttributeFilter(IGQLDynamicAttributeFilter<?, ?, ?> dynamicAttributeFilter) {
		this.dynamicAttributeFilter = dynamicAttributeFilter;
	}

}
