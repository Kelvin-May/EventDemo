package com.yjk.eventdemo;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.yjk.eventdemo.base.BaseActivity;

public class MainActivity extends BaseActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private Button mCenterButton;
    private Button mRightButton;
    private Button mBottomButton;
    private View mCenterLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUI();
        initData();
    }

    private void initUI() {
        mCenterButton = (Button) findViewById(R.id.id_main_activity_btn_1);
        mRightButton = (Button) findViewById(R.id.id_main_activity_btn_2);
        mBottomButton = (Button) findViewById(R.id.id_main_activity_btn_3);
        mCenterLayout = findViewById(R.id.id_main_activity_layout);
    }

    private void initData() {
        mCenterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "mButton1 on Click");
            }
        });
        mRightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "mButton2 on Click");
            }
        });
        mBottomButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Log.d(TAG, "mButton3 on Click");
                startActivity(new Intent(MainActivity.this, StatisticsActivity.class));
            }
        });

        mCenterLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "mLayout on Click");
            }
        });
    }
}
