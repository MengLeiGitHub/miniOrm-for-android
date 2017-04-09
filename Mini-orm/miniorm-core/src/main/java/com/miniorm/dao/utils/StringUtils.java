package com.miniorm.dao.utils;

public class StringUtils {

	/**
	 * Description:
	 * @param s
	 * @return boolean
	 */
	public static boolean isNull(String s) {
		if (s == null || "".equals(s.trim())) {
			return true;
		}

		return false;
	}


}
