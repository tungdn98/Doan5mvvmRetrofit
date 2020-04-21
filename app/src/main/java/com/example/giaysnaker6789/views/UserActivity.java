package com.example.giaysnaker6789.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.giaysnaker6789.BaseResponse.Billresponse;
import com.example.giaysnaker6789.R;
import com.example.giaysnaker6789.models.bills;
import com.example.giaysnaker6789.viewModels.BillViewModel;

import java.util.List;

public class UserActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


    }


}
