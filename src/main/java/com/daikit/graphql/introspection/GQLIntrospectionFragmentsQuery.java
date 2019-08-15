package com.daikit.graphql.introspection;

/**
 * Introspection fragments query
 *
 * @author Thibaut Caselli
 */
public interface GQLIntrospectionFragmentsQuery {

	/**
	 * Introspection fragments query
	 */
	String INTROSPECTION_FRAGMENTS_QUERY = "\n" + //
			"  query IntrospectionFragmentsQuery {\n" + //
			"    __schema {\n" + //
			"      types {\n" + //
			"        kind\n" + //
			"        name\n" + //
			"        possibleTypes {\n" + //
			"          name\n" + //
			"        }\n" + //
			"      }\n" + //
			"    }\n" + //
			"  }\n" + //
			"\n";
}
