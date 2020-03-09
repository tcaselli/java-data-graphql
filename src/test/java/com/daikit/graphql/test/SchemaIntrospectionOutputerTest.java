package com.daikit.graphql.test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;

import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.daikit.graphql.data.output.GQLExecutionResult;
import com.daikit.graphql.utils.Message;
import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * Test suite for schema building
 *
 * @author Thibaut Caselli
 */
public class SchemaIntrospectionOutputerTest extends AbstractTestSuite {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * Test introspection
	 *
	 * @throws IOException
	 *             if an error occurred while writing the file
	 */
	@Test
	public void testIntrospection() throws IOException {
		logger.info("Test introspection");
		generateIntrospectionFile(true);
		generateIntrospectionFile(false);
	}

	private void generateIntrospectionFile(final boolean automatic)
			throws JsonProcessingException, IOException, FileNotFoundException {
		final GQLExecutionResult schemaIntrospection = getSchemaIntrospection(getDefaultExecutionContext(), automatic);
		final String json = WRITER_PRETTY.writeValueAsString(schemaIntrospection.toSpecification());
		new File("src/test/output").mkdirs();
		final File output = new File("src/test/output/introspection" + (automatic ? "_auto" : "") + ".json");
		IOUtils.write(json, new FileOutputStream(output), Charset.forName("UTF-8"));
		logger.debug(Message.format("Output file written to [{}]", output.getAbsolutePath()));
	}

}
