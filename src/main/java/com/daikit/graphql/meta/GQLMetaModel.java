package com.daikit.graphql.meta;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.daikit.graphql.custommethod.GQLAbstractCustomMethod;
import com.daikit.graphql.custommethod.IGQLAbstractCustomMethod;
import com.daikit.graphql.dynamicattribute.IGQLAbstractDynamicAttribute;
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
 * Class holding meta data model
 *
 * @author Thibaut Caselli
 */
public class GQLMetaModel {

	private final List<GQLEnumMetaData> enums = new ArrayList<>();
	private final List<GQLInterfaceEntityMetaDataInfos> interfaces = new ArrayList<>();
	private final List<GQLConcreteEntityMetaDataInfos> concretes = new ArrayList<>();
	private final List<GQLAbstractMethodMetaData> customMethods = new ArrayList<>();

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// CONSTRUCTORS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	/**
	 * Constructor.
	 *
	 * @param enumMetaDatas
	 *            the collection of all registered {@link GQLEnumMetaData}
	 * @param entityMetaDatas
	 *            the collection of all registered {@link GQLEntityMetaData}
	 * @param dynamicAttributes
	 *            the collection of {@link IGQLAbstractDynamicAttribute} to be
	 *            automatically registered (meta data will be created
	 *            automatically)
	 * @param customMethods
	 *            the collection of {@link GQLAbstractCustomMethod} to be
	 *            automatically registered (meta data will be created
	 *            automatically)
	 */
	public GQLMetaModel(final Collection<GQLEnumMetaData> enumMetaDatas,
			final Collection<GQLEntityMetaData> entityMetaDatas,
			final Collection<IGQLAbstractDynamicAttribute<?>> dynamicAttributes,
			final Collection<IGQLAbstractCustomMethod<?>> customMethods) {
		final GQLDynamicAttributeMetaDataBuilder dynamicAttributeMetaDataBuilder = new GQLDynamicAttributeMetaDataBuilder();
		final GQLMethodMetaDataBuilder methodMetaDataBuilder = new GQLMethodMetaDataBuilder();
		final Collection<GQLAbstractAttributeMetaData> dynamicAttributeMetaDatas = dynamicAttributes.stream()
				.map(attribute -> dynamicAttributeMetaDataBuilder.build(enumMetaDatas, entityMetaDatas, attribute))
				.collect(Collectors.toList());
		final Collection<GQLAbstractMethodMetaData> methodMetaDatas = customMethods.stream()
				.map(customMethod -> methodMetaDataBuilder.build(enumMetaDatas, entityMetaDatas, customMethod))
				.collect(Collectors.toList());

		registerDynamicAttributes(entityMetaDatas, dynamicAttributeMetaDatas);
		initialize(enumMetaDatas, entityMetaDatas, methodMetaDatas);
	}

	/**
	 * Constructor.
	 *
	 * @param enumMetaDatas
	 *            the collection of all registered {@link GQLEnumMetaData}
	 * @param entityMetaDatas
	 *            the collection of all registered {@link GQLEntityMetaData}
	 * @param methodMetaDatas
	 *            the collection of all registered
	 *            {@link GQLAbstractMethodMetaData}
	 */
	public GQLMetaModel(final Collection<GQLEnumMetaData> enumMetaDatas,
			final Collection<GQLEntityMetaData> entityMetaDatas,
			final Collection<GQLAbstractMethodMetaData> methodMetaDatas) {
		initialize(enumMetaDatas, entityMetaDatas, methodMetaDatas);
	}

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// PRIVATE METHODS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	private void registerDynamicAttributes(Collection<GQLEntityMetaData> entityMetaDatas,
			Collection<GQLAbstractAttributeMetaData> dynamicAttributes) {
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

	private void initialize(final Collection<GQLEnumMetaData> enumMetaDatas,
			final Collection<GQLEntityMetaData> entityMetaDatas,
			final Collection<GQLAbstractMethodMetaData> methodMetaDatas) {
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

	private void buildAndSetSuperInterfaces(final List<GQLInterfaceEntityMetaDataInfos> allInterfaces,
			final GQLAbstractEntityMetaDataInfos infos) {
		getSuperInterfaceInfos(allInterfaces, infos)
				.ifPresent(superInterface -> setSuperInterfaceInfos(allInterfaces, superInterface, infos));
	}

	private Optional<GQLInterfaceEntityMetaDataInfos> getSuperInterfaceInfos(
			final List<GQLInterfaceEntityMetaDataInfos> allInterfaces, final GQLAbstractEntityMetaDataInfos infos) {
		return allInterfaces.stream().filter(potential -> potential.equals(infos.getSuperEntity())).findFirst();
	}

	private void setSuperInterfaceInfos(final List<GQLInterfaceEntityMetaDataInfos> allInterfaces,
			final GQLAbstractEntityMetaDataInfos superInterfaceInfos, final GQLAbstractEntityMetaDataInfos infos) {
		infos.getSuperInterfaces().add(superInterfaceInfos);
		getSuperInterfaceInfos(allInterfaces, superInterfaceInfos).ifPresent(
				superSuperInterfaceInfos -> setSuperInterfaceInfos(allInterfaces, superSuperInterfaceInfos, infos));
	}

	private void setConcreteSubEntities(final List<GQLConcreteEntityMetaDataInfos> concretes,
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
		return concretes.stream()
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
		return concretes.stream()
				.flatMap(entityMetaData -> entityMetaData.getEntity().getAttributes().stream()
						.filter(attribute -> attribute.isDynamicAttributeGetter())
						.map(attribute -> attribute.getDynamicAttributeGetter()))
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
