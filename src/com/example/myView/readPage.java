package com.example.myView;
import com.huyang.aaa.R;

import android.R.integer;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;

import android.graphics.Canvas;

import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.Region;
import android.graphics.drawable.GradientDrawable;
import android.text.format.Time;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

@SuppressLint("ResourceAsColor")
public class readPage extends View{
	private int mWidth ;
	private int mHeight;

	private boolean flag = true;
	Bitmap mCurPageBitmap = null; // 当前页
	Bitmap mCurPageBackBitmap = null;
	Bitmap mNextPageBitmap = null;



	private Paint mBitmapPaint;

	
	public boolean getContent(String temp,String chatperName,int position,int BrightTYPE,int fontsize,int fonttype,int pagecolor)
	{
		Log.i("colocr",""+BrightTYPE);
		Resources resources = this.getResources();  
		DisplayMetrics dm = resources.getDisplayMetrics();  
		mWidth = dm.widthPixels;  
		mHeight= dm.heightPixels;
		LayoutInflater factory = LayoutInflater.from(getContext()); 
        View view = factory.inflate(R.layout.temp_text, null); 
        if(BrightTYPE==2)
        {
        	view.setBackgroundColor(Color.rgb(35, 34, 47));
        }
        if(BrightTYPE==1)
        	view.setBackgroundColor(Color.rgb(206, 194, 156));
        switch(pagecolor)
        {
        case 1:
        	view.setBackgroundColor(Color.rgb(84, 279, 130));//第一种背景色
        	break;
        case 2:
        	view.setBackgroundColor(Color.rgb(186, 145, 105));//第二种背景色
        	break;
        case 3:
        	view.setBackgroundColor(Color.rgb(205, 198, 198));//第三种背景色
        	break;
        case 4:
        	view.setBackgroundColor(Color.rgb(241, 165, 200));//第四种背景色
        	break;
        }
        textviewPage page = (textviewPage) view.findViewById(R.id.viewtxt); 
        page.setText(temp,position,BrightTYPE,fontsize,fonttype);
        if(position>page.getPage_size())
        	return false;
        TextView percentView=(TextView) view.findViewById(R.id.percent);
        percentView.setText(Integer.toString((int)(position*100/page.getPage_size()))+"%");
        Time t=new Time();
        t.setToNow();
        int hour = t.hour; 
        int minute=t.minute;
        String minu=null;
        if(minute<10)
        	minu="0"+minute;
        else
        	minu=""+minute;
        ((TextView)view.findViewById(R.id.time)).setText(hour+":"+minu);
        TextView chapterName=(TextView) view.findViewById(R.id.chatper);
        chapterName.setText(chatperName);
        view.setDrawingCacheEnabled(true);
        view.measure(MeasureSpec.makeMeasureSpec(mWidth, MeasureSpec.EXACTLY), 
                MeasureSpec.makeMeasureSpec(mHeight, MeasureSpec.EXACTLY)); 
        view.layout(0, 0,view.getMeasuredWidth(), view.getMeasuredHeight()); 
        view.buildDrawingCache(); 
        mCurPageBitmap=view.getDrawingCache();
        mCurPageBackBitmap=mCurPageBitmap;
        mNextPageBitmap=mCurPageBitmap;
        Log.i("size",""+page.getPage_size());
        return true;
	}
	public int setContent(String temp,String chatperName,int position,int BrightTYPE,int fontsize,int fonttype,int pagecolor)
	{
		Log.i("colocr",""+BrightTYPE);
		Resources resources = this.getResources();  
		DisplayMetrics dm = resources.getDisplayMetrics();  
		mWidth = dm.widthPixels;  
		mHeight= dm.heightPixels;
		LayoutInflater factory = LayoutInflater.from(getContext()); 
        View view = factory.inflate(R.layout.temp_text, null);
        if(BrightTYPE==2)
        {
        	view.setBackgroundColor(Color.rgb(35, 34, 47));
        	
        }
        if(BrightTYPE==1)
        {
        	view.setBackgroundColor(Color.rgb(206, 194, 156));
        }
        switch(pagecolor)
        {
        case 1:
        	view.setBackgroundColor(Color.rgb(84, 279, 130));//第一种背景色
        	break;
        case 2:
        	view.setBackgroundColor(Color.rgb(186, 145, 105));//第二种背景色
        	break;
        case 3:
        	view.setBackgroundColor(Color.rgb(205, 198, 198));//第三种背景色
        	break;
        case 4:
        	view.setBackgroundColor(Color.rgb(241, 165, 200));//第四种背景色
        	break;
        }
        textviewPage page = (textviewPage) view.findViewById(R.id.viewtxt); 
        page.setText(temp,position,BrightTYPE,fontsize,fonttype);
        TextView percentView=(TextView) view.findViewById(R.id.percent);
        percentView.setText(Integer.toString((int)(position*100/page.getPage_size()))+"%");
        Time t=new Time();
        t.setToNow();
        int hour = t.hour; 
        int minute=t.minute;
        String minu=null;
        if(minute<10)
        	minu="0"+minute;
        else
        	minu=""+minute;
        ((TextView)view.findViewById(R.id.time)).setText(hour+":"+minu);
        TextView chapterName=(TextView) view.findViewById(R.id.chatper);
        chapterName.setText(chatperName);
        view.setDrawingCacheEnabled(true);
        view.measure(MeasureSpec.makeMeasureSpec(mWidth, MeasureSpec.EXACTLY), 
                MeasureSpec.makeMeasureSpec(mHeight, MeasureSpec.EXACTLY)); 
        view.layout(0, 0,view.getMeasuredWidth(), view.getMeasuredHeight()); 
        view.buildDrawingCache(); 
        mCurPageBitmap=view.getDrawingCache();
        mCurPageBackBitmap=mCurPageBitmap;
        mNextPageBitmap=mCurPageBitmap;
        Log.i("size",""+page.getPage_size());
        return page.getPage_size();
	}
	public readPage(Context context){
		super(context);
		// TODO Auto-generated constructor stub
		Resources resources = this.getResources();  
		DisplayMetrics dm = resources.getDisplayMetrics();  
		mWidth = dm.widthPixels;  
		mHeight= dm.heightPixels;    
		
	}
	public readPage(Context context, AttributeSet attrs) {  
	    super(context, attrs);  
	} 
	public readPage(Context context,AttributeSet attrs,int defStyle)
	{
		super(context,attrs,defStyle);
	}
	
	
	@Override
	protected void onDraw(Canvas canvas) {
		canvas.drawBitmap(mCurPageBitmap, 0, 0, mBitmapPaint);
	}

	
}
