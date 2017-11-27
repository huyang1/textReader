package com.example.linkWeb;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Map;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class Upload {
    /**
     * ֱ��ͨ��HTTPЭ���ύ���ݵ�������,ʵ����������ύ����:
     *   <FORM METHOD=POST ACTION="http://192.168.1.101:8083/upload/servlet/UploadServlet" enctype="multipart/form-data">
            <INPUT TYPE="text" NAME="name">
            <INPUT TYPE="text" NAME="id">
            <input type="file" name="imagefile"/>
            <input type="file" name="zip"/>
         </FORM>
     * @param path �ϴ�·��(ע������ʹ��localhost��127.0.0.1������·�����ԣ���Ϊ����ָ���ֻ�ģ�����������ʹ��http://www.iteye.cn��http://192.168.1.101:8083������·������)
     * @param params ������� keyΪ������,valueΪ����ֵ
     * @param file �ϴ��ļ�
     * @throws IOException 
     * @throws UnknownHostException 
     */
	public static String post(String path, Map<String, String> params, FormFile[] files) throws UnknownHostException, IOException{     
        final String BOUNDARY = "---------------------------7da2137580612"; //���ݷָ���
        final String endline = "--" + BOUNDARY + "--\r\n";//���ݽ�����־
        
        int fileDataLength = 0;
        for(FormFile uploadFile : files){//�õ��ļ��������ݵ��ܳ���
            StringBuilder fileExplain = new StringBuilder();
             fileExplain.append("--");
             fileExplain.append(BOUNDARY);
             fileExplain.append("\r\n");
             fileExplain.append("Content-Disposition: form-data;name=\""+ uploadFile.getParameterName()+"\";filename=\""+ uploadFile.getFilename() + "\"\r\n");
             fileExplain.append("Content-Type: "+ uploadFile.getContentType()+"\r\n\r\n");
             fileExplain.append("\r\n");
             fileDataLength += fileExplain.length();
            //if(uploadFile.getInStream()!=null){
                fileDataLength += uploadFile.getFile().length();
             //}else{
                 //fileDataLength += uploadFile.getData().length;
             //}
        }
        StringBuilder textEntity = new StringBuilder();
        for (Map.Entry<String, String> entry : params.entrySet()) {//�����ı����Ͳ�����ʵ������
            textEntity.append("--");
            textEntity.append(BOUNDARY);
            textEntity.append("\r\n");
            textEntity.append("Content-Disposition: form-data; name=\""+ entry.getKey() + "\"\r\n\r\n");
            textEntity.append(entry.getValue());
            textEntity.append("\r\n");
        }
        //���㴫�����������ʵ�������ܳ���
        int dataLength = textEntity.toString().getBytes().length + fileDataLength +  endline.getBytes().length;
        
        URL url = new URL(path);
        int port = url.getPort()==-1 ? 80 : url.getPort();
        Socket socket = new Socket(InetAddress.getByName(url.getHost()), port);
        OutputStream outStream = socket.getOutputStream();
        //�������HTTP����ͷ�ķ���
        String requestmethod = "POST "+ url.getPath()+" HTTP/1.1\r\n";
        outStream.write(requestmethod.getBytes());
        String accept = "Accept: image/gif, image/jpeg, image/pjpeg, image/pjpeg, application/x-shockwave-flash, application/xaml+xml, application/vnd.ms-xpsdocument, application/x-ms-xbap, application/x-ms-application, application/vnd.ms-excel, application/vnd.ms-powerpoint, application/msword, */*\r\n";
        outStream.write(accept.getBytes());
        String language = "Accept-Language: zh-CN\r\n";
        outStream.write(language.getBytes());
        String contenttype = "Content-Type: multipart/form-data; boundary="+ BOUNDARY+ "\r\n";
        outStream.write(contenttype.getBytes());
        String contentlength = "Content-Length: "+ dataLength + "\r\n";
        outStream.write(contentlength.getBytes());
        String alive = "Connection: Keep-Alive\r\n";
        outStream.write(alive.getBytes());
        String host = "Host: "+ url.getHost() +":"+ port +"\r\n";
        outStream.write(host.getBytes());
        //д��HTTP����ͷ�����HTTPЭ����дһ���س�����
        outStream.write("\r\n".getBytes());
        //�������ı����͵�ʵ�����ݷ��ͳ���
        outStream.write(textEntity.toString().getBytes());           
        //�������ļ����͵�ʵ�����ݷ��ͳ���
        for(FormFile uploadFile : files){
            StringBuilder fileEntity = new StringBuilder();
             fileEntity.append("--");
             fileEntity.append(BOUNDARY);
             fileEntity.append("\r\n");
             fileEntity.append("Content-Disposition: form-data;name=\""+ uploadFile.getParameterName()+"\";filename=\""+ uploadFile.getFilename() + "\"\r\n");
             fileEntity.append("Content-Type: "+ uploadFile.getContentType()+"\r\n\r\n");
             outStream.write(fileEntity.toString().getBytes());
          
                 byte[] buffer = new byte[1024*4];
                 int len = 0;
                 FileInputStream fis=new FileInputStream(uploadFile.getFile().getAbsolutePath());
                 while((len =fis.read(buffer))!=-1){
                     outStream.write(buffer, 0, len);
                 }
                 fis.close();
            
             outStream.write("\r\n".getBytes());
        }
        //���淢�����ݽ�����־����ʾ�����Ѿ�����
        outStream.write(endline.getBytes());
        outStream.flush();
        String result="default";
        String temp;
        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream(),"UTF-8"));
        if(reader.readLine().indexOf("200")==-1){//��ȡweb���������ص����ݣ��ж��������Ƿ�Ϊ200���������200����������ʧ��
            return "�����쳣";
        }
        while((temp=reader.readLine())!=null)
        {
        	if(temp.contains("flag:mainbodystar"))
        	{
        		result =reader.readLine();
        		break;
        	}
        	else if(temp.contains("loginsuccess"))
        	{
        		result =reader.readLine();
        		break;
        	}
        	else if(temp.contains("registersuccess"))
        	{
        		result =reader.readLine();
        		break;
        	}
        	else if(temp.contains("change"))
        	{
        		result =reader.readLine();
        		break;
        	}
        }
        outStream.close();
        reader.close();
        socket.close();
        return result;
    }
	public static String post(String path, Map<String, String> params) throws UnknownHostException, IOException{     
        final String BOUNDARY = "---------------------------7da2137580612"; //���ݷָ���
        final String endline = "--" + BOUNDARY + "--\r\n";//���ݽ�����־
        
        int fileDataLength = 0;
        
        StringBuilder textEntity = new StringBuilder();
        for (Map.Entry<String, String> entry : params.entrySet()) {//�����ı����Ͳ�����ʵ������
            textEntity.append("--");
            textEntity.append(BOUNDARY);
            textEntity.append("\r\n");
            textEntity.append("Content-Disposition: form-data; name=\""+ entry.getKey() + "\"\r\n\r\n");
            textEntity.append(entry.getValue());
            textEntity.append("\r\n");
        }
        //���㴫�����������ʵ�������ܳ���
        int dataLength = textEntity.toString().getBytes().length + fileDataLength +  endline.getBytes().length;
        
        URL url = new URL(path);
        int port = url.getPort()==-1 ? 80 : url.getPort();
        Socket socket = new Socket(InetAddress.getByName(url.getHost()), port);
        OutputStream outStream = socket.getOutputStream();
        //�������HTTP����ͷ�ķ���
        String requestmethod = "POST "+ url.getPath()+" HTTP/1.1\r\n";
        outStream.write(requestmethod.getBytes());
        String accept = "Accept: image/gif, image/jpeg, image/pjpeg, image/pjpeg, application/x-shockwave-flash, application/xaml+xml, application/vnd.ms-xpsdocument, application/x-ms-xbap, application/x-ms-application, application/vnd.ms-excel, application/vnd.ms-powerpoint, application/msword, */*\r\n";
        outStream.write(accept.getBytes());
        String language = "Accept-Language: zh-CN\r\n";
        outStream.write(language.getBytes());
        String contenttype = "Content-Type: multipart/form-data; boundary="+ BOUNDARY+ "\r\n";
        outStream.write(contenttype.getBytes());
        String contentlength = "Content-Length: "+ dataLength + "\r\n";
        outStream.write(contentlength.getBytes());
        String alive = "Connection: Keep-Alive\r\n";
        outStream.write(alive.getBytes());
        String host = "Host: "+ url.getHost() +":"+ port +"\r\n";
        outStream.write(host.getBytes());
        //д��HTTP����ͷ�����HTTPЭ����дһ���س�����
        outStream.write("\r\n".getBytes());
        //�������ı����͵�ʵ�����ݷ��ͳ���
        outStream.write(textEntity.toString().getBytes());           
        //�������ļ����͵�ʵ�����ݷ��ͳ���
        //���淢�����ݽ�����־����ʾ�����Ѿ�����
        outStream.write(endline.getBytes());
        outStream.flush();
        String result=null;
        String temp;
        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream(),"UTF-8"));
        while((temp=reader.readLine())!=null)
        {
        	if(temp.contains("flag:mainbodystar"))
        	{
        		result =reader.readLine();
        		break;
        	}
        	else if(temp.contains("loginsuccess"))
        	{
        		result =reader.readLine();
        		break;
        	}
        	else if(temp.contains("registersuccess"))
        	{
        		result =reader.readLine();
        		break;
        	}
        	else if(temp.contains("change"))
        	{
        		result =reader.readLine();
        		break;
        	}
        }
        outStream.close();
        reader.close();
        socket.close();
        return result;
    }
    /**
     * �ύ���ݵ�������
     * @param path �ϴ�·��(ע������ʹ��localhost��127.0.0.1������·�����ԣ���Ϊ����ָ���ֻ�ģ�����������ʹ��http://www.itcast.cn��http://192.168.1.10:8080������·������)
     * @param params ������� keyΪ������,valueΪ����ֵ
     * @param file �ϴ��ļ�
     */
    public static String post(String path, Map<String, String> params, FormFile file) throws Exception{
        return post(path, params, new FormFile[]{file});
    }
    public static boolean isNetworkAvailable(Context context) {   
        ConnectivityManager cm = (ConnectivityManager) context   
                .getSystemService(Context.CONNECTIVITY_SERVICE);   
        if (cm == null) {   
        } 
        else 
        { 
            NetworkInfo[] info = cm.getAllNetworkInfo();   
            if (info != null) {   
                for (int i = 0; i < info.length; i++) {   
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {   
                        return true;  
                    }
                }
            }
        }
		return false;
    }
}
