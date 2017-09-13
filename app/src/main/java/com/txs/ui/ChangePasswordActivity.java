package com.txs.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.txs.MainActivity;
import com.txs.R;
import com.txs.entity.Users;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

public class ChangePasswordActivity extends FragmentActivity {
    ImageButton xiu_back;
    EditText et_passwordchange1,et_passwordchange2,et_passwordchange3;
    Button xiu_queren;
    private SharedPreferences sp;
    String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        sp = this.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        et_passwordchange1= (EditText) findViewById(R.id.et_passwordchange1);
        et_passwordchange2= (EditText) findViewById(R.id.et_passwordchange2);
        et_passwordchange3= (EditText) findViewById(R.id.et_passwordchange3);
    }
    public void onClickEvent(View view){
        switch (view.getId()){
            case R.id.xiu_back:
                finish();
                break;
            case R.id.xiu_queren:
                xiugai();
                break;
        }
    }

    private void xiugai() {
        String phone=sp.getString("USER_NAME", "");
        final String pwd=sp.getString("PASSWORD", "");
        BmobQuery<Users> user = new BmobQuery<>();
        Users us=new Users();
        String ypwd=et_passwordchange1.getText().toString();
        String xpwd=et_passwordchange2.getText().toString();
        final String qpwd=et_passwordchange3.getText().toString();
        user.addWhereEqualTo("phone",phone);
        user.findObjects(new FindListener<Users>() {
            @Override
            public void done(List<Users> list, BmobException e) {
                if (e==null){
                    if (list.size()!=0){
                        for (Users user : list) {
                            id=user.getObjectId();
                            //toast(id);
                            Log.i("user", id);
                        }
                    }
                }
            }
        });
        if (pwd.equals(ypwd)){
            if (xpwd.equals(qpwd)){
                us.setPassword(qpwd);
                us.update(id, new UpdateListener(){
                    @Override
                    public void done(BmobException e){
                        Log.i("aa","更新成功");
                        if(e==null){
                            Log.i("aa","更新成功aa");
                            toast("修改成功");
                            SharedPreferences.Editor editor = sp.edit();
                            editor.putString("PASSWORD",qpwd);
                            editor.commit();
                            Intent intent = new Intent(ChangePasswordActivity.this,MainActivity.class);
                            ChangePasswordActivity.this.startActivity(intent);
                        }else{
                            //Log.i("bmob","更新失败："+e.getMessage()+","+e.getErrorCode());
                        }
                    }
                });
            }else {
                toast("两次密码不相同!");
            }
        }else {
            toast("原密码密码错误!");
        }
    }
    private void toast(String content) {
        Toast.makeText(this, content, Toast.LENGTH_SHORT).show();
    }
}
