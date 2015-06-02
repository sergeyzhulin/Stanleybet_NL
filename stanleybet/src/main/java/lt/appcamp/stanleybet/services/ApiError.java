package lt.appcamp.stanleybet.services;

import com.android.volley.VolleyError;
import android.content.Context;
import android.widget.Toast;
import com.android.volley.NetworkResponse;
import com.android.volley.VolleyError;

import lt.appcamp.stanleybet.R;

/**
 * Created by Tadas on 6/6/13.
 */
public class ApiError extends VolleyError {
    public String code = "";
    public String message = "";

    public ApiError(String code, String message) {
        super(code);
        this.code = code;
        this.message = message;
    }

    public ApiError(NetworkResponse networkResponse) {
        super(networkResponse);
    }

    public ApiError(Throwable cause) {
        super(cause);
    }

    @Override
    public String toString() {
        return "ApiError{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                "} " + super.toString();
    }

    public static void onError(Context context, VolleyError error) {
        //TODO kai bad token, reikia priisjungti is naujo ir redirect i prisijungimo langa isvalant visa nativeAuth

//        if (error instanceof ApiError) {
//
//            ApiError err = (ApiError) error;
//
//            if (err.code.equals(Api.ERROR_BAD_PARAMS)) {
//                Toast.makeText(context, R.string.server_error_bad_params, Toast.LENGTH_SHORT).show();
//            } else if (err.code.equals(Api.ERROR_USER_EXISTS)) {
//                Toast.makeText(context, R.string.server_error_user_exists, Toast.LENGTH_SHORT).show();
//            } else if (err.code.equals(Api.ERROR_BAD_TOKEN)) {
//                Toast.makeText(context, R.string.server_error_bad_token, Toast.LENGTH_SHORT).show();
//            } else if (err.code.equals(Api.ERROR_TOKEN_IS_EMPTY)) {
//                Toast.makeText(context, R.string.server_error_token_empty, Toast.LENGTH_SHORT).show();
//            } else if (err.code.equals(Api.ERROR_AUTHENTIFICATION_FAILED)) {
//                Toast.makeText(context, R.string.server_error_authentification_failed, Toast.LENGTH_SHORT).show();
//            } else if (err.code.equals(Api.ERROR_BAD_FIRST_NAME)) {
//                Toast.makeText(context, R.string.server_error_first_name, Toast.LENGTH_SHORT).show();
//            } else if (err.code.equals(Api.ERROR_BAD_LAST_NAME)) {
//                Toast.makeText(context, R.string.server_error_last_name, Toast.LENGTH_SHORT).show();
//            } else if (err.code.equals(Api.ERROR_BAD_EMAIL)) {
//                Toast.makeText(context, R.string.server_error_email, Toast.LENGTH_SHORT).show();
//            } else if (err.code.equals(Api.ERROR_BAD_PASSWORD)) {
//                Toast.makeText(context, R.string.server_error_password, Toast.LENGTH_SHORT).show();
//            } else if (err.code.equals(Api.ERROR_BAD_PHONE_NO)) {
//                Toast.makeText(context, R.string.server_error_phone_no, Toast.LENGTH_SHORT).show();
//            } else if (err.code.equals(Api.ERROR_BAD_AGE)) {
//                Toast.makeText(context, R.string.server_error_age_of_birth, Toast.LENGTH_SHORT).show();
//            } else if (err.code.equals(Api.ERROR_BAD_GENDER)) {
//                Toast.makeText(context, R.string.server_error_gender, Toast.LENGTH_SHORT).show();
//            } else if (err.code.equals(Api.BAD_REQUEST_ID)) {
//                Toast.makeText(context, R.string.server_error_bad_request_id, Toast.LENGTH_SHORT).show();
//            } else {
//                Toast.makeText(context, R.string.server_error_default, Toast.LENGTH_SHORT).show();
//            }
//        } else {
//            Toast.makeText(context, R.string.error_network, Toast.LENGTH_SHORT).show();
//        }
    }


}
