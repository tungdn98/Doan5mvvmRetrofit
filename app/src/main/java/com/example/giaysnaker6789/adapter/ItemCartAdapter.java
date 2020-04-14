package com.example.giaysnaker6789.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.giaysnaker6789.R;
import com.example.giaysnaker6789.models.products;
import com.example.giaysnaker6789.network.RetrofitService;
import com.example.tungnuinumberone.TungNuiButton;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

public class ItemCartAdapter extends BaseAdapter {
    ArrayList<products> list;
    Context context;

    public ItemCartAdapter(ArrayList<products> list, Context context) {
        this.list = list;
        this.context = context;
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
        TextView txtpromotion;
        TextView txtamount;
        ImageView imagesp;
        TungNuiButton tungNuiButton;
    }


    @Override
    public View getView(int position, View itemView, ViewGroup parent) {
        viewholder holder;  // tạo 1 biến từ class viewholder

        if(itemView == null){
            holder=new viewholder();
            LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            itemView=inflater.inflate(R.layout.item_product,null);
            holder.txttensp = itemView.findViewById(R.id.txttensp);
            holder.txtorigin = itemView.findViewById(R.id.txtorigin);
            holder.txtprice = itemView.findViewById(R.id.txtprice);
            holder.txtpromotion = itemView.findViewById(R.id.txtpromotion);
            holder.txtamount = itemView.findViewById(R.id.txtamount);

            holder.imagesp = itemView.findViewById(R.id.imghinhsp);
            holder.tungNuiButton = itemView.findViewById(R.id.number_button);

            itemView.setTag(holder);
        }else{
            holder = (viewholder) itemView.getTag();
        }

        products currentpro=list.get(position);
        holder.txttensp.setText(currentpro.getName());
        holder.txtorigin.setText(currentpro.getDescribe());
        holder.txtprice.setText(format(currentpro.getPrice()));
        holder.txtpromotion.setText(format(currentpro.getPromotion()));
        holder.txtamount.setText(currentpro.getDescribe());
        Picasso.get()
                .load(""+ RetrofitService.basePath+currentpro.getImage())
                //.resize(150, 150)
                // .centerCrop()
                .into(holder.imagesp);
        return itemView;
    }

    public String format(double number){
        NumberFormat formatter = new DecimalFormat("#,###,###");
        return formatter.format(number);
    }
}
