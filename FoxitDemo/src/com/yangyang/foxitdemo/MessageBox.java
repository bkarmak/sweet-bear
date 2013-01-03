package com.yangyang.foxitdemo;

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
	static Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message mesg) {
			throw new RuntimeException();
		}
	};
	TextView tvText;

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
				endDialog(MessageBox.this.tvText.getText().toString());
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

		setDialogResult(result);
		Message m = mHandler.obtainMessage();
		mHandler.sendMessage(m);
	}

	public String showDialog(String Msg, String Title) {
		this.tvText = (TextView) findViewById(R.id.textViewInfo);
		tvText.setText(Msg);
		TextView TvTitle = (TextView) findViewById(R.id.textViewTitle);
		TvTitle.setText(Title);
		super.show();
		tvText.requestFocus();
		try {
			Looper.getMainLooper();
			Looper.loop();
		} catch (RuntimeException e2) {
		}
		super.hide();
		return dialogResult;
	}
}