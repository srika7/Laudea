package com.example.laudea;

import java.util.Date;
import java.util.List;

public class DataClass {

    private String dataTitle;
    private String dataDesc;
    private String dataLang;
    private String dataImage;
    private String key;
    private Date uploadDate; // New field to represent the upload date

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getDataTitle() {
        return dataTitle;
    }

    public String getDataDesc() {
        return dataDesc;
    }

    public String getDataLang() {
        return dataLang;
    }

    public String getDataImage() {
        return dataImage;
    }

    public Date getUploadDate() {
        return uploadDate;
    }

    // Setter for uploadDate
    public void setUploadDate(Date uploadDate) {
        this.uploadDate = uploadDate;
    }

    // Constructor without uploadDate
    public DataClass(String dataTitle, String dataDesc, String dataLang, String dataImage) {
        this.dataTitle = dataTitle;
        this.dataDesc = dataDesc;
        this.dataLang = dataLang;
        this.dataImage = dataImage;
        // Set uploadDate to current date when creating an instance
        this.uploadDate = new Date();
    }

    public DataClass() {
    }
}
