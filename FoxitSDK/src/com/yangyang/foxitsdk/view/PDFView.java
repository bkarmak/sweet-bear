package com.yangyang.foxitsdk.view;

import java.util.ArrayList;

import FoxitEMBSDK.EMBJavaSupport;
import FoxitEMBSDK.EMBJavaSupport.PointF;
import FoxitEMBSDK.EMBJavaSupport.RectangleF;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.OnDoubleTapListener;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.ViewTreeObserver.OnPreDrawListener;

import com.yangyang.foxitsdk.exception.memoryException;
import com.yangyang.foxitsdk.service.YYPDFDoc;
import com.yangyang.foxitsdk.service.YYPDFDoc.AnnotationType;
import com.yangyang.foxitsdk.service.YYPDFDoc.Mode;
import com.yangyang.foxitsdk.util.ZoomStatus;

public class PDFView extends SurfaceView implements Callback, Runnable,
		OnGestureListener, OnDoubleTapListener, OnGlobalLayoutListener {

	private SurfaceHolder Holder;
	private Rect rect = null;
	private Bitmap pdfbmp = null;
	private Bitmap dirtydib = null;
	private Bitmap CurrentBitmap = null;
	private int nDisplayWidth = 0;
	private int nDisplayHeight = 0;
	private Thread mViewThread = null;
	private ArrayList<CPSIAction> mPSIActionList;
	private YYPDFDoc pDoc;
	GestureDetector detector;
	private ZoomStatus zoomStatus;
	private int nStartX = 0;
	private int nStartY = 0;
	private int nCurDisplayX = 0;
	private int nCurDisplayY = 0;
	private int leftBound, topBound;// 左边界，上边界
	private final static int FLING_SIZE = 200;
	private final static int VELOCITYLIMIT = 2000;

	public class CPSIAction {
		public int nActionType;
		public float x;
		public float y;
		public float nPressures;
		public int flag;
	}

	private int mode;
	private AnnotationType annotationType = AnnotationType.NONE;

	public PDFView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		this.init();
	}

	public PDFView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.init();
	}

	public PDFView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.init();
	}

	private void init() {
		Holder = this.getHolder();
		Holder.addCallback(this);
		setFocusable(true);
		setFocusableInTouchMode(true);
		detector = new GestureDetector(this);
		detector.setOnDoubleTapListener(this);
		mPSIActionList = new ArrayList<CPSIAction>();
		this.getViewTreeObserver().addOnGlobalLayoutListener(this);
		mViewThread = new Thread(this);
		mViewThread.start();
	}

	public void updateViewMode(int mode) {
		if (this.mode == Configuration.ORIENTATION_PORTRAIT
				&& this.nDisplayHeight < this.nDisplayWidth
				|| this.mode == Configuration.ORIENTATION_LANDSCAPE
				&& this.nDisplayHeight > this.nDisplayWidth) {
			int temp = this.nDisplayHeight;
			this.nDisplayHeight = this.nDisplayWidth;
			this.nDisplayWidth = temp;
			this.zoomStatus.updateDisplaySize(this.nDisplayWidth,
					this.nDisplayHeight);
			this.showCurrentPage();
		}
	}

	/*	*//**
	 * 改变控件模式
	 * 
	 * @param mode
	 */
	/*
	 * public void changeMode(int mode) { this.mode = mode;
	 * EMBJavaSupport.FPDFFormFillUpdatForm(pDoc.getPDFFormHandler()); //
	 * this.pDoc.updateMode(mode); this.showCurrentPage(); }
	 */

	float baseValue, last_x, last_y;

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		if (this.detector.onTouchEvent(event)) {
			return true;
		}
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			baseValue = 0;
			last_x = event.getRawX();
			last_y = event.getRawY();
		} else if (event.getAction() == MotionEvent.ACTION_MOVE) {
			if (event.getPointerCount() == 2) {
				float x = event.getX(0) - event.getX(1);
				float y = event.getY(0) - event.getY(1);
				float value = (float) Math.sqrt(x * x + y * y);// 计算两点的距离
				if (baseValue == 0) {
					baseValue = value;
				} else {
					if (value - baseValue >= 10 || value - baseValue <= -10) {
						float scale = (value - baseValue) / (baseValue * 20);// 当前两点间的距离除以手指落下时两点间的距离就是需要缩放的比例。
						this.zoomStatus.nextZoom(scale);
						this.rect = null;
						this.showCurrentPage();
						return true;
					}
				}
			} else if (event.getPointerCount() == 1) {
				float x = event.getRawX();
				float y = event.getRawY();
				x -= last_x;
				y -= last_y;
				if (x >= 10 || y < 10 || x <= -10 || y <= -10) {
					return this.SetMartix(x, y); // 移动图片位置
				}
				last_x = event.getRawX();
				last_y = event.getRawY();

			}
		} else if (event.getAction() == MotionEvent.ACTION_UP) {

		}
		if ((this.mode & Mode.Form.getType()) > 0) {

			int actionType = event.getAction() & MotionEvent.ACTION_MASK;
			int actionId = event.getAction()
					& MotionEvent.ACTION_POINTER_ID_MASK;
			actionId = actionId >> 8;

			PointF point = EMBJavaSupport.instance.new PointF();
			point.x = event.getX() + nStartX;
			point.y = event.getY() + nStartY;
			EMBJavaSupport.FPDFPageDeviceToPagePointF(
					pDoc.getCurrentPageHandler(), 0, 0, zoomStatus.getWidth(),
					zoomStatus.getHeight(), 0, point);
			Log.i("pdfview", "x:" + point.x + ",y:" + point.y);
			if (point.x >= 0 || point.y >= 0) {
				switch (actionType) {
				case MotionEvent.ACTION_MOVE://
					if (EMBJavaSupport.FPDFFormFillOnMouseMove(
							pDoc.getPDFFormHandler(),
							pDoc.getCurrentPageHandler(), 0, point.x, point.y))
						return true;
					break;
				case MotionEvent.ACTION_DOWN: //
					if (EMBJavaSupport.FPDFFormFillOnMouseMove(
							pDoc.getPDFFormHandler(),
							pDoc.getCurrentPageHandler(), 0, point.x, point.y))
						return true;
					EMBJavaSupport.FPDFFormFillOnLButtonDown(
							pDoc.getPDFFormHandler(),
							pDoc.getCurrentPageHandler(), 0, point.x, point.y);
					break;
				case MotionEvent.ACTION_UP: //
					if (EMBJavaSupport.FPDFFormFillOnLButtonUp(
							pDoc.getPDFFormHandler(),
							pDoc.getCurrentPageHandler(), 0, point.x, point.y))
						return true;
					break;
				}
			}

		} else if ((this.mode & Mode.Annotation.getType()) > 0) {
			if (true)
				return false;
			RectangleF rect = EMBJavaSupport.instance.new RectangleF();
			rect.left = event.getX() - 10;
			rect.right = event.getX() + 10;
			rect.top = event.getY() - 10;
			rect.bottom = event.getY() + 10;
			rect.left = rect.left >= 0 ? rect.left : 0;
			rect.right = rect.right >= 0 ? rect.right : 0;
			rect.top = rect.top >= 0 ? rect.top : 0;
			rect.bottom = rect.bottom >= 0 ? rect.bottom : 0;
			this.addNote(AnnotationType.NOTE, rect);
			return true;

		} else if ((this.mode & Mode.PSI.getType()) > 0) {

		}
		return false;
	}

	private void addNote(AnnotationType annotationType, RectangleF rect) {
		switch (annotationType) {
		case NOTE:
			try {
				pDoc.addAnnot(annotationType, rect);
			} catch (memoryException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		default:
			break;
		}
	}

	public void InitView(YYPDFDoc pDoc, int pageWidth, int pageHeight,
			int displayWidth, int displayHeight) {
		this.pDoc = pDoc;
		this.mode = pDoc.getMode();
		// this.nDisplayWidth = displayWidth;
		// this.nDisplayHeight = displayHeight;
		this.zoomStatus = new ZoomStatus(pageWidth, pageHeight, displayWidth,
				displayHeight);
		this.leftBound = this.getLeft();
		this.topBound = this.getTop();
		Log.i("pdfview", "left:" + this.leftBound + ",top:" + this.topBound);
	}

	public int getCurrentPage() {
		return pDoc.getCurrentPage();
	}

	public int getCurrentPageHandler() {
		return pDoc.getCurrentPageHandler();
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
		this.rect = null;
		this.showCurrentPage();
	}

	public void nextPage() {
		pDoc.nextPage();
		this.rect = null;
		this.showCurrentPage();
	}

	public void showCurrentPage() {
		this.setPDFBitmap(pDoc.getPageBitmap(zoomStatus.getWidth(),
				zoomStatus.getHeight()));
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

	public boolean SetMartix(float CurrentoffsetX, float CurrentoffsetY) {
		boolean result = true;
		if (pdfbmp == null
				|| (pdfbmp.getWidth() <= nDisplayWidth && pdfbmp.getHeight() <= nDisplayHeight))
			return false;
		nStartX = nCurDisplayX - (int) CurrentoffsetX;
		nStartY = nCurDisplayY - (int) CurrentoffsetY;
		if (nStartX > (pdfbmp.getWidth() - nDisplayWidth)) {
			nStartX = (int) (pdfbmp.getWidth() - nDisplayWidth);
			result = false;
		}
		if (nStartX < 0) {
			result = false;
			nStartX = 0;
		}
		if (nStartY > (pdfbmp.getHeight() - nDisplayHeight)) {
			result = false;
			nStartY = (int) (pdfbmp.getHeight() - nDisplayHeight);
		}
		if (nStartY < 0) {
			result = false;
			nStartY = 0;
		}
		nCurDisplayX = nStartX;
		nCurDisplayY = nStartY;
		Log.i("pdfview", "startx:" + nStartX + ",starty:" + nStartY + "width:"
				+ (pdfbmp.getWidth() - nStartX));
		if (CurrentBitmap != null && !CurrentBitmap.isRecycled()) {
			CurrentBitmap = null;
		}
		CurrentBitmap = Bitmap.createBitmap(pdfbmp, nStartX, nStartY,
				pdfbmp.getWidth() - nStartX, pdfbmp.getHeight() - nStartY);
		this.OnDraw();
		return result;
	}

	public void setPDFBitmap(Bitmap dib) {
		if (pdfbmp != null) {
			pdfbmp.recycle();
			pdfbmp = null;
		}
		if (dirtydib != null) {
			dirtydib.recycle();
			dirtydib = null;
		}
		pdfbmp = dib;
		CurrentBitmap = dib;
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
				canvas.drawColor(0xffffff);
				canvas.drawRect(0, 0, nDisplayWidth, nDisplayHeight, mPaint);
				canvas.drawBitmap(this.CurrentBitmap, mt, mPaint);
				if (this.CurrentBitmap != this.pdfbmp) {
					this.CurrentBitmap.recycle();
					this.CurrentBitmap = null;
				}
			}
			if (dirtydib != null) {
				Matrix m = new Matrix();
				m.postRotate(0, rect.width() / 2, rect.height() / 2);
				m.postTranslate(rect.left - nStartX, rect.top - nStartY);
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
				Log.e("xxxxxxxxxx run run run", "" + pDoc.getCurPSIHandle()
						+ action.x + action.y + action.nPressures + action.flag);
				EMBJavaSupport.FPSIAddPoint(pDoc.getCurPSIHandle(), action.x,
						action.y, action.nPressures, action.flag);
			}
		}
	}

	@Override
	public boolean onDown(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		// TODO Auto-generated method stub
		// if (true)
		// return false;
		Log.i("pdfview", "p1(x:" + e1.getX() + ",y:" + e1.getY() + "),p2(x:"
				+ e2.getX() + ",y:" + e2.getY() + ") vx:" + velocityX + ",vy:"
				+ velocityY);
		if (e1.getX() - e2.getX() > FLING_SIZE && velocityX < -VELOCITYLIMIT) {
			this.nextPage();
			return true;
		} else if (e1.getX() - e2.getX() < -FLING_SIZE
				&& velocityX > VELOCITYLIMIT) {
			this.previousPage();
			return true;
		}
		return false;
	}

	private void openLink(int x, int y) {
		PointF point = new EMBJavaSupport().new PointF();
		point.x = x + nStartX - leftBound;
		point.y = y + nStartY - topBound;
		if (point.x < 0 || point.y < 0)
			return;
		Log.i("pdfview", "screenx:" + (point.x) + ",screeny:" + (point.y));
		int textPage = EMBJavaSupport.FPDFTextLoadPage(pDoc
				.getCurrentPageHandler());
		EMBJavaSupport.FPDFPageDeviceToPagePointF(pDoc.getCurrentPageHandler(),
				0, 0, zoomStatus.getWidth(), zoomStatus.getHeight(), 0, point);
		String url = EMBJavaSupport.FPDFLinkOpenOuterLink(textPage,
				(int) point.x, (int) point.y);
		Log.i("link", url);
		if (url.length() > 0
				&& (url.startsWith("http") || url.startsWith("HTTP")
						|| url.startsWith("HTTPS") || url.startsWith("https"))) {
			Intent it = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
			new Activity().startActivity(it);

		} else {
			int pageNumber = EMBJavaSupport.FPDFLinkOpenInnerLink(
					pDoc.getDocumentHandler(), pDoc.getCurrentPageHandler(),
					(int) point.x, (int) point.y);
			if (pageNumber > 0) {
				pDoc.gotoPage(pageNumber);
				this.showCurrentPage();
			}
		}
		EMBJavaSupport.FPDFTextCloseTextPage(textPage);
	}

	@Override
	public void onLongPress(MotionEvent e) {
		// TODO Auto-generated method stub
		if (this.zoomStatus != null) {
			this.openLink((int) e.getX(), (int) e.getY());
		}
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

	@Override
	public boolean onSingleTapConfirmed(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onDoubleTap(MotionEvent e) {
		// TODO Auto-generated method stub
		// if (this.zoomStatus != null) {
		// this.zoomStatus.nextZoom(0);
		// this.showCurrentPage();
		// return true;
		// }
		return false;
	}

	@Override
	public boolean onDoubleTapEvent(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

	public void invalidate(float left, float top, float right, float bottom) {
		// TODO Auto-generated method stub
		if ((this.mode & Mode.Form.getType()) > 0) {
			int l, t, r, b;
			RectangleF rect = new EMBJavaSupport().new RectangleF();
			rect.left = left;
			rect.top = top;
			rect.right = right;
			rect.bottom = bottom;
			EMBJavaSupport.FPDFPagePageToDeviceRectF(
					pDoc.getCurrentPageHandler(), 0, 0, zoomStatus.getWidth(),
					zoomStatus.getHeight(), 0, rect);
			l = (int) rect.left;
			t = (int) rect.top;
			r = (int) rect.right;
			b = (int) rect.bottom;
			if (l < 0 || t < 0 || r < 0 || b < 0)
				return;
			Rect rc = new Rect(l, t, r, b);
			this.setDirtyRect(l, t, r, b);
			this.setDirtyBitmap(pDoc.getDirtyBitmap(rc, zoomStatus.getWidth(),
					zoomStatus.getHeight()));
			this.OnDraw();
		}
	}

	@Override
	public void onGlobalLayout() {
		// TODO Auto-generated method stub
		this.getViewTreeObserver().removeGlobalOnLayoutListener(this);
		this.nDisplayWidth = this.getWidth();
		this.nDisplayHeight = this.getHeight();
		this.leftBound = this.getLeft();
		this.topBound = this.getTop();
		Log.i("pdfview data", "left:" + this.leftBound + ",top :"
				+ this.topBound + ",width:" + this.nDisplayWidth + ",height:"
				+ this.nDisplayHeight);
	}
}