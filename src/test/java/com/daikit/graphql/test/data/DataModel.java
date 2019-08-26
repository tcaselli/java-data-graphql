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

import com.daikit.graphql.data.input.GQLListLoadConfig;
import com.daikit.graphql.data.output.GQLListLoadResult;
import com.daikit.graphql.data.output.GQLPaging;
import com.daikit.graphql.enums.GQLOrderByDirectionEnum;
import com.daikit.graphql.utils.GQLPropertyUtils;

/**
 * A data entity for tests
 *
 * @author Thibaut Caselli
 */
public class DataModel {

	private final List<Entity1> entity1s = new ArrayList<>();
	private final List<Entity2> entity2s = new ArrayList<>();
	private final List<Entity3> entity3s = new ArrayList<>();
	private final List<Entity4> entity4s = new ArrayList<>();

	private final Map<Class<?>, List<? extends AbstractEntity>> database = new HashMap<>();

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

		database.put(Entity1.class, entity1s);
		database.put(Entity2.class, entity2s);
		database.put(Entity3.class, entity3s);
		database.put(Entity4.class, entity4s);

		final File file = new File(getClass().getClassLoader().getResource("data/file.txt").getFile());
		final byte[] fileBytes = IOUtils.toByteArray(new FileInputStream(file));

		for (int i = 0; i < 5; i++) {
			final Entity1 entity = new Entity1();
			entity.setId(Integer.valueOf(i).toString());
			entity.setIntAttr(i);
			entity.setLongAttr(Integer.valueOf(i).longValue());
			entity.setDoubleAttr(Integer.valueOf(i).doubleValue());
			entity.setStringAttr("stringAttr_" + i);
			entity.setBooleanAttr(true);
			entity.setBigIntAttr(BigInteger.valueOf(Integer.valueOf(i).longValue()));
			entity.setBigDecimalAttr(BigDecimal.valueOf(Integer.valueOf(i).doubleValue()));
			entity.setBytesAttr(fileBytes);
			entity.setShortAttr(Integer.valueOf(i).shortValue());
			entity.setCharAttr(Integer.valueOf(i).toString().charAt(0));
			entity.setDateAttr(new Date());
			entity.setFileAttr(file);
			entity.setLocalDateAttr(LocalDate.now());
			entity.setLocalDateTimeAttr(LocalDateTime.now());
			entity.setEnumAttr(Enum1.VAL2);
			entity1s.add(entity);
		}

		for (int i = 0; i < 5; i++) {
			final Entity2 entity = new Entity2();
			entity.setId(Integer.valueOf(i).toString());
			entity2s.add(entity);
		}

		for (int i = 0; i < 5; i++) {
			final Entity3 entity = new Entity3();
			entity.setId(Integer.valueOf(i).toString());
			entity3s.add(entity);
		}

		for (int i = 0; i < 5; i++) {
			final Entity4 entity = new Entity4();
			entity.setId(Integer.valueOf(i).toString());
			entity4s.add(entity);
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
			final Entity1 entity1 = entity1s.get(i);
			final Entity2 entity2 = entity2s.get(i);

			entity1.setEntity2(entity2);
			entity2.getEntity1s().add(entity1);

			if (i == 0) {
				entity1.getEntity3s().addAll(entity3s);
				entity3s.forEach(entity3 -> entity3.setEntity1(entity1));
			}

			entity1.getEntity4s().addAll(entity4s);
			entity4s.forEach(entity4 -> entity4.getEntity1s().add(entity1));

			entity1.setEmbeddedData1(embeddedData1s.get(i));

			entity1.getEmbeddedData1s().addAll(embeddedData1s);
		}
	}

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// PUBLIC METHODS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	/**
	 * Get entity by ID
	 *
	 * @param entityClass
	 *            the entity class
	 * @param id
	 *            the ID
	 * @return an {@link Optional} entity
	 */
	public Optional<? extends AbstractEntity> getById(Class<?> entityClass, String id) {
		return database.get(entityClass).stream().filter(item -> item.getId().equals(id)).findFirst();
	}

	/**
	 * Get all entitys
	 *
	 * @param entityClass
	 *            the entity class
	 * @param listLoadConfig
	 *            the {@link GQLListLoadConfig}
	 * @return a {@link GQLListLoadResult}
	 */
	public GQLListLoadResult getAll(Class<?> entityClass, GQLListLoadConfig listLoadConfig) {
		final List<?> all = database.get(entityClass);
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
					final Object prop1 = GQLPropertyUtils.getPropertyValue(o1, orderBy.getField());
					final Object prop2 = GQLPropertyUtils.getPropertyValue(o2, orderBy.getField());
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
	 * Save entity
	 *
	 * @param entity
	 *            the entity to be saved
	 */
	@SuppressWarnings({"rawtypes", "unchecked"})
	public void save(Object entity) {
		final List<?> all = database.get(entity.getClass());
		Assert.notNull(all, "Unkonwn entity class " + entity.getClass().getName());
		final String entityId = ((AbstractEntity) entity).getId();
		if (StringUtils.isEmpty(entityId)) {
			final Optional<Integer> maxId = all.stream().map(item -> Integer.valueOf(((AbstractEntity) item).getId()))
					.max(Comparator.naturalOrder());
			((AbstractEntity) entity).setId(maxId.isPresent() ? Integer.valueOf(maxId.get() + 1).toString() : "0");
			((List) all).add(entity);
		} else {
			Assert.isTrue(all.contains(entity), "Entity to be updated (id != null) does not exist yet.");
			// nothing do do then ..
		}
	}

	/**
	 * Delete entity by ID
	 *
	 * @param entityClass
	 *            the entity class
	 * @param id
	 *            the ID of the entity
	 */
	public void delete(Class<?> entityClass, String id) {
		final Optional<?> optionalEntity = getById(entityClass, id);
		if (optionalEntity.isPresent()) {
			database.get(entityClass).remove(optionalEntity.get());
		}
	}

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// GETTERS / SETTERS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	/**
	 * @return the entity1s
	 */
	public List<Entity1> getEntity1s() {
		return entity1s;
	}

	/**
	 * @return the entity2s
	 */
	public List<Entity2> getEntity2s() {
		return entity2s;
	}

	/**
	 * @return the entity3s
	 */
	public List<Entity3> getEntity3s() {
		return entity3s;
	}

	/**
	 * @return the entity4s
	 */
	public List<Entity4> getEntity4s() {
		return entity4s;
	}

}
