package com.example.myView;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

public class mutiGridview extends GridView {

	public mutiGridview(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}


	public mutiGridview(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}
    

}
