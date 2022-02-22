package com.example.newapp;

import android.content.Context;
import android.text.Html;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class ReadMore {

    Popup popup = new Popup();


    public void popupReadmore(TextView tv_moreText, TextView tv_readMore,Context context){

//        if(tv_moreText.getLineCount() > 2){
////            Toast.makeText(context, "PopUp", Toast.LENGTH_SHORT).show();
//            tv_readMore.setVisibility(View.VISIBLE);
//
//        }
//        else {
////            tv_moreText.getLayout().getLineStart(1)
//
////            tv_moreText.add(substring);
//        }
        String fullDescription = tv_moreText.getText().toString();
        String text = fullDescription;

        if (text.length()>34) {

            text=text.substring(0,26)+".. " ;
            tv_moreText.setText(Html.fromHtml(text+"<font color='red'> <u>Read</u></font>"));
            tv_moreText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    Toast.makeText(context, ""+ , Toast.LENGTH_SHORT).show();
//                    Toast.makeText(context, "" +tv_moreText.getText().length(), Toast.LENGTH_SHORT).show();
                    popup.showPopup(context, view, fullDescription);
                }
            });

        }
    }
}
