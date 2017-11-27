package com.example.bean;

public class Page {
	private int pageStart=0;
	private int pageEnd=0;
	public Page(int pageStart,int pageEnd) {
		super();
		// TODO Auto-generated constructor stub
		this.pageStart=pageStart;
		this.pageEnd=pageEnd;
	}
	public Page()
	{
		this.pageStart=0;
		this.pageEnd=0;
	}
	public int getPageStart() {
		return pageStart;
	}
	public void setPageStart(int pageStart) {
		this.pageStart = pageStart;
	}
	public int getPageEnd() {
		return pageEnd;
	}
	public void setPageEnd(int pageEnd) {
		this.pageEnd = pageEnd;
	}
	public int getPageSize()
	{
		return this.pageEnd-this.pageStart;
	}

}
