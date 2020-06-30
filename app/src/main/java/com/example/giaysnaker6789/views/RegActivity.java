package com.example.giaysnaker6789.views;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.giaysnaker6789.BaseResponse.ResponseUser1s;
import com.example.giaysnaker6789.R;
import com.example.giaysnaker6789.models.user1s;
import com.example.giaysnaker6789.network.APIimage;
import com.example.giaysnaker6789.network.ApiUser;
import com.example.giaysnaker6789.network.RetrofitService;
import com.example.giaysnaker6789.viewModels.RegisterViewmodel;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegActivity extends BaseActivity {
        EditText edttk,edtmk,edtremk,edtaddress,edtname,edtphone,edtemail;
        Button btndangky,btnloginface;
        CircleImageView image;
        ImageView imgMenu;
        RegisterViewmodel registerViewmodel;

    int REQUEST_FOLDER = 321;
    String realpath;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        registerViewmodel = ViewModelProviders.of(this).get(RegisterViewmodel.class);
        initView();
        chooseImage();
        dangky();
        backpre();

    }

    private void backpre() {
        imgMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void dangky() {
        btndangky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(realpath!=null){
                    File file = new File(realpath);
                    String file_path = file.getAbsolutePath();
                    String[] mangtenfile = file_path.split("\\.");
                    file_path = mangtenfile[0] + System.currentTimeMillis() + "." + mangtenfile[1];
                    RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                    MultipartBody.Part body = MultipartBody.Part.createFormData("image", file_path, requestBody); //image laf field treen sever sẽ đọc
                    registerViewmodel.UploadImage(body).observe(RegActivity.this, new Observer<String>() {
                        @Override
                        public void onChanged(String s) {
                            if(s!=""){
                                dangkine(s);
                            }else{
                                Toast.makeText(RegActivity.this, "có lỗi xảy ra trong quá trình up ảnh", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }else{
                    dangkine("");
                    //Toast.makeText(RegActivity.this, "vui lòng chọn 1 hình ảnh", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
    ProgressDialog progressDialog;
    private void dangkine(String path) {
        String tk=edttk.getText().toString();
        String mk=edtmk.getText().toString();
        String dc=edtaddress.getText().toString();
        String phone=edtphone.getText().toString();
        String name=edtname.getText().toString();
        String email=edtemail.getText().toString();

        progressDialog = new ProgressDialog(RegActivity.this);
        progressDialog.setMessage("đang đăng ký chờ tý...");
        progressDialog.show();

        registerViewmodel.RegisterNomal(tk,mk,email,dc,phone,name,path).observe(this, new Observer<ResponseUser1s>() {
            @Override
            public void onChanged(ResponseUser1s responseUser1s) {
                if(responseUser1s.getMess().equals("SUCCESS")){
                    user1s user=  responseUser1s.getData();
                        Toast.makeText(RegActivity.this, "đăng kí thành công", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                }
                if(responseUser1s.getMess().equals("EXISTMAIL")){
                    Toast.makeText(RegActivity.this, "Email đã có người sử dụng vui lòng chọn tài khoản khác", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
                if(responseUser1s.getMess().equals("EXISTACC")){
                    Toast.makeText(RegActivity.this, "Tài khoản đã có người sử dụng vui lòng chọn tài khoản khác", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();

                }

            }
        });
    }

    private void chooseImage() {
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(Intent.ACTION_PICK);
                in.setType("image/*");  // cài đặt chỉ lấy định dạng là image
                startActivityForResult(in, REQUEST_FOLDER);
            }
        });
    }

    public String getRealPathFromURI(Uri contentUri) {
        String path = null;
        String[] proj = {MediaStore.MediaColumns.DATA};
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
            path = cursor.getString(column_index);
        }
        cursor.close();
        return path;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_FOLDER && resultCode == RESULT_OK && data != null) { // load anh tu thu vien
            Uri uri = data.getData();
            realpath = getRealPathFromURI(uri);
            Log.d("TAG", "onActivityResult: "+realpath);
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                image.setImageBitmap(bitmap);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }


        super.onActivityResult(requestCode, resultCode, data);
    }


    private void initView() {
        btndangky=findViewById(R.id.btndangky);
        btnloginface=findViewById(R.id.login_facebook);
        edttk=findViewById(R.id.edttk);
        edtmk=findViewById(R.id.edtpass);
        edtremk=findViewById(R.id.edtrepass);
        edtname=findViewById(R.id.edtname);
        edtaddress=findViewById(R.id.edtaddress);
        edtphone=findViewById(R.id.edtphone);
        image=findViewById(R.id.profile_image);
        imgMenu = findViewById(R.id.imgback);
        edtemail=findViewById(R.id.edtemail);
    }
}
