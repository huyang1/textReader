package com.example.adapterAndListener;

import java.util.ArrayList;

import com.example.DataBase.sqliteDataBase;
import com.huyang.aaa.R;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

public class myAdapter extends BaseAdapter{
    private ArrayList<String> book_list=null;
    private LayoutInflater mInflater;  
    private Context mContext; 
    private int selectItem=-1;
	private float screenWidth;
	private float screenHeight;
	private ImageView copyView;
	private Animation myAnimation_Scale;
	private sqliteDataBase downloadSet;
	public myAdapter(Context context, ArrayList<String> mDatas)  
    {  
        mInflater = LayoutInflater.from(context);  
        this.mContext = context;  
        this.book_list = mDatas;  
        downloadSet=new sqliteDataBase(context);
        copyView=new ImageView(context);
    	Resources resources = context.getResources();
		DisplayMetrics dm = resources.getDisplayMetrics();
		screenWidth=dm.widthPixels;
		screenHeight=dm.heightPixels;
		selectItem=-1;
    } 
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return book_list.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return book_list.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}


	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		Bitmap bitmap=downloadSet.getBookImage(book_list.get(arg0));
		Log.i("message", "数据条数："+downloadSet.getAllBooksName_download().size());
		//Log.i("message","当前bookName: "+downloadSet.getAllBooksName().get(arg0));
		
		if(!book_list.get(arg0).equals("import"))
		{
			ViewHolder viewHolder = null;  
	        if (arg1 == null)  
	        {  
	            arg1 = mInflater.inflate(R.layout.grid, null,  
	                    false);
	            viewHolder = new ViewHolder();  
	            viewHolder.book = (com.example.myView.bookBitmap) arg1  
	                    .findViewById(R.id.bookimage); 
	            if(bitmap!=null)
	            {
	            	viewHolder.book.setImageBitmap(bitmap);
	            	Log.i("message","bitmap已放入书架");
	            }
	            else
	            {
	            	viewHolder.book.setbookname(book_list.get(arg0));
	            }
	            arg1.setTag(viewHolder);  
	        } else  
	        {  
	            viewHolder = (ViewHolder) arg1.getTag();  
	        }  
	        return arg1; 
	        
		}
		else
			return mInflater.inflate(R.layout.loadbitmap, arg2, false);  
		
	}
	private final class ViewHolder  
    { 
		private com.example.myView.bookBitmap book;
		
    }
}
