package com.sky.casper.skywalker_new_app.Models;

public class PageInfo {  //// information about a page of ads such as total ads, total pages etc.
    int totalPages, totalAds, currentPage, fromAd, toAd;

    public PageInfo(int tp, int ta, int cp, int fa, int tA){
        this.totalAds = ta;
        this.totalPages = tp;
        this.currentPage = cp;
        this.fromAd = fa;
        this.toAd = tA;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public int getTotalAds() {
        return totalAds;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public int getFromAd() {
        return fromAd;
    }

    public int getToAd() {
        return toAd;
    }
}
