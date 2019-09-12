package com.sky.casper.skywalker_new_app.Models;


import com.sky.casper.skywalker_new_app.Helpers.Settings;


public class EmpType extends Type {

    public EmpType(String i, String t, String ty) {
        super(i, t, ty);
    }



    @Override
    public String toString() {
        return Settings.EMPLOYE.JSON_EMPTYPE_TYPE+" [id="+id+" type="+ type +" title="+title+"]";
    }
}
