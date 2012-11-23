package com.wanhu.android.shelves.business;

import java.util.List;

import android.content.Context;

import com.wanhu.android.shelves.business.base.BusinessBase;
import com.wanhu.android.shelves.database.sqlitedal.SQLiteDALBookmark;
import com.wanhu.android.shelves.model.ModelBookmark;

public class BusinessBookmark extends BusinessBase {

	private SQLiteDALBookmark mSqLiteDALBookmark;

	public BusinessBookmark(Context pContext) {
		super(pContext);
		mSqLiteDALBookmark = new SQLiteDALBookmark(pContext);
	}

	public boolean insertBookmark(ModelBookmark pInfo) {
		boolean _result = mSqLiteDALBookmark.insertBookmark(pInfo);
		return _result;
	}

	public boolean deleteBookmarksByBookID(String pBookID) {
		String _condition = " And BookID = '" + pBookID + "'";
		boolean _result = mSqLiteDALBookmark.deleteBookmark(_condition);

		return _result;
	}

	public boolean deleteBookmarkByBookIDAndPageNumber(String pBookID,
			int pPageNumber) {
		String _condition = " And BookID = '" + pBookID
				+ "' And PageNumber = '" + pPageNumber + "'";
		boolean _result = mSqLiteDALBookmark.deleteBookmark(_condition);

		return _result;
	}

	public boolean updateUserByBookID(ModelBookmark pInfo) {
		String _condition = " BookID = '" + pInfo.getBookID() + "'";
		boolean _result = mSqLiteDALBookmark.updateBookmark(_condition, pInfo);

		return _result;
	}

	private List<ModelBookmark> getBookmark(String pCondition) {
		return mSqLiteDALBookmark.getBookmark(pCondition);
	}

	public List<ModelBookmark> getBookmarksByBookID(String pBookID) {
		return getBookmark(" And BookID = '" + pBookID + "'");

	}

	public boolean isExistBookMarkByBookIDAndPageNumber(String pBookID,
			int pPageNumber) {
		String _condition = " And BookID = '" + pBookID
				+ "' And PageNumber = '" + pPageNumber + "'";
		List _list = getBookmark(_condition);
		if (_list.size() > 0) {
			return true;
		} else {
			return false;
		}
	}
}
