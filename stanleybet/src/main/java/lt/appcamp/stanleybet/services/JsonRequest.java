package lt.appcamp.stanleybet.services;

import android.content.Context;

import com.android.volley.Cache;
import com.android.volley.Response;

import org.apache.http.NameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Tadas on 7/10/13.
 */
public class JsonRequest extends ApiRequest<JSONObject> {


    public JsonRequest(Context context, String url, JSONObject jsonRequest, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        super(context, url, jsonRequest, listener, errorListener);
    }
    
    /** Added new constructor on 2013 10 23 by Justas. Now this class will support parameters (POST or GET). **/
    public JsonRequest(Context context, int requestMethod, String url, ArrayList<NameValuePair> params, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        super(context, requestMethod, url, params, listener, errorListener);
    }  

    @Override
    protected Response<JSONObject> doInBackground(JSONObject json, Cache.Entry cacheEntry) {
        return Response.success(json, cacheEntry);
    }
    
}
