package com.wanhu.android.shelves.activity;

import java.io.File;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewStub;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wanhu.android.shelves.R;
import com.wanhu.android.shelves.ShelvesApplication;
import com.wanhu.android.shelves.business.UpdateBusiness;
import com.wanhu.android.shelves.drawable.FastBitmapDrawable;
import com.wanhu.android.shelves.provider.BookStoreFactory;
import com.wanhu.android.shelves.provider.BooksManager;
import com.wanhu.android.shelves.provider.BooksStore;
import com.wanhu.android.shelves.provider.CentradisBooksStore;
import com.wanhu.android.shelves.util.IOUtilities;
import com.wanhu.android.shelves.util.IOUtilities.OnPublishProgress;
import com.wanhu.android.shelves.util.FileUtilities;
import com.wanhu.android.shelves.util.ImageUtilities;
import com.wanhu.android.shelves.util.Preferences;
import com.wanhu.android.shelves.util.UIUtilities;
import com.wanhu.android.shelves.view.ShelvesView;

public class ShelvesActivity extends Activity {
	
	public static String book_id;
	private static final String LOG_TAG = "Shelves";

	private static final String ACTION_UPDATE = "shelves.intent.action.ACTION_UPDATE";

	private static final String STATE_PREPARE_UPDATE_IN_PROGRESS = "shelves.prepareupdate.inprogress";
	private static final String STATE_PREPARE_UPDATE_QUERY = "shelves.prepareupdate.book";

	private static final String STATE_UPDATE_IN_PROGRESS = "shelves.update.inprogress";
	private static final String STATE_UPDATE_BOOKS = "shelves.update.books";
	private static final String STATE_UPDATE_INDEX = "shelves.update.index";
	private static final String STATE_BOOK_TO_Update = "shelves.update.bookToUpdate";

	private PrepareUpdateTask mPrepareUpdateTask;
	private UpdateTask mUpdateTask;
	private DeleteTask mDeleteTask;

	private FastBitmapDrawable mDefaultCover;

	private ProgressBar mUpdateProgress;
	private View mUpdatePanel;
	private View mDeletePanel;
	private ShelvesView mGrid;
	private BookViewHolder mBookToUpdate;

	private Bundle mSavedState;
	private ClickListener mClickListener;
	private UpdateBusiness mUpdateBusiness;

	private Button btnStore;
	private Button btnLogout;
	private Button btnOpen;
	private RelativeLayout rlBackground;
	private RelativeLayout rlTop;
	private View mUpdateView;

	private static final int DIALOG_UPDATE = 1;
	private static final String BOOK_TITLE = "title";
	private static final String BOOK_ID = "id";

	public static final String LOG_OUT = "logout";

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		setContentView(R.layout.screen_shelves);
		getWindow().setBackgroundDrawable(null);

		initVariables();
		setupViews();
		setupListeners();
		// handleSearchQuery(getIntent());

	}

	FastBitmapDrawable getDefaultCover() {
		return mDefaultCover;
	}

	private void handleSearchQuery(Intent queryIntent) {
		final String queryAction = queryIntent.getAction();
		if (Intent.ACTION_SEARCH.equals(queryAction)) {
			onSearch(queryIntent);
		} else if (Intent.ACTION_VIEW.equals(queryAction)) {
			final Intent viewIntent = new Intent(Intent.ACTION_VIEW,
					queryIntent.getData());
			startActivity(viewIntent);
		}
	}

	private void onSearch(Intent intent) {
		final String queryString = intent.getStringExtra(SearchManager.QUERY);
		mGrid.setFilterText(queryString);
	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);

		final String action = intent.getAction();
		if (Intent.ACTION_SEARCH.equals(action)) {
			onSearch(intent);
		} else if (Intent.ACTION_VIEW.equals(action)) {
			final Intent viewIntent = new Intent(Intent.ACTION_VIEW,
					intent.getData());
			startActivity(viewIntent);
		}
	}

	private void initVariables() {
		mClickListener = new ClickListener();
		mUpdateBusiness = new UpdateBusiness(this);
	}

	private void setupViews() {

		if (CentradisBooksStore.VALUE_USER_ID == null) {
			SharedPreferences mPreferences = getSharedPreferences(
					Preferences.NAME, 0);
			CentradisBooksStore.VALUE_USER_ID = mPreferences.getString(
					Preferences.KEY_USER_ID, "");
		}

		final BooksAdapter adapter = new BooksAdapter(this,
				CentradisBooksStore.VALUE_USER_ID);
		mDefaultCover = adapter.getDefaultCover();

		mGrid = (ShelvesView) findViewById(R.id.grid_shelves);

		final ShelvesView grid = mGrid;
		grid.setTextFilterEnabled(true);
		grid.setAdapter(adapter);
		grid.setOnItemClickListener(new BookViewer());

		registerForContextMenu(grid);

		btnStore = (Button) findViewById(R.id.btnStore);
		btnLogout = (Button) findViewById(R.id.btnLogOut);
		btnOpen = (Button) findViewById(R.id.btnOpen);
		rlBackground = (RelativeLayout) findViewById(R.id.rlBackground);
		rlTop = (RelativeLayout) findViewById(R.id.rlTop);

	}

	private void setupListeners() {
		btnStore.setOnClickListener(mClickListener);
		btnLogout.setOnClickListener(mClickListener);
		btnOpen.setOnClickListener(mClickListener);
	}

	private class ClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.btnStore:
				if (isNetworkAvaiable()) {
					onBookStore();
				} else {
					Toast.makeText(ShelvesActivity.this,
							R.string.network_not_available, Toast.LENGTH_LONG)
							.show();
				}
				break;
			case R.id.btnLogOut:
				logOut();
				break;
			case R.id.btnOpen:
				onOpenLocalPDF();
				break;
			default:
				break;
			}
		}

	}

	private void logOut() {
		Intent _intent = new Intent(ShelvesActivity.this, StartActivity.class);
		_intent.putExtra(LOG_OUT, true);
		_intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
				| Intent.FLAG_ACTIVITY_SINGLE_TOP);
		ShelvesActivity.this.startActivity(_intent);
		finish();
	}

	public void onOpenLocalPDF() {
		MainBrowserActivity.show(this, true);
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (isNetworkAvaiable()) {
			onPrepareUpdate();
		}
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onStop() {
		super.onStop();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		onCancelPrepareUpdate();
		onCancelUpdate();
		onCancelDelete();
		ImageUtilities.cleanupCache();
	}

	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			Intent _intent = new Intent(ShelvesActivity.this,
					StartActivity.class);
			_intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
					| Intent.FLAG_ACTIVITY_SINGLE_TOP);
			ShelvesActivity.this.startActivity(_intent);
			finish();
		}
		return true;
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenu.ContextMenuInfo menuInfo) {

		super.onCreateContextMenu(menu, v, menuInfo);

		AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
		BookViewHolder holder = (BookViewHolder) ((info.targetView).getTag());
		menu.setHeaderTitle((holder.title).getText());
		
		getMenuInflater().inflate(R.menu.book, menu);
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		final AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item
				.getMenuInfo();
		final BookViewHolder holder = (BookViewHolder) info.targetView.getTag();

		switch (item.getItemId()) {

		/*
		 * case R.id.context_menu_item_view: onView(holder.bookId); return true;
		 * case R.id.context_menu_item_buy:
		 * onBuy(BooksManager.findBook(getContentResolver(), holder.bookId));
		 * return true;
		 */

		case R.id.context_menu_item_delete:
			onDelete(holder);
			return true;
			/*
		case R.id.context_menu_item_email:
			onSendEmail(holder);
			return true;
			*/
		}

		return super.onContextItemSelected(item);
	}
/*
	private void onSendEmail(BookViewHolder pBookViewHolder) {

		Intent _intent = new Intent();
		_intent.setAction(Intent.ACTION_SEND);
		_intent.setType("application/octet-stream");
		if (pBookViewHolder.localPath != null) {
			_intent.putExtra(Intent.EXTRA_STREAM,
					Uri.fromFile(new File(pBookViewHolder.localPath)));
		} else {
			_intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(
					FileUtilities.getBooksCacheDirectory(),
					pBookViewHolder.title.getText().toString() + ".pdf")));
		}

		startActivity(_intent);
	}
*/
	private void onView(String bookId, String localPath) {
		
		Log.d(bookId,"SHELVE EREADER ACTIVITY BOOKID");
		
		Log.d(localPath,"SHELVE EREADER ACTIVITY LOCALPATH");
		this.finish();
		EReaderActivity.show(this, bookId, localPath);
	}

	private void onBookStore() {
		BookStoreActivity.show(this);
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

	private class BookViewer implements AdapterView.OnItemClickListener {
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			BookViewHolder _bookViewHolder = (BookViewHolder) view.getTag();
			
			book_id = _bookViewHolder.bookId;
			
			onView(_bookViewHolder.title.getText().toString() + ".pdf", _bookViewHolder.localPath);
			
			//onView(book_id, _bookViewHolder.localPath);
		}
	}

	static void show(Context context) {
		final Intent intent = new Intent(context, ShelvesActivity.class);
		context.startActivity(intent);
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
			rlTop.setBackgroundResource(R.drawable.bar_lp);
			btnLogout
					.setBackgroundResource(R.drawable.background_button_homepage);
			btnStore.setBackgroundResource(R.drawable.background_button_homepage);
			rlBackground
					.setBackgroundResource(R.drawable.hp_kg_no_shelves_lp_logo);
			mGrid.setNumColumns(7);
			mGrid.setPadding(115, 0, 80, 0);
		} else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
			rlTop.setBackgroundResource(R.drawable.read_bar_pt);
			btnLogout
					.setBackgroundResource(R.drawable.background_button_homepage_portrait);
			btnStore.setBackgroundResource(R.drawable.background_button_homepage_portrait);
			rlBackground.setBackgroundResource(R.drawable.hp_bkg_no_shelves_pt);
			mGrid.setNumColumns(4);
			mGrid.setPadding(95, 0, 60, 0);
		}
		onPrepareUpdate();
	}

	private void onPrepareUpdate() {
		if (mPrepareUpdateTask == null
				|| mPrepareUpdateTask.getStatus() == PrepareUpdateTask.Status.FINISHED) {
			mPrepareUpdateTask = (PrepareUpdateTask) new PrepareUpdateTask()
					.execute(CentradisBooksStore.VALUE_USER_ID);
		} else {
			UIUtilities.showToast(this, R.string.error_search_in_progress);
		}
	}

	private void onCancelPrepareUpdate() {
		if (mPrepareUpdateTask != null
				&& mPrepareUpdateTask.getStatus() == PrepareUpdateTask.Status.RUNNING) {
			mPrepareUpdateTask.cancel(true);
			mPrepareUpdateTask = null;
		}
	}

	private class PrepareUpdateTask extends
			AsyncTask<String, Void, List<Integer>> {

		private final Object mLock = new Object();
		private String mQuery;

		String getQuery() {
			synchronized (mLock) {
				return mQuery;
			}
		}

		@Override
		protected List<Integer> doInBackground(String... params) {
			synchronized (mLock) {
				mQuery = params[0];
			}
			return mUpdateBusiness.onPrepareUpdate(params[0]);
		}

		@Override
		protected void onPostExecute(List<Integer> result) {
			updateBookCovers(result);
		}
	}

	private void updateBookCovers(List<Integer> result) {
		final ShelvesView grid = mGrid;
		final int count = grid.getChildCount();
		if (result != null && result.size() > 0) {

			for (Integer _result : result) {
				
				for (int i = 0; i < count; i++) {
					final View view = grid.getChildAt(i);
					final BookViewHolder holder = (BookViewHolder) view
							.getTag();
					final String bookId = holder.bookId;
					
					if (String.valueOf(_result).equals(bookId)) {
						
						holder.ivUpdateLogo
								.setBackgroundResource(R.drawable.book_update_4);
						holder.ivUpdateLogo
								.setOnClickListener(new OnClickListener() {
									

									@Override
									public void onClick(View v) {
										mUpdateView = v;
										Bundle _bundle = new Bundle();
										_bundle.putString(BOOK_TITLE,
												holder.title.getText()
														.toString());
										
										_bundle.putString(BOOK_ID, bookId);
										showDialog(DIALOG_UPDATE, _bundle);
									}
								});
						holder.needUpdate = true;
					}
				}
			}

		} else {

			for (int i = 0; i < count; i++) {
				final View view = grid.getChildAt(i);
				final BookViewHolder holder = (BookViewHolder) view.getTag();
				holder.ivUpdateLogo.setBackgroundDrawable(null);
				holder.needUpdate = false;
			}
		}
		grid.invalidate();

	}

	@Override
	protected Dialog onCreateDialog(int id, Bundle args) {
		switch (id) {
		case DIALOG_UPDATE:
			final String _id = args.getString(BOOK_ID);
			
			Builder builder = new Builder(ShelvesActivity.this);
			builder.setTitle(args.getString(BOOK_TITLE));
			builder.setIcon(android.R.drawable.ic_dialog_alert);
			builder.setMessage(R.string.dialog_update_message);
			builder.setPositiveButton(R.string.dialog_update_ok,
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							Log.d(_id,"UPDATE ID HERE");
							onUpdate(_id);
						}
					});
			builder.setNegativeButton(R.string.dialog_update_cancel, null);
			builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
				public void onCancel(DialogInterface dialog) {
					dialog.dismiss();
				}
			});
			builder.setCancelable(true);
			return builder.create();

		}
		return super.onCreateDialog(id, args);
	}

	private class UpdateTask extends AsyncTask<String, String, BooksStore.Book>
			implements OnPublishProgress {
		private FastBitmapDrawable mDefaultCover;
		private ProgressBar mProgressBar;

		@Override
		public void onPreExecute() {
			final Bitmap defaultCoverBitmap = BitmapFactory.decodeResource(
					getResources(), R.drawable.pdf_icon_2);
			mDefaultCover = new FastBitmapDrawable(defaultCoverBitmap);

			if (mUpdatePanel == null) {
				mUpdatePanel = ((ViewStub) findViewById(R.id.stub_update))
						.inflate();
			}
			mProgressBar = ((ProgressBar) mUpdatePanel
					.findViewById(R.id.progress));
			mProgressBar.setIndeterminate(false);

			((TextView) findViewById(R.id.label_import))
					.setText(R.string.update_label);

			final View cancelButton = mUpdatePanel
					.findViewById(R.id.button_cancel);
			cancelButton.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					onCancelUpdate();
				}
			});
			mProgressBar.setProgress(0);
			showPanel(mUpdatePanel, false);
		}

		public BooksStore.Book doInBackground(String... params) {
			IOUtilities.mOnPublishProgress = this;
			return BooksManager.updateBook(getContentResolver(), params[0],
					BookStoreFactory.get(ShelvesActivity.this));
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
			hidePanel(mUpdatePanel, false);
		}

		@Override
		public void onPostExecute(BooksStore.Book book) {
		
			if (book == null) {
				
				UIUtilities.showToast(ShelvesActivity.this,
						R.string.error_update_book);
				
			} else {
				
				final String bookname = book.getTitle();
				
				UIUtilities.showFormattedImageToast(ShelvesActivity.this,
						R.string.success_updated, ImageUtilities
								.getCachedCover(book.getInternalId(),
										mDefaultCover), book.getTitle());
				mUpdateView.setBackgroundDrawable(null);
				mGrid.invalidate();
				
			}
			
			IOUtilities.mOnPublishProgress = null;
			hidePanel(mUpdatePanel, false);
		}

	}

	private void onUpdate(String pBookId) {
		if (BooksManager.bookExists(getContentResolver(), pBookId)) {
			// Log.d(pBookId,"BOOK EXIST");
			mUpdateTask = (UpdateTask) new UpdateTask().execute(pBookId);
		}
	}

	private void onCancelUpdate() {
		if (mUpdateTask != null
				&& mUpdateTask.getStatus() == AsyncTask.Status.RUNNING) {
			mUpdateTask.cancel(true);
			mUpdateTask = null;
		}
	}

	private class DeleteTask extends AsyncTask<String, Void, Boolean> {
		private ProgressBar mProgressBar;

		@Override
		public void onPreExecute() {

			if (mDeletePanel == null) {
				mDeletePanel = ((ViewStub) findViewById(R.id.stub_delete))
						.inflate();
				mProgressBar = ((ProgressBar) mDeletePanel
						.findViewById(R.id.progress));
				mProgressBar.setIndeterminate(true);

				((TextView) findViewById(R.id.label_import))
						.setText(R.string.delete_label);

				final View cancelButton = mDeletePanel
						.findViewById(R.id.button_cancel);
				cancelButton.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						onCancelDelete();
					}
				});
			}

			showPanel(mDeletePanel, true);
		}

		public Boolean doInBackground(String... params) {
			return BooksManager.deleteBook(getContentResolver(), params[0],
					BookStoreFactory.get(ShelvesActivity.this));
		}

		@Override
		public void onCancelled() {
			hidePanel(mDeletePanel, true);
		}

		@Override
		public void onPostExecute(Boolean successful) {
			if (!successful) {
				UIUtilities.showToast(ShelvesActivity.this,
						R.string.error_deleting_book);
			} else {
				UIUtilities.showToast(ShelvesActivity.this,
						R.string.success_deleted);
			}
			hidePanel(mDeletePanel, true);
		}
	}

	private void onDelete(BookViewHolder holder) {
		if (BooksManager.bookExists(getContentResolver(), holder.bookId)) {
			if (holder.bookId.startsWith("local")) {
				if (BooksManager.deleteLocalBook(getContentResolver(), holder.bookId,holder.title.getText().toString() + ".pdf")) {
					UIUtilities.showToast(ShelvesActivity.this,
							R.string.success_deleted);
				} else {
					UIUtilities.showToast(ShelvesActivity.this,
							R.string.error_deleting_book);
				}

			} else {
				mDeleteTask = (DeleteTask) new DeleteTask().execute(holder.bookId);
			}

		}
	}

	private void onCancelDelete() {
		if (mDeleteTask != null
				&& mDeleteTask.getStatus() == AsyncTask.Status.RUNNING) {
			mDeleteTask.cancel(true);
			mDeleteTask = null;
		}
	}

	public boolean isNetworkAvaiable() {
		ConnectivityManager _connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		if (_connectivityManager != null) {
			NetworkInfo _networkInfo = _connectivityManager
					.getActiveNetworkInfo();
			if (_networkInfo != null && _networkInfo.isConnected()) {
				return true;
			}
		}
		return false;
	}

}
