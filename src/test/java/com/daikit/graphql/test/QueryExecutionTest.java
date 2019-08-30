package com.daikit.graphql.test;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Assert;
import org.junit.Test;

import com.daikit.graphql.data.output.GQLExecutionResult;
import com.daikit.graphql.test.data.EmbeddedData1;
import com.daikit.graphql.test.data.Entity1;
import com.daikit.graphql.test.data.Entity1ListLoadResult;
import com.daikit.graphql.test.data.Enum1;

import graphql.ExecutionInput;
import graphql.ExecutionResult;

/**
 * Tests verifying queries are ran correctly
 *
 * @author Thibaut Caselli
 */
@SuppressWarnings("javadoc")
public class QueryExecutionTest extends AbstractTestSuite {

	@Test
	public void testGetEntity1() {
		final String query = readGraphql("testGetEntity1.graphql");
		final ExecutionResult result = handleErrors(EXECUTOR.execute(ExecutionInput.newExecutionInput().query(query)
				.variables(Collections.singletonMap("id", "3")).build()));
		final Entity1 resultData = toObject(result, Entity1.class);
		Assert.assertEquals(3, resultData.getIntAttr());
	}

	@Test
	public void testEntity1ById() {
		final String query = readGraphql("testGetAllEntity1.graphql");
		final ExecutionResult result = handleErrors(
				EXECUTOR.execute(ExecutionInput.newExecutionInput().query(query).build()));
		final Entity1ListLoadResult resultData = toObject(result, Entity1ListLoadResult.class);
		Assert.assertEquals(5, resultData.getData().size());
		for (int i = 0; i < resultData.getData().size(); i++) {
			final Entity1 entity = resultData.getData().get(i);
			Assert.assertEquals(i, entity.getIntAttr());
		}
	}

	@Test
	public void testCustomMethodQuery1() {
		final String query = readGraphql("testCustomMethodQuery1.graphql");
		final ExecutionResult result = handleErrors(EXECUTOR.execute(ExecutionInput.newExecutionInput().query(query)
				.variables(Collections.singletonMap("arg1", "testString")).build()));
		final Entity1 resultData = toObject(result, Entity1.class);
		Assert.assertEquals("testString", resultData.getStringAttr());
		Assert.assertEquals("testString", resultData.getEmbeddedData1().getStringAttr());
	}

	@Test
	public void testCustomMethodQuery2() {
		final String query = readGraphql("testCustomMethodQuery2.graphql");
		final EmbeddedData1 arg1 = new EmbeddedData1();
		arg1.setIntAttr(2);
		arg1.setStringAttr("testString");
		final ExecutionResult result = handleErrors(EXECUTOR
				.execute(ExecutionInput.newExecutionInput().query(query).variables(new HashMap<String, Object>() {
					private static final long serialVersionUID = 1L;
					{
						put("arg1", "testString1");
						put("arg2", toMap(arg1));
					}
				}).build()));
		final Entity1 resultData = toObject(result, Entity1.class);
		Assert.assertEquals(5, resultData.getIntAttr());
		Assert.assertEquals("testString1", resultData.getStringAttr());
		Assert.assertEquals(2, resultData.getEmbeddedData1().getIntAttr());
		Assert.assertEquals("testString", resultData.getEmbeddedData1().getStringAttr());
	}

	@Test
	public void testCustomMethodQuery3() {
		final String query = readGraphql("testCustomMethodQuery3.graphql");
		final Enum1 enumArg = Enum1.VAL2;
		final List<String> stringList = Arrays.asList("string1", "string2");
		final List<Enum1> enumList = Arrays.asList(Enum1.VAL1, Enum1.VAL2);
		final EmbeddedData1 data1 = new EmbeddedData1();
		data1.setStringAttr("data1");
		final EmbeddedData1 data2 = new EmbeddedData1();
		data2.setStringAttr("data2");
		final List<EmbeddedData1> dataList = Arrays.asList(data1, data2);
		final ExecutionResult result = handleErrors(EXECUTOR
				.execute(ExecutionInput.newExecutionInput().query(query).variables(new HashMap<String, Object>() {
					private static final long serialVersionUID = 1L;
					{
						put("arg1", enumArg);
						put("arg2", stringList);
						put("arg3", enumList);
						put("arg4", dataList.stream().map(data -> toMap(data)).collect(Collectors.toList()));
						put("arg5", null);
					}
				}).build()));
		final Entity1 resultData = toObject(result, Entity1.class);
		Assert.assertEquals(enumArg, resultData.getEnumAttr());
		Assert.assertEquals(stringList, resultData.getStringList());
		Assert.assertEquals(enumList, resultData.getEnumList());
		Assert.assertEquals(dataList.size(), resultData.getEmbeddedData1s().size());
		Assert.assertEquals("NULLVALUE", resultData.getStringAttr());
		dataList.forEach(
				data -> Assert.assertTrue("dataList argument does not contain data[" + data.getStringAttr() + "]",
						resultData.getEmbeddedData1s().stream()
								.filter(resData -> data.getStringAttr().equals(resData.getStringAttr())).findFirst()
								.isPresent()));
	}

	@Test
	public void testGetDynamicAttribute() {
		final String id = "3";
		final String query = readGraphql("testGetDynamicAttribute.graphql");
		final GQLExecutionResult result = handleErrors(EXECUTOR
				.execute(ExecutionInput.newExecutionInput().query(query).variables(new HashMap<String, Object>() {
					private static final long serialVersionUID = 1L;
					{
						put("id", id);
					}
				}).build()));
		Assert.assertEquals("dynamicValue" + id, getResultDataProperty(result, "dynamicAttribute1"));
	}

}
