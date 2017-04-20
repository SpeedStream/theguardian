package com.example.speedstream.theguardian;

/**
 * Created by speedstream on 14/04/17.
 */

public class News {
    private String mSectionName;
    private String mPublicationDate;
    private String mWebTitle;
    private String mWebURL;
    private String mSectionUrl;

    public News() {
        /**
         * Used only to retrive data directly.
         * */
    }

    /*Used to show sections*/
    public News(String sectionName, String sectionUrl){
        mSectionName = sectionName;
        mSectionUrl = sectionUrl;
    }

    /*Used to show news of one section*/
    public News(String sectionName, String webTitle, String publicationDate, String webURL){
        mSectionName=sectionName;
        mWebTitle=webTitle;
        mPublicationDate=publicationDate;
        mWebURL=webURL;
    }

    public String getmSectionName(){return mSectionName;}
    public String getmPublicationDate(){return mPublicationDate;}
    public String getmWebTitle(){return mWebTitle;}
    public String getmwebURL(){return mWebURL;}
    public String getmSectionURL(){return mSectionUrl;}
}