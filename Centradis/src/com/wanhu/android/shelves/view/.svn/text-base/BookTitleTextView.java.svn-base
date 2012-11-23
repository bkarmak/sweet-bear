package com.wanhu.android.shelves.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.TextView;

public class BookTitleTextView extends TextView {

	private Paint mStrokePaint;

	public BookTitleTextView(Context context) {
		super(context);
		init();
	}

	public BookTitleTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public BookTitleTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	private void init() {
		mStrokePaint = new Paint();
		mStrokePaint.setARGB(255, 107, 106, 106);
	}

	@Override
	public void draw(Canvas canvas) {

		canvas.drawText(getText().toString(), 0, 0, mStrokePaint);
		super.draw(canvas);
	}

}
