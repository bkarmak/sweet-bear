package com.wanhu.android.shelves.controls;

import android.content.Context;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.wanhu.android.shelves.R;

public class GotoPopupWindow extends PopupWindowBetter implements
		OnEditorActionListener {

	private Context mContext;
	private OnGotoListener mGotoListener;

	private ViewGroup mRoot;
	private EditText etGoto;

	public interface OnGotoListener {
		public abstract void onGoto(int pPageNumber);
	}

	public GotoPopupWindow(View anchor, OnGotoListener pOnGotoListener) {
		super(anchor);

		mGotoListener = pOnGotoListener;

		initVariable();
		initView();
		initListeners();
	}

	@Override
	protected void onCreate() {
		mContext = this.anchor.getContext();
		LayoutInflater inflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mRoot = (ViewGroup) inflater.inflate(R.layout.menu_goto, null);
		this.setContentView(mRoot);
	}

	private void initVariable() {
		// TODO Auto-generated method stub

	}

	private void initView() {
		etGoto = (EditText) mRoot.findViewById(R.id.etGoto);

	}

	private void initListeners() {
		etGoto.setOnEditorActionListener(this);

	}

	@Override
	public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
		if (actionId == EditorInfo.IME_ACTION_DONE) {
			mGotoListener.onGoto(Integer.parseInt(etGoto.getText().toString()));
		}
		etGoto.setText("");
		return false;
	}

}
