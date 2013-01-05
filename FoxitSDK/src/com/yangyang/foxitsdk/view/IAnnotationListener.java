package com.yangyang.foxitsdk.view;

public interface IAnnotationListener {

	/**
	 * 注解被点击
	 * 
	 * @param annotationIndex
	 */
	public void onAnnotationClick(int annotationIndex);

	/**
	 * 注解被删除
	 * 
	 * @param annotationIndex
	 */
	public void onAnnotationDelete(int annotationIndex);

	/**
	 * 要在某个点添加注解,一般是弹出对话框
	 * 
	 * @param x
	 * @param y
	 */
	public void onAnnotationAdd(int x, int y);

}
