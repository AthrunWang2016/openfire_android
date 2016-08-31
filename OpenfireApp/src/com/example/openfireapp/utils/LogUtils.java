package com.example.openfireapp.utils;

import android.util.Log;

public class LogUtils {

	public static boolean isLog = false;
	
	public static void log(String tag,String msg){
		if(isLog){
			Log.i(tag, msg);
		}
	}
	
}
