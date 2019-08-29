package com.daikit.graphql.builder.types;

import com.daikit.graphql.builder.GQLAbstractSchemaSubBuilder;
import com.daikit.graphql.builder.GQLSchemaBuilderCache;
import com.daikit.graphql.meta.GQLMetaModel;

/**
 * Entity and Interface reference map builder
 *
 * @author Thibaut Caselli
 */
public class GQLReferencesBuilder extends GQLAbstractSchemaSubBuilder {

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// CONSTRUCTORS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	/**
	 * Constructor
	 *
	 * @param cache
	 *            the {@link GQLSchemaBuilderCache}
	 */
	public GQLReferencesBuilder(final GQLSchemaBuilderCache cache) {
		super(cache);
	}

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// METHODS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	/**
	 * Build type references map
	 *
	 * @param metaModel
	 *            the {@link GQLMetaModel}
	 */
	public void buildTypeReferences(final GQLMetaModel metaModel) {
		logger.debug("START building reference types...");
		metaModel.getAllEntities().forEach(infos -> getCache().getTypeReferences()
				.put(infos.getEntity().getEntityClass(), infos.getEntity().getName()));
		metaModel.getEnums()
				.forEach(enumMeta -> getCache().getTypeReferences().put(enumMeta.getEnumClass(), enumMeta.getName()));
		logger.debug("END building reference types");
	}

}
