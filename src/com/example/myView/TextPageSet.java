/**
 *  @Project       : aaa;  
 *  @Program Name  : com.example.myView.TextPageSet.java;
 *  @Class Name    : TextPageSet;
 *  @Description   : 设置界面dialog;
 *  @Author        : huyang;
 *  @Creation Date : 2017-3-16 下午12:01:36 ;
 */

package com.example.myView;

import com.huyang.aaa.R;
import com.huyang.aaa.userPhoto;

import android.R.integer;
import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v4.widget.SimpleCursorAdapter.ViewBinder;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.TextView;

public class TextPageSet implements OnClickListener{
	private setDialog dialog;
	private SetMessage message;
	private int fontsize=0;
	private int fonttype=0;
	private int pageColor=0;
	private TextView fontSizeView;
    TextView font1,font2,font3,font4,font5;
    private Context context;
	public TextPageSet(Context context,SetMessage message)
	{
		this.context=context;
		this.message=message;
		dialog=new setDialog(context,R.style.dialog);
		View popView = LayoutInflater. 
                from(context).inflate(R.layout.textpage_set, null);
		font1=(TextView) popView.findViewById(R.id.font1);
		font1.setTypeface(Typeface.createFromAsset(context.getAssets(),"fonts/1.ttf"));
		font2=(TextView) popView.findViewById(R.id.font2);
		font2.setTypeface(Typeface.createFromAsset(context.getAssets(),"fonts/2.ttf"));
		font3=(TextView) popView.findViewById(R.id.font3);
		font3.setTypeface(Typeface.createFromAsset(context.getAssets(),"fonts/3.ttf"));
		font4=(TextView) popView.findViewById(R.id.font4);
		font5=(TextView) popView.findViewById(R.id.font5);
		font4.setTypeface(Typeface.createFromAsset(context.getAssets(),"fonts/4.ttf"));
		fontSizeView=(TextView) popView.findViewById(R.id.fontsize);
		fontsize=((userPhoto)context.getApplicationContext()).getFontsize();
		fonttype=((userPhoto)context.getApplicationContext()).getFonttype();
		pageColor=((userPhoto)context.getApplicationContext()).getPagecolor();
		fontSizeView.setText(String.valueOf(fontsize));
		switch(fonttype)
		{
		case 0:
			font5.setTextColor(Color.RED);
			break;
		case 1:
			font1.setTextColor(Color.RED);
			break;
			
		case 2:
			font2.setTextColor(Color.RED);
			break;
		case 3:
			font3.setTextColor(Color.RED);
			break;
		case 4:
			font4.setTextColor(Color.RED);
			break;
		}
		
		font1.setOnClickListener(this);
		font2.setOnClickListener(this);
		font3.setOnClickListener(this);
		font4.setOnClickListener(this);
		font5.setOnClickListener(this);
		popView.findViewById(R.id.fontadd).setOnClickListener(this);
		popView.findViewById(R.id.fontdec).setOnClickListener(this);
		popView.findViewById(R.id.pagecolor1).setOnClickListener(this);
		popView.findViewById(R.id.pagecolor2).setOnClickListener(this);
		popView.findViewById(R.id.pagecolor3).setOnClickListener(this);
		popView.findViewById(R.id.pagecolor4).setOnClickListener(this);
		dialog.setContentView(popView); 
        Window dialogWindow = dialog.getWindow();
        dialogWindow.setGravity( Gravity.BOTTOM);
        Resources resources = context.getResources();  
		DisplayMetrics dm = resources.getDisplayMetrics();  
		int mWidth = dm.widthPixels; 
		int mHeight=dm.heightPixels;
        android.view.WindowManager.LayoutParams p = dialog.getWindow().getAttributes();  //获取对话框当前的参数值  
        p.width = (int)mWidth;    //宽度设置为屏幕的0.5 
        p.height=(int)mHeight*2/5;
        dialog.getWindow().setAttributes(p);     //设置生效
	}
	public void show() { 
        dialog.show(); 
    } 
	public void hide() {
		
	    dialog.dismiss(); 
	} 
	public interface SetMessage
	{
		void getFontSize(int fontsize);
		void getFontType(int fonttype);
		void getpageColor(int pagecolor);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int action=v.getId();
		switch(action)
		{
		case R.id.fontadd:
			fontsize++;
			fontSizeView.setText(String.valueOf(fontsize)); 
			break;
		case R.id.fontdec:
			fontsize--;
			fontSizeView.setText(String.valueOf(fontsize)); 
			break;
		case R.id.font1:
			fonttype=1;
			font1.setTextColor(Color.RED);
			font2.setTextColor(Color.WHITE);
			font3.setTextColor(Color.WHITE);
			font4.setTextColor(Color.WHITE);
			font5.setTextColor(Color.WHITE);
			break;
		case R.id.font2:
			fonttype=2;
			font1.setTextColor(Color.WHITE);
			font3.setTextColor(Color.WHITE);
			font4.setTextColor(Color.WHITE);
			font2.setTextColor(Color.RED);
			font5.setTextColor(Color.WHITE);
			break;
		case R.id.font3:
			fonttype=3;
			font2.setTextColor(Color.WHITE);
			font1.setTextColor(Color.WHITE);
			font4.setTextColor(Color.WHITE);
			font3.setTextColor(Color.RED);
			font5.setTextColor(Color.WHITE);
			break;
		case R.id.font4:
			fonttype=4;
			font2.setTextColor(Color.WHITE);
			font3.setTextColor(Color.WHITE);
			font1.setTextColor(Color.WHITE);
			font4.setTextColor(Color.RED);
			font5.setTextColor(Color.WHITE);
			break;
		case R.id.font5:
			fonttype=0;
			font2.setTextColor(Color.WHITE);
			font3.setTextColor(Color.WHITE);
			font1.setTextColor(Color.WHITE);
			font5.setTextColor(Color.RED);
			font4.setTextColor(Color.WHITE);
			break;
		case R.id.pagecolor1:
			pageColor=1;
			break;
		case R.id.pagecolor2:
			pageColor=2;
			break;
		case R.id.pagecolor3:
			pageColor=3;
			break;
		case R.id.pagecolor4:
			pageColor=4;
			break;
		}
	}
	private class setDialog extends Dialog
	{
		public setDialog(Context context) {
			super(context);
		}
		public setDialog(Context context, int themeResId) {
			super(context, themeResId);
			// TODO Auto-generated constructor stub
		}
		@Override
		public void dismiss() {
			// TODO Auto-generated method stub
			Log.i("TextPageSet","interface");
			message.getFontSize(fontsize);
			((userPhoto)context.getApplicationContext()).setFontsize(fontsize);
			message.getFontType(fonttype);
			((userPhoto)context.getApplicationContext()).setFonttype(fonttype);
			message.getpageColor(pageColor);
			((userPhoto)context.getApplicationContext()).setPagecolor(pageColor);
			super.dismiss();
		}
		
		
	}
}
