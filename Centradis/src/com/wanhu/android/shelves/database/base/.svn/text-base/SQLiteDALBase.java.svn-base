package com.wanhu.android.shelves.database.base;

import java.util.ArrayList;
import java.util.List;

import com.wanhu.android.shelves.database.base.SQLiteHelper.SQLiteDataTable;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public abstract class SQLiteDALBase implements SQLiteDataTable{
	private Context mContext;
	private SQLiteDatabase mDataBase;

	protected abstract String[] getTableNameAndPK();

	protected abstract Object findModel(Cursor pCursor);

	public SQLiteDALBase(Context pContext) {
		mContext = pContext;
	}

	protected Context getContext() {
		return mContext;
	}

	public SQLiteDatabase getDataBase() {
		if (mDataBase == null) {
			mDataBase = SQLiteHelper.getInstance(mContext)
					.getWritableDatabase();
		}
		return mDataBase;
	}

	public void beginTransaction() {
		mDataBase.beginTransaction();
	}

	public void setTransactionSuccessful() {
		mDataBase.setTransactionSuccessful();
	}

	public void endTransaction() {
		mDataBase.endTransaction();
	}

	public int getCount(String pCondition) {
		String[] _string = getTableNameAndPK();
		Cursor _cursor = execSql("Select " + _string[1] + " From " + _string[0]
				+ " Where 1=1 " + pCondition);
		int _count = _cursor.getCount();
		_cursor.close();
		return _count;
	}

	public int getCount(String pPK, String pTableName, String pCondition) {
		Cursor _cursor = execSql("Select " + pPK + " From " + pTableName
				+ " Where 1=1 " + pCondition);
		int _count = _cursor.getCount();
		_cursor.close();
		return _count;
	}

	protected Boolean delete(String pTableName, String pCondition) {
		return getDataBase().delete(pTableName, " 1=1 " + pCondition, null) >= 0;
	}

	protected List getList(String pSqlText) {
		Cursor _cursor = execSql(pSqlText);
		return cursorToList(_cursor);
	}

	protected List cursorToList(Cursor pCursor) {
		List _list = new ArrayList();
		while (pCursor.moveToNext()) {
			Object _object = findModel(pCursor);
			_list.add(_object);
		}
		pCursor.close();
		return _list;
	}

	public Cursor execSql(String pSqlText) {
		return getDataBase().rawQuery(pSqlText, null);
	}
}
