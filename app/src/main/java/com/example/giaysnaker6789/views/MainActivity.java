package com.example.giaysnaker6789.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;


import com.example.giaysnaker6789.R;
import com.example.giaysnaker6789.TestActivity;
import com.example.giaysnaker6789.models.banners;
import com.example.giaysnaker6789.models.test;
import com.example.giaysnaker6789.network.RetrofitService;
import com.example.giaysnaker6789.viewModels.BannerViewModel;
import com.example.giaysnaker6789.viewModels.testViewmodel;
import com.example.tungnuislider.ImageSlider;
import com.example.tungnuislider.interfaces.ItemClickListener;
import com.example.tungnuislider.models.SlideModel;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jetbrains.annotations.NotNull;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;



public class MainActivity extends BaseActivity {
    DrawerLayout mDrawerLayout;
    NavigationView navigationView;
    SearchView searchView;
    TextView txtbadge,txttitle;
    ImageView imgMenu;
    ImageSlider imgslider;
    int i = 0;

   // testViewmodel newsViewModel=ViewModelProviders.of(this).get(testViewmodel.class);
    BannerViewModel bannerViewModel;
    private static final String TAG = "tungtung";

    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bannerViewModel=ViewModelProviders.of(this).get(BannerViewModel.class);
        initView();
       setupBanner();

    }



    private void setupBanner() {
        bannerViewModel.getBanners().observe(this, new Observer<List<banners>>() {
            @Override
            public void onChanged(List<banners> banners) {
                List<SlideModel> imageList = new ArrayList<>();
                if(banners!=null){
                    for (int i=0;i<banners.size();i++){
                        Log.d(TAG, "onChanged: "+RetrofitService.basePath+banners.get(i).getImage());
                        imageList.add(new SlideModel(RetrofitService.basePath+banners.get(i).getImage(),banners.get(i).getIdProduct() ));
                    }
                    imgslider.setImageList(imageList,false);

                    imgslider.setItemClickListener(new ItemClickListener() {
                        @Override
                        public void onItemSelected(int position) {
                            Toast.makeText(MainActivity.this, ""+imageList.get(position).getImageUrl(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }
        });
    }

    private void initView() {
        imgMenu = findViewById(R.id.imgMenu);
        mDrawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        txtbadge = findViewById(R.id.text);
        txttitle=findViewById(R.id.txttile);
        searchView = findViewById(R.id.searchView);
        imgslider=findViewById(R.id.image_slider);
        navigationView.getMenu().getItem(0).setChecked(true);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                menuItem.setChecked(true);
                mDrawerLayout.closeDrawers();
                switch (menuItem.getItemId()) {
                    case R.id.nav_home:
                        EventBus.getDefault().postSticky(new test(1,"tungtung","núi"));
                        Toast.makeText(MainActivity.this, "test nè", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_user:
                        EventBus.getDefault().postSticky(new test(1,"tungtung2","núi1"));
                        Toast.makeText(MainActivity.this, "user", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(MainActivity.this,LoginActivity.class));
                        break;
                    case R.id.nav_cart:
                        startActivity(new Intent(MainActivity.this, TestActivity.class));
                        Toast.makeText(MainActivity.this, "cart", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_cateroly:
                        Toast.makeText(MainActivity.this, "cateroly", Toast.LENGTH_SHORT).show();
                        break;
                }

                return true;
            }
        });
        imgMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onMessageEvent(test event) { // get model test
        if (event != null) {
            txttitle.setText(event.getUser());
           // getListPersonData(event.getUnitSelected().getId()); // gọi hàm xử lý với dữ liệu
        }else{
            txttitle.setText("nun");
        }
    }

}
