package lt.appcamp.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * User: Vykintas Valkaitis <v.valkaitis@appcamp.lt>
 * Date: 2013-05-13
 */
public abstract class AppDataBase extends android.database.sqlite.SQLiteOpenHelper {
    /**
     * Application context
     */
    protected Context mContext = null;

    public AppDataBase(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);

        // save context
        mContext = context;
    }

    /**
     * Exec SQL from file in assets
     * @param filename
     */
    protected void execSQLFile(String filename) {
        execSQLFile(filename, getWritableDatabase());
    }

    /**
     * Exec SQL from file in assets
     * @param filename
     * @param db
     */
    protected void execSQLFile(String filename, SQLiteDatabase db) {
        // get SQL
        String sql = getSQLFromFile(filename);

        // check sql
        if (sql == null || sql.length() == 0) return;

        // split sql queries
        String[] queries = sql.split(";");

        // begin transaction
        db.beginTransaction();

        try {
            // iteratate and exec queries
            for (String query : queries) {
                db.execSQL(query);
            }

            // set transaction successful
            db.setTransactionSuccessful();
        } catch (Exception e) {
            AppLog.e(e);
        } finally {
            // end transaction
            db.endTransaction();
        }
    }

    /**
     * Get SQL string from assets
     * @param filename
     * @return
     */
    protected String getSQLFromFile(String filename) {
        try {
            // open input stream from assets
            java.io.InputStream is = mContext.getAssets().open(filename);
            int size = is.available();

            // Read the entire asset into a local byte buffer.
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            // Convert the buffer into a string.
            return new String(buffer);
        } catch (Exception e) {
            AppLog.e(e);
        }

        return null;
    }

}
