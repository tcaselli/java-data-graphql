package com.daikit.graphql.meta;

/**
 * Annotation for entity rights for customizing CRUD
 *
 * @author Thibaut Caselli
 */
public @interface GQLEntityRights {

	/**
	 * The role these rights are for. Default is empty for a config applied to
	 * all roles.
	 *
	 * @return a string role
	 */
	String[] roles() default {};

	/**
	 * Get whether this entity is excluded from transport to client. This is a
	 * shortcut for "rights = @{@link GQLEntityRights}(exclude = true)". If this
	 * is true then rights are ignored.
	 *
	 * @return a boolean (false by default)
	 */
	boolean exclude() default false;

	/**
	 * Get whether this entity is read only (if true it is equivalent to
	 * read==true AND save==false AND delete==false).
	 *
	 * @return a boolean (false by default)
	 */
	boolean readOnly() default false;

	/**
	 * Get whether this entity can be read by client.
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
