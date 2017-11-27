package com.huyang.aaa;

import java.util.ArrayList;
import java.util.HashMap;

import com.example.DataBase.sqliteDataBase;
import com.example.adapterAndListener.SendseekbarData;
import com.example.myView.dialogSetting;
import com.example.myView.readPage;

import android.R.integer;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class reader extends Activity {
	private boolean touchToNext = false;
	private boolean click;
	private int pageWidth;
	private int pageHeight;
	private double starX = 0, starY = 0, endX = 0, endY = 0;
	private Context context;
	private dialogSetting dialogset;
	private Intent intent;
	private readPage readpage;
	private ArrayList<String> chapters=new ArrayList<String>();
	private int chapter=0;
	private int page=1;
	private String sb="";
	private int BrightTYPE=1;
	private static String TAG="reader";
	private int fontsize=0;
	private int fonttype=0;
	private int pagecolor=0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		intent = getIntent();
		super.onCreate(savedInstanceState);
		chapters=new sqliteDataBase(this).getChapters(intent.getStringExtra("name"));
		//requestWindowFeature(Window.FEATURE_NO_TITLE);
		// 设置全屏
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		readpage = new readPage(this);
		context = this;
		userPhoto data=(userPhoto) this.getApplication();
		
		fonttype=data.getFonttype();
		fontsize=data.getFontsize();
		pagecolor=data.getPagecolor();
		ArrayList<Integer> message=(new sqliteDataBase(this)).getImformation(intent.getStringExtra("name"));
		if(message.size()!=0)
		{
			chapter=message.get(0);
			page=message.get(1);
		}
		
		Resources resources = context.getResources();
		DisplayMetrics dm = resources.getDisplayMetrics();
		pageWidth = dm.widthPixels;
		pageHeight = dm.heightPixels;
		sb=(new sqliteDataBase(context)).getBookChapterContent(chapters.get(chapter),intent.getStringExtra("name"));
		readpage.getContent(sb,chapters.get(chapter), page,BrightTYPE,fontsize,fonttype,pagecolor);
	    setContentView(readpage);
		readpage.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub

				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					click = true;
					endX = starX = event.getX();
					endY = starY = event.getY();
					if (endX > 2 * pageWidth / 5 && endX < 3 * pageWidth / 5
							&& endY > 2 * pageHeight / 5
							&& endY < 3 * pageHeight / 5) {
						click = false;
						// 当用户进行设置时
						View popView = LayoutInflater.from(context).inflate(
								R.layout.chatper, null);
						ListView chatperListview = (ListView) popView
								.findViewById(R.id.ChatperListView);
						SimpleAdapter mySimpleAdapter = new SimpleAdapter(
								context, getChatper(), R.layout.chatper_item,
								new String[] { "chatper" },
								new int[] { R.id.chatperitem });
						chatperListview.setAdapter(mySimpleAdapter);
						chatperListview
								.setOnItemClickListener(new OnItemClickListener() {
									@Override
									public void onItemClick(
											AdapterView<?> arg0, View arg1,
											int arg2, long arg3) {
										// TODO Auto-generated method stub
										dialogset.hidechapter();
										page=1;
										sb=(new sqliteDataBase(context)).getBookChapterContent(chapters.get(arg2),intent.getStringExtra("name"));
										readpage.getContent(sb,chapters.get(arg2), page,BrightTYPE,fontsize,fonttype,pagecolor);
										chapter=arg2;
										readpage.postInvalidate();
									}
								});
						String name = intent.getStringExtra("name");
						if (name.contains(".")) {
							int temp = name.indexOf(".");
							name = name.substring(0, temp);
						}
						dialogset = new dialogSetting(context, popView, name);
						dialogset.setOnSeekbarData(seekbardata);
						dialogset.show();
						return true;
					}
				}
				if (event.getAction() == MotionEvent.ACTION_MOVE) {
					if (!click)
						return true;
					if (endX > event.getX())
						touchToNext = true;
					else
						touchToNext = false;
					endX = event.getX();
					endY = event.getY();
				}
				if (event.getAction() == MotionEvent.ACTION_UP) {
					if (!click)
						return true;
					if (touchToNext && click)// 后翻
					{
						page++;
						boolean flag=true;
						flag=readpage.getContent(sb,chapters.get(chapter), page,BrightTYPE,fontsize,fonttype,pagecolor);
						if(!flag)
						{
							if(chapter==chapters.size()-1)
								Toast.makeText(context, "阅读已结束", Toast.LENGTH_SHORT).show();
							page=1;
							chapter++;
							sb=(new sqliteDataBase(context)).getBookChapterContent(chapters.get(chapter),intent.getStringExtra("name"));
							readpage.getContent(sb,chapters.get(chapter), page,BrightTYPE,fontsize,fonttype,pagecolor);
						}
					} else// 前翻
					{
						if(page==1)
						{
							if(chapter==0)
								Toast.makeText(context, "该页是第一页", Toast.LENGTH_SHORT).show();
							else
							{
								
								chapter--;
								sb=(new sqliteDataBase(context)).getBookChapterContent(chapters.get(chapter),intent.getStringExtra("name"));
								page=readpage.setContent(sb,chapters.get(chapter), page,BrightTYPE,fontsize,fonttype,pagecolor);
								readpage.setContent(sb,chapters.get(chapter), page,BrightTYPE,fontsize,fonttype,pagecolor);
							}
						}
						else
						{
							page--;
							readpage.setContent(sb,chapters.get(chapter), page,BrightTYPE,fontsize,fonttype,pagecolor);
						}
					}
				}
				readpage.postInvalidate();
				return true;
			}
		});
	}
	private ArrayList<HashMap<String, String>> getChatper() {
		ArrayList<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();
		if (chapters.size() == 0) {
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("chatper", "暂无目录信息...");
			data.add(map);
		}
		for (int chatperPos = 0; chatperPos < chapters.size(); chatperPos++) {
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("chatper", chapters.get(chatperPos));
			data.add(map);
		}
		return data;
	}
	private SendseekbarData seekbardata = new SendseekbarData() {
		@Override
		public void onSeekbarData(int progress) {
			// TODO Auto-generated method stub
			page=readpage.setContent(sb,chapters.get(chapter), page,BrightTYPE,fontsize,fonttype,pagecolor);
			Log.i("reader"," "+page);
			readpage.setContent(sb,chapters.get(chapter), page*progress/100,BrightTYPE,fontsize,fonttype,pagecolor);
			readpage.postInvalidate();
		}

		@Override
		public void changeBright() {
			// TODO Auto-generated method stub
			Log.i("set","BrightTYPE");
			if(BrightTYPE==1)//白天
			{
				BrightTYPE=2;
				readpage.setContent(sb,chapters.get(chapter), page,BrightTYPE,fontsize,fonttype,pagecolor);
				readpage.postInvalidate();
			}
			else
			{
				BrightTYPE=1;
			    readpage.setContent(sb,chapters.get(chapter), page,BrightTYPE,fontsize,fonttype,pagecolor);
			    readpage.postInvalidate();
			}
		}
		@Override
		public void setFontSize(int fontsize) {
			// TODO Auto-generated method stub
			if(reader.this.fontsize!=fontsize)
			{
				readpage.setContent(sb,chapters.get(chapter), page,BrightTYPE,fontsize,fonttype,pagecolor);
				reader.this.fontsize=fontsize;
				readpage.postInvalidate();
			}
		}

		@Override
		public void setFontType(int fonttype) {
			// TODO Auto-generated method stub
			if(reader.this.fonttype!=fonttype)
			{
				readpage.setContent(sb,chapters.get(chapter), page,BrightTYPE,fontsize,fonttype,pagecolor);
				reader.this.fonttype=fonttype;
				readpage.postInvalidate();
			}
		}
		@Override
		public void setPageColor(int pageColor) {
			// TODO Auto-generated method stub
			if(reader.this.pagecolor!=pageColor)
			{
				readpage.setContent(sb,chapters.get(chapter), page,BrightTYPE,fontsize,fonttype,pageColor);
				reader.this.pagecolor=pageColor;
				readpage.postInvalidate();
			}
		}
	};
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		(new sqliteDataBase(this)).insert(intent.getStringExtra("name"), chapter, page);
		userPhoto data=(userPhoto) this.getApplication();
		data.setFontsize(fontsize);
		data.setFonttype(fonttype);
		data.setPagecolor(pagecolor);
		super.onDestroy();
   }

	
}
