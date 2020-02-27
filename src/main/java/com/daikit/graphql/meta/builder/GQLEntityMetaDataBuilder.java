package com.daikit.graphql.meta.builder;

import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;

import com.daikit.graphql.config.GQLSchemaConfig;
import com.daikit.graphql.meta.GQLEntity;
import com.daikit.graphql.meta.GQLEntityRights;
import com.daikit.graphql.meta.entity.GQLEntityMetaData;
import com.daikit.graphql.meta.entity.GQLEntityRightsMetaData;

/**
 * Build for entity meta data from its class
 *
 * @author Thibaut Caselli
 */
public class GQLEntityMetaDataBuilder extends GQLAbstractMetaDataBuilder {

	private final Collection<Class<?>> allEntityClasses;

	private final GQLAttributeMetaDataBuilder attributeBuilder;

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// CONSTRUCTORS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	/**
	 * Constructor
	 *
	 * @param schemaConfig
	 *            the {@link GQLSchemaConfig}
	 * @param allEntityClasses
	 *            a collection of all entity classes
	 * @param allDataClasses
	 *            a collection of all available data classes
	 * @param allEnumClasses
	 *            a collection of all available enum classes
	 */
	public GQLEntityMetaDataBuilder(final GQLSchemaConfig schemaConfig, final Collection<Class<?>> allEntityClasses,
			final Collection<Class<?>> allDataClasses, final Collection<Class<? extends Enum<?>>> allEnumClasses) {
		super(schemaConfig);
		this.allEntityClasses = allEntityClasses;
		attributeBuilder = new GQLAttributeMetaDataBuilder(schemaConfig, allEntityClasses, allDataClasses,
				allEnumClasses);
	}

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// PUBLIC METHODS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	/**
	 * Build GraphQL entity from given entity class
	 *
	 *
	 * @param entityClass
	 *            the entity class to build meta data from
	 * @param embedded
	 *            whether this entity is embedded
	 * @return a {@link GQLEntityMetaData}
	 */
	public GQLEntityMetaData build(final Class<?> entityClass, final boolean embedded) {
		final GQLEntity annotation = entityClass.getAnnotation(GQLEntity.class);
		GQLEntityMetaData gqlEntity = null;
		if (annotation == null || !annotation.exclude()) {
			gqlEntity = new GQLEntityMetaData();
			gqlEntity.setConcrete(!Modifier.isAbstract(entityClass.getModifiers()));
			gqlEntity.setEntityClass(entityClass);
			gqlEntity.setEmbedded(embedded);

			gqlEntity.setName(annotation == null || StringUtils.isEmpty(annotation.name())
					? entityClass.getSimpleName()
					: annotation.name());

			gqlEntity.setDescription(annotation == null ? null : annotation.description());

			setEntityRights(gqlEntity, annotation);

			if (allEntityClasses.contains(entityClass.getSuperclass())) {
				gqlEntity.setSuperEntityClass(entityClass.getSuperclass());
			}

			gqlEntity.getAttributes()
					.addAll(FieldUtils.getAllFieldsList(entityClass).stream()
							.map(field -> attributeBuilder.buildAttribute(entityClass, field)).filter(Objects::nonNull)
							.collect(Collectors.toList()));
		}
		return gqlEntity;
	}

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// PRIVATE UTILS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	private void setEntityRights(GQLEntityMetaData entityMetaData, GQLEntity annotation) {
		if (annotation == null || annotation.rights() == null || annotation.rights().length == 0) {
			final GQLEntityRightsMetaData rights = new GQLEntityRightsMetaData();
			rights.setSaveable(true);
			rights.setReadable(true);
			rights.setDeletable(true);
			entityMetaData.getRights().add(rights);
		} else {
			Stream.of(annotation.rights()).forEach(ann -> {
				if (ann.roles() == null || ann.roles().length == 0) {
					addRights(entityMetaData, ann, null);
				} else {
					Stream.of(ann.roles()).forEach(role -> {
						addRights(entityMetaData, ann, role);
					});
				}
			});
		}
	}

	private void addRights(GQLEntityMetaData entityMetaData, GQLEntityRights ann, Object role) {
		final GQLEntityRightsMetaData rights = new GQLEntityRightsMetaData();
		rights.setRole(role);
		rights.setSaveable(!ann.exclude() && !ann.readOnly() && ann.save());
		rights.setReadable(!ann.exclude() && ann.readOnly() || ann.read());
		rights.setDeletable(!ann.exclude() && !ann.readOnly() && ann.delete());
		entityMetaData.addRights(rights);
	}
}
