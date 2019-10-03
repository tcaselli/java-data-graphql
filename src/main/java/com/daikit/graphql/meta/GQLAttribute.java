package com.daikit.graphql.meta;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Annotation for entity attribute for customizing CRUD, nullable and filtering
 *
 * @author tcaselli
 * @version $Revision$ Last modifier: $Author$ Last commit: $Date$
 */
@Target({ElementType.FIELD})
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
	 * Get whether this attribute is excluded from transport to client (if true
	 * it is equivalent to read==false AND save==false).
	 *
	 * @return a boolean (false by default)
	 */
	boolean exclude() default false;

	/**
	 * Get whether this attribute is read only (if true it is equivalent to
	 * read==true AND save==false).
	 *
	 * @return a boolean (false by default)
	 */
	boolean readOnly() default false;

	/**
	 * Get whether this attribute is readable by client.
	 *
	 * @return a boolean (true by default)
	 */
	boolean read() default true;

	/**
	 * Get whether this attribute can be written by client when
	 * creating/updating parent entity instance.
	 *
	 * @return a boolean (true by default)
	 */
	boolean save() default true;

	/**
	 * Get whether this attribute can used as a filter property by client.
	 *
	 * @return a boolean (true by default)
	 */
	boolean filter() default true;

	/**
	 * Get whether this attribute can be nullified when parent entity is
	 * created/updated. If you need more granularity consider using
	 * {@link #nullableForCreation()} or {@link #nullableForUpdate()} instead.
	 *
	 * @return a boolean (true by default)
	 */
	boolean nullable() default true;

	/**
	 * Get whether this attribute can be nullified when parent entity is
	 * created. Setting this property has precedence over setting
	 * {@link #nullable()} property.
	 *
	 * @return a boolean (true by default)
	 */
	boolean nullableForCreation() default true;

	/**
	 * Get whether this attribute can be nullified when parent entity is
	 * updated. Setting this property has precedence over setting
	 * {@link #nullable()} property.
	 *
	 * @return a boolean (true by default)
	 */
	boolean nullableForUpdate() default true;

}
