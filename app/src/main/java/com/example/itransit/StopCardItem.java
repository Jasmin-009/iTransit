package com.example.itransit;

public class StopCardItem {
    private int mImageResource;
    private String mText;

    public StopCardItem(int imageResource,String text){
        mImageResource=imageResource;
        mText=text;
    }

    public int getImageResource(){
        return mImageResource;
    }
    public String getText(){
        return mText;
    }
}