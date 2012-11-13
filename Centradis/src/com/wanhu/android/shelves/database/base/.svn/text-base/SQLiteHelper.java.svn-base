package com.wanhu.android.shelves.database.base;

import java.util.ArrayList;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.wanhu.android.shelves.util.Reflection;

public class SQLiteHelper extends SQLiteOpenHelper {

	private static SQLiteDatabaseConfig SQLITE_DATABASE_CONFIG;
	private static SQLiteHelper INSTANCE;
	private Context mContext;
	private Reflection mReflection;

	public interface SQLiteDataTable {
		public void onCreate(SQLiteDatabase pDataBase);

		public void onUpgrade(SQLiteDatabase pDataBase);
	}

	public SQLiteHelper(Context pContext) {
		super(pContext, SQLITE_DATABASE_CONFIG.getDataBaseName(), null,
				SQLITE_DATABASE_CONFIG.getVersion());
		mContext = pContext;
	}

	public static SQLiteHelper getInstance(Context pContext) {
		if (INSTANCE == null) {
			SQLITE_DATABASE_CONFIG = SQLiteDatabaseConfig.getInstance(pContext);
			INSTANCE = new SQLiteHelper(pContext);
		}
		return INSTANCE;
	}

	@Override
	public void onCreate(SQLiteDatabase pDataBase) {
		ArrayList<String> _arrayList = SQLITE_DATABASE_CONFIG.getTables();
		mReflection = new Reflection();
		for (int i = 0; i < _arrayList.size(); i++) {
			try {
				((SQLiteDataTable) mReflection.newInstance(_arrayList.get(i),
						new Object[] { mContext },
						new Class[] { Context.class })).onCreate(pDataBase);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

}
