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
	private int mBorderThickness = 0; //����˫�߿�ļ�� 
    private Context mContext;  
    private int defaultColor = 0xFFFFFFFF;  
    // �������ֻ������һ����ֵ����ֻ��һ��Բ�α߿�  
    private int mBorderOutsideColor = 0;  
    private int mBorderInsideColor = 0;  
    // �ؼ�Ĭ�ϳ�����  
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
  
        if (getWidth() == 0 || getHeight() == 0) {  //����ڶԸ��������ÿ���Ϊ0ʱ����Draw
            return;  
        }  
        this.measure(0, 0);  
        if (drawable.getClass() == NinePatchDrawable.class)  
            return;  
        Bitmap b = ((BitmapDrawable) drawable).getBitmap();//ͨ������ÿؼ����Ի�ȡλͼ  
        Bitmap bitmap = b.copy(Bitmap.Config.ARGB_8888, true); // ��ȡλͼ  
        if (defaultWidth == 0) {  
            defaultWidth = getWidth();  //ͨ���û��������Եõ��ؼ��ĸ����
  
        }  
        if (defaultHeight == 0) {  
            defaultHeight = getHeight();  
        }  
        int radius = 0;  
        if (mBorderInsideColor != defaultColor //���б߿�Ļ��ƣ���δ���λͼ
                && mBorderOutsideColor != defaultColor) {// �û����廭�����߿򣬷ֱ�Ϊ��Բ�߿����Բ�߿�  
            radius = (defaultWidth < defaultHeight ? defaultWidth  
                    : defaultHeight) / 2 - 2*mBorderThickness;  
            // ����Բ  
            drawCircleBorder(canvas, radius + mBorderThickness / 2,  
                    mBorderInsideColor);  
            // ����Բ  
            drawCircleBorder(canvas, radius + mBorderThickness  
                    + mBorderThickness / 2, mBorderOutsideColor);  
        } else if (mBorderInsideColor != defaultColor  
                && mBorderOutsideColor == defaultColor) {// ����һ���߿�  
            radius = (defaultWidth < defaultHeight ? defaultWidth  
                    : defaultHeight) / 2 - mBorderThickness;  
            drawCircleBorder(canvas, radius + mBorderThickness / 2,  
                    mBorderInsideColor);  
        } else if (mBorderInsideColor == defaultColor  
                && mBorderOutsideColor != defaultColor) {// ����һ���߿�  
            radius = (defaultWidth < defaultHeight ? defaultWidth  
                    : defaultHeight) / 2 - mBorderThickness;  
            drawCircleBorder(canvas, radius + mBorderThickness / 2,  
                    mBorderOutsideColor);  
        } else {// û�б߿�  
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
	
	public Bitmap getCroppedRoundBitmap(Bitmap bmp, int radius) {  //���û�����λͼ���вü�
        Bitmap scaledSrcBmp;  
        int diameter = radius * 2;  
  
        // Ϊ�˷�ֹ��߲���ȣ����Բ��ͼƬ���Σ���˽�ȡ�������д����м�λ������������ͼƬ  
        int bmpWidth = bmp.getWidth();  
        int bmpHeight = bmp.getHeight();  
        int squareWidth = 0, squareHeight = 0; //�����εı߳� 
        int x = 0, y = 0;//��ȡ���������Ͻǵ�����
        Bitmap squareBitmap;  //��Ҫ��Ŀ��λͼ
        if (bmpHeight > bmpWidth) {// �ߴ��ڿ�  
            squareWidth = squareHeight = bmpWidth;  
            x = 0;  
            y = (bmpHeight - bmpWidth) / 2;  
            // ��ȡ������ͼƬ  
            squareBitmap = Bitmap.createBitmap(bmp, x, y, squareWidth,  
                    squareHeight);  
        } else if (bmpHeight < bmpWidth) {// ����ڸ�  
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
