package com.daikit.graphql.data.input;

import com.daikit.graphql.dynamicattribute.IGQLDynamicAttributeGetter;
import com.daikit.graphql.enums.GQLFilterOperatorEnum;

/**
 * Filter schemaConfig for {@link GQLListLoadConfig}
 *
 * @author Thibaut Caselli
 */
public class GQLFilterEntry {

	private String fieldName;
	private GQLFilterOperatorEnum operator;
	private Object value;

	private IGQLDynamicAttributeGetter<?, ?> dynamicAttributeGetter;

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
	 * @param dynamicAttributeGetter
	 *            the {@link IGQLDynamicAttributeGetter} related to the field
	 *            this entry is applied on
	 */
	public GQLFilterEntry(final String fieldName, final GQLFilterOperatorEnum operator, final Object value,
			final IGQLDynamicAttributeGetter<?, ?> dynamicAttributeGetter) {
		this.fieldName = fieldName;
		this.operator = operator;
		this.value = value;
		this.dynamicAttributeGetter = dynamicAttributeGetter;
	}

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// PUBLIC METHODS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	/**
	 * @return whether this filter is dynamic, ie whether
	 *         {@link #dynamicAttributeGetter} is not null.
	 */
	public boolean isDynamic() {

		return dynamicAttributeGetter != null;
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
	public void setFieldName(final String fieldName) {
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
	public void setOperator(final GQLFilterOperatorEnum operator) {
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
	public void setValue(final Object value) {
		this.value = value;
	}

	/**
	 * @return the dynamicAttributeGetter
	 */
	public IGQLDynamicAttributeGetter<?, ?> getDynamicAttributeGetter() {
		return dynamicAttributeGetter;
	}

	/**
	 * @param dynamicAttributeGetter
	 *            the dynamicAttributeGetter to set
	 */
	public void setDynamicAttributeGetter(final IGQLDynamicAttributeGetter<?, ?> dynamicAttributeGetter) {
		this.dynamicAttributeGetter = dynamicAttributeGetter;
	}

}
