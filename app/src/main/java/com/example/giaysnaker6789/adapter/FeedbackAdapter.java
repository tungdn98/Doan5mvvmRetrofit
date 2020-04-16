package com.example.giaysnaker6789.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.giaysnaker6789.R;
import com.example.giaysnaker6789.models.feedback_products;
import com.example.giaysnaker6789.models.product_types;
import com.example.giaysnaker6789.network.RetrofitService;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class FeedbackAdapter extends RecyclerView.Adapter<FeedbackAdapter.ViewHolder>{
    public List<feedback_products> mItemList;

    public FeedbackAdapter(List<feedback_products> mItemList) {
        this.mItemList = mItemList;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_feedback, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        feedback_products fe=mItemList.get(position);
    holder.txtnameuser.setText(""+fe.getName());
    holder.txtcontent.setText(""+fe.getContent());
    holder.txtdate.setText(""+fe.getFeedDate());
        Picasso.get()
                .load(""+ RetrofitService.basePath+fe.getImagefb())
                //.resize(80, 80)
                // .centerCrop()
                .into(holder.imgprofile);
    }

    @Override
    public int getItemCount() {
        return mItemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtnameuser,txtcontent,txtdate;
        CircleImageView imgprofile;
        RatingBar rateingbar;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtnameuser = itemView.findViewById(R.id.txtnameuser);
            txtcontent = itemView.findViewById(R.id.txtcontent);
            txtdate = itemView.findViewById(R.id.txtdate);
            imgprofile = itemView.findViewById(R.id.profile_image);
            rateingbar=itemView.findViewById(R.id.ratingBarproduct);
        }
    }

}
