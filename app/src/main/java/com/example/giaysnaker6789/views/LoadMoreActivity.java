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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.giaysnaker6789.BaseResponse.ProductBaseResponse;
import com.example.giaysnaker6789.R;
import com.example.giaysnaker6789.adapter.LoadMoreAdapter;
import com.example.giaysnaker6789.models.products;
import com.example.giaysnaker6789.viewModels.ProductViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class LoadMoreActivity extends BaseActivity {
    RecyclerView recyclerView;
    FloatingActionButton btnscrolltop;
    LoadMoreAdapter loadMoreAdapter;
    ArrayList<products> rowsArrayList = new ArrayList<>();
    GridLayoutManager layoutManager;
    boolean isLoading = false;
    ProductViewModel productViewModel;
    int page=1;
    private ProgressDialog dialog;

    SearchView searchView;
    TextView txtbadge, txttitle;
    ImageView imgMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_more);
        productViewModel = ViewModelProviders.of(this).get(ProductViewModel.class);
        initview();
        populateData();
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
        btnscrolltop=findViewById(R.id.btnscrolltop);
        imgMenu = findViewById(R.id.imgback);
        txtbadge = findViewById(R.id.text);
        txttitle = findViewById(R.id.txttile);
        searchView = findViewById(R.id.searchView);
    }

    private void populateData() {
        productViewModel.LoadProduct(1).observe(this, new Observer<ProductBaseResponse>() {
            @Override
            public void onChanged(ProductBaseResponse productBaseResponse) {
                rowsArrayList = (ArrayList<products>) productBaseResponse.getData();

                recyclerView.setHasFixedSize(true);
                layoutManager = new GridLayoutManager(LoadMoreActivity.this,2);

                recyclerView.setLayoutManager(layoutManager);

                loadMoreAdapter = new LoadMoreAdapter(rowsArrayList);
                recyclerView.setAdapter(loadMoreAdapter);
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
                        loadMore();
                        isLoading = true;
                    }
                }

            }
        });
    }

    private void loadMore() {
        rowsArrayList.add(null);
        loadMoreAdapter.notifyItemInserted(rowsArrayList.size() - 1);
        page=page+1;
        productViewModel.LoadProduct(page).observe(this, new Observer<ProductBaseResponse>() {
            @Override
            public void onChanged(ProductBaseResponse productBaseResponse) {
                if(productBaseResponse.getLastPage()>page){
                    rowsArrayList.remove(rowsArrayList.size() - 1);
                    int scrollPosition = rowsArrayList.size();
                    loadMoreAdapter.notifyItemRemoved(scrollPosition);
                    rowsArrayList.addAll((ArrayList<products>) productBaseResponse.getData());
                    loadMoreAdapter.notifyDataSetChanged();
                    isLoading = false;
                    dialog.dismiss();
                }else{
                    dialog.dismiss();
                    Toast.makeText(LoadMoreActivity.this, "đã hết mặt hàng ", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
