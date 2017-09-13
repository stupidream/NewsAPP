package com.txs.ui;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.txs.R;
import com.txs.entity.Users;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class ZhuceActivity extends FragmentActivity {
    Button bt_zhuce;
    EditText et_telzc,et_passwordzc,et_querenpwd,et_nichengzc;
    ImageButton ib_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhuce);
        Bmob.initialize(this, "9868a597b8d5f8addecd66c8bb8b8e96");
        bt_zhuce = (Button)findViewById(R.id.bt_zhuce);
        et_telzc = (EditText)findViewById(R.id.et_telzc);
        et_passwordzc = (EditText)findViewById(R.id.et_passwordzc);
        et_querenpwd = (EditText)findViewById(R.id.et_querenpwd);
        et_nichengzc = (EditText)findViewById(R.id.et_nichengzc);
        ib_back = (ImageButton)findViewById(R.id.ib_back);

        //注册
        bt_zhuce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone=et_telzc.getText().toString();
                if (isPhoneNumber(phone)){
                    userzhuce();
                }else {
                    toast("电话号码格式不正确！");
                }
            }
        });
        /*
        * 电话号码判断
        * */
        et_telzc.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                String phoneStr = s.toString();
                boolean b = isPhoneNumber(phoneStr);
                if(b){
                    et_telzc.setTextColor(Color.rgb(0,255,0));
                }else {
                    et_telzc.setTextColor(Color.rgb(255,0,0));
                }
            }
        });
        //返回
        ib_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ZhuceActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    private void userzhuce() {
        String phone=et_telzc.getText().toString();
        String pwd=et_passwordzc.getText().toString();
        String querenpwd=et_querenpwd.getText().toString();
        String nickname=et_nichengzc.getText().toString();
        //给对象类赋值
        Users user=new Users();
        user.setPhone(phone);
        user.setPassword(pwd);
        user.setNickname(nickname);
        if(pwd.equals(querenpwd)){
            user.save(new SaveListener<String>() {
                @Override
                public void done(String objectId,BmobException e) {
                    if(e==null){
                        toast("注册成功!");
                        Intent intent=new Intent(ZhuceActivity.this,LoginActivity.class);
                        startActivity(intent);
                    }else{
                        Log.i("error", e.getMessage());
                        toast("该用户已存在!");
                    }
                }
            });
        }else {
            toast("两次密码不相同!");
        }

    }

    private boolean isPhoneNumber(String phoneStr){
        String regex = "[1][34587]\\d{9}";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(phoneStr);
        return m.find();
    }

    private void toast(String content) {
        Toast.makeText(this, content, Toast.LENGTH_SHORT).show();
    }
}
