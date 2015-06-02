package lt.appcamp.stanleybet.dbms;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import lt.appcamp.stanleybet.model.Countries;
import lt.appcamp.stanleybet.model.Leagues;
import lt.appcamp.utils.AppJson;

/**
 * Created by stasmanas on 14-04-02.
 */
public class StableyBetDBWrapper {
    private Context mContext;

    StableyBetDBWrapper(Context context) {
        mContext = context;
    }

    public  ArrayList<List> getNavigationTree(JSONArray jsonArray) throws JSONException {
        //long id = AppJson.getLong(jsonObject, "ItemId");
        //String name = AppJson.getString(jsonObject, "ItemName");
        //String imageMapUrl = AppJson.getString(jsonObject, "image_map_url");
        //int type = AppJson.getInt(jsonObject, "type");
        ArrayList<List> navigationTree = new ArrayList<List>();
        ArrayList<Countries> countriesAL = new ArrayList<Countries>();
        ArrayList<Leagues> leaguesAL = new ArrayList<Leagues>();
        ArrayList<List> sports_main = new ArrayList<List>();
        JSONArray jsonArrayCountry = new JSONArray();
        JSONArray jsonArrayLeagues = new JSONArray();
        DaoMaster.DevOpenHelper db = new DaoMaster.DevOpenHelper(mContext, "stanleybet-db", null);
        DaoMaster daoMaster = new DaoMaster(db.getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();

        for (long i = 0; i < jsonArray.length(); i++) {
            ArrayList<lt.appcamp.stanleybet.model.Sport> sports = new ArrayList<lt.appcamp.stanleybet.model.Sport>();
            jsonArrayCountry = AppJson.getJsonArray(jsonArray.getJSONObject((int) i), "Children");
            lt.appcamp.stanleybet.model.Sport sport = lt.appcamp.stanleybet.model.Sport.newInstance(i, jsonArray.getJSONObject((int) i));
            ArrayList<List> countries = new ArrayList<List>();

            for (long x = 0; x < jsonArrayCountry.length(); x++) {
                ArrayList<Countries> country = new ArrayList<Countries>();

                jsonArrayLeagues = AppJson.getJsonArray(jsonArrayCountry.getJSONObject((int) x), "Children");
                Countries countries_element = Countries.newInstance(x, jsonArrayCountry.getJSONObject((int) x));
                country.add(countries_element);

                for (long x1 = 0; x1 < jsonArrayLeagues.length(); x1++) {
                    leaguesAL.add(Leagues.newInstance(x1, jsonArrayLeagues.getJSONObject((int) x1)));
                }

                countries.add(country);
                countries.add(leaguesAL);
            }

            sports.add(sport);
            sports_main.add(sports);
            sports_main.add(countries);
        }

        navigationTree.add(sports_main);
        //navigationTree.add(countriesAL);
        //navigationTree.add(leaguesAL);
        return navigationTree;

    }
}
