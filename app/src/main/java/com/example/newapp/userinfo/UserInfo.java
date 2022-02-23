package com.example.newapp.userinfo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.example.newapp.ApiManager;
import com.example.newapp.R;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserInfo extends AppCompatActivity {
//    public List<UserInfoUser> userInfoList = new ArrayList<UserInfoUser>();
    UserInfoModel userInfoUser = new UserInfoModel();
    TextView tvNumber;
    EditText edtName, edtEmail;
    ImageView imgUserLogo;

    private Uri mImageUri;
    public int PICK_IMAGE_REQUEST = 200;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
//        userInfoUser = new UserInfoModel();
        userApiCall();
        tvNumber = findViewById(R.id.userPage_tvNumber);
        edtName = findViewById(R.id.userPage_userName);
        edtEmail = findViewById(R.id.userPage_userEmail);
        imgUserLogo = (ImageView) findViewById(R.id.userPage_userIcon);
//        tvNumber.setText("" +userInfoUser.data.users.mobile);

        imgUserLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageSelect();
            }
        });

    }

    private void userApiCall(){
        new ApiManager("http://myewards.in/").service.getUserInfoData("15657", "9735821398", "Android", "timewaste", "91").enqueue(new Callback<UserInfoModel>() {
            @Override
            public void onResponse(Call<UserInfoModel> call, Response<UserInfoModel> response) {
                Toast.makeText(getApplicationContext(), "Success!", Toast.LENGTH_SHORT).show();
//                userInfoUser.addAll(response.body().data.item_list);
                userInfoUser = response.body();
//                Toast.makeText(UserInfo.this, "Error: " +response.body().data.users.mobile, Toast.LENGTH_SHORT).show();
//                Toast.makeText(getApplicationContext(), "Code: " +userInfoUser.error, Toast.LENGTH_SHORT).show();
                tvNumber.setText(userInfoUser.data.users.mobile);
                edtEmail.setText(userInfoUser.data.users.email);
                edtName.setText(userInfoUser.data.users.name);


                Glide.with(getApplicationContext())
                        .load("http://myewards.in/" +userInfoUser.data.users.image)
                        .into(imgUserLogo);


            }

            @Override
            public void onFailure(Call<UserInfoModel> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Failed!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void imageSelect() {
        Intent intent;
        if (Build.VERSION.SDK_INT < 19) {
            intent = new Intent(Intent.ACTION_GET_CONTENT);
        } else {
            intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
        }
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "Select Picture"),
                PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                // The user picked a image.
                // The Intent's data Uri identifies which item was selected.
                if (data != null) {

                    // This is the key line item, URI specifies the name of the data
                    mImageUri = data.getData();

                    // Removes Uri Permission so that when you restart the device, it will be allowed to reload.
                    this.grantUriPermission(this.getPackageName(), mImageUri, Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    final int takeFlags = Intent.FLAG_GRANT_READ_URI_PERMISSION;
                    this.getContentResolver().takePersistableUriPermission(mImageUri, takeFlags);

                    // Saves image URI as string to Default Shared Preferences
                    SharedPreferences preferences =
                            PreferenceManager.getDefaultSharedPreferences(this);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("image", String.valueOf(mImageUri));
                    editor.commit();
                    Log.e("Image Path: ", "" +mImageUri);
                    // Sets the ImageView with the Image URI
                    imgUserLogo.setImageURI(mImageUri);
                    imgUserLogo.invalidate();
                }
            }
        }
    }

    public void isImageSet(){

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String mImageUri = preferences.getString("image", null);

        if (mImageUri != null){
            imgUserLogo.setImageURI(Uri.parse(mImageUri));
        }
        else{
            imgUserLogo.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.user));
//            mImage.setImageDrawable(null);
//            mImage.invalidate();
        }
    }



}

/*/

[Today Work]

1. Create User Info activity.
2. Fetch User Info API data using Retrofit.
3. Fix bug on retrofit model.
4. Load API image using Glide.
5. Implement function for set image from local storage.
 */