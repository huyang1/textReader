package com.example.voice;

import com.iflytek.cloud.speech.RecognizerListener;
import com.iflytek.cloud.speech.RecognizerResult;
import com.iflytek.cloud.speech.SpeechConstant;
import com.iflytek.cloud.speech.SpeechError;
import com.iflytek.cloud.speech.SpeechListener;
import com.iflytek.cloud.speech.SpeechRecognizer;
import com.iflytek.cloud.speech.SpeechUser;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

public class RecognizerThread extends Thread{
	private Context context;
	private Handler handler;
	private SpeechRecognizer mIat;
	private String result;
	public RecognizerThread(Context context,Handler handler)
	{
		this.context=context;
		this.handler=handler;
	}
	public void run()
	{
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
		handler.post(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				
			}
		});
	}
	private RecognizerListener mRecoListener = new RecognizerListener(){    
        public void onResult(RecognizerResult results, boolean isLast) 
        {    
        	  result=JsonParser.parseIatResult(results.getResultString());
              Toast.makeText(context,result,
                  Toast.LENGTH_SHORT).show();
              mIat.destroy();
        };  
        public void onError(SpeechError error)
       {    
           error.getPlainDescription(true);
       };    
  //开始录音    
        public void onBeginOfSpeech() {};    
  //音量值0~30    
        public void onVolumeChanged(int volume){};    
  //结束录音    
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
