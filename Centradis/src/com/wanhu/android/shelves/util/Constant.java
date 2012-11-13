package com.wanhu.android.shelves.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import android.net.Uri;
import android.net.Uri.Builder;

public class Constant {

	//public static final String API_REST_HOST = "192.168.0.101";
	public static final String API_REST_HOST = "centradis.c3o-digital.com";
	public static final String API_REST_URL = "/api.php";

	public static final String PARAM_MD5 = "k";
	public static final String PARAM_TABLE_NAME = "table";
	public static final String JSON_TAG = "json";

	public static final String APP_KEY = "CENTRADIS#!";
	public static final String VALUE_TABLE_NAME = "user";

	public static final String LOGIN_SUCCESS = "success";
	public static final String USER_NAME = "email";
	public static final String USER_PASS = "password";
	public static final String JSON_TAG_UID = "uid";
	
	//Update
	public static final String VALUE_TABLE_NAME_CHECK_VERSION = "checkversion";

	// String to MD5
	public static String getMD5(String str) {
		MessageDigest md5 = null;
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		char[] charArray = str.toCharArray();
		byte[] byteArray = new byte[charArray.length];
		for (int i = 0; i < charArray.length; i++) {
			byteArray[i] = (byte) charArray[i];
		}
		byte[] md5Bytes = md5.digest(byteArray);
		StringBuffer hexValue = new StringBuffer();
		for (int i = 0; i < md5Bytes.length; i++) {
			int val = ((int) md5Bytes[i]) & 0xff;
			if (val < 16) {
				hexValue.append("0");
			}
			hexValue.append(Integer.toHexString(val));
		}
		return hexValue.toString();
	}
	
	public static Uri.Builder buildGetMethod() {
		final Builder builder = new Builder();
		builder.path(API_REST_URL);
		return builder;
	}

}
