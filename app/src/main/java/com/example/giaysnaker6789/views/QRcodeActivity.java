package com.example.giaysnaker6789.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.widget.TextView;
import android.widget.Toast;

import com.example.giaysnaker6789.R;
import com.example.giaysnaker6789.models.products;
import com.example.giaysnaker6789.viewModels.ProductViewModel;
import com.google.zxing.Result;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;


import org.greenrobot.eventbus.EventBus;

import java.util.List;

import me.dm7.barcodescanner.zxing.ZXingScannerView;


public class QRcodeActivity extends BaseActivity implements ZXingScannerView.ResultHandler {
    private ZXingScannerView scannerView;
    ProductViewModel productViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_q_rcode);
        scannerView=findViewById(R.id.zxscan);
        productViewModel = ViewModelProviders.of(this).get(ProductViewModel.class);

        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.CAMERA
                       )
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {
                            scannerView.setResultHandler(QRcodeActivity.this);
                            scannerView.startCamera();

                        }
                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            Toast.makeText(QRcodeActivity.this, "bạn phải cấp quyền camera", Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                })
                .onSameThread()
                .check();


    }

    @Override
    protected void onDestroy() {
        scannerView.stopCamera();
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        scannerView.setResultHandler(QRcodeActivity.this);
        scannerView.startCamera();
        super.onResume();
    }

    @Override
    public void handleResult(Result rawResult) {
        EventBus.getDefault().postSticky(rawResult.getText());
        startActivity(new Intent(QRcodeActivity.this,ProductDetailActivity.class));
    }



}
