package com.example.DataBase;
/**
 * 创建主页书架中书本的集合的table
 * 
 */
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

import com.huyang.aaa.loadChapter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

public class sqliteDataBase extends SQLiteOpenHelper implements operationDataBase{
	private Context context;
	public sqliteDataBase(Context context)
	{
		super(context,"BOOKSET",null,1);
		this.context=context;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL("create table book(id int,name varchar(20),path varchar(20))");
		db.execSQL("create table bookcontent(id int,name varchar(20),chaptername varchar(50),content TEXT)");
		db.execSQL("create table bookmark(id int,bookname varchar(20),chapter int,pages int)");
		db.execSQL("create table downloadbook(id int,name varchar(20),image BLOB,date varchar(20))");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
	}
    public boolean deleteDataBase(Context context)
    {
    	return context.deleteDatabase("BOOKSET");
    }
	public ArrayList<String> getAllBooksName()
	{
		ArrayList<String> temp=new ArrayList<String>();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor=db.query("book", new String[] {"name"},null, null, null, null, null);
		cursor.moveToFirst();
		for (int i = 0; i < cursor.getCount(); i++) {     
			String s = cursor.getString(0);
			temp.add(s);
			cursor.moveToNext();     
			}     
	    cursor.close(); 
	    db.close();
		return temp;
	}
	public ArrayList<String> getAllBooksPath()
	{
		ArrayList<String> temp=new ArrayList<String>();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor=db.query("book", new String[] {"path"},null, null, null, null, null);
		cursor.moveToFirst();
		for (int i = 0; i < cursor.getCount(); i++) {     
			String s = cursor.getString(cursor.getColumnIndex("path"));
			temp.add(s);
			cursor.moveToNext();     
			}     
	    cursor.close(); 
	    db.close();
		return temp;
	}
	@Override
	public void insert(Book book) {
		// TODO Auto-generated method stub
		loadChapter loadchapter=new loadChapter(context);
		ArrayList<String> params=new ArrayList<String>();
		params.add(book.getName());
		params.add(book.getPath());
		loadchapter.execute(params);
		this.insert(book.getName(), 0, 1);
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("name", book.getName());
		values.put("path", book.getPath());
		db.insert("book", null, values);
		db.close();
	}
	@Override
	public Book read(String temp) {
		// TODO Auto-generated method stub
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor=db.query("book", new String[] {"name","path"}, "path = "+temp, null, null, null, null);
		cursor.moveToFirst();
		String name=cursor.getString(cursor.getColumnIndex("name"));
		String path=cursor.getString(cursor.getColumnIndex("path"));
		cursor.close();
		db.close();
		return new Book(name,path);
	}
	@Override
	public void delete(Book book) {
		// TODO Auto-generated method stub
		SQLiteDatabase db = this.getReadableDatabase();
		String where= "path = '"+book.getPath()+"'";
		db.delete("book", where, null);
		db.close();
	}
	public void insert(String bookname,String chaptername,String text){
		// TODO Auto-generated method stub
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("name", bookname);
		values.put("chaptername", chaptername);
		values.put("content", text);
		db.insert("bookcontent", null, values);
		db.close();
	}
	public ArrayList<String> getChapters(String bookname)
	{
		
		ArrayList<String> data=new ArrayList<String>();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor=db.query("bookcontent", new String[] {"chaptername"},"name=?", new String[]{bookname}, null, null, null);
		cursor.moveToFirst();
		for(int i=0;i<cursor.getCount();i++)
		{
			String temp=cursor.getString(cursor.getColumnIndex("chaptername"));
            if(!data.contains(temp))
            	data.add(temp);
			cursor.moveToNext();
		}
	    cursor.close();
	    db.close();
		return data;
	}
	public String getBookChapterContent(String chaptername,String bookname)
	{
		String temp="";
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor=db.query("bookcontent", new String[] {"content"},"name=? and chaptername=?", new String[]{bookname,chaptername}, null, null, null);
		
		cursor.moveToFirst();  
		temp= cursor.getString(cursor.getColumnIndex("content"));
		cursor.moveToLast();
	    cursor.close();
	    db.close();
		return temp;
	}
	public void deleteBookMark(String bookname)
	{
		SQLiteDatabase db = this.getReadableDatabase();
		String where= "bookname = '"+bookname+"'";
		db.delete("bookmark", where, null);
		db.close();
	}
	public void insert(String bookname,int chapter,int page) {
		// TODO Auto-generated method stub
		SQLiteDatabase db1 = this.getWritableDatabase();
		Cursor cursor1=db1.query("bookmark", new String[]{"pages"}, "bookname=?", new String[]{bookname}, null, null, null);
		if(cursor1.getCount()!=0)
		{
			cursor1.close();
			db1.close();
			SQLiteDatabase db2=this.getWritableDatabase();
			ContentValues values = new ContentValues();
			values.put("bookname", bookname);
			values.put("chapter",chapter);
			values.put("pages",page);
			db2.update("bookmark", values, "bookname=?", new String[]{bookname});
			db2.close();
			return ;
		}
		else
		{
			cursor1.close();
			db1.close();
		}
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("bookname", bookname);
		values.put("chapter",chapter);
		values.put("pages",page);
		db.insert("bookmark", null, values);
		db.close();
	}
	public ArrayList<Integer> getImformation(String bookname)
	{
		ArrayList<Integer> temp=new ArrayList<Integer>();
		SQLiteDatabase db1 = this.getWritableDatabase();
		Cursor cursor1=db1.query("bookmark", new String[]{"pages"}, "bookname=?", new String[]{bookname}, null, null, null);
		if(cursor1.getCount()==0)
		{
			cursor1.close();
			db1.close();
			return temp;
		}
		else
		{
			cursor1.close();
			db1.close();
		}
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor=db.query("bookmark", new String[] {"chapter","pages"},"bookname=?", new String[]{bookname}, null, null, null);
		cursor.moveToFirst();
		for (int i = 0; i < cursor.getCount(); i++) {     
			int s = cursor.getInt(cursor.getColumnIndex("chapter"));
			temp.add(s);
			int a = cursor.getInt(cursor.getColumnIndex("pages"));
			temp.add(a);
			cursor.moveToNext();     
			}     
	    cursor.close(); 
	    db.close();
		return temp;
	}
	public void insert(String name,Bitmap image,String date) {
		// TODO Auto-generated method stub
		SQLiteDatabase db1 = this.getWritableDatabase();
		Cursor cursor1=db1.query("downloadbook", new String[]{"date"}, "name=?", new String[]{name}, null, null, null);
		if(cursor1.getCount()!=0)
		{
			cursor1.close();
			db1.close();
			return;
		}
		else
		{
			cursor1.close();
			db1.close();
		}
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("name", name);
		final ByteArrayOutputStream os = new ByteArrayOutputStream();   
		image.compress(Bitmap.CompressFormat.JPEG, 100, os); 
		values.put("image", os.toByteArray());
		values.put("date", date);
		db.insert("downloadbook", null, values);
		db.close();
	}
	public ArrayList<String> getAllBooksName_download()
	{
		ArrayList<String> temp=new ArrayList<String>();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor=db.query("downloadbook", new String[] {"name"},null, null, null, null, null);
		cursor.moveToFirst();
		for (int i = 0; i < cursor.getCount(); i++) {     
			String s = cursor.getString(cursor.getColumnIndex("name"));
			temp.add(s);
			cursor.moveToNext();     
			}     
	    cursor.close(); 
	    db.close();
		return temp;
	}
	public ArrayList<Bitmap> getAllBooksImage()
	{
		ArrayList<Bitmap> temp=new ArrayList<Bitmap>();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor=db.query("downloadbook", new String[] {"image"},null, null, null, null, null);
		cursor.moveToFirst();
		for (int i = 0; i < cursor.getCount(); i++) {     
			byte[] s = cursor.getBlob(cursor.getColumnIndex("image"));
			temp.add(BitmapFactory.decodeByteArray(s,0,s.length));
			cursor.moveToNext();     
			}     
	    cursor.close(); 
	    db.close();
		return temp;
	}
	public ArrayList<String> getAllBookDate()
	{
		ArrayList<String> temp=new ArrayList<String>();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor=db.query("downloadbook", new String[] {"date"},null, null, null, null, null);
		cursor.moveToFirst();
		for (int i = 0; i < cursor.getCount(); i++) {     
			String s = cursor.getString(cursor.getColumnIndex("date"));
			temp.add(s);
			cursor.moveToNext();     
			}     
	    cursor.close();
	    db.close();
		return temp;
	}
	public Bitmap getBookImage(String bookname)
	{
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor=db.query("downloadbook",null,null,null,null,null,null);
		cursor.moveToFirst();
		for (int i = 0; i < cursor.getCount(); i++) {     
			String s = cursor.getString(cursor.getColumnIndex("name"));
			if(s.equals(bookname))
			{
				byte[] s1 = cursor.getBlob(cursor.getColumnIndex("image"));
				cursor.close();
				db.close();
				Log.i("bookImageDataBase", "find the image");
				return BitmapFactory.decodeByteArray(s1,0,s1.length);
			}
			cursor.moveToNext();     
		}    
		
		cursor.close();
		db.close();
		return null;
	}
	public void delete(String bookname)
	{
		SQLiteDatabase db = this.getReadableDatabase();
		db.delete("downloadbook", "name=?", new String[]{bookname});
		db.close();
	}
	public void deleteBookContent(String bookname)
	{
		SQLiteDatabase db = this.getReadableDatabase();
		db.delete("bookcontent", "name=?", new String[]{bookname});
		db.close();
	}

	
	
    
}
