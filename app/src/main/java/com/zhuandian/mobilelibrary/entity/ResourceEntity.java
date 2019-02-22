package com.zhuandian.mobilelibrary.entity;

import cn.bmob.v3.BmobObject;

/**
 * desc :
 * author：xiedong
 * date：2019/2/20
 */
public class ResourceEntity extends BmobObject {
    private String resourceTitle;
    private int type; // 1.图片 2.文件
    private String resourceContent;
    private String resourceUrl;
    private String classType;  //1 化学系 ，2 英语  3.数学  物理
//    public static final int CHEMISTR=1;
//    public static final int ENGLISH=2;
//    public static final int MATH=3;
//    public static final int PHSICAL=4;

    public String getClassType() {
        return classType;
    }

    public void setClassType(String classType) {
        this.classType = classType;
    }

    public String getResourceUrl() {
        return resourceUrl;
    }

    public void setResourceUrl(String resourceUrl) {
        this.resourceUrl = resourceUrl;
    }

    public String getResourceTitle() {
        return resourceTitle;
    }

    public void setResourceTitle(String resourceTitle) {
        this.resourceTitle = resourceTitle;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getResourceContent() {
        return resourceContent;
    }

    public void setResourceContent(String resourceContent) {
        this.resourceContent = resourceContent;
    }
}
