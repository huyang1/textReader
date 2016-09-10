package com.example.DataBase;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class sqliteDataBase extends SQLiteOpenHelper implements operationDataBase{
	public sqliteDataBase(Context context)
	{
		super(context,"BOOKSET",null,1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL("create table book(id int,name varchar(20),path varchar(20))");
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
		return temp;
	}
	@Override
	public void insert(Book book) {
		// TODO Auto-generated method stub
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

	
	
    
}
