package lt.appcamp.stanleybet.services;

import android.content.Context;

import com.android.volley.Cache;
import com.android.volley.Response;

import org.json.JSONException;
import org.json.JSONObject;

import lt.appcamp.stanleybet.model.ObjectGraph;

/**
 * Created by Tadas on 6/6/13.
 */
public class ObjectGraphRequest extends ApiRequest<ObjectGraph> {


    public ObjectGraphRequest(Context context, String url, JSONObject jsonRequest, Response.Listener<ObjectGraph> listener, Response.ErrorListener errorListener) {
        super(context, url, jsonRequest, listener, errorListener);
    }

    @Override
    protected Response<ObjectGraph> doInBackground(JSONObject json, Cache.Entry cacheEntry) {

        try {
            ObjectGraph object = ObjectGraph.newInstance(json);
            object.applyBatch(getContext());
            return Response.success(object, cacheEntry);
        } catch (JSONException e) {
            e.printStackTrace();
            return Response.error(new ApiError(e));
        }

    }
}
