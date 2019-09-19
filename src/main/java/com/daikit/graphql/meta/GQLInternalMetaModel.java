package com.daikit.graphql.meta;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.daikit.graphql.config.GQLSchemaConfig;
import com.daikit.graphql.dynamicattribute.IGQLDynamicAttributeGetter;
import com.daikit.graphql.dynamicattribute.IGQLDynamicAttributeSetter;
import com.daikit.graphql.exception.GQLException;
import com.daikit.graphql.meta.attribute.GQLAbstractAttributeMetaData;
import com.daikit.graphql.meta.builder.GQLDynamicAttributeMetaDataBuilder;
import com.daikit.graphql.meta.builder.GQLMethodMetaDataBuilder;
import com.daikit.graphql.meta.custommethod.GQLAbstractMethodMetaData;
import com.daikit.graphql.meta.entity.GQLEntityMetaData;
import com.daikit.graphql.meta.entity.GQLEnumMetaData;
import com.daikit.graphql.meta.internal.GQLAbstractEntityMetaDataInfos;
import com.daikit.graphql.meta.internal.GQLConcreteEntityMetaDataInfos;
import com.daikit.graphql.meta.internal.GQLInterfaceEntityMetaDataInfos;
import com.daikit.graphql.utils.Message;

/**
 * Internal meta model initialized from input meta model with some pre
 * processing.
 *
 * @author Thibaut Caselli
 */
public class GQLInternalMetaModel {

	private final List<GQLEnumMetaData> enumMetaDatas = new ArrayList<>();
	private final List<GQLEntityMetaData> entityMetaDatas = new ArrayList<>();
	private final List<GQLAbstractMethodMetaData> methodMetaDatas = new ArrayList<>();
	private final List<GQLInterfaceEntityMetaDataInfos> interfaceMetaDatas = new ArrayList<>();
	private final List<GQLConcreteEntityMetaDataInfos> concreteMetaDatas = new ArrayList<>();

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// CONSTRUCTORS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	/**
	 * Post initialize this meta model with given {@link GQLSchemaConfig}. This
	 * pre processed meta model during schema building process.
	 *
	 * @param schemaConfig
	 *            the {@link GQLSchemaConfig}
	 * @param inputMetaModel
	 *            the {@link GQLMetaModel}
	 */
	public GQLInternalMetaModel(GQLSchemaConfig schemaConfig, final GQLMetaModel inputMetaModel) {

		this.enumMetaDatas.addAll(inputMetaModel.getEnumMetaDatas());
		this.entityMetaDatas.addAll(inputMetaModel.getEntityMetaDatas());

		final GQLDynamicAttributeMetaDataBuilder dynamicAttributeMetaDataBuilder = new GQLDynamicAttributeMetaDataBuilder(
				schemaConfig);
		final GQLMethodMetaDataBuilder methodMetaDataBuilder = new GQLMethodMetaDataBuilder(schemaConfig);
		final Collection<GQLAbstractAttributeMetaData> dynamicAttributeMetaDatas = inputMetaModel.getDynamicAttributes()
				.stream()
				.map(attribute -> dynamicAttributeMetaDataBuilder.build(enumMetaDatas, entityMetaDatas, attribute))
				.collect(Collectors.toList());
		final Collection<GQLAbstractMethodMetaData> methodMetaDatas = inputMetaModel.getMethods().stream()
				.map(customMethod -> methodMetaDataBuilder.build(enumMetaDatas, entityMetaDatas, customMethod))
				.collect(Collectors.toList());

		registerDynamicAttributes(entityMetaDatas, dynamicAttributeMetaDatas);

		final Comparator<GQLEnumMetaData> enumComparator = new Comparator<GQLEnumMetaData>() {
			@Override
			public int compare(final GQLEnumMetaData o1, final GQLEnumMetaData o2) {
				return o1.getName().compareTo(o2.getName());
			}
		};

		final Comparator<GQLAbstractEntityMetaDataInfos> infosComparator = new Comparator<GQLAbstractEntityMetaDataInfos>() {
			@Override
			public int compare(final GQLAbstractEntityMetaDataInfos o1, final GQLAbstractEntityMetaDataInfos o2) {
				return o1.getEntity().getName().compareTo(o2.getEntity().getName());
			}
		};

		// Sort & create inputs
		getEnums().addAll(enumMetaDatas);
		Collections.sort(getEnums(), enumComparator);

		// Create GQLEntityMetaDataInfos
		for (final GQLEntityMetaData entityMetaData : entityMetaDatas) {
			GQLAbstractEntityMetaDataInfos infos;
			if (entityMetaData.isConcrete()) {
				infos = new GQLConcreteEntityMetaDataInfos(entityMetaData);
				getAllConcretes().add((GQLConcreteEntityMetaDataInfos) infos);
			} else {
				infos = new GQLInterfaceEntityMetaDataInfos(entityMetaData);
				getAllInterfaces().add((GQLInterfaceEntityMetaDataInfos) infos);
			}
		}

		// Sort them
		Collections.sort(getAllConcretes(), infosComparator);
		Collections.sort(getAllInterfaces(), infosComparator);

		// Set super entities
		getAllEntities().forEach(infosToUpdate -> infosToUpdate.setSuperEntity(getAllEntities().stream().filter(
				infos -> infos.getEntity().getEntityClass().equals(infosToUpdate.getEntity().getSuperEntityClass()))
				.findFirst().orElse(null)));
		// Set super interfaces for all entities (embedded and not embedded)
		// (recursively)
		getAllEntities().forEach(infos -> buildAndSetSuperInterfaces(getAllInterfaces(), infos));
		// Set concrete sub entities for all interfaces
		getNonEmbeddedInterfaces().forEach(infos -> setConcreteSubEntities(getAllConcretes(), infos));
		// Set concrete sub entities for all embedded interfaces
		getEmbeddedInterfaces().forEach(infos -> setConcreteSubEntities(getAllConcretes(), infos));
		// Set custom methods
		getCustomMethods().addAll(methodMetaDatas);
	}

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// PRIVATE INITIALIZATION METHODS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	private void registerDynamicAttributes(final Collection<GQLEntityMetaData> entityMetaDatas,
			final Collection<GQLAbstractAttributeMetaData> dynamicAttributes) {
		for (final GQLAbstractAttributeMetaData dynamicAttribute : dynamicAttributes) {
			final Class<?> entityType = dynamicAttribute.isDynamicAttributeGetter()
					? dynamicAttribute.getDynamicAttributeGetter().getEntityType()
					: dynamicAttribute.getDynamicAttributeSetter().getEntityType();
			final Optional<GQLEntityMetaData> entityMetaData = entityMetaDatas.stream()
					.filter(metaData -> metaData.getEntityClass().isAssignableFrom(entityType)).findFirst();
			if (!entityMetaData.isPresent()) {
				throw new GQLException(
						Message.format("No entity meta data registered for dynamic attribute [{}] entity class [{}]",
								dynamicAttribute.getName(), entityType.getSimpleName()));
			}
			entityMetaData.get().addAttribute(dynamicAttribute);
		}
	}

	private void buildAndSetSuperInterfaces(final Collection<GQLInterfaceEntityMetaDataInfos> allInterfaces,
			final GQLAbstractEntityMetaDataInfos infos) {
		getSuperInterfaceInfos(allInterfaces, infos)
				.ifPresent(superInterface -> setSuperInterfaceInfos(allInterfaces, superInterface, infos));
	}

	private Optional<GQLInterfaceEntityMetaDataInfos> getSuperInterfaceInfos(
			final Collection<GQLInterfaceEntityMetaDataInfos> allInterfaces,
			final GQLAbstractEntityMetaDataInfos infos) {
		return allInterfaces.stream().filter(potential -> potential.equals(infos.getSuperEntity())).findFirst();
	}

	private void setSuperInterfaceInfos(final Collection<GQLInterfaceEntityMetaDataInfos> allInterfaces,
			final GQLAbstractEntityMetaDataInfos superInterfaceInfos, final GQLAbstractEntityMetaDataInfos infos) {
		infos.getSuperInterfaces().add(superInterfaceInfos);
		getSuperInterfaceInfos(allInterfaces, superInterfaceInfos).ifPresent(
				superSuperInterfaceInfos -> setSuperInterfaceInfos(allInterfaces, superSuperInterfaceInfos, infos));
	}

	private void setConcreteSubEntities(final Collection<GQLConcreteEntityMetaDataInfos> concretes,
			final GQLInterfaceEntityMetaDataInfos infos) {
		infos.getConcreteSubEntities().addAll(concretes.stream()
				.filter(concrete -> concrete.getSuperInterfaces().contains(infos)).collect(Collectors.toList()));
	}

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// PUBLIC METHODS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	/**
	 * Get entity class by its name
	 *
	 * @param entityName
	 *            the entity class name
	 * @param <ENTITY_CLASS>
	 *            the entity class
	 * @return the entity class
	 */
	@SuppressWarnings("unchecked")
	public <ENTITY_CLASS> Class<? extends ENTITY_CLASS> getEntityClassByEntityName(final String entityName) {
		final Optional<GQLAbstractEntityMetaDataInfos> entityMetaDataOptional = getAllEntities().stream()
				.filter(metaDataInfos -> metaDataInfos.getEntity().getName().equals(entityName)).findFirst();
		if (!entityMetaDataOptional.isPresent()) {
			throw new GQLException(Message.format("Not entity exists for name [{}]", entityName));
		}
		return (Class<? extends ENTITY_CLASS>) entityMetaDataOptional.get().getEntity().getEntityClass();
	}

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// GETTERS / SETTERS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	/**
	 * Get all registered dynamic attribute setters
	 * {@link IGQLDynamicAttributeSetter} in meta model
	 *
	 * @return a {@link List} of {@link IGQLDynamicAttributeSetter}
	 */
	public List<IGQLDynamicAttributeSetter<?, ?>> getDynamicAttributeSetters() {
		return concreteMetaDatas.stream()
				.flatMap(entityMetaData -> entityMetaData.getEntity().getAttributes().stream()
						.filter(attribute -> attribute.isDynamicAttributeSetter())
						.map(attribute -> attribute.getDynamicAttributeSetter()))
				.collect(Collectors.toList());
	}

	/**
	 * Get all registered dynamic attribute getters
	 * {@link IGQLDynamicAttributeGetter} in meta model
	 *
	 * @return a {@link List} of {@link IGQLDynamicAttributeGetter}
	 */
	public List<IGQLDynamicAttributeGetter<?, ?>> getDynamicAttributeGetters() {
		return concreteMetaDatas.stream()
				.flatMap(entityMetaData -> entityMetaData.getEntity().getAttributes().stream()
						.filter(attribute -> attribute.isDynamicAttributeGetter())
						.map(attribute -> attribute.getDynamicAttributeGetter()))
				.collect(Collectors.toList());
	}

	/**
	 * @return the enums
	 */
	public List<GQLEnumMetaData> getEnums() {
		return enumMetaDatas;
	}

	/**
	 * @return the interfaces
	 */
	public List<GQLInterfaceEntityMetaDataInfos> getNonEmbeddedInterfaces() {
		return interfaceMetaDatas.stream().filter(infos -> !infos.getEntity().isEmbedded())
				.collect(Collectors.toList());
	}

	/**
	 * @return the concretes
	 */
	public List<GQLConcreteEntityMetaDataInfos> getNonEmbeddedConcretes() {
		return concreteMetaDatas.stream().filter(infos -> !infos.getEntity().isEmbedded()).collect(Collectors.toList());
	}

	/**
	 * @return the embeddedInterfaces
	 */
	public List<GQLInterfaceEntityMetaDataInfos> getEmbeddedInterfaces() {
		return interfaceMetaDatas.stream().filter(infos -> infos.getEntity().isEmbedded()).collect(Collectors.toList());
	}

	/**
	 * @return the embeddedConcretes
	 */
	public List<GQLConcreteEntityMetaDataInfos> getEmbeddedConcretes() {
		return concreteMetaDatas.stream().filter(infos -> infos.getEntity().isEmbedded()).collect(Collectors.toList());
	}

	/**
	 * @return the allEntities
	 */
	public List<GQLAbstractEntityMetaDataInfos> getAllEntities() {
		return Stream.concat(interfaceMetaDatas.stream(), concreteMetaDatas.stream()).collect(Collectors.toList());
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
		return interfaceMetaDatas;
	}

	/**
	 * @return the concretes
	 */
	public List<GQLConcreteEntityMetaDataInfos> getAllConcretes() {
		return concreteMetaDatas;
	}

	/**
	 * @return the customMethods
	 */
	public List<GQLAbstractMethodMetaData> getCustomMethods() {
		return methodMetaDatas;
	}

}
