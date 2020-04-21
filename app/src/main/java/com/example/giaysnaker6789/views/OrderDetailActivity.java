package com.example.giaysnaker6789.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Bundle;

import com.example.giaysnaker6789.R;
import com.shuhart.stepview.StepView;

import java.util.ArrayList;

public class OrderDetailActivity extends AppCompatActivity {
StepView stepView;
int step1=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        initview();

        stepView.go(1, true);
        stepView.setOnStepClickListener(new StepView.OnStepClickListener() {
            @Override
            public void onStepClick(int step) {
                stepView.go(step, true);
               // stepView.done(true);
            }
        });
    }

    private void initview() {
        stepView=findViewById(R.id.step_view);
        stepView.getState()
                .selectedTextColor(ContextCompat.getColor(this, R.color.colorAccent))
                .animationType(StepView.ANIMATION_ALL)
                .selectedCircleColor(ContextCompat.getColor(this, R.color.colorAccent))
                .selectedCircleRadius(getResources().getDimensionPixelSize(R.dimen.dp14))
                .selectedStepNumberColor(ContextCompat.getColor(this, R.color.colorPrimary))
                // You should specify only stepsNumber or steps array of strings.
                // In case you specify both steps array is chosen.
                .steps(new ArrayList<String>() {{
                    add("First step");
                    add("Second step");
                    add("Third step");
                }})
                // You should specify only steps number or steps array of strings.
                // In case you specify both steps array is chosen.
                .stepsNumber(4)
                .animationDuration(getResources().getInteger(android.R.integer.config_shortAnimTime))
                .stepLineWidth(getResources().getDimensionPixelSize(R.dimen.dp1))
                .textSize(getResources().getDimensionPixelSize(R.dimen.sp14))
                .stepNumberTextSize(getResources().getDimensionPixelSize(R.dimen.sp16))
               // .typeface(ResourcesCompat.getFont(context, R.font.roboto_italic))
                // other state methods are equal to the corresponding xml attributes
                .commit();
    }
}
