package com.daikit.graphql.meta;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.daikit.graphql.meta.custommethod.GQLAbstractMethodMetaData;
import com.daikit.graphql.meta.entity.GQLEntityMetaData;
import com.daikit.graphql.meta.entity.GQLEnumMetaData;
import com.daikit.graphql.meta.internal.GQLAbstractEntityMetaDataInfos;
import com.daikit.graphql.meta.internal.GQLConcreteEntityMetaDataInfos;
import com.daikit.graphql.meta.internal.GQLInterfaceEntityMetaDataInfos;

/**
 * Builder for {@link GQLMetaDataModel}
 *
 * @author Thibaut Caselli
 */
public class GQLMetaDataModelBuilder {

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// METHODS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	/**
	 * Build {@link GQLMetaDataModel} and return it
	 *
	 * @param enumMetaDatas
	 *            the collection of all registered {@link GQLEnumMetaData}
	 * @param entityMetaDatas
	 *            the collection of all registered {@link GQLEntityMetaData}
	 * @param methodMetaDatas
	 *            the collection of all registered
	 *            {@link GQLAbstractMethodMetaData}
	 * @return the created {@link GQLMetaDataModel}
	 */
	public GQLMetaDataModel build(final Collection<GQLEnumMetaData> enumMetaDatas,
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

		final GQLMetaDataModel metaDataModel = new GQLMetaDataModel();

		// Sort & create inputs
		metaDataModel.getEnums().addAll(enumMetaDatas);
		Collections.sort(metaDataModel.getEnums(), enumComparator);

		// Create GQLEntityMetaDataInfos
		for (final GQLEntityMetaData entityMetaData : entityMetaDatas) {
			GQLAbstractEntityMetaDataInfos infos;
			if (entityMetaData.isConcrete()) {
				infos = new GQLConcreteEntityMetaDataInfos(entityMetaData);
				metaDataModel.getAllConcretes().add((GQLConcreteEntityMetaDataInfos) infos);
			} else {
				infos = new GQLInterfaceEntityMetaDataInfos(entityMetaData);
				metaDataModel.getAllInterfaces().add((GQLInterfaceEntityMetaDataInfos) infos);
			}
		}

		// Sort them
		Collections.sort(metaDataModel.getAllConcretes(), infosComparator);
		Collections.sort(metaDataModel.getAllInterfaces(), infosComparator);

		// Set super entities
		metaDataModel.getAllEntities()
				.forEach(
						infosToUpdate -> infosToUpdate.setSuperEntity(metaDataModel.getAllEntities().stream()
								.filter(infos -> infos.getEntity().getEntityClass()
										.equals(infosToUpdate.getEntity().getSuperEntityClass()))
								.findFirst().orElse(null)));

		// Set super interfaces for all entities (embedded and not embedded)
		// (recursively)
		metaDataModel.getAllEntities()
				.forEach(infos -> buildAndSetSuperInterfaces(metaDataModel.getAllInterfaces(), infos));

		// Set concrete sub entities for all interfaces
		metaDataModel.getNonEmbeddedInterfaces()
				.forEach(infos -> setConcreteSubEntities(metaDataModel.getAllConcretes(), infos));

		// Set concrete sub entities for all embedded interfaces
		metaDataModel.getEmbeddedInterfaces()
				.forEach(infos -> setConcreteSubEntities(metaDataModel.getAllConcretes(), infos));

		// Set custom methods
		metaDataModel.getCustomMethods().addAll(methodMetaDatas);

		return metaDataModel;
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

}
