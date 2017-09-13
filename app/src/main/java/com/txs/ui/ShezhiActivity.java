package com.txs.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.txs.MainActivity;
import com.txs.R;

/**
 * Created by Administrator on 2017/7/9.
 */
public class ShezhiActivity extends FragmentActivity {
    LinearLayout but_tuichu,but_xiugai;
    private SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shezhi);
        sp = this.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        but_tuichu = (LinearLayout) findViewById(R.id.but_tuichu);
        but_xiugai = (LinearLayout) findViewById(R.id.but_xiugai);
    }
    public void onClickEvent(View view){
        switch (view.getId()){
            case R.id.but_tuichu:
                sp.edit().clear().commit();
                Intent intent = new Intent(ShezhiActivity.this,MainActivity.class);
                ShezhiActivity.this.startActivity(intent);
                break;
            case R.id.but_xiugai:
                Intent intent1 = new Intent(ShezhiActivity.this,ChangePasswordActivity.class);
                ShezhiActivity.this.startActivity(intent1);
                break;
        }
    }
}
