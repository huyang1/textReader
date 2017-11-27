package com.huyang.aaa;
/**
 * 该activity主要为书城activity
 */
import java.util.ArrayList;
import java.util.List;
import com.example.adapterAndListener.ClassifyBookAdapter;

import com.example.adapterAndListener.MyAddBookAdapter;
import com.example.adapterAndListener.bookstoreAdapter;

import com.example.bean.BookStore_book;
import com.example.bean.book;
import com.example.bean.user;
import com.example.linkWeb.Download;
import com.example.myView.XListView;
import com.example.myView.XListView.XListViewListener;
import com.google.gson.Gson;

import android.R.integer;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.TimedText;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.widget.Toolbar;
import android.text.format.Time;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

public class BookStoreActivity extends Activity implements OnClickListener{

	private List<View> listviews;
    private Context context;
    private XListView listview ;
    private ImageView loadstatue;
    private int screenWidth;
    private Toolbar toolbar;
    private static String title[]={"精选","分类","排行"};
    private ViewPager mPager;
    private ImageView selection;
    private TextView selection_word;
    private ImageView classify;
    private TextView classify_word;
    private ImageView rank;
    private TextView rank_word;
    private SearchView searchView;
    private int count=1;
    private bookstoreAdapter madapter;
    private ArrayList<book> dataBitmap=new ArrayList<book>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.bookstore);
		DisplayMetrics dpMetrics = new DisplayMetrics();
		getWindow().getWindowManager().getDefaultDisplay()
				.getMetrics(dpMetrics);
		screenWidth = dpMetrics.widthPixels;
		context=BookStoreActivity.this;
		findViewById(R.id.recommention).setOnClickListener(this);
		findViewById(R.id.classify).setOnClickListener(this);
		findViewById(R.id.rank).setOnClickListener(this);
		init();
		searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
			
			@Override
			public boolean onQueryTextSubmit(String query) {
				// TODO Auto-generated method stub
				
				Search search=new Search();
				search.execute(query);
				searchView.setIconified(true);//
				return false;
			}
			@Override
			public boolean onQueryTextChange(String newText) {
				// TODO Auto-generated method stub
				return false;
			}
		});
		mPager = (ViewPager) findViewById(R.id.bookstoreview);
		toolbar=(Toolbar) findViewById(R.id.toolbar);
		loadstatue=(ImageView) findViewById(R.id.loadstatue);
		loadstatue.setVisibility(0);
		listviews = new ArrayList<View>();
		LayoutInflater mInflater = getLayoutInflater();
		View view = mInflater.inflate(R.layout.bookstorelistview, null);
		listview = (XListView) view.findViewById(R.id.listview);
		DownLoad download=new DownLoad();
		download.execute("http://yangstudent.cn:1200/upload/getBook.do");
		listviews.add(view);
		View view1 = mInflater.inflate(R.layout.bookstorelistview, null);
		XListView listview1 = (XListView) view1.findViewById(R.id.listview);
		listview1.setAdapter(new ClassifyBookAdapter(context));
		listview1.setPullRefreshEnable(false);
		listview1.setPullLoadEnable(false);
		listviews.add(view1);
		View view2 = mInflater.inflate(R.layout.rank, null);
		listviews.add(view2);
		
		mPager.setAdapter(new MyAddBookAdapter(listviews));
		mPager.setOnPageChangeListener(new MyOnPageChangeListener());
		mPager.setCurrentItem(0);
	}
	private class DownLoad extends AsyncTask<String, Integer, String> implements XListViewListener
	{
		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			String url=params[0];
			try {
				String data=Download.post(url,String.valueOf(count));
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
	        ArrayList<book> data= gson.fromJson(result, type);
	        if(data==null||data.size()==0)
	        {
	        	Log.i("message","null");
	        	listview.changeFootMessage();
	        }
	        dataBitmap.addAll(0, data);
	        if(count==1)
	        {
	        	Log.i("init","success"+dataBitmap.size());
	        	madapter=new bookstoreAdapter(dataBitmap,context,BookStoreActivity.this);
	        	listview.setAdapter(madapter);
	        	listview.setPullLoadEnable(true);
	        	listview.setXListViewListener(this);
	        }
	        else{
	        	Log.i("change","  "+dataBitmap.size());
	        	madapter.updateView();
		        madapter.notifyDataSetChanged();
	        }
			super.onPostExecute(result);
		}
		@Override
		public void onRefresh() {
			count=1;
			dataBitmap.clear();
			// TODO Auto-generated method stub
			DownLoad download=new DownLoad();
			download.execute("http://yangstudent.cn:1200/upload/getBook.do");
			onLoad();
		}
		@Override
		public void onLoadMore() {
			// TODO Auto-generated method stub
			//此处实现加载数据部分
			count++;
			// TODO Auto-generated method stub
			DownLoad download=new DownLoad();
			download.execute("http://yangstudent.cn:1200/upload/getBook.do");
			listview.stopRefresh();
			listview.stopLoadMore();
		}
		private void onLoad() {
			listview.stopRefresh();
			listview.stopLoadMore();
			Time time=new Time();
			time.setToNow();
			listview.setRefreshTime(time.year+"年"+time.month+"月"+
			time.monthDay+"日:"+time.hour+":"+time.minute+":"+time.second);
		}
	}
	private class Search extends AsyncTask<String, Void, String>
	{
		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			String bookname=params[0];
			try {
				Log.i("bookanme",bookname);
				String data=Download.search("http://yangstudent.cn:1200/upload/getBook.do",bookname);
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
	        Log.i("message" ,result);
	        Gson gson = new Gson();
	        ArrayList<book> data=new ArrayList<book>();
	        try{
	        	data= gson.fromJson(result, type);
	        }catch(Exception ex)
	        {
	        	Toast.makeText(context, "抱歉异常", Toast.LENGTH_LONG).show();
	        }
	        
	        if(data==null||data.size()==0)
	        {
	        	Toast.makeText(context, "抱歉，未找到该图书", Toast.LENGTH_LONG).show();
	        }
	        else{
	        	OnMyClick(data.get(0));
	        }
			super.onPostExecute(result);
		}
		
	}
	public class MyOnPageChangeListener implements OnPageChangeListener {
		public void onPageScrollStateChanged(int state) {
		}
		@Override
		public void onPageScrolled(int position, float offset, int offsetPixels) {
		}
		@Override
		public void onPageSelected(int position) {
			toolbar.setTitle(title[position]);
			if(position==0)
			{
				selection.setBackgroundResource(R.drawable.selection_in);
				selection_word.setTextColor(Color.RED);
				classify.setBackgroundResource(R.drawable.classify_before);
				classify_word.setTextColor(Color.BLACK);
				rank.setBackgroundResource(R.drawable.rank_before);
				rank_word.setTextColor(Color.BLACK);
			}
			else if(position==1)
			{
				selection.setBackgroundResource(R.drawable.selection_before);
				selection_word.setTextColor(Color.BLACK);
				classify.setBackgroundResource(R.drawable.classify_in);
				classify_word.setTextColor(Color.RED);
				rank.setBackgroundResource(R.drawable.rank_before);
				rank_word.setTextColor(Color.BLACK);
			}
			else if(position==2)
			{
				selection.setBackgroundResource(R.drawable.selection_before);
				selection_word.setTextColor(Color.BLACK);
				classify.setBackgroundResource(R.drawable.classify_before);
				classify_word.setTextColor(Color.BLACK);
				rank.setBackgroundResource(R.drawable.rank_in);
				rank_word.setTextColor(Color.RED);
			}
			Log.i("aaa",""+position);
		}
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int action=v.getId();
		switch(action)
		{
		case R.id.recommention :
			 mPager.setCurrentItem(0);
		     break;
		case R.id.classify :
			mPager.setCurrentItem(1);
		     break;
		case R.id.rank :
			mPager.setCurrentItem(2);
		     break;
		}
	}
	private void init()
	{
		selection=(ImageView) findViewById(R.id.selection1);
		selection_word=(TextView) findViewById(R.id.selection2);
		classify=(ImageView) findViewById(R.id.classify1);
		classify_word=(TextView) findViewById(R.id.classify2);
		rank=(ImageView) findViewById(R.id.rank1);
		rank_word=(TextView) findViewById(R.id.rank2);
		searchView=(SearchView) findViewById(R.id.searchView1);
	}
	public  void OnMyClick(book book1)
	{
		Intent intent = new Intent(BookStoreActivity.this, BookInformationActivity.class);
		intent.putExtra("bookName",book1.getBookName());
		intent.putExtra("author",book1.getAuthor());
		intent.putExtra("bookImageUrl",book1.getBookImageUrl());
		intent.putExtra("bookUrl",book1.getBookUrl());
		intent.putExtra("describe",book1.getDescribe());
		intent.putExtra("uploader",book1.getUploader());
		startActivity(intent);// 启动Read   Activity 
	}
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if(keyCode==KeyEvent.KEYCODE_BACK && event.getRepeatCount()==0)
		{
			finish();
			BookStoreActivity.this.setResult(5, getIntent());
			overridePendingTransition(0, R.anim.bookstore_exit);
			return true;
		}
	    return super.onKeyDown(keyCode, event);
	}
}
