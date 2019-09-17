package com.daikit.graphql.meta.attribute;

/**
 * GraphQL list attribute meta data
 *
 * @author Thibaut Caselli
 */
public class GQLAttributeListEntityMetaData extends GQLAbstractAttributeMetaData {

	private Class<?> foreignClass;
	private boolean cascadeSave = false;
	private boolean embedded = false;

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
	public GQLAttributeListEntityMetaData(final String name, final Class<?> foreignClass) {
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
	 * @return this instance
	 */
	public GQLAttributeListEntityMetaData setForeignClass(final Class<?> foreignClass) {
		this.foreignClass = foreignClass;
		return this;
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
	 * @return this instance
	 */
	public GQLAttributeListEntityMetaData setCascadeSave(final boolean cascadeSave) {
		this.cascadeSave = cascadeSave;
		return this;
	}

	/**
	 * @return the embedded
	 */
	public boolean isEmbedded() {
		return embedded;
	}

	/**
	 * @param embedded
	 *            the embedded to set
	 * @return this instance
	 */
	public GQLAttributeListEntityMetaData setEmbedded(final boolean embedded) {
		this.embedded = embedded;
		return this;
	}

}
