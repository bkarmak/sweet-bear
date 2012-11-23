package com.wanhu.android.shelves.controls;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wanhu.android.shelves.R;

public class BookmarkPopupWindow extends PopupWindowBetter implements
		OnClickListener {

	private Context mContext;
	private OnBookMarkListener mOnBookMarkListener;

	private ViewGroup mRoot;
	private TextView tvAddBookmark;
	private TextView tvShowBookmark;
	private TextView tvRemoveBookmark;

	public interface OnBookMarkListener {

		public abstract void onAddBookMark();

		public abstract void onShowBookMark();

		public abstract void onRemoveBookMark();
	}

	public BookmarkPopupWindow(View anchor,
			OnBookMarkListener pOnBookMarkListener) {
		super(anchor);

		mOnBookMarkListener = pOnBookMarkListener;

		initVariable();
		initView();
		initListeners();
	}

	@Override
	protected void onCreate() {
		mContext = this.anchor.getContext();
		LayoutInflater inflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mRoot = (ViewGroup) inflater.inflate(R.layout.menu_bookmark, null);
		this.setContentView(mRoot);
	}

	private void initVariable() {

	}

	private void initView() {
		tvAddBookmark = (TextView) mRoot.findViewById(R.id.tvAddBookmark);
		tvShowBookmark = (TextView) mRoot.findViewById(R.id.tvShowBookmark);
		tvRemoveBookmark = (TextView) mRoot.findViewById(R.id.tvRemoveBookmark);
	}

	private void initListeners() {
		tvAddBookmark.setOnClickListener(this);
		tvShowBookmark.setOnClickListener(this);
		tvRemoveBookmark.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tvAddBookmark:
			mOnBookMarkListener.onAddBookMark();
			break;
		case R.id.tvShowBookmark:
			mOnBookMarkListener.onShowBookMark();
			break;
		case R.id.tvRemoveBookmark:
			mOnBookMarkListener.onRemoveBookMark();
			break;

		default:
			break;
		}

	}

}
