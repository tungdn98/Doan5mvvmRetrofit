package com.example.giaysnaker6789.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.example.giaysnaker6789.R;
import com.example.giaysnaker6789.config.SharedPref;
import com.example.giaysnaker6789.models.user1s;
import com.example.giaysnaker6789.viewModels.LoginViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import static java.lang.Thread.sleep;

public class SplashActivity extends BaseActivity {
    LoginViewModel loginViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        init();
        checkAccount();
        final Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                SharedPreferences getpre = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
                boolean isfirtstart = getpre.getBoolean("firststart", true);
                if (isfirtstart) {
                    // RequirePermistion(); // xin quyeefn
                    startActivity(new Intent(SplashActivity.this, myintro.class));
                    SharedPreferences.Editor e = getpre.edit();
                    e.putBoolean("firststart", false);
                    e.apply();
                } else {
                    try {
                        sleep(5000); // cài đặt thời gian ngủ là 3 giây
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    } finally {

                        if (SharedPref.read(SharedPref.LOGIN, false)) { // kiểm tra đã login chưa
                            startActivity(new Intent(SplashActivity.this, MainActivity.class));
                            finish();
                        } else {
                            startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                            finish();
                        }
                    }

                }
            }
        });
        thread.start();
    }

    private void init() {
        SharedPref.init(getApplicationContext());
        loginViewModel = ViewModelProviders.of(this).get(LoginViewModel.class);
    }

    private void checkAccount(){
        String tk=SharedPref.read(SharedPref.USER,"");
        String mk=SharedPref.read(SharedPref.PASS,"");
        loginViewModel.loginHandle(tk,mk).observe(SplashActivity.this, new Observer<user1s>() {
            @Override
            public void onChanged(user1s user1s) {
                if(user1s.getAccount()!=null){
                    EventBus.getDefault().postSticky(user1s);
                    SharedPref.write(SharedPref.USER,""+user1s.getAccount());//save string in shared preference.
                    SharedPref.write(SharedPref.PASS,""+user1s.getPassword());//save int in shared preference.
                    SharedPref.write(SharedPref.LOGIN,true);
                   // startActivity(new Intent(SplashActivity.this,MainActivity.class));
                }else{
                    SharedPref.write(SharedPref.LOGIN,false);
                   // startActivity(new Intent(SplashActivity.this,LoginActivity.class));
                }
            }
        });
    }

    private void RequirePermistion() {
// check quyền trong runtime
        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {
                            // do you work now
                            Toast.makeText(SplashActivity.this, "hưm", Toast.LENGTH_SHORT).show();
                        }
                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            // permission is denied permenantly, navigate user to app settings

                        }
                    }
                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                })
                .onSameThread()
                .check();

    }

    private void registerMess(){
        FirebaseMessaging.getInstance().subscribeToTopic("weather") // ở trên firebase sẽ gửi theo topic hihi
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        String msg = "sub ok";
                        if (!task.isSuccessful()) {
                            msg = "sub phêu";
                        }
                        Log.d("TAG", msg);
                        Toast.makeText(SplashActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });

    }
}
