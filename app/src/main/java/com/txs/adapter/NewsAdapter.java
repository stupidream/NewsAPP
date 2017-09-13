package com.txs.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.txs.R;
import com.txs.base.NewsItemListener;
import com.txs.entity.News;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ZHAO on 2017/7/7.
 */

public class NewsAdapter  extends RecyclerView.Adapter<ViewHolder> implements View.OnClickListener{

    private List<News> list;
    private Context mContext;
    private NewsItemListener newsItemListener=null;
    private static final int TYPE_ITEM = 0;
    private static final int TYPE_FOOTER = 1;


    public void setList(List<News> list) {
        this.list = list;
    }

    public NewsAdapter(List<News> list, Context context) {
        this.list = list;
        this.mContext = context;
    }

    public int getItemViewType(int position) {
        if (position + 1 == getItemCount()) {
            return TYPE_FOOTER;
        } else {
            return TYPE_ITEM;
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_list, parent,false);
            view.setOnClickListener(this);
            return new NewsViewHolder(view);
        } else if (viewType == TYPE_FOOTER) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_footer, parent,false);
            return new FootViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        if (holder instanceof NewsViewHolder) {
            ((NewsViewHolder) holder).newSource.setText(list.get(position).getNewSource());
            ((NewsViewHolder) holder).newTitle.setText(list.get(position).getTitle());
            Uri uri=Uri.parse(list.get(position).getNewsImageUrl());
            ((NewsViewHolder) holder).newPic.setImageURI(uri);
            ((NewsViewHolder) holder).newTime.setText(list.get(position).getNewsTime());
            ((NewsViewHolder) holder).itemView.setTag(position);
            /*((NewsViewHolder) holder).checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.i("收藏的新闻",list.get(position).getNewsId());
                }
            });*/
        }
    }

    @Override
    public int getItemCount() {
        return list==null ?0:list.size();
    }

    @Override
    public void onClick(View view) {
        if (newsItemListener!=null){
            newsItemListener.onItemClick(view, (int) view.getTag());
        }
    }

    public void setOnItemClickListener(NewsItemListener listener) {
        this.newsItemListener = listener;
    }

    static class FootViewHolder extends ViewHolder {

        public FootViewHolder(View view) {
            super(view);
        }
    }

    static class NewsViewHolder extends ViewHolder{

        @BindView(R.id.newSource)
        public TextView newSource;

        @BindView(R.id.newTitle)
        public TextView newTitle;

        @BindView(R.id.newPic)
        public SimpleDraweeView newPic;

        @BindView(R.id.newTime)
        public TextView newTime;

        /*@BindView(R.id.collect)
        public CheckBox checkBox;*/

        public NewsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
