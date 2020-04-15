package com.example.giaysnaker6789.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;

import com.example.giaysnaker6789.R;
import com.example.giaysnaker6789.models.products;
import com.example.giaysnaker6789.network.RetrofitService;
import com.example.giaysnaker6789.roommodel.Cart;
import com.example.giaysnaker6789.roommodel.CartViewModel;
import com.example.giaysnaker6789.views.CartActivity;
import com.example.tungnuinumberone.TungNuiButton;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;


public class ItemCartAdapter extends BaseAdapter {
    ArrayList<Cart> list;
    Context context;

    private CartViewModel cartViewModel;

    public ItemCartAdapter(ArrayList<Cart> list, Context context) {
        this.list = list;
        this.context = context;
        cartViewModel = ViewModelProviders.of((FragmentActivity) context).get(CartViewModel.class);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    private class viewholder{  // tạo 1 class viewholder đầy đủ các thuộc tính của file dongkhach.xml
        TextView txttensp;
        TextView txtorigin;
        TextView txtprice;
        TextView txtsoluong;
        TextView txtthanhtien;
        ImageView imagesp,imgdelete;
        TungNuiButton tungNuiButton;
    }


    @Override
    public View getView(int position, View itemView, ViewGroup parent) {
        viewholder holder;  // tạo 1 biến từ class viewholder

        if(itemView == null){
            holder=new viewholder();
            LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            itemView=inflater.inflate(R.layout.item_cart,null);
            holder.txttensp = itemView.findViewById(R.id.txttensp);
            holder.txtorigin = itemView.findViewById(R.id.txtorigin);
            holder.txtprice = itemView.findViewById(R.id.txtprice);
            holder.txtsoluong = itemView.findViewById(R.id.txtsoluong);
            holder.txtthanhtien = itemView.findViewById(R.id.txtthanhtien);
            holder.imgdelete=itemView.findViewById(R.id.imgdelete);
            holder.imagesp = itemView.findViewById(R.id.imghinhsp);
            holder.tungNuiButton = itemView.findViewById(R.id.number_button);

            itemView.setTag(holder);
        }else{
            holder = (viewholder) itemView.getTag();
        }

        Cart currentpro=list.get(position);
        holder.txttensp.setText(currentpro.getTensp());
        holder.txtorigin.setText(currentpro.getXuatsu());
        holder.txtprice.setText(""+format(currentpro.getGiadagiam()));
        holder.txtthanhtien.setText(""+currentpro.getThanhtien());
        holder.tungNuiButton.setNumber(""+currentpro.getSoluong());
        Picasso.get()
                .load(""+ RetrofitService.basePath+currentpro.getHinhanh())
                //.resize(150, 150)
                // .centerCrop()
                .into(holder.imagesp);

        holder.imgdelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete(currentpro);
            }
        });

        holder.tungNuiButton.setOnClickListener(new TungNuiButton.OnClickListener() {
            @Override
            public void onClick(View view) {
                int number=Integer.parseInt(holder.tungNuiButton.getNumber());
                currentpro.setSoluong(number);
                currentpro.setThanhtien(number*currentpro.getGiadagiam());
                cartViewModel.update(currentpro);
            }
        });


        return itemView;
    }

    private void delete(Cart cart){
    cartViewModel.delete(cart);
    list.remove(cart);
    }

    public String format(double number){
        NumberFormat formatter = new DecimalFormat("#,###,###");
        return formatter.format(number);
    }
}
