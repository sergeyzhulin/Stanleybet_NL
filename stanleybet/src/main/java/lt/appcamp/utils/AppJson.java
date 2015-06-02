
package lt.appcamp.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class AppJson {

    public static JSONObject getJsonObject(JSONObject j, String key) {
        if (j.isNull(key)) {
            return new JSONObject();
        } else {

            try {
                return j.getJSONObject(key);
            } catch (JSONException e) {
                AppLog.e(e,true);
                return new JSONObject();
            }
        }
    }

    public static JSONArray getJsonArray(JSONObject j, String key) throws JSONException {
        if (j.isNull(key)) {
            return new JSONArray();
        } else {
            return j.getJSONArray(key);
        }
    }

    public static String getString(JSONObject j, String key) throws JSONException {
        if (j.isNull(key)) {
            return "";
        } else {
            return j.getString(key);
        }
    }

    public static int getInt(JSONObject j, String key) throws JSONException {

        if (j.isNull(key)) {
            return -1;
        } else {
            return j.getInt(key);
        }
    }

    public static long getLong(JSONObject j, String key) throws JSONException {

        if (j.isNull(key)) {
            return -1;
        } else {
            return j.getLong(key);
        }
    }

    public static boolean getBooleanFromInt(JSONObject j, String key) throws JSONException {

        if (j.isNull(key)) {
            return false;
        } else {
            return (j.getInt(key) == 1);
        }
    }

    public static double getDouble(JSONObject json, String key) throws JSONException {
        if (json.isNull(key)) {
            return -1;
        } else {
            return json.getDouble(key);
        }
    }
}


