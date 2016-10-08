package com.example.adapterAndListener;

import com.example.aaa.MainActivity;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

public class myOnClickLinstener implements OnItemClickListener{
	private MainActivity content;
	private int position;
    public myOnClickLinstener(Context context,int position)
    {
    	this.content=(MainActivity) context;
    	this.position=position;
    }
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
	    content.onmyClick(position*3+arg2);
	}
}
