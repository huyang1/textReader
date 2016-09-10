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
import android.util.Log;
import android.widget.ImageView;

public class bookBitmap extends ImageView{
    private Context context;
    private String name=new String();
	public bookBitmap(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		this.context=context;
		setCustomAttributeset(attrs);
	}
    private void setCustomAttributeset(AttributeSet attrs)
    {
    	TypedArray temp = context.obtainStyledAttributes(attrs,R.styleable.BookBitmap);  
        name = temp.getString(R.styleable.BookBitmap_bookname);
    }
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		Drawable drawable = getDrawable();
		Paint paint = new Paint(); 
		paint.setAntiAlias(true); 
		paint.setColor(Color.WHITE); 
		paint.setTextSize(15); 
		Bitmap b = ((BitmapDrawable) drawable).getBitmap();
		canvas.drawBitmap(b, 0, 0,paint);
        canvas.drawLine(b.getWidth()/20, 0, b.getWidth()/20, b.getHeight(), paint);
        Paint paint1 = new Paint(); 
		paint1.setAntiAlias(true); 
		paint1.setColor(Color.GRAY); 
		paint1.setTextSize(15);
		Paint paint2 = new Paint(); 
		paint2.setAntiAlias(true); 
		paint2.setColor(Color.GRAY); 
		paint2.setTextSize(15);
		paint2.setTextAlign(Paint.Align.CENTER);
        canvas.drawText(name, 55,35, paint2);
        canvas.drawText("---TXT---", 20,130, paint1);
        
	}
	public void setbookname(String string) {
		// TODO Auto-generated method stub
		name=string;
	}
    
}
