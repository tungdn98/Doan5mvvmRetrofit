package com.example.giaysnaker6789.views;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.giaysnaker6789.BaseResponse.ProductBaseResponse;
import com.example.giaysnaker6789.adapter.SpTrangchuAdapterHoz;
import com.example.giaysnaker6789.models.image_products;
import com.example.giaysnaker6789.models.product_types;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.giaysnaker6789.R;
import com.example.giaysnaker6789.models.banners;
import com.example.giaysnaker6789.models.products;
import com.example.giaysnaker6789.models.vouchers;
import com.example.giaysnaker6789.network.RetrofitService;
import com.example.giaysnaker6789.viewModels.ImageProductViewModel;
import com.example.giaysnaker6789.viewModels.ProductViewModel;
import com.example.giaysnaker6789.viewModels.VoucherViewModel;
import com.example.tungnuislider.ImageSlider;
import com.example.tungnuislider.models.SlideModel;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

public class ProductDetailActivity extends BaseActivity {
     TextView txttitle, txtprice, txtpromotion,txtdescription;
     RecyclerView rclistfeedback,rcsplienquan;
     Button btnchonmua,btnvietnhanxet,btncheckvoucher;
     EditText edtvou;
    ImageSlider imgslider;

    ArrayList<products> listproduct=new ArrayList<>();
     ProductViewModel productViewModel;
    ImageProductViewModel imageProductViewModel;
    VoucherViewModel voucherViewModel;
    SpTrangchuAdapterHoz recyclerViewAdapter;
    StaggeredGridLayoutManager layoutManager;
    products pro;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        initview();

        btncheckvoucher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String vou=edtvou.getText().toString();
                if(vou.isEmpty()){
                    Toast.makeText(ProductDetailActivity.this, "không tìm thấy voucher", Toast.LENGTH_SHORT).show();
                }else{
                    voucherViewModel.checkVoucher(vou,pro.getId()).observe(ProductDetailActivity.this, new Observer<vouchers>() {
                        @Override
                        public void onChanged(vouchers vouchers) {
                            if(vouchers.getId()!=null){
                                int promotion=pro.getPromotion()-vouchers.getPromotion();
                                txtpromotion.setText(""+promotion);
                            }else{
                                Toast.makeText(ProductDetailActivity.this, "mã giảm giá không đúng", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

    }

    private void initview() {
        productViewModel= ViewModelProviders.of(this).get(ProductViewModel.class);
        imageProductViewModel= ViewModelProviders.of(this).get(ImageProductViewModel.class);
        voucherViewModel= ViewModelProviders.of(this).get(VoucherViewModel.class);
        txttitle=findViewById(R.id.txttitle);
        txtprice =findViewById(R.id.txtprice);
        txtpromotion =findViewById(R.id.txtprmotion);
        txtdescription=findViewById(R.id.txtdescription);
        rclistfeedback=findViewById(R.id.rclistfeedback);
        rcsplienquan=findViewById(R.id.rcsplienquan);
        btnchonmua=findViewById(R.id.btnchonmua);
        btnvietnhanxet=findViewById(R.id.btnvietnhanxet);
        btncheckvoucher=findViewById(R.id.checkvoucher);
        edtvou=findViewById(R.id.edtvoucher);
        imgslider=findViewById(R.id.imgslide);
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onMessageEvent(products event) { // get model test
        pro=new products();
        pro=event;
        txttitle.setText(""+event.getName());
        txtdescription.setText(""+event.getDescribe());
        txtpromotion.setText(""+event.getPromotion());
        txtprice.setText(""+event.getPrice());
        loadDetail(event.getIdProductType());
        loadImage(event.getId());
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onMessageEvent(banners event) { // get model test

    }
    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onMessageEvent(String event) {
        pro=new products();
        pro.setId(Integer.parseInt(event));
        loadbyid(event);
    }

    private void loadbyid(String event) {
        productViewModel.searchByid(event).observe(this, new Observer<products>() {
            @Override
            public void onChanged(products products) {
                pro=products;
                txttitle.setText(""+products.getName());
                txtdescription.setText(""+products.getDescribe());
                txtpromotion.setText(""+products.getPromotion());
                txtprice.setText(""+products.getPrice());
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

                if(productBaseResponse.getData().size()>0){
                    listproduct.addAll((ArrayList<products>) productBaseResponse.getData());
                    layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.HORIZONTAL);
                    rcsplienquan.setLayoutManager(layoutManager);
                    recyclerViewAdapter = new SpTrangchuAdapterHoz(listproduct, ProductDetailActivity.this);
                    rcsplienquan.setAdapter(recyclerViewAdapter);
                }else{
                    Toast.makeText(ProductDetailActivity.this, "không tìm thấy sản phẩm", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    public void loadImage(int id){
        imageProductViewModel.getproImage(id).observe(this, new Observer<List<image_products>>() {
            @Override
            public void onChanged(List<image_products> image_products) {
                if(image_products.size()>0){
                    List<SlideModel> imageList = new ArrayList<>();
                    for (int i = 0; i < image_products.size(); i++) {
                        imageList.add(new SlideModel(RetrofitService.basePath + image_products.get(i).getImage(), image_products.get(i).getIdProduct()));
                    }
                    imgslider.setImageList(imageList, false);
                }else{
                    Toast.makeText(ProductDetailActivity.this, "ko tifm thaasy sarn phaarm", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

}
