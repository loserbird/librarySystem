package com.bingqin.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import sun.misc.BASE64Encoder;

public class MD5Util {
	/**
	 * 实现MD5数据加密
	 * @param password
	 * @return
	 */
    public static String MD5Encode(String password) {
    	MessageDigest md;
    	String result = null;
		try {
			md = MessageDigest.getInstance("MD5");
			BASE64Encoder encoder = new BASE64Encoder();
			result = encoder.encode(md.digest(password.getBytes("utf-8")));
		} catch(Exception e) {
			e.printStackTrace();
		}
		return result;
		
    }
}
