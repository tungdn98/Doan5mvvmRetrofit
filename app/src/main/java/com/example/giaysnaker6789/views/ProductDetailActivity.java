package com.example.giaysnaker6789.views;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.example.giaysnaker6789.BaseResponse.BaseResponseFeedback;
import com.example.giaysnaker6789.BaseResponse.ProductBaseResponse;
import com.example.giaysnaker6789.adapter.FeedbackAdapter;
import com.example.giaysnaker6789.adapter.SpTrangchuAdapterHoz;
import com.example.giaysnaker6789.config.SharedPref;
import com.example.giaysnaker6789.models.feedback_products;
import com.example.giaysnaker6789.models.image_products;
import com.example.giaysnaker6789.models.product_types;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.giaysnaker6789.R;
import com.example.giaysnaker6789.models.banners;
import com.example.giaysnaker6789.models.products;
import com.example.giaysnaker6789.models.user1s;
import com.example.giaysnaker6789.models.vouchers;
import com.example.giaysnaker6789.network.RetrofitService;
import com.example.giaysnaker6789.roommodel.Cart;
import com.example.giaysnaker6789.roommodel.CartViewModel;
import com.example.giaysnaker6789.viewModels.FeedbackViewModel;
import com.example.giaysnaker6789.viewModels.ImageProductViewModel;
import com.example.giaysnaker6789.viewModels.ProductViewModel;
import com.example.giaysnaker6789.viewModels.VoucherViewModel;
import com.example.tungnuislider.ImageSlider;
import com.example.tungnuislider.models.SlideModel;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

public class ProductDetailActivity extends BaseActivity {
    TextView txttitle, txtprice, txtpromotion, txtdescription;
    RecyclerView rclistfeedback, rcsplienquan;
    Button btnchonmua, btnvietnhanxet, btncheckvoucher;
    EditText edtvou;
    ImageSlider imgslider;

    ArrayList<products> listproduct = new ArrayList<>();
    ArrayList<feedback_products> listfeedback = new ArrayList<>();
    ProductViewModel productViewModel;
    ImageProductViewModel imageProductViewModel;
    VoucherViewModel voucherViewModel;
    FeedbackViewModel feedbackViewModel;
    private CartViewModel cartViewModel;

    SpTrangchuAdapterHoz recyclerViewAdapter;
    FeedbackAdapter feedbackAdapter;

    StaggeredGridLayoutManager layoutManager;
    LinearLayoutManager layoutManagerfeedback;
    products pro;
    user1s user;


    ArrayList<Cart> huhu = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        initview();

        btncheckvoucher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String vou = edtvou.getText().toString();
                if (vou.isEmpty()) {
                    Toast.makeText(ProductDetailActivity.this, "không tìm thấy voucher", Toast.LENGTH_SHORT).show();
                } else {
                    voucherViewModel.checkVoucher(vou, pro.getId()).observe(ProductDetailActivity.this, new Observer<vouchers>() {
                        @Override
                        public void onChanged(vouchers vouchers) {
                            if (vouchers.getId() != null) {
                                int promotion = pro.getPromotion() - vouchers.getPromotion();
                                txtpromotion.setText("" + promotion);
                            } else {
                                Toast.makeText(ProductDetailActivity.this, "mã giảm giá không đúng", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
        btnchonmua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnchonmua.setEnabled(false);
                boolean check = SharedPref.read(SharedPref.LOGIN, false);
                if (!check) {
                    startActivity(new Intent(ProductDetailActivity.this, LoginActivity.class));
                    Animatoo.animateCard(ProductDetailActivity.this);
                } else {
                    themgiohang();
                }


            }
        });


    }

    private void themgiohang() {
        cartViewModel.getAllNotes().observe(this, new Observer<List<Cart>>() {
            @Override
            public void onChanged(List<Cart> carts) {
               if(carts!=null){
                   huhu.addAll(carts);
               }else{
                   huhu.add(null);
               }
               xulythem();
            }
        });

    }
    private void xulythem(){
        boolean check = false;
        for(int i=0;i<huhu.size();i++){
            if (huhu.get(i).getIdsanpham() == pro.getId()) {
                check = true;
            }
        }

        if (!check) { // nếu sp chưa tồn tại trong giỏ hàng
            Cart cart = new Cart();
            int giatien = Integer.parseInt(txtpromotion.getText().toString());
            cart.setTensp(pro.getName());
            cart.setIduser(user.getId());
            cart.setIdsanpham(pro.getId());
            cart.setTenuser(user.getName());
            cart.setXuatsu(pro.getOrigin());
            cart.setGiadagiam(giatien);
            cart.setSoluong(1);
            cart.setThanhtien(giatien);
            cart.setHinhanh(pro.getImage());
            cart.setVoucher(edtvou.getText().toString());
            cartViewModel.insert(cart);
            Toast.makeText(ProductDetailActivity.this, "đã thêm sp mới", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(ProductDetailActivity.this, "đã có trong giỏ hàng", Toast.LENGTH_SHORT).show();
        }
    }


    private void initview() {
        SharedPref.init(ProductDetailActivity.this);
        productViewModel = ViewModelProviders.of(this).get(ProductViewModel.class);
        imageProductViewModel = ViewModelProviders.of(this).get(ImageProductViewModel.class);
        voucherViewModel = ViewModelProviders.of(this).get(VoucherViewModel.class);
        cartViewModel = ViewModelProviders.of(this).get(CartViewModel.class);
        feedbackViewModel = ViewModelProviders.of(this).get(FeedbackViewModel.class);
        txttitle = findViewById(R.id.txttitle);
        txtprice = findViewById(R.id.txtprice);
        txtpromotion = findViewById(R.id.txtprmotion);
        txtdescription = findViewById(R.id.txtdescription);
        rclistfeedback = findViewById(R.id.rclistfeedback);
        rcsplienquan = findViewById(R.id.rcsplienquan);
        btnchonmua = findViewById(R.id.btnchonmua);
        btnvietnhanxet = findViewById(R.id.btnvietnhanxet);
        btncheckvoucher = findViewById(R.id.checkvoucher);
        edtvou = findViewById(R.id.edtvoucher);
        imgslider = findViewById(R.id.imgslide);
        String id = getIntent().getStringExtra("noti");
        if (id != null) {
            loadbyid(id);
        }
    }


    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onMessageEvent(user1s event) { // get model test
        user = new user1s();
        user = event;
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onMessageEvent(products event) { // get model test
        pro = new products();
        pro = event;
        txttitle.setText("" + event.getName());
        txtdescription.setText("" + event.getDescribe());
        txtpromotion.setText("" + event.getPromotion());
        txtprice.setText("" + event.getPrice());
        loadDetail(event.getIdProductType());
        loadImage(event.getId());
        loadfeedback(event.getId(),1);

    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onMessageEvent(banners event) { // get model test

    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onMessageEvent(String event) {
        loadbyid(event);

    }

    private void loadbyid(String event) {
        productViewModel.searchByid(event).observe(this, new Observer<products>() {
            @Override
            public void onChanged(products products) {

                txttitle.setText("" + products.getName());
                txtdescription.setText("" + products.getDescribe());
                txtpromotion.setText("" + products.getPromotion());
                txtprice.setText("" + products.getPrice());
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

                if (productBaseResponse.getData().size() > 0) {
                    listproduct.addAll((ArrayList<products>) productBaseResponse.getData());
                    layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.HORIZONTAL);
                    rcsplienquan.setLayoutManager(layoutManager);
                    recyclerViewAdapter = new SpTrangchuAdapterHoz(listproduct, ProductDetailActivity.this);
                    rcsplienquan.setAdapter(recyclerViewAdapter);
                } else {
                    Toast.makeText(ProductDetailActivity.this, "không tìm thấy sản phẩm", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    public void loadImage(int id) {
        imageProductViewModel.getproImage(id).observe(this, new Observer<List<image_products>>() {
            @Override
            public void onChanged(List<image_products> image_products) {
                if (image_products.size() > 0) {
                    List<SlideModel> imageList = new ArrayList<>();
                    for (int i = 0; i < image_products.size(); i++) {
                        imageList.add(new SlideModel(RetrofitService.basePath + image_products.get(i).getImage(), image_products.get(i).getIdProduct()));
                    }
                    imgslider.setImageList(imageList, false);
                } else {
                    Toast.makeText(ProductDetailActivity.this, "ko tifm thaasy sarn phaarm", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    public void loadfeedback(int idproduct,int page){
        feedbackViewModel.LoadListProductType(idproduct,1).observe(this, new Observer<BaseResponseFeedback>() {
            @Override
            public void onChanged(BaseResponseFeedback baseResponseFeedback) {

                if (baseResponseFeedback.getData().size() > 0) {
                    listfeedback.addAll(baseResponseFeedback.getData());
                    layoutManagerfeedback = new LinearLayoutManager(ProductDetailActivity.this);
                    rclistfeedback.setLayoutManager(layoutManagerfeedback);
                    feedbackAdapter = new FeedbackAdapter(listfeedback);
                    rclistfeedback.setAdapter(feedbackAdapter);
                } else {
                    Toast.makeText(ProductDetailActivity.this, "không tìm thấy bình luận", Toast.LENGTH_SHORT).show();
                }

            }
        });

        btnvietnhanxet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProductDetailActivity.this,CommentActivity.class));
                Animatoo.animateSplit(ProductDetailActivity.this);
            }
        });
    }

}
