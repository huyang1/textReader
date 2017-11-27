package com.huyang.aaa;
import java.io.File;
import java.util.HashMap;
import com.example.linkWeb.DownloadTask;
import com.example.linkWeb.DownloadTask.Callback;
import com.huyang.aaa.DownloadManageActivity.DownloadBook;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class BookInformationActivity extends Activity implements
		OnClickListener {
	private boolean[] flag = { false, false, false, false, false };
	private Intent intent;
    private Bitmap bitmap;
    private Context context;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		context=this;
		intent = getIntent();
		setContentView(R.layout.book_information);
		((TextView) findViewById(R.id.bookName)).setText(intent
				.getStringExtra("bookName"));
		((TextView) findViewById(R.id.author)).setText("���ߣ�  "+intent
				.getStringExtra("author"));
		((TextView) findViewById(R.id.uploader)).setText("�ϴ��ߣ� "+intent
				.getStringExtra("uploader"));
		((TextView) findViewById(R.id.describle)).setText("  ������ "+intent
				.getStringExtra("describe"));
		((TextView) findViewById(R.id.download)).setOnClickListener(this);
		((ImageView) findViewById(R.id.back)).setOnClickListener(this);
		((ImageView) findViewById(R.id.star1)).setOnClickListener(this);
		((ImageView) findViewById(R.id.star2)).setOnClickListener(this);
		((ImageView) findViewById(R.id.star3)).setOnClickListener(this);
		((ImageView) findViewById(R.id.star4)).setOnClickListener(this);
		((ImageView) findViewById(R.id.star5)).setOnClickListener(this);

		DownloadTask download = new DownloadTask();
		Drawable drawable = download.loadDrawble(
				intent.getStringExtra("bookImageUrl"), new Callback() {
					@Override
					public void imagecache(Drawable drawble) {
						// TODO Auto-generated method stub
						((ImageView) findViewById(R.id.book))
								.setImageDrawable(drawble);
						BitmapDrawable bd = (BitmapDrawable) drawble;
						bitmap=bd.getBitmap();
					}
				});
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int action = v.getId();
		switch (action) {
		case R.id.star1:
			if (!flag[0]) {
				((ImageView) findViewById(R.id.star1))
						.setImageResource(R.drawable.goldstar);
				flag[0] = true;
			} else {
				((ImageView) findViewById(R.id.star1))
						.setImageResource(R.drawable.star);
				flag[0] = false;
			}
			break;
		case R.id.star2:
			if (!flag[1]) {
				((ImageView) findViewById(R.id.star2))
						.setImageResource(R.drawable.goldstar);
				flag[1] = true;
			} else {
				((ImageView) findViewById(R.id.star2))
						.setImageResource(R.drawable.star);
				flag[1] = false;
			}
			break;
		case R.id.star3:
			if (!flag[2]) {
				((ImageView) findViewById(R.id.star3))
						.setImageResource(R.drawable.goldstar);
				flag[2] = true;
			} else {
				((ImageView) findViewById(R.id.star3))
						.setImageResource(R.drawable.star);
				flag[2] = false;
			}
			break;
		case R.id.star4:
			if (!flag[3]) {
				((ImageView) findViewById(R.id.star4))
						.setImageResource(R.drawable.goldstar);
				flag[3] = true;
			} else {
				((ImageView) findViewById(R.id.star4))
						.setImageResource(R.drawable.star);
				flag[3] = false;
			}
			break;
		case R.id.star5:
			if (!flag[4]) {
				((ImageView) findViewById(R.id.star5))
						.setImageResource(R.drawable.goldstar);
				flag[4] = true;
			} else {
				((ImageView) findViewById(R.id.star5))
						.setImageResource(R.drawable.star);
				flag[4] = false;
			}
			break;
		case R.id.back:
			BookInformationActivity.this.finish();
			break;
		case R.id.download:
			userPhoto photo = (userPhoto) getApplication();
			if (!photo.isLogstatue()) {
				Toast.makeText(this, "���ȵ�¼", Toast.LENGTH_SHORT).show();
				return;
			}
			String dirName = "";
			dirName = Environment.getExternalStorageDirectory() +File.separator+ "Read"+File.separator;
			File f = new File(dirName);
			if (!f.exists()) {
				f.mkdir();
			}
			else
			{
				Log.i("Read","exit!");
			}
			String downloadPath = dirName + intent.getStringExtra("bookName");
			// ����ͼ�����ص�����·��
			HashMap<String, String> params=new HashMap<String, String>();
			params.put("path", downloadPath);
			params.put("url", intent.getStringExtra("bookUrl"));
			Log.i("PATH",intent.getStringExtra("bookUrl"));
			params.put("bookName", intent.getStringExtra("bookName"));
			params.put("bookImageUrl", intent.getStringExtra("bookImageUrl"));
			Toast.makeText(this, "���ؿ�ʼ,�����������Ĳ鿴...", Toast.LENGTH_SHORT).show();
			if(bitmap!=null)
				Log.i("message","bitmap �ѷ���DownloadBook��");
			DownloadBook download=new DownloadBook(context,bitmap);
			download.execute(params);
			break;
		}
	}
}
