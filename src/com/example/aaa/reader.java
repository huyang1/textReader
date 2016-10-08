package com.example.aaa;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
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
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Toast;

public class reader extends Activity{
	private final Handler handler=new Handler();
	private BufferedReader br;
    private boolean flag;
    private double x=0,y=0;
    private static final int PAGESIZE=600;
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
    private Map<BufferedReader,String> pos_chunk=new HashMap<BufferedReader, String>();
    private int pos_page_in_chunk=0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		final readPage readpage=new readPage(this);
		setContentView(readpage); 
		context=this;
		Intent intent = getIntent();
		convertCodeAndGetText(intent.getStringExtra("data"));
		char[] buf = new char[PAGESIZE*20];//ҳ�����ݻ����� 
    	try {br.read(buf);}
    	catch (IOException e){}
        sb.append(buf);
        pos_chunk.put(br,"1");//����ǰ��λ�ô洢
        pos_page_in_chunk++;
		readpage.getContent(sb.substring(0, PAGESIZE*pos_page_in_chunk));
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
							boolean flag=true;
						    sb.delete(0, sb.length());//��ջ�����
							int next_chunk_pos=Integer.parseInt(pos_chunk.get(br))+1;//�õ���һ���λ��
							//����map����Ҫ�Ŀ��Ƿ���map��
							Set<BufferedReader> set_chunk=pos_chunk.keySet();
							for(Iterator iter = set_chunk.iterator(); iter.hasNext();)
							{
								BufferedReader key = (BufferedReader) iter.next();
							     String value = (String)pos_chunk.get(key);
							     if(Integer.parseInt(value)==next_chunk_pos)
							     {
							    	flag=false;
							    	br=key;
							    	char[] buffer = new char[PAGESIZE*20];//ҳ�����ݻ����� 
							    	try {br.read(buffer);}
							    	catch (IOException e){}
							        sb.append(buffer);
							    	pos_page_in_chunk=0;
							     }
							}
							if(flag)
							{
								char[] buffer = new char[PAGESIZE*20];//ҳ�����ݻ����� 
						    	try {br.read(buffer);}
						    	catch (IOException e){}
						    	pos_chunk.put(br,""+next_chunk_pos);
						        sb.append(buffer);
						    	pos_page_in_chunk=0;	    
							}
						}
					    readpage.getContent(sb.substring(PAGESIZE*pos_page_in_chunk, PAGESIZE*(pos_page_in_chunk+1)));
						pos_page_in_chunk++;
					}
					else//ǰ��
					{
						if(pos_page_in_chunk==1&Integer.parseInt(pos_chunk.get(br))==1)
							Toast.makeText(getApplicationContext(),"��ҳ�ǵ�һҳ",
				                    Toast.LENGTH_SHORT).show();
						else
						{
							if(pos_page_in_chunk==1)//�����ҳΪ���еĵ�һҳҪǰ���Ķ���
							{
								sb.delete(0, sb.length());//��ջ�����
								int back_chunk_pos=Integer.parseInt(pos_chunk.get(br))-1;//�õ���һ���λ��
								//����map����Ҫ�Ŀ��Ƿ���map��
								Set<BufferedReader> set_chunk=pos_chunk.keySet();
								for(Iterator iter = set_chunk.iterator(); iter.hasNext();)
								{
									BufferedReader key = (BufferedReader) iter.next();
								     String value = (String)pos_chunk.get(key);
								     if(Integer.parseInt(value)==back_chunk_pos)
								     {
								    	 
								    	br=key;
								    	char[] buffer = new char[PAGESIZE*20];//ҳ�����ݻ����� 
								    	try {br.read(buffer);}
								    	catch (IOException e){}
								        sb.append(buffer);
								    	pos_page_in_chunk=20;
								    	readpage.getContent(sb.substring(sb.length()-PAGESIZE,sb.length()));
								     }
							    }
						   }
						   else
						   {
							   readpage.getContent(sb.substring(PAGESIZE*(pos_page_in_chunk-2), PAGESIZE*(pos_page_in_chunk-1)));
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
	
	public void convertCodeAndGetText(String str_filepath) {// ת��

        File file = new File(str_filepath);
        try {
                
                FileInputStream fis = new FileInputStream(file);
                BufferedInputStream in = new BufferedInputStream(fis);
                in.mark(4);
                byte[] first3bytes = new byte[3];
                in.read(first3bytes);//�ҵ��ĵ���ǰ�����ֽڲ��Զ��ж��ĵ����͡�
                in.reset();
                if (first3bytes[0] == (byte) 0xEF && first3bytes[1] == (byte) 0xBB
                                && first3bytes[2] == (byte) 0xBF) {// utf-8

                        br = new BufferedReader(new InputStreamReader(in, "utf-8"));

                } else if (first3bytes[0] == (byte) 0xFF
                                && first3bytes[1] == (byte) 0xFE) {

                        br = new BufferedReader(
                                        new InputStreamReader(in, "unicode"));
                } else if (first3bytes[0] == (byte) 0xFE
                                && first3bytes[1] == (byte) 0xFF) {

                        br = new BufferedReader(new InputStreamReader(in,
                                        "utf-16be"));
                } else if (first3bytes[0] == (byte) 0xFF
                                && first3bytes[1] == (byte) 0xFF) {

                        br = new BufferedReader(new InputStreamReader(in,
                                        "utf-16le"));
                } else {

                        br = new BufferedReader(new InputStreamReader(in, "GBK"));
                }
                
        } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
        } catch (IOException e) {
                e.printStackTrace();
        }
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
