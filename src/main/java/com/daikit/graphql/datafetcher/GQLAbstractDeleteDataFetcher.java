package com.daikit.graphql.datafetcher;

import com.daikit.graphql.builder.GQLSchemaBuilder;
import com.daikit.graphql.data.output.GQLDeleteResult;

import graphql.language.Field;
import graphql.schema.DataFetchingEnvironment;

/**
 * Abstract super class that may be overridden to provide "delete entity" data
 * fetcher by ID to the schema building. This class is typically to be extended
 * and used in {@link GQLSchemaBuilder} for buildSchema delete method data
 * fetcher argument
 *
 * @author Thibaut Caselli
 *
 */
public abstract class GQLAbstractDeleteDataFetcher extends GQLAbstractDataFetcher<GQLDeleteResult> {

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// ABSTRACT METHODS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	protected abstract void delete(Class<?> entityClass, String id);

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// METHODS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	@Override
	public GQLDeleteResult get(final DataFetchingEnvironment environment) {
		final Field queryField = environment.getField();
		final String entityName = getEntityName(getConfig().getMutationDeletePrefix(), queryField.getName());
		final String id = (String) getArgumentValue(queryField, getConfig().getAttributeIdName(),
				environment.getArguments());
		delete(getEntityClassByEntityName(entityName), id);
		final GQLDeleteResult result = new GQLDeleteResult();
		result.setId(id);
		result.setTypename(entityName);
		return result;
	}

}
