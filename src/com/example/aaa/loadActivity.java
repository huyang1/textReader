package com.example.aaa;

import java.io.File;
import java.util.ArrayList;   
import java.util.HashMap;   

import com.example.DataBase.Book;
import com.example.DataBase.sqliteDataBase;

import android.app.Activity;   
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;   

import android.view.View;   
import android.widget.AdapterView;   
import android.widget.AdapterView.OnItemClickListener;   
import android.widget.ListView;   
import android.widget.SimpleAdapter;   
import android.widget.Toast;   


public class loadActivity extends Activity {   
    /** Called when the activity is first created. */   
    //声明ListView对象   
    private ListView myListView; 
    private static final String ROOT_PATH ="/";
    private ArrayList<HashMap<String,Object>> myArrayList;
    private int [] resource={R.drawable.dir,R.drawable.txt};
    private Context context;
    @Override   
    public void onCreate(Bundle savedInstanceState) {   
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.loadmainview);   
        context=this;
        myListView=(ListView)findViewById(R.id.myListView1);
         
    	showDir(ROOT_PATH);
         
        //添加点击事件   
        /*myListView.setOnItemClickListener(new OnItemClickListener(){   
            @Override   
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,   
                    long arg3) {
            	HashMap<String,String> map=(HashMap<String,String>)myListView.getItemAtPosition(arg2);   
                String path=map.get("path");
                if(map.get("name").equals("返回上一级"))
                {
                	File file=new File(path);
                    showDir(file.getParent());
                }       	
            	else
            	{
            		//获得选中项的HashMap对象   
                    String title=map.get("name");   
                    String content=map.get("path");
                    File file=new File(content);
                    String end = title.substring(title.lastIndexOf(".") + 1, title.length()).toLowerCase();
                    if(file.exists()&& file.canRead())
                    {
                    	if(file.isDirectory())
                        {
                        	//Toast.makeText(getApplicationContext(),"this is Directory "+"path的值是:"+content,Toast.LENGTH_SHORT).show();
                        	showDir(content);
                        }
                        else if(file.isFile())
                        {
                        	/*Book books=(Book) getApplicationContext();
                        	books.add(title,content,resource[1]);*/
                        	/*sqliteDataBase database=new sqliteDataBase(context);
                        	database.insert(new Book(title,content));
                        	
                        	if(end=="txt")
                        		Toast.makeText(getApplicationContext(),"添加成功 " ,Toast.LENGTH_SHORT).show();   
                        	else
                        		Toast.makeText(getApplicationContext(),"添加成功" ,Toast.LENGTH_SHORT).show();
                        	
                        	Intent intent=new Intent(loadActivity.this, MainActivity.class);
                    		startActivity(intent);
                    		finish();
                    		MainActivity.main1.finish();
                        }	
                        else
                        	Toast.makeText(getApplicationContext(),"无法打开 " ,Toast.LENGTH_SHORT).show();
                    }
                    else
                    	Toast.makeText(getApplicationContext(),"没有权限进行此操作" ,Toast.LENGTH_SHORT).show();
            	}
            }      
        }); */  
    } 
    protected void showDir(String str){
    	myArrayList=new ArrayList<HashMap<String,Object>>();
    	if(!str.equals("/"))
    	{
    		HashMap<String, Object> map = new HashMap<String, Object>(); 
			map.put("name","返回上一级");
			map.put("path",str);
			myArrayList.add(map);
    	}
    	File file=new File(str);
		File[] files = file.listFiles();
		for (int i=0;i<files.length;i++){
			if((new File(files[i].getPath())).canRead())
			{
				HashMap<String, Object> map = new HashMap<String, Object>(); 
				map.put("name",files[i].getName());
				map.put("path",files[i].getPath());
				if((new File(files[i].getPath())).isFile())
					map.put("kind",resource[1]);
				else
					map.put("kind",resource[0]);
				myArrayList.add(map);
			}
	   }
		SimpleAdapter mySimpleAdapter=new SimpleAdapter(this,   
                myArrayList,//数据源   
                R.layout.list_item,//ListView内部数据展示形式的布局文件listitem.xml   
                new String[]{"name","kind"},//HashMap中的两个key值 itemTitle和itemContent   
                new int[]{R.id.itemTitle,R.id.image});/*布局文件listitem.xml中组件的id    
                                                            布局文件的各组件分别映射到HashMap的各元素上，完成适配*/   
        myListView.setAdapter(mySimpleAdapter);  
    }
}  

