package com.daikit.graphql.meta;

/**
 * Abstract super class for all GQL meta data
 *
 * @author Thibaut Caselli
 */
public abstract class GQLAbstractMetaData {

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// METHODS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	protected void appendToString(final StringBuilder stringBuilder) {
		// To be overridden
	}

	protected void finalizeToString(final StringBuilder stringBuilder) {
		stringBuilder.append(")");
	}

	@Override
	public final String toString() {
		final StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("(");
		appendToString(stringBuilder);
		finalizeToString(stringBuilder);
		return stringBuilder.toString();
	}

}
