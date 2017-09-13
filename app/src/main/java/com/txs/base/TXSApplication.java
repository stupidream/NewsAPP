package com.txs.base;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * Created by lkpassword on 2017/7/5.
 */

public class TXSApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        initFresco();
    }

    public  void initFresco(){
        Fresco.initialize(this);
    }
}
