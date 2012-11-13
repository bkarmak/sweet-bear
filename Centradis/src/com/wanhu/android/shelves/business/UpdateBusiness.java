package com.wanhu.android.shelves.business;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.ParseException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri.Builder;
import android.util.Log;

import com.wanhu.android.shelves.util.Constant;
import com.wanhu.android.shelves.util.DateTools;
import com.wanhu.android.shelves.util.HttpManager;

public class UpdateBusiness {

	static final String LOG_TAG = "UpdateBusiness";

	private Context mContext;
	private ContentResolver mResolver;

	public UpdateBusiness(Context pContext) {
		mContext = pContext;
		mResolver = mContext.getContentResolver();
	}

	public List<Integer> onPrepareUpdate(String pUserID) {
		final Builder uri = buildPrepareUpdateQuery(pUserID);
		final HttpGet get = new HttpGet(uri.build().toString());
		try {

			return executeRequest(new HttpHost(Constant.API_REST_HOST), get);
		} catch (IOException e) {
			android.util.Log.e(LOG_TAG, "Could not perform login with query: ",
					e);
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void onUpdate() {

	}

	public void onUpdateSuccess() {

	}
	/*
	private Builder buildPrepareUpdateQuery(String pUserID) {
		final Builder uri = Constant.buildGetMethod();
		uri.appendQueryParameter(
				Constant.PARAM_MD5,
				Constant.getMD5(Constant.APP_KEY + "-"
						+ DateTools.getFormatDateTime(new Date(), "MMyyyydd")));
		uri.appendQueryParameter(Constant.PARAM_TABLE_NAME,
				Constant.VALUE_TABLE_NAME_CHECK_VERSION);
		uri.appendQueryParameter(Constant.JSON_TAG_UID, pUserID);
		return uri;
	}
	*/
	private Builder buildPrepareUpdateQuery(String pUserID) {
		final Builder uri = Constant.buildGetMethod();
		uri.appendQueryParameter(
				Constant.PARAM_MD5,
				Constant.getMD5(Constant.APP_KEY));
		uri.appendQueryParameter(Constant.PARAM_TABLE_NAME,
				Constant.VALUE_TABLE_NAME_CHECK_VERSION);
		uri.appendQueryParameter(Constant.JSON_TAG_UID, pUserID);
		return uri;
	}
	private List<Integer> executeRequest(HttpHost host, HttpGet get)
			throws IOException, ParseException, JSONException {
		HttpEntity entity = null;
		try {
			final HttpResponse response = HttpManager.execute(host, get);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {

				entity = response.getEntity();

				String _response = EntityUtils.toString(entity, HTTP.UTF_8);
				try {
					JSONArray jsonArray = new JSONArray(_response);
					if (jsonArray != null) {
						List<Integer> _list = new ArrayList<Integer>();
						for (int i = 0; i < jsonArray.length(); i++) {

							JSONObject jsonObject = (JSONObject) jsonArray
									.get(i);

							if (jsonObject != null) {

								String _bookId = (String) jsonObject
										.get("id_documents");
								
								_list.add(Integer.parseInt(_bookId));
							}
						}
						return _list;
					}
				} catch (JSONException e) {
					final IOException ioe = new IOException(
							"Could not parse the response");
					ioe.initCause(e);
					throw ioe;
				}

			}
		} finally {
			if (entity != null) {
				entity.consumeContent();
			}
		}
		return null;
	}

}
