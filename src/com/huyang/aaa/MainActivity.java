package com.huyang.aaa;


import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.example.DataBase.Book;
import com.example.DataBase.sqliteDataBase;
import com.example.adapterAndListener.listviewAdapter;
import com.example.linkWeb.Download;

import com.example.myView.LableDialog;
import com.example.myView.MyScrollView;
import com.example.myView.myRoundImageView;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechRecognizer;  
import com.iflytek.cloud.SpeechUtility;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.cloud.ui.RecognizerDialogListener;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;

public class MainActivity extends Activity {

	private ListView gridview;
	private ArrayList<String> book_list = new ArrayList<String>();
	private ArrayList<String> iconName;
	private ArrayList<String> iconPath;
	public static Activity main1;
	private MyScrollView myScrollView;
	private sqliteDataBase database;
	private myRoundImageView myRound;
	private myRoundImageView myPhoto;
	private Context context;
	private SpeechRecognizer mIat;
	private RecognizerDialog mIatDialog;
	private HashMap<String, String> hashMapTexts = new LinkedHashMap<String, String>();
	// public ArrayList<String> iconName = new ArrayList<String>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menucontent);
		myScrollView = (MyScrollView) findViewById(R.id.menucontent);
		database = new sqliteDataBase(this);
		ArrayList<String> flag = database.getAllBooksName();
		if (flag.size() == 0)
			database.insert(new Book("import", ".."));
		myScrollView = (MyScrollView) findViewById(R.id.menucontent);
		gridview = (ListView) this.findViewById(R.id.ListView);
		gridview.setFocusable(false);
		main1 = this;
		context = this;
        findViewById(R.id.title).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent1 = new Intent(MainActivity.this,
						qqLogActivity.class);
				startActivityForResult(intent1,2);
			}
		});
        findViewById(R.id.load).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent1 = new Intent(MainActivity.this,
						BookStoreActivity.class);
				startActivityForResult(intent1,5);
				overridePendingTransition(R.anim.bookstore_enter,0); 
			}
		});
        findViewById(R.id.download).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent1 = new Intent(MainActivity.this,
						DownloadManageActivity.class);
				startActivity(intent1);
			}
		});
        findViewById(R.id.remark).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				final LableDialog dialog=new LableDialog(context, R.style.LableDialog);
				dialog.setTitle("反馈信息：");
				
				dialog.setYesListener(new LableDialog.YesListener() {
					@Override
					public void onYesListener(String message) {
						// TODO Auto-generated method stub
                         //进行反馈内容上传...实现弹窗。
						Toast.makeText(context, "", Toast.LENGTH_SHORT).show();
						dialog.hide();
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
				dialog.show();
			}
		});
        findViewById(R.id.upload).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent1 = new Intent(MainActivity.this,
						UploadManange.class);
				startActivity(intent1);
			}
		});
        findViewById(R.id.delete).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				userPhoto photo = (userPhoto) getApplication();
				if(!photo.isLogstatue())
				{
					Toast.makeText(context, "请先登录", Toast.LENGTH_SHORT).show();
					return;
				}
				photo.clear();
				Toast.makeText(context, "当前账号已成功退出", Toast.LENGTH_SHORT).show();
				myPhoto = (myRoundImageView) findViewById(R.id.id_img1);
				myPhoto.setImageBitmap(photo.getBitmap());
				myRound = (myRoundImageView) findViewById(R.id.log_in);
				myRound.setImageBitmap(photo.getBitmap());
				((TextView) findViewById(R.id.petname)).setText(photo.getPetName());
			}
		});
		findViewById(R.id.myhomepage).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent1 = new Intent(MainActivity.this,
						MyHomePage.class);
				startActivityForResult(intent1,1);
			}
		});
        findViewById(R.id.modifieddata).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent1 = new Intent(MainActivity.this,
						ChangeMessage.class);
				//startActivity(intent1);
				startActivityForResult(intent1, 1);
			}
		});
        
        
        findViewById(R.id.recognize).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				SpeechUtility.createUtility(MainActivity.this, SpeechConstant.APPID + "=589696d9");  
				  
	            mIat=SpeechRecognizer.createRecognizer( MainActivity.this,null);
				mIatDialog = new RecognizerDialog(MainActivity.this, null);  
	            
				mIat.setParameter(SpeechConstant.DOMAIN, "iat"); 
				mIat.setParameter(SpeechConstant.LANGUAGE, "zh_cn");  
				mIat.setParameter(SpeechConstant.ACCENT, "mandarin"); 

				mIatDialog.setListener(new RecognizerDialogListener() {  
	  
	                @Override  
	                public void onResult(RecognizerResult results, boolean isLast) {  
	                    // TODO 自动生成的方法存根  
	                     Log.d("Result", results.getResultString());  
	                     //(1) 解析 json 数据<< 一个一个分析文本 >>  
	                    StringBuffer strBuffer = new StringBuffer();  
	                    try {  
	                        JSONTokener tokener = new JSONTokener(results.getResultString());  
	                        Log.i("TAG", "Test"+results.getResultString());  
	                        Log.i("TAG", "Test"+results.toString());  
	                        JSONObject joResult = new JSONObject(tokener);  
	  
	                        JSONArray words = joResult.getJSONArray("ws");  
	                        for (int i = 0; i < words.length(); i++) {  
	                            // 转写结果词，默认使用第一个结果  
	                            JSONArray items = words.getJSONObject(i).getJSONArray("cw");  
	                            JSONObject obj = items.getJSONObject(0);  
	                            strBuffer.append(obj.getString("w"));  
	  
	                        }  
	                    } catch (Exception e) {  
	                        e.printStackTrace();  
	                    }   

	                    String sn = null;  
	                      
	                    try {  
	                        JSONObject resultJson = new JSONObject(results.getResultString());  
	                        sn = resultJson.optString("sn");  
	                    } catch (JSONException e) {  
	                        e.printStackTrace();  
	                    }  
	  
	                    //(3) 解析语音文本<< 将文本叠加成语音分析结果  >>  
	                    hashMapTexts.put(sn, strBuffer.toString());  
	                    StringBuffer resultBuffer = new StringBuffer();  //最后结果  
	                    for (String key : hashMapTexts.keySet()) {  
	                        resultBuffer.append(hashMapTexts.get(key));  
	                    }  
	                    Toast.makeText(context, resultBuffer.toString(), Toast.LENGTH_LONG).show();
	  
	                }  
	                @Override  
	                public void onError(SpeechError error) {  
	                    // TODO 自动生成的方法存根  
	                    error.getPlainDescription(true);  
	                }
	            });  
				mIatDialog.show();  //显示对话框  
	       }  
		});
		myPhoto = (myRoundImageView) findViewById(R.id.id_img1);
		userPhoto photo = (userPhoto) getApplication();
		myPhoto.setImageBitmap(photo.getBitmap());
		myRound = (myRoundImageView) findViewById(R.id.log_in);
		myRound.setImageBitmap(photo.getBitmap());
		((TextView) findViewById(R.id.petname)).setText(photo.getPetName());
		myRound.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				myScrollView.toggle();
			}
		});
		showBookShelf();
	}
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == 1||resultCode == 2) {
			userPhoto photo = (userPhoto) getApplication();
			myPhoto.setImageBitmap(photo.getBitmap());
			((TextView) findViewById(R.id.petname)).setText(photo.getPetName());
			myRound.setImageBitmap(photo.getBitmap());
			myRound.setVisibility(View.VISIBLE);
		}
		if(resultCode==2)
		{
			if(((userPhoto) getApplication()).getImageurl()!=null||!((userPhoto) getApplication()).getImageurl().contains("null"))
			{
				DownImage download=new DownImage();
				download.execute(((userPhoto) getApplication()).getImageurl());
				return;
			}
		}
		if(resultCode==5)
		{
			showBookShelf();
			Log.i("result","is do");
		}
	}
	public void showBookShelf() {
		iconName = new ArrayList<String>();
		iconPath = new ArrayList<String>();
		iconName = database.getAllBooksName();
		iconPath = database.getAllBooksPath();
		book_list = (ArrayList<String>) getData();
		ArrayList<ArrayList<String>> book = new ArrayList<ArrayList<String>>();
		for (int i = 0; i <= (book_list.size() - 1) / 3; i++) {
			if (i == book_list.size() / 3) {
				ArrayList<String> temp = new ArrayList<String>();
				for (int j = 3 * i; j < book_list.size(); j++) {
					temp.add(book_list.get(j));
				}
				book.add(temp);
				book.add(new ArrayList<String>());
			} else {
				ArrayList<String> temp = new ArrayList<String>();
				for (int j = 3 * i; j < 3 * i + 3; j++) {
					temp.add(book_list.get(j));
				}
				book.add(temp);
				book.add(new ArrayList<String>());
			}
		}
		gridview.setAdapter(new listviewAdapter(context, book));
	}

	public List<String> getData() {

		ArrayList<String> book_list1 = new ArrayList<String>();
		for (int i = (iconName.size() - 1); i >= 0; i--) {
			String name = iconName.get(i);
			if (name.contains(".")) {
				int temp = name.indexOf(".");
				name = name.substring(0, temp);
			}
			if (name.length() >= 8)
				name = name.substring(0, 8) + "...";
			book_list1.add(name);
		}
		return book_list1;
	}

	public void onmyClick(int position) {
			if (position != iconName.size() - 1) {
				Intent intent = new Intent(MainActivity.this, reader.class);
				intent.putExtra("data",
						iconPath.get(iconPath.size() - position - 1));
				
				intent.putExtra("name",
						iconName.get(iconName.size() - position - 1));
				startActivity(intent);// 启动Read   Activity  
			} else {
				Intent intent = new Intent(MainActivity.this, importActivity.class);
				startActivity(intent);
			}
	}

	public void onMyClickLong(int position) {
		if (position != iconName.size() - 1) {
			database.delete(new Book(iconName.get(iconName.size() - position
					- 1), iconPath.get(iconPath.size() - position - 1)));
			
		}
		showBookShelf();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			new AlertDialog.Builder(context)
					.setTitle("退出")
					.setMessage("确定退出？")
					.setNegativeButton("取消", null)
					.setPositiveButton("确定",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									// TODO Auto-generated method stub
									MainActivity.this.finish();
								}
							}).show();
			return true;
		} else
			return super.onKeyDown(keyCode, event);
	}
	private class DownImage extends AsyncTask<String, Integer, Bitmap>
	{
		@Override
		protected Bitmap doInBackground(String... params) {
			// TODO Auto-generated method stub
			String url =params[0];
			Log.i("Main", url);
            if(url.equals("null")||url==null)
            	url="http://yangstudent.cn:1200/upload/upload/image/log.jpg";
            Bitmap tmpBitmap = null; 
            try {
            InputStream is = new java.net.URL(url).openStream();
            tmpBitmap = BitmapFactory.decodeStream(is);
            is.close();
            } catch (Exception e) {
            Toast.makeText(context, "网络出错", Toast.LENGTH_SHORT).show();
            }
            return tmpBitmap;
		}
		@Override
		protected void onPostExecute(Bitmap result) {
			// TODO Auto-generated method stub
			userPhoto photo = (userPhoto) getApplication();
			photo.setBitmap(result);
			myPhoto.setImageBitmap(photo.getBitmap());
			myRound.setImageBitmap(result);
			super.onPostExecute(result);
		}
	}
}