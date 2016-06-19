package com.example.aaa;

import java.io.File;
import java.util.ArrayList;   
import java.util.HashMap;   
import android.app.Activity;   
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
    private ArrayList<HashMap<String,String>> myArrayList;
   
	 
    @Override   
    public void onCreate(Bundle savedInstanceState) {   
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.loadmainview);   
        
        myListView=(ListView)findViewById(R.id.myListView);
         
    	showDir(ROOT_PATH);
         
        //添加点击事件   
        myListView.setOnItemClickListener(new OnItemClickListener(){   
            @Override   
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,   
                    long arg3) {   
                //获得选中项的HashMap对象   
                HashMap<String,String> map=(HashMap<String,String>)myListView.getItemAtPosition(arg2);   
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
                    	Book books=(Book) getApplicationContext();
                    	books.add(title);
                    	if(end=="txt")
                    		Toast.makeText(getApplicationContext(),"添加成功 " ,Toast.LENGTH_SHORT).show();   
                    	else
                    		Toast.makeText(getApplicationContext(),"添加成功" ,Toast.LENGTH_SHORT).show();
                    	Intent intent=new Intent(loadActivity.this, MainActivity.class);
            			startActivity(intent);	
                    }	
                    else
                    	Toast.makeText(getApplicationContext(),"无法打开 " ,Toast.LENGTH_SHORT).show();
                }
                else
                	Toast.makeText(getApplicationContext(),"没有权限进行此操作" ,Toast.LENGTH_SHORT).show();
            }      
        });   
    }
    protected void showDir(String str){
    	myArrayList=new ArrayList<HashMap<String,String>>();
    	File file=new File(str);
		File[] files = file.listFiles();
		for (int i=0;i<files.length;i++){
			HashMap<String, String> map = new HashMap<String, String>(); 
			map.put("name",files[i].getName());
			map.put("path",files[i].getPath());
			myArrayList.add(map);      
	   }
		SimpleAdapter mySimpleAdapter=new SimpleAdapter(this,   
                myArrayList,//数据源   
                R.layout.list_item,//ListView内部数据展示形式的布局文件listitem.xml   
                new String[]{"name"},//HashMap中的两个key值 itemTitle和itemContent   
                new int[]{R.id.itemTitle});/*布局文件listitem.xml中组件的id    
                                                            布局文件的各组件分别映射到HashMap的各元素上，完成适配*/   
        myListView.setAdapter(mySimpleAdapter);  
    }
}  

