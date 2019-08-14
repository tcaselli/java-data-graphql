package com.daikit.graphql.meta.dynamic.method.abs;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.daikit.generics.utils.GenericsUtils;
import com.daikit.graphql.meta.dynamic.method.IGQLAbstractCustomMethod;

/**
 * Custom method that will be added to GQL Schema.
 *
 * @author tcaselli
 * @param <OUTPUT_TYPE>
 *            the output type
 */
public abstract class GQLAbstractCustomMethod<OUTPUT_TYPE> implements IGQLAbstractCustomMethod<OUTPUT_TYPE> {

	/**
	 * Prefix to be prepended to ID parameter names
	 */
	public static final String ID_PREFIX = "ID#";

	private String methodName;
	private boolean mutation;
	private List<String> argNames = new ArrayList<>();

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// CONSTRUCTORS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	/**
	 * Default constructor
	 */
	public GQLAbstractCustomMethod() {
		// Nothing done
	}

	/**
	 * Constructor
	 *
	 * @param methodName
	 *            the custom method name
	 * @param mutation
	 *            whether this method is a mutation (return true) or a query
	 *            (return false)
	 * @param argNames
	 *            argument names
	 */
	public GQLAbstractCustomMethod(String methodName, boolean mutation, String... argNames) {
		this.methodName = methodName;
		this.mutation = mutation;
		this.argNames = Arrays.asList(argNames);
	}

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// PUBLIC METHODS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	/**
	 * Get output type from generic configuration of this class
	 *
	 * @return the output type
	 */
	protected Type getOutputType() {
		return GenericsUtils.getTypeArguments(getClass(), GQLAbstractCustomMethod.class).get(0);
	}

	/**
	 * Get argument name at given position
	 *
	 * @param argumentPosition
	 *            the argument position (0 if first argument of the method, 1
	 * @return the argument name
	 */
	public String getArgName(int argumentPosition) {
		return getArgNames().get(argumentPosition);
	}

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// GETTERS / SETTERS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	/**
	 * Set the method name that will be available in GraphQL schema
	 *
	 * @param methodName
	 *            the methodName to set
	 */
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	/**
	 * Get the method name that will be available in GraphQL schema
	 *
	 * @return the method name
	 */
	@Override
	public String getMethodName() {
		return methodName;
	}

	/**
	 * Set whether this method is a mutation (return true) or a query (return
	 * false). Default value is <code>true</code> (mutation).
	 *
	 * @param mutation
	 *            the mutation to set
	 */
	public void setMutation(boolean mutation) {
		this.mutation = mutation;
	}

	/**
	 * Get whether this method is a mutation (return true) or a query (return
	 * false). Default value is <code>true</code> (mutation).
	 *
	 * @return a boolean
	 */
	@Override
	public boolean isMutation() {
		return mutation;
	}

	/**
	 * Get method argument names
	 *
	 * @return the argNames
	 */
	@Override
	public List<String> getArgNames() {
		return argNames;
	}

	/**
	 * Set custom method argument names
	 *
	 * @param argNames
	 *            the argNames to set
	 */
	public void setArgNames(List<String> argNames) {
		this.argNames = argNames;
	}

}
