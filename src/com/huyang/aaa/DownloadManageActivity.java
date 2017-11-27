package com.huyang.aaa;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import com.example.DataBase.Book;
import com.example.DataBase.sqliteDataBase;
import com.example.linkWeb.DownloadTask;
import com.example.linkWeb.DownloadTask.Callback;

import android.app.Activity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


public class DownloadManageActivity extends Activity {
	public static ArrayList<HashMap<String, String>> book_nums=new ArrayList<HashMap<String,String>>();
    private ListView listview;
    private static Context context;
    private static ArrayList<View> view_list=new ArrayList<View>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.download_manage);
		context=this;
		listview=(ListView) findViewById(R.id.listview);
		listview.setAdapter(new MyListAdapter());
		findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				DownloadManageActivity.this.finish();
			}
		});
		
	}
	
	public static class DownloadBook extends AsyncTask<HashMap<String, String>, Integer,Void >
	{
		public int download_num=-1;
		private long down_len=0;
		private String name;
		private String downloadPath;
		private Bitmap bitmap;
		private sqliteDataBase downloadBookSet;
		public DownloadBook(Context context,Bitmap bitmap)
		{
			downloadBookSet=new sqliteDataBase(context);
			download_num++;
			this.bitmap=bitmap;
		}
		@Override
		protected Void doInBackground(HashMap<String, String>... params) {
			// TODO Auto-generated method stub
			params[0].put("progress", Integer.toString(0));
			book_nums.add(params[0]);
			downloadPath=params[0].get("path");
			name=params[0].get("bookName");
			
			String Url=params[0].get("url");
			File file = new File(downloadPath);
			if (file.exists()) {
				file.delete();
				Log.i("file","delete");
			}
			try {
				file.createNewFile();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				URL url = new URL(Url);
				URLConnection con = url.openConnection();
				int length=con.getContentLength();
				InputStream is = con.getInputStream();
				byte[] bs = new byte[1024];
				int len=0;
				OutputStream os = new FileOutputStream(downloadPath);
				while ((len = is.read(bs)) != -1) {
					os.write(bs, 0, len);
					down_len+=len;
					publishProgress((int) ((down_len / (float) length) * 100));
				}
				os.close();
				is.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}
		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			SimpleDateFormat formatter = new SimpleDateFormat ("yyyy-MM-dd");
			Date curDate = new Date(System.currentTimeMillis());//获取当前时间
			String date = formatter.format(curDate);
			if(bitmap!=null)
				Log.i("message","bitmap 已存入数据库");
			downloadBookSet.insert(name,imageZoom(bitmap),date);
		}
		@Override
		protected void onProgressUpdate(Integer... values) {
			// TODO Auto-generated method stub
			super.onProgressUpdate(values);
			
			View view=null;
			if(download_num<view_list.size())
		           view=view_list.get(download_num);
			if(view!=null)	
			{
				ViewHolder viewHolder=(ViewHolder) view.getTag();
				viewHolder.progress.setProgress(values[0]);
				viewHolder.precent.setText(values[0]+"%");
			}
			book_nums.get(download_num).remove("progress");
			book_nums.get(download_num).put("progress", Integer.toString(values[0]));
		}
	}
	private class MyListAdapter extends BaseAdapter
	{
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return book_nums.size();
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return book_nums.get(arg0);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			final ViewHolder viewHolder;
			if(convertView==null)
			{
				LayoutInflater inflater = LayoutInflater.from(context);
				convertView = inflater.inflate(R.layout.down_item, null, false);
				viewHolder=new ViewHolder();
				viewHolder.bookImage=(ImageView) convertView.findViewById(R.id.bookimage);
				viewHolder.bookName=(TextView) convertView.findViewById(R.id.bookname);
				viewHolder.bookName.setText(book_nums.get(position).get("bookName"));
				viewHolder.progress=(ProgressBar) convertView.findViewById(R.id.progress);
				viewHolder.precent=(TextView) convertView.findViewById(R.id.precent);
				viewHolder.precent.setText(book_nums.get(position).get("progress")+"%");
				viewHolder.progress.setProgress(Integer.parseInt(book_nums.get(position).get("progress")));

				DownloadTask download=new DownloadTask();
				Drawable drawable=download.loadDrawble(book_nums.get(position).get("bookImageUrl"), new Callback(){
					@Override
					public void imagecache(Drawable drawble) {
						// TODO Auto-generated method stub	
						viewHolder.bookImage.setImageDrawable(drawble);
						
					}
				});
				convertView.setTag(viewHolder);
			}
			else
				viewHolder = (ViewHolder) convertView.getTag();
			convertView.setTag(R.id.listview, position);
			view_list.add(convertView);
			return convertView;
		}
	}
	private class ViewHolder
	{
		ImageView bookImage;
		TextView bookName;
		ProgressBar progress;
		TextView precent;
	}
	private static Bitmap imageZoom(Bitmap bip) {
		// 图片允许最大空间 单位：KB
		double maxSize = 20.00;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bip.compress(Bitmap.CompressFormat.JPEG, 100, baos);
		byte[] b = baos.toByteArray();
		// 将字节换成KB
		double mid = b.length / 1024;
		// 判断bitmap占用空间是否大于允许最大空间 如果大于则压缩 小于则不压缩
		if (mid > maxSize) {
			// 获取bitmap大小 是允许最大大小的多少倍
			double i = mid / maxSize;
			// 开始压缩 此处用到平方根 将宽带和高度压缩掉对应的平方根倍
			// （1.保持刻度和高度和原bitmap比率一致，压缩后也达到了最大大小占用空间的大小）
			bip = zoomImage(bip, bip.getWidth() / Math.sqrt(i), bip.getHeight()
					/ Math.sqrt(i));
		}
		return bip;

	}

	public static Bitmap zoomImage(Bitmap bgimage, double newWidth,
			double newHeight) {
		// 获取这个图片的宽和高
		float width = bgimage.getWidth();
		float height = bgimage.getHeight();
		// 创建操作图片用的matrix对象
		Matrix matrix = new Matrix();
		// 计算宽高缩放率
		float scaleWidth = ((float) newWidth) / width;
		float scaleHeight = ((float) newHeight) / height;
		// 缩放图片动作
		matrix.postScale(scaleWidth, scaleHeight);
		Bitmap bitmap = Bitmap.createBitmap(bgimage, 0, 0, (int) width,
				(int) height, matrix, true);
		return bitmap;
	}
}
