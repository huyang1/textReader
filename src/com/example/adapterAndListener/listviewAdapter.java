package com.example.adapterAndListener;
import java.util.ArrayList;
import com.huyang.aaa.R;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

public class listviewAdapter extends BaseAdapter {
	private ArrayList<ArrayList<String>> book_list;
	private LayoutInflater mInflater;
	private Context mContext;
	private int mWidth;

	public listviewAdapter(Context context, ArrayList<ArrayList<String>> mDatas) {
		mInflater = LayoutInflater.from(context);
		this.mContext = context;
		this.book_list = mDatas;
		Resources resources = context.getResources();
		DisplayMetrics dm = resources.getDisplayMetrics();
		mWidth = dm.widthPixels;
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
		if (book_list.get(arg0).size() != 0) {
			ViewHolder1 viewHolder = null;
			if (arg1 == null) {
				arg1 = mInflater.inflate(R.layout.book, arg2, false);
				viewHolder = new ViewHolder1();
				viewHolder.gridview =(GridView) arg1
						.findViewById(R.id.gridview);
				viewHolder.gridview.setFocusable(true);
				arg1.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder1) arg1.getTag();
			}
			if (book_list.get(arg0) != null) {
				if (viewHolder.gridview != null) {
					Log.e("error",book_list.get(arg0).get(0));
					ArrayList<String> data=book_list.get(arg0);
					viewHolder.gridview.setAdapter(new myAdapter(mContext,
							data));
					viewHolder.gridview
							.setOnItemClickListener(new myOnClickLinstener(
									mContext, (arg0 + 1) / 2));
					viewHolder.gridview
							.setOnItemLongClickListener(new myOnClickIongListener(
									mContext, (arg0 + 1) / 2));
					viewHolder.gridview.setSelector(new ColorDrawable(
							Color.TRANSPARENT));
				}
			}
			return arg1;
		}
		else {
			ViewHolder2 viewHolder = null;
			if (arg1 == null) {
				arg1 = mInflater.inflate(R.layout.bookbottom, arg2, false);
				viewHolder = new ViewHolder2();
				viewHolder.textview = (TextView) arg1
						.findViewById(R.id.bookbottom);
				// viewHolder.textview.setWidth(mWidth-8);
				arg1.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder2) arg1.getTag();
			}

			return arg1;
		}
	}

	private class ViewHolder1 {
		private GridView gridview;
	}

	private class ViewHolder2 {
		private TextView textview;
	}
}
