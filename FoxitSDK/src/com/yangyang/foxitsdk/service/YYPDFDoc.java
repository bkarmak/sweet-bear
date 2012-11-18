package com.yangyang.foxitsdk.service;

import java.nio.ByteBuffer;

import FoxitEMBSDK.EMBJavaSupport;
import FoxitEMBSDK.EMBJavaSupport.Rectangle;

import android.graphics.Bitmap;
import android.util.Log;

import com.yangyang.foxitsdk.exception.memoryException;
import com.yangyang.foxitsdk.exception.parameterException;

public class YYPDFDoc {

	/* state variables */
	private boolean cleanUpFlag = true;
	private boolean initFlag = false;
	private int fileAccessHandle = 0;
	private int nPDFDocHandler = 0;
	protected int[] pageHandlers;
	protected int nPageCount = 0;
	private int nCurrentPageNumber = 0;
	private static String TAG = "FoxitDoc";
	private static final String strFontFilePath = "/mnt/sdcard/DroidSansFallback.ttf";

	/**
	 * if you want to allow a specified memory block,you must call this function
	 * yourself.Otherwides,the SDK will initialize a 5M memory block when
	 * creating a PDF document.
	 * 
	 * @param memorySize
	 */
	public void initFoxitSDK(int memorySize) {
		try {
			EMBJavaSupport.FSMemInitFixedMemory(memorySize);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			postToLog(e.getMessage());
			return;
		}
		EMBJavaSupport.FSInitLibrary(0);
		EMBJavaSupport.FSUnlock("SDKEDTEMP",
				"3C86F25880658927118E766271BEB68454E49DFD");
		LoadJbig2Decoder();
		LoadJpeg2000Decoder();
		LoadCNSFontCMap();
		LoadKoreaFontCMap();
		LoadJapanFontCMap();
		SetFontFileMap();
		this.initFlag = true;
	}

	/** Load jbig2 decoder. */
	private void LoadJbig2Decoder() {
		EMBJavaSupport.FSLoadJbig2Decoder();
	}

	/** Load jpeg2000 decoder. */
	private void LoadJpeg2000Decoder() {
		EMBJavaSupport.FSLoadJpeg2000Decoder();
	}

	/** */
	private void LoadJapanFontCMap() {
		EMBJavaSupport.FSFontLoadJapanCMap();
		EMBJavaSupport.FSFontLoadJapanExtCMap();
	}

	/** */
	private void LoadCNSFontCMap() {
		EMBJavaSupport.FSFontLoadGBCMap();
		EMBJavaSupport.FSFontLoadGBExtCMap();
		EMBJavaSupport.FSFontLoadCNSCMap();
	}

	/** */
	private void LoadKoreaFontCMap() {
		EMBJavaSupport.FSFontLoadKoreaCMap();
	}

	private void SetFontFileMap() {
		try {
			EMBJavaSupport.FSSetFileFontmap(strFontFilePath);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public YYPDFDoc(String filePath, String password) {
		try {
			if (!initFlag)
				this.initFoxitSDK(5 * 1024 * 1024);
			fileAccessHandle = EMBJavaSupport.FSFileReadAlloc(filePath);
			nPDFDocHandler = EMBJavaSupport.FPDFDocLoad(fileAccessHandle,
					password);
			this.pageHandlers = new int[this.getPageCounts()];
		} catch (memoryException e) {
			EMBJavaSupport.FSFileReadRelease(fileAccessHandle);
			nPDFDocHandler = 0;
			fileAccessHandle = 0;
			postToLog(e.getMessage());
			return;
		} catch (Exception e) {
			postToLog(e.getMessage());
			return;
		}
	}

	/** Count PDF page. */
	public int getPageCounts() {
		if (nPDFDocHandler == 0) {
			return EMBJavaSupport.EMBJavaSupport_RESULT_ERROR;
		}
		if (nPageCount <= 0)
			try {
				nPageCount = EMBJavaSupport.FPDFDocGetPageCount(nPDFDocHandler);
			} catch (Exception e) {
				e.printStackTrace();
			}
		return nPageCount;
	}

	/**
	 * return a specified page handler.
	 * 
	 * @param pageNumber
	 * @return
	 */
	public int getPageHandler(int pageNumber) {
		if (pageHandlers.length <= pageNumber) {
			postToLog("pageNumber is bigger than max page count!");
		} else if (pageHandlers[pageNumber] <= 0) {
			try {
				pageHandlers[pageNumber] = EMBJavaSupport.FPDFPageLoad(
						nPDFDocHandler, pageNumber);
				EMBJavaSupport.FPDFPageStartParse(pageHandlers[pageNumber], 0,
						0);
			} catch (memoryException e) {
				postToLog(e.getMessage());
			} catch (Exception e) {
				postToLog(e.getMessage());
			}
		}
		return pageHandlers[pageNumber];
	}

	/**
	 * jump to the next page
	 */
	public void nextPage() {
		this.nCurrentPageNumber++;
		this.nCurrentPageNumber = this.nCurrentPageNumber % this.nPageCount;
	}

	/**
	 * jump to the previous page
	 */
	public void previoutPage() {
		this.nCurrentPageNumber--;
		if (this.nCurrentPageNumber < 0)
			this.nCurrentPageNumber = 0;
	}

	public int getCurrentPage() {
		return this.nCurrentPageNumber;
	}

	public int getCurrentPageHandler() {
		if (this.nCurrentPageNumber < 0) {
			postToLog("error current page number:" + this.nCurrentPageNumber);
			return -1;
		}
		return this.getPageHandler(this.nCurrentPageNumber);
	}

	/**
	 * jump to a specified page number.
	 * 
	 * @param pageNumber
	 */
	public void gotoPage(int pageNumber) {
		if (pageNumber >= this.getPageCounts()) {
			postToLog("pdf document has no page:" + pageNumber);
		} else {
			this.nCurrentPageNumber = pageNumber;
		}
	}

	/** Render pdf to bitmap. */
	public Bitmap getPageBitmap(int displayWidth, int displayHeight) {
		int nPDFCurPageHandler = this.getCurrentPageHandler();
		if (nPDFCurPageHandler == 0) {
			return null;
		}
		Bitmap bm;
		bm = Bitmap.createBitmap(displayWidth, displayHeight,
				Bitmap.Config.ARGB_8888);
		int dib;
		try {
			dib = EMBJavaSupport.FSBitmapCreate(displayWidth, displayHeight, 7,
					null, 0);
			EMBJavaSupport.FSBitmapFillColor(dib, 0xff);
			EMBJavaSupport.FPDFRenderPageStart(dib, nPDFCurPageHandler, 0, 0,
					displayWidth, displayHeight, 0, 0, null, 0);
			byte[] bmpbuf = EMBJavaSupport.FSBitmapGetBuffer(dib);
			ByteBuffer bmBuffer = ByteBuffer.wrap(bmpbuf);
			bm.copyPixelsFromBuffer(bmBuffer);
			EMBJavaSupport.FSBitmapDestroy(dib);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return bm;
	}

	private void RenderPage(int pageHandler, Bitmap bm, int startX, int startY,
			float xScale, float yScale, int rotate, int flags, Rectangle rect,
			int pauseHandler) {
		try {
			int dib = EMBJavaSupport.FSBitmapCreate(bm.getWidth(),
					bm.getHeight(), 7, null, 0);
			EMBJavaSupport.FSBitmapFillColor(dib, 0xff);
			float scaledWidth = bm.getWidth() * xScale;
			float scaledHeight = bm.getHeight() * yScale;
			EMBJavaSupport.FPDFRenderPageStart(dib, pageHandler, startX,
					startY, (int) scaledWidth, (int) scaledHeight, rotate,
					flags, rect, pauseHandler);
			byte[] bmpbuf = EMBJavaSupport.FSBitmapGetBuffer(dib);

			ByteBuffer bmBuffer = ByteBuffer.wrap(bmpbuf);
			bm.copyPixelsFromBuffer(bmBuffer);

		} catch (memoryException e) {
			postToLog(e.getMessage());
		} catch (Exception e) {
			postToLog(e.getMessage());
		}
	}

	public void lock() {

	}

	public void save(String fileName) {

	}

	// clean up unmanaged resources
	public void close() {
		for (int i = 0; i < pageHandlers.length; i++) {
			if (pageHandlers[i] > 0) {// if page handle exist for that page
				try {
					EMBJavaSupport.FPDFPageClose(pageHandlers[i]);
					pageHandlers[i] = 0;
				} catch (parameterException e) {
					// Not handling parameter exception for now
				}
			}
		}
		if (fileAccessHandle != 0) {
			EMBJavaSupport.FSFileReadRelease(fileAccessHandle);
		}
		cleanUpFlag = false;
	}

	public float GetPageSizeX(int pageIndex) {
		if (this.pageHandlers[pageIndex] == 0) {
			this.getPageHandler(pageIndex);
		}

		try {
			return EMBJavaSupport.FPDFPageGetSizeX(pageHandlers[pageIndex]);
		} catch (parameterException e) {
			postToLog(e.getMessage());
			return 0;
		}
	}

	public float GetPageSizeY(int pageIndex) {
		if (pageHandlers[pageIndex] == 0) {
			this.getPageHandler(pageIndex);
		}

		try {
			return EMBJavaSupport.FPDFPageGetSizeY(pageHandlers[pageIndex]);
		} catch (parameterException e) {
			postToLog(e.getMessage());
			return 0;
		}
	}

	private void postToLog(String msg) {
		Log.v(TAG, msg);
	}

	public int getDocumentHandler() {
		// TODO Auto-generated method stub
		return this.nPDFDocHandler;
	}
}
