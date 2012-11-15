package com.wanhu.android.shelves.business;

import android.content.Context;
import android.widget.Toast;

import com.wanhu.android.shelves.model.ModelBookmark;
import com.yangyang.foxitsdk.view.PDFView;

public class PDFBusiness {

	private Context mContext;
	private BusinessBookmark mBusinessBookmark;
	private PDFView mPDFViewCtrl;

	/*
	 * public static interface WordsSearchListener { void
	 * onWordsFound(TextSearchResult pTextSearchResult); }
	 */

	public PDFBusiness(Context pContext, PDFView pPDFViewCtrl) {
		mContext = pContext;
		mBusinessBookmark = new BusinessBookmark(pContext);
		mPDFViewCtrl = pPDFViewCtrl;
	}

	/*
	 * public void onSearch(String pPattern, final WordsSearchListener
	 * pWordsSearchListener) { try { PDFDoc doc = mPDFViewCtrl.getDoc();
	 * doc.initSecurityHandler();
	 * 
	 * TextSearch txt_search = new TextSearch(); int mode =
	 * TextSearch.e_whole_word | TextSearch.e_page_stop | TextSearch.e_highlight
	 * | TextSearch.e_ambient_string;
	 * 
	 * // call Begin() method to initialize the text search.
	 * txt_search.begin(doc, pPattern, mode, -1, -1);
	 * 
	 * // call Run() method iteratively to find all matching instances. while
	 * (true) { TextSearchResult result = txt_search.run();
	 * 
	 * if (result.getCode() == TextSearchResult.e_found) {
	 * 
	 * pWordsSearchListener.onWordsFound(result); } else if (result.getCode() ==
	 * TextSearchResult.e_page) { // you can update your UI here, if needed }
	 * else { break; }
	 * 
	 * }
	 * 
	 * } catch (PDFNetException e) { System.out.println(e); } }
	 */

	public void onRemoveBookMark(ModelBookmark pModelBookmark) {
		if (mBusinessBookmark.deleteBookmarkByBookIDAndPageNumber(
				pModelBookmark.getBookID(), pModelBookmark.getPageNumber())) {
			Toast.makeText(
					mContext,
					com.wanhu.android.shelves.R.string.remove_bookmark_successful,
					Toast.LENGTH_SHORT).show();
		}

	}

	public void addBookMark(ModelBookmark pModelBookmark) {

		if (mBusinessBookmark.isExistBookMarkByBookIDAndPageNumber(
				pModelBookmark.getBookID(), pModelBookmark.getPageNumber())) {

			Toast.makeText(mContext,
					com.wanhu.android.shelves.R.string.have_added_bookmark,
					Toast.LENGTH_SHORT).show();
		} else {

			if (mBusinessBookmark.insertBookmark(pModelBookmark)) {
				Toast.makeText(
						mContext,
						com.wanhu.android.shelves.R.string.add_bookmark_successful,
						Toast.LENGTH_SHORT).show();
			}

		}

	}

	/*
	 * public void onSave() { PDFDoc doc = mPDFViewCtrl.getDoc(); if (doc !=
	 * null) { try { doc.lock(); // note: document needs to be locked first //
	 * before it can be saved. if (doc.isModified()) { String file_name =
	 * doc.getFileName(); if (file_name.length() == 0) { } else { File file =
	 * new File(file_name); boolean exist = file.exists(); if (!exist ||
	 * file.canWrite()) { doc.save(file_name, 0, null); } else { } } } } catch
	 * (Exception e) { Log.v("PDFNet", e.toString()); } finally { try {
	 * doc.unlock(); // note: unlock the document is necessary. } catch
	 * (Exception e) { } } } }
	 */

	public void changePageMode() {

		/*
		 * mPDFViewCtrl
		 * .setPagePresentationMode(mPDFViewCtrl.getPagePresentationMode() ==
		 * PDFViewCtrl.PAGE_PRESENTATION_SINGLE ?
		 * PDFViewCtrl.PAGE_PRESENTATION_FACING :
		 * PDFViewCtrl.PAGE_PRESENTATION_SINGLE);
		 */
	}
}
