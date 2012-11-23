package com.wanhu.android.shelves.database.base;

import java.util.ArrayList;

import android.content.Context;

import com.wanhu.android.shelves.R;

public class SQLiteDatabaseConfig {
	private static final String DATABASE_NAME = "centradis.db";
	private static final int VERSION = 1;
	private static SQLiteDatabaseConfig INSTANCE;
	private static Context mContext;

	private SQLiteDatabaseConfig() {

	}

	public static SQLiteDatabaseConfig getInstance(Context pContext) {
		if (INSTANCE == null) {
			INSTANCE = new SQLiteDatabaseConfig();
			mContext = pContext;
		}
		return INSTANCE;
	}

	public String getDataBaseName() {
		return DATABASE_NAME;
	}

	public int getVersion() {
		return VERSION;
	}

	public ArrayList<String> getTables() {
		ArrayList<String> _arrayList = new ArrayList<String>();
		String[] _sqliteDALClassName = mContext.getResources().getStringArray(
				R.array.SQLiteDALClassName);
		String _packagePath = mContext.getPackageName()
				+ ".database.sqlitedal.";
		for (int i = 0; i < _sqliteDALClassName.length; i++) {
			_arrayList.add(_packagePath + _sqliteDALClassName[i]);
		}
		return _arrayList;
	}

}
