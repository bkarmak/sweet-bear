package com.wanhu.android.shelves.database.sqlitedal;

import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.wanhu.android.shelves.database.base.SQLiteDALBase;
import com.wanhu.android.shelves.model.ModelNote;

public class SQLiteDALNote extends SQLiteDALBase {

	public SQLiteDALNote(Context pContext) {
		super(pContext);
	}

	@Override
	public void onCreate(SQLiteDatabase pDataBase) {
		StringBuilder _sCreateTableScript = new StringBuilder();
		_sCreateTableScript.append("		Create TABLE Note(");
		_sCreateTableScript.append("			   [NoteID] integer PRIMARY KEY AUTOINCREMENT NOT NULL");
		_sCreateTableScript.append("			  ,[BookID] TEXT NOT NULL");
		_sCreateTableScript.append("			  ,[ContentEN] TEXT");
		_sCreateTableScript.append("			  ,[ContentFR] TEXT");
		_sCreateTableScript.append(" 			  )");
		pDataBase.execSQL(_sCreateTableScript.toString());
	}

	@Override
	public void onUpgrade(SQLiteDatabase pDataBase) {

	}

	@Override
	protected String[] getTableNameAndPK() {
		return new String[] { "Note", "NoteID" };
	}

	@Override
	protected Object findModel(Cursor pCursor) {
		ModelNote _modelNote = new ModelNote();
		_modelNote.setNoteID(pCursor.getInt(pCursor
				.getColumnIndexOrThrow("NoteID")));
		_modelNote.setBookID(pCursor.getString(pCursor
				.getColumnIndexOrThrow("BookID")));
		_modelNote.setContentEn(pCursor.getString(pCursor
				.getColumnIndexOrThrow("ContentEN")));
		_modelNote.setContentFr(pCursor.getString(pCursor
				.getColumnIndexOrThrow("ContentFR")));
		return _modelNote;
	}

	public ContentValues createParms(ModelNote pInfo) {
		ContentValues contentValues = new ContentValues();
		contentValues.put("BookID", pInfo.getBookID());
		contentValues.put("ContentEN", pInfo.getContentEn());
		contentValues.put("ContentFR", pInfo.getContentFr());
		return contentValues;

	}

	public boolean insertNote(ModelNote pModelNote) {
		ContentValues _contentValues = createParms(pModelNote);
		long _newID = getDataBase().insert(getTableNameAndPK()[0], null,
				_contentValues);
		pModelNote.setNoteID((int) _newID);
		return _newID > 0;
	}

	public boolean deleteNote(String pCondition) {
		return delete(getTableNameAndPK()[0], pCondition);
	}

	public boolean updateNote(String pCondition, ModelNote pModelNote) {
		ContentValues _contentValues = createParms(pModelNote);
		return getDataBase().update(getTableNameAndPK()[0], _contentValues,
				pCondition, null) > 0;
	}

	public Boolean updateNote(String pCondition, ContentValues pContentValues) {
		return getDataBase().update(getTableNameAndPK()[0], pContentValues,
				pCondition, null) > 0;
	}

	public List<ModelNote> getNote(String pCondition) {
		String _sqlText = "Select * From " + getTableNameAndPK()[0]
				+ " Where 1=1 " + pCondition;
		return getList(_sqlText);
	}

}
