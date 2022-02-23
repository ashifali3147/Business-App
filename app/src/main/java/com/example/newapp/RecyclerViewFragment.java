package com.example.newapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newapp.cartmodel.AddToCart;
import com.example.newapp.cartmodel.CartData;
import com.example.newapp.usermodel.UserData;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewFragment extends Fragment implements AddToCart {
    public List<UserData> list = new ArrayList<UserData>();
//    Button btnGetData;
    CartData cartData;
    //    View view;
    RecyclerView rv;
    public RCVAdapter adapter;
    TextView tv_cartItem, tv_cartPrice;
    ImageView img_CartConfirm, img_CartRemove;
    RelativeLayout layoutCart;
    int totalItemCount = 0;
    double totalPrice = 0;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.from(getContext()).inflate(R.layout.fragment_recycler_view, container, false);

        tv_cartPrice = (TextView) view.findViewById(R.id.cart_price);
        tv_cartItem = (TextView) view.findViewById(R.id.cart_item);
        img_CartConfirm = (ImageView) view.findViewById(R.id.cart_confirm);
        img_CartRemove = (ImageView) view.findViewById(R.id.cart_delete);
        layoutCart = (RelativeLayout) view.findViewById(R.id.layout_cart);
        rv = (RecyclerView) view.findViewById(R.id.rv);

        cartData = new CartData();
//        Toast.makeText(getActivity(), "rfergr", Toast.LENGTH_SHORT).show();
        setAdapter();
//        Toast.makeText(getContext(), "OnCreate", Toast.LENGTH_SHORT).show();
//        view.setClipToOutline(true);
//        btnGetData = (Button) view.findViewById(R.id.btn_getData);
//        btnGetData.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                loadingBar.showProgress();
//
//            }
//        });
//        addItem();
        img_CartConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "WIN", Toast.LENGTH_SHORT).show();
                Intent confirmpage = new Intent(getContext(), BuyConfirm.class);
                startActivity(confirmpage);
            }
        });
        img_CartRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeItem();
            }
        });

        itemAlreadyInCart();
        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onResume() {
//        Toast.makeText(getContext(), "OnResume", Toast.LENGTH_SHORT).show();
        itemAlreadyInCart();
        setTotalItemCountAndPrice();
        super.onResume();
    }

    @Override
    public void onPause() {
//        Toast.makeText(getContext(), "OnPause", Toast.LENGTH_SHORT).show();
        super.onPause();
    }

    private void setAdapter() {
        rv.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new RCVAdapter(getContext(), list);
        adapter.addToCart = this;
        rv.setAdapter(adapter);

    }


    @Override
    public void addItem(double currentItemPrice, int currentItemID, String currentItemTitle) {

//        rv.setLayoutParams(new RelativeLayout.LayoutParams(300, 660));
        layoutCart.setVisibility(View.VISIBLE);
        totalItemCount += 1;
        totalPrice = totalPrice + currentItemPrice;

        setTotalItemCountAndPrice();
//
    }


    @Override
    public void removeItem() {
        layoutCart.setVisibility(View.GONE);
        totalItemCount = 0;
        totalPrice = 00;
        cartData.clearModelData();

        tv_cartPrice.setText("Price: " +totalPrice);
        tv_cartItem.setText("Item: " +totalItemCount);
    }
    public void itemAlreadyInCart(){
        if(!cartData.itemID.isEmpty()){
            layoutCart.setVisibility(View.VISIBLE);
            setTotalItemCountAndPrice();
        }
        else{
            layoutCart.setVisibility(View.GONE);
        }
    }
    public void setTotalItemCountAndPrice(){
        int totalItemCount = 0, totalPrice = 0;
        for(int i=0; i< cartData.itemID.size() ; i++){
            totalItemCount += cartData.itemCount.get(i);
            totalPrice += cartData.itemPrice.get(i) * cartData.itemCount.get(i);
        }
        tv_cartItem.setText("Item: " +totalItemCount);
        tv_cartPrice.setText(String.format("Price: " + totalPrice, "%.4f"));
    }

}

/***
 [Yesterday Work]
 1. Add new Activity page for order confirm page
 2. Create Recycler View Adapter for Activity
 3. Pass ordered data details through DataModel
 4. Perform Checking that item is already exist in the cart or not (Using Interface).
 5. If item is already exist then just increase the quantity by using custom functions.
 6. Work on add & minus quantity button.

 [Today Work]
 1. Update Cart Menu data on Fragment
 2. Fix some bug related to add & minus button
 3. Remove item from Recycler View when item count is zero.
 4. Add OrderConfirm & OrderCancel button on BuyConfirm Activity.
 5. Perform logic on buttons.
 6. Generate APK and send it to the Debapriyo.
 7. Working on gallery image access.
 8. Set Image from gallery.



 */