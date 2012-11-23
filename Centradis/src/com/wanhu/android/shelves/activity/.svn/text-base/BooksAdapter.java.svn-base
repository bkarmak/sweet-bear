package com.wanhu.android.shelves.activity;

import android.content.Context;
import android.database.CharArrayBuffer;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.FilterQueryProvider;
import android.widget.ImageView;
import android.widget.TextView;

import com.wanhu.android.shelves.R;
import com.wanhu.android.shelves.drawable.FastBitmapDrawable;
import com.wanhu.android.shelves.provider.BooksStore;
import com.wanhu.android.shelves.util.ImageUtilities;

class BooksAdapter extends CursorAdapter implements FilterQueryProvider {
	private static final String[] PROJECTION_IDS_AND_TITLE = new String[] {
			BooksStore.Book._ID, BooksStore.Book.INTERNAL_ID,
			BooksStore.Book.TITLE, BooksStore.Book.SORT_TITLE,
			BooksStore.Book.EAN };

	private final LayoutInflater mInflater;
	private final int mTitleIndex;
	private final int mSortTitleIndex;
	private final int mInternalIdIndex;
	private final int mLocalPath;
	private final String mSelection;
	private final ShelvesActivity mActivity;
	private final Bitmap mDefaultCoverBitmap;
	private final FastBitmapDrawable mDefaultCover;
	private final String[] mArguments2 = new String[2];

	BooksAdapter(ShelvesActivity activity, String userId) {
		super(activity, activity.managedQuery(BooksStore.Book.CONTENT_URI,
				PROJECTION_IDS_AND_TITLE, BooksStore.Book.ISBN + "='" + userId
						+ "'", null, BooksStore.Book.DEFAULT_SORT_ORDER), true);

		final Cursor c = getCursor();

		mActivity = activity;
		mInflater = LayoutInflater.from(activity);
		mTitleIndex = c.getColumnIndexOrThrow(BooksStore.Book.TITLE);
		mSortTitleIndex = c.getColumnIndexOrThrow(BooksStore.Book.SORT_TITLE);
		mInternalIdIndex = c.getColumnIndexOrThrow(BooksStore.Book.INTERNAL_ID);
		mLocalPath = c.getColumnIndexOrThrow(BooksStore.Book.EAN);

		mDefaultCoverBitmap = BitmapFactory.decodeResource(
				activity.getResources(), R.drawable.pdf_icon_2);
		mDefaultCover = new FastBitmapDrawable(mDefaultCoverBitmap);

		final StringBuilder selection = new StringBuilder();
		selection.append(BooksStore.Book.TITLE);
		selection.append(" LIKE ? OR ");
		selection.append(BooksStore.Book.AUTHORS);
		selection.append(" LIKE ?");
		mSelection = selection.toString();

		setFilterQueryProvider(this);
	}

	FastBitmapDrawable getDefaultCover() {
		return mDefaultCover;
	}

	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		final View convertView = mInflater.inflate(R.layout.shelf_book, parent,
				false);

		BookViewHolder holder = new BookViewHolder();
		holder.title = (TextView) convertView.findViewById(R.id.title);
		holder.title.setSelected(true);
		holder.ivBookCover = (ImageView) convertView
				.findViewById(R.id.ivBookCover);
		holder.ivUpdateLogo = (ImageView) convertView
				.findViewById(R.id.ivUpdateLogo);

		convertView.setTag(holder);

		return convertView;
	}

	public void bindView(View view, Context context, Cursor c) {
		BookViewHolder holder = (BookViewHolder) view.getTag();
		String bookId = c.getString(mInternalIdIndex);
		holder.bookId = bookId;
		holder.sortTitle = c.getString(mSortTitleIndex);
		holder.localPath = c.getString(mLocalPath);
		if (bookId.startsWith("localSB")) {
			int a = bookId.indexOf("|");
			holder.ivBookCover.setBackgroundDrawable(ImageUtilities
					.getCachedCover(bookId.substring(7,a), mDefaultCover));
		} else {
			holder.ivBookCover.setBackgroundDrawable(ImageUtilities
					.getCachedCover(bookId, mDefaultCover));

		}

		final CharArrayBuffer buffer = holder.buffer;
		c.copyStringToBuffer(mTitleIndex, buffer);
		final int size = buffer.sizeCopied;
		if (size != 0) {
			holder.title.setText(buffer.data, 0, size);
		}
	}

	@Override
	public void changeCursor(Cursor cursor) {
		final Cursor oldCursor = getCursor();
		if (oldCursor != null)
			mActivity.stopManagingCursor(oldCursor);
		super.changeCursor(cursor);
	}

	public Cursor runQuery(CharSequence constraint) {
		if (constraint == null || constraint.length() == 0) {
			return mActivity.managedQuery(BooksStore.Book.CONTENT_URI,
					PROJECTION_IDS_AND_TITLE, null, null,
					BooksStore.Book.DEFAULT_SORT_ORDER);
		}

		final StringBuilder buffer = new StringBuilder();
		buffer.append('%').append(constraint).append('%');
		final String pattern = buffer.toString();

		final String[] arguments2 = mArguments2;
		arguments2[0] = arguments2[1] = pattern;
		return mActivity.managedQuery(BooksStore.Book.CONTENT_URI,
				PROJECTION_IDS_AND_TITLE, mSelection, arguments2,
				BooksStore.Book.DEFAULT_SORT_ORDER);
	}
}
