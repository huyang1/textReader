package com.example.aaa;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.Fragment.SavedState;
import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;
import android.widget.AdapterView.OnItemClickListener;

public class MainActivity extends Activity {
private GridView gridview;
private ArrayList<Map<String, Object>> book_list;
private int[] icon = { R.drawable.txt};
private ArrayList<String> iconName;
//public ArrayList<String> iconName = new ArrayList<String>();
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		gridview=(GridView) this.findViewById(R.id.gridview);
        Button new_load=(Button)this.findViewById(R.id.load);
        showBookShelf();
        gridview.setOnItemClickListener(new gridviewListener());
        	 
		new_load.setOnClickListener(new load_button_listener());
	}
	public class gridviewListener implements OnItemClickListener{
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// TODO Auto-generated method stub
			HashMap<String,Object> temp= (HashMap<String, Object>) gridview.getItemAtPosition(position);   
            String title=(String) temp.get("text");
            Toast.makeText(getApplicationContext(), position + "",
                    Toast.LENGTH_SHORT).show();
		}
	}
	
	public void showBookShelf(){
		iconName=new ArrayList<String>();
        //iconName.add("daoru");
        Book books=(Book) getApplicationContext();
        iconName=books.getBooks();
        getData();
        
        String [] from ={"image","text"};
        int [] to = {R.id.image,R.id.text};
        SimpleAdapter sim_adapter = new SimpleAdapter(this,book_list ,R.layout.grid, from, to);//book_list为该布局的数据源
        //配置适配器
        gridview.setAdapter(sim_adapter);
	}
	public List<Map<String, Object>> getData(){        
        //cion和iconName的长度是相同的，这里任选其一都可以
		book_list=new ArrayList<Map<String, Object>>();
        for(int i=0;i<iconName.size();i++){
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("text", iconName.get(i));
            map.put("image", icon[0]);
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
