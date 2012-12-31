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
	public boolean onAnnotationDelete(int annotationIndex);

	/**
	 * 要在某个点添加注解,一般是弹出对话框
	 * 
	 * @param x
	 * @param y
	 */
	public String onAnnotationAdd(int x, int y);

	/**
	 * 注解已经添加
	 * 
	 * @param annotationIndex
	 *            注解的序号
	 */
	public void annotationAdded(int annotationIndex);
}
