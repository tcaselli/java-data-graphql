package com.daikit.graphql.test.data;

import java.util.ArrayList;
import java.util.List;

/**
 * Entity entity class 2
 *
 * @author Thibaut Caselli
 */
public class Entity2 extends AbstractEntity {

	private List<Entity1> entity1s = new ArrayList<>();

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// GETTERS / SETTERS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	/**
	 * @return the entity1s
	 */
	public List<Entity1> getEntity1s() {
		return entity1s;
	}

	/**
	 * @param entity1s
	 *            the entity1s to set
	 */
	public void setEntity1s(List<Entity1> entity1s) {
		this.entity1s = entity1s;
	}

}
