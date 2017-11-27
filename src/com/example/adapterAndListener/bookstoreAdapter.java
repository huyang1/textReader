package com.example.adapterAndListener;

import java.util.ArrayList;
import com.example.bean.book;
import com.example.extra.Utility;
import com.example.linkWeb.DownloadTask;
import com.example.linkWeb.DownloadTask.Callback;

import com.huyang.aaa.BookStoreActivity;
import com.huyang.aaa.R;

import android.R.integer;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.BaseAdapter;

import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;

import android.widget.TextView;

public class bookstoreAdapter extends BaseAdapter {
	private ArrayList<book> dataBitmap = new ArrayList<book>();// 数据集
	private static Context context;
	private int currIndex = 0;
	private ViewPager viewpage;
	private TextView bottomtxt;
	private ArrayList<View> views = new ArrayList<View>();
	private Activity activity;
	private galleryAdapter madapter;
	private boolean updateItem = false;
    private final ArrayList<book> data = new ArrayList<book>();
	public bookstoreAdapter(ArrayList<book> dataBitmap, Context context,
			Activity activity) {
		super();
		this.context = context;
		this.dataBitmap = dataBitmap;
		this.activity = activity;
		updateItem = false;
		// TODO Auto-generated constructor stub
		// Log.i("size",""+dataBitmap.size());
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 3;
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return dataBitmap;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if (position == 0)// 画廊图片轮播区
		{
			ViewHolder2 viewHolder = null;
			if (convertView == null) {
				LayoutInflater inflater = LayoutInflater.from(context);
				convertView = inflater
						.inflate(R.layout.topgallery, null, false);
				viewpage = (ViewPager) convertView.findViewById(R.id.gallery);
				bottomtxt = (TextView) convertView.findViewById(R.id.textView1);
				initView();
				viewpage.setAdapter(new MyAddBookAdapter(views));
				viewpage.setCurrentItem(0);
				bottomtxt.setText("1/" + views.size());
				viewpage.setOnPageChangeListener(new MyOnPageChangeListener());
				viewHolder = new ViewHolder2();
				viewHolder.view = convertView;
				convertView.setTag(viewHolder);
			} else
				viewHolder = (ViewHolder2) convertView.getTag();

			return convertView;
		} else if (position == 1) {
			final ArrayList<book> data = new ArrayList<book>();
			for (int i = dataBitmap.size() - 9; i < dataBitmap.size() - 3; i++) {
				data.add(dataBitmap.get(i));
			}
			ViewHolder2 viewHolder = null;
			if (convertView == null) {
				LayoutInflater inflater = LayoutInflater.from(context);
				convertView = inflater.inflate(R.layout.nolve_recommendation,
						null, false);
				viewHolder = new ViewHolder2();
				viewHolder.gridview = (GridView) convertView
						.findViewById(R.id.gridview);
				viewHolder.gridview
						.setAdapter(new galleryAdapter(context, data));
				viewHolder.gridview
						.setOnItemClickListener(new OnItemClickListener() {

							@Override
							public void onItemClick(AdapterView<?> arg0,
									View arg1, int arg2, long arg3) {
								// TODO Auto-generated method stub
								((BookStoreActivity) activity).OnMyClick(data
										.get(arg2));
							}
						});
				Utility.setGridViewHeightBasedOnChildren(viewHolder.gridview);
				convertView.setTag(viewHolder);
			} else
				viewHolder = (ViewHolder2) convertView.getTag();
			return convertView;
		} else {
			int size=data.size();
			data.clear();
			if (!updateItem) {
				for (int i = 0; i < dataBitmap.size() - 9; i++) {
					data.add(dataBitmap.get(i));
				}
				ViewHolder2 viewHolder = null;
				/* if (convertView == null) { */
				LayoutInflater inflater = LayoutInflater.from(context);
				convertView = inflater.inflate(R.layout.nolve_recommendation,
						null, false);
				viewHolder = new ViewHolder2();
				viewHolder.gridview = (GridView) convertView
						.findViewById(R.id.gridview);
				viewHolder.toptext = (TextView) convertView
						.findViewById(R.id.toptext);
				viewHolder.toptext.setText("精选图书");
				madapter = new galleryAdapter(context, data);
				viewHolder.gridview.setAdapter(madapter);

				viewHolder.gridview.setSelection(viewHolder.gridview
						.getFirstVisiblePosition() - 1);
				viewHolder.gridview
						.setOnItemClickListener(new OnItemClickListener() {

							@Override
							public void onItemClick(AdapterView<?> arg0,
									View arg1, int arg2, long arg3) {
								// TODO Auto-generated method stub
								((BookStoreActivity) activity).OnMyClick(data
										.get(arg2));
							}
						});
				Utility.setGridViewHeightBasedOnChildren(viewHolder.gridview);
				convertView.setTag(viewHolder);
				/*
				 * } else viewHolder = (ViewHolder2) convertView.getTag();
				 */
				return convertView;
			} else {
				for (int i = dataBitmap.size() - 10; i >=0;i--) {
					data.add(dataBitmap.get(i));
				}
				ViewHolder2 viewHolder = null;
				viewHolder = (ViewHolder2) convertView.getTag();
				viewHolder.gridview.setFocusable(true);
				Log.i("bookstoreAdapter",""+data.size());

				madapter.notifyDataSetChanged();
				
				
				viewHolder.gridview.setSelection(viewHolder.gridview
						.getFirstVisiblePosition());
				Utility.setGridViewHeightBasedOnChildren(viewHolder.gridview);
				return convertView;

			}
			
		}
	}

	private class ViewHolder2 {
		private View view;
		private GridView gridview;
		private TextView toptext;
	}

	public class MyOnPageChangeListener implements OnPageChangeListener {

		public void onPageScrollStateChanged(int state) {

			// viewpage.setCurrentItem(currIndex);
		}

		@Override
		public void onPageScrolled(int position, float offset, int offsetPixels) {
			// viewpage.setCurrentItem(currIndex);

		}

		@Override
		public void onPageSelected(int position) {
			viewpage.setCurrentItem(position);
			bottomtxt.setText((position + 1) + "/" + views.size());
			currIndex = position;
		}
	}

	private void initView() {
		for (int i = 0; i < 3; i++) {
			LayoutInflater inflater = LayoutInflater.from(context);
			final View convertView = inflater.inflate(R.layout.topgalleyitem,
					null, false);
			if (dataBitmap != null) {
				DownloadTask download = new DownloadTask();
				Drawable drawable = download.loadDrawble(
						dataBitmap.get(dataBitmap.size() - 1 - i)
								.getBookImageUrl(), new Callback() {
							@Override
							public void imagecache(Drawable drawble) {
								// TODO Auto-generated method stub
								if (drawble != null)
									((ImageView) convertView
											.findViewById(R.id.bookview))
											.setBackground(drawble);
								else
									((ImageView) convertView
											.findViewById(R.id.bookview))
											.setBackgroundResource(R.drawable.top1);
							}
						});
			}
			views.add(convertView);
		}
	}

	public void updateView() {
		this.updateItem = true;
	}
}
