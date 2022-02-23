package com.example.newapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newapp.cartmodel.AddToCart;
import com.example.newapp.cartmodel.CartData;
import com.example.newapp.usermodel.UserData;

import java.util.ArrayList;
import java.util.List;

public class RCVAdapter extends RecyclerView.Adapter<RCVAdapter.MyViewHolder> implements Filterable {
    List<UserData> userModel;
    List<UserData> backupData;
    Context ctx;
    ReadMore readMore = new ReadMore();
    Popup popup = new Popup();
    ArrayList<String> id_list = new ArrayList<>();
//    RecyclerViewFragment recyclerViewFragment;
    //    SelectDeselect selectDeselect;
    AddToCart addToCart;
    public CartData cartData = new CartData();

    RCVAdapter(Context context, List<UserData> userModel){
        this.userModel = userModel;
        this.ctx = context;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(ctx).inflate(R.layout.custom_card, (ViewGroup) parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {
//----------------------------[Set data to the RecyclerView]-----------------------------
        holder.tvTitle.setText(userModel.get(position).item_name);
        holder.tvPrice.setText("₹ " +userModel.get(position).cost_price);
        holder.tvDescription.setText(userModel.get(position).description);
        holder.imageView.setClipToOutline(true);
//        holder.tvDescription.setMovementMethod(new ScrollingMovementMethod());
//        -------------------------------------------[Item Add to Cart]----------------------------------------------
        holder.img_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Double itemPrice = Double.valueOf(userModel.get(position).cost_price);
                int itemID = Integer.valueOf(userModel.get(position).item_id);

                cartData.addModelData(userModel.get(position).item_id, Double.valueOf(userModel.get(position).cost_price), userModel.get(position).item_name);
                addToCart.addItem(itemPrice, itemID, userModel.get(position).item_name);
                Toast.makeText(ctx, "Item Added!", Toast.LENGTH_SHORT).show();
            }
        });
        readMore.popupReadmore(holder.tvDescription, holder.tvReadMore, ctx);
//
//----------------------------------------------------------------------------------------
//        ------------------------------[OnClick Event on Name]-----------------------
        holder.tvTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//-------------------------------[Select/ Deselect Functionality]------------------------
//                String idData = userModel.get(position).id;
//                selectDeselect = new SelectDeselect() {
//                    @Override
//                    public void select(Context ctx, String idData) {
//
//                    }
//                };
//                selectDeselect.select(ctx, idData);

//                mainActivity.select(ctx, idData);
//                ------------------------------------------------------------------
            }
//            -------------------------------------------------------------------------
        });

//        ------------------------------------------------------

//        holder.img_CartRemove.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                addToCart.removeItem();
//            }
//        });
    }


    @Override
    public int getItemCount() {
        return userModel.size();
    }
    //-------------------------------[Filter Method]----------------------------------
    @Override
    public Filter getFilter() {
        return filter;
    }
    Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {

            List<UserData> filteredList = new ArrayList<>();
            if (charSequence.toString().isEmpty()){
                filteredList.addAll(backupData);
            }
            else{
                for (UserData names: backupData){
                    if (names.item_name.toLowerCase().contains(charSequence.toString().toLowerCase())) {
                        filteredList.add(names);
                    }
                }
            }
            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredList;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            userModel.clear();
            userModel.addAll((List<UserData>) filterResults.values);
            notifyDataSetChanged();
        }
    };

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvPrice, tvDescription, tvReadMore;
        ImageView imageView, img_add;

        TextView tv_cartItem, tv_cartPrice;
        ImageView img_CartConfirm, img_CartRemove;

        RelativeLayout layoutCart;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            tvPrice = (TextView) itemView.findViewById(R.id.tvPrice);
            tvDescription = (TextView) itemView.findViewById(R.id.tvDescription);
            imageView = (ImageView) itemView.findViewById(R.id.img_user);
            img_add = (ImageView) itemView.findViewById(R.id.img_addButton);
//            -----------------------------[Cart Menu]---------------------------

//            tv_cartPrice = (TextView) itemView.findViewById(R.id.cart_price);
//            tv_cartItem = (TextView) itemView.findViewById(R.id.cart_item);
//            img_CartConfirm = (ImageView) itemView.findViewById(R.id.cart_confirm);
//            img_CartRemove = (ImageView) itemView.findViewById(R.id.cart_delete);
//            layoutCart = (RelativeLayout) itemView.findViewById(R.id.layout_cart);
        }
    }
}

