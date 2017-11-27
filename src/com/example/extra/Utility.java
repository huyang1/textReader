package com.example.extra;

import java.lang.reflect.Field;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ListAdapter;

/**
 * ���listviewǶ��gridview��gridviewֻ��ʾһ������
 * @author Administrator
 *
 */
public class Utility {
    public static void setGridViewHeightBasedOnChildren(GridView gridView) {  
        // ��ȡGridView��Ӧ��Adapter  
        ListAdapter listAdapter = gridView.getAdapter();  
        if (listAdapter == null) {  
            return;  
        }  
        int rows;  
        int columns = 0;  
        int horizontalBorderHeight = 0;  
        Class<?> clazz = gridView.getClass();  
        try {  
            // ���÷��䣬ȡ��ÿ����ʾ�ĸ���  
            Field column = clazz.getDeclaredField("mRequestedNumColumns");  
            column.setAccessible(true);  
            columns = (Integer) column.get(gridView);  
            // ���÷��䣬ȡ�ú���ָ��߸߶�  
            Field horizontalSpacing = clazz  
                    .getDeclaredField("mRequestedHorizontalSpacing");  
            horizontalSpacing.setAccessible(true);  
            horizontalBorderHeight = (Integer) horizontalSpacing.get(gridView);  
        } catch (Exception e) {  
            // TODO: handle exception  
            e.printStackTrace();  
        }  
        // �ж�������������ÿ�и����Ƿ��������������������ж��࣬��Ҫ��һ��  
        if (listAdapter.getCount() % columns > 0) {  
            rows = listAdapter.getCount() / columns + 1;  
        } else {  
            rows = listAdapter.getCount() / columns;  
        }  
        int totalHeight = 0;  
        for (int i = 0; i < rows; i++) { // ֻ����ÿ��߶�*����  
            View listItem = listAdapter.getView(i, null, gridView);
            listItem.measure(0, 0); // ��������View �Ŀ��  
            
            totalHeight += listItem.getMeasuredHeight(); // ͳ������������ܸ߶�  
        }  
        ViewGroup.LayoutParams params = gridView.getLayoutParams();  
        params.height = totalHeight + horizontalBorderHeight * (rows - 1)+40;// �����Ϸָ����ܸ߶�
        gridView.setLayoutParams(params);  
    }
    
    public static void setGridViewHeightBasedOnMultiChildren(GridView gridView) {  
        // ��ȡGridView��Ӧ��Adapter  
        ListAdapter listAdapter = gridView.getAdapter();  
        if (listAdapter == null) {  
            return;  
        }  
        int rows;  
        int columns = 0;  
        int horizontalBorderHeight = 0;  
        Class<?> clazz = gridView.getClass();  
        try {  
            // ���÷��䣬ȡ��ÿ����ʾ�ĸ���  
            Field column = clazz.getDeclaredField("mRequestedNumColumns");  
            column.setAccessible(true);  
            columns = (Integer) column.get(gridView);  
            // ���÷��䣬ȡ�ú���ָ��߸߶�  
            Field horizontalSpacing = clazz  
                    .getDeclaredField("mRequestedHorizontalSpacing");  
            horizontalSpacing.setAccessible(true);  
            horizontalBorderHeight = (Integer) horizontalSpacing.get(gridView);  
        } catch (Exception e) {  
            // TODO: handle exception  
            e.printStackTrace();  
        }  
        // �ж�������������ÿ�и����Ƿ��������������������ж��࣬��Ҫ��һ��  
        if (listAdapter.getCount() % columns > 0) {  
            rows = listAdapter.getCount() / columns + 1;  
        } else {  
            rows = listAdapter.getCount() / columns;  
        }  
        int totalHeight = 0;  
        for (int i = 0; i < rows; i++) { // ֻ����ÿ��߶�*����  
            View listItem = listAdapter.getView(i, null, gridView);
            listItem.measure(0, 0); // ��������View �Ŀ��  
            
            totalHeight += listItem.getMeasuredHeight(); // ͳ������������ܸ߶�  
        }  
        ViewGroup.LayoutParams params = gridView.getLayoutParams();  
        params.height = totalHeight + horizontalBorderHeight * (rows - 1)+2;// �����Ϸָ����ܸ߶�
        gridView.setLayoutParams(params);  
    } 
}
