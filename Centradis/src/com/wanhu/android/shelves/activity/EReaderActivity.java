package com.wanhu.android.shelves.activity;

import java.io.File;

import FoxitEMBSDK.EMBJavaSupport;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.wanhu.android.shelves.R;
import com.wanhu.android.shelves.business.BusinessNote;
import com.wanhu.android.shelves.business.PDFBusiness;
import com.wanhu.android.shelves.controls.BookmarkPopupWindow;
import com.wanhu.android.shelves.controls.BookmarkPopupWindow.OnBookMarkListener;
import com.wanhu.android.shelves.controls.GotoPopupWindow;
import com.wanhu.android.shelves.controls.GotoPopupWindow.OnGotoListener;
import com.wanhu.android.shelves.controls.NotePoupWindow;
import com.wanhu.android.shelves.controls.NotePoupWindow.OnNoteListener;
import com.wanhu.android.shelves.controls.ShowBookMarkPopUpWindow;
import com.wanhu.android.shelves.controls.ShowBookMarkPopUpWindow.OnBookMarkClickListener;
import com.wanhu.android.shelves.controls.ShowNotesPopupWindow;
import com.wanhu.android.shelves.model.ModelBookmark;
import com.wanhu.android.shelves.provider.BooksStore;
import com.wanhu.android.shelves.provider.CentradisBooksStore;
import com.wanhu.android.shelves.util.FileUtilities;
import com.yangyang.foxitsdk.service.YYPDFDoc;
import com.yangyang.foxitsdk.service.YYPDFDoc.Mode;
import com.yangyang.foxitsdk.view.IPDFView;
import com.yangyang.foxitsdk.view.PDFView;

public class EReaderActivity extends Activity implements
		OnBookMarkClickListener, OnGotoListener, OnBookMarkListener,
		OnNoteListener, IPDFView {

	private Handler mHandler = new Handler();
	private String mFileName = null;
	private static final String EXTRA_BOOK = "shelves.extra.book_id";
	private OnClickListener mOnClickListener;
	private PDFBusiness mPdfBusiness;
	public static final String LOG_OUT = "logout";
	private boolean mIsClosed = true;
	// private SearchTask mSearchTask;
	private String mBookId;
	private BusinessNote mBusinessNote;
	private ViewerPreferences mViewerPreferences;
	private Uri mUri;
	private YYPDFDoc mDoc;

	public static PDFView mPDFView; // derived from anroid.view.ViewGroup
	private RelativeLayout rlMenu;
	private Button btnMenu;
	private Button btnLibrary;
	private Button btnBookMark;
	private Button btnIndex;
	private Button btnGoto;
	private Button btnHomepage;
	private Button btnStore;
	private Button btnOpen;
	private Button btnNotes;
	private Button btnPageMode;
	private Button btnSave;
	private Button btnSearch;
	private RelativeLayout rlTop;
	private RelativeLayout rlBackground;

	private static final int DIALOG_SAVE_AS = 1;

	@Override
	protected void onNewIntent(Intent intent) {
		mUri = intent.getData();
		mFileName = mUri.getPath();
		if (mUri != null) {
			mViewerPreferences = new ViewerPreferences(this);
			mViewerPreferences.addRecent(mUri);
		}

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent _intent = getIntent();
		if (_intent != null) {
			mBookId = _intent.getStringExtra(EXTRA_BOOK);
			String _localPath = _intent.getStringExtra("localPath");
			if (_localPath != null) {
				mFileName = _localPath;
				Log.d(mFileName, "FILENAME LOCALHOST");
			} else if (mBookId != null) {
				mFileName = FileUtilities.getBooksCacheDirectory() + "/"
						+ mBookId;
				Log.d(mFileName, "FILENAME BOOKID");

			} else {
				mUri = _intent.getData();
				mFileName = mUri.getPath();

				Log.d(mFileName, "FILENAME ELSE");

				boolean _shouldAdd = _intent
						.getBooleanExtra("shouldAdd", false);
				if (_shouldAdd) {
					ContentResolver _resolver = getContentResolver();

					final ContentValues values = new ContentValues();

					values.put(BooksStore.Book.EAN, mFileName);

					int a = mFileName.indexOf(".pdf");
					int b = mFileName.lastIndexOf("/");
					String _title = mFileName.substring(b + 1, a);

					values.put(BooksStore.Book.TITLE, _title);
					values.put(BooksStore.Book.INTERNAL_ID, "local" + _title);
					values.put(BooksStore.Book.ISBN,
							CentradisBooksStore.VALUE_USER_ID);

					_resolver.insert(BooksStore.Book.CONTENT_URI, values);
				}
			}

		}

		/*
		 * initialize PDFNet
		 */
		try {
			// PDFNet.initialize(this); // no license key, will produce
			// water-marks
			/*
			 * Log.v("PDFNet", "Version: " + PDFNet.getVersion()); //check the
			 * version number PDFNet.initialize(this, "your license key");
			 * //full version PDFNet.setColorManagement(PDFNet.e_lcms); //sets
			 * color management (more accurate, but more expensive)
			 */
			Log.d(this.toString(), "INIT PDFNET");

		} catch (Exception e) {
			return;
		}

		setContentView(R.layout.screen_ereader);

		initVariables();
		setupViews();
		setupListeners();

	}

	private void initVariables() {

		mOnClickListener = new ClickListener();
		// mSearchAdapter = new AdapterSearchResults(this);
		mBusinessNote = new BusinessNote(this);

		if (mUri != null) {
			mViewerPreferences = new ViewerPreferences(this);
			mViewerPreferences.addRecent(mUri);
		}

	}

	private void setupListeners() {
		btnMenu.setOnClickListener(mOnClickListener);
		btnLibrary.setOnClickListener(mOnClickListener);

		btnSearch.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				onSearch(event);

				return false;
			}
		});

		btnIndex.setOnClickListener(mOnClickListener);
		btnGoto.setOnClickListener(mOnClickListener);
		btnBookMark.setOnClickListener(mOnClickListener);
		btnHomepage.setOnClickListener(mOnClickListener);
		btnStore.setOnClickListener(mOnClickListener);
		btnOpen.setOnClickListener(mOnClickListener);
		btnNotes.setOnClickListener(mOnClickListener);
		btnPageMode.setOnClickListener(mOnClickListener);
		btnSave.setOnClickListener(mOnClickListener);
	}

	private void setupViews() {

		rlBackground = (RelativeLayout) findViewById(R.id.rlBackground);
		rlTop = (RelativeLayout) findViewById(R.id.rlTop);
		btnMenu = (Button) findViewById(R.id.btnMenu);
		btnLibrary = (Button) findViewById(R.id.btnLibrary);

		mPDFView = (PDFView) findViewById(R.id.pdfViewCtrl);

		mPdfBusiness = new PDFBusiness(this, mPDFView);

		/*
		 * use the tool manager to add additional UI modules to PDFViewCtrl.
		 * PDFNet SDK ships with a Tools.jar that contains various modules,
		 * including annotation editing, text search, text selection, and etc.
		 * if you are looking for a bare bone viewer with only basic viewing
		 * features, such as scrolling and zooming, simply comment out the
		 * following two lines.
		 */

		// pdftron.PDF.Tools.ToolManager tm = new
		// pdftron.PDF.Tools.ToolManager();
		// mPDFView.setToolManager(tm);

		/*
		 * misc PDFViewCtrl settings
		 */
		// mPDFView.setPagePresentationMode(PDFViewCtrl.PAGE_PRESENTATION_SINGLE);

		// mPDFView.setUseThumbView(true, false); //use the thumbs from the
		// input PDF file (more efficient)
		// mPDFView.setProgressiveRendering(false); // turn off progressive
		// rendering
		// mPDFView.setImageSmoothing(true); //turn on image smoothing (better
		// quality, but more expensive);
		// mPDFView.setHighlightFields(true); // turn on form fields
		// highlighting.
		// mPDFView.setCaching(false); // turn on caching (consume more memory,
		// but faster);
		// mPDFView.setOverprint(PDFViewCtrl.OVERPRINT_PDFX); //turn on
		// overprint for PDF/X files (more accurate, but more expensive);

		/*
		 * if you want to set the background of PDFViewCtrl to a Drawable, you
		 * can first set its background to be transparent and then set the
		 * drawable.
		 */
		// mPDFView.setClientBackgroundColor(255, 255, 255, true);
		// Drawable draw = ...
		// mPDFView.setBackgroundDrawable(draw);

		/*
		 * set zoom limits
		 */
		// mPDFView.setZoomLimits(PDFViewCtrl.ZOOM_LIMIT_RELATIVE, 1.0, 4);

		/*
		 * load a PDF file.
		 */
		mDoc = null;
		try {
			if (mFileName != null && !mFileName.equals("")) {
				mDoc = new YYPDFDoc(mFileName, "", this, 7);
				Log.d(mFileName, "PDF DOC OPEN");

			}
		} catch (Exception e) {
			mDoc = null;
		}

		Display display = getWindowManager().getDefaultDisplay();
		mPDFView.InitView(mDoc, (int) mDoc.GetPageSizeX(0),
				(int) mDoc.GetPageSizeY(0), display.getWidth(),
				display.getHeight());
		mPDFView.showCurrentPage();
		// mPDFView.setd(mDoc);

		Log.d(mDoc.toString(), "PDF DOC SET");

		/*
		 * Note: after setting the doc to PDFViewCtrl, it will be constantly
		 * accessed by PDFViewCtrl for rendering. However, simultaneous
		 * accessing to a PDFDoc is not allowed. So, if the same PDFDoc is to be
		 * accessed from other places, it is necessary to call PDFDoc.lock(),
		 * and this could wait until the current rendering task
		 */

		rlMenu = (RelativeLayout) findViewById(R.id.rlMenu);
		btnIndex = (Button) findViewById(R.id.btnIndex);
		btnGoto = (Button) findViewById(R.id.btnGoto);
		btnBookMark = (Button) findViewById(R.id.btnBookmark);
		btnHomepage = (Button) findViewById(R.id.btnHomepage);
		btnStore = (Button) findViewById(R.id.btnStore);
		btnOpen = (Button) findViewById(R.id.btnOpen);
		btnNotes = (Button) findViewById(R.id.btnNotes);
		btnPageMode = (Button) findViewById(R.id.btnPageMode);
		btnSave = (Button) findViewById(R.id.btnSave);
		btnSearch = (Button) findViewById(R.id.btnSearch);

	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		if (this.mPDFView != null)
			return this.mPDFView.onTouchEvent(event);
		return super.onTouchEvent(event);
	}

	@Override
	protected void onPause() {
		super.onPause();
		if (mPDFView != null) {
			mPDFView.pause();
		}
		Log.d("EREADER", "ONPAUSE");
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (mPDFView != null) {
			mPDFView.resume();
		}
		Log.d("EREADER", "ONRESUME");
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

		System.gc();
		if (mPDFView != null) {
			// mPDFView.purgeMemory();
			// mPDFView.destroy();
			// PDFNet.terminate(); COMMENTED BECAUSE CREATE A GC_CONCURRENT
			// CRASH ON PDF REOPENING
		}
		Log.d("EREADER", "ONDESTROY");
	}

	@Override
	public void onLowMemory() {
		super.onLowMemory();
		if (mPDFView != null) {
			// mPDFView.purgeMemory();
		}
		Log.d("EREADER", "ONLOWMEMORY");
	}

	static void show(Context context, String bookId, String localPath) {
		final Intent intent = new Intent(context, EReaderActivity.class);
		intent.putExtra(EXTRA_BOOK, bookId);
		intent.putExtra("localPath", localPath);
		Log.d("EREADER", "SHOW");
		context.startActivity(intent);
	}

	private void showMenu(View menu) {
		/*
		 * menu.startAnimation(AnimationUtils.loadAnimation(this,
		 * R.anim.slide_out_top));
		 */
		menu.setVisibility(View.VISIBLE);
		Log.d("EREADER", "SHOW MENU");
	}

	private void hiddenMenu(View menu) {
		/*
		 * menu.startAnimation(AnimationUtils.loadAnimation(this,
		 * R.anim.slide_in_top));
		 */
		menu.setVisibility(View.GONE);
		Log.d("EREADER", "HIDE MENU");
	}

	class ClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {

			Log.d("EREADER", "ONCLICK LISTENER " + v.getId());

			switch (v.getId()) {
			case R.id.btnMenu:
				toggle();
				break;
			case R.id.btnLibrary:
				// finish();
				// if (mPDFView != null) {
				// mPDFView.pause();
				// }
				finish();
				// EReaderActivity.this.finish();
				break;
			case R.id.btnIndex:
				mPDFView.gotoPage(1);
				break;
			case R.id.btnGoto:
				new GotoPopupWindow(v, EReaderActivity.this)
						.showLikePopDownMenu(0, 0);
				break;
			case R.id.btnBookmark:
				new BookmarkPopupWindow(v, EReaderActivity.this)
						.showLikePopDownMenu(0, 0);
				break;
			case R.id.btnHomepage:
				EReaderActivity.this.finish();
				break;
			case R.id.btnStore:
				onBookStore();
				break;
			case R.id.btnOpen:
				mHandler.postDelayed(new Runnable() {

					@Override
					public void run() {
						onOpenLocalPDF();
					}
				}, 1000);
				break;
			case R.id.btnNotes:
				new NotePoupWindow(v, EReaderActivity.this)
						.showLikePopDownMenu(0, 0);

				break;
			case R.id.btnPageMode:
				// mPDFView.changeMode(Mode.Form);
				break;
			case R.id.btnSave:
				showDialog(DIALOG_SAVE_AS);
				break;
			default:
				break;
			}
		}

	}

	private void onOpenLocalPDF() {
		Log.d("EREADER", "OPEN LOCAL PDF");
		MainBrowserActivity.show(this, false);
		finish();
		Log.d("EREADER", "OPENLOCALPDF FINISH");
	}

	private void toggle() {
		Log.d("EREADER", "TOOGLE");
		if (mIsClosed) {
			mIsClosed = false;
			showMenu(rlMenu);
		} else {
			mIsClosed = true;
			hiddenMenu(rlMenu);
		}
	}

	@Override
	public void onBookMarkClick(int pPageNumber) {
		if (pPageNumber != -1) {
			mPDFView.gotoPage(pPageNumber);

		}
	}

	@Override
	public void onGoto(int pPageNumber) {
		mPDFView.gotoPage(pPageNumber);
	}

	@Override
	public void onAddBookMark() {
		final EditText _eEditText = new EditText(this);
		new AlertDialog.Builder(this)
				.setTitle(R.string.enter_bookmark_name)
				.setView(_eEditText)
				.setNegativeButton(R.string.ok,
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								String _title = _eEditText.getText().toString();
								if (TextUtils.isEmpty(_title)) {
									Toast.makeText(EReaderActivity.this,
											R.string.enter_bookmark_name,
											Toast.LENGTH_SHORT).show();
								} else {
									ModelBookmark _modelBookmark = new ModelBookmark();
									_modelBookmark.setBookID(mBookId);
									_modelBookmark.setName(_title);

									_modelBookmark.setPageNumber(mPDFView
											.getCurrentPage());
									mPdfBusiness.addBookMark(_modelBookmark);
								}
							}
						}).setPositiveButton(R.string.cancel, null).show();

	}

	@Override
	public void onShowBookMark() {
		ShowBookMarkPopUpWindow _bookMarkPopupWindow = new ShowBookMarkPopUpWindow(
				btnBookMark, mBookId, EReaderActivity.this);
		_bookMarkPopupWindow.showLikePopDownMenu(-516, 0);
	}

	@Override
	public void onRemoveBookMark() {
		ModelBookmark _modelBookmark = new ModelBookmark();
		_modelBookmark.setBookID(mBookId);
		_modelBookmark.setPageNumber(mPDFView.getCurrentPage());
		mPdfBusiness.onRemoveBookMark(_modelBookmark);
	}

	@Override
	public void onShowNote() {
		if (!TextUtils.isEmpty(mBookId)) {
			ShowNotesPopupWindow _notesPopupWindow = new ShowNotesPopupWindow(
					btnNotes, mBookId);
			Configuration newConfig = getResources().getConfiguration();
			if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
				_notesPopupWindow.showLikePopDownMenu(-582, -309);
			} else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
				_notesPopupWindow.showLikePopDownMenu(-182, 0);
			}

		}
	}

	@Override
	public void onRemoveNote() {
		if (!TextUtils.isEmpty(mBookId)) {
			mBusinessNote.deleteNoteByBookID(mBookId);
			Toast.makeText(this, R.string.remove_note_successfully,
					Toast.LENGTH_SHORT).show();
		}
	}

	private void onBookStore() {
		Log.d("EREADER", "ONBOOKSTORE");
		BookStoreActivity.show(this);
		finish();
		Log.d("EREADER", "ONBOOKSTORE FINISH");
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		Log.d("EREADER", "ONCONFIGURATION CHANGE");
		if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
			// rlTop.setBackgroundResource(R.drawable.bar_lp);
			// btnLibrary
			// .setBackgroundResource(R.drawable.background_button_homepage);
			// btnMenu.setBackgroundResource(R.drawable.background_button_homepage);
			// rlBackground.setBackgroundResource(R.drawable.read_bkg_lp);
			EReaderActivity.mPDFView
					.updateViewMode(Configuration.ORIENTATION_LANDSCAPE);
		} else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
			// rlTop.setBackgroundResource(R.drawable.read_bar_pt);
			// btnLibrary
			// .setBackgroundResource(R.drawable.background_button_homepage_portrait);
			// btnMenu.setBackgroundResource(R.drawable.background_button_homepage_portrait);
			// rlBackground.setBackgroundResource(R.drawable.read_bkg_pt);
			EReaderActivity.mPDFView
					.updateViewMode(Configuration.ORIENTATION_LANDSCAPE);
		}

	}

	private void onSave(String title, String fileName, boolean isOverride) {
		YYPDFDoc doc = mPDFView.getDoc();
		if (doc != null) {
			try {
				doc.lock(); // note: document needs to be locked first
							// before it can be saved.
				doc.save(fileName);

				if (!isOverride) {
					ContentResolver _resolver = getContentResolver();

					final ContentValues values = new ContentValues();

					values.put(BooksStore.Book.EAN, fileName);
					values.put(BooksStore.Book.TITLE, title);

					if (mBookId != null) {
						values.put(BooksStore.Book.INTERNAL_ID, "localSB"
								+ ShelvesActivity.book_id + "|" + title);
					} else {
						values.put(BooksStore.Book.INTERNAL_ID, "local" + title);
					}

					values.put(BooksStore.Book.ISBN,
							CentradisBooksStore.VALUE_USER_ID);
					_resolver.insert(BooksStore.Book.CONTENT_URI, values);
				}

				Toast.makeText(EReaderActivity.this, R.string.save_successful,
						Toast.LENGTH_LONG).show();
			} catch (Exception e) {
				Log.v("PDFNet", e.toString());
			} finally {
				try {
					// doc.unlock(); // note: unlock the document is necessary.
				} catch (Exception e) {
				}
			}
		}
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		Builder builder = new Builder(EReaderActivity.this);
		switch (id) {
		case DIALOG_SAVE_AS:
			final EditText _editText = new EditText(this);
			_editText.setSingleLine();
			builder.setTitle(R.string.ereader_saveas);
			builder.setIcon(android.R.drawable.ic_dialog_alert);
			builder.setMessage(R.string.dialog_saveas_message);
			builder.setPositiveButton(R.string.cancel_label, null);
			builder.setView(_editText);
			builder.setNegativeButton(R.string.save,
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							final String _title = _editText.getText()
									.toString();
							if (!TextUtils.isEmpty(_title)) {

								File booksCacheDirectory = new File(Environment
										.getExternalStorageDirectory(),
										"shelves/books");
								if (!booksCacheDirectory.exists()) {
									booksCacheDirectory.mkdirs();
								}

								File file = new File(booksCacheDirectory,
										_title + ".pdf");

								final String file_name = file.getPath();

								boolean exist = file.exists();
								if (exist) {

									Builder builder = new Builder(
											EReaderActivity.this);
									builder.setTitle(R.string.ereader_saveas);
									builder.setIcon(android.R.drawable.ic_dialog_alert);
									builder.setMessage(R.string.dialog_saveas_message);
									builder.setPositiveButton(
											R.string.cancel_label, null);
									builder.setNegativeButton(
											R.string.override,
											new DialogInterface.OnClickListener() {
												public void onClick(
														DialogInterface dialog,
														int which) {
													onSave(_title, file_name,
															true);
													dialog.dismiss();
													dismissDialog(DIALOG_SAVE_AS);
												}
											});
									builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
										public void onCancel(
												DialogInterface dialog) {
											dialog.dismiss();
										}
									});
									builder.setCancelable(true);
									builder.create().show();

								} else {
									onSave(_title, file_name, false);
									dialog.dismiss();
								}
							} else {
								Toast.makeText(EReaderActivity.this,
										R.string.dialog_saveas_message,
										Toast.LENGTH_LONG).show();
							}

						}
					});
			builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
				public void onCancel(DialogInterface dialog) {
					dialog.dismiss();
				}
			});
			builder.setCancelable(true);
			return builder.create();
		}
		return super.onCreateDialog(id);
	}

	private void onSearch(MotionEvent pMotionEvent) {
		pMotionEvent.setLocation(65, 25);
		mPDFView.dispatchTouchEvent(pMotionEvent);
	}

	@Override
	public void createAndroidTextField(String arg0) {
		// TODO Auto-generated method stub
		Intent intent = new Intent();
		Bundle bundle = new Bundle();
		bundle.putString("textValue", arg0);
		intent.setClass(this, textfieldActivity.class);
		intent.putExtra("key", bundle);
		this.startActivityForResult(intent, 0);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK && requestCode == 0) {
			Bundle bundle = data.getBundleExtra("Result");
			String text = bundle.getString("ResultValue");
			EMBJavaSupport.FPDFFormFillOnSetText(mDoc.getPDFFormHandler(),
					mDoc.getCurrentPageHandler(), text, 0);
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public int getCurrentPageHandler() {
		// TODO Auto-generated method stub
		return mDoc.getCurrentPageHandler();
	}

	@Override
	public int getPageHandler(int arg0) {
		// TODO Auto-generated method stub
		return mDoc.getPageHandler(arg0);
	}

	@Override
	public void invalidate(float arg0, float arg1, float arg2, float arg3) {
		// TODO Auto-generated method stub
		this.mPDFView.invalidate(arg0, arg1, arg2, arg3);
	}

}
