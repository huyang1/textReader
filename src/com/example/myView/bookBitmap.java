package com.example.myView;



import com.huyang.aaa.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;

public class bookBitmap extends ImageView{
    private Context context;
    private String name=new String();
    private Bitmap background=null;
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
		Bitmap b;
		if(background==null)
		{
			b = ((BitmapDrawable) drawable).getBitmap();
			canvas.drawBitmap(b, 0, 0,paint);
	        canvas.drawLine(b.getWidth()/20, 0, b.getWidth()/20, b.getHeight(), paint);
	        Paint paint1 = new Paint(); 
			paint1.setAntiAlias(true); 
			paint1.setColor(Color.GRAY); 
			paint1.setTextSize(10);
			paint1.setTypeface(Typeface.DEFAULT_BOLD);
			Paint paint2 = new Paint(); 
			paint2.setAntiAlias(true); 
			paint2.setColor(Color.GRAY); 
			paint2.setTextSize(20);
			paint.setTypeface(Typeface.DEFAULT_BOLD);
			paint2.setTextAlign(Paint.Align.CENTER);
	        canvas.drawText(name, 2*getWidth()/5,getHeight()/5, paint2);
	        canvas.drawText("---TXT---",getWidth()/4,9*getHeight()/10, paint1);
		}
		else
		{
			int width = background.getWidth();  
		    int height = background.getHeight();  
		    // 设置想要的大小  
		    int newWidth = getWidth();  
		    int newHeight = getHeight();  
		    // 计算缩放比例  
		    float scaleWidth = ((float) newWidth) / width;  
		    float scaleHeight = ((float) newHeight) / height;  
		    // 取得想要缩放的matrix参数  
		    Matrix matrix = new Matrix();  
		    matrix.postScale(scaleWidth, scaleHeight);  
		    // 得到新的图片  
		    Bitmap newbm = Bitmap.createBitmap(background, 0, 0, width, height, matrix,  
		      true);  
			canvas.drawBitmap(newbm, 0, 0,paint);
		}
		//Bitmap b = ((BitmapDrawable) drawable).getBitmap();
		
        
	}
	public void setbookname(String string) {
		// TODO Auto-generated method stub
		name=string;
	}
	@Override
	public void setImageBitmap(Bitmap bm) {
		// TODO Auto-generated method stub
		background=bm;
		super.setImageBitmap(bm);
	}
	

    
}
