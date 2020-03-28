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

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.interfaces.ItemClickListener;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.giaysnaker6789.R;
import com.example.giaysnaker6789.models.test;
import com.example.giaysnaker6789.viewModels.testViewmodel;
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
    Toolbar toolbar;
    SearchView searchView;
    TextView txtbadge,txttitle;
    ImageView imgMenu;
    SwipeRefreshLayout pullToRefresh;
    int i = 0;

    testViewmodel newsViewModel;
    private static final String TAG = "tungtung";

    ProgressDialog progressDialog;
    ImageSlider imgslider;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        newsViewModel = ViewModelProviders.of(this).get(testViewmodel.class);
        initView();
       setupBanner();
       setupListProduct();


    }

    private void setupListProduct() {
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Toast.makeText(MainActivity.this, "ddax theem", Toast.LENGTH_SHORT).show();
                pullToRefresh.setRefreshing(false);
            }
        });

    }

    private void setupBanner() {
        List<SlideModel> imageList=new ArrayList<>();
        imageList.add(new SlideModel("https://1.bp.blogspot.com/-GUZsgr8my50/XJUWOhyHyaI/AAAAAAAABUo/bljp3LCS3SUtj-judzlntiETt7G294WcgCLcBGAs/s1600/fox.jpg", "Foxes live wild in the city.", true));
        imageList.add(new SlideModel("https://2.bp.blogspot.com/-CyLH9NnPoAo/XJUWK2UHiMI/AAAAAAAABUk/D8XMUIGhDbwEhC29dQb-7gfYb16GysaQgCLcBGAs/s1600/tiger.jpg"));
        imageList.add(new SlideModel("https://3.bp.blogspot.com/-uJtCbNrBzEc/XJUWQPOSrfI/AAAAAAAABUs/ZlReSwpfI3Ack60629Rv0N8hSrPFHb3TACLcBGAs/s1600/elephant.jpg", "The population of elephants is decreasing in the world."));
        imageList.add(new SlideModel("https://1.bp.blogspot.com/-GUZsgr8my50/XJUWOhyHyaI/AAAAAAAABUo/bljp3LCS3SUtj-judzlntiETt7G294WcgCLcBGAs/s1600/fox.jpg", "Foxes live wild in the city.", true));
        imageList.add(new SlideModel("https://2.bp.blogspot.com/-CyLH9NnPoAo/XJUWK2UHiMI/AAAAAAAABUk/D8XMUIGhDbwEhC29dQb-7gfYb16GysaQgCLcBGAs/s1600/tiger.jpg"));
        imageList.add(new SlideModel("https://3.bp.blogspot.com/-uJtCbNrBzEc/XJUWQPOSrfI/AAAAAAAABUs/ZlReSwpfI3Ack60629Rv0N8hSrPFHb3TACLcBGAs/s1600/elephant.jpg", "The population of elephants is decreasing in the world."));
        imageList.add(new SlideModel("https://1.bp.blogspot.com/-GUZsgr8my50/XJUWOhyHyaI/AAAAAAAABUo/bljp3LCS3SUtj-judzlntiETt7G294WcgCLcBGAs/s1600/fox.jpg", "Foxes live wild in the city.", true));
        imageList.add(new SlideModel("https://2.bp.blogspot.com/-CyLH9NnPoAo/XJUWK2UHiMI/AAAAAAAABUk/D8XMUIGhDbwEhC29dQb-7gfYb16GysaQgCLcBGAs/s1600/tiger.jpg"));
        imageList.add(new SlideModel("https://3.bp.blogspot.com/-uJtCbNrBzEc/XJUWQPOSrfI/AAAAAAAABUs/ZlReSwpfI3Ack60629Rv0N8hSrPFHb3TACLcBGAs/s1600/elephant.jpg", "The population of elephants is decreasing in the world."));

        imgslider.setImageList(imageList,false);
        imgslider.startSliding(9000);
        imgslider.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemSelected(int i) {
                Toast.makeText(MainActivity.this, ""+imageList.get(i).getTitle(), Toast.LENGTH_SHORT).show();
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
        pullToRefresh=findViewById(R.id.pullToRefresh);
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
