package com.daikit.graphql.builder;

import java.util.ArrayList;
import java.util.List;

import graphql.execution.ExecutionContext;

/**
 * Context for building the GQL schema and for execution. This is useful when
 * the schema is depending on who is logged in for example.
 *
 * @author Thibaut Caselli
 */
public class GQLExecutionContext {

	private List<String> roles = new ArrayList<String>();
	private GQLRolesJunctionEnum rolesJunction = GQLRolesJunctionEnum.OR;

	/**
	 * Default execution context. For all roles with a
	 * {@link GQLRolesJunctionEnum#OR} junction, or no role
	 */
	public static final GQLExecutionContext DEFAULT = new GQLExecutionContext();

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// PUBLIC METHODS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	@Override
	public String toString() {
		return "(Context:" + getRoles() + ")";
	}

	@Override
	public boolean equals(Object obj) {
		return obj instanceof ExecutionContext && ((GQLExecutionContext) obj).getRoles().containsAll(roles)
				&& roles.containsAll(((GQLExecutionContext) obj).getRoles());
	}

	@Override
	public int hashCode() {
		return roles.stream().map(Object::hashCode).reduce(0, Integer::sum);
	}

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// INNER CLASSES
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	/**
	 * Junction for roles.
	 *
	 * @author Thibaut Caselli
	 */
	public enum GQLRolesJunctionEnum {
		/**
		 * In order to be read, saved or deleted, an Entity or an attribute must
		 * have a client configuration allowing the action for all the roles in
		 * this context.
		 */
		AND,
		/**
		 * In order to be read, saved or deleted, an Entity or an attribute must
		 * have a client configuration allowing the action for one of the roles
		 * in this context.
		 */
		OR
	}

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// GETTERS / SETTERS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	/**
	 * Get the roles. Default is empty = no matter the role, can even be "no
	 * role".
	 * 
	 * @return the roles
	 */
	public List<String> getRoles() {
		return roles;
	}

	/**
	 * Set the roles. Default is empty = no matter the role, can even be "no
	 * role".
	 * 
	 * @param roles
	 *            the roles to set
	 */
	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

	/**
	 * Get the {@link GQLRolesJunctionEnum}. Default is
	 * {@link GQLRolesJunctionEnum#OR}.
	 *
	 * @return the rolesJunction
	 */
	public GQLRolesJunctionEnum getRolesJunction() {
		return rolesJunction;
	}

	/**
	 * Set the {@link GQLRolesJunctionEnum}. Default is
	 * {@link GQLRolesJunctionEnum#OR}.
	 *
	 * @param rolesJunction
	 *            the rolesJunction to set
	 */
	public void setRolesJunction(GQLRolesJunctionEnum rolesJunction) {
		this.rolesJunction = rolesJunction;
	}

}
