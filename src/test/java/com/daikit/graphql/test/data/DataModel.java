package com.daikit.graphql.test.data;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;

import com.daikit.graphql.enums.GQLOrderByDirectionEnum;
import com.daikit.graphql.query.input.GQLListLoadConfig;
import com.daikit.graphql.query.output.GQLListLoadResult;
import com.daikit.graphql.query.output.GQLPaging;
import com.daikit.graphql.test.utils.PropertyUtils;

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

	private final Map<Class<?>, List<? extends AbstractModel>> database = new HashMap<>();

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// CONSTRUCTORS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	/**
	 * Constructor
	 *
	 * @throws IOException
	 *             if file is not readable
	 * @throws FileNotFoundException
	 *             if file is not found
	 */
	public DataModel() throws FileNotFoundException, IOException {

		database.put(Model1.class, model1s);
		database.put(Model2.class, model2s);
		database.put(Model3.class, model3s);
		database.put(Model4.class, model4s);

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

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// PUBLIC METHODS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	/**
	 * Get model by ID
	 *
	 * @param modelClass
	 *            the model class
	 * @param id
	 *            the ID
	 * @return an {@link Optional} model
	 */
	public Optional<?> getById(Class<?> modelClass, String id) {
		return database.get(modelClass).stream().filter(item -> item.getId().equals(id)).findFirst();
	}

	/**
	 * Get all models
	 *
	 * @param modelClass
	 *            the model class
	 * @param listLoadConfig
	 *            the {@link GQLListLoadConfig}
	 * @return a {@link GQLListLoadResult}
	 */
	public GQLListLoadResult getAll(Class<?> modelClass, GQLListLoadConfig listLoadConfig) {
		final List<?> all = database.get(modelClass);
		final Stream<?> stream = all.stream();
		if (listLoadConfig.getOffset() > 0) {
			stream.skip(listLoadConfig.getOffset());
		}
		if (listLoadConfig.getLimit() > 0) {
			stream.limit(listLoadConfig.getLimit());
		}
		if (!listLoadConfig.getOrderBy().isEmpty()) {
			listLoadConfig.getOrderBy().stream().forEach(orderBy -> stream.sorted(new Comparator<Object>() {
				@SuppressWarnings({"rawtypes", "unchecked"})
				@Override
				public int compare(Object o1, Object o2) {
					final Object prop1 = PropertyUtils.getPropertyValue(o1, orderBy.getField());
					final Object prop2 = PropertyUtils.getPropertyValue(o2, orderBy.getField());
					int comparison;
					if (prop1 instanceof Comparable) {
						comparison = prop1 == null ? prop2 == null ? 0 : -1 : ((Comparable) prop1).compareTo(prop2);
					} else {
						// We don't care about testing non comparable types in
						// tests
						comparison = 0;
					}
					return (GQLOrderByDirectionEnum.DESC.equals(orderBy.getDirection()) ? -1 : 1) * comparison;
				}
			}));
		}
		final GQLListLoadResult result = new GQLListLoadResult();
		result.setData(stream.collect(Collectors.toList()));
		if (listLoadConfig.isPaged()) {
			result.setPaging(new GQLPaging(listLoadConfig.getOffset(), listLoadConfig.getLimit(), all.size()));
		}
		if (listLoadConfig.isOrdered()) {
			result.setOrderBy(listLoadConfig.getOrderBy());
		}
		return result;
	}

	/**
	 * Save model
	 *
	 * @param model
	 *            the model to be saved
	 */
	@SuppressWarnings({"rawtypes", "unchecked"})
	public void save(Object model) {
		final List<?> all = database.get(model.getClass());
		Assert.notNull(all, "Unkonwn model class " + model.getClass().getName());
		final String modelId = ((AbstractModel) model).getId();
		if (StringUtils.isEmpty(modelId)) {
			final Optional<Integer> maxId = all.stream().map(item -> Integer.valueOf(((AbstractModel) item).getId()))
					.max(Comparator.naturalOrder());
			((AbstractModel) model).setId(maxId.isPresent() ? Integer.valueOf(maxId.get() + 1).toString() : "0");
			((List) all).add(model);
		} else {
			Assert.isTrue(all.contains(model), "Model to be updated (id != null) does not exist yet.");
			// nothing do do then ..
		}
	}

	/**
	 * Delete model by ID
	 *
	 * @param modelClass
	 *            the model class
	 * @param id
	 *            the ID of the model
	 */
	public void delete(Class<?> modelClass, String id) {
		final Object model = getById(modelClass, id);
		if (model != null) {
			database.get(modelClass).remove(model);
		}
	}

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// GETTERS / SETTERS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

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
