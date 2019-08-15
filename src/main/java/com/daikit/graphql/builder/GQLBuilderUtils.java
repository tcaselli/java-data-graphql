package com.daikit.graphql.builder;

import java.util.Collection;
import java.util.List;

import graphql.schema.GraphQLEnumType;
import graphql.schema.GraphQLFieldDefinition;
import graphql.schema.GraphQLInputObjectField;
import graphql.schema.GraphQLList;
import graphql.schema.GraphQLScalarType;
import graphql.schema.GraphQLType;
import graphql.schema.GraphQLTypeReference;

/**
 * Utility class for GraphQL builders
 *
 * @author Thibaut Caselli
 */
public class GQLBuilderUtils {

	/**
	 * Add or replace {@link GraphQLInputObjectField} objects from second list
	 * within first list
	 *
	 * @param objectFields
	 *            the list of objects to be replaced
	 * @param objectFieldsToAdd
	 *            the list of objects to be added in first list or that will
	 *            replace elements in first list
	 */
	public static final void addOrReplaceInputObjectFields(final List<GraphQLInputObjectField> objectFields,
			final List<GraphQLInputObjectField> objectFieldsToAdd) {
		loop : for (final GraphQLInputObjectField objectField : objectFieldsToAdd) {
			for (int i = 0; i < objectFields.size(); i++) {
				if (objectFields.get(i).getName().equals(objectField.getName())) {
					objectFields.set(i, objectField);
					continue loop;
				}
			}
			objectFields.add(objectField);
		}
	}

	/**
	 * Add or replace {@link GraphQLFieldDefinition} objects from second list
	 * within first list
	 *
	 * @param fieldDefinitions
	 *            the {@link List} of objects to be replaced
	 * @param fieldDefinitionsToAdd
	 *            the {@link Collection} of objects to be added in first list or
	 *            that will replace elements in first list
	 */
	public static final void addOrReplaceFieldDefinitions(final List<GraphQLFieldDefinition> fieldDefinitions,
			final Collection<GraphQLFieldDefinition> fieldDefinitionsToAdd) {
		loop : for (final GraphQLFieldDefinition fieldDefinition : fieldDefinitionsToAdd) {
			for (int i = 0; i < fieldDefinitions.size(); i++) {
				if (fieldDefinitions.get(i).getName().equals(fieldDefinition.getName())) {
					fieldDefinitions.set(i, fieldDefinition);
					continue loop;
				}
			}
			fieldDefinitions.add(fieldDefinition);
		}
	}

	/**
	 * Get a {@link GraphQLType} to String
	 *
	 * @param type
	 *            the {@link GraphQLType}
	 * @return the string representation of {@link GraphQLType}
	 */
	public static final String typeToString(final GraphQLType type) {
		final StringBuilder sb = new StringBuilder();
		if (type instanceof GraphQLEnumType) {
			sb.append("Enum<").append(type.getName()).append(">");
		} else if (type instanceof GraphQLList) {
			sb.append("List<").append(typeToString(((GraphQLList) type).getWrappedType())).append(">");
		} else if (type instanceof GraphQLTypeReference) {
			sb.append("Reference<").append(type.getName()).append(">");
		} else if (type instanceof GraphQLScalarType) {
			sb.append("Scalar<").append(type.getName()).append(">");
		} else {
			sb.append(type);
		}
		return sb.toString();
	}

}
