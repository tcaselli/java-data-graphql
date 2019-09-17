package com.daikit.graphql.test.data;

/**
 * Entity class 3
 *
 * @author Thibaut Caselli
 */
public class Entity3 extends AbstractEntity {

	private Entity1 entity1;

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// GETTERS / SETTERS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	/**
	 * @return the entity1
	 */
	public Entity1 getEntity1() {
		return entity1;
	}

	/**
	 * @param entity1
	 *            the entity1 to set
	 */
	public void setEntity1(final Entity1 entity1) {
		this.entity1 = entity1;
	}
}
