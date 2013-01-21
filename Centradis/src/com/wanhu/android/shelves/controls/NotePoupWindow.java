package com.wanhu.android.shelves.controls;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wanhu.android.shelves.R;
import com.yangyang.foxitsdk.service.PDFDoc.AnnotationType;

public class NotePoupWindow extends PopupWindowBetter implements
		OnClickListener {

	private Context mContext;
	private OnNoteListener mOnNoteListener;

	private ViewGroup mRoot;
	private TextView tvShowNote;
	private TextView tvRemoveNote;
	private TextView tvAddNote;
	private TextView tvAddLine;

	public interface OnNoteListener {

		public abstract void onShowNote();

		public abstract void onRemoveNote();

		public abstract void onAddAnnotation(AnnotationType type);
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
		tvAddNote = (TextView) mRoot.findViewById(R.id.tvAddNote);
		tvAddLine = (TextView) mRoot.findViewById(R.id.tvAddLine);
	}

	private void initListeners() {
		tvShowNote.setOnClickListener(this);
		tvRemoveNote.setOnClickListener(this);
		tvAddNote.setOnClickListener(this);
		tvAddLine.setOnClickListener(this);
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
		case R.id.tvAddNote:
			this.dismiss();
			mOnNoteListener.onAddAnnotation(AnnotationType.NOTE);
			break;
		case R.id.tvAddLine:
			this.dismiss();
			mOnNoteListener.onAddAnnotation(AnnotationType.PENCIL);
			break;
		default:
			break;
		}
	}

}
