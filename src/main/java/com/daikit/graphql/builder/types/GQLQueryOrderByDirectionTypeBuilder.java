package com.daikit.graphql.builder.types;

import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;

import com.daikit.graphql.builder.GQLSchemaBuilderCache;
import com.daikit.graphql.enums.GQLOrderByDirectionEnum;

import graphql.schema.GraphQLEnumType;

/**
 * GQL query order by direction type builder
 *
 * @author Thibaut Caselli
 */
public class GQLQueryOrderByDirectionTypeBuilder extends GQLAbstractTypesBuilder {

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// CONSTRUCTORS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	/**
	 * Constructor
	 *
	 * @param cache
	 *            the {@link GQLSchemaBuilderCache}
	 */
	public GQLQueryOrderByDirectionTypeBuilder(final GQLSchemaBuilderCache cache) {
		super(cache);
	}

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// METHODS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	/**
	 * Build orderBy direction type and cache it
	 */
	public void buildOrderByDirectionType() {
		logger.debug("Build orderBy direction type");
		getCache().setOrderByDirectionEnumType(buildOrderByDirectionEnumType());
	}

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// PRIVATE UTILS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	private GraphQLEnumType buildOrderByDirectionEnumType() {
		final GraphQLEnumType.Builder builder = GraphQLEnumType.newEnum();
		builder.name(StringUtils.capitalize(getConfig().getQueryGetListFilterAttributeOrderByName())
				+ StringUtils.capitalize(getConfig().getQueryGetListFilterAttributeOrderByDirectionName())
				+ getConfig().getOutputTypeNameSuffix());
		builder.description("Possible directions in which to order a list of items when provided an ["
				+ getConfig().getQueryGetListFilterAttributeOrderByName() + "] argument.");
		Arrays.asList(GQLOrderByDirectionEnum.values())
				.forEach(orderEnum -> builder.value(orderEnum.name(), orderEnum));
		return builder.build();
	}

}
