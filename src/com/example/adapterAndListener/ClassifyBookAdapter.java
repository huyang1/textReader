package com.example.adapterAndListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import com.example.extra.Utility;
import com.example.linkWeb.Download;
import com.huyang.aaa.BookListActivity;
import com.huyang.aaa.BookStoreActivity;
import com.huyang.aaa.ChangeMessage;
import com.huyang.aaa.MainActivity;
import com.huyang.aaa.R;
import com.huyang.aaa.userPhoto;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class ClassifyBookAdapter extends BaseAdapter {
	private static String[] classify_Book_man = new String[] { "玄幻", "奇幻",
			"武侠", "仙侠", "都市", "校园", "历史", "军事", "游戏", "竞技", "科幻" };
	private static String[] classify_Book_woman = new String[] { "现代言情",
			"古代言情", "幻想言情", "青春校园", "同人作品", "推理悬疑", "惊悚/恐怖" };
	private ArrayList<Map<String,String>> dataInMan=new ArrayList<Map<String,String>>(); 
	private ArrayList<Map<String,String>> dataInWoman=new ArrayList<Map<String,String>>(); 
	private Context context;

	public ClassifyBookAdapter(Context context) {
		this.context = context;
		initdata();
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 2;
	}
	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		if (position == 0)
			return classify_Book_man;
		else
			return classify_Book_woman;
	}
	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if (position == 0) {
			ViewHolder viewholder = null;
			if (convertView == null) {
				LayoutInflater inflater = LayoutInflater.from(context);
				convertView = inflater.inflate(R.layout.classifybook,
						null, false);
				viewholder = new ViewHolder();
				viewholder.classify_content = (GridView) convertView
						.findViewById(R.id.gridview);
				viewholder.classify_name = (TextView) convertView
						.findViewById(R.id.toptext);
				viewholder.classify_name.setText("男频");
				viewholder.classify_content.setAdapter(new SimpleAdapter(context,
						dataInMan, R.layout.classifybook_item, new String[]{"classify_name"}, new int[]{R.id.classify_name}));
				viewholder.classify_content
				.setOnItemClickListener(new OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> arg0,
							View arg1, int arg2, long arg3) {
						// TODO Auto-generated method stub
						Log.i("........","click");
						Get_Lable_Book getlableBook=new Get_Lable_Book();
						getlableBook.execute("玄幻");
					}
				});
				Utility.setGridViewHeightBasedOnMultiChildren(viewholder.classify_content);
				convertView.setTag(viewholder);
			}
			else
				viewholder=(ViewHolder) convertView.getTag();
			return convertView;
		}
		else
		{
			ViewHolder viewholder = null;
			if (convertView == null) {
				LayoutInflater inflater = LayoutInflater.from(context);
				convertView = inflater.inflate(R.layout.classifybook,
						null, false);
				viewholder = new ViewHolder();
				viewholder.classify_content = (GridView) convertView
						.findViewById(R.id.gridview);
				viewholder.classify_name = (TextView) convertView
						.findViewById(R.id.toptext);
				viewholder.classify_name.setText("女频");
				viewholder.classify_content.setAdapter(new SimpleAdapter(context,
						dataInWoman, R.layout.classifybook_item, new String[]{"classify_name"}, new int[]{R.id.classify_name}));
				viewholder.classify_content
				.setOnItemClickListener(new OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> arg0,
							View arg1, int arg2, long arg3) {
						// TODO Auto-generated method stub
						Log.i("........","click");
						Get_Lable_Book getlableBook=new Get_Lable_Book();
						getlableBook.execute("玄幻");
					}
				});
				Utility.setGridViewHeightBasedOnMultiChildren(viewholder.classify_content);
				convertView.setTag(viewholder);
			}
			else
				viewholder=(ViewHolder) convertView.getTag();
			return convertView;
		}
	}

	private class ViewHolder {
		private GridView classify_content;
		private TextView classify_name;
	}
	private void initdata()
	{
		for(int i=0;i<classify_Book_man.length;i++)
		{
			HashMap<String, String> map=new HashMap<String, String>();
			map.put("classify_name", classify_Book_man[i]);
			dataInMan.add(map);
		}
		for(int i=0;i<classify_Book_woman.length;i++)
		{
			HashMap<String, String> map=new HashMap<String, String>();
			map.put("classify_name", classify_Book_woman[i]);
			dataInWoman.add(map);
		}
	}
	private class Get_Lable_Book extends AsyncTask<String, Void, String>
	{
		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			String lable=params[0];
			try {
				Log.i("........","getdata");
				String data=Download.LableBook("http://yangstudent.cn:1200/upload/getBook.do",lable,"1");
				return data;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			Log.i("........","post");
			if(result==null)
			     return;
			Log.i("........","start activity");
			Intent intent = new Intent(context,
					BookListActivity.class);
			intent.putExtra("data", result);
			context.startActivity(intent);
			super.onPostExecute(result);
		}
		
		
	}

}
