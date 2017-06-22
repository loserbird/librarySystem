package com.bingqin.util;

import javax.servlet.http.Cookie;
/**
 * 根据名称查找指定的cookie
 * @author 蔡炳钦
 *
 */
public class CookieUtils {

	public static Cookie findCookieByName(Cookie[] cs, String name) {
		if (cs == null || cs.length == 0) {
			return null;
		}
		for (Cookie c : cs) {
			if (c.getName().equals(name)) {
				return c;
			}
		}
		return null;
	}
}
