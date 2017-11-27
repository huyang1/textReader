package com.example.myView;
import com.huyang.aaa.R;
import com.example.adapterAndListener.SendseekbarData;
import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.SeekBar.OnSeekBarChangeListener;
/**
 * 自定义遮罩dialog
 * @author Administrator
 *
 */
public class dialogSetting{
	private Dialog dialog;
	private Chatper chatper;
	private TextPageSet pageset;
	private int process=0;
	private SendseekbarData seekbardata=null;
	
	public dialogSetting( final Context context,final View view,String chapterName) {
		dialog=new Dialog(context,R.style.dialog);
		View popView = LayoutInflater. 
                from(context).inflate(R.layout.set, null); 
		catalog calog=(catalog) popView.findViewById(R.id.catalog);
		calog.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				hide();
				chatper=new Chatper(context,view);
				
				chatper.show();
			}
		});
		popView.findViewById(R.id.setlogo).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				hide();
				pageset=new TextPageSet(context,new TextPageSet.SetMessage() {
					
					@Override
					public void getpageColor(int pagecolor) {
						// TODO Auto-generated method stub
						seekbardata.setPageColor(pagecolor);
					}
					
					@Override
					public void getFontType(int fonttype) {
						// TODO Auto-generated method stub
						seekbardata.setFontType(fonttype);
					}
					
					@Override
					public void getFontSize(int fontsize) {
						// TODO Auto-generated method stub
						seekbardata.setFontSize(fontsize);
					}
				});
				
				pageset.show();
			}
		});
		RelativeLayout lineearlayout=(RelativeLayout) popView.findViewById(R.id.blank);
		lineearlayout.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				hide();
			}
		});
		BackView backview=(BackView) popView.findViewById(R.id.backTobook);
		backview.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				hide();
			}
		});
		brightLogo setbright=(brightLogo) popView.findViewById(R.id.bright);
		setbright.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.i("dialog","click");
				seekbardata.changeBright();
			}
		});
		TextView name=(TextView) popView.findViewById(R.id.chaptername);
		name.setText(chapterName);
		SeekBar seekBar = (SeekBar) popView.findViewById(R.id.seekbar);
		seekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				//Toast.makeText(context, " "+process, Toast.LENGTH_SHORT).show();
				seekbardata.onSeekbarData(process);
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				// TODO Auto-generated method stub
				process=progress;
			}
		});
		/**
		 * 进行设置代码区
		 */
		dialog.setContentView(popView); 
        Window dialogWindow = dialog.getWindow();
        //dialogWindow.setGravity( Gravity.BOTTOM);
        Resources resources = context.getResources();  
		DisplayMetrics dm = resources.getDisplayMetrics();  
		int mWidth = dm.widthPixels;  
		int mHeight=dm.heightPixels;
        android.view.WindowManager.LayoutParams p = dialog.getWindow().getAttributes();  //获取对话框当前的参数值  
        p.width = (int) (mWidth);    
        p.height=(int) mHeight;
        dialog.getWindow().setAttributes(p);     //设置生效  
        
	}
	public void show() { 
        dialog.show(); 
    } 
	
	public void hide() { 
	        dialog.dismiss(); 
	}
	public void hidechapter()
	{
		chatper.hide();
	}
	public void setOnSeekbarData(SendseekbarData listener)
	{
		seekbardata=listener;
	}
}
