package com.txs.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.txs.R;
import com.txs.base.MyItemClickListener;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lkpassword on 2017/7/5.
 */

public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    @BindView(R.id.txt_left_title)
    public TextView name;

    @BindView(R.id.txt_brief)
    public TextView text;

    @BindView(R.id.sim_iv_pic)
    public SimpleDraweeView pic;

    @BindView(R.id.ch_order)
    public CheckBox flag;

    private MyItemClickListener mListener;

    public MyViewHolder(final View itemView, MyItemClickListener listener) {
        super(itemView);
        ButterKnife.bind(this, itemView);

        this.mListener = listener;
        itemView.setOnClickListener(this);


     /*   flag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(itemView.getContext(), "点击了订阅按钮", Toast.LENGTH_SHORT).show();
            }
        });*/


    }

    //为实现点击item
    @Override
    public void onClick(View v) {
        if (mListener != null) {
            mListener.onItemClick(v, getPosition());
        }

    }
}
