package com.txs.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.txs.base.MyItemClickListener;

import java.util.List;

import com.txs.R;
import com.txs.entity.Dingyue;
import com.txs.entity.News;
import com.txs.ui.LoginActivity;
import com.txs.ui.ZhuceActivity;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by lkpassword on 2017/7/5.
 */

public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {

    Context mContext;
    List<News> list;
    private MyItemClickListener mListener;

    public void setList(List<News> list) {
        this.list = list;
    }

    public MyAdapter(Context context, List<News> datas) {
        this.mContext = context;
        this.list = datas;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.classify_recylerview_item, parent, false);

        MyViewHolder viewHolder = new MyViewHolder(view, mListener);

        return viewHolder;
    }

    int status_dy = 1;

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.name.setText(list.get(position).getNewSource());
        holder.flag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String source=list.get(position).getNewSource();
                Dingyue dingyue=new Dingyue();
                dingyue.setName(source);
                dingyue.save(new SaveListener<String>() {
                    @Override
                    public void done(String objectId,BmobException e) {
                        /*if(e==null){
                            toast("订阅成功");
                        }else{
                            Log.i("error", e.getMessage());
                            toast("已经订阅过啦!");
                        }*/
                    }
                });
            }

        });
        if (holder.flag.isChecked()) {
            status_dy = 2;
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public void setOnItemClickListener(MyItemClickListener listener) {
        this.mListener = listener;
    }
}
