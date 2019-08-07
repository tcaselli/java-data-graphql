package com.daikit.graphql.meta.data.method;

import java.util.ArrayList;
import java.util.List;

import com.daikit.graphql.meta.data.GQLAbstractMetaData;
import com.daikit.graphql.meta.data.method.argument.GQLAbstractMethodArgumentMetaData;
import com.daikit.graphql.meta.dynamic.method.GQLAbstractCustomMethod;

/**
 * Dynamic method meta data
 *
 * @author tcaselli
 */
public class GQLAbstractMethodMetaData extends GQLAbstractMetaData {

	private String name;
	// true for mutation , false for query
	private boolean mutation = false;
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
	 * @param name
	 *            the name for the method. This name will be used for building
	 *            GraphQL schema query or mutation for this method
	 * @param mutation
	 *            whether this is a mutation (<code>true</code>) or a query
	 *            (<code>false</code>)
	 * @param method
	 *            the {@link GQLAbstractCustomMethod}
	 */
	public GQLAbstractMethodMetaData(String name, boolean mutation, GQLAbstractCustomMethod<?> method) {
		this.name = name;
		this.mutation = mutation;
		this.method = method;
	}

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// METHODS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	@Override
	protected void appendToString(final StringBuilder stringBuilder) {
		stringBuilder.append(name);
	}

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// GETTERS / SETTERS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	/**
	 * Get the name for the method. This name will be used for building GraphQL
	 * schema query or mutation for this method.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Set the name for the method. This name will be used for building GraphQL
	 * schema query or mutation for this method.
	 *
	 * @param name
	 *            the name to set
	 */
	public void setName(final String name) {
		this.name = name;
	}

	/**
	 * Get the method {@link GQLAbstractCustomMethod}
	 *
	 * @return the method
	 */
	public GQLAbstractCustomMethod<?> getMethod() {
		return method;
	}

	/**
	 * Set the method {@link GQLAbstractCustomMethod}
	 *
	 * @param method
	 *            the method to set
	 */
	public void setMethod(final GQLAbstractCustomMethod<?> method) {
		this.method = method;
	}

	/**
	 * Get method arguments list
	 *
	 * @return the arguments
	 */
	public List<GQLAbstractMethodArgumentMetaData> getArguments() {
		return arguments;
	}

	/**
	 * Set method arguments list
	 *
	 * @param arguments
	 *            the arguments to set
	 */
	public void setArguments(final List<GQLAbstractMethodArgumentMetaData> arguments) {
		this.arguments = arguments;
	}

	/**
	 * Get whether this is a mutation (<code>true</code>) or a query
	 * (<code>false</code>)
	 *
	 * @return the mutation
	 */
	public boolean isMutation() {
		return mutation;
	}

	/**
	 * Set whether this is a mutation (<code>true</code>) or a query
	 * (<code>false</code>)
	 * 
	 * @param mutation
	 *            the mutation to set
	 */
	public void setMutation(final boolean mutation) {
		this.mutation = mutation;
	}

}
