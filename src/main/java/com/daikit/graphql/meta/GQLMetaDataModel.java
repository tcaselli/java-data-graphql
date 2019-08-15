package com.daikit.graphql.meta;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.daikit.graphql.dynamicattribute.IGQLDynamicAttributeSetter;
import com.daikit.graphql.meta.custommethod.GQLAbstractMethodMetaData;
import com.daikit.graphql.meta.entity.GQLEnumMetaData;
import com.daikit.graphql.meta.internal.GQLAbstractEntityMetaDataInfos;
import com.daikit.graphql.meta.internal.GQLConcreteEntityMetaDataInfos;
import com.daikit.graphql.meta.internal.GQLInterfaceEntityMetaDataInfos;

/**
 * Class holding meta data model
 *
 * @author Thibaut Caselli
 */
public class GQLMetaDataModel {

	private final List<GQLEnumMetaData> enums = new ArrayList<>();
	private final List<GQLInterfaceEntityMetaDataInfos> interfaces = new ArrayList<>();
	private final List<GQLConcreteEntityMetaDataInfos> concretes = new ArrayList<>();
	private final List<GQLAbstractMethodMetaData> customMethods = new ArrayList<>();

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// METHODS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	/**
	 * Get all registered dynamic attribute setters
	 * {@link IGQLDynamicAttributeSetter} in meta model
	 *
	 * @return a {@link List} of {@link IGQLDynamicAttributeSetter}
	 */
	public List<IGQLDynamicAttributeSetter<?, ?>> getDynamicAttributeSetters() {
		return concretes.stream()
				.flatMap(entityMetaData -> entityMetaData.getEntity().getAttributes().stream()
						.filter(attribute -> attribute.isDynamicAttributeSetter())
						.map(attribute -> attribute.getDynamicAttributeSetter()))
				.collect(Collectors.toList());
	}

	/**
	 * @return the enums
	 */
	public List<GQLEnumMetaData> getEnums() {
		return enums;
	}

	/**
	 * @return the interfaces
	 */
	public List<GQLInterfaceEntityMetaDataInfos> getNonEmbeddedInterfaces() {
		return interfaces.stream().filter(infos -> !infos.getEntity().isEmbedded()).collect(Collectors.toList());
	}

	/**
	 * @return the concretes
	 */
	public List<GQLConcreteEntityMetaDataInfos> getNonEmbeddedConcretes() {
		return concretes.stream().filter(infos -> !infos.getEntity().isEmbedded()).collect(Collectors.toList());
	}

	/**
	 * @return the embeddedInterfaces
	 */
	public List<GQLInterfaceEntityMetaDataInfos> getEmbeddedInterfaces() {
		return interfaces.stream().filter(infos -> infos.getEntity().isEmbedded()).collect(Collectors.toList());
	}

	/**
	 * @return the embeddedConcretes
	 */
	public List<GQLConcreteEntityMetaDataInfos> getEmbeddedConcretes() {
		return concretes.stream().filter(infos -> infos.getEntity().isEmbedded()).collect(Collectors.toList());
	}

	/**
	 * @return the allEntities
	 */
	public List<GQLAbstractEntityMetaDataInfos> getAllEntities() {
		return Stream.concat(interfaces.stream(), concretes.stream()).collect(Collectors.toList());
	}

	/**
	 * @return the allEmbeddedEntities
	 */
	public List<GQLAbstractEntityMetaDataInfos> getAllEmbeddedEntities() {
		return Stream.concat(getEmbeddedInterfaces().stream(), getEmbeddedConcretes().stream())
				.collect(Collectors.toList());
	}

	/**
	 * @return the allNonEmbeddedEntities
	 */
	public List<GQLAbstractEntityMetaDataInfos> getAllNonEmbeddedEntities() {
		return Stream.concat(getNonEmbeddedInterfaces().stream(), getNonEmbeddedConcretes().stream())
				.collect(Collectors.toList());
	}

	/**
	 * @return the interfaces
	 */
	public List<GQLInterfaceEntityMetaDataInfos> getAllInterfaces() {
		return interfaces;
	}

	/**
	 * @return the concretes
	 */
	public List<GQLConcreteEntityMetaDataInfos> getAllConcretes() {
		return concretes;
	}

	/**
	 * @return the customMethods
	 */
	public List<GQLAbstractMethodMetaData> getCustomMethods() {
		return customMethods;
	}

}
