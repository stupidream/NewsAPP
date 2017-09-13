package com.txs;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.RadioButton;

import com.txs.entity.News;
import com.txs.ui.DingyueFragment;
import com.txs.ui.DingyueFragmentNologin;
import com.txs.ui.NewsFragment;
import com.txs.ui.PersonalFragment;
import com.txs.ui.PersonalLoginFragment;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.Bmob;

public class MainActivity extends FragmentActivity {

    public static final int SHOW_RESPONSE = 0;
    public static List<News> list;
    private NewsFragment newsFragment;
    private PersonalFragment personalFragment;
    private DingyueFragment dingyueFragment;
    private SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Bmob.initialize(this, "9868a597b8d5f8addecd66c8bb8b8e96");
        //获得实例对象
        sp = this.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        ButterKnife.bind(this);
        showNewsFragment();
    }

    @OnClick({R.id.rbtn_home,R.id.rbtn_subscibe,R.id.rbtn_mine})
    public void onClickEvent(RadioButton radioButton){
        switch (radioButton.getId()){
            case R.id.rbtn_home:
                showNewsFragment();
                break;
            case R.id.rbtn_subscibe:
                showClassifyActivity();
                break;
            case R.id.rbtn_mine:
                showMine();
                break;
        }
    }

    public void showNewsFragment(){
        if (newsFragment==null){
            newsFragment=new NewsFragment();
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment,newsFragment).commit();
    }

    private void showMine() {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        if(sp.getBoolean("AUTO_ISCHECK", false)){
            //设置默认是自动登录状态
            PersonalFragment personalFragment = new PersonalFragment();
            transaction.replace(R.id.fragment, personalFragment, "personal");
            transaction.commit();
        }else{
            PersonalLoginFragment personalloginFragment = new PersonalLoginFragment();
            transaction.replace(R.id.fragment, personalloginFragment, "personal");
            transaction.commit();
        }
    }

    private void showClassifyActivity() {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        if(sp.getBoolean("AUTO_ISCHECK", false)){
            //设置默认是自动登录状态
            DingyueFragment personalFragment = new DingyueFragment();
            transaction.replace(R.id.fragment, personalFragment, "personal");
            transaction.commit();
        }else{
            DingyueFragmentNologin personalloginFragment = new DingyueFragmentNologin();
            transaction.replace(R.id.fragment, personalloginFragment, "personal");
            transaction.commit();
        }
    }

}
