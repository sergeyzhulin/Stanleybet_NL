package lt.appcamp.stanleybet.auth;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;

import lt.appcamp.utils.AppJson;
//import lt.appcamp.visitvilnius.helper.IdentificationHelper;

/**
 * Created by Tadas on 6/10/13.
 */
public abstract class BaseAuth {

    public static final String EMAIL = "email";
    public static final String TOKEN = "token";
    public static final String LOGIN_TYPE = "login_type";

    public String token;
    public String email;
    public int loginType;

    public static final int LOGIN_NATIVE = 3;
    public static final int LOGIN_FB = 1;
    public static final int LOGIN_GPLUS = 2;
    public static final int LOGIN_ANONYMOUS = 4;

    private static final String AUTH_FILE = "auth.xml";

    public BaseAuth() {
    }

    public BaseAuth(JSONObject json) throws JSONException {
        this.email = AppJson.getString(json, "email");
        this.token = AppJson.getString(json, "token");
        Log.d("testing", "token: " + AppJson.getString(json, "token")); //delete later
    }

    public final String getDeviceId(Context context) {
        return toString();

        //return IdentificationHelper.getAndroidId(context);
    }
    
    //DEPRECATED on 2013 11 06
    /** Method that generates a unique session ID based on timestamp and device id **/
    public final String getEncodedDeviceId(Context context) {
    	//String baseId = IdentificationHelper.getAndroidId(context);
        return new String(Base64.encode((getDeviceId(context) + System.currentTimeMillis()).getBytes(), 0));
    }
    

    public static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences(AUTH_FILE, Context.MODE_PRIVATE);
    }

    protected static String encryptPassword(String password) {
        String sha1 = "";
        try {
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(password.getBytes("UTF-8"));
            sha1 = byteToHex(crypt.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return sha1;
    }

    protected static String byteToHex(final byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash) {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }

    public final void pullAuth(Context context) {
        SharedPreferences pref = getSharedPreferences(context);
        email = pref.getString(EMAIL, null);
        token = pref.getString(TOKEN, null);
        loginType = pref.getInt(LOGIN_TYPE, -1);
        doPullAuth(pref);
    }

    public final void pushAuth(Context context) {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(EMAIL, email);
        editor.putString(TOKEN, token);
        editor.putInt(LOGIN_TYPE, getUserAuthType());
        doPushAuth(editor);

        //commit
        editor.commit();
    }
    
    //Check if it was an anonymous login. Return false by default.
	public boolean isAnonymousLogin() {
		return false;
	}

    public abstract boolean isUserLoggedIn();

    public abstract int getUserAuthType();

    public abstract String getUserInfoField();

    protected abstract void doPushAuth(SharedPreferences.Editor editor);

    protected abstract void doPullAuth(SharedPreferences preferences);
}
