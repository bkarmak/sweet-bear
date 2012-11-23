package com.wanhu.android.shelves.controls;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Handler;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.PopupWindow;

import com.wanhu.android.shelves.R;
import com.wanhu.android.shelves.controls.RegisterPopupWindow.OnRegisterListener;
import com.wanhu.android.shelves.util.Preferences;
import com.wanhu.android.shelves.util.UIUtilities;

public class LoginPopupWindow extends PopupWindow implements
		android.view.View.OnClickListener, OnRegisterListener {

	private View mDecorView;
	private Activity mContext;
	private OnLoginListener mLoginListener;
	private SharedPreferences mPreferences;
	private RegisterPopupWindow mRegisterPopupWindow;
	private ViewGroup mRoot;
	private final Handler mHandler = new Handler();
	private final Runnable mShowPopup = new Runnable() {
		public void run() {
			showPopup();
		}
	};

	private final Runnable mDismissPopup = new Runnable() {
		public void run() {
			mHandler.removeCallbacks(mShowPopup);
			dismissPopup();
		}
	};

	private EditText etUserName;
	private EditText etPassword;
	private Button btnLogin;
	private Button btnRegister;
	private CheckBox cbStayConnected;
	
	private static final int WINDOW_DISMISS_DELAY = 600;
	private static final int WINDOW_SHOW_DELAY = 600;

	public interface OnLoginListener {
		public abstract void onLoginButtonClicked(String pUserName,
				String pPassword);
	}

	
	public LoginPopupWindow(Activity pContext,OnLoginListener pLoginListener) {
		super(pContext);
		mContext = pContext;
		mLoginListener = pLoginListener;

		LayoutInflater inflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mRoot = (ViewGroup) inflater.inflate(R.layout.login_dialog, null);
		this.setContentView(mRoot);
		this.setFocusable(true);
		this.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
		this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
		this.setBackgroundDrawable(null);
		this.setAnimationStyle(R.style.PopupAnimation);

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

		etUserName = (EditText) mRoot.findViewById(R.id.etUserName);
		etPassword = (EditText) mRoot.findViewById(R.id.etPassword);
		btnLogin = (Button) mRoot.findViewById(R.id.btnLogin);
		btnRegister = (Button) mRoot.findViewById(R.id.btnRegister);
		cbStayConnected = (CheckBox) mRoot.findViewById(R.id.cbStayConnected);
		mDecorView = mContext.getWindow().getDecorView();
	}

	private void initListeners() {
		btnLogin.setOnClickListener(this);
		btnRegister.setOnClickListener(this);
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
			final Runnable showPopup = mShowPopup;
			mHandler.removeCallbacks(showPopup);
			mHandler.postDelayed(showPopup, WINDOW_SHOW_DELAY);
			break;

		default:
			break;
		}

	}

	@Override
	public void onRegisterButtonClicked() {
		final Runnable dismissPopup = mDismissPopup;
		mHandler.removeCallbacks(dismissPopup);
		mHandler.postDelayed(dismissPopup, WINDOW_DISMISS_DELAY);
	}
	
	private void showPopup() {
		if (mRegisterPopupWindow == null) {
			mRegisterPopupWindow = new RegisterPopupWindow(mContext,this);
		}

		if (mDecorView.getWindowVisibility() == View.VISIBLE) {
			mRegisterPopupWindow.showAtLocation(mDecorView, Gravity.CENTER, 0, 0);
		}
	}

	private void dismissPopup() {
		if (mRegisterPopupWindow != null) {
			mRegisterPopupWindow.dismiss();
		}
	}
	
}