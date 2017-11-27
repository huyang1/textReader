package com.example.myView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

public class BackView extends ImageView {

	public BackView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public BackView(Context context) {
		super(context);
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
		paint.setTextSize(40); 
		canvas.drawLine(2*getWidth()/3, getHeight()/4, getWidth()/3, getHeight()/2, paint);
		canvas.drawLine(2*getWidth()/3, 3*getHeight()/4, getWidth()/3, getHeight()/2, paint);
		
	}
	

}
