package com.yangyang.foxitdemo;

import FoxitEMBSDK.EMBJavaSupport;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;

import com.yangyang.foxitdemo.MessageBox.IMessageBoxResult;
import com.yangyang.foxitdemo.OpenFileDialog.CallbackBundle;
import com.yangyang.foxitsdk.service.YYPDFDoc;
import com.yangyang.foxitsdk.service.YYPDFDoc.AnnotationType;
import com.yangyang.foxitsdk.view.IAnnotationListener;
import com.yangyang.foxitsdk.view.IPDFView;
import com.yangyang.foxitsdk.view.PDFView;

public class MainActivity extends Activity implements IPDFView,
		IAnnotationListener {

	private YYPDFDoc pDoc;
	private PDFView pdfView;
	private int screenWidth, screenHeight;
	private final static int openFileDialogId = 10212739;
	private SparseArray<String> mapIndex2Content = new SparseArray<String>();
	MessageBox messageBox;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
				WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		setContentView(R.layout.activity_main);
		pdfView = (PDFView) this.findViewById(R.id.pdfViewCtrl);
		Display display = getWindowManager().getDefaultDisplay();
		screenWidth = display.getWidth();
		screenHeight = display.getHeight();
		this.messageBox = new MessageBox(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case R.id.open:
			showDialog(openFileDialogId);
			return true;
		case R.id.previous:
			if (pDoc != null) {
				this.pdfView.previousPage();
			}
			break;
		case R.id.next:
			if (pDoc != null) {
				this.pdfView.nextPage();
			}
			break;
		case R.id.Note:
			if (pDoc != null) {
				// this.pdfView.changeMode(Mode.Read);
				this.pdfView.changeMode(3);
				this.pdfView.setAnnotationType(AnnotationType.NOTE);
			}
			break;
		case R.id.Eraser:
			if (pDoc != null) {
				// this.pdfView.changeMode(Mode.Form);
				this.pdfView.changeMode(3);
				this.pdfView.setAnnotationType(AnnotationType.ERASER);
			}
			break;
		case R.id.Read:
			if (pDoc != null) {
				this.pdfView.changeMode(3);
				this.pdfView.setAnnotationType(AnnotationType.NONE);
			}
			break;
		case R.id.Line: {
			if (pDoc != null) {
				this.pdfView.changeMode(3);
				this.pdfView.setAnnotationType(AnnotationType.PENCIL);
				break;
			}
		}
		case R.id.go_to: {
			if (pDoc != null) {
				this.pdfView.gotoPage(1);
			}
			break;
		}
		}
		return super.onMenuItemSelected(featureId, item);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		return this.pdfView.onTouchEvent(event);
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		this.messageBox.dismiss();
		super.onDestroy();
		if (this.pDoc != null)
			this.pDoc.close();
	}

	@Override
	@Deprecated
	public Dialog onCreateDialog(int id) {
		// TODO Auto-generated method stub
		if (id == openFileDialogId) {
			return OpenFileDialog.createDialog(openFileDialogId, this,
					"Open PDF", new CallbackBundle() {
						public void callback(Bundle bundle) {
							// TODO Auto-generated method stub
							String filepath = bundle.getString("path");
							setTitle(filepath); // 把文件路径显示在标题上
							MainActivity.this.pDoc = new YYPDFDoc(filepath, "",
									MainActivity.this, 3);
							MainActivity.this.pdfView.InitView(pDoc,
									(int) pDoc.GetPageSizeX(0),
									(int) pDoc.GetPageSizeY(0),
									MainActivity.this.screenWidth,
									MainActivity.this.screenHeight);
							MainActivity.this.pdfView
									.setAnnotationListener(MainActivity.this);
							MainActivity.this.pdfView.showCurrentPage();
						}
					}, ".pdf;");

		}
		return super.onCreateDialog(id);
	}

	@Override
	public void createAndroidTextField(String arg0) {
		// TODO Auto-generated method stub
		Intent intent = new Intent();
		Bundle bundle = new Bundle();
		bundle.putString("textValue", arg0);
		intent.setClass(this, textfieldActivity.class);
		intent.putExtra("key", bundle);
		this.startActivityForResult(intent, 0);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK && requestCode == 0) {
			Bundle bundle = data.getBundleExtra("Result");
			String text = bundle.getString("ResultValue");
			EMBJavaSupport.FPDFFormFillOnSetText(pDoc.getPDFFormHandler(),
					pDoc.getCurrentPageHandler(), text, 0);
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		// TODO Auto-generated method stub
		if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
			this.pdfView.updateViewMode(newConfig.orientation);
		} else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
			this.pdfView.updateViewMode(newConfig.orientation);
		}
		super.onConfigurationChanged(newConfig);
	}

	@Override
	public int getCurrentPageHandler() {
		// TODO Auto-generated method stub
		return pDoc.getCurrentPageHandler();
	}

	@Override
	public int getPageHandler(int arg0) {
		// TODO Auto-generated method stub
		return pDoc.getPageHandler(arg0);
	}

	@Override
	public void invalidate(float arg0, float arg1, float arg2, float arg3) {
		// TODO Auto-generated method stub
		this.pdfView.invalidate(arg0, arg1, arg2, arg3);
	}

	@Override
	public void onAnnotationAdd(final int arg0, final int arg1) {
		// TODO Auto-generated method stub
		messageBox.showDialog("Content", "Note", new IMessageBoxResult() {
			@Override
			public void onResult(String result) {
				// TODO Auto-generated method stub
				if (result != null) {
					int index = MainActivity.this.pdfView.addAnnotation(
							AnnotationType.NOTE, arg0, arg1);
					MainActivity.this.mapIndex2Content.put(index, result);
					MainActivity.this.pdfView.showCurrentPage();
				}
			}
		});
	}

	@Override
	public void onAnnotationClick(int arg0) {
		// TODO Auto-generated method stub
		if (this.mapIndex2Content.get(arg0) != null) {
			String content = messageBox.showDialog(
					this.mapIndex2Content.get(arg0), "Note", null);
			if (content != null) {
				this.mapIndex2Content.put(arg0, content);
			}
		}
	}

	@Override
	public void onAnnotationDelete(final int arg0) {
		// TODO Auto-generated method stub
		if (this.messageBox.showDialog(this.mapIndex2Content.get(arg0),
				"Delete Note?", new IMessageBoxResult() {

					@Override
					public void onResult(String result) {
						// TODO Auto-generated method stub
						if (result != null) {
							MainActivity.this.mapIndex2Content.remove(arg0);
							MainActivity.this.pdfView.deleteAnnotation(arg0);
							MainActivity.this.pdfView.showCurrentPage();
						}
					}
				}) != null) {

		}
	}

}
