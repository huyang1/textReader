package com.example.aaa;

import java.util.ArrayList;
import java.util.List;

import com.example.DataBase.Book;
import com.example.DataBase.sqliteDataBase;
import com.example.adapterAndListener.listviewAdapter;
import com.example.myView.MyScrollView;



import com.example.myView.myRoundImageView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import android.os.Bundle;

import android.view.View;

import android.view.View.OnClickListener;

import android.widget.*;
public class MainActivity extends Activity{
private ListView gridview;
private ArrayList<String> book_list=new ArrayList<String>();
private ArrayList<String> iconName;
private ArrayList<String> iconPath;
public static Activity main1;
private MyScrollView myScrollView;
private sqliteDataBase database;
private RelativeLayout relativeLayout;
private myRoundImageView myRound;
private myRoundImageView myPhoto;
private Context context;
//public ArrayList<String> iconName = new ArrayList<String>();
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menucontent);
		myScrollView=(MyScrollView) findViewById(R.id.menucontent);
		database=new sqliteDataBase(this);
		//database.deleteDataBase(this);
		ArrayList<String> flag=database.getAllBooksName();
		if(flag.size()==0)
		     database.insert(new Book("import",".."));
		myScrollView=(MyScrollView) findViewById(R.id.menucontent);
		gridview=(ListView) this.findViewById(R.id.ListView);
		main1=this;
		context=this;
		relativeLayout=(RelativeLayout) findViewById(R.id.relativeLayout);
		relativeLayout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent1 = new Intent(MainActivity.this,
						logActivity.class);
		        startActivityForResult(intent1, 1);
			}
		});
		myPhoto=(myRoundImageView) findViewById(R.id.id_img1);
		userPhoto photo=(userPhoto) getApplication();
		myPhoto.setImageBitmap(photo.getBitmap());
		myRound=(myRoundImageView) findViewById(R.id.log_in);
		myRound.setImageBitmap(photo.getBitmap());
		myRound.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				myScrollView.toggle();
			}
		});
        showBookShelf();
        //gridview.setOnItemClickListener(this);
        //gridview.setOnItemLongClickListener(this);
        
		//new_load.setOnClickListener(this);
	}
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
			if(resultCode==1)
			{
				userPhoto photo=(userPhoto) getApplication();
				myPhoto.setImageBitmap(photo.getBitmap());
				myRound.setImageBitmap(photo.getBitmap());
				myRound.setVisibility(View.VISIBLE);
			}
	}
	public void showBookShelf(){
		iconName=new ArrayList<String>();
		iconPath=new ArrayList<String>();
        //iconName.add("daoru");
        //Book books=(Book) getApplicationContext();
        //iconName=books.getBooks();
		iconName=database.getAllBooksName();
		iconPath=database.getAllBooksPath();
        book_list=(ArrayList<String>) getData();
        ArrayList<ArrayList<String>> book=new ArrayList<ArrayList<String>>();
        for(int i=0;i<=(book_list.size()-1)/3;i++)
        {
        	if(i==book_list.size()/3)
        	{
        		ArrayList<String> temp=new ArrayList<String>();
        		for(int j=3*i;j<book_list.size();j++)
        		{
        			temp.add(book_list.get(j));
        		}
        		book.add(temp);
        		book.add(new ArrayList<String>());
        	}
        	else
        	{
        		ArrayList<String> temp=new ArrayList<String>();
        		for(int j=3*i;j<3*i+3;j++)
        		{
        			temp.add(book_list.get(j));
        		}
        		book.add(temp);
        		book.add(new ArrayList<String>());
        	}
        }
        gridview.setAdapter(new listviewAdapter(context,book));
	}
	public List<String> getData(){        
        
		ArrayList<String> book_list1=new ArrayList<String>();
        for(int i=(iconName.size()-1);i>=0;i--){
        	String name=iconName.get(i);
        	if(name.length()>=8)
        		name=name.substring(0, 8)+"...";
            book_list1.add(name);
        }
        return book_list1;
    }
	
	public void onmyClick(int position)
	{
		if(position!=iconName.size()-1)
        {
        	Intent intent =new Intent(MainActivity.this,reader.class);
        	sqliteDataBase database=new sqliteDataBase(this);
        	intent.putExtra("data",iconPath.get(iconPath.size()-position-1)/*books.getPath(position)*/);
        	startActivity(intent);//Æô¶¯Activity
        }
        else
        {
        	Intent intent=new Intent(MainActivity.this, importActivity.class);
			startActivity(intent);
        }
	}
	public void onMyClickLong(int position)
	{
		if(position!=iconName.size()-1)
	    {
	    	database.delete(new Book(iconName.get(iconName.size()-position-1),iconPath.get(iconPath.size()-position-1)));
	    }
		showBookShelf();
	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		Toast.makeText(this, "Mainactivity destroy", Toast.LENGTH_LONG).show();
	}
	

}
