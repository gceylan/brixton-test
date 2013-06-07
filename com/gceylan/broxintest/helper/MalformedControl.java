package com.gceylan.broxintest.helper;

public class MalformedControl {

	public static boolean isMalformedGrupAdi(String grupAdi) {
		return grupAdi.length() == 0;
	}

	public static boolean testSekliAdIsMalformed(String testSekliAd) {
		return testSekliAd.length() == 0;
	}
	

}
