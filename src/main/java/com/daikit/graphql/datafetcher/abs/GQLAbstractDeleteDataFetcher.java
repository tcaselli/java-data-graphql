package com.daikit.graphql.datafetcher.abs;

import com.daikit.graphql.builder.GQLSchemaBuilder;
import com.daikit.graphql.constants.GQLSchemaConstants;
import com.daikit.graphql.datafetcher.GQLAbstractDataFetcher;
import com.daikit.graphql.query.output.GQLDeleteResult;

import graphql.language.Field;
import graphql.schema.DataFetchingEnvironment;

/**
 * Abstract super class that may be overridden to provide "delete entity" data
 * fetcher by ID to the schema building. This class is typically to be extended
 * and used in {@link GQLSchemaBuilder} for buildSchema delete method data
 * fetcher argument
 *
 * @author tcaselli
 *
 */
public abstract class GQLAbstractDeleteDataFetcher extends GQLAbstractDataFetcher<GQLDeleteResult> {

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// ABSTRACT METHODS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	protected abstract void runDelete(String entityName, String id);

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// METHODS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	@Override
	public GQLDeleteResult get(final DataFetchingEnvironment environment) {
		final Field queryField = environment.getField();
		final String entityName = getEntityName(GQLSchemaConstants.MUTATION_DELETE_PREFIX, queryField.getName());
		final String id = (String) getArgumentValue(queryField, GQLSchemaConstants.FIELD_ID,
				environment.getArguments());
		runDelete(entityName, id);
		final GQLDeleteResult result = new GQLDeleteResult();
		result.setId(id);
		result.setTypename(entityName);
		return result;
	}

}
