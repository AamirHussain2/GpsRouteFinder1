package com.example.gpsroutefinder.models;

public class ExploreNearByRVModel {

    private final int imageButton;
    private final String textView;

    public ExploreNearByRVModel(int imageButton, String textView) {
        this.imageButton = imageButton;
        this.textView = textView;
    }

    public int getImageButton() {
        return imageButton;
    }

    public String getTextView() {
        return textView;
    }

}
