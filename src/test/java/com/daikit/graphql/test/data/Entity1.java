package com.daikit.graphql.test.data;

import java.io.File;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Entity class 1
 *
 * @author Thibaut Caselli
 */
public class Entity1 extends AbstractEntity {

	// Scalars
	private int intAttr;
	private long longAttr;
	private double doubleAttr;
	private String stringAttr;
	private boolean booleanAttr;
	private BigInteger bigIntAttr;
	private BigDecimal bigDecimalAttr;
	private byte[] bytesAttr;
	private short shortAttr;
	private char charAttr;
	private Date dateAttr;
	private File fileAttr;
	private LocalDate localDateAttr;
	private LocalDateTime localDateTimeAttr;
	private Instant instantAttr;

	// Scalar collections
	private List<String> stringList = new ArrayList<>();
	private Set<String> stringSet = new HashSet<>();

	// Enumerations
	private Enum1 enumAttr;

	// Enumeration collections
	private List<Enum1> enumList = new ArrayList<>();
	private Set<Enum1> enumSet = new HashSet<>();

	// Relations

	// Relation many to 1
	private Entity2 entity2;
	// Relation 1 to many
	private List<Entity3> entity3s = new ArrayList<>();
	// Relation many to many
	private List<Entity4> entity4s = new ArrayList<>();

	// Embedded data relations

	// Single data
	private EmbeddedData1 embeddedData1;
	// List of datas
	private List<EmbeddedData1> embeddedData1s = new ArrayList<>();

	private transient String transientStringAttr;
	/**
	 * Static String attribute
	 */
	public static String STATIC_STRING_ATTR = "staticStringAttr";

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// GETTERS / SETTERS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	/**
	 * @return the intAttr
	 */
	public int getIntAttr() {
		return intAttr;
	}
	/**
	 * @param intAttr
	 *            the intAttr to set
	 */
	public void setIntAttr(final int intAttr) {
		this.intAttr = intAttr;
	}
	/**
	 * @return the longAttr
	 */
	public long getLongAttr() {
		return longAttr;
	}
	/**
	 * @param longAttr
	 *            the longAttr to set
	 */
	public void setLongAttr(final long longAttr) {
		this.longAttr = longAttr;
	}
	/**
	 * @return the doubleAttr
	 */
	public double getDoubleAttr() {
		return doubleAttr;
	}
	/**
	 * @param doubleAttr
	 *            the doubleAttr to set
	 */
	public void setDoubleAttr(final double doubleAttr) {
		this.doubleAttr = doubleAttr;
	}
	/**
	 * @return the stringAttr
	 */
	public String getStringAttr() {
		return stringAttr;
	}
	/**
	 * @param stringAttr
	 *            the stringAttr to set
	 */
	public void setStringAttr(final String stringAttr) {
		this.stringAttr = stringAttr;
	}
	/**
	 * @return the booleanAttr
	 */
	public boolean isBooleanAttr() {
		return booleanAttr;
	}
	/**
	 * @param booleanAttr
	 *            the booleanAttr to set
	 */
	public void setBooleanAttr(final boolean booleanAttr) {
		this.booleanAttr = booleanAttr;
	}
	/**
	 * @return the bigIntAttr
	 */
	public BigInteger getBigIntAttr() {
		return bigIntAttr;
	}
	/**
	 * @param bigIntAttr
	 *            the bigIntAttr to set
	 */
	public void setBigIntAttr(final BigInteger bigIntAttr) {
		this.bigIntAttr = bigIntAttr;
	}
	/**
	 * @return the bigDecimalAttr
	 */
	public BigDecimal getBigDecimalAttr() {
		return bigDecimalAttr;
	}
	/**
	 * @param bigDecimalAttr
	 *            the bigDecimalAttr to set
	 */
	public void setBigDecimalAttr(final BigDecimal bigDecimalAttr) {
		this.bigDecimalAttr = bigDecimalAttr;
	}
	/**
	 * @return the bytesAttr
	 */
	public byte[] getBytesAttr() {
		return bytesAttr;
	}
	/**
	 * @param bytesAttr
	 *            the bytesAttr to set
	 */
	public void setBytesAttr(final byte[] bytesAttr) {
		this.bytesAttr = bytesAttr;
	}
	/**
	 * @return the shortAttr
	 */
	public short getShortAttr() {
		return shortAttr;
	}
	/**
	 * @param shortAttr
	 *            the shortAttr to set
	 */
	public void setShortAttr(final short shortAttr) {
		this.shortAttr = shortAttr;
	}
	/**
	 * @return the charAttr
	 */
	public char getCharAttr() {
		return charAttr;
	}
	/**
	 * @param charAttr
	 *            the charAttr to set
	 */
	public void setCharAttr(final char charAttr) {
		this.charAttr = charAttr;
	}
	/**
	 * @return the dateAttr
	 */
	public Date getDateAttr() {
		return dateAttr;
	}
	/**
	 * @param dateAttr
	 *            the dateAttr to set
	 */
	public void setDateAttr(final Date dateAttr) {
		this.dateAttr = dateAttr;
	}
	/**
	 * @return the fileAttr
	 */
	public File getFileAttr() {
		return fileAttr;
	}
	/**
	 * @param fileAttr
	 *            the fileAttr to set
	 */
	public void setFileAttr(final File fileAttr) {
		this.fileAttr = fileAttr;
	}
	/**
	 * @return the localDateAttr
	 */
	public LocalDate getLocalDateAttr() {
		return localDateAttr;
	}
	/**
	 * @param localDateAttr
	 *            the localDateAttr to set
	 */
	public void setLocalDateAttr(final LocalDate localDateAttr) {
		this.localDateAttr = localDateAttr;
	}
	/**
	 * @return the localDateTimeAttr
	 */
	public LocalDateTime getLocalDateTimeAttr() {
		return localDateTimeAttr;
	}
	/**
	 * @param localDateTimeAttr
	 *            the localDateTimeAttr to set
	 */
	public void setLocalDateTimeAttr(final LocalDateTime localDateTimeAttr) {
		this.localDateTimeAttr = localDateTimeAttr;
	}
	/**
	 * @return the entity2
	 */
	public Entity2 getEntity2() {
		return entity2;
	}
	/**
	 * @param entity2
	 *            the entity2 to set
	 */
	public void setEntity2(final Entity2 entity2) {
		this.entity2 = entity2;
	}
	/**
	 * @return the entity3s
	 */
	public List<Entity3> getEntity3s() {
		return entity3s;
	}
	/**
	 * @param entity3s
	 *            the entity3s to set
	 */
	public void setEntity3s(final List<Entity3> entity3s) {
		this.entity3s = entity3s;
	}
	/**
	 * @return the entity4s
	 */
	public List<Entity4> getEntity4s() {
		return entity4s;
	}
	/**
	 * @param entity4s
	 *            the entity4s to set
	 */
	public void setEntity4s(final List<Entity4> entity4s) {
		this.entity4s = entity4s;
	}
	/**
	 * @return the embeddedData1
	 */
	public EmbeddedData1 getEmbeddedData1() {
		return embeddedData1;
	}
	/**
	 * @param embeddedData1
	 *            the embeddedData1 to set
	 */
	public void setEmbeddedData1(final EmbeddedData1 embeddedData1) {
		this.embeddedData1 = embeddedData1;
	}
	/**
	 * @return the embeddedData1s
	 */
	public List<EmbeddedData1> getEmbeddedData1s() {
		return embeddedData1s;
	}
	/**
	 * @param embeddedData1s
	 *            the embeddedData1s to set
	 */
	public void setEmbeddedData1s(final List<EmbeddedData1> embeddedData1s) {
		this.embeddedData1s = embeddedData1s;
	}
	/**
	 * @return the enumAttr
	 */
	public Enum1 getEnumAttr() {
		return enumAttr;
	}
	/**
	 * @param enumAttr
	 *            the enumAttr to set
	 */
	public void setEnumAttr(final Enum1 enumAttr) {
		this.enumAttr = enumAttr;
	}
	/**
	 * @return the stringList
	 */
	public List<String> getStringList() {
		return stringList;
	}
	/**
	 * @param stringList
	 *            the stringList to set
	 */
	public void setStringList(final List<String> stringList) {
		this.stringList = stringList;
	}
	/**
	 * @return the stringSet
	 */
	public Set<String> getStringSet() {
		return stringSet;
	}
	/**
	 * @param stringSet
	 *            the stringSet to set
	 */
	public void setStringSet(final Set<String> stringSet) {
		this.stringSet = stringSet;
	}
	/**
	 * @return the enumList
	 */
	public List<Enum1> getEnumList() {
		return enumList;
	}
	/**
	 * @param enumList
	 *            the enumList to set
	 */
	public void setEnumList(final List<Enum1> enumList) {
		this.enumList = enumList;
	}
	/**
	 * @return the enumSet
	 */
	public Set<Enum1> getEnumSet() {
		return enumSet;
	}
	/**
	 * @param enumSet
	 *            the enumSet to set
	 */
	public void setEnumSet(final Set<Enum1> enumSet) {
		this.enumSet = enumSet;
	}
	/**
	 * @return the transientStringAttr
	 */
	public String getTransientStringAttr() {
		return transientStringAttr;
	}
	/**
	 * @param transientStringAttr
	 *            the transientStringAttr to set
	 */
	public void setTransientStringAttr(String transientStringAttr) {
		this.transientStringAttr = transientStringAttr;
	}
	/**
	 * @return the instantAttr
	 */
	public Instant getInstantAttr() {
		return instantAttr;
	}
	/**
	 * @param instantAttr
	 *            the instantAttr to set
	 */
	public void setInstantAttr(Instant instantAttr) {
		this.instantAttr = instantAttr;
	}
}
