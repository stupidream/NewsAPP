package com.txs.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.txs.ClassifyActivity;
import com.txs.R;
import com.txs.custom.CustomTitleActivity;

public class OrderActivity extends Activity {

    CustomTitleActivity mTitleActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        mTitleActivity= (CustomTitleActivity) findViewById(R.id.custom_order);

        initTopClickEvent();
    }


    public void onClickEvent(View view){
        switch (view.getId()){
            case R.id.txt_order_all:

                break;
            case R.id.iv_order:
                Intent intent=new Intent(this, ClassifyActivity.class);
                startActivity(intent);
                break;
        }

    }

    public void initTopClickEvent(){

        mTitleActivity.setBackListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(OrderActivity.this,ClassifyActivity.class);;
                startActivity(intent);
            }
        });
        //点击“全部”的事件
        mTitleActivity.setOrderListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                * 在此处填写事件
                * */
            }
        });



    }
}
