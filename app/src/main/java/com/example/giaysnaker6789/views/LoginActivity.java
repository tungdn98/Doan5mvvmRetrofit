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
import com.example.giaysnaker6789.models.test;
import com.example.giaysnaker6789.viewModels.LoginViewModel;
import com.example.giaysnaker6789.viewModels.testViewmodel;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

public class LoginActivity extends BaseActivity {


    LoginViewModel loginViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();

    }




    private void initView() {
        loginViewModel = ViewModelProviders.of(this).get(LoginViewModel.class);

    }
}
