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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;


import com.example.giaysnaker6789.BaseResponse.ProductBaseResponse;
import com.example.giaysnaker6789.R;
import com.example.giaysnaker6789.TestActivity;
import com.example.giaysnaker6789.adapter.SpTrangchuAdapter;
import com.example.giaysnaker6789.models.banners;
import com.example.giaysnaker6789.models.products;
import com.example.giaysnaker6789.models.test;
import com.example.giaysnaker6789.network.DataClient;
import com.example.giaysnaker6789.network.RetrofitService;
import com.example.giaysnaker6789.viewModels.BannerViewModel;
import com.example.giaysnaker6789.viewModels.ProductViewModel;
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

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends BaseActivity {
    DrawerLayout mDrawerLayout;
    NavigationView navigationView;
    SearchView searchView;
    TextView txtbadge, txttitle;
    ImageView imgMenu;
    ImageSlider imgslider;
    Button btnloadmore;
    int page = 1;

    // testViewmodel newsViewModel=ViewModelProviders.of(this).get(testViewmodel.class);
    BannerViewModel bannerViewModel;
    ProductViewModel productViewModel;
    private static final String TAG = "tungtung";

    ProgressDialog progressDialog;

    RecyclerView recyclerView;
    SpTrangchuAdapter recyclerViewAdapter;
    StaggeredGridLayoutManager layoutManager;
    ArrayList<products> rowsArrayList = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bannerViewModel = ViewModelProviders.of(this).get(BannerViewModel.class);
        productViewModel = ViewModelProviders.of(this).get(ProductViewModel.class);

        initView();
        setCountButton();
        setupBanner();
        setupListSp();
        btnloadmore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,LoadMoreActivity.class));
            }
        });

    }

    private void setCountButton() {
        productViewModel.getCount().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                btnloadmore.setText("xem thêm "+s+" sản phẩm");
            }
        });
    }


    private void setupListSp() {
        recyclerView.setHasFixedSize(true);
        layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        // Khai báo Adapter (mn xem tiếp ví dụ dưới nhé)
        productViewModel.LoadProduct(1).observe(this, new Observer<ProductBaseResponse>() {
            @Override
            public void onChanged(ProductBaseResponse productBaseResponse) {
                rowsArrayList = (ArrayList<products>) productBaseResponse.getData();
                recyclerViewAdapter = new SpTrangchuAdapter(rowsArrayList, MainActivity.this);
                recyclerView.setAdapter(recyclerViewAdapter);
            }
        });

    }


    private void setupBanner() {
        bannerViewModel.getBanners().observe(this, new Observer<List<banners>>() {
            @Override
            public void onChanged(List<banners> banners) {
                List<SlideModel> imageList = new ArrayList<>();
                if (banners != null) {
                    for (int i = 0; i < banners.size(); i++) {
                        imageList.add(new SlideModel(RetrofitService.basePath + banners.get(i).getImage(), banners.get(i).getIdProduct()));
                    }
                    imgslider.setImageList(imageList, false);

                    imgslider.setItemClickListener(new ItemClickListener() {
                        @Override
                        public void onItemSelected(int position) {
                            Toast.makeText(MainActivity.this, "" + imageList.get(position).getImageUrl(), Toast.LENGTH_SHORT).show();
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
        txttitle = findViewById(R.id.txttile);
        searchView = findViewById(R.id.searchView);
        imgslider = findViewById(R.id.image_slider);
        recyclerView = findViewById(R.id.rcmain);
        btnloadmore=findViewById(R.id.btnloadmore);

        navigationView.getMenu().getItem(0).setChecked(true);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                menuItem.setChecked(true);
                mDrawerLayout.closeDrawers();
                switch (menuItem.getItemId()) {
                    case R.id.nav_home:
                        EventBus.getDefault().postSticky(new test(1, "tungtung", "núi"));
                        Toast.makeText(MainActivity.this, "test nè", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_user:
                        EventBus.getDefault().postSticky(new test(1, "tungtung2", "núi1"));
                        Toast.makeText(MainActivity.this, "user", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(MainActivity.this, LoginActivity.class));
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
        } else {
            txttitle.setText("nun");
        }
    }

}
