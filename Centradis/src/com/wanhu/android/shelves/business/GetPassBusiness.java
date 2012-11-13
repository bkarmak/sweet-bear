package com.wanhu.android.shelves.business;

import java.io.IOException;
import java.util.Date;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.ParseException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;

import android.content.Context;
import android.net.Uri;
import android.net.Uri.Builder;

import com.wanhu.android.shelves.util.DateTools;
import com.wanhu.android.shelves.util.HttpManager;

public class GetPassBusiness {

	//private static final String API_REST_HOST = "192.168.0.101";
	private static final String API_REST_HOST = "centradis.c3o-digital.com";
	private static final String API_REST_URL = "/pwd.php";

	static final String LOG_TAG = "Shelves.GetPass";
	private Context mContext;
	private static final String APP_KEY = "CENTRADIS#!";
	private String VALUE_MD5;
	private static final String PARAM_MD5 = "k";
	private static final String PARAM_EMAIL = "email";

	private static final String REGISTER_SUCCESS = "success";
	private static final String REGISTER_EMAIL = "email";

	public GetPassBusiness(Context pContext) {
		mContext = pContext;
	}

	public boolean getPass(String pEmail) {
		/*

		VALUE_MD5 = LoginBusiness.getMD5(APP_KEY + "-"
				+ DateTools.getFormatDateTime(new Date(), "MMyyyydd"));
				*/
		
		VALUE_MD5 = LoginBusiness.getMD5(APP_KEY);
		
		final Builder uri = buildGetPassQuery(pEmail);
		final HttpGet get = new HttpGet(uri.build().toString());

		try {
			return executeRequest(new HttpHost(API_REST_HOST, 80, "http"), get);
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

	private boolean executeRequest(HttpHost host, HttpGet get)
			throws IOException, ParseException, JSONException {
		HttpEntity entity = null;
		try {
			final HttpResponse response = HttpManager.execute(host, get);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {

				entity = response.getEntity();

				String _response = EntityUtils.toString(entity, HTTP.UTF_8);

				if (_response != null && _response.equals("1")) {
					return true;
				} else {
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

	private Builder buildGetPassQuery(String pEmail) {
		final Builder uri = buildGetMethod();
		uri.appendQueryParameter(PARAM_MD5, VALUE_MD5);
		uri.appendQueryParameter(PARAM_EMAIL, pEmail);
		uri.appendQueryParameter("do", "check");
		return uri;
	}

	public static Uri.Builder buildGetMethod() {
		final Builder builder = new Builder();
		builder.path(API_REST_URL);
		return builder;
	}

}
