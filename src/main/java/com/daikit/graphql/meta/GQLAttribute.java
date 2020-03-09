package com.daikit.graphql.meta;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Annotation for entity attribute or dynamic attribute class for customizing
 * CRUD, nullable and filtering
 *
 * @author tcaselli
 * @version $Revision$ Last modifier: $Author$ Last commit: $Date$
 */
@Target({ElementType.FIELD, ElementType.TYPE})
@Retention(RUNTIME)
public @interface GQLAttribute {

	/**
	 * The description that will be generated for this attribute in the GraphQL
	 * schema.
	 *
	 * @return the attribute description
	 */
	String description() default "";

	/**
	 * Provide a value here in order to change the name under which the
	 * annotated attribute will be accessible in the API. If empty or null then
	 * the annotated attribute name will be used.
	 *
	 * @return the attribute name
	 */
	String name() default "";

	/**
	 * Get whether this attribute is excluded from transport to client. This is
	 * a shortcut for "rights = @{@link GQLAttributeRights}(exclude = true)". If
	 * this is true then rights are ignored.
	 *
	 * @return a boolean (false by default)
	 */
	boolean exclude() default false;

	/**
	 * Get whether this attribute can be used as a filter property by client.
	 *
	 * @return a boolean (true by default)
	 */
	boolean filter() default true;

	/**
	 * Rights for annotated attribute
	 *
	 * @return an array of {@link GQLAttributeRights}
	 */
	GQLAttributeRights[] rights() default {};

}
