package com.relitu.tools;

import com.relitu.domain.PHIS_HOUSE_KUANXUAN;

public class GetLocation {
	public static PHIS_HOUSE_KUANXUAN getLocationFromAction(String str, PHIS_HOUSE_KUANXUAN ad) {
		if (str == null || !str.contains("\"status\":0"))// 出错返回
			return null;
		// 将返回的字符串进行分割M,提取经纬度
		String s = "," + "\"" + "lat" + "\"" + ":";
		String strs[] = str.split(s, 2);
		String s1 = "\"" + "lng" + "\"" + ":";
		String a[] = strs[0].split(s1, 2);
		ad.setLNG(a[1]);
		s1 = "}" + "," + "\"";
		String a1[] = strs[1].split(s1, 2);
		ad.setLAT(a1[0]);
		return ad;
	}
}
