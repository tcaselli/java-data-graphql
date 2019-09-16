package com.daikit.graphql.builder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.daikit.graphql.config.GQLSchemaConfig;

/**
 * Abstract super class for all schema fragment builders
 *
 * @author Thibaut Caselli
 */
public class GQLAbstractSchemaSubBuilder {

	protected final Logger logger = LoggerFactory.getLogger(getClass());

	private final GQLSchemaBuilderCache cache;

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// CONSTRUCTORS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	/**
	 * Constructor
	 *
	 * @param cache
	 *            the {@link GQLSchemaBuilderCache}
	 */
	public GQLAbstractSchemaSubBuilder(final GQLSchemaBuilderCache cache) {
		this.cache = cache;
	}

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// PROTECTED METHODS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	/**
	 * @return the {@link GQLSchemaBuilderCache}
	 */
	protected GQLSchemaBuilderCache getCache() {
		return cache;
	}

	/**
	 * @return the {@link GQLSchemaConfig}
	 */
	protected GQLSchemaConfig getConfig() {
		return cache.getConfig();
	}

}
