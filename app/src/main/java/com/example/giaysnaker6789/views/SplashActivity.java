package com.example.giaysnaker6789.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.example.giaysnaker6789.BaseResponse.ResponseUser1s;
import com.example.giaysnaker6789.R;
import com.example.giaysnaker6789.config.Constant;
import com.example.giaysnaker6789.config.SharedPref;
import com.example.giaysnaker6789.models.user1s;
import com.example.giaysnaker6789.viewModels.LoginViewModel;
import com.example.giaysnaker6789.viewModels.RegisterViewmodel;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.lang.Thread.sleep;

public class SplashActivity extends BaseActivity {
    LoginViewModel loginViewModel;

    LottieAnimationView lottieAnimationView;

    private CallbackManager callbackManager;
    private FacebookCallback<LoginResult> loginResult;
    RegisterViewmodel registerViewmodel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        RegNotifi("all");
        init();
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        initFaceBook();
        LoginManager.getInstance().registerCallback(callbackManager, loginResult);

        checkAccount();
        final Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                SharedPreferences getpre = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
                boolean isfirtstart = getpre.getBoolean("firststart", true);
                if (isfirtstart) {
                    // RequirePermistion(); // xin quyeefn
                    startActivity(new Intent(SplashActivity.this, myintro.class));
                    Animatoo.animateFade(SplashActivity.this);
                    SharedPreferences.Editor e = getpre.edit();
                    e.putBoolean("firststart", false);
                    e.apply();
                } else {
                    try {
                        sleep(5000); // cài đặt thời gian ngủ là 3 giây
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    } finally {
                        boolean check = SharedPref.read(SharedPref.LOGIN, false);
                        if (check == true) { // kiểm tra đã login chưa
                            startActivity(new Intent(SplashActivity.this, MainActivity.class));
                            Animatoo.animateZoom(SplashActivity.this);
                            finish();
                        } else {
                            startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                            Animatoo.animateFade(SplashActivity.this);
                            finish();
                        }
                    }

                }
            }
        });
        thread.start();
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

    private void init() {
        SharedPref.init(SplashActivity.this);
        loginViewModel = ViewModelProviders.of(this).get(LoginViewModel.class);
        registerViewmodel = ViewModelProviders.of(this).get(RegisterViewmodel.class);

    }

    private void checkAccount() {
        String tk = SharedPref.read(SharedPref.USER, "");
        String mk = SharedPref.read(SharedPref.PASS, "");
        if (tk != "" && mk != "") {
            loginViewModel.loginHandle(tk, mk).observe(SplashActivity.this, new Observer<ResponseUser1s>() {
                @Override
                public void onChanged(ResponseUser1s responseUser1s) {
                    if (responseUser1s != null) {
                        if (responseUser1s.getMess().equals("SUCCESS")) {
                            user1s user = responseUser1s.getData();
                            if (user.getAccount() != null) {
                                EventBus.getDefault().postSticky(user);
                                SharedPref.write(SharedPref.IDUSER, user.getId());
                                SharedPref.write(SharedPref.LOGIN, true);
                                Constant.user1s=user;
                                RegNotifi(user.getAccount());
                            } else {
                                SharedPref.write(SharedPref.LOGIN, false);
                                Constant.user1s=null;
                            }
                        }
                    }
                }
            });
        }else{
            boolean che=isLoggedInFaceBook();
            if(che){
                SharedPref.write(SharedPref.LOGIN, true);
            }else{
                SharedPref.write(SharedPref.LOGIN, false);
            }
        }


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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    //Login facebook with permisstion
    public void loginFaceBook() {
        LoginManager.getInstance().logInWithReadPermissions(SplashActivity.this, Arrays.asList("public_profile", "email", "user_link"));
    }

    //Hàm check login facebook
    public boolean isLoggedInFaceBook() {
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        return accessToken != null;
    }

    //Lấy Avatar
    public URL extractFacebookIcon(String id) {
        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);

            URL imageURL = new URL("http://graph.facebook.com/" + id
                    + "/picture?type=large");
            return imageURL;
        } catch (Throwable e) {
            return null;
        }
    }

    String name;
    String id;
    String email;
    String link;
    String imageURL;
    int count = 1;

    public void initFaceBook() {
        loginResult = new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                //Login thành công xử lý tại đây
                GraphRequest request = GraphRequest.newMeRequest(
                        AccessToken.getCurrentAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object,
                                                    GraphResponse response) {
                                // Application code
                                name = object.optString(getString(R.string.name));
                                id = object.optString(getString(R.string.id));
                                email = object.optString(getString(R.string.email));
                                link = object.optString(getString(R.string.link));
                                imageURL = extractFacebookIcon(id).toString();
                                // call api login with facebook
                                registerViewmodel.RegisterFacebook(id, name, imageURL.toString(), "", "").observe(SplashActivity.this, new Observer<ResponseUser1s>() {
                                    @Override
                                    public void onChanged(ResponseUser1s responseUser1s) {
                                        user1s user = responseUser1s.getData();
                                        if (user.getIdfb() != null) {
                                            RegNotifi(user.getIdfb());
                                            EventBus.getDefault().postSticky(user);
                                            SharedPref.write(SharedPref.IDUSER, user.getId());
                                            // SharedPref.write(SharedPref.USER, "" + user.getAccount());//save string in shared preference.
                                            // SharedPref.write(SharedPref.PASS, "" + user.getPassword());//save int in shared preference.
                                            SharedPref.write(SharedPref.LOGIN, true);
                                            Animatoo.animateZoom(SplashActivity.this);
                                        }
                                    }
                                });
                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString(getString(R.string.fields), getString(R.string.fields_name));
                request.setParameters(parameters);
                request.executeAsync();

            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        };

    }

}
