package com.txs.ui;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.txs.R;

/**
 * Created by Administrator on 2017/7/9.
 */

public class PersonalLoginFragment extends Fragment {
    LinearLayout user_login;
    public PersonalLoginFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.personal_login, container, false);
        user_login= (LinearLayout) view.findViewById(R.id.user_login);
        user_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().startActivity(new Intent(getActivity(),LoginActivity.class));
            }
        });
        return view;
    }
}
