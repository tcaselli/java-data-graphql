package com.daikit.graphql.meta.data.method;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.daikit.generics.utils.GenericsUtils;
import com.daikit.graphql.meta.data.GQLAbstractMetaData;
import com.daikit.graphql.meta.data.method.argument.GQLAbstractMethodArgumentMetaData;
import com.daikit.graphql.meta.dynamic.method.abs.GQLAbstractCustomMethod;

/**
 * Dynamic method meta data
 *
 * @author tcaselli
 */
public class GQLAbstractMethodMetaData extends GQLAbstractMetaData {

	private GQLAbstractCustomMethod<?> method;
	private List<GQLAbstractMethodArgumentMetaData> arguments = new ArrayList<>();

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// CONSTRUCTORS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	/**
	 * Default constructor
	 */
	public GQLAbstractMethodMetaData() {
		// Nothing done
	}

	/**
	 * Constructor passing name, whether this is a mutation or a query, and
	 * method
	 *
	 * @param method
	 *            the {@link GQLAbstractCustomMethod}
	 */
	public GQLAbstractMethodMetaData(GQLAbstractCustomMethod<?> method) {
		this.method = method;
	}

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// METHODS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	@Override
	protected void appendToString(final StringBuilder stringBuilder) {
		stringBuilder.append(getName());
	}

	/**
	 * Get the name for the method. This name will be used for building GraphQL
	 * schema query or mutation for this method.
	 *
	 * @return the name
	 */
	public String getName() {
		return getMethod() == null ? null : getMethod().getMethodName();
	}

	/**
	 * Get whether this is a mutation (<code>true</code>) or a query
	 * (<code>false</code>)
	 *
	 * @return the mutation
	 */
	public boolean isMutation() {
		return getMethod() == null ? false : getMethod().isMutation();
	}

	/**
	 * Utility method to retrieve embedded method return type
	 *
	 * @return a {@link Type}
	 */
	protected Type getMethodReturnType() {
		return GenericsUtils.getTypeArguments(method.getClass()).get(0);
	}

	/**
	 * Add argument {@link GQLAbstractMethodArgumentMetaData}
	 * 
	 * @param argument
	 *            the argument
	 */
	public void addArgument(GQLAbstractMethodArgumentMetaData argument) {
		getArguments().add(argument);
	}

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// GETTERS / SETTERS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	/**
	 * Get the method {@link GQLAbstractCustomMethod}
	 *
	 * @return the method
	 */
	public GQLAbstractCustomMethod<?> getMethod() {
		return method;
	}

	/**
	 * @param method
	 *            the method to set
	 */
	public void setMethod(GQLAbstractCustomMethod<?> method) {
		this.method = method;
	}

	/**
	 * @return the arguments
	 */
	public List<GQLAbstractMethodArgumentMetaData> getArguments() {
		return arguments;
	}

	/**
	 * @param arguments
	 *            the arguments to set
	 */
	public void setArguments(List<GQLAbstractMethodArgumentMetaData> arguments) {
		this.arguments = arguments;
	}

}
