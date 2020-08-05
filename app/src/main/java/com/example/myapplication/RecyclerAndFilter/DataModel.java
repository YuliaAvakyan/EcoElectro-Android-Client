package com.example.myapplication.RecyclerAndFilter;

import com.example.myapplication.R;

import java.util.Locale;

public class DataModel {
    private int imageDrawable;
    private String title;
    private String subTitle;

    private int itemId;

    public DataModel(int id) {
        imageDrawable = R.drawable.cardlogo;
        title = String.format("Title %d Goes Here", id);
        subTitle = String.format("Sub title %d goes here", id);
    }

    public DataModel(String title, String subTitle, int itemId) {
        imageDrawable = R.drawable.cardlogo;
        this.title = title;
        this.subTitle = subTitle;
        this.itemId = itemId;
    }

    public int getImageDrawable() {
        return imageDrawable;
    }

    public String getTitle() {
        return title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public int getItemId() {
        return itemId;
    }
}
