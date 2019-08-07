package com.daikit.graphql.meta.internal;

import java.util.ArrayList;
import java.util.List;

import com.daikit.graphql.meta.data.entity.GQLAbstractEntityMetaData;
import com.daikit.graphql.meta.data.entity.GQLEntityMetaData;

/**
 * Meta data computed informations for {@link GQLEntityMetaData}
 *
 * @author tcaselli
 */
public abstract class GQLAbstractEntityMetaDataInfos {

	private final GQLAbstractEntityMetaData entity;
	private GQLAbstractEntityMetaDataInfos superEntity;
	// Super interfaces recursively
	private final List<GQLAbstractEntityMetaDataInfos> superInterfaces = new ArrayList<>();

	private boolean embedded;

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// CONSTRUCTORS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	/**
	 * Constructor
	 *
	 * @param entity
	 *            the GQLAbstractEntityMetaData
	 */
	public GQLAbstractEntityMetaDataInfos(final GQLAbstractEntityMetaData entity) {
		this.entity = entity;
	}

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// METHODS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	/**
	 * @return whether this instance is for concrete meta data
	 */
	public boolean isConcrete() {
		return this instanceof GQLConcreteEntityMetaDataInfos;
	}

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// GETTERS / SETTERS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	/**
	 * @return the entity
	 */
	public GQLAbstractEntityMetaData getEntity() {
		return entity;
	}

	/**
	 * @return the superEntity
	 */
	public GQLAbstractEntityMetaDataInfos getSuperEntity() {
		return superEntity;
	}

	/**
	 * @param superEntity
	 *            the superEntity to set
	 */
	public void setSuperEntity(final GQLAbstractEntityMetaDataInfos superEntity) {
		this.superEntity = superEntity;
	}

	/**
	 * @return the superInterfaces
	 */
	public List<GQLAbstractEntityMetaDataInfos> getSuperInterfaces() {
		return superInterfaces;
	}

	/**
	 * @return the embedded
	 */
	public boolean isEmbedded() {
		return embedded;
	}

	/**
	 * @param embedded
	 *            the embedded to set
	 */
	public void setEmbedded(final boolean embedded) {
		this.embedded = embedded;
	}

}
