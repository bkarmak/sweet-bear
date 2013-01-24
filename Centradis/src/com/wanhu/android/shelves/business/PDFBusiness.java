package com.wanhu.android.shelves.business;

import android.content.Context;
import android.widget.Toast;

import com.wanhu.android.shelves.R;
import com.wanhu.android.shelves.model.ModelBookmark;
import com.wanhu.android.shelves.model.ModelNote;
import com.yangyang.foxitsdk.service.PDFDoc;
import com.yangyang.foxitsdk.util.TextSearchResult;
import com.yangyang.foxitsdk.view.PDFView;

public class PDFBusiness {

	private Context context;
	private BusinessBookmark businessBookMark;
	private BusinessNote businessNote;
	private PDFView mPDFViewCtrl;

	public static interface WordsSearchListener {
		void onWordsFound(TextSearchResult pTextSearchResult);
	}

	public PDFBusiness(Context context, PDFView pPDFViewCtrl) {
		this.context = context;
		businessBookMark = new BusinessBookmark(context);
		businessNote = new BusinessNote(context);
		mPDFViewCtrl = pPDFViewCtrl;
	}

	public void onSearch(String pPattern) {
		// final WordsSearchListener pWordsSearchListener) {
		// try {
		// PDFDoc doc = mPDFViewCtrl.getDoc();
		// doc.SearchStart(pPattern);
		// // call Run() method iteratively to find all matching instances.
		// while (true) {
		// TextSearchResult result = txt_search.run();
		// if (result.getCode() == TextSearchResult.e_found) {
		// pWordsSearchListener.onWordsFound(result);
		// } else if (result.getCode() == TextSearchResult.e_page) {
		// } else {
		// break;
		// }
		//
		// }
		// } catch (Exception e) {
		// System.out.println(e);
		// }
	}

	public void onRemoveBookMark(ModelBookmark pModelBookmark) {
		if (businessBookMark.deleteBookmarkByBookIDAndPageNumber(
				pModelBookmark.getBookID(), pModelBookmark.getPageNumber())) {
			Toast.makeText(
					context,
					com.wanhu.android.shelves.R.string.remove_bookmark_successful,
					Toast.LENGTH_SHORT).show();
		}

	}

	public void addBookMark(ModelBookmark pModelBookmark) {

		if (businessBookMark.isExistBookMarkByBookIDAndPageNumber(
				pModelBookmark.getBookID(), pModelBookmark.getPageNumber())) {

			Toast.makeText(context,
					com.wanhu.android.shelves.R.string.have_added_bookmark,
					Toast.LENGTH_SHORT).show();
		} else {

			if (businessBookMark.insertBookmark(pModelBookmark)) {
				Toast.makeText(
						context,
						com.wanhu.android.shelves.R.string.add_bookmark_successful,
						Toast.LENGTH_SHORT).show();
			}

		}

	}

	public void addBookNote(ModelNote note) {
		if (businessNote.insertNote(note)) {
			Toast.makeText(context, R.string.add_note_successfully,
					Toast.LENGTH_SHORT).show();
		}
	}

	public void removeBookNote(String bookID, int noteID) {
		if (businessNote.deleteNode(bookID, noteID)) {
			Toast.makeText(context, R.string.remove_note_successfully,
					Toast.LENGTH_SHORT).show();
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

		// mPDFViewCtrl
		// .setPagePresentationMode(mPDFViewCtrl.getPagePresentationMode() ==
		// PDFViewCtrl.PAGE_PRESENTATION_SINGLE ?
		// PDFViewCtrl.PAGE_PRESENTATION_FACING
		// : PDFViewCtrl.PAGE_PRESENTATION_SINGLE);
	}
}
