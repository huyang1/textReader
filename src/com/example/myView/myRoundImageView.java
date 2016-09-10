package com.example.myView;

import com.example.aaa.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;
import android.util.AttributeSet;
import android.widget.ImageView;

public class myRoundImageView extends ImageView{
	private int mBorderThickness = 0; //定义双边框的间距 
    private Context mContext;  
    private int defaultColor = 0xFFFFFFFF;  
    // 以下如果只有其中一个有值，则只画一个圆形边框  
    private int mBorderOutsideColor = 0;  
    private int mBorderInsideColor = 0;  
    // 控件默认长、宽  
    private int defaultWidth = 0;  
    private int defaultHeight = 0; 
	public myRoundImageView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		mContext = context;  
	}

	public myRoundImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		mContext = context;  
        setCustomAttributes(attrs);  
	}

	public myRoundImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		mContext = context;  
        setCustomAttributes(attrs);  
	}
	private void setCustomAttributes(AttributeSet attrs) {  
        TypedArray a = mContext.obtainStyledAttributes(attrs,R.styleable.RoundImageViewAttrs);  
        mBorderThickness = a.getDimensionPixelSize(R.styleable.RoundImageViewAttrs_border_thickness, 0);  
        mBorderOutsideColor = a.getColor(R.styleable.RoundImageViewAttrs_border_outside_color,defaultColor);  
        mBorderInsideColor = a.getColor(R.styleable.RoundImageViewAttrs_border_inside_color, defaultColor);  
        a.recycle();
    }  
	
	protected void onDraw(Canvas canvas) {  
        Drawable drawable = getDrawable();    
        if (drawable == null) {  
            return;  
        }  
  
        if (getWidth() == 0 || getHeight() == 0) {  //如果在对该属性设置宽或高为0时，则不Draw
            return;  
        }  
        this.measure(0, 0);  
        if (drawable.getClass() == NinePatchDrawable.class)  
            return;  
        Bitmap b = ((BitmapDrawable) drawable).getBitmap();//通过定义该控件属性获取位图  
        Bitmap bitmap = b.copy(Bitmap.Config.ARGB_8888, true); // 获取位图  
        if (defaultWidth == 0) {  
            defaultWidth = getWidth();  //通过用户定义属性得到控件的高与宽
  
        }  
        if (defaultHeight == 0) {  
            defaultHeight = getHeight();  
        }  
        int radius = 0;  
        if (mBorderInsideColor != defaultColor //进行边框的绘制，并未添加位图
                && mBorderOutsideColor != defaultColor) {// 用户定义画两个边框，分别为外圆边框和内圆边框  
            radius = (defaultWidth < defaultHeight ? defaultWidth  
                    : defaultHeight) / 2 - 2*mBorderThickness;  
            // 画内圆  
            drawCircleBorder(canvas, radius + mBorderThickness / 2,  
                    mBorderInsideColor);  
            // 画外圆  
            drawCircleBorder(canvas, radius + mBorderThickness  
                    + mBorderThickness / 2, mBorderOutsideColor);  
        } else if (mBorderInsideColor != defaultColor  
                && mBorderOutsideColor == defaultColor) {// 定义一个边框  
            radius = (defaultWidth < defaultHeight ? defaultWidth  
                    : defaultHeight) / 2 - mBorderThickness;  
            drawCircleBorder(canvas, radius + mBorderThickness / 2,  
                    mBorderInsideColor);  
        } else if (mBorderInsideColor == defaultColor  
                && mBorderOutsideColor != defaultColor) {// 定义一个边框  
            radius = (defaultWidth < defaultHeight ? defaultWidth  
                    : defaultHeight) / 2 - mBorderThickness;  
            drawCircleBorder(canvas, radius + mBorderThickness / 2,  
                    mBorderOutsideColor);  
        } else {// 没有边框  
            radius = (defaultWidth < defaultHeight ? defaultWidth  
                    : defaultHeight) / 2;  
        }  
        Bitmap roundBitmap = getCroppedRoundBitmap(bitmap, radius);  
        canvas.drawBitmap(roundBitmap, defaultWidth / 2 - radius, defaultHeight  
                / 2 - radius, null);  
    }
	
	private void drawCircleBorder(Canvas canvas, int radius, int color) {  
        Paint paint = new Paint();    
        paint.setAntiAlias(true);  
        paint.setFilterBitmap(true);  
        paint.setDither(true);  
        paint.setColor(color);  
        paint.setStyle(Paint.Style.STROKE);  
        paint.setStrokeWidth(mBorderThickness);  
        canvas.drawCircle(defaultWidth / 2, defaultHeight / 2, radius, paint);  
    }
	
	public Bitmap getCroppedRoundBitmap(Bitmap bmp, int radius) {  //对用户所给位图进行裁剪
        Bitmap scaledSrcBmp;  
        int diameter = radius * 2;  
  
        // 为了防止宽高不相等，造成圆形图片变形，因此截取长方形中处于中间位置最大的正方形图片  
        int bmpWidth = bmp.getWidth();  
        int bmpHeight = bmp.getHeight();  
        int squareWidth = 0, squareHeight = 0; //正方形的边长 
        int x = 0, y = 0;//获取正方形左上角的坐标
        Bitmap squareBitmap;  //所要的目标位图
        if (bmpHeight > bmpWidth) {// 高大于宽  
            squareWidth = squareHeight = bmpWidth;  
            x = 0;  
            y = (bmpHeight - bmpWidth) / 2;  
            // 截取正方形图片  
            squareBitmap = Bitmap.createBitmap(bmp, x, y, squareWidth,  
                    squareHeight);  
        } else if (bmpHeight < bmpWidth) {// 宽大于高  
            squareWidth = squareHeight = bmpHeight;  
            x = (bmpWidth - bmpHeight) / 2;  
            y = 0;  
            squareBitmap = Bitmap.createBitmap(bmp, x, y, squareWidth,  
                    squareHeight);  
        } else {  
            squareBitmap = bmp;  
        }  
  
        if (squareBitmap.getWidth() != diameter  
                || squareBitmap.getHeight() != diameter) {  
            scaledSrcBmp = Bitmap.createScaledBitmap(squareBitmap, diameter,  
                    diameter, true);  
  
        } else {  
            scaledSrcBmp = squareBitmap;  
        }  
        Bitmap output = Bitmap.createBitmap(scaledSrcBmp.getWidth(),  
                scaledSrcBmp.getHeight(), Config.ARGB_8888);  
        Canvas canvas = new Canvas(output);  
  
        Paint paint = new Paint();  
        Rect rect = new Rect(0, 0, scaledSrcBmp.getWidth(),  
                scaledSrcBmp.getHeight());  
  
        paint.setAntiAlias(true);  
        paint.setFilterBitmap(true);  
        paint.setDither(true);  
        canvas.drawARGB(0, 0, 0, 0);  
        canvas.drawCircle(scaledSrcBmp.getWidth() / 2,  
                scaledSrcBmp.getHeight() / 2, scaledSrcBmp.getWidth() / 2,  
                paint);  
        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));  
        canvas.drawBitmap(scaledSrcBmp, rect, rect, paint);  
        bmp = null;  
        squareBitmap = null;  
        scaledSrcBmp = null;  
        return output;  
    }
}
