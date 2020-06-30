package com.example.giaysnaker6789.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.giaysnaker6789.BaseResponse.ResponseUser1s;
import com.example.giaysnaker6789.R;
import com.example.giaysnaker6789.databinding.ActivityUserBinding;
import com.example.giaysnaker6789.models.user1s;
import com.example.giaysnaker6789.network.RetrofitService;
import com.example.giaysnaker6789.viewModels.LoginViewModel;
import com.example.giaysnaker6789.viewModels.RegisterViewmodel;
import com.facebook.login.Login;
import com.google.android.material.textfield.TextInputEditText;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import de.hdodenhof.circleimageview.CircleImageView;


public class UserActivity extends BaseActivity {
    ActivityUserBinding binding;
    LoginViewModel loginViewModel;
    user1s us;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityUserBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        loginViewModel = ViewModelProviders.of(this).get(LoginViewModel.class);
        binding.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int idRd=binding.rdSex.getCheckedRadioButtonId();
                RadioButton rd=findViewById(idRd);
                us.setSex(""+rd.getText());
                us.setName(""+binding.edtName.getText());
                us.setEmail(""+binding.edtEmail.getText());
                us.setAddress(""+binding.edtAddress.getText());
                us.setPhone(""+binding.edtPhone.getText());
                loginViewModel.updateData(us).observe(UserActivity.this, new Observer<ResponseUser1s>() {
                    @Override
                    public void onChanged(ResponseUser1s responseUser1s) {
                        EventBus.getDefault().postSticky(us);
                        Toast.makeText(UserActivity.this, "update thành công", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onMessageEvent(user1s event) { // get model test
        us=event;
        binding.edtName.setText("" + event.getName());
        Picasso.get()
                .load("" + RetrofitService.basePath + event.getImagefb())
                .resize(100, 100)
                // .centerCrop()
                .into(binding.imgUser);
        binding.edtEmail.setText(""+event.getEmail());
        binding.edtPhone.setText(""+event.getPhone());
        binding.edtAddress.setText(""+event.getAddress());
        if(event.getSex().equals("Nam ")){
            binding.rdMale.setChecked(true);
        }else{
            binding.rdFemale.setChecked(true);
        }
    }

}