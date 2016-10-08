package com.example.aaa;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import com.example.DataBase.Book;
import com.example.DataBase.sqliteDataBase;
import com.example.adapterAndListener.MyAddBookAdapter;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class importActivity extends Activity {
	private ViewPager mPager;// ҳ������
	private List<View> listViews; // Tabҳ���б�
	private ImageView cursor;// ����ͼƬ
	private TextView t1, t2;// ҳ��ͷ��
	private ListView view1, view2;
	private int offset = 0;// ����ͼƬƫ����
	private int currIndex = 0;// ��ǰҳ�����
	private int screenWidth;
	private static final String ROOT_PATH = "/";
	private ArrayList<HashMap<String, Object>> myArrayList2;
	private static ArrayList<HashMap<String, Object>> myArrayList1;
	// private static ArrayList<File> myfile;
	private static int[] resource = { R.drawable.dir, R.drawable.txt };
	private Context context;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.addbook);
		context = this;
		InitViewPager();
		InitTextView();
		// InitImageView();
		DisplayMetrics dpMetrics = new DisplayMetrics();
		getWindow().getWindowManager().getDefaultDisplay()
				.getMetrics(dpMetrics);
		screenWidth = dpMetrics.widthPixels;
	}

	/**
	 * ��ʼ��ͷ��
	 */
	private void InitTextView() {
		t1 = (TextView) this.findViewById(R.id.text1);
		t2 = (TextView) this.findViewById(R.id.text2);
		cursor = (ImageView) this.findViewById(R.id.cursor);
		t1.setOnClickListener(new MyOnClickListener(0));
		t2.setOnClickListener(new MyOnClickListener(1));
	}

	/**
	 * ͷ��������
	 */
	public class MyOnClickListener implements View.OnClickListener {
		private int index = 0;

		public MyOnClickListener(int i) {
			index = i;
		}

		@Override
		public void onClick(View v) {
			mPager.setCurrentItem(index);
		}
	};

	/**
	 * ��ʼ��ViewPager
	 */
	@SuppressWarnings("deprecation")
	private void InitViewPager() {
		mPager = (ViewPager) this.findViewById(R.id.vPager);
		listViews = new ArrayList<View>();
		LayoutInflater mInflater = getLayoutInflater();
		View mainview1 = mInflater.inflate(R.layout.loadmainview, null);
		View mainview2 = mInflater.inflate(R.layout.loadmainview1, null);
		listViews.add(mainview1);
		listViews.add(mainview2);
		mPager.setAdapter(new MyAddBookAdapter(listViews));
		mPager.setCurrentItem(0);
		mPager.setOnPageChangeListener(new MyOnPageChangeListener());
		view1 = (ListView) mainview1.findViewById(R.id.myListView1);
		view2 = (ListView) mainview2.findViewById(R.id.myListView2);
		showDir2(ROOT_PATH, view2);
		view2.setOnItemClickListener(new MyListView2Listsner());
		// showDir1(ROOT_PATH, view1);
		myArrayList1 = new ArrayList<HashMap<String, Object>>();
		findbook(new File("/sdcard"));

		SimpleAdapter mySimpleAdapter = new SimpleAdapter(context,
				myArrayList1,// ����Դ
				R.layout.list_item,// ListView�ڲ�����չʾ��ʽ�Ĳ����ļ�listitem.xml
				new String[] { "name", "kind" },// HashMap�е�����keyֵ
												// itemTitle��itemContent
				new int[] { R.id.itemTitle, R.id.image });/*
														 * �����ļ�listitem .
														 * xml�������id
														 * �����ļ��ĸ�����ֱ�ӳ�䵽HashMap�ĸ�Ԫ����
														 * �� �������
														 */
		view1.setAdapter(mySimpleAdapter);
		view1.setOnItemClickListener(new MyListView1Listsner());
	}
	public class MyListView2Listsner implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub
			HashMap<String, String> map = (HashMap<String, String>) view2
					.getItemAtPosition(arg2);
			String path = map.get("path");
			if (map.get("name").equals("������һ��")) {
				File file = new File(path);
				showDir2(file.getParent(), view2);
			} else {
				// ���ѡ�����HashMap����
				String title = map.get("name");
				String content = map.get("path");
				File file = new File(content);
				String end = title.substring(title.lastIndexOf(".") + 1,
						title.length()).toLowerCase();
				if (file.exists() && file.canRead()) {
					if (file.isDirectory()) {
						showDir2(content, view2);
					} else if (file.isFile()) {
					
						sqliteDataBase database = new sqliteDataBase(context);
						database.insert(new Book(title, content));

						if (end == "txt")
							Toast.makeText(getApplicationContext(), "��ӳɹ� ",
									Toast.LENGTH_SHORT).show();
						else
							Toast.makeText(getApplicationContext(), "��ӳɹ�",
									Toast.LENGTH_SHORT).show();

						Intent intent = new Intent(importActivity.this,
								MainActivity.class);
						startActivity(intent);
						finish();
						MainActivity.main1.finish();
					} else
						Toast.makeText(getApplicationContext(), "�޷��� ",
								Toast.LENGTH_SHORT).show();
				} else
					Toast.makeText(getApplicationContext(), "û��Ȩ�޽��д˲���",
							Toast.LENGTH_SHORT).show();
			}
		}

	}

	public class MyListView1Listsner implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {

			HashMap<String, String> map = (HashMap<String, String>) view1
					.getItemAtPosition(arg2);
			String title = map.get("name");
			String content = map.get("path");
			File file = new File(content);
			String end = title.substring(title.lastIndexOf(".") + 1,
					title.length()).toLowerCase();

			sqliteDataBase database = new sqliteDataBase(context);
			database.insert(new Book(title, content));
			Toast.makeText(getApplicationContext(), "��ӳɹ� ",
						Toast.LENGTH_SHORT).show();
			Intent intent = new Intent(importActivity.this, MainActivity.class);
			startActivity(intent);
			finish();
			MainActivity.main1.finish();
		}
	}
	/**
	 * ҳ���л�����
	 */

	public class MyOnPageChangeListener implements OnPageChangeListener {

		public void onPageScrollStateChanged(int state) {

		}
		/**
		 * position :��ǰҳ�棬������������ҳ�� offset:��ǰҳ��ƫ�Ƶİٷֱ� offsetPixels:��ǰҳ��ƫ�Ƶ�����λ��
		 */
		@Override
		public void onPageScrolled(int position, float offset, int offsetPixels) {
			LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) cursor
					.getLayoutParams();

			Log.e("offset:", offset + "");
			/**
			 * ����currentIndex(��ǰ����ҳ��)��position(��һ��ҳ��)�Լ�offset�� ����mTabLineIv����߾�
			 * ���������� ��3��ҳ��, �����ҷֱ�Ϊ0,1,2 0->1; 1->2; 2->1; 1->0
			 */

			if (currIndex == 0 && position == 0)// 0->1
			{
				lp.leftMargin = (int) (offset * (screenWidth * 1.0 / 2) + currIndex
						* (screenWidth / 2));
			} else if (currIndex == 1 && position == 0) // 1->0
			{
				lp.leftMargin = (int) (-(1 - offset) * (screenWidth * 1.0 / 2) + currIndex
						* (screenWidth / 2));
			}
			lp.width = screenWidth / 2;
			cursor.setLayoutParams(lp);
		}

		@Override
		public void onPageSelected(int position) {
			currIndex = position;
		}
	}

	public void showDir2(String str, ListView myListView) {
		myArrayList2 = new ArrayList<HashMap<String, Object>>();
		if (!str.equals("/")) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("name", "������һ��");
			map.put("path", str);
			myArrayList2.add(map);
		}
		File file = new File(str);
		File[] files = file.listFiles();
		for (int i = 0; i < files.length; i++) {
			if ((new File(files[i].getPath())).canRead()) {
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("name", files[i].getName());
				map.put("path", files[i].getPath());
				if ((new File(files[i].getPath())).isFile())
					map.put("kind", resource[1]);
				else
					map.put("kind", resource[0]);
				myArrayList2.add(map);
			}
		}
		SimpleAdapter mySimpleAdapter = new SimpleAdapter(this, myArrayList2,// ����Դ
				R.layout.list_item,// ListView�ڲ�����չʾ��ʽ�Ĳ����ļ�listitem.xml
				new String[] { "name", "kind" },// HashMap�е�����keyֵ
												// itemTitle��itemContent
				new int[] { R.id.itemTitle, R.id.image });/*
														 * �����ļ�listitem.xml�������id
														 * �����ļ��ĸ�����ֱ�ӳ�䵽HashMap�ĸ�Ԫ����
														 * ���������
														 */
		myListView.setAdapter(mySimpleAdapter);
	}

	private static void findbook(File file) {
		if (file == null)
			return;
		File[] files = file.listFiles();
		for (int i = 0; i < files.length; i++) {
			if (!files[i].isDirectory()) {
				if (files[i].getPath().contains(".txt")) {
					HashMap<String, Object> map = new HashMap<String, Object>();
					map.put("name", files[i].getName());
					map.put("path", files[i].getAbsolutePath());
					map.put("kind", resource[1]);
					myArrayList1.add(map);
					Log.e("docu", files[i].getName());
				}
			} else {
				findbook(files[i]);
			}
		}

	}
}
