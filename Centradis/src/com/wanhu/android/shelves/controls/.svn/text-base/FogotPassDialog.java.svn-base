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
import com.wanhu.android.shelves.business.GetPassBusiness;
import com.wanhu.android.shelves.business.RegisterBusiness;
import com.wanhu.android.shelves.util.UIUtilities;

public class FogotPassDialog extends Dialog implements
		android.view.View.OnClickListener {

	private Context mContext;
	private OnGetPassListener mOnGetPassListener;
	private GetPassBusiness mGetPassBusiness;
	private GetPassTask mGetPassTask;

	private ProgressDialog mProgressDialog;
	private EditText etEmail;
	private Button btnSubmit;
	private Button btnBack;
	private View mView;

	public interface OnGetPassListener {
		public abstract void onGetPassButtonClicked();
	}

	public FogotPassDialog(Context pContext,
			OnGetPassListener pOnGetPassListener, View pView) {
		super(pContext);
		mContext = pContext;
		mOnGetPassListener = pOnGetPassListener;
		mView = pView;
	}

	public FogotPassDialog(Context pContext, int pTheme,
			OnGetPassListener pOnGetPassListener, View pView) {
		super(pContext, pTheme);
		mContext = pContext;
		mOnGetPassListener = pOnGetPassListener;
		mView = pView;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fogot_pass_dialog);

		initVariable();
		initView();
		initListeners();
	}

	private void initVariable() {
		mGetPassBusiness = new GetPassBusiness(mContext);
	}

	private void initView() {
		etEmail = (EditText) findViewById(R.id.etEmail);
		btnSubmit = (Button) findViewById(R.id.btnSubmit);
		btnBack = (Button) findViewById(R.id.btnBack);
	}

	private void initListeners() {
		btnSubmit.setOnClickListener(this);
		btnBack.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.btnBack:
			dismiss();
			mView.setVisibility(View.VISIBLE);
			break;
		case R.id.btnSubmit:
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

			onSubmit(_email);
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

	private void onSubmit(String pEmail) {
		if (mGetPassTask == null
				|| mGetPassTask.getStatus() == AsyncTask.Status.FINISHED) {
			mGetPassTask = (GetPassTask) new GetPassTask().execute(pEmail);
		} else {
			UIUtilities.showToast(mContext,
					R.string.get_pass_error);
		}
	}

	private void onCancelGetPass() {
		if (mGetPassTask != null
				&& mGetPassTask.getStatus() == AsyncTask.Status.RUNNING) {
			mGetPassTask.cancel(true);
			mGetPassTask = null;
		}
	}

	@Override
	public void onDetachedFromWindow() {
		super.onDetachedFromWindow();
		onCancelGetPass();
	}

	private class GetPassTask extends AsyncTask<String, Void, Boolean> {

		@Override
		protected void onPreExecute() {
			showProgressDialog(R.string.wait);
		}

		@Override
		protected Boolean doInBackground(String... params) {
			return mGetPassBusiness.getPass(params[0]);
		}

		@Override
		protected void onCancelled() {
			dismissProgressDialog();
		}

		@Override
		protected void onPostExecute(Boolean result) {
			if (result) {
				mOnGetPassListener.onGetPassButtonClicked();
			} else {
				UIUtilities.showToast(mContext, R.string.get_pass_error);
			}
			dismissProgressDialog();
		}
	}

	protected void showProgressDialog(int pTitleResID) {
		mProgressDialog = new ProgressDialog(mContext);
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
