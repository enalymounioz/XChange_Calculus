package com.sky.casper.skywalker_new_app.Helpers;

import android.content.Context;

import com.sky.casper.skywalker_new_app.R;
import com.sky.casper.skywalker_new_app.Skywalker;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Settings {





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
        public static final String LOGIN_CANDIDATE = MyServer + "elGR/mobile-api/get-candidate-id";
        public static final String BIOS_URL = MyServer + "elGR/mobile-api/get-bios";
        public static final String UPLOAD_BIO_URL_CONN = MyServer + "elGR/mobile-api/upload-bio-connected";
        public static final String SEND_BIO_CONN_URL = MyServer + "elGR/mobile-api/send-bio-to-ad";
        public static final String REGISTER_URL = MyServer + "elGR/mobile-api/register-candidate";
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

    public static class FRIENDS{
        public static final String SHOW_PROFILE = "show";
        public static final String REQUEST_FRIENDSHIP = "request";
        public static final String MESSAGES = "availability";
    }

    // regions
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

    public static class EVENT{
        public static final String TITLE = "Title";
        public static final String FROM = "From";
        public static final String TO = "To";
        public static final String SITE = "Site";
    }

    public static class CERTIFICATE{
        public static final String TITLE = "Title";
        public static final String FROM = "From";
        public static final String TO = "To";
        public static final String SITE = "Site";
        public static final String ACTION = "Action";
    }


    //employee type
    public static class EMPLOYE {
//        public static final String SELECT_EMPTYPE = Skywalker.getContext().getResources().getString(R.string.emplyee_type);
        public static final String EMPLOYEE_TYPE = "emptype";
        public static final String JSON_EMPTYPE_TYPE = "EmpType";
    }

    public static class UNIVERSITY{
//        public static final String SELECT_UNIVERSITY = Skywalker.getContext().getResources().getString(R.string.university);
        public static final String UNIVERSITY_TYPE = "university";
        public static final String JSON_UNIVERSITY_TYPE = "University";
    }

    public static class EDUCATION{
//        public static final String SELECT_EDUTYPE = Skywalker.getContext().getResources().getString(R.string.education);
        public static final String EDUCATION_TYPE = "edutype";
        public static final String JSON_EDUCATION_TYPE = "EduType";
    }

    public static class FOREIGN_LANG{
//        public static final String SELECT_LANGUAGE = Skywalker.getContext().getResources().getString(R.string.language);
        public static final String FOREIGN_LANG_TYPE = "foreignlang";
        public static final String JSON_FOREIGN_LANG_TYPE = "ForeignLang";
        public static final String POST_FOREIGN_LABG = "languages";
    }

    public static class CERTIFICATION_LANG{
//        public static final String SELECT_CERTIFICATION = Skywalker.getContext().getResources().getString(R.string.certification);
        public static final String CERTIFICATION_TYPE = "certificationlang";
        public static final String JSON_CERTIFICATION_LANG_TYPE = "ForeignLangCert";
    }

    public static class IT_CATEGORY{
//        public static final String SELECT_ITCATEGORY = Skywalker.getContext().getResources().getString(R.string.it_category);
        public static final String ITCATEGORY_TYPE = "itcategory";
        public static final String JSON_ITCATEGORY_TYPE = "ItCategory";
    }

    public static class IT_SKILL{
//        public static final String SELECT_ITSKILL = Skywalker.getContext().getResources().getString(R.string.it_title_software);
        public static final String ITSKILL_TYPE = "itskill";
        public static final String JSON_ITSKILL_TYPE = "ItSkill";
    }


    static final String  getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }
}
