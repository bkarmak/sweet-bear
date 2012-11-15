package com.wanhu.android.shelves.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;

import com.wanhu.android.shelves.R;
import com.wanhu.android.shelves.activity.base.ActivityFrame;
import com.wanhu.android.shelves.business.LoginBusiness;
import com.wanhu.android.shelves.controls.LoginDialog;
import com.wanhu.android.shelves.controls.LoginDialog.OnLoginListener;
import com.wanhu.android.shelves.provider.CentradisBooksStore;
import com.wanhu.android.shelves.util.Preferences;
import com.wanhu.android.shelves.util.UIUtilities;

public class StartActivity extends ActivityFrame implements OnLoginListener {

	private SharedPreferences mPreferences;
	private LoginBusiness mLoginBusiness;
	private LoginTask mLoginTask;
	private LoginDialog mLoginDialog;

	private static final String STATE_LOGIN_IN_PROGRESS = "shelves.add.inprogress";
	private static final String STATE_LOGIN_NAME = "shelves.login.name";
	private static final String STATE_LOGIN_PASSORD = "shelves.login.password";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		/*
		 * getWindow().setBackgroundDrawable(
		 * getResources().getDrawable(R.drawable.login_bkg_lp));
		 */
		setContentView(R.layout.start);

		initVariables();

		if (isStayConnected()) {

			String _userName = mPreferences.getString(
					Preferences.KEY_USER_NAME, Preferences.DEFAULT_USER_NAME);
			String _password = mPreferences.getString(
					Preferences.KEY_USER_PASSWORD,
					Preferences.DEFAULT_USER_PASSWORD);

			if (mLoginBusiness.isNetworkAvaiable()) {
				onLogin(_userName, _password);
			} else {
				CentradisBooksStore.VALUE_USER_ID = mPreferences.getString(
						Preferences.KEY_USER_ID, "");
				ShelvesActivity.show(StartActivity.this);
			}
		} else {
			mLoginDialog.show();
		}

	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	private void initVariables() {
		mLoginBusiness = new LoginBusiness(this);
		mLoginDialog = new LoginDialog(this, R.style.CentradisDialog, this);
	}

	private boolean isStayConnected() {
		mPreferences = getSharedPreferences(Preferences.NAME, 0);
		return mPreferences.getBoolean(Preferences.KEY_STAY_CONNECTED,
				Preferences.DEFAULT_STAY_CONNECTED);
	}

	private class LoginTask extends AsyncTask<String, Void, Boolean> {

		private final Object _lock = new Object();
		private String[] _params = new String[2];

		String[] getParams() {
			synchronized (_lock) {
				return _params;
			}
		}

		@Override
		protected void onPreExecute() {
			showProgressDialog(R.string.wait, R.string.login_label);
		}

		@Override
		protected Boolean doInBackground(String... params) {
			synchronized (_lock) {
				_params[0] = params[0];
				_params[1] = params[1];
			}
			return mLoginBusiness.login(params[0], params[1]);
		}

		@Override
		protected void onCancelled() {
			dismissProgressDialog();
		}

		@Override
		protected void onPostExecute(Boolean result) {
			if (result) {
				ShelvesActivity.show(StartActivity.this);
			} else {
				UIUtilities.showToast(StartActivity.this,
						R.string.login_failure);
				if (!mLoginDialog.isShowing()) {
					String _userName = mPreferences.getString(
							Preferences.KEY_USER_NAME,
							Preferences.DEFAULT_USER_NAME);
					String _password = mPreferences.getString(
							Preferences.KEY_USER_PASSWORD,
							Preferences.DEFAULT_USER_PASSWORD);
					mLoginDialog.show();
					mLoginDialog.setViewValues(_userName, _password, true);
				}
			}
			dismissProgressDialog();
		}
	}

	private void saveLoginTask(Bundle outState) {
		final LoginTask _task = mLoginTask;
		if (_task != null && _task.getStatus() != AsyncTask.Status.FINISHED) {
			final String[] _params = _task.getParams();
			_task.cancel(true);

			if (_params != null && _params.length > 1) {
				outState.putBoolean(STATE_LOGIN_IN_PROGRESS, true);
				outState.putString(STATE_LOGIN_NAME, _params[0]);
				outState.putString(STATE_LOGIN_PASSORD, _params[1]);
			}

			mLoginTask = null;
		}
	}

	private void restoreLoginTask(Bundle savedInstanceState) {
		if (savedInstanceState.getBoolean(STATE_LOGIN_IN_PROGRESS)) {
			final String _name = savedInstanceState.getString(STATE_LOGIN_NAME);
			final String _password = savedInstanceState
					.getString(STATE_LOGIN_PASSORD);
			if (!TextUtils.isEmpty(_name) && !TextUtils.isEmpty(_password)) {
				mLoginTask = (LoginTask) new LoginTask().execute(_name,
						_password);
			}
		}
	}

	private void onLogin(String pUserName, String pPassword) {
		if (mLoginTask == null
				|| mLoginTask.getStatus() == AsyncTask.Status.FINISHED) {
			mLoginTask = (LoginTask) new LoginTask().execute(pUserName,
					pPassword);
		} else {
			UIUtilities.showToast(this, R.string.error_logining_in_progress);
		}
	}

	private void onCancelLogin() {
		if (mLoginTask != null
				&& mLoginTask.getStatus() == AsyncTask.Status.RUNNING) {
			mLoginTask.cancel(true);
			mLoginTask = null;
		}
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		restoreLoginTask(savedInstanceState);
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		saveLoginTask(outState);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		onCancelLogin();
		if (mLoginDialog != null) {
			mLoginDialog.dismiss();
		}
		dismissProgressDialog();
	}

	@Override
	public void onLoginButtonClicked(String pUserName, String pPassword) {
		if (mLoginBusiness.isNetworkAvaiable()) {
			onLogin(pUserName, pPassword);
		} else {
			String _userName = mPreferences.getString(
					Preferences.KEY_USER_NAME, Preferences.DEFAULT_USER_NAME);
			String _password = mPreferences.getString(
					Preferences.KEY_USER_PASSWORD,
					Preferences.DEFAULT_USER_PASSWORD);
			if (true || pUserName.equals(_userName) && pPassword.equals(_password)) {
				ShelvesActivity.show(StartActivity.this);
			} else {
				UIUtilities.showToast(StartActivity.this,
						R.string.login_failure);
			}
		}

	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE
				|| newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
			setContentView(R.layout.start);
		}
	}

	@Override
	protected void onNewIntent(Intent intent) {
		if (intent.getBooleanExtra(ShelvesActivity.LOG_OUT, false)) {
			String _userName = mPreferences.getString(
					Preferences.KEY_USER_NAME, Preferences.DEFAULT_USER_NAME);
			String _password = mPreferences.getString(
					Preferences.KEY_USER_PASSWORD,
					Preferences.DEFAULT_USER_PASSWORD);
			mLoginDialog.show();
			mLoginDialog.setViewValues(_userName, _password, true);
		} else {
			finish();
		}
	}

}
