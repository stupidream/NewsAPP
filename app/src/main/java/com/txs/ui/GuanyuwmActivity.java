package com.txs.ui;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.txs.R;

public class GuanyuwmActivity extends FragmentActivity {
    Button guan_ret;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guanyuwm);
        guan_ret= (Button) findViewById(R.id.guan_ret);
        guan_ret.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
