package com.txs.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.txs.R;
import com.txs.custom.CustomTitleActivity;

import butterknife.BindView;
import butterknife.ButterKnife;


public class NewsIntroduceActivity extends Activity {

    @BindView(R.id.newsIntroduce_top_title)
    CustomTitleActivity mTitleActivity;
    TextView txt_title;

    TextView item_txt_left_title, item_txt_brief;
    SimpleDraweeView item_sim_iv_pic;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_introduce);
        ButterKnife.bind(this);
        // mTitleActivity=new CustomTitleActivity(this);
        item_txt_left_title = (TextView) findViewById(R.id.item_txt_left_title);
        item_txt_brief = (TextView) findViewById(R.id.item_txt_brief);
        item_sim_iv_pic = (SimpleDraweeView) findViewById(R.id.item_sim_iv_pic);
        //头部标题
        txt_title = (TextView) findViewById(R.id.txt_title);
        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        String text = intent.getStringExtra("text");

        txt_title.setText(title);
        item_txt_left_title.setText(title);
        item_txt_brief.setText(text);
        item_sim_iv_pic.setImageURI("res:///" + R.mipmap.logo);
        initTopClickEvent();


    }

    //自定义标签的点击事件
    public void initTopClickEvent() {
        mTitleActivity.setBackListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mTitleActivity.setOrderListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(NewsIntroduceActivity.this, "点击了iv_icon_order", Toast.LENGTH_SHORT).show();
            }
        });

        mTitleActivity.setSearchListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(NewsIntroduceActivity.this, "点击了iv_right_search", Toast.LENGTH_SHORT).show();
            }
        });

    }

}
