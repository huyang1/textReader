package com.example.linkWeb;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import java.util.Map;


import com.example.bean.uploadPhotoBean;
import com.example.bean.user;
import com.google.gson.Gson;
import com.huyang.aaa.changMessageStatue;
import com.huyang.aaa.userPhoto;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

public class uploadTask extends AsyncTask<uploadPhotoBean, Integer, String> {
	private Context context;
	private String flag=null;
	private changMessageStatue listener;
	public uploadTask(Context context)
	{
		this.context=context;
	}
	@Override
	protected String doInBackground(uploadPhotoBean... params) {
		// TODO Auto-generated method stub
		uploadPhotoBean data=params[0];
		Map<String,String> temp=data.getParams();
		flag=temp.get("type");
		try {
			if(data.getFile()==null)
				return Upload.post(data.getUrl(), data.getParams());
			else
			    return Upload.post(data.getUrl(), data.getParams(), data.getFile());
 
		} catch (Exception e) {
			// TODO Auto-generated catch block
            listener.getStatue(false);
			StringWriter sw = new StringWriter();  
            PrintWriter pw = new PrintWriter(sw);  
            e.printStackTrace(pw);  
            System.out.println(sw.toString().toUpperCase());  
            
            Toast.makeText(context,sw.toString(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
			return "服务器异常";
		}
		
	}

	@Override
	protected void onPostExecute(String result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		if(flag.equals("login"))
		{
			if(result!=null&&result.contains("{")&&result.contains("}"))
			{
				java.lang.reflect.Type type = new com.google.gson.reflect.TypeToken<user>() {  
		        }.getType();  
		        Gson gson = new Gson();  
		        user user1 = gson.fromJson(result, type); 
		        userPhoto user=(userPhoto) context.getApplicationContext();
		        user.setUsername(user1.getUserName());
		        user.setImageurl(user1.getImageUrl());
		        if(user1.getPetname()==null)
		        {
		        	user.setPetName("_^_");
		        }
		        else
		            user.setPetName(user1.getPetname());
		        if(user1.getSex()==null)
		        {
		        	user.setSex("去设置");
		        }
		        else
		            user.setSex(user1.getSex());
		        if(user1.getBrith()==null)
		        {
		        	user.setBrith("去设置");
		        }
		        else
		            user.setBrith(user1.getBrith());
		        user.setLogstatue(true);
		        if(user1.getCountry()==null)
		        {
		        	user.setCountry("去设置");
		        }
		        else
		            user.setCountry(user1.getCountry());
		        if(user1.getImageUrl().contains("null"))
		        	user.setBitmap(null);
		        Toast.makeText(context, "登录成功", Toast.LENGTH_SHORT).show();
			}
			else
				Toast.makeText(context, result, Toast.LENGTH_SHORT).show();
			
		}
		else if(flag.equals("register"))
		{
			Toast.makeText(context, result, Toast.LENGTH_SHORT).show();
		}
		else if(flag.equals("change"))
		{
			listener.getStatue(true);
			Toast.makeText(context, result, Toast.LENGTH_SHORT).show();
		}
	}
	public void setChangeStatueListener(changMessageStatue listener)
	{
		this.listener=listener;
	}
}
