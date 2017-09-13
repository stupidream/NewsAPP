package com.txs.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.txs.NewsDetail;
import com.txs.R;
import com.txs.adapter.NewsAdapter;
import com.txs.base.NewsItemListener;
import com.txs.entity.News;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ZHAO on 2017/7/9.
 */

public class NewsListFragment extends Fragment {
    @BindView(R.id.newsRefresh)
    SwipeRefreshLayout refreshLayout;

    @BindView(R.id.newItem)
    RecyclerView recyclerView;

    public static final int SHOW_RESPONSE = 0;
    private NewsAdapter newsAdapter;
    private List<News> newsList=new ArrayList<>();
    private int page=0;
    private Handler handler1 = new Handler();

    private Activity activity;
    private String sort;
    private View view;
    private boolean isFirst=true;
    private int Listsize;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public static Fragment getInstance(String sort){
        NewsListFragment listFragment=new NewsListFragment();
        Bundle bundle=new Bundle();
        bundle.putString("sort",sort);
        listFragment.setArguments(bundle);
        return listFragment;
    }

    public NewsListFragment() {
    }

    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()){
            getNews(page);
        }
    }

    public NewsListFragment(String sort) {
        this.sort=sort;
    }

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view==null){
            view=inflater.inflate(R.layout.news_list_view,container,false);
        }
        ButterKnife.bind(this,view);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(activity);
        recyclerView.setLayoutManager(linearLayoutManager);

        initEvent();
        activity=getActivity();

        return view;
    }

    public void getNews(final int nPage){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    List<News> list1=new ArrayList<>();
                    String url="http://3g.163.com/touch/reconstruct/article/list/"+sort+"/"+nPage+"-10.html";
                    Document dc = Jsoup.connect(url).userAgent("Mozilla/5.0 (Linux; U; Android 4.3; en-us; SM-N900T Build/JSS15J) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30")
                            .ignoreContentType(true).get();
                    if (dc!=null){
                        Message message = new Message();
                        message.what = SHOW_RESPONSE;
                        message.obj = dc;
                        handler.sendMessage(message);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            Document dc = (Document) msg.obj;
            switch (msg.what) {
                case SHOW_RESPONSE:
                    String text=dc.body().text().substring(9);
                    String newsJson=text.substring(0,text.length()-1);
                    JsonParser jsonParser=new JsonParser();
                    JsonObject jsonObject= (JsonObject) jsonParser.parse(newsJson);
                    JsonArray newLists=jsonObject.get(sort).getAsJsonArray();
                    for (int i=0;i<newLists.size();i++){
                        News news=new News();
                        JsonObject newsInfo=newLists.get(i).getAsJsonObject();
                        String newsId=newsInfo.get("docid").getAsString();
                        if (isExist(newsId)){
                            news.setTitle(newsInfo.get("title").getAsString());
                            String urltext=newsInfo.get("url").getAsString();
                            String url=urltext.replaceAll("\\d{2}\\/\\d{4}\\/\\d{2}","article");
                            news.setNewsId(newsInfo.get("docid").getAsString());
                            news.setNewsUrl(url);
                            news.setNewsImageUrl(newsInfo.get("imgsrc").getAsString());
                            news.setNewSource(newsInfo.get("source").getAsString().trim());
                            String dateString=newsInfo.get("ptime").getAsString();
                            DateFormat format1=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            DateFormat format2=new SimpleDateFormat("dd");
                            DateFormat format3=new SimpleDateFormat("MM月dd日 HH:mm");
                            String date=format1.format(new Date());
                            try {
                                Date newsDate=format1.parse(dateString);
                                int date1=Integer.parseInt(format2.format(newsDate));
                                int date2=Integer.parseInt(format2.format(new Date()));
                                if (date2-date1>0){
                                    news.setNewsTime(format3.format(newsDate));
                                }else {
                                    long longTime=System.currentTimeMillis()-newsDate.getTime();
                                    long days = longTime / (1000 * 60 * 60 * 24);
                                    long hours = (longTime-days*(1000 * 60 * 60 * 24))/(1000* 60 * 60);
                                    long minutes = (longTime-days*(1000 * 60 * 60 * 24)-hours*(1000* 60 * 60))/(1000* 60);
                                    if (hours>0){
                                        news.setNewsTime(hours+"小时前");
                                    }else if (minutes>0){
                                        news.setNewsTime(minutes+"分钟前");
                                    }else if (minutes<1){
                                        news.setNewsTime("刚刚");
                                    }
                                }
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            newsList.add(news);
                        }
                    }
                    bindNews();
                    break;
            }
        }
    };

    public boolean isExist(String newsId){
        boolean flag=true;
        for (News news:newsList){
            if (newsId.equals(news.getNewsId())){
                flag=false;
            }
        }
        return flag;
    }

    public void bindNews(){
        if(isFirst){  //第一次加载
            isFirst=false;
            Listsize=newsList.size();
            page++;
            newsAdapter=new NewsAdapter(newsList,activity);
            recyclerView.setAdapter(newsAdapter);
        }else {
            newsAdapter.setList(newsList);
            newsAdapter.notifyDataSetChanged();
            if (newsList.size()>Listsize){ //防止胡乱添加新闻
                Listsize=newsList.size();
                page++;
            }
        }

        refreshLayout.setRefreshing(false);

        newsAdapter.setOnItemClickListener(new NewsItemListener() {
            @Override
            public void onItemClick(View view, int position) {
                startMyActivity(view,position);
            }
        });

    }

    public void startMyActivity(View view,int position){
        Intent intent=new Intent(activity,NewsDetail.class);
        intent.putExtra("newUrl",newsList.get(position).getNewsUrl());
        activity.startActivity(intent);
    }

    public void initEvent(){

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                handler1.postDelayed(new Runnable() {
                    public void run() {
                        newsList=new ArrayList<>();
                        page=0;
                        getNews(page);
                    }
                },1500);
            }
        });

        refreshLayout.post(new Runnable() {
            public void run() {
                refreshLayout.setRefreshing(true);
            }
        });

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager linearLayoutManager= (LinearLayoutManager) recyclerView.getLayoutManager();
                int lastItem=linearLayoutManager.findLastVisibleItemPosition();
                //当前屏幕所看到的子项个数
                int visibleItemCount = linearLayoutManager.getChildCount();
                //当前RecyclerView的所有子项个数
                int totalItemCount = linearLayoutManager.getItemCount();

                int state=recyclerView.getScrollState();
                if (visibleItemCount>0&&lastItem==totalItemCount-1){
                    handler1.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            getNews(page*10);
                        }
                    },1000);
                }
            }
        });

    }

}
