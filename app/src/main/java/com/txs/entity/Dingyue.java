package com.txs.entity;

import cn.bmob.v3.BmobObject;

/**
 * Created by ZHAO on 2017/7/11.
 */

public class Dingyue extends BmobObject {
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name;
}
