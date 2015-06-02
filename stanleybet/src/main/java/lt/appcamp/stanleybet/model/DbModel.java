package lt.appcamp.stanleybet.model;

import android.content.ContentProviderOperation;
import android.content.ContentValues;
import android.content.Context;
import android.os.Parcelable;
import android.provider.BaseColumns;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

abstract public class DbModel implements Parcelable {
    public static final String AUTHORITY = "lt.appcamp.visitvilnius";

    public interface UriPath {
        String LANGUAGE = "language";
        String LANGUAGE_JOIN_CATEGORY = "language_join_category";
        String LANGUAGE_JOIN_COUPONS_PLACES = "language_join_coupons_places";
        String LANGUAGE_JOIN_COUPONS = "language_join_coupons";
    }

    public static final int DATA_RECEIVED = 1;
    public static final int DATA_SAVED = 1;
    public static final int DATA_ERROR = 2;
    public static final int DATA_INVALID = 3;

    public String created;
    public String modified;

    public long autoIncrementId;

    public abstract ArrayList<ContentProviderOperation> getBatch(Context context);

    abstract public boolean validate(Context context);

    public ContentValues getContentValues() {
        final String now = getNowDate();
        created = now;
        modified = now;

        ContentValues values = new ContentValues();
        if (autoIncrementId > 0) {
            values.put(Columns._ID, autoIncrementId);
        }
        values.put(Columns.CREATED, created);
        values.put(Columns.MODIFIED, modified);

        fillContentValues(values);

        return values;
    }

    abstract void fillContentValues(ContentValues values);

    public DbModel() {

    }

    public DbModel(long autoIncrementId) {
        this.autoIncrementId = autoIncrementId;
    }

    public String getNowDate() {
        final Calendar cal = Calendar.getInstance();
        final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(cal.getTime());
    }

    public static class Columns implements BaseColumns {
        protected Columns() {
        }

        public static final String CREATED = "created";
        public static final String MODIFIED = "modified";
    }

    @Override
    public String toString() {
        return "DbModel{" +
                "created='" + created + '\'' +
                ", modified='" + modified + '\'' +
                ", autoIncrementId=" + autoIncrementId +
                "} ";
    }
}