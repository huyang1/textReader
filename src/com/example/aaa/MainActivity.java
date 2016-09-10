package com.example.aaa;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.example.DataBase.Book;
import com.example.DataBase.sqliteDataBase;
import com.example.myView.MyScrollView;

import com.example.myView.myAdapter;
import com.example.myView.myRoundImageView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.*;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;

public class MainActivity extends Activity implements OnItemClickListener,OnItemLongClickListener{
private GridView gridview;
private ArrayList<String> book_list;
private ArrayList<String> iconName;
private ArrayList<String> iconPath;
public static Activity main1;
private MyScrollView myScrollView;
private sqliteDataBase database;
private Bundle tempSavedInstanceState;
private RelativeLayout relativeLayout;
private myRoundImageView myRound;
private Context context;
//public ArrayList<String> iconName = new ArrayList<String>();
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menucontent);
		myScrollView=(MyScrollView) findViewById(R.id.menucontent);
		tempSavedInstanceState=savedInstanceState;
		database=new sqliteDataBase(this);
		//database.deleteDataBase(this);
		ArrayList<String> flag=database.getAllBooksName();
		if(flag.size()==0)
		     database.insert(new Book("import",".."));
		myScrollView=(MyScrollView) findViewById(R.id.menucontent);
		gridview=(GridView) this.findViewById(R.id.gridview);
		main1=this;
		context=this;
		relativeLayout=(RelativeLayout) findViewById(R.id.relativeLayout);
		relativeLayout.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				Intent intent1=new Intent(MainActivity.this, logActivity.class);
		        startActivity(intent1);
				return false;
			}
		});
		myRound=(myRoundImageView) findViewById(R.id.log_in);
		myRound.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				myScrollView.toggle();
			}
		});
        showBookShelf();
        gridview.setOnItemClickListener(this);
        gridview.setOnItemLongClickListener(this);
        
		//new_load.setOnClickListener(this);
	}
	public void showBookShelf(){
		iconName=new ArrayList<String>();
		iconPath=new ArrayList<String>();
        //iconName.add("daoru");
        //Book books=(Book) getApplicationContext();
        //iconName=books.getBooks();
		iconName=database.getAllBooksName();
		iconPath=database.getAllBooksPath();
        getData();
        
        String [] from ={"image"};
        int [] to = {R.id.bookimage};
        gridview.setAdapter(new myAdapter(context, book_list));
	}
	public List<String> getData(){        
        
		book_list=new ArrayList<String>();
        for(int i=(iconName.size()-1);i>=0;i--){
        	String name=iconName.get(i);
        	if(name.length()>=8)
        		name=name.substring(0, 8)+"...";
            book_list.add(name);
        }
        return book_list;
    }
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		//Book books=(Book) getApplicationContext();
		
        Toast.makeText(getApplicationContext(),iconPath.get(iconPath.size()-position-1),
                Toast.LENGTH_SHORT).show();
        if(position!=(iconName.size()-1))
        {
        	Intent intent =new Intent(MainActivity.this,reader.class);
        	sqliteDataBase database=new sqliteDataBase(this);
        	intent.putExtra("data",iconPath.get(iconPath.size()-position-1)/*books.getPath(position)*/);
        	startActivity(intent);//Æô¶¯Activity
        }
        else
        {
        	Intent intent=new Intent(MainActivity.this, loadActivity.class);
			startActivity(intent);
        }
	}
	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view,
			int position, long id) {
		// TODO Auto-generated method stub
		    if(position!=iconName.size()-1)
		    {
		    	database.delete(new Book(iconName.get(iconName.size()-position-1),iconPath.get(iconPath.size()-position-1)));
		    }
		    onCreate(tempSavedInstanceState);
		return false;
	}
}
