package lt.appcamp.utils;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Parcel;
import android.util.Log;
import org.apache.http.message.BasicNameValuePair;

/**
 * ADB Log proxy class
 * User: Vykintas Valkaitis <v.valkatis@appcamp.lt>
 * Date: 2013-05-07
 */
public class AppLog {

    public static final boolean ALLOW_LOG = false;

    /**
     * Log Exception error
     *
     * @param value
     */
    public static void e(Exception value) {
        AppLog.error(value.getMessage());
    }


    /**
     * Log String error
     *
     * @param value
     */
    private static void error(String value) {
        if (ALLOW_LOG) {
            try {
                // create throwable
                Throwable t = new Throwable();
                StackTraceElement[] elements = t.getStackTrace();

                // build tag from throwable
                String tag = elements[2].getClassName() + "(" + elements[2].getMethodName() + ":" + elements[2].getLineNumber() + ")";

                // log debug
                Log.e(tag, value);
            } catch (Exception e) {
            }
        }
    }

    /**
     * Log Exception error with stacktrace flag
     *
     * @param value
     */
    public static void e(Exception value, boolean trace) {
        // Log Exception info
        AppLog.error(value.toString());

        // display stack trace
        if (trace) {
            for (StackTraceElement element : value.getStackTrace()) {
                AppLog.error(element.toString());
            }
        }
    }

    /**
     * Log String error
     *
     * @param value
     */
    public static void e(String value) {
        AppLog.error(value);
    }

    /**
     * Log Exception debug
     *
     * @param value
     */
    public static void d(Exception value) {
        // Log Exception info
        AppLog.debug(value.getMessage());
    }

    /**
     * Log String debug
     *
     * @param value
     */
    private static void debug(String value) {
        if (ALLOW_LOG) {
            try {
                // create throwable
                Throwable t = new Throwable();
                StackTraceElement[] elements = t.getStackTrace();

                // build tag from throwable
                String tag = elements[2].getClassName() + "(" + elements[2].getMethodName() + ":" + elements[2].getLineNumber() + ")";

                // log debug
                Log.d(tag, value);
            } catch (Exception e) {
            }
        }
    }

    /**
     * Log Exception debug with stacktrace flag
     *
     * @param value
     */
    public static void d(Exception value, boolean trace) {
        // Log Exception info
        AppLog.debug(value.toString());

        // display stack trace
        if (trace) {
            for (StackTraceElement element : value.getStackTrace()) {
                AppLog.debug(element.toString());
            }
        }
    }

    /**
     * Log String debug
     *
     * @param value
     */
    public static void d(String value) {
        AppLog.debug(value);
    }

    /**
     * Log double/float debug
     *
     * @param value
     */
    public static void d(double value) {
        AppLog.debug(String.valueOf(value));
    }

    /**
     * Log long/int debug
     *
     * @param value
     */
    public static void d(long value) {
        AppLog.debug(String.valueOf(value));
    }

    /**
     * Log Cursor debug
     *
     * @param value
     */
    public static void d(Cursor value) {
        if (value != null && value.getCount() > 0 && value.getColumnCount() > 0) {

            if (value.getPosition() == -1) {
                AppLog.e("Error cursor in -1 pos");
                return;
            }


            StringBuilder builder = new StringBuilder();
            for (String column : value.getColumnNames()) {
                builder.append("[" + column + ":" + AppCursor.getString(value, column) + "]");
            }

            AppLog.debug(builder.toString());
        }
    }

    /**
     * Log Cursor debug
     *
     * @param value
     */
    public static void d(Parcel value) {

        StringBuilder builder = new StringBuilder();
        for (String column : value.createStringArrayList()) {
            builder.append("[" + column + ":" + "]");
        }

        AppLog.debug(builder.toString());

    }

    /**
     * Log BasicNameValuePair debug
     *
     * @param value
     */
    public static void d(BasicNameValuePair value) {
        AppLog.debug(value.getName() + ":" + value.getValue());
    }

    /**
     * Log ContentValues debug
     *
     * @param value
     */
    public static void d(ContentValues value) {
        AppLog.debug(value.toString());
    }
}