package com.sky.casper.skywalker_new_app.Models;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class CVProfile implements Serializable {
    private Map<String,Boolean> privacy;
    private BioInfo bInfo;
    private PersonalInfo pInfo;

    public CVProfile(Map<String,String> attributes){

    }

    private class BioInfo{

    }

    private class PersonalInfo{

    }
}
