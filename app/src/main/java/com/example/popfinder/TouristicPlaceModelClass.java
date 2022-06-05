package com.example.popfinder;

public class TouristicPlaceModelClass {

    private int imageview1;
    private String textview1;
    private String textview2;

    private String lats;
    private String longs;

    public TouristicPlaceModelClass(int imageview1, String textview1, String textview2, String lats, String longs) {
        this.imageview1 = imageview1;
        this.textview1 = textview1;
        this.textview2 = textview2;
        this.lats = lats;
        this.longs = longs;
    }

    public int getImageview1() {
        return imageview1;
    }

    public void setImageview1(int imageview1) {
        this.imageview1 = imageview1;
    }

    public String getTextview1() {
        return textview1;
    }

    public void setTextview1(String textview1) {
        this.textview1 = textview1;
    }

    public String getTextview2() {
        return textview2;
    }

    public void setTextview2(String textview2) {
        this.textview2 = textview2;
    }


    public String getLats() {
        return lats;
    }

    public void setLats(String lats) {
        this.lats = lats;
    }

    public String getLongs() {
        return longs;
    }

    public void setLongs(String longs) {
        this.longs = longs;
    }
}
