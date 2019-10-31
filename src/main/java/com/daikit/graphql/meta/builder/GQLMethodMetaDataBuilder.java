package com.daikit.graphql.meta.builder;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.daikit.generics.utils.GenericsUtils;
import com.daikit.graphql.config.GQLSchemaConfig;
import com.daikit.graphql.custommethod.GQLCustomMethod;
import com.daikit.graphql.custommethod.GQLCustomMethodArg;
import com.daikit.graphql.meta.custommethod.GQLAbstractMethodArgumentMetaData;
import com.daikit.graphql.meta.custommethod.GQLAbstractMethodMetaData;
import com.daikit.graphql.meta.custommethod.GQLMethodArgumentEntityMetaData;
import com.daikit.graphql.meta.custommethod.GQLMethodArgumentEnumMetaData;
import com.daikit.graphql.meta.custommethod.GQLMethodArgumentListEntityMetaData;
import com.daikit.graphql.meta.custommethod.GQLMethodArgumentListEnumMetaData;
import com.daikit.graphql.meta.custommethod.GQLMethodArgumentListScalarMetaData;
import com.daikit.graphql.meta.custommethod.GQLMethodArgumentScalarMetaData;
import com.daikit.graphql.meta.custommethod.GQLMethodEntityMetaData;
import com.daikit.graphql.meta.custommethod.GQLMethodEnumMetaData;
import com.daikit.graphql.meta.custommethod.GQLMethodListEntityMetaData;
import com.daikit.graphql.meta.custommethod.GQLMethodListEnumMetaData;
import com.daikit.graphql.meta.custommethod.GQLMethodListScalarMetaData;
import com.daikit.graphql.meta.custommethod.GQLMethodScalarMetaData;
import com.daikit.graphql.meta.entity.GQLEntityMetaData;
import com.daikit.graphql.meta.entity.GQLEnumMetaData;
import com.daikit.graphql.utils.Message;

/**
 * Builder for custom method meta data from {@link GQLCustomMethod}
 *
 * @author Thibaut Caselli
 */
public class GQLMethodMetaDataBuilder extends GQLAbstractMetaDataBuilder {

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// CONSTRUCTORS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	/**
	 * Constructor
	 *
	 * @param schemaConfig
	 *            the {@link GQLSchemaConfig}
	 */
	public GQLMethodMetaDataBuilder(final GQLSchemaConfig schemaConfig) {
		super(schemaConfig);
	}

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// PUBLIC METHODS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	/**
	 * Build custom method meta data from its {@link GQLCustomMethod} definition
	 *
	 * @param enumMetaDatas
	 *            the collection of all registered {@link GQLEnumMetaData} for
	 *            being able to look for type references (for method return type
	 *            or argument types)
	 * @param entityMetaDatas
	 *            the collection of all registered {@link GQLEntityMetaData} for
	 *            being able to look for type references (for method return type
	 *            or argument types)
	 * @param customMethod
	 *            the {@link GQLCustomMethod} to create meta data from
	 * @return the created {@link GQLAbstractMethodMetaData}
	 */
	public GQLAbstractMethodMetaData build(final Collection<GQLEnumMetaData> enumMetaDatas,
			final Collection<GQLEntityMetaData> entityMetaDatas, final GQLCustomMethod customMethod) {
		final List<GQLAbstractMethodArgumentMetaData> arguments = new ArrayList<>();
		for (final GQLCustomMethodArg arg : customMethod.getArgs()) {
			arguments.add(
					createMethodArgument(enumMetaDatas, entityMetaDatas, customMethod, arg.getName(), arg.getType()));
		}
		return createMethod(enumMetaDatas, entityMetaDatas, customMethod, arguments);
	}

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// PRIVATE METHODS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	@SuppressWarnings("unchecked")
	private GQLAbstractMethodMetaData createMethod(final Collection<GQLEnumMetaData> enumMetaDatas,
			final Collection<GQLEntityMetaData> entityMetaDatas, final GQLCustomMethod method,
			final List<GQLAbstractMethodArgumentMetaData> arguments) {
		final GQLAbstractMethodMetaData methodMetaData;
		final Class<?> outputRawClass = GenericsUtils.getTypeClass(method.getOutputType());
		if (getConfig().isScalarType(outputRawClass)) {
			methodMetaData = new GQLMethodScalarMetaData();
			((GQLMethodScalarMetaData) methodMetaData)
					.setScalarType(getConfig().getScalarTypeCodeFromClass(outputRawClass).get());
		} else if (isByteArray(outputRawClass)) {
			methodMetaData = new GQLMethodScalarMetaData();
			((GQLMethodScalarMetaData) methodMetaData)
					.setScalarType(getConfig().getScalarTypeCodeFromClass(outputRawClass.getComponentType()).get());
		} else if (isEnum(enumMetaDatas, outputRawClass)) {
			methodMetaData = new GQLMethodEnumMetaData();
			((GQLMethodEnumMetaData) methodMetaData).setEnumClass((Class<? extends Enum<?>>) outputRawClass);
		} else if (isEntity(entityMetaDatas, outputRawClass)) {
			methodMetaData = new GQLMethodEntityMetaData();
			((GQLMethodEntityMetaData) methodMetaData).setEntityClass(outputRawClass);
		} else if (Collection.class.isAssignableFrom(outputRawClass)) {
			final Class<?> foreignClass = GenericsUtils
					.getTypeArgumentsAsClasses(method.getOutputType(), Collection.class).get(0);
			if (getConfig().isScalarType(foreignClass)) {
				methodMetaData = new GQLMethodListScalarMetaData();
				((GQLMethodListScalarMetaData) methodMetaData)
						.setScalarType(getConfig().getScalarTypeCodeFromClass(foreignClass).get());
			} else if (isEnum(enumMetaDatas, foreignClass)) {
				methodMetaData = new GQLMethodListEnumMetaData();
				((GQLMethodListEnumMetaData) methodMetaData).setEnumClass((Class<? extends Enum<?>>) foreignClass);
			} else if (isEntity(entityMetaDatas, foreignClass)) {
				methodMetaData = new GQLMethodListEntityMetaData();
				((GQLMethodListEntityMetaData) methodMetaData).setForeignClass(foreignClass);
			} else {
				throw new IllegalArgumentException(
						Message.format("Not handled method [{}] output collection type [{}].", method.getName(),
								outputRawClass.getName()));
			}
		} else {
			throw new IllegalArgumentException(Message.format("Not handled method [{}] output type [{}].",
					method.getName(), outputRawClass.getSimpleName()));
		}

		methodMetaData.getArguments().addAll(arguments);
		methodMetaData.setMethod(method);

		return methodMetaData;
	}

	@SuppressWarnings("unchecked")
	private GQLAbstractMethodArgumentMetaData createMethodArgument(final Collection<GQLEnumMetaData> enumMetaDatas,
			final Collection<GQLEntityMetaData> entityMetaDatas, final GQLCustomMethod customMethod,
			final String argumentName, final Type argumentType) {
		final GQLAbstractMethodArgumentMetaData argumentMetaData;
		final Class<?> argumentRawClass = GenericsUtils.getTypeClass(argumentType);
		if (getConfig().isScalarType(argumentRawClass)) {
			argumentMetaData = new GQLMethodArgumentScalarMetaData();
			((GQLMethodArgumentScalarMetaData) argumentMetaData)
					.setScalarType(getConfig().getScalarTypeCodeFromClass(argumentRawClass).get());
		} else if (isByteArray(argumentRawClass)) {
			argumentMetaData = new GQLMethodArgumentScalarMetaData();
			((GQLMethodArgumentScalarMetaData) argumentMetaData)
					.setScalarType(getConfig().getScalarTypeCodeFromClass(argumentRawClass.getComponentType()).get());
		} else if (isEnum(enumMetaDatas, argumentRawClass)) {
			argumentMetaData = new GQLMethodArgumentEnumMetaData();
			((GQLMethodArgumentEnumMetaData) argumentMetaData)
					.setEnumClass((Class<? extends Enum<?>>) argumentRawClass);
		} else if (isEntity(entityMetaDatas, argumentRawClass)) {
			argumentMetaData = new GQLMethodArgumentEntityMetaData();
			((GQLMethodArgumentEntityMetaData) argumentMetaData).setEntityClass(argumentRawClass);
		} else if (Collection.class.isAssignableFrom(argumentRawClass)) {
			final Class<?> foreignClass = GenericsUtils.getTypeArgumentsAsClasses(argumentType, Collection.class)
					.get(0);
			if (getConfig().isScalarType(foreignClass)) {
				argumentMetaData = new GQLMethodArgumentListScalarMetaData();
				((GQLMethodArgumentListScalarMetaData) argumentMetaData)
						.setScalarType(getConfig().getScalarTypeCodeFromClass(foreignClass).get());
			} else if (isEnum(enumMetaDatas, foreignClass)) {
				argumentMetaData = new GQLMethodArgumentListEnumMetaData();
				((GQLMethodArgumentListEnumMetaData) argumentMetaData).setEnumClass(foreignClass);
			} else if (isEntity(entityMetaDatas, foreignClass)) {
				argumentMetaData = new GQLMethodArgumentListEntityMetaData();
				((GQLMethodArgumentListEntityMetaData) argumentMetaData).setForeignClass(foreignClass);
			} else {
				throw new IllegalArgumentException(
						Message.format("Not handled method [{}] argument [{}] collection type [{}].",
								customMethod.getName(), argumentName, foreignClass.getName()));
			}
		} else {
			throw new IllegalArgumentException(Message.format("Not handled method [{}] argument [{}] type [{}].",
					customMethod.getName(), argumentName, argumentRawClass.getName()));
		}

		argumentMetaData.setName(argumentName);

		return argumentMetaData;
	}

}
