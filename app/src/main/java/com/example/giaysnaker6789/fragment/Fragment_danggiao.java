package com.example.giaysnaker6789.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.giaysnaker6789.BaseResponse.bills2BaseResponse;
import com.example.giaysnaker6789.R;
import com.example.giaysnaker6789.adapter.billUserAdapter;
import com.example.giaysnaker6789.config.CustomDialog;
import com.example.giaysnaker6789.config.SharedPref;
import com.example.giaysnaker6789.models.bills2;
import com.example.giaysnaker6789.viewModels.BillUserViewModel;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

public class Fragment_danggiao extends Fragment {
    ArrayList<bills2> list = new ArrayList<>();
    billUserAdapter billUserAdapter;
    ListView lvdangigao;
    private BillUserViewModel billUserViewModel;

    String stt;

    public Fragment_danggiao(String stt) {
        this.stt = stt;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_danggiao, container, false);

        Toast.makeText(getActivity(), ""+stt, Toast.LENGTH_SHORT).show();
        billUserViewModel = ViewModelProviders.of(getActivity()).get(BillUserViewModel.class);
        lvdangigao = rootView.findViewById(R.id.lvdangigao);
        billUserAdapter=new billUserAdapter(list,getActivity());
        lvdangigao.setAdapter(billUserAdapter);

        SharedPref.init(getActivity());
        int us = SharedPref.read(SharedPref.IDUSER, 0);

        populatedata(us);

        lvdangigao.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                CustomDialog cdd=new CustomDialog(getActivity(),list.get(i).getId());
                cdd.show();
            }
        });

        return rootView;
    }

    private void populatedata(int us) {
        billUserViewModel.getbilldetail(us,stt).observe(this, new Observer<bills2BaseResponse>() {
            @Override
            public void onChanged(bills2BaseResponse bills2BaseResponse) {

                   list.addAll(bills2BaseResponse.getData());
                   billUserAdapter.notifyDataSetChanged();

            }
        });
    }

//    @Override
//    public void onStart() {
//        if (!EventBus.getDefault().isRegistered(getActivity())) {
//            EventBus.getDefault().register(getActivity());
//        }
//        super.onStart();
//    }
//
//    @Override
//    public void onStop() {
//        EventBus.getDefault().unregister(getActivity());
//        super.onStop();
//    }
//
//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void onMessageEvent(billuser event) {
//
//    }
}
