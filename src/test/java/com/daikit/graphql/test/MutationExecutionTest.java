package com.daikit.graphql.test;

import java.util.Collections;
import java.util.HashMap;

import org.junit.Assert;
import org.junit.Test;

import com.daikit.graphql.data.output.GQLDeleteResult;
import com.daikit.graphql.test.data.EmbeddedData1;
import com.daikit.graphql.test.data.Entity1;

import graphql.ExecutionInput;
import graphql.ExecutionResult;

/**
 * Tests verifying mutations are ran correctly
 *
 * @author Thibaut Caselli
 */
@SuppressWarnings("javadoc")
public class MutationExecutionTest extends AbstractTestSuite {

	@Test
	public void testDeleteEntity() {
		final String id = "3";
		final Entity1 entity1 = getEntity(id);
		Assert.assertNotNull(entity1);
		final String mutation = readGraphql("testDeleteEntity.graphql");
		final ExecutionResult result = handleErrors(executorManualMetaModel.execute(ExecutionInput.newExecutionInput().query(mutation)
				.variables(Collections.singletonMap("id", "3")).build()));
		final GQLDeleteResult deletResult = toObject(result, GQLDeleteResult.class);
		Assert.assertEquals("3", deletResult.getId());
		final Entity1 entity1Bis = getEntity(id);
		Assert.assertNull(entity1Bis);
	}

	@Test
	public void testSaveEntity() {
		final EmbeddedData1 data1 = new EmbeddedData1();
		data1.setStringAttr("data1");
		final String id = "3";
		final Entity1 entity1 = getEntity(id);
		Assert.assertNotNull(entity1);
		Assert.assertEquals(3, entity1.getIntAttr());
		final String mutation = readGraphql("testSaveEntity.graphql");
		final ExecutionResult result = handleErrors(executorManualMetaModel
				.execute(ExecutionInput.newExecutionInput().query(mutation).variables(new HashMap<String, Object>() {
					private static final long serialVersionUID = 1L;
					{
						put("id", id);
						put("intAttr", 150);
						put("embeddedData1", toMap(data1));
					}
				}).build()));
		final Entity1 entity1Bis = toObject(result, Entity1.class);
		Assert.assertEquals(150, entity1Bis.getIntAttr());
		Assert.assertEquals("data1", entity1Bis.getEmbeddedData1().getStringAttr());
		final Entity1 entity1Ter = getEntity(id);
		Assert.assertEquals(150, entity1Ter.getIntAttr());
		Assert.assertEquals("data1", entity1Ter.getEmbeddedData1().getStringAttr());
	}

	@Test
	public void testCustomMethodMutation1() {
		final String mutation = readGraphql("testCustomMethodMutation1.graphql");
		final ExecutionResult result = handleErrors(executorManualMetaModel.execute(ExecutionInput.newExecutionInput().query(mutation)
				.variables(Collections.singletonMap("arg1", "testString")).build()));
		final Entity1 resultData = toObject(result, Entity1.class);
		Assert.assertEquals("testString", resultData.getStringAttr());
	}

	@Test
	public void testSaveDynamicAttribute() {
		final String id = "3";
		final String mutation = readGraphql("testSaveDynamicAttribute.graphql");
		final ExecutionResult result = handleErrors(executorManualMetaModel
				.execute(ExecutionInput.newExecutionInput().query(mutation).variables(new HashMap<String, Object>() {
					private static final long serialVersionUID = 1L;
					{
						put("id", id);
						put("dynamicAttribute2", "blabla");
					}
				}).build()));
		final Entity1 entity1Bis = toObject(result, Entity1.class);
		Assert.assertEquals("blabla", entity1Bis.getStringAttr());
		final Entity1 entity1Ter = getEntity(id);
		Assert.assertEquals("blabla", entity1Ter.getStringAttr());
	}

	private Entity1 getEntity(final String id) {
		final String query = readGraphql("testGetEntity1.graphql");
		final ExecutionResult result = handleErrors(executorManualMetaModel.execute(
				ExecutionInput.newExecutionInput().query(query).variables(Collections.singletonMap("id", id)).build()));
		return toObject(result, Entity1.class);
	}

}
