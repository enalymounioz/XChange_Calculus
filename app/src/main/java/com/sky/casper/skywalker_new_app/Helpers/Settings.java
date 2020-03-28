package com.sky.casper.skywalker_new_app.Helpers;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.opengl.Visibility;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.sky.casper.skywalker_new_app.Activities.ActivityHome;
import com.sky.casper.skywalker_new_app.Models.CVProfile;
import com.sky.casper.skywalker_new_app.R;
import com.sky.casper.skywalker_new_app.Skywalker;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.Normalizer;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.regex.Pattern;

public class Settings {

    private  Context context;

    public Settings(){
        this.context = Skywalker.getContext();
    }

/* All existing urls for communication with the server */
    public static class URLS {
        public static final String YOUTUBE_WATCH = "https://www.youtube.com/watch?v=";
        public static final String SICP = "https://sicp.gr/";
        public static final String SICP_EVENTS = SICP + "/elGR/forms/RemoteFormRegister/";
        public static final String MyLocalServer = "http://192.168.1.20:83/";
        public static final String MyServer = /*"http://192.168.1.20:83/"*/"https://www.skywalker.gr/";
        public static final String URL_ADS = MyServer + "elGR/aggelia-ajax/euresi-aggelion-ergasias";
        public static final String FRIEND_URL = MyServer+"elGR/mobile-api/friend-details";
        public static final String URL_GET_FILES = "elGR/mobile-api/get-files";
        public static final String URL_EVENT_REGISTER = MyServer + "elGR/mobile-api/event-form/";
        public static final String URL_SEARCH_FRIENDS = MyServer + "elGR/mobile-api/search-friend";
        public static final String URL_FRIENDS = MyServer + "elGR/mobile-api/friends";
        public static final String URL_ACCEPT_FRIEND = MyServer + "elGR/mobile-api/accept-request";
        public static final String URL_DENY_FRIEND = MyServer + "elGR/mobile-api/deny-request";
        public static final String URL_EVENTS = MyServer + "elGR/mobile-api/events";
        public static final String URL_SEND_CERT = MyServer + "elGR/mobile-api/send-certificate";
        public static final String URL_PDF_EXTRACT = MyServer + "elGR/mobile-api/get-pdf";
        public static final String URL_SEND_REQUEST = MyServer + "elGR/mobile-api/friend-request";
        public static final String URL_CERTIFICATE_DOWNLOAD = SICP + "elGR/event/certificate/";
        public static final String URL_PUBLIC_SMALL =  MyServer + "elGR/aggelia/ergasias-mikri/";
        public static final String URL_FORGOT_PASS = MyServer + "elGR/mobile-api/forgot-pass";
        public static final String URL_LOAD_DATA = MyServer + "elGR/mobile-api/get-all";
        public static final String URL_AD_TEXT = MyServer + "elGR/mobile-api/get-big-ad";
        public static final String URL_SMALL_AD_UPLOAD = MyServer+"elGR/mobile-api/upload-bio-disconnected-small";
        public static final String URL_SMALL_AD_SEND_BIO = MyServer+"elGR/mobile-api/send-bio-to-small";
        public static final String URL_SOCIAL_LOGIN = MyServer + "elGR/mobile-api/register-social";
        public static final String UPLOAD_BIO_URL_DISCONN = MyServer + "elGR/mobile-api/upload-bio-disconnected";
        public static final String LOGIN_CANDIDATE = MyServer + "elGR/mobile-api/candidate-login-id";
        public static final String BIOS_URL = MyServer + "elGR/mobile-api/get-bios";
        public static final String UPLOAD_BIO_URL_CONN = MyServer + "elGR/mobile-api/upload-bio-connected";
        public static final String SEND_BIO_CONN_URL = MyServer + "elGR/mobile-api/send-bio-to-ad";
        public static final String REGISTER_URL = MyServer + "elGR/mobile-api/register-candidate-v2";
        public static final String URL_SMALL_ADS = MyServer + "elGR/mobile-api/get-small-ads";
        public static final String URL_LINKEDIN = "https://api.linkedin.com/v1/people/~:(email-address,formatted-name,)";
        public static final String CDM_URL = "https://cdnsimg.scdn3.secure.raxcdn.com/";
        public static final String URL_UPDATE_PERMISSIONS = MyServer + "elGR/mobile-api/update-permissions";
        public static final String SKYWALKER_FILES = "/files.skywalker.gr";
        public static final String SEND_CLIENT_URL = MyServer+ "elGR/mobile-api/send-bio-to-client";
        public static final String UPLOAD_CLIENT_URL = MyServer+"elGR/mobile-api/upload-bio-to-client";
        public static final String CLIENT_URL = MyServer+"elGR/mobile-api/client-info";
        public static final String CANDIDATE_URL = MyServer+"elGR/mobile-api/candidate-details";
        public static final String BIO_URL = MyServer+"elGR/mobile-api/get-bio-info";
        public static final String UPDATE_CAND_URL = MyServer+"elGR/mobile-api/update-candidate-details";
        public static final String UPLOAD_PROFILE_IMG_URL = MyServer+"elGR/mobile-api/edit-profile-img";
        public static final String DELETE_PROFILE_IMG_URL = MyServer+"elGR/mobile-api/delete-profile-img";
        public static final String EDIT_BIO_URL = MyServer+"elGR/mobile-api/edit-bio-info";
        public static final String DELETE_BIO_INFO_URL = MyServer+"elGR/mobile-api/delete-bio-info";
        public static final String ADD_BIO_URL = MyServer+"elGR/mobile-api/add-bio-info";
        public static final String URL_QUESTIONS = MyServer+"elGR/mobile-api/get-questions";
        public static final String URL_DELETE_ACCOUNT = MyServer+"elGR/mobile-api/delete-account";
        public static final String URL_CHANGE_PASSWORD = MyServer+"elGR/mobile-api/edit-password";
        public static final String URL_PUBLIC_PROFILE = MyServer+"elGR/mobile-api/public-profile";
        public static final String URL_DELETE_BIO = MyServer+"elGR/mobile-api/delete-bio";
        public static final String URL_UPLOAD_LET = MyServer+"elGR/mobile-api/upload-letter-connected";
        public static final String URL_SEND_MAIL = MyServer+"elGR/mobile-api/send-ad-mail";
        public static final String URL_SAVE_SEARCH = MyServer+"elGR/mobile-api/save-search";
        public static final String URL_PERCENT_BIO = MyServer+"elGR/mobile-api/get-percentage";
        public static final String URL_CONTACT_FORM = MyServer+"elGR/mobile-api/contact-form";
        public static final String URL_REGISTER_GDPR = MyServer+"elGR/mobile-api/accept-tos";
        public static final String URL_SAVE_SEARCHES = MyServer+"elGR/mobile-api/get-saved-searches";
        public static final String URL_GET_URL_SEARCHES = MyServer+"elGR/mobile-api/get-save-searching";
        public static final String URL_CHANGE_VISIBILITY = MyServer+"elGR/mobile-api/change-profile-visibility";
        public static final String URL_EDIT_SEARCHES = MyServer+"elGR/mobile-api/edit-save-search";
        public static final String URL_DELETE_SEARCH = MyServer+"elGR/mobile-api/delete-save-search";
        public static final String URL_CHECK_SAVE_SEARCH = MyServer+"elGR/mobile-api/check-save-searches";
        public static final String URL_SEND_CVS = MyServer+"elGR/mobile-api/get-send-cvs";
        public static final String URL_GET_SUGGESTIONS = MyServer+"elGR/frontend-ajax/get-job-position-suggestions/";
        public static final String URL_GET_ANONYMOUS_TITLE =  MyServer+"elGR/mobile-api/get-anonymous-title";
        public static final String URL_TOKEN = MyServer + "elGR/mobile-api/get-token";
        public static final String GOOGLE_SERVER = "https://drive.google.com/";
        public static final String GOOLE_DRIVE_OPEN = GOOGLE_SERVER+"open?id=";

    }

    public static class ACTION_TYPES{
        public static String[] FILE_TYPES =
        {"application/msword","application/vnd.openxmlformats-officedocument.wordprocessingml.document", "application/vnd.openxmlformats-officedocument.wordprocessingml.template","application/vnd.ms-word.document.macroEnabled.12","application/vnd.ms-word.template.macroEnabled.12", // .doc & .docx
                "application/vnd.ms-powerpoint","application/vnd.openxmlformats-officedocument.presentationml.presentation", // .ppt & .pptx
                "application/vnd.ms-excel","application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", // .xls & .xlsx
                "text/plain",
                "application/pdf",
                "application/vnd.oasis.opendocument.text",
                "application/vnd.google-apps.kix",
                "application/vnd.google-apps.document","application/octet-stream","application/vnd.google-apps.document"};
        public static int CHOOSE_FILE = 24;
    }

    public static class CONNECTION_TYPES{
        public static String FILE = "FILE";
        public static String POST = "POST";
        public static String GET = "GET";
        public static String JSON = "JSON";
        public static String SOCIAL = "SOCIAL";
    }

    public static class  ERROR_MSG{ /// error types giving the appropriate answer to the user (check handleAnswer)
        public static String ERROR_SRVR = "error_server";
        public static String NO_INTERNET = "no_internet";
        public static String DATABASE_ERROR = "error_db";
    }


    public static class ADS{ /// Useful info for for decodind and encoding and sending server request or recving server's answer
        public static final String AD_SMALL_TYPE = "small_advert";
        public static final String AD_TYPE = "advert";
        public static final String TITLE = "Title";
        public static final String TEXT = "Text";
        public static final String LOGO = "Logo";
        public static final String ID = "Id";
        public static final String EMPTYPE = "EmpType";
        public static final String LOCATION = "Perioxi";
        public static final String PUBLISH_DATE = "PublishDate";
        public static final String NAME = "Name";
        public static final String CLIENT_ID = "ClientId";
        public static final String IS_ANONYMOUS = "IsAnonymous";
        public static final String CLIENT_NAME = "ClientName";
        public static final String FULL_TXT = "FullText";
        public static final String LINK = "Link";
        public static final String AD_CODE = "AdCode";
        public static final String TOTAL_PAGES = "TotalPages";
        public static final String TOTAL_ADS = "TotalAds";
        public static final String START_ADS = "FromAds";
        public static final String END_ADS = "ToAds";
        public static final String PAGE = "Page";
        public static final String SEO_URL = "seoUrl";
        public static final String FULL_TITLE = "FullTitle";
        public static final String ANONYMOUS_TITLE = "AnonymousTitle";
        public static final String GDPR_INFO = "GdprInfo";
        public static final String GDPR_RESPO = "GdprResponsibility";
        public static final String STATUS_ANONYMOUS = "anonymous";
//        public static final String [] CONTEXT_MENU = Skywalker.getContext().getResources().getStringArray(R.array.context_menu_items);
//        public static final String [] CONTEXT_SAVE_MENU = Skywalker.getContext().getResources().getStringArray(R.array.save_search_menu);
        public static final String HAS_QUESTIONS = "HasQuestions";
        public static final String [] ANSWER_TYPES = {"radio", "checkbox", "textarea", "advcheckbox"};
        public static final String [] DURATION_MAIL = {"3 Ημέρες","7 Ημέρες","14 Ημέρες","Κάθε Μήνα","Ποτέ"};
    }

    public static class JOB_CATEGORIES {
        public static final String CATEGORY_TYPE = "categories";
        public static final String SUBCATEGORY_TYPE = "subcategories";
        public static final String JOBPOSITION_TYPE = "jobpositions";
//        public static final String SELECT_CATEGORY = Skywalker.getContext().getResources().getString(R.string.category);
//        public static final String SELECT_SUBCATEGORY = Skywalker.getContext().getResources().getString(R.string.subcategory);
//        public static final String SELECT_JOBPOSITION = Skywalker.getContext().getResources().getString(R.string.job_position);
        public static final String JSON_CATEGORY_TYPE = "Category";
        public static final String JSON_SUBCATEOGORY_TYPE = "SubCategory";
        public static final String JSON_JOBPOSITION_TYPE = "JobPosition";
        public static final String KEYWORDS_TYPE = "keywords";
        public static final String SELECT_KEYWORDS = "Select keywords";
    }

    public static class ACADEMIC {
//        public static final String SELECT_UNIVERSITY = Skywalker.getContext().getResources().getString(R.string.university);
        public static final String UNIVERSITY_TYPE = "university";
        public static final String JSON_UNIVERSITY_TYPE = "University";

//        public static final String SELECT_EDUTYPE = Skywalker.getContext().getResources().getString(R.string.education);
        public static final String EDUCATION_TYPE = "edutype";
        public static final String JSON_EDUCATION_TYPE = "EduType";

//        public static final String SELECT_LANGUAGE = Skywalker.getContext().getResources().getString(R.string.language);
        public static final String FOREIGN_LANG_TYPE = "foreignlang";
        public static final String JSON_FOREIGN_LANG_TYPE = "ForeignLang";
        public static final String POST_FOREIGN_LABG = "languages";

//        public static final String SELECT_CERTIFICATION = Skywalker.getContext().getResources().getString(R.string.certification);
        public static final String CERTIFICATION_TYPE = "certificationlang";
        public static final String JSON_CERTIFICATION_LANG_TYPE = "ForeignLangCert";
    }

    public static class REGIONS {
//        public static final String SELECT_COUNTRY = Skywalker.getContext().getResources().getString(R.string.country);
//        public static final String SELECT_GEO_DEP = Skywalker.getContext().getResources().getString(R.string.gdepartment);
//        public static final String SELECT_STATE = Skywalker.getContext().getResources().getString(R.string.state);
//        public static final String SELECT_CITY = Skywalker.getContext().getResources().getString(R.string.city);
//        public static final String SELECT_MUNICIPALITY = Skywalker.getContext().getResources().getString(R.string.municipality);
        public static final String COUNTRY_TYPE = "countries";
        public static final String GEO_DEP_TYPE = "departments";
        public static final String STATE_TYPE = "states";
        public static final String CITY_TYPE = "cities";
        public static final String MUNICIPALITY_TYPE = "municipalities";
        public static final String All_REGIONS = "all";
        public static final String REGION = "region";
        public static final String JSON_COUNTRY_TYPE = "Country";
        public static final String JSON_GEO_DEP_TYPE = "GeoDepartment";
        public static final String JSON_STATE_TYPE = "State";
        public static final String JSON_CITY_TYPE = "City";
        public static final String JSON_MUNICIPALITY_TYPE = "Municipality";
    }

    public static class PersonalInfo{
        public static final String CANDIDATE_TYPE="candidate";
        public static final String NAME = "Name";
        public static final String SURNAME = "LastName";
        public static final String PHONE = "Phone";
        public static final String EMAIL = "Email";
        public static final String MOB_PHONE = "MobPhone";
        public static final String REGION = "Perioxi";
        public static final String ADDRESS = "Address";
        public static final String PHOTO = "Photo";
        public static final String CHANGEMAIL = "ChangeMail";
        public static final String BIRTH_DATE = "BirthDate";
        public static final String SEX = "Sex";
        public static final String ARMY_SERVICES = "ArmyStatus";
        public static final String DRIVING_LICENSE = "DrivingLic";
        public static final String TRAVEL_INTERNAL = "TravelInterval";
        public static final String RELOACATE = "CanRelocate";
        public static final String HOME_DISTANCE = "HomeDistance";
        public static final String LINKED_IN = "LinkedInProfile";
        public static final String PORTFOLIO = "Portfolio";
        public static final String CV_VIDEO = "BioVideo";
    }


    public static class BIO_INFO{    ///// Bio server json values
        public static final String VISIBILITY = "Visibility";
        public static final String BIO_ID = "Id";
        public static final String TITLE = "Title";
        public static final String INTERESTS = "Interests";
        public static final String KNOWLEDGES = "OtherKnowledges";
        public static final String COMPLETE_PERCENT = "Complete";

        public static final Map<String,Integer> attrToPos;
        static {
            Map<String,Integer> tempMap =new HashMap<String, Integer>() {
                {
                    put(BioPrefAreas.class.getSimpleName(), 0);
                    put(BioPrefPositions.class.getSimpleName(), 1);
                    put(BioWorkExperience.class.getSimpleName(), 2);
                    put(BioEducation.class.getSimpleName(), 3);
                    put(BioForeignLang.class.getSimpleName(), 4);
                    put(BioItSkills.class.getSimpleName(), 5);
                    put(BioSeminars.class.getSimpleName(), 6);
                }
            };

            attrToPos = Collections.unmodifiableMap(tempMap);

        }

        public static class BioPermissions{

            // Permissions
            public static final String AllowPrefWorkAreas = "AllowPrefWorkAreas";
            public static final String AllowPrefWorkPositions = "AllowPrefWorkPositions";
            public static final String AllowWorkExperiences = "AllowWorkExperiences";
            public static final String AllowEducations = "AllowEducations";
            public static final String AllowForeignLangs = "AllowForeignLangs";
            public static final String  AllowItSkills = "AllowItSkills";
            public static final String AllowSeminars = "AllowSeminars";
            public static final String  AllowPhoto = "AllowPhoto";
            public static final String  AllowCvs = "AllowCvs";
            public static final String  AllowOtherKnowledges = "AllowOtherKnowledges";
            public static final String AllowEndiaferonta = "AllowEndiaferonta";
            public static final String  AllowPortfolio = "AllowPortfolio";
            public static final String  AllowVideoCv = "AllowVideoCv";
            public static final String  AllowNames = "AllowNames";
            public static final String AllowMail = "AllowMail";
            public static final String AllowBirthday = "AllowBirthday";
            public static final String  AllowSex = "AllowSex";
            public static final String  AllowGeo = "AllowGeo";
            public static final String  AllowArmy = "AllowArmy";
            public static final String AllowDriving = "AllowDriving";
            public static final String AllowPhones = "AllowPhones";
            public static final String AllowMetegkatastasi = "AllowMetegkatastasi";
            public static final String  AllowTravels = "AllowTravels";
            public static final String AllowKmFromHome = "AllowKmFromHome";
            public static final String AllowLinkedIn = "AllowLinkedIn";

            //BioValue


            public static String[] getPermissionsTitles(){   //// get permission json key values as string array
                String[] titles = new String[26];
                titles[0] = VISIBILITY;
                titles[1] = AllowPrefWorkAreas;
                titles[2] = AllowPrefWorkPositions;
                titles[3] = AllowWorkExperiences;
                titles[4] = AllowEducations;
                titles[5] = AllowForeignLangs;
                titles[6] = AllowItSkills;
                titles[7] = AllowSeminars;
                titles[8] = AllowPhoto;
                titles[9] = AllowCvs;
                titles[10] = AllowOtherKnowledges;
                titles[11] = AllowEndiaferonta;
                titles[12] = AllowPortfolio;
                titles[13] = AllowVideoCv;
                titles[14] = AllowNames;
                titles[15] = AllowMail;
                titles[16] = AllowBirthday;
                titles[17] = AllowSex;
                titles[18] = AllowGeo;
                titles[19] = AllowArmy;
                titles[20] = AllowDriving;
                titles[21] = AllowPhones;
                titles[22] = AllowMetegkatastasi;
                titles[23] = AllowTravels;
                titles[24] = AllowKmFromHome;
                titles[25] = AllowLinkedIn;

                return titles;
            }

        }

        public static class BioPrefAreas {
            public static final String COUNTRY = "PrefCountry";
            public static final String DEPARTMENT = "PrefDepartment";
            public static final String STATE = "PrefState";
            public static final String CITY = "PrefCity";
            public static final String MUNICIPALITY = "PrefMunicipality";
        }

        public static class BioPrefPositions{
            public static final String CATEGORY = "PrefCategory";
            public static final String SUBCATEGORY = "PrefSubCategory";
            public static final String JOBPOSITION = "PrefPosition";
        }

        public static class BioWorkExperience{
            public static final String START = "Start";
            public static final String END = "End";
            public static final String CATEGORY = "Category";
            public static final String SUBCATEGORY = "SubCategory";
            public static final String POSITION = "Position";
            public static final String SPECIALITY = "Eidikotita";
            public static final String LEVEL = "Level";
            public static final String EMP_TYPE = "Employmenttype";
            public static final String COMPANY = "Company";
            public static final String WORK_COUNTRY = "WorkCountry";
            public static final String WORK_DEPARTMENT = "WorkDepartment";
            public static final String WORK_STATE = "WorkState";
            public static final String WORK_CITY = "WorkCity";
            public static final String WORK_MUNICIPALITY = "WorkMunicipality";
            public static final String REGION = "Perioxi";
            public static final String TODAY = "Today";
        }

        public static class BioEducation{
            public static final String FROM = "DateFrom";
            public static final String TO = "DateTo";
            public static final String EDU_LEVEL = "EducationLevel";
            public static final String UNIVERSITY = "University";
            public static final String EDU_DEPARTMENT = "EduDepartment";
            public static final String EDU_TITLE = "EduTitle";
        }


        public static class BioForeignLang{
            public static final String LANGUAGE = "Lang";
            public static final String LEVEL = "Level";
            public static final String CERTIFICATE = "Certificate";
        }

        public static class BioItSkills{
            public static final String IT_CATEGORY = "ItCategory";
            public static final String LEVEL = "Level";
            public static final String IT_SKILL = "ItSkill";
        }

        public static class BioSeminars{
            public static final String NAME = "Name";
            public static final String FOREAS = "Foreas";
            public static final String DATE = "SemDate";
            public static final String CARTIFICATE = "Certificate";
        }

        public static class BioSpinnerValues{
            public static final String NO_VALUE = "Χωρίς Επιλογή";
            public static final String[] SEX_VALUES = {NO_VALUE,"Άνδρας","Γυναίκα"};
            public static final String[] ARMY_VALUES = {NO_VALUE,"ΕΚΠΛΗΡΩΜΕΝΕΣ", "ΑΠΑΛΑΓΜΕΝΟΣ", "ΕΝ ΕΝΕΡΓΕΙΑ", "ΣΤΟ ΜΕΛΛΟΝ"};
            public static final String[] DRIVING_VALUES = {NO_VALUE,"Α ΚΑΤΗΓΟΡΙΑΣ", "Β ΚΑΤΗΓΟΡΙΑΣ", "Γ ΚΑΤΗΓΟΡΙΑΣ", "Δ ΚΑΤΗΓΟΡΙΑΣ", "Ε ΚΑΤΗΓΟΡΙΑΣ"};
            public static final String[] TRAVEL_VALUES = {NO_VALUE,"ΣΥΧΝΑ", "ΣΠΑΝΙΑ", "ΠΟΤΕ"};
            public static final String[] RELOCATION_VALUES = {NO_VALUE,"ΝΑΙ", "ΟΧΙ", "ΙΣΩΣ"};
            public static final String[] DISTANCE_VALUES = {NO_VALUE,"5 KM", "10 KM", "25 KM", "50 KM", "ΑΔΙΑΦΟΡΟ"};
            public static final String[] POS_VALUES = {NO_VALUE,"assistant","middlemngm","supervisor","manager","director"};
            public static final String[] EDU_VALUES = {"ΛΥΚΕΙΟ", "ΤΕΕ (ΤΕΧΝ. ΕΠΑΓΓ.)", "ΙΕΚ / ΤΕΧΝΙΚΗ ΣΧΟΛΗ", "ΤΕΙ", "ΑΕΙ", "ΜΕΤΑΠΤΥΧΙΑΚΟ", "ΔΙΔΑΚΤΟΡΙΚΟ", "ΙΔΙΩΤΙΚΑ ΚΟΛΛΕΓΙΑ - ΠΑΝΕΠΙΣΤΗΜΙΑ"};
            public static final String[] LANG_LEVELS_VALUES = {"ΣΤΟΙΧΕΙΩΔΗ","ΜΕΤΡΙΑ", "ΚΑΛΑ", "ΠΟΛΥ ΚΑΛΑ", "ΑΡΙΣΤΑ", "ΜΗΤΡΙΚΗ"};
            public static final String[] IT_VALUES = {"BEGINNER","NORMAL", "ADVANCED", "POWER", "EXPERT"};
        }
    }




    public static boolean isNetworkConnected(Context context) {
        boolean result = false;
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (connectivityManager != null) {
                NetworkCapabilities capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.getActiveNetwork());
                if (capabilities != null) {
                    if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                        result = true;
                    } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                        result = true;
                    }
                }
            }
        } else {
            if (connectivityManager != null) {
                NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
                if (activeNetwork != null) {
                    // connected to the internet
                    if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                        result = true;
                    } else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                        result = true;
                    }
                }
            }
        }
        return result;
    }

    /**
     * Checking whether network is connected
     * @param context Context to get {@link ConnectivityManager}
     * @return true if Network is connected, else false
     */
    public static boolean isConnected(Context context){
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
        if (activeNetwork != null && activeNetwork.isConnected()) {
            int networkType = activeNetwork.getType();
            return networkType == ConnectivityManager.TYPE_WIFI || networkType == ConnectivityManager.TYPE_MOBILE;
        } else {
            return false;
        }
    }

//Has been replaced for newest version of API
       /**
        Public static boolean checkInternetAccess(Context c){
        ConnectivityManager connectivityManager = (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            return true;
        } else
            return false;
        }**/

       static final String convertInputStreamToString(InputStream inputStream) throws IOException {
           BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
           String line = "";
           String result = "";
           while((line = bufferedReader.readLine()) != null)
               result += line;

           inputStream.close();
           return result;

       }

       public static  boolean getCandidateDetails(){ //// get candidate personal and bio details
           String candDetails="",bioDetails="";
           DatabaseHelper db = new DatabaseHelper(Skywalker.getContext());
           Cache cache = new Cache(Skywalker.getContext());
           try {
               candDetails = new ServerRequest(Skywalker.getContext()).execute(Settings.CONNECTION_TYPES.POST,"Id",db.getUserId(),"Token",cache.getServerToken(),Settings.URLS.CANDIDATE_URL).get(); /// personal details and a few bio details
               Log.e("CANDDETAILS",candDetails);
               JsonHelper jsonHelper = new JsonHelper(candDetails);
               if(jsonHelper.invalidToken()){ /// if the token is invalid update it from the server
                    cache.saveUserToken(new ServerRequest(Skywalker.getContext()).execute(CONNECTION_TYPES.POST,"Id",db.getUserId(), URLS.URL_TOKEN).get());
                    candDetails = new ServerRequest(Skywalker.getContext()).execute(Settings.CONNECTION_TYPES.POST,"Id",db.getUserId(),"Token",cache.getServerToken(),Settings.URLS.CANDIDATE_URL).get();
               }
               bioDetails = new ServerRequest(Skywalker.getContext()).execute(Settings.CONNECTION_TYPES.POST,"Id",db.getUserId(),"Token",cache.getServerToken(),Settings.URLS.BIO_URL).get(); //// get bio detils from server


           } catch (ExecutionException e) {
               e.printStackTrace();
               if(candDetails.equals(ERROR_MSG.ERROR_SRVR) || bioDetails.equals(ERROR_MSG.ERROR_SRVR)){
                   Toast.makeText(Skywalker.getContext(),Skywalker.getContext().getResources().getString(R.string.server_error),Toast.LENGTH_LONG).show();
                   return false;
               }
               return false;
           } catch (InterruptedException e) {
               e.printStackTrace();
               if(candDetails.equals(ERROR_MSG.ERROR_SRVR) || bioDetails.equals(ERROR_MSG.ERROR_SRVR)){
                   Toast.makeText(Skywalker.getContext(),Skywalker.getContext().getResources().getString(R.string.server_error),Toast.LENGTH_LONG).show();
                   return false;
               }
               return false;
           } catch (JSONException e) {
               e.printStackTrace();
               if(candDetails.equals(ERROR_MSG.ERROR_SRVR) || bioDetails.equals(ERROR_MSG.ERROR_SRVR)){
                   Toast.makeText(Skywalker.getContext(),Skywalker.getContext().getResources().getString(R.string.server_error),Toast.LENGTH_LONG).show();
                   return false;
               }
               return false;
           }
           Log.e("Candidate Details",candDetails);
           Log.e("Candidate Bio",bioDetails);
           try {
               CVProfile profile = new CVProfile(new JsonHelper(candDetails).decodePersonalInfo(),new JsonHelper(bioDetails).decodeBioInfo()); //// construct cv profile
               if(cache.saveProfile(profile)){
                   Log.e("CACHE","Profile saved");
               }
               else{
                   Log.e("CACHE","Profile not saved");
               }
           } catch (JSONException e) {
               e.printStackTrace();
               return false;
           }
           return true;
       }


       public static void alertMessage(String message){
           AlertDialog.Builder builder = new AlertDialog.Builder(Skywalker.getContext());
           builder.setMessage(message)
                   .setPositiveButton(Skywalker.getContext().getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                       public void onClick(DialogInterface dialog, int id) {

                       }
                   })
                   .setNegativeButton(Skywalker.getContext().getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
                       public void onClick(DialogInterface dialog, int id) {
                           dialog.dismiss();
                       }
                   });
           builder.show();
       }

       public static String replaceAllExceptLast(String regex,String replace, String toReplace){
           StringBuilder sb = new StringBuilder(toReplace);
           int first = toReplace.indexOf(regex), last = toReplace.lastIndexOf(regex);
//           String [] pieces = toReplace.split(regex);
//           Log.e("NUM OCCUR",pieces.length+"");
           toReplace = toReplace.replace(regex,replace);
           if (first != last) {
               for(int i=first; i<last-1; i++){
                   if(sb.charAt(i) == regex.charAt(0))
                      sb.setCharAt(i,replace.charAt(0));
               }
               toReplace = sb.toString();
           }



           return  toReplace;
       }



    public static String randomIdentifier() {
        final String lexicon = "ABCDEFGHIJKLMNOPQRSTUVWXYZ12345674890";

        final java.util.Random rand = new java.util.Random();

        // consider using a Map<String,Boolean> to say whether the identifier is being used or not
        final Set<String> identifiers = new HashSet<String>();
        StringBuilder builder = new StringBuilder();
        while(builder.toString().length() == 0) {
            int length = rand.nextInt(5)+5;
            for(int i = 0; i < length; i++) {
                builder.append(lexicon.charAt(rand.nextInt(lexicon.length())));
            }
            if(identifiers.contains(builder.toString())) {
                builder = new StringBuilder();
            }
        }
        return builder.toString();
    }

    public static String deAccent(String str) {
        String nfdNormalizedString = Normalizer.normalize(str, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(nfdNormalizedString).replaceAll("");
    }


    public static String parseHtml(String reg){
        return reg.replaceAll("\\<.*?>","");
    }

}



