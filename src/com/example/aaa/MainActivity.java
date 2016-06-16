package com.example.aaa;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;

public class MainActivity extends Activity {
private GridView gridview;
ArrayList<Map<String, Object>> book_list;
private int[] icon = { R.drawable.txt};
private String[] iconName = { "通讯录", "日历", "照相机", "时钟", "游戏", "短信", "铃声",
        "设置", "语音", "天气", "浏览器", "视频" };
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		gridview=(GridView) this.findViewById(R.id.gridview);
        Button new_load=(Button)this.findViewById(R.id.load);
        
        String [] from ={"image","text"};
        int [] to = {R.id.image,R.id.text};
        getbook();
        SimpleAdapter sim_adapter = new SimpleAdapter(this, book_list, R.layout.grid, from, to);
        //配置适配器
        gridview.setAdapter(sim_adapter);
        
        
        
        
		new_load.setOnClickListener(new load_button_listener());
	}
	
	public ArrayList<Map<String, Object>> getbook(){        
		book_list=new ArrayList<Map<String, Object>>();
		for(int i=0;i<iconName.length;i++){
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("image", icon[0]);
            map.put("text", iconName[i]);
            book_list.add(map);
        }
            
        return book_list;
    }
	
	
	class load_button_listener implements OnClickListener{
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent=new Intent(MainActivity.this, loadActivity.class);
			startActivity(intent);
		}
	}
 
}
