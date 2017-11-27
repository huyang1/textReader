package com.example.myView;

import com.huyang.aaa.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

public class loadBitmap extends ImageView{
	private Context context;
    
	public loadBitmap(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		this.context=context;
		
	}
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		Drawable drawable = getDrawable();
		Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG); 
		paint.setAntiAlias(true); 
		paint.setColor(Color.BLACK); 
		paint.setTextSize(40);
		paint.setTypeface(Typeface.DEFAULT_BOLD);
		Bitmap b = ((BitmapDrawable) drawable).getBitmap();
        canvas.drawBitmap(b, 0, 0, paint);
        canvas.drawLine(getWidth()/2, getHeight()/2-15, getWidth()/2, getHeight()/2+15, paint);
        canvas.drawLine(getWidth()/2-15,getHeight()/2,getWidth()/2+15,getHeight()/2, paint);
	}


}
