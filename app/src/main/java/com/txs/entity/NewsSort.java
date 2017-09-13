package com.txs.entity;

import android.widget.ImageView;

/**
 * Created by lkpassword on 2017/7/5.
 */

public class NewsSort {


    String name;
    String imageUri;
    String text;
    int flag=1;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    @Override
    public String toString() {
        return "News{" +
                "name='" + name + '\'' +
                ", imageUri='" + imageUri + '\'' +
                ", text='" + text + '\'' +
                ", flag=" + flag +
                '}';
    }
}
