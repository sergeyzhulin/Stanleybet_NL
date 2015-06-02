package lt.appcamp.stanleybet.auth;

import android.content.SharedPreferences;
import android.text.TextUtils;

import org.json.JSONException;
import org.json.JSONObject;

import lt.appcamp.utils.AppJson;

/**
 * Created by Tadas on 6/6/13.
 */
public class NativeAuth extends BaseAuth {
    public static final int SEX_MALE = 1;
    public static final int SEX_FEMALE = 2;
    public static final int SEX_NO = 0;

    private static final String SALT = "stabas56";

    private static final String FIRST_NAME = "first_name";
    private static final String LAST_NAME = "last_name";

    private static final String PHONE_NO = "phone_no";
    private static final String PASSWORD_SH1 = "password";
    private static final String BIRTH_DATE = "birth_date";
    private static final String GENDER = "gender";


    public String firstName;
    public String lastName;
    public String phoneNo;
    public String password;
    public String passwordSh1;
    public String birthDate;
    public int gender;


    public String getPasswordSha1() {
        if (TextUtils.isEmpty(password)) {
            return null;
        }
        return encryptPassword(password + SALT);
    }

    public NativeAuth(){};

    public NativeAuth(JSONObject json) throws JSONException {
        super(json);
        this.firstName = AppJson.getString(json, "first_name");
        this.lastName = AppJson.getString(json, "last_name");
        this.passwordSh1 = AppJson.getString(json, "password");
        this.phoneNo = AppJson.getString(json, "phone_no");
        this.gender = AppJson.getInt(json, "gender");
        this.birthDate = AppJson.getString(json, "birth_date");

    }

    @Override
    public boolean isUserLoggedIn() {
        return !TextUtils.isEmpty(token);
    }

    @Override
    public int getUserAuthType() {
        return LOGIN_NATIVE;
    }

    @Override
    public String getUserInfoField() {
        return email;
    }


    public void doPullAuth(SharedPreferences pref) {

        firstName = pref.getString(FIRST_NAME, null);
        lastName = pref.getString(LAST_NAME, null);
        phoneNo = pref.getString(PHONE_NO, null);
        passwordSh1 = pref.getString(PASSWORD_SH1, null);
        birthDate = pref.getString(BIRTH_DATE, null);
        gender = pref.getInt(GENDER, 0);

    }

    /**
     * Commit not required
     * @param editor
     */
    public void doPushAuth(SharedPreferences.Editor editor) {

        editor.putString(FIRST_NAME, firstName);
        editor.putString(LAST_NAME, lastName);
        editor.putString(PHONE_NO, phoneNo);
        editor.putString(PASSWORD_SH1, getPasswordSha1());
        editor.putString(BIRTH_DATE, birthDate);
        editor.putInt(GENDER, gender);

    }


    @Override
    public String toString() {
        return "NativeAuth{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", phoneNo='" + phoneNo + '\'' +
                ", password='" + password + '\'' +
                ", passwordSh1='" + passwordSh1 + '\'' +
                ", birthDate=" + birthDate +
                ", gender=" + gender +
                ", token='" + token + '\'' +
                '}';
    }
}
