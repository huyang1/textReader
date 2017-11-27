package com.huyang.aaa;

import java.io.File;
import java.util.HashMap;
import com.example.bean.uploadPhotoBean;
import com.example.linkWeb.FormFile;
import com.example.linkWeb.Upload;
import com.example.linkWeb.uploadTask;
import com.example.myView.myRoundImageView;
import android.app.Activity;
import android.content.Intent;

import android.graphics.Bitmap;

import android.os.Bundle;
import android.os.StrictMode;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


public class logActivity extends Activity implements OnClickListener{

	private String photoPath =null;
	private ImageView myphoto;

	private Bitmap bp = null;
    //private Intent intent;
	private EditText username,pwd;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()       
        .detectDiskReads()       
        .detectDiskWrites()       
        .detectNetwork()   // or .detectAll() for all detectable problems       
        .penaltyLog()       
        .build());       
         StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()       
        .detectLeakedSqlLiteObjects()    
        .penaltyLog()       
        .penaltyDeath()       
        .build());
		setContentView(R.layout.onlog);
		//intent=getIntent();
		//intent.putExtra("touch", false);
		myphoto = (myRoundImageView) findViewById(R.id.photo);
		userPhoto photo = (userPhoto) getApplication();
		myphoto.setImageBitmap(photo.getBitmap());
		username=(EditText) findViewById(R.id.editText1);
		pwd=(EditText) findViewById(R.id.editText2);

		Button login=(Button) findViewById(R.id.button1);
		Button regist=(Button) findViewById(R.id.button2);
		login.setOnClickListener(this);
		regist.setOnClickListener(this);
	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		//cursor.close();
		super.onDestroy();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int action=v.getId();
		switch(action)
		{
		case R.id.button1://µÇÂ¼
			bp=null;
			login();
			break;
		case R.id.button2://×¢²á
			bp=null;
			register();
			
			break;
		}
	}
	public void login()
	{
		if(!Upload.isNetworkAvailable(this))
		{
			Toast.makeText(this, "ÍøÂç³ö´í", Toast.LENGTH_SHORT).show();
			return;
		}

		String requestUrl = "http://yangstudent.cn:1200/upload/upload/execute.do";
		HashMap<String, String> params=new HashMap<String, String>();
		//Toast.makeText(logActivity.this, username.getText().toString(), Toast.LENGTH_SHORT).show();
		params.put("username", username.getText().toString());
		params.put("pwd", pwd.getText().toString());
		params.put("type", "login");
		params.put("fileName", username.getText().toString()+".jpg");
		if(photoPath!=null)
		{
			File file=new File(photoPath);
			FormFile formfile = new FormFile(username.getText().toString()+".jpg", file, "image", "application/octet-stream");
			uploadPhotoBean data=new uploadPhotoBean(requestUrl, params, formfile);
			uploadTask upload=new uploadTask(logActivity.this);
			upload.execute(data);
		}
		else
		{
			uploadPhotoBean data=new uploadPhotoBean(requestUrl, params, null);
			uploadTask upload=new uploadTask(logActivity.this);
			upload.execute(data);
		}	
	}
	public void register()
	{
		if(!Upload.isNetworkAvailable(this))
		{
			Toast.makeText(this, "ÍøÂç³ö´í", Toast.LENGTH_SHORT).show();
			return;
		}
		//intent.putExtra("touch", true);
		String requestUrl = "http://yangstudent.cn:1200/upload/upload/execute.do";
		HashMap<String, String> params=new HashMap<String, String>();
		params.put("username", username.getText().toString());
		params.put("pwd", pwd.getText().toString());
		params.put("type", "register");
		params.put("fileName", username.getText().toString()+".jpg");
		if(photoPath!=null)
		{
			File file=new File(photoPath);
			FormFile formfile = new FormFile(username.getText().toString()+".jpg", file, "image", "application/octet-stream");
			uploadPhotoBean data=new uploadPhotoBean(requestUrl, params, formfile);
			uploadTask upload=new uploadTask(logActivity.this);
			upload.execute(data);
		}
		else
		{
			uploadPhotoBean data=new uploadPhotoBean(requestUrl, params, null);
			uploadTask upload=new uploadTask(logActivity.this);
			upload.execute(data);
		}
		userPhoto user=(userPhoto)getApplicationContext();
        user.setUsername(username.getText().toString());
        user.setImageurl(null);
        user.setBitmap(bp);
        user.setLogstatue(true);
	}
}
