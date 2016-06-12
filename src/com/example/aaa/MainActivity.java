package com.example.aaa;
 

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;
 

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
        Button new_exit=(Button)this.findViewById(R.id.exit);
        Button new_setting=(Button)this.findViewById(R.id.setting);
        Button new_load=(Button)this.findViewById(R.id.load);
        
        new_setting.setOnClickListener(new setting_button_Listener());
		new_exit.setOnClickListener(new exit_button_Listener());
		new_load.setOnClickListener(new load_button_listener());
	}
	class load_button_listener implements OnClickListener{
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent=new Intent(MainActivity.this, loadActivity.class);
			startActivity(intent);
		}
	}
	class setting_button_Listener implements OnClickListener{    //set button¼àÌýÆ÷
		@Override
		public void onClick(View arg0) {
			 
			 Intent intent=new Intent(MainActivity.this, setActivity.class);
			 startActivity(intent);
		}
	}
	
	class exit_button_Listener implements OnClickListener{    //exit button¼àÌýÆ÷
		@Override
		public void onClick(View arg0) {
			 
			finish();
		}
	}
}
