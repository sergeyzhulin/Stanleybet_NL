package lt.appcamp.stanleybet.auth;

import android.content.Context;
import android.content.SharedPreferences;
import lt.appcamp.stanleybet.auth.AnonymousAuth;
import lt.appcamp.stanleybet.auth.BaseAuth;
//import lt.appcamp.stanleybet.auth.FBAuth;
//import lt.appcamp.stanleybet.auth.GAuth;
//import lt.appcamp.stanleybet.auth.NativeAuth;

/**
 * Created by Tadas on 6/28/13.
 */
public class AuthFactory {


    private static int getLoginType(Context context) {
        SharedPreferences pref = BaseAuth.getSharedPreferences(context);
        int loginType = pref.getInt(BaseAuth.LOGIN_TYPE, -1);
        return loginType;
    }

    public static BaseAuth getAuth(Context context) {

        int loginType = getLoginType(context);

        switch (loginType) {
//            case BaseAuth.LOGIN_NATIVE:
//                return new NativeAuth();
//            case BaseAuth.LOGIN_FB:
//                return new FBAuth();
//            case BaseAuth.LOGIN_GPLUS:
//                return new GAuth();
            case BaseAuth.LOGIN_ANONYMOUS:
                return new AnonymousAuth();
             default:
                return new AnonymousAuth();
        }

    }


}
