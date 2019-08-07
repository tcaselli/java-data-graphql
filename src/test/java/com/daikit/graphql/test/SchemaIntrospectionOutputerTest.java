package com.daikit.graphql.test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;

import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.daikit.graphql.introspection.GQLIntrospection;
import com.daikit.graphql.utils.Message;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import graphql.ExecutionResult;

/**
 * Test suite for schema building
 *
 * @author tcaselli
 */
public class SchemaIntrospectionOutputerTest extends AbstractTestSuite {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	private static ObjectWriter WRITER_PRETTY;

	static {
		final ObjectMapper mapper = new ObjectMapper();
		mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		mapper.configure(MapperFeature.PROPAGATE_TRANSIENT_MARKER, true);
		WRITER_PRETTY = mapper.writer(new DefaultPrettyPrinter());
	}

	/**
	 * Test introspection
	 *
	 * @throws IOException
	 *             if an error occurred while writing the file
	 */
	@Test
	public void testIntrospection() throws IOException {
		logger.info("Test introspection");
		final ExecutionResult schemaIntrospection = GQLIntrospection.getAllTypes(query -> executor.execute(query));
		final String json = WRITER_PRETTY.writeValueAsString(schemaIntrospection.toSpecification());
		new File("src/test/output").mkdirs();
		final File output = new File("src/test/output/introspection.json");
		IOUtils.write(json, new FileOutputStream(output), Charset.forName("UTF-8"));
		logger.debug(Message.format("Output file written to [{}]", output.getAbsolutePath()));
	}

}
