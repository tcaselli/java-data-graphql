package com.daikit.graphql.meta;

/**
 * Annotation for entity attribute rights for customizing CRUD
 *
 * @author Thibaut Caselli
 */
public @interface GQLAttributeRights {

	/**
	 * The role these rights are for. Default is empty for a config applied to
	 * all roles.
	 *
	 * @return a string role
	 */
	String[] roles() default {};

	/**
	 * Get whether this attribute is excluded from transport to client. If this
	 * is true then rights are ignored for this attribute and these roles.
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
	 * Get whether this attribute can be nullified when parent entity is
	 * created/updated. If you need more granularity consider using
	 * {@link #nullableForCreate()} or {@link #nullableForUpdate()} instead.
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
	boolean nullableForCreate() default true;

	/**
	 * Get whether this attribute can be nullified when parent entity is
	 * updated. Setting this property has precedence over setting
	 * {@link #nullable()} property.
	 *
	 * @return a boolean (true by default)
	 */
	boolean nullableForUpdate() default true;

	/**
	 * Get whether this attribute is mandatory when parent entity is
	 * created/updated. If you need more granularity consider using
	 * {@link #mandatoryForCreate()} or {@link #mandatoryForUpdate()} instead.
	 *
	 * @return a boolean (false by default)
	 */
	boolean mandatory() default true;

	/**
	 * Get whether this attribute is mandatory when parent entity is created.
	 * Setting this property has precedence over setting {@link #mandatory()}
	 * property.
	 *
	 * @return a boolean (false by default)
	 */
	boolean mandatoryForCreate() default true;

	/**
	 * Get whether this attribute is mandatory when parent entity is updated.
	 * Setting this property has precedence over setting {@link #mandatory()}
	 * property.
	 *
	 * @return a boolean (false by default)
	 */
	boolean mandatoryForUpdate() default true;
}
