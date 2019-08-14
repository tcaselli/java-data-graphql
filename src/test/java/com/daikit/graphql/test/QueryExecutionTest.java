package com.daikit.graphql.test;

import java.util.Collections;
import java.util.HashMap;

import org.junit.Assert;
import org.junit.Test;

import com.daikit.graphql.test.data.EmbeddedData1;
import com.daikit.graphql.test.data.Entity1;
import com.daikit.graphql.test.data.Entity1ListLoadResult;

import graphql.ExecutionInput;
import graphql.ExecutionResult;

/**
 * Tests verifying queries are ran correctly
 *
 * @author tcaselli
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
		Assert.assertEquals("stringAttr", resultData.getStringAttr());
		Assert.assertEquals("test attr", resultData.getEmbeddedData1().getStringAttr());
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
						put("arg1", toMap(arg1));
						put("arg2", "value2");
					}
				}).build()));
		final Entity1 resultData = toObject(result, Entity1.class);
		Assert.assertEquals(5, resultData.getIntAttr());
		Assert.assertEquals("stringAttr", resultData.getStringAttr());
		Assert.assertEquals(2, resultData.getEmbeddedData1().getIntAttr());
		Assert.assertEquals("testString", resultData.getEmbeddedData1().getStringAttr());
	}

	@Test
	public void testGetDynamicAttribute() {
		final String id = "3";
		final String query = readGraphql("testGetDynamicAttribute.graphql");
		final ExecutionResult result = handleErrors(EXECUTOR
				.execute(ExecutionInput.newExecutionInput().query(query).variables(new HashMap<String, Object>() {
					private static final long serialVersionUID = 1L;
					{
						put("id", id);
					}
				}).build()));
		Assert.assertEquals("dynamicValue" + id, getResultDataProperty(result, "dynamicAttribute1"));
	}

}
