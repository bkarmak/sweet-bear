package com.wanhu.android.shelves.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.wanhu.android.shelves.R;
import com.wanhu.android.shelves.model.ModelBookmark;

public class AdapterBookMark extends BaseAdapter {

	private ViewHolder mViewHolder;
	private Context mContext;
	private List<ModelBookmark> mBookmarks;

	private static class ViewHolder {
		TextView tvPage;
		TextView tvPageInfo;
	}

	public AdapterBookMark(Context pContext, List<ModelBookmark> pBookmarks) {
		mContext = pContext;
		mBookmarks = pBookmarks;
	}

	@Override
	public int getCount() {
		return mBookmarks.size();
	}

	@Override
	public Object getItem(int position) {
		return mBookmarks.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ModelBookmark _bookmark = (ModelBookmark) getItem(position);

		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.layout_bookmark_item, null);
			mViewHolder = new ViewHolder();
			mViewHolder.tvPage = (TextView) convertView
					.findViewById(R.id.tvPage);
			mViewHolder.tvPageInfo = (TextView) convertView
					.findViewById(R.id.tvPageInfo);
			convertView.setTag(mViewHolder);
		} else {
			mViewHolder = (ViewHolder) convertView.getTag();
		}

		mViewHolder.tvPageInfo.setText(_bookmark.getName());
		int _pageNumber = _bookmark.getPageNumber();
		mViewHolder.tvPage.setText(mContext.getString(R.string.page_number,
				_pageNumber + ""));

		return convertView;
	}

}
