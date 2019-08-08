package com.daikit.graphql.test.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import com.daikit.graphql.meta.GQLMetaDataModel;
import com.daikit.graphql.meta.GQLMetaDataModelBuilder;
import com.daikit.graphql.meta.data.GQLScalarTypeEnum;
import com.daikit.graphql.meta.data.attribute.GQLAttributeEmbeddedEntityMetaData;
import com.daikit.graphql.meta.data.attribute.GQLAttributeEntityMetaData;
import com.daikit.graphql.meta.data.attribute.GQLAttributeEnumMetaData;
import com.daikit.graphql.meta.data.attribute.GQLAttributeListEmbeddedEntityMetaData;
import com.daikit.graphql.meta.data.attribute.GQLAttributeListEntityMetaData;
import com.daikit.graphql.meta.data.attribute.GQLAttributeListEnumMetaData;
import com.daikit.graphql.meta.data.attribute.GQLAttributeListScalarMetaData;
import com.daikit.graphql.meta.data.attribute.GQLAttributeScalarMetaData;
import com.daikit.graphql.meta.data.entity.GQLAbstractEntityMetaData;
import com.daikit.graphql.meta.data.entity.GQLEmbeddedEntityMetaData;
import com.daikit.graphql.meta.data.entity.GQLEntityMetaData;
import com.daikit.graphql.meta.data.entity.GQLEnumMetaData;

/**
 * Meta data for building test schema
 *
 * @author tcaselli
 */
public class GQLMetaData {

	/**
	 * Build the test GraphQL data meta model
	 *
	 * @return the built {@link GQLMetaDataModel}
	 */
	public static GQLMetaDataModel buildMetaDataModel() {
		return new GQLTestMetaDataModelBuilder().build();
	}

	private static class GQLTestMetaDataModelBuilder {

		private final Collection<GQLAbstractEntityMetaData> entityMetaDatas = new ArrayList<>();
		private final Collection<GQLEnumMetaData> enumMetaDatas = new ArrayList<>();

		protected GQLMetaDataModel build() {
			entityMetaDatas.add(buildModel1());
			entityMetaDatas.add(buildModel2());
			entityMetaDatas.add(buildModel3());
			entityMetaDatas.add(buildModel4());

			entityMetaDatas.add(buildEmbeddedData1());
			entityMetaDatas.add(buildEmbeddedData2());
			entityMetaDatas.add(buildEmbeddedData3());

			enumMetaDatas.add(buildEnumMetaData());

			return new GQLMetaDataModelBuilder().build(enumMetaDatas, entityMetaDatas, Collections.emptyList());
		}

		private GQLEntityMetaData buildModel1() {
			final GQLEntityMetaData entity1 = new GQLEntityMetaData(Model1.class.getSimpleName(), Model1.class,
					AbstractModel.class);

			final GQLAttributeScalarMetaData idAttr = new GQLAttributeScalarMetaData("id", GQLScalarTypeEnum.ID);
			idAttr.setNullable(false);
			entity1.getAttributes().add(idAttr);

			final GQLAttributeScalarMetaData intAttr = new GQLAttributeScalarMetaData("intAttr", GQLScalarTypeEnum.INT);
			intAttr.setNullable(false);
			entity1.getAttributes().add(intAttr);

			final GQLAttributeScalarMetaData longAttr = new GQLAttributeScalarMetaData("longAttr",
					GQLScalarTypeEnum.LONG);
			longAttr.setNullable(false);
			entity1.getAttributes().add(longAttr);

			final GQLAttributeScalarMetaData doubleAttr = new GQLAttributeScalarMetaData("doubleAttr",
					GQLScalarTypeEnum.FLOAT);
			doubleAttr.setNullable(false);
			entity1.getAttributes().add(doubleAttr);

			final GQLAttributeScalarMetaData stringAttr = new GQLAttributeScalarMetaData("stringAttr",
					GQLScalarTypeEnum.STRING);
			entity1.getAttributes().add(stringAttr);

			final GQLAttributeScalarMetaData booleanAttr = new GQLAttributeScalarMetaData("booleanAttr",
					GQLScalarTypeEnum.BOOLEAN);
			booleanAttr.setNullable(false);
			entity1.getAttributes().add(booleanAttr);

			final GQLAttributeScalarMetaData bigIntAttr = new GQLAttributeScalarMetaData("bigIntAttr",
					GQLScalarTypeEnum.BIG_INTEGER);
			entity1.getAttributes().add(bigIntAttr);

			final GQLAttributeScalarMetaData bigDecimalAttr = new GQLAttributeScalarMetaData("bigDecimalAttr",
					GQLScalarTypeEnum.BIG_DECIMAL);
			entity1.getAttributes().add(bigDecimalAttr);

			final GQLAttributeScalarMetaData bytesAttr = new GQLAttributeScalarMetaData("bytesAttr",
					GQLScalarTypeEnum.BYTE);
			entity1.getAttributes().add(bytesAttr);

			final GQLAttributeScalarMetaData shortAttr = new GQLAttributeScalarMetaData("shortAttr",
					GQLScalarTypeEnum.SHORT);
			shortAttr.setNullable(false);
			entity1.getAttributes().add(shortAttr);

			final GQLAttributeScalarMetaData charAttr = new GQLAttributeScalarMetaData("charAttr",
					GQLScalarTypeEnum.CHAR);
			entity1.getAttributes().add(charAttr);

			final GQLAttributeScalarMetaData dateAttr = new GQLAttributeScalarMetaData("dateAttr",
					GQLScalarTypeEnum.DATE);
			entity1.getAttributes().add(dateAttr);

			final GQLAttributeScalarMetaData fileAttr = new GQLAttributeScalarMetaData("fileAttr",
					GQLScalarTypeEnum.FILE);
			entity1.getAttributes().add(fileAttr);

			final GQLAttributeScalarMetaData localDateAttr = new GQLAttributeScalarMetaData("localDateAttr",
					GQLScalarTypeEnum.LOCAL_DATE);
			entity1.getAttributes().add(localDateAttr);

			final GQLAttributeScalarMetaData localDateTimeAttr = new GQLAttributeScalarMetaData("localDateTimeAttr",
					GQLScalarTypeEnum.LOCAL_DATE_TIME);
			entity1.getAttributes().add(localDateTimeAttr);

			final GQLAttributeListScalarMetaData stringList = new GQLAttributeListScalarMetaData("stringList",
					GQLScalarTypeEnum.STRING);
			localDateTimeAttr.setNullable(false);
			entity1.getAttributes().add(stringList);

			final GQLAttributeListScalarMetaData stringSet = new GQLAttributeListScalarMetaData("stringSet",
					GQLScalarTypeEnum.STRING);
			localDateTimeAttr.setNullable(false);
			entity1.getAttributes().add(stringSet);

			final GQLAttributeEnumMetaData enumAttr = new GQLAttributeEnumMetaData("enumAttr", Enum1.class);
			entity1.getAttributes().add(enumAttr);

			final GQLAttributeListEnumMetaData enumList = new GQLAttributeListEnumMetaData("enumList", Enum1.class);
			entity1.getAttributes().add(enumList);

			final GQLAttributeListEnumMetaData enumSet = new GQLAttributeListEnumMetaData("enumSet", Enum1.class);
			entity1.getAttributes().add(enumSet);

			final GQLAttributeEntityMetaData model2 = new GQLAttributeEntityMetaData("model2", Model2.class);
			entity1.getAttributes().add(model2);

			final GQLAttributeListEntityMetaData model3s = new GQLAttributeListEntityMetaData("model3s", Model3.class);
			entity1.getAttributes().add(model3s);

			final GQLAttributeListEntityMetaData model4s = new GQLAttributeListEntityMetaData("model4s", Model4.class);
			entity1.getAttributes().add(model4s);

			final GQLAttributeEmbeddedEntityMetaData embeddedData1 = new GQLAttributeEmbeddedEntityMetaData(
					"embeddedData1", EmbeddedData1.class);
			entity1.getAttributes().add(embeddedData1);

			final GQLAttributeListEmbeddedEntityMetaData embeddedData1s = new GQLAttributeListEmbeddedEntityMetaData(
					"embeddedData1s", EmbeddedData1.class);
			entity1.getAttributes().add(embeddedData1s);

			return entity1;
		}

		private GQLEntityMetaData buildModel2() {
			final GQLEntityMetaData entity2 = new GQLEntityMetaData(Model2.class.getSimpleName(), Model2.class,
					AbstractModel.class);

			final GQLAttributeScalarMetaData idAttr = new GQLAttributeScalarMetaData("id", GQLScalarTypeEnum.ID);
			idAttr.setNullable(false);
			entity2.getAttributes().add(idAttr);

			final GQLAttributeListEntityMetaData model1s = new GQLAttributeListEntityMetaData("model1s", Model1.class);
			entity2.getAttributes().add(model1s);

			return entity2;
		}

		private GQLEntityMetaData buildModel3() {
			final GQLEntityMetaData entity3 = new GQLEntityMetaData(Model3.class.getSimpleName(), Model3.class,
					AbstractModel.class);

			final GQLAttributeScalarMetaData idAttr = new GQLAttributeScalarMetaData("id", GQLScalarTypeEnum.ID);
			idAttr.setNullable(false);
			entity3.getAttributes().add(idAttr);

			final GQLAttributeEntityMetaData model1 = new GQLAttributeEntityMetaData("model1", Model1.class);
			entity3.getAttributes().add(model1);

			return entity3;
		}

		private GQLEntityMetaData buildModel4() {
			final GQLEntityMetaData entity4 = new GQLEntityMetaData(Model4.class.getSimpleName(), Model4.class,
					AbstractModel.class);

			final GQLAttributeScalarMetaData idAttr = new GQLAttributeScalarMetaData("id", GQLScalarTypeEnum.ID);
			idAttr.setNullable(false);
			entity4.getAttributes().add(idAttr);

			final GQLAttributeListEntityMetaData model1s = new GQLAttributeListEntityMetaData("model1s", Model1.class);
			entity4.getAttributes().add(model1s);

			return entity4;
		}

		private GQLEmbeddedEntityMetaData buildEmbeddedData1() {
			final GQLEmbeddedEntityMetaData embeddedEntity1 = new GQLEmbeddedEntityMetaData(
					EmbeddedData1.class.getSimpleName(), EmbeddedData1.class);

			final GQLAttributeScalarMetaData intAttr = new GQLAttributeScalarMetaData("intAttr", GQLScalarTypeEnum.INT);
			intAttr.setNullable(false);
			embeddedEntity1.getAttributes().add(intAttr);

			final GQLAttributeScalarMetaData longAttr = new GQLAttributeScalarMetaData("longAttr",
					GQLScalarTypeEnum.LONG);
			longAttr.setNullable(false);
			embeddedEntity1.getAttributes().add(longAttr);

			final GQLAttributeScalarMetaData doubleAttr = new GQLAttributeScalarMetaData("doubleAttr",
					GQLScalarTypeEnum.FLOAT);
			doubleAttr.setNullable(false);
			embeddedEntity1.getAttributes().add(doubleAttr);

			final GQLAttributeScalarMetaData stringAttr = new GQLAttributeScalarMetaData("stringAttr",
					GQLScalarTypeEnum.STRING);
			embeddedEntity1.getAttributes().add(stringAttr);

			final GQLAttributeScalarMetaData booleanAttr = new GQLAttributeScalarMetaData("booleanAttr",
					GQLScalarTypeEnum.BOOLEAN);
			booleanAttr.setNullable(false);
			embeddedEntity1.getAttributes().add(booleanAttr);

			final GQLAttributeScalarMetaData bigIntAttr = new GQLAttributeScalarMetaData("bigIntAttr",
					GQLScalarTypeEnum.BIG_INTEGER);
			embeddedEntity1.getAttributes().add(bigIntAttr);

			final GQLAttributeScalarMetaData bigDecimalAttr = new GQLAttributeScalarMetaData("bigDecimalAttr",
					GQLScalarTypeEnum.BIG_DECIMAL);
			embeddedEntity1.getAttributes().add(bigDecimalAttr);

			final GQLAttributeScalarMetaData bytesAttr = new GQLAttributeScalarMetaData("bytesAttr",
					GQLScalarTypeEnum.BYTE);
			embeddedEntity1.getAttributes().add(bytesAttr);

			final GQLAttributeScalarMetaData shortAttr = new GQLAttributeScalarMetaData("shortAttr",
					GQLScalarTypeEnum.SHORT);
			shortAttr.setNullable(false);
			embeddedEntity1.getAttributes().add(shortAttr);

			final GQLAttributeScalarMetaData charAttr = new GQLAttributeScalarMetaData("charAttr",
					GQLScalarTypeEnum.CHAR);
			embeddedEntity1.getAttributes().add(charAttr);

			final GQLAttributeScalarMetaData dateAttr = new GQLAttributeScalarMetaData("dateAttr",
					GQLScalarTypeEnum.DATE);
			embeddedEntity1.getAttributes().add(dateAttr);

			final GQLAttributeScalarMetaData fileAttr = new GQLAttributeScalarMetaData("fileAttr",
					GQLScalarTypeEnum.FILE);
			embeddedEntity1.getAttributes().add(fileAttr);

			final GQLAttributeScalarMetaData localDateAttr = new GQLAttributeScalarMetaData("localDateAttr",
					GQLScalarTypeEnum.LOCAL_DATE);
			embeddedEntity1.getAttributes().add(localDateAttr);

			final GQLAttributeScalarMetaData localDateTimeAttr = new GQLAttributeScalarMetaData("localDateTimeAttr",
					GQLScalarTypeEnum.LOCAL_DATE_TIME);
			embeddedEntity1.getAttributes().add(localDateTimeAttr);

			final GQLAttributeListScalarMetaData stringList = new GQLAttributeListScalarMetaData("stringList",
					GQLScalarTypeEnum.STRING);
			localDateTimeAttr.setNullable(false);
			embeddedEntity1.getAttributes().add(stringList);

			final GQLAttributeListScalarMetaData stringSet = new GQLAttributeListScalarMetaData("stringSet",
					GQLScalarTypeEnum.STRING);
			localDateTimeAttr.setNullable(false);
			embeddedEntity1.getAttributes().add(stringSet);

			final GQLAttributeEnumMetaData enumAttr = new GQLAttributeEnumMetaData("enumAttr", Enum1.class);
			embeddedEntity1.getAttributes().add(enumAttr);

			final GQLAttributeListEnumMetaData enumList = new GQLAttributeListEnumMetaData("enumList", Enum1.class);
			embeddedEntity1.getAttributes().add(enumList);

			final GQLAttributeListEnumMetaData enumSet = new GQLAttributeListEnumMetaData("enumSet", Enum1.class);
			embeddedEntity1.getAttributes().add(enumSet);

			final GQLAttributeEmbeddedEntityMetaData embeddedData2 = new GQLAttributeEmbeddedEntityMetaData("data2",
					EmbeddedData2.class);
			embeddedEntity1.getAttributes().add(embeddedData2);

			final GQLAttributeListEmbeddedEntityMetaData embeddedData3s = new GQLAttributeListEmbeddedEntityMetaData(
					"data3s", EmbeddedData3.class);
			embeddedEntity1.getAttributes().add(embeddedData3s);

			return embeddedEntity1;
		}

		private GQLEmbeddedEntityMetaData buildEmbeddedData2() {
			final GQLEmbeddedEntityMetaData embeddedEntity2 = new GQLEmbeddedEntityMetaData(
					EmbeddedData2.class.getSimpleName(), EmbeddedData2.class);
			return embeddedEntity2;
		}

		private GQLEmbeddedEntityMetaData buildEmbeddedData3() {
			final GQLEmbeddedEntityMetaData embeddedEntity3 = new GQLEmbeddedEntityMetaData(
					EmbeddedData3.class.getSimpleName(), EmbeddedData3.class);
			return embeddedEntity3;
		}

		private GQLEnumMetaData buildEnumMetaData() {
			final GQLEnumMetaData enumMetaData = new GQLEnumMetaData(Enum1.class.getSimpleName(), Enum1.class);
			return enumMetaData;
		}
	}

}
