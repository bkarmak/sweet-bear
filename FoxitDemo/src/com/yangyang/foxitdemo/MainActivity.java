package com.yangyang.foxitdemo;

import java.util.HashMap;
import java.util.Map;

import FoxitEMBSDK.EMBJavaSupport;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;

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
	private Map<Integer, String> mapIndex2Content = new HashMap<Integer, String>();

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
		case R.id.go_to: {
			if (pDoc != null) {
				this.pdfView.gotoPage(1);
			}
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
	public void annotationAdded(int arg0) {
		// TODO Auto-generated method stub
		if (!this.mapIndex2Content.containsKey(-1)
				|| this.mapIndex2Content.get(-1) == null) {
			Log.i("mainactivity", "annotation error!");
		} else
			this.mapIndex2Content.put(arg0, this.mapIndex2Content.get(-1));
	}

	@Override
	public String onAnnotationAdd(int arg0, int arg1) {
		// TODO Auto-generated method stub
		MessageBox messageBox = new MessageBox(this);
		String content = messageBox.showDialog("Content", "Note");
		this.mapIndex2Content.put(-1, content);
		return content;
	}

	@Override
	public void onAnnotationClick(int arg0) {
		// TODO Auto-generated method stub
		if (this.mapIndex2Content.containsKey(arg0)) {
			MessageBox box = new MessageBox(this);
			String content = box.showDialog(this.mapIndex2Content.get(arg0),
					"Note");
			if (content != null) {
				this.mapIndex2Content.put(arg0, content);
			}
		}
	}

	@Override
	public boolean onAnnotationDelete(int arg0) {
		// TODO Auto-generated method stub
		return false;
	}

}
