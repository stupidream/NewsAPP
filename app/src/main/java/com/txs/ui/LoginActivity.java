package com.txs.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

import com.txs.MainActivity;
import com.txs.R;
import com.txs.entity.Users;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class LoginActivity extends FragmentActivity {
    Button bt_zcnow,bt_login;
    EditText et_tel,et_password;
    ImageButton ib_backlogo;
    SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        sp = this.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        bt_zcnow =(Button)findViewById(R.id.bt_zcnow);
        bt_login =(Button)findViewById(R.id.bt_login);
        et_tel = (EditText)findViewById(R.id.et_tel);
        et_password = (EditText)findViewById(R.id.et_password);
        ib_backlogo = (ImageButton) findViewById(R.id.ib_backlogo);
        //登录
        bt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone=et_tel.getText().toString();
                String pwd=et_password.getText().toString();
                userlogo(phone,pwd);
            }
        });

        bt_zcnow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,ZhuceActivity.class);
                startActivity(intent);
            }
        });

        /*
        * 电话号码判断
        * */
        et_tel.addTextChangedListener(new TextWatcher() {
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
                    et_tel.setTextColor(Color.rgb(0,255,0));
                }else {
                    et_tel.setTextColor(Color.rgb(255,0,0));
                }
            }
        });
        ib_backlogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /**
     * 用户登录
     * @param phone
     * @param pwd
     */
    private void userlogo(final String phone, final String pwd) {
        if (isPhoneNumber(phone)){
            //从服务器获取用户数据
            BmobQuery<Users> q1 = new BmobQuery<>();
            q1.addWhereEqualTo("phone", phone);
            BmobQuery<Users> q2 = new BmobQuery<>();
            q2.addWhereEqualTo("password", pwd);
            List<BmobQuery<Users>> q = new ArrayList<>();
            q.add(q1);
            q.add(q2);
            BmobQuery<Users> query = new BmobQuery<>();
            query.and(q);
            query.findObjects(new FindListener<Users>() {
                @Override
                public void done(List<Users> list, BmobException e) {
                    if (e == null) {
                        Log.i("mmp", "null");
                        if (list.size() == 0) {
                            toast("用户名或密码错误!");
                        }else{
                            for (Users user : list) {
                                Log.i("user", user.getPhone());
                                Log.i("user", user.getPassword());
                            }
                            //保存信息
                            SharedPreferences.Editor editor = sp.edit();
                            editor.putString("USER_NAME", phone);
                            editor.putString("PASSWORD",pwd);
                            editor.commit();
                            //自动登录
                            sp.edit().putBoolean("AUTO_ISCHECK", true).commit();
                            Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                            LoginActivity.this.startActivity(intent);
                        }
                    } else {
                        Log.i("error", e.getMessage());
                        toast("用户名或密码错误-->MMP!");
                    }
                }
            });
        }else {
            toast("电话号码格式不正确!");
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
