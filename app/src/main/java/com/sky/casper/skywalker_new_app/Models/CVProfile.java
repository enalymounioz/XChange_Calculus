package com.sky.casper.skywalker_new_app.Models;

import android.se.omapi.SEService;
import android.util.Log;
import android.util.Pair;

import com.sky.casper.skywalker_new_app.Helpers.Settings;
import com.sky.casper.skywalker_new_app.R;
import com.sky.casper.skywalker_new_app.Skywalker;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CVProfile implements Serializable { //// cv profile is separated in personal details (name,surname,phone etc.) and bio details(work experience, it skills, education etc.)
    private Map<String,Boolean> privacies;
    private boolean bioVisibility;
    private int percentageComplete;
    private BioInfo bInfo;
    private PersonalInfo pInfo;

    private static final long serialVersionUID = 1L;

    public CVProfile(Map<String,String> personalBioDetails,Pair<Map,Map> moreBioValues){   /// constructor with parameters the personal and bio details for now the only thing that we want is the privacy values
        privacies = new HashMap<>();
        Map<String,String> simpleValues = moreBioValues.first;
        Map<String,JSONArray> arrayValues = moreBioValues.second;
        JSONArray jsonArray = arrayValues.get(Settings.BIO_INFO.BioPermissions.class.getSimpleName()); /// get privacy values
        for(int i=0; i<jsonArray.length(); i++){
            try {
                JSONObject temp = (JSONObject) jsonArray.get(i);
                String key;
                privacies.put(key=temp.keys().next(),temp.get(key).equals("1"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }



        bioVisibility = simpleValues.get(Settings.BIO_INFO.VISIBILITY).equals("1"); /// set bio visibility
        privacies.put(Settings.BIO_INFO.VISIBILITY,bioVisibility);
        this.percentageComplete = Integer.parseInt(simpleValues.get(Settings.BIO_INFO.COMPLETE_PERCENT));

        bInfo = new BioInfo(simpleValues,arrayValues);
        pInfo = new PersonalInfo(personalBioDetails);
    }

    public Map<String,Boolean> getPrivacies(){
        return this.privacies;
    }

    public void setPrivacy(String key, Boolean value){
        this.privacies.put(key,value);
    }

    public void updatePrivacies(String jsonArrPrivacy){
        try {
            JSONArray jsonArray = new JSONArray(jsonArrPrivacy);
            for(int i=0; i<jsonArray.length(); i++){
                JSONObject privacy = jsonArray.getJSONObject(i);
                this.privacies.put(privacy.keys().next(),privacy.getString(privacy.keys().next()).equals("1"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void setBioVisibility(boolean v){
        this.bioVisibility = v;
        this.privacies.put(Settings.BIO_INFO.VISIBILITY,v);
    }

    public void setPrivacy(Boolean value){
        for(Map.Entry<String,Boolean> entryPrivacy : this.privacies.entrySet()){
            entryPrivacy.setValue(value);
        }
        this.bioVisibility = value;
    }

    private class BioInfo implements Serializable{  /// bio details
        private String id;
        private String title;
        private String interests;
        private String otherKnowledge;
        private List<BioAttributes> [] bioDetails ;
//        private List<BioAttributes> desiredWorkAreas;
//        private List<BioAttributes> desiredWorkPositions;
//        private List<BioAttributes> workExperiences;
//        private List<BioAttributes> workEducations;
//        private List<BioAttributes> foreignLanguages;
//        private List<BioAttributes> itSkills;
//        private List<BioAttributes> seminars;

        BioInfo(Map<String,String> simpleVal, Map<String,JSONArray> arrayVal){
            id = simpleVal.get(Settings.BIO_INFO.BIO_ID);
            title = simpleVal.get(Settings.BIO_INFO.TITLE);
            interests = simpleVal.get(Settings.BIO_INFO.INTERESTS);
            otherKnowledge = simpleVal.get(Settings.BIO_INFO.KNOWLEDGES);

            bioDetails = new List[7];
            for(int i=0; i<7; i++){
                bioDetails[i] = new ArrayList<>();
            }
//            desiredWorkAreas = new ArrayList<>();
//            desiredWorkPositions = new ArrayList<>();
//            workEducations = new ArrayList<>();
//            workExperiences = new ArrayList<>();
//            foreignLanguages = new ArrayList<>();
//            itSkills = new ArrayList<>();
//            seminars = new ArrayList<>();

            try{
                for(Map.Entry<String, JSONArray>  bioValues : arrayVal.entrySet()){
                    Log.e("KEYSBIO", bioValues.getKey());
                    Integer arrayPosition = Settings.BIO_INFO.attrToPos.get(bioValues.getKey());
                    if(arrayPosition != null) {
                        BioAttributes objAtrr = null;
                        switch (arrayPosition) {
                            case 0:
                                objAtrr = new WorkArea();
                                break;
                            case 1:
                                objAtrr = new WorkPosition();
                                break;
                            case 2:
                                objAtrr = new WorkExperience();
                                break;
                            case 3:
                                objAtrr = new WorkEducation();
                                break;
                            case 4:
                                objAtrr = new ForeignLanguage();
                                break;
                            case 5:
                                objAtrr = new ItAttribute();
                                break;
                            case 6:
                                objAtrr = new Seminar();
                                break;
                        }

                        for (int i = 0; i < bioValues.getValue().length(); i++) {
                            JSONObject obj = bioValues.getValue().getJSONObject(i);
                            objAtrr.setValues(obj);
                            this.bioDetails[Settings.BIO_INFO.attrToPos.get(bioValues.getKey())].add(objAtrr.clone());
                        }
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }

        }

        abstract class BioAttributes implements Serializable{
            String id = null;
            Boolean permission;

            BioAttributes(JSONObject jb){
                try {
                    this.id = jb.getString("Id");
                    // TODO set permission value
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            BioAttributes(){

            }

            BioAttributes(BioAttributes ba){
                this.id = ba.id;
                this.permission = ba.permission;
            }


            public abstract void setValues(JSONObject obj);
            public abstract BioAttributes clone();
        }

        class WorkArea extends BioAttributes{
            private String country, department, state, city, municipality;
            private String region;

            WorkArea(JSONObject values){
                super(values);
                this.setValues(values);
            }

            WorkArea(){
                super();
            }

            WorkArea(String c, String d, String s, String ci, String m){
                this.id = null;
                this.permission = null;
                this.country = c;
                this.department = d;
                this.state = s;
                this.city = ci;
                this.municipality = m;
            }

            WorkArea(WorkArea wa){
                super(wa);
                this.country = wa.country;
                this.department = wa.department;
                this.state = wa.state;
                this.city = wa.city;
                this.municipality = wa.municipality;
                this.region = wa.region;
            }

            @Override
            public void setValues(JSONObject values){
                try {
                    if(this.id == null)
                        this.id = values.getString(Settings.BIO_INFO.BIO_ID);

                    // TODO check permission value;
                    
                    this.country = values.getString(Settings.BIO_INFO.BioPrefAreas.COUNTRY);
                    this.department = values.getString(Settings.BIO_INFO.BioPrefAreas.DEPARTMENT);
                    this.state = values.getString(Settings.BIO_INFO.BioPrefAreas.STATE);
                    this.city = values.getString(Settings.BIO_INFO.BioPrefAreas.CITY);
                    this.municipality = values.getString(Settings.BIO_INFO.BioPrefAreas.MUNICIPALITY);

                    if(!municipality.equals("")){
                        this.region = municipality;
                    }
                    else if(!city.equals("")){
                        this.region = this.city;
                    }
                    else if(!state.equals("")){
                        this.region = state;
                    }
                    else if(!department.equals("")){
                        this.region = department;
                    }
                    else {
                        region = country;
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public WorkArea clone() {
                return new WorkArea(this);
            }
        }

        class WorkPosition extends BioAttributes {
            private String category, subcategory, position;

            WorkPosition(JSONObject values){
                super(values);
                this.setValues(values);
            }

            WorkPosition(){
                super();
            }

            WorkPosition(String c, String s, String p){
                this.id = null;
                this.permission = null;
                this.category = c;
                this.subcategory = s;
                this.position = p;
            }

            WorkPosition(WorkPosition wp){
                super(wp);
                this.category = wp.category;
                this.subcategory = wp.subcategory;
                this.position = wp.position;
            }

            @Override
            public void setValues(JSONObject obj) {
                try {
                    if(this.id == null)
                        this.id = obj.getString(Settings.BIO_INFO.BIO_ID);

                    // TODO check permission value;
                    
                    this.category = obj.getString(Settings.BIO_INFO.BioPrefPositions.CATEGORY);
                    this.subcategory = obj.getString(Settings.BIO_INFO.BioPrefPositions.SUBCATEGORY);
                    this.position = obj.getString(Settings.BIO_INFO.BioPrefPositions.JOBPOSITION);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public WorkPosition clone() {
                return new WorkPosition(this);
            }
        }

        class WorkExperience extends BioAttributes{
            private Date start, end;
            private boolean untilToday;
            private WorkPosition workPosition;
            private WorkArea workArea;
            private String levelPosition, company, employmentType, specialty, endStr;

            WorkExperience(JSONObject values){
                super(values);
                this.setValues(values);
            }

            WorkExperience(){
                super();
            }

            WorkExperience(WorkExperience we){
                super(we);
                this.start = new Date(we.start.getTime());
                if(we.end == null){
                    this.end = null;
                }
                else{
                    this.end = new Date(we.end.getTime());
                }

                this.endStr=we.endStr;
                this.untilToday = we.untilToday;
                this.levelPosition = we.levelPosition;
                this.company = we.company;
                this.employmentType = we.employmentType;
                this.specialty = we.specialty;
                workPosition = new WorkPosition(we.workPosition);
                workArea = new WorkArea(we.workArea);
            }

            @Override
            public void setValues(JSONObject obj) {
                try {
                    SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                    format.setLenient(false);
                    
                    if(this.id == null)
                        this.id = obj.getString(Settings.BIO_INFO.BIO_ID);

                    // TODO check permission value;
                    
                    this.start = format.parse(obj.getString(Settings.BIO_INFO.BioWorkExperience.START));
                    try {
                        this.end = format.parse(obj.getString(Settings.BIO_INFO.BioWorkExperience.END));
                        this.endStr = null;
                    }catch (ParseException pe){
                        this.end = null;
                        this.endStr = Skywalker.getContext().getResources().getString(R.string.until_today);
                    }
                    this.untilToday = obj.getString(Settings.BIO_INFO.BioWorkExperience.TODAY).equals("1");
                    this.levelPosition = obj.getString(Settings.BIO_INFO.BioWorkExperience.LEVEL);
                    this.company = obj.getString(Settings.BIO_INFO.BioWorkExperience.COMPANY);
                    this.employmentType = obj.getString(Settings.BIO_INFO.BioWorkExperience.EMP_TYPE);
                    this.specialty = obj.getString(Settings.BIO_INFO.BioWorkExperience.SPECIALITY);

                    workArea = new WorkArea(obj.getString(Settings.BIO_INFO.BioWorkExperience.WORK_COUNTRY),obj.getString(Settings.BIO_INFO.BioWorkExperience.WORK_DEPARTMENT),obj.getString(Settings.BIO_INFO.BioWorkExperience.WORK_STATE),
                            obj.getString(Settings.BIO_INFO.BioWorkExperience.WORK_CITY),obj.getString(Settings.BIO_INFO.BioWorkExperience.WORK_MUNICIPALITY));

                    workPosition = new WorkPosition(obj.getString(Settings.BIO_INFO.BioWorkExperience.CATEGORY),obj.getString(Settings.BIO_INFO.BioWorkExperience.SUBCATEGORY),obj.getString(Settings.BIO_INFO.BioWorkExperience.POSITION));
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public WorkExperience clone() {
                return new WorkExperience(this);
            }
        }

        class WorkEducation extends BioAttributes{
            private Date from, to;
            private String eduLevel, university, eduDepartment, eduTitle;

            WorkEducation(JSONObject values){
                super(values);
                this.setValues(values);
            }

            WorkEducation(){
                super();
            }
            
            WorkEducation(WorkEducation wed){
                super(wed);
                this.from = new Date(wed.from.getTime());
                this.to = new Date(wed.to.getTime());
                
                this.eduLevel = wed.eduLevel;
                this.university = wed.university;
                this.eduDepartment = wed.eduDepartment;
                this.eduTitle = wed.eduTitle;
            }

            @Override
            public void setValues(JSONObject obj) {
                SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                format.setLenient(false);
                
                try {
                    if (this.id == null) {
                        this.id = obj.getString(Settings.BIO_INFO.BIO_ID);
                    }

                    // TODO check permission value;
                    this.from = format.parse(obj.getString(Settings.BIO_INFO.BioEducation.FROM));
                    this.to = format.parse(obj.getString(Settings.BIO_INFO.BioEducation.TO));
                    
                    this.eduLevel = obj.getString(Settings.BIO_INFO.BioEducation.EDU_LEVEL);
                    this.university = obj.getString(Settings.BIO_INFO.BioEducation.UNIVERSITY);
                    this.eduDepartment = obj.getString(Settings.BIO_INFO.BioEducation.EDU_DEPARTMENT);
                    this.eduTitle = obj.getString(Settings.BIO_INFO.BioEducation.EDU_TITLE);
                    
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public WorkEducation clone() {
                return new WorkEducation(this);
            }
        }

        class ForeignLanguage extends BioAttributes{
            private String language, level, certificate;

            ForeignLanguage(JSONObject values){
                super(values);
                this.setValues(values);
            }

            ForeignLanguage(){
                super();
            }
            
            ForeignLanguage(ForeignLanguage fl){
                super(fl);
                this.language = fl.language;
                this.level = fl.level;
                this.certificate = fl.certificate;
            }

            @Override
            public void setValues(JSONObject obj) {
                try {
                    if (this.id == null) {
                        this.id = obj.getString(Settings.BIO_INFO.BIO_ID);
                    }
                    // TODO check permission value;
                    this.language = obj.getString(Settings.BIO_INFO.BioForeignLang.LANGUAGE);
                    this.level = obj.getString(Settings.BIO_INFO.BioForeignLang.LEVEL);
                    this.certificate = obj.getString(Settings.BIO_INFO.BioForeignLang.CERTIFICATE);
                    
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public ForeignLanguage clone() {
                return new ForeignLanguage(this);
            }
        }

        class ItAttribute extends BioAttributes{
            private String it_category, software, level;

            ItAttribute(JSONObject values){
                super(values);
                this.setValues(values);
            }

            ItAttribute(){
                super();
            }
            
            ItAttribute(ItAttribute is){
                super(is);
                this.it_category = is.it_category;
                this.software = is.software;
                this.level = is.level;
            }

            @Override
            public void setValues(JSONObject obj) {
                try {
                    if (this.id == null) {
                        this.id = obj.getString(Settings.BIO_INFO.BIO_ID);
                    }
                    // TODO check permission value;
                    this.it_category = obj.getString(Settings.BIO_INFO.BioItSkills.IT_CATEGORY);
                    this.software = obj.getString(Settings.BIO_INFO.BioItSkills.IT_SKILL);
                    this.level = obj.getString(Settings.BIO_INFO.BioItSkills.LEVEL);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public ItAttribute clone() {
                return new ItAttribute(this);
            }
        }

        class Seminar extends BioAttributes{
            private String name,operator, certificate;
            Date date;

            Seminar(JSONObject values){
                super(values);
                this.setValues(values);
            }

            Seminar(){
                super();
            }

            Seminar(Seminar s){
                super(s);
                this.date = new Date(s.date.getTime());
                this.name = s.name;
                this.operator = s.operator;
                this.certificate = s.certificate;
            }

            @Override
            public void setValues(JSONObject obj) {

                try {
                    if (this.id == null) {
                        this.id = obj.getString(Settings.BIO_INFO.BIO_ID);
                    }
                    SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                    format.setLenient(false);
                    // TODO check permission value;
                    this.date = format.parse(Settings.BIO_INFO.BioSeminars.DATE);
                    this.name = obj.getString(Settings.BIO_INFO.BioSeminars.NAME);
                    this.operator = obj.getString(Settings.BIO_INFO.BioSeminars.FOREAS);
                    this.certificate = obj.getString(Settings.BIO_INFO.BioSeminars.CARTIFICATE);


                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public Seminar clone() {
                return new Seminar(this);
            }
        }
    }

    private class PersonalInfo implements Serializable{ //// personal details
        private String name;
        private String surname;
        private String phone;
        private String mobilePhone;
        private String email;
        private String country;
        private boolean changeMail;
        private String address;
        private String photoUrl;
        private Date birthDate;
        private String sex;
        private String armyStatus;
        private String drivingStatus;
        private String travelInternal;
        private String relocateStatus;
        private String homeDistance;
        private String linkedInUrl;
        private String portfolioUrl;
        private String videoUrl;

        PersonalInfo(Map<String,String> details){
            this.name = details.get(Settings.PersonalInfo.NAME);
            this.surname = details.get(Settings.PersonalInfo.SURNAME);
            this.phone = details.get(Settings.PersonalInfo.PHONE);
            this.mobilePhone = details.get(Settings.PersonalInfo.MOB_PHONE);
            this.email = details.get(Settings.PersonalInfo.EMAIL);
            this.country = details.get(Settings.REGIONS.JSON_COUNTRY_TYPE);
            this.changeMail = details.get(Settings.PersonalInfo.CHANGEMAIL).equals("1");
            this.address = details.get(Settings.PersonalInfo.ADDRESS);
            this.photoUrl = details.get(Settings.PersonalInfo.PHOTO);
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            format.setLenient(false);
            try {
                this.birthDate = format.parse(details.get(Settings.PersonalInfo.BIRTH_DATE));
            } catch (ParseException e) {
                e.printStackTrace();
                this.birthDate = null;
            }

            this.sex = details.get(Settings.PersonalInfo.SEX);
            this.armyStatus = details.get(Settings.PersonalInfo.ARMY_SERVICES);
            this.drivingStatus = details.get(Settings.PersonalInfo.DRIVING_LICENSE);
            this.travelInternal = details.get(Settings.PersonalInfo.TRAVEL_INTERNAL);
            this.relocateStatus = details.get(Settings.PersonalInfo.RELOACATE);
            this.homeDistance = details.get(Settings.PersonalInfo.HOME_DISTANCE);
            this.linkedInUrl = details.get(Settings.PersonalInfo.LINKED_IN);
            this.portfolioUrl = details.get(Settings.PersonalInfo.PORTFOLIO);
            this.videoUrl = details.get(Settings.PersonalInfo.CV_VIDEO);


        }
    }
}
