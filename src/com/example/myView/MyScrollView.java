package com.example.myView;

import java.lang.reflect.Field;

import com.huyang.aaa.R;
import com.nineoldandroids.view.ViewHelper;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

public class MyScrollView extends HorizontalScrollView{
    private ViewGroup mMenu;
    private ViewGroup mContent;
    private LinearLayout mLinear;
    private int mScreenWidth;
    private int mMenuRightPadding = 150;
    private int mMenuWidth;
    private boolean load=false;
    private boolean isOpen;
    private myRoundImageView myRound;
    private Context context;
    private int mScreenHeight;
    private int statueBarHeight;
	public MyScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		/**
		 * 获取设备的宽
		 */
		this.context=context;
		WindowManager wm = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics outMetrics = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(outMetrics);
		mScreenWidth = outMetrics.widthPixels;
		mScreenHeight=outMetrics.heightPixels;
		statueBarHeight=getStatusBarHeight(context);
	}
	
	public MyScrollView(Context context) {
		
		super(context);
		// TODO Auto-generated constructor stub
		this.context=context;
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		if(!load)
		{
			mLinear=(LinearLayout) getChildAt(0);
			mMenu=(ViewGroup) mLinear.getChildAt(0);
			mContent=(ViewGroup) mLinear.getChildAt(1);
			myRound=(myRoundImageView) mContent.findViewById(R.id.log_in);
			mMenu.getLayoutParams().width=mScreenWidth - mMenuRightPadding;
			//mMenu.getLayoutParams().height=mScreenHeight-statueBarHeight;
			mMenuWidth=mScreenWidth - mMenuRightPadding;
            mContent.getLayoutParams().width=mScreenWidth;
            //mContent.getLayoutParams().height=mScreenHeight-statueBarHeight;
			load=true;
			this.scrollTo(mMenuWidth, 0);
		}
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		// TODO Auto-generated method stub
		this.scrollTo(mMenuWidth, 0);
		if(changed)
		{
			this.scrollTo(mMenuWidth, 0);
		}
		super.onLayout(changed, l, t, r, b);
	}
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
		int action=ev.getAction();
		switch(action)
		{
		case MotionEvent.ACTION_UP:
			int scrollX=getScrollX();
			if (scrollX >= mMenuWidth / 2)
			{
				this.smoothScrollTo(mMenuWidth, 0);
				myRound.setVisibility(View.VISIBLE);
				isOpen = false;
			} else
			{
				this.smoothScrollTo(0, 0);
				myRound.setVisibility(View.INVISIBLE);
				isOpen = true;
			}
			return true;
		}
		return super.onTouchEvent(ev);
	}
	private void OpenMenu()
	{
		if(isOpen)
			return;
		else
		{
			this.smoothScrollTo(0, 0);
			isOpen = true;
		}
	}
	private void CloseMenu()
	{
		if(isOpen)
		{
			this.smoothScrollTo(mMenuWidth, 0);
			isOpen = false;
		}
		else
			return;
	}
	public void toggle()
	{
		if(isOpen)
		{
			CloseMenu();
			myRound.setVisibility(View.VISIBLE);
		}
			
		else
		{
			OpenMenu();
			myRound.setVisibility(View.INVISIBLE);
		}
			
	}
	protected void onScrollChanged(int l, int t, int oldl, int oldt)
	{
		super.onScrollChanged(l, t, oldl, oldt);
		float scale = l * 1.0f / mMenuWidth; // 1 ~ 0

		/**
		 * 区别1：内容区域1.0~0.7 缩放的效果 scale : 1.0~0.0 0.7 + 0.3 * scale
		 * 
		 * 区别2：菜单的偏移量需要修改
		 * 
		 * 区别3：菜单的显示时有缩放以及透明度变化 缩放：0.7 ~1.0 1.0 - scale * 0.3 透明度 0.6 ~ 1.0 
		 * 0.6+ 0.4 * (1- scale) ;
		 * 
		 */
		float rightScale = 0.7f + 0.3f * scale;
		float leftScale = 1.0f - scale * 0.3f;
		float leftAlpha = 0.6f + 0.4f * (1 - scale);

		// 调用属性动画，设置TranslationX
		ViewHelper.setTranslationX(mMenu, mMenuWidth * scale * 0.8f);
		
		ViewHelper.setScaleX(mMenu, leftScale);
		ViewHelper.setScaleY(mMenu, leftScale);
		ViewHelper.setAlpha(mMenu, leftAlpha);
		// 设置content的缩放的中心点
		ViewHelper.setPivotX(mContent, 0);
		ViewHelper.setPivotY(mContent, mContent.getHeight() / 2);
		ViewHelper.setScaleX(mContent, rightScale);
		ViewHelper.setScaleY(mContent, rightScale);
	}
	public static int getStatusBarHeight(Context context){
        Class<?> c = null;
        Object obj = null;
        Field field = null;
        int x = 0, statusBarHeight = 0;
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            statusBarHeight = context.getResources().getDimensionPixelSize(x);
        } catch (Exception e1) {
            e1.printStackTrace();
        } 
        return statusBarHeight;
    }
}
