package com.sky.casper.skywalker_new_app.Helpers;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.opengl.Visibility;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import com.sky.casper.skywalker_new_app.Models.CVProfile;
import com.sky.casper.skywalker_new_app.R;
import com.sky.casper.skywalker_new_app.Skywalker;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

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

    public static class BIO_INFO{    ///// Bio server json values
        public static final String VISIBILITY = "Visibility";

        public static class BioPermissions{
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
           String candDetails=null,bioDetails=null;
           DatabaseHelper db = new DatabaseHelper(Skywalker.getContext());
           Cache cache = new Cache(Skywalker.getContext());
           try {
               candDetails = new ServerRequest(Skywalker.getContext()).execute(Settings.CONNECTION_TYPES.POST,"Id",db.getUserId(),"Token",cache.getServerToken(),Settings.URLS.CANDIDATE_URL).get(); /// personal details and a few bio details
               JsonHelper jsonHelper = new JsonHelper(candDetails);
               if(jsonHelper.invalidToken()){ /// if the token is invalid update it from the server
                    cache.saveUserToken(new ServerRequest(Skywalker.getContext()).execute(CONNECTION_TYPES.POST,"Id",db.getUserId()).get());
                    candDetails = new ServerRequest(Skywalker.getContext()).execute(Settings.CONNECTION_TYPES.POST,"Id",db.getUserId(),"Token",cache.getServerToken(),Settings.URLS.CANDIDATE_URL).get();
               }
               bioDetails = new ServerRequest(Skywalker.getContext()).execute(Settings.CONNECTION_TYPES.POST,"Id",db.getUserId(),"Token",cache.getServerToken(),Settings.URLS.BIO_URL).get(); //// get bio detils from server

               if(candDetails.equals(ERROR_MSG.ERROR_SRVR) || bioDetails.equals(ERROR_MSG.ERROR_SRVR)){
                   Toast.makeText(Skywalker.getContext(),Skywalker.getContext().getResources().getString(R.string.server_error),Toast.LENGTH_LONG).show();
                   return false;
               }
           } catch (ExecutionException e) {
               e.printStackTrace();
               return false;
           } catch (InterruptedException e) {
               e.printStackTrace();
               return false;
           } catch (JSONException e) {
               e.printStackTrace();
               return false;
           }
           Log.e("Candidate Details",candDetails);
           Log.e("Candidate Bio",bioDetails);
           try {
               CVProfile profile = new CVProfile(new JsonHelper(candDetails).decodePersonalAndBioInfo(),new JsonHelper(bioDetails).decodeBioInfo()); //// construct cv profile
               cache.saveProfile(profile);
           } catch (JSONException e) {
               e.printStackTrace();
               return false;
           }
           return true;
       }

}



