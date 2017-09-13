package com.txs;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.view.KeyEvent.KEYCODE_BACK;

/**
 * Created by ZHAO on 2017/7/8.
 */

public class NewsDetail extends Activity {

    @BindView(R.id.newDetail)
    WebView webView;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    public static final int SHOW_RESPONSE = 0;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        ButterKnife.bind(this);
        Intent intent=getIntent();
        String url=intent.getStringExtra("newUrl");
        getOriginalUrl(url);
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case SHOW_RESPONSE:
                    Document dc = (Document) msg.obj;
                    dc.select("div.page").addClass("on");
                    dc.select("header").remove();
                    dc.select("footer").remove();
                    dc.select("div.a_adtemp").remove();
                    dc.select("div.footer").remove();
                    dc.select("div.article_comment").remove();
                    dc.select("section.article_comment").remove();
                    dc.select("div.relative_doc").remove();
                    dc.select("div.hot_news").remove();
                    initView(dc);
                    break;
            }
        }
    };

    public void initView(Document dc){
        WebSettings webSettings=webView.getSettings();
        webSettings.setUseWideViewPort(true);//将图片调整到适合webview的大小
        webSettings.setLoadWithOverviewMode(true);// 缩放至屏幕的大小
        webSettings.setJavaScriptEnabled(true);//启用js
        webSettings.setSupportZoom(false);//设置不支持缩放
  //      webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);//不启用缓存
        webSettings.setDefaultTextEncodingName("utf-8");
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        webView.loadDataWithBaseURL(null,dc.toString(),"text/html","utf-8",null);
        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (newProgress==100){
                    progressBar.setVisibility(View.GONE);
                }else {
                    progressBar.setVisibility(View.VISIBLE);
                    progressBar.setProgress(newProgress);
                }
            }
        });

    }

    public void getOriginalUrl(final String url){
        new Thread(new Runnable() {
            @Override
            public void run() {
                Document dc=null;
                try {
                    dc= Jsoup.connect(url).userAgent("Mozilla/5.0 (Linux; Android 5.0; SM-G900P Build/LRX21T) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/59.0.3071.86 Mobile Safari/537.36")
                            .get();
                    Message message = new Message();
                    message.what = SHOW_RESPONSE;
                    message.obj = dc;
                    handler.sendMessage(message);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode==KEYCODE_BACK)&&webView.canGoBack()){
            webView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    protected void onDestroy() {
        webView.clearHistory();
        webView.clearCache(true);
        webView.stopLoading();
        webView.removeAllViews();
        webView.destroy();
        super.onDestroy();
    }
}
