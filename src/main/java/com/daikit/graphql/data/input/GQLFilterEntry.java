package com.daikit.graphql.data.input;

import com.daikit.graphql.dynamicattribute.IGQLDynamicAttributeFilter;
import com.daikit.graphql.enums.GQLFilterOperatorEnum;

/**
 * Filter config for {@link GQLListLoadConfig}
 *
 * @author tcaselli
 */
public class GQLFilterEntry {

	private String field;
	private GQLFilterOperatorEnum operator;
	private Object value;

	private IGQLDynamicAttributeFilter<?, ?, ?> fieldFilter;

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// CONSTRUCTORS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	/**
	 * Constructor
	 *
	 * @param field
	 *            the field name
	 * @param operator
	 *            the {@link GQLFilterOperatorEnum}
	 * @param value
	 *            the value
	 */
	public GQLFilterEntry(final String field, final GQLFilterOperatorEnum operator, final Object value) {
		this(field, operator, value, null);
	}

	/**
	 * Constructor
	 *
	 * @param field
	 *            the field name
	 * @param operator
	 *            the {@link GQLFilterOperatorEnum}
	 * @param value
	 *            the value
	 * @param fieldFilter
	 *            the {@link IGQLDynamicAttributeFilter} related to the field
	 *            this entry is applied on
	 */
	public GQLFilterEntry(final String field, final GQLFilterOperatorEnum operator, final Object value,
			IGQLDynamicAttributeFilter<?, ?, ?> fieldFilter) {
		this.field = field;
		this.operator = operator;
		this.value = value;
		this.fieldFilter = fieldFilter;
	}

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// GETTERS / SETTERS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	/**
	 * @return the field
	 */
	public String getField() {
		return field;
	}
	/**
	 * @param field
	 *            the field to set
	 */
	public void setField(String field) {
		this.field = field;
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
	 * @return the fieldFilter
	 */
	public IGQLDynamicAttributeFilter<?, ?, ?> getFieldFilter() {
		return fieldFilter;
	}

	/**
	 * @param fieldFilter
	 *            the fieldFilter to set
	 */
	public void setFieldFilter(IGQLDynamicAttributeFilter<?, ?, ?> fieldFilter) {
		this.fieldFilter = fieldFilter;
	}
}