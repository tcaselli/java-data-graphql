package com.daikit.graphql.meta;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Annotation for controller class holding methods that should be provided in
 * GQL Schema.
 *
 * @author tcaselli
 * @version $Revision$ Last modifier: $Author$ Last commit: $Date$
 */
@Target({ElementType.TYPE})
@Retention(RUNTIME)
public @interface GQLController {

}
