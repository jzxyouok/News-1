package com.example.dwight.myapplication.model;

import cn.bmob.v3.BmobObject;

/**
 * Created by YoKeyword on 16/2/1.
 */
public class Article extends BmobObject {
    private String title;
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
