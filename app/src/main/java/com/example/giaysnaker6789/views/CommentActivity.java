package com.example.giaysnaker6789.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.giaysnaker6789.R;
import com.example.giaysnaker6789.models.products;
import com.example.giaysnaker6789.models.user1s;
import com.example.giaysnaker6789.models.feedback_products;
import com.example.giaysnaker6789.network.RetrofitService;
import com.example.giaysnaker6789.viewModels.FeedbackViewModel;
import com.google.android.material.textfield.TextInputEditText;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CommentActivity extends BaseActivity {
        TextView txttensp;
        TextView txtpost;
        ImageView imghinhsp;
        RatingBar rateting;
        TextInputEditText edtcontent;

        feedback_products feedback=new feedback_products();
        FeedbackViewModel feedbackViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        feedbackViewModel = ViewModelProviders.of(this).get(FeedbackViewModel.class);
        initView();
        txtpost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date c = Calendar.getInstance().getTime();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String formattedDate = simpleDateFormat.format(c);
                getcontent();
                feedback.setFeedDate(formattedDate);
                feedbackViewModel.postFeedback(
                        feedback.getIdProduct(),
                        feedback.getIdUser(),
                        edtcontent.getText().toString(),
                        feedback.getFeedDate(),
                        rateting.getRating()).observe(CommentActivity.this, new Observer<Integer>() {
                    @Override
                    public void onChanged(Integer integer) {
                        if(integer==0){
                            Toast.makeText(CommentActivity.this, "Thành công", Toast.LENGTH_SHORT).show();
                            finish();
                        }else{
                            Toast.makeText(CommentActivity.this, "thất bại ", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                });
            }
        });
    }

    private void getcontent() {
        String content=edtcontent.getText().toString();
        if(content!=null){
            feedback.setContent(content);
        }else{
            Toast.makeText(this, "bạn phải nhập nội dung", Toast.LENGTH_SHORT).show();
        }
    }

    private void initView() {
        txttensp=findViewById(R.id.txttensanpham);
        txtpost=findViewById(R.id.txtpost);
        imghinhsp=findViewById(R.id.imghinhsp);
        rateting=findViewById(R.id.rateingpro);
        edtcontent=findViewById(R.id.edtcontent);
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onMessageEvent(user1s event) { // get model test
       feedback.setIdUser(event.getId());
       feedback.setName(event.getName());
    }
    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onMessageEvent(products event) { // get model test
        feedback.setIdProduct(event.getId());
        txttensp.setText(event.getName());
        Picasso.get()
                .load(""+ RetrofitService.basePath+event.getImage())
                .into(imghinhsp);
    }




}
