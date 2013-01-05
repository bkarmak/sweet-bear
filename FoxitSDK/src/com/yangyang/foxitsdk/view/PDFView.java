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
import android.support.v4.app.NotificationCompat.Action;
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
	private int displayWidth = 0;
	private int displayHeight = 0;
	private Thread viewThread = null;
	private ArrayList<CPSIAction> psiActionList;
	private YYPDFDoc doc;
	GestureDetector detector;
	private ZoomStatus zoomStatus;
	private int startX = 0;
	private int startY = 0;
	private int curDisplayX = 0;
	private int curDisplayY = 0;
	private int leftBound, topBound;// 左边界，上边界
	private final static int FLING_SIZE = 200;
	private final static int VELOCITYLIMIT = 2000;
	Paint paint = new Paint();
	IAnnotationListener annotationListener;

	public class CPSIAction {
		public int nActionType;
		public float x;
		public float y;
		public float nPressures;
		public int flag;
	}

	private int mode; // 默认只读模式
	private AnnotationType annotationType = AnnotationType.NONE;// 默认无注解类型

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
		psiActionList = new ArrayList<CPSIAction>();
		this.getViewTreeObserver().addOnGlobalLayoutListener(this);
		viewThread = new Thread(this);
		viewThread.start();
	}

	public void updateViewMode(int mode) {
		if (this.mode == Configuration.ORIENTATION_PORTRAIT
				&& this.displayHeight < this.displayWidth
				|| this.mode == Configuration.ORIENTATION_LANDSCAPE
				&& this.displayHeight > this.displayWidth) {
			int temp = this.displayHeight;
			this.displayHeight = this.displayWidth;
			this.displayWidth = temp;
			this.zoomStatus.updateDisplaySize(this.displayWidth,
					this.displayHeight);
			this.showCurrentPage();
		}
	}

	public void setAnnotationListener(IAnnotationListener annotationListener) {
		this.annotationListener = annotationListener;
	}

	public void changeMode(int mode) {
		this.mode = mode;
	}

	float baseValue, last_x, last_y;

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		if (this.detector.onTouchEvent(event) || performZoomAndScroll(event)
				|| performFromAction(event) || performAnnotationAction(event)
				|| performPSIAction(event)) {
			return true;
		}
		return false;
	}

	private boolean performPSIAction(MotionEvent event) {
		if ((this.mode & Mode.PSI.getType()) > 0) {
		}
		return false;
	}

	private boolean performAnnotationAction(MotionEvent event) {
		if ((this.mode & Mode.Annotation.getType()) > 0
				&& this.annotationType != AnnotationType.NONE
				&& event.getAction() == MotionEvent.ACTION_DOWN) {
			PointF p = device2PagePointF(event, 0, 0);
			int index = doc.getAnnotationIndex((int) p.x, (int) p.y);
			if (this.annotationType == AnnotationType.ERASER) {
				if (index >= 0 && this.annotationListener != null) {
					this.annotationListener.onAnnotationDelete(index);
					return true;
				}
			} else if (index >= 0 && this.annotationListener != null) {
				this.annotationListener.onAnnotationClick(index);
				return true;
			} else if (this.annotationType == AnnotationType.NOTE
					&& this.annotationListener != null) {
				this.annotationListener.onAnnotationAdd((int) event.getX(),
						(int) event.getY());
				return true;
			}
		}
		return false;
	}

	private PointF device2PagePointF(MotionEvent event, int left, int top) {
		PointF p = EMBJavaSupport.instance.new PointF();
		p.x = event.getX() + startX - left;
		p.y = event.getY() + startY - top;
		EMBJavaSupport.FPDFPageDeviceToPagePointF(this.getCurrentPageHandler(),
				0, 0, zoomStatus.getWidth(), zoomStatus.getHeight(), 0, p);
		return p;
	}

	//
	private boolean performFromAction(MotionEvent event) {
		if ((this.mode & Mode.Form.getType()) > 0) {
			int actionType = event.getAction() & MotionEvent.ACTION_MASK;
			int actionId = event.getAction()
					& MotionEvent.ACTION_POINTER_ID_MASK;
			actionId = actionId >> 8;

			PointF point = EMBJavaSupport.instance.new PointF();
			point.x = event.getX() + startX;
			point.y = event.getY() + startY;
			EMBJavaSupport.FPDFPageDeviceToPagePointF(
					doc.getCurrentPageHandler(), 0, 0, zoomStatus.getWidth(),
					zoomStatus.getHeight(), 0, point);
			Log.i("pdfview", "x:" + point.x + ",y:" + point.y);
			if (point.x >= 0 || point.y >= 0) {
				switch (actionType) {
				case MotionEvent.ACTION_MOVE://
					if (EMBJavaSupport.FPDFFormFillOnMouseMove(
							doc.getPDFFormHandler(),
							doc.getCurrentPageHandler(), 0, point.x, point.y))
						return true;
					break;
				case MotionEvent.ACTION_DOWN: //
					if (EMBJavaSupport.FPDFFormFillOnMouseMove(
							doc.getPDFFormHandler(),
							doc.getCurrentPageHandler(), 0, point.x, point.y))
						return true;
					EMBJavaSupport.FPDFFormFillOnLButtonDown(
							doc.getPDFFormHandler(),
							doc.getCurrentPageHandler(), 0, point.x, point.y);
					break;
				case MotionEvent.ACTION_UP: //
					if (EMBJavaSupport.FPDFFormFillOnLButtonUp(
							doc.getPDFFormHandler(),
							doc.getCurrentPageHandler(), 0, point.x, point.y))
						return true;
					break;
				}
			}

		}
		return false;
	}

	private boolean performZoomAndScroll(MotionEvent event) {
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
						this.recyleBitmap();
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
		return false;
	}

	/**
	 * 设置注释类型
	 * 
	 * @param type
	 * @return
	 */
	public boolean setAnnotationType(AnnotationType type) {
		if ((this.mode & Mode.Annotation.getType()) > 0) {
			this.annotationType = type;
			return true;
		}
		return false;
	}

	/**
	 * 添加注释
	 * 
	 * @param annotationType
	 * @param x
	 * @param y
	 * @return
	 */
	public int addAnnotation(AnnotationType annotationType, int x, int y) {
		switch (annotationType) {
		case NOTE:
			try {
				RectangleF rect = EMBJavaSupport.instance.new RectangleF();
				rect.left = x - 10 + startX;
				rect.right = x + 10 + startY;
				rect.top = y - 10 + startX;
				rect.bottom = y + 10 + startY;
				// rect.left = 100;
				// rect.right = 120;
				// rect.top = 100;
				// rect.bottom = 120;
				rect.left = rect.left >= 0 ? rect.left : 0;
				rect.right = rect.right >= 0 ? rect.right : 0;
				rect.top = rect.top >= 0 ? rect.top : 0;
				rect.bottom = rect.bottom >= 0 ? rect.bottom : 0;
				EMBJavaSupport.FPDFPageDeviceToPageRectF(
						doc.getCurrentPageHandler(), 0, 0,
						zoomStatus.getWidth(), zoomStatus.getHeight(), 0, rect);
				return doc.addAnnot(annotationType, rect);
			} catch (memoryException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		default:
			break;
		}
		return -1;
	}

	/**
	 * 删除注释
	 * 
	 * @param annotationIndex
	 */
	public void deleteAnnotation(int annotationIndex) {
		if (this.doc != null) {
			this.doc.deleteAnnotation(annotationIndex);
			this.showCurrentPage();
		}
	}

	public void InitView(YYPDFDoc pDoc, int pageWidth, int pageHeight,
			int displayWidth, int displayHeight) {
		this.doc = pDoc;
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
		return doc.getCurrentPage();
	}

	public int getCurrentPageHandler() {
		return doc.getCurrentPageHandler();
	}

	public int getPageCount() {
		return doc.getPageCounts();
	}

	public int getPageHandler(int pageNumber) {
		return doc.getPageHandler(pageNumber);
	}

	public void gotoPage(int pageNumber) {
		doc.gotoPage(pageNumber);
		this.showCurrentPage();
	}

	public void previousPage() {
		this.recyleBitmap();
		doc.previoutPage();
		this.rect = null;
		this.showCurrentPage();
	}

	public void nextPage() {
		this.recyleBitmap();
		doc.nextPage();
		this.rect = null;
		this.showCurrentPage();
	}

	public void showCurrentPage() {
		this.recyleBitmap();
		this.setPDFBitmap(doc.getPageBitmap(zoomStatus.getWidth(),
				zoomStatus.getHeight()));
		this.OnDraw();
	}

	public void pause() {

	}

	public void resume() {

	}

	public YYPDFDoc getDoc() {
		return this.doc;
	}

	public void finalize() {
		if (doc != null)
			doc.close();
	}

	public synchronized void addAction(int nActionType, float x, float y,
			float nPressures, int flag) {
		CPSIAction action = new CPSIAction();
		action.nActionType = nActionType;
		action.x = x;
		action.y = y;
		action.nPressures = nPressures;
		action.flag = flag;

		psiActionList.add(action);
	}

	public synchronized CPSIAction getHeadAction() {
		CPSIAction action = null;
		if (psiActionList == null)
			return null;

		int nSize = psiActionList.size();
		if (nSize <= 0)
			return null;

		action = psiActionList.remove(0);
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
		recyleDirtyBitmap();
		dirtydib = dib;
	}

	public boolean SetMartix(float CurrentoffsetX, float CurrentoffsetY) {
		boolean result = true;
		if (pdfbmp == null
				|| (pdfbmp.getWidth() <= displayWidth && pdfbmp.getHeight() <= displayHeight))
			return false;
		startX = curDisplayX - (int) CurrentoffsetX;
		startY = curDisplayY - (int) CurrentoffsetY;
		if (startX > (pdfbmp.getWidth() - displayWidth)) {
			startX = (int) (pdfbmp.getWidth() - displayWidth);
			result = false;
		}
		if (startX < 0) {
			result = false;
			startX = 0;
		}
		if (startY > (pdfbmp.getHeight() - displayHeight)) {
			result = false;
			startY = (int) (pdfbmp.getHeight() - displayHeight);
		}
		if (startY < 0) {
			result = false;
			startY = 0;
		}
		curDisplayX = startX;
		curDisplayY = startY;
		recyleCurrentBitmap();
		recyleDirtyBitmap();
		CurrentBitmap = Bitmap.createBitmap(pdfbmp, startX, startY,
				pdfbmp.getWidth() - startX, pdfbmp.getHeight() - startY);
		this.OnDraw();
		return result;
	}

	private void recyleBitmap() {
		this.rect = null;
		recyleCurrentBitmap();
		recylePDFBitmap();
		recyleDirtyBitmap();
	}

	private void recyleDirtyBitmap() {
		if (dirtydib != null && !dirtydib.isRecycled()) {
			dirtydib.recycle();
			dirtydib = null;
		}
	}

	private void recylePDFBitmap() {
		if (pdfbmp != null && !pdfbmp.isRecycled()) {
			pdfbmp = null;
		}
	}

	private void recyleCurrentBitmap() {
		if (CurrentBitmap != null && !CurrentBitmap.isRecycled()) {
			CurrentBitmap = null;
		}
	}

	public void setPDFBitmap(Bitmap dib) {
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
			if (pdfbmp != null && rect == null) {
				Matrix mt = new Matrix();
				mt.postRotate(0, displayWidth, displayHeight);
				mt.postTranslate(0, 0);
				canvas.drawColor(0xffffff);
				canvas.drawRect(0, 0, displayWidth, displayHeight, paint);
				canvas.drawBitmap(this.CurrentBitmap, mt, paint);
			}
			if (dirtydib != null && rect != null) {
				canvas.drawBitmap(dirtydib, rect.left - startX, rect.top
						- startY, paint);
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
				Log.e("xxxxxxxxxx run run run", "" + doc.getCurPSIHandle()
						+ action.x + action.y + action.nPressures + action.flag);
				EMBJavaSupport.FPSIAddPoint(doc.getCurPSIHandle(), action.x,
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
		if ((this.mode & Mode.Annotation.getType()) > 0
				&& this.annotationType == AnnotationType.PENCIL) {
			if (e2.getAction() == MotionEvent.ACTION_UP) {
				try {
					PointF p1 = this.device2PagePointF(e1, this.leftBound,
							this.topBound);
					PointF p2 = this.device2PagePointF(e2, this.leftBound,
							this.topBound);
					int index = doc.addAnnot(AnnotationType.PENCIL,
							new float[] { p1.x, p1.y, p2.x, p2.y });
					if (index >= 0) {
						this.showCurrentPage();
						return true;
					}
				} catch (memoryException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			return false;
		} else {
			Log.i("pdfview", "p1(x:" + e1.getX() + ",y:" + e1.getY()
					+ "),p2(x:" + e2.getX() + ",y:" + e2.getY() + ") vx:"
					+ velocityX + ",vy:" + velocityY);
			if (e1.getX() - e2.getX() > FLING_SIZE
					&& velocityX < -VELOCITYLIMIT) {
				this.nextPage();
				return true;
			} else if (e1.getX() - e2.getX() < -FLING_SIZE
					&& velocityX > VELOCITYLIMIT) {
				this.previousPage();
				return true;
			}
		}
		return false;
	}

	private void openLink(int x, int y) {
		PointF point = new EMBJavaSupport().new PointF();
		point.x = x + startX - leftBound;
		point.y = y + startY - topBound;
		if (point.x < 0 || point.y < 0)
			return;
		Log.i("pdfview", "screenx:" + (point.x) + ",screeny:" + (point.y));
		int textPage = EMBJavaSupport.FPDFTextLoadPage(doc
				.getCurrentPageHandler());
		EMBJavaSupport.FPDFPageDeviceToPagePointF(doc.getCurrentPageHandler(),
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
					doc.getDocumentHandler(), doc.getCurrentPageHandler(),
					(int) point.x, (int) point.y);
			if (pageNumber > 0) {
				doc.gotoPage(pageNumber);
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
	public boolean onDoubleTap(MotionEvent event) {
		// TODO Auto-generated method stub
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
					doc.getCurrentPageHandler(), 0, 0, zoomStatus.getWidth(),
					zoomStatus.getHeight(), 0, rect);
			l = (int) rect.left;
			t = (int) rect.top;
			r = (int) rect.right;
			b = (int) rect.bottom;
			if (l < 0 || t < 0 || r < 0 || b < 0)
				return;
			Rect rc = new Rect(l, t, r, b);
			this.setDirtyRect(l, t, r, b);
			this.setDirtyBitmap(doc.getDirtyBitmap(rc, zoomStatus.getWidth(),
					zoomStatus.getHeight()));
			this.OnDraw();
		}
	}

	@Override
	public void onGlobalLayout() {
		// TODO Auto-generated method stub
		this.getViewTreeObserver().removeGlobalOnLayoutListener(this);
		this.displayWidth = this.getWidth();
		this.displayHeight = this.getHeight();
		this.leftBound = this.getLeft();
		this.topBound = this.getTop();
		Log.i("pdfview data", "left:" + this.leftBound + ",top :"
				+ this.topBound + ",width:" + this.displayWidth + ",height:"
				+ this.displayHeight);
	}
}