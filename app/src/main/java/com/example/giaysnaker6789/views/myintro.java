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
        addSlide(AppIntroFragment.newInstance("man1", // dòng trên
                "man11", // dòng dưới
                R.drawable.com_facebook_profile_picture_blank_portrait, // hình ảnh ở giữa
                Color.parseColor("#51e2b7"))); // màu nền

        addSlide(AppIntroFragment.newInstance("man2",
                "man22",
                R.drawable.com_facebook_profile_picture_blank_portrait,
                Color.parseColor("#51e2b7")));

        addSlide(AppIntroFragment.newInstance("man3",
                "man33",
                R.drawable.com_facebook_profile_picture_blank_portrait,
                Color.parseColor("#51e2b7")));


        addSlide(AppIntroFragment.newInstance("man4",
                "man44",
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
        Toast.makeText(this, "skip click", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSlideChanged() { // khi vuốt slide
        Toast.makeText(this, "change slide ", Toast.LENGTH_SHORT).show();
    }
}
