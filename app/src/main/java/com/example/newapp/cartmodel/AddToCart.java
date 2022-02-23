package com.example.newapp.cartmodel;

import android.widget.ImageView;

public interface AddToCart {
    public void addItem(double currentItemPrice, int currentItemID, String currentItemTitle);
    public void removeItem();
}
