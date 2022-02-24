package com.example.newapp.userinfo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.example.newapp.ApiManager;
import com.example.newapp.ProgressDialogMenu;
import com.example.newapp.R;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.util.HashMap;

import id.zelory.compressor.Compressor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserInfo extends AppCompatActivity {
    private static final int REQUEST_CODE_ASK_SD_CARD_READ_PERMISSIONS = 77;
    UserInfoModel userInfoUser = new UserInfoModel();
    TextView tvNumber;
    EditText edtName, edtEmail;
    ImageView imgUserLogo;
    Button btn_Upload;
    private Uri mImageUri;
    public int PICK_IMAGE_REQUEST = 200;

    File compressedImageFile;
    ProgressDialogMenu progressDialogGetData = new ProgressDialogMenu();
    ProgressDialogMenu progressDialogUpdate = new ProgressDialogMenu();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
//        userInfoUser = new UserInfoModel();
        progressDialogGetData.show(UserInfo.this, "Getting User Data", "Please Wait");
        userApiCall();
//        progressDialogMenu.dismiss();
        tvNumber = findViewById(R.id.userPage_tvNumber);
        edtName = findViewById(R.id.userPage_userName);
        edtEmail = findViewById(R.id.userPage_userEmail);
        imgUserLogo = (ImageView) findViewById(R.id.userPage_userIcon);
        btn_Upload = (Button) findViewById(R.id.userPage_btnUpdateData);
//        tvNumber.setText("" +userInfoUser.data.users.mobile);
        btn_Upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialogUpdate.show(UserInfo.this, "Updating", "Please Wait");
                uploadData();
            }
        });
        imgUserLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                imageSelect();
                pickImage();
            }
        });

    }

    private void uploadData() {


        HashMap<String, RequestBody> mapdata = new HashMap<>();
//        File file = new File(String.valueOf(mImageUri)+".jpg");

        File file = new File(FileUtil.getPath(mImageUri, this)); // *this* here is context, which can be Activity/Fragment

//        File reduceImageFile = compressImageSize(file);

        try {
            compressedImageFile = new Compressor(this).compressToFile(file);
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, ""+e, Toast.LENGTH_SHORT).show();
        }

//        File mSaveBit; // Your image file
//        String filePath = file.getPath();
//        Bitmap bitmap = BitmapFactory.decodeFile(filePath);

//        Bitmap converetdImage = getResizedBitmap(bitmap, 500);

//        File reduceImageFIle = bitmapToFile(UserInfo.this, converetdImage, "upload_image.jpg");

//        try {
//            File compressedImageFile = new Compressor(this).compressToFile(file);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        File file = new File(mImageUri.getPath());
        Log.e("File Location: ", "" + file);

        MultipartBody.Part imageBody = prepareFilePart("image", compressedImageFile);
        mapdata.put("name", RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(edtName.getText())));
        mapdata.put("email", RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(edtEmail.getText())));
        mapdata.put("user_id", RequestBody.create(MediaType.parse("multipart/form-data"), "15441566"));
        mapdata.put("merchant_id", RequestBody.create(MediaType.parse("multipart/form-data"), "15657"));
        mapdata.put("gender", RequestBody.create(MediaType.parse("multipart/form-data"), ""));
        mapdata.put("marital", RequestBody.create(MediaType.parse("multipart/form-data"), ""));
        mapdata.put("dob", RequestBody.create(MediaType.parse("multipart/form-data"), ""));
        mapdata.put("pincode", RequestBody.create(MediaType.parse("multipart/form-data"), ""));
        mapdata.put("address", RequestBody.create(MediaType.parse("multipart/form-data"), ""));
        mapdata.put("address", RequestBody.create(MediaType.parse("multipart/form-data"), ""));

        new ApiManager("http://myewards.in/").service.updateUserInfoData(mapdata, imageBody).enqueue(new Callback<UserInfoModel>() {
            @Override
            public void onResponse(Call<UserInfoModel> call, Response<UserInfoModel> response) {
                progressDialogUpdate.dismiss();
                Toast.makeText(UserInfo.this, "Updated", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<UserInfoModel> call, Throwable t) {
                progressDialogUpdate.dismiss();
                Toast.makeText(UserInfo.this, "Error", Toast.LENGTH_SHORT).show();
                Log.e("Error: ", "" + t.getMessage());
            }
        });
    }

    private static MultipartBody.Part prepareFilePart(String partName, File file) {
        if (file == null)
            return null;
        else {
            RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            return MultipartBody.Part.createFormData(partName, file.getAbsolutePath(), requestFile);
        }
    }


    private void userApiCall() {
        new ApiManager("http://myewards.in/").service.getUserInfoData("15657", "9735821398", "Android", "timewaste", "91").enqueue(new Callback<UserInfoModel>() {
            @Override
            public void onResponse(Call<UserInfoModel> call, Response<UserInfoModel> response) {
                progressDialogGetData.dismiss();
                if (!response.body().error){
                    Toast.makeText(UserInfo.this, response.body().message, Toast.LENGTH_SHORT).show();
                    userInfoUser = response.body();
//                Toast.makeText(UserInfo.this, "Error: " +response.body().data.users.mobile, Toast.LENGTH_SHORT).show();
//                Toast.makeText(getApplicationContext(), "Code: " +userInfoUser.error, Toast.LENGTH_SHORT).show();
                    tvNumber.setText(userInfoUser.data.users.mobile);
                    edtEmail.setText(userInfoUser.data.users.email);
                    edtName.setText(userInfoUser.data.users.name);


                    Glide.with(getApplicationContext())
                            .load("http://myewards.in/" + userInfoUser.data.users.image)
                            .placeholder(R.drawable.user)
                            .into(imgUserLogo);


                }else Toast.makeText(UserInfo.this, response.body().message, Toast.LENGTH_SHORT).show();

              //  Toast.makeText(getApplicationContext(), "Success!", Toast.LENGTH_SHORT).show();
//                userInfoUser.addAll(response.body().data.item_list);

            }

            @Override
            public void onFailure(Call<UserInfoModel> call, Throwable t) {
                progressDialogGetData.dismiss();
                Toast.makeText(getApplicationContext(), "Failed!", Toast.LENGTH_SHORT).show();
            }
        });
    }




    private void pickImage() {
        int hasReadPermission = ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        if (hasReadPermission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        } else {
            imageSelect();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_CODE_ASK_SD_CARD_READ_PERMISSIONS && grantResults.length != 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            imageSelect();
        }
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

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        // Check which request we're responding to
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == PICK_IMAGE_REQUEST) {
//            // Make sure the request was successful
//            if (resultCode == RESULT_OK) {
//                // The user picked a image.
//                // The Intent's data Uri identifies which item was selected.
//                if (data != null) {
//
//                    // This is the key line item, URI specifies the name of the data
//                    mImageUri = data.getData();
//
//                    // Removes Uri Permission so that when you restart the device, it will be allowed to reload.
//                    this.grantUriPermission(this.getPackageName(), mImageUri, Intent.FLAG_GRANT_READ_URI_PERMISSION);
//                    final int takeFlags = Intent.FLAG_GRANT_READ_URI_PERMISSION;
//                    this.getContentResolver().takePersistableUriPermission(mImageUri, takeFlags);
//
//                    // Saves image URI as string to Default Shared Preferences
//                    SharedPreferences preferences =
//                            PreferenceManager.getDefaultSharedPreferences(this);
//                    SharedPreferences.Editor editor = preferences.edit();
//                    editor.putString("image", String.valueOf(mImageUri));
//                    editor.commit();
//                    Log.e("Image Path: ", "" + mImageUri);
//                    // Sets the ImageView with the Image URI
//                    imgUserLogo.setImageURI(mImageUri);
//                    imgUserLogo.invalidate();
//                }
//            }
//        }
//    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE_REQUEST) {
            mImageUri = data.getData();
            imgUserLogo.setImageURI(mImageUri);
        }
    }

    public void isImageSet() {

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String mImageUri = preferences.getString("image", null);

        if (mImageUri != null) {
            imgUserLogo.setImageURI(Uri.parse(mImageUri));
        } else {
            imgUserLogo.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.user));
//            mImage.setImageDrawable(null);
//            mImage.invalidate();
        }
    }

    public File compressImageSize(File file){
        try {

            // BitmapFactory options to downsize the image
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            o.inSampleSize = 6;
            // factor of downsizing the image

            FileInputStream inputStream = new FileInputStream(file);
            //Bitmap selectedBitmap = null;
            BitmapFactory.decodeStream(inputStream, null, o);
            inputStream.close();

            // The new size we want to scale to
            final int REQUIRED_SIZE=75;

            // Find the correct scale value. It should be the power of 2.
            int scale = 1;
            while(o.outWidth / scale / 2 >= REQUIRED_SIZE &&
                    o.outHeight / scale / 2 >= REQUIRED_SIZE) {
                scale *= 2;
            }

            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            inputStream = new FileInputStream(file);

            Bitmap selectedBitmap = BitmapFactory.decodeStream(inputStream, null, o2);
            inputStream.close();

            // here i override the original image file
            file.createNewFile();
            FileOutputStream outputStream = new FileOutputStream(file);

            selectedBitmap.compress(Bitmap.CompressFormat.JPEG, 100 , outputStream);

            return file;
        } catch (Exception e) {
            return null;
        }
    }
//    public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
//
//
//
//    int width = image.getWidth();
//    int height = image.getHeight();
//
//    float bitmapRatio = (float)width / (float) height;
//    if (bitmapRatio > 1) {
//        width = maxSize;
//        height = (int) (width / bitmapRatio);
//    } else {
//        height = maxSize;
//        width = (int) (height * bitmapRatio);
//    }
//    return Bitmap.createScaledBitmap(image, width, height, true);
//    }

//    -----------------------------------------------
//    public static File bitmapToFile(Context context, Bitmap bitmap, String fileNameToSave) { // File name like "image.png"
//        //create a file to write bitmap data
//        File file = null;
//        try {
//            file = new File(Environment.getExternalStorageDirectory() + File.separator + fileNameToSave);
//            file.createNewFile();
//
////Convert bitmap to byte array
//            ByteArrayOutputStream bos = new ByteArrayOutputStream();
//            bitmap.compress(Bitmap.CompressFormat.PNG, 0 , bos); // YOU can also save it in JPEG
//            byte[] bitmapdata = bos.toByteArray();
//
////write the bytes in file
//            FileOutputStream fos = new FileOutputStream(file);
//            fos.write(bitmapdata);
//            fos.flush();
//            fos.close();
//            return file;
//        }catch (Exception e){
//            e.printStackTrace();
//            return file; // it will return null
//        }
//    }

}

/*/

[Today Work]

1. Create User Info activity.
2. Fetch User Info API data using Retrofit.
3. Fix bug on retrofit model.
4. Load API image using Glide.
5. Implement function for set image from local storage.
 */