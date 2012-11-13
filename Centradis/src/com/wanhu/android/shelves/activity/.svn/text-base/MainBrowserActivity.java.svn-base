package com.wanhu.android.shelves.activity;

import java.io.File;
import java.io.FileFilter;
import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public class MainBrowserActivity extends BaseBrowserActivity {
	
	protected static boolean shouldAdd = false;
	private final static HashMap<String, Class<? extends Activity>> extensionToActivity = new HashMap<String, Class<? extends Activity>>();

	static {
		extensionToActivity.put("pdf", EReaderActivity.class);
	}

	@Override
	protected FileFilter createFileFilter() {
		return new FileFilter() {
			public boolean accept(File pathname) {
				for (String s : extensionToActivity.keySet()) {
					if (pathname.getName().endsWith("." + s))
						return true;
				}
				return pathname.isDirectory();
			}
		};
	}

	@Override
	protected void showDocument(Uri uri) {
		final Intent intent = new Intent(Intent.ACTION_VIEW, uri);
		String uriString = uri.toString();
		String extension = uriString.substring(uriString.lastIndexOf('.') + 1);
		intent.setClass(this, extensionToActivity.get(extension));
		intent.putExtra("shouldAdd", shouldAdd);
		startActivity(intent);
		finish();
	}

	static void show(Context context, boolean fromLocal) {
		final Intent intent = new Intent(context, MainBrowserActivity.class);
		shouldAdd = fromLocal;
		context.startActivity(intent);
	}
}
