package lt.appcamp.stanleybet.model;

import android.content.ContentProviderOperation;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import lt.appcamp.utils.AppCursor;
import lt.appcamp.utils.AppJson;

/**
 * Created by Tadas on 5/27/13.
 */
public class Leagues extends DbModel {

    public static final String TABLE_NAME = "Leagues";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + TABLE_NAME);
    public static final Uri CONTENT_URI_LANG = CONTENT_URI.buildUpon().appendPath(UriPath.LANGUAGE).build();
    public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd." + AUTHORITY + "." + TABLE_NAME;
    public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd." + AUTHORITY + "." + TABLE_NAME;

    public static Uri languageUri(String language){
        return CONTENT_URI_LANG.buildUpon().appendPath(language).build();
    }

    //fields
    public long id;
//    public String lang;
    public String name;
//    public String imageUrl;
//    public String imageMapUrl;
//    public int type;

    private Leagues(long _id) {
        id = _id;
    }

    public static Leagues newInstance(Cursor c) {

        Leagues leagues = new Leagues(AppCursor.getLong(c, Columns.SPORT_ID));
        leagues.autoIncrementId = AppCursor.getLong(c, Columns._ID);
        leagues.created = AppCursor.getString(c, Columns.CREATED);
        leagues.modified = AppCursor.getString(c, Columns.MODIFIED);
        //Countries.lang = AppCursor.getString(c, Columns.LANG);
        leagues.name = AppCursor.getString(c, Columns.NAME);
        //Countries.imageUrl = AppCursor.getString(c, Columns.IMAGE_URL);
        //Countries.imageMapUrl = AppCursor.getString(c, Columns.IMAGE_MAP_URL);
        //Countries.type = AppCursor.getInt(c, Columns.TYPE);
        return leagues;
    }

    public static Leagues newInstance(long id, String name, JSONObject jsonObject) throws JSONException {
        Leagues leagues = new Leagues(id);
        //leagues.imageUrl = imageUrl;
        //leagues.imageMapUrl = imageMapUrl;
        //leagues.lang = AppJson.getString(jsonObject, "lang");
        leagues.name = AppJson.getString(jsonObject, "ItemName");

        //leagues.type = type;
        //TODO: add type here
        
        return leagues;
    }

    public static Leagues newInstance(long id, JSONObject jsonObject) throws JSONException {
        Leagues leagues = new Leagues(id);
        //leagues.imageUrl = imageUrl;
        //leagues.imageMapUrl = imageMapUrl;
        //leagues.lang = AppJson.getString(jsonObject, "lang");
        leagues.name = AppJson.getString(jsonObject, "ItemName");

        //leagues.type = type;
        //TODO: add type here

        return leagues;
    }

    public static ArrayList<Leagues> getSports(JSONArray jsonArray) throws JSONException {

        //long id = AppJson.getLong(jsonObject, "ItemId");
        //String name = AppJson.getString(jsonObject, "ItemName");
        //String imageMapUrl = AppJson.getString(jsonObject, "image_map_url");
        
        //int type = AppJson.getInt(jsonObject, "type");

        //JSONArray jsonArray = AppJson.getJsonArray(jsonObject, "");

        ArrayList<Leagues> leagueses = new ArrayList<Leagues>();

        for (long i = 0; i < jsonArray.length(); i++) {
            leagueses.add(Leagues.newInstance(i, jsonArray.getJSONObject((int) i)));
        }
        return leagueses;
    }

    public static class Columns extends DbModel.Columns {
        private Columns() {
        }

        public static final String SPORT_ID = "sport_id";
        //public static final String LANG = "lang";
        public static final String NAME = "name";
        //public static final String IMAGE_URL = "image_url";
        //public static final String IMAGE_MAP_URL = "image_map_url";
        //public static final String TYPE = "type";
    }

    public static final void getCreatedScript(SQLiteDatabase db, Context context) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + Countries.TABLE_NAME + "(" +
                Columns._ID + " INTEGER PRIMARY KEY " + ", " +
                Columns.CREATED + " INTEGER" + ", " +
                Columns.MODIFIED + " INTEGER" + ", " +
                Columns.SPORT_ID + " INTEGER" + ", " +
                Columns.NAME + " TEXT" + ", " +
//                Columns.TEXT + " TEXT" + ", " +
//                Columns.IMAGE_URL + " TEXT" + ", " +
//                Columns.IMAGE_MAP_URL + " TEXT" + ", " +
//                Columns.TYPE + " INTEGER" +
                ");");
    }

    public static final void getIndexScript(SQLiteDatabase db, Context context) {
        //db.execSQL("CREATE UNIQUE INDEX sport_id_lang ON " + TABLE_NAME + "(" + Columns.SPORT_ID + "," + Columns.NAME + ")");
        db.execSQL("CREATE INDEX sport_id ON " +TABLE_NAME+"("+ Columns.SPORT_ID+")");
        db.execSQL("CREATE INDEX sport_lang ON " +TABLE_NAME+"("+ Columns.NAME+")");
    }

    public static final Parcelable.Creator<Countries> CREATOR = new Parcelable.Creator<Countries>() {
        public Countries createFromParcel(Parcel p) {
            return new Countries(p);
        }

        public Countries[] newArray(int size) {
            return new Countries[size];
        }
    };

    public Leagues(Parcel p) {
        autoIncrementId = p.readLong();
        created = p.readString();
        modified = p.readString();
        id = p.readLong();
        name = p.readString();
//        text = p.readString();
//        imageUrl = p.readString();
//        imageMapUrl = p.readString();
//        type = p .readInt();
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(autoIncrementId);
        dest.writeString(created);
        dest.writeString(modified);
        dest.writeLong(id);
        dest.writeString(name);
//        dest.writeString(text);
//        dest.writeString(imageUrl);
//        dest.writeString(imageMapUrl);
//        dest.writeInt(type);
    }

    public int describeContents() {
        return 0;
    }

    public void fillContentValues(ContentValues values) {
        values.put(Columns.SPORT_ID, id);
        values.put(Columns.NAME, name);
//        values.put(Columns.TEXT, text);
//        values.put(Columns.IMAGE_URL, imageUrl);
//        values.put(Columns.IMAGE_MAP_URL, imageMapUrl);
//        values.put(Columns.TYPE, type);
    }

    @Override
    public ArrayList<ContentProviderOperation> getBatch(Context context) {
        ArrayList<ContentProviderOperation> batch = new ArrayList<ContentProviderOperation>();

        //add coupon
        ContentProviderOperation.Builder builder = ContentProviderOperation.newInsert(CONTENT_URI);

        builder.withValues(getContentValues());
        builder.withYieldAllowed(true);
        batch.add(builder.build());

        return batch;
    }


    @Override
    public boolean validate(Context context) {
        if (id > 0 && !TextUtils.isEmpty(name)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return "Countries{" +
                "id=" + id +
                ", name='" + name + '\'' +
//                ", text='" + text + '\'' +
                '}';
    }
}
