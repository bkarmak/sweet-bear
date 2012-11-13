package com.wanhu.android.shelves.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wanhu.android.shelves.R;
import com.wanhu.android.shelves.ShelvesApplication;
import com.wanhu.android.shelves.drawable.FastBitmapDrawable;
import com.wanhu.android.shelves.provider.BookStoreFactory;
import com.wanhu.android.shelves.provider.BooksManager;
import com.wanhu.android.shelves.provider.BooksStore;
import com.wanhu.android.shelves.provider.BooksStore.Book;
import com.wanhu.android.shelves.provider.CentradisBooksStore;
import com.wanhu.android.shelves.util.IOUtilities;
import com.wanhu.android.shelves.util.IOUtilities.OnPublishProgress;
import com.wanhu.android.shelves.util.ImageUtilities;
import com.wanhu.android.shelves.util.TextUtilities;
import com.wanhu.android.shelves.util.UIUtilities;
import com.wanhu.android.shelves.util.UserTask;

public class BookStoreActivity extends Activity implements
		AdapterView.OnItemClickListener, OnClickListener {

	private View mSearchPanel;
	private View mAddPanel;
	private ProgressBar mProgressBar;
	private GridView mGrid;

	private static final int DIALOG_ADD = 1;
	private static final int DIALOG_SEE = 2;

	private static final String STATE_ADD_IN_PROGRESS = "shelves.add.inprogress";
	private static final String STATE_ADD_BOOK = "shelves.add.book";

	private static final String STATE_SEARCH_IN_PROGRESS = "shelves.search.inprogress";
	private static final String STATE_SEARCH_QUERY = "shelves.search.book";

	private static final String STATE_BOOK_TO_ADD = "shelves.add.bookToAdd";

	public static final String LOG_OUT = "logout";

	private SearchTask mSearchTask;
	private AddTask mAddTask;

	private SearchResultsAdapter mBooksAdapter;
	private BooksStore.Book mBookToAdd;
	private Bitmap mBookCoverToAdd;
	private List<ResultBook> mResultBooks = new ArrayList<BookStoreActivity.ResultBook>();
	private int mPosition;

	// Moon
	private Button btnHome;
	private Button btnLogout;
	private LinearLayout llBackground;
	private RelativeLayout rlTop;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.screen_bookstore);

		setupViews();
		setupListeners();

		// Look for the books on the online store
		onSearch();
	}

	private void setupViews() {

		mBooksAdapter = new SearchResultsAdapter(this, mResultBooks);

		final SearchResultsAdapter resultsAdapter = mBooksAdapter;
		final SearchResultsAdapter oldAdapter = (SearchResultsAdapter) getLastNonConfigurationInstance();

		if (oldAdapter != null) {
			final int count = oldAdapter.getCount();
			for (int i = 0; i < count; i++) {
				mResultBooks.add((ResultBook) oldAdapter.getItem(i));
				mBooksAdapter.notifyDataSetChanged();
			}
		}

		mGrid = (GridView) findViewById(R.id.grid_shelves);

		final GridView grid = mGrid;
		grid.setTextFilterEnabled(true);
		grid.setAdapter(resultsAdapter);
		grid.setOnItemClickListener(this);

		btnHome = (Button) findViewById(R.id.btnHome);
		btnLogout = (Button) findViewById(R.id.btnLogOut);

		rlTop = (RelativeLayout) findViewById(R.id.rlTop);
		llBackground = (LinearLayout) findViewById(R.id.llBackground);

	}

	private void setupListeners() {
		btnHome.setOnClickListener(this);
		btnLogout.setOnClickListener(this);
	}

	static void show(Context context) {
		final Intent intent = new Intent(context, BookStoreActivity.class);
		context.startActivity(intent);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

		onCancelAdd();
		onCancelSearch();
		IOUtilities.mOnPublishProgress = null;
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		restoreBookToAdd(savedInstanceState);
		restoreSearchTask(savedInstanceState);
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		if (isFinishing()) {
			saveBookToAdd(outState);
			saveSearchTask(outState);
		}
	}

	@Override
	public Object onRetainNonConfigurationInstance() {
		return mBooksAdapter;
	}

	private void saveBookToAdd(Bundle outState) {
		if (mBookToAdd != null) {
			outState.putParcelable(STATE_BOOK_TO_ADD, mBookToAdd);
		}
	}

	private void restoreBookToAdd(Bundle savedInstanceState) {
		final Object data = savedInstanceState.get(STATE_BOOK_TO_ADD);
		if (data != null) {
			mBookToAdd = (BooksStore.Book) data;
		}
	}

	private void saveSearchTask(Bundle outState) {
		final SearchTask task = mSearchTask;
		if (task != null && task.getStatus() != UserTask.Status.FINISHED) {
			final String bookId = task.getQuery();
			task.cancel(true);

			if (bookId != null) {
				outState.putBoolean(STATE_SEARCH_IN_PROGRESS, true);
				outState.putString(STATE_SEARCH_QUERY, bookId);
			}

			mSearchTask = null;
		}
	}

	private void restoreSearchTask(Bundle savedInstanceState) {
		if (savedInstanceState.getBoolean(STATE_SEARCH_IN_PROGRESS)) {
			final String query = savedInstanceState
					.getString(STATE_SEARCH_QUERY);
			if (!TextUtils.isEmpty(query)) {
				mSearchTask = (SearchTask) new SearchTask().execute(query);
			}
		}
	}

	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		mPosition = position;
		ResultBook _resultBook = (ResultBook) mBooksAdapter.getItem(position);
		mBookToAdd = _resultBook.book;

		mBookCoverToAdd = _resultBook.coverBitMap;

		showDialog(DIALOG_ADD);
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		final AlertDialog.Builder builder = new AlertDialog.Builder(this);
		switch (id) {
		case DIALOG_ADD:
			builder.setTitle(mBookToAdd != null ? mBookToAdd.getTitle() : " ");
			builder.setIcon(android.R.drawable.ic_dialog_alert);
			builder.setMessage(R.string.dialog_add_message);
			builder.setPositiveButton(R.string.dialog_add_ok,
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							onAdd(mBookToAdd, mBookCoverToAdd);
							mBookCoverToAdd = null;
						}
					});
			builder.setNegativeButton(R.string.dialog_add_cancel,
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							dismissDialog(DIALOG_ADD);
						}
					});
			builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
				public void onCancel(DialogInterface dialog) {
					dismissDialog(DIALOG_ADD);
				}
			});
			builder.setCancelable(true);

			return builder.create();
		case DIALOG_SEE:
			final String _title = mBookToAdd != null ? mBookToAdd.getTitle() : " ";
			builder.setTitle(_title);
			builder.setIcon(android.R.drawable.ic_dialog_alert);
			builder.setMessage(getString(R.string.dialog_see_message, _title));
			builder.setPositiveButton(R.string.cancel_label,
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {

							mResultBooks.remove(mPosition);
							mBooksAdapter.notifyDataSetChanged();

						}
					});
			builder.setNegativeButton(R.string.see_label,
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							
							/*onView(mBookToAdd.getInternalId(),
									mBookToAdd.getEan()); PRBL HERE  */
							onView(_title + ".pdf",
									mBookToAdd.getEan());
							
							BookStoreActivity.this.finish();
							dismissDialog(DIALOG_SEE);
						}
					});
			builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
				public void onCancel(DialogInterface dialog) {
					dismissDialog(DIALOG_SEE);
				}
			});
			builder.setCancelable(true);

			return builder.create();

		}

		return super.onCreateDialog(id);
	}

	@Override
	protected void onPrepareDialog(int id, Dialog dialog) {
		super.onPrepareDialog(id, dialog);

		switch (id) {
		case DIALOG_ADD:
			dialog.setTitle(mBookToAdd.getTitle());
			break;
		case DIALOG_SEE:
			dialog.setTitle(mBookToAdd.getTitle());
			break;
		}
	}

	private void onSearch() {
		if (mSearchTask == null
				|| mSearchTask.getStatus() == SearchTask.Status.FINISHED) {
			mSearchTask = (SearchTask) new SearchTask().execute("");
		} else {
			UIUtilities.showToast(this, R.string.error_search_in_progress);
		}
	}

	private void onCancelSearch() {
		if (mSearchTask != null
				&& mSearchTask.getStatus() == UserTask.Status.RUNNING) {
			mSearchTask.cancel(true);
			mSearchTask = null;
		}
	}

	private void onAdd(BooksStore.Book pBookToAdd, Bitmap pBookCoverToAdd) {
		if (!BooksManager.bookExists(getContentResolver(),
				pBookToAdd.getInternalId())) {
			mAddTask = (AddTask) new AddTask().execute(pBookToAdd,
					pBookCoverToAdd);
		} else {
			UIUtilities.showToast(this, R.string.error_book_exists);
		}
	}

	private void onCancelAdd() {
		if (mAddTask != null && mAddTask.getStatus() == UserTask.Status.RUNNING) {
			mAddTask.cancel(true);
			mAddTask = null;
		}
	}

	private void showPanel(View panel, boolean slideUp) {
		panel.startAnimation(AnimationUtils.loadAnimation(this,
				slideUp ? R.anim.slide_in : R.anim.slide_out_top));
		panel.setVisibility(View.VISIBLE);
	}

	private void hidePanel(View panel, boolean slideDown) {
		panel.startAnimation(AnimationUtils.loadAnimation(this,
				slideDown ? R.anim.slide_out : R.anim.slide_in_top));
		panel.setVisibility(View.GONE);
	}

	private class AddTask extends UserTask<Object, String, BooksStore.Book>
			implements OnPublishProgress {
		private FastBitmapDrawable mDefaultCover;

		@Override
		public void onPreExecute() {
			final Bitmap defaultCoverBitmap = BitmapFactory.decodeResource(
					getResources(), R.drawable.pdf_icon_2);
			mDefaultCover = new FastBitmapDrawable(defaultCoverBitmap);

			if (mAddPanel == null) {
				mAddPanel = ((ViewStub) findViewById(R.id.stub_add)).inflate();
			}
			mProgressBar = ((ProgressBar) mAddPanel.findViewById(R.id.progress));
			mProgressBar.setIndeterminate(false);

			final View cancelButton = mAddPanel
					.findViewById(R.id.button_cancel);
			cancelButton.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					onCancelAdd();
				}
			});
			mProgressBar.setProgress(0);
			showPanel(mAddPanel, false);
		}

		public BooksStore.Book doInBackground(Object... params) {
			IOUtilities.mOnPublishProgress = this;
			return BooksManager.loadAndAddBook(getContentResolver(),
					(Book) params[0], (Bitmap) params[1],
					BookStoreFactory.get(BookStoreActivity.this));
		}

		@Override
		public void onProgressUpdate(String... progress) {
			mProgressBar.setProgress(Integer.parseInt(progress[0]));
		}

		@Override
		public void onPublisthProgress(String value) {
			publishProgress(value);
		}

		@Override
		public void onCancelled() {
			hidePanel(mAddPanel, false);
		}

		@Override
		public void onPostExecute(BooksStore.Book book) {
			if (book == null) {
				UIUtilities.showToast(BookStoreActivity.this,
						R.string.error_adding_book);
				IOUtilities.mOnPublishProgress = null;
				hidePanel(mAddPanel, false);
			} else {
				UIUtilities.showFormattedImageToast(BookStoreActivity.this,
						R.string.success_added, ImageUtilities.getCachedCover(
								book.getInternalId(), mDefaultCover), book
								.getTitle());
				IOUtilities.mOnPublishProgress = null;
				hidePanel(mAddPanel, false);

				showDialog(DIALOG_SEE);
			}

		}

	}

	private class SearchTask extends UserTask<String, ResultBook, Void>
			implements BooksStore.BookSearchListener {

		private final Object mLock = new Object();
		private String mQuery;

		@Override
		public void onPreExecute() {

			if (mSearchPanel == null) {
				mSearchPanel = ((ViewStub) findViewById(R.id.stub_search))
						.inflate();

				ProgressBar progress = (ProgressBar) mSearchPanel
						.findViewById(R.id.progress);
				progress.setIndeterminate(true);

				((TextView) findViewById(R.id.label_import))
						.setText(R.string.search_progress);

				final View cancelButton = mSearchPanel
						.findViewById(R.id.button_cancel);
				cancelButton.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						onCancelSearch();
					}
				});
			}

			mResultBooks.clear();
			mBooksAdapter.notifyDataSetChanged();
			showPanel(mSearchPanel, true);
		}

		String getQuery() {
			synchronized (mLock) {
				return mQuery;
			}
		}

		public Void doInBackground(String... params) {
			synchronized (mLock) {
				mQuery = params[0];
			}
			BookStoreFactory.get(BookStoreActivity.this).searchBooks(mQuery,
					this);

			return null;
		}

		@Override
		public void onProgressUpdate(ResultBook... values) {
			for (ResultBook book : values) {
				mResultBooks.add(book);
				mBooksAdapter.notifyDataSetChanged();
			}
		}

		@Override
		public void onPostExecute(Void ignore) {

			UIUtilities.showFormattedToast(BookStoreActivity.this,
					R.string.success_found, mBooksAdapter.getCount());
			hidePanel(mSearchPanel, true);
		}

		@Override
		public void onCancelled() {
			hidePanel(mSearchPanel, true);
		}

		public void onBookFound(BooksStore.Book book,
				ArrayList<BooksStore.Book> books) {
			if (book != null && !isCancelled()) {
				book.mIsbn = CentradisBooksStore.VALUE_USER_ID;
				publishProgress(new ResultBook(book));
			}
		}
	}

	private static class SearchResultsAdapter extends BaseAdapter {
		private final LayoutInflater mLayoutInflater;
		private final Bitmap mDefaultCoverBitmap;
		private final FastBitmapDrawable mDefaultCover;
		private List<ResultBook> mResultBooks;

		SearchResultsAdapter(BookStoreActivity activity,
				List<ResultBook> pResultBooks) {
			mDefaultCoverBitmap = BitmapFactory.decodeResource(
					activity.getResources(), R.drawable.pdf_icon_2);
			mDefaultCover = new FastBitmapDrawable(mDefaultCoverBitmap);
			mLayoutInflater = LayoutInflater.from(activity);
			mResultBooks = pResultBooks;
		}

		FastBitmapDrawable getDefaultCover() {
			return mDefaultCover;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			BookViewHolder holder;
			if (convertView == null) {
				convertView = mLayoutInflater.inflate(R.layout.shelf_book,
						parent, false);

				holder = new BookViewHolder();
				holder.title = (TextView) convertView.findViewById(R.id.title);
				holder.title.setSelected(true);
				holder.ivBookCover = (ImageView) convertView
						.findViewById(R.id.ivBookCover);
				convertView.setTag(holder);

			} else {
				holder = (BookViewHolder) convertView.getTag();
			}

			final ResultBook book = (ResultBook) getItem(position);

			holder.title.setText(book.title);

			final boolean hasCover = book.cover != null;
			holder.ivBookCover.setBackgroundDrawable(hasCover ? book.cover
					: mDefaultCover);

			return convertView;
		}

		@Override
		public int getCount() {
			return mResultBooks.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return mResultBooks.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}
	}

	private static class ResultBook {
		final BooksStore.Book book;
		final String text;
		final String title;
		final String authors;
		final FastBitmapDrawable cover;
		final Bitmap coverBitMap;

		ResultBook(BooksStore.Book book) {
			this.book = book;
			coverBitMap = book.loadCover(BooksStore.ImageSize.TINY);
			if (coverBitMap != null) {
				cover = new FastBitmapDrawable(coverBitMap);
			} else {
				cover = null;
			}
			title = book.getTitle();
			authors = TextUtilities.join(book.getAuthors(), ", ");
			text = title + ' ' + authors;
		}

		@Override
		public String toString() {
			return text;
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnHome:
			finish();
			break;
		case R.id.btnLogOut:
			logOut();
			break;
		default:
			break;
		}
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
			rlTop.setBackgroundResource(R.drawable.bar_lp);
			btnLogout
					.setBackgroundResource(R.drawable.background_button_homepage);
			btnHome.setBackgroundResource(R.drawable.background_button_homepage);
			llBackground.setBackgroundResource(R.drawable.st_bkg_lp);
			mGrid.setNumColumns(7);
			mGrid.setPadding(115, 0, 80, 0);
		} else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
			rlTop.setBackgroundResource(R.drawable.read_bar_pt);
			btnLogout
					.setBackgroundResource(R.drawable.background_button_homepage_portrait);
			btnHome.setBackgroundResource(R.drawable.background_button_homepage_portrait);
			llBackground.setBackgroundResource(R.drawable.st_bkg_pt);
			mGrid.setNumColumns(4);
			mGrid.setPadding(95, 0, 60, 0);
		}
	}

	private void onView(String bookId, String localPath) {
		Log.d(bookId,"ONVIEW TO EREADER ACTIVITY BOOKID");
		Log.d(localPath,"ONVIEW TO EREADER ACTIVITY LOCALPATH");
		EReaderActivity.show(this, bookId, localPath);
	}

	private void logOut() {
		Intent _intent = new Intent(BookStoreActivity.this, StartActivity.class);
		_intent.putExtra(LOG_OUT, true);
		_intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
				| Intent.FLAG_ACTIVITY_SINGLE_TOP);
		BookStoreActivity.this.startActivity(_intent);
		finish();
	}

}
