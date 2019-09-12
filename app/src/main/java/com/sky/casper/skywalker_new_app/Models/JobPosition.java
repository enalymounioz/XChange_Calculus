package com.sky.casper.skywalker_new_app.Models;


public class JobPosition extends Type {
    private String subcategory_id;

    public JobPosition(String i, String t, String s_i, String ty){
        super(i,t,ty);
        subcategory_id=s_i;
    }

    public String getSubcategory_id(){
        return subcategory_id;
    }

    @Override
    public String toString() {
        return "JobPosition [type=" + type + " id="+id + " title="+title +"subcategory_id="+subcategory_id+"]";
    }

}
