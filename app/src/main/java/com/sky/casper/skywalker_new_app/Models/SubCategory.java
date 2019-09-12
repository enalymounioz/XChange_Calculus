package com.sky.casper.skywalker_new_app.Models;


public class SubCategory extends Type {
    private String category_id;

    public SubCategory(String i, String t, String k, String ty){
        super(i,t,ty);
        category_id=k;
    }




    public void setParent_id(String parent_id){
        this.category_id=parent_id;
    }

    public String getParent_id(){ return category_id; }

    @Override
    public String toString() {
        return "SubCategory [type=" + type + " id="+id + " title="+title + "category_id="+category_id+"]";
    }
}
