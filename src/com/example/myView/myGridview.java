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
    }
}
