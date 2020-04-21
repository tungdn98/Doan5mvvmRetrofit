package com.example.giaysnaker6789.views;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.widget.Toast;

import com.example.giaysnaker6789.config.Progressdialog;
import com.example.giaysnaker6789.config.SharedPref;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.giaysnaker6789.service.CheckConnectService;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class BaseActivity extends AppCompatActivity {
     int count = 0;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
    }

    @Override
    protected void onStart() {
        startService(new Intent(getBaseContext(), CheckConnectService.class));
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        super.onStart();
    }

    @Override
    protected void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(String init){

    }



}
