package com.example.myView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

public class brightLogo extends ImageView {

	public brightLogo(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub

	}

	public brightLogo(Context context) {
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
		// canvas.drawCircle(getWidth()/2, getHeight()/3, getWidth()/5, paint);
		canvas.drawArc(new RectF(3 * getWidth() / 10, getHeight() / 3
				- getWidth() / 5, 7 * getWidth() / 10, getHeight() / 3
				+ getWidth() / 5), 90, 180, true, paint);
		Paint paint2 = new Paint();
		paint2.setAntiAlias(true);
		paint2.setColor(Color.BLACK);
		canvas.drawArc(new RectF(3 * getWidth() / 10, getHeight() / 3
				- getWidth() / 5, 7 * getWidth() / 10, getHeight() / 3
				+ getWidth() / 5), 270, 180, true, paint2);
		Paint paint1 = new Paint();
		paint1.setAntiAlias(true);
		paint1.setColor(Color.WHITE);
		paint1.setTextSize(20);
		canvas.drawText("¡¡∂»", getWidth() * 7 / 24, getHeight() * 4 / 5, paint1);

	}

}