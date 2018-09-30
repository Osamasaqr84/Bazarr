package com.bazar.bazars;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bazar.bazars.Adapters.ad_photo_adapter;
import com.bazar.bazars.Connect_TO_Server.ApiService;
import com.bazar.bazars.Connect_TO_Server.FileUtils;
import com.bazar.bazars.Items.Ad_photo_item;
import com.bazar.bazars.MultiSelectImagePicker.Action;
import com.bazar.bazars.MultiSelectImagePicker.CustomGallery;
import com.kosalgeek.android.photoutil.GalleryPhoto;
import com.kosalgeek.android.photoutil.PhotoLoader;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by AG on 3/29/2017.
 */

public class UP_LOAD extends AppCompatActivity {
    GalleryPhoto galleryPhoto;
    ImageView btnUpload, btnPickImage;
    String mediaPath;
    ImageView imgView;
    EditText title,content,mobile;
    private static final int PERMISSION_REQUEST_CODE = 1;
    ArrayList<Uri> fileuri = new ArrayList<Uri>();
    PreferenceHelper helper;
    String[] mediaColumns = { MediaStore.Video.Media._ID };
    ProgressDialog progressDialog;
    int pos = 0;
    ProgressDialog dialog = null;
    RecyclerView photos;
    ad_photo_adapter adapter;
    ArrayList<Ad_photo_item> items;
    Receiver receivera;
    boolean isRecieverRegistered = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.up_loaaad);
        helper = new PreferenceHelper(this);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Uploading...");
        //linearMain = (LinearLayout)findViewById(R.id.linearMain);
        galleryPhoto = new GalleryPhoto(getApplicationContext());
        helper.putImagePreference("0");
        btnUpload = (ImageView) findViewById(R.id.ivGallary);
        title = (EditText) findViewById(R.id.title);
        content = (EditText) findViewById(R.id.typeclass);
        mobile = (EditText) findViewById(R.id.mobile);
        photos = (RecyclerView)findViewById(R.id.photo_recycler);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        photos.setLayoutManager(manager);
        photos.setHasFixedSize(true);
        photos.setNestedScrollingEnabled(true);
        items = new ArrayList<Ad_photo_item>();
        adapter = new ad_photo_adapter(this,items);
        photos.setAdapter(adapter);
        btnPickImage = (ImageView) findViewById(R.id.IvCamera);
//        imgView = (ImageView) findViewById(R.id.preview);

        btnUpload.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // showSimpleProgressDialog(getApplicationContext(),"",getString(R.string.adding),false);

                uploadFile(fileuri);
            }
        });

        btnPickImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Dialog dialog = new Dialog(UP_LOAD.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(true);
                dialog.setContentView(R.layout.which_upload);
                dialog.show();

                Button open_gallary = (Button) dialog.findViewById(R.id.open_the_gallary);
                Button open_camera = (Button) dialog.findViewById(R.id.open_the_camera);

                open_camera.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                        if(checkPermission()) {
                            CropImage.activity()
                                    .setGuidelines(CropImageView.Guidelines.ON)
                                    .start(UP_LOAD.this);
                        }else {
                            Toast.makeText(UP_LOAD.this, getString(R.string.enablecamera), Toast.LENGTH_SHORT).show();
                        }
                        dialog.dismiss();
                    }
                });

                open_gallary.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
//                        Intent intent = new Intent();
//                        intent.setType("image/*");
//                         intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
//                        intent.setAction(Intent.ACTION_GET_CONTENT);
//                        intent.addCategory(Intent.CATEGORY_OPENABLE);
//                        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1);
                        Intent i = new Intent(Action.ACTION_MULTIPLE_PICK);
                        startActivityForResult(i, 200);
                        dialog.dismiss();

                    }
                });


//
//                Intent intent = new Intent();
//                intent.setType("image/*");
//                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
//                intent.setAction(Intent.ACTION_GET_CONTENT);
//                startActivityForResult(Intent.createChooser(intent,"Select Picture"), 1);
            }
        });

    }


    //  public void showSimpleProgressDialog(Context context, String title,
    //                                     String msg, boolean isCancelable) {
    String imgPath;
    public Uri setImageUri() {
        // Store image in dcim
        File file = new File(Environment.getExternalStorageDirectory() + "/DCIM/", "image" + new Date().getTime() + ".png");
        Uri imgUri = Uri.fromFile(file);
        this.imgPath = file.getAbsolutePath();
        return imgUri;
    }
    String patha;
    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        patha = path;
        return Uri.parse(path);

    }
    public String getAbsolutePath(Uri uri) {
        String[] projection = { MediaStore.MediaColumns.DATA };
        @SuppressWarnings("deprecation")
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        if (cursor != null) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } else
            return null;
    }

    public String getImagePath() {
        return imgPath;
    }
    public ProgressDialog createProgressDialog(Activity mContext) {
        dialog = new ProgressDialog(mContext);
        try {
            dialog.show();
        } catch (WindowManager.BadTokenException e) {
        }
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(R.layout.progress_dialog_layout);
        return dialog;
    }


    private void askFormPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (ContextCompat.checkSelfPermission(UP_LOAD.this, android.Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(UP_LOAD.this, android.Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED) {
                if (!ActivityCompat.shouldShowRequestPermissionRationale(UP_LOAD.this,
                        android.Manifest.permission.ACCESS_COARSE_LOCATION)) {
                    ActivityCompat.requestPermissions(UP_LOAD.this,
                            new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.CAMERA, android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.WAKE_LOCK},
                            8);
                }
            }
        }
    }

    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(UP_LOAD.this, Manifest.permission.CAMERA);
        if (result == PackageManager.PERMISSION_GRANTED) {

            return true;
        } else {
            return false;
        }
    }

    private void requestPermission() {

        if (ActivityCompat.shouldShowRequestPermissionRationale(UP_LOAD.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            Toast.makeText(UP_LOAD.this, "Write External Storage permission allows us to do store images. Please allow this permission in App Settings.", Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions(UP_LOAD.this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
        }
    }
    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Picture"), 1);
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                if (data.getClipData() == null) {
                    try {
                        Ad_photo_item photoItem = new Ad_photo_item();
                        Uri itm = data.getData();
                        galleryPhoto.setPhotoUri(itm);
                        String photoPath = galleryPhoto.getPath();
                        try {
                            Bitmap bitmap = PhotoLoader.init().from(photoPath).requestSize(512, 512).getBitmap();
                            FileOutputStream bmpFile = new FileOutputStream(photoPath);
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bmpFile);
                            ImageView imageView = new ImageView(getApplicationContext());
                            LinearLayout.LayoutParams layoutParams =
                                    new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                            ViewGroup.LayoutParams.MATCH_PARENT);
                            imageView.setLayoutParams(layoutParams);
                            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                            imageView.setPadding(0, 0, 0, 10);
                            imageView.setAdjustViewBounds(true);
                            imageView.setImageBitmap(bitmap);
                            fileuri.add(itm);
                            photoItem.setImage(itm);
                            //linearMain.addView(imageView);
                            items.add(photoItem);
                            adapter.notifyDataSetChanged();
                            if (items.size() >= 1) {
                                photos.setVisibility(View.VISIBLE);
                            }
                        } catch (FileNotFoundException e) {
                            Toast.makeText(getApplicationContext(), "Error while loading image", Toast.LENGTH_SHORT).show();

                        }
                    } finally {

                    }

                } else {
                    if (data.getClipData() != null)
                        for (int i = 0; i < data.getClipData().getItemCount(); i++) {
                            ClipData.Item item = data.getClipData().getItemAt(i);
                            Ad_photo_item photoItem = new Ad_photo_item();
                            galleryPhoto.setPhotoUri(item.getUri());
                            String photoPath = galleryPhoto.getPath();

                            try {
                                Bitmap bitmap = PhotoLoader.init().from(photoPath).requestSize(512, 512).getBitmap();
                                FileOutputStream bmpFile = new FileOutputStream(photoPath);
                                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bmpFile);
                                ImageView imageView = new ImageView(getApplicationContext());
                                LinearLayout.LayoutParams layoutParams =
                                        new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                                ViewGroup.LayoutParams.MATCH_PARENT);
                                imageView.setLayoutParams(layoutParams);
                                imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                                imageView.setPadding(0, 0, 0, 10);
                                imageView.setAdjustViewBounds(true);
                                imageView.setImageBitmap(bitmap);
                                fileuri.add(item.getUri());
                                photoItem.setImage(item.getUri());
                                //linearMain.addView(imageView);
                                items.add(photoItem);
                            } catch (FileNotFoundException e) {
                                Toast.makeText(getApplicationContext(), "Error while loading image", Toast.LENGTH_SHORT).show();

                            }
                        }
                    if (items.size() >= 1) {
                        photos.setVisibility(View.VISIBLE);
                    }
                    adapter.notifyDataSetChanged();
                }
            } else if (requestCode == 4) {
                // if (data.getClipData() == null) {
                try {
                    Ad_photo_item photoItem = new Ad_photo_item();
//                            Uri itm = data.getData();
//                            galleryPhoto.setPhotoUri(itm);
                    String photoPath = getImagePath();

                    try {
                        Bitmap bitmap = PhotoLoader.init().from(photoPath).requestSize(512, 512).getBitmap();
                        Uri itm = getImageUri(UP_LOAD.this,bitmap);
                        FileOutputStream bmpFile = new FileOutputStream(photoPath);
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bmpFile);
                        ImageView imageView = new ImageView(getApplicationContext());
                        LinearLayout.LayoutParams layoutParams =
                                new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                        ViewGroup.LayoutParams.MATCH_PARENT);
                        imageView.setLayoutParams(layoutParams);
                        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                        imageView.setPadding(0, 0, 0, 10);
                        imageView.setAdjustViewBounds(true);
                        imageView.setImageBitmap(bitmap);
                        fileuri.add(itm);
                        photoItem.setImage(itm);
                        //linearMain.addView(imageView);
                        items.add(photoItem);
                        adapter.notifyDataSetChanged();
                        if (items.size() >= 1) {
                            photos.setVisibility(View.VISIBLE);
                        }
                    } catch (FileNotFoundException e) {
                        Toast.makeText(getApplicationContext(), "Error while loading image", Toast.LENGTH_SHORT).show();

                    }
                } finally {

                }
            }else  if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
                CropImage.ActivityResult result = CropImage.getActivityResult(data);
                if (resultCode == RESULT_OK) {
                    Bitmap bitmap = null;
                    FileOutputStream bmpFile = null;
                    Ad_photo_item photoItem = new Ad_photo_item();
                    try {
                        galleryPhoto.setPhotoUri(result.getUri());
                        String photoPath = galleryPhoto.getPath();
                        bitmap = PhotoLoader.init().from(photoPath).requestSize(512, 512).getBitmap();
                        bmpFile = new FileOutputStream(photoPath);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    assert bitmap != null;
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bmpFile);
                    Uri itm = getImageUri(UP_LOAD.this,bitmap);
                    File target = new File(patha);
                    Log.d(" target_path", "" + patha);
                    if (target.exists() && target.isFile() && target.canWrite()) {
                        target.delete();
                        Log.d("d_file", "" + target.getName());
                    }
                    fileuri.add(itm);

                    photoItem.setImage(itm);
                    //linearMain.addView(imageView);
                    items.add(photoItem);
                    adapter.notifyDataSetChanged();
                    if (items.size() >= 1) {
                        photos.setVisibility(View.VISIBLE);
                    }
                } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                    Exception error = result.getError();
                }
            }else
            if (requestCode == 100 && resultCode == Activity.RESULT_OK) {
                // adapter.clear();

                //viewSwitcher.setDisplayedChild(1);
                String single_path = data.getStringExtra("single_path");
                //  imageLoader.displayImage("file://" + single_path, imgSinglePick);

            } else if (requestCode == 200) {
                String[] all_path = data.getStringArrayExtra("all_path");
                FileOutputStream bmpFile = null;

                ArrayList<CustomGallery> dataT = new ArrayList<CustomGallery>();

                for (String string : all_path) {
                    // CustomGallery item = new CustomGallery();
                    // item.sdcardPath = string;
                    Bitmap bitmap = null;
                    Ad_photo_item photoItem = new Ad_photo_item();

                    //FileOutputStream bmpFile = null;
                    try {
                        bitmap = PhotoLoader.init().from(string).requestSize(512, 512).getBitmap();
                        bmpFile = new FileOutputStream(string);

                        File target = new File(string);
                        Log.d(" target_path", "" + string);
                        if (target.exists() && target.isFile() && target.canWrite()) {
                            target.delete();
                            Log.d("d_file", "" + target.getName());
                        }
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    if(bitmap!= null) {
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bmpFile);

                        Uri uri = getImageUri(UP_LOAD.this, bitmap);

                        photoItem.setImage(uri)
                        ;
                        items.add(photoItem);

                        fileuri.add(uri);
                    }
                }
                if (items.size() >= 1) {
                    photos.setVisibility(View.VISIBLE);
                    adapter.notifyDataSetChanged();
                }

            }
        } else {

        }
    }
    // }



    public   ProgressDialog mProgressDialog;

    public  void showSimpleProgressDialog(Context context, String title,
                                          String msg, boolean isCancelable) {


        mProgressDialog = new ProgressDialog(UP_LOAD.this);
        mProgressDialog.setMessage(msg);
        mProgressDialog.show();
    }



    public  void removeSimpleProgressDialog() {

        if (mProgressDialog != null) {
            if (mProgressDialog.isShowing()) {
                mProgressDialog.dismiss();
                mProgressDialog = null;
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (receivera == null) {
            receivera = new Receiver();
            IntentFilter filter = new IntentFilter(BroadcastHelper.ACTION_NAME);
            this.registerReceiver(receivera, filter);
            isRecieverRegistered = true;
        }
        askFormPermissions();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(isRecieverRegistered){
            unregisterReceiver(receivera);
        }
    }

    private void uploadFile(ArrayList<Uri> fileUri) {
        if(title.getText().length()<1){
            Toast.makeText(UP_LOAD.this,getString(R.string.entertitle), Toast.LENGTH_SHORT).show();
            return;
        }else if(mobile.getText().length()<1){
            Toast.makeText(UP_LOAD.this,getString(R.string.enterphone), Toast.LENGTH_SHORT).show();
            return;
        }else if(content.getText().length()<1){
            Toast.makeText(UP_LOAD.this,getString(R.string.entertarget), Toast.LENGTH_SHORT).show();
            return;
        }else if(fileUri.size()<1) {
            Toast.makeText(UP_LOAD.this, getString(R.string.chooseimage), Toast.LENGTH_SHORT).show();
            return;
        }
        showSimpleProgressDialog(UP_LOAD.this,"",getString(R.string.adding),false);

        //showSimpleProgressDialog(this,"",getString(R.string.adding),false);
        Retrofit.Builder  builder = new Retrofit.Builder().baseUrl("http://alsog.net/api/")
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();
        List<MultipartBody.Part> parts = new ArrayList<>();

        for (int i = 0; i < fileUri.size(); i++) {
            parts.add(prepareFilePart("" + i ,fileUri.get(i)));
        }
        Intent intent = getIntent();
        String cityid = intent.getStringExtra("city_id");
        String subid = intent.getStringExtra("sub_id");
        String subsubid = intent.getStringExtra("subsub_id");
        String maincatid = intent.getStringExtra("cat_id");
        String year = intent.getStringExtra("year");
        if(year == null){
            year = "0";
        }
        if(subsubid == null){
            subsubid="0";
        }
        Log.i("cityid", cityid);
        ApiService client = retrofit.create(ApiService.class);
        if(maincatid == null){
            Toast.makeText(UP_LOAD.this,getString(R.string.entertitle), Toast.LENGTH_SHORT).show();
            return;
        }
        showSimpleProgressDialog(UP_LOAD.this,"",getString(R.string.adding),false);
        {
            Call<ResponseBody> call = client.uploadAlbums(
                    createPartFromString(maincatid),
                    createPartFromString(subid),
                    createPartFromString(subsubid),
                    createPartFromString(cityid),
                    createPartFromString(year),
                    createPartFromString(title.getText().toString()),
                    createPartFromString(content.getText().toString()),
                    createPartFromString(helper.getUserID()),
                    createPartFromString(mobile.getText().toString()),
                    parts
            );

            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call,
                                       Response<ResponseBody> response) {
                    String serverResponse = null;
                    try {
                        serverResponse = response.body().string();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                    //Log.i("call", "onResponse: " + response.body().string());
                    removeSimpleProgressDialog();
                    Toast.makeText(UP_LOAD.this, getString(R.string.addsucc), Toast.LENGTH_SHORT).show();
                    finish();
                    Log.v("Upload", String.valueOf(serverResponse));
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    removeSimpleProgressDialog();
                    Log.e("Upload error:", t.getMessage());
                    Log.i("respone", "onResponse: " + t.getMessage());

                }


            });
        }
        removeSimpleProgressDialog();
    }
    @NonNull
    private RequestBody createPartFromString(String descriptionString) {
        return RequestBody.create(
                MultipartBody.FORM, descriptionString);
    }


    @NonNull
    private MultipartBody.Part prepareFilePart(String partName, Uri fileUri) {

        File file = FileUtils.getFile(this, fileUri);

        RequestBody requestFile =
                RequestBody.create(
                        MediaType.parse(getContentResolver().getType(fileUri)),
                        file
                );
        return MultipartBody.Part.createFormData(partName, file.getName(), requestFile);
    }

    private class Receiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context arg0, Intent arg1) {
            //   Log.v("r", "receive " + arg1.getStringExtra(BroadcastHelper.BROADCAST_EXTRA_METHOD_NAME));
            String methodName = arg1.getStringExtra(BroadcastHelper.BROADCAST_EXTRA_METHOD_NAME);
            if (methodName != null && methodName.length() > 0) {
                //  Log.v("receive", methodName);
                switch (methodName) {
                    case "title":
                        adapter.notifyDataSetChanged();
                        pos = arg1.getIntExtra("position",0);
                        fileuri.remove(pos);
                        break;


                }
            }}
    }

}
