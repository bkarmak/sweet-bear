package com.yangyang.foxitsdk.service;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import FoxitEMBSDK.EMBJavaSupport;
import FoxitEMBSDK.EMBJavaSupport.CPDFFormFillerInfo;
import FoxitEMBSDK.EMBJavaSupport.CPDFJsPlatform;
import FoxitEMBSDK.EMBJavaSupport.CPDFPSI;
import FoxitEMBSDK.EMBJavaSupport.RectangleF;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.util.Log;

import com.yangyang.foxitsdk.exception.invalidLicenseException;
import com.yangyang.foxitsdk.exception.memoryException;
import com.yangyang.foxitsdk.exception.parameterException;
import com.yangyang.foxitsdk.view.IPDFView;

/**
 * defined for a wrap for All PDF implements��
 * 
 * @author Foxit
 * 
 */

public class WrapPDFFunc {
	private int nFileRead = 0;
	private int nPDFDocHandler = 0;
	private int nPDFCurPageHandler = 0;
	private int nDisplayX = 0;
	private int nDisplayY = 0;
	private List<Integer> arrIndexList = null;

	/** form */
	private IPDFView mainView = null;
	private CPDFFormFillerInfo formFillerInfo = null;
	private int nPDFFormFillerInfo = 0;
	private CPDFJsPlatform jsPlatform = null;
	private int nPDFJsPlatform = 0;
	private int nPDFFormHandler = 0;

	/** psi */
	private CPDFPSI fxPsi = null;
	private int nPSICallback = 0;
	private int nPSIHandle = 0;

	/** */
	public WrapPDFFunc(int x, int y, IPDFView context) {
		nDisplayX = x;
		nDisplayY = y;
		this.mainView = context;
		arrIndexList = new ArrayList<Integer>();
	}

	public WrapPDFFunc(IPDFView context) {
		this(0, 0, context);
	}

	public int getPageHandler(int nPageIndex) {
		if (nPDFDocHandler == 0) {
			return EMBJavaSupport.EMBJavaSupport_RESULT_ERROR;
		}

		if (nPageIndex == 0 && nPDFCurPageHandler != 0)
			return nPDFCurPageHandler;

		int nPageHandler = 0;
		try {
			nPageHandler = EMBJavaSupport.FPDFPageLoad(nPDFDocHandler,
					nPageIndex);

			EMBJavaSupport.FPDFPageStartParse(nPageHandler, 0, 0);
			EMBJavaSupport.FPDFFormFillOnAfterLoadPage(nPDFFormHandler,
					nPageHandler);
			// /
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return nPageHandler;
	}

	public boolean InitFoxitFixedMemory(int initMemSize)
			throws parameterException, invalidLicenseException {
		EMBJavaSupport.FSMemInitFixedMemory(initMemSize);
		EMBJavaSupport.FSInitLibrary(0);
		EMBJavaSupport.FSUnlock("SDKEDTEMP",
				"3C86F25880658927118E766271BEB68454E49DFD");

		// ///////formfiller implemention
		if (mainView == null)
			return false;
		formFillerInfo = new EMBJavaSupport().new CPDFFormFillerInfo(mainView);
		if (formFillerInfo == null)
			return false;
		nPDFFormFillerInfo = EMBJavaSupport
				.FPDFFormFillerInfoAlloc(formFillerInfo);
		if (nPDFFormFillerInfo == 0)
			return false;

		jsPlatform = new EMBJavaSupport().new CPDFJsPlatform();
		if (jsPlatform == null)
			return false;
		nPDFJsPlatform = EMBJavaSupport.FPDFJsPlatformAlloc(jsPlatform);
		if (nPDFJsPlatform == 0)
			return false;

		EMBJavaSupport.FPDFFormFillerInfoSetJsPlatform(nPDFFormFillerInfo,
				nPDFJsPlatform);
		EMBJavaSupport.FPDFJsPlatformSetFormFillerInfo(nPDFJsPlatform,
				nPDFFormFillerInfo);

		// PSI
		if (mainView == null) {
			// return EMBJavaSupport.EMBJavaSupport_RESULT_ERROR;
		}
		fxPsi = new EMBJavaSupport().new CPDFPSI(mainView);
		if (fxPsi == null) {
			// return EMBJavaSupport.EMBJavaSupport_RESULT_ERROR;
		}
		nPSICallback = EMBJavaSupport.FPSIInitAppCallback(fxPsi);
		nPSIHandle = EMBJavaSupport.FPSIInitEnvironment(nPSICallback, true);
		if (nPSIHandle == 0) {
			// return EMBJavaSupport.EMBJavaSupport_RESULT_ERROR;
		}
		int nRet = EMBJavaSupport.FPSISetInkDiameter(nPSIHandle, 20);
		if (nRet != 0) {
			// return EMBJavaSupport.EMBJavaSupport_RESULT_ERROR;
		}
		nRet = EMBJavaSupport.FPSIInitCanvas(nPSIHandle, nDisplayX, nDisplayY);
		if (nRet != 0) {
			// return EMBJavaSupport.EMBJavaSupport_RESULT_ERROR;
		}
		nRet = EMBJavaSupport.FPSISetInkColor(nPSIHandle, 255);
		if (nRet != 0) {
			// return EMBJavaSupport.EMBJavaSupport_RESULT_ERROR;
		}

		return true;
	}

	public void setDisplaySize(int x, int y) {
		this.nDisplayX = x;
		this.nDisplayY = y;
	}

	public FoxitDoc createFoxitDoc(String fileName, String password) {
		FoxitDoc doc = new FoxitDoc(fileName, password);
		this.nPDFDocHandler = doc.getDocumentHandle();
		nPDFFormHandler = EMBJavaSupport.FPDFDocInitFormFillEnviroument(
				nPDFDocHandler, nPDFFormFillerInfo);
		{
			if (nPDFFormHandler == -1) {
				Log.e("error", "get pdf form handler error");
			}
		}

		return doc;

	}

	public void SetFontFileMap(String strFontFilePath)
			throws parameterException {
		EMBJavaSupport.FSSetFileFontmap(strFontFilePath);
	}

	/** Destroy EMB SDK */
	public int DestroyFoxitFixedMemory() {

		EMBJavaSupport.FSDestroyLibrary();

		EMBJavaSupport.FSMemDestroyMemory();

		return EMBJavaSupport.EMBJavaSupport_RESULT_SUCCESS;
	}

	/** Load jbig2 decoder. */
	public void LoadJbig2Decoder() {
		EMBJavaSupport.FSLoadJbig2Decoder();
	}

	/** Load jpeg2000 decoder. */
	public void LoadJpeg2000Decoder() {
		EMBJavaSupport.FSLoadJpeg2000Decoder();
	}

	/** */
	public void LoadJapanFontCMap() {
		EMBJavaSupport.FSFontLoadJapanCMap();
		EMBJavaSupport.FSFontLoadJapanExtCMap();
	}

	/** */
	public void LoadCNSFontCMap() {
		EMBJavaSupport.FSFontLoadGBCMap();
		EMBJavaSupport.FSFontLoadGBExtCMap();
		EMBJavaSupport.FSFontLoadCNSCMap();
	}

	/** */
	public void LoadKoreaFontCMap() {
		EMBJavaSupport.FSFontLoadKoreaCMap();
	}

	/** Close PDF Document. */
	public int ClosePDFDoc() {

		if (nPDFDocHandler == 0) {
			return EMBJavaSupport.EMBJavaSupport_RESULT_ERROR;
		}

		// /formfiller implemention
		EMBJavaSupport.FPDFDocExitFormFillEnviroument(nPDFFormHandler);
		nPDFFormHandler = 0;

		EMBJavaSupport.FPDFFormFillerInfoRelease(nPDFFormFillerInfo);
		nPDFFormFillerInfo = 0;

		EMBJavaSupport.FPDFJsPlatformRelease(nPDFJsPlatform);
		nPDFJsPlatform = 0;
		//

		// /PSI
		if (nPSIHandle == 0) {
			return EMBJavaSupport.EMBJavaSupport_RESULT_ERROR;
		}
		EMBJavaSupport.FPSIDestroyEnvironment(nPSIHandle);
		nPSIHandle = 0;
		EMBJavaSupport.FPSIReleaseAppCallback(nPSICallback);
		nPSICallback = 0;
		//

		try {
			EMBJavaSupport.FPDFDocClose(nPDFDocHandler);
		} catch (Exception e) {
			e.printStackTrace();
		}

		EMBJavaSupport.FSFileReadRelease(nFileRead);
		nFileRead = 0;

		return EMBJavaSupport.EMBJavaSupport_RESULT_SUCCESS;
	}

	/** Load and parser a PDF page. */
	public int InitPDFPage(int nPageIndex) {

		if (nPDFDocHandler == 0) {
			return EMBJavaSupport.EMBJavaSupport_RESULT_ERROR;
		}

		try {
			if (nPDFCurPageHandler <= 0) {
				nPDFCurPageHandler = EMBJavaSupport.FPDFPageLoad(
						nPDFDocHandler, nPageIndex);

				if (nPDFCurPageHandler == 0) {
					return EMBJavaSupport.EMBJavaSupport_RESULT_ERROR;
				}
			}

			EMBJavaSupport.FPDFPageStartParse(nPDFCurPageHandler, 0, 0);
			EMBJavaSupport.FPDFFormFillOnAfterLoadPage(nPDFFormHandler,
					nPDFCurPageHandler);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return EMBJavaSupport.EMBJavaSupport_RESULT_SUCCESS;
	}

	/** Close a PDF Page. */
	public int ClosePDFPage() {

		if (nPDFCurPageHandler == 0) {
			return EMBJavaSupport.EMBJavaSupport_RESULT_ERROR;
		}

		try {
			EMBJavaSupport.FPDFFormFillOnBeforeClosePage(nPDFFormHandler,
					nPDFCurPageHandler);
			nPDFCurPageHandler = 0;
			// EMBJavaSupport.FPDFPageClose(nPDFCurPageHandler);
		} catch (Exception e) {
			e.printStackTrace();
		}

		nPDFCurPageHandler = 0;

		return EMBJavaSupport.EMBJavaSupport_RESULT_SUCCESS;
	}

	/** Count PDF page. */
	public int GetPageCounts() {

		if (nPDFDocHandler == 0) {
			return EMBJavaSupport.EMBJavaSupport_RESULT_ERROR;
		}

		int nPageCount = 0;
		try {
			nPageCount = EMBJavaSupport.FPDFDocGetPageCount(nPDFDocHandler);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return nPageCount;
	}

	public int getCurPDFPageHandler() {
		return nPDFCurPageHandler;
	}

	public int getPDFFormHandler() {
		return nPDFFormHandler;
	}

	public void setCurPDFPageHandler(int pageHandler) {
		this.nPDFCurPageHandler = pageHandler;
	}

	public Bitmap getDirtyBitmap(Rect rect, int nSizex, int nSizey) {
		Bitmap bm = null;
		if (nPDFCurPageHandler == 0) {
			return null;
		}

		bm = Bitmap.createBitmap(rect.width(), rect.height(),
				Bitmap.Config.ARGB_8888);
		int dib;
		try {
			dib = EMBJavaSupport.FSBitmapCreate(rect.width(), rect.height(), 7,
					null, 0);

			EMBJavaSupport.FSBitmapFillColor(dib, 0xff);
			EMBJavaSupport.FPDFRenderPageStart(dib, nPDFCurPageHandler,
					-rect.left, -rect.top, nSizex, nSizey, 0, 0, null, 0);

			EMBJavaSupport.FPSIRender(nPSIHandle, dib, 0, 0, rect.right
					- rect.left, rect.bottom - rect.top, rect.left, rect.top);

			//
			// /formfiller implemention
			if (nPDFFormHandler == 0)
				return null;
			else
				EMBJavaSupport.FPDFFormFillDraw(nPDFFormHandler, dib,
						nPDFCurPageHandler, -rect.left, -rect.top, nSizex,
						nSizey, 0, 0);

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

	/** Render pdf to bitmap. */
	public Bitmap getPageBitmap(int displayWidth, int displayHeight) {
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

			// ///formfiller implemention
			// if (nPDFFormHandler == 0)
			// return null;
			// EMBJavaSupport.FPDFFormFillDraw(nPDFFormHandler, dib,
			// nPDFCurPageHandler, 0, 0, displayWidth, displayHeight, 0, 0);
			// ///

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

	public int addAnnot(int pageHandler, int nAnnotType) throws memoryException {

		this.nPDFCurPageHandler = pageHandler;
		switch (nAnnotType) {

		case EMBJavaSupport.EMBJavaSupport_ANNOTTYPE_UNKNOWN: {
			return EMBJavaSupport.EMBJavaSupport_RESULT_ERROR;
		}

		case EMBJavaSupport.EMBJavaSupport_ANNOTTYPE_NOTE: {
			RectangleF pdfRect = new EMBJavaSupport().new RectangleF();
			pdfRect.left = 10;
			pdfRect.top = 220;
			pdfRect.right = 30;
			pdfRect.bottom = 200;
			EMBJavaSupport.FPDFAnnotGetCount(nPDFCurPageHandler);
			int nNoteInfoItem = EMBJavaSupport.FPDFNoteInfoAlloc("James",
					0x0000ff, 80, pdfRect, "I like note", nPDFCurPageHandler);
			EMBJavaSupport.FPDFAnnotGetCount(nPDFCurPageHandler);
			int nIndex = EMBJavaSupport
					.FPDFAnnotAdd(nPDFCurPageHandler,
							EMBJavaSupport.EMBJavaSupport_ANNOTTYPE_NOTE,
							nNoteInfoItem);
			arrIndexList.add(nIndex);
			EMBJavaSupport.FPDFNoteInfoRelease(nNoteInfoItem);

			/** device space convert to pdf space. */
			RectangleF deviceRect = new EMBJavaSupport().new RectangleF();
			deviceRect.left = 10;
			deviceRect.top = 100;
			deviceRect.right = 30;
			deviceRect.bottom = 120;
			EMBJavaSupport.FPDFPageDeviceToPageRectF(nPDFCurPageHandler, 0, 0,
					nDisplayX, nDisplayY, 0, deviceRect);
			deviceRect.right = deviceRect.left + 20;
			deviceRect.bottom = deviceRect.top - 20;
			nNoteInfoItem = EMBJavaSupport.FPDFNoteInfoAlloc("James", 0xff00ff,
					80, deviceRect, "I like note too", nPDFCurPageHandler);
			nIndex = EMBJavaSupport
					.FPDFAnnotAdd(nPDFCurPageHandler,
							EMBJavaSupport.EMBJavaSupport_ANNOTTYPE_NOTE,
							nNoteInfoItem);
			arrIndexList.add(nIndex);
			EMBJavaSupport.FPDFNoteInfoRelease(nNoteInfoItem);

			break;
		}

		case EMBJavaSupport.EMBJavaSupport_ANNOTTYPE_HIGHLIGHT: {

			int TextPage = EMBJavaSupport.FPDFTextLoadPage(nPDFCurPageHandler);
			EMBJavaSupport.FPDFTextCountRects(TextPage, 0, 5);
			RectangleF rect = EMBJavaSupport.FPDFTextGetRect(TextPage, 0);
			EMBJavaSupport.FPDFTextCloseTextPage(TextPage);
			int nQuadsCount = 1;
			int nHighlightInfo = EMBJavaSupport.FPDFHighlightInfoAlloc("James",
					0x00ff00, 80, nQuadsCount);
			float[] quads = { rect.left, rect.top, rect.right, rect.top,
					rect.left, rect.bottom, rect.right, rect.bottom };
			int nQuadsInfo = EMBJavaSupport.FPDFQuadsInfoAlloc(nQuadsCount,
					quads);
			EMBJavaSupport.FPDFHighlightSetQuads(nHighlightInfo, nQuadsInfo);

			int nIndex = EMBJavaSupport.FPDFAnnotAdd(nPDFCurPageHandler,
					EMBJavaSupport.EMBJavaSupport_ANNOTTYPE_HIGHLIGHT,
					nHighlightInfo);
			arrIndexList.add(nIndex);

			EMBJavaSupport.FPDFHighlightInfoRelease(nHighlightInfo);
			EMBJavaSupport.FPDFQuadsInfoRelease(nQuadsInfo);

			break;
		}

		case EMBJavaSupport.EMBJavaSupport_ANNOTTYPE_PENCIL: {
			int line_count = 2;
			int nPencilInfoItem = EMBJavaSupport.FPDFPencilInfoAlloc("James",
					0xff0000, 80, true, true, 5, line_count);
			int nLineInfo = EMBJavaSupport.FPDFLineInfoAlloc(line_count);
			float[] points1 = { 300f, 100f, 400f, 200f };
			float[] points2 = { 400f, 200f, 500f, 200f, 400f, 100f };
			EMBJavaSupport.FPDFLineInfoSetPointInfo(nLineInfo, 0, 2, points1);
			EMBJavaSupport.FPDFLineInfoSetPointInfo(nLineInfo, 1, 3, points2);
			EMBJavaSupport
					.FPDFPencilInfoSetLineInfo(nPencilInfoItem, nLineInfo);

			int nIndex = EMBJavaSupport.FPDFAnnotAdd(nPDFCurPageHandler,
					EMBJavaSupport.EMBJavaSupport_ANNOTTYPE_PENCIL,
					nPencilInfoItem);
			arrIndexList.add(nIndex);

			EMBJavaSupport.FPDFLineInfoRelease(nLineInfo);
			EMBJavaSupport.FPDFPencilInfoRelease(nPencilInfoItem);

			break;
		}

		case EMBJavaSupport.EMBJavaSupport_ANNOTTYPE_STAMP: {
			RectangleF rect = new EMBJavaSupport().new RectangleF();
			rect.left = 137.97f;
			rect.top = 542.73f;
			rect.right = 417.97f;
			rect.bottom = 322.73f;
			String path = "/mnt/sdcard/FoxitLog.jpg";
			int nStampInfo = EMBJavaSupport.FPDFStampInfoAlloc("James",
					0xffff00, 80, rect, "Stamp_Test", path);
			int nIndex = EMBJavaSupport.FPDFAnnotAdd(nPDFCurPageHandler,
					EMBJavaSupport.EMBJavaSupport_ANNOTTYPE_STAMP, nStampInfo);
			arrIndexList.add(nIndex);

			EMBJavaSupport.FPDFStampInfoRelease(nStampInfo);
			break;
		}

		case EMBJavaSupport.EMBJavaSupport_ANNOTTYPE_FILEATTACHMENT: {
			RectangleF rect = new EMBJavaSupport().new RectangleF();
			rect.left = 24.31f;
			rect.top = 623.53f;
			rect.right = 246.97f;
			rect.bottom = 522.76f;
			String filepath = "/mnt/sdcard/FoxitForm.pdf";
			int nFileAttachmentInfo = EMBJavaSupport
					.FPDFFileAttachmentInfoAlloc("James", 0x0000ff, 80, rect,
							filepath);
			int nIndex = EMBJavaSupport.FPDFAnnotAdd(nPDFCurPageHandler,
					EMBJavaSupport.EMBJavaSupport_ANNOTTYPE_FILEATTACHMENT,
					nFileAttachmentInfo);
			arrIndexList.add(nIndex);

			EMBJavaSupport.FPDFFileAttachmentInfoRelease(nFileAttachmentInfo);
			break;
		}

		default:
			break;
		}
		/*
		 * int filewrite = EMBJavaSupport .FSFileWriteAlloc(
		 * "/data/data/com.foxitsample.annotations/FoxitSaveAnnotation.pdf");
		 * EMBJavaSupport.FPDFDocSaveAs(nPDFDocHandler,
		 * EMBJavaSupport.EMBJavaSupport_SAVEFLAG_INCREMENTAL, 0, filewrite);
		 * EMBJavaSupport.FSFileWriteRelease(filewrite);
		 */
		return EMBJavaSupport.EMBJavaSupport_RESULT_SUCCESS;
	}

	public int deleteFileAttachment(int x, int y) {
		int annot_index = EMBJavaSupport.FPDFAnnotGetIndexAtPos(
				nPDFCurPageHandler, x, y);
		int nRet = EMBJavaSupport.FPDFAnnotDelete(nPDFCurPageHandler,
				annot_index);

		return nRet;
	}

	public int deleteAnnot() {
		int nSize = arrIndexList.size();
		if (nSize == 0) {
			return 0;
		}
		int annot_index = arrIndexList.get(nSize - 1);
		int nRet = EMBJavaSupport.FPDFAnnotDelete(nPDFCurPageHandler,
				annot_index);
		arrIndexList.remove(nSize - 1);
		return nRet;
	}

	public int getCurPSIHandle() {
		return nPSIHandle;
	}

}