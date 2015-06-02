package lt.appcamp.stanleybet.auth;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Justas on 10/25/13.
 */
public class AnonymousAuth extends BaseAuth {
	
	public String deviceID;

    public AnonymousAuth(){};

    //NOTICE: should not be used anymore.
    public AnonymousAuth(JSONObject json) throws JSONException {
    	//FIXME: the anonymous authentication is never created. My bets gues because i did not call super constructor.
        //super.token = AppJson.getString(json, "token");  
    	//BUGFIX: attempting to call the super constructor. This is by no means ideal. Should be revised later.
    	super(json);
    	//this.deviceID = getDeviceId(Context context); //FUCK ME
    }
    
    //NOTICE: context is needed to get the device id, which should be used instead of email in the case of anonymous login
    public AnonymousAuth(JSONObject json, Context context) throws JSONException {    	
    	super(json);
    	this.deviceID = getDeviceId(context); //TODO: review later
    }
    
    /**
     *  Always return false, because the user is not actually logged in. He is just authenticated.
     *  This is a new requirement for anonymous purchases.
     */
    @Override
    public boolean isUserLoggedIn() {
        return false;
    }

    @Override
    public int getUserAuthType() {
        return LOGIN_ANONYMOUS;
    }

    //FIXME: this makes no sense with anonymous auth...
    @Override
    public String getUserInfoField() {
    	//return email;
    	//BUGFIX: return device id instead of email
    	return this.deviceID;
    }

    @Override
    public String toString() {
        return "AnonymousAuth{" +
                "deviceID='" + deviceID != null ? deviceID : "null" + '\'' +
                ", token='" + token + '\'' +
                '}';
    }
    
    //helper function. Maybe useless...
    public void saveDeviceId(String deviceID) {
    	this.deviceID = deviceID;
    }
    
    public String getDeviceName() {
    	return android.os.Build.MODEL;
    }
    
    public String getOSVersion() {
    	return android.os.Build.VERSION.RELEASE;
    }
    public int getSDKVersion() {
    	return android.os.Build.VERSION.SDK_INT;
    }
    
	@Override
	protected void doPushAuth(Editor editor) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void doPullAuth(SharedPreferences preferences) {
		// TODO Auto-generated method stub
		
	}

	//Return true. This is anonymous login.
	@Override
	public boolean isAnonymousLogin() {
		return true;
	}
}
