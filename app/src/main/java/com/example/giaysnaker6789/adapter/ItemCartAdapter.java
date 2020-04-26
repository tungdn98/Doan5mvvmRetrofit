package com.example.giaysnaker6789.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.giaysnaker6789.BaseResponse.Billresponse;
import com.example.giaysnaker6789.R;
import com.example.giaysnaker6789.config.Progressdialog;
import com.example.giaysnaker6789.models.bills;
import com.example.giaysnaker6789.models.products;
import com.example.giaysnaker6789.network.RetrofitService;
import com.example.giaysnaker6789.roommodel.Cart;
import com.example.giaysnaker6789.roommodel.CartViewModel;
import com.example.giaysnaker6789.viewModels.BillViewModel;
import com.example.giaysnaker6789.views.CartActivity;
import com.example.tungnuinumberone.TungNuiButton;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;


public class ItemCartAdapter extends BaseAdapter {
    ArrayList<bills> list;
    Context context;

    Progressdialog progressdialog=new Progressdialog();

    private BillViewModel billViewModel;

    public ItemCartAdapter(ArrayList<bills> list, Context context) {
        this.list = list;
        this.context = context;
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

        bills currentpro=list.get(position);
        holder.txttensp.setText(currentpro.getNameproduct());
        holder.txtorigin.setText(currentpro.getOriginproduct());
        holder.txtprice.setText(""+format(currentpro.getPrice()));
        holder.txtthanhtien.setText(""+currentpro.getPrice()*currentpro.getCount());
        holder.tungNuiButton.setNumber(""+currentpro.getCount());
        Picasso.get()
                .load(""+ RetrofitService.basePath+currentpro.getImageproduct())
                //.resize(150, 150)
                // .centerCrop()
                .into(holder.imagesp);

        holder.imgdelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressdialog.showDialog("đang xóa",context);
               // delete(currentpro.getIdUser(),currentpro.getIdProduct(),currentpro);
            }
        });

//        holder.tungNuiButton.setOnClickListener(new TungNuiButton.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                int number=Integer.parseInt(holder.tungNuiButton.getNumber());
//                progressdialog.showDialog("đang update",context);
//              billViewModel.updateBill(currentpro.getIdUser(),currentpro.getIdProduct(),number,"b1").observe((LifecycleOwner) context, new Observer<Billresponse>() {
//                  @Override
//                  public void onChanged(Billresponse billresponse) {
//                      currentpro.setCount(number);
//                      CartActivity.listcac.set(position,currentpro);
//                      CartActivity.adapterCart.notifyDataSetChanged();
//                      CartActivity.tinhtongtien(CartActivity.listcac);
//                      CartActivity.setcountcart(CartActivity.listcac);
//                      progressdialog.dismissDialog();
//                  }
//              });
//            }
//        });
        return itemView;
    }

    private void delete(int iduser,int idproduct,bills bill){
   billViewModel.deleteBill(iduser,idproduct).observe((LifecycleOwner) context, new Observer<Integer>() {
       @Override
       public void onChanged(Integer integer) {
           CartActivity.listcac.remove(bill);
           CartActivity.adapterCart.notifyDataSetChanged();
           CartActivity.tinhtongtien(CartActivity.listcac);
           CartActivity.setcountcart(CartActivity.listcac);
           progressdialog.dismissDialog();
       }
   });

    }

    public String format(double number){
        NumberFormat formatter = new DecimalFormat("#,###,###");
        return formatter.format(number);
    }
}
