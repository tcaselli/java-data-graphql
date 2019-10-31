package com.daikit.graphql.meta;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.daikit.graphql.enums.GQLMethodType;

/**
 * Annotation for a GraphQL method within a {@link GQLController}
 *
 * @author Thibaut Caselli
 */
@Target({ElementType.METHOD})
@Retention(RUNTIME)
public @interface GQLMethod {

	/**
	 * Name of the method. Byt default it is an empty String so the method name
	 * is the actual name of the annotated method.
	 *
	 * @return a String
	 */
	String value() default "";

	/**
	 * Type of the method. By default type is {@link GQLMethodType#MUTATION}
	 *
	 * @return the {@link GQLMethodType}
	 */
	GQLMethodType type() default GQLMethodType.MUTATION;

}
