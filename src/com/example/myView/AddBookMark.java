package com.example.myView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

public class AddBookMark extends ImageView {

	public AddBookMark(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public AddBookMark(Context context) {
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
		canvas.drawLine(getWidth()/4-2, getHeight()/2, 3*getWidth()/4+2, getHeight()/2, paint);
		canvas.drawLine(getWidth()/2, getHeight()/3, getWidth()/2, 2*getHeight()/3, paint);
		
	}
	

}

