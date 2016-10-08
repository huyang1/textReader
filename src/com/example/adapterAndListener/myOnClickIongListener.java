package com.example.adapterAndListener;

import com.example.aaa.MainActivity;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;

public class myOnClickIongListener implements OnItemLongClickListener{
	private MainActivity content;
	private int position;
	
	public myOnClickIongListener(Context context,int position)
    {
    	this.content=(MainActivity) context;
    	this.position=position;
    }
	
	public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		// TODO Auto-generated method stub
		content.onMyClickLong(position*3+arg2);
		return true;
	}

}
