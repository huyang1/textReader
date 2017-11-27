/**
 *  @Project       : aaa;  
 *  @Program Name  : com.huyang.aaa.BookListActivity.java;
 *  @Class Name    : BookListActivity;
 *  @Description   : 分类时，书籍列表;
 *  @Author        : huyang;
 *  @Creation Date : 2017-3-11 下午7:36:52 ;
 */

package com.huyang.aaa;

import java.util.ArrayList;

import com.example.adapterAndListener.BookListAdapter;
import com.example.bean.book;
import com.example.linkWeb.Download;
import com.example.myView.XListView;
import com.example.myView.XListView.XListViewListener;
import com.google.gson.Gson;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

public class BookListActivity extends Activity implements OnItemClickListener,XListViewListener,OnClickListener{
	private XListView xListView;
	private Intent intent;
    private ArrayList<book> data=new ArrayList<book>();
    private int count=1;
    private  BookListAdapter madapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lable_books);
		xListView=(XListView) findViewById(R.id.listview);
		findViewById(R.id.back).setOnClickListener(this);
		intent=getIntent();
		FormatJson();
		madapter=new BookListAdapter(this, data);
		xListView.setAdapter(madapter);
		xListView.setPullLoadEnable(true);
		xListView.setPullRefreshEnable(true);
		xListView.setOnItemClickListener(this);
		xListView.setXListViewListener(this);
	}
	private void FormatJson()
	{
		java.lang.reflect.Type type = new com.google.gson.reflect.TypeToken<ArrayList<book>>() {  
        }.getType();  
        Gson gson = new Gson();  
        data= gson.fromJson(intent.getStringExtra("data"), type);
	}
	/* (non-Javadoc)
	 * @see android.widget.AdapterView.OnItemClickListener#onItemClick(android.widget.AdapterView, android.view.View, int, long)
	 */
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		book book1=data.get(arg2-1);
		Intent intent = new Intent(BookListActivity.this, BookInformationActivity.class);
		intent.putExtra("bookName",book1.getBookName());
		intent.putExtra("author",book1.getAuthor());
		intent.putExtra("bookImageUrl",book1.getBookImageUrl());
		intent.putExtra("bookUrl",book1.getBookUrl());
		intent.putExtra("describe",book1.getDescribe());
		intent.putExtra("uploader",book1.getUploader());
		startActivity(intent);// 启动  Activity 
		
	}
	/* (non-Javadoc)
	 * @see com.example.myView.XListView.XListViewListener#onRefresh()
	 */
	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub
		count=1;
		data.clear();
		data=new ArrayList<book>();
		// TODO Auto-generated method stub
		Get_Lable_Book getlableBook=new Get_Lable_Book();
		getlableBook.execute("玄幻");
		onLoad();
	}
	/* (non-Javadoc)
	 * @see com.example.myView.XListView.XListViewListener#onLoadMore()
	 */
	@Override
	public void onLoadMore() {
		// TODO Auto-generated method stub
		count++;
		Get_Lable_Book getlableBook=new Get_Lable_Book();
		getlableBook.execute("玄幻");
		onLoad();
		
	}
	private class Get_Lable_Book extends AsyncTask<String, Void, String>
	{
		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			String lable=params[0];
			try {
				Log.i("........","getdata");
				String data=Download.LableBook("http://yangstudent.cn:1200/upload/getBook.do",lable,String.valueOf(count));
				return data;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			java.lang.reflect.Type type = new com.google.gson.reflect.TypeToken<ArrayList<book>>() {  
	        }.getType();  
	        Gson gson = new Gson();  
	        ArrayList<book >temp= gson.fromJson(result, type);
	        if(count!=1)
	        {
	        	
	        	if(temp==null||temp.size()==0)
	        	{
	        		xListView.changeFootMessage();
	        	}
	        	data.addAll(temp);
	        	madapter.setUpdateItem(xListView.getFirstVisiblePosition()+3);
	        	xListView.setSelection(xListView.getFirstVisiblePosition()-5);
	        	madapter.notifyDataSetChanged();
	        }
	        else
	        {
	        	data=gson.fromJson(result, type);
	        	madapter=new BookListAdapter(BookListActivity.this, data);
	        	xListView.setAdapter(madapter);
	        }
	        Log.i("Fresh", data.get(data.size()-1).getBookName()+data.size());
			super.onPostExecute(result);
		}
	}
	private void onLoad() {
		xListView.stopRefresh();
		xListView.stopLoadMore();
		Time time=new Time();
		time.setToNow();
		xListView.setRefreshTime(time.year+"年"+time.month+"月"+
		time.monthDay+"日:"+time.hour+":"+time.minute+":"+time.second);
	}
	/* (non-Javadoc)
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int action=v.getId();
		switch(action)
		{
		     case R.id.back:
		    	 BookListActivity.this.finish();
		    	 break;
			default :
				break;
		
		}
	}

}
