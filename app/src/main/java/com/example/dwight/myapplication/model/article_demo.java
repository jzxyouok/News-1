package com.example.dwight.myapplication.model;

/**
 * Created by Dwight on 16/6/6.
 */
public class article_demo {

    private String title;
    private String content;

    public article_demo(String title, String content) {
        this.title = title;
        this.content = content;
    }

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