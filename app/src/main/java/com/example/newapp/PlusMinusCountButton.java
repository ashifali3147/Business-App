package com.example.newapp;

import android.view.View;
import android.widget.TextView;

public interface PlusMinusCountButton {
    public void plus(int position, String ID, TextView tvItemCount, TextView tvItemPrice);
    public void minus(int position, String ID, TextView tvItemCount, TextView tvItemPrice);

}
