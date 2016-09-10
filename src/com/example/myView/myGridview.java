package com.example.myView;

import com.example.aaa.R;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.appcompat.R.color;
import android.util.AttributeSet;
import android.view.View;
import android.widget.GridView;

public class myGridview extends GridView{
    private View bookbitmap;
	public myGridview(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
    
	public myGridview(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		// TODO Auto-generated constructor stub
	}

	public myGridview(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
    protected void dispatchDraw(Canvas canvas)
    {
    	super.dispatchDraw(canvas);
    	View localView1 = getChildAt(0);
        int column = getWidth() / localView1.getWidth();
        int childCount = getChildCount();
        Paint localPaint;
        localPaint = new Paint();
        localPaint.setStyle(Paint.Style.STROKE);
        localPaint.setColor(Color.BLACK);
        for(int i = 0;i < childCount;i++)
        {
            View cellView = getChildAt(i);
            canvas.drawLine(cellView.getRight(), cellView.getTop(), cellView.getRight(), cellView.getBottom(), localPaint);
        }
        bookbitmap = getChildAt(0);
		if(bookbitmap!=null)
		{
			for(int i=0;i<=getHeight()/bookbitmap.getHeight();i++)
			{
				int position=bookbitmap.getHeight()+getVerticalSpacing();
				Paint paint=new Paint();
				paint.setAntiAlias(true); 
				paint.setColor(0xffFFFACD); 
				paint.setTextSize(15); 
				canvas.drawRect(7, position*(i+1), getWidth()-7, position*(i+1)+20, paint);
			}
		}
    }
}
