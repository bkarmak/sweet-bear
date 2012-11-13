package com.wanhu.android.shelves.activity;

import android.database.CharArrayBuffer;
import android.widget.ImageView;
import android.widget.TextView;

class BookViewHolder {
	ImageView ivBookCover;
	ImageView ivUpdateLogo;
	TextView title;
	String bookId;
	final CharArrayBuffer buffer = new CharArrayBuffer(64);
	String sortTitle;
	public String localPath;
	boolean needUpdate = false;
}
