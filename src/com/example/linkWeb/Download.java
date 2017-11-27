package com.example.linkWeb;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.util.Map;

import android.util.Log;

public class Download {
	final static String BOUNDARY = "---------------------------7da2137580612"; // ���ݷָ���
	final static String endline = "--" + BOUNDARY + "--\r\n";// ���ݽ�����־

	public static String post(String path, String count) throws Exception {
		StringBuilder textEntity = new StringBuilder();

		textEntity.append("--");
		textEntity.append(BOUNDARY);
		textEntity.append("\r\n");
		textEntity.append("Content-Disposition: form-data; name=\""
				+ "count" + "\"\r\n\r\n");
		textEntity.append(count);
		textEntity.append("\r\n");

		URL url = new URL(path);
		int port = url.getPort() == -1 ? 80 : url.getPort();
		Socket socket = new Socket(InetAddress.getByName(url.getHost()), port);
		OutputStream outStream = socket.getOutputStream();
		String requestmethod = "POST " + url.getPath() + " HTTP/1.1\r\n";
		outStream.write(requestmethod.getBytes());
		String accept = "Accept: image/gif, image/jpeg, image/pjpeg, image/pjpeg, application/x-shockwave-flash, application/xaml+xml, application/vnd.ms-xpsdocument, application/x-ms-xbap, application/x-ms-application, application/vnd.ms-excel, application/vnd.ms-powerpoint, application/msword, */*\r\n";
		outStream.write(accept.getBytes());
		String language = "Accept-Language: zh-CN\r\n";
		outStream.write(language.getBytes());
		String contenttype = "Content-Type: multipart/form-data; boundary="
				+ BOUNDARY + "\r\n";
		outStream.write(contenttype.getBytes());
		String contentlength = "Content-Length: " + (endline.getBytes().length+textEntity.toString().getBytes().length)
				+ "\r\n";
		outStream.write(contentlength.getBytes());
		String alive = "Connection: Keep-Alive\r\n";
		outStream.write(alive.getBytes());
		String host = "Host: " + url.getHost() + ":" + port + "\r\n";
		outStream.write(host.getBytes());
		// д��HTTP����ͷ�����HTTPЭ����дһ���س�����
		outStream.write("\r\n".getBytes());
		outStream.write(textEntity.toString().getBytes());
		outStream.write(endline.getBytes());
		outStream.flush();
		String result = "default";
		String temp;
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				socket.getInputStream(), "UTF-8"));
		if (reader.readLine().indexOf("200") == -1) {// ��ȡweb���������ص����ݣ��ж��������Ƿ�Ϊ200���������200����������ʧ��
			return "�����쳣";
		}
		while ((temp = reader.readLine()) != null) {
			if (temp.contains("flag:mainbookdata")) {
				result = reader.readLine();
				break;
			}
		}
		return result;
	}
	public static String search(String path, String bookname) throws Exception {
		StringBuilder textEntity = new StringBuilder();

		textEntity.append("--");
		textEntity.append(BOUNDARY);
		textEntity.append("\r\n");
		textEntity.append("Content-Disposition: form-data; name=\""
				+ "bookname" + "\"\r\n\r\n");
		textEntity.append(bookname);
		textEntity.append("\r\n");

		URL url = new URL(path);
		int port = url.getPort() == -1 ? 80 : url.getPort();
		Socket socket = new Socket(InetAddress.getByName(url.getHost()), port);
		OutputStream outStream = socket.getOutputStream();
		String requestmethod = "POST " + url.getPath() + " HTTP/1.1\r\n";
		outStream.write(requestmethod.getBytes());
		String accept = "Accept: image/gif, image/jpeg, image/pjpeg, image/pjpeg, application/x-shockwave-flash, application/xaml+xml, application/vnd.ms-xpsdocument, application/x-ms-xbap, application/x-ms-application, application/vnd.ms-excel, application/vnd.ms-powerpoint, application/msword, */*\r\n";
		outStream.write(accept.getBytes());
		String language = "Accept-Language: zh-CN\r\n";
		outStream.write(language.getBytes());
		String contenttype = "Content-Type: multipart/form-data; boundary="
				+ BOUNDARY + "\r\n";
		outStream.write(contenttype.getBytes());
		String contentlength = "Content-Length: " + (endline.getBytes().length+textEntity.toString().getBytes().length)
				+ "\r\n";
		outStream.write(contentlength.getBytes());
		String alive = "Connection: Keep-Alive\r\n";
		outStream.write(alive.getBytes());
		String host = "Host: " + url.getHost() + ":" + port + "\r\n";
		outStream.write(host.getBytes());
		// д��HTTP����ͷ�����HTTPЭ����дһ���س�����
		outStream.write("\r\n".getBytes());
		outStream.write(textEntity.toString().getBytes());
		outStream.write(endline.getBytes());
		outStream.flush();
		String result = "default";
		String temp;
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				socket.getInputStream(), "UTF-8"));
		if (reader.readLine().indexOf("200") == -1) {// ��ȡweb���������ص����ݣ��ж��������Ƿ�Ϊ200���������200����������ʧ��
			return "�����쳣";
		}
		while ((temp = reader.readLine()) != null) {
			if (temp.contains("flag:mainbookdata")) {
				result = reader.readLine();
				break;
			}
		}
		return result;
	}
	public static String LableBook(String path, String lable,String count) throws Exception {
		StringBuilder textEntity = new StringBuilder();

		textEntity.append("--");
		textEntity.append(BOUNDARY);
		textEntity.append("\r\n");
		textEntity.append("Content-Disposition: form-data; name=\""
				+ "lable" + "\"\r\n\r\n");
		textEntity.append(lable);
		textEntity.append("\r\n");
		
		textEntity.append("--");
		textEntity.append(BOUNDARY);
		textEntity.append("\r\n");
		textEntity.append("Content-Disposition: form-data; name=\""
				+ "count" + "\"\r\n\r\n");
		textEntity.append(count);
		textEntity.append("\r\n");

		URL url = new URL(path);
		int port = url.getPort() == -1 ? 80 : url.getPort();
		Socket socket = new Socket(InetAddress.getByName(url.getHost()), port);
		OutputStream outStream = socket.getOutputStream();
		String requestmethod = "POST " + url.getPath() + " HTTP/1.1\r\n";
		outStream.write(requestmethod.getBytes());
		String accept = "Accept: image/gif, image/jpeg, image/pjpeg, image/pjpeg, application/x-shockwave-flash, application/xaml+xml, application/vnd.ms-xpsdocument, application/x-ms-xbap, application/x-ms-application, application/vnd.ms-excel, application/vnd.ms-powerpoint, application/msword, */*\r\n";
		outStream.write(accept.getBytes());
		String language = "Accept-Language: zh-CN\r\n";
		outStream.write(language.getBytes());
		String contenttype = "Content-Type: multipart/form-data; boundary="
				+ BOUNDARY + "\r\n";
		outStream.write(contenttype.getBytes());
		String contentlength = "Content-Length: " + (endline.getBytes().length+textEntity.toString().getBytes().length)
				+ "\r\n";
		outStream.write(contentlength.getBytes());
		String alive = "Connection: Keep-Alive\r\n";
		outStream.write(alive.getBytes());
		String host = "Host: " + url.getHost() + ":" + port + "\r\n";
		outStream.write(host.getBytes());
		// д��HTTP����ͷ�����HTTPЭ����дһ���س�����
		outStream.write("\r\n".getBytes());
		outStream.write(textEntity.toString().getBytes());
		outStream.write(endline.getBytes());
		outStream.flush();
		String result = "default";
		String temp;
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				socket.getInputStream(), "UTF-8"));
		if (reader.readLine().indexOf("200") == -1) {// ��ȡweb���������ص����ݣ��ж��������Ƿ�Ϊ200���������200����������ʧ��
			return "�����쳣";
		}
		while ((temp = reader.readLine()) != null) {
			if (temp.contains("flag:mainbookdata")) {
				result = reader.readLine();
				break;
			}
		}
		return result;
	}

}
