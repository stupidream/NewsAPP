package com.txs.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.txs.ClassifyActivity;
import com.txs.R;
import com.txs.custom.CustomTitleActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ZHAO on 2017/7/11.
 */

public class DingyueFragmentNologin extends Fragment {
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.dingyue_nologin, container, false);
        CustomTitleActivity titleActivity=view.findViewById(R.id.custom_order);
        View.OnClickListener onClickListener=new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startMyActivity();
            }
        };
        titleActivity.setBackListener(onClickListener);
        ButterKnife.bind(this,view);
        return view;
    }
    @OnClick(R.id.iv_order)
    public void startMyActivity(){
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        getActivity().startActivity(intent);
    }
}
