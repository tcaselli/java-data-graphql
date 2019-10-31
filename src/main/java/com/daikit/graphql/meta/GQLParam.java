package com.daikit.graphql.meta;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Annotation for a GraphQL method parameter within a {@link GQLController}
 *
 * @author Thibaut Caselli
 */
@Target({ElementType.PARAMETER})
@Retention(RUNTIME)
public @interface GQLParam {

	/**
	 * Name of the parameter. In case this annotation is not added the default
	 * parameter name value is the actual parameter name. If debug is not
	 * enabled during compilation, default parameter names will be arg0, arg1,
	 * arg2 etc.
	 *
	 * @return a the parameter name
	 */
	String value();

}
