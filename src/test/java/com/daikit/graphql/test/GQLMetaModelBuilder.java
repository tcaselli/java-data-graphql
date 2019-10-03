package com.daikit.graphql.test;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.daikit.graphql.custommethod.GQLCustomMethod1Arg;
import com.daikit.graphql.custommethod.GQLCustomMethod2Arg;
import com.daikit.graphql.custommethod.GQLCustomMethod5Arg;
import com.daikit.graphql.custommethod.IGQLAbstractCustomMethod;
import com.daikit.graphql.dynamicattribute.GQLDynamicAttributeGetter;
import com.daikit.graphql.dynamicattribute.GQLDynamicAttributeSetter;
import com.daikit.graphql.dynamicattribute.IGQLAbstractDynamicAttribute;
import com.daikit.graphql.enums.GQLScalarTypeEnum;
import com.daikit.graphql.meta.GQLMetaModel;
import com.daikit.graphql.meta.attribute.GQLAttributeEntityMetaData;
import com.daikit.graphql.meta.attribute.GQLAttributeEnumMetaData;
import com.daikit.graphql.meta.attribute.GQLAttributeListEntityMetaData;
import com.daikit.graphql.meta.attribute.GQLAttributeListEnumMetaData;
import com.daikit.graphql.meta.attribute.GQLAttributeListScalarMetaData;
import com.daikit.graphql.meta.attribute.GQLAttributeScalarMetaData;
import com.daikit.graphql.meta.entity.GQLEntityMetaData;
import com.daikit.graphql.meta.entity.GQLEnumMetaData;
import com.daikit.graphql.test.data.AbstractEntity;
import com.daikit.graphql.test.data.EmbeddedData1;
import com.daikit.graphql.test.data.EmbeddedData2;
import com.daikit.graphql.test.data.EmbeddedData3;
import com.daikit.graphql.test.data.Entity1;
import com.daikit.graphql.test.data.Entity2;
import com.daikit.graphql.test.data.Entity3;
import com.daikit.graphql.test.data.Entity4;
import com.daikit.graphql.test.data.Entity5;
import com.daikit.graphql.test.data.Entity6;
import com.daikit.graphql.test.data.Entity7;
import com.daikit.graphql.test.data.Entity8;
import com.daikit.graphql.test.data.Entity9;
import com.daikit.graphql.test.data.Enum1;

/**
 * Meta data for building test schema
 *
 * @author Thibaut Caselli
 */
public class GQLMetaModelBuilder {

	/**
	 * Build the test GraphQL data model
	 *
	 * @param automatic
	 *            whether to build the test GraphQL meta model automatically
	 *            from classes
	 * @return the built {@link GQLMetaModelBuilder}
	 */
	public GQLMetaModel build(boolean automatic) {
		final GQLMetaModel metaModel = new GQLMetaModel();
		if (automatic) {
			final Collection<Class<?>> entityClasses = Arrays.asList(Entity1.class, Entity2.class, Entity3.class,
					Entity4.class, Entity5.class, Entity6.class, Entity7.class, Entity8.class, Entity9.class);
			final Collection<Class<?>> availableEmbeddedEntityClasses = Arrays.asList(EmbeddedData1.class,
					EmbeddedData2.class, EmbeddedData3.class);
			metaModel.buildFromEntityClasses(entityClasses, availableEmbeddedEntityClasses, buildDynamicAttributes(),
					buildCustomMethods());
		} else {
			final Collection<GQLEntityMetaData> entityMetaDatas = Arrays.asList(buildEntity1(), buildEntity2(),
					buildEntity3(), buildEntity4(), buildEntity5(), buildEntity6(), buildEntity7(), buildEntity8(),
					buildEntity9(), buildEmbeddedData1(), buildEmbeddedData2(), buildEmbeddedData3());
			final Collection<GQLEnumMetaData> enumMetaDatas = Arrays.asList(buildEnumMetaData());
			metaModel.buildFromMetaDatas(enumMetaDatas, entityMetaDatas, buildDynamicAttributes(),
					buildCustomMethods());
		}
		return metaModel;
	}

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// PRIVATE UTILS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	private GQLEntityMetaData buildEntity1() {
		final GQLEntityMetaData entity = new GQLEntityMetaData(Entity1.class.getSimpleName(), Entity1.class,
				AbstractEntity.class);
		// Fields from class
		entity.addAttribute(new GQLAttributeScalarMetaData("intAttr", GQLScalarTypeEnum.INT));
		entity.addAttribute(new GQLAttributeScalarMetaData("longAttr", GQLScalarTypeEnum.LONG));
		entity.addAttribute(new GQLAttributeScalarMetaData("doubleAttr", GQLScalarTypeEnum.FLOAT));
		entity.addAttribute(new GQLAttributeScalarMetaData("stringAttr", GQLScalarTypeEnum.STRING));
		entity.addAttribute(new GQLAttributeScalarMetaData("booleanAttr", GQLScalarTypeEnum.BOOLEAN));
		entity.addAttribute(new GQLAttributeScalarMetaData("bigIntAttr", GQLScalarTypeEnum.BIG_INTEGER));
		entity.addAttribute(new GQLAttributeScalarMetaData("bigDecimalAttr", GQLScalarTypeEnum.BIG_DECIMAL));
		entity.addAttribute(new GQLAttributeScalarMetaData("bytesAttr", GQLScalarTypeEnum.BYTE));
		entity.addAttribute(new GQLAttributeScalarMetaData("shortAttr", GQLScalarTypeEnum.SHORT));
		entity.addAttribute(new GQLAttributeScalarMetaData("charAttr", GQLScalarTypeEnum.CHAR));
		entity.addAttribute(new GQLAttributeScalarMetaData("dateAttr", GQLScalarTypeEnum.DATE));
		entity.addAttribute(new GQLAttributeScalarMetaData("fileAttr", GQLScalarTypeEnum.FILE));
		entity.addAttribute(new GQLAttributeScalarMetaData("localDateAttr", GQLScalarTypeEnum.LOCAL_DATE));
		entity.addAttribute(new GQLAttributeScalarMetaData("localDateTimeAttr", GQLScalarTypeEnum.LOCAL_DATE_TIME));
		entity.addAttribute(new GQLAttributeScalarMetaData("instantAttr", GQLScalarTypeEnum.INSTANT));
		entity.addAttribute(new GQLAttributeListScalarMetaData("stringList", GQLScalarTypeEnum.STRING));
		entity.addAttribute(new GQLAttributeListScalarMetaData("stringSet", GQLScalarTypeEnum.STRING));
		entity.addAttribute(new GQLAttributeEnumMetaData("enumAttr", Enum1.class));
		entity.addAttribute(new GQLAttributeListEnumMetaData("enumList", Enum1.class));
		entity.addAttribute(new GQLAttributeListEnumMetaData("enumSet", Enum1.class));
		entity.addAttribute(new GQLAttributeEntityMetaData("entity2", Entity2.class));
		entity.addAttribute(new GQLAttributeListEntityMetaData("entity3s", Entity3.class));
		entity.addAttribute(new GQLAttributeListEntityMetaData("entity4s", Entity4.class));
		entity.addAttribute(new GQLAttributeEntityMetaData("embeddedData1", EmbeddedData1.class).setEmbedded(true));
		entity.addAttribute(
				new GQLAttributeListEntityMetaData("embeddedData1s", EmbeddedData1.class).setEmbedded(true));
		// Fields from super class
		entity.addAttribute(new GQLAttributeScalarMetaData("id", GQLScalarTypeEnum.ID).setNullableForUpdate(false));
		return entity;
	}

	private GQLEntityMetaData buildEntity2() {
		final GQLEntityMetaData entity = new GQLEntityMetaData(Entity2.class.getSimpleName(), Entity2.class,
				AbstractEntity.class);
		// Fields from class
		entity.addAttribute(new GQLAttributeListEntityMetaData("entity1s", Entity1.class));
		// Fields from super class
		entity.addAttribute(new GQLAttributeScalarMetaData("id", GQLScalarTypeEnum.ID).setNullableForUpdate(false));
		return entity;
	}

	private GQLEntityMetaData buildEntity3() {
		final GQLEntityMetaData entity = new GQLEntityMetaData(Entity3.class.getSimpleName(), Entity3.class,
				AbstractEntity.class);
		// Fields from class
		entity.addAttribute(new GQLAttributeEntityMetaData("entity1", Entity1.class));
		// Fields from super class
		entity.addAttribute(new GQLAttributeScalarMetaData("id", GQLScalarTypeEnum.ID).setNullableForUpdate(false));
		return entity;
	}

	private GQLEntityMetaData buildEntity4() {
		final GQLEntityMetaData entity = new GQLEntityMetaData(Entity4.class.getSimpleName(), Entity4.class,
				AbstractEntity.class);
		// Fields from class
		entity.addAttribute(new GQLAttributeListEntityMetaData("entity1s", Entity1.class));
		// Fields from super class
		entity.addAttribute(new GQLAttributeScalarMetaData("id", GQLScalarTypeEnum.ID).setNullableForUpdate(false));
		return entity;
	}

	private GQLEntityMetaData buildEntity5() {
		final GQLEntityMetaData entity = new GQLEntityMetaData(Entity5.class.getSimpleName(), Entity5.class,
				AbstractEntity.class);
		// Fields from class
		entity.addAttribute(new GQLAttributeScalarMetaData("intAttr", GQLScalarTypeEnum.INT));
		entity.addAttribute(new GQLAttributeScalarMetaData("stringAttr", GQLScalarTypeEnum.STRING));
		// Fields from super class
		entity.addAttribute(new GQLAttributeScalarMetaData("id", GQLScalarTypeEnum.ID).setNullableForUpdate(false));
		return entity;
	}

	private GQLEntityMetaData buildEntity6() {
		final GQLEntityMetaData entity = new GQLEntityMetaData(Entity6.class.getSimpleName(), Entity6.class,
				AbstractEntity.class);
		// Fields from class
		entity.addAttribute(new GQLAttributeScalarMetaData("attr1", GQLScalarTypeEnum.STRING).setReadable(false));
		entity.addAttribute(new GQLAttributeScalarMetaData("attr2", GQLScalarTypeEnum.STRING).setSaveable(false));
		entity.addAttribute(new GQLAttributeScalarMetaData("attr3", GQLScalarTypeEnum.STRING).setNullable(false));
		entity.addAttribute(
				new GQLAttributeScalarMetaData("attr4", GQLScalarTypeEnum.STRING).setNullableForUpdate(false));
		entity.addAttribute(
				new GQLAttributeScalarMetaData("attr5", GQLScalarTypeEnum.STRING).setNullableForCreate(false));
		entity.addAttribute(new GQLAttributeScalarMetaData("attr6", GQLScalarTypeEnum.STRING).setFilterable(false));
		// Fields from super class
		entity.addAttribute(new GQLAttributeScalarMetaData("id", GQLScalarTypeEnum.ID).setNullableForUpdate(false));
		return entity;
	}

	private GQLEntityMetaData buildEntity7() {
		final GQLEntityMetaData entity = new GQLEntityMetaData(Entity7.class.getSimpleName(), Entity7.class,
				AbstractEntity.class).setSaveable(false);
		// Fields from class
		// Fields from super class
		entity.addAttribute(new GQLAttributeScalarMetaData("id", GQLScalarTypeEnum.ID).setNullableForUpdate(false));
		return entity;
	}

	private GQLEntityMetaData buildEntity8() {
		final GQLEntityMetaData entity = new GQLEntityMetaData(Entity8.class.getSimpleName(), Entity8.class,
				AbstractEntity.class).setDeletable(false);
		// Fields from class
		// Fields from super class
		entity.addAttribute(new GQLAttributeScalarMetaData("id", GQLScalarTypeEnum.ID).setNullableForUpdate(false));
		return entity;
	}

	private GQLEntityMetaData buildEntity9() {
		final GQLEntityMetaData entity = new GQLEntityMetaData(Entity9.class.getSimpleName(), Entity9.class,
				AbstractEntity.class).setReadable(false);
		// Fields from class
		// Fields from super class
		entity.addAttribute(new GQLAttributeScalarMetaData("id", GQLScalarTypeEnum.ID).setNullableForUpdate(false));
		return entity;
	}

	private GQLEntityMetaData buildEmbeddedData1() {
		final GQLEntityMetaData entity = new GQLEntityMetaData(EmbeddedData1.class.getSimpleName(), EmbeddedData1.class)
				.setEmbedded(true);

		entity.addAttribute(new GQLAttributeScalarMetaData("intAttr", GQLScalarTypeEnum.INT));
		entity.addAttribute(new GQLAttributeScalarMetaData("longAttr", GQLScalarTypeEnum.LONG));
		entity.addAttribute(new GQLAttributeScalarMetaData("doubleAttr", GQLScalarTypeEnum.FLOAT));
		entity.addAttribute(new GQLAttributeScalarMetaData("stringAttr", GQLScalarTypeEnum.STRING));
		entity.addAttribute(new GQLAttributeScalarMetaData("booleanAttr", GQLScalarTypeEnum.BOOLEAN));
		entity.addAttribute(new GQLAttributeScalarMetaData("bigIntAttr", GQLScalarTypeEnum.BIG_INTEGER));
		entity.addAttribute(new GQLAttributeScalarMetaData("bigDecimalAttr", GQLScalarTypeEnum.BIG_DECIMAL));
		entity.addAttribute(new GQLAttributeScalarMetaData("bytesAttr", GQLScalarTypeEnum.BYTE));

		entity.addAttribute(new GQLAttributeScalarMetaData("shortAttr", GQLScalarTypeEnum.SHORT));

		entity.addAttribute(new GQLAttributeScalarMetaData("charAttr", GQLScalarTypeEnum.CHAR));
		entity.addAttribute(new GQLAttributeScalarMetaData("dateAttr", GQLScalarTypeEnum.DATE));
		entity.addAttribute(new GQLAttributeScalarMetaData("fileAttr", GQLScalarTypeEnum.FILE));
		entity.addAttribute(new GQLAttributeScalarMetaData("localDateAttr", GQLScalarTypeEnum.LOCAL_DATE));
		entity.addAttribute(new GQLAttributeScalarMetaData("localDateTimeAttr", GQLScalarTypeEnum.LOCAL_DATE_TIME));
		entity.addAttribute(new GQLAttributeScalarMetaData("instantAttr", GQLScalarTypeEnum.INSTANT));
		entity.addAttribute(new GQLAttributeListScalarMetaData("stringList", GQLScalarTypeEnum.STRING));
		entity.addAttribute(new GQLAttributeListScalarMetaData("stringSet", GQLScalarTypeEnum.STRING));
		entity.addAttribute(new GQLAttributeEnumMetaData("enumAttr", Enum1.class));
		entity.addAttribute(new GQLAttributeListEnumMetaData("enumList", Enum1.class));
		entity.addAttribute(new GQLAttributeListEnumMetaData("enumSet", Enum1.class));
		entity.addAttribute(new GQLAttributeEntityMetaData("data2", EmbeddedData2.class).setEmbedded(true));
		entity.addAttribute(new GQLAttributeListEntityMetaData("data3s", EmbeddedData3.class).setEmbedded(true));

		return entity;
	}

	private GQLEntityMetaData buildEmbeddedData2() {
		final GQLEntityMetaData entity = new GQLEntityMetaData(EmbeddedData2.class.getSimpleName(), EmbeddedData2.class)
				.setEmbedded(true);
		entity.addAttribute(new GQLAttributeScalarMetaData("intAttr", GQLScalarTypeEnum.INT));
		return entity;
	}

	private GQLEntityMetaData buildEmbeddedData3() {
		final GQLEntityMetaData entity = new GQLEntityMetaData(EmbeddedData3.class.getSimpleName(), EmbeddedData3.class)
				.setEmbedded(true);
		entity.addAttribute(new GQLAttributeScalarMetaData("intAttr", GQLScalarTypeEnum.INT));
		return entity;
	}

	private GQLEnumMetaData buildEnumMetaData() {
		return new GQLEnumMetaData(Enum1.class.getSimpleName(), Enum1.class);
	}

	private List<IGQLAbstractDynamicAttribute<?>> buildDynamicAttributes() {
		return Stream.of(new GQLDynamicAttributeGetter<Entity1, String>("dynamicAttribute1") {
			@Override
			public String getValue(final Entity1 source) {
				return "dynamicValue" + source.getId();
			}
		}, new GQLDynamicAttributeSetter<Entity1, String>("dynamicAttribute2") {
			@Override
			public void setValue(final Entity1 source, final String valueToSet) {
				source.setStringAttr(valueToSet);
			}
		}).collect(Collectors.toList());
	}

	private List<IGQLAbstractCustomMethod<?>> buildCustomMethods() {
		return Stream.of(new GQLCustomMethod1Arg<Entity1, String>("customMethodQuery1", false, "arg1") {
			@Override
			public Entity1 apply(final String arg1) {
				final Entity1 result = new Entity1();
				result.setStringAttr(arg1);
				final EmbeddedData1 embeddedData1 = new EmbeddedData1();
				embeddedData1.setStringAttr(arg1);
				result.setEmbeddedData1(embeddedData1);
				return result;
			}
		}, new GQLCustomMethod2Arg<Entity1, String, EmbeddedData1>("customMethodQuery2", false, "arg1", "arg2") {
			@Override
			public Entity1 apply(final String arg1, final EmbeddedData1 arg2) {
				final Entity1 result = new Entity1();
				result.setIntAttr(5);
				result.setStringAttr(arg1);
				result.setEmbeddedData1(arg2);
				return result;
			}
		}, new GQLCustomMethod5Arg<Entity1, Enum1, List<String>, List<Enum1>, List<EmbeddedData1>, String>(
				"customMethodQuery3", false, "arg1", "arg2", "arg3", "arg4", "arg5") {
			@Override
			public Entity1 apply(final Enum1 arg1, final List<String> arg2, final List<Enum1> arg3,
					final List<EmbeddedData1> arg4, final String arg5) {
				final Entity1 result = new Entity1();
				result.setEnumAttr(arg1);
				result.setStringList(arg2);
				result.setEnumList(arg3);
				result.setEmbeddedData1s(arg4);
				if (arg5 == null) {
					result.setStringAttr("NULLVALUE");
				}
				return result;
			}
		}, new GQLCustomMethod1Arg<Entity1, String>("customMethodMutation1", true, "arg1") {
			@Override
			public Entity1 apply(final String arg1) {
				final Entity1 result = new Entity1();
				result.setStringAttr(arg1);
				return result;
			}
		}).collect(Collectors.toList());
	}
}
