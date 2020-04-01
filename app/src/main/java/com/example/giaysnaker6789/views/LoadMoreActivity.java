package com.example.giaysnaker6789.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.example.giaysnaker6789.BaseResponse.ProductBaseResponse;
import com.example.giaysnaker6789.R;
import com.example.giaysnaker6789.adapter.RecyclerViewAdapter;
import com.example.giaysnaker6789.adapter.SpTrangchuAdapter;
import com.example.giaysnaker6789.models.products;
import com.example.giaysnaker6789.viewModels.ProductViewModel;

import java.util.ArrayList;

public class LoadMoreActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    RecyclerViewAdapter recyclerViewAdapter;
    ArrayList<products> rowsArrayList = new ArrayList<>();
    LinearLayoutManager layoutManager;
    boolean isLoading = false;
    ProductViewModel productViewModel;
    int page=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_more);
        productViewModel = ViewModelProviders.of(this).get(ProductViewModel.class);
        recyclerView = findViewById(R.id.recyclerView);
        populateData();
        initScrollListener();
    }

    private void populateData() {
        productViewModel.LoadProduct(1).observe(this, new Observer<ProductBaseResponse>() {
            @Override
            public void onChanged(ProductBaseResponse productBaseResponse) {
                rowsArrayList = (ArrayList<products>) productBaseResponse.getData();

                recyclerView.setHasFixedSize(true);
                layoutManager = new LinearLayoutManager(LoadMoreActivity.this);
                recyclerView.setLayoutManager(layoutManager);

                recyclerViewAdapter = new RecyclerViewAdapter(rowsArrayList);
                recyclerView.setAdapter(recyclerViewAdapter);
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
                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                if (!isLoading) {
                    if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == rowsArrayList.size() - 1) {
                        //bottom of list!
                        loadMore();
                        isLoading = true;
                    }
                }

            }
        });
    }

    private void loadMore() {
        rowsArrayList.add(null);
        recyclerViewAdapter.notifyItemInserted(rowsArrayList.size() - 1);
        page=page+1;
        productViewModel.LoadProduct(page).observe(this, new Observer<ProductBaseResponse>() {
            @Override
            public void onChanged(ProductBaseResponse productBaseResponse) {
                rowsArrayList.remove(rowsArrayList.size() - 1);
                int scrollPosition = rowsArrayList.size();
                recyclerViewAdapter.notifyItemRemoved(scrollPosition);
                int currentSize = scrollPosition;
                int nextLimit = currentSize + 15;

                rowsArrayList.addAll((ArrayList<products>) productBaseResponse.getData());
                recyclerViewAdapter.notifyDataSetChanged();
                isLoading = false;
            }
        });
    }
}
