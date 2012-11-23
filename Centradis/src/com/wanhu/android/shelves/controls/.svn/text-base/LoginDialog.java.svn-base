package com.wanhu.android.shelves.controls;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wanhu.android.shelves.R;
import com.wanhu.android.shelves.controls.FogotPassDialog.OnGetPassListener;
import com.wanhu.android.shelves.controls.RegisterDialog.OnRegisterListener;
import com.wanhu.android.shelves.util.Preferences;
import com.wanhu.android.shelves.util.UIUtilities;

public class LoginDialog extends Dialog implements
		android.view.View.OnClickListener, OnRegisterListener,
		OnGetPassListener {

	private Context mContext;
	private OnLoginListener mLoginListener;
	private SharedPreferences mPreferences;
	private RegisterDialog mRegisterDialog;
	private FogotPassDialog mFogotPassDialog;

	private LinearLayout llLogin;
	private EditText etUserName;
	private EditText etPassword;
	private Button btnLogin;
	private Button btnRegister;
	private CheckBox cbStayConnected;
	private TextView tvForgotPass;

	public interface OnLoginListener {
		public abstract void onLoginButtonClicked(String pUserName,
				String pPassword);

	}

	public LoginDialog(Context pContext, OnLoginListener pLoginListener) {
		super(pContext);
		mContext = pContext;
		mLoginListener = pLoginListener;
	}

	public LoginDialog(Context pContext, int pTheme,
			OnLoginListener pLoginListener) {
		super(pContext, pTheme);
		mContext = pContext;
		mLoginListener = pLoginListener;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_dialog);

		initVariable();
		initView();
		initListeners();
	}

	public void setViewValues(String pUserName, String pPassword,
			boolean pStayConnected) {
		etUserName.setText(pUserName);
		etPassword.setText(pPassword);
		cbStayConnected.setChecked(pStayConnected);
	}

	private void initVariable() {
		mPreferences = mContext.getSharedPreferences(Preferences.NAME, 0);
	}

	private void initView() {
		llLogin = (LinearLayout) findViewById(R.id.llLogin);
		etUserName = (EditText) findViewById(R.id.etUserName);
		etPassword = (EditText) findViewById(R.id.etPassword);
		btnLogin = (Button) findViewById(R.id.btnLogin);
		btnRegister = (Button) findViewById(R.id.btnRegister);
		cbStayConnected = (CheckBox) findViewById(R.id.cbStayConnected);
		mRegisterDialog = new RegisterDialog(mContext, R.style.CentradisDialog,
				this, llLogin);
		mFogotPassDialog = new FogotPassDialog(mContext,
				R.style.CentradisDialog, this, llLogin);
		tvForgotPass = (TextView) findViewById(R.id.tvForgotPass);
	}

	private void initListeners() {
		btnLogin.setOnClickListener(this);
		btnRegister.setOnClickListener(this);
		tvForgotPass.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnLogin:

			String _name = etUserName.getText().toString();
			String _password = etPassword.getText().toString();
			if (TextUtils.isEmpty(_name)) {
				UIUtilities.showToast(mContext, R.string.login_name_hint);
				etUserName.setFocusable(true);
			} else if (TextUtils.isEmpty(_password)) {
				UIUtilities.showToast(mContext, R.string.login_pass_hint);
				etPassword.setFocusable(true);
			} else {
				if (cbStayConnected.isChecked()) {
					final Editor _editor = mPreferences.edit();
					_editor.putString(Preferences.KEY_USER_NAME, _name);
					_editor.putString(Preferences.KEY_USER_PASSWORD, _password);
					_editor.putBoolean(Preferences.KEY_STAY_CONNECTED, true);
					_editor.commit();
				} else {
					final Editor _editor = mPreferences.edit();
					_editor.putString(Preferences.KEY_USER_NAME, "");
					_editor.putString(Preferences.KEY_USER_PASSWORD, "");
					_editor.putBoolean(Preferences.KEY_STAY_CONNECTED, false);
					_editor.commit();
				}
				mLoginListener.onLoginButtonClicked(_name, _password);
			}

			break;

		case R.id.btnRegister:
			llLogin.setVisibility(View.GONE);
			mRegisterDialog.show();
			break;
		case R.id.tvForgotPass:
			llLogin.setVisibility(View.GONE);
			mFogotPassDialog.show();
			break;

		default:
			break;
		}

	}

	@Override
	public void onRegisterButtonClicked() {
		UIUtilities.showToast(mContext, R.string.register_successful);
	}

	@Override
	public void onGetPassButtonClicked() {
		UIUtilities.showToast(mContext, R.string.get_pass_successful);
	}

	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			dismiss();
		}
		return true;
	}

	@Override
	public void onDetachedFromWindow() {
		super.onDetachedFromWindow();
		if (mRegisterDialog != null) {
			mRegisterDialog.dismiss();
		}

		if (mFogotPassDialog != null) {
			mFogotPassDialog.dismiss();
		}
	}

}