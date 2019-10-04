package com.daikit.graphql.test.data;

import com.daikit.graphql.meta.GQLAttribute;

/**
 * Entity class 5
 *
 * @author Thibaut Caselli
 */
public class Entity6 extends AbstractEntity {

	@GQLAttribute(read = false)
	private String attr1;
	@GQLAttribute(save = false)
	private String attr2;
	@GQLAttribute(nullable = false)
	private String attr3;
	@GQLAttribute(nullableForUpdate = false)
	private String attr4;
	@GQLAttribute(nullableForCreate = false)
	private String attr5;
	@GQLAttribute(filter = false)
	private String attr6;

	/**
	 * @return the attr1
	 */
	public String getAttr1() {
		return attr1;
	}
	/**
	 * @param attr1
	 *            the attr1 to set
	 */
	public void setAttr1(final String attr1) {
		this.attr1 = attr1;
	}
	/**
	 * @return the attr2
	 */
	public String getAttr2() {
		return attr2;
	}
	/**
	 * @param attr2
	 *            the attr2 to set
	 */
	public void setAttr2(final String attr2) {
		this.attr2 = attr2;
	}
	/**
	 * @return the attr3
	 */
	public String getAttr3() {
		return attr3;
	}
	/**
	 * @param attr3
	 *            the attr3 to set
	 */
	public void setAttr3(final String attr3) {
		this.attr3 = attr3;
	}
	/**
	 * @return the attr4
	 */
	public String getAttr4() {
		return attr4;
	}
	/**
	 * @param attr4
	 *            the attr4 to set
	 */
	public void setAttr4(final String attr4) {
		this.attr4 = attr4;
	}
	/**
	 * @return the attr5
	 */
	public String getAttr5() {
		return attr5;
	}
	/**
	 * @param attr5
	 *            the attr5 to set
	 */
	public void setAttr5(String attr5) {
		this.attr5 = attr5;
	}
	/**
	 * @return the attr6
	 */
	public String getAttr6() {
		return attr6;
	}
	/**
	 * @param attr6
	 *            the attr6 to set
	 */
	public void setAttr6(String attr6) {
		this.attr6 = attr6;
	}
}
