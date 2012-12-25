package com.yangyang.foxitsdk.util;

/**
 * 缩放控制
 * 
 * @author yangyang
 * 
 */
public class ZoomStatus {

	// //////////////////////////////////////////////////////////////
	private final static float MINSCALE = 0.5f;// 最小缩放比
	private final static float MAXSCALE = 2f;// 最大缩放比
	private final static byte ST_NORMAL = 0;// 原始模式
	private final static byte ST_FITWIDTH = 1;// 宽度满屏
	private final static byte ST_FITHEIGHT = 2;// 高度满屏
	private final static byte ST_FITSCREEN = 3;// 全屏
	private final static byte ST_SCALE = 4;// 自定义缩放

	// //////////////////////////////////////////////////////////////
	private float scaleX, scaleY;// 水平，竖直方向的缩放比例
	private int displayWidth, displayHeight;// 屏幕的宽高
	private int width, height;// 默认的宽高
	private byte status;

	public ZoomStatus(int width, int height, int displayWidth, int displayHeight) {
		this.width = width;
		this.height = height;
		this.scaleX = this.scaleY = 1f;
		this.displayWidth = displayWidth;
		this.displayHeight = displayHeight;
	}

	public void updateDisplaySize(int displayWidth, int displayHeight) {
		this.displayWidth = displayWidth;
		this.displayHeight = displayHeight;
	}

	public void nextZoom(float rate) {
		if (rate != 0)
			status = ST_SCALE;
		else
			status = (byte) ((++status) % 4);
		switch (status) {
		case ST_NORMAL:
			this.scaleX = this.scaleY = 1;
			break;
		case ST_FITWIDTH:
			this.scaleY = this.scaleX = (float) displayWidth / this.width;
			break;
		case ST_FITHEIGHT:
			this.scaleX = this.scaleY = (float) displayHeight / this.height;
			break;
		case ST_FITSCREEN:
			this.scaleX = (float) displayWidth / this.width;
			this.scaleY = (float) displayHeight / this.height;
			break;
		case ST_SCALE:
			this.scaleX += rate;
			this.scaleY += rate;
			if (this.scaleX > MAXSCALE)
				this.scaleX = MAXSCALE;
			if (this.scaleY > MAXSCALE)
				this.scaleY = MAXSCALE;
			if (this.scaleX < MINSCALE)
				this.scaleX = MINSCALE;
			if (this.scaleY < MINSCALE)
				this.scaleY = MINSCALE;
		}
	}

	public int getDisplayWidth() {
		return this.displayWidth;
	}

	public int getDisplayHeight() {
		return this.displayHeight;
	}

	public float getScaleX() {
		return this.scaleX;
	}

	public float getScaleY() {
		return this.scaleY;
	}

	public int getWidth() {
		return (int) (this.width * scaleX);
	}

	public int getHeight() {
		return (int) (this.height * scaleY);
	}
}
