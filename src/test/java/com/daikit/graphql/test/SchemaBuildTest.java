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
import com.daikit.graphql.test.data.Enum1;
import com.daikit.graphql.test.data.Model1;
import com.daikit.graphql.test.data.Model2;
import com.daikit.graphql.test.data.Model3;
import com.daikit.graphql.test.data.Model4;
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
	 * Test {@link Model1} schema representation
	 */
	@Test
	public void testModel1() {
		logger.info("Run testModel1");
		final IntrospectionResult introspection = getIntrospection();
		final IntrospectionFullType fullType = getFullType(introspection, Model1.class);
		Assert.assertEquals(25, fullType.getFields().size());

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
		assertField(fullType, "localDateAttr", IntrospectionTypeKindEnum.SCALAR, GQLJavaScalars.GraphQLLocalDate.getName());
		assertField(fullType, "localDateTimeAttr", IntrospectionTypeKindEnum.SCALAR,
				GQLJavaScalars.GraphQLLocalDateTime.getName());

		assertField(fullType, "stringList", IntrospectionTypeKindEnum.LIST, IntrospectionTypeKindEnum.SCALAR,
				Scalars.GraphQLString.getName());
		assertField(fullType, "stringSet", IntrospectionTypeKindEnum.LIST, IntrospectionTypeKindEnum.SCALAR,
				Scalars.GraphQLString.getName());

		assertField(fullType, "enumAttr", IntrospectionTypeKindEnum.ENUM, Enum1.class.getSimpleName());

		assertField(fullType, "enumList", IntrospectionTypeKindEnum.LIST, IntrospectionTypeKindEnum.ENUM, Enum1.class);
		assertField(fullType, "enumSet", IntrospectionTypeKindEnum.LIST, IntrospectionTypeKindEnum.ENUM, Enum1.class);

		assertField(fullType, "model2", IntrospectionTypeKindEnum.OBJECT, Model2.class);
		assertField(fullType, "model3s", IntrospectionTypeKindEnum.LIST, IntrospectionTypeKindEnum.OBJECT, Model3.class);
		assertField(fullType, "model4s", IntrospectionTypeKindEnum.LIST, IntrospectionTypeKindEnum.OBJECT, Model4.class);

		assertField(fullType, "embeddedData1", IntrospectionTypeKindEnum.OBJECT, EmbeddedData1.class);
		assertField(fullType, "embeddedData1s", IntrospectionTypeKindEnum.LIST, IntrospectionTypeKindEnum.OBJECT, EmbeddedData1.class);
	}

	/**
	 * Test {@link Model2} schema representation
	 */
	@Test
	public void testModel2() {
		logger.info("Run testModel2");
		final IntrospectionResult introspection = getIntrospection();
		final IntrospectionFullType fullType = getFullType(introspection, Model2.class);
		assertField(fullType, "id", IntrospectionTypeKindEnum.SCALAR, Scalars.GraphQLID.getName());
		assertField(fullType, "model1s", IntrospectionTypeKindEnum.LIST, IntrospectionTypeKindEnum.OBJECT, Model1.class);
	}

	/**
	 * Test {@link Model3} schema representation
	 */
	@Test
	public void testModel3() {
		logger.info("Run testModel3");
		final IntrospectionResult introspection = getIntrospection();
		final IntrospectionFullType fullType = getFullType(introspection, Model3.class);
		assertField(fullType, "id", IntrospectionTypeKindEnum.SCALAR, Scalars.GraphQLID.getName());
		assertField(fullType, "model1", IntrospectionTypeKindEnum.OBJECT, Model1.class);
	}

	/**
	 * Test {@link Model1} schema representation
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
		assertField(fullType, "localDateAttr", IntrospectionTypeKindEnum.SCALAR, GQLJavaScalars.GraphQLLocalDate.getName());
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
		assertField(fullType, "data3s", IntrospectionTypeKindEnum.LIST, IntrospectionTypeKindEnum.OBJECT, EmbeddedData3.class);
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
		assertInputField(orderByInputType, "field", IntrospectionTypeKindEnum.NON_NULL, IntrospectionTypeKindEnum.SCALAR,
				Scalars.GraphQLString.getName());
		assertInputField(orderByInputType, "direction", IntrospectionTypeKindEnum.ENUM, getOrderByDirectionOutputTypeName());

		// OrderByOutputType
		final IntrospectionFullType orderByOutputType = getFullType(introspection, getOrderByOutputTypeName());
		Assert.assertEquals(2, orderByOutputType.getFields().size());
		assertField(orderByOutputType, "field", IntrospectionTypeKindEnum.SCALAR, Scalars.GraphQLString.getName());
		assertField(orderByOutputType, "direction", IntrospectionTypeKindEnum.ENUM, getOrderByDirectionOutputTypeName());

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
	 * Test all expected internal model related types are present
	 */
	@Test
	public void testInternalModelRelatedTypes() {
		logger.info("Run testInternalModelRelatedTypes");
		final IntrospectionResult introspection = getIntrospection();

		// Model1InputType
		final IntrospectionFullType model1InputType = getFullType(introspection,
				Model1.class.getSimpleName() + GQLSchemaConstants.INPUT_OBJECT_SUFFIX);
		Assert.assertEquals(25, model1InputType.getInputFields().size());

		assertInputField(model1InputType, "id", IntrospectionTypeKindEnum.SCALAR, Scalars.GraphQLID.getName());
		assertInputField(model1InputType, "intAttr", IntrospectionTypeKindEnum.SCALAR, Scalars.GraphQLInt.getName());
		assertInputField(model1InputType, "longAttr", IntrospectionTypeKindEnum.SCALAR, Scalars.GraphQLLong.getName());
		assertInputField(model1InputType, "doubleAttr", IntrospectionTypeKindEnum.SCALAR, Scalars.GraphQLFloat.getName());
		assertInputField(model1InputType, "stringAttr", IntrospectionTypeKindEnum.SCALAR, Scalars.GraphQLString.getName());
		assertInputField(model1InputType, "booleanAttr", IntrospectionTypeKindEnum.SCALAR, Scalars.GraphQLBoolean.getName());
		assertInputField(model1InputType, "bigIntAttr", IntrospectionTypeKindEnum.SCALAR, Scalars.GraphQLBigInteger.getName());
		assertInputField(model1InputType, "bigDecimalAttr", IntrospectionTypeKindEnum.SCALAR,
				Scalars.GraphQLBigDecimal.getName());
		assertInputField(model1InputType, "bytesAttr", IntrospectionTypeKindEnum.SCALAR, Scalars.GraphQLByte.getName());
		assertInputField(model1InputType, "shortAttr", IntrospectionTypeKindEnum.SCALAR, Scalars.GraphQLShort.getName());
		assertInputField(model1InputType, "charAttr", IntrospectionTypeKindEnum.SCALAR, Scalars.GraphQLChar.getName());
		assertInputField(model1InputType, "dateAttr", IntrospectionTypeKindEnum.SCALAR, GQLJavaScalars.GraphQLDate.getName());
		assertInputField(model1InputType, "fileAttr", IntrospectionTypeKindEnum.SCALAR, GQLJavaScalars.GraphQLFile.getName());
		assertInputField(model1InputType, "localDateAttr", IntrospectionTypeKindEnum.SCALAR,
				GQLJavaScalars.GraphQLLocalDate.getName());
		assertInputField(model1InputType, "localDateTimeAttr", IntrospectionTypeKindEnum.SCALAR,
				GQLJavaScalars.GraphQLLocalDateTime.getName());

		assertInputField(model1InputType, "stringList", IntrospectionTypeKindEnum.LIST, IntrospectionTypeKindEnum.SCALAR,
				Scalars.GraphQLString.getName());
		assertInputField(model1InputType, "stringSet", IntrospectionTypeKindEnum.LIST, IntrospectionTypeKindEnum.SCALAR,
				Scalars.GraphQLString.getName());

		assertInputField(model1InputType, "stringList", IntrospectionTypeKindEnum.LIST, IntrospectionTypeKindEnum.SCALAR,
				Scalars.GraphQLString.getName());
		assertInputField(model1InputType, "stringSet", IntrospectionTypeKindEnum.LIST, IntrospectionTypeKindEnum.SCALAR,
				Scalars.GraphQLString.getName());

		assertInputField(model1InputType, "enumAttr", IntrospectionTypeKindEnum.ENUM, Enum1.class.getSimpleName());

		assertInputField(model1InputType, "enumList", IntrospectionTypeKindEnum.LIST, IntrospectionTypeKindEnum.ENUM, Enum1.class);
		assertInputField(model1InputType, "enumSet", IntrospectionTypeKindEnum.LIST, IntrospectionTypeKindEnum.ENUM, Enum1.class);

		assertInputField(model1InputType, "model2" + GQLSchemaConstants.ID_SUFFIX, IntrospectionTypeKindEnum.SCALAR,
				Scalars.GraphQLID.getName());
		assertInputField(model1InputType, "model3" + GQLSchemaConstants.IDS_SUFFIX, IntrospectionTypeKindEnum.LIST,
				IntrospectionTypeKindEnum.SCALAR, Scalars.GraphQLID.getName());
		assertInputField(model1InputType, "model4" + GQLSchemaConstants.IDS_SUFFIX, IntrospectionTypeKindEnum.LIST,
				IntrospectionTypeKindEnum.SCALAR, Scalars.GraphQLID.getName());

		assertInputField(model1InputType, "embeddedData1", IntrospectionTypeKindEnum.INPUT_OBJECT,
				EmbeddedData1.class.getSimpleName() + GQLSchemaConstants.INPUT_OBJECT_SUFFIX);
		assertInputField(model1InputType, "embeddedData1s", IntrospectionTypeKindEnum.LIST, IntrospectionTypeKindEnum.INPUT_OBJECT,
				EmbeddedData1.class.getSimpleName() + GQLSchemaConstants.INPUT_OBJECT_SUFFIX);

		// Model1LoadResult
		final IntrospectionFullType model1LoadResult = getFullType(introspection,
				Model1.class.getSimpleName() + GQLSchemaConstants.LOAD_RESULT_SUFFIX);
		Assert.assertEquals(3, model1LoadResult.getFields().size());

		assertField(model1LoadResult, "data", IntrospectionTypeKindEnum.LIST, IntrospectionTypeKindEnum.OBJECT,
				Model1.class.getSimpleName());
		assertField(model1LoadResult, "orderBy", IntrospectionTypeKindEnum.LIST, IntrospectionTypeKindEnum.OBJECT,
				getOrderByOutputTypeName());
		assertField(model1LoadResult, "paging", IntrospectionTypeKindEnum.OBJECT, getPagingOutputTypeName());

		// Model2InputType
		final IntrospectionFullType model2InputType = getFullType(introspection,
				Model2.class.getSimpleName() + GQLSchemaConstants.INPUT_OBJECT_SUFFIX);
		Assert.assertEquals(2, model2InputType.getInputFields().size());

		assertInputField(model2InputType, "id", IntrospectionTypeKindEnum.SCALAR, Scalars.GraphQLID.getName());
		assertInputField(model2InputType, "model1" + GQLSchemaConstants.IDS_SUFFIX, IntrospectionTypeKindEnum.LIST,
				IntrospectionTypeKindEnum.SCALAR, Scalars.GraphQLID.getName());

		// Model2LoadResult
		final IntrospectionFullType model2LoadResult = getFullType(introspection,
				Model2.class.getSimpleName() + GQLSchemaConstants.LOAD_RESULT_SUFFIX);
		Assert.assertEquals(3, model2LoadResult.getFields().size());

		assertField(model2LoadResult, "data", IntrospectionTypeKindEnum.LIST, IntrospectionTypeKindEnum.OBJECT,
				Model2.class.getSimpleName());
		assertField(model2LoadResult, "orderBy", IntrospectionTypeKindEnum.LIST, IntrospectionTypeKindEnum.OBJECT,
				getOrderByOutputTypeName());
		assertField(model2LoadResult, "paging", IntrospectionTypeKindEnum.OBJECT, getPagingOutputTypeName());

		// Model3InputType
		final IntrospectionFullType model3InputType = getFullType(introspection,
				Model3.class.getSimpleName() + GQLSchemaConstants.INPUT_OBJECT_SUFFIX);
		Assert.assertEquals(2, model3InputType.getInputFields().size());

		assertInputField(model3InputType, "id", IntrospectionTypeKindEnum.SCALAR, Scalars.GraphQLID.getName());
		assertInputField(model3InputType, "model1" + GQLSchemaConstants.ID_SUFFIX, IntrospectionTypeKindEnum.SCALAR,
				Scalars.GraphQLID.getName());

		// Model4InputType
		final IntrospectionFullType model4InputType = getFullType(introspection,
				Model4.class.getSimpleName() + GQLSchemaConstants.INPUT_OBJECT_SUFFIX);
		Assert.assertEquals(2, model4InputType.getInputFields().size());

		assertInputField(model4InputType, "id", IntrospectionTypeKindEnum.SCALAR, Scalars.GraphQLID.getName());
		assertInputField(model4InputType, "model1" + GQLSchemaConstants.IDS_SUFFIX, IntrospectionTypeKindEnum.LIST,
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
		Assert.assertEquals(8, queryType.getFields().size());

		// - check all queries are available
		final List<String> queryNames = new ArrayList<>();
		Arrays.asList(Model1.class, Model2.class, Model3.class, Model4.class).stream().forEach(clazz -> {
			queryNames.add(GQLSchemaConstants.QUERY_GET_SINGLE_PREFIX + clazz.getSimpleName());
			queryNames.add(GQLSchemaConstants.QUERY_GET_LIST_PREFIX + clazz.getSimpleName());
		});
		queryType.getFields().forEach(field -> Assert.assertTrue(queryNames.contains(field.getName())));

		// - check one 'getSingle' query (other ones are built the same way)
		final IntrospectionTypeField getModel1 = assertField(queryType, queryNames.get(0), IntrospectionTypeKindEnum.OBJECT,
				Model1.class);
		Assert.assertEquals(1, getModel1.getArgs().size());
		assertArg(getModel1, "id", IntrospectionTypeKindEnum.NON_NULL, IntrospectionTypeKindEnum.SCALAR, Scalars.GraphQLID.getName());

		// - check one 'getAll' query (other ones are built the same way)
		final IntrospectionTypeField getAllModel1 = assertField(queryType, queryNames.get(1), IntrospectionTypeKindEnum.OBJECT,
				Model1.class.getSimpleName() + GQLSchemaConstants.LOAD_RESULT_SUFFIX);
		Assert.assertEquals(3, getAllModel1.getArgs().size());
		assertArg(getAllModel1, GQLSchemaConstants.FILTER, IntrospectionTypeKindEnum.INPUT_OBJECT,
				GQLSchemaConstants.FILTER_FIELDS_PREFIX + Model1.class.getSimpleName());
		assertArg(getAllModel1, GQLSchemaConstants.PAGING, IntrospectionTypeKindEnum.INPUT_OBJECT, getPagingInputTypeName());
		assertArg(getAllModel1, GQLSchemaConstants.ORDER_BY, IntrospectionTypeKindEnum.LIST, IntrospectionTypeKindEnum.INPUT_OBJECT,
				getOrderByInputTypeName());
	}

	/**
	 * Test all expected mutations are present
	 */
	@Test
	public void testMutationTypes() {
		final IntrospectionResult introspection = getIntrospection();
		// MutationType
		final IntrospectionFullType mutationType = getFullType(introspection, GQLSchemaConstants.MUTATION_TYPE);
		Assert.assertEquals(8, mutationType.getFields().size());

		// - check all mutations are available
		final List<String> mutationNames = new ArrayList<>();
		Arrays.asList(Model1.class, Model2.class, Model3.class, Model4.class).stream().forEach(clazz -> {
			mutationNames.add(GQLSchemaConstants.MUTATION_SAVE_PREFIX + clazz.getSimpleName());
			mutationNames.add(GQLSchemaConstants.MUTATION_DELETE_PREFIX + clazz.getSimpleName());
		});
		mutationType.getFields().forEach(field -> Assert.assertTrue(mutationNames.contains(field.getName())));

		// - check one 'save' mutation (other ones are built the same way)
		final IntrospectionTypeField saveModel1 = assertField(mutationType, mutationNames.get(0), IntrospectionTypeKindEnum.OBJECT,
				Model1.class);
		Assert.assertEquals(1, saveModel1.getArgs().size());
		assertArg(saveModel1, "data", IntrospectionTypeKindEnum.NON_NULL, IntrospectionTypeKindEnum.INPUT_OBJECT,
				Model1.class.getSimpleName() + GQLSchemaConstants.INPUT_OBJECT_SUFFIX);

		// - check one 'delete' mutation (other ones are built the same way)
		final IntrospectionTypeField deleteModel1 = assertField(mutationType, mutationNames.get(1), IntrospectionTypeKindEnum.OBJECT,
				GQLSchemaConstants.DELETE_RESULT_PREFIX + GQLSchemaConstants.OUTPUT_OBJECT_SUFFIX);
		Assert.assertEquals(1, deleteModel1.getArgs().size());
		assertArg(deleteModel1, "id", IntrospectionTypeKindEnum.NON_NULL, IntrospectionTypeKindEnum.SCALAR,
				Scalars.GraphQLID.getName());
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
		final List<IntrospectionEnum> introEnums = fullType.getEnumValues().stream().filter(en -> name.equals(en.getName()))
				.collect(Collectors.toList());
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

	private IntrospectionTypeField assertField(IntrospectionFullType fullType, String fieldName, IntrospectionTypeKindEnum kind,
			Class<?> typeClass) {
		return assertField(fullType, fieldName, kind, typeClass.getSimpleName());
	}

	private IntrospectionTypeField assertField(IntrospectionFullType fullType, String fieldName, IntrospectionTypeKindEnum kind,
			String typeName) {
		final IntrospectionTypeField field = getField(fullType, fieldName);
		Assert.assertEquals(kind, field.getType().getKind());
		Assert.assertEquals(typeName, field.getType().getName());
		return field;
	}

	private IntrospectionTypeField assertField(IntrospectionFullType fullType, String fieldName, IntrospectionTypeKindEnum kind,
			IntrospectionTypeKindEnum ofKind, Class<?> typeClass) {
		return assertField(fullType, fieldName, kind, ofKind, typeClass.getSimpleName());
	}

	private IntrospectionTypeField assertField(IntrospectionFullType fullType, String fieldName, IntrospectionTypeKindEnum kind,
			IntrospectionTypeKindEnum ofKind, String typeName) {
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

	private IntrospectionInputValue assertInputField(IntrospectionFullType fullType, String fieldName, IntrospectionTypeKindEnum kind,
			String typeName) {
		final IntrospectionInputValue field = getInputField(fullType, fieldName);
		Assert.assertEquals(kind, field.getType().getKind());
		Assert.assertEquals(typeName, field.getType().getName());
		return field;
	}

	private IntrospectionInputValue assertInputField(IntrospectionFullType fullType, String fieldName, IntrospectionTypeKindEnum kind,
			IntrospectionTypeKindEnum ofKind, Class<?> typeClass) {
		return assertInputField(fullType, fieldName, kind, ofKind, typeClass.getSimpleName());
	}

	private IntrospectionInputValue assertInputField(IntrospectionFullType fullType, String fieldName, IntrospectionTypeKindEnum kind,
			IntrospectionTypeKindEnum ofKind, String typeName) {
		final IntrospectionInputValue field = getInputField(fullType, fieldName);
		Assert.assertEquals(kind, field.getType().getKind());
		Assert.assertEquals(ofKind, field.getType().getOfType().getKind());
		Assert.assertEquals(typeName, field.getType().getOfType().getName());
		return field;
	}

	// Args

	private IntrospectionInputValue getArg(IntrospectionTypeField field, String argName) {
		final List<IntrospectionInputValue> typeArg = field.getArgs().stream().filter(arg -> argName.equals(arg.getName()))
				.collect(Collectors.toList());
		Assert.assertEquals(1, typeArg.size());
		return typeArg.get(0);
	}

	// private IntroInputValue assertArg(IntroTypeField field, String argName,
	// IntroTypeKindEnum kind,
	// Class<?> typeClass) {
	// return assertArg(field, argName, kind, typeClass.getSimpleName());
	// }

	private IntrospectionInputValue assertArg(IntrospectionTypeField field, String argName, IntrospectionTypeKindEnum kind, String typeName) {
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

	private IntrospectionInputValue assertArg(IntrospectionTypeField fullType, String argName, IntrospectionTypeKindEnum kind,
			IntrospectionTypeKindEnum ofKind, String typeName) {
		final IntrospectionInputValue arg = getArg(fullType, argName);
		Assert.assertEquals(kind, arg.getType().getKind());
		Assert.assertEquals(ofKind, arg.getType().getOfType().getKind());
		Assert.assertEquals(typeName, arg.getType().getOfType().getName());
		return arg;
	}

}
