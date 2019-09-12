package com.sky.casper.skywalker_new_app.Helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.sky.casper.skywalker_new_app.Models.Advert;
import com.sky.casper.skywalker_new_app.Models.Category;
import com.sky.casper.skywalker_new_app.Models.CertificationForeignLang;
import com.sky.casper.skywalker_new_app.Models.City;
import com.sky.casper.skywalker_new_app.Models.Country;
import com.sky.casper.skywalker_new_app.Models.EduType;
import com.sky.casper.skywalker_new_app.Models.EmpType;
import com.sky.casper.skywalker_new_app.Models.ForeignLang;
import com.sky.casper.skywalker_new_app.Models.GeoDepartment;
import com.sky.casper.skywalker_new_app.Models.ItCategory;
import com.sky.casper.skywalker_new_app.Models.ItSkill;
import com.sky.casper.skywalker_new_app.Models.JobPosition;
import com.sky.casper.skywalker_new_app.Models.Municipality;
import com.sky.casper.skywalker_new_app.Models.State;
import com.sky.casper.skywalker_new_app.Models.SubCategory;
import com.sky.casper.skywalker_new_app.Models.Type;
import com.sky.casper.skywalker_new_app.Models.University;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class DatabaseHelper extends SQLiteOpenHelper {
    private SQLiteDatabase myDataBase;
    private static final int DATABSE_VERSION=9;
    private static String DEFAULT_DB_PATH = "";

    private static final String DATABASE_NAME = "Skywalker.db";
    private final Context mContext;

    /* Tables properties*/

    private static final String TABLE_CATEGORIES = "categories";
    private static final String CATEGORY_ID = "ID_CATEGORY";
    private static final String CATEGORY_TITLE="TITLE_CATEGORY";
    private static final String CREATE_CATEGORIES_TABLE = "CREATE TABLE "
            + TABLE_CATEGORIES +" ("
            + CATEGORY_ID +" TEXT PRIMARY KEY, "
            + CATEGORY_TITLE + " TEXT)";

    private static final String TABLE_SUBCATEGORIES = "subcategories";
    private static final String SUBCATEGORY_ID = "ID_SUBCATEGORY";
    private static final String SUBCATEGORY_TITLE = "TITLE_SUBCATEGORY";
    private static final String SUBCATEGORY_PARENT_CATEGORY = "PARENT_CATEGORY_ID";
    private static final String CREATE_SUBCATEGORIES_TABLE = "CREATE TABLE "
            + TABLE_SUBCATEGORIES + " ("
            + SUBCATEGORY_ID + " TEXT PRIMARY KEY, "
            + SUBCATEGORY_TITLE +" TEXT, "
            + SUBCATEGORY_PARENT_CATEGORY +" TEXT, "
            +"FOREIGN KEY("+ SUBCATEGORY_PARENT_CATEGORY +") REFERENCES "+TABLE_CATEGORIES +"( "+CATEGORY_ID+" ))";

    private static final String TABLE_JOBPOSITIONS = "jobpositions";
    private static final String JOBPOSITION_ID = "ID_JOBPOSITION";
    private static final String JOBPOSITION_TITLE = "TITLE_JOBPOSITION";
    private static final String JOBPOSITION_PARENT_SUBCATEGORY = "PARENT_SUBCATEGORY_ID";
    private static final String CREATE_JOBPOSITIONS_TABLE = "CREATE TABLE "
            + TABLE_JOBPOSITIONS + " ("
            + JOBPOSITION_ID + " TEXT PRIMARY KEY, "
            + JOBPOSITION_TITLE +" TEXT, "
            + JOBPOSITION_PARENT_SUBCATEGORY +" TEXT, "
            +"FOREIGN KEY("+JOBPOSITION_PARENT_SUBCATEGORY+") REFERENCES "+TABLE_SUBCATEGORIES+"( "+SUBCATEGORY_ID+" ))";

    private static final String TABLE_COUNTRIES = "countries";
    private static final String COUNTRY_ID = "ID_COUNTRY";
    private static final String COUNTRY_TITLE = "TITLE_COUNTRY";
    private static final String CREATE_COUNTRIES_TABLE = "CREATE TABLE "
            + TABLE_COUNTRIES + "("
            + COUNTRY_ID + " TEXT PRIMARY KEY, "
            + COUNTRY_TITLE +" TEXT)";

    private static final String TABLE_GEODEPARTMENT = "geographicaldepartment";
    private static final String GEO_ID = "ID_GEO";
    private static final String GEO_TITLE = "TITLE_GEO";
    private static final String GEO_PARENT_COUNTRY = "PARENT_COUNTRY_ID";
    private static final String CREATE_GEODEPARTMENT_TABLE = "CREATE TABLE "
            + TABLE_GEODEPARTMENT + " ("
            + GEO_ID + " TEXT PRIMARY KEY, "
            + GEO_TITLE + " TEXT, "
            + GEO_PARENT_COUNTRY + " TEXT, "
            +"FOREIGN KEY("+GEO_PARENT_COUNTRY+") REFERENCES "+TABLE_COUNTRIES+"( "+COUNTRY_ID+" ))";

    private static final String TABLE_STATES = "states";
    private static final String STATE_ID = "ID_STATE";
    private static final String STATE_TITLE = "TITLE_STATE";
    private static final String STATE_PARENT_GEODEPARTMENT = "PARENT_GEODEPARTMENT_ID";
    private static final String CREATE_STATES_TABLE = "CREATE TABLE "
            + TABLE_STATES + " ("
            + STATE_ID + " TEXT PRIMARY KEY, "
            + STATE_TITLE +" TEXT, "
            + STATE_PARENT_GEODEPARTMENT + " TEXT, "
            + "FOREIGN KEY("+STATE_PARENT_GEODEPARTMENT+") REFERENCES "+TABLE_GEODEPARTMENT+"( "+GEO_ID+" ))";

    private static final String TABLE_CITIES = "cities";
    private static final String CITY_ID = "ID_CITY";
    private static final String CITY_TITLE = "TITLE_CITY";
    private static final String CITY_PARENT_STATE = "PARENT_STATE_ID";
    private static final String CREATE_CITY_TABLE = "CREATE TABLE "
            + TABLE_CITIES + " ("
            + CITY_ID + " TEXT PRIMARY KEY, "
            + CITY_TITLE + " TEXT, "
            + CITY_PARENT_STATE + " TEXT, "
            + "FOREIGN KEY("+CITY_PARENT_STATE+") REFERENCES "+TABLE_STATES+"( "+STATE_ID+" ))";

    private static final String TABLE_MUNICIPALITIES = "municipalities";
    private static final String MUNICIPALITY_ID = "ID_MUNICIPALITY";
    private static final String MUNICIPALITY_TITLE = "TITLE_MUNICIPALITY";
    private static final String MUNICIPALITY_PARENT_CITY = "PARENT_CITY_ID";
    private static final String CREATE_MUNICIPALITY_TABLE = "CREATE TABLE "
            + TABLE_MUNICIPALITIES + " ("
            + MUNICIPALITY_ID + " TEXT PRIMARY KEY, "
            + MUNICIPALITY_TITLE + " TEXT, "
            + MUNICIPALITY_PARENT_CITY + " TEXT, "
            + "FOREIGN KEY("+MUNICIPALITY_PARENT_CITY+") REFERENCES "+TABLE_CITIES+"( "+CITY_ID+" ))";

    private static final String TABLE_EMPTYPE = "emptype";
    private static final String EMPTYPE_ID = "ID_EMPTYPE";
    private static final String EMPTYPE_TITLE = "TITLE_EMPTYPE";
    private static final String CREATE_EMPTYPE_TABLE = "CREATE TABLE "
            + TABLE_EMPTYPE + " ("
            + EMPTYPE_ID + " TEXT PRIMARY KEY, "
            + EMPTYPE_TITLE + " TEXT)";

    private static final String TABLE_UNIVERSITY = "university";
    private static final String UNIVERSITY_ID = "ID_UNIVERSITY";
    private static final String UNIVERSITY_TITLE = "TITLE_UNIVERSITY";
    private static final String UNIVERSITY_LEVEL = "EDUCATION_LEVEL";
    private static final String CREATE_UNIVERSITY_TABLE = "CREATE TABLE "
            + TABLE_UNIVERSITY + " ("
            + UNIVERSITY_ID + " TEXT PRIMARY KEY, "
            + UNIVERSITY_TITLE + " TEXT, "
            + UNIVERSITY_LEVEL + " TEXT)";

    private static final String TABLE_EDUTYPE = "edutype";
    private static final String EDUTYPE_ID = "ID_EDUTYPE";
    private static final String EDUTYPE_TITLE = "TITLE_EDUTITLE";
    private static final String CREATE_EDUTYPE_TABLE = "CREATE TABLE "
            + TABLE_EDUTYPE + " ("
            + EDUTYPE_ID + " TEXT PRIMARY KEY, "
            + EDUTYPE_TITLE + " TEXT)";

    private static final String TABLE_LANGUAGE = "languages";
    private static final String LANG_ID = "ID_LANGUAGE";
    private static final String LANG_TITLE = "TITLE_LANGUAGE";
    private static final String CREATE_LANGUAGE_TABLE = "CREATE TABLE "
            + TABLE_LANGUAGE + " ("
            + LANG_ID + " TEXT PRIMARY KEY, "
            + LANG_TITLE + " TEXT)";

    private static final String TABLE_LANG_CERTIFICATION = "lang_certification";
    private static final String CERT_ID = "ID_CERTIFICATION";
    private static final String CERT_TITLE = "TITLE_CERTIFICATION";
    private static final String LANGUAGE = "LANGUAGE_ID";
    private static final String CREATE_CERTIFICATION_TABLE = "CREATE TABLE "
            + TABLE_LANG_CERTIFICATION +" ("
            + CERT_ID + " TEXT PRIMARY KEY, "
            + CERT_TITLE + " TEXT, "
            + LANGUAGE + " TEXT)";

    private static final String TABLE_IT_CATEGORY = "it_category";
    private static final String ITCATEGORY_ID = "ID_ITCATEGORY";
    private static final String ITCATEGORY_TITLE = "TITLE_ITCATEGORY";
    private static final String CREATE_ITCATEGORY_TABLE = "CREATE TABLE "
            + TABLE_IT_CATEGORY + " ("
            + ITCATEGORY_ID + " TEXT PRIMARY KEY, "
            + ITCATEGORY_TITLE + " TEXT)";

    private static final String TABLE_IT_SKILL = "it_skill";
    private static final String ITSKILL_ID = "ID_ITSKILL";
    private static final String ITSKILL_TITLE = "TITLE_ITSKILL";
    private static final String ITSKILL_CATEGORY = "ITCATEGORY_TITLE";
    private static final String CREATE_ITSKILL_TABLE = "CREATE TABLE "
            + TABLE_IT_SKILL + " ("
            + ITSKILL_ID + " TEXT PRIMARY KEY, "
            + ITSKILL_TITLE + " TEXT, "
            + ITSKILL_CATEGORY + " TEXT)";

    private static final String TABLE_SAVED_ADS = "saved_ads";
    private static final String SAVED_ID = "ID_AD";
    private static final String SAVED_TITLE = "TITLE_AD";
    private static final String SAVED_PUBLISH_DATE = "PUBLISH_DATE_AD";
    private static final String SAVED_TEXT = "TEXT_AD";
    private static final String SAVED_EMP_TYPE = "EMP_TYPE_AD";
    private static final String SAVED_IS_ANONYMOUS = "IS_ANONYMOUS_AD";
    private static final String SAVED_REGION = "REGION_AD";
    private static final String SAVED_CLIENT = "CLIENT_AD";
    private static final String SAVED_IMAGE = "IMAGE_AD";
    private static final String SAVED_DATE = "DATE_AD";
    private static final String SAVED_ADCODE = "CODE_AD";
    private static final String SAVED_URL = "URL_AD";
    private static final String CREATE_SAVED_ADS_TABLE = "CREATE TABLE "
            + TABLE_SAVED_ADS + " ("
            + SAVED_ID + " TEXT PRIMARY KEY, "
            + SAVED_TITLE + " TEXT, "
            + SAVED_PUBLISH_DATE + " TEXT, "
            + SAVED_TEXT + " TEXT, "
            + SAVED_EMP_TYPE + " TEXT, "
            + SAVED_IS_ANONYMOUS + " TEXT, "
            + SAVED_REGION + " TEXT, "
            + SAVED_CLIENT + " TEXT, "
            + SAVED_IMAGE + " TEXT, "
            + SAVED_ADCODE + " TEXT, "
            + SAVED_URL + " TEXT, "
            + SAVED_DATE + " DATETIME DEFAULT CURRENT_TIMESTAMP)";


    private static final String TABLE_SEND_CV_ADS = "send_cv_ads";
    private static final String SEND_CV_ID = "ID_AD";
    private static final String SEND_CV_TITLE = "TITLE_AD";
    private static final String SEND_CV_PUBLISH_DATE = "PUBLISH_DATE_AD";
    private static final String SEND_CV_TEXT = "TEXT_AD";
    private static final String SEND_CV_EMP_TYPE = "EMP_TYPE_AD";
    private static final String SEND_CV_IS_ANONYMOUS = "IS_ANONYMOUS_AD";
    private static final String SEND_CV_REGION = "REGION_AD";
    private static final String SEND_CV_CLIENT = "CLIENT_AD";
    private static final String SEND_CV_IMAGE = "IMAGE_AD";
    private static final String SEND_CV_DATE = "DATE_AD";
    private static final String SEND_CV_ADCODE = "CODE_AD";
    private static final String SEND_CV_URL = "URL_AD";
    private static final String CREATE_SEND_CV_ADS = "CREATE TABLE "
            + TABLE_SEND_CV_ADS + " ("
            + SEND_CV_ID + " TEXT PRIMARY KEY, "
            + SEND_CV_TITLE + " TEXT, "
            + SEND_CV_PUBLISH_DATE + " TEXT, "
            + SEND_CV_TEXT +" TEXT, "
            + SEND_CV_EMP_TYPE + " TEXT, "
            + SEND_CV_IS_ANONYMOUS + " TEXT, "
            + SEND_CV_REGION + " TEXT, "
            + SEND_CV_CLIENT + " TEXT, "
            + SEND_CV_IMAGE + " TEXT, "
            + SEND_CV_ADCODE + " TEXT, "
            + SEND_CV_URL +" TEXT, "
            + SEND_CV_DATE + " DATETIME DEFAULT CURRENT_TIMESTAMP)";





    private static final String TABLE_SETTINGS = "settings";
    private static final String USER_ID = "ID_LOGIN";
    private static final String CREATE_SETTINGS_TABLE = "CREATE TABLE "
            + TABLE_SETTINGS +" ("
            + USER_ID +" TEXT PRIMARY KEY)";


    /* construct the app's database */
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABSE_VERSION);
        if(android.os.Build.VERSION.SDK_INT >= 17){
            DEFAULT_DB_PATH = context.getApplicationInfo().dataDir + "/databases/";
        }
        else
        {
            DEFAULT_DB_PATH = "/data/data/" + context.getPackageName() + "/databases/";
        }
        mContext = context;
        try {
            createDataBase();
            openDataBase();
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("DB","Not copying db");
        }

    }

    @Override /* built-in function which handle different procedures while opening the database */
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        db.disableWriteAheadLogging();
    }

    public void createDataBase() throws IOException {

        boolean dbExist = checkDataBase();
        Log.e("DB exist","gyrnaei "+dbExist);

        if(dbExist){
            //do nothing - database already exist
        }else{

            //By calling this method and empty database will be created into the default system path
            //of your application so we are gonna be able to overwrite that database with our database.
            try{
                this.getReadableDatabase();



                copyDataBase();


            } catch (IOException e) {

                e.printStackTrace();
                Log.e("DB Erroor","Den ginetai copy");

            } catch (Exception e1){
                Log.e("Error","Allo error");
            }
        }

    }

    /* finds the if the database exist in the device space*/
    private boolean checkDataBase()
    {
        SQLiteDatabase checkDB = null;

        try{
            String myPath = DEFAULT_DB_PATH + DATABASE_NAME;
            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);

        }catch(SQLiteException e){

            //database does't exist yet.

        }

        if(checkDB != null){

            checkDB.close();

        }

        return checkDB != null ? true : false;
    }

    private void copyDataBase() throws Exception{

        //Open your local db as the input stream
        InputStream myInput = mContext.getAssets().open(DATABASE_NAME);

        // Path to the just created empty db
        String outFileName = DEFAULT_DB_PATH + DATABASE_NAME;

        //Open the empty db as the output stream
        OutputStream myOutput = new FileOutputStream(outFileName);


        //transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
            Log.e("CP","copy db2 "+length);
        }

        //Close the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();

    }

    /* Create Tables */
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
//        sqLiteDatabase.execSQL(CREATE_CATEGORIES_TABLE);
//        sqLiteDatabase.execSQL(CREATE_SUBCATEGORIES_TABLE);
//        sqLiteDatabase.execSQL(CREATE_JOBPOSITIONS_TABLE);
//        sqLiteDatabase.execSQL(CREATE_SETTINGS_TABLE);
//        sqLiteDatabase.execSQL(CREATE_COUNTRIES_TABLE);
//        sqLiteDatabase.execSQL(CREATE_GEODEPARTMENT_TABLE);
//        sqLiteDatabase.execSQL(CREATE_STATES_TABLE);
//        sqLiteDatabase.execSQL(CREATE_CITY_TABLE);
//        sqLiteDatabase.execSQL(CREATE_MUNICIPALITY_TABLE);
//        sqLiteDatabase.execSQL(CREATE_EMPTYPE_TABLE);
//        sqLiteDatabase.execSQL(CREATE_UNIVERSITY_TABLE);
//        sqLiteDatabase.execSQL(CREATE_EDUTYPE_TABLE);
//        sqLiteDatabase.execSQL(CREATE_LANGUAGE_TABLE);
//        sqLiteDatabase.execSQL(CREATE_CERTIFICATION_TABLE);
//        sqLiteDatabase.execSQL(CREATE_ITCATEGORY_TABLE);
//        sqLiteDatabase.execSQL(CREATE_ITSKILL_TABLE);
//        sqLiteDatabase.execSQL(CREATE_SAVED_ADS_TABLE);
//        sqLiteDatabase.execSQL(CREATE_SEND_CV_ADS);
    }

    public boolean insertDB(Type values){
//        SQLiteDatabase myDataBase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        String table="";
        if(values.getType().equals(Settings.JOB_CATEGORIES.JSON_CATEGORY_TYPE)) {
            Category category = (Category) values;
            contentValues.put(CATEGORY_ID, category.getId());
            contentValues.put(CATEGORY_TITLE, category.getTitle());
            table = TABLE_CATEGORIES;
        }
        else if(values.getType().equals(Settings.JOB_CATEGORIES.JSON_SUBCATEOGORY_TYPE)){
            SubCategory subCategory = (SubCategory) values;
            contentValues.put(SUBCATEGORY_ID,subCategory.getId());
            contentValues.put(SUBCATEGORY_TITLE,subCategory.getTitle());
            contentValues.put(SUBCATEGORY_PARENT_CATEGORY,subCategory.getParent_id());
            table = TABLE_SUBCATEGORIES;
        }
        else if(values.getType().equals(Settings.JOB_CATEGORIES.JSON_JOBPOSITION_TYPE)){
            JobPosition jobPosition = (JobPosition) values;
            contentValues.put(JOBPOSITION_ID,jobPosition.getId());
            contentValues.put(JOBPOSITION_TITLE,jobPosition.getTitle());
            contentValues.put(JOBPOSITION_PARENT_SUBCATEGORY,jobPosition.getSubcategory_id());
            table = TABLE_JOBPOSITIONS;
        }
        else if(values.getType().equals(Settings.REGIONS.JSON_COUNTRY_TYPE)){
            Country country = (Country) values;
            contentValues.put(COUNTRY_ID,country.getId());
            contentValues.put(COUNTRY_TITLE,country.getTitle());
            table = TABLE_COUNTRIES;
        }
        else if(values.getType().equals(Settings.REGIONS.JSON_GEO_DEP_TYPE)){
            GeoDepartment geoDepartment = (GeoDepartment) values;
            contentValues.put(GEO_ID,geoDepartment.getId());
            contentValues.put(GEO_TITLE,geoDepartment.getTitle());
            contentValues.put(GEO_PARENT_COUNTRY,geoDepartment.getCountry_id());
            table = TABLE_GEODEPARTMENT;
        }
        else if(values.getType().equals(Settings.REGIONS.JSON_STATE_TYPE)){
            State state = (State) values;
            contentValues.put(STATE_ID,state.getId());
            contentValues.put(STATE_TITLE,state.getTitle());
            contentValues.put(STATE_PARENT_GEODEPARTMENT,state.getGeoDepar_id());
            table = TABLE_STATES;
        }
        else if(values.getType().equals(Settings.REGIONS.JSON_CITY_TYPE)){
            City city = (City) values;
            contentValues.put(CITY_ID,city.getId());
            contentValues.put(CITY_TITLE,city.getTitle());
            contentValues.put(CITY_PARENT_STATE,city.getState_id());
            table = TABLE_CITIES;
        }
        else if(values.getType().equals(Settings.REGIONS.JSON_MUNICIPALITY_TYPE)){
            Municipality municipality = (Municipality) values;
            contentValues.put(MUNICIPALITY_ID,municipality.getId());
            contentValues.put(MUNICIPALITY_TITLE,municipality.getTitle());
            contentValues.put(MUNICIPALITY_PARENT_CITY,municipality.getCity_id());
            table = TABLE_MUNICIPALITIES;
        }
        else if(values.getType().equals(Settings.EMPLOYE.JSON_EMPTYPE_TYPE)){
            EmpType empType = (EmpType) values;
            contentValues.put(EMPTYPE_ID,empType.getId());
            contentValues.put(EMPTYPE_TITLE,empType.getTitle());
            table = TABLE_EMPTYPE;
        }
        else if(values.getType().equals(Settings.UNIVERSITY.JSON_UNIVERSITY_TYPE)){
            University university = (University) values;
            contentValues.put(UNIVERSITY_ID,university.getId());
            contentValues.put(UNIVERSITY_TITLE,university.getTitle());
            contentValues.put(UNIVERSITY_LEVEL,university.getEduLevel());
            table = TABLE_UNIVERSITY;
        }
        else if(values.getType().equals(Settings.EDUCATION.JSON_EDUCATION_TYPE)){
            EduType eduType = (EduType) values;
            contentValues.put(EDUTYPE_ID,eduType.getId());
            contentValues.put(EDUTYPE_TITLE,eduType.getTitle());
            table = TABLE_EDUTYPE;
        }
        else if(values.getType().equals(Settings.FOREIGN_LANG.JSON_FOREIGN_LANG_TYPE)){
            ForeignLang foreignLang = (ForeignLang) values;
            contentValues.put(LANG_ID,foreignLang.getId());
            contentValues.put(LANG_TITLE,foreignLang.getTitle());
            table = TABLE_LANGUAGE;
        }
        else if(values.getType().equals(Settings.CERTIFICATION_LANG.JSON_CERTIFICATION_LANG_TYPE)){
            CertificationForeignLang certificationForeignLang = (CertificationForeignLang) values;
            contentValues.put(CERT_ID,certificationForeignLang.getId());
            contentValues.put(CERT_TITLE,certificationForeignLang.getTitle());
            contentValues.put(LANGUAGE,certificationForeignLang.getParent_id());
            table = TABLE_LANG_CERTIFICATION;
        }
        else if(values.getType().equals(Settings.IT_CATEGORY.JSON_ITCATEGORY_TYPE)){
            ItCategory itCategory = (ItCategory) values;
            contentValues.put(ITCATEGORY_ID,itCategory.getId());
            contentValues.put(ITCATEGORY_TITLE,itCategory.getTitle());
            table = TABLE_IT_CATEGORY;
        }
        else if(values.getType().equals(Settings.IT_SKILL.JSON_ITSKILL_TYPE)){
            ItSkill itSkill = (ItSkill) values;
            contentValues.put(ITSKILL_ID,itSkill.getId());
            contentValues.put(ITSKILL_TITLE,itSkill.getTitle());
            contentValues.put(ITSKILL_CATEGORY,itSkill.getParent_id());
            table = TABLE_IT_SKILL;
        }
        else if (values.getType().equals(Settings.URLS.LOGIN_CANDIDATE)){
            String id = values.getId();
            contentValues.put(USER_ID,id);
            table=TABLE_SETTINGS;
        }
        long result = myDataBase.insert(table,null,contentValues);

        if(result==-1){
            return false;
        }
        else{
            return true;
        }
    }

    public boolean send_cv(Advert ad){
//        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        long result;

        String countQuery = "SELECT * FROM " + TABLE_SEND_CV_ADS;
        Cursor cursor = myDataBase.rawQuery(countQuery,null);
        int cnt  = cursor.getCount();

        if(cnt>=40){
            myDataBase.delete(TABLE_SEND_CV_ADS,SEND_CV_DATE+" = ( SELECT min("+SEND_CV_DATE+") FROM "+TABLE_SEND_CV_ADS+")",null);
        }

        contentValues.put(SEND_CV_ID,ad.getId());
        contentValues.put(SEND_CV_DATE,Settings.getDateTime());
        contentValues.put(SEND_CV_CLIENT,ad.getClientId());
        contentValues.put(SEND_CV_IMAGE,ad.getImage_file());
        contentValues.put(SEND_CV_EMP_TYPE,ad.getEmpType());
        contentValues.put(SEND_CV_REGION,ad.getRegion());
        contentValues.put(SEND_CV_PUBLISH_DATE,ad.getDate());
        contentValues.put(SEND_CV_IS_ANONYMOUS,ad.isAnonymous());
        contentValues.put(SEND_CV_TEXT,ad.getText());
        contentValues.put(SEND_CV_TITLE,ad.getTitle());
        contentValues.put(SEND_CV_ADCODE,ad.getAd_code());
        contentValues.put(SEND_CV_URL,ad.getImage_url());

        try{
            result = myDataBase.insert(TABLE_SEND_CV_ADS,null,contentValues);

        }
        catch (Exception e){
            result = 1;
        }

        if(result == 1){
            return false;
        }
        else{
            return true;
        }
    }

    public boolean saveAd(Advert ad){
//        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        long result;

        String countQuery = "SELECT * FROM " + TABLE_SAVED_ADS;
        Cursor cursor = myDataBase.rawQuery(countQuery,null);
        int cnt  = cursor.getCount();

        contentValues.put(SAVED_ID, ad.getId());
        contentValues.put(SAVED_DATE, Settings.getDateTime());
        contentValues.put(SAVED_CLIENT, ad.getClientId());
        contentValues.put(SAVED_IMAGE, ad.getImage_file());
        contentValues.put(SAVED_EMP_TYPE, ad.getEmpType());
        contentValues.put(SAVED_REGION, ad.getRegion());
        contentValues.put(SAVED_PUBLISH_DATE, ad.getDate());
        contentValues.put(SAVED_IS_ANONYMOUS, ad.isAnonymous());
        contentValues.put(SAVED_TEXT, ad.getText());
        contentValues.put(SAVED_TITLE, ad.getTitle());
        contentValues.put(SAVED_ADCODE,ad.getAd_code());
        contentValues.put(SAVED_URL,ad.getImage_url());

        if(cnt>=40) {
            myDataBase.delete(TABLE_SAVED_ADS,SAVED_DATE+" = ( SELECT min("+SAVED_DATE+") FROM "+TABLE_SAVED_ADS+")",null);
        }


        try {
            result = myDataBase.insert(TABLE_SAVED_ADS, null, contentValues);

        } catch (Exception e) {
            result = 1;
        }

        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }


    public boolean deleteOldSendings(){
//        SQLiteDatabase myDataBase = this.getWritableDatabase();
        Calendar theEnd = Calendar.getInstance();
        Calendar theStart = (Calendar) theEnd.clone();

        theStart.add(Calendar.DAY_OF_MONTH, -1);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String start = dateFormat.format(theStart.getTime());
        String end = dateFormat.format(theEnd.getTime());

        Log.e("DATABASECALENDARSEND",start+" "+end);

        return myDataBase.delete(TABLE_SEND_CV_ADS,"NOT ("+SEND_CV_DATE + " >= date('now','-7 day'))",null) > 0;

    }

    public boolean deleteOldAds(){
//        SQLiteDatabase myDataBase = this.getWritableDatabase();
        Calendar theEnd = Calendar.getInstance();
        Calendar theStart = (Calendar) theEnd.clone();


        theStart.add(Calendar.DAY_OF_MONTH, -1);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String start = dateFormat.format(theStart.getTime());
        String end = dateFormat.format(theEnd.getTime());



        Log.e("DATABASECALENDARhfjh",start+" "+end);

//        return db.delete(TABLE_SAVED_ADS,SAVED_DATE + " BETWEEN "+start+" AND "+end,null) > 0;
        return myDataBase.delete(TABLE_SAVED_ADS,"NOT ("+SAVED_DATE + " >= date('now','-7 day'))",null) > 0;
    }


    public boolean isSaved(String id){
//        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res=null;

        res = myDataBase.rawQuery("select "+SAVED_DATE+" from "+TABLE_SAVED_ADS+" where "+SAVED_ID+" = ? ",new String[]{id});

        if(res.getCount()>0){
            return true;
        }
        else{
            return false;
        }
    }

    public boolean deleteSaved(String id){
        return  myDataBase.delete(TABLE_SAVED_ADS,SAVED_ID+"=?",new String[]{id}) > 0;
    }

    public boolean deleteSendCv(String id){
        return  myDataBase.delete(TABLE_SEND_CV_ADS,SEND_CV_ID+"=?",new String[]{id}) > 0;
    }

    public Advert[] getSendCvAds(){
//        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res=null;
        String isAnonymous;

        res = myDataBase.rawQuery("select "+SEND_CV_ID+", "+SEND_CV_TITLE+", "+SEND_CV_TEXT+", "+SEND_CV_PUBLISH_DATE+", "+SEND_CV_EMP_TYPE+", "+SEND_CV_IMAGE+", "+SEND_CV_IS_ANONYMOUS+", "+SEND_CV_CLIENT+", "+SEND_CV_REGION+", "+SEND_CV_ADCODE+", "+SEND_CV_URL
                +" from "+TABLE_SEND_CV_ADS,null);
        Advert[] ads = new Advert[res.getCount()];
        int i=0;
        while (res.moveToNext()){
            isAnonymous = res.getString(6);
            ads[i]=new Advert(res.getString(2),res.getString(10),res.getString(1),res.getString(0),res.getString(4),res.getString(8),res.getString(3),res.getString(7)
                    ,null,isAnonymous.equals("true") || isAnonymous.equals("TRUE") || isAnonymous.equals("True") || isAnonymous.equals("1"),null,res.getString(9),null);
            ads[i].setImage_file(res.getString(5));
            i++;
        }

        return ads;
    }

    public Advert[] getSavedAds(){
//        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res=null;
        String isAnonymous;

        res = myDataBase.rawQuery("select "+SAVED_ID+", "+SAVED_TITLE+", "+SAVED_TEXT+", "+SAVED_PUBLISH_DATE+", "+SAVED_EMP_TYPE+", "+SAVED_IMAGE+", "+SAVED_IS_ANONYMOUS+", "+SAVED_CLIENT+", "+SAVED_REGION+", "+SAVED_ADCODE+", "+SAVED_URL
                +" from "+TABLE_SAVED_ADS,null);
        Advert[] ads = new Advert[res.getCount()];
        int i=0;
        while (res.moveToNext()){
            isAnonymous = res.getString(6);
            Log.e("ISANONYMOUS",isAnonymous);
            ads[i]=new Advert(res.getString(2),res.getString(10),res.getString(1),res.getString(0),res.getString(4),res.getString(8),res.getString(3),res.getString(7)
                    ,null,isAnonymous.equals("true") || isAnonymous.equals("TRUE") || isAnonymous.equals("True") || isAnonymous.equals("1"),null,res.getString(9),null);
            ads[i].setImage_file(res.getString(5));
            i++;
        }

        return ads;
    }

    public boolean syncDB(Type values){
//        SQLiteDatabase db = this.getWritableDatabase();
        long result=-1;
        Cursor res = null;
        ContentValues contentValues = new ContentValues();
        if(values.getType().equals(Settings.JOB_CATEGORIES.JSON_CATEGORY_TYPE)) {
            Category category = (Category) values;
            Log.e("Values"," "+values.toString());
            res = myDataBase.rawQuery("select " + CATEGORY_TITLE + " from " + TABLE_CATEGORIES + " where " + CATEGORY_ID + " = ? ", new String[]{category.getId()});
            if (res.getCount() == 0) {
                contentValues.put(CATEGORY_ID, category.getId());
                contentValues.put(CATEGORY_TITLE, category.getTitle());
                result = myDataBase.insert(TABLE_CATEGORIES, null, contentValues);
            }
            else {
                res.moveToNext();
                if (!res.getString(0).equals(category.getTitle())) {
                    Log.e("Mpaieni sync cat", category.getTitle());
                    contentValues.put(CATEGORY_TITLE, category.getTitle());
                    result = myDataBase.update(TABLE_CATEGORIES, contentValues, "_id=" + category.getId(), null);
                }
            }
        }
        else if(values.getType().equals(Settings.JOB_CATEGORIES.JSON_SUBCATEOGORY_TYPE)){
            SubCategory subCategory = (SubCategory) values;
            res = myDataBase.rawQuery("select "+SUBCATEGORY_TITLE+", "+SUBCATEGORY_PARENT_CATEGORY+" from "+TABLE_SUBCATEGORIES+" where "
                    +SUBCATEGORY_ID +" = ? ",new String[]{subCategory.getId()});
            if(res.getCount()==0){
                contentValues.put(SUBCATEGORY_ID,subCategory.getId());
                contentValues.put(SUBCATEGORY_TITLE,subCategory.getTitle());
                contentValues.put(SUBCATEGORY_PARENT_CATEGORY,subCategory.getParent_id());
                result = myDataBase.insert(TABLE_SUBCATEGORIES,null,contentValues);
            }
            else{
                res.moveToNext();
                if(!res.getString(0).equals(subCategory.getTitle()) || !res.getString(1).equals(subCategory.getParent_id())){
                    contentValues.put(SUBCATEGORY_TITLE,subCategory.getTitle());
                    contentValues.put(SUBCATEGORY_PARENT_CATEGORY,subCategory.getParent_id());
                    result = myDataBase.update(TABLE_SUBCATEGORIES,contentValues,"_id=" + subCategory.getId(), null);
                }
            }
        }
        else if(values.getType().equals(Settings.JOB_CATEGORIES.JSON_JOBPOSITION_TYPE)){
            JobPosition jobPosition = (JobPosition) values;
            Log.e("JOB pos insrtBefore",jobPosition.getTitle());Log.e("JOB pos insrtBefore",jobPosition.toString());
            res = myDataBase.rawQuery("select "+JOBPOSITION_TITLE+", "+JOBPOSITION_PARENT_SUBCATEGORY+" from "+TABLE_JOBPOSITIONS+" where "
                    +JOBPOSITION_ID+" = ?",new String[]{jobPosition.getId()});
            if(res.getCount()==0){
                Log.e("JOB pos insrt",jobPosition.getTitle());
                contentValues.put(JOBPOSITION_ID,jobPosition.getId());
                contentValues.put(JOBPOSITION_TITLE,jobPosition.getTitle());
                contentValues.put(JOBPOSITION_PARENT_SUBCATEGORY,jobPosition.getSubcategory_id());
                result=myDataBase.insertWithOnConflict(TABLE_JOBPOSITIONS,null,contentValues,SQLiteDatabase.CONFLICT_IGNORE);
            }
            else{
                res.moveToNext();
                if(!res.getString(0).equals(jobPosition.getTitle()) || !res.getString(1).equals(jobPosition.getSubcategory_id())){
                    contentValues.put(JOBPOSITION_TITLE,jobPosition.getTitle());
                    contentValues.put(JOBPOSITION_PARENT_SUBCATEGORY,jobPosition.getSubcategory_id());
                    result = myDataBase.update(TABLE_JOBPOSITIONS,contentValues,"_id="+jobPosition.getId(),null);
                }
            }
        }
        else if(values.getType().equals(Settings.REGIONS.JSON_COUNTRY_TYPE)){
            Country country = (Country) values;
            res = myDataBase.rawQuery("select "+COUNTRY_TITLE+" from "+TABLE_COUNTRIES+" where "+COUNTRY_ID+"=?",new String[]{country.getId()});
            if(res.getCount()==0){
                contentValues.put(COUNTRY_ID,country.getId());
                contentValues.put(COUNTRY_TITLE,country.getTitle());
                result = myDataBase.insert(TABLE_COUNTRIES,null,contentValues);
            }
            else{
                res.moveToNext();
                if(!res.getString(0).equals(country.getTitle())){
                    contentValues.put(COUNTRY_TITLE,country.getTitle());
                    result = myDataBase.update(TABLE_COUNTRIES,contentValues,"_id="+country.getId(),null);
                }
            }
        }
        else if(values.getType().equals(Settings.REGIONS.JSON_GEO_DEP_TYPE)){
            GeoDepartment geoDepartment = (GeoDepartment) values;
            res = myDataBase.rawQuery("select "+GEO_TITLE+", "+GEO_PARENT_COUNTRY+" from "+TABLE_GEODEPARTMENT+" where "
                    +GEO_ID+" =? ",new String[]{geoDepartment.getId()});
            if(res.getCount()==0){
                contentValues.put(GEO_ID,geoDepartment.getId());
                contentValues.put(GEO_TITLE,geoDepartment.getTitle());
                contentValues.put(GEO_PARENT_COUNTRY,geoDepartment.getCountry_id());
                result = myDataBase.insert(TABLE_GEODEPARTMENT,null,contentValues);
            }
            else{
                res.moveToNext();
                if(!res.getString(0).equals(geoDepartment.getTitle()) || !res.getString(1).equals(geoDepartment.getCountry_id())){
                    contentValues.put(GEO_TITLE,geoDepartment.getTitle());
                    contentValues.put(GEO_PARENT_COUNTRY,geoDepartment.getCountry_id());
                    result = myDataBase.update(TABLE_GEODEPARTMENT,contentValues,"_id="+geoDepartment.getId(),null);
                }
            }
        }
        else if(values.getType().equals(Settings.REGIONS.JSON_STATE_TYPE)){
            State state = (State) values;
            res = myDataBase.rawQuery("select "+STATE_TITLE+", "+STATE_PARENT_GEODEPARTMENT+" from "+TABLE_STATES+" where "
                    +STATE_ID+" =? ",new String[]{state.getId()});
            if(res.getCount()==0){
                contentValues.put(STATE_ID,state.getId());
                contentValues.put(STATE_TITLE, state.getTitle());
                contentValues.put(STATE_PARENT_GEODEPARTMENT,state.getGeoDepar_id());
                result = myDataBase.insert(TABLE_STATES,null,contentValues);
            }
            else{
                res.moveToNext();
                if(!res.getString(0).equals(state.getTitle()) || !res.getString(1).equals(state.getGeoDepar_id())){
                    contentValues.put(STATE_TITLE,state.getTitle());
                    contentValues.put(STATE_PARENT_GEODEPARTMENT,state.getGeoDepar_id());
                    result = myDataBase.update(TABLE_STATES,contentValues,"_id="+state.getId(),null);
                }
            }
        }
        else if(values.getType().equals(Settings.REGIONS.JSON_CITY_TYPE)){
            City city = (City) values;
            res = myDataBase.rawQuery("select "+CITY_TITLE+", "+CITY_PARENT_STATE+" from "+TABLE_CITIES+" where "
                    +CITY_ID+" =? ",new String[]{city.getId()});
            if(res.getCount()==0){
                contentValues.put(CITY_ID,city.getId());
                contentValues.put(CITY_TITLE,city.getTitle());
                contentValues.put(CITY_PARENT_STATE,city.getState_id());
                result = myDataBase.insert(TABLE_CITIES,null,contentValues);
            }
            else{
                res.moveToNext();
                if(!res.getString(0).equals(city.getTitle()) || !res.getString(1).equals(city.getState_id())){
                    contentValues.put(CITY_TITLE,city.getTitle());
                    contentValues.put(CITY_PARENT_STATE,city.getState_id());
                    result = myDataBase.update(TABLE_CITIES,contentValues,"_id="+city.getId(),null);
                }
            }
        }
        else if(values.getType().equals(Settings.REGIONS.JSON_MUNICIPALITY_TYPE)){
            Municipality municipality = (Municipality) values;
            res = myDataBase.rawQuery("select "+MUNICIPALITY_TITLE+", "+MUNICIPALITY_PARENT_CITY+" from "+TABLE_MUNICIPALITIES+" where "
                    +MUNICIPALITY_ID+" =? ",new String[]{municipality.getId()});
            if(res.getCount()==0){
                contentValues.put(MUNICIPALITY_ID,municipality.getId());
                contentValues.put(MUNICIPALITY_TITLE,municipality.getTitle());
                contentValues.put(MUNICIPALITY_PARENT_CITY,municipality.getCity_id());
                result = myDataBase.insert(TABLE_MUNICIPALITIES,null,contentValues);
            }
            else{
                res.moveToNext();
                if(!res.getString(0).equals(municipality.getTitle()) || !res.getString(1).equals(municipality.getCity_id())){
                    contentValues.put(MUNICIPALITY_TITLE,municipality.getTitle());
                    contentValues.put(MUNICIPALITY_PARENT_CITY,municipality.getCity_id());
                    result = myDataBase.update(TABLE_MUNICIPALITIES,contentValues,"_id="+municipality.getId(),null);
                }
            }
        }
        else if(values.getType().equals(Settings.EMPLOYE.JSON_EMPTYPE_TYPE)){
            EmpType empType = (EmpType) values;
            res = myDataBase.rawQuery("select "+EMPTYPE_TITLE+" from "+TABLE_EMPTYPE+" where "
                    +EMPTYPE_ID+" =?",new String[]{empType.getId()});
            if(res.getCount()==0){
                contentValues.put(EMPTYPE_ID,empType.getId());
                contentValues.put(EMPTYPE_TITLE,empType.getTitle());
                result = myDataBase.insert(TABLE_EMPTYPE,null,contentValues);
            }
            else{
                res.moveToNext();
                if(!res.getString(0).equals(empType.getTitle())){
                    contentValues.put(EMPTYPE_TITLE,empType.getTitle());
                    result = myDataBase.update(TABLE_EMPTYPE,contentValues,"_id="+empType.getId(),null);
                }
            }
        }
        else if(values.getType().equals(Settings.UNIVERSITY.JSON_UNIVERSITY_TYPE)){
            University university = (University) values;
            res = myDataBase.rawQuery("select "+UNIVERSITY_TITLE+", "+UNIVERSITY_LEVEL+" from "+TABLE_UNIVERSITY+" where "
                    +UNIVERSITY_ID+" =?",new String[]{university.getId()});
            if(res.getCount()==0){
                contentValues.put(UNIVERSITY_ID,university.getId());
                contentValues.put(UNIVERSITY_TITLE,university.getTitle());
                contentValues.put(UNIVERSITY_LEVEL,university.getEduLevel());
                result = myDataBase.insert(TABLE_UNIVERSITY,null,contentValues);
            }
            else{
                res.moveToNext();
                if(!res.getString(0).equals(university.getTitle()) || !res.getString(1).equals(university.getEduLevel())){
                    contentValues.put(UNIVERSITY_TITLE,university.getTitle());
                    contentValues.put(UNIVERSITY_LEVEL,university.getEduLevel());
                    result = myDataBase.update(TABLE_UNIVERSITY,contentValues,"_id="+university.getId(),null);
                }
            }
        }
        else if(values.getType().equals(Settings.EDUCATION.JSON_EDUCATION_TYPE)){
            EduType eduType = (EduType) values;
            res = myDataBase.rawQuery("select "+EDUTYPE_TITLE+" from "+TABLE_EDUTYPE+" where "+
                    EDUTYPE_ID+" =?",new String[]{eduType.getId()});
            if(res.getCount()==0){
                contentValues.put(EDUTYPE_ID,eduType.getId());
                contentValues.put(EDUTYPE_TITLE,eduType.getTitle());
                result = myDataBase.insert(TABLE_EDUTYPE,null,contentValues);
            }
            else{
                res.moveToNext();
                if(!res.getString(0).equals(eduType.getTitle())){
                    contentValues.put(EDUTYPE_TITLE,eduType.getTitle());
                    result = myDataBase.update(TABLE_EDUTYPE,contentValues,"_id="+eduType.getId(),null);
                }
            }
        }
        else if(values.getType().equals(Settings.FOREIGN_LANG.JSON_FOREIGN_LANG_TYPE)){
            ForeignLang foreignLang = (ForeignLang) values;
            res = myDataBase.rawQuery("select "+LANG_TITLE+" from "+TABLE_LANGUAGE+" where "+
                    LANG_ID+" =?",new String[]{foreignLang.getId()});
            if(res.getCount()==0){
                contentValues.put(LANG_ID,foreignLang.getId());
                contentValues.put(LANG_TITLE,foreignLang.getTitle());
                result = myDataBase.insert(TABLE_LANGUAGE,null,contentValues);
            }
            else{
                res.moveToNext();
                if(!res.getString(0).equals(foreignLang.getTitle())){
                    contentValues.put(LANG_TITLE,foreignLang.getTitle());
                    result = myDataBase.update(TABLE_LANGUAGE,contentValues,"_id="+foreignLang.getId(),null);
                }
            }
        }
        else if(values.getType().equals(Settings.CERTIFICATION_LANG.JSON_CERTIFICATION_LANG_TYPE)){
            CertificationForeignLang certificationForeignLang = (CertificationForeignLang) values;
            res = myDataBase.rawQuery("select "+CERT_TITLE+", "+LANGUAGE+" from "+TABLE_LANG_CERTIFICATION+" where "+
                    CERT_ID+" =?",new String[]{certificationForeignLang.getId()});
            if(res.getCount()==0){
                contentValues.put(CERT_ID,certificationForeignLang.getId());
                contentValues.put(CERT_TITLE,certificationForeignLang.getTitle());
                contentValues.put(LANGUAGE,certificationForeignLang.getParent_id());
                result = myDataBase.insert(TABLE_LANG_CERTIFICATION,null,contentValues);
            }
            else{
                res.moveToNext();
                if(!res.getString(0).equals(certificationForeignLang.getTitle()) || !res.getString(1).equals(certificationForeignLang.getParent_id())){
                    contentValues.put(CERT_TITLE,certificationForeignLang.getTitle());
                    contentValues.put(LANGUAGE,certificationForeignLang.getParent_id());
                    result = myDataBase.update(TABLE_LANG_CERTIFICATION,contentValues,"_id="+certificationForeignLang.getId(),null);
                }
            }
        }
        else if(values.getType().equals(Settings.IT_CATEGORY.JSON_ITCATEGORY_TYPE)){
            ItCategory itCategory = (ItCategory) values;
            res = myDataBase.rawQuery("select "+ITCATEGORY_TITLE+" from "+TABLE_IT_CATEGORY+" where "+
                    ITCATEGORY_ID+" =?",new String[]{itCategory.getId()});
            if(res.getCount()==0){
                contentValues.put(ITCATEGORY_ID,itCategory.getId());
                contentValues.put(ITCATEGORY_TITLE,itCategory.getTitle());
                result = myDataBase.insert(TABLE_IT_CATEGORY,null,contentValues);
            }
            else{
                res.moveToNext();
                if(!res.getString(0).equals(itCategory.getTitle())){
                    contentValues.put(ITCATEGORY_TITLE,itCategory.getTitle());
                    result = myDataBase.update(TABLE_IT_CATEGORY,contentValues,"_id="+itCategory.getId(),null);
                }
            }
        }
        else if(values.getType().equals(Settings.IT_SKILL.JSON_ITSKILL_TYPE)){
            ItSkill itSkill = (ItSkill) values;
            res = myDataBase.rawQuery("select "+ITSKILL_TITLE+", "+ITSKILL_CATEGORY+" from "+TABLE_IT_SKILL+" where "+
                    ITSKILL_ID+" =?",new String[]{itSkill.getId()});
            if(res.getCount()==0){
                contentValues.put(ITSKILL_ID,itSkill.getId());
                contentValues.put(ITSKILL_TITLE,itSkill.getTitle());
                contentValues.put(ITSKILL_CATEGORY,itSkill.getParent_id());
                result = myDataBase.insert(TABLE_IT_SKILL,null,contentValues);
            }
            else{
                res.moveToNext();
                if(!res.getString(0).equals(itSkill.getTitle()) || !res.getString(1).equals(itSkill.getParent_id())){
                    contentValues.put(ITSKILL_TITLE,itSkill.getTitle());
                    contentValues.put(ITSKILL_CATEGORY,itSkill.getParent_id());
                    result = myDataBase.update(TABLE_IT_SKILL,contentValues,"_id="+itSkill.getId(),null);
                }
            }
        }
        res.close();
        if(result==-1){
            return false;
        }
        else{
            return true;
        }
    }


    public int get_data_count(){
//        SQLiteDatabase myDataBase = this.getWritableDatabase();
        Cursor res;
        int count=0;
        res = myDataBase.rawQuery("select "+CATEGORY_ID+" from "+TABLE_CATEGORIES,null);
        count += res.getCount();
        res  = myDataBase.rawQuery("select "+SUBCATEGORY_ID+" from "+TABLE_SUBCATEGORIES,null);
        count += res.getCount();
        res  = myDataBase.rawQuery("select "+JOBPOSITION_ID+" from "+TABLE_JOBPOSITIONS,null);
        count += res.getCount();
        return count;
    }

    public String[] getPositions(){
//        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = null;
        String[] positions;
        int i=0;

        res = myDataBase.rawQuery("select "+JOBPOSITION_TITLE+" from "+TABLE_JOBPOSITIONS,null);
        positions = new String[res.getCount()];
        while(res.moveToNext()){
            positions[i] = res.getString(0);
            i++;
        }
        return positions;
    }

    public Cursor getAllData(String type){
//        SQLiteDatabase myDataBase = this.getWritableDatabase();
        Cursor res=null;
        if(type.equals(Settings.JOB_CATEGORIES.JSON_CATEGORY_TYPE)){
            res = myDataBase.rawQuery("select "+CATEGORY_ID+", "+CATEGORY_TITLE+" from "+TABLE_CATEGORIES,null);
        }
        else if(type.equals(Settings.JOB_CATEGORIES.JSON_SUBCATEOGORY_TYPE)){
            res = myDataBase.rawQuery("select "+SUBCATEGORY_ID+", "+SUBCATEGORY_TITLE+", "+SUBCATEGORY_PARENT_CATEGORY+" from "+TABLE_SUBCATEGORIES,null);
        }
        else if(type.equals(Settings.JOB_CATEGORIES.JSON_JOBPOSITION_TYPE)){
            res = myDataBase.rawQuery("select "+JOBPOSITION_ID+", "+JOBPOSITION_TITLE+", "+JOBPOSITION_PARENT_SUBCATEGORY+" from "+TABLE_JOBPOSITIONS,null);
        }
        else if(type.equals(Settings.REGIONS.JSON_COUNTRY_TYPE)){
            res = myDataBase.rawQuery("select "+COUNTRY_ID+", "+COUNTRY_TITLE+" from "+TABLE_COUNTRIES,null);
        }
        else if(type.equals(Settings.REGIONS.JSON_GEO_DEP_TYPE)){
            res = myDataBase.rawQuery("select "+GEO_ID+", "+GEO_TITLE+", "+GEO_PARENT_COUNTRY+" from "+TABLE_GEODEPARTMENT,null);
        }
        else if(type.equals(Settings.REGIONS.JSON_STATE_TYPE)){
            res = myDataBase.rawQuery("select "+STATE_ID+", "+STATE_TITLE+", "+STATE_PARENT_GEODEPARTMENT+" from "+TABLE_STATES,null);
        }
        else if(type.equals(Settings.REGIONS.JSON_CITY_TYPE)){
            res = myDataBase.rawQuery("select "+CITY_ID+", "+CITY_TITLE+", "+CITY_PARENT_STATE+" from "+TABLE_CITIES,null);
        }
        else if(type.equals(Settings.REGIONS.JSON_MUNICIPALITY_TYPE)){
            res = myDataBase.rawQuery("select "+MUNICIPALITY_ID+", "+MUNICIPALITY_TITLE+", "+MUNICIPALITY_PARENT_CITY+" from "+TABLE_MUNICIPALITIES,null);
        }
        else if(type.equals(Settings.EMPLOYE.JSON_EMPTYPE_TYPE)){
            res = myDataBase.rawQuery("select "+EMPTYPE_ID+", "+EMPTYPE_TITLE+" from "+TABLE_EMPTYPE,null);
        }
        else if(type.equals(Settings.UNIVERSITY.JSON_UNIVERSITY_TYPE)){
            res = myDataBase.rawQuery("select "+UNIVERSITY_ID+", "+UNIVERSITY_TITLE+", "+UNIVERSITY_LEVEL+" from "+TABLE_UNIVERSITY,null);
        }
        else if(type.equals(Settings.EDUCATION.JSON_EDUCATION_TYPE)){
            res = myDataBase.rawQuery("select "+EDUTYPE_ID+", "+EDUTYPE_TITLE+" from "+TABLE_EDUTYPE,null);
        }
        else if(type.equals(Settings.FOREIGN_LANG.JSON_FOREIGN_LANG_TYPE)){
            res = myDataBase.rawQuery("select "+LANG_ID+", "+LANG_TITLE+" from "+TABLE_LANGUAGE,null);
        }
        else if(type.equals(Settings.CERTIFICATION_LANG.JSON_CERTIFICATION_LANG_TYPE)){
            res = myDataBase.rawQuery("select "+CERT_ID+", "+CERT_TITLE+", "+LANGUAGE+" from "+TABLE_LANG_CERTIFICATION,null);
        }
        else if(type.equals(Settings.IT_CATEGORY.JSON_ITCATEGORY_TYPE)){
            res = myDataBase.rawQuery("select "+ITCATEGORY_ID+", "+ITCATEGORY_TITLE+" from "+TABLE_IT_CATEGORY,null);
        }
        else if(type.equals(Settings.IT_SKILL.JSON_ITSKILL_TYPE)){
            res = myDataBase.rawQuery("select "+ITSKILL_ID+", "+ITSKILL_TITLE+", "+ITSKILL_CATEGORY+" from "+TABLE_IT_SKILL,null);
        }
        else{
            res = myDataBase.rawQuery("select *",null);
        }
        return res;
    }

    public String getUserId(){
//        myDataBase = this.getWritableDatabase();
        Cursor res=null;

        res=myDataBase.rawQuery("select "+USER_ID+" from "+TABLE_SETTINGS,null);
        if(res.getCount()==0){
            return null;
        }
        else if(res.getCount()>1){
            myDataBase.execSQL("delete from "+ TABLE_SETTINGS);
            return "wrong";
        }
        else{
            res.moveToNext();
            return res.getString(0);
        }
    }

    public String getId(String name,String type,String parent){
//        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = null;


        if(type.equals(Settings.JOB_CATEGORIES.CATEGORY_TYPE)){
            res=myDataBase.rawQuery("select "+CATEGORY_ID+" from "+TABLE_CATEGORIES+" where "+CATEGORY_TITLE+" =?",new String[]{name});
        }
        else if(type.equals(Settings.JOB_CATEGORIES.SUBCATEGORY_TYPE)){
            res=myDataBase.rawQuery("select "+SUBCATEGORY_ID+", "+SUBCATEGORY_PARENT_CATEGORY+" from "+TABLE_SUBCATEGORIES+" where "+SUBCATEGORY_TITLE+" =?",new String[]{name});
        }
        else if(type.equals(Settings.JOB_CATEGORIES.JOBPOSITION_TYPE)){
            res=myDataBase.rawQuery("select "+JOBPOSITION_ID+", "+JOBPOSITION_PARENT_SUBCATEGORY+" from "+TABLE_JOBPOSITIONS+" where "+JOBPOSITION_TITLE+" =?",new String[]{name});
        }
        else if(type.equals(Settings.REGIONS.COUNTRY_TYPE)) {
            res=myDataBase.rawQuery("select "+COUNTRY_ID+" from "+TABLE_COUNTRIES+" where "+COUNTRY_TITLE+" =?",new String[]{name});
        }
        else if(type.equals(Settings.REGIONS.GEO_DEP_TYPE)){
            res=myDataBase.rawQuery("select "+GEO_ID+", "+GEO_PARENT_COUNTRY+" from "+TABLE_GEODEPARTMENT+" where "+GEO_TITLE+" =?",new String[]{name});
        }
        else if(type.equals(Settings.REGIONS.STATE_TYPE)){
            res=myDataBase.rawQuery("select "+STATE_ID+", "+STATE_PARENT_GEODEPARTMENT+" from "+TABLE_STATES+" where "+STATE_TITLE+" =?",new String[]{name});
        }
        else if(type.equals(Settings.REGIONS.CITY_TYPE)){
            res=myDataBase.rawQuery("select "+CITY_ID+", "+CITY_PARENT_STATE+" from "+TABLE_CITIES+" where "+CITY_TITLE+" =?",new String[]{name});
        }
        else if(type.equals(Settings.REGIONS.MUNICIPALITY_TYPE)){
            res=myDataBase.rawQuery("select "+MUNICIPALITY_ID+", "+MUNICIPALITY_PARENT_CITY+" from "+TABLE_MUNICIPALITIES+" where "+MUNICIPALITY_TITLE+" =?",new String[]{name});
        }
        else if(type.equals(Settings.EMPLOYE.EMPLOYEE_TYPE)){
            res=myDataBase.rawQuery("select "+EMPTYPE_ID+" from "+TABLE_EMPTYPE+" where "+EMPTYPE_TITLE+" =?",new String[]{name});
        }
        else if(type.equals(Settings.UNIVERSITY.UNIVERSITY_TYPE)){
            res=myDataBase.rawQuery("select "+UNIVERSITY_ID+" from "+TABLE_UNIVERSITY+" where "+UNIVERSITY_TITLE+" =?",new String[]{name});
        }
        else if(type.equals(Settings.EDUCATION.EDUCATION_TYPE)){
            res=myDataBase.rawQuery("select "+EDUTYPE_ID+" from "+TABLE_EDUTYPE+" where "+EDUTYPE_TITLE+" =?",new String[]{name});
        }
        else if(type.equals(Settings.FOREIGN_LANG.FOREIGN_LANG_TYPE)){
            res = myDataBase.rawQuery("select "+LANG_ID+" from "+TABLE_LANGUAGE+" where "+LANG_TITLE+" =?",new String[]{name});
        }
        else if(type.equals(Settings.CERTIFICATION_LANG.CERTIFICATION_TYPE)){
            res = myDataBase.rawQuery("select "+CERT_ID+" from "+TABLE_LANG_CERTIFICATION+" where "+CERT_TITLE+" =?",new String[]{name});
        }
        else if(type.equals(Settings.IT_CATEGORY.ITCATEGORY_TYPE)){
            res = myDataBase.rawQuery("select "+ITCATEGORY_ID+" from "+TABLE_IT_CATEGORY+" where "+ITCATEGORY_TITLE+" =?",new String[]{name});
        }
        else if(type.equals(Settings.IT_SKILL.ITSKILL_TYPE)){
            res = myDataBase.rawQuery("select "+ITSKILL_ID+" from "+TABLE_IT_SKILL+" where "+ITSKILL_TITLE+" =?",new String[]{name});
        }

        if(res.getCount()==0){
            return "";
        }
        else if(res.getCount()>1){
            while (res.moveToNext()){
                if(res.getString(1).equals(parent)){
                    return res.getString(0);
                }
            }
            return "wrong";
        }
        else{
            res.moveToNext();
            return res.getString(0);
        }
    }


    public void delete_id(String id){
//        SQLiteDatabase db = this.getWritableDatabase();
        myDataBase.delete(TABLE_SETTINGS, USER_ID + "=?" ,new String[]{ id });
    }

    /* Deletes Tables if exist */
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
//        db.execSQL("DROP TABLE IF EXISTS "+TABLE_CATEGORIES);
//        db.execSQL("DROP TABLE IF EXISTS "+TABLE_SUBCATEGORIES);
//        db.execSQL("DROP TABLE IF EXISTS "+TABLE_JOBPOSITIONS);
//        db.execSQL("DROP TABLE IF EXISTS "+TABLE_SETTINGS);
//        db.execSQL("DROP TABLE IF EXISTS "+TABLE_COUNTRIES);
//        db.execSQL("DROP TABLE IF EXISTS "+TABLE_GEODEPARTMENT);
//        db.execSQL("DROP TABLE IF EXISTS "+TABLE_STATES);
//        db.execSQL("DROP TABLE IF EXISTS "+TABLE_CITIES);
//        db.execSQL("DROP TABLE IF EXISTS "+TABLE_MUNICIPALITIES);
//        db.execSQL("DROP TABLE IF EXISTS "+TABLE_EMPTYPE);
//        db.execSQL("DROP TABLE IF EXISTS "+TABLE_UNIVERSITY);
//        db.execSQL("DROP TABLE IF EXISTS "+TABLE_EDUTYPE);
//        db.execSQL("DROP TABLE IF EXISTS "+TABLE_LANGUAGE);
//        db.execSQL("DROP TABLE IF EXISTS "+TABLE_LANG_CERTIFICATION);
//        db.execSQL("DROP TABLE IF EXISTS "+TABLE_IT_CATEGORY);
//        db.execSQL("DROP TABLE IF EXISTS "+TABLE_IT_SKILL);
//        db.execSQL("DROP TABLE IF EXISTS "+TABLE_SAVED_ADS);
//        db.execSQL("DROP TABLE IF EXISTS "+TABLE_SEND_CV_ADS);
//        onCreate(db);
    }


    public boolean isOpen(){
        return  this.isOpen();
    }

    /* Opens Database in a specific path and gives permissions */
    public void openDataBase() throws SQLException {

        //Open the database
        String myPath = DEFAULT_DB_PATH + DATABASE_NAME;
        myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
//        myDataBase = this.getWritableDatabase();

    }


    @Override
    public synchronized void close() {

        if(myDataBase != null)
            myDataBase.close();

        super.close();

    }

}
