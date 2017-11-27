/**
 *  @Project       : aaa;  
 *  @Program Name  : com.example.myView.LableDialog.java;
 *  @Class Name    : LableDialog;
 *  @Description   : 设置当用户上传文件时，需要添加描述，便以自动分类;
 *  @Author        : huyang;
 *  @Creation Date : 2017-3-11 下午1:20:05 ;
 */

package com.example.myView;

import com.huyang.aaa.R;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LableDialog extends Dialog {
	private EditText message;
	private Button yes,no;
	private YesListener yesListener;
	private NoListener noListener;
	private String title=null;
	public LableDialog(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lable_layout);
		setCanceledOnTouchOutside(false);
		message=(EditText) findViewById(R.id.message);
		if(title!=null)
		     ((TextView) findViewById(R.id.title)).setText(title);
		yes=(Button) findViewById(R.id.yes);
		no=(Button) findViewById(R.id.no);
		yes.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				yesListener.onYesListener(message.getText().toString());
			}
		});
		no.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				noListener.onNoListener();
			}
		});
	}
	public LableDialog(Context context, int themeResId) {
		super(context, themeResId);
		// TODO Auto-generated constructor stub
	}
	
	public void setYesListener(YesListener yesListener) {
		this.yesListener = yesListener;
	}

	public void setNoListener(NoListener noListener) {
		this.noListener = noListener;
	}

	public interface YesListener
	{
		public void onYesListener(String message);
	}
	public interface NoListener
	{
		public void onNoListener();
	}
	public void setTitle(String title)
	{
		this.title=title;
	}
}
