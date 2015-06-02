package lt.appcamp.stanleybet.services;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Cache;
import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.HttpHeaderParser;

import org.apache.http.NameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import lt.appcamp.utils.AppJson;
import lt.appcamp.utils.AppLog;

/**
 * Created by Tadas on 6/6/13.
 */
public abstract class ApiRequest<T> extends JsonRequest<T> {
	
	private ArrayList<NameValuePair> params;        // the request params

    private Context mContext;

    private ApiRequest(Context context, int method, String url, JSONObject jsonRequest,
                       Response.Listener<T> listener, Response.ErrorListener errorListener) {
        super(method, url, (jsonRequest == null) ? null : jsonRequest.toString(), listener, errorListener);
        mContext = context;
    }

    public ApiRequest(Context context, String url, JSONObject jsonRequest, Response.Listener<T> listener,
                      Response.ErrorListener errorListener) {
        this(context, jsonRequest == null ? Method.GET : Method.POST, url, jsonRequest, listener, errorListener);

        AppLog.d("json:" + jsonRequest.toString());
    }
    
    /** Added new constructor on 2013 10 23 by Justas. Now this class will support parameters (POST or GET). **/
    public ApiRequest(Context context, int requestMethod, String url, ArrayList<NameValuePair> params, Response.Listener<T> listener,
                      Response.ErrorListener errorListener) {
		super(requestMethod, url, null, listener, errorListener);
		this.params = params;
		
		//AppLog.d("json:" + jsonRequest.toString());
	}

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {
            String jsonString = new String(response.data, HttpHeaderParser.parseCharset(response.headers));

            JSONObject responseJson = new JSONObject(jsonString);
            JSONObject dataJson = AppJson.getJsonObject(responseJson,"result");
            JSONObject errorJson = AppJson.getJsonObject(responseJson, "error");

            //If error string is empty, there is no errors
            if (!(errorJson.length() > 0)) {

                AppLog.d("Success:" + dataJson);

                Cache.Entry cacheEntry = HttpHeaderParser.parseCacheHeaders(response);
                return doInBackground(dataJson, cacheEntry);
            } else {
                AppLog.d("Error:" + errorJson);

                String code = AppJson.getString(errorJson, "code");
                String message = AppJson.getString(errorJson, "message");
                return Response.error(new ApiError(code, message));
            }

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return Response.error(new ApiError(e));
        } catch (JSONException je) {
            je.printStackTrace();
            return Response.error(new ApiError(je));
        }
    }
    
    @Override    
    public Map<String, String> getParams() throws AuthFailureError {
    	Map<String, String> map = new HashMap<String, String>();
    	// Iterate through the params and add them to our HashMap 
    	for (NameValuePair pair : params) {
    		map.put(pair.getName(), pair.getValue()); 
    	}         
    	return map;    
    } 


    protected abstract Response<T> doInBackground(JSONObject json, Cache.Entry cacheEntry);


    protected Context getContext() {
        return mContext;
    }
}
