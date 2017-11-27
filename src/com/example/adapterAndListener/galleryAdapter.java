package com.example.adapterAndListener;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import com.example.bean.BookStore_book;
import com.example.bean.book;
import com.example.linkWeb.DownloadTask;
import com.example.linkWeb.DownloadTask.Callback;
import com.huyang.aaa.R;

import android.R.integer;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.AsyncTask;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import android.widget.Gallery;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.ListAdapter;
import android.widget.TextView;

public class galleryAdapter extends BaseAdapter {
    private ArrayList<book> data=new ArrayList<book>();
    private Context context;
    public galleryAdapter(Context context,ArrayList<book> dataBitmap)
    {
    	this.data=dataBitmap;
    	this.context=context;
    }
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if(data==null)
			return 0;
		return data.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return data.get(arg0);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		final ViewHolder viewholder;
		if(convertView==null)
		{
			LayoutInflater inflater = LayoutInflater.from(context);
			convertView = inflater.inflate(R.layout.nolve_recommendation_item, null, false);
			viewholder=new ViewHolder();
			viewholder.textview=((TextView) convertView.findViewById(R.id.name));
			viewholder.imageview=(ImageView) convertView.findViewById(R.id.bitmap);
			if(data.get(position).getDate()==null)
			    viewholder.textview.setText(data.get(position).getBookName());
			else
				viewholder.textview.setText(data.get(position).getBookName()+"  обтьсз"+data.get(position).getDate());
			if(data.get(position).getBitmap()!=null)
				viewholder.imageview.setImageBitmap(data.get(position).getBitmap());
			else
			{
				//Log.i("galleryAdapter",data.get(position).getBookUrl());
				DownloadTask download=new DownloadTask();
				Drawable drawable=download.loadDrawble(data.get(position).getBookImageUrl(), new Callback(){
					@Override
					public void imagecache(Drawable drawble) {
						// TODO Auto-generated method stub	
						viewholder.imageview.setImageDrawable(drawble);
					}
				});
				if(drawable==null)
					viewholder.imageview.setImageResource(R.drawable.book);
				else
					viewholder.imageview.setImageDrawable(drawable);
			}
			convertView.setTag(viewholder);
		}
		else
		   viewholder = (ViewHolder) convertView.getTag();
		return convertView;
	}
	private class ViewHolder
	{
		private ImageView imageview;
		private TextView textview;
	}
}
