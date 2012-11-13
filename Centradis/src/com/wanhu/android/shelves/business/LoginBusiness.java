package com.wanhu.android.shelves.business;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.Uri.Builder;

import com.wanhu.android.shelves.ShelvesApplication;
import com.wanhu.android.shelves.provider.CentradisBooksStore;
import com.wanhu.android.shelves.util.DateTools;
import com.wanhu.android.shelves.util.HttpManager;
import com.wanhu.android.shelves.util.Preferences;

public class LoginBusiness {

	static final String LOG_TAG = "Shelves";

	private Context mContext;

	//private static final String API_REST_HOST = "192.168.0.101";
	private static final String API_REST_HOST = "centradis.c3o-digital.com";
	private static final String API_REST_URL = "/api.php";

	private static final String PARAM_MD5 = "k";
	private static final String PARAM_TABLE_NAME = "table";
	private static final String JSON_TAG = "json";

	private static final String APP_KEY = "CENTRADIS#!";
	private String VALUE_MD5;
	private static final String VALUE_TABLE_NAME = "user";

	private static final String LOGIN_SUCCESS = "success";
	private static final String USER_NAME = "email";
	private static final String USER_PASS = "password";
	private static final String JSON_TAG_UID = "uid";

	public LoginBusiness(Context pContext) {
		mContext = pContext;
	}

	public boolean login(String pName, String pPassword) {
		/*
		VALUE_MD5 = getMD5(APP_KEY + "-"
				+ DateTools.getFormatDateTime(new Date(), "MMyyyydd"));
				*/
		VALUE_MD5 = getMD5(APP_KEY);

		final Builder uri = buildLoginQuery();
		final HttpPost post = new HttpPost(uri.build().toString());
		android.util.Log.e(uri.build().toString(),"URL HERE");
		try {

			List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();
			JSONObject jsonObject = new JSONObject();
			jsonObject.put(USER_NAME, pName);
			jsonObject.put(USER_PASS, pPassword);
			nameValuePair.add(new BasicNameValuePair(JSON_TAG, jsonObject
					.toString()));
			post.setEntity(new UrlEncodedFormEntity(nameValuePair));

			/*
			 * JSONObject jr = new JSONObject(); jr.put(USER_NAME, pName);
			 * jr.put(USER_PASS, pPassword);
			 * 
			 * JSONObject jsonObject = new JSONObject();
			 * jsonObject.put(JSON_TAG, jr);
			 * 
			 * StringEntity entity = new StringEntity(jsonObject.toString(),
			 * HTTP.UTF_8);
			 * 
			 * post.setEntity(entity);
			 */

			return executeRequest(new HttpHost(API_REST_HOST), post);
		} catch (IOException e) {
			android.util.Log.e(LOG_TAG, "Could not perform login with query: ",
					e);
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return false;
	}

	private Builder buildLoginQuery() {
		final Builder uri = buildGetMethod();
		uri.appendQueryParameter(PARAM_MD5, VALUE_MD5);
		uri.appendQueryParameter(PARAM_TABLE_NAME, VALUE_TABLE_NAME);
		return uri;
	}

	public static Uri.Builder buildGetMethod() {
		final Builder builder = new Builder();
		builder.path(API_REST_URL);
		return builder;
	}

	private boolean executeRequest(HttpHost host, HttpPost post)
			throws IOException, ParseException, JSONException {
		HttpEntity entity = null;
		try {
			final HttpResponse response = HttpManager.execute(host, post);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {

				entity = response.getEntity();

				String _response = EntityUtils.toString(entity, HTTP.UTF_8);
				//android.util.Log.e(_response,"URL RESPONSE HERE");
				JSONObject _result = new JSONObject(_response);
				try {
					JSONObject _jsonObject = _result
							.getJSONObject(LOGIN_SUCCESS);
					if (_jsonObject != null) {
						CentradisBooksStore.VALUE_USER_ID = (String) _jsonObject
								.get(JSON_TAG_UID);
						SharedPreferences mPreferences = mContext.getSharedPreferences(Preferences.NAME, 0);
						final Editor _editor = mPreferences.edit();
						_editor.putString(Preferences.KEY_USER_ID, CentradisBooksStore.VALUE_USER_ID);
						_editor.commit();
						return true;
					}
				} catch (Exception e) {
					return false;
				}

			}
		} finally {
			if (entity != null) {
				entity.consumeContent();
			}
		}
		return false;
	}

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

	public boolean isNetworkAvaiable() {
		ConnectivityManager _connectivityManager = (ConnectivityManager) mContext
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (_connectivityManager != null) {
			NetworkInfo _networkInfo = _connectivityManager
					.getActiveNetworkInfo();
			if (_networkInfo != null && _networkInfo.isConnected()) {
				return true;
			}
		}
		return false;
	}

}
