package com.huyang.aaa;

import java.util.ArrayList;

import com.example.DataBase.sqliteDataBase;
import com.example.adapterAndListener.galleryAdapter;
import com.example.bean.book;
import com.example.extra.Utility;
import com.example.myView.myRoundImageView;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;

public class MyHomePage extends Activity {
	private sqliteDataBase database;
	private sqliteDataBase downloadBookSet;
	private GridView gridview;
    private ArrayList<book> data=new ArrayList<book>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.myhomepage);
		database=new sqliteDataBase(this);
		downloadBookSet=new sqliteDataBase(this);
		myRoundImageView myphoto=(myRoundImageView) findViewById(R.id.myRoundImageView1);
		userPhoto user = (userPhoto) getApplication();
		myphoto.setImageBitmap(user.getBitmap());
		TextView title=(TextView) findViewById(R.id.textView2);
		title.setText(user.getPetName());
		findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				MyHomePage.this.finish();
			}
		});
		((TextView)findViewById(R.id.PostReadBook)).setText(""+(database.getAllBooksName().size()-1));
		((TextView)findViewById(R.id.downloadNum)).setText(""+downloadBookSet.getAllBooksName_download().size());
		gridview=(GridView) findViewById(R.id.downloadbook);
		initData();
		gridview.setAdapter(new galleryAdapter(this, data));
		Utility.setGridViewHeightBasedOnChildren(gridview);
	}
	private void initData()
	{
		for(int i=0;i<downloadBookSet.getAllBooksName_download().size();i++)
		{
			book temp=new book();
			temp.setBookName(downloadBookSet.getAllBooksName_download().get(i));
			temp.setBitmap(downloadBookSet.getAllBooksImage().get(i));
			temp.setDate(downloadBookSet.getAllBookDate().get(i));
			data.add(temp);
		}
	}
	

}
