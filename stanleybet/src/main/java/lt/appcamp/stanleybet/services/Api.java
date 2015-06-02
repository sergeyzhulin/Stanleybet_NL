package lt.appcamp.stanleybet.services;

import com.android.volley.RequestQueue;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import lt.appcamp.stanleybet.auth.BaseAuth;
import lt.appcamp.stanleybet.auth.NativeAuth;
import lt.appcamp.stanleybet.helper.ConWin;
import lt.appcamp.stanleybet.model.ObjectGraph;
import lt.appcamp.utils.AppLog;
import lt.appcamp.stanleybet.auth.AnonymousAuth;
import lt.appcamp.stanleybet.auth.BaseAuth;
//import lt.appcamp.visitvilnius.auth.ExternalAuth;
import lt.appcamp.stanleybet.auth.NativeAuth;
//import lt.appcamp.visitvilnius.helper.ConWin;
//import lt.appcamp.visitvilnius.model.ObjectGraph;
//import lt.appcamp.visitvilnius.model.OrderGraph;
//import lt.appcamp.visitvilnius.model.OrderPackages;
import lt.appcamp.stanleybet.services.AuthRequest;

/**
 * Created by Tadas on 5/24/13.
 */

/** NOTICE: changed current_device_id to device_id **/
public class Api {

    private final RequestQueue mQueue;
    public Context mContext;
    //Changed to public from private
    public static final String API_VERSION = "v1";
    
    private static final String API_URL = ConWin.API_URL; //+ API_VERSION + "/";
    private static final String API_KEY = ConWin.API_KEY;
    
    private static final int OS_TYPE = 2; //constant from back end

    private static final String WS_GET_NAVIGATION_TREE = "GetContentNavigationTreeV1";
    private static final String WS_GET_DATA_BUNDLE = "get-data-bundle";
    private static final String WS_LOGOUT_USER = "logout-user";
    private static final String WS_CREATE_USER = "create_user";
    private static final String WS_LOGIN_USER = "login-user";
    private static final String WS_UPDATE_USER = "update-user";
    private static final String WS_LOGIN_EXTERNAL_USER = "login-external-user";
    private static final String WS_GET_MY_PURCHASE_LIST = "get-my-purchase-list";
    private static final String WS_REMIND_PASSWORD = "reset-password";
    private static final String WS_CREATE_ORDER = "create-order";
    private static final String WS_UPDATE_ORDER_STATUS = "update-order-status";
    //New web service for anonymous purchases
    private static final String WS_REGISTER_USER = "register-user";
    
    public static final String ERROR_BAD_PARAMS = "BAD_PARAMS";
    public static final String ERROR_USER_EXISTS = "USER_EXISTS";
    public static final String ERROR_BAD_TOKEN = "BAD_TOKEN";
    public static final String ERROR_AUTHENTIFICATION_FAILED = "AUTHENTIFICATION_FAILED";
    public static final String ERROR_TOKEN_IS_EMPTY = "TOKEN_IS_EMPTY";
    public static final String ERROR_DB_ERROR = "DB_ERROR";
    public static final String ERROR_BAD_FIRST_NAME = "BAD_FIRST_NAME";
    public static final String ERROR_BAD_LAST_NAME = "BAD_LAST_NAME";
    public static final String ERROR_BAD_EMAIL = "BAD_EMAIL";
    public static final String ERROR_BAD_PASSWORD = "BAD_PASSWORD";
    public static final String ERROR_BAD_PHONE_NO = "BAD_PHONE_NO";
    public static final String ERROR_BAD_AGE = "BAD_AGE";
    public static final String ERROR_BAD_GENDER = "BAD_GENDER" ;
    public static final String BAD_REQUEST_ID = "BAD_REQUEST_ID";


    public Api(Context context, RequestQueue queue) {
        mQueue = queue;
        mContext = context;
    }


    public void cancelAll(Object tag) {
        mQueue.cancelAll(tag);
    }

    public void cancelAll(RequestQueue.RequestFilter filter) {
        mQueue.cancelAll(filter);
    }

    /**
     * Make Request using Custom Callbacks
     *
     * @param successListener
     * @param errorListener
     * @return
     */
    public Request<?> getDataBundle(Response.Listener<ObjectGraph> successListener, Response.ErrorListener errorListener) {

        JSONObject requestJson = makeRequest(WS_GET_DATA_BUNDLE, new JSONObject());

        ObjectGraphRequest request = new ObjectGraphRequest(mContext, API_URL, requestJson, successListener, errorListener);

        return mQueue.add(request);
    }

    /**
     * Make Request using Custom Callbacks
     *
     * @param successListener
     * @param errorListener
     * @return
     */
    public Request<?> getNavigationTree(Response.Listener<ObjectGraph> successListener, Response.ErrorListener errorListener) {
        try {
            JSONObject json = new JSONObject();
            json.put("SessionId", 0);
            json.put("PlayerId", 0);
            json.put("Language", "en");
            json.put("OfferCode", "*");
            json.put("Period", "today");
            JSONObject requestJson = makeRequest("navigation/tree", json);

            ObjectGraphRequest request = new ObjectGraphRequest(mContext, API_URL, requestJson, successListener, errorListener);

            return mQueue.add(request);
        } catch (JSONException e) {
            AppLog.e(e);
        }

        return null;
    }

    /**
     * Make Request using Custom Callbacks
     *
     * @param successListener
     * @param errorListener
     * @return
     */
    public Request<?> getNavigationTreeFromStringUri(Response.Listener<String> successListener, Response.ErrorListener errorListener) {
            String uri = String.format("http://api.test.stanleybet.com:20000/content/v1/navigation/tree/0/0/en/*/today.json");

            StringRequest request = new StringRequest(Request.Method.GET, uri, successListener, errorListener);

            return mQueue.add(request);
    }

    /**
     * Login user
     *
     * @param nativeAuth
     * @param successListener
     * @param errorListener
     * @return
     */
    public Request<?> loginUser(NativeAuth nativeAuth, Response.Listener<BaseAuth> successListener, Response.ErrorListener errorListener) {


        try {
            JSONObject json = new JSONObject();
            json.put("email", nativeAuth.email);
            json.put("password", nativeAuth.getPasswordSha1());
            json.put("device_id", nativeAuth.getDeviceId(mContext));
            JSONObject requestJson = makeRequest(WS_LOGIN_USER, json);

            AuthRequest request = new AuthRequest(mContext, nativeAuth.getUserAuthType(), API_URL, requestJson, successListener, errorListener);

            return mQueue.add(request);
        } catch (JSONException e) {
            AppLog.e(e);
        }

        return null;
    }

    /**
     * CreateUser
     *
     * @param nativeAuth
     * @param successListener
     * @param errorListener
     * @return
     */
    public Request<?> createUser(NativeAuth nativeAuth, Response.Listener<BaseAuth> successListener, Response.ErrorListener errorListener) {

        try {
            JSONObject json = new JSONObject();
            json.put("first_name", nativeAuth.firstName);
            json.put("last_name", nativeAuth.lastName);
            json.put("email", nativeAuth.email);
            json.put("password", nativeAuth.getPasswordSha1());
            json.put("phone_no", nativeAuth.phoneNo);
            json.put("birth_date", nativeAuth.birthDate);
            json.put("gender", nativeAuth.gender);
            json.put("device_id", nativeAuth.getDeviceId(mContext));

            JSONObject requestJson = makeRequest(WS_CREATE_USER, json);

            AuthRequest request = new AuthRequest(mContext, nativeAuth.getUserAuthType(), API_URL, requestJson, successListener, errorListener);

            return mQueue.add(request);
        } catch (JSONException e) {
            AppLog.e(e);
        }

        return null;
    }

    /**
     * UpdateUser
     *
     * @param nativeAuth
     * @param successListener
     * @param errorListener
     * @return
     */
    public Request<?> updateUser(NativeAuth nativeAuth, Response.Listener<BaseAuth> successListener, Response.ErrorListener errorListener) {

        try {
            JSONObject json = new JSONObject();
            json.put("first_name", nativeAuth.firstName);
            json.put("last_name", nativeAuth.lastName);
            json.put("email", nativeAuth.email);
            json.put("password", nativeAuth.getPasswordSha1());
            json.put("phone_no", nativeAuth.phoneNo);
            json.put("birth_date", nativeAuth.birthDate);
            json.put("gender", nativeAuth.gender);
            json.put("device_id", nativeAuth.getDeviceId(mContext));
            json.put("token", nativeAuth.token);

            JSONObject requestJson = makeRequest(WS_UPDATE_USER, json);

            AuthRequest request = new AuthRequest(mContext, nativeAuth.getUserAuthType(), API_URL, requestJson, successListener, errorListener);

            return mQueue.add(request);
        } catch (JSONException e) {
            AppLog.e(e);
        }

        return null;
    }

    /**
     * LogoutUser
     *
     * @param auth
     * @param successListener
     * @param errorListener
     * @return
     */
    public Request<?> logoutUser(BaseAuth auth, Response.Listener<BaseAuth> successListener, Response.ErrorListener errorListener) {

        try {
            JSONObject json = new JSONObject();
            json.put("token", auth.token);
            json.put("device_id", auth.getDeviceId(mContext));

            JSONObject requestJson = makeRequest(WS_LOGOUT_USER, json);

            AuthRequest request = new AuthRequest(mContext, auth.getUserAuthType(), API_URL, requestJson, successListener, errorListener);

            return mQueue.add(request);
        } catch (JSONException e) {
            AppLog.e(e);
        }

        return null;
    }

    /**
     *
     * @param email
     * @param successListener
     * @param errorListener
     * @return
     */
    public Request<?> remindPassword(String email, Response.Listener<JSONObject> successListener, Response.ErrorListener errorListener) {

        try {
            JSONObject json = new JSONObject();
            json.put("email", email);

            JSONObject requestJson = makeRequest(WS_REMIND_PASSWORD, json);

            JsonRequest request = new JsonRequest(mContext,API_URL,requestJson,successListener,errorListener);

            return mQueue.add(request);
        } catch (JSONException e) {
            AppLog.e(e);
        }

        return null;
    }

    /**
     * @param auth
     * @param successListener
     * @param errorListener
     * @return
     */
//    public Request<?> loginExternalUser(ExternalAuth auth, Response.Listener<ExternalAuth> successListener, Response.ErrorListener errorListener) {
//        try {
//            JSONObject json = new JSONObject();
//            json.put("token", auth.externalToken);
//            json.put("device_id", auth.getDeviceId(mContext));
//            json.put("email", auth.email);
//            json.put("auth_type", auth.getUserAuthType());
//
//            JSONObject requestJson = makeRequest(WS_LOGIN_EXTERNAL_USER, json);
//
//            AuthExternalRequest request = new AuthExternalRequest(mContext, auth.getUserAuthType(), API_URL, requestJson, successListener, errorListener);
//
//            return mQueue.add(request);
//        } catch (JSONException e) {
//            AppLog.e(e);
//        }
//
//        return null;
//    }

    /**
     *
     * @param auth
     * @param hideResponse
     * @param errorListener
     * @return
     */
//    public Request<?> getMyPurchaseList(final BaseAuth auth,boolean hideResponse, Response.Listener<OrderGraph> successListener, Response.ErrorListener errorListener) {
//        try {
//            JSONObject json = new JSONObject();
//            json.put("token", auth.token);
//            json.put("orders", getOrders(auth.email));
//
//            JSONObject requestJson = makeRequest(WS_GET_MY_PURCHASE_LIST, json);
//
//            OrderGraphRequest request = new OrderGraphRequest(mContext, API_URL,auth.email,hideResponse, requestJson, successListener, errorListener);
//
//            return mQueue.add(request);
//        } catch (JSONException e) {
//            AppLog.e(e);
//        }
//
//        return null;
//    }

    /**
     * getOrders for request
     * @return
     */
//    private JSONArray getOrders(String email) {
//
//        //get packages
//        ContentResolver cr = mContext.getContentResolver();
//        Uri uri = OrderPackages.languageUri(ConWin.EN);
//        String where = OrderPackages.Columns.USER_EMAIL+" = '"+email+"'";
//                Cursor cursor = cr.query(uri, null, where, null, null);
//
//        if (cursor != null && cursor.getCount() > 0) {
//
//            //Build Orders hashmap
//
//            HashMap<Integer, ArrayList<OrderPackages>> orders = new HashMap<Integer, ArrayList<OrderPackages>>();
//
//            while (cursor.moveToNext()) {
//                OrderPackages pack = OrderPackages.newInstance(cursor);
//
//                if (orders.containsKey(pack.orderId)) {
//                    orders.get(pack.orderId).add(pack);
//                } else {
//                    ArrayList<OrderPackages> packList = new ArrayList<OrderPackages>();
//                    packList.add(pack);
//                    orders.put((int)pack.orderId,packList);
//                }
//
//            }
//
//
//            try {
//                //Build Json
//                JSONArray jArr = new JSONArray();
//
//                for (Integer orderId : orders.keySet()) {
//                    JSONObject orderJson = new JSONObject();
//
//                    //get packages
//                    JSONArray packagesJsonArr = new JSONArray();
//                    ArrayList<OrderPackages> packages = orders.get(orderId);
//
//                    for (OrderPackages ordPack : packages) {
//                        JSONObject packJson = new JSONObject();
//                        packJson.put("id", ordPack.id);
//                        packJson.put("status", ordPack.packageStatus);
//                        packJson.put("activation_date", ordPack.activationDate);
//                        packagesJsonArr.put(packJson);
//                    }
//
//                    orderJson.put("id", orderId);
//                    orderJson.put("packages", packagesJsonArr);
//
//                    jArr.put(orderJson);
//
//                }
//
//                return jArr;
//
//            } catch (JSONException e) {
//                e.printStackTrace();
//                AppLog.e(e);
//            }
//
//        }
//
//
//        return null;
//    }


    /**
     * Build Request Object
     *
     * @param method
     * @param params
     * @return
     */
    public JSONObject makeRequest(String method, JSONObject params) {
        JSONObject request = new JSONObject();
        try {
            request.put("method", method);
            params.put("api_key", API_KEY);
            request.put("params", params);
            request.put("id", null);
        } catch (JSONException e) {
            AppLog.e(e);
        }

        return request;
    }
    
    /**
    * Creates a new purchase order.
    * Uses POST to pass parameters.
    * 
    * @param packageID
    * @param requestID
    * @param paymentType
    * @param auth
    * @param successListener
    * @param errorListener
    * @return
    */
   public Request<?> createNewOrderPost(long packageID, long requestID, int paymentType, final BaseAuth auth, Response.Listener<JSONObject> successListener, Response.ErrorListener errorListener) {
	   //Create URL
	   String url = API_URL + "/" + WS_CREATE_ORDER;
           
       // Create List of BasicNameValuePairs
       ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
       params.add(new BasicNameValuePair("token", auth.token));
       params.add(new BasicNameValuePair("package_id", "" + packageID));
       params.add(new BasicNameValuePair("request_id", "" + requestID));
       params.add(new BasicNameValuePair("payment_type", "" + paymentType)); //ConWin.PAYMENT_OPTION_IN_APP_SMS));
           
       JsonRequest postRequest = new JsonRequest(mContext, Request.Method.POST, url, params, successListener, errorListener);
        	
       return mQueue.add(postRequest);
   }
   
   /**
    * Updates the status of the given mokejimai.lt order (payment) ID
    * Uses POST to pass parameters.
    * 
    * @param requestID
    * @param isCompleted
    * @param auth
    * @param successListener
    * @param errorListener
    * @return
    */
   public Request<?> updateOrderStatusPost(long requestID, boolean isCompleted, final BaseAuth auth, Response.Listener<JSONObject> successListener, Response.ErrorListener errorListener) {
	   //Create URL
	   String url = API_URL + "/" + WS_UPDATE_ORDER_STATUS;
           
       // Create List of BasicNameValuePairs
       ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
       params.add(new BasicNameValuePair("token", auth.token));
       params.add(new BasicNameValuePair("request_id", "" + requestID));
       params.add(new BasicNameValuePair("status", "" + (isCompleted ? 1 : 0)));
           
       JsonRequest postRequest = new JsonRequest(mContext, Request.Method.POST, url, params, successListener, errorListener);
        	
       return mQueue.add(postRequest);
   }

   
   /**
    * Creates a new purchase order.
    * Uses JSON to pass parameters.
    * 
    * @param packageID
    * @param requestID
    * @param paymentType
    * @param auth
    * @param successListener
    * @param errorListener
    * @return
    */
   public Request<?> createNewOrder(long packageID, long requestID, int paymentType, final BaseAuth auth, Response.Listener<JSONObject> successListener, Response.ErrorListener errorListener) {
	   try {
           JSONObject json = new JSONObject();
           json.put("token", auth.token);
           json.put("package_id", packageID);
           json.put("request_id", requestID);
           json.put("payment_type", paymentType);

           JSONObject requestJson = makeRequest(WS_CREATE_ORDER, json);

           JsonRequest request = new JsonRequest(mContext, API_URL, requestJson, successListener, errorListener);

           return mQueue.add(request);
       } catch (JSONException e) {
           AppLog.e(e);
       }	   
	   
       return null;
   }
   
   /**
    * Updates the status of the given mokejimai.lt order (payment) ID
    * Uses JSON to pass parameters.
    * 
    * @param requestID
    * @param isCompleted
    * @param auth
    * @param successListener
    * @param errorListener
    * @return
    */
   public Request<?> updateOrderStatus(long requestID, boolean isCompleted, final BaseAuth auth, Response.Listener<JSONObject> successListener, Response.ErrorListener errorListener) {
	   try {
           JSONObject json = new JSONObject();
           json.put("token", auth.token);
           json.put("request_id", requestID);
           json.put("status", (isCompleted ? 1 : 0));

           JSONObject requestJson = makeRequest(WS_UPDATE_ORDER_STATUS, json);

           JsonRequest request = new JsonRequest(mContext, API_URL, requestJson, successListener, errorListener);

           return mQueue.add(request);
       } catch (JSONException e) {
           AppLog.e(e);
       }
	   
       return null;
   }
   
   
   //New web service for anonymous purchases.
   /**
    * Register user
    *
    * @param nativeAuth
    * @param successListener
    * @param errorListener
    * @return
    */
   // REFERENCE: {"method": "register-user", "params": {"api_key" : "Godzila-591", "device_id":"5000", "device_name":"aa3434abbb", "push_token":"5c1983a1343434392f04", "os_type":"1", "os_version":"v1.6", "receive_news":"1", "receive_events":"1", "locale":"LT"}, "id": null}
   //public Request<?> registerUser(AnonymousAuth anonymousAuth, long deviceID, String deviceName, String pushToken, int osType, String osVersion, 
   
   public Request<?> registerUser(AnonymousAuth anonymousAuth, String pushToken, boolean shouldReceiveNews,
		   boolean shouldReceiveEvents, String locale, Response.Listener<BaseAuth> successListener,
		   Response.ErrorListener errorListener) {

       try {
           JSONObject json = new JSONObject();
           //NOTE: 2013 11 05 - changed to a method that generates a unique session ID based on timestamp and device id
           //UPDATE: 2013 11 06 - changed back ;D
           json.put("device_id", anonymousAuth.getDeviceId(mContext));//anonymousAuth.getEncodedDeviceId(mContext));
           json.put("device_name", anonymousAuth.getDeviceName());
           json.put("push_token", pushToken);
           json.put("os_type", OS_TYPE);
           json.put("os_version", anonymousAuth.getOSVersion());
           json.put("receive_news",  (shouldReceiveNews ? 1 : 0));
           json.put("receive_events", (shouldReceiveEvents ? 1 : 0));
           json.put("locale", locale); //Decided to pass locale as a parameter, so we could set it manually if need be.
           //App.instance().locale); //get the current locale
           JSONObject requestJson = makeRequest(WS_REGISTER_USER, json);

           AuthRequest request = new AuthRequest(mContext, anonymousAuth.getUserAuthType(), API_URL, requestJson, successListener, errorListener);

           return mQueue.add(request);
       } catch (JSONException e) {
           AppLog.e(e);
       }

       return null;
   }



}
