package com.daikit.graphql.meta.builder;

import java.util.stream.Stream;

import com.daikit.graphql.config.GQLSchemaConfig;
import com.daikit.graphql.meta.GQLAttribute;
import com.daikit.graphql.meta.GQLAttributeRights;
import com.daikit.graphql.meta.attribute.GQLAbstractAttributeMetaData;
import com.daikit.graphql.meta.attribute.GQLAttributeRightsMetaData;

/**
 * Abstract super class for attribute (field or dynamic) meta data builders
 *
 * @author Thibaut Caselli
 */
public class GQLAbstractAttributeMetaDataBuilder extends GQLAbstractMetaDataBuilder {

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// CONSTRUCTORS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	/**
	 * Constructor
	 *
	 * @param schemaConfig
	 *            the {@link GQLSchemaConfig}
	 */
	public GQLAbstractAttributeMetaDataBuilder(final GQLSchemaConfig schemaConfig) {
		super(schemaConfig);
	}

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// PROTECTED METHODS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	protected void populateAttributeRights(GQLAbstractAttributeMetaData attributeMetaData, GQLAttribute annotation) {
		populateAttributeRights(attributeMetaData, annotation, true, true);
	}

	protected void populateAttributeRights(GQLAbstractAttributeMetaData attributeMetaData, GQLAttribute annotation,
			boolean defaultReadable, boolean defaultSaveable) {
		if (annotation == null || annotation.rights() == null || annotation.rights().length == 0) {
			final GQLAttributeRightsMetaData rights = new GQLAttributeRightsMetaData();
			rights.setSaveable(defaultSaveable);
			rights.setReadable(defaultReadable);
			rights.setNullable(true);
			rights.setMandatory(false);
			attributeMetaData.getRights().add(rights);
		} else {
			Stream.of(annotation.rights()).forEach(ann -> {
				if (ann.roles() == null || ann.roles().length == 0) {
					addRights(attributeMetaData, ann, null);
				} else {
					Stream.of(ann.roles()).forEach(role -> {
						addRights(attributeMetaData, ann, role);
					});
				}
			});
		}
	}

	private void addRights(GQLAbstractAttributeMetaData attributeMetaData, GQLAttributeRights ann, Object role) {
		final GQLAttributeRightsMetaData rights = new GQLAttributeRightsMetaData();
		rights.setRole(role);
		rights.setSaveable(!ann.exclude() && !ann.readOnly() && ann.save());
		rights.setReadable(!ann.exclude() && ann.readOnly() || ann.read());
		rights.setNullableForCreate(ann.nullableForCreate() && ann.nullable());
		rights.setNullableForUpdate(ann.nullableForUpdate() && ann.nullable());
		attributeMetaData.addRights(rights);
	}

}
