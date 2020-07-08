package com.example.giaysnaker6789.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.example.giaysnaker6789.BaseResponse.BillUserResponse;
import com.example.giaysnaker6789.BaseResponse.Billresponse;
import com.example.giaysnaker6789.R;
import com.example.giaysnaker6789.adapter.ItemCartAdapter;
import com.example.giaysnaker6789.config.Constant;
import com.example.giaysnaker6789.config.Progressdialog;
import com.example.giaysnaker6789.config.SharedPref;
import com.example.giaysnaker6789.models.bills;
import com.example.giaysnaker6789.models.billuser;
import com.example.giaysnaker6789.models.user1s;
import com.example.giaysnaker6789.viewModels.BillUserViewModel;
import com.example.giaysnaker6789.viewModels.BillViewModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

import androidx.cardview.widget.CardView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

public class CartActivity extends BaseActivity {
    static ListView lv;
    CardView cardView;
    static TextView txttitle;
    static TextView txtthanhtien;
    Button btnpay;
    ImageView imgclose;
    public static ArrayList<bills> listcac = new ArrayList<>();
    public static ItemCartAdapter adapterCart;
    private BillUserViewModel billUserViewModel;
    private BillViewModel billViewModel;
    user1s user;
    billuser mbilluser;

    static int thanhtien = 0;

    Progressdialog progressdialog = new Progressdialog();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        initview();
        int id = SharedPref.read(SharedPref.IDUSER, 1);
        getcountCart(id);


        btnpay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(listcac!=null){
                    progressdialog.showDialog("đang tiến hành đặt hàng ",CartActivity.this);
                    billViewModel.orDerBill(mbilluser.getId(),"b1").observe(CartActivity.this, new Observer<Billresponse>() {
                        @Override
                        public void onChanged(Billresponse billresponse) {
                            if(billresponse.getMess().equals(Constant.STATUS_SUCCESS)){
                                // update lại product
                                ArrayList<bills> templist= listcac;
                               updateproduct(templist);

                                EventBus.getDefault().removeStickyEvent(mbilluser);

                                txttitle.setText("giỏ hàng (0)");
                                setemtyview();
                                listcac.clear();
                                adapterCart.notifyDataSetChanged();
                                progressdialog.dismissDialog();
                            }

                        }
                    });
                }else{
                    Toast.makeText(CartActivity.this, "giỏ hàng trống !!!!!!", Toast.LENGTH_SHORT).show();
                }

            }
        });

        imgclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void updateproduct(ArrayList<bills> listcac) {
       for (int i=0;i<listcac.size();i++){
           billViewModel.updateCountProduct(listcac.get(i).getIdProduct(),listcac.get(i).getCount());
       }
    }

    private void getcountCart(int iduser) {
        progressdialog.showDialog("đang load chờ tý", CartActivity.this);
        billUserViewModel.getcountbill(iduser, "b1").observe(this, new Observer<BillUserResponse>() {
            @Override
            public void onChanged(BillUserResponse billUserResponse) {
                if (billUserResponse.getMess().equals("SUCCESS")) {
                    txttitle.setText("giỏ hàng (" + billUserResponse.getData().get(0).getCount() + ")");
                    progressdialog.dismissDialog();
                } else {
                    txttitle.setText("giỏ hàng (0)");
                    //setemtyview();
                    listcac.clear();
                    adapterCart.notifyDataSetChanged();
                    setemtyview();
                    progressdialog.dismissDialog();
                }
            }
        });
    }

    private void getallcart(int idBill) {
        progressdialog.showDialog("đang load chờ tý", this);
        billViewModel.getBill(idBill).observe(this, new Observer<Billresponse>() {
            @Override
            public void onChanged(Billresponse billresponse) {
                listcac.clear();
                listcac.addAll(billresponse.getData());
                if (listcac.size() > 0) {
                    cardView.setVisibility(View.VISIBLE);
                    adapterCart.notifyDataSetChanged();
                    tinhtongtien(listcac);
                    progressdialog.dismissDialog();
                }else{
                    setemtyview();
                    progressdialog.dismissDialog();
                }
            }
        });
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onMessageEvent(user1s event) { // get model test
        user = event;
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onMessageEvent(billuser event) { // get model test
        mbilluser = event;
        getallcart(mbilluser.getId());
    }

    public static void tinhtongtien(ArrayList<bills> listcac) {
        int dem = 0;
        thanhtien = 0;
        for (int i = 0; i < listcac.size(); i++) {
            dem += listcac.get(i).getCount() * listcac.get(i).getPrice();
            thanhtien = dem;
        }
        txtthanhtien.setText("" + format(dem)+" đ");
    }

    public static void setcountcart(ArrayList<bills> listcac) {
        int dem = 0;
        for (int i = 0; i < listcac.size(); i++) {
            dem += 1;
        }
        if (dem > 0) {
            txttitle.setText("giỏ hàng (" + dem + ")");
        } else {
            txttitle.setText("giỏ hàng (" + dem + ")");
            // setemtyview();

        }

    }

    public static String format(double number) {
        NumberFormat formatter = new DecimalFormat("#,###,###");
        return formatter.format(number);
    }

    private void initview() {
        SharedPref.init(getApplicationContext());
        btnpay = findViewById(R.id.btnpay);
        billUserViewModel = ViewModelProviders.of(this).get(BillUserViewModel.class);
        billViewModel = ViewModelProviders.of(this).get(BillViewModel.class);
        lv = findViewById(R.id.lvcart);
        adapterCart = new ItemCartAdapter(listcac, CartActivity.this,"");
        lv.setAdapter(adapterCart);
        cardView = findViewById(R.id.carview);

        txttitle = findViewById(R.id.txtsoluong);
        txtthanhtien = findViewById(R.id.txtthanhtien);
        imgclose = findViewById(R.id.imgclose);
    }


    Button btntieptucmuahang;

    private void setemtyview() {
        View view = getLayoutInflater().inflate(R.layout.emty_view, null);
        ViewGroup viewGroup = (ViewGroup) lv.getParent();


        btntieptucmuahang = view.findViewById(R.id.btntieptucmuahang);
        viewGroup.addView(view);
        lv.setEmptyView(view);
        cardView.setVisibility(view.GONE);

        btntieptucmuahang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CartActivity.this, MainActivity.class));
                Animatoo.animateSlideDown(CartActivity.this);
            }
        });
    }


}
