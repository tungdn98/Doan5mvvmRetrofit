package com.example.giaysnaker6789.config;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.example.giaysnaker6789.BaseResponse.Billresponse;
import com.example.giaysnaker6789.R;
import com.example.giaysnaker6789.adapter.ItemCartAdapter;
import com.example.giaysnaker6789.models.bills;
import com.example.giaysnaker6789.viewModels.BillViewModel;
import com.example.giaysnaker6789.views.ProductDetailActivity;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

public class CustomDialog extends Dialog implements
        android.view.View.OnClickListener{
    public Activity mactivity;
    public Dialog dialog;
    public Button btnok;
    public ListView lv;
    public ItemCartAdapter mitemCartAdapter;
    ArrayList<bills> listbill=new ArrayList<>();
    private BillViewModel mbillViewModel;
    public int idbill=0;

    public CustomDialog(@NonNull Activity context ,int idbill) {
        super(context);
        this.mactivity=context;
        this.idbill=idbill;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_detail_cart);
        btnok=findViewById(R.id.btnok);
        lv=findViewById(R.id.lv_detail_cart);
        mbillViewModel = ViewModelProviders.of((FragmentActivity) mactivity).get(BillViewModel.class);
        btnok.setOnClickListener(this);
        populatedata(idbill);
        ListviewClick();
    }

    private void ListviewClick() {
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                EventBus.getDefault().postSticky(""+listbill.get(i).getIdProduct());
                mactivity.startActivity(new Intent(mactivity, ProductDetailActivity.class));
            }
        });
    }

    private void populatedata(int idbill) {
    mbillViewModel.getBillWithID(idbill).observe((LifecycleOwner) mactivity, new Observer<Billresponse>() {
        @Override
        public void onChanged(Billresponse billresponse) {
            if(billresponse.getStatus().equals(Constant.STATUS_SUCCESS)){
                listbill.addAll(billresponse.getData());
                mitemCartAdapter =new ItemCartAdapter(listbill,mactivity,Constant.ORDER_DETAIL);
                lv.setAdapter(mitemCartAdapter);
            }
        }
    });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnok:
               // code
                dismiss();
                break;
            default:
                break;
        }
        dismiss();
    }

}
