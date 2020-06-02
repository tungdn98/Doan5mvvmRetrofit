package com.example.giaysnaker6789.views;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.giaysnaker6789.R;
import com.example.giaysnaker6789.adapter.PagerAdapterOrder;
import com.google.android.material.tabs.TabLayout;

import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

public class OrderManagerActivity extends BaseActivity{
    private ViewPager pager;
    private TabLayout tabLayout;
    ImageView imgback;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_manager);
        addControl();
        imgback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    private void addControl() {
        imgback=findViewById(R.id.imgback);
        pager = findViewById(R.id.view_pager);
        tabLayout =  findViewById(R.id.tab_layout);
        FragmentManager manager=getSupportFragmentManager();
        PagerAdapterOrder adapter=new PagerAdapterOrder(manager); //khởi tạo adapter cho viewpager
        pager.setAdapter(adapter);
        tabLayout.setupWithViewPager(pager);
        pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));// lắng nghe sự kiện thay ddooir khi vuốt
        tabLayout.setTabsFromPagerAdapter(adapter);
    }


}
