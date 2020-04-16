package com.example.giaysnaker6789.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.giaysnaker6789.R;
import com.example.giaysnaker6789.models.products;
import com.example.giaysnaker6789.network.RetrofitService;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

public class SpTrangchuAdapter extends RecyclerView.Adapter<SpTrangchuAdapter.ViewHolder> {
    private List<products> notes;
    private Context context;

    public SpTrangchuAdapter(List<products> notes, Context context) {
        this.notes = notes;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_product, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        products currentpro = notes.get(position);
        holder.txttitle.setText(currentpro.getName());
        holder.txtdescription.setText(currentpro.getDescribe());
        holder.txtgiachinh.setText(format(currentpro.getPrice()));
        holder.txtgiagiam.setText(format(currentpro.getPromotion()));
        Picasso.get()
                .load(""+ RetrofitService.basePath+currentpro.getImage())
                //.resize(150, 150)
               // .centerCrop()
                .into(holder.imagesp);
        holder.RTbar.setRating(currentpro.getRate());
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
         TextView txtdescription;
         TextView txttitle;
         TextView txtgiachinh;
         TextView txtgiagiam;
         ImageView imagesp;
         RatingBar RTbar;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txttitle = itemView.findViewById(R.id.txttitle);
            txtdescription = itemView.findViewById(R.id.txtdescription);
            txtgiachinh = itemView.findViewById(R.id.txtgiachinh);
            txtgiagiam = itemView.findViewById(R.id.txtgiagiam);
            imagesp = itemView.findViewById(R.id.imghinhsp);
            RTbar=itemView.findViewById(R.id.ratingBarproductp);

        }
    }


    public String format(double number){
        NumberFormat formatter = new DecimalFormat("#,###,###");
        return formatter.format(number);
    }
}