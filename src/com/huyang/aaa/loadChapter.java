package com.huyang.aaa;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.example.DataBase.sqliteDataBase;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

public class loadChapter extends AsyncTask<ArrayList<String>, Integer, Void> {
	private BufferedReader br;
	private Context context;
	private static String TAG = "loadChapter";

	public loadChapter(Context context) {
		this.context = context;
	}

	@Override
	protected Void doInBackground(ArrayList<String>... params) {
		// TODO Auto-generated method stub
		String bookname = params[0].get(0);
		String bookpath = params[0].get(1);
		convertCodeAndGetText(bookpath);
		String str;
		String bookchapter = "";
		String bookcontent = "";
		String regEx = "第.{1,7}章.{0,}";
		Pattern pattern = Pattern.compile(regEx);
		try {
			while ((str = br.readLine()) != null) {

				Matcher matcher = pattern.matcher(str);
				if (matcher.find()) {
					if (bookchapter.length() > 0) {
						Log.i(TAG, bookchapter);
						Log.i(TAG, bookname);
						(new sqliteDataBase(context)).insert(bookname,
								bookchapter, bookcontent);
						bookcontent = "";
					}
					bookchapter = matcher.group();
					bookcontent+=bookchapter+"\r\n";
				} else
				{
					bookcontent += str + "\r\n";
					if(bookcontent.length()>10000)
					{
						(new sqliteDataBase(context)).insert(bookname,
								bookchapter, bookcontent);
						bookchapter=bookcontent.substring(0, 8);
						bookchapter.replaceAll(" ", "").replaceAll("\r\n", "");
						bookcontent = "";
					}
						
				}
			
			}
			(new sqliteDataBase(context)).insert(bookname, bookchapter,
					bookcontent);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public void convertCodeAndGetText(String str_filepath) {// 转码

		File file = new File(str_filepath);
		try {

			FileInputStream fis = new FileInputStream(file);
			BufferedInputStream in = new BufferedInputStream(fis);
			in.mark(4);
			byte[] first3bytes = new byte[3];
			in.read(first3bytes);// 找到文档的前三个字节并自动判断文档类型。
			in.reset();
			if (first3bytes[0] == (byte) 0xEF && first3bytes[1] == (byte) 0xBB
					&& first3bytes[2] == (byte) 0xBF) {// utf-8

				br = new BufferedReader(new InputStreamReader(in, "utf-8"));

			} else if (first3bytes[0] == (byte) 0xFF
					&& first3bytes[1] == (byte) 0xFE) {

				br = new BufferedReader(new InputStreamReader(in, "unicode"));
			} else if (first3bytes[0] == (byte) 0xFE
					&& first3bytes[1] == (byte) 0xFF) {

				br = new BufferedReader(new InputStreamReader(in, "utf-16be"));
			} else if (first3bytes[0] == (byte) 0xFF
					&& first3bytes[1] == (byte) 0xFF) {

				br = new BufferedReader(new InputStreamReader(in, "utf-16le"));
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
}
