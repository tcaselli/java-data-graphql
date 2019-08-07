package com.daikit.graphql.builder.types;

import com.daikit.graphql.builder.GQLAbstractSchemaSubBuilder;
import com.daikit.graphql.builder.GQLSchemaBuilderCache;
import com.daikit.graphql.meta.GQLMetaDataModel;

/**
 * Entity and Interface reference map builder
 *
 * @author tcaselli
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
	 * @param metaDataModel
	 *            the {@link GQLMetaDataModel}
	 */
	public void buildTypeReferences(final GQLMetaDataModel metaDataModel) {
		logger.debug("START building reference types...");
		metaDataModel.getAllEntities().forEach(infos -> getCache().getTypeReferences()
				.put(infos.getEntity().getEntityClass(), infos.getEntity().getName()));
		metaDataModel.getEnums()
				.forEach(enumMeta -> getCache().getTypeReferences().put(enumMeta.getEnumClass(), enumMeta.getName()));
		logger.debug("END building reference types");
	}

}
