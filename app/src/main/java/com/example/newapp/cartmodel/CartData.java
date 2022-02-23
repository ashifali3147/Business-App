package com.example.newapp.cartmodel;

import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CartData {
    public static List<String> itemID = new ArrayList<>();
    public static List<Double> itemPrice = new ArrayList<>();
    public static List<String> itemTitle = new ArrayList<>();
    public static List<Integer> itemCount = new ArrayList<>();

    public static void addModelData(String ID, Double Price, String Title){


        if(itemID.contains(ID)){

            itemCount.add(itemID.indexOf(ID), itemCount.get(itemID.indexOf(ID))+1);

            Log.e("Count IF: ", "IndexValue: " +itemCount.get(itemID.indexOf(ID)) + "ID List: " +itemID);
            Log.e("ID", "Getting ID: " +ID);

        }
        else{
            itemID.add(ID);
            itemPrice.add(Price);
            itemTitle.add(Title);
            itemCount.add(itemID.indexOf(ID),1);
            Log.e("Count Else: ", "IndexValue: " +itemCount.get(itemID.indexOf(ID)) + " ID: " +itemID);
            Log.e("ID", "Getting ID: " +ID);
        }
    }
    public void clearModelData(){
        itemID.clear();
        itemPrice.clear();
        itemTitle.clear();
        itemCount.clear();

    }
}
