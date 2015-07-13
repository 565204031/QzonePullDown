package com.sskgg.gg.utils;

import android.util.Log;
/**
 * 日志帮助类
 * @author 杀死凯 QQ565204031
 * 
 */
public class LogUtils {

	//设置为false日志不打印
	public static boolean isDebug;
	
	public static void myPrintln(int priority,String tag,String msg)
	{
		if(isDebug)
		{
			Log.println(priority, tag, msg);
		}
	}
	public static void i(String tag,String msg)
	{
		if(isDebug)
		{
			Log.i(tag, msg);
		}
	}
	public static void d(String tag,String msg)
	{
		if(isDebug)
		{
			Log.d(tag, msg);
		}
	}
	public static void w(String tag,String msg)
	{
		if(isDebug)
		{
			Log.w(tag, msg);
		}
	}
}
