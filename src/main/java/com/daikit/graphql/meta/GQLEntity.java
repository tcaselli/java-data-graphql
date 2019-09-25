package com.daikit.graphql.meta;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Annotation for entity attribute for customizing CRUD
 *
 * @author tcaselli
 * @version $Revision$ Last modifier: $Author$ Last commit: $Date$
 */
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.TYPE})
@Retention(RUNTIME)
public @interface GQLEntity {

	/**
	 * The description that will be generated for this entity in the GraphQL
	 * schema.
	 *
	 * @return the entity description
	 */
	String description() default "";

	/**
	 * Provide a value here in order to change the name under which the
	 * annotated entity class will be accessible in the API. If empty or null
	 * then the annotated entity class simple name will be used.
	 *
	 * @return the entity name
	 */
	String name() default "";

	/**
	 * Get whether this entity is excluded from transport to client (if true it
	 * is equivalent to read==false AND save==false AND delete==false AND
	 * filter==false)
	 *
	 * @return a boolean (false by default)
	 */
	boolean exclude() default false;

	/**
	 * Get whether this entity is read only (if true it is equivalent to
	 * read==true AND save==false AND delete==false)
	 *
	 * @return a boolean (false by default)
	 */
	boolean readOnly() default false;

	/**
	 * Get whether this entity can be read by client
	 *
	 * @return a boolean (true by default)
	 */
	boolean read() default true;

	/**
	 * Get whether this entity can be saved by client.
	 *
	 * @return a boolean (true by default)
	 */
	boolean save() default true;

	/**
	 * Get whether this entity can be deleted by client.
	 *
	 * @return a boolean (true by default)
	 */
	boolean delete() default true;

}
