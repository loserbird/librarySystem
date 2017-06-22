package com.bingqin.util;

import java.util.UUID;
/**
 * 上传图片工具类
 * @author 蔡炳钦
 *
 */
public class UploadUtil {
	/**
	 * 处理浏览器差异问题
	 */
	public static String subFileName(String fileName){
		int index = fileName.lastIndexOf("\\");
		if(index!=-1){
			return fileName.substring(index+1);
		}
		return fileName;
	}
	/**
	 * 获得随机uuid文件名
	 * @param fileName
	 * @return
	 */
	public static String randomFileName(String fileName){
		String ext = fileName.substring(fileName.lastIndexOf("."));
		return UUID.randomUUID().toString()+ext;
	}
	/**
	 * 利用hashcode随机生成二层目录
	 * @param fileName
	 * @return
	 */
	public static String randomDir(String fileName){
		int hcode = fileName.hashCode();
		String hex = Integer.toHexString(hcode);
		return "/"+hex.charAt(0)+"/"+hex.charAt(1);
	}
}
