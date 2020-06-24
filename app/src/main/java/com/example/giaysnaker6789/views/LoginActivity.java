package com.example.giaysnaker6789.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Base64;
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
import com.example.giaysnaker6789.network.RetrofitService;
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
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.messaging.FirebaseMessaging;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONObject;

import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class LoginActivity extends BaseActivity {


    LoginViewModel loginViewModel;
    RegisterViewmodel registerViewmodel;
    Button btndangky, btndangnhp;
    TextInputEditText edttk, edtmk;
    ProgressDialog progressDialog;


    private CallbackManager callbackManager;
    private FacebookCallback<LoginResult> loginResult;
    LoginButton loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        laykeyhash();
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
                startActivity(new Intent(LoginActivity.this, RegActivity.class));
            }
        });

        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        initFaceBook();
        LoginManager.getInstance().registerCallback(callbackManager, loginResult);
        loginButton = findViewById(R.id.login_button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginFaceBook();
            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    //Login facebook with permisstion
    public void loginFaceBook() {
        LoginManager.getInstance().logInWithReadPermissions(LoginActivity.this, Arrays.asList("public_profile", "email", "user_link"));
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
                                    registerViewmodel.RegisterFacebook(id, name, imageURL.toString(), "", "").observe(LoginActivity.this, new Observer<ResponseUser1s>() {
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
                                                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                                Animatoo.animateZoom(LoginActivity.this);

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


    private void dangnhap() {
        btndangnhp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog = new ProgressDialog(LoginActivity.this);
                progressDialog.setMessage("đang login chờ tý...");
                progressDialog.show();
                String tk = edttk.getText().toString();
                String mk = edtmk.getText().toString();
                loginViewModel.loginHandle(tk, mk).observe(LoginActivity.this, new Observer<ResponseUser1s>() {
                    @Override
                    public void onChanged(ResponseUser1s responseUser1s) {
                        if (responseUser1s.getMess().equals("SUCCESS")) {
                            user1s user = responseUser1s.getData();
                            if (user.getAccount() != null) {
                                RegNotifi(user.getAccount());
                                EventBus.getDefault().postSticky(user);
                                SharedPref.write(SharedPref.IDUSER, user.getId());
                                SharedPref.write(SharedPref.USER, "" + user.getAccount());//save string in shared preference.
                                SharedPref.write(SharedPref.PASS, "" + user.getPassword());//save int in shared preference.
                                SharedPref.write(SharedPref.LOGIN, true);
                                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                Animatoo.animateZoom(LoginActivity.this);
                                progressDialog.dismiss();
                            }
                        } else {
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
        registerViewmodel = ViewModelProviders.of(this).get(RegisterViewmodel.class);
        btndangnhp = findViewById(R.id.btndangnhap);
        btndangky = findViewById(R.id.btndangky);
        edttk = findViewById(R.id.edttklg);
        edtmk = findViewById(R.id.edtmklg);
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

    private void laykeyhash() {
        try {
            PackageInfo info = null;
            try {
                info = getPackageManager().getPackageInfo(
                        "com.example.giaysnaker6789",
                        PackageManager.GET_SIGNATURES);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (NoSuchAlgorithmException e) {

        }
    }

}
