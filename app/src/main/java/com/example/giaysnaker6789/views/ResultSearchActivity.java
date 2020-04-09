package com.example.giaysnaker6789.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.giaysnaker6789.R;
import com.example.giaysnaker6789.adapter.searchAdapter;
import com.example.giaysnaker6789.models.products;
import com.example.giaysnaker6789.models.user1s;
import com.example.giaysnaker6789.viewModels.ProductViewModel;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

public class ResultSearchActivity extends BaseActivity {
    ListView lvitem;
    TextView txtEmty;

    SearchView searchView;
    TextView txtbadge, txttitle;
    ImageView imgMenu;

    searchAdapter adapter;
    ArrayList<products> listproductl=new ArrayList<>();

    ProductViewModel productViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_search);
        init();

    }

    private void populateData(String name) {
        productViewModel.searchProduct(name).observe(this, new Observer<List<products>>() {
            @Override
            public void onChanged(List<products> products) {
                if(products!=null){
                    listproductl.addAll(products);
                    adapter=new searchAdapter(listproductl,ResultSearchActivity.this);
                    lvitem.setAdapter(adapter);
                }else{
                    lvitem.setEmptyView(txtEmty);
                }
            }
        });
    }

    private void init() {
        lvitem=findViewById(R.id.lvsp);
        txtEmty=findViewById(R.id.txtempty);
        productViewModel = ViewModelProviders.of(this).get(ProductViewModel.class);
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onMessageEvent(String event) {
        Toast.makeText(this, ""+event, Toast.LENGTH_SHORT).show();
        populateData(event);
    }
}
