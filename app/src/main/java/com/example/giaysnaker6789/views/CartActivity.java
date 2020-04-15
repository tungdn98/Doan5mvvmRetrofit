package com.example.giaysnaker6789.views;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.example.giaysnaker6789.R;
import com.example.giaysnaker6789.adapter.ItemCartAdapter;
import com.example.giaysnaker6789.roommodel.Cart;
import com.example.giaysnaker6789.roommodel.CartViewModel;

import org.greenrobot.eventbus.EventBus;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class CartActivity extends BaseActivity {
    ListView lv;
    CardView cardView;
    TextView txttitle,txtthanhtien;
    ImageView imgclose;

    ArrayList<Cart> listcac=new ArrayList<>();
    ItemCartAdapter adapterCart;


    private CartViewModel cartViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        cartViewModel = ViewModelProviders.of(CartActivity.this).get(CartViewModel.class);
        initview();
        getcountCart();
        imgclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void getcountCart() {
        cartViewModel.getcount().observe(CartActivity.this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                if(integer>0){
                    txttitle.setText("Giỏ hàng ("+integer+")");
                    getallcart();
                }else{
                    txttitle.setText("Giỏ hàng (0)");
                    setemtyview();
                }
            }
        });
    }

    private void getallcart() {
        cartViewModel.getAllNotes().observe(CartActivity.this, new Observer<List<Cart>>() {
            @Override
            public void onChanged(List<Cart> carts) {
                listcac=new ArrayList<>(carts);
                if(listcac.size()>0){
                    cardView.setVisibility(View.VISIBLE);
                    adapterCart = new ItemCartAdapter(listcac,CartActivity.this);
                    lv.setAdapter(adapterCart);
                    tinhtongtien(listcac);
                }
            }
        });
    }

    public  void tinhtongtien(ArrayList<Cart> listcac) {
        int dem=0;
        for (int i=0;i<listcac.size();i++){
            dem+=listcac.get(i).getThanhtien();
        }
        txtthanhtien.setText(""+format(dem));
    }
    public String format(double number){
        NumberFormat formatter = new DecimalFormat("#,###,###");
        return formatter.format(number);
    }

    private void initview() {
        lv = findViewById(R.id.lvcart);

        cardView = findViewById(R.id.carview);
        txttitle=findViewById(R.id.txtsoluong);
        txtthanhtien=findViewById(R.id.txtthanhtien);
        imgclose=findViewById(R.id.imgclose);
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
               startActivity(new Intent(CartActivity.this,MainActivity.class));
                Animatoo.animateSlideDown(CartActivity.this);
            }
        });
    }
}
