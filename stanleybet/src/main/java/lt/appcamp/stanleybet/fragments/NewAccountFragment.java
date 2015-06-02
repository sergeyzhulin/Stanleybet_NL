package lt.appcamp.stanleybet.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import lt.appcamp.stanleybet.R;

/**
 * Created by cauac on 05/03/14.
 */
public class NewAccountFragment extends Fragment {
    public static final String ARG_OBJECT = "sb_create_account";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        int i = getArguments().getInt(ARG_OBJECT);
        LinearLayout rootView;

        if (i == 1) {
            rootView = (LinearLayout) inflater.inflate(R.layout.fragment_new_account_first_step, container, false);
        } else if (i == 2) {
            rootView = (LinearLayout) inflater.inflate(R.layout.fragment_new_account_second_step, container, false);
        } else {
            rootView = (LinearLayout) inflater.inflate(R.layout.fragment_new_account_third_step, container, false);
        }

        return rootView;
    }

}

