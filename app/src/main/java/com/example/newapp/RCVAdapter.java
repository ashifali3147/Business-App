package com.example.newapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.util.Log;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Timer;

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


    RCVAdapter(Context context, List<UserData> userModel) {
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
        holder.tvPrice.setText("₹ " + userModel.get(position).cost_price);
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
        holder.tvCancelOrder.setOnClickListener(view -> {
            Toast.makeText(ctx, "Order Cancel", Toast.LENGTH_SHORT).show();
        });
        Calendar calendar = Calendar.getInstance();

        long hour_in_mili = (calendar.get(Calendar.HOUR)) * 3600000;
        long min_in_mili = (calendar.get(Calendar.MINUTE)) * 60000;
        long sec_in_mili = (calendar.get(Calendar.SECOND)) * 1000;
        if (holder.timer != null) {
            holder.timer.cancel();
        }

        if(holder.date.get(position) >= calendar.get(Calendar.DATE)){
            Log.e("Date Check:", "Position: " +position+" Check:true ");
            Log.e("Check Time", "OutTime " +holder.timeCount.get(position));
            Log.e("Check Time", "CurrentTime " +(hour_in_mili+min_in_mili+sec_in_mili));
            if (!holder.timeCount.isEmpty()) {
                if(holder.timeCount.get(position)  > (hour_in_mili+min_in_mili+sec_in_mili)){
                    holder.timer = new CountDownTimer(holder.timeCount.get(position)  - (hour_in_mili+min_in_mili+sec_in_mili), 1000) {
                        @Override
                        public void onTick(long l) {
                            long seconds = l / 1000;
                            long minutes = seconds / 60;
                            long hours = minutes / 60;
                            long days = hours / 24;
                            String time = days + " " + "days" + " :" + hours % 24 + ":" + minutes % 60 + ":" + seconds % 60;
                            holder.tvTimer.setText(time);
                        }

                        @Override
                        public void onFinish() {
                            holder.tvTimer.setText("Time up!");
                            holder.tvCancelOrder.setTextColor(Color.parseColor("#bdbdbd"));
                            holder.tvCancelOrder.setClickable(false);

                        }
                    }.start();
                }

            }
            else {
                holder.tvTimer.setText("Time up!");
                holder.tvCancelOrder.setTextColor(Color.parseColor("#bdbdbd"));
                holder.tvCancelOrder.setClickable(false);
            }
        }
        else {
            Log.e("Date Check:", "Position: " +position+" Check:false ");
            holder.tvTimer.setText("Time up!");
        }


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
            if (charSequence.toString().isEmpty()) {
                filteredList.addAll(backupData);
            } else {
                for (UserData names : backupData) {
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
        TextView tvTitle, tvPrice, tvDescription, tvReadMore, tvTimer, tvCancelOrder;
        ImageView imageView, img_add;


        TextView tv_cartItem, tv_cartPrice;
        ImageView img_CartConfirm, img_CartRemove;
        CountDownTimer timer;
        RelativeLayout layoutCart;
        Calendar calendarCurrent = Calendar.getInstance();

        List<Integer> date = new ArrayList<>();
        List<Long> timeCount = new ArrayList<>();

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            tvPrice = (TextView) itemView.findViewById(R.id.tvPrice);
            tvDescription = (TextView) itemView.findViewById(R.id.tvDescription);
            imageView = (ImageView) itemView.findViewById(R.id.img_user);
            img_add = (ImageView) itemView.findViewById(R.id.img_addButton);
            tvTimer = (TextView) itemView.findViewById(R.id.tv_timer);
            tvCancelOrder = (TextView) itemView.findViewById(R.id.tv_cancelOrder);
//            -----------------------------[Cart Menu]---------------------------

//            tv_cartPrice = (TextView) itemView.findViewById(R.id.cart_price);
//            tv_cartItem = (TextView) itemView.findViewById(R.id.cart_item);
//            img_CartConfirm = (ImageView) itemView.findViewById(R.id.cart_confirm);
//            img_CartRemove = (ImageView) itemView.findViewById(R.id.cart_delete);
//            layoutCart = (RelativeLayout) itemView.findViewById(R.id.layout_cart);
//            Calendar calendar = Calendar.getInstance();
//            int time_in_mili = (calendar.get(Calendar.MINUTE)) * 60000;
            Date c = Calendar.getInstance().getTime();
            System.out.println("Current time => " + c);
            SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
            String formattedDate = df.format(c);
            Log.e("Date: ", "" +formattedDate);
//
//            if (timer != null) {
//                timer.cancel();
//            }
//            for (int i = 0; i < timeCount.size(); i++) {
//                timer = new CountDownTimer(timeCount.get(i) - time_in_mili,500) {
//                    @Override
//                    public void onTick(long l) {
//                        long seconds = l / 1000;
//                        long minutes = seconds / 60;
//                        long hours = minutes / 60;
//                        long days = hours / 24;
//                        String time = days + " " + "days" + " :" + hours % 24 + ":" + minutes % 60 + ":" + seconds % 60;
//                        tvTimer.setText(time);
//                    }
//
//                    @Override
//                    public void onFinish() {
//                        tvTimer.setText("Time up!");
//                    }
//                }.start();
//            }
            int dd = calendarCurrent.get(Calendar.DATE);
            Log.e("Time: ", "DAY_OF_Year" +dd);
            long hour = calendarCurrent.get(Calendar.HOUR);
            Log.e("Time: ", "HOUR" +hour);
            long outTime = calendarCurrent.get(Calendar.MINUTE);
            for (int i = 0; i < userModel.size(); i++) {
                date.add(dd);
                timeCount.add((hour*3600000) + (outTime * 60000));
                outTime++;
            }

        }
    }
}


