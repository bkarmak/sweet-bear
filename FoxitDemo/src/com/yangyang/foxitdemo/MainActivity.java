package com.yangyang.foxitdemo;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager;

import com.yangyang.foxitdemo.OpenFileDialog.CallbackBundle;
import com.yangyang.foxitsdk.service.YYPDFDoc;
import com.yangyang.foxitsdk.view.PDFView;

public class MainActivity extends Activity implements OnTouchListener {

	private YYPDFDoc pDoc;
	private PDFView pdfView;
	private int nDisplayWidth, nDisplayHeight;
	private final static int openFileDialogId = 10212739;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
				WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

		pdfView = new com.yangyang.foxitsdk.view.PDFView(this);
		setContentView(pdfView);
		pdfView.setOnTouchListener(this);

		// code start
		Display display = getWindowManager().getDefaultDisplay();
		nDisplayWidth = display.getWidth();
		nDisplayHeight = display.getHeight();
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
				pDoc.previoutPage();
				this.showCurrentPage();
			}
			break;
		case R.id.next:
			if (pDoc != null) {
				pDoc.nextPage();
				this.showCurrentPage();
			}
			break;
		}
		return super.onMenuItemSelected(featureId, item);
	}

	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		return false;
	}

	private void showCurrentPage() {
		if (this.pDoc != null) {
			this.pdfView.setPDFBitmap(
					this.pDoc.getPageBitmap(nDisplayWidth, nDisplayHeight),
					nDisplayWidth, nDisplayHeight);
		}
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
	protected Dialog onCreateDialog(int id) {
		// TODO Auto-generated method stub
		if (id == openFileDialogId) {
			return OpenFileDialog.createDialog(openFileDialogId, this,
					"Open PDF", new CallbackBundle() {
						public void callback(Bundle bundle) {
							// TODO Auto-generated method stub
							String filepath = bundle.getString("path");
							setTitle(filepath); // 把文件路径显示在标题上
							MainActivity.this.pDoc = new YYPDFDoc(filepath, "");
							MainActivity.this.showCurrentPage();
						}
					}, ".pdf;");
		}
		return super.onCreateDialog(id);
	}

}