package com.wanhu.android.shelves.controls;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;

import com.wanhu.android.shelves.R;
import com.wanhu.android.shelves.adapter.AdapterBookMark;
import com.wanhu.android.shelves.business.BusinessBookmark;
import com.wanhu.android.shelves.model.ModelBookmark;

public class ShowBookMarkPopUpWindow extends PopupWindowBetter implements
		OnItemClickListener, OnItemLongClickListener {

	private List<ModelBookmark> mBookmarks;
	private AdapterBookMark mAdapterBookMark;
	private ModelBookmark mRootBookMark;
	private String mBookId;
	private OnBookMarkClickListener mOnBookMarkClickListener;
	private Context mContext;
	private BusinessBookmark mBusinessBookmark;
	private ListView lvBookMarks;
	private ViewGroup mRoot;

	public interface OnBookMarkClickListener {
		public abstract void onBookMarkClick(int pPageNumber);
	};

	public ShowBookMarkPopUpWindow(View anchor, String pBookId,
			OnBookMarkClickListener pOnBookMarkClickListener) {

		super(anchor);
		mBookId = pBookId;
		mOnBookMarkClickListener = pOnBookMarkClickListener;

		initVariable();
		initView();
		initListeners();
		bindData();

	}

	@Override
	protected void onCreate() {

		mContext = this.anchor.getContext();
		LayoutInflater inflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mRoot = (ViewGroup) inflater
				.inflate(R.layout.popup_show_bookmark, null);
		this.setContentView(mRoot);
	}

	private void initVariable() {
		mBusinessBookmark = new BusinessBookmark(mContext);

		mBookmarks = mBusinessBookmark.getBookmarksByBookID(mBookId);

		mAdapterBookMark = new AdapterBookMark(mContext, mBookmarks);

	}

	private void initView() {
		lvBookMarks = (ListView) mRoot.findViewById(R.id.lvBookMarks);

	}

	private void initListeners() {
		lvBookMarks.setOnItemClickListener(this);
	}

	private void bindData() {
		lvBookMarks.setAdapter(mAdapterBookMark);
	}

	@Override
	public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		return false;
	}

	@Override
	public void onItemClick(AdapterView<?> pAdapterView, View pView,
			int pPosition, long arg3) {
		ModelBookmark _bookMark = (ModelBookmark) ((Adapter) pAdapterView
				.getAdapter()).getItem(pPosition);
		mOnBookMarkClickListener.onBookMarkClick(_bookMark.getPageNumber());

	}

}
