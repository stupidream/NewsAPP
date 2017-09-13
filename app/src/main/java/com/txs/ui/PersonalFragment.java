package com.txs.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.txs.R;
import com.txs.entity.Users;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;


/**
 * Created by Administrator on 2017/7/5.
 */

public class PersonalFragment extends Fragment {
    LinearLayout photo,shoucang,guanyu,shezhi;
    TextView per_znicheng;
    private SharedPreferences sp;
    public PersonalFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.personal, container, false);
        photo= view.findViewById(R.id.photo);
        sp = getActivity().getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        shoucang= view.findViewById(R.id.shoucang);
        guanyu= view.findViewById(R.id.guanyu);
        shezhi= view.findViewById(R.id.shezhi);
        per_znicheng= view.findViewById(R.id.per_znicheng);
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
                            per_znicheng.setText(user.getNickname());
                        }
                    }
                }
            }
        });
        shezhi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().startActivity(new Intent(getActivity(),ShezhiActivity.class));
            }
        });
        photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getActivity().startActivity(new Intent(getActivity(),PerPhActivity.class));
            }
        });
        shoucang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().startActivity(new Intent(getActivity(),ShoucangActivity.class));
            }
        });
        guanyu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().startActivity(new Intent(getActivity(),GuanyuwmActivity.class));
            }
        });
        return view;
    }
}
