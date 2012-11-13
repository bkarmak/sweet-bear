package com.wanhu.android.shelves.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.wanhu.android.shelves.R;

public class AdapterSearchResults{
	
}
/*
public class AdapterSearchResults extends ArrayAdapter<TextSearchResult> {

	private final LayoutInflater mLayoutInflater;
	private ViewHolder mViewHolder;

	private class ViewHolder {
		TextView tvAmbient;
		TextView tvPage;
	}

	public AdapterSearchResults(Activity activity) {
		super(activity, 0);
		mLayoutInflater = LayoutInflater.from(activity);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		TextSearchResult _textSearchResult = (TextSearchResult) getItem(position);

		if (convertView == null) {
			convertView = mLayoutInflater.inflate(
					R.layout.layout_searchresults_item, null);
			mViewHolder = new ViewHolder();
			mViewHolder.tvAmbient = (TextView) convertView
					.findViewById(R.id.tvAmbient);
			mViewHolder.tvPage = (TextView) convertView
					.findViewById(R.id.tvPage);

			convertView.setTag(mViewHolder);
		} else {
			mViewHolder = (ViewHolder) convertView.getTag();
		}

		mViewHolder.tvAmbient.setText(_textSearchResult.getAmbientStr());
		mViewHolder.tvPage.setText(_textSearchResult.getPageNum() + "");

		return convertView;
	}

}
*/