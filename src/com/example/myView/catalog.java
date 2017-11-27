package com.example.myView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

public class catalog extends ImageView {

	public catalog(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public catalog(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		// TODO Auto-generated constructor stub
	}

	public catalog(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		Drawable drawable = getDrawable();
		Paint paint = new Paint(); 
		paint.setAntiAlias(true); 
		paint.setColor(Color.WHITE); 
		paint.setTextSize(20); 
		canvas.drawCircle(getWidth()/3, getHeight()/4,1, paint);
		canvas.drawLine(getWidth()/3+10, getHeight()/4, 2*getWidth()/3, getHeight()/4, paint);
		
		canvas.drawCircle(getWidth()/3, 11*getHeight()/28,1, paint);
		canvas.drawLine(getWidth()/3+10, 11*getHeight()/28, 2*getWidth()/3, 11*getHeight()/28, paint);
		
		canvas.drawCircle(getWidth()/3, 15*getHeight()/28,1, paint);
		canvas.drawLine(getWidth()/3+10, 15*getHeight()/28, 2*getWidth()/3, 15*getHeight()/28, paint);
		
		canvas.drawText("Ŀ¼",8*getWidth()/24, 4*getHeight()/5, paint);
	}
	

}
