package com.wanhu.android.shelves;

import android.app.Application;

import com.wanhu.android.shelves.activity.EReaderActivity;
import com.wanhu.android.shelves.util.CookieStore;

public class ShelvesApplication extends Application {
	@Override
	public void onCreate() {
		super.onCreate();

		CookieStore.initialize(this);

	}

	@Override
	public void onLowMemory() {
		super.onLowMemory();
		if (EReaderActivity.mPDFView != null) {
			EReaderActivity.mPDFView.purgeMemory();
		}
	}
}
