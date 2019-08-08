package com.daikit.graphql.test.data;

import java.util.ArrayList;
import java.util.List;

/**
 * Entity model class 2
 *
 * @author tcaselli
 */
public class Model2 extends AbstractModel {

	private List<Model1> model1s = new ArrayList<>();

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// GETTERS / SETTERS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	/**
	 * @return the model1s
	 */
	public List<Model1> getModel1s() {
		return model1s;
	}

	/**
	 * @param model1s
	 *            the model1s to set
	 */
	public void setModel1s(List<Model1> model1s) {
		this.model1s = model1s;
	}

}
