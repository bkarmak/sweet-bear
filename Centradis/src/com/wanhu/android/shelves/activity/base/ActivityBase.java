package com.wanhu.android.shelves.activity.base;

import java.lang.reflect.Field;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.widget.Toast;

import com.wanhu.android.shelves.R;

public class ActivityBase extends Activity {

	protected static final int SHOW_TIME = 1;
	private ProgressDialog mProgressDialog;
	
	protected void showMsg(String pMsg) {
		Toast.makeText(this, pMsg, SHOW_TIME).show();
	}

	protected void showMsg(int pResID) {
		Toast.makeText(this, pResID, SHOW_TIME).show();
	}

	protected void openActivity(Class<?> pClass) {
		Intent _Intent = new Intent();
		_Intent.setClass(this, pClass);
		startActivity(_Intent);
	}

	protected LayoutInflater getLayouInflater() {
		LayoutInflater _LayoutInflater = LayoutInflater.from(this);
		return _LayoutInflater;
	}

	public void setAlertDialogIsClose(DialogInterface pDialog, Boolean pIsClose) {
		try {
			Field _Field = pDialog.getClass().getSuperclass()
					.getDeclaredField("mShowing");
			_Field.setAccessible(true);
			_Field.set(pDialog, pIsClose);
		} catch (Exception e) {
		}
	}

	protected AlertDialog showAlertDialog(int pTitelResID, String pMessage,
			DialogInterface.OnClickListener pClickListener) {
		String _title = getResources().getString(pTitelResID);
		return showAlertDialog(_title, pMessage, pClickListener);
	}

	protected AlertDialog showAlertDialog(String pTitle, String pMessage,
			DialogInterface.OnClickListener pClickListener) {
		return new AlertDialog.Builder(this).setTitle(pTitle)
				.setMessage(pMessage)
				.setPositiveButton(R.string.button_text_yes, pClickListener)
				.setNegativeButton(R.string.button_text_no, null).show();
	}

	protected void showProgressDialog(int pTitleResID, int pMessageResID) {
		mProgressDialog = new ProgressDialog(this);
		mProgressDialog.setTitle(getString(pTitleResID));
		mProgressDialog.setMessage(getString(pMessageResID));
		mProgressDialog.show();
	}

	protected void dismissProgressDialog() {
		if (mProgressDialog != null) {
			mProgressDialog.dismiss();
		}
	}
	
}
