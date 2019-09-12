package com.sky.casper.skywalker_new_app.Models;

public class CertificationForeignLang extends Type {
    private String language;

    public CertificationForeignLang(String i, String t, String ty) {
        super(i, t, ty);
    }

    public CertificationForeignLang(String i, String t, String p, String ty){
        super(i,t,ty,p);
        language=p;
    }

    public String getLang(){
        return language;
    }
}
