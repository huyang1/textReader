package com.example.adapterAndListener;

import java.util.List;

import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

public class MyAddBookAdapter extends PagerAdapter {
    public List<View> mListViews;
    private Toolbar toolbar=null;

    public MyAddBookAdapter(List<View> mListViews) {
        this.mListViews = mListViews;
    }
    @Override
    public void destroyItem(View arg0, int arg1, Object arg2) {
    }



    @Override
    public int getCount() {
        return mListViews.size();
    }

    @Override
    public Object instantiateItem(View arg0, int arg1) {

    	 View imageView =mListViews.get(arg1%mListViews.size());  
         if(imageView.getParent()!=null){  
             ((ViewPager)imageView.getParent()).removeView(imageView);  
         }  
         ((ViewPager) arg0).addView((View) imageView, 0);  

         return imageView;  
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == (arg1);
    }
}
