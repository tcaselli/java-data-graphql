package com.daikit.graphql.test.introspection;

/**
 * Introspection type reference
 *
 * @author Thibaut Caselli
 */
public class IntrospectionTypeRef {
	private IntrospectionTypeKindEnum kind;
	private String name;
	private IntrospectionTypeRef ofType;
	/**
	 * @return the kind
	 */
	public IntrospectionTypeKindEnum getKind() {
		return kind;
	}
	/**
	 * @param kind
	 *            the kind to set
	 */
	public void setKind(IntrospectionTypeKindEnum kind) {
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
	 * @return the ofType
	 */
	public IntrospectionTypeRef getOfType() {
		return ofType;
	}
	/**
	 * @param ofType
	 *            the ofType to set
	 */
	public void setOfType(IntrospectionTypeRef ofType) {
		this.ofType = ofType;
	}
}
