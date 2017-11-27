package com.huyang.aaa;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.HashMap;

import com.example.bean.uploadPhotoBean;
import com.example.linkWeb.FormFile;
import com.example.linkWeb.Upload;
import com.example.linkWeb.uploadTask;
import com.example.myView.myRoundImageView;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;

import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ChangeMessage extends Activity implements OnClickListener {
	private static final String requestUrl = "http://yangstudent.cn:1200/upload/upload/execute.do";
	private static final int SELECT_PIC_BY_TACK_PHOTO = 9;
	private static final int SELECT_PIC_BY_CHOOSE_PHOTO = 10;
	private HashMap<String, String> params = new HashMap<String, String>();
	private String photoPath = null;
	private ImageView myphoto;
	private Uri photoUri;
	private Bitmap bp = null;
	private AlertDialog alert;
	private Cursor cursor;
    private userPhoto photo;
    private changMessageStatue listener=new changMessageStatue() {
		
		@Override
		public void getStatue(boolean statue) {
			// TODO Auto-generated method stub
			if(statue)
			{
				if(photoPath!=null)
					photo.setBitmap(bp);
				if(params.get("petname")!=null)
					photo.setPetName(params.get("petname"));
				if(params.get("sex")!=null)
					photo.setSex(params.get("sex"));
				if(params.get("brith")!=null)
					photo.setBrith(params.get("brith"));
				if(params.get("country")!=null)
					photo.setCountry(params.get("country"));
			}
		}
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.changemessage);

		myphoto = (myRoundImageView) findViewById(R.id.userphoto);
		photo = (userPhoto) getApplication();
		myphoto.setImageBitmap(photo.getBitmap());
		((TextView) findViewById(R.id.petname)).setText(photo.getPetName());
		((TextView) findViewById(R.id.username)).setText(photo.getUsername());
		((TextView) findViewById(R.id.sex)).setText(photo.getSex());
		((TextView) findViewById(R.id.brith)).setText(photo.getBrith());
		((TextView) findViewById(R.id.country)).setText(photo.getCountry());
		findViewById(R.id.item2).setOnClickListener(this);
		findViewById(R.id.item3).setOnClickListener(this);
		findViewById(R.id.item4).setOnClickListener(this);
		findViewById(R.id.item5).setOnClickListener(this);
		findViewById(R.id.item6).setOnClickListener(this);
		findViewById(R.id.upload).setOnClickListener(this);
		findViewById(R.id.back).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ChangeMessage.this.setResult(1, getIntent());
				ChangeMessage.this.finish();
			}
		});
	}

	@Override
	public void onClick(View v) {
		if(!photo.isLogstatue())
		{
			Toast.makeText(ChangeMessage.this, "���ȵ�¼��",
					Toast.LENGTH_SHORT).show();
			return ;
		}
		// TODO Auto-generated method stub
		int action = v.getId();
		switch (action) {
		case R.id.item2:
			doDialog();
			break;
		case R.id.item3:
			Intent intent = new Intent(ChangeMessage.this, itemActivity.class);
			intent.putExtra("title", "�ǳ�");
			intent.putExtra("content",
					((TextView) findViewById(R.id.petname)).getText());
			startActivityForResult(intent, 3);
			break;
		case R.id.item4:
			Intent intent1 = new Intent(ChangeMessage.this, itemActivity.class);
			intent1.putExtra("title", "�Ա�");
			intent1.putExtra("content",
					((TextView) findViewById(R.id.sex)).getText());
			startActivityForResult(intent1, 4);
			break;
		case R.id.item5:
			Intent intent2 = new Intent(ChangeMessage.this, itemActivity.class);
			intent2.putExtra("title", "����");
			intent2.putExtra("content",
					((TextView) findViewById(R.id.brith)).getText());
			startActivityForResult(intent2, 5);
			break;
		case R.id.item6:
			Intent intent3 = new Intent(ChangeMessage.this, itemActivity.class);
			intent3.putExtra("title", "����");
			intent3.putExtra("content",
					((TextView) findViewById(R.id.country)).getText());
			startActivityForResult(intent3, 6);
			break;
		case R.id.upload:
			if(!Upload.isNetworkAvailable(this))
			{
				Toast.makeText(this, "�������", Toast.LENGTH_SHORT).show();
				break;
			}
			params.put("username", photo.getUsername());
			HashMap<String, String> uploadmessage = new HashMap<String, String>();
			uploadmessage.put("username", params.get("username"));
			uploadmessage.put("petname", params.get("petname"));
			uploadmessage.put("fileName", params.get("username")+".jpg");
			uploadmessage.put("sex", params.get("sex"));
			uploadmessage.put("brith", params.get("brith"));
			uploadmessage.put("country", params.get("country"));
			uploadmessage.put("type", "change");
			if(photoPath!=null)
			{
				File file=new File(photoPath);
				FormFile formfile = new FormFile(params.get("username")+".jpg", file, "image", "application/octet-stream");
				
				uploadPhotoBean data=new uploadPhotoBean(requestUrl, uploadmessage, formfile);
				uploadTask upload=new uploadTask(ChangeMessage.this);
				upload.setChangeStatueListener(listener);//�ϴ��ɹ��޸ı�־
				upload.execute(data);
			}
			else
			{
				uploadPhotoBean data=new uploadPhotoBean(requestUrl, uploadmessage, null);
				uploadTask upload=new uploadTask(ChangeMessage.this);
				upload.setChangeStatueListener(listener);
				upload.execute(data);
			}
			ChangeMessage.this.setResult(1, getIntent());
			ChangeMessage.this.finish();
			break;
		}
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		
		switch (requestCode) {
		case SELECT_PIC_BY_TACK_PHOTO:
			if(resultCode==Activity.RESULT_OK)
			{
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
					try    
	                {    
	                    //4.0���ϵİ汾�α���Զ��رջ��Զ��ر� 
	                    if(Integer.parseInt(Build.VERSION.SDK) < 14)    
	                    {    
	                        //ֻ��4.0���²���Ҫ�ֶ��ر�  
	                        cursor.close();    
	                    }    
	                }catch(Exception e)    
	                {    
	                   
	                }    
				}
				bp = BitmapFactory.decodeFile(photoPath);
				if (photoPath != null
						&& (photoPath.endsWith(".png")
								|| photoPath.endsWith(".PNG")
								|| photoPath.endsWith(".jpg") || photoPath
									.endsWith(".JPG"))) {
					bp = imageZoom(bp);// ��ͼƬ����ѹ����Ϊintent����bitmap���ܴ���40K
					
					myphoto.setImageBitmap(bp);
					userPhoto photo = (userPhoto) getApplication();
					
					params.put("photoPath", photoPath);
				} else
					Toast.makeText(this, "ѡ��ͼƬ����", Toast.LENGTH_LONG).show();
			}
			break;
		case SELECT_PIC_BY_CHOOSE_PHOTO:
			if(resultCode==Activity.RESULT_OK)
			{
				if (data == null || "".equals(data)) {
					Toast.makeText(this, "ѡ��ͼƬ�ļ�����", Toast.LENGTH_LONG).show();
					return;
				}
				photoUri = data.getData();

				String[] pojo1 = { MediaStore.Images.Media.DATA };
				cursor = managedQuery(photoUri, pojo1, null, null, null);
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
					int columnIndex = cursor.getColumnIndexOrThrow(pojo1[0]);
					cursor.moveToFirst();
					photoPath = cursor.getString(columnIndex);
					try    
	                {    
						//4.0���ϵİ汾�α���Զ��رջ��Զ��ر�     
	                    if(Integer.parseInt(Build.VERSION.SDK) < 14)    
	                    {    
	                        //ֻ��4.0���²���Ҫ�ֶ��ر�  
	                        cursor.close();    
	                    }    
	                }catch(Exception e)    
	                {    
	                    
	                }    
				}
				bp = BitmapFactory.decodeFile(photoPath);
				if (photoPath != null
						&& (photoPath.endsWith(".png")
								|| photoPath.endsWith(".PNG")
								|| photoPath.endsWith(".jpg") || photoPath
									.endsWith(".JPG"))) {
					bp = imageZoom(bp);// ��ͼƬ����ѹ����Ϊintent����bitmap���ܴ���40K
					
					myphoto.setImageBitmap(bp);
					userPhoto photo = (userPhoto) getApplication();
				
					params.put("photoPath", photoPath);
				} else
					Toast.makeText(this, "ѡ��ͼƬ����", Toast.LENGTH_LONG).show();
			}
			break;
		case 3:
			if(data!=null&&resultCode==8)
			{
				String message = data.getStringExtra("message");
				((TextView) findViewById(R.id.petname)).setText(message);
				userPhoto photo = (userPhoto) getApplication();
				photo.setPetName(message);
				params.put("petname", message);
			}
			break;
		case 4:
			if(data!=null&&resultCode==8)
			{
				String message2 = data.getStringExtra("message");
				((TextView) findViewById(R.id.sex)).setText(message2);
				params.put("sex", message2);
				
			}
			break;
		case 5:
			if(data!=null&&resultCode==8)
			{
				
				String message3 = data.getStringExtra("message");
				((TextView) findViewById(R.id.brith)).setText(message3);
				params.put("brith", message3);
			}
			break;
		case 6:
			if(data!=null&&resultCode==8)
			{
				String message4 = data.getStringExtra("message");
				((TextView) findViewById(R.id.country)).setText(message4);
				params.put("country", message4);
			}
			break;
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	public void doDialog() {

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("ѡ���ȡͼƬ��ʽ")
				.setCancelable(false)
				.setPositiveButton("����", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						takephoto();
					}
				})
				.setNegativeButton("�����",
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
		Intent intent = new Intent(Intent.ACTION_PICK,
				android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		startActivityForResult(intent, SELECT_PIC_BY_CHOOSE_PHOTO);
	}
	private Bitmap imageZoom(Bitmap bip) {
		// ͼƬ�������ռ� ��λ��KB
		double maxSize = 20.00;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bip.compress(Bitmap.CompressFormat.JPEG, 100, baos);
		byte[] b = baos.toByteArray();
		// ���ֽڻ���KB
		double mid = b.length / 1024;
		// �ж�bitmapռ�ÿռ��Ƿ�����������ռ� ���������ѹ�� С����ѹ��
		if (mid > maxSize) {
			// ��ȡbitmap��С ����������С�Ķ��ٱ�
			double i = mid / maxSize;
			// ��ʼѹ�� �˴��õ�ƽ���� ������͸߶�ѹ������Ӧ��ƽ������
			// ��1.���̶ֿȺ͸߶Ⱥ�ԭbitmap����һ�£�ѹ����Ҳ�ﵽ������Сռ�ÿռ�Ĵ�С��
			bip = zoomImage(bip, bip.getWidth() / Math.sqrt(i), bip.getHeight()
					/ Math.sqrt(i));
		}
		return bip;

	}

	public static Bitmap zoomImage(Bitmap bgimage, double newWidth,
			double newHeight) {
		// ��ȡ���ͼƬ�Ŀ�͸�
		float width = bgimage.getWidth();
		float height = bgimage.getHeight();
		// ��������ͼƬ�õ�matrix����
		Matrix matrix = new Matrix();
		// ������������
		float scaleWidth = ((float) newWidth) / width;
		float scaleHeight = ((float) newHeight) / height;
		// ����ͼƬ����
		matrix.postScale(scaleWidth, scaleHeight);
		Bitmap bitmap = Bitmap.createBitmap(bgimage, 0, 0, (int) width,
				(int) height, matrix, true);
		return bitmap;
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
             
			return true;
		} else
			return super.onKeyDown(keyCode, event);
	}
}