package com.yangyang.foxitsdk.view;

import android.content.Intent;

public interface IPDFView {

	public int getCurrentPageHandler();// 获取当前PDP文档当前页的页柄(get the current page
										// handler of the current PDP)
	public void createAndroidTextField(String focusText);// 当前页输入框需要输入时弹出自定义输入框

	public void invalidate(float left, float top, float right, float bottom);// 刷新当前页的部分缓冲区

	public int getPageHandler(int pageNumber);// 获取特定页面的页柄(get the special page
												// handler of a doc)

	public void onActivityResult(int requestCode, int resultCode, Intent data);
}
