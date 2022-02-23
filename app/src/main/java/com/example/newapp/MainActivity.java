package com.example.newapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.newapp.userinfo.UserInfo;
import com.example.newapp.usermodel.UserModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    RecyclerViewFragment recyclerViewFragment;
    LayoutInflater inflater;
    View popupView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        apiCall();
        inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        popupView = inflater.inflate(R.layout.popup_window, null);


    }
    private void apiCall() {
        new ApiManager("http://myewards.in/").service.getMenuItemListing("15657", "15", "24019","", "", "", "", "1", "50", "", "", "", "name", "asc", "", "").enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                Toast.makeText(MainActivity.this, "Success!", Toast.LENGTH_SHORT).show();

                Log.e("Data Error: ", ""+response.body().data.item_list.get(0).item_name);
                recyclerViewFragment =new RecyclerViewFragment();
                recyclerViewFragment.list.addAll(response.body().data.item_list);
                setFragment(recyclerViewFragment);
            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
    protected void setFragment(RecyclerViewFragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fl_recycle, fragment);
        fragmentTransaction.commit();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.user_info: startActivity(new Intent(getApplicationContext(), UserInfo.class));
        }
        return super.onOptionsItemSelected(item);
    }
}

