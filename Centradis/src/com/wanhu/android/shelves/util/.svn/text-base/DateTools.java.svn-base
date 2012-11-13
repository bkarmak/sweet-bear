/*
 * @(#) 锟斤拷锟�?�帮拷�?�锟斤拷锟斤拷楣�?拷锟斤拷锟斤拷锟斤拷锟�
 * 
 * $Id$
 * 
 * ===================================
 * Electric Operation Maintenance System(EOMS)
 * 
 * Copyright (c) 2006 bycom.wanhu.android.shelves.utilreserved.
 */
package com.wanhu.android.shelves.util;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

/**
 * 锟斤拷锟节诧拷锟斤拷锟斤拷啵拷锟�?�碉拷锟斤拷锟斤拷锟斤拷诘�?锟斤拷貌锟斤拷锟�
 * <p>
 * 锟节癸拷锟斤拷锟斤拷锟�?�撅拷锟斤拷使锟矫碉拷锟斤拷锟斤拷锟斤拷�?锟绞斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷�?锟斤拷一锟斤拷锟斤拷锟节的诧拷锟斤拷锟�?，
 * 锟斤拷锟斤拷锟斤拷志锟斤拷�?锟斤拷�?使锟斤拷 SimpleDateFormat锟侥讹拷锟斤拷锟绞�
 * <p>
 * 锟斤拷�?锟斤拷锟斤拷锟斤拷锟斤拷锟铰�?拷 锟斤拷锟节猴拷时锟斤拷模�? <br>
 * 锟斤拷锟节猴拷时锟斤拷锟绞斤拷锟斤拷锟斤拷诤锟绞憋拷锟侥Ｊ斤拷�?�锟街�?�拷锟斤拷锟斤拷锟斤拷锟斤拷诤锟绞憋拷锟侥Ｊ斤拷�?�锟斤拷校锟轿达拷锟斤拷锟脚碉拷锟斤拷�?
 * 'A' 锟斤拷 'Z' 锟斤拷 'a' 锟斤拷 'z'
 * 锟斤拷锟斤拷锟斤拷为模�?锟斤拷�?锟斤拷锟斤拷4锟斤拷示锟斤拷锟节伙拷时锟斤拷锟街凤拷元锟截★拷锟侥憋拷锟斤拷锟斤拷使锟矫碉拷锟斤拷锟�(')
 * 锟斤拷锟斤拷4锟斤拷锟斤拷锟斤拷锟斤拷薪锟斤拷汀锟�''"
 * 锟斤拷示锟斤拷锟斤拷拧锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷�?�锟斤拷锟斤拷�?�锟街伙拷锟斤拷诟锟绞斤拷锟绞憋拷锟斤拷锟斤拷羌虻ジ锟斤拷频锟斤拷锟斤拷锟街凤拷锟斤拷
 * 锟斤拷锟节凤拷锟斤拷时锟斤拷锟斤拷锟斤拷锟街凤拷锟斤拷锟狡ワ拷洹�
 * <p>
 * 锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷模�?锟斤拷�?锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟街凤拷 'A' 锟斤拷 'Z' 锟斤拷 'a' 锟斤拷 'z'
 * 锟斤拷锟斤拷锟斤拷锟斤拷锟�<br>
 * <table>
 * <tr>
 * <td>锟斤拷�?</td>
 * <td>锟斤拷锟节伙拷时锟斤拷元锟斤拷</td>
 * <td>锟斤拷示</td>
 * <td>示锟斤拷</td>
 * <td>
 * </tr>
 * <tr>
 * <td>G</td>
 * <td>Era</td>
 * <td>锟斤拷志锟斤拷</td>
 * <td>Text</td>
 * <td>AD</td>
 * <td>
 * </tr>
 * <tr>
 * <td>y</td>
 * <td>锟斤拷</td>
 * <td>Year</td>
 * <td>1996;</td>
 * <td>96</td>
 * <td>
 * </tr>
 * <tr>
 * <td>M</td>
 * <td>锟斤拷锟�?�碉拷锟铰凤拷</td>
 * <td>Month</td>
 * <td>July;</td>
 * <td>Jul;</td>
 * <td>07
 * </tr>
 * <tr>
 * <td>w</td>
 * <td>锟斤拷锟�?�碉拷锟斤拷锟斤拷</td>
 * <td>Number</td>
 * <td>27</td>
 * <td>
 * </tr>
 * <tr>
 * <td>W</td>
 * <td>锟铰凤拷锟�?�碉拷锟斤拷锟斤拷</td>
 * <td>Number</td>
 * <td>2</td>
 * <td>
 * </tr>
 * <tr>
 * <td>D</td>
 * <td>锟斤拷锟�?�碉拷锟斤拷锟斤拷</td>
 * <td>Number</td>
 * <td>189</td>
 * <td>
 * </tr>
 * <tr>
 * <td>d</td>
 * <td>锟铰凤拷锟�?�碉拷锟斤拷锟斤拷</td>
 * <td>Number</td>
 * <td>10</td>
 * <td>
 * </tr>
 * <tr>
 * <td>F</td>
 * <td>锟铰凤拷锟�?�碉拷锟斤拷锟斤拷</td>
 * <td>Number</td>
 * <td>2</td>
 * <td>
 * </tr>
 * <tr>
 * <td>E</td>
 * <td>锟斤拷锟斤拷锟�?�碉拷锟斤拷锟斤拷</td>
 * <td>Text</td>
 * <td>Tuesday;</td>
 * <td>Tue
 * </tr>
 * <tr>
 * <td>a</td>
 * <td>Am/pm</td>
 * <td>锟斤拷锟�/td>
 * <td>Text</td>
 * <td>PM</td>
 * <td>
 * </tr>
 * <tr>
 * <td>H</td>
 * <td>一锟斤拷锟�?�碉拷�?时锟斤拷0-23锟斤拷</td>
 * <td>Number</td>
 * <td>0
 * </tr>
 * <tr>
 * <td>k</td>
 * <td>一锟斤拷锟�?�碉拷�?时锟斤拷1-24锟斤拷</td>
 * <td>Number</td>
 * <td>24</td>
 * <td>
 * </tr>
 * <tr>
 * <td>K</td>
 * <td>am/pm</td>
 * <td>锟�?�碉拷�?时锟斤拷0-11锟斤拷</td>
 * <td>Number</td>
 * <td>0</td>
 * <td>
 * </tr>
 * <tr>
 * <td>h</td>
 * <td>am/pm</td>
 * <td>锟�?�碉拷�?时锟斤拷1-12锟斤拷</td>
 * <td>Number</td>
 * <td>12</td>
 * <td>
 * </tr>
 * <tr>
 * <td>m</td>
 * <td>�?时锟�?�的凤拷锟斤拷锟斤拷</td>
 * <td>Number</td>
 * <td>30</td>
 * <td>
 * </tr>
 * <tr>
 * <td>s</td>
 * <td>锟斤拷锟斤拷锟�?�碉拷锟斤拷锟斤拷</td>
 * <td>Number</td>
 * <td>55</td>
 * <td>
 * </tr>
 * <tr>
 * <td>S</td>
 * <td>锟斤拷锟斤拷锟斤拷</td>
 * <td>Number</td>
 * <td>978</td>
 * <td>
 * </tr>
 * <tr>
 * <td>z</td>
 * <td>时锟斤拷</td>
 * <td>General</td>
 * <td>time</td>
 * <td>zone</td>
 * <td>Pacific</td>
 * <td>Standard</td>
 * <td>Time;</td>
 * <td>PST;</td>
 * <td>GMT-08:00
 * </tr>
 * <tr>
 * <td>Z</td>
 * <td>时锟斤拷</td>
 * <td>RFC</td>
 * <td>822</td>
 * <td>time</td>
 * <td>zone</td>
 * <td>-0800</td>
 * <td>
 * </tr>
 * </table>
 * 
 * 模�?锟斤拷�?通锟斤拷锟斤拷锟截�?�拷锟侥�?拷锟斤拷锟斤拷确锟斤拷锟戒精确锟斤拷示锟斤拷
 * 
 */
public final class DateTools implements Serializable {
	/**
  * 
  */
	private static final long serialVersionUID = -3098985139095632110L;

	private DateTools() {
	}

	/**
	 * 锟斤拷�?锟斤拷锟斤拷锟斤拷锟斤拷示锟斤拷�?
	 * 
	 * @param sdate
	 *            原始锟斤拷锟节�?�拷�? s - 锟斤拷示 "yyyy-mm-dd" 锟斤拷�?锟斤拷锟斤拷锟节碉拷 String 锟斤拷锟斤拷
	 * @param format
	 *            锟斤拷�?锟斤拷锟斤拷锟斤拷锟节�?�拷�?
	 * @return 锟斤拷�?锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟绞�
	 */
	public static String dateFormat(String sdate, String format) {
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		java.sql.Date date = java.sql.Date.valueOf(sdate);
		String dateString = formatter.format(date);

		return dateString;
	}

	/**
	 * 锟斤拷}锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟�
	 * 
	 * @param sd
	 *            锟斤拷始锟斤拷锟节�?拷锟斤拷�?yyyy-MM-dd
	 * @param ed
	 *            锟斤拷止锟斤拷锟节�?拷锟斤拷�?yyyy-MM-dd
	 * @return 锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟�
	 */
	public static long getIntervalDays(String sd, String ed) {
		return ((java.sql.Date.valueOf(ed)).getTime() - (java.sql.Date
				.valueOf(sd)).getTime()) / (3600 * 24 * 1000);
	}

	/**
	 * 锟斤拷始锟斤拷锟斤拷yyyy-MM锟斤拷锟斤拷止锟斤拷yyyy-MM之锟斤拷锟饺碉拷锟斤拷锟斤拷
	 * 
	 * @param beginMonth
	 *            锟斤拷�?为yyyy-MM
	 * @param endMonth
	 *            锟斤拷�?为yyyy-MM
	 * @return 锟斤拷锟斤拷
	 */
	public static int getInterval(String beginMonth, String endMonth) {
		int intBeginYear = Integer.parseInt(beginMonth.substring(0, 4));
		int intBeginMonth = Integer.parseInt(beginMonth.substring(beginMonth
				.indexOf("-") + 1));
		int intEndYear = Integer.parseInt(endMonth.substring(0, 4));
		int intEndMonth = Integer.parseInt(endMonth.substring(endMonth
				.indexOf("-") + 1));

		return ((intEndYear - intBeginYear) * 12)
				+ (intEndMonth - intBeginMonth) + 1;
	}

	/**
	 * 锟斤拷莞锟侥凤拷锟斤拷�?锟矫匡拷始锟斤拷锟斤拷锟斤拷锟斤拷/时锟斤拷锟街凤拷锟斤拷锟界，时锟斤拷锟侥憋拷
	 * "07/10/96 4:5 PM, PDT" 锟斤拷锟斤拷锟缴碉拷�?�锟斤拷 Date(837039928046) 锟斤拷 Date锟斤拷
	 * 
	 * @param sDate
	 * @param dateFormat
	 * @return
	 */
	public static Date getDate(String sDate, String dateFormat) {
		SimpleDateFormat fmt = new SimpleDateFormat(dateFormat);
		ParsePosition pos = new ParsePosition(0);

		return fmt.parse(sDate, pos);
	}

	/**
	 * �?�锟矫碉拷�?锟斤拷锟节碉拷锟斤拷�?�锟斤拷锟統yyy锟斤拷�?锟斤拷锟斤拷.
	 * 
	 * @return 锟斤拷锟斤拷 yyyy
	 */
	public static String getCurrentYear() {
		return getFormatCurrentTime("yyyy");
	}

	/**
	 * 锟皆讹拷锟斤拷锟斤拷锟斤拷一锟疥。锟斤拷锟界当�?锟斤拷锟斤拷锟�007锟疥，锟斤拷么锟斤拷锟皆讹拷锟斤拷锟斤拷2006
	 * 
	 * @return 锟斤拷锟截斤拷锟侥�?�拷�?为 yyyy
	 */
	public static String getBeforeYear() {
		String currentYear = getFormatCurrentTime("yyyy");
		int beforeYear = Integer.parseInt(currentYear) - 1;
		return "" + beforeYear;
	}

	/**
	 * �?�锟矫碉拷�?锟斤拷锟节碉拷锟铰份�?拷锟斤拷MM锟斤拷�?锟斤拷锟斤拷.
	 * 
	 * @return 锟斤拷�?锟铰凤拷 MM
	 */
	public static String getCurrentMonth() {
		return getFormatCurrentTime("MM");
	}

	/**
	 * �?�锟矫碉拷�?锟斤拷锟节碉拷锟斤拷锟斤拷锟皆�?�拷�?"dd"锟斤拷锟斤拷.
	 * 
	 * @return 锟斤拷�?锟斤拷锟�?�碉拷�?锟斤拷dd
	 */
	public static String getCurrentDay() {
		return getFormatCurrentTime("dd");
	}

	/**
	 * 锟斤拷锟截碉拷�?时锟斤拷锟街凤拷
	 * <p>
	 * 锟斤拷�?锟斤拷yyyy-MM-dd
	 * 
	 * @return String 指锟斤拷锟斤拷�?锟斤拷锟斤拷锟斤拷锟街凤拷.
	 */
	public static String getCurrentDate() {
		return getFormatDateTime(new Date(), "yyyy-MM-dd");
	}

	/**
	 * 锟斤拷锟截碉拷�?指锟斤拷锟斤拷时锟斤拷a锟斤拷锟绞轿�?�yyy-MM-dd HH:mm:ss
	 * 
	 * @return 锟斤拷�?为yyyy-MM-dd HH:mm:ss锟斤拷锟�?�癸拷19�?锟斤拷
	 */
	public static String getCurrentDateTime() {
		return getFormatDateTime(new Date(), "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 锟斤拷锟截�?�拷时锟斤拷锟街凤拷
	 * <p>
	 * 锟斤拷�?锟斤拷yyyy-MM-dd
	 * 
	 * @param date
	 *            锟斤拷锟斤拷
	 * @return String 指锟斤拷锟斤拷�?锟斤拷锟斤拷锟斤拷锟街凤拷.
	 */
	public static String getFormatDate(Date date) {
		return getFormatDateTime(date, "yyyy-MM-dd");
	}

	/**
	 * 锟斤拷锟斤拷贫锟斤拷�?锟绞斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷�?�锟斤拷锟斤拷锟�007-05-05
	 * 
	 * @param format
	 *            "yyyy-MM-dd" 锟斤拷锟斤拷 "yyyy/MM/dd",锟斤拷然也锟斤拷锟斤拷锟角憋拷锟斤拷锟绞斤拷锟�
	 * @return 指锟斤拷锟斤拷�?锟斤拷锟斤拷锟斤拷锟街凤拷
	 */
	public static String getFormatDate(String format) {
		return getFormatDateTime(new Date(), format);
	}

	/**
	 * 锟斤拷锟截碉拷�?时锟斤拷锟街凤拷
	 * <p>
	 * 锟斤拷�?锟斤拷yyyy-MM-dd HH:mm:ss
	 * 
	 * @return String 指锟斤拷锟斤拷�?锟斤拷锟斤拷锟斤拷锟街凤拷.
	 */
	public static String getCurrentTime() {
		return getFormatDateTime(new Date(), "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 锟斤拷锟截�?�拷时锟斤拷锟街凤拷
	 * <p>
	 * 锟斤拷�?锟斤拷yyyy-MM-dd HH:mm:ss
	 * 
	 * @param date
	 *            锟斤拷锟斤拷
	 * @return String 指锟斤拷锟斤拷�?锟斤拷锟斤拷锟斤拷锟街凤拷.
	 */
	public static String getFormatTime(Date date) {
		return getFormatDateTime(date, "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 锟斤拷锟截�?�拷锟绞憋拷锟斤拷�?�锟�
	 * <p>
	 * 锟斤拷�?锟斤拷yyyy-MM-dd
	 * 
	 * @param date
	 *            锟斤拷锟斤拷
	 * @return String 指锟斤拷锟斤拷�?锟斤拷锟斤拷锟斤拷锟街凤拷.
	 */
	public static String getFormatShortTime(Date date) {
		return getFormatDateTime(date, "yyyy-MM-dd");
	}

	/**
	 * 锟斤拷莞锟侥�?�拷�?锟斤拷锟斤拷锟斤拷时锟斤拷锟街凤拷
	 * <p>
	 * 锟斤拷�?锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟剿碉拷锟�锟酵凤拷锟斤拷getFormatDate锟斤拷一锟斤拷摹锟�
	 * 
	 * @param format
	 *            锟斤拷锟节�?�拷�?锟街凤拷
	 * @return String 指锟斤拷锟斤拷�?锟斤拷锟斤拷锟斤拷锟街凤拷.
	 */
	public static String getFormatCurrentTime(String format) {
		return getFormatDateTime(new Date(), format);
	}

	/**
	 * 锟斤拷莞锟侥�?�拷�?锟斤拷时锟斤拷(Date锟斤拷锟酵碉拷)锟斤拷锟斤拷锟斤拷时锟斤拷锟街凤拷锟斤拷为通锟矫★拷<br>
	 * 
	 * @param date
	 *            指锟斤拷锟斤拷锟斤拷锟斤拷
	 * @param format
	 *            锟斤拷锟节�?�拷�?锟街凤拷
	 * @return String 指锟斤拷锟斤拷�?锟斤拷锟斤拷锟斤拷锟街凤拷.
	 */
	public static String getFormatDateTime(Date date, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}

	/**
	 * �?�锟斤拷指锟斤拷锟斤拷锟斤拷锟秸碉拷锟斤拷锟节讹拷锟斤拷.
	 * 
	 * @param year
	 *            锟斤拷
	 * @param month
	 *            锟斤拷注锟斤拷锟角达拷1锟斤拷12
	 * @param day
	 *            锟斤拷
	 * @return 一锟斤拷java.util.Date()锟斤拷锟酵的讹拷锟斤拷
	 */
	public static Date getDateObj(int year, int month, int day) {
		Calendar c = new GregorianCalendar();
		c.set(year, month - 1, day);
		return c.getTime();
	}

	/**
	 * 锟斤拷�?�指锟斤拷锟斤拷锟节碉拷锟斤拷一锟届。
	 * 
	 * @param date
	 *            yyyy/MM/dd
	 * @return yyyy/MM/dd
	 */
	public static String getDateTomorrow(String date) {

		Date tempDate = null;
		if (date.indexOf("/") > 0)
			tempDate = getDateObj(date, "[/]");
		if (date.indexOf("-") > 0)
			tempDate = getDateObj(date, "[-]");
		tempDate = getDateAdd(tempDate, 1);
		return getFormatDateTime(tempDate, "yyyy/MM/dd");
	}

	/**
	 * 锟斤拷�?�锟斤拷指锟斤拷锟斤拷锟斤拷锟斤拷锟街�?�拷锟斤拷锟斤拷锟斤拷锟斤拷锟节★拷
	 * 
	 * @param date
	 *            yyyy/MM/dd
	 * @param offset
	 *            锟斤拷锟斤拷锟斤拷
	 * @return yyyy/MM/dd
	 */
	public static String getDateOffset(String date, int offset) {

		// Date tempDate = getDateObj(date, "[/]");
		Date tempDate = null;
		if (date.indexOf("/") > 0)
			tempDate = getDateObj(date, "[/]");
		if (date.indexOf("-") > 0)
			tempDate = getDateObj(date, "[-]");
		tempDate = getDateAdd(tempDate, offset);
		return getFormatDateTime(tempDate, "yyyy/MM/dd");
	}

	/**
	 * �?�锟斤拷指锟斤拷锟街�?�拷锟街�?�拷锟斤拷锟斤拷锟斤拷�?锟斤拷锟斤拷诙锟斤拷锟�
	 * 
	 * @param argsDate
	 *            锟斤拷�?为"yyyy-MM-dd"
	 * @param split
	 *            时锟斤拷锟绞斤拷募锟斤拷锟斤拷锟斤拷纭�锟斤拷锟斤拷锟斤拷/锟斤拷锟斤拷�?锟斤拷时锟斤拷一锟斤拷锟斤拷4锟斤拷
	 * @return 一锟斤拷java.util.Date()锟斤拷锟酵的讹拷锟斤拷
	 */
	public static Date getDateObj(String argsDate, String split) {
		String[] temp = argsDate.split(split);
		int year = new Integer(temp[0]).intValue();
		int month = new Integer(temp[1]).intValue();
		int day = new Integer(temp[2]).intValue();
		return getDateObj(year, month, day);
	}

	/**
	 * �?�锟矫�?�拷锟街凤拷锟斤拷锟斤拷锟斤拷锟斤拷诙锟斤拷锟斤拷锟斤拷锟侥Ｊ斤拷锟斤拷锟絧attern指锟斤拷锟侥�?�拷�?.
	 * 
	 * @param dateStr
	 *            锟斤拷锟斤拷锟斤拷锟斤拷 锟接�?�拷锟街凤拷目锟绞硷拷锟斤拷锟斤拷谋锟斤拷锟斤拷锟斤拷锟斤拷一锟斤拷锟斤拷锟节★
	 *            拷锟矫凤拷锟斤拷锟斤拷使锟矫�?�拷锟街凤拷锟斤拷锟斤拷锟侥憋拷锟斤拷
	 *            锟�?�癸拷锟斤拷锟节凤拷锟斤拷�?锟斤拷锟斤拷�?�锟斤拷锟斤拷锟斤拷锟�parse(String, ParsePosition)
	 *            锟斤拷锟斤拷锟斤拷一锟斤拷 String锟斤拷应锟斤拷锟戒开始锟斤拷锟斤拷锟�?�凤拷锟斤拷
	 * 
	 * @param pattern
	 *            锟斤拷锟斤拷模�?
	 * @return 锟斤拷锟街凤拷锟斤拷锟斤拷锟斤拷锟斤拷诙锟斤拷锟�
	 */
	public static Date getDateFromString(String dateStr, String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		Date resDate = null;
		try {
			resDate = sdf.parse(dateStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resDate;
	}

	/**
	 * �?�锟矫碉拷�?Date锟斤拷锟斤拷.
	 * 
	 * @return Date 锟斤拷�?Date锟斤拷锟斤拷.
	 */
	public static Date getDateObj() {
		Calendar c = new GregorianCalendar();
		return c.getTime();
	}

	/**
	 * 
	 * @return 锟斤拷�?锟铰凤拷锟�?�讹拷锟斤拷锟届；
	 */
	public static int getDaysOfCurMonth() {
		int curyear = new Integer(getCurrentYear()).intValue(); // 锟斤拷�?锟斤拷锟�
		int curMonth = new Integer(getCurrentMonth()).intValue();// 锟斤拷�?锟铰凤拷
		int mArray[] = new int[] { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30,
				31 };
		// 锟�?�讹拷锟斤拷锟斤拷锟斤拷锟斤拷 锟斤拷2锟铰凤拷锟斤拷29锟届；
		if ((curyear % 400 == 0)
				|| ((curyear % 100 != 0) && (curyear % 4 == 0))) {
			mArray[1] = 29;
		}
		return mArray[curMonth - 1];
		// 锟斤拷锟�?��?�拷锟斤拷锟斤拷赂锟斤拷碌锟斤拷锟斤拷锟阶拷�?�︼拷锟斤拷路锟�2锟斤拷锟斤拷锟斤拷止锟斤拷锟斤拷越锟界；
		// 锟斤拷锟�?��?�拷锟斤拷锟斤拷细锟斤拷碌锟斤拷锟斤拷锟阶拷�?�︼拷锟斤拷路锟�锟斤拷锟斤拷锟斤拷止锟斤拷锟斤拷越锟界；
	}

	/**
	 * 锟斤拷锟街�?�拷锟斤拷锟斤拷锟斤拷锟�锟斤拷锟斤拷指锟斤拷锟铰份�?拷yyyy-MM锟斤拷锟�?�讹拷锟斤拷锟届。
	 * 
	 * @param time
	 *            yyyy-MM
	 * @return 锟斤拷锟斤拷指锟斤拷锟铰份碉拷锟斤拷锟斤拷
	 */
	public static int getDaysOfCurMonth(final String time) {
		if (time.length() != 7) {
			throw new NullPointerException("锟斤拷锟斤拷�?锟绞斤拷锟斤拷锟斤拷锟統yyy-MM");
		}
		String[] timeArray = time.split("-");
		int curyear = new Integer(timeArray[0]).intValue(); // 锟斤拷�?锟斤拷锟�
		int curMonth = new Integer(timeArray[1]).intValue();// 锟斤拷�?锟铰凤拷
		if (curMonth > 12) {
			throw new NullPointerException(
					"锟斤拷锟斤拷�?锟绞斤拷锟斤拷锟斤拷锟統yyy-MM锟斤拷锟斤拷锟斤拷锟铰份憋拷锟斤拷�?锟节碉拷锟斤拷12锟斤拷");
		}
		int mArray[] = new int[] { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30,
				31 };
		// 锟�?�讹拷锟斤拷锟斤拷锟斤拷锟斤拷 锟斤拷2锟铰凤拷锟斤拷29锟届；
		if ((curyear % 400 == 0)
				|| ((curyear % 100 != 0) && (curyear % 4 == 0))) {
			mArray[1] = 29;
		}
		if (curMonth == 12) {
			return mArray[0];
		}
		return mArray[curMonth - 1];
		// 锟斤拷锟�?��?�拷锟斤拷锟斤拷赂锟斤拷碌锟斤拷锟斤拷锟阶拷�?�︼拷锟斤拷路锟�2锟斤拷锟斤拷锟斤拷止锟斤拷锟斤拷越锟界；
		// 锟斤拷锟�?��?�拷锟斤拷锟斤拷细锟斤拷碌锟斤拷锟斤拷锟阶拷�?�︼拷锟斤拷路锟�锟斤拷锟斤拷锟斤拷止锟斤拷锟斤拷越锟界；
	}

	/**
	 * 锟斤拷锟斤拷指锟斤拷为锟斤拷锟轿�?�ear锟铰讹拷month锟斤拷锟铰凤拷锟节�?拷锟斤拷weekOfMonth锟斤拷锟斤拷锟节的碉拷dayOfWeek锟斤拷锟角碉拷锟铰的硷拷锟脚
	 * � 锟�br> 00 00 00 01 02 03 04 <br>
	 * 05 06 07 08 09 10 11<br>
	 * 12 13 14 15 16 17 18<br>
	 * 19 20 21 22 23 24 25<br>
	 * 26 27 28 29 30 31 <br>
	 * 2006锟斤拷牡锟�?�伙拷锟斤拷艿锟�锟斤拷7锟斤拷为锟斤拷05 06 07 01 02 03 04 <br>
	 * 2006锟斤拷牡诙锟斤拷锟斤拷艿锟�锟斤拷7锟斤拷为锟斤拷12 13 14 08 09 10 11 <br>
	 * 2006锟斤拷牡锟斤拷锟斤拷锟�?�碉拷1锟斤拷7锟斤拷为锟斤拷19 20 21 15 16 17 18 <br>
	 * 2006锟斤拷牡锟斤拷�?锟斤拷艿锟�锟斤拷7锟斤拷为锟斤拷26 27 28 22 23 24 25 <br>
	 * 2006锟斤拷牡锟斤拷锟斤拷锟�?�碉拷1锟斤拷7锟斤拷为锟斤拷02 03 04 29 30 31 01
	 * 锟斤拷锟斤拷锟斤拷没锟�?�撅拷锟皆讹拷转锟斤拷锟铰�?�拷锟斤拷锟剿★拷
	 * 
	 * @param year
	 *            锟斤拷�?为yyyy <br>
	 * @param month
	 *            锟斤拷�?为MM,锟斤拷锟斤拷值锟斤拷[1-12]锟斤拷<br>
	 * @param weekOfMonth
	 *            锟斤拷[1-6],锟斤拷为一锟斤拷锟斤拷锟斤拷锟斤拷锟�锟斤拷锟�?�★拷<br>
	 * @param dayOfWeek
	 *            锟斤拷锟斤拷锟斤拷1锟斤拷7之锟戒，锟斤拷(1锟斤拷7锟斤拷1锟斤拷示锟斤拷锟斤拷锟届，7锟斤拷示锟斤拷锟斤拷锟斤拷<br>
	 *            -6为锟斤拷锟斤拷锟斤拷-1为锟斤拷锟斤拷锟藉，0为锟斤拷锟斤拷锟斤拷 <br>
	 * @return <type>int</type>
	 */
	public static int getDayofWeekInMonth(String year, String month,
			String weekOfMonth, String dayOfWeek) {
		Calendar cal = new GregorianCalendar();
		// 锟节撅拷锟斤拷默锟斤拷锟斤拷锟皆伙拷锟斤拷锟斤拷默锟斤拷时锟斤拷锟斤拷使锟矫碉拷�?时锟戒构锟斤拷一锟斤拷默锟较碉拷
		// GregorianCalendar锟斤拷
		int y = new Integer(year).intValue();
		int m = new Integer(month).intValue();
		cal.clear();// 锟斤拷锟斤拷锟斤拷锟斤拷�?锟斤拷锟斤拷锟斤拷
		cal.set(y, m - 1, 1);// 锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷为锟斤拷锟铰的碉拷一锟届。
		cal.set(Calendar.DAY_OF_WEEK_IN_MONTH,
				new Integer(weekOfMonth).intValue());
		cal.set(Calendar.DAY_OF_WEEK, new Integer(dayOfWeek).intValue());
		// System.out.print(cal.get(Calendar.MONTH)+" ");
		// System.out.print("锟斤拷"+cal.get(Calendar.WEEK_OF_MONTH)+"\t");
		// WEEK_OF_MONTH锟斤拷示锟斤拷锟斤拷锟节憋拷锟铰的第硷拷锟斤拷锟�?�★拷锟斤拷锟斤拷1锟斤拷锟斤拷锟斤拷锟节硷拷锟斤拷锟斤拷锟斤拷示锟节憋拷锟铰的碉拷一锟斤拷锟斤拷
		return cal.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * 锟斤拷锟街�?�拷锟斤拷锟斤拷锟斤拷锟斤拷锟�?�∈憋拷锟斤拷耄拷锟斤拷锟�?�伙拷锟絡ava.Util.Date锟斤拷锟斤拷
	 * 
	 * @param year
	 *            锟斤拷
	 * @param month
	 *            锟斤拷 0-11
	 * @param date
	 *            锟斤拷
	 * @param hourOfDay
	 *            �?时 0-23
	 * @param minute
	 *            锟斤拷 0-59
	 * @param second
	 *            锟斤拷 0-59
	 * @return 一锟斤拷Date锟斤拷锟斤拷
	 */
	public static Date getDate(int year, int month, int date, int hourOfDay,
			int minute, int second) {
		Calendar cal = new GregorianCalendar();
		cal.set(year, month, date, hourOfDay, minute, second);
		return cal.getTime();
	}

	/**
	 * 锟斤拷锟街�?�拷锟斤拷锟斤拷�?拷隆锟斤拷辗锟斤拷�?锟角帮拷锟斤拷锟斤拷诩锟斤拷锟�锟斤拷示锟斤拷锟斤拷锟届�?2
	 * 锟斤拷示锟斤拷锟斤拷一锟斤拷7锟斤拷示锟斤拷锟斤拷锟斤拷
	 * 
	 * @param year
	 * @param month
	 *            month锟角达拷1锟斤拷始锟斤拷12锟斤拷锟斤拷
	 * @param day
	 * @return 锟斤拷锟斤拷一锟斤拷锟�锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟节硷拷锟斤拷锟斤拷锟街★拷1锟斤拷示锟斤拷锟斤拷锟届�?2
	 *         锟斤拷示锟斤拷锟斤拷一锟斤拷7锟斤拷示锟斤拷锟斤拷锟斤拷
	 */
	public static int getDayOfWeek(String year, String month, String day) {
		Calendar cal = new GregorianCalendar(new Integer(year).intValue(),
				new Integer(month).intValue() - 1, new Integer(day).intValue());
		return cal.get(Calendar.DAY_OF_WEEK);
	}

	/**
	 * 锟斤拷锟街�?�拷锟斤拷锟斤拷�?拷隆锟斤拷辗锟斤拷�?锟角帮拷锟斤拷锟斤拷诩锟斤拷锟�锟斤拷示锟斤拷锟斤拷锟届�?2
	 * 锟斤拷示锟斤拷锟斤拷一锟斤拷7锟斤拷示锟斤拷锟斤拷锟斤拷
	 * 
	 * @param date
	 *            "yyyy/MM/dd",锟斤拷锟斤拷"yyyy-MM-dd"
	 * @return 锟斤拷锟斤拷一锟斤拷锟�锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟节硷拷锟斤拷锟斤拷锟街★拷1锟斤拷示锟斤拷锟斤拷锟届�?2
	 *         锟斤拷示锟斤拷锟斤拷一锟斤拷7锟斤拷示锟斤拷锟斤拷锟斤拷
	 */
	public static int getDayOfWeek(String date) {
		String[] temp = null;
		if (date.indexOf("/") > 0) {
			temp = date.split("/");
		}
		if (date.indexOf("-") > 0) {
			temp = date.split("-");
		}
		return getDayOfWeek(temp[0], temp[1], temp[2]);
	}

	/**
	 * 锟斤拷锟截碉拷�?锟斤拷锟斤拷锟斤拷锟斤拷锟节硷拷锟斤拷锟斤拷锟界：锟斤拷锟斤拷锟秸★拷锟斤拷锟斤拷一锟斤拷锟斤拷锟斤拷锟斤拷鹊�?�锟�
	 * 
	 * @param date
	 *            锟斤拷�?为 yyyy/MM/dd 锟斤拷锟斤拷 yyyy-MM-dd
	 * @return 锟斤拷锟截碉拷�?锟斤拷锟斤拷锟斤拷锟斤拷锟节硷拷
	 */
	public static String getChinaDayOfWeek(String date) {
		String[] weeks = new String[] { "锟斤拷锟斤拷锟斤拷", "锟斤拷锟斤拷一", "锟斤拷锟节讹拷",
				"锟斤拷锟斤拷锟斤拷", "锟斤拷锟斤拷锟斤拷", "锟斤拷锟斤拷锟斤拷", "锟斤拷锟斤拷锟斤拷" };
		int week = getDayOfWeek(date);
		return weeks[week - 1];
	}

	/**
	 * 锟斤拷锟街�?�拷锟斤拷锟斤拷�?拷隆锟斤拷辗锟斤拷�?锟角帮拷锟斤拷锟斤拷诩锟斤拷锟�锟斤拷示锟斤拷锟斤拷锟届�?2
	 * 锟斤拷示锟斤拷锟斤拷一锟斤拷7锟斤拷示锟斤拷锟斤拷锟斤拷
	 * 
	 * @param date
	 * 
	 * @return 锟斤拷锟斤拷一锟斤拷锟�锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟节硷拷锟斤拷锟斤拷锟街★拷1锟斤拷示锟斤拷锟斤拷锟届�?2
	 *         锟斤拷示锟斤拷锟斤拷一锟斤拷7锟斤拷示锟斤拷锟斤拷锟斤拷
	 */
	public static int getDayOfWeek(Date date) {
		Calendar cal = new GregorianCalendar();
		cal.setTime(date);
		return cal.get(Calendar.DAY_OF_WEEK);
	}

	/**
	 * 锟斤拷锟斤拷锟狡讹拷锟斤拷锟斤拷锟斤拷锟节碉拷锟斤拷锟斤拷一锟斤拷锟�?�的第硷拷锟斤拷锟�?�★拷<br>
	 * created by wangmj at 20060324.<br>
	 * 
	 * @param year
	 * @param month
	 *            锟斤拷围1-12<br>
	 * @param day
	 * @return int
	 */
	public static int getWeekOfYear(String year, String month, String day) {
		Calendar cal = new GregorianCalendar();
		cal.clear();
		cal.set(new Integer(year).intValue(),
				new Integer(month).intValue() - 1, new Integer(day).intValue());
		return cal.get(Calendar.WEEK_OF_YEAR);
	}

	/**
	 * �?�锟矫�?�拷锟斤拷锟节硷拷锟斤拷一锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟节讹拷锟斤拷.
	 * 
	 * @param date
	 *            锟斤拷锟斤拷锟斤拷诙锟斤拷锟�
	 * @param amount
	 *            锟斤拷�?锟斤拷拥锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷�?锟斤拷锟斤拷锟斤拷使锟矫�?�拷锟斤拷涂锟斤拷锟�
	 * @return Date 锟斤拷锟斤拷一锟斤拷锟斤拷锟斤拷锟皆猴拷锟紻ate锟斤拷锟斤拷.
	 */
	public static Date getDateAdd(Date date, int amount) {
		Calendar cal = new GregorianCalendar();
		cal.setTime(date);
		cal.add(GregorianCalendar.DATE, amount);
		return cal.getTime();
	}

	/**
	 * �?�锟矫�?�拷锟斤拷锟节硷拷锟斤拷一锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟节讹拷锟斤拷.
	 * 
	 * @param date
	 *            锟斤拷锟斤拷锟斤拷诙锟斤拷锟�
	 * @param amount
	 *            锟斤拷�?锟斤拷拥锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷�?锟斤拷锟斤拷锟斤拷使锟矫�?�拷锟斤拷涂锟斤拷锟�
	 * @param format
	 *            锟斤拷锟斤拷�?.
	 * @return Date 锟斤拷锟斤拷一锟斤拷锟斤拷锟斤拷锟皆猴拷锟紻ate锟斤拷锟斤拷.
	 */
	public static String getFormatDateAdd(Date date, int amount, String format) {
		Calendar cal = new GregorianCalendar();
		cal.setTime(date);
		cal.add(GregorianCalendar.DATE, amount);
		return getFormatDateTime(cal.getTime(), format);
	}

	/**
	 * 锟斤拷玫锟角帮拷锟斤拷诠潭锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷冢锟斤拷锟角�0锟斤拷dateAdd(-60)
	 * 
	 * @param amount
	 *            锟斤拷锟斤拷锟侥硷拷锟斤拷锟斤拷诔锟斤拷龋锟斤拷锟角拔�?�拷锟斤拷锟斤拷锟斤拷为锟斤拷
	 * @param format
	 *            锟斤拷锟斤拷锟斤拷诘�?锟绞�
	 * @return java.lang.String 锟斤拷锟秸�?�拷�?锟斤拷锟侥硷拷锟斤拷锟斤拷锟斤拷锟街凤拷.
	 */
	public static String getFormatCurrentAdd(int amount, String format) {

		Date d = getDateAdd(new Date(), amount);

		return getFormatDateTime(d, format);
	}

	/**
	 * �?�锟矫�?�拷锟绞斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟�
	 * 
	 * @param format
	 *            锟斤拷锟斤拷锟斤拷锟侥�?�拷�?
	 * @return String 锟斤拷锟绞斤拷锟斤拷锟斤拷锟斤拷�?�锟�
	 */
	public static String getFormatYestoday(String format) {
		return getFormatCurrentAdd(-1, format);
	}

	/**
	 * 锟斤拷锟斤拷指锟斤拷锟斤拷锟节碉拷�?一锟届。<br>
	 * 
	 * @param sourceDate
	 * @param format
	 *            yyyy MM dd hh mm ss
	 * @return 锟斤拷锟斤拷锟斤拷锟斤拷锟街凤拷锟斤拷�?锟斤拷formcat一锟铰★拷
	 */
	public static String getYestoday(String sourceDate, String format) {
		return getFormatDateAdd(getDateFromString(sourceDate, format), -1,
				format);
	}

	/**
	 * 锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷冢锟�br>
	 * 
	 * @param format
	 * @return 锟斤拷锟斤拷锟斤拷锟斤拷锟街凤拷锟斤拷�?锟斤拷formcat一锟铰★拷
	 */
	public static String getFormatTomorrow(String format) {
		return getFormatCurrentAdd(1, format);
	}

	/**
	 * 锟斤拷锟斤拷指锟斤拷锟斤拷锟节的猴拷一锟届。<br>
	 * 
	 * @param sourceDate
	 * @param format
	 * @return 锟斤拷锟斤拷锟斤拷锟斤拷锟街凤拷锟斤拷�?锟斤拷formcat一锟铰★拷
	 */
	public static String getFormatDateTommorrow(String sourceDate, String format) {
		return getFormatDateAdd(getDateFromString(sourceDate, format), 1,
				format);
	}

	/**
	 * 锟斤拷锟斤拷锟斤拷锟侥拷锟�TimeZone锟斤拷4锟斤拷锟街�?�拷锟斤拷锟绞斤拷锟绞憋拷锟斤拷�?�锟�
	 * 
	 * @param dateFormat
	 * @return 锟斤拷锟斤拷锟斤拷锟斤拷锟街凤拷锟斤拷�?锟斤拷formcat一锟铰★拷
	 */
	public static String getCurrentDateString(String dateFormat) {
		Calendar cal = Calendar.getInstance(TimeZone.getDefault());
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		sdf.setTimeZone(TimeZone.getDefault());

		return sdf.format(cal.getTime());
	}

	// /**
	// * @deprecated 锟斤拷锟斤拷锟斤拷使锟矫★拷 锟斤拷锟截碉拷�?时锟戒串
	// 锟斤拷�?:yyMMddhhmmss,锟斤拷锟较达拷锟斤拷锟斤拷时使锟斤拷
	// *
	// * @return String
	// */
	// public static String getCurDate() {
	// GregorianCalendar gcDate = new GregorianCalendar();
	// int year = gcDate.get(GregorianCalendar.YEAR);
	// int month = gcDate.get(GregorianCalendar.MONTH) + 1;
	// int day = gcDate.get(GregorianCalendar.DAY_OF_MONTH);
	// int hour = gcDate.get(GregorianCalendar.HOUR_OF_DAY);
	// int minute = gcDate.get(GregorianCalendar.MINUTE);
	// int sen = gcDate.get(GregorianCalendar.SECOND);
	// String y;
	// String m;
	// String d;
	// String h;
	// String n;
	// String s;
	// y = new Integer(year).toString();
	//
	// if (month < 10) {
	// m = "0" + new Integer(month).toString();
	// } else {
	// m = new Integer(month).toString();
	// }
	//
	// if (day < 10) {
	// d = "0" + new Integer(day).toString();
	// } else {
	// d = new Integer(day).toString();
	// }
	//
	// if (hour < 10) {
	// h = "0" + new Integer(hour).toString();
	// } else {
	// h = new Integer(hour).toString();
	// }
	//
	// if (minute < 10) {
	// n = "0" + new Integer(minute).toString();
	// } else {
	// n = new Integer(minute).toString();
	// }
	//
	// if (sen < 10) {
	// s = "0" + new Integer(sen).toString();
	// } else {
	// s = new Integer(sen).toString();
	// }
	//
	// return "" + y + m + d + h + n + s;
	// }

	/**
	 * 锟斤拷莞锟侥�?�拷�?锟斤拷锟斤拷锟斤拷时锟斤拷锟街凤拷 锟斤拷getFormatDate(String format)锟斤拷锟狡★拷
	 * 
	 * @param format
	 *            yyyy MM dd hh mm ss
	 * @return 锟斤拷锟斤拷一锟斤拷时锟斤拷锟街凤拷
	 */
	public static String getCurTimeByFormat(String format) {
		Date newdate = new Date(System.currentTimeMillis());
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(newdate);
	}

	/**
	 * 锟斤拷�?�}锟斤拷时锟戒串时锟斤拷牟锟街碉拷锟斤拷锟轿晃�?�拷锟�
	 * 
	 * @param startTime
	 *            锟斤拷始时锟斤拷 yyyy-MM-dd HH:mm:ss
	 * @param endTime
	 *            锟斤拷锟斤拷时锟斤拷 yyyy-MM-dd HH:mm:ss
	 * @return 锟斤拷时锟斤拷牟锟街�锟斤拷)
	 */
	public static long getDiff(String startTime, String endTime) {
		long diff = 0;
		SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			Date startDate = ft.parse(startTime);
			Date endDate = ft.parse(endTime);
			diff = startDate.getTime() - endDate.getTime();
			diff = diff / 1000;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return diff;
	}

	/**
	 * 锟斤拷�?��?时/锟斤拷锟斤拷/锟斤拷
	 * 
	 * @param second
	 *            锟斤拷
	 * @return 锟斤拷�?时锟斤拷锟斤拷锟接★拷锟斤拷锟绞憋拷锟斤拷�?�锟斤拷锟斤拷锟��?时23锟斤拷锟斤拷13锟诫。
	 */
	public static String getHour(long second) {
		long hour = second / 60 / 60;
		long minute = (second - hour * 60 * 60) / 60;
		long sec = (second - hour * 60 * 60) - minute * 60;

		return hour + "�?时" + minute + "锟斤拷锟斤拷" + sec + "锟斤拷";

	}

	/**
	 * 锟斤拷锟斤拷指锟斤拷时锟斤拷锟街凤拷
	 * <p>
	 * 锟斤拷�?锟斤拷yyyy-MM-dd HH:mm:ss
	 * 
	 * @return String 指锟斤拷锟斤拷�?锟斤拷锟斤拷锟斤拷锟街凤拷.
	 */
	public static String getDateTime(long microsecond) {
		return getFormatDateTime(new Date(microsecond), "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 锟斤拷锟截碉拷�?时锟斤拷锟绞碉拷锟�?�∈憋拷锟斤拷锟斤拷锟斤拷时锟戒。
	 * <p>
	 * 锟斤拷�?锟斤拷yyyy-MM-dd HH:mm:ss
	 * 
	 * @return Float 锟接硷拷实锟斤拷�?时.
	 */
	public static String getDateByAddFltHour(float flt) {
		int addMinute = (int) (flt * 60);
		Calendar cal = new GregorianCalendar();
		cal.setTime(new Date());
		cal.add(GregorianCalendar.MINUTE, addMinute);
		return getFormatDateTime(cal.getTime(), "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 锟斤拷锟斤拷指锟斤拷时锟斤拷锟街�?�拷锟�?�∈憋拷锟斤拷锟斤拷锟斤拷锟绞憋拷洹�
	 * <p>
	 * 锟斤拷�?锟斤拷yyyy-MM-dd HH:mm:ss
	 * 
	 * @return 时锟斤拷.
	 */
	public static String getDateByAddHour(String datetime, int minute) {
		String returnTime = null;
		Calendar cal = new GregorianCalendar();
		SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date;
		try {
			date = ft.parse(datetime);
			cal.setTime(date);
			cal.add(GregorianCalendar.MINUTE, minute);
			returnTime = getFormatDateTime(cal.getTime(), "yyyy-MM-dd HH:mm:ss");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return returnTime;

	}

	/**
	 * 锟斤拷�?�}锟斤拷时锟戒串时锟斤拷牟锟街碉拷锟斤拷锟轿晃∈�
	 * 
	 * @param startTime
	 *            锟斤拷始时锟斤拷 yyyy-MM-dd HH:mm:ss
	 * @param endTime
	 *            锟斤拷锟斤拷时锟斤拷 yyyy-MM-dd HH:mm:ss
	 * @return 锟斤拷时锟斤拷牟锟街�锟斤拷)
	 */
	public static int getDiffHour(String startTime, String endTime) {
		long diff = 0;
		SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			Date startDate = ft.parse(startTime);
			Date endDate = ft.parse(endTime);
			diff = startDate.getTime() - endDate.getTime();
			diff = diff / (1000 * 60 * 60);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return new Long(diff).intValue();
	}

	/**
	 * 锟斤拷锟斤拷锟斤拷莸锟斤拷锟�锟斤拷
	 * 
	 * @param selectName
	 *            锟斤拷-锟斤拷锟斤拷锟�
	 * @param value
	 *            锟斤拷�?锟斤拷-锟斤拷锟街�
	 * @param startYear
	 *            锟斤拷始锟斤拷锟�
	 * @param endYear
	 *            锟斤拷锟斤拷锟斤拷锟�
	 * @return 锟斤拷锟斤拷锟�锟斤拷锟絟tml
	 */
	public static String getYearSelect(String selectName, String value,
			int startYear, int endYear) {
		int start = startYear;
		int end = endYear;
		if (startYear > endYear) {
			start = endYear;
			end = startYear;
		}
		StringBuffer sb = new StringBuffer("");
		sb.append("<select name=\"" + selectName + "\">");
		for (int i = start; i <= end; i++) {
			if (!value.trim().equals("") && i == Integer.parseInt(value)) {
				sb.append("<option value=\"" + i + "\" selected>" + i
						+ "</option>");
			} else {
				sb.append("<option value=\"" + i + "\">" + i + "</option>");
			}
		}
		sb.append("</select>");
		return sb.toString();
	}

	/**
	 * 锟斤拷锟斤拷锟斤拷莸锟斤拷锟�锟斤拷
	 * 
	 * @param selectName
	 *            锟斤拷-锟斤拷锟斤拷锟�
	 * @param value
	 *            锟斤拷�?锟斤拷-锟斤拷锟街�
	 * @param startYear
	 *            锟斤拷始锟斤拷锟�
	 * @param endYear
	 *            锟斤拷锟斤拷锟斤拷锟�
	 *            锟斤拷锟界开始锟斤拷锟轿�001锟斤拷锟斤拷锟斤拷锟轿�005锟斤拷么锟斤拷-锟斤拷锟斤拷锟斤拷锟斤拷值锟斤拷锟斤拷2001锟斤拷2002锟
	 *            � 锟�003锟斤拷2004锟斤拷2005锟斤拷锟斤拷
	 * @return 锟斤拷锟斤拷锟斤拷莸锟斤拷锟�锟斤拷锟絟tml锟斤拷
	 */
	public static String getYearSelect(String selectName, String value,
			int startYear, int endYear, boolean hasBlank) {
		int start = startYear;
		int end = endYear;
		if (startYear > endYear) {
			start = endYear;
			end = startYear;
		}
		StringBuffer sb = new StringBuffer("");
		sb.append("<select name=\"" + selectName + "\">");
		if (hasBlank) {
			sb.append("<option value=\"\"></option>");
		}
		for (int i = start; i <= end; i++) {
			if (!value.trim().equals("") && i == Integer.parseInt(value)) {
				sb.append("<option value=\"" + i + "\" selected>" + i
						+ "</option>");
			} else {
				sb.append("<option value=\"" + i + "\">" + i + "</option>");
			}
		}
		sb.append("</select>");
		return sb.toString();
	}

	/**
	 * 锟斤拷锟斤拷锟斤拷莸锟斤拷锟�锟斤拷
	 * 
	 * @param selectName
	 *            锟斤拷-锟斤拷锟斤拷锟�
	 * @param value
	 *            锟斤拷�?锟斤拷-锟斤拷锟街�
	 * @param startYear
	 *            锟斤拷始锟斤拷锟�
	 * @param endYear
	 *            锟斤拷锟斤拷锟斤拷锟�
	 * @param js
	 *            锟斤拷锟斤拷锟絡s为js锟街凤拷锟斤拷锟斤拷 " onchange=\"changeYear()\" "
	 *            ,锟斤拷锟斤拷锟轿猴拷js锟侥凤拷锟斤拷锟酵匡拷锟斤拷锟斤拷jsp页锟斤拷锟�?�憋拷写锟斤拷锟斤拷锟斤拷锟斤拷锟诫。
	 * @return 锟斤拷锟斤拷锟斤拷莸锟斤拷锟�锟斤拷
	 */
	public static String getYearSelect(String selectName, String value,
			int startYear, int endYear, boolean hasBlank, String js) {
		int start = startYear;
		int end = endYear;
		if (startYear > endYear) {
			start = endYear;
			end = startYear;
		}
		StringBuffer sb = new StringBuffer("");

		sb.append("<select name=\"" + selectName + "\" " + js + ">");
		if (hasBlank) {
			sb.append("<option value=\"\"></option>");
		}
		for (int i = start; i <= end; i++) {
			if (!value.trim().equals("") && i == Integer.parseInt(value)) {
				sb.append("<option value=\"" + i + "\" selected>" + i
						+ "</option>");
			} else {
				sb.append("<option value=\"" + i + "\">" + i + "</option>");
			}
		}
		sb.append("</select>");
		return sb.toString();
	}

	/**
	 * 锟斤拷锟斤拷锟斤拷莸锟斤拷锟�锟斤拷
	 * 
	 * @param selectName
	 *            锟斤拷-锟斤拷锟斤拷锟�
	 * @param value
	 *            锟斤拷�?锟斤拷-锟斤拷锟街�
	 * @param startYear
	 *            锟斤拷始锟斤拷锟�
	 * @param endYear
	 *            锟斤拷锟斤拷锟斤拷锟�
	 * @param js
	 *            锟斤拷锟斤拷锟絡s为js锟街凤拷锟斤拷锟斤拷 " onchange=\"changeYear()\" "
	 *            ,锟斤拷锟斤拷锟轿猴拷js锟侥凤拷锟斤拷锟酵匡拷锟斤拷锟斤拷jsp页锟斤拷锟�?�憋拷写锟斤拷锟斤拷锟斤拷锟斤拷锟诫。
	 * @return 锟斤拷锟斤拷锟斤拷莸锟斤拷锟�锟斤拷
	 */
	public static String getYearSelect(String selectName, String value,
			int startYear, int endYear, String js) {
		int start = startYear;
		int end = endYear;
		if (startYear > endYear) {
			start = endYear;
			end = startYear;
		}
		StringBuffer sb = new StringBuffer("");
		sb.append("<select name=\"" + selectName + "\" " + js + ">");
		for (int i = start; i <= end; i++) {
			if (!value.trim().equals("") && i == Integer.parseInt(value)) {
				sb.append("<option value=\"" + i + "\" selected>" + i
						+ "</option>");
			} else {
				sb.append("<option value=\"" + i + "\">" + i + "</option>");
			}
		}
		sb.append("</select>");
		return sb.toString();
	}

	/**
	 * 锟斤拷�?�锟铰份碉拷锟斤拷-锟斤拷
	 * 
	 * @param selectName
	 * @param value
	 * @param hasBlank
	 * @return 锟斤拷锟斤拷锟铰份碉拷锟斤拷-锟斤拷
	 */
	public static String getMonthSelect(String selectName, String value,
			boolean hasBlank) {
		StringBuffer sb = new StringBuffer("");
		sb.append("<select name=\"" + selectName + "\">");
		if (hasBlank) {
			sb.append("<option value=\"\"></option>");
		}
		for (int i = 1; i <= 12; i++) {
			if (!value.trim().equals("") && i == Integer.parseInt(value)) {
				sb.append("<option value=\"" + i + "\" selected>" + i
						+ "</option>");
			} else {
				sb.append("<option value=\"" + i + "\">" + i + "</option>");
			}
		}
		sb.append("</select>");
		return sb.toString();
	}

	/**
	 * 锟斤拷�?�锟铰份碉拷锟斤拷-锟斤拷
	 * 
	 * @param selectName
	 * @param value
	 * @param hasBlank
	 * @param js
	 * @return 锟斤拷锟斤拷锟铰份碉拷锟斤拷-锟斤拷
	 */
	public static String getMonthSelect(String selectName, String value,
			boolean hasBlank, String js) {
		StringBuffer sb = new StringBuffer("");
		sb.append("<select name=\"" + selectName + "\" " + js + ">");
		if (hasBlank) {
			sb.append("<option value=\"\"></option>");
		}
		for (int i = 1; i <= 12; i++) {
			if (!value.trim().equals("") && i == Integer.parseInt(value)) {
				sb.append("<option value=\"" + i + "\" selected>" + i
						+ "</option>");
			} else {
				sb.append("<option value=\"" + i + "\">" + i + "</option>");
			}
		}
		sb.append("</select>");
		return sb.toString();
	}

	/**
	 * 锟斤拷�?�锟斤拷锟斤拷锟�锟斤拷默锟较碉拷为1-31锟斤拷 注锟解：锟剿凤拷锟斤拷锟斤拷锟�?�癸拷锟斤拷锟铰凤拷锟斤拷-锟斤拷锟斤拷锟絡锟斤拷锟斤拷
	 * 
	 * @param selectName
	 * @param value
	 * @param hasBlank
	 * @return 锟斤拷锟斤拷锟斤拷锟斤拷-锟斤拷
	 */
	public static String getDaySelect(String selectName, String value,
			boolean hasBlank) {
		StringBuffer sb = new StringBuffer("");
		sb.append("<select name=\"" + selectName + "\">");
		if (hasBlank) {
			sb.append("<option value=\"\"></option>");
		}
		for (int i = 1; i <= 31; i++) {
			if (!value.trim().equals("") && i == Integer.parseInt(value)) {
				sb.append("<option value=\"" + i + "\" selected>" + i
						+ "</option>");
			} else {
				sb.append("<option value=\"" + i + "\">" + i + "</option>");
			}
		}
		sb.append("</select>");
		return sb.toString();
	}

	/**
	 * 锟斤拷�?�锟斤拷锟斤拷锟�锟斤拷默锟较碉拷为1-31
	 * 
	 * @param selectName
	 * @param value
	 * @param hasBlank
	 * @param js
	 * @return 锟斤拷�?�锟斤拷锟斤拷锟�锟斤拷
	 */
	public static String getDaySelect(String selectName, String value,
			boolean hasBlank, String js) {
		StringBuffer sb = new StringBuffer("");
		sb.append("<select name=\"" + selectName + "\" " + js + ">");
		if (hasBlank) {
			sb.append("<option value=\"\"></option>");
		}
		for (int i = 1; i <= 31; i++) {
			if (!value.trim().equals("") && i == Integer.parseInt(value)) {
				sb.append("<option value=\"" + i + "\" selected>" + i
						+ "</option>");
			} else {
				sb.append("<option value=\"" + i + "\">" + i + "</option>");
			}
		}
		sb.append("</select>");
		return sb.toString();
	}

	/**
	 * 锟斤拷锟斤拷}锟斤拷之锟斤拷锟�?�讹拷锟劫�?�拷锟斤拷末锟斤拷锟斤拷锟斤拷锟侥╋拷锟街�?�拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟届，
	 * 一锟斤拷锟斤拷末锟斤拷锟截斤拷锟轿�锟斤拷}锟斤拷为4锟斤拷锟皆达拷锟斤拷锟狡�
	 * 锟斤拷锟�锟斤拷锟剿凤拷锟斤拷目�?锟斤拷锟斤拷统锟斤拷�?�锟斤拷锟矫筹拷锟斤拷录锟斤拷锟斤拷
	 * 注锟解开始锟斤拷锟节和斤拷锟斤拷锟斤拷锟斤拷�?统一锟斤拷4锟斤拷
	 * 
	 * @param startDate
	 *            锟斤拷始锟斤拷锟斤拷 锟斤拷锟斤拷�?"yyyy/MM/dd" 锟斤拷锟斤拷"yyyy-MM-dd"
	 * @param endDate
	 *            锟斤拷锟斤拷锟斤拷锟斤拷 锟斤拷锟斤拷�?"yyyy/MM/dd"锟斤拷锟斤拷"yyyy-MM-dd"
	 * @return int
	 */
	public static int countWeekend(String startDate, String endDate) {
		int result = 0;
		Date sdate = null;
		Date edate = null;
		if (startDate.indexOf("/") > 0 && endDate.indexOf("/") > 0) {
			sdate = getDateObj(startDate, "/"); // 锟斤拷始锟斤拷锟斤拷
			edate = getDateObj(endDate, "/");// 锟斤拷锟斤拷锟斤拷锟斤拷
		}
		if (startDate.indexOf("-") > 0 && endDate.indexOf("-") > 0) {
			sdate = getDateObj(startDate, "-"); // 锟斤拷始锟斤拷锟斤拷
			edate = getDateObj(endDate, "-");// 锟斤拷锟斤拷锟斤拷锟斤拷
		}

		// 锟斤拷锟饺硷拷锟斤拷锟斤拷锟斤拷锟�?�╋拷锟斤拷冢锟饺伙拷锟斤拷页锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷
		int sumDays = Math.abs(getDiffDays(startDate, endDate));
		int dayOfWeek = 0;
		for (int i = 0; i <= sumDays; i++) {
			dayOfWeek = getDayOfWeek(getDateAdd(sdate, i)); // 锟斤拷锟斤拷�?锟斤拷一锟斤拷锟斤拷锟斤拷锟�
			if (dayOfWeek == 1 || dayOfWeek == 7) { // 1 锟斤拷锟斤拷锟斤拷 7锟斤拷锟斤拷锟斤拷
				result++;
			}
		}
		return result;
	}

	/**
	 * 锟斤拷锟斤拷}锟斤拷锟斤拷锟斤拷之锟斤拷锟斤拷锟斤拷锟斤拷锟届。 注锟解开始锟斤拷锟节和斤拷锟斤拷锟斤拷锟斤拷�?统一锟斤拷4锟斤拷
	 * 
	 * @param startDate
	 *            锟斤拷�?"yyyy/MM/dd" 锟斤拷锟斤拷"yyyy-MM-dd"
	 * @param endDate
	 *            锟斤拷�?"yyyy/MM/dd" 锟斤拷锟斤拷"yyyy-MM-dd"
	 * @return 锟斤拷锟斤拷
	 */
	public static int getDiffDays(String startDate, String endDate) {
		long diff = 0;
		SimpleDateFormat ft = null;
		if (startDate.indexOf("/") > 0 && endDate.indexOf("/") > 0) {
			ft = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		}
		if (startDate.indexOf("-") > 0 && endDate.indexOf("-") > 0) {
			ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		}
		try {
			Date sDate = ft.parse(startDate + " 00:00:00");
			Date eDate = ft.parse(endDate + " 00:00:00");
			diff = eDate.getTime() - sDate.getTime();
			diff = diff / 86400000;// 1000*60*60*24;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return (int) diff;

	}

	/**
	 * 锟斤拷锟斤拷}锟斤拷锟斤拷锟斤拷之锟斤拷锟斤拷锟较�?�拷锟斤拷锟斤拷锟斤拷锟�锟斤拷(锟斤拷始锟斤拷锟节和斤拷锟斤拷锟斤拷锟斤拷)锟斤拷
	 * 锟斤拷锟界：2007/07/01 锟斤拷2007/07/03 ,锟斤拷么锟斤拷锟斤拷锟斤拷锟斤拷
	 * {"2007/07/01","2007/07/02","2007/07/03"} 注锟解开始锟斤拷锟节和斤拷锟斤拷锟斤拷锟斤拷�?统一锟斤拷4锟斤拷
	 * 
	 * @param startDate
	 *            锟斤拷�?"yyyy/MM/dd"锟斤拷锟斤拷 yyyy-MM-dd
	 * @param endDate
	 *            锟斤拷�?"yyyy/MM/dd"锟斤拷锟斤拷 yyyy-MM-dd
	 * @return 锟斤拷锟斤拷一锟斤拷锟街凤拷锟斤拷锟斤拷锟斤拷锟�
	 */
	public static String[] getArrayDiffDays(String startDate, String endDate) {
		int LEN = 0; // 锟斤拷4锟斤拷锟斤拷}锟斤拷之锟斤拷锟�?�癸拷锟�?�讹拷锟斤拷锟斤拷
		// 锟斤拷锟斤拷锟斤拷锟斤拷锟节和匡拷始锟斤拷锟斤拷锟斤拷�?�
		if (startDate.equals(endDate)) {
			return new String[] { startDate };
		}
		Date sdate = null;
		if (startDate.indexOf("/") > 0 && endDate.indexOf("/") > 0) {
			sdate = getDateObj(startDate, "/"); // 锟斤拷始锟斤拷锟斤拷
		}
		if (startDate.indexOf("-") > 0 && endDate.indexOf("-") > 0) {
			sdate = getDateObj(startDate, "-"); // 锟斤拷始锟斤拷锟斤拷
		}

		LEN = getDiffDays(startDate, endDate);
		String[] dateResult = new String[LEN + 1];
		dateResult[0] = startDate;
		for (int i = 1; i < LEN + 1; i++) {
			if (startDate.indexOf("/") > 0 && endDate.indexOf("/") > 0) {
				dateResult[i] = getFormatDateTime(getDateAdd(sdate, i),
						"yyyy/MM/dd");
			}
			if (startDate.indexOf("-") > 0 && endDate.indexOf("-") > 0) {
				dateResult[i] = getFormatDateTime(getDateAdd(sdate, i),
						"yyyy-MM-dd");
			}
		}

		return dateResult;
	}

	/**
	 * 锟�?�讹拷一锟斤拷锟斤拷锟斤拷锟角凤拷锟节匡拷始锟斤拷锟节和斤拷锟斤拷锟斤拷锟斤拷之锟戒。
	 * 
	 * @param srcDate
	 *            目锟斤拷锟斤拷锟斤拷 yyyy/MM/dd 锟斤拷锟斤拷 yyyy-MM-dd
	 * @param startDate
	 *            锟斤拷始锟斤拷锟斤拷 yyyy/MM/dd 锟斤拷锟斤拷 yyyy-MM-dd
	 * @param endDate
	 *            锟斤拷锟斤拷锟斤拷锟斤拷 yyyy/MM/dd 锟斤拷锟斤拷 yyyy-MM-dd
	 * @return 
	 *         锟斤拷锟节碉拷锟节匡拷始锟斤拷锟斤拷�?锟节碉拷锟节斤拷锟斤拷锟斤拷锟节�?拷锟斤拷么锟斤拷锟斤拷true锟斤拷锟斤拷锟津返伙拷false
	 */
	public static boolean isInStartEnd(String srcDate, String startDate,
			String endDate) {
		if (startDate.compareTo(srcDate) <= 0
				&& endDate.compareTo(srcDate) >= 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 锟斤拷�?�锟斤拷锟斤拷锟�锟斤拷默锟较碉拷为1-4锟斤拷 注锟解：锟剿凤拷锟斤拷锟斤拷锟�?�癸拷锟斤拷锟铰凤拷锟斤拷-锟斤拷锟斤拷锟絡锟斤拷锟斤拷
	 * 
	 * @param selectName
	 * @param value
	 * @param hasBlank
	 * @return 锟斤拷眉锟斤拷鹊锟斤拷锟�锟斤拷
	 */
	public static String getQuarterSelect(String selectName, String value,
			boolean hasBlank) {
		StringBuffer sb = new StringBuffer("");
		sb.append("<select name=\"" + selectName + "\">");
		if (hasBlank) {
			sb.append("<option value=\"\"></option>");
		}
		for (int i = 1; i <= 4; i++) {
			if (!value.trim().equals("") && i == Integer.parseInt(value)) {
				sb.append("<option value=\"" + i + "\" selected>" + i
						+ "</option>");
			} else {
				sb.append("<option value=\"" + i + "\">" + i + "</option>");
			}
		}
		sb.append("</select>");
		return sb.toString();
	}

	/**
	 * 锟斤拷�?�锟斤拷锟饺碉拷锟斤拷-锟斤拷默锟较碉拷为1-4
	 * 
	 * @param selectName
	 * @param value
	 * @param hasBlank
	 * @param js
	 * @return 锟斤拷�?�锟斤拷锟饺碉拷锟斤拷-锟斤拷
	 */
	public static String getQuarterSelect(String selectName, String value,
			boolean hasBlank, String js) {
		StringBuffer sb = new StringBuffer("");
		sb.append("<select name=\"" + selectName + "\" " + js + ">");
		if (hasBlank) {
			sb.append("<option value=\"\"></option>");
		}
		for (int i = 1; i <= 4; i++) {
			if (!value.trim().equals("") && i == Integer.parseInt(value)) {
				sb.append("<option value=\"" + i + "\" selected>" + i
						+ "</option>");
			} else {
				sb.append("<option value=\"" + i + "\">" + i + "</option>");
			}
		}
		sb.append("</select>");
		return sb.toString();
	}

	/**
	 * 锟斤拷锟斤拷�?为yyyy锟斤拷锟斤拷yyyy.MM锟斤拷锟斤拷yyyy.MM.dd锟斤拷锟斤拷锟斤拷转锟斤拷为yyyy/MM/
	 * dd锟侥�?�拷�?锟斤拷�?锟斤拷锟斤拷模锟斤拷锟斤拷锟� 01锟斤拷<br>
	 * 锟斤拷锟斤拷.1999 = 1999/01/01 锟斤拷锟界：1989.02=1989/02/01
	 * 
	 * @param argDate
	 *            锟斤拷�?锟斤拷锟斤拷转锟斤拷锟斤拷锟斤拷锟节★拷锟斤拷�?锟斤拷锟斤拷为yyyy锟斤拷锟斤拷yyyy.MM锟斤拷锟斤拷yyyy
	 *            .MM.dd
	 * @return 锟斤拷锟截�?�拷�?为yyyy/MM/dd锟斤拷锟街凤拷
	 */
	public static String changeDate(String argDate) {
		if (argDate == null || argDate.trim().equals("")) {
			return "";
		}
		String result = "";
		// 锟斤拷锟斤拷歉锟绞轿�?�yyy/MM/dd锟侥撅拷直锟接凤拷锟斤拷
		if (argDate.length() == 10 && argDate.indexOf("/") > 0) {
			return argDate;
		}
		String[] str = argDate.split("[.]"); // .锟饺斤拷锟斤拷锟斤拷
		int LEN = str.length;
		for (int i = 0; i < LEN; i++) {
			if (str[i].length() == 1) {
				if (str[i].equals("0")) {
					str[i] = "01";
				} else {
					str[i] = "0" + str[i];
				}
			}
		}
		if (LEN == 1) {
			result = argDate + "/01/01";
		}
		if (LEN == 2) {
			result = str[0] + "/" + str[1] + "/01";
		}
		if (LEN == 3) {
			result = str[0] + "/" + str[1] + "/" + str[2];
		}
		return result;
	}

	/**
	 * 锟斤拷锟斤拷�?为yyyy锟斤拷锟斤拷yyyy.MM锟斤拷锟斤拷yyyy.MM.dd锟斤拷锟斤拷锟斤拷转锟斤拷为yyyy/MM/
	 * dd锟侥�?�拷�?锟斤拷�?锟斤拷锟斤拷模锟斤拷锟斤拷锟� 01锟斤拷<br>
	 * 锟斤拷锟斤拷.1999 = 1999/01/01 锟斤拷锟界：1989.02=1989/02/01
	 * 
	 * @param argDate
	 *            锟斤拷�?锟斤拷锟斤拷转锟斤拷锟斤拷锟斤拷锟节★拷锟斤拷�?锟斤拷锟斤拷为yyyy锟斤拷锟斤拷yyyy.MM锟斤拷锟斤拷yyyy
	 *            .MM.dd
	 * @return 锟斤拷锟截�?�拷�?为yyyy/MM/dd锟斤拷锟街凤拷
	 */
	public static String changeDateWithSplit(String argDate, String split) {
		if (argDate == null || argDate.trim().equals("")) {
			return "";
		}
		if (split == null || split.trim().equals("")) {
			split = "-";
		}
		String result = "";
		// 锟斤拷锟斤拷歉锟绞轿�?�yyy/MM/dd锟侥撅拷直锟接凤拷锟斤拷
		if (argDate.length() == 10 && argDate.indexOf("/") > 0) {
			return argDate;
		}
		// 锟斤拷锟斤拷歉锟绞轿�?�yyy-MM-dd锟侥撅拷直锟接凤拷锟斤拷
		if (argDate.length() == 10 && argDate.indexOf("-") > 0) {
			return argDate;
		}
		String[] str = argDate.split("[.]"); // .锟饺斤拷锟斤拷锟斤拷
		int LEN = str.length;
		for (int i = 0; i < LEN; i++) {
			if (str[i].length() == 1) {
				if (str[i].equals("0")) {
					str[i] = "01";
				} else {
					str[i] = "0" + str[i];
				}
			}
		}
		if (LEN == 1) {
			result = argDate + split + "01" + split + "01";
		}
		if (LEN == 2) {
			result = str[0] + split + str[1] + split + "01";
		}
		if (LEN == 3) {
			result = str[0] + split + str[1] + split + str[2];
		}
		return result;
	}

	/**
	 * 锟斤拷锟斤拷指锟斤拷锟斤拷锟节的碉拷锟斤拷一锟斤拷锟铰碉拷锟斤拷锟斤拷
	 * 
	 * @param argDate
	 *            锟斤拷�?为yyyy-MM-dd锟斤拷锟斤拷yyyy/MM/dd
	 * @return 锟斤拷一锟斤拷锟铰碉拷锟斤拷锟斤拷
	 */
	public static int getNextMonthDays(String argDate) {
		String[] temp = null;
		if (argDate.indexOf("/") > 0) {
			temp = argDate.split("/");
		}
		if (argDate.indexOf("-") > 0) {
			temp = argDate.split("-");
		}
		Calendar cal = new GregorianCalendar(new Integer(temp[0]).intValue(),
				new Integer(temp[1]).intValue() - 1,
				new Integer(temp[2]).intValue());
		int curMonth = cal.get(Calendar.MONTH);
		cal.set(Calendar.MONTH, curMonth + 1);

		int curyear = cal.get(Calendar.YEAR);// 锟斤拷�?锟斤拷锟�
		curMonth = cal.get(Calendar.MONTH);// 锟斤拷�?锟铰凤拷,0-11

		int mArray[] = new int[] { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30,
				31 };
		// 锟�?�讹拷锟斤拷锟斤拷锟斤拷锟斤拷 锟斤拷2锟铰凤拷锟斤拷29锟届；
		if ((curyear % 400 == 0)
				|| ((curyear % 100 != 0) && (curyear % 4 == 0))) {
			mArray[1] = 29;
		}
		return mArray[curMonth];
	}

	public static String toGMT(Date pDate, int pTimeZone, String pDateFormat) {

		Calendar _calendar = Calendar.getInstance();
		
		if (pTimeZone > 0) {
			_calendar.setTimeZone(TimeZone.getTimeZone("GMT+" + (pTimeZone)));
		} else {
			_calendar.setTimeZone(TimeZone.getTimeZone("GMT-" + (pTimeZone)));
		}
		_calendar.set(pDate.getYear()+1900, pDate.getMonth(), pDate.getDate(), pDate.getHours(), pDate.getMinutes(), pDate.getSeconds());

		Date _date = new Date(_calendar.getTimeInMillis());
		
		DateFormat df2 = new SimpleDateFormat(pDateFormat);
		df2.setTimeZone(TimeZone.getTimeZone("GMT"));
		return df2.format(_date);

	}

	public static String fromGMT(Date pDate, int pTimeZone, String pDateFormat) {

		Calendar _calendar = Calendar.getInstance();
		_calendar.setTimeZone(TimeZone.getTimeZone("GMT"));
		_calendar.set(pDate.getYear()+1900, pDate.getMonth(), pDate.getDate(), pDate.getHours(), pDate.getMinutes(), pDate.getSeconds());
		
		DateFormat df = new SimpleDateFormat(pDateFormat);
		if (pTimeZone > 0) {
			df.setTimeZone(TimeZone.getTimeZone("GMT+" + (pTimeZone)));
		} else {
			df.setTimeZone(TimeZone.getTimeZone("GMT-" + (pTimeZone)));
		}

		return df.format(_calendar.getTimeInMillis());
	}
}