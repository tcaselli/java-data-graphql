package com.daikit.graphql.custommethod;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.daikit.graphql.enums.GQLMethodType;
import com.daikit.graphql.meta.GQLController;

/**
 * Custom method, built from {@link GQLController} annotated classes
 *
 * @author Thibaut Caselli
 */
public class GQLCustomMethod {

	private String name;
	private GQLMethodType type;
	private List<GQLCustomMethodArg> args = new ArrayList<>();
	private Type outputType;

	private Object controller;
	private Method method;

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// PUBLIC METHODS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	/**
	 * @return whether this method is a mutation
	 */
	public boolean isMutation() {
		return GQLMethodType.MUTATION.equals(type);
	}

	/**
	 * @return whether this method is a query
	 */
	public boolean isQuery() {
		return GQLMethodType.QUERY.equals(type);
	}

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// GETTERS / SETTERS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	/**
	 * @return the type
	 */
	public GQLMethodType getType() {
		return type;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(GQLMethodType type) {
		this.type = type;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the args
	 */
	public List<GQLCustomMethodArg> getArgs() {
		return args;
	}
	/**
	 * @param args
	 *            the args to set
	 */
	public void setArgs(List<GQLCustomMethodArg> args) {
		this.args = args;
	}
	/**
	 * @return the outputType
	 */
	public Type getOutputType() {
		return outputType;
	}
	/**
	 * @param outputType
	 *            the outputType to set
	 */
	public void setOutputType(Type outputType) {
		this.outputType = outputType;
	}

	/**
	 * @return the controller
	 */
	public Object getController() {
		return controller;
	}

	/**
	 * @param controller
	 *            the controller to set
	 */
	public void setController(Object controller) {
		this.controller = controller;
	}

	/**
	 * @return the method
	 */
	public Method getMethod() {
		return method;
	}

	/**
	 * @param method
	 *            the method to set
	 */
	public void setMethod(Method method) {
		this.method = method;
	}
}
