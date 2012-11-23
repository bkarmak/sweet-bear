package com.wanhu.android.shelves.controls;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wanhu.android.shelves.R;

public class NotePoupWindow extends PopupWindowBetter implements
		OnClickListener {

	private Context mContext;
	private OnNoteListener mOnNoteListener;

	private ViewGroup mRoot;
	private TextView tvShowNote;
	private TextView tvRemoveNote;

	public interface OnNoteListener {

		public abstract void onShowNote();

		public abstract void onRemoveNote();
	}

	public NotePoupWindow(View anchor, OnNoteListener pOnNoteListener) {
		super(anchor);
		mOnNoteListener = pOnNoteListener;

		initVariable();
		initView();
		initListeners();
	}

	@Override
	protected void onCreate() {
		mContext = this.anchor.getContext();
		LayoutInflater inflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mRoot = (ViewGroup) inflater.inflate(R.layout.menu_note, null);
		this.setContentView(mRoot);
	}

	private void initVariable() {
		// TODO Auto-generated method stub

	}

	private void initView() {
		tvShowNote = (TextView) mRoot.findViewById(R.id.tvShowNote);
		tvRemoveNote = (TextView) mRoot.findViewById(R.id.tvRemoveNote);
	}

	private void initListeners() {
		tvShowNote.setOnClickListener(this);
		tvRemoveNote.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tvShowNote:
			mOnNoteListener.onShowNote();
			break;

		case R.id.tvRemoveNote:
			mOnNoteListener.onRemoveNote();
			break;
		default:
			break;
		}
	}

}
