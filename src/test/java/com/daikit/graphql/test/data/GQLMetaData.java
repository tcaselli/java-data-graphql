package com.daikit.graphql.test.data;

import java.util.ArrayList;
import java.util.Collection;

import com.daikit.graphql.custommethod.abs.GQLCustomMethod1Arg;
import com.daikit.graphql.custommethod.abs.GQLCustomMethod2Arg;
import com.daikit.graphql.dynamicattribute.IGQLDynamicAttributeGetter;
import com.daikit.graphql.dynamicattribute.IGQLDynamicAttributeSetter;
import com.daikit.graphql.dynamicattribute.abs.GQLDynamicAttributeGetter;
import com.daikit.graphql.dynamicattribute.abs.GQLDynamicAttributeSetter;
import com.daikit.graphql.enums.GQLScalarTypeEnum;
import com.daikit.graphql.meta.GQLMetaDataModel;
import com.daikit.graphql.meta.GQLMetaDataModelBuilder;
import com.daikit.graphql.meta.attribute.GQLAttributeEmbeddedEntityMetaData;
import com.daikit.graphql.meta.attribute.GQLAttributeEntityMetaData;
import com.daikit.graphql.meta.attribute.GQLAttributeEnumMetaData;
import com.daikit.graphql.meta.attribute.GQLAttributeListEmbeddedEntityMetaData;
import com.daikit.graphql.meta.attribute.GQLAttributeListEntityMetaData;
import com.daikit.graphql.meta.attribute.GQLAttributeListEnumMetaData;
import com.daikit.graphql.meta.attribute.GQLAttributeListScalarMetaData;
import com.daikit.graphql.meta.attribute.GQLAttributeScalarMetaData;
import com.daikit.graphql.meta.custommethod.GQLAbstractMethodMetaData;
import com.daikit.graphql.meta.custommethod.GQLMethodArgumentEmbeddedEntityMetaData;
import com.daikit.graphql.meta.custommethod.GQLMethodArgumentScalarMetaData;
import com.daikit.graphql.meta.custommethod.GQLMethodEntityMetaData;
import com.daikit.graphql.meta.entity.GQLAbstractEntityMetaData;
import com.daikit.graphql.meta.entity.GQLEmbeddedEntityMetaData;
import com.daikit.graphql.meta.entity.GQLEntityMetaData;
import com.daikit.graphql.meta.entity.GQLEnumMetaData;

/**
 * Meta data for building test schema
 *
 * @author tcaselli
 */
public class GQLMetaData {

	/**
	 * Build the test GraphQL data meta entity
	 *
	 * @return the built {@link GQLMetaDataModel}
	 */
	public static GQLMetaDataModel buildMetaDataModel() {
		return new GQLTestMetaDataModelBuilder().build();
	}

	private static class GQLTestMetaDataModelBuilder {

		private final Collection<GQLAbstractEntityMetaData> entityMetaDatas = new ArrayList<>();
		private final Collection<GQLEnumMetaData> enumMetaDatas = new ArrayList<>();
		private final Collection<GQLAbstractMethodMetaData> methodMetaDatas = new ArrayList<>();

		protected GQLMetaDataModel build() {
			entityMetaDatas.add(buildEntity1());
			entityMetaDatas.add(buildEntity2());
			entityMetaDatas.add(buildEntity3());
			entityMetaDatas.add(buildEntity4());

			entityMetaDatas.add(buildEmbeddedData1());
			entityMetaDatas.add(buildEmbeddedData2());
			entityMetaDatas.add(buildEmbeddedData3());

			enumMetaDatas.add(buildEnumMetaData());

			methodMetaDatas.add(buildCustomMethodQuery1());
			methodMetaDatas.add(buildCustomMethodQuery2());
			methodMetaDatas.add(buildCustomMethodMutation1());

			return new GQLMetaDataModelBuilder().build(enumMetaDatas, entityMetaDatas, methodMetaDatas);
		}

		private GQLEntityMetaData buildEntity1() {
			final GQLEntityMetaData entity = new GQLEntityMetaData(Entity1.class.getSimpleName(), Entity1.class,
					AbstractEntity.class);

			final GQLAttributeScalarMetaData idAttr = new GQLAttributeScalarMetaData("id", GQLScalarTypeEnum.ID);
			idAttr.setNullable(false);
			entity.addAttribute(idAttr);

			final GQLAttributeScalarMetaData intAttr = new GQLAttributeScalarMetaData("intAttr", GQLScalarTypeEnum.INT);
			intAttr.setNullable(false);
			entity.addAttribute(intAttr);

			final GQLAttributeScalarMetaData longAttr = new GQLAttributeScalarMetaData("longAttr",
					GQLScalarTypeEnum.LONG);
			longAttr.setNullable(false);
			entity.addAttribute(longAttr);

			final GQLAttributeScalarMetaData doubleAttr = new GQLAttributeScalarMetaData("doubleAttr",
					GQLScalarTypeEnum.FLOAT);
			doubleAttr.setNullable(false);
			entity.addAttribute(doubleAttr);

			entity.addAttribute(new GQLAttributeScalarMetaData("stringAttr", GQLScalarTypeEnum.STRING));

			final GQLAttributeScalarMetaData booleanAttr = new GQLAttributeScalarMetaData("booleanAttr",
					GQLScalarTypeEnum.BOOLEAN);
			booleanAttr.setNullable(false);
			entity.addAttribute(booleanAttr);

			entity.addAttribute(new GQLAttributeScalarMetaData("bigIntAttr", GQLScalarTypeEnum.BIG_INTEGER));
			entity.addAttribute(new GQLAttributeScalarMetaData("bigDecimalAttr", GQLScalarTypeEnum.BIG_DECIMAL));
			entity.addAttribute(new GQLAttributeScalarMetaData("bytesAttr", GQLScalarTypeEnum.BYTE));

			final GQLAttributeScalarMetaData shortAttr = new GQLAttributeScalarMetaData("shortAttr",
					GQLScalarTypeEnum.SHORT);
			shortAttr.setNullable(false);
			entity.addAttribute(shortAttr);

			entity.addAttribute(new GQLAttributeScalarMetaData("charAttr", GQLScalarTypeEnum.CHAR));
			entity.addAttribute(new GQLAttributeScalarMetaData("dateAttr", GQLScalarTypeEnum.DATE));
			entity.addAttribute(new GQLAttributeScalarMetaData("fileAttr", GQLScalarTypeEnum.FILE));
			entity.addAttribute(new GQLAttributeScalarMetaData("localDateAttr", GQLScalarTypeEnum.LOCAL_DATE));
			entity.addAttribute(new GQLAttributeScalarMetaData("localDateTimeAttr", GQLScalarTypeEnum.LOCAL_DATE_TIME));
			entity.addAttribute(new GQLAttributeListScalarMetaData("stringList", GQLScalarTypeEnum.STRING));
			entity.addAttribute(new GQLAttributeListScalarMetaData("stringSet", GQLScalarTypeEnum.STRING));
			entity.addAttribute(new GQLAttributeEnumMetaData("enumAttr", Enum1.class));
			entity.addAttribute(new GQLAttributeListEnumMetaData("enumList", Enum1.class));
			entity.addAttribute(new GQLAttributeListEnumMetaData("enumSet", Enum1.class));
			entity.addAttribute(new GQLAttributeEntityMetaData("entity2", Entity2.class));
			entity.addAttribute(new GQLAttributeListEntityMetaData("entity3s", Entity3.class));
			entity.addAttribute(new GQLAttributeListEntityMetaData("entity4s", Entity4.class));
			entity.addAttribute(new GQLAttributeEmbeddedEntityMetaData("embeddedData1", EmbeddedData1.class));
			entity.addAttribute(new GQLAttributeListEmbeddedEntityMetaData("embeddedData1s", EmbeddedData1.class));

			final IGQLDynamicAttributeGetter<Entity1, String> dynamicAttributeGetter = new GQLDynamicAttributeGetter<Entity1, String>(
					"dynamicAttribute1") {
				@Override
				public String getValue(Entity1 source) {
					return "dynamicValue" + source.getId();
				}
			};
			final GQLAttributeScalarMetaData dynamicAttrGetterAttribute = new GQLAttributeScalarMetaData(
					dynamicAttributeGetter.getName(), GQLScalarTypeEnum.STRING);
			dynamicAttrGetterAttribute.setDynamicAttributeGetter(dynamicAttributeGetter);
			entity.addAttribute(dynamicAttrGetterAttribute);

			final IGQLDynamicAttributeSetter<Entity1, String> dynamicAttributeSetter = new GQLDynamicAttributeSetter<Entity1, String>(
					"dynamicAttribute2") {
				@Override
				public void setValue(Entity1 source, String valueToSet) {
					source.setStringAttr(valueToSet);
				}
			};
			final GQLAttributeScalarMetaData dynamicAttrSetterAttribute = new GQLAttributeScalarMetaData(
					dynamicAttributeSetter.getName(), GQLScalarTypeEnum.STRING);
			dynamicAttrSetterAttribute.setDynamicAttributeSetter(dynamicAttributeSetter);
			entity.addAttribute(dynamicAttrSetterAttribute);

			return entity;
		}

		private GQLEntityMetaData buildEntity2() {
			final GQLEntityMetaData entity2 = new GQLEntityMetaData(Entity2.class.getSimpleName(), Entity2.class,
					AbstractEntity.class);

			final GQLAttributeScalarMetaData idAttr = new GQLAttributeScalarMetaData("id", GQLScalarTypeEnum.ID);
			idAttr.setNullable(false);
			entity2.addAttribute(idAttr);

			final GQLAttributeListEntityMetaData entity1s = new GQLAttributeListEntityMetaData("entity1s",
					Entity1.class);
			entity2.addAttribute(entity1s);

			return entity2;
		}

		private GQLEntityMetaData buildEntity3() {
			final GQLEntityMetaData entity3 = new GQLEntityMetaData(Entity3.class.getSimpleName(), Entity3.class,
					AbstractEntity.class);

			final GQLAttributeScalarMetaData idAttr = new GQLAttributeScalarMetaData("id", GQLScalarTypeEnum.ID);
			idAttr.setNullable(false);
			entity3.addAttribute(idAttr);

			final GQLAttributeEntityMetaData entity1 = new GQLAttributeEntityMetaData("entity1", Entity1.class);
			entity3.addAttribute(entity1);

			return entity3;
		}

		private GQLEntityMetaData buildEntity4() {
			final GQLEntityMetaData entity4 = new GQLEntityMetaData(Entity4.class.getSimpleName(), Entity4.class,
					AbstractEntity.class);

			final GQLAttributeScalarMetaData idAttr = new GQLAttributeScalarMetaData("id", GQLScalarTypeEnum.ID);
			idAttr.setNullable(false);
			entity4.addAttribute(idAttr);

			final GQLAttributeListEntityMetaData entity1s = new GQLAttributeListEntityMetaData("entity1s",
					Entity1.class);
			entity4.addAttribute(entity1s);

			return entity4;
		}

		private GQLEmbeddedEntityMetaData buildEmbeddedData1() {
			final GQLEmbeddedEntityMetaData entity = new GQLEmbeddedEntityMetaData(EmbeddedData1.class.getSimpleName(),
					EmbeddedData1.class);

			final GQLAttributeScalarMetaData intAttr = new GQLAttributeScalarMetaData("intAttr", GQLScalarTypeEnum.INT);
			intAttr.setNullable(false);
			entity.addAttribute(intAttr);

			final GQLAttributeScalarMetaData longAttr = new GQLAttributeScalarMetaData("longAttr",
					GQLScalarTypeEnum.LONG);
			longAttr.setNullable(false);
			entity.addAttribute(longAttr);

			final GQLAttributeScalarMetaData doubleAttr = new GQLAttributeScalarMetaData("doubleAttr",
					GQLScalarTypeEnum.FLOAT);
			doubleAttr.setNullable(false);
			entity.addAttribute(doubleAttr);

			entity.addAttribute(new GQLAttributeScalarMetaData("stringAttr", GQLScalarTypeEnum.STRING));

			final GQLAttributeScalarMetaData booleanAttr = new GQLAttributeScalarMetaData("booleanAttr",
					GQLScalarTypeEnum.BOOLEAN);
			booleanAttr.setNullable(false);
			entity.addAttribute(booleanAttr);

			entity.addAttribute(new GQLAttributeScalarMetaData("bigIntAttr", GQLScalarTypeEnum.BIG_INTEGER));
			entity.addAttribute(new GQLAttributeScalarMetaData("bigDecimalAttr", GQLScalarTypeEnum.BIG_DECIMAL));
			entity.addAttribute(new GQLAttributeScalarMetaData("bytesAttr", GQLScalarTypeEnum.BYTE));

			final GQLAttributeScalarMetaData shortAttr = new GQLAttributeScalarMetaData("shortAttr",
					GQLScalarTypeEnum.SHORT);
			shortAttr.setNullable(false);
			entity.addAttribute(shortAttr);

			entity.addAttribute(new GQLAttributeScalarMetaData("charAttr", GQLScalarTypeEnum.CHAR));
			entity.addAttribute(new GQLAttributeScalarMetaData("dateAttr", GQLScalarTypeEnum.DATE));
			entity.addAttribute(new GQLAttributeScalarMetaData("fileAttr", GQLScalarTypeEnum.FILE));
			entity.addAttribute(new GQLAttributeScalarMetaData("localDateAttr", GQLScalarTypeEnum.LOCAL_DATE));
			entity.addAttribute(new GQLAttributeScalarMetaData("localDateTimeAttr", GQLScalarTypeEnum.LOCAL_DATE_TIME));
			entity.addAttribute(new GQLAttributeListScalarMetaData("stringList", GQLScalarTypeEnum.STRING));
			entity.addAttribute(new GQLAttributeListScalarMetaData("stringSet", GQLScalarTypeEnum.STRING));
			entity.addAttribute(new GQLAttributeEnumMetaData("enumAttr", Enum1.class));
			entity.addAttribute(new GQLAttributeListEnumMetaData("enumList", Enum1.class));
			entity.addAttribute(new GQLAttributeListEnumMetaData("enumSet", Enum1.class));
			entity.addAttribute(new GQLAttributeEmbeddedEntityMetaData("data2", EmbeddedData2.class));
			entity.addAttribute(new GQLAttributeListEmbeddedEntityMetaData("data3s", EmbeddedData3.class));

			return entity;
		}

		private GQLEmbeddedEntityMetaData buildEmbeddedData2() {
			return new GQLEmbeddedEntityMetaData(EmbeddedData2.class.getSimpleName(), EmbeddedData2.class);
		}

		private GQLEmbeddedEntityMetaData buildEmbeddedData3() {
			return new GQLEmbeddedEntityMetaData(EmbeddedData3.class.getSimpleName(), EmbeddedData3.class);
		}

		private GQLEnumMetaData buildEnumMetaData() {
			return new GQLEnumMetaData(Enum1.class.getSimpleName(), Enum1.class);
		}

		private GQLAbstractMethodMetaData buildCustomMethodQuery1() {
			final GQLCustomMethod1Arg<Entity1, String> method = new GQLCustomMethod1Arg<Entity1, String>(
					"customMethodQuery1", false, "arg1") {
				@Override
				public Entity1 apply(String arg1) {
					final Entity1 result = new Entity1();
					result.setStringAttr("stringAttr");
					final EmbeddedData1 embeddedData1 = new EmbeddedData1();
					embeddedData1.setStringAttr("test attr");
					result.setEmbeddedData1(embeddedData1);
					return result;
				}
			};
			final GQLAbstractMethodMetaData metaData = new GQLMethodEntityMetaData(method, Entity1.class);
			metaData.addArgument(new GQLMethodArgumentScalarMetaData(method.getArgName(0), GQLScalarTypeEnum.STRING));
			return metaData;
		}

		private GQLAbstractMethodMetaData buildCustomMethodQuery2() {
			final GQLCustomMethod2Arg<Entity1, EmbeddedData1, String> method = new GQLCustomMethod2Arg<Entity1, EmbeddedData1, String>(
					"customMethodQuery2", false, "arg1", "arg2") {
				@Override
				public Entity1 apply(EmbeddedData1 arg1, String arg2) {
					final Entity1 result = new Entity1();
					result.setIntAttr(5);
					result.setStringAttr("stringAttr");
					result.setEmbeddedData1(arg1);
					return result;
				}
			};
			final GQLAbstractMethodMetaData metaData = new GQLMethodEntityMetaData(method, Entity1.class);
			metaData.addArgument(
					new GQLMethodArgumentEmbeddedEntityMetaData(method.getArgName(0), EmbeddedData1.class));
			metaData.addArgument(new GQLMethodArgumentScalarMetaData(method.getArgName(1), GQLScalarTypeEnum.STRING));
			return metaData;
		}

		private GQLAbstractMethodMetaData buildCustomMethodMutation1() {
			final GQLCustomMethod1Arg<Entity1, String> method = new GQLCustomMethod1Arg<Entity1, String>(
					"customMethodMutation1", true, "arg1") {
				@Override
				public Entity1 apply(String arg1) {
					final Entity1 result = new Entity1();
					result.setStringAttr(arg1);
					return result;
				}
			};
			final GQLAbstractMethodMetaData metaData = new GQLMethodEntityMetaData(method, Entity1.class);
			metaData.addArgument(new GQLMethodArgumentScalarMetaData(method.getArgName(0), GQLScalarTypeEnum.STRING));
			return metaData;
		}
	}

}
