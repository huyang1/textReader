package com.example.aaa;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.app.Application;

public class Book extends Application{
	private  ArrayList<Map<String,String>> iconName=new ArrayList<Map<String,String>>();
	public Book()
	{
		Map<String,String> map=new HashMap<String, String>();
		map.put("name","µº»Î");
		map.put("path", "..");
		iconName.add(map);
	}
	public ArrayList<String> getBooks()
	{
		ArrayList<String> temp=new ArrayList<String>();
		for(int i=0;i<iconName.size();i++)
			temp.add(iconName.get(i).get("name"));
		return temp;
	}
	public String getPath(int i)
	{
		return iconName.get(i).get("path");
	}
	public void add(String name,String path)
	{
		Map<String,String> map=new HashMap<String, String>();
		map.put("name",name);
		map.put("path",path);
		iconName.add(map);
	}
}
