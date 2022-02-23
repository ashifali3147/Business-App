package com.example.newapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.newapp.cartmodel.CartData;

import java.util.List;

public class BuyConfirm extends AppCompatActivity implements PlusMinusCountButton{
    TextView tv_totalItem, tv_totalPrice;
    CartData cartData;
    List<CartData> cartDataList;
    int cfTotalItem;
    Double cfTotalPrice =0.00;
    RecyclerView rvConfirm;
    Button btnConfirm, btnCancel;
    RCV_ConfirmAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_confirm);

        tv_totalItem = findViewById(R.id.totalItem);
        tv_totalPrice = findViewById(R.id.totalPrice);

        rvConfirm = findViewById(R.id.recycle_for_buy_confirm);
        rvConfirm.setLayoutManager(new LinearLayoutManager(this));

        adapter = new RCV_ConfirmAdapter(this, cartDataList);
        adapter.plusMinusCountButton = this;
        rvConfirm.setAdapter(adapter);

        btnConfirm = (Button) findViewById(R.id.btn_confirm_order);
        btnCancel = (Button) findViewById(R.id.btn_cancel_order);
        
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(BuyConfirm.this, "Now your pocket is empty", Toast.LENGTH_SHORT).show();

                cartData = new CartData();
                cartData.clearModelData();
                Intent intent = new Intent(BuyConfirm.this, RecyclerViewFragment.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                finish();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(BuyConfirm.this, "You take a right decision", Toast.LENGTH_SHORT).show();
                cartData = new CartData();
                cartData.clearModelData();
                Intent intent = new Intent(BuyConfirm.this, RecyclerViewFragment.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                finish();
            }
        });


        setTotalPriceAndItem();
    }

    @Override
    public void plus(int position, String ID, TextView tvItemCount, TextView tvItemPrice) {
//        int currentItemCount = cartData.itemCount.get(position);
//        int updatedItemCount = cartData.itemCount.get(position) + 1;
        cartData.itemCount.set(position, (cartData.itemCount.get(cartData.itemID.indexOf(ID)) + 1));

//        Toast.makeText(context, "Added! " +cartData.itemCount.get(position), Toast.LENGTH_SHORT).show();
//        tvItemCount.setText("" +(cartData.itemCount.get(cartData.itemID.indexOf(ID))));
//        tvItemPrice.setText("₹ " + (cartData.itemPrice.get(cartData.itemID.indexOf(ID)) * cartData.itemCount.get(cartData.itemID.indexOf(ID))));
        setTotalPriceAndItem();
//        rvConfirm.notifyAll();
    }

    @Override
    public void minus(int position, String ID, TextView tvItemCount, TextView tvItemPrice) {
        if(cartData.itemCount.get(position) > 1) {
            cartData.itemCount.set(position, (cartData.itemCount.get(cartData.itemID.indexOf(ID)) - 1));

//            tvItemCount.setText("" +(cartData.itemCount.get(cartData.itemID.indexOf(ID))));
//            tvItemPrice.setText("₹ " + (cartData.itemPrice.get(cartData.itemID.indexOf(ID)) * cartData.itemCount.get(cartData.itemID.indexOf(ID))));
        }
        else{
            Toast.makeText(this, "Item Removed!", Toast.LENGTH_SHORT).show();
            cartData.itemID.remove(position);
            cartData.itemPrice.remove(position);
            cartData.itemTitle.remove(position);
            cartData.itemCount.remove(position);
        }
//        rvConfirm.notifyAll();
        setTotalPriceAndItem();
    }

//    -------------------------[Back Button Handle]-------------------------------
    @Override
    public void onBackPressed() {
        Toast.makeText(this, "Back Button", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(BuyConfirm.this, RecyclerViewFragment.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        finish();

    }
//    ---------------------------[Set Total Price and Items]-------------------------------
    public void setTotalPriceAndItem(){
        int totalItemCount = 0, totalPrice = 0;
        for(int i=0; i< cartData.itemID.size() ; i++){
            totalItemCount += cartData.itemCount.get(i);
            totalPrice += cartData.itemPrice.get(i) * cartData.itemCount.get(i);
        }
        tv_totalItem.setText("Item: " +totalItemCount);
        tv_totalPrice.setText("Price: " +totalPrice);
    }

}