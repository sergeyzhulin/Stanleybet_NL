package lt.appcamp.stanleybet.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.google.analytics.tracking.android.Log;

import lt.appcamp.stanleybet.R;

/**
 * Created by cauac on 05/03/14.
 */
public class LoginFragment extends Fragment {
    public static final String ARG_OBJECT = "sb_login";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LinearLayout rootView;

        if (getArguments().getInt(LoginFragment.ARG_OBJECT) == 1)
            rootView = (LinearLayout) inflater.inflate(R.layout.fragment_user_account, container, false);
        else
            rootView = (LinearLayout) inflater.inflate(R.layout.fragment_user_logged_in, container, false);

        return rootView;
    }

}

