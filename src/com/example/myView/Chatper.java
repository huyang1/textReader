package com.example.myView;


import com.huyang.aaa.R;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.view.Gravity;

import android.view.View;
import android.view.Window;

import android.widget.ListView;


public class Chatper{
	private Dialog dialog;
	private ListView chatperListview;
	public Chatper( final Context context,View view)
	{
		dialog=new Dialog(context,R.style.chapter);
		dialog.setContentView(view); 
        Window dialogWindow = dialog.getWindow();
        dialogWindow.setGravity( Gravity.LEFT);
        Resources resources = context.getResources();  
		DisplayMetrics dm = resources.getDisplayMetrics();  
		int mWidth = dm.widthPixels; 
		int mHeight=dm.heightPixels;
        android.view.WindowManager.LayoutParams p = dialog.getWindow().getAttributes();  //获取对话框当前的参数值  
        p.width = (int) (5*mWidth/6);    //宽度设置为屏幕的0.5 
        p.height=(int)mHeight;
        dialog.getWindow().setAttributes(p);     //设置生效  
	}
	public void show() { 
        dialog.show(); 
    } 
	
	public void hide() { 
	        dialog.dismiss(); 
	} 
}
