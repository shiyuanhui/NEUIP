package com.shiyuanhui.bean;

public class PushMessage {
    private String tag;//推送消息标志，数据为link或者text
    private String title;
    private String text;
    private String url;//仅当link类型时才有用

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
