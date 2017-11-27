package com.example.myView;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.R.integer;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.TextView;
public class textviewPage extends TextView {
	private Context context;

	private String content;
	private int width;
	private Paint paint;
	private int xPadding;
	private int yPadding;
	private int textHeight;
	private int xPaddingBorder;
	private int yPaddingBorder;
    private int paragraphPadding;
    private int LineWordCount;
    private int Height;
	int count=0;
	private int page=1;
	private int fonttype=0;
	private ArrayList<Integer> page_pos=new ArrayList<Integer>();
	private Typeface font=Typeface.DEFAULT;
	    //记录每个字的二维数组
    int[][] position;
    private String tag="textviewPage";
    private String data="";
	public textviewPage(Context context) {
		super(context);
		this.context = context;
		init();
		// TODO Auto-generated constructor stub
	}

	public textviewPage(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		// TODO Auto-generated constructor stub
		this.context = context;
		init();
	}

	public textviewPage(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		this.context = context;
		init();
	}

	    private void init() {
	    	Resources resources = context.getResources();
	    	DisplayMetrics dm = resources.getDisplayMetrics();
	        paint = new Paint();
	        //TODO 设置画笔颜色，也就是字体颜色
	        paint.setColor(Color.BLACK);
	        paint.setAntiAlias(true);
	        paint.setStrokeWidth(10f);
	        //paint.setAlpha(200);
	        //paint.setTypeface(font);
	        paint.setTypeface(Typeface.DEFAULT/*Typeface.createFromAsset(context.getAssets(),"fonts/ragular_script.ttf")*/);
	        //TODO 设置画笔大小，也就是字体大小
	        paint.setTextSize(getTextSize());
	        Paint.FontMetrics fm = paint.getFontMetrics();// 得到系统默认字体属性
	        // TODO 设置字体高度
	        textHeight = (int) (Math.ceil(fm.descent - fm.top) + 2);
	        paragraphPadding=textHeight;
	        //TODO 设置字间距
	        xPadding = 2;
	        //TODO 设置行间距
	        yPaddingBorder=dip2px(this.getContext(), 10f);
	        yPadding =textHeight/3;
	        //TODO 设置比较小的字间距（字母和数字应紧凑）
	        width = dm.widthPixels;
	        Height=dm.heightPixels-dip2px(this.getContext(), 20f);
	        xPaddingBorder=15;
	        LineWordCount=(int) ((width-30+xPadding)/(this.getTextSize()+xPadding));
	        xPaddingBorder=(int) ((width-LineWordCount*getTextSize()-(LineWordCount-1)*xPadding)/2);
	        
	    }
	    public void setText(String str,int position,int BrightTYPE,int fontsize,int fonttype) {
	    	switch(BrightTYPE)
	    	{
	    	case 1:
	    		paint.setColor(Color.BLACK);
	    		break;
	    	case 2:
	    		paint.setColor(Color.rgb(102, 102, 102));
	    	    break;
	    	}
	    	if(fonttype!=0)//设置字体
	    	{
	    		if(this.fonttype!=fonttype)
	    		{
	    			changeFonts(fonttype);
	    			this.fonttype=fontsize;
	    		}
	    	}
	    	paint.setTextSize(getTextSize()+fontsize);//设置字体
	    	if(str.equals(this.data))
	    	{
	    		this.page=position;
	    		this.invalidate();
	    	}
	    	else
	    	{
	    		this.page=position;
	    		this.data=str;
	    		Resources resources = context.getResources();
	    		DisplayMetrics dm = resources.getDisplayMetrics();
	    		//获得设备的宽
	    		width = dm.widthPixels;
	    		getPositions(str);
	    		//重新画控件，将会调用onDraw方法
	    		this.invalidate();
	    	}
	    }
	    @Override
	    protected void onDraw(Canvas canvas) {
	        super.onDraw(canvas);
	        if (!TextUtils.isEmpty(content)) {
	            for (int i =page_pos.get(page-1); i < page_pos.get(page); i++) {
	            	//Log.i(tag,""+String.valueOf(content.charAt(i)));
	                canvas.drawText(String.valueOf(content.charAt(i)), position[i][0],position[i][1], paint);
	            }
	        }
	    }
	    public void getPositions(String content) {
	    	page_pos=new ArrayList<Integer>();
	        page_pos.add(0);
	    	String rows[]=content.split("\r\n");
	    	count = content.length();
	    	this.content="";
	    	position=new int[count][2];
	        int word=0;
	        int xStart=xPaddingBorder;
	        int yStart=yPaddingBorder;
	        for(int i=0;i<rows.length;i++)
	        {
	        	if(rows[i]!=null||rows[i].length()==0)
	        	{
	        		xStart=xPaddingBorder;
	        		String temp;
	        		if(rows[i].contains("    "))
	        		{
	        			this.content+=rows[i].substring(2, rows[i].length());
	        			temp=rows[i].substring(2, rows[i].length());
	        		}
	        		else if(rows[i].contains("  "))
	        		{
	        			this.content+=rows[i];
	        			temp=rows[i];
	        		}
	        		else
	        		{
	        			this.content+="  "+rows[i];
	        			temp="  "+rows[i];
	        		}
	        		
	        			
	        		for(int j=0;j<temp.length();j++)
	        		{
	        			if(yStart+textHeight>Height)
	        			{
	        				int LeftyBorder=(Height-yStart+yPadding)/2;
	        				for(int k=page_pos.get(page_pos.size()-1);k<word;k++)
	        				{
	        					position[k][1]+=LeftyBorder;
	        				}
	        				xStart=xPaddingBorder;
	        				yStart=yPaddingBorder;
	        				page_pos.add(word);
	        				//Log.i(tag, "break"+word);
	        			}
	        			position[word][0]=xStart;
	        			position[word][1]=yStart;
	        			if((j+1)%LineWordCount==0)
	        			{
	        				//Log.i(tag,""+rows[i].charAt(j));
	        				xStart=xPaddingBorder;
	        				if(j+1<temp.length())
	        					yStart+=textHeight+yPadding;
	        			}
	        			else
	        			{
	        				xStart+=getTextSize()+xPadding;
	        			}
	        			word++;
	        		}
	        		if(rows[i]==null||rows[i].length()==0){}
	        		else
	        		    yStart+=textHeight+yPaddingBorder;
	        	}
	        }
	        page_pos.add(word);
	        for(int i=page_pos.get(page_pos.size()-2);i<page_pos.get(page_pos.size()-1);i++)
	        {
	        	position[i][1]+=yPaddingBorder;
	        }
	    }
	    //工具类：判断是否是字母或者数字
	    public boolean isNumOrLetters(String str){
	        String regEx="^[A-Za-z0-9_]+$";
	        Pattern p= Pattern.compile(regEx);
	        Matcher m=p.matcher(str);
	        return m.matches();
	    }
	    // 工具类：在代码中使用dp的方法（因为代码中直接用数字表示的是像素）
	    public static int dip2px(Context context, float dip) {
	        final float scale = context.getResources().getDisplayMetrics().density;
	        return (int) (dip * scale + 0.5f);
	    }
	public int getlinewords() {
		float textSize = getTextSize();
		Resources resources = context.getResources();
		DisplayMetrics dm = resources.getDisplayMetrics();
		int mwidth = dm.widthPixels;
		float linewords = mwidth / (textSize+dip2px(this.getContext(), 2f));
		return (int) linewords;
	}
	public int getlinecount() {
		Resources resources = context.getResources();
		DisplayMetrics dm = resources.getDisplayMetrics();
		int mheight = dm.heightPixels - dip2px(this.getContext(), 24f);
		int linecount = (mheight) / (textHeight+textHeight/3);
		return (int) (linecount);
	}
	public int getpagewords() {
		return getlinewords() * getlinecount()*2;
	}
	public void changeFonts(int str)
	{
		if(str==1)
			paint.setTypeface(Typeface.createFromAsset(context.getAssets(),"fonts/1.ttf"));
		else if(str==2)
			paint.setTypeface(Typeface.createFromAsset(context.getAssets(),"fonts/2.ttf"));
		else if(str==3)
			paint.setTypeface(Typeface.createFromAsset(context.getAssets(),"fonts/3.ttf"));
		else if(str==4)
			paint.setTypeface(Typeface.createFromAsset(context.getAssets(),"fonts/4.ttf"));
	}
	public int getPage_size()
	{
		return page_pos.size()-1;
	}

}
