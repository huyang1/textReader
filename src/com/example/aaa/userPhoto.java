package com.example.aaa;

import android.app.Application;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class userPhoto extends Application{
	private Bitmap bitmap;

	public Bitmap getBitmap() {
		return bitmap;
	}

	public void setBitmap(Bitmap bitmap) {
		this.bitmap = bitmap;
	}
	
	public void onCreate()
	{
		bitmap=BitmapFactory.decodeResource(getResources(), R.drawable.log);
		super.onCreate();
	}

}
