package com.sky.casper.skywalker_new_app.Models;


public class Municipality extends Type {
    private String city_id;
    public Municipality(String i, String t, String ci, String ty) {
        super(i, t, ty);
        city_id=ci;
    }

    public String getCity_id(){
        return city_id;
    }

    @Override
    public String toString() {
        return "Municipality [type=" + type + " id="+id + " title="+title +"country_id="+city_id+"]";
    }
}
