package com.yangyang.foxitsdk.view;

import java.util.ArrayList;

import FoxitEMBSDK.EMBJavaSupport;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;

import com.yangyang.foxitsdk.service.WrapPDFFunc;
import com.yangyang.foxitsdk.service.YYPDFDoc;
import com.yangyang.foxitsdk.util.ZoomStatus;

public class PDFView extends SurfaceView implements Callback, Runnable,
		OnGestureListener {

	private SurfaceHolder Holder;
	private Rect rect = null;
	private Bitmap pdfbmp = null;
	private Bitmap dirtydib = null;
	private Bitmap CurrentBitmap = null;
	private int nDisplayWidth = 0;
	private int nDisplayHeight = 0;
	private WrapPDFFunc mFunc = null;
	private Thread mViewThread = null;
	private ArrayList<CPSIAction> mPSIActionList;
	private YYPDFDoc pDoc;
	GestureDetector detector;
	private ZoomStatus zoomStatus;
	private int nStartX = 0;
	private int nStartY = 0;
	private int nCurDisplayX = 0;
	private int nCurDisplayY = 0;

	public class CPSIAction {
		public int nActionType;
		public float x;
		public float y;
		public float nPressures;
		public int flag;
	}

	public PDFView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		Holder = this.getHolder();// ��ȡholder
		Holder.addCallback(this);
		setFocusable(true);
		setFocusableInTouchMode(true);
		detector = new GestureDetector(this);
		mPSIActionList = new ArrayList<CPSIAction>();
		mViewThread = new Thread(this);
		mViewThread.start();
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (this.detector != null)
			return this.detector.onTouchEvent(event);
		return false;
	}

	public void InitView(WrapPDFFunc func, YYPDFDoc pDoc, int pageWidth,
			int pageHeight, int displayWidth, int displayHeight) {
		mFunc = func;
		this.pDoc = pDoc;
		this.nDisplayWidth = displayWidth;
		this.nDisplayHeight = displayHeight;
		this.zoomStatus = new ZoomStatus(pageWidth, pageHeight, displayWidth,
				displayHeight);
	}

	public int getCurrentPage() {
		return pDoc.getCurrentPage();
	}

	public int getCurrentPageHandler() {
		return pDoc.getCurrentPageHandler();
	}

	public Bitmap getPageBitmap(int displayWidth, int displayHeight) {
		return pDoc.getPageBitmap(displayWidth, displayHeight);
	}

	public int getPageCount() {
		return pDoc.getPageCounts();
	}

	public int getPageHandler(int pageNumber) {
		return pDoc.getPageHandler(pageNumber);
	}

	public void gotoPage(int pageNumber) {
		pDoc.gotoPage(pageNumber);
		this.showCurrentPage();
	}

	public void previousPage() {
		pDoc.previoutPage();
		this.showCurrentPage();
	}

	public void nextPage() {
		pDoc.nextPage();
		this.showCurrentPage();
	}

	public void showCurrentPage() {
		this.setPDFBitmap(this.getPageBitmap(nDisplayWidth, nDisplayHeight),
				nDisplayWidth, nDisplayHeight);
		this.OnDraw();
	}

	public void pause() {

	}

	public void resume() {

	}

	public YYPDFDoc getDoc() {
		return this.pDoc;
	}

	public void finalize() {
		if (pDoc != null)
			pDoc.close();
	}

	public synchronized void addAction(int nActionType, float x, float y,
			float nPressures, int flag) {
		CPSIAction action = new CPSIAction();
		action.nActionType = nActionType;
		action.x = x;
		action.y = y;
		action.nPressures = nPressures;
		action.flag = flag;

		mPSIActionList.add(action);
	}

	public synchronized CPSIAction getHeadAction() {
		CPSIAction action = null;
		if (mPSIActionList == null)
			return null;

		int nSize = mPSIActionList.size();
		if (nSize <= 0)
			return null;

		action = mPSIActionList.remove(0);
		return action;
	}

	public void setDirtyRect(int left, int top, int right, int bottom) {
		if (rect == null) {
			rect = new Rect();
		}
		rect.left = left;
		rect.top = top;
		rect.right = right;
		rect.bottom = bottom;
	}

	public void setDirtyRect(Rect rc) {
		rect = rc;
	}

	public void setDirtyBitmap(Bitmap dib) {
		dirtydib = dib;
	}

	public void SetMartix(float CurrentoffsetX, float CurrentoffsetY) {
		nStartX = nCurDisplayX - (int) CurrentoffsetX;
		nStartY = nCurDisplayY - (int) CurrentoffsetY;
		if (nStartX < 0)
			nStartX = 0;
		if (nStartX > (pdfbmp.getWidth() - nDisplayWidth))
			nStartX = (int) (pdfbmp.getWidth() - nDisplayWidth);
		if (nStartY < 0)
			nStartY = 0;
		if (nStartY > (pdfbmp.getHeight() - nDisplayHeight))
			nStartY = (int) (pdfbmp.getHeight() - nDisplayHeight);
		nCurDisplayX = nStartX;
		nCurDisplayY = nStartY;
		CurrentBitmap = Bitmap.createBitmap(pdfbmp, nStartX, nStartY,
				pdfbmp.getWidth() - nStartX, pdfbmp.getHeight() - nStartY);
	}

	public void setPDFBitmap(Bitmap dib, int sizex, int sizey) {
		pdfbmp = dib;
		CurrentBitmap = dib;
		nDisplayWidth = sizex;
		nDisplayHeight = sizey;
	}

	public void OnDraw() {
		Canvas canvas = null;
		try {
			if (rect == null) {
				canvas = Holder.lockCanvas();
			} else {
				canvas = Holder.lockCanvas(rect);
			}
			if (canvas == null)
				return;
			Paint mPaint = new Paint();
			if (pdfbmp != null && rect == null) {
				Matrix mt = new Matrix();
				mt.postRotate(0, nDisplayWidth, nDisplayHeight);
				mt.postTranslate(0, 0);
				canvas.drawBitmap(this.CurrentBitmap, mt, mPaint);
			}
			if (dirtydib != null) {
				Matrix m = new Matrix();
				m.postRotate(0, rect.width() / 2, rect.height() / 2);
				m.postTranslate(rect.left, rect.top);
				canvas.drawBitmap(dirtydib, m, mPaint);
			}
		} finally {
			if (canvas != null) {
				Holder.unlockCanvasAndPost(canvas);
			}
		}
	}

	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub

	}

	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		int count = 0;
		while (count < 2) {
			OnDraw();
			count++;
		}
	}

	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub

	}

	public void run() {
		// TODO Auto-generated method stub
		while (!Thread.currentThread().isInterrupted()) {
			CPSIAction action = getHeadAction();
			if (action != null) {
				Log.e("xxxxxxxxxx run run run", "" + mFunc.getCurPSIHandle()
						+ action.x + action.y + action.nPressures + action.flag);
				EMBJavaSupport.FPSIAddPoint(mFunc.getCurPSIHandle(), action.x,
						action.y, action.nPressures, action.flag);
			}
		}
	}

	@Override
	public boolean onDown(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

	private final static int FLING_SIZE = 120;

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		// TODO Auto-generated method stub
		if (e1.getX() - e2.getX() > FLING_SIZE) {
			this.nextPage();
			return true;
		} else if (e1.getX() - e2.getX() < -FLING_SIZE) {
			this.previousPage();
			return true;
		}
		return false;
	}

	@Override
	public void onLongPress(MotionEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onShowPress(MotionEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

}