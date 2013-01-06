package com.wanhu.android.shelves.business;

import java.util.List;

import android.content.Context;

import com.wanhu.android.shelves.business.base.BusinessBase;
import com.wanhu.android.shelves.database.sqlitedal.SQLiteDALNote;
import com.wanhu.android.shelves.model.ModelNote;

public class BusinessNote extends BusinessBase {

	private SQLiteDALNote mSqLiteDALNote;

	public BusinessNote(Context pContext) {
		super(pContext);
		mSqLiteDALNote = new SQLiteDALNote(pContext);
	}

	public boolean insertNote(ModelNote pInfo) {
		boolean _result = mSqLiteDALNote.insertNote(pInfo);
		return _result;
	}

	public boolean deleteNoteByBookID(String pBookID) {
		String _condition = " And BookID = '" + pBookID + "'";
		boolean _result = mSqLiteDALNote.deleteNote(_condition);

		return _result;
	}

	public boolean deleteNote(ModelNote note) {
		String _condition = " and BookID = '" + note.getBookID()
				+ "'  and NoteID = " + note.getNoteID();
		return mSqLiteDALNote.deleteNote(_condition);
	}

	public boolean deleteNode(String bookID, int noteID) {
		String _condition = " and BookID = '" + bookID + "'  and NoteID = "
				+ noteID;
		return mSqLiteDALNote.deleteNote(_condition);
	}

	public boolean updateUserByBookID(ModelNote pInfo) {
		String _condition = " BookID = '" + pInfo.getBookID() + "'";
		boolean _result = mSqLiteDALNote.updateNote(_condition, pInfo);

		return _result;
	}

	private List<ModelNote> getNote(String pCondition) {
		return mSqLiteDALNote.getNote(pCondition);
	}

	public ModelNote getModelNoteByBookID(String pBookID) {
		List<ModelNote> _list = getNote(" And BookID = '" + pBookID + "'");
		if (_list.size() == 1) {
			return _list.get(0);
		} else {
			return null;
		}
	}
}
