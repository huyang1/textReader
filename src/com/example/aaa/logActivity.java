package com.example.aaa;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;

import com.example.myView.myRoundImageView;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.Toast;

public class logActivity extends Activity {
	private static final int SELECT_PIC_BY_TACK_PHOTO = 1;
	private static final int SELECT_PIC_BY_CHOOSE_PHOTO = 2;
	private String photoPath = new String();
	private ImageView myphoto;
	private Uri photoUri;
	private Intent intent;
	private Bitmap bp = null;
	private AlertDialog alert;
	private Cursor cursor;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.onlog);
		intent = getIntent();
		myphoto = (myRoundImageView) findViewById(R.id.photo);
		userPhoto photo = (userPhoto) getApplication();
		myphoto.setImageBitmap(photo.getBitmap());
		myphoto.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				doDialog();
			}
		});
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		
	    updataphoto(requestCode, data);

		super.onActivityResult(requestCode, resultCode, data);
	}

	public void doDialog() {

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("选择获取图片方式")
				.setCancelable(false)
				.setPositiveButton("拍照", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						takephoto();
					}
				})
				.setNegativeButton("从相册",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								choosephoto();
							}
						});
		builder.setCancelable(true);
		alert = builder.create();
		alert.show();
	}

	public void takephoto() {
		String SDPath = Environment.getExternalStorageState();
		if (SDPath.equals(Environment.MEDIA_MOUNTED))
			;
		{
			Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
			ContentValues values = new ContentValues();
			photoUri = this.getContentResolver().insert(
					MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
			intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, photoUri);
			startActivityForResult(intent, SELECT_PIC_BY_TACK_PHOTO);
		}
	}

	public void choosephoto() {
		/*
		 * Intent intent = new Intent(); intent.setType("image/*");
		 * intent.setAction(Intent.ACTION_GET_CONTENT);
		 */
		Intent intent = new Intent(Intent.ACTION_PICK,
				android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		startActivityForResult(intent, SELECT_PIC_BY_CHOOSE_PHOTO);
	}

	public void updataphoto(int requestCode, Intent data) {
		if (requestCode == SELECT_PIC_BY_CHOOSE_PHOTO) {
			if (data == null||"".equals(data)) {
				Toast.makeText(this, "选择图片文件出错", Toast.LENGTH_LONG).show();
				return;
			}
			photoUri = data.getData();
		}
		String[] pojo = { MediaStore.Images.Media.DATA };
		cursor = managedQuery(photoUri, pojo, null, null, null);
		if (cursor == null) {
			String str = photoUri.toString();
			System.out.println(str);
			if (str.contains("file:///")) {
				cursor.close();
				str = str.substring(7);
				photoPath = str;
			}
		}
		if (cursor != null) {
			int columnIndex = cursor.getColumnIndexOrThrow(pojo[0]);
			cursor.moveToFirst();
			photoPath = cursor.getString(columnIndex);
			cursor.close();
		}
		bp = BitmapFactory.decodeFile(photoPath);
		// bp=MediaStore.Images.Media.getBitmap(getContentResolver(), photoUri);
		if (photoPath != null
				&& (photoPath.endsWith(".png") || photoPath.endsWith(".PNG")
						|| photoPath.endsWith(".jpg") || photoPath
							.endsWith(".JPG"))) {
			bp=imageZoom(bp);//对图片进行压缩因为intent传递bitmap不能大于40K
			intent.putExtra("bitmap", bp);
			userPhoto photo = (userPhoto) getApplication();
			photo.setBitmap(bp);
			this.setResult(1, intent);
			myphoto.setImageBitmap(photo.getBitmap());
			logActivity.this.finish();
		} else
			Toast.makeText(this, "选择图片出错", Toast.LENGTH_LONG).show();

	}

	private Bitmap imageZoom(Bitmap bip) {
		// 图片允许最大空间 单位：KB
		double maxSize = 30.00;
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

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		//cursor.close();
		super.onDestroy();
	}

	/*
	 * @Override public boolean onKeyDown(int keyCode, KeyEvent event) { // TODO
	 * Auto-generated method stub if(keyCode==KeyEvent.KEYCODE_BACK&&!flag) {
	 * logActivity.this.finish(); }
	 * 
	 * else if(keyCode==KeyEvent.KEYCODE_BACK&&flag) { flag=false;
	 * alert.dismiss(); } return super.onKeyDown(keyCode, event); }
	 */
}
