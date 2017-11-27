package com.example.myView;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;

import com.huyang.aaa.R;

public class HeadLine {
	private Dialog dialog;
	private Chatper chatper;
	public HeadLine( final Context context,final View view) {
		dialog=new Dialog(context,R.style.dialog);
		dialog.setContentView(view); 
        Window dialogWindow = dialog.getWindow();
        dialogWindow.setGravity( Gravity.TOP);
        Resources resources = context.getResources();  
		DisplayMetrics dm = resources.getDisplayMetrics();  
		int mWidth = dm.widthPixels;  
        android.view.WindowManager.LayoutParams p = dialog.getWindow().getAttributes();  //获取对话框当前的参数值  
        p.width = (int) (mWidth);    //宽度设置为屏幕的0.5 
        dialog.getWindow().setAttributes(p);     //设置生效  
        
	}
	public void show() { 
        dialog.show(); 
    } 
	
	public void hide() { 
	        dialog.dismiss(); 
	}
	public void hidechapter()
	{
		chatper.hide();
	}

}

