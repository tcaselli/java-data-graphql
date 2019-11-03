package com.daikit.graphql.meta.builder;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.daikit.graphql.custommethod.GQLCustomMethod;
import com.daikit.graphql.custommethod.GQLCustomMethodArg;
import com.daikit.graphql.meta.GQLMethod;
import com.daikit.graphql.meta.GQLParam;

/**
 * Collector for GQL Controller methods
 *
 * @author Thibaut Caselli
 */
public class GQLControllerMethodCollector {

	/**
	 * Collect {@link GQLCustomMethod} list from given controllers list
	 *
	 * @param controllers
	 *            the {@link Collection} of controllers
	 * @return a {@link List} of {@link GQLCustomMethod}
	 */
	public List<GQLCustomMethod> collect(final Collection<Object> controllers) {
		final List<GQLCustomMethod> customMethods = new ArrayList<>();
		for (final Object controller : controllers) {
			final Method[] methods = controller.getClass().getMethods();
			for (final Method method : methods) {
				if (Modifier.isPublic(method.getModifiers()) && method.isAnnotationPresent(GQLMethod.class)) {
					customMethods.add(collectMethod(controller, method));
				}
			}
		}
		return customMethods;
	}

	private GQLCustomMethod collectMethod(final Object controller, final Method method) {
		final GQLCustomMethod customMethod = new GQLCustomMethod();
		final GQLMethod methodAnnotation = method.getAnnotation(GQLMethod.class);
		customMethod
				.setName(StringUtils.isEmpty(methodAnnotation.value()) ? method.getName() : methodAnnotation.value());
		customMethod.setDescription(methodAnnotation.description());
		customMethod.setType(methodAnnotation.type());
		customMethod.setOutputType(method.getGenericReturnType());
		customMethod.setController(controller);
		customMethod.setMethod(method);
		for (final Parameter parameter : method.getParameters()) {
			final GQLCustomMethodArg arg = new GQLCustomMethodArg();
			final GQLParam paramAnnotation = parameter.getAnnotation(GQLParam.class);
			arg.setName(paramAnnotation != null && StringUtils.isNotEmpty(paramAnnotation.value())
					? paramAnnotation.value()
					: parameter.getName());
			arg.setType(parameter.getParameterizedType());
			customMethod.getArgs().add(arg);
		}
		return customMethod;
	}
}
