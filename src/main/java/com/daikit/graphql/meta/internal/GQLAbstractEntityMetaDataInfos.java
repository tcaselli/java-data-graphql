package com.daikit.graphql.meta.internal;

import java.util.ArrayList;
import java.util.List;

import com.daikit.graphql.meta.entity.GQLEntityMetaData;

/**
 * Meta data computed informations for {@link GQLEntityMetaData}
 *
 * @author Thibaut Caselli
 */
public abstract class GQLAbstractEntityMetaDataInfos {

	private final GQLEntityMetaData entity;
	private GQLAbstractEntityMetaDataInfos superEntity;
	// Super interfaces recursively
	private final List<GQLAbstractEntityMetaDataInfos> superInterfaces = new ArrayList<>();

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// CONSTRUCTORS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	/**
	 * Constructor
	 *
	 * @param entity
	 *            the GQLAbstractEntityMetaData
	 */
	public GQLAbstractEntityMetaDataInfos(final GQLEntityMetaData entity) {
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
	public GQLEntityMetaData getEntity() {
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

}
