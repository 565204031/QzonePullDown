package com.sskgg.gg.utils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.os.Environment;
import android.support.v4.util.LruCache;

/**
 * 图片缓存帮助类
 * @author 杀死凯 QQ565204031
 * 
 */
public class LruCacheUtils {

	
	 // 缓存Image的类，当存储Image的大小大于LruCache设定的值，系统自动释放内存 
    private static LruCache<String, Bitmap> mMemoryCache;  
    public final static String SD_PATH = Environment.getExternalStorageDirectory().getAbsolutePath().toString(); 
    
    private static void initLruCache(){
    	 int maxMemory = (int) Runtime.getRuntime().maxMemory();    
         int mCacheSize = maxMemory / 8;  
         mMemoryCache=new LruCache<String, Bitmap>(mCacheSize){
			
			 //必须重写此方法，来测量Bitmap的大小  
			@Override
			protected int sizeOf(String key, Bitmap value) {
				  return value.getRowBytes() * value.getHeight(); 
			}
			
		};
    }
	 /** 
     * 添加Bitmap到内存缓存 
     * @param key 
     * @param bitmap
     * @author 杀死凯 QQ565204031 
     */  
    public static void addBitmapToMemoryCache(String key, Bitmap bitmap) {    
        if (getBitmapFromMemCache(key) == null && bitmap != null) { 
        	if(mMemoryCache==null)
        	{
        		initLruCache();
        	}
            mMemoryCache.put(key, bitmap);    
            
        }    
    }    
       
    /** 
     * 从内存缓存中获取一个Bitmap 
     * @param key 
     * @return 
     * @author 杀死凯 QQ565204031
     */  
    public static Bitmap getBitmapFromMemCache(String key) {   
    	if(mMemoryCache==null)
    	{
    		initLruCache();
    	}
        return mMemoryCache.get(key);    
    }  
     /**
      * 
      * @param 流
      * @param 图片MD5值
      * @return
      * @author 杀死凯 QQ565204031
      */
	public static Bitmap getBitmap(byte[] avatar, String filename) {
		try {
			ByteArrayInputStream bais = new ByteArrayInputStream(avatar);
			// 判断目录是否存在，没有则创建
			File file = new File(SD_PATH + "/sskgg");
			if (!file.exists()) {
				file.mkdir();
			}
			// 下载图片到sd卡
			FileOutputStream os = new FileOutputStream(SD_PATH + "/sskgg/"
					+ filename);
			int len = 0;
			byte[] buffer = new byte[1024];
			// 返回-1表示读完
			while ((len = bais.read(buffer)) != -1) {
				os.write(buffer, 0, len);
			}
			bais.close();
			os.close();
			return getZoomBitmap(SD_PATH + "/sskgg/"+ filename,200,200);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 
	 * @param SD卡路径
	 * @param 压缩宽度
	 * @return 压缩后的图片
	 * @author 杀死凯 QQ565204031
	 */
	public static Bitmap getZoomBitmap(String path,int reqWidth,int reqHeight) {
		//原图
		//Bitmap bitmap = BitmapFactory.decodeFile(path);
		BitmapFactory.Options options=new Options();
		//如果为true，options.outWidth 返回图片宽度，图片不会加载到内存
		options.inJustDecodeBounds=true;
		BitmapFactory.decodeFile(path,options);
		
		//压缩图片比例
		int zoomimageWidth=options.outWidth/reqWidth;
		int zoomimageHeight=options.outHeight/reqHeight;
		options.inJustDecodeBounds=false;
		//按照比例大的来缩放
		options.inSampleSize=zoomimageWidth>zoomimageHeight?zoomimageWidth:zoomimageHeight;
		
		Bitmap bitmap=BitmapFactory.decodeFile(path,options);
		//将Bitmap 加入内存缓存  
		addBitmapToMemoryCache(path, bitmap);  
		//内存缓存获得图片
		return getBitmapFromMemCache(path);
	}
	/**
	 * @param 图片MD5
	 * @return 返回null表示SD卡无图
	 * @author 杀死凯 QQ565204031
	 */
	public static Bitmap SDloadImage(String name){
		return getZoomBitmap(SD_PATH+"/sskgg/"+name,200,220);
	}
}
