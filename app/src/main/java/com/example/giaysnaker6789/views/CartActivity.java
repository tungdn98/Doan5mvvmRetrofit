package com.example.giaysnaker6789.views;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.giaysnaker6789.R;
import com.example.giaysnaker6789.roommodel.Cart;
import com.example.giaysnaker6789.roommodel.CartViewModel;

import java.util.List;

public class CartActivity extends BaseActivity {
    ListView lv;

    CardView cardView;


    private CartViewModel cartViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        cartViewModel = ViewModelProviders.of(CartActivity.this).get(CartViewModel.class);
        initview();
        getallcart();
        setemtyview();

        cartViewModel.getcount().observe(CartActivity.this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                Toast.makeText(CartActivity.this, "hmm "+integer, Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void getallcart() {
        cartViewModel.getAllNotes().observe(CartActivity.this, new Observer<List<Cart>>() {
            @Override
            public void onChanged(List<Cart> carts) {
                Log.d("ca", "onChanged: "+carts);
            }
        });
    }

    private void initview() {
        lv = findViewById(R.id.lvcart);
        cardView = findViewById(R.id.carview);
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
                Cart cart=new Cart(
                        123,"123",123,"123",
                        "123",123,123,123,
                        "123","123");
                cartViewModel.insert(cart);
            }
        });
    }
}
