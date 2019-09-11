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
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.Assert;

import com.daikit.graphql.data.input.GQLFilterEntry;
import com.daikit.graphql.data.input.GQLListLoadConfig;
import com.daikit.graphql.data.output.GQLListLoadResult;
import com.daikit.graphql.data.output.GQLOrderByEntry;
import com.daikit.graphql.data.output.GQLPaging;
import com.daikit.graphql.enums.GQLOrderByDirectionEnum;

/**
 * A data entity for tests
 *
 * @author Thibaut Caselli
 */
public class DataModel {

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
		Stream.of(Entity1.class, Entity2.class, Entity3.class, Entity4.class, Entity6.class, Entity5.class,
				Entity7.class, Entity8.class).forEach(entityClass -> database.put(entityClass, new ArrayList<>()));

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
			registerEntity(entity);
		}

		for (int i = 0; i < 5; i++) {
			final Entity2 entity = new Entity2();
			entity.setId(Integer.valueOf(i).toString());
			registerEntity(entity);
		}

		for (int i = 0; i < 5; i++) {
			final Entity3 entity = new Entity3();
			entity.setId(Integer.valueOf(i).toString());
			registerEntity(entity);
		}

		for (int i = 0; i < 5; i++) {
			final Entity4 entity = new Entity4();
			entity.setId(Integer.valueOf(i).toString());
			registerEntity(entity);
		}

		for (int i = 0; i < 100; i++) {
			final Entity5 entity = new Entity5();
			entity.setId(Integer.valueOf(i).toString());
			entity.setIntAttr(i % 10);
			entity.setStringAttr("value-" + Integer.valueOf((i + 1) * 3 % 8).toString());
			registerEntity(entity);
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
			final Entity1 entity1 = getAll(Entity1.class).get(i);
			final Entity2 entity2 = getAll(Entity2.class).get(i);

			entity1.setEntity2(entity2);
			entity2.getEntity1s().add(entity1);

			if (i == 0) {
				entity1.getEntity3s().addAll(getAll(Entity3.class));
				getAll(Entity3.class).forEach(entity3 -> entity3.setEntity1(entity1));
			}

			entity1.getEntity4s().addAll(getAll(Entity4.class));
			getAll(Entity4.class).forEach(entity4 -> entity4.getEntity1s().add(entity1));

			entity1.setEmbeddedData1(embeddedData1s.get(i));

			entity1.getEmbeddedData1s().addAll(embeddedData1s);
		}
	}

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// PRIVATE METHODS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	@SuppressWarnings("unchecked")
	private <T extends AbstractEntity> void registerEntity(T entity) {
		getAll((Class<T>) entity.getClass()).add(entity);
	}

	@SuppressWarnings("unchecked")
	private <T extends AbstractEntity> List<T> getAll(Class<T> entityClass) {
		return (List<T>) database.get(entityClass);
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
	 * Get all entities
	 *
	 * @param entityClass
	 *            the entity class
	 * @param listLoadConfig
	 *            the {@link GQLListLoadConfig}
	 * @return a {@link GQLListLoadResult}
	 */
	public GQLListLoadResult getAll(Class<?> entityClass, GQLListLoadConfig listLoadConfig) {
		final List<?> all = database.get(entityClass);
		Stream<?> stream = all.stream();

		// Handle paging
		if (listLoadConfig.getOffset() > 0) {
			stream = stream.skip(listLoadConfig.getOffset());
		}
		if (listLoadConfig.getLimit() > 0) {
			stream = stream.limit(listLoadConfig.getLimit());
		}

		// Handle sorting
		if (!listLoadConfig.getOrderBy().isEmpty()) {
			final List<Comparator<Object>> comparators = listLoadConfig.getOrderBy().stream()
					.map(orderBy -> createEntityComparator(orderBy)).collect(Collectors.toList());
			stream = stream.sorted(new Comparator<Object>() {
				@Override
				public int compare(Object o1, Object o2) {
					int comparison = 0;
					final Iterator<Comparator<Object>> it = comparators.iterator();
					while (it.hasNext() && comparison == 0) {
						comparison = it.next().compare(o1, o2);
					}
					return comparison;
				}
			});
		}

		//
		if (!listLoadConfig.getFilters().isEmpty()) {
			for (final GQLFilterEntry filterEntry : listLoadConfig.getFilters()) {
				stream = stream.filter(entity -> {
					try {
						return isMatching(entity, filterEntry);
					} catch (final IllegalAccessException e) {
						throw new RuntimeException(e);
					}
				});
			}
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

	private Comparator<Object> createEntityComparator(GQLOrderByEntry orderBy) {
		return new Comparator<Object>() {
			@SuppressWarnings({"rawtypes", "unchecked"})
			@Override
			public int compare(Object o1, Object o2) {
				Object prop1;
				Object prop2;
				try {
					prop1 = FieldUtils.readField(o1, orderBy.getField(), true);
					prop2 = FieldUtils.readField(o2, orderBy.getField(), true);
				} catch (final IllegalAccessException e) {
					throw new RuntimeException(e);
				}
				int comparison;
				if (prop1 instanceof Comparable) {
					comparison = prop1 == null ? prop2 == null ? 0 : -1 : ((Comparable) prop1).compareTo(prop2);
				} else {
					// We don't care about testing non comparable types
					// here, let's keep it simple for the demo
					comparison = 0;
				}
				return (GQLOrderByDirectionEnum.DESC.equals(orderBy.getDirection()) ? -1 : 1) * comparison;
			}
		};
	}

	@SuppressWarnings("unchecked")
	private boolean isMatching(Object entity, GQLFilterEntry filterEntry) throws IllegalAccessException {
		final Class<?> propertyType = FieldUtils.getField(entity.getClass(), filterEntry.getFieldName(), true)
				.getType();
		boolean matching = true;
		if (String.class.isAssignableFrom(propertyType)) {
			final String propertyValue = (String) FieldUtils.readField(entity, filterEntry.getFieldName(), true);
			switch (filterEntry.getOperator()) {
				case EQUAL :
					matching = Objects.equals(filterEntry.getValue(), propertyValue);
					break;
				case NOT_EQUAL :
					matching = !Objects.equals(filterEntry.getValue(), propertyValue);
					break;
				case IN :
					matching = ((Collection<String>) filterEntry.getValue()).contains(propertyValue);
					break;
				case NOT_IN :
					matching = !((Collection<String>) filterEntry.getValue()).contains(propertyValue);
					break;
				case NULL :
					matching = propertyValue == null;
					break;
				case NOT_NULL :
					matching = propertyValue != null;
					break;
				case EMPTY :
					matching = StringUtils.isEmpty(propertyValue);
					break;
				case NOT_EMPTY :
					matching = StringUtils.isNotEmpty(propertyValue);
					break;
				case STARTS_WITH :
					matching = propertyValue != null && propertyValue.startsWith((String) filterEntry.getValue());
					break;
				case ENDS_WITH :
					matching = propertyValue != null && propertyValue.endsWith((String) filterEntry.getValue());
					break;
				case CONTAINS :
					matching = propertyValue != null && propertyValue.indexOf((String) filterEntry.getValue()) > -1;
					break;
				case LIKE :
					// TODO
					break;
				default :
					// Cannot happen
					break;
			}
		} else if (Integer.class.isAssignableFrom(propertyType) || int.class.isAssignableFrom(propertyType)) {
			final Integer propertyValue = (Integer) FieldUtils.readField(entity, filterEntry.getFieldName());
			switch (filterEntry.getOperator()) {
				case EQUAL :
					matching = propertyValue.intValue() == ((Integer) filterEntry.getValue()).intValue();
					break;
				case NOT_EQUAL :
					matching = propertyValue.intValue() != ((Integer) filterEntry.getValue()).intValue();
					break;
				case GREATER_EQUAL :
					matching = propertyValue.intValue() >= ((Integer) filterEntry.getValue()).intValue();
					break;
				case GREATER_THAN :
					matching = propertyValue.intValue() > ((Integer) filterEntry.getValue()).intValue();
					break;
				case LOWER_EQUAL :
					matching = propertyValue.intValue() <= ((Integer) filterEntry.getValue()).intValue();
					break;
				case LOWER_THAN :
					matching = propertyValue.intValue() < ((Integer) filterEntry.getValue()).intValue();
					break;
				case IN :
					matching = ((Collection<Integer>) filterEntry.getValue()).contains(propertyValue);
					break;
				case NOT_IN :
					matching = !((Collection<Integer>) filterEntry.getValue()).contains(propertyValue);
					break;
				case NULL :
					matching = propertyValue == null;
					break;
				case NOT_NULL :
					matching = propertyValue != null;
					break;
				default :
					// Cannot happen
					break;
			}
		}
		return matching;
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
		Assert.assertNotNull("Unkonwn entity class " + entity.getClass().getName(), all);
		final String entityId = ((AbstractEntity) entity).getId();
		if (StringUtils.isEmpty(entityId)) {
			final Optional<Integer> maxId = all.stream().map(item -> Integer.valueOf(((AbstractEntity) item).getId()))
					.max(Comparator.naturalOrder());
			((AbstractEntity) entity).setId(maxId.isPresent() ? Integer.valueOf(maxId.get() + 1).toString() : "0");
			((List) all).add(entity);
		} else {
			Assert.assertTrue("Entity to be updated (id != null) does not exist yet.", all.contains(entity));
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

}
