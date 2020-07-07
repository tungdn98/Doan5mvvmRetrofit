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
import android.widget.Toast;

import com.example.giaysnaker6789.BaseResponse.ResponseUser1s;
import com.example.giaysnaker6789.config.Constant;
import com.example.giaysnaker6789.databinding.ActivityVerifyPhoneBinding;
import com.example.giaysnaker6789.models.user1s;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.OnCompleteListener;
import com.example.giaysnaker6789.viewModels.RegisterViewmodel;

import java.io.File;
import java.util.ConcurrentModificationException;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class VerifyPhoneActivity extends AppCompatActivity {
    ActivityVerifyPhoneBinding binding;

    private FirebaseAuth mAuth;
    private String verificationId;
    RegisterViewmodel registerViewmodel;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityVerifyPhoneBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        registerViewmodel = ViewModelProviders.of(this).get(RegisterViewmodel.class);
        mAuth = FirebaseAuth.getInstance();
        sendVerificationCode(Constant.user1s.getPhone());
        binding.buttonSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code = binding.editTextCode.getText().toString().trim();
                if (code.isEmpty() || code.length() < 6) {
                    binding.editTextCode.setError("Enter code...");
                    binding.editTextCode.requestFocus();
                    return;
                }
                verifyCode(code);
            }
        });
    }

    private void verifyCode(String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        signInWithCredential(credential);
    }

    private void signInWithCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            if(Constant.user1s.getImagefb()!=null){
                                UploadImage();

                            }else{
                                dangkine("");
                            }
                        } else {
                            Toast.makeText(VerifyPhoneActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            Log.d("huhuhu", "onComplete: " + task.getException().getMessage());
                        }
                    }
                });
    }

    public void UploadImage(){
        File file = new File(Constant.user1s.getImagefb());
        String file_path = file.getAbsolutePath();
        String[] mangtenfile = file_path.split("\\.");
        file_path = mangtenfile[0] + System.currentTimeMillis() + "." + mangtenfile[1];
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("image", file_path, requestBody); //image laf field treen sever sẽ đọc
        registerViewmodel.UploadImage(body).observe(VerifyPhoneActivity.this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if (s != "") {
                    dangkine(s);
                } else {
                    Toast.makeText(VerifyPhoneActivity.this, "có lỗi xảy ra trong quá trình up ảnh", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void dangkine(String path) {

        progressDialog = new ProgressDialog(VerifyPhoneActivity.this);
        progressDialog.setMessage("đang đăng ký chờ tý...");
        progressDialog.show();

        registerViewmodel.RegisterNomal(Constant.user1s.getAccount(),
                Constant.user1s.getPassword(),
                Constant.user1s.getEmail(),
                Constant.user1s.getAddress(),
                Constant.user1s.getPhone(),
                Constant.user1s.getName(),
                path).observe(this, new Observer<ResponseUser1s>() {
            @Override
            public void onChanged(ResponseUser1s responseUser1s) {
                if(responseUser1s.getMess().equals("SUCCESS")){
                    user1s user=  responseUser1s.getData();
                    Toast.makeText(VerifyPhoneActivity.this, "đăng kí thành công", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                    Intent intent = new Intent(VerifyPhoneActivity.this, LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
                if(responseUser1s.getMess().equals("EXISTMAIL")){
                    Toast.makeText(VerifyPhoneActivity.this, "Email đã có người sử dụng vui lòng chọn tài khoản khác", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
                if(responseUser1s.getMess().equals("EXISTACC")){
                    Toast.makeText(VerifyPhoneActivity.this, "Tài khoản đã có người sử dụng vui lòng chọn tài khoản khác", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();

                }

            }
        });
    }

    private void sendVerificationCode(String number) {
        binding.progressbar.setVisibility(View.VISIBLE);
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                number,
                60,
                TimeUnit.SECONDS,
                TaskExecutors.MAIN_THREAD,
                mCallBack
        );
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks
            mCallBack = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            verificationId = s;
            Toast.makeText(VerifyPhoneActivity.this, "đã gửi code", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            String code = phoneAuthCredential.getSmsCode();
            if (code != null) {
                binding.editTextCode.setText(code);
                verifyCode(code);
            }
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            Toast.makeText(VerifyPhoneActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
            Log.d("huhuhu", "onVerificationFailed: " + e.getMessage());
        }
    };
}