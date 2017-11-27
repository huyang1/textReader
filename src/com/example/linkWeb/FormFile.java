package com.example.linkWeb;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * �ϴ��ļ�
 */
public class FormFile {
    /* �ϴ��ļ������� */
    private byte[] data;
    private InputStream inStream;
    private File file;
    /* �ļ����� */
    private String filename;
    /* �����������*/
    private String parameterName;
    /* �������� */
    private String contentType = "application/octet-stream";
    
    public FormFile(String filename, byte[] data, String parameterName, String contentType) {
        this.data = data;
        this.filename = filename;
        this.parameterName = parameterName;
        if(contentType!=null) this.contentType = contentType;
    }
    
    public FormFile(String filename, File file, String parameterName, String contentType) {
        this.filename = filename;
        this.parameterName = parameterName;
        this.file = file;
        try {
            this.inStream = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        if(contentType!=null) this.contentType = contentType;
    }
    public FormFile()
    {
    	this.filename = null;
        this.parameterName = null;
        this.file = null;
    }
    public File getFile() {
        return file;
    }

    public InputStream getInStream() {
        return inStream;
    }

    public byte[] getData() {
        return data;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilname(String filename) {
        this.filename = filename;
    }

    public String getParameterName() {
        return parameterName;
    }

    public void setParameterName(String parameterName) {
        this.parameterName = parameterName;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }
    
}