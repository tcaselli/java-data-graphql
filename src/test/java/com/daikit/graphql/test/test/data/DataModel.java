package com.daikit.graphql.test.test.data;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.IOUtils;

/**
 * A data model for tests
 *
 * @author tcaselli
 */
public class DataModel {

	private final List<Model1> model1s = new ArrayList<>();
	private final List<Model2> model2s = new ArrayList<>();
	private final List<Model3> model3s = new ArrayList<>();
	private final List<Model4> model4s = new ArrayList<>();

	/**
	 * Constructor
	 *
	 * @throws IOException
	 *             if file is not readable
	 * @throws FileNotFoundException
	 *             if file is not found
	 */
	public DataModel() throws FileNotFoundException, IOException {
		final File file = new File(getClass().getClassLoader().getResource("data/file.txt").getFile());
		final byte[] fileBytes = IOUtils.toByteArray(new FileInputStream(file));

		for (int i = 0; i < 5; i++) {
			final Model1 model = new Model1();
			model.setId(Integer.valueOf(i).toString());
			model.setIntAttr(i);
			model.setLongAttr(Integer.valueOf(i).longValue());
			model.setDoubleAttr(Integer.valueOf(i).doubleValue());
			model.setStringAttr("stringAttr_" + i);
			model.setBooleanAttr(true);
			model.setBigIntAttr(BigInteger.valueOf(Integer.valueOf(i).longValue()));
			model.setBigDecimalAttr(BigDecimal.valueOf(Integer.valueOf(i).doubleValue()));
			model.setBytesAttr(fileBytes);
			model.setShortAttr(Integer.valueOf(i).shortValue());
			model.setCharAttr(Integer.valueOf(i).toString().charAt(0));
			model.setDateAttr(new Date());
			model.setFileAttr(file);
			model.setLocalDateAttr(LocalDate.now());
			model.setLocalDateTimeAttr(LocalDateTime.now());
			model.setEnumAttr(Enum1.VAL2);
			model1s.add(model);
		}

		for (int i = 0; i < 5; i++) {
			final Model2 model = new Model2();
			model.setId(Integer.valueOf(i).toString());
			model2s.add(model);
		}

		for (int i = 0; i < 5; i++) {
			final Model3 model = new Model3();
			model.setId(Integer.valueOf(i).toString());
			model3s.add(model);
		}

		for (int i = 0; i < 5; i++) {
			final Model4 model = new Model4();
			model.setId(Integer.valueOf(i).toString());
			model4s.add(model);
		}

		final List<EmbeddedData1> embeddedData1s = new ArrayList<>();

		for (int i = 0; i < 5; i++) {
			final EmbeddedData1 data = new EmbeddedData1();
			data.setIntAttr(i);
			data.setLongAttr(Integer.valueOf(i).longValue());
			data.setDoubleAttr(Integer.valueOf(i).doubleValue());
			data.setStringAttr("stringAttr_" + i);
			data.setBooleanAttr(true);
			data.setBigIntAttr(BigInteger.valueOf(Integer.valueOf(i).longValue()));
			data.setBigDecimalAttr(BigDecimal.valueOf(Integer.valueOf(i).doubleValue()));
			data.setBytesAttr(fileBytes);
			data.setShortAttr(Integer.valueOf(i).shortValue());
			data.setCharAttr(Integer.valueOf(i).toString().charAt(0));
			data.setDateAttr(new Date());
			data.setFileAttr(file);
			data.setLocalDateAttr(LocalDate.now());
			data.setLocalDateTimeAttr(LocalDateTime.now());
			data.setEnumAttr(Enum1.VAL2);

			final EmbeddedData2 data2 = new EmbeddedData2();
			data.setData2(data2);

			for (int j = 0; j < 5; j++) {
				final EmbeddedData3 data3 = new EmbeddedData3();
				data.getData3s().add(data3);
			}

			embeddedData1s.add(data);
		}

		for (int i = 0; i < 5; i++) {
			final Model1 model1 = model1s.get(i);
			final Model2 model2 = model2s.get(i);

			model1.setModel2(model2);
			model2.getModel1s().add(model1);

			if (i == 0) {
				model1.getModel3s().addAll(model3s);
				model3s.forEach(model3 -> model3.setModel1(model1));
			}

			model1.getModel4s().addAll(model4s);
			model4s.forEach(model4 -> model4.getModel1s().add(model1));

			model1.setEmbeddedData1(embeddedData1s.get(i));

			model1.getEmbeddedData1s().addAll(embeddedData1s);
		}

	}

	/**
	 * @return the model1s
	 */
	public List<Model1> getModel1s() {
		return model1s;
	}

	/**
	 * @return the model2s
	 */
	public List<Model2> getModel2s() {
		return model2s;
	}

	/**
	 * @return the model3s
	 */
	public List<Model3> getModel3s() {
		return model3s;
	}

	/**
	 * @return the model4s
	 */
	public List<Model4> getModel4s() {
		return model4s;
	}

}
