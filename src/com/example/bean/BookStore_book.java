package com.example.bean;

import android.graphics.Bitmap;

public class BookStore_book {
	private Bitmap bp;
	private String bookName;
	private String bookUrl;
	private int resid;
	public BookStore_book(String bookName,String bookUrl,int resid)
	{
		this.bookName=bookName;
		this.bookUrl=bookUrl;
		this.bp=bp;
		this.resid=resid;
	}
	
	public int getResid() {
		return resid;
	}

	public void setResid(int resid) {
		this.resid = resid;
	}

	public Bitmap getBp() {
		return bp;
	}
	public void setBp(Bitmap bp) {
		this.bp = bp;
	}
	public String getBookName() {
		return bookName;
	}
	public void setBookName(String bookName) {
		this.bookName = bookName;
	}
	public String getBookUrl() {
		return bookUrl;
	}
	public void setBookUrl(String bookUrl) {
		this.bookUrl = bookUrl;
	}
	

}
