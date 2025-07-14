package com.example.itransit;

public class BusCardItem {
    private int mImageResource;
    private String mBusNo;
    private String changeStation;
    private String Price;

    public BusCardItem(int imageResource,String text,String text1,String text2){
        mImageResource=imageResource;
        mBusNo=text;
        changeStation=text1;
        Price=text2;
    }

    public String getChangeStation() {
        return changeStation;
    }

    public String getPrice() {
        return Price;
    }

    public int getImageResource(){
        return mImageResource;
    }
    public String getText(){
        return mBusNo;
    }
}
