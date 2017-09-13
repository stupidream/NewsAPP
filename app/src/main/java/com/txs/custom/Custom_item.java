package com.txs.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.txs.R;
/**
 * Created by lkpassword on 2017/7/7.
 */

public class Custom_item extends LinearLayout {
    TextView item_txt_left_title,item_txt_brief;
    SimpleDraweeView item_sim_iv_pic;
    CheckBox item_ch_order;

    public Custom_item(Context context) {
        super(context);
    }

    public Custom_item(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);


    }

    public Custom_item(Context context, AttributeSet attrs) {
        super(context, attrs);

        initView(context, attrs);
    }



    public  void initView( Context context, AttributeSet attributeSet){
        LayoutInflater.from(context).inflate(R.layout.custom_item,this);
        item_txt_left_title= (TextView) findViewById(R.id.item_txt_left_title);
        item_txt_brief= (TextView) findViewById(R.id.item_txt_brief);
        item_ch_order= (CheckBox) findViewById(R.id.item_ch_order);
        // item_sim_iv_pic= (SimpleDraweeView) findViewById(R.id.item_sim_iv_pic);

        if(attributeSet!=null){
            TypedArray ta=context.obtainStyledAttributes(attributeSet,R.styleable.Custom_item);
            item_txt_left_title.setText(ta.getText(R.styleable.Custom_item_title_item));
            item_txt_brief.setText(ta.getText(R.styleable.Custom_item_brief));
            item_ch_order.setChecked(true);
        }




    }
}
