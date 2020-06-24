package com.example.giaysnaker6789.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.giaysnaker6789.R;
import com.example.giaysnaker6789.models.product_types;
import com.example.giaysnaker6789.network.RetrofitService;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ProductTypeAdapter extends RecyclerView.Adapter<ProductTypeAdapter.ViewHolder> {
    private List<product_types> notes;
    private Context context;

    public ProductTypeAdapter(List<product_types> notes, Context context) {
        this.notes = notes;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_product_type, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        product_types currentpro = notes.get(position);
        holder.txtdescription.setText(currentpro.getName());
        Picasso.get()
                .load(""+ RetrofitService.basePath+currentpro.getImage())
                //.resize(150, 150)
                // .centerCrop()
                .into(holder.imagesp);
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtdescription;
        ImageView imagesp;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtdescription = itemView.findViewById(R.id.txttenloaisp);
            imagesp = itemView.findViewById(R.id.imghinhloaisp);
        }
    }

}