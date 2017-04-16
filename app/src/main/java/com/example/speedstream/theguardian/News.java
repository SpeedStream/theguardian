package com.example.speedstream.theguardian;

/**
 * Created by speedstream on 14/04/17.
 */

public class News {
    private String mSectionId;
    private String mSectionName;
    private String mPublicationDate;
    private String mWebTitle;
    private String mWebURL;

    public News(String sectionId, String sectionName, String publicationDate, String webTitle, String webURL){
        mSectionId=sectionId;
        mSectionName=sectionName;
        mPublicationDate=publicationDate;
        mWebTitle=webTitle;
        mWebURL=webURL;
    }

    public String getmSectionId(){return mSectionId;}
    public String getmSectionName(){return mSectionName;}
    public String getmPublicationDate(){return mPublicationDate;}
    public String getmWebTitle(){return mWebTitle;}
    public String getMwebURL(){return mWebURL;}
}