package com.sky.casper.skywalker_new_app.Models;

import android.text.Html;

import java.io.Serializable;


public class Advert implements Serializable {
    private String text,image_url,title,
            image_file,id,empType,region,date,clientId,name,full_text,clientName,link,category,ad_code,seoUrl,full_title,anonymous_title;
    private boolean hasQuestions;
    boolean isAnonymous;
    boolean fromWebview;
    private String gdprInfo,gdprResp;

    public Advert(String txt, String img, String ttl, String i,
                  String emT, String reg, String d, String client, String n, Boolean iA, String clientN, String ac, String at){
        text=txt;
        image_url=img;
        title=ttl;
        id=i;
        empType=emT;
        region=reg;
        date=d;
        clientId=client;
        name=n;
        isAnonymous=iA;
        clientName=clientN;
        full_text=null;
        link=null;
        image_file=null;
        ad_code=ac;
        fromWebview=false;
        anonymous_title = at;
    }

    public Advert(String txt, String n, String r, String d, String t, String i, String ft, String categ, String client, String gdprI, String gdprR){ // Mikres
        text=txt;
        name=n;
        region=r;
        date=d;
        title=t;
        id=i;
        full_text=ft;
        category=categ;
        fromWebview=false;
        clientName = client;
        gdprInfo = gdprI;
        gdprResp = gdprR;
    }

    public Advert(){

    }

    public String getGdprResp() {
        return gdprResp;
    }

    public String getGdprInfo() {
        return gdprInfo;
    }

    public String getText(){
        return text;
    }

    public String getName(){
        return name;
    }

    public String getAnonymous_title(){
        return anonymous_title;
    }

    public String getImage_url(){
        return image_url;
    }

    public String getTitle(){
        return title;
    }

    public String getImage_file(){
        return image_file;
    }

    public String getId(){
        return id;
    }

    public String getDate(){
        return date;
    }

    public String getEmpType(){
        return empType;
    }

    public String getFull_text(){
        return full_text;
    }

    public String getLink(){
        return link;
    }

    public String getRegion(){
        return region;
    }

    public String getCategory(){
        return category;
    }

    public String getPureText(){
        return Html.fromHtml(full_text).toString();
    }

    public String getClientId(){
        return clientId;
    }

    public String getClientName(){
        return clientName;
    }

    public boolean getFromWebView(){
        return fromWebview;
    }

    public String getFull_title(){
        return full_title;
    }

    public String getAd_code(){
        return (ad_code==null || ad_code.equals("null") || ad_code.equals("NULL") || ad_code.equals("Null")) ? "" :ad_code;
    }

    public String getSeoUrl(){
        return seoUrl;
    }






    public boolean isAnonymous() {
        return isAnonymous;
    }

    public boolean hasQuestions() {return hasQuestions; }

    public boolean hasExtraInfos(){
        return (full_text!=null);
    }

    public boolean hasAnonymousTitle(){
        return isAnonymous() && anonymous_title!=null && anonymous_title.equals("");
    }

    public boolean hasLink(){
        return link!=null && !link.equals("");
    }






    public void setImage_file(String imf){
        image_file=imf;
    }

    public void setAd_code(String ac){
        ad_code=ac;
    }

    public void setHasQuestions(String q){
        hasQuestions = q.equals("1") || q.equals("true") || q.equals("True") || q.equals("TRUE");
    }

    public void setFull_text(String ft){
        full_text=ft;
    }

    public void setLink(String li){
        if(!li.equals("null")) {
            link = li;
        }
    }

    public void setFromWebView(boolean b){
        fromWebview = b;
    }

    public void  setSeoUrl(String su){
        seoUrl = su;
    }

    public void setFull_title(String ft){
        full_title = ft;
    }

    public void setAnonymous_title(String at){
        anonymous_title = at;
    }

    public void setGdprInfo(String gi){
        gdprInfo = gi;
    }

    public void setGdprResp(String gr){
        gdprResp = gr;
    }

    public void setCategory(String c){
        category = c;
    }


    public boolean isSmall(){
        return empType==null;
    }



    @Override
    public String toString(){
        return "Advert [text="+text +" image_url="+image_url +" title="+title +" category="+category+" image_file="+image_file+" id="+id+" empType="+empType+" region="+region+
                " date="+date+" clientId="+clientId+" name="+name+" full_text="+full_text+" link="+link+" ad_code="+ad_code+" seoUrl="+seoUrl+"]";
    }
}
