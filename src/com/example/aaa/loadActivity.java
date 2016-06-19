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
    //����ListView����   
    private ListView myListView; 
    private static final String ROOT_PATH ="/";
    private ArrayList<HashMap<String,String>> myArrayList;
   
	 
    @Override   
    public void onCreate(Bundle savedInstanceState) {   
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.loadmainview);   
        
        myListView=(ListView)findViewById(R.id.myListView);
         
    	showDir(ROOT_PATH);
         
        //��ӵ���¼�   
        myListView.setOnItemClickListener(new OnItemClickListener(){   
            @Override   
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,   
                    long arg3) {   
                //���ѡ�����HashMap����   
                HashMap<String,String> map=(HashMap<String,String>)myListView.getItemAtPosition(arg2);   
                String title=map.get("name");   
                String content=map.get("path");
                File file=new File(content);
                String end = title.substring(title.lastIndexOf(".") + 1, title.length()).toLowerCase();
                if(file.exists()&& file.canRead())
                {
                	if(file.isDirectory())
                    {
                    	//Toast.makeText(getApplicationContext(),"this is Directory "+"path��ֵ��:"+content,Toast.LENGTH_SHORT).show();
                    	showDir(content);
                    }
                    else if(file.isFile())
                    {
                    	Book books=(Book) getApplicationContext();
                    	books.add(title);
                    	if(end=="txt")
                    		Toast.makeText(getApplicationContext(),"��ӳɹ� " ,Toast.LENGTH_SHORT).show();   
                    	else
                    		Toast.makeText(getApplicationContext(),"��ӳɹ�" ,Toast.LENGTH_SHORT).show();
                    	Intent intent=new Intent(loadActivity.this, MainActivity.class);
            			startActivity(intent);	
                    }	
                    else
                    	Toast.makeText(getApplicationContext(),"�޷��� " ,Toast.LENGTH_SHORT).show();
                }
                else
                	Toast.makeText(getApplicationContext(),"û��Ȩ�޽��д˲���" ,Toast.LENGTH_SHORT).show();
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
                myArrayList,//����Դ   
                R.layout.list_item,//ListView�ڲ�����չʾ��ʽ�Ĳ����ļ�listitem.xml   
                new String[]{"name"},//HashMap�е�����keyֵ itemTitle��itemContent   
                new int[]{R.id.itemTitle});/*�����ļ�listitem.xml�������id    
                                                            �����ļ��ĸ�����ֱ�ӳ�䵽HashMap�ĸ�Ԫ���ϣ��������*/   
        myListView.setAdapter(mySimpleAdapter);  
    }
}  

