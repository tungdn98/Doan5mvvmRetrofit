package com.example.giaysnaker6789.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.example.giaysnaker6789.BaseResponse.BaseResponseFeedback;
import com.example.giaysnaker6789.BaseResponse.BillUserResponse;
import com.example.giaysnaker6789.BaseResponse.ProductBaseResponse;
import com.example.giaysnaker6789.ItemClickSupport;
import com.example.giaysnaker6789.R;
import com.example.giaysnaker6789.adapter.FeedbackAdapter;
import com.example.giaysnaker6789.adapter.SpTrangchuAdapterHoz;
import com.example.giaysnaker6789.config.SharedPref;
import com.example.giaysnaker6789.models.banners;
import com.example.giaysnaker6789.models.billuser;
import com.example.giaysnaker6789.models.feedback_products;
import com.example.giaysnaker6789.models.image_products;
import com.example.giaysnaker6789.models.product_types;
import com.example.giaysnaker6789.models.products;
import com.example.giaysnaker6789.models.user1s;
import com.example.giaysnaker6789.models.vouchers;
import com.example.giaysnaker6789.network.RetrofitService;
import com.example.giaysnaker6789.viewModels.BillUserViewModel;
import com.example.giaysnaker6789.viewModels.BillViewModel;
import com.example.giaysnaker6789.viewModels.FeedbackViewModel;
import com.example.giaysnaker6789.viewModels.ImageProductViewModel;
import com.example.giaysnaker6789.viewModels.ProductViewModel;
import com.example.giaysnaker6789.viewModels.VoucherViewModel;
import com.example.tungnuislider.ImageSlider;
import com.example.tungnuislider.models.SlideModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

public class ProductDetailActivity extends BaseActivity {
    TextView txttitle, txtprice, txtpromotion, txtdescription,txtSaled;
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
    BillViewModel billViewModel;
    BillUserViewModel billUserViewModel;

    SpTrangchuAdapterHoz recyclerViewAdapter;
    FeedbackAdapter feedbackAdapter;

    StaggeredGridLayoutManager layoutManager;
    LinearLayoutManager layoutManagerfeedback;
    products pro;
    user1s user;
    billuser billuser;


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
                                txtpromotion.setText("" + promotion +" đ");
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
                    Addtocart();
                }
            }
        });

        ItemClickSupport.addTo(rcsplienquan).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                products pro = listproduct.get(position);
                EventBus.getDefault().postSticky(pro);
                startActivity(new Intent(ProductDetailActivity.this, ProductDetailActivity.class));
                Animatoo.animateShrink(ProductDetailActivity.this);
            }
        });

    }

    private void Addtocart() {
        if(user==null){
            startActivity(new Intent(ProductDetailActivity.this, LoginActivity.class));
            Animatoo.animateCard(ProductDetailActivity.this);
        }
        int iduser = user.getId();
        int idproduct = pro.getId();
        int price = Integer.parseInt(txtpromotion.getText().toString());
        DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd"); // Format date
        String date = df1.format(Calendar.getInstance().getTime());
        String vou = edtvou.getText().toString();
            if(billuser!=null){
                billUserViewModel.AddtoExCart( "b1", price, 1, date, idproduct, vou, billuser.getId()).observe(this, new Observer<BillUserResponse>() {
                    @Override
                    public void onChanged(BillUserResponse billUserResponse) {
                        Toast.makeText(ProductDetailActivity.this, "add to ex cart", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(ProductDetailActivity.this, MainActivity.class));
                        Animatoo.animateSplit(ProductDetailActivity.this);
                    }
                });
            }else{ // thêm mới nè
                billUserViewModel.AddtoCart( iduser,  "b1",  price,  1,  date,
                         "", idproduct,  vou).observe(this, new Observer<BillUserResponse>() {
                    @Override
                    public void onChanged(BillUserResponse billUserResponse) {
                        Toast.makeText(ProductDetailActivity.this, "đã thêm vào giỏ hàng", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(ProductDetailActivity.this, MainActivity.class));
                        Animatoo.animateSplit(ProductDetailActivity.this);
                    }
                });
            }


    }


    private void initview() {
        SharedPref.init(ProductDetailActivity.this);

        productViewModel = ViewModelProviders.of(this).get(ProductViewModel.class);
        imageProductViewModel = ViewModelProviders.of(this).get(ImageProductViewModel.class);
        voucherViewModel = ViewModelProviders.of(this).get(VoucherViewModel.class);
        billViewModel = ViewModelProviders.of(ProductDetailActivity.this).get(BillViewModel.class);
        feedbackViewModel = ViewModelProviders.of(this).get(FeedbackViewModel.class);
        billUserViewModel = ViewModelProviders.of(this).get(BillUserViewModel.class);

        txttitle = findViewById(R.id.txttitle);
        txtSaled=findViewById(R.id.txtSaled);
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
    public void onMessageEvent(products event) { // get model 
        pro = new products();
        pro = event;
        txttitle.setText("" + event.getName());
        txtdescription.setText("" + event.getDescribe());
        txtpromotion.setText("" + format(event.getPromotion())+" đ");
        txtprice.setText("" + format(event.getPrice())+" đ");
        txtSaled.setText("Đã bán: "+event.getSaled());
        loadDetail(event.getIdProductType());
        loadImage(event.getId());
        loadfeedback(event.getId(), 1);

    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onMessageEvent(billuser event) { // get model test
        billuser = event;
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
                txtpromotion.setText("" + format(products.getPromotion())+" đ");
                txtprice.setText("" + format(products.getPrice())+" đ");
                txtSaled.setText("Đã bán: "+products.getSaled());
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
                }

            }
        });
    }

    public void loadfeedback(int idproduct, int page) {
        feedbackViewModel.LoadListProductType(idproduct, 1).observe(this, new Observer<BaseResponseFeedback>() {
            @Override
            public void onChanged(BaseResponseFeedback baseResponseFeedback) {

                if (baseResponseFeedback.getData().size() > 0) {
                    listfeedback.clear();
                    listfeedback.addAll(baseResponseFeedback.getData());
                    layoutManagerfeedback = new LinearLayoutManager(ProductDetailActivity.this);
                    rclistfeedback.setLayoutManager(layoutManagerfeedback);
                    feedbackAdapter = new FeedbackAdapter(listfeedback);
                    rclistfeedback.setAdapter(feedbackAdapter);
                } else {
                }

            }
        });

        btnvietnhanxet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().postSticky(pro);
                startActivity(new Intent(ProductDetailActivity.this, CommentActivity.class));
                Animatoo.animateSplit(ProductDetailActivity.this);
            }
        });
    }

    public String format(double number) {
        NumberFormat formatter = new DecimalFormat("#,###,###");
        return formatter.format(number);
    }

}
