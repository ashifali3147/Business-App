package com.example.newapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newapp.cartmodel.CartData;

import java.util.ArrayList;
import java.util.List;

public class RCV_ConfirmAdapter extends RecyclerView.Adapter<RCV_ConfirmAdapter.MyViewHolder>{
    List<CartData> cartDataList = new ArrayList<>();

    PlusMinusCountButton plusMinusCountButton;
    CartData cartData;
    Context context;
    public RCV_ConfirmAdapter(Context context, List<CartData> cartData) {
        this.cartDataList = cartData;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.custom_order_item, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
//        int a = cartData.itemPrice.get((2));
//        Toast.makeText(context, " Hiii" +a, Toast.LENGTH_SHORT).show();

        holder.tvPrice.setText("₹ " + String.format("" +(cartData.itemPrice.get(position) * cartData.itemCount.get(position)),"%.00f"));
        holder.tvTitle.setText(cartData.itemTitle.get(position));
        holder.tvCount.setText("" + cartData.itemCount.get(position));


//        ------------------[Add Button]-----------------------------
        holder.img_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                totalItemCount = itemCount.get(position) + 1;
//                itemCount.add(position, totalItemCount);
                plusMinusCountButton.plus(position, cartData.itemID.get(position), holder.tvCount, holder.tvPrice);
//                holder.tvCount.setText(Integer.toString(itemCount.get(position)));
                notifyDataSetChanged();
//                notifyItemChanged(position);
            }
        });

//        --------------------[Minus Button]--------------------
        holder.img_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                totalItemCount = itemCount.get(position) - 1;
//                itemCount.add(position, totalItemCount);
                plusMinusCountButton.minus(position, cartData.itemID.get(position), holder.tvCount, holder.tvPrice);
//                notifyItemChanged(position);
                notifyDataSetChanged();
            }
        });

//        ------------------------[Item Count]--------------------------

    }

    @Override
    public int getItemCount() {
        return cartData.itemID.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvTitle, tvPrice, tvDescription, tvCount;
        ImageView imageView, img_add, img_minus;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            tvPrice = (TextView) itemView.findViewById(R.id.tvPrice);
            tvDescription = (TextView) itemView.findViewById(R.id.tvDescription);
            imageView = (ImageView) itemView.findViewById(R.id.img_user);
            img_add = (ImageView) itemView.findViewById(R.id.img_addButton);
            img_minus = (ImageView) itemView.findViewById(R.id.img_minusButton);
            tvCount = (TextView) itemView.findViewById(R.id.tv_count);
        }
    }
}
