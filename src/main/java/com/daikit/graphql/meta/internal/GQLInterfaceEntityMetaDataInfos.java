package com.daikit.graphql.meta.internal;

import java.util.ArrayList;
import java.util.List;

import com.daikit.graphql.meta.entity.GQLAbstractEntityMetaData;
import com.daikit.graphql.meta.entity.GQLEntityMetaData;

/**
 * Meta data computed informations for abstract (non embedded)
 * {@link GQLEntityMetaData}
 *
 * @author tcaselli
 */
public class GQLInterfaceEntityMetaDataInfos extends GQLAbstractEntityMetaDataInfos {

	// Concrete sub entities from data model
	private final List<GQLConcreteEntityMetaDataInfos> concreteSubEntities = new ArrayList<>();

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// CONSTRUCTORS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	/**
	 * Constructor
	 *
	 * @param entity
	 *            the {@link GQLEntityMetaData}
	 */
	public GQLInterfaceEntityMetaDataInfos(final GQLAbstractEntityMetaData entity) {
		super(entity);
	}

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// METHODS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("(INFOS-INTERFACE[").append(getEntity() == null ? "" : getEntity().toString()).append("])");
		return sb.toString();
	}

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// GETTERS / SETTERS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	/**
	 * @return the concreteSubEntities
	 */
	public List<GQLConcreteEntityMetaDataInfos> getConcreteSubEntities() {
		return concreteSubEntities;
	}

}
