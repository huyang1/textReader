package com.example.myView;

import com.example.aaa.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
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
		paint.setTextSize(15); 
		Bitmap b = ((BitmapDrawable) drawable).getBitmap();
        canvas.drawBitmap(b, 0, 0, paint);
        canvas.drawLine(50, 60, 50, 90, paint);
        canvas.drawLine(35,75,65 ,75, paint);
	}


}
