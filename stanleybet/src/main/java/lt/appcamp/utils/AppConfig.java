package lt.appcamp.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * User: Vykintas Valkaitis <v.valkaitis@appcamp.lt>
 * Date: 2013-05-13
 */
public class AppConfig {
    /**
     * Shared preference key
     */
    protected static final String PREFERENCE_key = "Config";
    /**
     * Object instance (Singleton pattern)
     */
    protected static AppConfig mInstance = null;
    /**
     * Shared preference and editor
     */
    protected SharedPreferences mSettings = null;
    protected SharedPreferences.Editor mEditor = null;
    /**
     * AppActivity object
     */
    protected AppActivity mAppActivity = null;

    /**
     * Private constructor (Singleton pattern)
     *
     * @param activity
     */
    private AppConfig(AppActivity activity) {
        mAppActivity = activity;
    }

    /**
     * Get instance (Singleton pattern)
     *
     * @param activity
     */
    public static AppConfig getInstance(AppActivity activity) {
        if (mInstance == null) {
            mInstance = new AppConfig(activity);
        }

        return mInstance;
    }

    /**
     * Put String value
     *
     * @param key
     * @param value
     */
    public void setValue(String key, String value) {
        // enter edit mode
        edit();

        // put value
        mEditor.putString(key, value);

        // save changes
        save();
    }

    /**
     * Init settings mode
     */
    protected void init() {
        try {
            if (mSettings == null) {
                mSettings = mAppActivity.getSharedPreferences(PREFERENCE_key, Context.MODE_PRIVATE);
            }
        } catch (Exception e) {
            AppLog.e(e);
        }
    }

    /**
     * Set Edit mode
     */
    protected void edit() {
        init();

        try {
            if (mEditor == null) {
                mEditor = mSettings.edit();
            }
        } catch (Exception e) {
            AppLog.e(e);
        }
    }

    /**
     * Save changes
     */
    protected void save() {
        try {
            mEditor.apply();
        } catch (Exception e) {
            AppLog.e(e);
        }
    }

    /**
     * Put boolean value
     *
     * @param key
     * @param value
     */
    public void setValue(String key, boolean value) {
        // enter edit mode
        edit();

        // put value
        mEditor.putBoolean(key, value);

        // save changes
        save();
    }

    /**
     * Put long value
     *
     * @param key
     * @param value
     */
    public void setValue(String key, long value) {
        // enter edit mode
        edit();

        // put value
        mEditor.putLong(key, value);

        // save changes
        save();
    }

    /**
     * Put float value
     *
     * @param key
     * @param value
     */
    public void setValue(String key, float value) {
        // enter edit mode
        edit();

        // put value
        mEditor.putFloat(key, value);

        // save changes
        save();
    }

    /**
     * Get int value
     * @param key
     * @return
     */
    public int getIntValue(String key) {
        // init settings
        init();

        return mSettings.getInt(key, 0);
    }

    /**
     * Get long value
     * @param key
     * @return
     */
    public long getLongValue(String key) {
        // init settings
        init();

        return mSettings.getLong(key, 0);
    }

    /**
     * Get String value
     * @param key
     * @return
     */
    public String getStringValue(String key) {
        // init settings
        init();

        return mSettings.getString(key, "");
    }

    /**
     * Get boolean value
     * @param key
     * @return
     */
    public boolean getBooleanValue(String key) {
        // init settings
        init();

        return mSettings.getBoolean(key, false);
    }

    /**
     * Get float value
     * @param key
     * @return
     */
    public float getFloatValue(String key) {
        // init settings
        init();

        return mSettings.getFloat(key, 0);
    }

    /**
     * Put int value
     *
     * @param key
     * @param value
     */
    public void setValue(String key, int value) {
        // enter edit mode
        edit();

        // put value
        mEditor.putInt(key, value);

        // save changes
        save();
    }
}
