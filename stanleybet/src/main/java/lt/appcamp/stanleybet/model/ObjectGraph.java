package lt.appcamp.stanleybet.model;

import android.content.ContentProviderOperation;
import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import lt.appcamp.utils.AppJson;
import lt.appcamp.stanleybet.app.App;

/**
 * Created by Tadas on 5/24/13.
 *
 */
public class ObjectGraph {

    public String imageBaseUrl;

    public ArrayList<Countries> leagueses = new ArrayList<Countries>();
//
//    public ArrayList<Place> places = new ArrayList<Place>();
//
//    public ArrayList<Event> events = new ArrayList<Event>();
//
//    public ArrayList<Packages> packageses = new ArrayList<Packages>();
//
//    public ArrayList<Trip> trips = new ArrayList<Trip>();

    public static ObjectGraph newInstance(JSONObject json) throws JSONException {
        ObjectGraph object = new ObjectGraph();

        object.imageBaseUrl = AppJson.getString(json, "image_base_url");
        App.instance().imageBaseUrl = object.imageBaseUrl;

        //Parse Categories
//        JSONArray jArr = AppJson.getJsonArray(json, "categories");
//        for (int i = 0; i < jArr.length(); i++) {
//            ArrayList<Category> categorieList = Category.getCategories(jArr.getJSONObject(i));
//            object.categories.addAll(categorieList);
//        }
//
//        //Parse Places
//        jArr = AppJson.getJsonArray(json, "objects");
//        for (int i = 0; i < jArr.length(); i++) {
//            ArrayList<Place> placeList = Place.getPlaces(jArr.getJSONObject(i));
//            object.places.addAll(placeList);
//        }
//
//        //Parse Event
//        jArr = AppJson.getJsonArray(json, "events");
//        for (int i = 0; i < jArr.length(); i++) {
//            ArrayList<Event> eventList = Event.getEvents(jArr.getJSONObject(i));
//            object.events.addAll(eventList);
//        }
//
//        //Parse Packages
//        jArr = AppJson.getJsonArray(json, "packages");
//        for (int i = 0; i < jArr.length(); i++) {
//            ArrayList<Packages> packagesList = Packages.getPackageArray(jArr.getJSONObject(i));
//            object.packageses.addAll(packagesList);
//        }
//
//        //Parse Trips
//        jArr = AppJson.getJsonArray(json, "trips");
//        for (int i = 0; i < jArr.length(); i++) {
//            ArrayList<Trip> tripsList = Trip.getTrips(jArr.getJSONObject(i));
//            object.trips.addAll(tripsList);
//        }

        return object;
    }

    public int applyBatch(Context context) {


        long start = System.currentTimeMillis();


        ArrayList<ContentProviderOperation> batch = new ArrayList<ContentProviderOperation>();

        //First delete old data
//        batch.add(ContentProviderOperation.newDelete(Packages.CONTENT_URI).build());
//        batch.add(ContentProviderOperation.newDelete(CouponGroup.CONTENT_URI).build());
//        batch.add(ContentProviderOperation.newDelete(Coupon.CONTENT_URI).build());
//        batch.add(ContentProviderOperation.newDelete(Place.CONTENT_URI).build());
//        batch.add(ContentProviderOperation.newDelete(Event.CONTENT_URI).build());
//        batch.add(ContentProviderOperation.newDelete(CategoryPlaces.CONTENT_URI).build());
//        batch.add(ContentProviderOperation.newDelete(Category.CONTENT_URI).build());

        /***/
//        batch.add(ContentProviderOperation.newDelete(Trip.CONTENT_URI).build());
//        batch.add(ContentProviderOperation.newDelete(TripObjects.CONTENT_URI).build());
        /***/
        
//        try {
//            //Categories
//            for (Category category : categories) {
//                if (category.validate(context)) {
//                    batch.addAll(category.getBatch(context));
//                }
//            }
//
//            //Places
//            for (Place place : places) {
//                if (place.validate(context)) {
//                    batch.addAll(place.getBatch(context));
//                }
//            }
//
//            //Events
//            for (Event event : events) {
//                if (event.validate(context)) {
//                    batch.addAll(event.getBatch(context));
//                }
//            }
//
//            //Packages
//            for (Packages pack : packageses) {
//                if (pack.validate(context)) {
//                    batch.addAll(pack.getBatch(context));
//                }
//            }
//
//            //Trips
//            for (Trip trip : trips) {
//                if (trip.validate(context)) {
//                    batch.addAll(trip.getBatch(context));
//                }
//            }
//
//            context.getContentResolver().applyBatch(DbModel.AUTHORITY, batch);
//
//
//            //notify
//            context.getContentResolver().notifyChange(Packages.CONTENT_URI,null);
//            context.getContentResolver().notifyChange(CouponGroup.CONTENT_URI,null);
//            context.getContentResolver().notifyChange(Coupon.CONTENT_URI,null);
//            context.getContentResolver().notifyChange(Place.CONTENT_URI,null);
//            context.getContentResolver().notifyChange(Event.CONTENT_URI,null);
//            context.getContentResolver().notifyChange(CategoryPlaces.CONTENT_URI,null);
//            context.getContentResolver().notifyChange(Category.CONTENT_URI,null);
//
//            /***/
//            context.getContentResolver().notifyChange(Trip.CONTENT_URI,null);
//            context.getContentResolver().notifyChange(TripObjects.CONTENT_URI,null);
//            /***/
//
//        } catch (RemoteException e) {
//            throw new RuntimeException("Problem applying batch operation", e);
//        } catch (OperationApplicationException e) {
//            throw new RuntimeException("Problem applying batch operation", e);
//        }
//
//        AppLog.d("Insert ObjectGraph Batch [" + batch.size() + "] took:" + ((System.currentTimeMillis() - start) / 1000) + " s");
//
//        return DbModel.DATA_SAVED;

        return 0;

    }


    @Override
    public String toString() {
        return super.toString();
//        return "ObjectGraph{" +
//                "events=" + events +
//                "places=" + places +
//                '}';
    }
}
