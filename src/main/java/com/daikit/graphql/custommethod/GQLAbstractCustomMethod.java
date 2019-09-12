package com.daikit.graphql.custommethod;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.daikit.generics.utils.GenericsUtils;

/**
 * Custom method that will be added to GQL Schema.
 *
 * @author Thibaut Caselli
 * @param <OUTPUT_TYPE>
 *            the output type
 */
public abstract class GQLAbstractCustomMethod<OUTPUT_TYPE> implements IGQLAbstractCustomMethod<OUTPUT_TYPE> {

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

	@Override
	public Type getOutputType() {
		return GenericsUtils.getTypeArguments(getClass(), GQLAbstractCustomMethod.class).get(0);
	}

	/**
	 * Get the nae of this custom method argument at given position
	 *
	 * @param argumentPosition
	 *            the argument position
	 * @return the name of the argument
	 */
	public String getArgumentName(int argumentPosition) {
		return getArgumentNames().get(argumentPosition);
	}

	/**
	 * Get the {@link Type} of this custom method argument at given position
	 *
	 * @param argumentPosition
	 *            the argument position
	 * @return the {@link Type} of the argument
	 */
	public Type getArgumentType(int argumentPosition) {
		return getArgumentTypes().get(argumentPosition);
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
	public List<String> getArgumentNames() {
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
