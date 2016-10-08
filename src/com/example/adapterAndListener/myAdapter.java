package com.example.adapterAndListener;

import java.util.ArrayList;

import com.example.aaa.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class myAdapter extends BaseAdapter{
    private ArrayList<String> book_list;
    private LayoutInflater mInflater;  
    private Context mContext; 

	public myAdapter(Context context, ArrayList<String> mDatas)  
    {  
        mInflater = LayoutInflater.from(context);  
        this.mContext = context;  
        this.book_list = mDatas;  
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
	            arg1.setTag(viewHolder);  
	        } else  
	        {  
	            viewHolder = (ViewHolder) arg1.getTag();  
	        }  
	        viewHolder.book.setbookname(book_list.get(arg0)); 
	        return arg1;  
		}
		else
			return mInflater.inflate(R.layout.loadbitmap, null, false);  
		
	}
	private final class ViewHolder  
    { 
		private com.example.myView.bookBitmap book;
		
	
    }   
}
