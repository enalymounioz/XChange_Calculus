package com.sky.casper.skywalker_new_app.Models;

import java.io.Serializable;
import java.util.Comparator;


public class Type implements Serializable, Comparable<Type> {
    protected String type;
    protected String id;
    protected String title;
    protected String parent_id;
    protected static final long serialiseUID = 1L;

    public Type(String i, String t, String ty){
        type=ty;
        id=i;
        title=t;
    }

    public Type(String i, String t, String ty, String pi){
        type=ty;
        id=i;
        title=t;
        parent_id=pi;
    }


    public void setId(String id){
        this.id=id;
    }

    public void setTitle(String title){
        this.title=title;
    }

    public void setType(String type){
        this.type=type;
    }


    public String getTitle(){
        return title;
    }

    public String getId(){
        return id;
    }

    public String getType(){
        return  type;
    }

    public String getParent_id(){
        return parent_id;
    }

    @Override
    public String toString() {
        return "Type [type=" + type + " id="+id + " title="+title +"]";
    }

    public static Type[] deSerialize(String serialiazableString){
        Type[] types = null;

        return types;
    }

    @Override
    public int compareTo(Type type) {
        return Comparators.TITLE.compare(this,type);
    }


    public static class Comparators{
        public static Comparator<Type> TITLE = new Comparator<Type>(){

            @Override
            public int compare(Type t1, Type t2) {
                return t1.title.compareTo(t2.title);
            }
        };
    }
}
