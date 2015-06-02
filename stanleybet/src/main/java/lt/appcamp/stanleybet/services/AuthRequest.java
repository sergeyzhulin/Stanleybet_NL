package lt.appcamp.stanleybet.services;

import android.content.Context;
import android.util.Log;

import com.android.volley.Cache;
import com.android.volley.Response;

import org.json.JSONException;
import org.json.JSONObject;

import lt.appcamp.stanleybet.auth.AnonymousAuth;
import lt.appcamp.stanleybet.auth.BaseAuth;
//import lt.appcamp.visitvilnius.auth.FBAuth;
//import lt.appcamp.visitvilnius.auth.GAuth;
import lt.appcamp.stanleybet.auth.NativeAuth;

/**
 * Created by Tadas on 6/6/13.
 */
public class AuthRequest extends ApiRequest<BaseAuth> {
    private static final String TAG = AuthRequest.class.getSimpleName();
    
	private final int mType;
	private Context context;

    public AuthRequest(Context context, int type, String url, JSONObject jsonRequest, Response.Listener<BaseAuth> listener, Response.ErrorListener errorListener) {
        super(context, url, jsonRequest, listener, errorListener);
        this.mType = type;
        this.context = context;
    }

    @Override
    protected Response<BaseAuth> doInBackground(JSONObject json, Cache.Entry cacheEntry) {

        BaseAuth auth = null;

        try {
            switch (mType) {
                case BaseAuth.LOGIN_NATIVE:
                    auth = new NativeAuth(json);
                    break;
//                case BaseAuth.LOGIN_FB:
//                    auth = new FBAuth(json);
//                    break;
//                case BaseAuth.LOGIN_GPLUS:
//                    auth = new GAuth(json);
//                    break;
                case BaseAuth.LOGIN_ANONYMOUS:
                    auth = new AnonymousAuth(json, context);
                    break;
                default:
                    throw new UnsupportedOperationException("Invalid type:" + mType);
            }
            
            //DEBUG
            Log.d(TAG, "auth: " + auth.toString() + " token: " + auth.token);

            return Response.success(auth, cacheEntry);
        } catch (JSONException e) {
            e.printStackTrace();
            return Response.error(new ApiError(e));
        }

    }
}
