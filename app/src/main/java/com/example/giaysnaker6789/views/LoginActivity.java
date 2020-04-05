package com.example.giaysnaker6789.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.giaysnaker6789.R;
import com.example.giaysnaker6789.config.SharedPref;
import com.example.giaysnaker6789.models.test;
import com.example.giaysnaker6789.models.user1s;
import com.example.giaysnaker6789.viewModels.LoginViewModel;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.greenrobot.eventbus.EventBus;

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
        edttk.setText(""+us);
        edtmk.setText(""+pass);
         dangnhap();
         btndangky.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 SharedPref.remove(SharedPref.USER);
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
                loginViewModel.loginHandle(tk,mk).observe(LoginActivity.this, new Observer<user1s>() {
                    @Override
                    public void onChanged(user1s user1s) {
                        if(user1s.getAccount()!=null){
                            EventBus.getDefault().postSticky(user1s);
                            SharedPref.write(SharedPref.USER,""+user1s.getAccount());//save string in shared preference.
                            SharedPref.write(SharedPref.PASS,""+user1s.getPassword());//save int in shared preference.
                            startActivity(new Intent(LoginActivity.this,MainActivity.class));
                            progressDialog.dismiss();
                        }else{
                            Toast.makeText(LoginActivity.this, "tai khoarn matj khau", Toast.LENGTH_SHORT).show();
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
            edttk=findViewById(R.id.edttk);
            edtmk=findViewById(R.id.edtmk);
    }
}
