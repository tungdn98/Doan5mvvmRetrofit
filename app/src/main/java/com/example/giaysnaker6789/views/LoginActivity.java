package com.example.giaysnaker6789.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.example.giaysnaker6789.BaseResponse.ResponseUser1s;
import com.example.giaysnaker6789.R;
import com.example.giaysnaker6789.config.SharedPref;
import com.example.giaysnaker6789.models.test;
import com.example.giaysnaker6789.models.user1s;
import com.example.giaysnaker6789.viewModels.LoginViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.messaging.FirebaseMessaging;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class LoginActivity extends BaseActivity {


    LoginViewModel loginViewModel;
    Button btndangky,btndangnhp;
    TextInputEditText edttk,edtmk;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        String us = SharedPref.read(SharedPref.USER, "");//read string in shared preference.
        String pass = SharedPref.read(SharedPref.PASS, "");//read int in shared preference.
        edttk.setText(us);
        edtmk.setText(pass);
         dangnhap();
         btndangky.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
//                 SharedPref.remove(SharedPref.LOGIN);
//                 SharedPref.remove(SharedPref.PASS);
                startActivity(new Intent(LoginActivity.this,RegActivity.class));
             }
         });
    }

    private void dangnhap() {
        btndangnhp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog = new ProgressDialog(LoginActivity.this);
                progressDialog.setMessage("đang login chờ tý...");
                progressDialog.show();
                String tk=edttk.getText().toString();
                String mk=edtmk.getText().toString();
                loginViewModel.loginHandle(tk,mk).observe(LoginActivity.this, new Observer<ResponseUser1s>() {
                    @Override
                    public void onChanged(ResponseUser1s responseUser1s) {
                        if(responseUser1s.getMess().equals("SUCCESS")){
                            ArrayList<user1s> user= (ArrayList<user1s>) responseUser1s.getData();
                            if(user.get(0).getAccount()!=null){
                                RegNotifi(user.get(0).getAccount());
                                EventBus.getDefault().postSticky(user);
                                SharedPref.write(SharedPref.IDUSER,user.get(0).getId());
                                SharedPref.write(SharedPref.USER,""+user.get(0).getAccount());//save string in shared preference.
                                SharedPref.write(SharedPref.PASS,""+user.get(0).getPassword());//save int in shared preference.
                                SharedPref.write(SharedPref.LOGIN,true);
                                startActivity(new Intent(LoginActivity.this,MainActivity.class));
                                Animatoo.animateZoom(LoginActivity.this);
                                progressDialog.dismiss();
                            }
                        }else{
                            Toast.makeText(LoginActivity.this, "tài khoản hoặc mật khẩu không chính xác ", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }

                    }
                });
            }
        });
    }


    private void initView() {
        SharedPref.init(getApplicationContext());
        loginViewModel = ViewModelProviders.of(this).get(LoginViewModel.class);
            btndangnhp=findViewById(R.id.btndangnhap);
            btndangky=findViewById(R.id.btndangky);
            edttk=findViewById(R.id.edttklg);
            edtmk=findViewById(R.id.edtmklg);
    }

    private void RegNotifi(String topic) {
        FirebaseMessaging.getInstance().subscribeToTopic(topic) // ở trên firebase sẽ gửi theo topic hihi
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        String msg = "sub ok";
                        if (!task.isSuccessful()) {
                            msg = "sub phêu";
                        }
                        Log.d("TAG", msg);
                        // Toast.makeText(Main2Activity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });



    }
}
