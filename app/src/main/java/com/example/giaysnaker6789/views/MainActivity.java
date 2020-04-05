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
import com.example.giaysnaker6789.ItemClickSupport;
import com.example.giaysnaker6789.R;
import com.example.giaysnaker6789.TestActivity;
import com.example.giaysnaker6789.adapter.ProductTypeAdapter;
import com.example.giaysnaker6789.adapter.SpTrangchuAdapter;
import com.example.giaysnaker6789.models.banners;
import com.example.giaysnaker6789.models.product_types;
import com.example.giaysnaker6789.models.products;
import com.example.giaysnaker6789.models.test;
import com.example.giaysnaker6789.models.user1s;
import com.example.giaysnaker6789.network.DataClient;
import com.example.giaysnaker6789.network.RetrofitService;
import com.example.giaysnaker6789.viewModels.BannerViewModel;
import com.example.giaysnaker6789.viewModels.ProductTypeViewModel;
import com.example.giaysnaker6789.viewModels.ProductViewModel;
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
    View headerview;
    TextView txtname;

    ImageSlider imgslider;
    Button btnloadmore;
    int page = 1;

    // testViewmodel newsViewModel=ViewModelProviders.of(this).get(testViewmodel.class);
    BannerViewModel bannerViewModel;
    ProductViewModel productViewModel;
    ProductTypeViewModel productTypeViewModel;
    private static final String TAG = "tungtung";

    ProgressDialog progressDialog;

    RecyclerView recyclerView;
    SpTrangchuAdapter recyclerViewAdapter;
    StaggeredGridLayoutManager layoutManager;

    RecyclerView recyclerView2;
    ProductTypeAdapter productTypeAdapter;
    LinearLayoutManager layoutManager2;
    ArrayList<products> rowsArrayList = new ArrayList<>();
    ArrayList<product_types> listproducttype = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bannerViewModel = ViewModelProviders.of(this).get(BannerViewModel.class);
        productViewModel = ViewModelProviders.of(this).get(ProductViewModel.class);
        productTypeViewModel=ViewModelProviders.of(this).get(ProductTypeViewModel.class);
        initView();
        setCountButton();
        setupBanner();
        setupListSp();
        setuplistLoaiSp();
        btnloadmore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,LoadMoreActivity.class));
            }
        });

    }

    private void setuplistLoaiSp() {
        recyclerView2.setHasFixedSize(true);
        layoutManager2 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView2.setLayoutManager(layoutManager2);
        productTypeViewModel.LoadProductType().observe(this, new Observer<List<product_types>>() {
            @Override
            public void onChanged(List<product_types> product_types) {
                listproducttype.addAll(product_types);
                productTypeAdapter = new ProductTypeAdapter(listproducttype, MainActivity.this);
                recyclerView2.setAdapter(productTypeAdapter);
            }
        });

        ItemClickSupport.addTo(recyclerView2).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                product_types pro=listproducttype.get(position);
                EventBus.getDefault().postSticky(pro);
                startActivity(new Intent(MainActivity.this,ProductDetailActivity.class));
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
        layoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
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
        ItemClickSupport.addTo(recyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                products pro=rowsArrayList.get(position);
                EventBus.getDefault().postSticky(pro);
                startActivity(new Intent(MainActivity.this,ProductDetailActivity.class));
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
                            EventBus.getDefault().postSticky(banners.get(position));
                            startActivity(new Intent(MainActivity.this,ProductDetailActivity.class));
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
        headerview=navigationView.getHeaderView(0);
         txtname=headerview.findViewById(R.id.txtname);
        imgslider = findViewById(R.id.image_slider);
        recyclerView = findViewById(R.id.rcmain);
        recyclerView2=findViewById(R.id.rclistloaisp);
        btnloadmore=findViewById(R.id.btnloadmore);

        navigationView.getMenu().getItem(0).setChecked(true);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                menuItem.setChecked(true);
                mDrawerLayout.closeDrawers();
                switch (menuItem.getItemId()) {
                    case R.id.nav_home:
                       // EventBus.getDefault().postSticky(new test(1, "tungtung", "núi"));
                        Toast.makeText(MainActivity.this, "test nè", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_user:

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
    public void onMessageEvent(user1s event) { // get model test
        txtname.setText(""+event.getName());
    }

}
