package com.sky.casper.skywalker_new_app.Models;


public class University extends Type {

    public String education_level;

    public University(String i, String t, String ty) {
        super(i, t, ty);
    }

    public University(String i, String t, String p, String ty){
        super(i,t,ty);
        education_level=p;
    }

    public String getEduLevel(){
        return education_level;
    }



}
