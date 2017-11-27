package com.example.bean;

import java.util.ArrayList;

public class Chapter {
	private ArrayList<Page> page;
	public Chapter()
	{
		page=new ArrayList<Page>();
	}
	public Page getPage(int pos)
	{
		if(pos>=page.size())
			return null;
		else
			return page.get(pos);
	}
	public void addPage(Page page)
	{
		this.page.add(page);
	}
	public int getSize()
	{
		return page.size();
	}

	

}
