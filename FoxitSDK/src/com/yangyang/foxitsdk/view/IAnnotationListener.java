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
}
