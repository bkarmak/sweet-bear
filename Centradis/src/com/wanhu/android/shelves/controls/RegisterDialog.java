package com.wanhu.android.shelves.controls;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.wanhu.android.shelves.R;
import com.wanhu.android.shelves.activity.ShelvesActivity;
import com.wanhu.android.shelves.activity.StartActivity;
import com.wanhu.android.shelves.business.RegisterBusiness;
import com.wanhu.android.shelves.util.UIUtilities;

public class RegisterDialog extends Dialog implements
		android.view.View.OnClickListener {

	private Context mContext;
	private OnRegisterListener mOnRegisterListener;
	private RegisterBusiness mRegisterBusiness;
	private RegisterTask mRegisterTask;
	private static final String STATE_REGISTER_IN_PROGRESS = "shelves.add.inprogress";
	private static final String STATE_REGISTER_NAME = "shelves.register.name";
	private static final String STATE_REGISTER_SURNAME = "shelves.register.surname";
	private static final String STATE_REGISTER_EMAIL = "shelves.register.email";
	private static final String STATE_REGISTER_TEL = "shelves.register.telephone";
	private static final String STATE_REGISTER_COMPANY = "shelves.register.company";
	private static final String STATE_REGISTER_DESCRIPTION = "shelves.register.description";

	private ProgressDialog mProgressDialog;
	private EditText etName;
	private EditText etNickName;
	private EditText etEmail;
	private EditText etTel;
	private EditText etCompany;
	private EditText etMessage;
	private Button btnRegister;
	private Button btnBack;
	private View mView;

	public interface OnRegisterListener {
		public abstract void onRegisterButtonClicked();
	}

	public RegisterDialog(Context pContext,
			OnRegisterListener pOnRegisterListener, View pView) {
		super(pContext);
		mContext = pContext;
		mOnRegisterListener = pOnRegisterListener;
		mView = pView;
	}

	public RegisterDialog(Context pContext, int pTheme,
			OnRegisterListener pOnRegisterListener, View pView) {
		super(pContext, pTheme);
		mContext = pContext;
		mOnRegisterListener = pOnRegisterListener;
		mView = pView;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register_dialog);

		initVariable();
		initView();
		initListeners();
	}

	private void initVariable() {
		mRegisterBusiness = new RegisterBusiness(mContext);
	}

	private void initView() {
		etName = (EditText) findViewById(R.id.etName);
		etNickName = (EditText) findViewById(R.id.etNickName);
		etEmail = (EditText) findViewById(R.id.etEmail);
		etTel = (EditText) findViewById(R.id.etTel);
		etCompany = (EditText) findViewById(R.id.etCompany);
		etMessage = (EditText) findViewById(R.id.etMessage);
		etMessage.setCursorVisible(true);
		btnRegister = (Button) findViewById(R.id.btnRegister);
		btnBack = (Button) findViewById(R.id.btnBack);
	}

	private void initListeners() {
		btnRegister.setOnClickListener(this);
		btnBack.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.btnBack:
			dismiss();
			mView.setVisibility(View.VISIBLE);
			break;
		case R.id.btnRegister:
			String _name = etName.getText().toString();
			if (TextUtils.isEmpty(_name)) {
				UIUtilities.showToast(mContext, R.string.register_name_hint);
				etName.requestFocus();
				return;
			}
			String _surName = etNickName.getText().toString();
			if (TextUtils.isEmpty(_surName)) {
				UIUtilities
						.showToast(mContext, R.string.register_nickname_hint);
				etNickName.requestFocus();
				return;
			}
			String _email = etEmail.getText().toString();
			if (TextUtils.isEmpty(_email)) {
				UIUtilities.showToast(mContext, R.string.register_email_hint);
				etEmail.requestFocus();
				return;
			} else if (!validateEmail(_email)) {
				UIUtilities.showToast(mContext, R.string.register_email_error);
				etEmail.requestFocus();
				return;
			}
			String _tel = etTel.getText().toString();
			if (TextUtils.isEmpty(_tel)) {
				UIUtilities.showToast(mContext, R.string.register_tel_hint);
				etTel.requestFocus();
				return;
			}
			String _company = etCompany.getText().toString();
			if (TextUtils.isEmpty(_company)) {
				UIUtilities.showToast(mContext, R.string.register_company_hint);
				etMessage.requestFocus();
				return;
			}
			String _message = etMessage.getText().toString();
			if (TextUtils.isEmpty(_message)) {
				UIUtilities.showToast(mContext, R.string.register_comment_hint);
				etMessage.requestFocus();
				return;
			}

			onRegister(_name, _surName, _email, _tel, _company, _message);
			break;
		default:
			break;
		}

	}

	private boolean validateEmail(String pEmail) {
		String strPattern = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";
		Pattern pattern = Pattern.compile(strPattern);
		Matcher m = pattern.matcher(pEmail);
		if (!m.matches()) {
			return false;
		}
		return true;
	}

	private void onRegister(String pName, String pSurname, String pEmail,
			String pTel, String pCompany, String pDescription) {
		if (mRegisterTask == null
				|| mRegisterTask.getStatus() == AsyncTask.Status.FINISHED) {
			mRegisterTask = (RegisterTask) new RegisterTask().execute(pName,
					pSurname, pEmail, pTel, pCompany, pDescription);
		} else {
			UIUtilities.showToast(mContext,
					R.string.error_registering_in_progress);
		}
	}

	private void onCancelRegister() {
		if (mRegisterTask != null
				&& mRegisterTask.getStatus() == AsyncTask.Status.RUNNING) {
			mRegisterTask.cancel(true);
			mRegisterTask = null;
		}
	}

	@Override
	public void onDetachedFromWindow() {
		super.onDetachedFromWindow();
		onCancelRegister();
	}

	private class RegisterTask extends AsyncTask<String, Void, Boolean> {

		private final Object _lock = new Object();
		private String[] _params = new String[6];

		String[] getParams() {
			synchronized (_lock) {
				return _params;
			}
		}

		@Override
		protected void onPreExecute() {
			showProgressDialog(R.string.wait, R.string.register_label);
		}

		@Override
		protected Boolean doInBackground(String... params) {
			synchronized (_lock) {
				_params[0] = params[0];
				_params[1] = params[1];
				_params[2] = params[2];
				_params[3] = params[3];
				_params[4] = params[4];
				_params[5] = params[5];
			}
			return mRegisterBusiness.register(_params[0], _params[1],
					_params[2], _params[3], _params[4], _params[5]);
		}

		@Override
		protected void onCancelled() {
			dismissProgressDialog();
		}

		@Override
		protected void onPostExecute(Boolean result) {
			if (result) {
				mOnRegisterListener.onRegisterButtonClicked();
			} else {
				UIUtilities.showToast(mContext, R.string.register_failure);
			}
			dismissProgressDialog();
		}
	}

	protected void showProgressDialog(int pTitleResID, int pMessageResID) {
		mProgressDialog = new ProgressDialog(mContext);
		mProgressDialog.setTitle(mContext.getString(pTitleResID));
		mProgressDialog.setMessage(mContext.getString(pMessageResID));
		mProgressDialog.show();
	}

	protected void dismissProgressDialog() {
		if (mProgressDialog != null) {
			mProgressDialog.dismiss();
		}
	}

	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			dismiss();
			mView.setVisibility(View.VISIBLE);
		}
		return true;
	}

}
