package com.example.giaysnaker6789.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.giaysnaker6789.R;
import com.example.giaysnaker6789.models.products;
import com.example.giaysnaker6789.network.RetrofitService;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

public class searchAdapter extends BaseAdapter {
    ArrayList<products> listproduct;
    Context context;

    public searchAdapter(ArrayList<products> listproduct, Context context) {
        this.listproduct = listproduct;
        this.context = context;
    }

    @Override
    public int getCount() {
        return listproduct.size();
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
        TextView txtdescription;
        TextView txttitle;
        TextView txtgiachinh;
        TextView txtgiagiam;
        ImageView imagesp;
        RatingBar rate;
    }

    @Override
    public View getView(int position, View itemView, ViewGroup parent) {
        viewholder holder;  // tạo 1 biến từ class viewholder

        if(itemView == null){
            holder=new viewholder();
            LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            itemView=inflater.inflate(R.layout.item_product,null);
            holder.txttitle = itemView.findViewById(R.id.txttitle);
            holder.txtdescription = itemView.findViewById(R.id.txtdescription);
            holder.txtgiachinh = itemView.findViewById(R.id.txtgiachinh);
            holder.txtgiagiam = itemView.findViewById(R.id.txtgiagiam);
            holder.imagesp = itemView.findViewById(R.id.imghinhsp);
            holder.rate=itemView.findViewById(R.id.ratingBarproductp);
            itemView.setTag(holder);
        }else{
            holder = (viewholder) itemView.getTag();
        }

        products currentpro=listproduct.get(position);
        holder.txttitle.setText(currentpro.getName());
        holder.txtdescription.setText(currentpro.getDescribe());
        holder.txtgiachinh.setText(format(currentpro.getPrice()));
        holder.txtgiagiam.setText(format(currentpro.getPromotion()));
        holder.rate.setRating(currentpro.getRate());
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
