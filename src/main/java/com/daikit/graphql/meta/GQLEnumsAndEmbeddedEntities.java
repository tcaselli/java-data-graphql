package com.daikit.graphql.meta;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * {@link GQLEnumsAndEmbeddedEntitiesCollector} output
 *
 * @author Thibaut Caselli
 */
public class GQLEnumsAndEmbeddedEntities {

	private final Set<Class<?>> entities = new LinkedHashSet<>();
	private final Set<Class<? extends Enum<?>>> enums = new LinkedHashSet<>();

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// GETTERS / SETTERS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	/**
	 * @return the entities
	 */
	public Set<Class<?>> getEntities() {
		return entities;
	}

	/**
	 * @return the enums
	 */
	public Set<Class<? extends Enum<?>>> getEnums() {
		return enums;
	}

}
