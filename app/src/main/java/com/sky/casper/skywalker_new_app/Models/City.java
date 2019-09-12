package com.sky.casper.skywalker_new_app.Models;


public class City extends Type {
    private String state_id;
    public City(String i, String t, String s_i, String ty) {
        super(i, t, ty);
        state_id=s_i;
    }

    public String getState_id(){
        return state_id;
    }

    @Override
    public String toString() {
        return "City [type=" + type + " id="+id + " title="+title +"country_id="+state_id+"]";
    }
}
