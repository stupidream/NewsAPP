package com.txs;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.txs.adapter.NewsAdapter;
import com.txs.base.NewsItemListener;
import com.txs.entity.News;
import com.txs.entity.NewsSort;
import com.txs.ui.NewsIntroduceActivity;
import com.txs.adapter.MyAdapter;
import com.txs.base.MyItemClickListener;
import com.txs.custom.CustomTitleActivity;

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
import butterknife.OnClick;

public class ClassifyActivity extends FragmentActivity {

    MyAdapter mAdapter;
 //   List<NewsSort> list = new ArrayList<>();
    NewsSort news;
    private int page=0;
    public static final int SHOW_RESPONSE = 0;
    private Handler handler1 = new Handler();
    private List<News> sortList=new ArrayList<>();
    private boolean isFirst=true;
    private int listSize;

    private String sort;

    @BindView(R.id.classify_recycleView)
    RecyclerView mRecyclerView;

    @BindView(R.id.classify_top_title)
    CustomTitleActivity mTitleActivity;

    @BindView(R.id.sortRefresh)
    SwipeRefreshLayout refreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classify);
        ButterKnife.bind(this);
        initView();
        initEvent();
        getNewsSort(page,"BD29LPUBwangning");
        initTopClickEvent();
    }


    //初始化列表
    public void initView() {
        /*recylerView 有多种显示方式  需要自己选择显示方式 */
        LinearLayoutManager manager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(manager);
    }

    public void initData(String sort){
        refreshLayout.post(new Runnable() {
            public void run() {
                refreshLayout.setRefreshing(true);
            }
        });
        page=0;
        isFirst=true;
        sortList=new ArrayList<>();
        getNewsSort(page,sort);
    }

    @OnClick({R.id.guonei,R.id.guowai,R.id.keji, R.id.junshi,R.id.shehui,R.id.tiyu,R.id.jiaoyu})
    public void onClickEvent(RadioButton radioButton){
        switch (radioButton.getId()){
            case R.id.guonei:
                initData("BD29LPUBwangning");
                break;
            case R.id.guowai:
                initData("BD29MJTVwangning");
                break;
            case R.id.keji:
                initData("BA8D4A3Rwangning");
                break;
            case R.id.junshi:
                initData("BAI67OGGwangning");
                break;
            case R.id.shehui:
                initData("BCR1UC1Qwangning");
                break;
            case R.id.tiyu:
                initData("BA8E6OEOwangning");
                break;
            case R.id.jiaoyu:
                initData("BA8FF5PRwangning");
                break;

        }
    }

    public void initEvent(){

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                handler1.postDelayed(new Runnable() {
                    public void run() {
                        sortList=new ArrayList<>();
                        page=0;
                        isFirst=true;
                        getNewsSort(page,sort);
                    }
                },1500);
            }
        });


        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            getNewsSort(page*10,sort);
                        }
                    },1000);
                }
            }
        });

    }

    //初始化 数据

    public void bindData() {
        if (isFirst){
            isFirst=false;
            page++;
            listSize=sortList.size();
            mAdapter=new MyAdapter(this,sortList);
            mRecyclerView.setAdapter(mAdapter);
        }else {
            mAdapter.setList(sortList);
            mAdapter.notifyDataSetChanged();
            if (sortList.size()>listSize){
                listSize=sortList.size();
                page++;
            }
        }

        refreshLayout.setRefreshing(false);
    }

    //自动义标签的点击事件
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
                Toast.makeText(ClassifyActivity.this, "点击了iv_icon_order", Toast.LENGTH_SHORT).show();
            }
        });

        mTitleActivity.setSearchListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ClassifyActivity.this, "点击了iv_right_search", Toast.LENGTH_SHORT).show();
            }
        });

    }

    //item的点击事件
    /*public void onItemClick(View view, int postion) {
        NewsSort news = list.get(postion);
        if (news != null) {
            *//*
            * 在此处添加item点击事件
            * *//*
            Intent intent = new Intent(this, NewsIntroduceActivity.class);
            intent.putExtra("title", news.getName() + postion);
            intent.putExtra("ImageUri", news.getImageUri());
            intent.putExtra("text", news.getText());
            intent.putExtra("flag", news.getFlag());
            startActivity(intent);

            Toast.makeText(this, news.getImageUri(), Toast.LENGTH_SHORT).show();
        }
    }*/

    public void getNewsSort(final int nPage, final String sorts){
        sort=sorts;
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    List<News> list1=new ArrayList<>();
                    String url="http://3g.163.com/touch/reconstruct/article/list/"+sorts+"/"+nPage+"-10.html";
                    Document dc = Jsoup.connect(url).userAgent("Mozilla/5.0 (Linux; U; Android 4.3; en-us; SM-N900T Build/JSS15J) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30")
                            .ignoreContentType(true).get();
                    if (dc.body().text()!=null){
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
                    if (jsonObject.get(sort)!=null){
                        JsonArray newLists=jsonObject.get(sort).getAsJsonArray();
                        for (int i=0;i<newLists.size();i++){
                            News news=new News();
                            JsonObject newsInfo=newLists.get(i).getAsJsonObject();
                            String source=newsInfo.get("source").getAsString().trim();
                            if (source!=""){
                                if (isExist(sortList,source)){
                                    news.setNewSource(source);
                                    sortList.add(news);
                                }
                            }
                        }
                        bindData();
                    }
                    break;
            }
        }
    };

    public boolean isExist(List<News> list,String source){
        boolean flag = true;
        for (News news:list){
            if (source.equals(news.getNewSource())){
                flag=false;
            }
        }
        return flag;
    }

}
