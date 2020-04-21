package com.example.giaysnaker6789.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.example.giaysnaker6789.R;
import com.example.giaysnaker6789.adapter.PagerAdapterOrder;
import com.google.android.material.tabs.TabLayout;

public class OrderManagerActivity extends AppCompatActivity {
    private ViewPager pager;
    private TabLayout tabLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_manager);
        addControl();
    }
    private void addControl() {
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
