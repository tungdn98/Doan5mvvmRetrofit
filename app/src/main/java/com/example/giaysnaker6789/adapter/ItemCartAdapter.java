package com.example.giaysnaker6789.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.giaysnaker6789.BaseResponse.Billresponse;
import com.example.giaysnaker6789.R;
import com.example.giaysnaker6789.config.Constant;
import com.example.giaysnaker6789.config.Progressdialog;
import com.example.giaysnaker6789.models.bills;
import com.example.giaysnaker6789.network.RetrofitService;
import com.example.giaysnaker6789.viewModels.BillViewModel;
import com.example.giaysnaker6789.views.CartActivity;
import com.example.tungnuinumberone.TungNuiButton;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;


public class ItemCartAdapter extends BaseAdapter {
    ArrayList<bills> list;
    Context context;

    Progressdialog progressdialog = new Progressdialog();

    private BillViewModel billViewModel;
    private String type;
    public ItemCartAdapter(ArrayList<bills> list, Context context , String type) {
        this.list = list;
        this.context = context;
        this.type=type;
        billViewModel = ViewModelProviders.of((FragmentActivity) context).get(BillViewModel.class);
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

    private class viewholder {  // tạo 1 class viewholder đầy đủ các thuộc tính của file dongkhach.xml
        TextView txttensp;
        TextView txtorigin;
        TextView txtprice;
        TextView txtsoluong;
        TextView txtthanhtien;
        ImageView imagesp, imgdelete;
        TungNuiButton tungNuiButton;
    }


    @Override
    public View getView(int position, View itemView, ViewGroup parent) {
        viewholder holder;

        if (itemView == null) {
            holder = new viewholder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            itemView = inflater.inflate(R.layout.item_cart, null);
            holder.txttensp = itemView.findViewById(R.id.txttensp);
            holder.txtorigin = itemView.findViewById(R.id.txtorigin);
            holder.txtprice = itemView.findViewById(R.id.txtprice);
            holder.txtsoluong = itemView.findViewById(R.id.txtsoluong);
            holder.txtthanhtien = itemView.findViewById(R.id.txtthanhtien);
            holder.imgdelete = itemView.findViewById(R.id.imgdelete);
            holder.imagesp = itemView.findViewById(R.id.imghinhsp);
            holder.tungNuiButton = itemView.findViewById(R.id.number_button);

            itemView.setTag(holder);
        } else {
            holder = (viewholder) itemView.getTag();
        }

        bills currentpro = list.get(position);
        holder.txttensp.setText("Tên sp: "+currentpro.getName());
        holder.txtorigin.setText("Xuất sứ: "+currentpro.getOrigin());
        holder.txtsoluong.setText("số lượng: "+currentpro.getCount());
        holder.txtprice.setText("giá: " + format(currentpro.getPrice())+" VNĐ");
        holder.txtthanhtien.setText("Thành tiền: " + format(currentpro.getPrice() * currentpro.getCount())+" VNĐ");
        holder.tungNuiButton.setNumber("" + currentpro.getCount());
        Picasso.get()
                .load("" + RetrofitService.basePath + currentpro.getImage())
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.error)
                //.resize(150, 150)
                // .centerCrop()
                .into(holder.imagesp);

        if(type.equals(Constant.ORDER_DETAIL)){
            holder.tungNuiButton.setVisibility(View.GONE);
            holder.imgdelete.setVisibility(View.GONE);
        }



        holder.tungNuiButton.setOnClickListener(new TungNuiButton.OnClickListener() {
            @Override
            public void onClick(View view) {
                int number=Integer.parseInt(holder.tungNuiButton.getNumber());
                progressdialog.showDialog("đang update",context);
                int saled=0;
                if(number>currentpro.getCount()){
                    saled=1;
                }else {
                    saled=-1;
                }
              billViewModel.updateBill(currentpro.getId(),currentpro.getIdBill(),number,"b1",currentpro.getIdProduct(),saled).observe((LifecycleOwner) context, new Observer<Billresponse>() {
                  @Override
                  public void onChanged(Billresponse billresponse) {
                      currentpro.setCount(number);
                      CartActivity.listcac.set(position,currentpro);
                      CartActivity.adapterCart.notifyDataSetChanged();
                      CartActivity.tinhtongtien(CartActivity.listcac);
                      CartActivity.setcountcart(CartActivity.listcac);
                      progressdialog.dismissDialog();
                  }
              });
            }
        });

        holder.imgdelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressdialog.showDialog("đang xóa", context);
                delete(currentpro.getId(),currentpro.getIdBill(),currentpro);
            }
        });

        return itemView;
    }

    private void delete(int id, int idbill, bills bill) {
        billViewModel.deleteBill(id, idbill,bill.getIdProduct(),bill.getCount()).observe((LifecycleOwner) context, new Observer<Billresponse>() {
            @Override
            public void onChanged(Billresponse billresponse) {
                CartActivity.listcac.remove(bill);
                CartActivity.adapterCart.notifyDataSetChanged();
                CartActivity.tinhtongtien(CartActivity.listcac);
                CartActivity.setcountcart(CartActivity.listcac);
                progressdialog.dismissDialog();
            }
        });

    }

    public String format(double number) {
        NumberFormat formatter = new DecimalFormat("#,###,###");
        return formatter.format(number);
    }
}
