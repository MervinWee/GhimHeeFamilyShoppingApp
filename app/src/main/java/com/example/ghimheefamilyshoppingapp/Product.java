package com.example.ghimheefamilyshoppingapp;

import android.widget.TextView;

public class Product {
    public TextView tvName;
    public String image;

    public Product(){

    }

    public Product(TextView tvName, String image) {
        this.tvName = tvName;
        this.image = image;
    }

    public Product(String product, int image) {

    }

    public TextView getTvName() {
        return tvName;
    }

    public void setTvName(TextView tvName) {
        this.tvName = tvName;
    }

    public static int getImage() {
        return R.drawable.logoimage;
    }

    public void setImage(String image) {
        this.image = image;
    }


}
