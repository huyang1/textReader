package com.example.bean;

import java.io.File;
import java.util.ArrayList;

/**
 * ���ܵ���txt�ļ���·������
 * Created by Administrator on 2016/10/12.
 */
public class AllBook {
    private static ArrayList<String> Book=new ArrayList<String>();
    public static ArrayList<String> getFindBook()
    {
        return Book;
    }
    public static void addBook(String str)
    {
        Book.add(str);
    }
    public static void clear()
    {
        Book=new ArrayList<String>();
    }
}
