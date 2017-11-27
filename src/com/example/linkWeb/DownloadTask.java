package com.example.linkWeb;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.net.InterfaceAddress;
import java.net.MalformedURLException;
import java.util.HashMap;

import com.iflytek.cloud.thirdparty.ay;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;

public class DownloadTask{
	private HashMap<String, SoftReference<Drawable>> imageCache;
	private Drawable drawable;
	private Callback callback;
	private String path;
	public DownloadTask()
	{
		imageCache=new HashMap<String, SoftReference<Drawable>>();
	}
	public Drawable loadDrawble(String url ,Callback callback)
	{
		this.callback=callback;
		path=url;
		if(url==null)
			return null;
		//Log.i("url",url);
		if (imageCache.containsKey(url)) {  
            SoftReference<Drawable> softReference = imageCache.get(url);  
            Drawable drawable = softReference.get();  
            if (drawable != null) {  
                return drawable;  
            }  
        }
		DownLoad download=new DownLoad();
		download.execute(url);
		return null;
	}
	
	public interface Callback
	{
		public void imagecache(Drawable drawble);
	}
	private class DownLoad extends AsyncTask<String, Integer, Drawable>
	{

		@Override
		protected Drawable doInBackground(String... params) {
			// TODO Auto-generated method stub
			String url=params[0];
			InputStream is=null;
			try {
				is = new java.net.URL(url).openStream();
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            Bitmap bp= BitmapFactory.decodeStream(is);
            try {
				is.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            BitmapDrawable bd = new BitmapDrawable(bp);  
            return (Drawable)bd;
		}
		@Override
		protected void onPostExecute(Drawable result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			drawable=result;
			imageCache.put(path,new SoftReference<Drawable>(result));
			callback.imagecache(drawable);
		}
		
		
	}

}
