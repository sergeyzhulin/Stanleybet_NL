package lt.appcamp.stanleybet.app;


import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import lt.appcamp.utils.AppLog;
import lt.appcamp.stanleybet.R;
import lt.appcamp.stanleybet.auth.AnonymousAuth;
import lt.appcamp.stanleybet.auth.BaseAuth;
import lt.appcamp.stanleybet.factory.AuthFactory;
import lt.appcamp.stanleybet.helper.ConWin;
import lt.appcamp.stanleybet.helper.TimeHelper;
import lt.appcamp.stanleybet.services.Api;
import lt.appcamp.stanleybet.services.ApiError;


public class App extends Application {
	
	protected static final String TAG = App.class.getSimpleName();

    public String imageBaseUrl;
    public String locale;

    public static final String PREF_FILE_NAME = "app_preference";
    private static final String IMAGE_BASE_URL = "image_base_url";
    public static final String LOCALE = "locale";
    private static final String PREF_RCV_EVENT_PUSH = "receive_event_push";
    private static final String PREF_RCV_DEALS_PUSH = "receive_deals_push";
    private static final String PREF_DATA_REFRESH_TIMESTAMP = "data_refresh_timestamp";
    private static final String PREF_SHOW_WALKTHROUGH = "show_walkthrough";

    private static App instance;
    //TODO hack
    private static final int REFRESH_INT = 3600; //seconds

    public BaseAuth auth;
    public Api api;
    public RequestQueue queue;
    public boolean receiveEventPush;
    public boolean receiveDealsPush;
    public long refreshDataTimestamp = 0;
    public boolean showWalkthrough = true;
    
    /**FIXME: workaround for WebToPay library **/
    public long lastPaymentTimestamp;

    public static App instance() {
        return instance;
    }

    private SharedPreferences getPreferences() {
        return getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
    }

    public void commitPreferences(Context context) {
        SharedPreferences pref = getPreferences();
        SharedPreferences.Editor editor = pref.edit();

        editor.putString(IMAGE_BASE_URL, imageBaseUrl);
        editor.putString(LOCALE, locale);
        editor.putBoolean(PREF_RCV_EVENT_PUSH, receiveEventPush);
        editor.putBoolean(PREF_RCV_DEALS_PUSH, receiveDealsPush);
        editor.putLong(PREF_DATA_REFRESH_TIMESTAMP, refreshDataTimestamp);
        editor.putBoolean(PREF_SHOW_WALKTHROUGH, showWalkthrough);

        editor.commit();
        AppLog.d("app:" + toString());
    }

    public void pullPreferences(Context context) {
        SharedPreferences pref = getPreferences();
        imageBaseUrl = pref.getString(IMAGE_BASE_URL, ConWin.IMAGE_DEFAULT_BASE_URL);
        locale = pref.getString(LOCALE, "");
        receiveEventPush = pref.getBoolean(PREF_RCV_EVENT_PUSH, false);
        receiveDealsPush = pref.getBoolean(PREF_RCV_DEALS_PUSH, false);
        refreshDataTimestamp = pref.getLong(PREF_DATA_REFRESH_TIMESTAMP, 0);
        showWalkthrough = pref.getBoolean(PREF_SHOW_WALKTHROUGH, true);
        AppLog.d("app:" + toString());
    }

    public boolean dataRefreshNeeded() {

        long delta = TimeHelper.getNowTimestampGMT() - refreshDataTimestamp;

        if (delta > REFRESH_INT) {
            return true;
        }
        return false;
    }


    @Override
    public String toString() {
        return "App{" +
                "imageBaseUrl='" + imageBaseUrl + '\'' +
                ", locale='" + locale + '\'' +
                '}';
    }


    @Override
    public void onCreate() {
        super.onCreate();

        //craete instanse
        instance = this;

        //init
        queue = Volley.newRequestQueue(this);
        api = new Api(this, queue);
        auth = AuthFactory.getAuth(this);


    }
    
    /* Anonymous login methods. Placed here for convenience. Maybe move to BaseActivity */
    /** Check to see if user has an auth attached **/
    public boolean isUserAuthAvailable() {
    	if(this.auth != null) {
    		if(!this.auth.isUserLoggedIn() && !this.auth.isAnonymousLogin()) {
    			return false;
    		} else {
    			return true;
    		}    		
    	} else {
    		return false;    		
    	}
    }
    
    /** Register a new user auth (Anonymous). Update user and device details. 
     *  Anonymous login WEB service call. Internet is a requirement 
     *  
     *  @param
     *  final Activity activity - calling activity
     *  final ProgressBar progress - the progress bar to use, if null - there is no progress bar
     *  Response.Listener<BaseAuth> successListener - custom success listener (nullable)
     *  Response.ErrorListener errorListener - custom error listener (nullable)
     *  
     **/
    //TODO: find out if the custom listener are actually needed.
    public void registerUser(final Activity activity, final ProgressBar progress, Response.Listener<BaseAuth> successListener,
  		   Response.ErrorListener errorListener) {
    	//Create anonymous authentication
       	AnonymousAuth anonymousAuth = new AnonymousAuth();

       	//show the progress bar
       	if(progress != null) //if given - show, else - ignore
       		progress.setVisibility(View.VISIBLE);
       	
       	// No other auth is present, so not to overwrite the user current login status
       	if(!isUserAuthAvailable()) {
	        //update user in server
	        api.registerUser(anonymousAuth, (String) null, receiveDealsPush, receiveDealsPush, locale,
	        		successListener != null ?  successListener : new Response.Listener<BaseAuth>() {
	                    @Override
	                    public void onResponse(BaseAuth response) {
	                    	//stop the progress bar
	                    	if(progress != null) //if given - show, else - ignore
	                    		progress.setVisibility(View.GONE);
	                        //get response and save to application                   		
	                   		auth = response;
	                       	auth.pushAuth(getBaseContext());  //Using app as context instead of activity                     		
	                      
	                       	//Debug stuff
	                       	if(auth != null) {
	                       		Log.d(TAG, "regsiterUser(): new anonymous auth was added successfully");                       		
	                            Log.d(TAG, "auth: " + auth.toString() + " token: " + auth.token);
	                       	}
	                        
	                   }
	                }, errorListener != null ?  errorListener : new Response.ErrorListener() {
	                	@Override
	                	public void onErrorResponse(VolleyError error) {
	                		//stop the progress bar
	                		if(progress != null) //if given - show, else - ignore
	                			progress.setVisibility(View.GONE);
	                          	
	                   		ApiError.onError(getBaseContext(), error); //Using app as context instead of activity
	                   		
	                   		//If there was an error and there is no user auth available. Die with an error message
	                   		if(!isUserAuthAvailable()) {
	                   			//Toast.makeText(activity, R.string.critical_error, Toast.LENGTH_SHORT).show();
	                   			activity.finish();                   		
	                       	} else {
	                       		//Silently ignore
	                        }
	                	}
	                }
	        );
       	} else {
       		//Silently ignore
       		Log.d(TAG, "regsiterUser(): authentication present. Ignoring.");
       	}
    }
    
}
