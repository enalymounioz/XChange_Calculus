package com.sky.casper.skywalker_new_app.Models;


public class State extends Type {
    private String geoDepar_id;
    public State(String i, String t, String s_i, String ty) {
        super(i, t, ty);
        geoDepar_id=s_i;
    }

    public String getGeoDepar_id(){
        return geoDepar_id;
    }

    @Override
    public String toString() {
        return "State [type=" + type + " id="+id + " title="+title +"country_id="+geoDepar_id+"]";
    }
}
