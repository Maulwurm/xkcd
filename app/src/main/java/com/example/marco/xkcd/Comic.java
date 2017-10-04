package com.example.marco.xkcd;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Marco on 04.10.2017.
 */

public class Comic {

    @SerializedName("month") private String month;
    @SerializedName("num") private int num;
    @SerializedName("link") private String link;
    @SerializedName("year") private String year;
    @SerializedName("news")private String news;
    @SerializedName("safeTitle")private String safeTitle;
    @SerializedName("transcript")private String transcript;
    @SerializedName("alt")private String alt;
    @SerializedName("img")private String img;
    @SerializedName("title")private String title;
    @SerializedName("day")private String day;

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getNews() {
        return news;
    }

    public void setNews(String news) {
        this.news = news;
    }

    public String getSafeTitle() {
        return safeTitle;
    }

    public void setSafeTitle(String safeTitle) {
        this.safeTitle = safeTitle;
    }

    public String getTranscript() {
        return transcript;
    }

    public void setTranscript(String transcript) {
        this.transcript = transcript;
    }

    public String getAlt() {
        return alt;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }
}
