package com.example.giaysnaker6789.views;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.giaysnaker6789.BaseResponse.ProductBaseResponse;
import com.example.giaysnaker6789.R;
import com.example.giaysnaker6789.adapter.LoadMoreAdapter;
import com.example.giaysnaker6789.adapter.ProductTypeAdapter;
import com.example.giaysnaker6789.models.products;
import com.example.giaysnaker6789.models.product_types;
import com.example.giaysnaker6789.viewModels.ProductTypeViewModel;
import com.example.giaysnaker6789.viewModels.ProductViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LoadMoreActivity extends BaseActivity {
    RecyclerView recyclerView;
    FloatingActionButton btnscrolltop;
    Spinner spgia,spxuatsu;
    LoadMoreAdapter loadMoreAdapter;
    ArrayList<products> rowsArrayList = new ArrayList<>();
    GridLayoutManager layoutManager;
    boolean isLoading = false;
    ProductViewModel productViewModel;
    ProductTypeViewModel productTypeViewModel;
    int page = 1;
    int typesort = 0 ;
    int typeorigin = 00;
    private ProgressDialog dialog;

    SearchView searchView;
    TextView txtbadge, txttitle;
    ImageView imgMenu;

    final ArrayList<String> listgia = new ArrayList<String>();
    final ArrayList<product_types> listxuatsu = new ArrayList<product_types>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_more);
        productViewModel = ViewModelProviders.of(this).get(ProductViewModel.class);
        productTypeViewModel = ViewModelProviders.of(this).get(ProductTypeViewModel.class);
        initview();
        setupspinner();
       // populateData();
        initScrollListener();
        imgbackEvent();
        btnscrolltop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GridLayoutManager layoutManager = (GridLayoutManager) recyclerView
                        .getLayoutManager();
                layoutManager.scrollToPositionWithOffset(0, 0);
            }
        });
    }

    private void setupspinner() {
        // setup spinner locj theo gia
        listgia.add("lọc theo giá cả");
        listgia.add("giá rẻ trước");
        listgia.add("từ cao xuống thấp");
        ArrayAdapter<String> adp1 = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, listgia);
        adp1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spgia.setAdapter(adp1);

        // setup spinner loc theo xuat su
        // load list product types thực ra chỗ này phải load từ server về

        productTypeViewModel.LoadProductType().observe(this, new Observer<List<product_types>>() {
            @Override
            public void onChanged(List<product_types> product_types) {
                listxuatsu.add(new product_types(00,"chọn quốc gia",""));
                listxuatsu.addAll(product_types);
                ArrayAdapter<product_types> adapter =
                        new ArrayAdapter<product_types>(getApplicationContext(),  android.R.layout.simple_spinner_dropdown_item, listxuatsu);
                adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
                spxuatsu.setAdapter(adapter);
            }
        });




        spxuatsu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                typeorigin=listxuatsu.get(position).getId();
                rowsArrayList.clear();
                populateData();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spgia.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                typesort=position;
                rowsArrayList.clear();
                populateData();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void imgbackEvent() {
        imgMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initview() {
        recyclerView = findViewById(R.id.recyclerView);
        btnscrolltop = findViewById(R.id.btnscrolltop);
        imgMenu = findViewById(R.id.imgback);
        txtbadge = findViewById(R.id.text);
        txttitle = findViewById(R.id.txttile);
        searchView = findViewById(R.id.searchView);
        spgia=findViewById(R.id.spgia);
        spxuatsu=findViewById(R.id.spfilter);
    }

    private void populateData() {
        productViewModel.LoadProduct(1,typesort,typeorigin).observe(this, new Observer<ProductBaseResponse>() {
            @Override
            public void onChanged(ProductBaseResponse productBaseResponse) {
                rowsArrayList = (ArrayList<products>) productBaseResponse.getData();

                recyclerView.setHasFixedSize(true);
                layoutManager = new GridLayoutManager(LoadMoreActivity.this, 2);

                recyclerView.setLayoutManager(layoutManager);

                loadMoreAdapter = new LoadMoreAdapter(rowsArrayList);
                recyclerView.setAdapter(loadMoreAdapter);
                isLoading = false;
                page=1;
            }
        });
    }

    private void initScrollListener() {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                GridLayoutManager staggeredGridLayoutManager = (GridLayoutManager) recyclerView.getLayoutManager();
                if (!isLoading) {
                    if (staggeredGridLayoutManager != null && staggeredGridLayoutManager.findLastCompletelyVisibleItemPosition() == rowsArrayList.size() - 1) {
                        dialog = new ProgressDialog(LoadMoreActivity.this);
                        dialog.setMessage("đang load chờ tý...");
                        dialog.show();
                        loadMore(typesort,typeorigin);
                        isLoading = true;
                    }
                }

            }
        });
    }

    private void loadMore(int typesort,int typeorigin) {
        rowsArrayList.add(null);
        loadMoreAdapter.notifyItemInserted(rowsArrayList.size() - 1);
        page = page + 1;
        productViewModel.LoadProduct(page,typesort,typeorigin).observe(this, new Observer<ProductBaseResponse>() {
            @Override
            public void onChanged(ProductBaseResponse productBaseResponse) {
                if (productBaseResponse.getLastPage() > page) {
                    rowsArrayList.remove(rowsArrayList.size() - 1);
                    int scrollPosition = rowsArrayList.size();
                    loadMoreAdapter.notifyItemRemoved(scrollPosition);
                    rowsArrayList.addAll((ArrayList<products>) productBaseResponse.getData());
                    loadMoreAdapter.notifyDataSetChanged();
                    isLoading = false;
                    dialog.dismiss();
                } else {
                    dialog.dismiss();
                    Toast.makeText(LoadMoreActivity.this, "đã hết mặt hàng ", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }



}
