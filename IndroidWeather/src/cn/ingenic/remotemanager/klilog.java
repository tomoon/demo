package cn.ingenic.remotemanager;

import android.util.Log;

class klilog {
	private final static String TAG = "remote manager";
	public static void i(String msg){
		Log.i(TAG, msg);
	}
	public static void e(String msg){
		Log.e(TAG, msg);
	}
}
