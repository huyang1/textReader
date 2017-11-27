package com.huyang.aaa;

import org.json.JSONException;
import org.json.JSONObject;
import com.tencent.connect.UserInfo;
import com.tencent.connect.auth.QQToken;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.SyncStateContract.Constants;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class qqLogActivity extends Activity {
	private static final String APP_ID = "1105759463";
	private Tencent mTencent;
    private IUiListener uilistener;
    private TextView content;
    private Intent intent;
    private Context context;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.qqlogon);
		intent=getIntent();
		context=this;
		intent.putExtra("touch", false);
		// Tencent类是SDK的主要实现类，开发者可通过Tencent类访问腾讯开放的OpenAPI。
		// 其中APP_ID是分配给第三方应用的appid，类型为String。
		mTencent = Tencent.createInstance(APP_ID, this.getApplicationContext());
		// 1.4版本:此处需新增参数，传入应用程序的全局context，可通过activity的getApplicationContext方法获取
		// 初始化视图
		uilistener=new IUiListener() {
			
			@Override
			public void onError(UiError arg0) {
				// TODO Auto-generated method stub
				Toast.makeText(qqLogActivity.this, arg0.toString(),
						Toast.LENGTH_SHORT).show();
			}
			
			@Override
			public void onComplete(Object arg0) {
				// TODO Auto-generated method stub
				JSONObject object = (JSONObject) arg0;

                try {

                    String accessToken = object.getString("access_token");

                    String expires = object.getString("expires_in");

                    String openID = object.getString("openid");

                    mTencent.setAccessToken(accessToken, expires);

                    mTencent.setOpenId(openID);

                } catch (JSONException e) {

                    e.printStackTrace();

                }
			}
			
			@Override
			public void onCancel() {
				// TODO Auto-generated method stub
				Toast.makeText(qqLogActivity.this, "cancel", Toast.LENGTH_SHORT)
				.show();
			}
		};
		((Button)findViewById(R.id.qqlogin)).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mTencent.login(qqLogActivity.this, "all", uilistener);
			}
		});
         ((Button)findViewById(R.id.readlogin)).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//intent.putExtra("touch", true);
				Intent intent1 = new Intent(qqLogActivity.this,
						logActivity.class);
				startActivity(intent1);
				qqLogActivity.this.setResult(2, intent);
				qqLogActivity.this.finish();
			}
		});
		
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
	            if (resultCode == -1) {

	                Tencent.onActivityResultData(requestCode, resultCode, data, uilistener);

	                Tencent.handleResultData(data, uilistener);

	                UserInfo info = new UserInfo(this, mTencent.getQQToken());

	                info.getUserInfo(new IUiListener() {

	                    @Override

	                    public void onComplete(Object o) {

	                        try {

	                            JSONObject info = (JSONObject) o;

	                            String nickName = info.getString("nickname");//获取用户昵称
                                String place =info.getString("city");
	                            String iconUrl = info.getString("figureurl_qq_2");//获取用户头像的url
                                String sex=info.getString("gender");
	                            userPhoto user=(userPhoto)getApplicationContext();
	            		        user.setPetName(nickName);
	            		        user.setImageurl(iconUrl);
	            		        user.setCountry(place);
	            		        user.setSex(sex);
	            		        user.setLogstatue(true);
	            		        mTencent.logout(qqLogActivity.this);
	            		        qqLogActivity.this.setResult(2, intent);
	            				qqLogActivity.this.finish();
	                        } catch (JSONException e) {
	                            e.printStackTrace();
	                        }
	                    }
	                    @Override
	                    public void onError(UiError uiError) {}
	                    @Override
	                    public void onCancel() {}
	                });
	        }
		super.onActivityResult(requestCode, resultCode, data);
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			mTencent.logout(qqLogActivity.this);
			
			qqLogActivity.this.setResult(2, intent);
			qqLogActivity.this.finish();
			return true;
		} else
			return super.onKeyDown(keyCode, event);
	}
}
