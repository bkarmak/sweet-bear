package com.yangyang.foxitdemo;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

public class MessageBox extends Dialog {

	String dialogResult;
	Handler mHandler;
	TextView tvTest;

	public MessageBox(Activity context) {
		super(context);
		dialogResult = null;
		setOwnerActivity(context);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		onCreate();
	}

	public void onCreate() {
		setContentView(R.layout.messagebox);
		findViewById(R.id.btnCancel).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View paramView) {
						endDialog(null);
					}
				});
		findViewById(R.id.btnOK).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View paramView) {
				endDialog(MessageBox.this.tvTest.getText().toString());
			}
		});
	}

	public String getDialogResult() {
		return dialogResult;
	}

	public void setDialogResult(String dialogResult) {
		this.dialogResult = dialogResult;
	}

	public void endDialog(String result) {
		dismiss();
		setDialogResult(result);
		Message m = mHandler.obtainMessage();
		mHandler.sendMessage(m);
	}

	@SuppressLint("HandlerLeak")
	public String showDialog(String Msg, String Title) {
		this.tvTest = (TextView) findViewById(R.id.textViewInfo);
		tvTest.setText(Msg);
		TextView TvTitle = (TextView) findViewById(R.id.textViewTitle);
		TvTitle.setText(Title);

		mHandler = new Handler() {
			@Override
			public void handleMessage(Message mesg) {
				throw new RuntimeException();
			}
		};
		super.show();
		try {
			Looper.getMainLooper();
			Looper.loop();
		} catch (RuntimeException e2) {
		}
		return dialogResult;
	}
}