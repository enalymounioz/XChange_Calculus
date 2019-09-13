package com.sky.casper.skywalker_new_app.Models;


import com.sky.casper.skywalker_new_app.Helpers.Settings;


public class EmpType extends Type {

    public EmpType(String i, String t, String ty) {
        super(i, t, ty);
    }



    @Override
    public String toString() { /* Needs for passing to another activity */
        return "EmpType"+" [id="+id+" type="+ type +" title="+title+"]";
    }
}
