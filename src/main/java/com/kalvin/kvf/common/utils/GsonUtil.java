package com.kalvin.kvf.common.utils;

import com.google.gson.Gson;

public class GsonUtil {

	private static Gson gson = null;
	static {
		if (gson == null) {
			gson = new Gson();
		}
	}

	/**
	 * 
	 * 将对象转换成json 本方法由 LYJ 创建于2019年6月2日 下午3:58:34
	 * 
	 * @param obj
	 * @return String
	 */
	public static String toJson(Object obj) {
		String jsonString = null;
		if (gson != null) {
			jsonString = gson.toJson(obj);
		}
		return jsonString;
	}

	public static <T> T toObject(String jsonString, Class<T> clazz) {
		T t = null;
		if (gson != null) {
			t = gson.fromJson(jsonString, clazz);
		}
		return t;
	}

}
