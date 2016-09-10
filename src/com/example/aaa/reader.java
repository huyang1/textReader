package com.example.aaa;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.example.myView.readPage;
import com.example.voice.JsonParser;
import com.iflytek.cloud.speech.RecognizerListener;
import com.iflytek.cloud.speech.RecognizerResult;
import com.iflytek.cloud.speech.SpeechConstant;
import com.iflytek.cloud.speech.SpeechError;
import com.iflytek.cloud.speech.SpeechListener;
import com.iflytek.cloud.speech.SpeechRecognizer;
import com.iflytek.cloud.speech.SpeechUser;


import android.R.integer;
import android.app.Activity;

import android.content.Context;
import android.content.Intent;

import android.graphics.Bitmap;

import android.graphics.Paint;

import android.os.Bundle;
import android.os.Handler;

import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.TextView;
import android.widget.Toast;

public class reader extends Activity{
	private final Handler handler=new Handler();
	private FileReader fr;
    private boolean flag;
    private double x=0,y=0;
    private SpeechRecognizer mIat;
    Context context;
    final Runnable runnable=new Runnable() {  
	    @Override  
	    public void run() {  
	        // TODO Auto-generated method stub  
	    	VoiceToWord();
	    //Ҫ��������  
	       handler.postDelayed(this, 500);  // 1���ִ��
	    }

		private void VoiceToWord() {
			// TODO Auto-generated method stub
			SpeechUser.getUser().login(context, null, null
					, "appid=" + "534e3fe2", listener);
			mIat= SpeechRecognizer.createRecognizer(context);    
			mIat.setParameter(SpeechConstant.DOMAIN, "iat");    
			mIat.setParameter(SpeechConstant.LANGUAGE, "zh_cn");  
			mIat.setParameter(SpeechConstant.ACCENT, "mandarin ");
			mIat.setParameter(SpeechConstant.ASR_PTT, "0");  
			mIat.setParameter(SpeechConstant.VAD_BOS, "10000"); 
			mIat.setParameter(SpeechConstant.VAD_EOS, "500");  
			mIat.startListening(mRecoListener);
		}  
	};
    private String speakWord;
	//private ArrayList<String> allPage = new ArrayList<String>();
    private StringBuilder sb = new StringBuilder(1024*20);//���ݻ�����
    private Map<FileReader,String> pos_chunk=new HashMap<FileReader, String>();
    private int pos_page_in_chunk=0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		final readPage readpage=new readPage(this);
		setContentView(readpage); 
		context=this;
		//setContentView(R.layout.main_text);
		/*Bundle b = getIntent().getExtras(); // ��õ�ǰ·����һ�ݿ���
		String str = b.getString("FILE_PATH");*/
		//thread.start();
		Intent intent = getIntent();
		try {
			fr=new FileReader(new File(intent.getStringExtra("data")));
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		char[] buf = new char[1024*20];//ҳ�����ݻ����� 
    	try {fr.read(buf);}
    	catch (IOException e){}
        sb.append(buf);
        pos_chunk.put(fr,"1");//����ǰ��λ�ô洢
        pos_page_in_chunk++;
		readpage.getContent(sb.substring(0, 1024*pos_page_in_chunk));
		readpage.setOnTouchListener(new OnTouchListener(){
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				if(event.getAction() == MotionEvent.ACTION_DOWN)
				{
					x = event.getX();
					y = event.getY();
				}
				if (event.getAction() == MotionEvent.ACTION_MOVE) {
					if (x > event.getX())
						flag = true;
					else
						flag = false;
					x = event.getX();
					y = event.getY();
				}
				if (event.getAction() == MotionEvent.ACTION_UP)
				{
					
					if(flag)//��
					{
						if(pos_page_in_chunk==20)
						{
						    sb.delete(0, sb.length());//��ջ�����
							int next_chunk_pos=Integer.parseInt(pos_chunk.get(fr))+1;//�õ���һ���λ��
							//����map����Ҫ�Ŀ��Ƿ���map��
							Set<FileReader> set_chunk=pos_chunk.keySet();
							for(Iterator iter = set_chunk.iterator(); iter.hasNext();)
							{
							     FileReader key = (FileReader) iter.next();
							     String value = (String)pos_chunk.get(key);
							     if(Integer.parseInt(value)==next_chunk_pos)
							     {
							    	 
							    	fr=key;
							    	char[] buffer = new char[1024*20];//ҳ�����ݻ����� 
							    	try {fr.read(buffer);}
							    	catch (IOException e){}
							        sb.append(buffer);
							    	pos_page_in_chunk=0;
							     }
							}
						}
					    readpage.getContent(sb.substring(1024*pos_page_in_chunk, 1024*(pos_page_in_chunk+1)));
						pos_page_in_chunk++;
					}
					else//ǰ��
					{
						if(pos_page_in_chunk==1&Integer.parseInt(pos_chunk.get(fr))==1)
							Toast.makeText(getApplicationContext(),"��ҳ�ǵ�һҳ",
				                    Toast.LENGTH_SHORT).show();
						else
						{
							if(pos_page_in_chunk==1)//������ҳΪ���еĵ�һҳҪǰ���Ķ���
							{
								sb.delete(0, sb.length());//��ջ�����
								int back_chunk_pos=Integer.parseInt(pos_chunk.get(fr))-1;//�õ���һ���λ��
								//����map����Ҫ�Ŀ��Ƿ���map��
								Set<FileReader> set_chunk=pos_chunk.keySet();
								for(Iterator iter = set_chunk.iterator(); iter.hasNext();)
								{
								     FileReader key = (FileReader) iter.next();
								     String value = (String)pos_chunk.get(key);
								     if(Integer.parseInt(value)==back_chunk_pos)
								     {
								    	 
								    	fr=key;
								    	char[] buffer = new char[1024*20];//ҳ�����ݻ����� 
								    	try {fr.read(buffer);}
								    	catch (IOException e){}
								        sb.append(buffer);
								    	pos_page_in_chunk=20;
								    	readpage.getContent(sb.substring(sb.length()-1024,sb.length()));
								     }
							    }
						   }
						   else
						   {
							   readpage.getContent(sb.substring(1024*(pos_page_in_chunk-2), 1024*(pos_page_in_chunk-1)));
								pos_page_in_chunk--;
						   }	
						}	
					}
				}
				return false;
			}
		});
		handler.postDelayed(runnable, 500);//0.5��ִ��һ��runnable.
		
	}
	private RecognizerListener mRecoListener = new RecognizerListener(){    
        public void onResult(RecognizerResult results, boolean isLast) 
        {
        	String result=JsonParser.parseIatResult(results.getResultString());
        	if(!result.trim().isEmpty())
                  Toast.makeText(getApplicationContext(),result,
                  Toast.LENGTH_SHORT).show();
              mIat.destroy();
        };  
        public void onError(SpeechError error)
       {    
           error.getPlainDescription(true);
       };    
        //��ʼ¼��    
          public void onBeginOfSpeech() {};    
         //����ֵ0~30    
          public void onVolumeChanged(int volume){};    
          //����¼��    
          public void onEndOfSpeech() {
          };    
         
	      public void onEvent(int arg0, int arg1, int arg2, String arg3) {
		// TODO Auto-generated method stub
	      };    
    };

    private SpeechListener listener = new SpeechListener()
    {

        @Override
        public void onData(byte[] arg0) {
        }

        @Override
        public void onCompleted(SpeechError error) {
	        if(error != null) {
		       System.out.println("user login success");
	        }			
        }

        @Override
        public void onEvent(int arg0, Bundle arg1) {
       }		
   };
	
}