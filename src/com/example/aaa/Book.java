package com.example.aaa;

import java.util.ArrayList;

import android.app.Application;

public class Book extends Application{
	private  ArrayList<String> iconName = new ArrayList<String>();
	public Book()
	{
		iconName.add("µº»Î");
	}
	public ArrayList<String> getBooks()
	{
		return iconName;
	}
	public void add(String str)
	{
		iconName.add(str);
	}
}
