package com.zhuandian.mobilelibrary.entity;

import cn.bmob.v3.BmobObject;

/**
 * desc :帖子实体
 * author：xiedong
 * date：2019/2/19
 */
public class PostEntity extends BmobObject {

    /**
     * 帖子标题
     */
    private String title;

    /**
     * 帖子内容
     */
    private String content;



    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
