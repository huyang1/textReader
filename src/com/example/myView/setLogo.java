package com.example.myView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

public class setLogo extends ImageView {

	public setLogo(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public setLogo(Context context) {
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
		canvas.drawText("Aa", getWidth()/4,getHeight()*3/7,paint);
		Paint paint1 = new Paint(); 
		paint1.setAntiAlias(true); 
		paint1.setColor(Color.WHITE); 
		paint1.setTextSize(20); 
		canvas.drawText("…Ë÷√", getWidth()*7/24, getHeight()*4/5, paint1);
	}
	

}
