package com.zhuandian.mobilelibrary.entity;

import java.io.Serializable;

import cn.bmob.v3.BmobObject;

/**
 * desc :
 * author：xiedong
 * date：2019/2/20
 */
public class WorkEntity extends BmobObject implements Serializable {
    private String title;
    private String options;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOptions() {
        return options;
    }

    public void setOptions(String options) {
        this.options = options;
    }
}
