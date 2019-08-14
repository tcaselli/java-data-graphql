package com.daikit.graphql.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Test;

import com.daikit.graphql.constants.GQLJavaScalars;
import com.daikit.graphql.constants.GQLSchemaConstants;
import com.daikit.graphql.enums.GQLOrderByDirectionEnum;
import com.daikit.graphql.test.data.EmbeddedData1;
import com.daikit.graphql.test.data.EmbeddedData2;
import com.daikit.graphql.test.data.EmbeddedData3;
import com.daikit.graphql.test.data.Entity1;
import com.daikit.graphql.test.data.Entity2;
import com.daikit.graphql.test.data.Entity3;
import com.daikit.graphql.test.data.Entity4;
import com.daikit.graphql.test.data.Enum1;
import com.daikit.graphql.test.introspection.IntrospectionEnum;
import com.daikit.graphql.test.introspection.IntrospectionFullType;
import com.daikit.graphql.test.introspection.IntrospectionInputValue;
import com.daikit.graphql.test.introspection.IntrospectionResult;
import com.daikit.graphql.test.introspection.IntrospectionTypeField;
import com.daikit.graphql.test.introspection.IntrospectionTypeKindEnum;

import graphql.Scalars;

/**
 * Test verifying schema is built correctly
 *
 * @author tcaselli
 *
 */
public class SchemaBuildTest extends AbstractTestSuite {

	/**
	 * Test {@link Entity1} schema representation
	 */
	@Test
	public void testEntity1() {
		logger.info("Run testEntity1");
		final IntrospectionResult introspection = getIntrospection();
		final IntrospectionFullType fullType = getFullType(introspection, Entity1.class);
		Assert.assertEquals(27, fullType.getFields().size());

		assertField(fullType, "id", IntrospectionTypeKindEnum.SCALAR, Scalars.GraphQLID.getName());
		assertField(fullType, "intAttr", IntrospectionTypeKindEnum.SCALAR, Scalars.GraphQLInt.getName());
		assertField(fullType, "longAttr", IntrospectionTypeKindEnum.SCALAR, Scalars.GraphQLLong.getName());
		assertField(fullType, "doubleAttr", IntrospectionTypeKindEnum.SCALAR, Scalars.GraphQLFloat.getName());
		assertField(fullType, "stringAttr", IntrospectionTypeKindEnum.SCALAR, Scalars.GraphQLString.getName());
		assertField(fullType, "booleanAttr", IntrospectionTypeKindEnum.SCALAR, Scalars.GraphQLBoolean.getName());
		assertField(fullType, "bigIntAttr", IntrospectionTypeKindEnum.SCALAR, Scalars.GraphQLBigInteger.getName());
		assertField(fullType, "bigDecimalAttr", IntrospectionTypeKindEnum.SCALAR, Scalars.GraphQLBigDecimal.getName());
		assertField(fullType, "bytesAttr", IntrospectionTypeKindEnum.SCALAR, Scalars.GraphQLByte.getName());
		assertField(fullType, "shortAttr", IntrospectionTypeKindEnum.SCALAR, Scalars.GraphQLShort.getName());
		assertField(fullType, "charAttr", IntrospectionTypeKindEnum.SCALAR, Scalars.GraphQLChar.getName());
		assertField(fullType, "dateAttr", IntrospectionTypeKindEnum.SCALAR, GQLJavaScalars.GraphQLDate.getName());
		assertField(fullType, "fileAttr", IntrospectionTypeKindEnum.SCALAR, GQLJavaScalars.GraphQLFile.getName());
		assertField(fullType, "localDateAttr", IntrospectionTypeKindEnum.SCALAR,
				GQLJavaScalars.GraphQLLocalDate.getName());
		assertField(fullType, "localDateTimeAttr", IntrospectionTypeKindEnum.SCALAR,
				GQLJavaScalars.GraphQLLocalDateTime.getName());

		assertField(fullType, "stringList", IntrospectionTypeKindEnum.LIST, IntrospectionTypeKindEnum.SCALAR,
				Scalars.GraphQLString.getName());
		assertField(fullType, "stringSet", IntrospectionTypeKindEnum.LIST, IntrospectionTypeKindEnum.SCALAR,
				Scalars.GraphQLString.getName());

		assertField(fullType, "enumAttr", IntrospectionTypeKindEnum.ENUM, Enum1.class.getSimpleName());

		assertField(fullType, "enumList", IntrospectionTypeKindEnum.LIST, IntrospectionTypeKindEnum.ENUM, Enum1.class);
		assertField(fullType, "enumSet", IntrospectionTypeKindEnum.LIST, IntrospectionTypeKindEnum.ENUM, Enum1.class);

		assertField(fullType, "entity2", IntrospectionTypeKindEnum.OBJECT, Entity2.class);
		assertField(fullType, "entity3s", IntrospectionTypeKindEnum.LIST, IntrospectionTypeKindEnum.OBJECT,
				Entity3.class);
		assertField(fullType, "entity4s", IntrospectionTypeKindEnum.LIST, IntrospectionTypeKindEnum.OBJECT,
				Entity4.class);

		assertField(fullType, "embeddedData1", IntrospectionTypeKindEnum.OBJECT, EmbeddedData1.class);
		assertField(fullType, "embeddedData1s", IntrospectionTypeKindEnum.LIST, IntrospectionTypeKindEnum.OBJECT,
				EmbeddedData1.class);
	}

	/**
	 * Test {@link Entity2} schema representation
	 */
	@Test
	public void testEntity2() {
		logger.info("Run testEntity2");
		final IntrospectionResult introspection = getIntrospection();
		final IntrospectionFullType fullType = getFullType(introspection, Entity2.class);
		assertField(fullType, "id", IntrospectionTypeKindEnum.SCALAR, Scalars.GraphQLID.getName());
		assertField(fullType, "entity1s", IntrospectionTypeKindEnum.LIST, IntrospectionTypeKindEnum.OBJECT,
				Entity1.class);
	}

	/**
	 * Test {@link Entity3} schema representation
	 */
	@Test
	public void testEntity3() {
		logger.info("Run testEntity3");
		final IntrospectionResult introspection = getIntrospection();
		final IntrospectionFullType fullType = getFullType(introspection, Entity3.class);
		assertField(fullType, "id", IntrospectionTypeKindEnum.SCALAR, Scalars.GraphQLID.getName());
		assertField(fullType, "entity1", IntrospectionTypeKindEnum.OBJECT, Entity1.class);
	}

	/**
	 * Test {@link Entity1} schema representation
	 */
	@Test
	public void testEmbeddedData1() {
		logger.info("Run testEmbeddedData1");
		final IntrospectionResult introspection = getIntrospection();
		final IntrospectionFullType fullType = getFullType(introspection, EmbeddedData1.class);
		Assert.assertEquals(21, fullType.getFields().size());

		assertField(fullType, "intAttr", IntrospectionTypeKindEnum.SCALAR, Scalars.GraphQLInt.getName());
		assertField(fullType, "longAttr", IntrospectionTypeKindEnum.SCALAR, Scalars.GraphQLLong.getName());
		assertField(fullType, "doubleAttr", IntrospectionTypeKindEnum.SCALAR, Scalars.GraphQLFloat.getName());
		assertField(fullType, "stringAttr", IntrospectionTypeKindEnum.SCALAR, Scalars.GraphQLString.getName());
		assertField(fullType, "booleanAttr", IntrospectionTypeKindEnum.SCALAR, Scalars.GraphQLBoolean.getName());
		assertField(fullType, "bigIntAttr", IntrospectionTypeKindEnum.SCALAR, Scalars.GraphQLBigInteger.getName());
		assertField(fullType, "bigDecimalAttr", IntrospectionTypeKindEnum.SCALAR, Scalars.GraphQLBigDecimal.getName());
		assertField(fullType, "bytesAttr", IntrospectionTypeKindEnum.SCALAR, Scalars.GraphQLByte.getName());
		assertField(fullType, "shortAttr", IntrospectionTypeKindEnum.SCALAR, Scalars.GraphQLShort.getName());
		assertField(fullType, "charAttr", IntrospectionTypeKindEnum.SCALAR, Scalars.GraphQLChar.getName());
		assertField(fullType, "dateAttr", IntrospectionTypeKindEnum.SCALAR, GQLJavaScalars.GraphQLDate.getName());
		assertField(fullType, "fileAttr", IntrospectionTypeKindEnum.SCALAR, GQLJavaScalars.GraphQLFile.getName());
		assertField(fullType, "localDateAttr", IntrospectionTypeKindEnum.SCALAR,
				GQLJavaScalars.GraphQLLocalDate.getName());
		assertField(fullType, "localDateTimeAttr", IntrospectionTypeKindEnum.SCALAR,
				GQLJavaScalars.GraphQLLocalDateTime.getName());

		assertField(fullType, "stringList", IntrospectionTypeKindEnum.LIST, IntrospectionTypeKindEnum.SCALAR,
				Scalars.GraphQLString.getName());
		assertField(fullType, "stringSet", IntrospectionTypeKindEnum.LIST, IntrospectionTypeKindEnum.SCALAR,
				Scalars.GraphQLString.getName());

		assertField(fullType, "enumAttr", IntrospectionTypeKindEnum.ENUM, Enum1.class.getSimpleName());

		assertField(fullType, "enumList", IntrospectionTypeKindEnum.LIST, IntrospectionTypeKindEnum.ENUM, Enum1.class);
		assertField(fullType, "enumSet", IntrospectionTypeKindEnum.LIST, IntrospectionTypeKindEnum.ENUM, Enum1.class);

		assertField(fullType, "data2", IntrospectionTypeKindEnum.OBJECT, EmbeddedData2.class);
		assertField(fullType, "data3s", IntrospectionTypeKindEnum.LIST, IntrospectionTypeKindEnum.OBJECT,
				EmbeddedData3.class);
	}

	/**
	 * Test all expected internal types are present
	 */
	@Test
	public void testInternalTypes() {
		logger.info("Run testInternalTypes");
		final IntrospectionResult introspection = getIntrospection();

		// OrderByDirectionOutputType
		final IntrospectionFullType orderByDirectionOutputType = getFullType(introspection,
				getOrderByDirectionOutputTypeName());
		Assert.assertEquals(2, orderByDirectionOutputType.getEnumValues().size());
		assertEnum(orderByDirectionOutputType, GQLOrderByDirectionEnum.ASC.toString(), false);
		assertEnum(orderByDirectionOutputType, GQLOrderByDirectionEnum.DESC.toString(), false);

		// OrderByInputType
		final IntrospectionFullType orderByInputType = getFullType(introspection, getOrderByInputTypeName());
		Assert.assertEquals(2, orderByInputType.getInputFields().size());
		assertInputField(orderByInputType, "field", IntrospectionTypeKindEnum.NON_NULL,
				IntrospectionTypeKindEnum.SCALAR, Scalars.GraphQLString.getName());
		assertInputField(orderByInputType, "direction", IntrospectionTypeKindEnum.ENUM,
				getOrderByDirectionOutputTypeName());

		// OrderByOutputType
		final IntrospectionFullType orderByOutputType = getFullType(introspection, getOrderByOutputTypeName());
		Assert.assertEquals(2, orderByOutputType.getFields().size());
		assertField(orderByOutputType, "field", IntrospectionTypeKindEnum.SCALAR, Scalars.GraphQLString.getName());
		assertField(orderByOutputType, "direction", IntrospectionTypeKindEnum.ENUM,
				getOrderByDirectionOutputTypeName());

		// PagingInputType
		final IntrospectionFullType pagingInputType = getFullType(introspection, getPagingInputTypeName());
		Assert.assertEquals(2, pagingInputType.getInputFields().size());
		assertInputField(pagingInputType, "offset", IntrospectionTypeKindEnum.SCALAR, Scalars.GraphQLInt.getName());
		assertInputField(pagingInputType, "limit", IntrospectionTypeKindEnum.SCALAR, Scalars.GraphQLInt.getName());

		// PagingOutputType
		final IntrospectionFullType pagingOutputType = getFullType(introspection, getPagingOutputTypeName());
		Assert.assertEquals(3, pagingOutputType.getFields().size());
		assertField(pagingOutputType, "offset", IntrospectionTypeKindEnum.SCALAR, Scalars.GraphQLInt.getName());
		assertField(pagingOutputType, "limit", IntrospectionTypeKindEnum.SCALAR, Scalars.GraphQLInt.getName());
		assertField(pagingOutputType, "totalLength", IntrospectionTypeKindEnum.SCALAR, Scalars.GraphQLLong.getName());
	}

	/**
	 * Test all expected internal entity related types are present
	 */
	@Test
	public void testInternalEntityRelatedTypes() {
		logger.info("Run testInternalEntityRelatedTypes");
		final IntrospectionResult introspection = getIntrospection();

		// Entity1InputType
		final IntrospectionFullType entity1InputType = getFullType(introspection,
				Entity1.class.getSimpleName() + GQLSchemaConstants.INPUT_OBJECT_SUFFIX);
		Assert.assertEquals(27, entity1InputType.getInputFields().size());

		assertInputField(entity1InputType, "id", IntrospectionTypeKindEnum.SCALAR, Scalars.GraphQLID.getName());
		assertInputField(entity1InputType, "intAttr", IntrospectionTypeKindEnum.SCALAR, Scalars.GraphQLInt.getName());
		assertInputField(entity1InputType, "longAttr", IntrospectionTypeKindEnum.SCALAR, Scalars.GraphQLLong.getName());
		assertInputField(entity1InputType, "doubleAttr", IntrospectionTypeKindEnum.SCALAR,
				Scalars.GraphQLFloat.getName());
		assertInputField(entity1InputType, "stringAttr", IntrospectionTypeKindEnum.SCALAR,
				Scalars.GraphQLString.getName());
		assertInputField(entity1InputType, "booleanAttr", IntrospectionTypeKindEnum.SCALAR,
				Scalars.GraphQLBoolean.getName());
		assertInputField(entity1InputType, "bigIntAttr", IntrospectionTypeKindEnum.SCALAR,
				Scalars.GraphQLBigInteger.getName());
		assertInputField(entity1InputType, "bigDecimalAttr", IntrospectionTypeKindEnum.SCALAR,
				Scalars.GraphQLBigDecimal.getName());
		assertInputField(entity1InputType, "bytesAttr", IntrospectionTypeKindEnum.SCALAR,
				Scalars.GraphQLByte.getName());
		assertInputField(entity1InputType, "shortAttr", IntrospectionTypeKindEnum.SCALAR,
				Scalars.GraphQLShort.getName());
		assertInputField(entity1InputType, "charAttr", IntrospectionTypeKindEnum.SCALAR, Scalars.GraphQLChar.getName());
		assertInputField(entity1InputType, "dateAttr", IntrospectionTypeKindEnum.SCALAR,
				GQLJavaScalars.GraphQLDate.getName());
		assertInputField(entity1InputType, "fileAttr", IntrospectionTypeKindEnum.SCALAR,
				GQLJavaScalars.GraphQLFile.getName());
		assertInputField(entity1InputType, "localDateAttr", IntrospectionTypeKindEnum.SCALAR,
				GQLJavaScalars.GraphQLLocalDate.getName());
		assertInputField(entity1InputType, "localDateTimeAttr", IntrospectionTypeKindEnum.SCALAR,
				GQLJavaScalars.GraphQLLocalDateTime.getName());

		assertInputField(entity1InputType, "stringList", IntrospectionTypeKindEnum.LIST,
				IntrospectionTypeKindEnum.SCALAR, Scalars.GraphQLString.getName());
		assertInputField(entity1InputType, "stringSet", IntrospectionTypeKindEnum.LIST,
				IntrospectionTypeKindEnum.SCALAR, Scalars.GraphQLString.getName());

		assertInputField(entity1InputType, "stringList", IntrospectionTypeKindEnum.LIST,
				IntrospectionTypeKindEnum.SCALAR, Scalars.GraphQLString.getName());
		assertInputField(entity1InputType, "stringSet", IntrospectionTypeKindEnum.LIST,
				IntrospectionTypeKindEnum.SCALAR, Scalars.GraphQLString.getName());

		assertInputField(entity1InputType, "enumAttr", IntrospectionTypeKindEnum.ENUM, Enum1.class.getSimpleName());

		assertInputField(entity1InputType, "enumList", IntrospectionTypeKindEnum.LIST, IntrospectionTypeKindEnum.ENUM,
				Enum1.class);
		assertInputField(entity1InputType, "enumSet", IntrospectionTypeKindEnum.LIST, IntrospectionTypeKindEnum.ENUM,
				Enum1.class);

		assertInputField(entity1InputType, "entity2" + GQLSchemaConstants.ID_SUFFIX, IntrospectionTypeKindEnum.SCALAR,
				Scalars.GraphQLID.getName());
		assertInputField(entity1InputType, "entity3" + GQLSchemaConstants.IDS_SUFFIX, IntrospectionTypeKindEnum.LIST,
				IntrospectionTypeKindEnum.SCALAR, Scalars.GraphQLID.getName());
		assertInputField(entity1InputType, "entity4" + GQLSchemaConstants.IDS_SUFFIX, IntrospectionTypeKindEnum.LIST,
				IntrospectionTypeKindEnum.SCALAR, Scalars.GraphQLID.getName());

		assertInputField(entity1InputType, "embeddedData1", IntrospectionTypeKindEnum.INPUT_OBJECT,
				EmbeddedData1.class.getSimpleName() + GQLSchemaConstants.INPUT_OBJECT_SUFFIX);
		assertInputField(entity1InputType, "embeddedData1s", IntrospectionTypeKindEnum.LIST,
				IntrospectionTypeKindEnum.INPUT_OBJECT,
				EmbeddedData1.class.getSimpleName() + GQLSchemaConstants.INPUT_OBJECT_SUFFIX);

		// Entity1LoadResult
		final IntrospectionFullType entity1LoadResult = getFullType(introspection,
				Entity1.class.getSimpleName() + GQLSchemaConstants.LOAD_RESULT_SUFFIX);
		Assert.assertEquals(3, entity1LoadResult.getFields().size());

		assertField(entity1LoadResult, "data", IntrospectionTypeKindEnum.LIST, IntrospectionTypeKindEnum.OBJECT,
				Entity1.class.getSimpleName());
		assertField(entity1LoadResult, "orderBy", IntrospectionTypeKindEnum.LIST, IntrospectionTypeKindEnum.OBJECT,
				getOrderByOutputTypeName());
		assertField(entity1LoadResult, "paging", IntrospectionTypeKindEnum.OBJECT, getPagingOutputTypeName());

		// Entity2InputType
		final IntrospectionFullType entity2InputType = getFullType(introspection,
				Entity2.class.getSimpleName() + GQLSchemaConstants.INPUT_OBJECT_SUFFIX);
		Assert.assertEquals(2, entity2InputType.getInputFields().size());

		assertInputField(entity2InputType, "id", IntrospectionTypeKindEnum.SCALAR, Scalars.GraphQLID.getName());
		assertInputField(entity2InputType, "entity1" + GQLSchemaConstants.IDS_SUFFIX, IntrospectionTypeKindEnum.LIST,
				IntrospectionTypeKindEnum.SCALAR, Scalars.GraphQLID.getName());

		// Entity2LoadResult
		final IntrospectionFullType entity2LoadResult = getFullType(introspection,
				Entity2.class.getSimpleName() + GQLSchemaConstants.LOAD_RESULT_SUFFIX);
		Assert.assertEquals(3, entity2LoadResult.getFields().size());

		assertField(entity2LoadResult, "data", IntrospectionTypeKindEnum.LIST, IntrospectionTypeKindEnum.OBJECT,
				Entity2.class.getSimpleName());
		assertField(entity2LoadResult, "orderBy", IntrospectionTypeKindEnum.LIST, IntrospectionTypeKindEnum.OBJECT,
				getOrderByOutputTypeName());
		assertField(entity2LoadResult, "paging", IntrospectionTypeKindEnum.OBJECT, getPagingOutputTypeName());

		// Entity3InputType
		final IntrospectionFullType entity3InputType = getFullType(introspection,
				Entity3.class.getSimpleName() + GQLSchemaConstants.INPUT_OBJECT_SUFFIX);
		Assert.assertEquals(2, entity3InputType.getInputFields().size());

		assertInputField(entity3InputType, "id", IntrospectionTypeKindEnum.SCALAR, Scalars.GraphQLID.getName());
		assertInputField(entity3InputType, "entity1" + GQLSchemaConstants.ID_SUFFIX, IntrospectionTypeKindEnum.SCALAR,
				Scalars.GraphQLID.getName());

		// Entity4InputType
		final IntrospectionFullType entity4InputType = getFullType(introspection,
				Entity4.class.getSimpleName() + GQLSchemaConstants.INPUT_OBJECT_SUFFIX);
		Assert.assertEquals(2, entity4InputType.getInputFields().size());

		assertInputField(entity4InputType, "id", IntrospectionTypeKindEnum.SCALAR, Scalars.GraphQLID.getName());
		assertInputField(entity4InputType, "entity1" + GQLSchemaConstants.IDS_SUFFIX, IntrospectionTypeKindEnum.LIST,
				IntrospectionTypeKindEnum.SCALAR, Scalars.GraphQLID.getName());
	}

	/**
	 * Test all expected queries are present
	 */
	@Test
	public void testQueryTypes() {
		final IntrospectionResult introspection = getIntrospection();
		// QueryType
		final IntrospectionFullType queryType = getFullType(introspection, GQLSchemaConstants.QUERY_TYPE);
		// 10 = 4 entities * 2 queries + 2 custom method queries
		Assert.assertEquals(10, queryType.getFields().size());
		// - check all queries are available
		final List<String> queryNames = new ArrayList<>();
		Arrays.asList(Entity1.class, Entity2.class, Entity3.class, Entity4.class).stream().forEach(clazz -> {
			queryNames.add(GQLSchemaConstants.QUERY_GET_SINGLE_PREFIX + clazz.getSimpleName());
			queryNames.add(GQLSchemaConstants.QUERY_GET_LIST_PREFIX + clazz.getSimpleName());
		});
		queryNames.forEach(queryName -> Assert.assertTrue(queryType.getFields().stream()
				.map(IntrospectionTypeField::getName).collect(Collectors.toList()).contains(queryName)));

		// - check one 'getSingle' query (other ones are built the same way)
		final IntrospectionTypeField getEntity1 = assertField(queryType, queryNames.get(0),
				IntrospectionTypeKindEnum.OBJECT, Entity1.class);
		Assert.assertEquals(1, getEntity1.getArgs().size());
		assertArg(getEntity1, "id", IntrospectionTypeKindEnum.NON_NULL, IntrospectionTypeKindEnum.SCALAR,
				Scalars.GraphQLID.getName());

		// - check one 'getAll' query (other ones are built the same way)
		final IntrospectionTypeField getAllEntity1 = assertField(queryType, queryNames.get(1),
				IntrospectionTypeKindEnum.OBJECT,
				Entity1.class.getSimpleName() + GQLSchemaConstants.LOAD_RESULT_SUFFIX);
		Assert.assertEquals(3, getAllEntity1.getArgs().size());
		assertArg(getAllEntity1, GQLSchemaConstants.FILTER, IntrospectionTypeKindEnum.INPUT_OBJECT,
				GQLSchemaConstants.FILTER_FIELDS_PREFIX + Entity1.class.getSimpleName());
		assertArg(getAllEntity1, GQLSchemaConstants.PAGING, IntrospectionTypeKindEnum.INPUT_OBJECT,
				getPagingInputTypeName());
		assertArg(getAllEntity1, GQLSchemaConstants.ORDER_BY, IntrospectionTypeKindEnum.LIST,
				IntrospectionTypeKindEnum.INPUT_OBJECT, getOrderByInputTypeName());
	}

	/**
	 * Test all expected mutations are present
	 */
	@Test
	public void testMutationTypes() {
		final IntrospectionResult introspection = getIntrospection();
		// MutationType
		final IntrospectionFullType mutationType = getFullType(introspection, GQLSchemaConstants.MUTATION_TYPE);
		Assert.assertEquals(9, mutationType.getFields().size());

		// - check all mutations are available
		final List<String> mutationNames = new ArrayList<>();
		Arrays.asList(Entity1.class, Entity2.class, Entity3.class, Entity4.class).stream().forEach(clazz -> {
			mutationNames.add(GQLSchemaConstants.MUTATION_SAVE_PREFIX + clazz.getSimpleName());
			mutationNames.add(GQLSchemaConstants.MUTATION_DELETE_PREFIX + clazz.getSimpleName());
		});
		mutationNames.forEach(mutationName -> Assert.assertTrue(mutationType.getFields().stream()
				.map(IntrospectionTypeField::getName).collect(Collectors.toList()).contains(mutationName)));

		// - check one 'save' mutation (other ones are built the same way)
		final IntrospectionTypeField saveEntity1 = assertField(mutationType, mutationNames.get(0),
				IntrospectionTypeKindEnum.OBJECT, Entity1.class);
		Assert.assertEquals(1, saveEntity1.getArgs().size());
		assertArg(saveEntity1, "data", IntrospectionTypeKindEnum.NON_NULL, IntrospectionTypeKindEnum.INPUT_OBJECT,
				Entity1.class.getSimpleName() + GQLSchemaConstants.INPUT_OBJECT_SUFFIX);

		// - check one 'delete' mutation (other ones are built the same way)
		final IntrospectionTypeField deleteEntity1 = assertField(mutationType, mutationNames.get(1),
				IntrospectionTypeKindEnum.OBJECT,
				GQLSchemaConstants.DELETE_RESULT_PREFIX + GQLSchemaConstants.OUTPUT_OBJECT_SUFFIX);
		Assert.assertEquals(1, deleteEntity1.getArgs().size());
		assertArg(deleteEntity1, "id", IntrospectionTypeKindEnum.NON_NULL, IntrospectionTypeKindEnum.SCALAR,
				Scalars.GraphQLID.getName());
	}

	/**
	 * Test all custom method queries are present
	 */
	@Test
	public void testCustomMethodTypes() {
		final IntrospectionResult introspection = getIntrospection();
		final IntrospectionFullType queryType = getFullType(introspection, GQLSchemaConstants.QUERY_TYPE);

		// - check all custom method queries are available
		final List<String> queryNames = Arrays.asList("customMethodQuery1", "customMethodQuery2");
		queryNames.forEach(queryName -> Assert.assertTrue(queryType.getFields().stream()
				.map(IntrospectionTypeField::getName).collect(Collectors.toList()).contains(queryName)));

		// - check custom method 'customMethod1'
		final IntrospectionTypeField customMethod1 = assertField(queryType, queryNames.get(0),
				IntrospectionTypeKindEnum.OBJECT, Entity1.class);
		Assert.assertEquals(1, customMethod1.getArgs().size());
		assertArg(customMethod1, "arg1", IntrospectionTypeKindEnum.SCALAR, Scalars.GraphQLString.getName());

		// - check custom method 'customMethod2'
		final IntrospectionTypeField customMethod2 = assertField(queryType, queryNames.get(1),
				IntrospectionTypeKindEnum.OBJECT, Entity1.class);
		Assert.assertEquals(2, customMethod2.getArgs().size());
		assertArg(customMethod2, "arg1", IntrospectionTypeKindEnum.INPUT_OBJECT,
				EmbeddedData1.class.getSimpleName() + GQLSchemaConstants.INPUT_OBJECT_SUFFIX);
		assertArg(customMethod2, "arg2", IntrospectionTypeKindEnum.SCALAR, Scalars.GraphQLString.getName());
	}

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// PRIVATE METHODS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	// Names

	private String getOrderByInputTypeName() {
		return StringUtils.capitalize(GQLSchemaConstants.ORDER_BY) + GQLSchemaConstants.INPUT_OBJECT_SUFFIX;
	}

	private String getOrderByDirectionOutputTypeName() {
		final String orderByDirectionOutputTypeName = StringUtils.capitalize(GQLSchemaConstants.ORDER_BY)
				+ StringUtils.capitalize(GQLSchemaConstants.ORDER_BY_DIRECTION)
				+ GQLSchemaConstants.OUTPUT_OBJECT_SUFFIX;
		return orderByDirectionOutputTypeName;
	}

	private String getOrderByOutputTypeName() {
		return StringUtils.capitalize(GQLSchemaConstants.ORDER_BY) + GQLSchemaConstants.OUTPUT_OBJECT_SUFFIX;
	}

	private String getPagingInputTypeName() {
		return StringUtils.capitalize(GQLSchemaConstants.PAGING) + GQLSchemaConstants.INPUT_OBJECT_SUFFIX;
	}

	private String getPagingOutputTypeName() {
		return StringUtils.capitalize(GQLSchemaConstants.PAGING) + GQLSchemaConstants.OUTPUT_OBJECT_SUFFIX;
	}

	private IntrospectionResult getIntrospection() {
		return MAPPER.convertValue(getSchemaIntrospection().toSpecification(), IntrospectionResult.class);
	}

	// FullTypes

	private IntrospectionFullType getFullType(IntrospectionResult introspection, Class<?> entityClass) {
		return getFullType(introspection, entityClass.getSimpleName());
	}

	private IntrospectionFullType getFullType(IntrospectionResult introspection, String name) {
		final List<IntrospectionFullType> types = introspection.getData().getSchema().getTypes().stream()
				.filter(type -> name.equals(type.getName())).collect(Collectors.toList());
		Assert.assertEquals(1, types.size());
		return types.get(0);
	}

	// Enumerations

	private void assertEnum(IntrospectionFullType fullType, String name, boolean deprecated) {
		final List<IntrospectionEnum> introEnums = fullType.getEnumValues().stream()
				.filter(en -> name.equals(en.getName())).collect(Collectors.toList());
		Assert.assertEquals(1, introEnums.size());
		Assert.assertEquals(deprecated, introEnums.get(0).isDeprecated());
	}

	// Fields

	private IntrospectionTypeField getField(IntrospectionFullType fullType, String fieldName) {
		final List<IntrospectionTypeField> typeField = fullType.getFields().stream()
				.filter(field -> fieldName.equals(field.getName())).collect(Collectors.toList());
		Assert.assertEquals(1, typeField.size());
		return typeField.get(0);
	}

	private IntrospectionTypeField assertField(IntrospectionFullType fullType, String fieldName,
			IntrospectionTypeKindEnum kind, Class<?> typeClass) {
		return assertField(fullType, fieldName, kind, typeClass.getSimpleName());
	}

	private IntrospectionTypeField assertField(IntrospectionFullType fullType, String fieldName,
			IntrospectionTypeKindEnum kind, String typeName) {
		final IntrospectionTypeField field = getField(fullType, fieldName);
		Assert.assertEquals(kind, field.getType().getKind());
		Assert.assertEquals(typeName, field.getType().getName());
		return field;
	}

	private IntrospectionTypeField assertField(IntrospectionFullType fullType, String fieldName,
			IntrospectionTypeKindEnum kind, IntrospectionTypeKindEnum ofKind, Class<?> typeClass) {
		return assertField(fullType, fieldName, kind, ofKind, typeClass.getSimpleName());
	}

	private IntrospectionTypeField assertField(IntrospectionFullType fullType, String fieldName,
			IntrospectionTypeKindEnum kind, IntrospectionTypeKindEnum ofKind, String typeName) {
		final IntrospectionTypeField field = getField(fullType, fieldName);
		Assert.assertEquals(kind, field.getType().getKind());
		Assert.assertEquals(ofKind, field.getType().getOfType().getKind());
		Assert.assertEquals(typeName, field.getType().getOfType().getName());
		return field;
	}

	// InputFields

	private IntrospectionInputValue getInputField(IntrospectionFullType fullType, String fieldName) {
		final List<IntrospectionInputValue> typeField = fullType.getInputFields().stream()
				.filter(field -> fieldName.equals(field.getName())).collect(Collectors.toList());
		Assert.assertEquals(1, typeField.size());
		return typeField.get(0);
	}

	// private IntroInputValue assertInputField(IntroFullType fullType, String
	// fieldName, IntroTypeKindEnum kind,
	// Class<?> typeClass) {
	// return assertInputField(fullType, fieldName, kind,
	// typeClass.getSimpleName());
	// }

	private IntrospectionInputValue assertInputField(IntrospectionFullType fullType, String fieldName,
			IntrospectionTypeKindEnum kind, String typeName) {
		final IntrospectionInputValue field = getInputField(fullType, fieldName);
		Assert.assertEquals(kind, field.getType().getKind());
		Assert.assertEquals(typeName, field.getType().getName());
		return field;
	}

	private IntrospectionInputValue assertInputField(IntrospectionFullType fullType, String fieldName,
			IntrospectionTypeKindEnum kind, IntrospectionTypeKindEnum ofKind, Class<?> typeClass) {
		return assertInputField(fullType, fieldName, kind, ofKind, typeClass.getSimpleName());
	}

	private IntrospectionInputValue assertInputField(IntrospectionFullType fullType, String fieldName,
			IntrospectionTypeKindEnum kind, IntrospectionTypeKindEnum ofKind, String typeName) {
		final IntrospectionInputValue field = getInputField(fullType, fieldName);
		Assert.assertEquals(kind, field.getType().getKind());
		Assert.assertEquals(ofKind, field.getType().getOfType().getKind());
		Assert.assertEquals(typeName, field.getType().getOfType().getName());
		return field;
	}

	// Args

	private IntrospectionInputValue getArg(IntrospectionTypeField field, String argName) {
		final List<IntrospectionInputValue> typeArg = field.getArgs().stream()
				.filter(arg -> argName.equals(arg.getName())).collect(Collectors.toList());
		Assert.assertEquals(1, typeArg.size());
		return typeArg.get(0);
	}

	// private IntroInputValue assertArg(IntroTypeField field, String argName,
	// IntroTypeKindEnum kind,
	// Class<?> typeClass) {
	// return assertArg(field, argName, kind, typeClass.getSimpleName());
	// }

	private IntrospectionInputValue assertArg(IntrospectionTypeField field, String argName,
			IntrospectionTypeKindEnum kind, String typeName) {
		final IntrospectionInputValue arg = getArg(field, argName);
		Assert.assertEquals(kind, arg.getType().getKind());
		Assert.assertEquals(typeName, arg.getType().getName());
		return arg;
	}

	// private IntroInputValue assertArg(IntroTypeField field, String argName,
	// IntroTypeKindEnum kind,
	// IntroTypeKindEnum ofKind, Class<?> typeClass) {
	// return assertArg(field, argName, kind, ofKind,
	// typeClass.getSimpleName());
	// }

	private IntrospectionInputValue assertArg(IntrospectionTypeField fullType, String argName,
			IntrospectionTypeKindEnum kind, IntrospectionTypeKindEnum ofKind, String typeName) {
		final IntrospectionInputValue arg = getArg(fullType, argName);
		Assert.assertEquals(kind, arg.getType().getKind());
		Assert.assertEquals(ofKind, arg.getType().getOfType().getKind());
		Assert.assertEquals(typeName, arg.getType().getOfType().getName());
		return arg;
	}

}
