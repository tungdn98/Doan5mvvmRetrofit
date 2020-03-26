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

import android.app.ProgressDialog;
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
import com.example.giaysnaker6789.models.test;
import com.example.giaysnaker6789.viewModels.testViewmodel;
import com.google.android.material.navigation.NavigationView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

public class MainActivity extends BaseActivity {
    DrawerLayout mDrawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    SearchView searchView;
    TextView txtbadge,txttitle;
    ImageView imgMenu;
    int i = 0;

    testViewmodel newsViewModel;
    private static final String TAG = "tungtung";

    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        txttitle.setText("tugn");
        newsViewModel = ViewModelProviders.of(this).get(testViewmodel.class);

        Button btn=findViewById(R.id.button);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog = new ProgressDialog(MainActivity.this);

                progressDialog.setMessage("đang load nè");
                progressDialog.show();
                newsViewModel.loginHandle("tung","tung").observe(MainActivity.this, new Observer<List<test>>() {
                    @Override
                    public void onChanged(List<test> tests) {
                        Log.d(TAG, "login: "+tests.get(0).getUser());
                        if(tests.size()>0){
                            Toast.makeText(MainActivity.this, "tài khoản "+tests.get(0).getUser(), Toast.LENGTH_SHORT).show();
                            EventBus.getDefault().postSticky(new test(tests.get(0).getId(),tests.get(0).getUser(),tests.get(0).getPass()));
                            progressDialog.dismiss();
                        }

                    }
                });

                newsViewModel.insertHandle("is","is").observe(MainActivity.this, new Observer<Integer>() {
                    @Override
                    public void onChanged(Integer integer) {
                        Log.d(TAG, "onChanged: "+integer);
                    }
                });

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
