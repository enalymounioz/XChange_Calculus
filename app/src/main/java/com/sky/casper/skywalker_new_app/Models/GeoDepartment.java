package com.sky.casper.skywalker_new_app.Models;


public class GeoDepartment extends Type {
    private String country_id;

    public GeoDepartment(String i, String t, String c_i, String ty) {
        super(i, t, ty);
        country_id = c_i;
    }

    public String getCountry_id(){
        return country_id;
    }

    @Override
    public String toString() {
        return "GeoDepartment [type=" + type + " id="+id + " title="+title +"country_id="+country_id+"]";
    }
}
