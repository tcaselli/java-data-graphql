package com.daikit.graphql.test.data;

import java.io.File;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Class intended to be embedded in an entity model
 *
 * @author tcaselli
 */
public class EmbeddedData1 {

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

	// Scalar collections
	private List<String> stringList = new ArrayList<>();
	private Set<String> stringSet = new HashSet<>();

	// Enumerations
	private Enum1 enumAttr;

	// Enumeration collections
	private List<Enum1> enumList = new ArrayList<>();
	private Set<Enum1> enumSet = new HashSet<>();

	// Embedded data relations

	private EmbeddedData2 data2;
	private List<EmbeddedData3> data3s = new ArrayList<>();

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
	public void setIntAttr(int intAttr) {
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
	public void setLongAttr(long longAttr) {
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
	public void setDoubleAttr(double doubleAttr) {
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
	public void setStringAttr(String stringAttr) {
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
	public void setBooleanAttr(boolean booleanAttr) {
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
	public void setBigIntAttr(BigInteger bigIntAttr) {
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
	public void setBigDecimalAttr(BigDecimal bigDecimalAttr) {
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
	public void setBytesAttr(byte[] bytesAttr) {
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
	public void setShortAttr(short shortAttr) {
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
	public void setCharAttr(char charAttr) {
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
	public void setDateAttr(Date dateAttr) {
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
	public void setFileAttr(File fileAttr) {
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
	public void setLocalDateAttr(LocalDate localDateAttr) {
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
	public void setLocalDateTimeAttr(LocalDateTime localDateTimeAttr) {
		this.localDateTimeAttr = localDateTimeAttr;
	}

	/**
	 * @return the data2
	 */
	public EmbeddedData2 getData2() {
		return data2;
	}

	/**
	 * @param data2
	 *            the data2 to set
	 */
	public void setData2(EmbeddedData2 data2) {
		this.data2 = data2;
	}

	/**
	 * @return the data3s
	 */
	public List<EmbeddedData3> getData3s() {
		return data3s;
	}

	/**
	 * @param data3s
	 *            the data3s to set
	 */
	public void setData3s(List<EmbeddedData3> data3s) {
		this.data3s = data3s;
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
	public void setEnumAttr(Enum1 enumAttr) {
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
	public void setStringList(List<String> stringList) {
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
	public void setStringSet(Set<String> stringSet) {
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
	public void setEnumList(List<Enum1> enumList) {
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
	public void setEnumSet(Set<Enum1> enumSet) {
		this.enumSet = enumSet;
	}
}
