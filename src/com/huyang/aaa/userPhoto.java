package com.huyang.aaa;

import java.util.ArrayList;

import com.example.bean.BookStore_book;
import com.example.extra.AnimationToRead;

import android.R.integer;
import android.app.Application;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

public  class userPhoto extends Application{
	private Bitmap bitmap;
	private String username;
	private String petName;
	private String pwd;
    private String sex;
    private String brith;
    private String country;
	private String imageurl;
    private boolean logstatue;
    private int fontsize=0;
    private int fonttype=0;
    private int pagecolor=0;
	
	public void onCreate()
	{
		bitmap=BitmapFactory.decodeResource(getResources(), R.drawable.log);
		username="unknow";
		pwd=null;
		petName="unknow";
		sex="unknow";
		brith="unknow";
		country="unknow";
		ArrayList<Object> temp=new ArrayList<Object>();
		temp.add(R.drawable.temp);
		temp.add(R.drawable.temp);
		temp.add(R.drawable.temp);
		temp.add(R.drawable.temp);
		temp.add(R.drawable.temp);
		
		logstatue=false;
		super.onCreate();
	}
	
    
	public int getFontsize() {
		return fontsize;
	}


	public void setFontsize(int fontsize) {
		this.fontsize = fontsize;
	}


	public int getFonttype() {
		return fonttype;
	}


	public void setFonttype(int fonttype) {
		this.fonttype = fonttype;
	}


	public int getPagecolor() {
		return pagecolor;
	}


	public void setPagecolor(int pagecolor) {
		this.pagecolor = pagecolor;
	}


	public void clear()
	{
		bitmap=BitmapFactory.decodeResource(getResources(), R.drawable.log);
		username="unknow";
		pwd=null;
		petName="unknow";
		sex="unknow";
		brith="unknow";
		country="unknow";
	}
	public Bitmap getBitmap() {
		return bitmap;
	}
    
	public String getPetName() {
		return petName;
	}

	public void setPetName(String petName) {
		this.petName = petName;
	}

	public String getImageurl() {
		return imageurl;
	}

	public void setImageurl(String imageurl) {
		this.imageurl = imageurl;
	}

	public void setBitmap(Bitmap bitmap) {
		this.bitmap = bitmap;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	
	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getBrith() {
		return brith;
	}

	public void setBrith(String brith) {
		this.brith = brith;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public boolean isLogstatue() {
		return logstatue;
	}

	public  void setLogstatue(boolean logstatue) {
		this.logstatue = logstatue;
	}
	
}
