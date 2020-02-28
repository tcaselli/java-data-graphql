package com.daikit.graphql.test.data;

import com.daikit.graphql.meta.GQLAttribute;
import com.daikit.graphql.meta.GQLAttributeRights;

/**
 * Entity class 5
 *
 * @author Thibaut Caselli
 */
public class Entity6 extends AbstractEntity {

	@GQLAttribute(rights = @GQLAttributeRights(read = false))
	private String attr1;
	@GQLAttribute(rights = @GQLAttributeRights(save = false))
	private String attr2;
	@GQLAttribute(rights = @GQLAttributeRights(nullable = false))
	private String attr3;
	@GQLAttribute(rights = @GQLAttributeRights(nullableForUpdate = false))
	private String attr4;
	@GQLAttribute(rights = @GQLAttributeRights(nullableForCreate = false))
	private String attr5;
	@GQLAttribute(filter = false)
	private String attr6;
	@GQLAttribute(rights = @GQLAttributeRights(mandatory = true))
	private String attr7;
	@GQLAttribute(rights = @GQLAttributeRights(mandatoryForUpdate = true))
	private String attr8;
	@GQLAttribute(rights = @GQLAttributeRights(mandatoryForCreate = true))
	private String attr9;
	@GQLAttribute(rights = @GQLAttributeRights(roles = Roles.ROLE1, read = false))
	private String attr10;
	@GQLAttribute(rights = @GQLAttributeRights(roles = Roles.ROLE1, save = false))
	private String attr11;
	@GQLAttribute(rights = @GQLAttributeRights(roles = Roles.ROLE1, nullable = false))
	private String attr12;
	@GQLAttribute(rights = @GQLAttributeRights(roles = Roles.ROLE1, nullableForUpdate = false))
	private String attr13;
	@GQLAttribute(rights = @GQLAttributeRights(roles = Roles.ROLE1, nullableForCreate = false))
	private String attr14;
	@GQLAttribute(rights = @GQLAttributeRights(roles = Roles.ROLE1, mandatory = true))
	private String attr15;
	@GQLAttribute(rights = @GQLAttributeRights(roles = Roles.ROLE1, mandatoryForUpdate = true))
	private String attr16;
	@GQLAttribute(rights = @GQLAttributeRights(roles = Roles.ROLE1, mandatoryForCreate = true))
	private String attr17;
	@GQLAttribute(rights = @GQLAttributeRights(roles = {Roles.ROLE1, Roles.ROLE2}, read = false))
	private String attr18;
	@GQLAttribute(rights = {//
			@GQLAttributeRights(roles = Roles.ROLE1, read = false),
			@GQLAttributeRights(roles = Roles.ROLE2, read = false)})
	private String attr19;
	@GQLAttribute(rights = {//
			@GQLAttributeRights(roles = Roles.ROLE1, read = false),
			@GQLAttributeRights(roles = Roles.ROLE2, save = false)})
	private String attr20;
	@GQLAttribute(rights = {//
			@GQLAttributeRights(roles = {Roles.ROLE1, Roles.ROLE2}, read = false, nullable = false),
			@GQLAttributeRights(roles = {Roles.ROLE3, Roles.ROLE4}, save = false, mandatory = true)})
	private String attr21;
	@GQLAttribute(rights = {//
			@GQLAttributeRights(read = false), //
			@GQLAttributeRights(roles = Roles.ROLE1, read = true)})
	private String attr22;

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
	/**
	 * @return the attr10
	 */
	public String getAttr10() {
		return attr10;
	}
	/**
	 * @param attr10
	 *            the attr10 to set
	 */
	public void setAttr10(String attr10) {
		this.attr10 = attr10;
	}
	/**
	 * @return the attr11
	 */
	public String getAttr11() {
		return attr11;
	}
	/**
	 * @param attr11
	 *            the attr11 to set
	 */
	public void setAttr11(String attr11) {
		this.attr11 = attr11;
	}
	/**
	 * @return the attr12
	 */
	public String getAttr12() {
		return attr12;
	}
	/**
	 * @param attr12
	 *            the attr12 to set
	 */
	public void setAttr12(String attr12) {
		this.attr12 = attr12;
	}
	/**
	 * @return the attr13
	 */
	public String getAttr13() {
		return attr13;
	}
	/**
	 * @param attr13
	 *            the attr13 to set
	 */
	public void setAttr13(String attr13) {
		this.attr13 = attr13;
	}
	/**
	 * @return the attr14
	 */
	public String getAttr14() {
		return attr14;
	}
	/**
	 * @param attr14
	 *            the attr14 to set
	 */
	public void setAttr14(String attr14) {
		this.attr14 = attr14;
	}
	/**
	 * @return the attr15
	 */
	public String getAttr15() {
		return attr15;
	}
	/**
	 * @param attr15
	 *            the attr15 to set
	 */
	public void setAttr15(String attr15) {
		this.attr15 = attr15;
	}
	/**
	 * @return the attr16
	 */
	public String getAttr16() {
		return attr16;
	}
	/**
	 * @param attr16
	 *            the attr16 to set
	 */
	public void setAttr16(String attr16) {
		this.attr16 = attr16;
	}
	/**
	 * @return the attr17
	 */
	public String getAttr17() {
		return attr17;
	}
	/**
	 * @param attr17
	 *            the attr17 to set
	 */
	public void setAttr17(String attr17) {
		this.attr17 = attr17;
	}
	/**
	 * @return the attr18
	 */
	public String getAttr18() {
		return attr18;
	}
	/**
	 * @param attr18
	 *            the attr18 to set
	 */
	public void setAttr18(String attr18) {
		this.attr18 = attr18;
	}
	/**
	 * @return the attr19
	 */
	public String getAttr19() {
		return attr19;
	}
	/**
	 * @param attr19
	 *            the attr19 to set
	 */
	public void setAttr19(String attr19) {
		this.attr19 = attr19;
	}
	/**
	 * @return the attr20
	 */
	public String getAttr20() {
		return attr20;
	}
	/**
	 * @param attr20
	 *            the attr20 to set
	 */
	public void setAttr20(String attr20) {
		this.attr20 = attr20;
	}
	/**
	 * @return the attr21
	 */
	public String getAttr21() {
		return attr21;
	}
	/**
	 * @param attr21
	 *            the attr21 to set
	 */
	public void setAttr21(String attr21) {
		this.attr21 = attr21;
	}
	/**
	 * @return the attr22
	 */
	public String getAttr22() {
		return attr22;
	}
	/**
	 * @param attr22
	 *            the attr22 to set
	 */
	public void setAttr22(String attr22) {
		this.attr22 = attr22;
	}
}
