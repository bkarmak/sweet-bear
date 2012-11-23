package com.wanhu.android.shelves.controls;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.wanhu.android.shelves.R;

public class RegisterPopupWindow extends PopupWindow {

	private Context mContext;
	private OnRegisterListener mOnRegisterListener;
	private ViewGroup mRoot;

	public interface OnRegisterListener {
		public abstract void onRegisterButtonClicked();
	}

	public RegisterPopupWindow(Context pContext,
			OnRegisterListener pOnRegisterListener) {
		super(pContext);
		mContext = pContext;
		mOnRegisterListener = pOnRegisterListener;

		LayoutInflater inflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mRoot = (ViewGroup) inflater.inflate(R.layout.register_dialog, null);
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

	private void initVariable() {

	}

	private void initView() {

	}

	private void initListeners() {

	}

}
