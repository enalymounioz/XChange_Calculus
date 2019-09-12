package com.sky.casper.skywalker_new_app.Models;


public class Category extends Type {


    public Category(String i, String t, String ty){
        super(i,t,ty);
    }

    @Override
    public String toString() {
        return "Category [type=" + type + " id="+id + " title="+title +"]";
    }
}
