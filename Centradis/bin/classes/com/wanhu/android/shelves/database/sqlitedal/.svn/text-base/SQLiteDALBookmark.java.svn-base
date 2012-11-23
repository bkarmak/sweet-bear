package com.wanhu.android.shelves.database.sqlitedal;

import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.wanhu.android.shelves.database.base.SQLiteDALBase;
import com.wanhu.android.shelves.model.ModelBookmark;

public class SQLiteDALBookmark extends SQLiteDALBase {

	public SQLiteDALBookmark(Context pContext) {
		super(pContext);
	}

	@Override
	public void onCreate(SQLiteDatabase pDataBase) {
		StringBuilder _sCreateTableScript = new StringBuilder();
		_sCreateTableScript.append("		Create TABLE Bookmark(");
		_sCreateTableScript.append("			   [BookMarkID] integer PRIMARY KEY AUTOINCREMENT NOT NULL");
		_sCreateTableScript.append("			  ,[Name] TEXT");
		_sCreateTableScript.append("			  ,[PageNumber] TEXT");
		_sCreateTableScript.append("			  ,[BookID] TEXT NOT NULL");
		_sCreateTableScript.append(" 			  )");
		pDataBase.execSQL(_sCreateTableScript.toString());
	}

	@Override
	public void onUpgrade(SQLiteDatabase pDataBase) {

	}

	@Override
	protected String[] getTableNameAndPK() {
		return new String[] { "Bookmark", "BookMarkID" };
	}

	@Override
	protected Object findModel(Cursor pCursor) {
		ModelBookmark _modelBookmark = new ModelBookmark();
		_modelBookmark.setBookmarkID(pCursor.getInt(pCursor
				.getColumnIndexOrThrow("BookMarkID")));
		_modelBookmark.setName(pCursor.getString(pCursor
				.getColumnIndexOrThrow("Name")));
		_modelBookmark.setPageNumber(pCursor.getInt(pCursor
				.getColumnIndexOrThrow("PageNumber")));
		_modelBookmark.setBookID(pCursor.getString(pCursor
				.getColumnIndexOrThrow("BookID")));

		return _modelBookmark;
	}

	public ContentValues createParms(ModelBookmark pInfo) {
		ContentValues contentValues = new ContentValues();
		contentValues.put("Name", pInfo.getName());
		contentValues.put("PageNumber", pInfo.getPageNumber());
		contentValues.put("BookID", pInfo.getBookID());
		return contentValues;

	}

	public boolean insertBookmark(ModelBookmark pBookmark) {
		ContentValues _contentValues = createParms(pBookmark);
		long _newID = getDataBase().insert(getTableNameAndPK()[0], null,
				_contentValues);
		pBookmark.setBookmarkID((int) _newID);
		return _newID > 0;
	}

	public boolean deleteBookmark(String pCondition) {
		return delete(getTableNameAndPK()[0], pCondition);
	}

	public boolean updateBookmark(String pCondition, ModelBookmark pModelBookmark) {
		ContentValues _contentValues = createParms(pModelBookmark);
		return getDataBase().update(getTableNameAndPK()[0], _contentValues,
				pCondition, null) > 0;
	}

	public Boolean updateBookmark(String pCondition, ContentValues pContentValues) {
		return getDataBase().update(getTableNameAndPK()[0], pContentValues,
				pCondition, null) > 0;
	}

	public List<ModelBookmark> getBookmark(String pCondition) {
		String _sqlText = "Select * From " + getTableNameAndPK()[0]
				+ " Where 1=1 " + pCondition + " Order by  PageNumber";
		return getList(_sqlText);
	}

}
