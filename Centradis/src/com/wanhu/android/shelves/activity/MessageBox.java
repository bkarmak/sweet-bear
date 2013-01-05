package com.wanhu.android.shelves.activity;

import com.wanhu.android.shelves.R;

import android.app.Activity;
import android.app.Dialog;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.view.Window.Callback;
import android.widget.TextView;

public class MessageBox extends Dialog {

	private TextView tvText;

	public interface IMessageBoxResult {
		void onResult(String result);
	}

	IMessageBoxResult callBack;

	public MessageBox(Activity context) {
		super(context);
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

	public void endDialog(String result) {
		this.hide();
		this.dismiss();
		if (this.callBack != null) {
			this.callBack.onResult(result);
		}
	}

	public String showDialog(String Msg, String Title,
			IMessageBoxResult callBack) {
		this.callBack = callBack;
		this.tvText = (TextView) findViewById(R.id.textViewInfo);
		tvText.setText(Msg);
		TextView TvTitle = (TextView) findViewById(R.id.textViewTitle);
		TvTitle.setText(Title);
		tvText.requestFocus();
		super.show();
		return null;
	}
}