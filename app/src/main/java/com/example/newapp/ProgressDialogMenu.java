package com.example.newapp;

import android.app.ProgressDialog;
import android.content.Context;

public class ProgressDialogMenu {
    ProgressDialog  progressDialog;
    public void show(Context context, String title, String message){
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage(message); // Setting Message
        progressDialog.setTitle(title); // Setting Title
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
        progressDialog.show(); // Display Progress Dialog
        progressDialog.setCancelable(false);
    }
    public void dismiss(){
        progressDialog.dismiss();
    }
}
