package com.example.adapterAndListener;



import java.io.File;
import java.util.ArrayList;

import com.example.DataBase.sqliteDataBase;
import com.example.extra.AnimationToRead;
import com.example.extra.Rotate3dAnimation;
import com.huyang.aaa.MainActivity;
import com.huyang.aaa.R;
import com.huyang.aaa.userPhoto;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;

import android.view.View;

import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;

import android.view.animation.ScaleAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.RelativeLayout.LayoutParams;

public class myOnClickLinstener implements OnItemClickListener{
	private MainActivity content;
	private int position;
	private float screenWidth;
	private float screenHeight;
	private ImageView copyView;
	private ImageView bottomVIew;
	private sqliteDataBase database;
	private Animation myAnimation_Scale;
	private int toolbarWidth;
	private AnimationToRead listener;
    public myOnClickLinstener(Context context,int position)
    {
    	this.content=(MainActivity) context;
    	this.position=position;
    	copyView=new ImageView(context);
    	bottomVIew=new ImageView(context);
    	Resources resources = context.getResources();
		DisplayMetrics dm = resources.getDisplayMetrics();
		screenWidth=dm.widthPixels;
		screenHeight=dm.heightPixels;
		Toolbar toolbar=(Toolbar) content.findViewById(R.id.toolbar);
		toolbarWidth=toolbar.getHeight();
    }
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		database=new sqliteDataBase(content);
		ArrayList<String> bookPath=database.getAllBooksPath();
		File file=new File(bookPath.get(bookPath.size()-position*3-arg2-1));
		if(!file.exists())
		{
			Toast.makeText(content, "图书已被清理，无效", Toast.LENGTH_LONG).show();
			return ;
		}
		/*float mutl=0;
		final int pos=arg2;
		if(arg2==0)
			mutl=1.5f;
		else if(arg2==1)
			mutl=2.5f;
		else if(arg2==2)
			mutl=3.5f;
		if((arg2+position*3)!=(database.getAllBooksPath().size()-1))
		
//			
//			/*int padding=(int) ((screenWidth-3*dip2px(content,75))/4);
//			int oripadding=arg1.getBottom()-arg1.getTop()-dip2px(content,100);
//			copyView.setBackgroundResource(R.drawable.book);
//			bottomVIew.setBackgroundResource(R.drawable.bottomanim);
//			final RelativeLayout parent=(RelativeLayout) content.findViewById(R.id.screen);
//			
//			parent.removeView(bottomVIew);
//			LayoutParams params=new LayoutParams(dip2px(content,75),dip2px(content,100));
//			params.leftMargin=padding+arg2*dip2px(content,75)+padding*arg2;
//			params.topMargin=toolbarWidth+dip2px(content,30)+position*arg1.getHeight()+position*dip2px(content,42);
//			parent.addView(bottomVIew, params);
//			parent.addView(copyView, params);
//			
//			float scaleX=screenWidth/((float)dip2px(content,75));
//			float scaleY=screenHeight/((float)dip2px(content,100));
//			myAnimation_Scale=new ScaleAnimation(1,(float) (scaleX*Math.cos(20)), 1,(float) (scaleY+arg2*Math.sin(20)),
//					(float) ((padding+arg2*dip2px(content,75)+padding*arg2)/(screenWidth-dip2px(content,75))*dip2px(content,75)/Math.cos(20))+mutl*(padding+arg2*dip2px(content,75)+padding*arg2)/scaleX,
//					 (toolbarWidth+dip2px(content,30)+position*arg1.getHeight()+position*dip2px(content,42))/
//					 (screenHeight-dip2px(content,100))*dip2px(content,100));
//			myAnimation_Scale.setDuration(1000);
//			Rotate3dAnimation rotate=new Rotate3dAnimation(Rotate3dAnimation.ROLL_BY_Y,0, -90,-padding-arg2*dip2px(content,75)-padding*arg2,(toolbarWidth+dip2px(content,30)+position*arg1.getHeight()+position*dip2px(content,42))/
//					 (screenHeight-dip2px(content,100))*dip2px(content,100));
//			rotate.setDuration(1500);
//			AnimationSet animset=new AnimationSet(true);
//			animset.addAnimation(myAnimation_Scale);
//			animset.addAnimation(rotate);
//			copyView.startAnimation(animset);
//			Animation bottomAnim=new ScaleAnimation(1,scaleX, 1,scaleY,
//					 (padding+arg2*dip2px(content,75)+padding*arg2)/(screenWidth-dip2px(content,75))*dip2px(content,75),
//					 (toolbarWidth+dip2px(content,30)+position*arg1.getHeight()+position*dip2px(content,42))/
//					 (screenHeight-dip2px(content,100))*dip2px(content,100));
//			bottomAnim.setDuration(1000);
//			bottomAnim.setFillAfter(true);
//			bottomAnim.setAnimationListener(new AnimationListener() {
//				
//				@Override
//				public void onAnimationStart(Animation animation) {
//					// TODO Auto-generated method stub
//				}
//				
//				@Override
//				public void onAnimationRepeat(Animation animation) {
//					// TODO Auto-generated method stub
//					
//				}
//				
//				@Override
//				public void onAnimationEnd(Animation animation) {
//					// TODO Auto-generated method stub
//					parent.removeView(copyView);
//					content.onmyClick(position*3+pos);	
//				}
//			});
//			bottomVIew.startAnimation(bottomAnim);
//		}*/
		//else
			content.onmyClick(position*3+arg2);
	   
	}
	public static int dip2px(Context context, float dip) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dip * scale + 0.5f);
    }
}
