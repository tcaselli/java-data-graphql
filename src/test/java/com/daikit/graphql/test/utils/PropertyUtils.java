package com.daikit.graphql.test.utils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import org.springframework.beans.BeanUtils;

import com.daikit.graphql.utils.Message;

/**
 * Utility class for setting and getting values from/in object properties
 *
 * @author tcaselli
 */
public class PropertyUtils {

	/**
	 * Get property value with given property name on given object
	 *
	 * @param source
	 *            the source object
	 * @param propertyName
	 *            the name of the property within source object
	 * @return the property value
	 */
	@SuppressWarnings("unchecked")
	public static final <T> T getPropertyValue(final Object source, final String propertyName) {
		T value;
		final PropertyDescriptor sourcePd = BeanUtils.getPropertyDescriptor(source.getClass(), propertyName);
		if (sourcePd == null || sourcePd.getReadMethod() == null) {
			throw new IllegalArgumentException(
					Message.format("No property [{}] exists on object [{}]", propertyName, source));
		} else {
			final Method readMethod = sourcePd.getReadMethod();
			boolean reset = false;
			if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {
				readMethod.setAccessible(true);
				reset = true;
			}
			try {
				value = (T) readMethod.invoke(source, new Object[0]);
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				throw new IllegalArgumentException(
						Message.format("Cannot read property [{}] on object [{}]", propertyName, source));
			} finally {
				if (reset) {
					readMethod.setAccessible(false);
				}
			}
		}
		return value;
	}

	/**
	 * Set property value on given target object
	 *
	 * @param target
	 *            the object to set value in
	 * @param propertyName
	 *            the property name of the value to be set
	 * @param valueToSet
	 *            the value to be set
	 */
	public static final void setPropertyValue(final Object target, final String propertyName, final Object valueToSet) {
		final PropertyDescriptor targetPd = BeanUtils.getPropertyDescriptor(target.getClass(), propertyName);
		if (targetPd == null || targetPd.getWriteMethod() == null) {
			throw new IllegalArgumentException(
					Message.format("No property [{}] exists on object [{}]", propertyName, target));
		} else {
			final Method writeMethod = targetPd.getWriteMethod();
			boolean reset = false;
			if (!Modifier.isPublic(writeMethod.getDeclaringClass().getModifiers())) {
				writeMethod.setAccessible(true);
				reset = true;
			}
			try {
				writeMethod.invoke(target, new Object[]{valueToSet});
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				throw new IllegalArgumentException(
						Message.format("Cannot write property [{}] on object [{}]", propertyName, target));
			} finally {
				if (reset) {
					writeMethod.setAccessible(false);
				}
			}
		}
	}
}
