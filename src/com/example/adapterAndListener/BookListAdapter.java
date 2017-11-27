/**
 *  @Project       : aaa;  
 *  @Program Name  : com.example.adapterAndListener.BookListAdapter.java;
 *  @Class Name    : BookListAdapter;
 *  @Description   : 书籍列表适配器;
 *  @Author        : huyang;
 *  @Creation Date : 2017-3-11 下午8:01:16 ;
 */

package com.example.adapterAndListener;

import java.util.ArrayList;
import com.example.bean.book;
import com.example.linkWeb.DownloadTask;
import com.example.linkWeb.DownloadTask.Callback;
import com.huyang.aaa.R;

import android.R.integer;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class BookListAdapter extends BaseAdapter {
	private ArrayList<book> data=new ArrayList<book>();
	private Context context;
	private int updateItem=0;
	private final ArrayList<View> views=new ArrayList<View>();
	public BookListAdapter(Context context,ArrayList<book> data)
	{
		this.data=data;
		this.context=context;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return data.size();
	}
	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return data.get(position);
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
		Log.i("updateItem",""+updateItem);
		if(position>=updateItem&&position>=views.size())
		{
			Log.i("draw","  "+position);
			LayoutInflater inflater = LayoutInflater.from(context);
			convertView = inflater.inflate(R.layout.book_list, null, false);
			viewholder=new ViewHolder();
			viewholder.bookName=((TextView) convertView.findViewById(R.id.bookName));
			viewholder.bookName.setText(data.get(position).getBookName());
			viewholder.uploader=((TextView) convertView.findViewById(R.id.uploader));
			viewholder.author=((TextView) convertView.findViewById(R.id.author));
			viewholder.descriable=((TextView) convertView.findViewById(R.id.descriable));
			viewholder.bookImage=(ImageView) convertView.findViewById(R.id.bookimage);
			viewholder.uploader.setText(" 上传者："+data.get(position).getUploader());
			viewholder.author.setText(" 作者："+data.get(position).getAuthor());
			viewholder.descriable.setText("  简述："+data.get(position).getDescribe());
				DownloadTask download=new DownloadTask();
				Drawable drawable=download.loadDrawble(data.get(position).getBookImageUrl(), new Callback(){
					@Override
					public void imagecache(Drawable drawble) {
						// TODO Auto-generated method stub	
						viewholder.bookImage.setImageDrawable(drawble);
					}
				});
				if(drawable==null)
					viewholder.bookImage.setImageResource(R.drawable.book);
				else
					viewholder.bookImage.setImageDrawable(drawable);
			convertView.setTag(viewholder);
			views.add(convertView);
		}
		else 
		{
			convertView=views.get(position);
		}
		return convertView;
	}
	public void setUpdateItem(int position)
	{
		this.updateItem=position;
	}
	private class ViewHolder
	{
		private TextView bookName;
		private TextView uploader;
		private TextView author;
		private TextView descriable;
		private ImageView bookImage;
	}
	
}
