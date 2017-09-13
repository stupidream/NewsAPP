package com.txs.ui;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.txs.R;
import com.txs.entity.Users;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;


public class PerPhActivity extends FragmentActivity {
    SimpleDraweeView per_pho;
    TextView per_nicheng;
    TextView  per_tianxin;
    private SharedPreferences sp;
    String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal_photo);
        sp = this.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        per_pho= (SimpleDraweeView) findViewById(R.id.per_pho);
        per_nicheng = (TextView) findViewById(R.id.per_nicheng);
        per_tianxin = (TextView) findViewById(R.id.per_tianxin);
        tianzhi();
    }

    private void tianzhi() {
        //从服务器获取用户数据
        BmobQuery<Users> user = new BmobQuery<>();
        String phone=sp.getString("USER_NAME", "");
        // toast(phone);
        user.addWhereEqualTo("phone",phone);
        user.findObjects(new FindListener<Users>() {
            @Override
            public void done(List<Users> list, BmobException e) {
                if (e==null){
                    if (list.size()!=0){
                        for (Users user : list) {
                            //Log.i("user", user.getPhone());
                            //Log.i("user", user.getPassword());
                           // toast(user.getNickname()+"--"+user.getSex());
                            per_nicheng.setText(user.getNickname());
                            per_tianxin.setText(user.getSex());
                            id=user.getObjectId();
                           // toast(id);
                            Log.i("user", id);
                        }
                    }
                }
            }
        });
    }

    public void onClickEvent(View view){
        switch (view.getId()){
            case R.id.find_ret:
                finish();
                break;
            case R.id.per_pho:
                xiugaiphoto();
                break;
            case R.id.per_ni:
                nicheng();
                break;
            case R.id.per_xingbie:
                xingbie();
                break;
        }
    }
    /*修改头像*/
    private void xiugaiphoto() {
        // per_pho.setEnabled(false);
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent,1);
    }

    /*修改昵称*/
    private void nicheng() {
        final PopupWindow popupWindow = new PopupWindow(this);
        /* 通过View.inflate 加载布局 得到View对象*/
        View contentView = View.inflate(this, R.layout.personal_nicheng, null);
        LinearLayout rRoot = (LinearLayout) contentView.findViewById(R.id.rRoot );
        final EditText shu_nicheng = (EditText) contentView.findViewById(R.id.shu_nicheng);
        Button quxiao = (Button) contentView.findViewById(R.id.quxiao);
        Button queren = (Button) contentView.findViewById(R.id.queren);

        quxiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        queren.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nicheng=shu_nicheng.getText().toString();
                Users usn=new Users();
                if (!nicheng.equals("")) {
                    usn.setNickname(nicheng);
                    usn.update(id, new UpdateListener(){
                        @Override
                        public void done(BmobException e){
                            Log.i("aa","更新成功");
                            if(e==null){
                                Log.i("aa","更新成功aa");
                                toast("修改成功");
                            }else{
                                //Log.i("bmob","更新失败："+e.getMessage()+","+e.getErrorCode());
                            }
                        }
                    });
                    per_nicheng.setText(nicheng);
                    popupWindow.dismiss();
                }
            }
        });
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        };
        rRoot.setOnClickListener(onClickListener);
        /* 将View绑到到Popwindow*/
        popupWindow.setContentView(contentView);
        popupWindow.setFocusable(true);
        /* 设置宽高*/
        popupWindow.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        /* 设置背景*/
        ColorDrawable dw = new ColorDrawable(0xb0000000); //实例化一个ColorDrawable颜色为半透明
        /*设置键盘不挡住弹框*/
        popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        popupWindow.setBackgroundDrawable(dw);//设置PopupWindow弹出窗体的背景
        popupWindow.showAtLocation(this.getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
    }

    /*修改性别*/
    private void xingbie() {
        final PopupWindow popupWindow = new PopupWindow(this);
        /* 通过View.inflate 加载布局 得到View对象*/
        View contentView = View.inflate(this, R.layout.personal_xingbie, null);
        RelativeLayout xRoot = (RelativeLayout) contentView.findViewById(R.id.xRoot);
        final TextView xingbie_nan = (TextView) contentView.findViewById(R.id.xingbie_nan);
        final TextView xingbie_nv = (TextView) contentView.findViewById(R.id.xingbie_nv);
        TextView xingbie_quxiao = (TextView) contentView.findViewById(R.id.xingbie_quxiao);
        /* 将View绑到到Popwindow*/
        //调用滑动动画
        donghua(xRoot);
        xingbie_nan.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String nan=xingbie_nan.getText().toString();
                Users us=new Users();
                us.setSex(nan);
                us.update(id, new UpdateListener(){
                    @Override
                    public void done(BmobException e){
                        Log.i("aa","更新成功");
                        if(e==null){
                            Log.i("aa","更新成功aa");
                            toast("修改成功");
                        }else{
                            //Log.i("bmob","更新失败："+e.getMessage()+","+e.getErrorCode());
                        }
                    }
                });
                per_tianxin.setText(nan);
                popupWindow.dismiss();
            }
        });
        xingbie_nv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nv=xingbie_nv.getText().toString();
                Users us1=new Users();
                us1.setSex(nv);
                us1.update(id, new UpdateListener(){
                    @Override
                    public void done(BmobException e){
                        Log.i("aa","更新成功");
                        if(e==null){
                            Log.i("aa","更新成功aa");
                            toast("修改成功");
                        }else{
                            //Log.i("bmob","更新失败："+e.getMessage()+","+e.getErrorCode());
                        }
                    }
                });
                per_tianxin.setText(nv);
                popupWindow.dismiss();
            }
        });
        xingbie_quxiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        popupWindow.setContentView(contentView);
        popupWindow.setFocusable(true);
        /* 设置宽高*/
        popupWindow.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        /* 设置背景*/
        ColorDrawable dw = new ColorDrawable(0xb0000000); //实例化一个ColorDrawable颜色为半透明
        popupWindow.setBackgroundDrawable(dw);//设置PopupWindow弹出窗体的背景
        popupWindow.showAtLocation(this.getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
    }

    /*实现上滑动画*/
    public void donghua(final RelativeLayout xRoot){
        TranslateAnimation translateAnimation = new TranslateAnimation(0, 0, 0, -720);
        translateAnimation.setDuration(500);
        xRoot.setAnimation(translateAnimation);
        xRoot.startAnimation(translateAnimation);
        translateAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }
            @Override
            public void onAnimationRepeat(Animation animation) {
            }
            @Override
            public void onAnimationEnd(Animation animation) {
                xRoot.clearAnimation();
                int left = xRoot.getLeft();
                int top = xRoot.getTop()-720;
                int width = xRoot.getWidth();
                int height = xRoot.getHeight();
                xRoot.layout(left, top, left+width, top+height);
            }
        });
    }
    /*处理头像*/
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode==RESULT_OK){
            Uri uri=data.getData();
            ContentResolver content=this.getContentResolver();
            try {
                // Bitmap bitmap = BitmapFactory.decodeStream(content.openInputStream(uri));
                per_pho.setImageURI(uri);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    private void toast(String content) {
        Toast.makeText(this, content, Toast.LENGTH_SHORT).show();
    }
}
