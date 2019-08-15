package com.daikit.graphql.data.output;

/**
 * Result for delete method
 *
 * @author tcaselli
 */
public class GQLDeleteResult {

	private String id;
	private String typename;

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// CONSTRUCTORS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	/**
	 * Default constructor
	 */
	public GQLDeleteResult() {
		// Nothing done
	}

	/**
	 * Constructor
	 * 
	 * @param id
	 *            the id
	 * @param typename
	 *            the type name
	 */
	public GQLDeleteResult(String id, String typename) {
		this.id = id;
		this.typename = typename;
	}

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// GETTERS / SETTERS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(final String id) {
		this.id = id;
	}

	/**
	 * @return the typename
	 */
	public String getTypename() {
		return typename;
	}

	/**
	 * @param typename
	 *            the typename to set
	 */
	public void setTypename(final String typename) {
		this.typename = typename;
	}

}
