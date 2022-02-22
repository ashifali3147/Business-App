package com.example.newapp;

import android.widget.ImageView;

public interface AddToCart {
    public void addItem(double currentItemPrice, int currentItemID, String currentItemTitle);
    public void removeItem();
}
