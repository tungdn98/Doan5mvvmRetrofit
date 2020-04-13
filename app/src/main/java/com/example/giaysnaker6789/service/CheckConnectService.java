package com.example.giaysnaker6789.service;

import android.app.Dialog;
import android.app.NotificationManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import com.example.giaysnaker6789.R;
import com.example.giaysnaker6789.config.ConnectionHelper;
import com.example.giaysnaker6789.views.MainActivity;

public class CheckConnectService extends Service {
    public CheckConnectService() {
    }

    static final String CONNECTIVITY_CHANGE_ACTION = "android.net.conn.CONNECTIVITY_CHANGE";
    NotificationManager manager ;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Let it continue running until it is stopped.
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        BroadcastReceiver receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                if (CONNECTIVITY_CHANGE_ACTION.equals(action)) {
                    //check internet connection
                    if (!ConnectionHelper.isConnectedOrConnecting(context)) {
                        if (context != null) {
                            boolean show = false;
                            if (ConnectionHelper.lastNoConnectionTs == -1) {//first time
                                show = true;
                                ConnectionHelper.lastNoConnectionTs = System.currentTimeMillis();
                            } else {
                                if (System.currentTimeMillis() - ConnectionHelper.lastNoConnectionTs > 1000) {
                                    show = true;
                                    ConnectionHelper.lastNoConnectionTs = System.currentTimeMillis();
                                }
                            }

                            if (show && ConnectionHelper.isOnline) {
                                ConnectionHelper.isOnline = false;
                                Log.i("NETWORK123","Connection lost");
                                //manager.cancelAll();
                                Toast.makeText(context, "bật mạng  để truy cập thông tin ", Toast.LENGTH_LONG).show();
                               // showAlertDialog();
                            }
                        }
                    } else {
                        Log.i("NETWORK123","Connected");
                        //showNotifications("APP" , "It is working");
                        // Perform your actions here
                        ConnectionHelper.isOnline = true;

                    }
                }
            }
        };
        registerReceiver(receiver,filter);
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "Service Destroyed", Toast.LENGTH_LONG).show();
    }

    AlertDialog alertDialog;
    public void showAlertDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.Theme_AppCompat_Light);
        builder.setTitle("ê cu");
        builder.setMessage("mày đang đéo bật mạng hay gì đấy? bật lên");
        builder.setCancelable(false);
        builder.setPositiveButton("ok để tao bật", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(Intent.ACTION_MAIN, null);
                intent.addCategory(Intent.CATEGORY_LAUNCHER);
                ComponentName cn = new ComponentName("com.android.settings", "com.android.settings.wifi.WifiSettings");
                intent.setComponent(cn);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity( intent);
            }
        });
        builder.setNegativeButton("đéo", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
         alertDialog = builder.create();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            alertDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY - 1);

        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            alertDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        }
        alertDialog.show();
    }


}
