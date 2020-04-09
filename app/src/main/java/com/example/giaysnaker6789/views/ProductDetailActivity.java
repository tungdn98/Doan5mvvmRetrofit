package com.example.giaysnaker6789.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.giaysnaker6789.BaseResponse.ProductBaseResponse;
import com.example.giaysnaker6789.adapter.SpTrangchuAdapter;
import com.example.giaysnaker6789.adapter.SpTrangchuAdapterHoz;
import com.example.giaysnaker6789.models.image_products;
import com.example.giaysnaker6789.models.product_types;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.giaysnaker6789.R;
import com.example.giaysnaker6789.models.banners;
import com.example.giaysnaker6789.models.products;
import com.example.giaysnaker6789.models.test;
import com.example.giaysnaker6789.network.RetrofitService;
import com.example.giaysnaker6789.viewModels.ImageProductViewModel;
import com.example.giaysnaker6789.viewModels.ProductViewModel;
import com.example.tungnuislider.ImageSlider;
import com.example.tungnuislider.models.SlideModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

public class ProductDetailActivity extends BaseActivity {
     TextView txttitle,txtgiachinh,txtgiagiam,txtdescription;
     RecyclerView rclistfeedback,rcsplienquan;
     Button btnchonmua,btnvietnhanxet;
    ImageSlider imgslider;

    ArrayList<products> listproduct=new ArrayList<>();
     ProductViewModel productViewModel;
    ImageProductViewModel imageProductViewModel;
    SpTrangchuAdapterHoz recyclerViewAdapter;
    StaggeredGridLayoutManager layoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        initview();

    }

    private void initview() {
        productViewModel= ViewModelProviders.of(this).get(ProductViewModel.class);
        imageProductViewModel= ViewModelProviders.of(this).get(ImageProductViewModel.class);

        txttitle=findViewById(R.id.txttitle);
        txtgiachinh=findViewById(R.id.txtgiachinh);
        txtgiagiam=findViewById(R.id.txtgiagiam);
        txtdescription=findViewById(R.id.txtdescription);
        rclistfeedback=findViewById(R.id.rclistfeedback);
        rcsplienquan=findViewById(R.id.rcsplienquan);
        btnchonmua=findViewById(R.id.btnchonmua);
        btnvietnhanxet=findViewById(R.id.btnvietnhanxet);
        imgslider=findViewById(R.id.imgslide);
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onMessageEvent(products event) { // get model test
        txttitle.setText(""+event.getName());
        txtdescription.setText(""+event.getDescribe());
        txtgiagiam.setText(""+event.getPromotion());
        txtgiachinh.setText(""+event.getPrice());
        loadDetail(event.getIdProductType());
        loadImage(event.getId());
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onMessageEvent(banners event) { // get model test

    }
    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onMessageEvent(String event) { // get model test
        loadbyid(event);
    }

    private void loadbyid(String event) {
        productViewModel.searchByid(event).observe(this, new Observer<products>() {
            @Override
            public void onChanged(products products) {
                txttitle.setText(""+products.getName());
                txtdescription.setText(""+products.getDescribe());
                txtgiagiam.setText(""+products.getPromotion());
                txtgiachinh.setText(""+products.getPrice());
                loadDetail(products.getIdProductType());
                loadImage(products.getId());
            }
        });
    }


    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onMessageEvent(product_types event) { // get model test

    }


    private void loadDetail(int idtype) {
        productViewModel.LoadProductwithType(idtype).observe(this, new Observer<ProductBaseResponse>() {
            @Override
            public void onChanged(ProductBaseResponse productBaseResponse) {
                listproduct.addAll((ArrayList<products>) productBaseResponse.getData());
                layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.HORIZONTAL);
                rcsplienquan.setLayoutManager(layoutManager);
                recyclerViewAdapter = new SpTrangchuAdapterHoz(listproduct, ProductDetailActivity.this);
                rcsplienquan.setAdapter(recyclerViewAdapter);
            }
        });

    }

    public void loadImage(int id){
        imageProductViewModel.getproImage(id).observe(this, new Observer<List<image_products>>() {
            @Override
            public void onChanged(List<image_products> image_products) {
                List<SlideModel> imageList = new ArrayList<>();
                for (int i = 0; i < image_products.size(); i++) {
                    imageList.add(new SlideModel(RetrofitService.basePath + image_products.get(i).getImage(), image_products.get(i).getIdProduct()));
                }
                imgslider.setImageList(imageList, false);
            }
        });
    }

}
