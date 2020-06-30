package com.example.giaysnaker6789.views;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.giaysnaker6789.R;
import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntroFragment;

public class myintro extends AppIntro {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addSlide(AppIntroFragment.newInstance("chất lượng sản phẩm tuyệt vời", // dòng trên
                "giá cả phù hợp", // dòng dưới
                R.drawable.com_facebook_profile_picture_blank_portrait, // hình ảnh ở giữa
                Color.parseColor("#51e2b7"))); // màu nền

        addSlide(AppIntroFragment.newInstance("đi đầu trong nghề bán giày lậu",
                "giày lậu muôn năm",
                R.drawable.com_facebook_profile_picture_blank_portrait,
                Color.parseColor("#51e2b7")));

        addSlide(AppIntroFragment.newInstance("",
                "",
                R.drawable.com_facebook_profile_picture_blank_portrait,
                Color.parseColor("#51e2b7")));


        addSlide(AppIntroFragment.newInstance("",
                "",
                R.drawable.com_facebook_profile_picture_blank_portrait,
                Color.parseColor("#51e2b7")));

        showStatusBar(false);
        setBarColor(Color.parseColor("#333639"));
        setSeparatorColor(Color.parseColor("#2196F3"));
    }

    @Override
    public void onDonePressed() { // nhấn nút done
        startActivity(new Intent(this, SplashActivity.class));
        finish();
    }

    @Override
    public void onSkipPressed(Fragment currentFragment) { // nhấn nút skip

    }

    @Override
    public void onSlideChanged() { // khi vuốt slide
    }
}
