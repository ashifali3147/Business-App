package com.example.newapp;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {
    @FormUrlEncoded
    @POST("api/v5/whitelabel/getMenuItemListingTagFilter")
    Call<UserModel> getMenuItemListing
            (
                    @Field("merchant_id") String merchantID,
                    @Field("outlet_id") String outlet_id,
                    @Field("section_id") String section_id,
                    @Field("supercategory_id") String supercategory_id,
                    @Field("category_id") String category_id,
                    @Field("subcategory_id") String subcategory_id,
                    @Field("microcategory_id") String microcategory_id,
                    @Field("current_page") String current_page,
                    @Field("page_size") String page_size,
                    @Field("start_price") String start_price,
                    @Field("end_price") String end_price,
                    @Field("name") String name,
                    @Field("sorting_value") String sorting_value,
                    @Field("sorting_action") String sorting_action,
                    @Field("tag_category_id") String tag_name_id,
                    @Field("item_type") String item_type
            );
}