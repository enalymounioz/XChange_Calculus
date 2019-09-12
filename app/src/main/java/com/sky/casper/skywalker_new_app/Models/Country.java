package com.sky.casper.skywalker_new_app.Models;


public class Country extends Type {
    public Country(String i, String t, String ty) {
        super(i, t, ty);
    }

    @Override
    public String toString() {
        return "Country [type=" + type + " id="+id + " title="+title +"]";
    }
}
