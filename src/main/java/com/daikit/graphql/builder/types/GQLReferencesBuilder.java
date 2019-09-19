package com.daikit.graphql.builder.types;

import com.daikit.graphql.builder.GQLAbstractSchemaSubBuilder;
import com.daikit.graphql.builder.GQLSchemaBuilderCache;
import com.daikit.graphql.meta.GQLInternalMetaModel;

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
	 *            the {@link GQLInternalMetaModel}
	 */
	public void buildTypeReferences(final GQLInternalMetaModel metaModel) {
		logger.debug("START building reference types...");
		metaModel.getAllEntities().forEach(infos -> getCache().getTypeReferences()
				.put(infos.getEntity().getEntityClass(), infos.getEntity().getName()));
		metaModel.getEnums()
				.forEach(enumMeta -> getCache().getTypeReferences().put(enumMeta.getEnumClass(), enumMeta.getName()));
		logger.debug("END building reference types");
	}

}
