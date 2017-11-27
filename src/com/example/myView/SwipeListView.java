package com.example.myView;

import com.huyang.aaa.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ListView;

@SuppressLint("NewApi")
public class SwipeListView extends ListView {
	private int mScreemWidth;
	private int textViewWidth;
	private int mWidth,mHeight;
	private ViewGroup selectItem;
	private LinearLayout.LayoutParams layoutparams;
	private boolean isSwipe=false;
    private  float scale;
	public SwipeListView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		float scale1 = context.getResources().getDisplayMetrics().density;
		textViewWidth= (int) (70 * scale1 + 0.5f);
		scale = context.getResources().getDisplayMetrics().density;
		 WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
	     DisplayMetrics dm = new DisplayMetrics();
	     wm.getDefaultDisplay().getMetrics(dm);
	     mScreemWidth = dm.widthPixels;
	}
	public SwipeListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		float scale1 = context.getResources().getDisplayMetrics().density;
		textViewWidth= (int) (70 * scale1 + 0.5f);
		scale = context.getResources().getDisplayMetrics().density;
		 WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
	     DisplayMetrics dm = new DisplayMetrics();
	     wm.getDefaultDisplay().getMetrics(dm);
	     mScreemWidth = dm.widthPixels;
		// TODO Auto-generated constructor stub
	}
	public SwipeListView(Context context, AttributeSet attrs, int defStyleAttr,
			int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
		float scale1 = context.getResources().getDisplayMetrics().density;
		textViewWidth= (int) (70 * scale1 + 0.5f);
		scale = context.getResources().getDisplayMetrics().density;
		 WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
	     DisplayMetrics dm = new DisplayMetrics();
	     wm.getDefaultDisplay().getMetrics(dm);
	     mScreemWidth = dm.widthPixels;
		// TODO Auto-generated constructor stub
	}
	public SwipeListView(Context context) {
		super(context);
		float scale1 = context.getResources().getDisplayMetrics().density;
		textViewWidth= (int) (70 * scale1 + 0.5f);
		scale = context.getResources().getDisplayMetrics().density;
		 WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
	     DisplayMetrics dm = new DisplayMetrics();
	     wm.getDefaultDisplay().getMetrics(dm);
	     mScreemWidth = dm.widthPixels;
		// TODO Auto-generated constructor stub
	}
	@Override
    public boolean onTouchEvent(MotionEvent ev) {
		if(isSwipe)
			return super.onTouchEvent(ev);
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                ActionDown(ev);
                break;
            case MotionEvent.ACTION_UP:
                ActionUp(ev);
                break;
            case MotionEvent.ACTION_MOVE:
                return ActionMove(ev);
        }

        return super.onTouchEvent(ev);
    }
	private void ActionDown(MotionEvent ev)
	{
		/*if(isSwipe)
		{
			recovery();
			isSwipe=false;
		}*/
		mWidth=(int) ev.getX();
		mHeight=(int) ev.getY();
		if(pointToPosition((int)ev.getX(), (int)ev.getY())<0)
		{
			selectItem=(ViewGroup) getChildAt(getLastVisiblePosition());
			layoutparams=(LinearLayout.LayoutParams) selectItem.getChildAt(0).getLayoutParams();
			return;
		}
		Log.i("touch",""+pointToPosition((int)ev.getX(), (int)ev.getY())+"  "+getFirstVisiblePosition());
		selectItem=(ViewGroup) getChildAt(pointToPosition((int)ev.getX(), (int)ev.getY())-getFirstVisiblePosition());
		layoutparams=(LinearLayout.LayoutParams) selectItem.getChildAt(0).getLayoutParams();
	}
	private boolean ActionMove(MotionEvent ev)
	{
		int scroll_X=mWidth-(int)ev.getX();
	    
	    	//layoutparams.width=mScreemWidth-scroll_X;
	    	layoutparams.leftMargin=-scroll_X;

	    selectItem.getChildAt(0).setLayoutParams(layoutparams);
	    
		return false;
	}
	private void ActionUp(MotionEvent ev)
	{
		int scroll_X=mWidth-(int)ev.getX();
		if(scroll_X<textViewWidth)
		{
			layoutparams.leftMargin = 0;
			//layoutparams.width=mScreemWidth;
			selectItem.getChildAt(0).setLayoutParams(layoutparams);
			isSwipe=false;
		}
		else
		{
			//layoutparams.width=mScreemWidth-2*textViewWidth;
	    	layoutparams.leftMargin=-2*textViewWidth;
	    	selectItem.getChildAt(0).setLayoutParams(layoutparams);
	    	isSwipe=false;
		}
	}
	public void recovery() {
		layoutparams.leftMargin = 0;
		//layoutparams.width=mScreemWidth;
		selectItem.getChildAt(0).setLayoutParams(layoutparams);
		this.postInvalidate();
		isSwipe=false;
    }
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}
}
