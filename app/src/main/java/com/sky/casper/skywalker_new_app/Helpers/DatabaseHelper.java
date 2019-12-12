package com.sky.casper.skywalker_new_app.Helpers;


import android.content.ContentValues;
import android.content.Context;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


public class DatabaseHelper extends SQLiteOpenHelper {
    private SQLiteDatabase myDataBase;
    private static final int DATABSE_VERSION = 9;
    private static String DEFAULT_DB_PATH = "";

    private static final String DATABASE_NAME = "Skywalker.db";
    private final Context mContext;

    /* Tables properties*/

    private static final String TABLE_CATEGORIES = "categories";
    private static final String CATEGORY_ID = "ID_CATEGORY";
    private static final String CATEGORY_TITLE = "TITLE_CATEGORY";
    private static final String CREATE_CATEGORIES_TABLE = "CREATE TABLE "
            + TABLE_CATEGORIES + " ("
            + CATEGORY_ID + " TEXT PRIMARY KEY, "
            + CATEGORY_TITLE + " TEXT)";

    private static final String TABLE_SUBCATEGORIES = "subcategories";
    private static final String SUBCATEGORY_ID = "ID_SUBCATEGORY";
    private static final String SUBCATEGORY_TITLE = "TITLE_SUBCATEGORY";
    private static final String SUBCATEGORY_PARENT_CATEGORY = "PARENT_CATEGORY_ID";
    private static final String CREATE_SUBCATEGORIES_TABLE = "CREATE TABLE "
            + TABLE_SUBCATEGORIES + " ("
            + SUBCATEGORY_ID + " TEXT PRIMARY KEY, "
            + SUBCATEGORY_TITLE + " TEXT, "
            + SUBCATEGORY_PARENT_CATEGORY + " TEXT, "
            + "FOREIGN KEY(" + SUBCATEGORY_PARENT_CATEGORY + ") REFERENCES " + TABLE_CATEGORIES + "( " + CATEGORY_ID + " ))";

    private static final String TABLE_JOBPOSITIONS = "jobpositions";
    private static final String JOBPOSITION_ID = "ID_JOBPOSITION";
    private static final String JOBPOSITION_TITLE = "TITLE_JOBPOSITION";
    private static final String JOBPOSITION_PARENT_SUBCATEGORY = "PARENT_SUBCATEGORY_ID";
    private static final String CREATE_JOBPOSITIONS_TABLE = "CREATE TABLE "
            + TABLE_JOBPOSITIONS + " ("
            + JOBPOSITION_ID + " TEXT PRIMARY KEY, "
            + JOBPOSITION_TITLE + " TEXT, "
            + JOBPOSITION_PARENT_SUBCATEGORY + " TEXT, "
            + "FOREIGN KEY(" + JOBPOSITION_PARENT_SUBCATEGORY + ") REFERENCES " + TABLE_SUBCATEGORIES + "( " + SUBCATEGORY_ID + " ))";

    private static final String TABLE_COUNTRIES = "countries";
    private static final String COUNTRY_ID = "ID_COUNTRY";
    private static final String COUNTRY_TITLE = "TITLE_COUNTRY";
    private static final String CREATE_COUNTRIES_TABLE = "CREATE TABLE "
            + TABLE_COUNTRIES + "("
            + COUNTRY_ID + " TEXT PRIMARY KEY, "
            + COUNTRY_TITLE + " TEXT)";

    private static final String TABLE_GEODEPARTMENT = "geographicaldepartment";
    private static final String GEO_ID = "ID_GEO";
    private static final String GEO_TITLE = "TITLE_GEO";
    private static final String GEO_PARENT_COUNTRY = "PARENT_COUNTRY_ID";
    private static final String CREATE_GEODEPARTMENT_TABLE = "CREATE TABLE "
            + TABLE_GEODEPARTMENT + " ("
            + GEO_ID + " TEXT PRIMARY KEY, "
            + GEO_TITLE + " TEXT, "
            + GEO_PARENT_COUNTRY + " TEXT, "
            + "FOREIGN KEY(" + GEO_PARENT_COUNTRY + ") REFERENCES " + TABLE_COUNTRIES + "( " + COUNTRY_ID + " ))";

    private static final String TABLE_STATES = "states";
    private static final String STATE_ID = "ID_STATE";
    private static final String STATE_TITLE = "TITLE_STATE";
    private static final String STATE_PARENT_GEODEPARTMENT = "PARENT_GEODEPARTMENT_ID";
    private static final String CREATE_STATES_TABLE = "CREATE TABLE "
            + TABLE_STATES + " ("
            + STATE_ID + " TEXT PRIMARY KEY, "
            + STATE_TITLE + " TEXT, "
            + STATE_PARENT_GEODEPARTMENT + " TEXT, "
            + "FOREIGN KEY(" + STATE_PARENT_GEODEPARTMENT + ") REFERENCES " + TABLE_GEODEPARTMENT + "( " + GEO_ID + " ))";

    private static final String TABLE_CITIES = "cities";
    private static final String CITY_ID = "ID_CITY";
    private static final String CITY_TITLE = "TITLE_CITY";
    private static final String CITY_PARENT_STATE = "PARENT_STATE_ID";
    private static final String CREATE_CITY_TABLE = "CREATE TABLE "
            + TABLE_CITIES + " ("
            + CITY_ID + " TEXT PRIMARY KEY, "
            + CITY_TITLE + " TEXT, "
            + CITY_PARENT_STATE + " TEXT, "
            + "FOREIGN KEY(" + CITY_PARENT_STATE + ") REFERENCES " + TABLE_STATES + "( " + STATE_ID + " ))";

    private static final String TABLE_MUNICIPALITIES = "municipalities";
    private static final String MUNICIPALITY_ID = "ID_MUNICIPALITY";
    private static final String MUNICIPALITY_TITLE = "TITLE_MUNICIPALITY";
    private static final String MUNICIPALITY_PARENT_CITY = "PARENT_CITY_ID";
    private static final String CREATE_MUNICIPALITY_TABLE = "CREATE TABLE "
            + TABLE_MUNICIPALITIES + " ("
            + MUNICIPALITY_ID + " TEXT PRIMARY KEY, "
            + MUNICIPALITY_TITLE + " TEXT, "
            + MUNICIPALITY_PARENT_CITY + " TEXT, "
            + "FOREIGN KEY(" + MUNICIPALITY_PARENT_CITY + ") REFERENCES " + TABLE_CITIES + "( " + CITY_ID + " ))";

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
            + TABLE_LANG_CERTIFICATION + " ("
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
            + SEND_CV_TEXT + " TEXT, "
            + SEND_CV_EMP_TYPE + " TEXT, "
            + SEND_CV_IS_ANONYMOUS + " TEXT, "
            + SEND_CV_REGION + " TEXT, "
            + SEND_CV_CLIENT + " TEXT, "
            + SEND_CV_IMAGE + " TEXT, "
            + SEND_CV_ADCODE + " TEXT, "
            + SEND_CV_URL + " TEXT, "
            + SEND_CV_DATE + " DATETIME DEFAULT CURRENT_TIMESTAMP)";


    private static final String TABLE_SETTINGS = "settings";
    private static final String USER_ID = "ID_LOGIN";
    private static final String CREATE_SETTINGS_TABLE = "CREATE TABLE "
            + TABLE_SETTINGS + " ("
            + USER_ID + " TEXT PRIMARY KEY)";


    /* construct the app's database */
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABSE_VERSION);
        if (android.os.Build.VERSION.SDK_INT >= 17) {
            DEFAULT_DB_PATH = context.getApplicationInfo().dataDir + "/databases/";
        } else {
            DEFAULT_DB_PATH = "/data/data/" + context.getPackageName() + "/databases/";
        }
        mContext = context;
        try {
            createDataBase();
            openDataBase();
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("DB", "Not copying db");
        }

    }

    @Override /* built-in function which handles different procedures while opening the database */
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        db.disableWriteAheadLogging();
    }

    public void createDataBase() throws IOException {

        boolean dbExist = checkDataBase();
        Log.e("DB exist", "returns exist " + dbExist);

        if (dbExist) {
            //do nothing - database already exist
        } else {

            //By calling this method and empty database will be created into the default system path
            //of your application so we are gonna be able to overwrite that database with our database.
            try {
                this.getReadableDatabase();


                copyDataBase();


            } catch (IOException e) {

                e.printStackTrace();
                Log.e("DB Error", "No copy");

            } catch (Exception e1) {
                Log.e("Error", "Other database error");
            }
        }

    }

    /* finds the if the database exist in the device space*/
    private boolean checkDataBase() {
        SQLiteDatabase checkDB = null;

        try {
            String myPath = DEFAULT_DB_PATH + DATABASE_NAME;
            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);

        } catch (SQLiteException e) {

            //database does't exist yet.

        }

        if (checkDB != null) {

            checkDB.close();

        }

        return checkDB != null ? true : false;
    }

    private void copyDataBase() throws Exception {

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
            Log.e("CP", "copy db2 " + length);
        }

        //Close the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();

    }

    public void openDataBase() throws SQLException {

        //Open the database
        String myPath = DEFAULT_DB_PATH + DATABASE_NAME;
        myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);

    }

    public boolean insertUserId(String id){
        ContentValues contentValues = new ContentValues();
        contentValues.put(USER_ID,id);
        long result = myDataBase.insert(TABLE_SETTINGS,null,contentValues); /// insert query
        return result!=-1;
    }

    public String getUserId(){
        Cursor cur;

        cur=myDataBase.rawQuery("select "+USER_ID+" from "+TABLE_SETTINGS,null); /// select id query

        if(cur.getCount() == 0){
            return null;
        }
        else if(cur.getCount()>1){ /// if there is more than one id values
            myDataBase.execSQL("delete from "+TABLE_SETTINGS);
            return Settings.ERROR_MSG.DATABASE_ERROR;
        }
        else{
            cur.moveToNext();
            return cur.getString(0);
        }
    }

    public void delete_id(String id){ /// delete id from table
//        SQLiteDatabase db = this.getWritableDatabase();
        myDataBase.delete(TABLE_SETTINGS, USER_ID + "=?" ,new String[]{ id });
    }

    /* Create Tables */
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
    }


    /* Deletes Tables if exist */
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
    }


    @Override
    public synchronized void close() {

        if (myDataBase != null)
            myDataBase.close();

        super.close();

    }

}
