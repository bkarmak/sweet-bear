package com.wanhu.android.shelves.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import android.graphics.Bitmap;

import com.wanhu.android.shelves.provider.BooksStore;

public final class ImportUtilities {
	private static final String COVER_CACHE_DIRECTORY = "shelves/books/covers";
	private static final String IMPORT_FILE = "library.txt";

	private ImportUtilities() {
	}

	public static File getCacheDirectory() {
		return IOUtilities.getExternalFile(COVER_CACHE_DIRECTORY);
	}

	public static ArrayList<String> loadItems() throws IOException {
		ArrayList<String> list = new ArrayList<String>();

		File importFile = IOUtilities.getExternalFile(IMPORT_FILE);
		if (!importFile.exists())
			return list;

		BufferedReader in = null;
		try {
			in = new BufferedReader(new InputStreamReader(new FileInputStream(
					importFile)), IOUtilities.IO_BUFFER_SIZE);

			String line;

			// Read the CSV headers
			in.readLine();

			while ((line = in.readLine()) != null) {
				final int index = line.indexOf('\t');
				final int length = line.length();

				// Only one field, we grab the entire line
				if (index == -1 && length > 0) {
					list.add(line);
					// Only one field, the first one is empty
				} else if (index != length - 1) {
					list.add(line.substring(index + 1));
					// We have two fields or the second one is empty
				} else {
					list.add(line.substring(0, index));
				}
			}
		} finally {
			IOUtilities.closeStream(in);
		}

		return list;
	}

	public static boolean addBookCoverToCache(BooksStore.Book book,
			Bitmap bitmap) {
		File cacheDirectory;
		try {
			cacheDirectory = ensureCache();
		} catch (IOException e) {
			return false;
		}

		File coverFile = new File(cacheDirectory, book.getInternalId());
		FileOutputStream out = null;
		try {
			out = new FileOutputStream(coverFile);
			bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
		} catch (FileNotFoundException e) {
			return false;
		} finally {
			IOUtilities.closeStream(out);
		}

		return true;
	}

	private static File ensureCache() throws IOException {
		File cacheDirectory = getCacheDirectory();
		if (!cacheDirectory.exists()) {
			cacheDirectory.mkdirs();
			new File(cacheDirectory, ".nomedia").createNewFile();
		}
		return cacheDirectory;
	}

}
