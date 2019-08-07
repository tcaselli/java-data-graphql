package com.daikit.graphql.meta.data.attribute;

/**
 * GraphQL list attribute meta data
 *
 * @author tcaselli
 */
public class GQLAttributeListEntityMetaData extends GQLAbstractAttributeMetaData {

	private Class<?> foreignClass;
	private boolean cascadeSave = false;

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// CONSTRUCTORS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	/**
	 * Default constructor
	 */
	public GQLAttributeListEntityMetaData() {
		// Nothing done
	}

	/**
	 * Constructor passing name and foreign entity class and keeping default
	 * values for other attributes.
	 *
	 * @param name
	 *            the name for the attribute. This name will be used for
	 *            building GraphQL schema : queries, mutations, descriptions
	 *            etc.
	 * @param foreignClass
	 *            the foreign entity class
	 */
	public GQLAttributeListEntityMetaData(String name, Class<?> foreignClass) {
		super(name);
		this.foreignClass = foreignClass;
	}

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// METHODS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	@Override
	protected void appendToString(final StringBuilder stringBuilder) {
		stringBuilder.append("{LIST-ENTITY(").append(foreignClass == null ? "" : foreignClass.getSimpleName())
				.append(")}");
		super.appendToString(stringBuilder);
	}

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// GETTERS / SETTERS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	/**
	 * Get the foreign class
	 *
	 * @return the foreignClass
	 */
	public Class<?> getForeignClass() {
		return foreignClass;
	}

	/**
	 * Set the foreign class
	 *
	 * @param foreignClass
	 *            the foreignClass to set
	 */
	public void setForeignClass(final Class<?> foreignClass) {
		this.foreignClass = foreignClass;
	}

	/**
	 * Set whether save has to be cascaded to foreign entities when executing
	 * save mutation
	 *
	 * @return the cascadeSave
	 */
	public boolean isCascadeSave() {
		return cascadeSave;
	}

	/**
	 * Get whether save has to be cascaded to foreign entities when executing
	 * save mutation
	 *
	 * @param cascadeSave
	 *            the cascadeSave to set
	 */
	public void setCascadeSave(final boolean cascadeSave) {
		this.cascadeSave = cascadeSave;
	}

}
