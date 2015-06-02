package lt.appcamp.utils;

import android.database.Cursor;

/**
 * SQLite DataBase cursor utility class
 * User: Vykintas Valkaitis <v.valkatis@appcamp.lt>
 * Date: 2013-05-07
 */
public class AppCursor {
    /**
     * Get double field value from cursor. Return 0 if field not found or cursor invalid.
     *
     * @param cursor
     * @param field
     * @return
     */
    public static double getDouble(Cursor cursor, String field) {
        return getDouble(cursor, field, 0);
    }

    /**
     * Get double field value from cursor with default value.
     *
     * @param cursor
     * @param field
     * @param value
     * @return
     */
    public static double getDouble(Cursor cursor, String field, double value) {
        // default output
        double output = value;

        // check cursor
        if (!isValid(cursor, field)) return output;

        // get value from cursor
        output = cursor.getDouble(cursor.getColumnIndex(field));

        return output;
    }

    /**
     * Get float field value from cursor. Return 0 if field not found or cursor invalid.
     *
     * @param cursor
     * @param field
     * @return
     */
    public static float getFloat(Cursor cursor, String field) {
        return getFloat(cursor, field, 0);
    }

    /**
     * Get long field value from cursor with default value.
     *
     * @param cursor
     * @param field
     * @param value
     * @return
     */
    public static float getFloat(Cursor cursor, String field, float value) {
        // default output
        float output = value;

        // check cursor
        if (!isValid(cursor, field)) return output;

        // get value from cursor
        output = cursor.getFloat(cursor.getColumnIndex(field));

        return output;
    }

    /**
     * Get int field value from cursor. Return 0 if field not found or cursor invalid.
     *
     * @param cursor
     * @param field
     * @return
     */
    public static int getInt(Cursor cursor, String field) {
        return getInt(cursor, field, 0);
    }

    /**
     * Get int field value from cursor with default value.
     *
     * @param cursor
     * @param field
     * @param value
     * @return
     */
    public static int getInt(Cursor cursor, String field, int value) {
        // default output
        int output = value;

        // check cursor
        if (!isValid(cursor, field)) return output;

        // get value from cursor
        output = cursor.getInt(cursor.getColumnIndex(field));

        return output;
    }

    /**
     * Get long field value from cursor. Return 0 if field not found or cursor invalid.
     *
     * @param cursor
     * @param field
     * @return
     */
    public static long getLong(Cursor cursor, String field) {
        return getLong(cursor, field, 0);
    }

    /**
     * Get long field value from cursor with default value.
     *
     * @param cursor
     * @param field
     * @param value
     * @return
     */
    public static long getLong(Cursor cursor, String field, long value) {
        // default output
        long output = value;

        // check cursor
        if (!isValid(cursor, field)) return output;

        // get value from cursor
        output = cursor.getLong(cursor.getColumnIndex(field));

        return output;
    }

    /**
     * Get String field value from cursor. Return null if field not found or cursor invalid.
     *
     * @param cursor
     * @param field
     * @return
     */
    public static String getString(Cursor cursor, String field) {
        return getString(cursor, field, null);
    }

    /**
     * Get String field value from cursor with default value.
     *
     * @param cursor
     * @param field
     * @param value
     * @return
     */
    public static String getString(Cursor cursor, String field, String value) {
        // default output
        String output = value;

        // check cursor
        if (!isValid(cursor, field)) return output;

        // get value from cursor
        output = cursor.getString(cursor.getColumnIndex(field));

        return output;
    }

    /**
     * Validate cursor and field
     *
     * @param cursor
     * @param field
     * @return
     */
    private static boolean isValid(Cursor cursor, String field) {
        // check cursor
        if (cursor == null || cursor.getColumnCount() == 0) return false;

        // check if field exists
        if (cursor.getColumnIndex(field) < 0) return false;

        // check if field is null
        if (cursor.isNull(cursor.getColumnIndex(field))) return false;

        return true;
    }
}
