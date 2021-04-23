package com.msb.myspringboot.utils;

import java.beans.PropertyEditor;
import java.beans.PropertyEditorManager;

/**
 * 类型工具类
 * 
 * @author Stickerleee
 * @since 2021年4月15日 下午4:08:06
 */
public class ObjectUtils {

	/**
	 * 转换String类型对象为目标类型对象
	 *
	 * @param targetClass 需要转换的目标类型
	 * @param content     String文本对象
	 * @return 转换后的对象
	 */
	public static Object convertToClass(Class<?> targetClass, String content) {
		PropertyEditor editor = PropertyEditorManager.findEditor(targetClass);
		editor.setAsText(content);
		return editor.getValue();
	}
}
