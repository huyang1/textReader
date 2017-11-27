package com.huyang.aaa;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.example.DataBase.sqliteDataBase;
import com.example.linkWeb.FormFile;

import com.example.myView.CircularProgressButton;
import com.example.myView.LableDialog;
import com.example.myView.SwipeListView;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import android.widget.TextView;

public class UploadManange extends Activity {
	private SwipeListView listview;
	final String BOUNDARY = "---------------------------7da2137580612"; // 数据分隔线
	final String endline = "--" + BOUNDARY + "--\r\n";// 数据结束标志

	private Context context;
	private int mScreemWidth;
	private ArrayList<HashMap<String, String>> upload_book = new ArrayList<HashMap<String, String>>();
	private userPhoto photo;
	private static ArrayList<View> view_list = new ArrayList<View>();
	private String descriable;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.upload_manage);
		context = this;
		WindowManager wm = (WindowManager) this
				.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics dm = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(dm);
		mScreemWidth = dm.widthPixels;
		photo = (userPhoto) getApplication();
		listview = (SwipeListView) findViewById(R.id.listview);
		init_upload_book(Environment.getExternalStorageDirectory()
				+ File.separator + "Read" + File.separator);
		listview.setAdapter(new MyListAdapter());
		listview.setOnTouchListener(null);
		findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				UploadManange.this.finish();
			}
		});
	}

	private void init_upload_book(String str) {
		upload_book.clear();
		File file = new File(str);
		File[] files = file.listFiles();
		if (files != null) {
			for (int i = 0; i < files.length; i++) {
				if ((new File(files[i].getPath())).canRead()) {
					HashMap<String, String> map = new HashMap<String, String>();
					map.put("name", files[i].getName());
					map.put("path", files[i].getPath());
					upload_book.add(map);
				}
			}
		}
	}
	private class MyListAdapter extends BaseAdapter {
		private int item2Width,item3Width;
		@Override
		public int getCount(){
			// TODO Auto-generated method stub
			return upload_book.size();
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return upload_book.get(arg0);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			// TODO Auto-generated method stub
			final ViewHolder viewHolder;
			if (convertView == null) {
				LayoutInflater inflater = LayoutInflater.from(context);
				convertView = inflater.inflate(R.layout.upload_item, null,
						false);
				viewHolder = new ViewHolder();
				viewHolder.bookPath = (TextView) convertView
						.findViewById(R.id.uploadbookPath);
				viewHolder.bookPath.setText(upload_book.get(position).get(
						"path"));
				viewHolder.bookName = (TextView) convertView
						.findViewById(R.id.uploadbookName);
				viewHolder.bookName.setText(upload_book.get(position).get(
						"name"));
				item2Width=convertView.findViewById(R.id.uploadprecent).getLayoutParams().width;
				item3Width=convertView.findViewById(R.id.downloaditem3).getLayoutParams().width;
				convertView.findViewById(R.id.downloaditem1).getLayoutParams().width=mScreemWidth-item2Width-item3Width;
				viewHolder.uploadtxt = (TextView) convertView
						.findViewById(R.id.uploadtxt);
				viewHolder.precent = (CircularProgressButton) convertView
						.findViewById(R.id.uploadprecent);
				viewHolder.precent
						.setOnClickListener(new View.OnClickListener() {
							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								if (viewHolder.precent.getProgress() == 0) {
									final LableDialog dialog=new LableDialog(context, R.style.LableDialog);
									dialog.show();
									dialog.setYesListener(new LableDialog.YesListener() {
										@Override
										public void onYesListener(String message) {
											// TODO Auto-generated method stub
											descriable=message;
											Toast.makeText(context, descriable, Toast.LENGTH_SHORT).show();
											dialog.hide();
											UploadTask uploadtask = new UploadTask();
											uploadtask.execute(position);
										}
									});
									dialog.setNoListener(new LableDialog.NoListener() {
										@Override
										public void onNoListener() {
											// TODO Auto-generated method stub
											dialog.hide();
											return;
										}
									});
									
								}
							}
						});
				viewHolder.addVIew=(TextView) convertView.findViewById(R.id.add);
				viewHolder.deleteView=(TextView) convertView.findViewById(R.id.delete);
				viewHolder.deleteView.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						(new sqliteDataBase(context)).delete(upload_book.get(position).get("name"));
						File file=new File(upload_book.get(position).get("path"));
						(new sqliteDataBase(context)).deleteBookContent(upload_book.get(position).get("name"));
						file.delete();
						init_upload_book(Environment.getExternalStorageDirectory()
								+ File.separator + "Read" + File.separator);
						notifyDataSetChanged();
						listview.recovery();
					}
				});
				convertView.setTag(viewHolder);
			} else
			{
				viewHolder = (ViewHolder) convertView.getTag();
				viewHolder.bookPath = (TextView) convertView
						.findViewById(R.id.uploadbookPath);
				viewHolder.bookPath.setText(upload_book.get(position).get(
						"path"));
				viewHolder.bookName = (TextView) convertView
						.findViewById(R.id.uploadbookName);
				viewHolder.bookName.setText(upload_book.get(position).get(
						"name"));
				convertView.setTag(viewHolder);
			}
				
			convertView.setTag(R.id.listview, position);
			view_list.add(convertView);
			return convertView;
		}
	}

	private class ViewHolder {
		TextView bookName;
		TextView bookPath;
		CircularProgressButton precent;
		TextView uploadtxt;
		TextView addVIew,deleteView;
	}

	private class UploadTask extends AsyncTask<Integer, Integer, Void> {
		private int pos = -1;
		private long upload_num = 0;

		@Override
		protected Void doInBackground(Integer... params) {
			// TODO Auto-generated method stub
			int position = params[0];
			pos = position;
			HashMap<String, String> uploadmessage = new HashMap<String, String>();
			uploadmessage.put("fileName", upload_book.get(position).get("name")
					+ ".txt");
			uploadmessage.put("uploader", /* photo.getPetName() */"111");
			File file = new File(upload_book.get(position).get("path"));
			FormFile formfile = new FormFile(
					Long.toString(new Date().getTime()) + ".txt", file, "txt",
					"application/octet-stream");
			int fileDataLength = 0;
			StringBuilder fileExplain = new StringBuilder();
			fileExplain.append("--");
			fileExplain.append(BOUNDARY);
			fileExplain.append("\r\n");
			fileExplain.append("Content-Disposition: form-data;name=\""
					+ formfile.getParameterName() + "\";filename=\""
					+ formfile.getFilename() + "\"\r\n");
			fileExplain.append("Content-Type: " + formfile.getContentType()
					+ "\r\n\r\n");
			fileExplain.append("\r\n");
			fileDataLength += fileExplain.length();
			fileDataLength += formfile.getFile().length();
			StringBuilder textEntity = new StringBuilder();
			for (Map.Entry<String, String> entry : uploadmessage.entrySet()) {// 构造文本类型参数的实体数据
				textEntity.append("--");
				textEntity.append(BOUNDARY);
				textEntity.append("\r\n");
				textEntity.append("Content-Disposition: form-data; name=\""
						+ entry.getKey() + "\"\r\n\r\n");
				textEntity.append(entry.getValue());
				textEntity.append("\r\n");
			}
			int dataLength = textEntity.toString().getBytes().length
					+ fileDataLength + endline.getBytes().length;
			try {
				URL url = new URL(
						"http://yangstudent.cn:1200/upload/uploadBook.do");
				int port = url.getPort() == -1 ? 80 : url.getPort();
				Socket socket = new Socket(
						InetAddress.getByName(url.getHost()), port);
				OutputStream outStream = socket.getOutputStream();
				String requestmethod = "POST " + url.getPath()
						+ " HTTP/1.1\r\n";
				outStream.write(requestmethod.getBytes());
				String accept = "Accept: image/gif, image/jpeg, image/pjpeg, image/pjpeg, application/x-shockwave-flash, application/xaml+xml, application/vnd.ms-xpsdocument, application/x-ms-xbap, application/x-ms-application, application/vnd.ms-excel, application/vnd.ms-powerpoint, application/msword, */*\r\n";
				outStream.write(accept.getBytes());
				String language = "Accept-Language: zh-CN\r\n";
				outStream.write(language.getBytes());
				String contenttype = "Content-Type: multipart/form-data; boundary="
						+ BOUNDARY + "\r\n";
				outStream.write(contenttype.getBytes());
				String contentlength = "Content-Length: " + dataLength + "\r\n";
				outStream.write(contentlength.getBytes());
				String alive = "Connection: Keep-Alive\r\n";
				outStream.write(alive.getBytes());
				String host = "Host: " + url.getHost() + ":" + port + "\r\n";
				outStream.write(host.getBytes());
				outStream.write("\r\n".getBytes());
				outStream.write(textEntity.toString().getBytes());
				StringBuilder fileEntity = new StringBuilder();
				fileEntity.append("--");
				fileEntity.append(BOUNDARY);
				fileEntity.append("\r\n");
				fileEntity.append("Content-Disposition: form-data;name=\""
						+ formfile.getParameterName() + "\";filename=\""
						+ formfile.getFilename() + "\"\r\n");
				fileEntity.append("Content-Type: " + formfile.getContentType()
						+ "\r\n\r\n");
				outStream.write(fileEntity.toString().getBytes());
				byte[] buffer = new byte[1024 * 4];
				int len = 0;
				FileInputStream fis = new FileInputStream(formfile.getFile()
						.getAbsolutePath());
				while ((len = fis.read(buffer)) != -1) {
					outStream.write(buffer, 0, len);
					upload_num += len;
					publishProgress((int) ((upload_num / (float) dataLength) * 100));
				}
				fis.close();
				outStream.write("\r\n".getBytes());
				// 下面发送数据结束标志，表示数据已经结束
				outStream.write(endline.getBytes());
				outStream.flush();
				outStream.close();

				socket.close();
			} catch (Exception e) {

				e.printStackTrace();
			}

			return null;
		}

		@Override
		protected void onProgressUpdate(Integer... values) {
			// TODO Auto-generated method stub
			super.onProgressUpdate(values);
			View view = null;
			if (pos < view_list.size())
				view = view_list.get(pos);
			if (view != null) {
				ViewHolder viewHolder = (ViewHolder) view.getTag();
				viewHolder.precent.setProgress((values[0] + 1) > 100 ? 100
						: (values[0] + 1));
				viewHolder.uploadtxt.setText(((values[0] + 1) > 100 ? 100
						: (values[0] + 1)) + "%");

			}
			// Log.i("progress",""+values[0]);
		}

	}

}
