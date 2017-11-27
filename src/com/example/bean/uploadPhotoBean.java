package com.example.bean;

import java.util.Map;

import com.example.linkWeb.FormFile;

public class uploadPhotoBean {
	private String url;
	private Map<String, String> params;
	private FormFile file;
	public uploadPhotoBean()
	{
		this.file=null;
		this.params=null;
		this.url=null;
	}
	public uploadPhotoBean(String url,Map<String, String> params,FormFile file)
	{
		this.file=file;
		this.params=params;
		this.url=url;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Map<String, String> getParams() {
		return params;
	}
	public void setParams(Map<String, String> params) {
		this.params = params;
	}
	public FormFile getFile() {
		return file;
	}
	public void setFile(FormFile file) {
		this.file = file;
	}
	

}
